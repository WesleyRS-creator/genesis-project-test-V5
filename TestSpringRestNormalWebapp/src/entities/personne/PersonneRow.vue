<template>
  <tr class="hover:bg-base-200">
    <template v-for="field in Personne.getAllSearchFieldsMetadata()" :key="field.key">
      <td
        v-show="field.showInTable && visibleFields.includes(field.key)"
        class="px-3 py-2"
        :class="{
          'sticky left-0 bg-base-200 font-semibold': field.identifier,
        }"
      >
        <template v-if="field.type === 'date'">
          {{ formatDate(personneData[field.key]) }}
        </template>

        <template v-else-if="field.type === 'number'">
          {{ personneData[field.key] != null ? $n(personneData[field.key], 'decimal') : '--' }}
        </template>

        <template v-else-if="field.type === 'select'">
          {{ personneData[field.key] != null ? personneData[field.key].getKeyValue() : '--' }}
        </template>

        <template v-else-if="field.type === 'file'">
          <img v-if="personneData[field.key]"
            :src="getUrl(personneData[field.key])"
            :alt="field.key" class="max-h-10 max-w-10"/>
        </template>

        <template v-else>
          {{ personneData[field.key] ?? '--' }}
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
import { Personne } from '@/models/PersonneModel'
import EntityRowActions from '@/components/table/EntityRowActions.vue'
import { useDateFormat } from '@/composables/useDateFormat'
import { getUrl } from '@/composables/useImage'

const props = defineProps({
  personne: {
    type: Object as PropType<Personne>,
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

const personneData = computed(() => {
  return props.personne as Record<string, any>
})

const emit = defineEmits<{
  (e: 'request-delete', personne: Personne): void
  (e: 'request-view', personne: Personne): void
  (e: 'request-update', personne: Personne): void
}>()

const deleteRow = () => emit('request-delete', props.personne)
const viewRow = () => emit('request-view', props.personne)
const updateRow = () => emit('request-update', props.personne)
const { formatDate } = useDateFormat()

</script>
<style scoped></style>
