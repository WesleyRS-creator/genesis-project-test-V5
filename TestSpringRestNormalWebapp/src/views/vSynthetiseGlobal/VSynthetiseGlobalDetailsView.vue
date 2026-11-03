<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'VSynthetiseGlobal' }) }}
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
    <VSynthetiseGlobalDetails v-if="entity" :vSynthetiseGlobal="entity" />
    
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import VSynthetiseGlobalDetails from '@/entities/vSynthetiseGlobal/VSynthetiseGlobalDetails.vue'
import { useVSynthetiseGlobals } from '@/composables/useVSynthetiseGlobals'
import { VSynthetiseGlobal } from '@/models/VSynthetiseGlobalModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'

const route = useRoute()
const pathId = Number(route.params.id)
const { getVSynthetiseGlobalById, goToListView } = useVSynthetiseGlobals()
const entity = ref<VSynthetiseGlobal | null>(null)

onMounted(async () => {
  const result = await getVSynthetiseGlobalById(pathId)
  entity.value = result.data || null
});

</script>
<style scoped></style>
