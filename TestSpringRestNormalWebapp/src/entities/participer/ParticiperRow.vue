<template>
  <tr class="hover:bg-base-200">
    <template v-for="field in Participer.getAllSearchFieldsMetadata()" :key="field.key">
      <td
        v-show="field.showInTable && visibleFields.includes(field.key)"
        class="px-3 py-2"
        :class="{
          'sticky left-0 bg-base-200 font-semibold': field.identifier,
        }"
      >
        <template v-if="field.type === 'date'">
          {{ formatDate(participerData[field.key]) }}
        </template>

        <template v-else-if="field.type === 'number'">
          {{ participerData[field.key] != null ? $n(participerData[field.key], 'decimal') : '--' }}
        </template>

        <template v-else-if="field.type === 'select'">
          {{ participerData[field.key] != null ? participerData[field.key].getKeyValue() : '--' }}
        </template>

        <template v-else-if="field.type === 'file'">
          <img v-if="participerData[field.key]"
            :src="getUrl(participerData[field.key])"
            :alt="field.key" class="max-h-10 max-w-10"/>
        </template>

        <template v-else>
          {{ participerData[field.key] ?? '--' }}
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
import { Participer } from '@/models/ParticiperModel'
import EntityRowActions from '@/components/table/EntityRowActions.vue'
import { useDateFormat } from '@/composables/useDateFormat'
import { getUrl } from '@/composables/useImage'

const props = defineProps({
  participer: {
    type: Object as PropType<Participer>,
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

const participerData = computed(() => {
  return props.participer as Record<string, any>
})

const emit = defineEmits<{
  (e: 'request-delete', participer: Participer): void
  (e: 'request-view', participer: Participer): void
  (e: 'request-update', participer: Participer): void
}>()

const deleteRow = () => emit('request-delete', props.participer)
const viewRow = () => emit('request-view', props.participer)
const updateRow = () => emit('request-update', props.participer)
const { formatDate } = useDateFormat()

</script>
<style scoped></style>
