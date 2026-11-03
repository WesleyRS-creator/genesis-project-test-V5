<template>
  <tr class="hover:bg-base-200">
    <template v-for="field in VFicheCompletePersonne.getAllSearchFieldsMetadata()" :key="field.key">
      <td
        v-show="field.showInTable && visibleFields.includes(field.key)"
        class="px-3 py-2"
        :class="{
          'sticky left-0 bg-base-200 font-semibold': field.identifier,
        }"
      >
        <template v-if="field.type === 'date'">
          {{ formatDate(vFicheCompletePersonneData[field.key]) }}
        </template>

        <template v-else-if="field.type === 'number'">
          {{ vFicheCompletePersonneData[field.key] != null ? $n(vFicheCompletePersonneData[field.key], 'decimal') : '--' }}
        </template>

        <template v-else-if="field.type === 'select'">
          {{ vFicheCompletePersonneData[field.key] != null ? vFicheCompletePersonneData[field.key].getKeyValue() : '--' }}
        </template>

        <template v-else-if="field.type === 'file'">
          <img v-if="vFicheCompletePersonneData[field.key]"
            :src="getUrl(vFicheCompletePersonneData[field.key])"
            :alt="field.key" class="max-h-10 max-w-10"/>
        </template>

        <template v-else>
          {{ vFicheCompletePersonneData[field.key] ?? '--' }}
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
import { VFicheCompletePersonne } from '@/models/VFicheCompletePersonneModel'
import EntityRowActions from '@/components/table/EntityRowActions.vue'
import { useDateFormat } from '@/composables/useDateFormat'
import { getUrl } from '@/composables/useImage'

const props = defineProps({
  vFicheCompletePersonne: {
    type: Object as PropType<VFicheCompletePersonne>,
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

const vFicheCompletePersonneData = computed(() => {
  return props.vFicheCompletePersonne as Record<string, any>
})

const emit = defineEmits<{
  (e: 'request-delete', vFicheCompletePersonne: VFicheCompletePersonne): void
  (e: 'request-view', vFicheCompletePersonne: VFicheCompletePersonne): void
  (e: 'request-update', vFicheCompletePersonne: VFicheCompletePersonne): void
}>()

const deleteRow = () => emit('request-delete', props.vFicheCompletePersonne)
const viewRow = () => emit('request-view', props.vFicheCompletePersonne)
const updateRow = () => emit('request-update', props.vFicheCompletePersonne)
const { formatDate } = useDateFormat()

</script>
<style scoped></style>
