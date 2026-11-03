<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Projet' }) }}
        <span class="text-base-content/50 font-normal"> {{ $t('entity.list.nav') }}</span>
      </h3>
      <div class="flex items-center gap-3">
        <!-- Composant réutilisable pour le basculement de vue -->
        <LayoutSwitcher v-model="layoutMode" />

        <GenesisButton title="Create new projet" @click="goToCreateFormView" class="btn-secondary">
          <PlusIcon />
          {{ $t('button.addEntity', { entity: 'Projet' }) }}
        </GenesisButton>

      </div>
    </div>

    <!-- Table -->
    <EntityTable
      :entity-model="{}"
      :entity-search-fields="searchFields"
      :searchFn="searchProjets"
      :getPaginationData="getPaginationData"
      :listComponent="ProjetList"
      :entities="projets"
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
import { Projet } from '@/models/ProjetModel'
import ProjetList from '@/entities/projet/ProjetList.vue'
import EntityTable from '@/components/table/EntityTable.vue'
import LayoutSwitcher from '@/components/button/LayoutSwitcher.vue'
import { useProjets } from '@/composables/useProjets'
import GenesisButton from '@/components/button/GenesisButton.vue'
import PlusIcon from '@/components/icons/PlusIcon.vue'

const {
  projets,
  searchProjets,
  message,
  getPaginationData,
  goToCreateFormView,
  exportProjetsToCsv,
  visibleListFields,
  loadColumnConfig,
  saveColumnConfig,
  layoutMode
} = useProjets()

const searchFields = ref<EntitySearchField[]>(Projet.getAllSearchFieldsMetadata())

const handleUpdateVisibleFields = async (newFields: string[]) => {
  const result = await saveColumnConfig(newFields)
  if (!result.success) {
    console.error('Échec de la sauvegarde de la configuration des colonnes:', result.error)
  }
}

const onExport = async () => {
  await exportProjetsToCsv(projets.value)
}

onMounted(() => {
  loadColumnConfig()
})

</script>
<style scoped></style>
