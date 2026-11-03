import api from '@/services/api'
import {getFilenameFromHeaders, triggerFileDownload} from "@/utils/download.ts";
import RestResponse from '@/models/api/RestResponseModel'
import { type IPageResponse } from '@/models/api/PageResponseModel'
import {
  PaginationRequestParameter,
  RequestModel,
  SortFieldParameter,
} from '@/models/api/RequestModel'
import { VFicheCompletePersonne } from '@/models/VFicheCompletePersonneModel'
import type { DataResponse, PagedDataResponse } from '@/models/api/DataResponseModel'

const restApiWithPage = api<IPageResponse<VFicheCompletePersonne>>()
const restApi = api<VFicheCompletePersonne>()
const BASE_URL = '/vfichecompletepersonnes'

/** Helper to wrap response data in VFicheCompletePersonne instances */
function wrapVFicheCompletePersonne(response: DataResponse<VFicheCompletePersonne> | PagedDataResponse<VFicheCompletePersonne>) {
  if (Array.isArray(response.data)) {
    response.data = response.data.map((r) => new VFicheCompletePersonne(r))
  } else if (response.data && typeof response.data === 'object') {
    response.data = new VFicheCompletePersonne(response.data)
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
  return wrapVFicheCompletePersonne(RestResponse.handlePagedResponse<VFicheCompletePersonne>(response)) as PagedDataResponse<VFicheCompletePersonne>
}

/** POST search with pagination */
export async function search(
  data: Partial<VFicheCompletePersonne>,
  pagination?: PaginationRequestParameter,
  sortFields?: SortFieldParameter[],
) {
  const request = new RequestModel(BASE_URL + '/search', pagination, sortFields)
  const response = await restApiWithPage.POST(request.buildRequestUrl(), data)
  return wrapVFicheCompletePersonne(RestResponse.handlePagedResponse<VFicheCompletePersonne>(response)) as PagedDataResponse<VFicheCompletePersonne>
}

/** GET by ID */
export async function getById(id: number | string | undefined) {
  if (!id) throw new Error('getById: id is required')
  const response = await restApi.GET(BASE_URL + '/' + id)
  return wrapVFicheCompletePersonne(RestResponse.handleDataResponse<VFicheCompletePersonne>(response)) as DataResponse<VFicheCompletePersonne>
}

export async function create(data: Partial<VFicheCompletePersonne>){
  const response = await restApi.POST(BASE_URL, data)
  return wrapVFicheCompletePersonne(RestResponse.handleDataResponse<VFicheCompletePersonne>(response)) as DataResponse<VFicheCompletePersonne>
}




export async function exportCsv(
  vFicheCompletePersonnes: Partial<VFicheCompletePersonne[]>,
): Promise<{ success: boolean; error: string | null }> {
     const url = import.meta.env.VITE_APP_API_URL+BASE_URL+'/export/csv'

     try {
     const response = await fetch(url, {
                      method: 'POST',
                      headers: {
                        'Content-Type': 'application/json',
                      },
                      body: JSON.stringify(vFicheCompletePersonnes),
   })
     if (!response.ok) {
     const errorText = await response.text()
     console.error('CSV Export Failed - Server Error:', response.status, errorText)
                      throw new Error(`CSV export failed: . Status: .`)
   }
      const blob = await response.blob()
      const contentDisposition = response.headers.get('content-disposition');
      triggerFileDownload(blob,getFilenameFromHeaders(contentDisposition,"VFicheCompletePersonne.csv"));
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

