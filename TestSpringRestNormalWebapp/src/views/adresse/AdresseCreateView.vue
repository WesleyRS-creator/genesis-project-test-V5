<template>
  <div class="w-full">
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Adresse' }) }}
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

    <adresse-form
      :submit-label="$t('entity.create.submitLabel', { entity: 'Adresse' })"
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
import AdresseForm from '@/entities/adresse/AdresseForm.vue'
import AlertPopup from '@/components/popup/AlertPopup.vue'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import { AdresseFormDTO } from '@/models/AdresseModel'
import { useAdresses } from '@/composables/useAdresses'
import { usePopup } from '@/composables/usePopup'
import { useFreezeScreenStore } from '@/stores/useFreezeScreenStore'
import { ref } from 'vue'

const { createAdresse, goToListView, message, viewAdresse } = useAdresses()
const { openPopup, closePopup, visible: alertPopup } = usePopup()
const freezeStore = useFreezeScreenStore()
const violations = ref<Record<string, string> | null>(null)
const createHandler = async (formDTO: Partial<AdresseFormDTO>) => {
  freezeStore.freeze('Creating a new Adresse ...')
  try {
    const { data, errors } = await createAdresse(formDTO)
    if (errors) {
      violations.value = Object.fromEntries(
        Object.entries(errors).filter((entry): entry is [string, string] => typeof entry[1] === 'string'),
      )
    }
    if (data) {
      viewAdresse(data)
      return
    }
    throw new Error(
      message.value || 'Creation failed without an error message from the API.',
    )
  } catch (error: unknown) {
    console.error('Error creating adresse:',error)
    openPopup()
  } finally {
    freezeStore.unfreeze()
  }
}

</script>
<style scoped></style>
