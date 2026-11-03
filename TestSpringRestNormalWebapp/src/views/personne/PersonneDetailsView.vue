<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Personne' }) }}
        <span class="text-base-content/50 font-normal">{{ $t('entity.details.nav') }}</span>
      </h3>

      <!-- Back button -->
      <GenesisButton
        :title="$t('button.backToListDescription')"
        @click="goToListView"
        class="btn-secondary"
      >
        <LeftArrowIcon />
        {{ $t('button.backToList') }}
      </GenesisButton>
    </div>
    <!-- Entity details -->
    <PersonneDetails v-if="entity" :personne="entity" />
    
    <div class="tabs tabs-border">
      <input type="radio" name="details_tab" class="tab" :aria-label="$t('entities.adresse.nav.title')" checked />
      <div class="tab-content card bg-base-100 border border-base-300">
        <div class="card-body">
          <div class="mt-4">
            <EntityTable
              :entity-model="adresseEntity"
              :entity-search-fields="adresseSearchFields"
              :default-active-filters="['idpersonnePersonne']"
              :searchFn="getAdressesData"
              :getPaginationData="adresseGetPaginationData"
              :listComponent="AdresseList"
              :entities="adresses"
              :message="adresseMessage"
              :show-filters="false"
              :edit-action="false"
              :remove-action="false"
            />
          </div>
        </div>
      </div>            
    </div>
    <div class="tabs tabs-border">
      <input type="radio" name="details_tab" class="tab" :aria-label="$t('entities.participer.nav.title')" checked />
      <div class="tab-content card bg-base-100 border border-base-300">
        <div class="card-body">
          <div class="mt-4">
            <EntityTable
              :entity-model="participerEntity"
              :entity-search-fields="participerSearchFields"
              :default-active-filters="['idpersonnePersonne']"
              :searchFn="getParticipersData"
              :getPaginationData="participerGetPaginationData"
              :listComponent="ParticiperList"
              :entities="participers"
              :message="participerMessage"
              :show-filters="false"
              :edit-action="false"
              :remove-action="false"
            />
          </div>
        </div>
      </div>            
    </div>
    <div class="tabs tabs-border">
      <input type="radio" name="details_tab" class="tab" :aria-label="$t('entities.passeport.nav.title')" checked />
      <div class="tab-content card bg-base-100 border border-base-300">
        <div class="card-body">
          <div class="mt-4">
            <EntityTable
              :entity-model="passeportEntity"
              :entity-search-fields="passeportSearchFields"
              :default-active-filters="['idpersonnePersonne']"
              :searchFn="getPasseportsData"
              :getPaginationData="passeportGetPaginationData"
              :listComponent="PasseportList"
              :entities="passeports"
              :message="passeportMessage"
              :show-filters="false"
              :edit-action="false"
              :remove-action="false"
            />
          </div>
        </div>
      </div>            
    </div>
    
    
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import PersonneDetails from '@/entities/personne/PersonneDetails.vue'
import { usePersonnes } from '@/composables/usePersonnes'
import { Personne } from '@/models/PersonneModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import EntityTable from '@/components/table/EntityTable.vue'
import type { EntitySearchField } from '@/models/EntityModel'
import type { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { useAdresses } from '@/composables/useAdresses'
import { Adresse } from '@/models/AdresseModel'
import AdresseList from '@/entities/adresse/AdresseList.vue'
import { useParticipers } from '@/composables/useParticipers'
import { Participer } from '@/models/ParticiperModel'
import ParticiperList from '@/entities/participer/ParticiperList.vue'
import { usePasseports } from '@/composables/usePasseports'
import { Passeport } from '@/models/PasseportModel'
import PasseportList from '@/entities/passeport/PasseportList.vue'


const route = useRoute()
const pathId = Number(route.params.id)
const { getPersonneById, goToListView } = usePersonnes()
const entity = ref<Personne | null>(null)

const {
  adresses,
  getAllAdressesByPersonneId,
  message: adresseMessage,
  getPaginationData: adresseGetPaginationData,
} = useAdresses()
const getAdressesData = async (
  unpagined: boolean,
  filters: Record<string, unknown>,
  pagination: PaginationRequestParameter,
  sortFields: SortFieldParameter[],
) => {
  return await getAllAdressesByPersonneId(pathId, unpagined, pagination, sortFields)
}
const adresseSearchFields = ref<EntitySearchField[]>(Adresse.getAllSearchFieldsMetadata())
const adresseEntity = ref<Record<string, unknown>>({ idpersonnePersonne: pathId })
const {
  participers,
  getAllParticipersByPersonneId,
  message: participerMessage,
  getPaginationData: participerGetPaginationData,
} = useParticipers()
const getParticipersData = async (
  unpagined: boolean,
  filters: Record<string, unknown>,
  pagination: PaginationRequestParameter,
  sortFields: SortFieldParameter[],
) => {
  return await getAllParticipersByPersonneId(pathId, unpagined, pagination, sortFields)
}
const participerSearchFields = ref<EntitySearchField[]>(Participer.getAllSearchFieldsMetadata())
const participerEntity = ref<Record<string, unknown>>({ idpersonnePersonne: pathId })
const {
  passeports,
  getAllPasseportsByPersonneId,
  message: passeportMessage,
  getPaginationData: passeportGetPaginationData,
} = usePasseports()
const getPasseportsData = async (
  unpagined: boolean,
  filters: Record<string, unknown>,
  pagination: PaginationRequestParameter,
  sortFields: SortFieldParameter[],
) => {
  return await getAllPasseportsByPersonneId(pathId, unpagined, pagination, sortFields)
}
const passeportSearchFields = ref<EntitySearchField[]>(Passeport.getAllSearchFieldsMetadata())
const passeportEntity = ref<Record<string, unknown>>({ idpersonnePersonne: pathId })

onMounted(async () => {
  const result = await getPersonneById(pathId)
  entity.value = result.data || null
});

</script>
<style scoped></style>
