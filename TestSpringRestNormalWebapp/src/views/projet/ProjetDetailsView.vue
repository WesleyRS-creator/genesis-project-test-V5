<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Projet' }) }}
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
    <ProjetDetails v-if="entity" :projet="entity" />
    
    <div class="tabs tabs-border">
      <input type="radio" name="details_tab" class="tab" :aria-label="$t('entities.participer.nav.title')" checked />
      <div class="tab-content card bg-base-100 border border-base-300">
        <div class="card-body">
          <div class="mt-4">
            <EntityTable
              :entity-model="participerEntity"
              :entity-search-fields="participerSearchFields"
              :default-active-filters="['idprojetProjet']"
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
    
    
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import ProjetDetails from '@/entities/projet/ProjetDetails.vue'
import { useProjets } from '@/composables/useProjets'
import { Projet } from '@/models/ProjetModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import EntityTable from '@/components/table/EntityTable.vue'
import type { EntitySearchField } from '@/models/EntityModel'
import type { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { useParticipers } from '@/composables/useParticipers'
import { Participer } from '@/models/ParticiperModel'
import ParticiperList from '@/entities/participer/ParticiperList.vue'


const route = useRoute()
const pathId = Number(route.params.id)
const { getProjetById, goToListView } = useProjets()
const entity = ref<Projet | null>(null)

const {
  participers,
  getAllParticipersByProjetId,
  message: participerMessage,
  getPaginationData: participerGetPaginationData,
} = useParticipers()
const getParticipersData = async (
  unpagined: boolean,
  filters: Record<string, unknown>,
  pagination: PaginationRequestParameter,
  sortFields: SortFieldParameter[],
) => {
  return await getAllParticipersByProjetId(pathId, unpagined, pagination, sortFields)
}
const participerSearchFields = ref<EntitySearchField[]>(Participer.getAllSearchFieldsMetadata())
const participerEntity = ref<Record<string, unknown>>({ idprojetProjet: pathId })

onMounted(async () => {
  const result = await getProjetById(pathId)
  entity.value = result.data || null
});

</script>
<style scoped></style>
