<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <form @submit.prevent="handleSubmit" class="grid grid-cols-1 w-7/12 gap-4 mx-auto">
      <!-- <form @submit.prevent="handleSubmit" class=""> -->
        <div class="grid grid-cols-1 gap-4">
          <!-- Date naissance -->
          <GenesisInput
            label="Date naissance"
            :violation="violations ? violations['dateNaissance'] : undefined"
            placeholder="Enter Date naissance"
            type="date"
            v-model="formModel.dateNaissance"
            :value="formModel.dateNaissance"
          />
          
          <!-- Numero ss -->
          <GenesisInput
            label="Numero ss"
            :violation="violations ? violations['numeroSs'] : undefined"
            placeholder="Enter Numero ss"
            type="number"
            v-model="formModel.numeroSs"
            :value="formModel.numeroSs"
          />
          
          <!-- Nom -->
          <GenesisInput
            label="Nom"
            :violation="violations ? violations['nom'] : undefined"
            placeholder="Enter Nom"
            type="text"
            v-model="formModel.nom"
            :value="formModel.nom"
          />
          
          <!-- Prenom -->
          <GenesisInput
            label="Prenom"
            :violation="violations ? violations['prenom'] : undefined"
            placeholder="Enter Prenom"
            type="text"
            v-model="formModel.prenom"
            :value="formModel.prenom"
          />
          
          <!-- Biographie -->
          <GenesisInput
            label="Biographie"
            :violation="violations ? violations['biographie'] : undefined"
            placeholder="Enter Biographie"
            type="text"
            v-model="formModel.biographie"
            :value="formModel.biographie"
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
import { Personne, PersonneFormDTO } from '@/models/PersonneModel.ts'
import GenesisButton from '@/components/button/GenesisButton.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'

const props = defineProps<{
  personne?: Personne
  violations?: Record<string, string> | null
  submitLabel?: string
}>()

const emit = defineEmits<{
  (e: 'submit', payload: Partial<PersonneFormDTO>): void
  (e: 'cancel', payload: Partial<PersonneFormDTO>): void
}>()




const formModel = ref<Partial<PersonneFormDTO>>({ ...PersonneFormDTO.parse(props.personne) })


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
