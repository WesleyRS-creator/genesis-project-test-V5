<template>
  <tr class="hover:bg-base-200">
    <template v-for="field in Passeport.getAllSearchFieldsMetadata()" :key="field.key">
      <td
        v-show="field.showInTable && visibleFields.includes(field.key)"
        class="px-3 py-2"
        :class="{
          'sticky left-0 bg-base-200 font-semibold': field.identifier,
        }"
      >
        <template v-if="field.type === 'date'">
          {{ formatDate(passeportData[field.key]) }}
        </template>

        <template v-else-if="field.type === 'number'">
          {{ passeportData[field.key] != null ? $n(passeportData[field.key], 'decimal') : '--' }}
        </template>

        <template v-else-if="field.type === 'select'">
          {{ passeportData[field.key] != null ? passeportData[field.key].getKeyValue() : '--' }}
        </template>

        <template v-else-if="field.type === 'file'">
          <img v-if="passeportData[field.key]"
            :src="getUrl(passeportData[field.key])"
            :alt="field.key" class="max-h-10 max-w-10"/>
        </template>

        <template v-else>
          {{ passeportData[field.key] ?? '--' }}
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
import { Passeport } from '@/models/PasseportModel'
import EntityRowActions from '@/components/table/EntityRowActions.vue'
import { useDateFormat } from '@/composables/useDateFormat'
import { getUrl } from '@/composables/useImage'

const props = defineProps({
  passeport: {
    type: Object as PropType<Passeport>,
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

const passeportData = computed(() => {
  return props.passeport as Record<string, any>
})

const emit = defineEmits<{
  (e: 'request-delete', passeport: Passeport): void
  (e: 'request-view', passeport: Passeport): void
  (e: 'request-update', passeport: Passeport): void
}>()

const deleteRow = () => emit('request-delete', props.passeport)
const viewRow = () => emit('request-view', props.passeport)
const updateRow = () => emit('request-update', props.passeport)
const { formatDate } = useDateFormat()

</script>
<style scoped></style>
