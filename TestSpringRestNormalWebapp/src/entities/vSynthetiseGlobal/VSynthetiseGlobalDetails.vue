<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="vSynthetiseGlobal">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="vSynthetiseGlobalIdPersonne" class="block text-sm font-medium mb-1">Id personne</label>
            <div id="vSynthetiseGlobalIdPersonne" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vSynthetiseGlobal.idPersonne ? $n(vSynthetiseGlobal.idPersonne, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vSynthetiseGlobalNomComplet" class="block text-sm font-medium mb-1">Nom complet</label>
            <div id="vSynthetiseGlobalNomComplet" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vSynthetiseGlobal.nomComplet ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vSynthetiseGlobalTrancheAge" class="block text-sm font-medium mb-1">Tranche age</label>
            <div id="vSynthetiseGlobalTrancheAge" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vSynthetiseGlobal.trancheAge ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vSynthetiseGlobalTotalParticipations" class="block text-sm font-medium mb-1">Total participations</label>
            <div id="vSynthetiseGlobalTotalParticipations" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vSynthetiseGlobal.totalParticipations ? $n(vSynthetiseGlobal.totalParticipations, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vSynthetiseGlobalRolePrincipal" class="block text-sm font-medium mb-1">Role principal</label>
            <div id="vSynthetiseGlobalRolePrincipal" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vSynthetiseGlobal.rolePrincipal ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vSynthetiseGlobalBudgetMoyenProjets" class="block text-sm font-medium mb-1">Budget moyen projets</label>
            <div id="vSynthetiseGlobalBudgetMoyenProjets" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vSynthetiseGlobal.budgetMoyenProjets ? $n(vSynthetiseGlobal.budgetMoyenProjets, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vSynthetiseGlobalBudgetGlobalMoyen" class="block text-sm font-medium mb-1">Budget global moyen</label>
            <div id="vSynthetiseGlobalBudgetGlobalMoyen" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vSynthetiseGlobal.budgetGlobalMoyen ? $n(vSynthetiseGlobal.budgetGlobalMoyen, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vSynthetiseGlobalPositionBudget" class="block text-sm font-medium mb-1">Position budget</label>
            <div id="vSynthetiseGlobalPositionBudget" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vSynthetiseGlobal.positionBudget ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vSynthetiseGlobalNombreVilles" class="block text-sm font-medium mb-1">Nombre villes</label>
            <div id="vSynthetiseGlobalNombreVilles" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vSynthetiseGlobal.nombreVilles ? $n(vSynthetiseGlobal.nombreVilles, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vSynthetiseGlobalLatitudeMoyenne" class="block text-sm font-medium mb-1">Latitude moyenne</label>
            <div id="vSynthetiseGlobalLatitudeMoyenne" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vSynthetiseGlobal.latitudeMoyenne ? $n(vSynthetiseGlobal.latitudeMoyenne, 'decimal') : '--' }}</span>
              
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { VSynthetiseGlobal } from '@/models/VSynthetiseGlobalModel'


defineProps({
  vSynthetiseGlobal: {
    type: Object as PropType<VSynthetiseGlobal>,
    required: true,
  },
})

</script>
<style scoped></style>
