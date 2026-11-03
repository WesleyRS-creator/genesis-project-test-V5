import { ref, watch } from 'vue'
import { Participer, ParticiperFormDTO } from '@/models/ParticiperModel'
import * as participerService from '@/services/ParticiperService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['id', 'idpersonnePersonne', 'idprojetProjet', 'dateInscription', 'roleDansProjet']

/**
 * Composable to manage Participer entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useParticipers() {
  /** List of all participers */
  const participers = ref<Participer[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_participer') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_participer', newValue)
  })

  /**
   * Handles API response by updating the participers and pagination
   * @param data Array of Participer returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: Participer[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      participers.value = participers.value.concat(data)
    } else {
      participers.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('participer', 'list')
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
      const response = await columnConfigService.updateVisibleFields('participer', 'list', fields)
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
  /** Navigate to participer detail view */
  const viewParticiper = (participer: Participer) => {
    router.push({ name: "participerdetailsview", params: { id: participer.getKeyValue() } })
  }

  /** Navigate to participer list view */
  const goToListView = () => {
    router.push({ path: "/participers" })
  }

  /** Navigate to participer create form */
  const goToCreateFormView = () => {
    router.push({ path: "/participers/create" })
  }

  /** Navigate to participer update form */
  const goToUpdateFormView = (participer: Participer) => {
    router.push({ name: "participerupdateview", params: { id: participer.getKeyValue() } })
  }

  /**
   * Delete a participer by id
   * @param participer Participer object to delete
   */
  const deleteParticiper = async (participer: Participer) => {
    const result = await participerService.remove(participer.getKeyValue())
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  
  /**
   * Create a new participer
   * @param participerFormDTO Participer object to create
   */
  const createParticiper = async (participerFormDTO: Partial<ParticiperFormDTO>) => {
    const dto = new ParticiperFormDTO(participerFormDTO)
    const participer: Partial<Participer> = await dto.toEntity()
    const { data, error, errors } = await participerService.create(participer)
    if (error) {
      message.value = error
    }
    return { data, errors }
  }
  
  
  /**
   * Update an existing participer
   * @param participerFormDTO Participer object to update
   */
  const updateParticiper = async (id: number | string,participerFormDTO: Partial<ParticiperFormDTO>) => {
    const dto = new ParticiperFormDTO(participerFormDTO)
    const participer: Partial<Participer> = await dto.toEntity()
    const { data, error } = await participerService.update(id, participer)
    if (error) {
      message.value = error
    }
    return data
  }

  // API Requests
  /** Load all participers from the API */
  const loadParticipers = async (
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
    } = await participerService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && participers.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await participerService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single participer by id
   * @param id Participer ID
   */
  const getParticiperById = async (id: number | string) => {
    const result = await participerService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search participers with filters, pagination, and sorting
   * @param filters Partial Participer object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchParticipers = async (
    unpagined: boolean = false,
    filters: Partial<Participer>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await participerService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await participerService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportParticipersToCsv = async (data: Partial<Participer[]>) => {
    console.log('Exporting Participer to CSV', data)
    await participerService.exportCsv(data)
  }
  
  const getAllParticipersByPersonneId = async (
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
    } = await participerService.getAllByPersonneId(personneId, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext() && participers.value.length > 0) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await participerService.getAllByPersonneId(
          personneId,
          paginationData.value.toParameter(),
          sortFields,
        )
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const getAllParticipersByProjetId = async (
    projetId: number | string | undefined,
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
    } = await participerService.getAllByProjetId(projetId, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext() && participers.value.length > 0) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await participerService.getAllByProjetId(
          projetId,
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
    participers,
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
    exportParticipersToCsv,
    loadParticipers,
    searchParticipers,
    getParticiperById,
    viewParticiper,
    goToListView,
    goToCreateFormView,
    goToUpdateFormView,
    deleteParticiper,
    createParticiper,
    updateParticiper,
    getAllParticipersByPersonneId,
    getAllParticipersByProjetId,
    
  }
}
