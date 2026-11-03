import { ref, watch } from 'vue'
import { VFicheCompletePersonne,  } from '@/models/VFicheCompletePersonneModel'
import * as vFicheCompletePersonneService from '@/services/VFicheCompletePersonneService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['idPersonne', 'nom', 'prenom', 'dateNaissance', 'age', 'biographie', 'numeroSs', 'numeroPasseport', 'dateExpiration', 'statutPasseport', 'nombreAdresses', 'villePrincipale', 'nombreProjets']

/**
 * Composable to manage VFicheCompletePersonne entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useVFicheCompletePersonnes() {
  /** List of all vFicheCompletePersonnes */
  const vFicheCompletePersonnes = ref<VFicheCompletePersonne[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_vFicheCompletePersonne') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_vFicheCompletePersonne', newValue)
  })

  /**
   * Handles API response by updating the vFicheCompletePersonnes and pagination
   * @param data Array of VFicheCompletePersonne returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: VFicheCompletePersonne[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      vFicheCompletePersonnes.value = vFicheCompletePersonnes.value.concat(data)
    } else {
      vFicheCompletePersonnes.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('v_fiche_complete_personne', 'list')
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
      const response = await columnConfigService.updateVisibleFields('v_fiche_complete_personne', 'list', fields)
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
  /** Navigate to vFicheCompletePersonne detail view */
  const viewVFicheCompletePersonne = (vFicheCompletePersonne: VFicheCompletePersonne) => {
    router.push({ name: "vFicheCompletePersonnedetailsview", params: { id: vFicheCompletePersonne.getKeyValue() } })
  }

  /** Navigate to vFicheCompletePersonne list view */
  const goToListView = () => {
    router.push({ path: "/vFicheCompletePersonnes" })
  }

  
  

  // API Requests
  /** Load all vFicheCompletePersonnes from the API */
  const loadVFicheCompletePersonnes = async (
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
    } = await vFicheCompletePersonneService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && vFicheCompletePersonnes.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await vFicheCompletePersonneService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single vFicheCompletePersonne by id
   * @param id VFicheCompletePersonne ID
   */
  const getVFicheCompletePersonneById = async (id: number | string) => {
    const result = await vFicheCompletePersonneService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search vFicheCompletePersonnes with filters, pagination, and sorting
   * @param filters Partial VFicheCompletePersonne object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchVFicheCompletePersonnes = async (
    unpagined: boolean = false,
    filters: Partial<VFicheCompletePersonne>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await vFicheCompletePersonneService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await vFicheCompletePersonneService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportVFicheCompletePersonnesToCsv = async (data: Partial<VFicheCompletePersonne[]>) => {
    console.log('Exporting VFicheCompletePersonne to CSV', data)
    await vFicheCompletePersonneService.exportCsv(data)
  }
  
  /** Getter for pagination data */
  const getPaginationData = () => paginationData.value

  return {
    // state
    vFicheCompletePersonnes,
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
    exportVFicheCompletePersonnesToCsv,
    loadVFicheCompletePersonnes,
    searchVFicheCompletePersonnes,
    getVFicheCompletePersonneById,
    viewVFicheCompletePersonne,
    goToListView,
  }
}
