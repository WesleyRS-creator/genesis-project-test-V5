<template>
  <tr class="hover:bg-base-200">
    <template v-for="field in Projet.getAllSearchFieldsMetadata()" :key="field.key">
      <td
        v-show="field.showInTable && visibleFields.includes(field.key)"
        class="px-3 py-2"
        :class="{
          'sticky left-0 bg-base-200 font-semibold': field.identifier,
        }"
      >
        <template v-if="field.type === 'date'">
          {{ formatDate(projetData[field.key]) }}
        </template>

        <template v-else-if="field.type === 'number'">
          {{ projetData[field.key] != null ? $n(projetData[field.key], 'decimal') : '--' }}
        </template>

        <template v-else-if="field.type === 'select'">
          {{ projetData[field.key] != null ? projetData[field.key].getKeyValue() : '--' }}
        </template>

        <template v-else-if="field.type === 'file'">
          <img v-if="projetData[field.key]"
            :src="getUrl(projetData[field.key])"
            :alt="field.key" class="max-h-10 max-w-10"/>
        </template>

        <template v-else>
          {{ projetData[field.key] ?? '--' }}
        </template>
      </td>
    </template>

    <!-- Actions -->
    <td class="px-3 py-2 bg-base-200 sticky right-0 text-end">
      <div class="rounded-md inline-flex">
        <EntityRowActions
          :view="viewAction"
          :remove="removeAction"
          :edit="editAction"
          @delete-row="deleteRow" 
          @view-row="viewRow" 
          @edit-row="updateRow" />
      </div>
    </td>
  </tr>
</template>

<script setup lang="ts">
import { computed, type PropType } from 'vue'
import { Projet } from '@/models/ProjetModel'
import EntityRowActions from '@/components/table/EntityRowActions.vue'
import { useDateFormat } from '@/composables/useDateFormat'
import { getUrl } from '@/composables/useImage'

const props = defineProps({
  projet: {
    type: Object as PropType<Projet>,
    required: true,
  },
  viewAction: {
    type: Boolean,
    default: true,
  },
  editAction: {
    type: Boolean,
    default: true,
  },
  removeAction: {
    type: Boolean,
    default: true,
  },
  visibleFields: {
    type: Array as PropType<string[]>,
    default: () => []
  }
})

const projetData = computed(() => {
  return props.projet as Record<string, any>
})

const emit = defineEmits<{
  (e: 'request-delete', projet: Projet): void
  (e: 'request-view', projet: Projet): void
  (e: 'request-update', projet: Projet): void
}>()

const deleteRow = () => emit('request-delete', props.projet)
const viewRow = () => emit('request-view', props.projet)
const updateRow = () => emit('request-update', props.projet)
const { formatDate } = useDateFormat()

</script>
<style scoped></style>
