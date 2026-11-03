<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Adresse' }) }}
        <span class="text-base-content/50 font-normal"> {{ $t('entity.list.nav') }}</span>
      </h3>
      <div class="flex items-center gap-3">
        <!-- Composant réutilisable pour le basculement de vue -->
        <LayoutSwitcher v-model="layoutMode" />

        <GenesisButton title="Create new adresse" @click="goToCreateFormView" class="btn-secondary">
          <PlusIcon />
          {{ $t('button.addEntity', { entity: 'Adresse' }) }}
        </GenesisButton>

      </div>
    </div>

    <!-- Table -->
    <EntityTable
      :entity-model="{}"
      :entity-search-fields="searchFields"
      :searchFn="searchAdresses"
      :getPaginationData="getPaginationData"
      :listComponent="AdresseList"
      :entities="adresses"
      :message="message"
      :visible-fields="visibleListFields"
      :layout-mode="layoutMode"
      @update:visible-fields="handleUpdateVisibleFields"
      @export:csv="onExport"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import type { EntitySearchField } from '@/models/EntityModel'
import { Adresse } from '@/models/AdresseModel'
import AdresseList from '@/entities/adresse/AdresseList.vue'
import EntityTable from '@/components/table/EntityTable.vue'
import LayoutSwitcher from '@/components/button/LayoutSwitcher.vue'
import { useAdresses } from '@/composables/useAdresses'
import GenesisButton from '@/components/button/GenesisButton.vue'
import PlusIcon from '@/components/icons/PlusIcon.vue'

const {
  adresses,
  searchAdresses,
  message,
  getPaginationData,
  goToCreateFormView,
  exportAdressesToCsv,
  visibleListFields,
  loadColumnConfig,
  saveColumnConfig,
  layoutMode
} = useAdresses()

const searchFields = ref<EntitySearchField[]>(Adresse.getAllSearchFieldsMetadata())

const handleUpdateVisibleFields = async (newFields: string[]) => {
  const result = await saveColumnConfig(newFields)
  if (!result.success) {
    console.error('Échec de la sauvegarde de la configuration des colonnes:', result.error)
  }
}

const onExport = async () => {
  await exportAdressesToCsv(adresses.value)
}

onMounted(() => {
  loadColumnConfig()
})

</script>
<style scoped></style>
