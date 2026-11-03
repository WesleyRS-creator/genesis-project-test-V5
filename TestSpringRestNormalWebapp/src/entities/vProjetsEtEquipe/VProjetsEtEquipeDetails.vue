<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="vProjetsEtEquipe">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="vProjetsEtEquipeIdProjet" class="block text-sm font-medium mb-1">Id projet</label>
            <div id="vProjetsEtEquipeIdProjet" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vProjetsEtEquipe.idProjet ? $n(vProjetsEtEquipe.idProjet, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vProjetsEtEquipeNomProjet" class="block text-sm font-medium mb-1">Nom projet</label>
            <div id="vProjetsEtEquipeNomProjet" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vProjetsEtEquipe.nomProjet ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vProjetsEtEquipeDateDebut" class="block text-sm font-medium mb-1">Date debut</label>
            <div id="vProjetsEtEquipeDateDebut" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vProjetsEtEquipe.dateDebut ? $d(vProjetsEtEquipe.dateDebut, 'long') : '--/--/--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vProjetsEtEquipeBudgetEstime" class="block text-sm font-medium mb-1">Budget estime</label>
            <div id="vProjetsEtEquipeBudgetEstime" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vProjetsEtEquipe.budgetEstime ? $n(vProjetsEtEquipe.budgetEstime, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vProjetsEtEquipeDureeJours" class="block text-sm font-medium mb-1">Duree jours</label>
            <div id="vProjetsEtEquipeDureeJours" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vProjetsEtEquipe.dureeJours ? $n(vProjetsEtEquipe.dureeJours, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vProjetsEtEquipeNombreParticipants" class="block text-sm font-medium mb-1">Nombre participants</label>
            <div id="vProjetsEtEquipeNombreParticipants" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vProjetsEtEquipe.nombreParticipants ? $n(vProjetsEtEquipe.nombreParticipants, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vProjetsEtEquipeBudgetParParticipant" class="block text-sm font-medium mb-1">Budget par participant</label>
            <div id="vProjetsEtEquipeBudgetParParticipant" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vProjetsEtEquipe.budgetParParticipant ? $n(vProjetsEtEquipe.budgetParParticipant, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vProjetsEtEquipeListeEquipe" class="block text-sm font-medium mb-1">Liste equipe</label>
            <div id="vProjetsEtEquipeListeEquipe" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vProjetsEtEquipe.listeEquipe ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vProjetsEtEquipeRolesDistribues" class="block text-sm font-medium mb-1">Roles distribues</label>
            <div id="vProjetsEtEquipeRolesDistribues" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vProjetsEtEquipe.rolesDistribues ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vProjetsEtEquipeDerniereInscription" class="block text-sm font-medium mb-1">Derniere inscription</label>
            <div id="vProjetsEtEquipeDerniereInscription" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vProjetsEtEquipe.derniereInscription ?? '' }}</span>
              
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { VProjetsEtEquipe } from '@/models/VProjetsEtEquipeModel'


defineProps({
  vProjetsEtEquipe: {
    type: Object as PropType<VProjetsEtEquipe>,
    required: true,
  },
})

</script>
<style scoped></style>
