<script setup lang="ts">
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import { ref, onMounted } from 'vue'
import { Participer, ParticiperFormDTO } from '@/models/ParticiperModel'
import TrashIcon from '@/components/icons/TrashIcon.vue'

const props = defineProps<{
  internalId: number
  participer: Participer
  violations?: Record<string, string> | null
}>()

const emit = defineEmits<{
  (e: 'request:remove'): void
  (e: 'udpate:model-value', value: ParticiperFormDTO): void
}>()

const formModel = ref<ParticiperFormDTO>(ParticiperFormDTO.parse(props.participer))


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
        :violation="violations ? violations['dateInscription'] : undefined"
        placeholder="Enter Date inscription"
        type="datetime-local"
        v-model="formModel.dateInscription"
        :value="formModel.dateInscription"
        @update:model-value="(newVal) => updateModel()"
      />
    </td>
    
    <td>
      <GenesisInput
        :violation="violations ? violations['roleDansProjet'] : undefined"
        placeholder="Enter Role dans projet"
        type="text"
        v-model="formModel.roleDansProjet"
        :value="formModel.roleDansProjet"
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
