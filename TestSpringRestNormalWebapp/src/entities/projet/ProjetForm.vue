<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <form @submit.prevent="handleSubmit" class="grid grid-cols-1 w-7/12 gap-4 mx-auto">
      <!-- <form @submit.prevent="handleSubmit" class=""> -->
        <div class="grid grid-cols-1 gap-4">
          <!-- Budget estime -->
          <GenesisInput
            label="Budget estime"
            :violation="violations ? violations['budgetEstime'] : undefined"
            placeholder="Enter Budget estime"
            type="number"
            v-model="formModel.budgetEstime"
            :value="formModel.budgetEstime"
          />
          
          <!-- Date debut -->
          <GenesisInput
            label="Date debut"
            :violation="violations ? violations['dateDebut'] : undefined"
            placeholder="Enter Date debut"
            type="date"
            v-model="formModel.dateDebut"
            :value="formModel.dateDebut"
          />
          
          <!-- Nom projet -->
          <GenesisInput
            label="Nom projet"
            :violation="violations ? violations['nomProjet'] : undefined"
            placeholder="Enter Nom projet"
            type="text"
            v-model="formModel.nomProjet"
            :value="formModel.nomProjet"
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
import { Projet, ProjetFormDTO } from '@/models/ProjetModel.ts'
import GenesisButton from '@/components/button/GenesisButton.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'

const props = defineProps<{
  projet?: Projet
  violations?: Record<string, string> | null
  submitLabel?: string
}>()

const emit = defineEmits<{
  (e: 'submit', payload: Partial<ProjetFormDTO>): void
  (e: 'cancel', payload: Partial<ProjetFormDTO>): void
}>()




const formModel = ref<Partial<ProjetFormDTO>>({ ...ProjetFormDTO.parse(props.projet) })


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
