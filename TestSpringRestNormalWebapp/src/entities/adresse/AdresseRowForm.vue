<script setup lang="ts">
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import { ref, onMounted } from 'vue'
import { Adresse, AdresseFormDTO } from '@/models/AdresseModel'
import TrashIcon from '@/components/icons/TrashIcon.vue'

const props = defineProps<{
  internalId: number
  adresse: Adresse
  violations?: Record<string, string> | null
}>()

const emit = defineEmits<{
  (e: 'request:remove'): void
  (e: 'udpate:model-value', value: AdresseFormDTO): void
}>()

const formModel = ref<AdresseFormDTO>(AdresseFormDTO.parse(props.adresse))


function removeRow() {
  emit('request:remove')
}

function updateModel() {
  emit('udpate:model-value', formModel.value)
}

onMounted(() => {
  updateModel()
})
</script>

<template>
  <tr>
    <td>
      {{ internalId }}
    </td>
    <td>
      <GenesisInput
        :violation="violations ? violations['latitude'] : undefined"
        placeholder="Enter Latitude"
        type="number"
        v-model="formModel.latitude"
        :value="formModel.latitude"
        @update:model-value="(newVal) => updateModel()"
      />
    </td>
    
    <td>
      <GenesisInput
        :violation="violations ? violations['codePostal'] : undefined"
        placeholder="Enter Code postal"
        type="text"
        v-model="formModel.codePostal"
        :value="formModel.codePostal"
        @update:model-value="(newVal) => updateModel()"
      />
    </td>
    
    <td>
      <GenesisInput
        :violation="violations ? violations['ville'] : undefined"
        placeholder="Enter Ville"
        type="text"
        v-model="formModel.ville"
        :value="formModel.ville"
        @update:model-value="(newVal) => updateModel()"
      />
    </td>
    
    <td>
      <GenesisInput
        :violation="violations ? violations['rue'] : undefined"
        placeholder="Enter Rue"
        type="text"
        v-model="formModel.rue"
        :value="formModel.rue"
        @update:model-value="(newVal) => updateModel()"
      />
    </td>
    
    
    <td class="text-center">
      <button type="button" title="delete row" @click="removeRow" class="btn btn-error btn-outline">
        <TrashIcon />
      </button>
    </td>
  </tr>
</template>

<style scoped></style>
