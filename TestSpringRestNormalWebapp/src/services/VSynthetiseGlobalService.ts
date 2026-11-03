import api from '@/services/api'
import {getFilenameFromHeaders, triggerFileDownload} from "@/utils/download.ts";
import RestResponse from '@/models/api/RestResponseModel'
import { type IPageResponse } from '@/models/api/PageResponseModel'
import {
  PaginationRequestParameter,
  RequestModel,
  SortFieldParameter,
} from '@/models/api/RequestModel'
import { VSynthetiseGlobal } from '@/models/VSynthetiseGlobalModel'
import type { DataResponse, PagedDataResponse } from '@/models/api/DataResponseModel'

const restApiWithPage = api<IPageResponse<VSynthetiseGlobal>>()
const restApi = api<VSynthetiseGlobal>()
const BASE_URL = '/vsynthetiseglobals'

/** Helper to wrap response data in VSynthetiseGlobal instances */
function wrapVSynthetiseGlobal(response: DataResponse<VSynthetiseGlobal> | PagedDataResponse<VSynthetiseGlobal>) {
  if (Array.isArray(response.data)) {
    response.data = response.data.map((r) => new VSynthetiseGlobal(r))
  } else if (response.data && typeof response.data === 'object') {
    response.data = new VSynthetiseGlobal(response.data)
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
  return wrapVSynthetiseGlobal(RestResponse.handlePagedResponse<VSynthetiseGlobal>(response)) as PagedDataResponse<VSynthetiseGlobal>
}

/** POST search with pagination */
export async function search(
  data: Partial<VSynthetiseGlobal>,
  pagination?: PaginationRequestParameter,
  sortFields?: SortFieldParameter[],
) {
  const request = new RequestModel(BASE_URL + '/search', pagination, sortFields)
  const response = await restApiWithPage.POST(request.buildRequestUrl(), data)
  return wrapVSynthetiseGlobal(RestResponse.handlePagedResponse<VSynthetiseGlobal>(response)) as PagedDataResponse<VSynthetiseGlobal>
}

/** GET by ID */
export async function getById(id: number | string | undefined) {
  if (!id) throw new Error('getById: id is required')
  const response = await restApi.GET(BASE_URL + '/' + id)
  return wrapVSynthetiseGlobal(RestResponse.handleDataResponse<VSynthetiseGlobal>(response)) as DataResponse<VSynthetiseGlobal>
}

export async function create(data: Partial<VSynthetiseGlobal>){
  const response = await restApi.POST(BASE_URL, data)
  return wrapVSynthetiseGlobal(RestResponse.handleDataResponse<VSynthetiseGlobal>(response)) as DataResponse<VSynthetiseGlobal>
}




export async function exportCsv(
  vSynthetiseGlobals: Partial<VSynthetiseGlobal[]>,
): Promise<{ success: boolean; error: string | null }> {
     const url = import.meta.env.VITE_APP_API_URL+BASE_URL+'/export/csv'

     try {
     const response = await fetch(url, {
                      method: 'POST',
                      headers: {
                        'Content-Type': 'application/json',
                      },
                      body: JSON.stringify(vSynthetiseGlobals),
   })
     if (!response.ok) {
     const errorText = await response.text()
     console.error('CSV Export Failed - Server Error:', response.status, errorText)
                      throw new Error(`CSV export failed: . Status: .`)
   }
      const blob = await response.blob()
      const contentDisposition = response.headers.get('content-disposition');
      triggerFileDownload(blob,getFilenameFromHeaders(contentDisposition,"VSynthetiseGlobal.csv"));
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

