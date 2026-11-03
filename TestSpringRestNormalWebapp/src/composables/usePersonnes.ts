import { ref, watch } from 'vue'
import { Personne, PersonneFormDTO } from '@/models/PersonneModel'
import * as personneService from '@/services/PersonneService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['dateNaissance', 'id', 'numeroSs', 'nom', 'prenom', 'biographie']

/**
 * Composable to manage Personne entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function usePersonnes() {
  /** List of all personnes */
  const personnes = ref<Personne[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_personne') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_personne', newValue)
  })

  /**
   * Handles API response by updating the personnes and pagination
   * @param data Array of Personne returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: Personne[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      personnes.value = personnes.value.concat(data)
    } else {
      personnes.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('personne', 'list')
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
      const response = await columnConfigService.updateVisibleFields('personne', 'list', fields)
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
  /** Navigate to personne detail view */
  const viewPersonne = (personne: Personne) => {
    router.push({ name: "personnedetailsview", params: { id: personne.getKeyValue() } })
  }

  /** Navigate to personne list view */
  const goToListView = () => {
    router.push({ path: "/personnes" })
  }

  /** Navigate to personne create form */
  const goToCreateFormView = () => {
    router.push({ path: "/personnes/create" })
  }

  /** Navigate to personne update form */
  const goToUpdateFormView = (personne: Personne) => {
    router.push({ name: "personneupdateview", params: { id: personne.getKeyValue() } })
  }

  /**
   * Delete a personne by id
   * @param personne Personne object to delete
   */
  const deletePersonne = async (personne: Personne) => {
    const result = await personneService.remove(personne.getKeyValue())
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  
  /**
   * Create a new personne
   * @param personneFormDTO Personne object to create
   */
  const createPersonne = async (personneFormDTO: Partial<PersonneFormDTO>) => {
    const dto = new PersonneFormDTO(personneFormDTO)
    const personne: Partial<Personne> = await dto.toEntity()
    const { data, error, errors } = await personneService.create(personne)
    if (error) {
      message.value = error
    }
    return { data, errors }
  }
  
  
  /**
   * Update an existing personne
   * @param personneFormDTO Personne object to update
   */
  const updatePersonne = async (id: number | string,personneFormDTO: Partial<PersonneFormDTO>) => {
    const dto = new PersonneFormDTO(personneFormDTO)
    const personne: Partial<Personne> = await dto.toEntity()
    const { data, error } = await personneService.update(id, personne)
    if (error) {
      message.value = error
    }
    return data
  }

  // API Requests
  /** Load all personnes from the API */
  const loadPersonnes = async (
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
    } = await personneService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && personnes.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await personneService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single personne by id
   * @param id Personne ID
   */
  const getPersonneById = async (id: number | string) => {
    const result = await personneService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search personnes with filters, pagination, and sorting
   * @param filters Partial Personne object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchPersonnes = async (
    unpagined: boolean = false,
    filters: Partial<Personne>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await personneService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await personneService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportPersonnesToCsv = async (data: Partial<Personne[]>) => {
    console.log('Exporting Personne to CSV', data)
    await personneService.exportCsv(data)
  }
  
  /** Getter for pagination data */
  const getPaginationData = () => paginationData.value

  return {
    // state
    personnes,
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
    exportPersonnesToCsv,
    loadPersonnes,
    searchPersonnes,
    getPersonneById,
    viewPersonne,
    goToListView,
    goToCreateFormView,
    goToUpdateFormView,
    deletePersonne,
    createPersonne,
    updatePersonne,
  }
}
