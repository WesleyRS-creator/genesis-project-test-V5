import { ref, watch } from 'vue'
import { Adresse, AdresseFormDTO } from '@/models/AdresseModel'
import * as adresseService from '@/services/AdresseService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['id', 'idpersonnePersonne', 'latitude', 'codePostal', 'ville', 'rue']

/**
 * Composable to manage Adresse entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useAdresses() {
  /** List of all adresses */
  const adresses = ref<Adresse[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_adresse') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_adresse', newValue)
  })

  /**
   * Handles API response by updating the adresses and pagination
   * @param data Array of Adresse returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: Adresse[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      adresses.value = adresses.value.concat(data)
    } else {
      adresses.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('adresse', 'list')
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
      const response = await columnConfigService.updateVisibleFields('adresse', 'list', fields)
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
  /** Navigate to adresse detail view */
  const viewAdresse = (adresse: Adresse) => {
    router.push({ name: "adressedetailsview", params: { id: adresse.getKeyValue() } })
  }

  /** Navigate to adresse list view */
  const goToListView = () => {
    router.push({ path: "/adresses" })
  }

  /** Navigate to adresse create form */
  const goToCreateFormView = () => {
    router.push({ path: "/adresses/create" })
  }

  /** Navigate to adresse update form */
  const goToUpdateFormView = (adresse: Adresse) => {
    router.push({ name: "adresseupdateview", params: { id: adresse.getKeyValue() } })
  }

  /**
   * Delete a adresse by id
   * @param adresse Adresse object to delete
   */
  const deleteAdresse = async (adresse: Adresse) => {
    const result = await adresseService.remove(adresse.getKeyValue())
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  
  /**
   * Create a new adresse
   * @param adresseFormDTO Adresse object to create
   */
  const createAdresse = async (adresseFormDTO: Partial<AdresseFormDTO>) => {
    const dto = new AdresseFormDTO(adresseFormDTO)
    const adresse: Partial<Adresse> = await dto.toEntity()
    const { data, error, errors } = await adresseService.create(adresse)
    if (error) {
      message.value = error
    }
    return { data, errors }
  }
  
  
  /**
   * Update an existing adresse
   * @param adresseFormDTO Adresse object to update
   */
  const updateAdresse = async (id: number | string,adresseFormDTO: Partial<AdresseFormDTO>) => {
    const dto = new AdresseFormDTO(adresseFormDTO)
    const adresse: Partial<Adresse> = await dto.toEntity()
    const { data, error } = await adresseService.update(id, adresse)
    if (error) {
      message.value = error
    }
    return data
  }

  // API Requests
  /** Load all adresses from the API */
  const loadAdresses = async (
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
    } = await adresseService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && adresses.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await adresseService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single adresse by id
   * @param id Adresse ID
   */
  const getAdresseById = async (id: number | string) => {
    const result = await adresseService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search adresses with filters, pagination, and sorting
   * @param filters Partial Adresse object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchAdresses = async (
    unpagined: boolean = false,
    filters: Partial<Adresse>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await adresseService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await adresseService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportAdressesToCsv = async (data: Partial<Adresse[]>) => {
    console.log('Exporting Adresse to CSV', data)
    await adresseService.exportCsv(data)
  }
  
  const getAllAdressesByPersonneId = async (
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
    } = await adresseService.getAllByPersonneId(personneId, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext() && adresses.value.length > 0) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await adresseService.getAllByPersonneId(
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
    adresses,
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
    exportAdressesToCsv,
    loadAdresses,
    searchAdresses,
    getAdresseById,
    viewAdresse,
    goToListView,
    goToCreateFormView,
    goToUpdateFormView,
    deleteAdresse,
    createAdresse,
    updateAdresse,
    getAllAdressesByPersonneId,
    
  }
}
