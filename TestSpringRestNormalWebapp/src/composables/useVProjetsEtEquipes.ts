import { ref, watch } from 'vue'
import { VProjetsEtEquipe,  } from '@/models/VProjetsEtEquipeModel'
import * as vProjetsEtEquipeService from '@/services/VProjetsEtEquipeService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['idProjet', 'nomProjet', 'dateDebut', 'budgetEstime', 'dureeJours', 'nombreParticipants', 'budgetParParticipant', 'listeEquipe', 'rolesDistribues', 'derniereInscription']

/**
 * Composable to manage VProjetsEtEquipe entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useVProjetsEtEquipes() {
  /** List of all vProjetsEtEquipes */
  const vProjetsEtEquipes = ref<VProjetsEtEquipe[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_vProjetsEtEquipe') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_vProjetsEtEquipe', newValue)
  })

  /**
   * Handles API response by updating the vProjetsEtEquipes and pagination
   * @param data Array of VProjetsEtEquipe returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: VProjetsEtEquipe[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      vProjetsEtEquipes.value = vProjetsEtEquipes.value.concat(data)
    } else {
      vProjetsEtEquipes.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('v_projets_et_equipes', 'list')
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
      const response = await columnConfigService.updateVisibleFields('v_projets_et_equipes', 'list', fields)
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
  /** Navigate to vProjetsEtEquipe detail view */
  const viewVProjetsEtEquipe = (vProjetsEtEquipe: VProjetsEtEquipe) => {
    router.push({ name: "vProjetsEtEquipedetailsview", params: { id: vProjetsEtEquipe.getKeyValue() } })
  }

  /** Navigate to vProjetsEtEquipe list view */
  const goToListView = () => {
    router.push({ path: "/vProjetsEtEquipes" })
  }

  
  

  // API Requests
  /** Load all vProjetsEtEquipes from the API */
  const loadVProjetsEtEquipes = async (
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
    } = await vProjetsEtEquipeService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && vProjetsEtEquipes.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await vProjetsEtEquipeService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single vProjetsEtEquipe by id
   * @param id VProjetsEtEquipe ID
   */
  const getVProjetsEtEquipeById = async (id: number | string) => {
    const result = await vProjetsEtEquipeService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search vProjetsEtEquipes with filters, pagination, and sorting
   * @param filters Partial VProjetsEtEquipe object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchVProjetsEtEquipes = async (
    unpagined: boolean = false,
    filters: Partial<VProjetsEtEquipe>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await vProjetsEtEquipeService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await vProjetsEtEquipeService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportVProjetsEtEquipesToCsv = async (data: Partial<VProjetsEtEquipe[]>) => {
    console.log('Exporting VProjetsEtEquipe to CSV', data)
    await vProjetsEtEquipeService.exportCsv(data)
  }
  
  /** Getter for pagination data */
  const getPaginationData = () => paginationData.value

  return {
    // state
    vProjetsEtEquipes,
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
    exportVProjetsEtEquipesToCsv,
    loadVProjetsEtEquipes,
    searchVProjetsEtEquipes,
    getVProjetsEtEquipeById,
    viewVProjetsEtEquipe,
    goToListView,
  }
}
