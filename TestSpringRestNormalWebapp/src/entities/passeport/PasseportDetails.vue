<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="passeport">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="passeportDateExpiration" class="block text-sm font-medium mb-1">Date expiration</label>
            <div id="passeportDateExpiration" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ passeport.dateExpiration ? $d(passeport.dateExpiration, 'long') : '--/--/--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="passeportId" class="block text-sm font-medium mb-1">Id</label>
            <div id="passeportId" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ passeport.id ? $n(passeport.id, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="passeportIdpersonnePersonne" class="block text-sm font-medium mb-1">Personne</label>
            <div id="passeportIdpersonnePersonne" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ passeport.idpersonnePersonne?.getKeyValue() ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="passeportNumero" class="block text-sm font-medium mb-1">Numero</label>
            <div id="passeportNumero" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ passeport.numero ?? '' }}</span>
              
            </div>
          </div>
        </div>
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton @click="goToUpdateFormView(passeport)" class="btn btn-outline btn-neutral">
            <EditIcon class="mr-2" />
            <span>{{ $t('button.update') }}</span>
          </GenesisButton>
          <GenesisButton 
            @click="openDeletePopup(passeport)" 
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
    :message="`Êtes-vous sûr de vouloir supprimer Passeport passeport?.id ?`"
    subMessage="Cette action est irréversible."
    @confirm="confirmDelete"
    @cancel="closePopup"
  />
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { Passeport } from '@/models/PasseportModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import { usePopup } from '@/composables/usePopup'
import { usePasseports } from '@/composables/usePasseports'
import DeleteConfirmationPopup from '@/components/popup/DeleteConfirmationPopup.vue'
import TrashIcon from '@/components/icons/TrashIcon.vue'
import EditIcon from '@/components/icons/EditIcon.vue'
import { useDateFormat } from '@/composables/useDateFormat'



const props = defineProps({
  passeport: {
    type: Object as PropType<Passeport>,
    required: true,
  },
})
// Stores
const { closePopup, openPopup, visible: deletePopup } = usePopup(false)
const { deletePasseport, goToListView, goToUpdateFormView } = usePasseports()

// Methods
const openDeletePopup = (entity: Passeport) => {
  if (!entity) return
  openPopup()
}

const confirmDelete = async () => {
  const { success, error } = await deletePasseport(props.passeport)
  if (!success) console.error(error)
  closePopup()
  goToListView()
}

const { formatDate } = useDateFormat();



</script>
<style scoped></style>
