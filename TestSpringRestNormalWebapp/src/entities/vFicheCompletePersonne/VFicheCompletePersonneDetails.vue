<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="vFicheCompletePersonne">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="vFicheCompletePersonneIdPersonne" class="block text-sm font-medium mb-1">Id personne</label>
            <div id="vFicheCompletePersonneIdPersonne" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vFicheCompletePersonne.idPersonne ? $n(vFicheCompletePersonne.idPersonne, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vFicheCompletePersonneNom" class="block text-sm font-medium mb-1">Nom</label>
            <div id="vFicheCompletePersonneNom" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vFicheCompletePersonne.nom ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vFicheCompletePersonnePrenom" class="block text-sm font-medium mb-1">Prenom</label>
            <div id="vFicheCompletePersonnePrenom" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vFicheCompletePersonne.prenom ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vFicheCompletePersonneDateNaissance" class="block text-sm font-medium mb-1">Date naissance</label>
            <div id="vFicheCompletePersonneDateNaissance" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vFicheCompletePersonne.dateNaissance ? $d(vFicheCompletePersonne.dateNaissance, 'long') : '--/--/--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vFicheCompletePersonneAge" class="block text-sm font-medium mb-1">Age</label>
            <div id="vFicheCompletePersonneAge" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vFicheCompletePersonne.age ? $n(vFicheCompletePersonne.age, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vFicheCompletePersonneBiographie" class="block text-sm font-medium mb-1">Biographie</label>
            <div id="vFicheCompletePersonneBiographie" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vFicheCompletePersonne.biographie ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vFicheCompletePersonneNumeroSs" class="block text-sm font-medium mb-1">Numero ss</label>
            <div id="vFicheCompletePersonneNumeroSs" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vFicheCompletePersonne.numeroSs ? $n(vFicheCompletePersonne.numeroSs, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vFicheCompletePersonneNumeroPasseport" class="block text-sm font-medium mb-1">Numero passeport</label>
            <div id="vFicheCompletePersonneNumeroPasseport" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vFicheCompletePersonne.numeroPasseport ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vFicheCompletePersonneDateExpiration" class="block text-sm font-medium mb-1">Date expiration</label>
            <div id="vFicheCompletePersonneDateExpiration" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vFicheCompletePersonne.dateExpiration ? $d(vFicheCompletePersonne.dateExpiration, 'long') : '--/--/--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vFicheCompletePersonneStatutPasseport" class="block text-sm font-medium mb-1">Statut passeport</label>
            <div id="vFicheCompletePersonneStatutPasseport" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vFicheCompletePersonne.statutPasseport ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vFicheCompletePersonneNombreAdresses" class="block text-sm font-medium mb-1">Nombre adresses</label>
            <div id="vFicheCompletePersonneNombreAdresses" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vFicheCompletePersonne.nombreAdresses ? $n(vFicheCompletePersonne.nombreAdresses, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vFicheCompletePersonneVillePrincipale" class="block text-sm font-medium mb-1">Ville principale</label>
            <div id="vFicheCompletePersonneVillePrincipale" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vFicheCompletePersonne.villePrincipale ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vFicheCompletePersonneNombreProjets" class="block text-sm font-medium mb-1">Nombre projets</label>
            <div id="vFicheCompletePersonneNombreProjets" class="input w-full bg-base-100 cursor-default">
              
              <span>{{ vFicheCompletePersonne.nombreProjets ? $n(vFicheCompletePersonne.nombreProjets, 'decimal') : '--' }}</span>
              
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { VFicheCompletePersonne } from '@/models/VFicheCompletePersonneModel'


defineProps({
  vFicheCompletePersonne: {
    type: Object as PropType<VFicheCompletePersonne>,
    required: true,
  },
})

</script>
<style scoped></style>
