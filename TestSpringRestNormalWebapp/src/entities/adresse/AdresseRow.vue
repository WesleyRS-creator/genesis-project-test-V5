<template>
  <tr class="hover:bg-base-200">
    <template v-for="field in Adresse.getAllSearchFieldsMetadata()" :key="field.key">
      <td
        v-show="field.showInTable && visibleFields.includes(field.key)"
        class="px-3 py-2"
        :class="{
          'sticky left-0 bg-base-200 font-semibold': field.identifier,
        }"
      >
        <template v-if="field.type === 'date'">
          {{ formatDate(adresseData[field.key]) }}
        </template>

        <template v-else-if="field.type === 'number'">
          {{ adresseData[field.key] != null ? $n(adresseData[field.key], 'decimal') : '--' }}
        </template>

        <template v-else-if="field.type === 'select'">
          {{ adresseData[field.key] != null ? adresseData[field.key].getKeyValue() : '--' }}
        </template>

        <template v-else-if="field.type === 'file'">
          <img v-if="adresseData[field.key]"
            :src="getUrl(adresseData[field.key])"
            :alt="field.key" class="max-h-10 max-w-10"/>
        </template>

        <template v-else>
          {{ adresseData[field.key] ?? '--' }}
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
import { Adresse } from '@/models/AdresseModel'
import EntityRowActions from '@/components/table/EntityRowActions.vue'
import { useDateFormat } from '@/composables/useDateFormat'
import { getUrl } from '@/composables/useImage'

const props = defineProps({
  adresse: {
    type: Object as PropType<Adresse>,
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

const adresseData = computed(() => {
  return props.adresse as Record<string, any>
})

const emit = defineEmits<{
  (e: 'request-delete', adresse: Adresse): void
  (e: 'request-view', adresse: Adresse): void
  (e: 'request-update', adresse: Adresse): void
}>()

const deleteRow = () => emit('request-delete', props.adresse)
const viewRow = () => emit('request-view', props.adresse)
const updateRow = () => emit('request-update', props.adresse)
const { formatDate } = useDateFormat()

</script>
<style scoped></style>
