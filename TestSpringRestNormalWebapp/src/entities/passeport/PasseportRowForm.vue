<script setup lang="ts">
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import { ref, onMounted } from 'vue'
import { Passeport, PasseportFormDTO } from '@/models/PasseportModel'
import TrashIcon from '@/components/icons/TrashIcon.vue'

const props = defineProps<{
  internalId: number
  passeport: Passeport
  violations?: Record<string, string> | null
}>()

const emit = defineEmits<{
  (e: 'request:remove'): void
  (e: 'udpate:model-value', value: PasseportFormDTO): void
}>()

const formModel = ref<PasseportFormDTO>(PasseportFormDTO.parse(props.passeport))


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
        :violation="violations ? violations['dateExpiration'] : undefined"
        placeholder="Enter Date expiration"
        type="date"
        v-model="formModel.dateExpiration"
        :value="formModel.dateExpiration"
        @update:model-value="(newVal) => updateModel()"
      />
    </td>
    
    <td>
      <GenesisInput
        :violation="violations ? violations['numero'] : undefined"
        placeholder="Enter Numero"
        type="text"
        v-model="formModel.numero"
        :value="formModel.numero"
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
