<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <form @submit.prevent="handleSubmit" class="grid grid-cols-1 w-7/12 gap-4 mx-auto">
      <!-- <form @submit.prevent="handleSubmit" class=""> -->
        <div class="grid grid-cols-1 gap-4">
          <!-- Idpersonne personne FK -->
          <GenesisSelectSearchCriteria
            v-if="idpersonnePersonneSearchField?.multicriteriaSelect"
            label="Idpersonne personne"
            key="participerIdpersonnePersonne"
            :search-function="idpersonnePersonneSearchField?.multicriteriaSelect.searchFunction"
            :filters="idpersonnePersonneSearchField?.multicriteriaSelect.filters"
            :default-value="idpersonnePersonneDefaultValue"
            :violation="violations ? violations['idpersonnePersonne'] : undefined"
            @option-selected="(selectedValue) => (formModel.idpersonnePersonne = String(selectedValue))"
          />
          
          <!-- Idprojet projet FK -->
          <GenesisSelectSearchCriteria
            v-if="idprojetProjetSearchField?.multicriteriaSelect"
            label="Idprojet projet"
            key="participerIdprojetProjet"
            :search-function="idprojetProjetSearchField?.multicriteriaSelect.searchFunction"
            :filters="idprojetProjetSearchField?.multicriteriaSelect.filters"
            :default-value="idprojetProjetDefaultValue"
            :violation="violations ? violations['idprojetProjet'] : undefined"
            @option-selected="(selectedValue) => (formModel.idprojetProjet = String(selectedValue))"
          />
          
          <!-- Date inscription -->
          <GenesisInput
            label="Date inscription"
            :violation="violations ? violations['dateInscription'] : undefined"
            placeholder="Enter Date inscription"
            type="datetime-local"
            v-model="formModel.dateInscription"
            :value="formModel.dateInscription"
          />
          
          <!-- Role dans projet -->
          <GenesisInput
            label="Role dans projet"
            :violation="violations ? violations['roleDansProjet'] : undefined"
            placeholder="Enter Role dans projet"
            type="text"
            v-model="formModel.roleDansProjet"
            :value="formModel.roleDansProjet"
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
import { Participer, ParticiperFormDTO } from '@/models/ParticiperModel.ts'
import GenesisButton from '@/components/button/GenesisButton.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'

const props = defineProps<{
  participer?: Participer
  violations?: Record<string, string> | null
  submitLabel?: string
}>()

const emit = defineEmits<{
  (e: 'submit', payload: Partial<ParticiperFormDTO>): void
  (e: 'cancel', payload: Partial<ParticiperFormDTO>): void
}>()




const formModel = ref<Partial<ParticiperFormDTO>>({ ...ParticiperFormDTO.parse(props.participer) })
const idpersonnePersonneSearchField = Participer.getSearchFieldByKey('idpersonnePersonne')
const idpersonnePersonneDefaultValue = props.participer
? (props.participer?.idpersonnePersonne?.getKeyValue?.() ?? undefined)
: undefined
const idprojetProjetSearchField = Participer.getSearchFieldByKey('idprojetProjet')
const idprojetProjetDefaultValue = props.participer
? (props.participer?.idprojetProjet?.getKeyValue?.() ?? undefined)
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
