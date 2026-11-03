<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <form @submit.prevent="handleSubmit" class="grid grid-cols-1 w-7/12 gap-4 mx-auto">
      <!-- <form @submit.prevent="handleSubmit" class=""> -->
        <div class="grid grid-cols-1 gap-4">
          <!-- Date expiration -->
          <GenesisInput
            label="Date expiration"
            :violation="violations ? violations['dateExpiration'] : undefined"
            placeholder="Enter Date expiration"
            type="date"
            v-model="formModel.dateExpiration"
            :value="formModel.dateExpiration"
          />
          
          <!-- Idpersonne personne FK -->
          <GenesisSelectSearchCriteria
            v-if="idpersonnePersonneSearchField?.multicriteriaSelect"
            label="Idpersonne personne"
            key="passeportIdpersonnePersonne"
            :search-function="idpersonnePersonneSearchField?.multicriteriaSelect.searchFunction"
            :filters="idpersonnePersonneSearchField?.multicriteriaSelect.filters"
            :default-value="idpersonnePersonneDefaultValue"
            :violation="violations ? violations['idpersonnePersonne'] : undefined"
            @option-selected="(selectedValue) => (formModel.idpersonnePersonne = String(selectedValue))"
          />
          
          <!-- Numero -->
          <GenesisInput
            label="Numero"
            :violation="violations ? violations['numero'] : undefined"
            placeholder="Enter Numero"
            type="text"
            v-model="formModel.numero"
            :value="formModel.numero"
          />
          
        </div>
        
        <!-- Action buttons -->
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton type="submit" class="btn btn-primary text-primary-content" :label="submitLabel" />
          <GenesisButton @click="cancelForm" class="btn btn-outline btn-error" :label="$t('button.cancel')" />
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue' 
import { Passeport, PasseportFormDTO } from '@/models/PasseportModel.ts'
import GenesisButton from '@/components/button/GenesisButton.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'

const props = defineProps<{
  passeport?: Passeport
  violations?: Record<string, string> | null
  submitLabel?: string
}>()

const emit = defineEmits<{
  (e: 'submit', payload: Partial<PasseportFormDTO>): void
  (e: 'cancel', payload: Partial<PasseportFormDTO>): void
}>()




const formModel = ref<Partial<PasseportFormDTO>>({ ...PasseportFormDTO.parse(props.passeport) })
const idpersonnePersonneSearchField = Passeport.getSearchFieldByKey('idpersonnePersonne')
const idpersonnePersonneDefaultValue = props.passeport
? (props.passeport?.idpersonnePersonne?.getKeyValue?.() ?? undefined)
: undefined


const fileToBase64 = (file: File): Promise<string> => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.onload = () => {
      if (typeof reader.result !== 'string') {
        reject(new Error('Unable to read the selected file'))
        return
      } 
      const commaIndex = reader.result.indexOf(',')
      resolve(
        commaIndex >= 0
        ? reader.result.substring(commaIndex + 1)
        : reader.result,
      )
    }  
    reader.onerror = () => {
      reject(reader.error ?? new Error('Unable to read the selected file'))
    }
    reader.readAsDataURL(file)
  })
}
async function handleSubmit() {
  const data: any = { ...formModel.value }
  Object.keys(data).forEach(key => {
    if (data[key] === ''
            || data[key] === null
            || data[key] === undefined) {
        delete data[key];
    }
  });
  
  emit('submit', data)
}

function cancelForm() {
  emit('cancel', formModel.value)
}

</script>
<style scoped></style>
