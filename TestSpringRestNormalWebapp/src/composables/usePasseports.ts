import { ref, watch } from 'vue'
import { Passeport, PasseportFormDTO } from '@/models/PasseportModel'
import * as passeportService from '@/services/PasseportService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['dateExpiration', 'id', 'idpersonnePersonne', 'numero']

/**
 * Composable to manage Passeport entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function usePasseports() {
  /** List of all passeports */
  const passeports = ref<Passeport[]>([])

  /** Loading state management */
  const { loading, startLoading, stopLoading } = useLoading()

  /** Pagination information returned by API */
  const paginationData = ref(new PaginationData())

  /** Message for errors or notifications */
  const message = ref<string | null>(null)

  /** Vue router instance for navigation */
  const router = useRouter()

  const visibleListFields = ref<string[]>([...DEFAULT_LIST_VIEW_FIELDS])

  // On utilise une clé spécifique à l'entité pour plus de flexibilité future
  const savedLayoutMode = localStorage.getItem('layoutMode_passeport') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_passeport', newValue)
  })

  /**
   * Handles API response by updating the passeports and pagination
   * @param data Array of Passeport returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: Passeport[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      passeports.value = passeports.value.concat(data)
    } else {
      passeports.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('passeport', 'list')
      if (response.returnCode === 1 && response.data && response.data.length > 0) {
        visibleListFields.value = response.data
      }
    } catch (error) {
      console.warn('Impossible de charger la config des colonnes, utilisation des valeurs par défaut.', error)
    }
  }

  /** Sauvegarde la nouvelle configuration vers le serveur */
  const saveColumnConfig = async (fields: string[]) => {
    try {
      const response = await columnConfigService.updateVisibleFields('passeport', 'list', fields)
      if (response.returnCode === 1) {
        visibleListFields.value = fields
        return { success: true, error: null }
      } else {
        return { success: false, error: response.message || 'Erreur lors de la sauvegarde' }
      }
    } catch (error) {
      return { success: false, error: 'Erreur réseau lors de la sauvegarde' }
    }
  }

  // Navigation functions
  /** Navigate to passeport detail view */
  const viewPasseport = (passeport: Passeport) => {
    router.push({ name: "passeportdetailsview", params: { id: passeport.getKeyValue() } })
  }

  /** Navigate to passeport list view */
  const goToListView = () => {
    router.push({ path: "/passeports" })
  }

  /** Navigate to passeport create form */
  const goToCreateFormView = () => {
    router.push({ path: "/passeports/create" })
  }

  /** Navigate to passeport update form */
  const goToUpdateFormView = (passeport: Passeport) => {
    router.push({ name: "passeportupdateview", params: { id: passeport.getKeyValue() } })
  }

  /**
   * Delete a passeport by id
   * @param passeport Passeport object to delete
   */
  const deletePasseport = async (passeport: Passeport) => {
    const result = await passeportService.remove(passeport.getKeyValue())
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  
  /**
   * Create a new passeport
   * @param passeportFormDTO Passeport object to create
   */
  const createPasseport = async (passeportFormDTO: Partial<PasseportFormDTO>) => {
    const dto = new PasseportFormDTO(passeportFormDTO)
    const passeport: Partial<Passeport> = await dto.toEntity()
    const { data, error, errors } = await passeportService.create(passeport)
    if (error) {
      message.value = error
    }
    return { data, errors }
  }
  
  
  /**
   * Update an existing passeport
   * @param passeportFormDTO Passeport object to update
   */
  const updatePasseport = async (id: number | string,passeportFormDTO: Partial<PasseportFormDTO>) => {
    const dto = new PasseportFormDTO(passeportFormDTO)
    const passeport: Partial<Passeport> = await dto.toEntity()
    const { data, error } = await passeportService.update(id, passeport)
    if (error) {
      message.value = error
    }
    return data
  }

  // API Requests
  /** Load all passeports from the API */
  const loadPasseports = async (
    unpagined: boolean = false,
    pagination?: PaginationRequestParameter,
    sortFields?: SortFieldParameter[],
  ): Promise<void> => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await passeportService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && passeports.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await passeportService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single passeport by id
   * @param id Passeport ID
   */
  const getPasseportById = async (id: number | string) => {
    const result = await passeportService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search passeports with filters, pagination, and sorting
   * @param filters Partial Passeport object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchPasseports = async (
    unpagined: boolean = false,
    filters: Partial<Passeport>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await passeportService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await passeportService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportPasseportsToCsv = async (data: Partial<Passeport[]>) => {
    console.log('Exporting Passeport to CSV', data)
    await passeportService.exportCsv(data)
  }
  
  const getAllPasseportsByPersonneId = async (
    personneId: number | string | undefined,
    unpagined: boolean = false,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await passeportService.getAllByPersonneId(personneId, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext() && passeports.value.length > 0) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await passeportService.getAllByPersonneId(
          personneId,
          paginationData.value.toParameter(),
          sortFields,
        )
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  
  /** Getter for pagination data */
  const getPaginationData = () => paginationData.value

  return {
    // state
    passeports,
    loading,
    message,
    paginationData,
    layoutMode,

    // State & function of column configuration
    visibleListFields,
    loadColumnConfig,
    saveColumnConfig,

    // Actions
    getPaginationData,
    exportPasseportsToCsv,
    loadPasseports,
    searchPasseports,
    getPasseportById,
    viewPasseport,
    goToListView,
    goToCreateFormView,
    goToUpdateFormView,
    deletePasseport,
    createPasseport,
    updatePasseport,
    getAllPasseportsByPersonneId,
    
  }
}
