<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Passeport' }) }}
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
    <PasseportDetails v-if="entity" :passeport="entity" />
    
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import PasseportDetails from '@/entities/passeport/PasseportDetails.vue'
import { usePasseports } from '@/composables/usePasseports'
import { Passeport } from '@/models/PasseportModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'

const route = useRoute()
const pathId = Number(route.params.id)
const { getPasseportById, goToListView } = usePasseports()
const entity = ref<Passeport | null>(null)

onMounted(async () => {
  const result = await getPasseportById(pathId)
  entity.value = result.data || null
});

</script>
<style scoped></style>
