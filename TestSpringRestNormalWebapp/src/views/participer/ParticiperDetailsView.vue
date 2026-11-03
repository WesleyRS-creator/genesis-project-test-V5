<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Participer' }) }}
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
    <ParticiperDetails v-if="entity" :participer="entity" />
    
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import ParticiperDetails from '@/entities/participer/ParticiperDetails.vue'
import { useParticipers } from '@/composables/useParticipers'
import { Participer } from '@/models/ParticiperModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'

const route = useRoute()
const pathId = Number(route.params.id)
const { getParticiperById, goToListView } = useParticipers()
const entity = ref<Participer | null>(null)

onMounted(async () => {
  const result = await getParticiperById(pathId)
  entity.value = result.data || null
});

</script>
<style scoped></style>
