<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="personne">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="personneDateNaissance" class="block text-sm font-medium mb-1">Date naissance</label>
            <div id="personneDateNaissance" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ personne.dateNaissance ? $d(personne.dateNaissance, 'long') : '--/--/--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="personneId" class="block text-sm font-medium mb-1">Id</label>
            <div id="personneId" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ personne.id ? $n(personne.id, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="personneNumeroSs" class="block text-sm font-medium mb-1">Numero ss</label>
            <div id="personneNumeroSs" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ personne.numeroSs ? $n(personne.numeroSs, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="personneNom" class="block text-sm font-medium mb-1">Nom</label>
            <div id="personneNom" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ personne.nom ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="personnePrenom" class="block text-sm font-medium mb-1">Prenom</label>
            <div id="personnePrenom" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ personne.prenom ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="personneBiographie" class="block text-sm font-medium mb-1">Biographie</label>
            <div id="personneBiographie" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ personne.biographie ?? '' }}</span>
              
            </div>
          </div>
        </div>
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton @click="goToUpdateFormView(personne)" class="btn btn-outline btn-neutral">
            <EditIcon class="mr-2" />
            <span>{{ $t('button.update') }}</span>
          </GenesisButton>
          <GenesisButton 
            @click="openDeletePopup(personne)" 
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
    :message="`Êtes-vous sûr de vouloir supprimer Personne personne?.id ?`"
    subMessage="Cette action est irréversible."
    @confirm="confirmDelete"
    @cancel="closePopup"
  />
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { Personne } from '@/models/PersonneModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import { usePopup } from '@/composables/usePopup'
import { usePersonnes } from '@/composables/usePersonnes'
import DeleteConfirmationPopup from '@/components/popup/DeleteConfirmationPopup.vue'
import TrashIcon from '@/components/icons/TrashIcon.vue'
import EditIcon from '@/components/icons/EditIcon.vue'
import { useDateFormat } from '@/composables/useDateFormat'



const props = defineProps({
  personne: {
    type: Object as PropType<Personne>,
    required: true,
  },
})
// Stores
const { closePopup, openPopup, visible: deletePopup } = usePopup(false)
const { deletePersonne, goToListView, goToUpdateFormView } = usePersonnes()

// Methods
const openDeletePopup = (entity: Personne) => {
  if (!entity) return
  openPopup()
}

const confirmDelete = async () => {
  const { success, error } = await deletePersonne(props.personne)
  if (!success) console.error(error)
  closePopup()
  goToListView()
}

const { formatDate } = useDateFormat();



</script>
<style scoped></style>
