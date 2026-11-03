<template>
  <div class="w-full">
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Projet' }) }}
        <span class="text-base-content/50 font-normal">{{ $t('entity.create.nav') }}</span>
      </h3>
      <GenesisButton
        :title="$t('button.backToListDescription')"
        @click="goToListView"
        class="btn-secondary"
      >
        <LeftArrowIcon />
        {{ $t('button.backToList') }}
      </GenesisButton>
    </div>

    <projet-form
      :submit-label="$t('entity.create.submitLabel', { entity: 'Projet' })"
      :violations="violations"
      @submit="createHandler"
      @cancel="goToListView"
    />

    <AlertPopup
      :message="message ?? undefined"
      title="Error"
      :visible="alertPopup"
      @close="closePopup"
    />
  </div>
</template>

<script setup lang="ts">
import ProjetForm from '@/entities/projet/ProjetForm.vue'
import AlertPopup from '@/components/popup/AlertPopup.vue'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import { ProjetFormDTO } from '@/models/ProjetModel'
import { useProjets } from '@/composables/useProjets'
import { usePopup } from '@/composables/usePopup'
import { useFreezeScreenStore } from '@/stores/useFreezeScreenStore'
import { ref } from 'vue'

const { createProjet, goToListView, message, viewProjet } = useProjets()
const { openPopup, closePopup, visible: alertPopup } = usePopup()
const freezeStore = useFreezeScreenStore()
const violations = ref<Record<string, string> | null>(null)
const createHandler = async (formDTO: Partial<ProjetFormDTO>) => {
  freezeStore.freeze('Creating a new Projet ...')
  try {
    const { data, errors } = await createProjet(formDTO)
    if (errors) {
      violations.value = Object.fromEntries(
        Object.entries(errors).filter((entry): entry is [string, string] => typeof entry[1] === 'string'),
      )
    }
    if (data) {
      viewProjet(data)
      return
    }
    throw new Error(
      message.value || 'Creation failed without an error message from the API.',
    )
  } catch (error: unknown) {
    console.error('Error creating projet:',error)
    openPopup()
  } finally {
    freezeStore.unfreeze()
  }
}

</script>
<style scoped></style>
