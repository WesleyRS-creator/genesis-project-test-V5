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
            key="adresseIdpersonnePersonne"
            :search-function="idpersonnePersonneSearchField?.multicriteriaSelect.searchFunction"
            :filters="idpersonnePersonneSearchField?.multicriteriaSelect.filters"
            :default-value="idpersonnePersonneDefaultValue"
            :violation="violations ? violations['idpersonnePersonne'] : undefined"
            @option-selected="(selectedValue) => (formModel.idpersonnePersonne = String(selectedValue))"
          />
          
          <!-- Latitude -->
          <GenesisInput
            label="Latitude"
            :violation="violations ? violations['latitude'] : undefined"
            placeholder="Enter Latitude"
            type="number"
            v-model="formModel.latitude"
            :value="formModel.latitude"
          />
          
          <!-- Code postal -->
          <GenesisInput
            label="Code postal"
            :violation="violations ? violations['codePostal'] : undefined"
            placeholder="Enter Code postal"
            type="text"
            v-model="formModel.codePostal"
            :value="formModel.codePostal"
          />
          
          <!-- Ville -->
          <GenesisInput
            label="Ville"
            :violation="violations ? violations['ville'] : undefined"
            placeholder="Enter Ville"
            type="text"
            v-model="formModel.ville"
            :value="formModel.ville"
          />
          
          <!-- Rue -->
          <GenesisInput
            label="Rue"
            :violation="violations ? violations['rue'] : undefined"
            placeholder="Enter Rue"
            type="text"
            v-model="formModel.rue"
            :value="formModel.rue"
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
import { Adresse, AdresseFormDTO } from '@/models/AdresseModel.ts'
import GenesisButton from '@/components/button/GenesisButton.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'

const props = defineProps<{
  adresse?: Adresse
  violations?: Record<string, string> | null
  submitLabel?: string
}>()

const emit = defineEmits<{
  (e: 'submit', payload: Partial<AdresseFormDTO>): void
  (e: 'cancel', payload: Partial<AdresseFormDTO>): void
}>()




const formModel = ref<Partial<AdresseFormDTO>>({ ...AdresseFormDTO.parse(props.adresse) })
const idpersonnePersonneSearchField = Adresse.getSearchFieldByKey('idpersonnePersonne')
const idpersonnePersonneDefaultValue = props.adresse
? (props.adresse?.idpersonnePersonne?.getKeyValue?.() ?? undefined)
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
