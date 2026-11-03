<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="projet">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="projetBudgetEstime" class="block text-sm font-medium mb-1">Budget estime</label>
            <div id="projetBudgetEstime" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ projet.budgetEstime ? $n(projet.budgetEstime, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="projetDateDebut" class="block text-sm font-medium mb-1">Date debut</label>
            <div id="projetDateDebut" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ projet.dateDebut ? $d(projet.dateDebut, 'long') : '--/--/--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="projetId" class="block text-sm font-medium mb-1">Id</label>
            <div id="projetId" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ projet.id ? $n(projet.id, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="projetNomProjet" class="block text-sm font-medium mb-1">Nom projet</label>
            <div id="projetNomProjet" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ projet.nomProjet ?? '' }}</span>
              
            </div>
          </div>
        </div>
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton @click="goToUpdateFormView(projet)" class="btn btn-outline btn-neutral">
            <EditIcon class="mr-2" />
            <span>{{ $t('button.update') }}</span>
          </GenesisButton>
          <GenesisButton 
            @click="openDeletePopup(projet)" 
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
    :message="`Êtes-vous sûr de vouloir supprimer Projet projet?.id ?`"
    subMessage="Cette action est irréversible."
    @confirm="confirmDelete"
    @cancel="closePopup"
  />
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { Projet } from '@/models/ProjetModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import { usePopup } from '@/composables/usePopup'
import { useProjets } from '@/composables/useProjets'
import DeleteConfirmationPopup from '@/components/popup/DeleteConfirmationPopup.vue'
import TrashIcon from '@/components/icons/TrashIcon.vue'
import EditIcon from '@/components/icons/EditIcon.vue'
import { useDateFormat } from '@/composables/useDateFormat'



const props = defineProps({
  projet: {
    type: Object as PropType<Projet>,
    required: true,
  },
})
// Stores
const { closePopup, openPopup, visible: deletePopup } = usePopup(false)
const { deleteProjet, goToListView, goToUpdateFormView } = useProjets()

// Methods
const openDeletePopup = (entity: Projet) => {
  if (!entity) return
  openPopup()
}

const confirmDelete = async () => {
  const { success, error } = await deleteProjet(props.projet)
  if (!success) console.error(error)
  closePopup()
  goToListView()
}

const { formatDate } = useDateFormat();



</script>
<style scoped></style>
