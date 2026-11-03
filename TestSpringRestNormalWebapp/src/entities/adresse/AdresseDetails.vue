<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="adresse">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="adresseId" class="block text-sm font-medium mb-1">Id</label>
            <div id="adresseId" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ adresse.id ? $n(adresse.id, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="adresseIdpersonnePersonne" class="block text-sm font-medium mb-1">Personne</label>
            <div id="adresseIdpersonnePersonne" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ adresse.idpersonnePersonne?.getKeyValue() ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="adresseLatitude" class="block text-sm font-medium mb-1">Latitude</label>
            <div id="adresseLatitude" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ adresse.latitude ? $n(adresse.latitude, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="adresseCodePostal" class="block text-sm font-medium mb-1">Code postal</label>
            <div id="adresseCodePostal" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ adresse.codePostal ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="adresseVille" class="block text-sm font-medium mb-1">Ville</label>
            <div id="adresseVille" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ adresse.ville ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="adresseRue" class="block text-sm font-medium mb-1">Rue</label>
            <div id="adresseRue" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ adresse.rue ?? '' }}</span>
              
            </div>
          </div>
        </div>
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton @click="goToUpdateFormView(adresse)" class="btn btn-outline btn-neutral">
            <EditIcon class="mr-2" />
            <span>{{ $t('button.update') }}</span>
          </GenesisButton>
          <GenesisButton 
            @click="openDeletePopup(adresse)" 
            class="btn-outline btn-error hover:text-white"
          >
            <TrashIcon class="mr-2" />
            <span>{{ $t('button.delete') }}</span>
          </GenesisButton>
        </div>
      </div>
    </div>
  </div>
  <DeleteConfirmationPopup
    :visible="deletePopup"
    :message="`Êtes-vous sûr de vouloir supprimer Adresse adresse?.id ?`"
    subMessage="Cette action est irréversible."
    @confirm="confirmDelete"
    @cancel="closePopup"
  />
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { Adresse } from '@/models/AdresseModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import { usePopup } from '@/composables/usePopup'
import { useAdresses } from '@/composables/useAdresses'
import DeleteConfirmationPopup from '@/components/popup/DeleteConfirmationPopup.vue'
import TrashIcon from '@/components/icons/TrashIcon.vue'
import EditIcon from '@/components/icons/EditIcon.vue'
import { useDateFormat } from '@/composables/useDateFormat'



const props = defineProps({
  adresse: {
    type: Object as PropType<Adresse>,
    required: true,
  },
})
// Stores
const { closePopup, openPopup, visible: deletePopup } = usePopup(false)
const { deleteAdresse, goToListView, goToUpdateFormView } = useAdresses()

// Methods
const openDeletePopup = (entity: Adresse) => {
  if (!entity) return
  openPopup()
}

const confirmDelete = async () => {
  const { success, error } = await deleteAdresse(props.adresse)
  if (!success) console.error(error)
  closePopup()
  goToListView()
}

const { formatDate } = useDateFormat();



</script>
<style scoped></style>
