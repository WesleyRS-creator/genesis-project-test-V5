import { ref, watch } from 'vue'
import { VSynthetiseGlobal,  } from '@/models/VSynthetiseGlobalModel'
import * as vSynthetiseGlobalService from '@/services/VSynthetiseGlobalService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['idPersonne', 'nomComplet', 'trancheAge', 'totalParticipations', 'rolePrincipal', 'budgetMoyenProjets', 'budgetGlobalMoyen', 'positionBudget', 'nombreVilles', 'latitudeMoyenne']

/**
 * Composable to manage VSynthetiseGlobal entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useVSynthetiseGlobals() {
  /** List of all vSynthetiseGlobals */
  const vSynthetiseGlobals = ref<VSynthetiseGlobal[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_vSynthetiseGlobal') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_vSynthetiseGlobal', newValue)
  })

  /**
   * Handles API response by updating the vSynthetiseGlobals and pagination
   * @param data Array of VSynthetiseGlobal returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: VSynthetiseGlobal[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      vSynthetiseGlobals.value = vSynthetiseGlobals.value.concat(data)
    } else {
      vSynthetiseGlobals.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('v_synthetise_global', 'list')
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
      const response = await columnConfigService.updateVisibleFields('v_synthetise_global', 'list', fields)
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
  /** Navigate to vSynthetiseGlobal detail view */
  const viewVSynthetiseGlobal = (vSynthetiseGlobal: VSynthetiseGlobal) => {
    router.push({ name: "vSynthetiseGlobaldetailsview", params: { id: vSynthetiseGlobal.getKeyValue() } })
  }

  /** Navigate to vSynthetiseGlobal list view */
  const goToListView = () => {
    router.push({ path: "/vSynthetiseGlobals" })
  }

  
  

  // API Requests
  /** Load all vSynthetiseGlobals from the API */
  const loadVSynthetiseGlobals = async (
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
    } = await vSynthetiseGlobalService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && vSynthetiseGlobals.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await vSynthetiseGlobalService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single vSynthetiseGlobal by id
   * @param id VSynthetiseGlobal ID
   */
  const getVSynthetiseGlobalById = async (id: number | string) => {
    const result = await vSynthetiseGlobalService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search vSynthetiseGlobals with filters, pagination, and sorting
   * @param filters Partial VSynthetiseGlobal object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchVSynthetiseGlobals = async (
    unpagined: boolean = false,
    filters: Partial<VSynthetiseGlobal>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await vSynthetiseGlobalService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await vSynthetiseGlobalService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportVSynthetiseGlobalsToCsv = async (data: Partial<VSynthetiseGlobal[]>) => {
    console.log('Exporting VSynthetiseGlobal to CSV', data)
    await vSynthetiseGlobalService.exportCsv(data)
  }
  
  /** Getter for pagination data */
  const getPaginationData = () => paginationData.value

  return {
    // state
    vSynthetiseGlobals,
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
    exportVSynthetiseGlobalsToCsv,
    loadVSynthetiseGlobals,
    searchVSynthetiseGlobals,
    getVSynthetiseGlobalById,
    viewVSynthetiseGlobal,
    goToListView,
  }
}
