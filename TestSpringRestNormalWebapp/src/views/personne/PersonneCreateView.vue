<template>
  <div class="w-full">
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Personne' }) }}
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

    <personne-form
      :submit-label="$t('entity.create.submitLabel', { entity: 'Personne' })"
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
import PersonneForm from '@/entities/personne/PersonneForm.vue'
import AlertPopup from '@/components/popup/AlertPopup.vue'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import { PersonneFormDTO } from '@/models/PersonneModel'
import { usePersonnes } from '@/composables/usePersonnes'
import { usePopup } from '@/composables/usePopup'
import { useFreezeScreenStore } from '@/stores/useFreezeScreenStore'
import { ref } from 'vue'

const { createPersonne, goToListView, message, viewPersonne } = usePersonnes()
const { openPopup, closePopup, visible: alertPopup } = usePopup()
const freezeStore = useFreezeScreenStore()
const violations = ref<Record<string, string> | null>(null)
const createHandler = async (formDTO: Partial<PersonneFormDTO>) => {
  freezeStore.freeze('Creating a new Personne ...')
  try {
    const { data, errors } = await createPersonne(formDTO)
    if (errors) {
      violations.value = Object.fromEntries(
        Object.entries(errors).filter((entry): entry is [string, string] => typeof entry[1] === 'string'),
      )
    }
    if (data) {
      viewPersonne(data)
      return
    }
    throw new Error(
      message.value || 'Creation failed without an error message from the API.',
    )
  } catch (error: unknown) {
    console.error('Error creating personne:',error)
    openPopup()
  } finally {
    freezeStore.unfreeze()
  }
}

</script>
<style scoped></style>
