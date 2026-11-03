import api from '@/services/api'
import {getFilenameFromHeaders, triggerFileDownload} from "@/utils/download.ts";
import RestResponse from '@/models/api/RestResponseModel'
import { type IPageResponse } from '@/models/api/PageResponseModel'
import {
  PaginationRequestParameter,
  RequestModel,
  SortFieldParameter,
} from '@/models/api/RequestModel'
import { Participer } from '@/models/ParticiperModel'
import type { DataResponse, PagedDataResponse } from '@/models/api/DataResponseModel'

const restApiWithPage = api<IPageResponse<Participer>>()
const restApi = api<Participer>()
const BASE_URL = '/participers'

/** Helper to wrap response data in Participer instances */
function wrapParticiper(response: DataResponse<Participer> | PagedDataResponse<Participer>) {
  if (Array.isArray(response.data)) {
    response.data = response.data.map((r) => new Participer(r))
  } else if (response.data && typeof response.data === 'object') {
    response.data = new Participer(response.data)
  }
  return response
}

/** GET all with pagination */
export async function getAll(
  pagination?: PaginationRequestParameter,
  sortFields?: SortFieldParameter[],
) {
  const request = new RequestModel(BASE_URL, pagination, sortFields)
  const response = await restApiWithPage.GET(request.buildRequestUrl())
  return wrapParticiper(RestResponse.handlePagedResponse<Participer>(response)) as PagedDataResponse<Participer>
}

/** POST search with pagination */
export async function search(
  data: Partial<Participer>,
  pagination?: PaginationRequestParameter,
  sortFields?: SortFieldParameter[],
) {
  const request = new RequestModel(BASE_URL + '/search', pagination, sortFields)
  const response = await restApiWithPage.POST(request.buildRequestUrl(), data)
  return wrapParticiper(RestResponse.handlePagedResponse<Participer>(response)) as PagedDataResponse<Participer>
}

/** GET by ID */
export async function getById(id: number | string | undefined) {
  if (!id) throw new Error('getById: id is required')
  const response = await restApi.GET(BASE_URL + '/' + id)
  return wrapParticiper(RestResponse.handleDataResponse<Participer>(response)) as DataResponse<Participer>
}

export async function create(data: Partial<Participer>){
  const response = await restApi.POST(BASE_URL, data)
  return wrapParticiper(RestResponse.handleDataResponse<Participer>(response)) as DataResponse<Participer>
}

/** PUT update */
export async function update(id: number | string | undefined, data: Partial<Participer>) {
  if (!id) throw new Error("update: id is required")
  const response = await restApi.PUT(BASE_URL + '/' + id, data)
  return wrapParticiper(RestResponse.handleDataResponse<Participer>(response)) as DataResponse<Participer>
}

/** DELETE remove */
export async function remove(id: number | string | undefined) {
  if (!id) throw new Error("remove: id is required")
  const response = await restApi.DELETE(BASE_URL + '/' + id)
  return {
    success: response.returnCode === 1,
    error: response.returnCode !== 1 ? response.message : null,
  };
}
export async function exportCsv(
  participers: Partial<Participer[]>,
): Promise<{ success: boolean; error: string | null }> {
     const url = import.meta.env.VITE_APP_API_URL+BASE_URL+'/export/csv'

     try {
     const response = await fetch(url, {
                      method: 'POST',
                      headers: {
                        'Content-Type': 'application/json',
                      },
                      body: JSON.stringify(participers),
   })
     if (!response.ok) {
     const errorText = await response.text()
     console.error('CSV Export Failed - Server Error:', response.status, errorText)
                      throw new Error(`CSV export failed: . Status: .`)
   }
      const blob = await response.blob()
      const contentDisposition = response.headers.get('content-disposition');
      triggerFileDownload(blob,getFilenameFromHeaders(contentDisposition,"Participer.csv"));
      return {
        success: true,
        error: null,
   }

    } catch (error) {
      console.error('Error during CSV export:', error)
      return {
        success: false,
        error: error instanceof Error ? error.message : 'An unknown error occurred during export.',
      }
    }
  }
  export async function getAllByPersonneId(
    id: number | string | undefined,
    pagination?: PaginationRequestParameter,
    sortFields?: SortFieldParameter[],
  ) {
    if (!id) throw new Error('getAllByPersonneId: id of personne is required')
    const request = new RequestModel('/personnes/' + id + BASE_URL, pagination, sortFields)
    const response = await restApiWithPage.GET(request.buildRequestUrl())
    return wrapParticiper(
      RestResponse.handlePagedResponse<Participer>(response),
    ) as PagedDataResponse<Participer>
  }export async function getAllByProjetId(
    id: number | string | undefined,
    pagination?: PaginationRequestParameter,
    sortFields?: SortFieldParameter[],
  ) {
    if (!id) throw new Error('getAllByProjetId: id of projet is required')
    const request = new RequestModel('/projets/' + id + BASE_URL, pagination, sortFields)
    const response = await restApiWithPage.GET(request.buildRequestUrl())
    return wrapParticiper(
      RestResponse.handlePagedResponse<Participer>(response),
    ) as PagedDataResponse<Participer>
  }

