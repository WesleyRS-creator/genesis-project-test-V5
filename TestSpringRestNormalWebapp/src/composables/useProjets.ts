import { ref, watch } from 'vue'
import { Projet, ProjetFormDTO } from '@/models/ProjetModel'
import * as projetService from '@/services/ProjetService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['budgetEstime', 'dateDebut', 'id', 'nomProjet']

/**
 * Composable to manage Projet entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useProjets() {
  /** List of all projets */
  const projets = ref<Projet[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_projet') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_projet', newValue)
  })

  /**
   * Handles API response by updating the projets and pagination
   * @param data Array of Projet returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: Projet[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      projets.value = projets.value.concat(data)
    } else {
      projets.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('projet', 'list')
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
      const response = await columnConfigService.updateVisibleFields('projet', 'list', fields)
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
  /** Navigate to projet detail view */
  const viewProjet = (projet: Projet) => {
    router.push({ name: "projetdetailsview", params: { id: projet.getKeyValue() } })
  }

  /** Navigate to projet list view */
  const goToListView = () => {
    router.push({ path: "/projets" })
  }

  /** Navigate to projet create form */
  const goToCreateFormView = () => {
    router.push({ path: "/projets/create" })
  }

  /** Navigate to projet update form */
  const goToUpdateFormView = (projet: Projet) => {
    router.push({ name: "projetupdateview", params: { id: projet.getKeyValue() } })
  }

  /**
   * Delete a projet by id
   * @param projet Projet object to delete
   */
  const deleteProjet = async (projet: Projet) => {
    const result = await projetService.remove(projet.getKeyValue())
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  
  /**
   * Create a new projet
   * @param projetFormDTO Projet object to create
   */
  const createProjet = async (projetFormDTO: Partial<ProjetFormDTO>) => {
    const dto = new ProjetFormDTO(projetFormDTO)
    const projet: Partial<Projet> = await dto.toEntity()
    const { data, error, errors } = await projetService.create(projet)
    if (error) {
      message.value = error
    }
    return { data, errors }
  }
  
  
  /**
   * Update an existing projet
   * @param projetFormDTO Projet object to update
   */
  const updateProjet = async (id: number | string,projetFormDTO: Partial<ProjetFormDTO>) => {
    const dto = new ProjetFormDTO(projetFormDTO)
    const projet: Partial<Projet> = await dto.toEntity()
    const { data, error } = await projetService.update(id, projet)
    if (error) {
      message.value = error
    }
    return data
  }

  // API Requests
  /** Load all projets from the API */
  const loadProjets = async (
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
    } = await projetService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && projets.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await projetService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single projet by id
   * @param id Projet ID
   */
  const getProjetById = async (id: number | string) => {
    const result = await projetService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search projets with filters, pagination, and sorting
   * @param filters Partial Projet object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchProjets = async (
    unpagined: boolean = false,
    filters: Partial<Projet>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await projetService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await projetService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportProjetsToCsv = async (data: Partial<Projet[]>) => {
    console.log('Exporting Projet to CSV', data)
    await projetService.exportCsv(data)
  }
  
  /** Getter for pagination data */
  const getPaginationData = () => paginationData.value

  return {
    // state
    projets,
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
    exportProjetsToCsv,
    loadProjets,
    searchProjets,
    getProjetById,
    viewProjet,
    goToListView,
    goToCreateFormView,
    goToUpdateFormView,
    deleteProjet,
    createProjet,
    updateProjet,
  }
}
