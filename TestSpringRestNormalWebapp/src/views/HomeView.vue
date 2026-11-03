<template>
  <div class="w-full">
    <!-- Header -->
    <div class="items-center mb-6">
      <h2 class="text-xl font-bold text-primary">{{ $t('home.greetDeveloper') }}</h2>
      <span class="text-gray-500">{{ $t('home.welcome') }}</span>
    </div>

    <h2 class="text-xl font-bold text-neutral">{{ $t('home.titles.entities') }}</h2>
    <hr class="border-gray-200 mb-4" />
    <!-- Quick Actions -->
    <div class="grid grid-cols-1 md:grid-cols-4 gap-4 mb-8">
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">Adresses</h3>
        <ul class="menu w-full">
          <li @click="useAdresse.goToListView()">
            <span>
              <LinkIcon />
              View list of Adresse
            </span>
          </li>

          <li @click="useAdresse.goToCreateFormView()">
            <span>
              <LinkIcon />
              Add new Adresse
            </span>
          </li>
        </ul>
      </div>
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">Participers</h3>
        <ul class="menu w-full">
          <li @click="useParticiper.goToListView()">
            <span>
              <LinkIcon />
              View list of Participer
            </span>
          </li>

          <li @click="useParticiper.goToCreateFormView()">
            <span>
              <LinkIcon />
              Add new Participer
            </span>
          </li>
        </ul>
      </div>
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">Passeports</h3>
        <ul class="menu w-full">
          <li @click="usePasseport.goToListView()">
            <span>
              <LinkIcon />
              View list of Passeport
            </span>
          </li>

          <li @click="usePasseport.goToCreateFormView()">
            <span>
              <LinkIcon />
              Add new Passeport
            </span>
          </li>
        </ul>
      </div>
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">Personnes</h3>
        <ul class="menu w-full">
          <li @click="usePersonne.goToListView()">
            <span>
              <LinkIcon />
              View list of Personne
            </span>
          </li>

          <li @click="usePersonne.goToCreateFormView()">
            <span>
              <LinkIcon />
              Add new Personne
            </span>
          </li>
        </ul>
      </div>
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">Projets</h3>
        <ul class="menu w-full">
          <li @click="useProjet.goToListView()">
            <span>
              <LinkIcon />
              View list of Projet
            </span>
          </li>

          <li @click="useProjet.goToCreateFormView()">
            <span>
              <LinkIcon />
              Add new Projet
            </span>
          </li>
        </ul>
      </div>
    </div>

    <h2 class="text-xl font-bold text-neutral">{{ $t('home.titles.views') }}</h2>
    <hr class="border-gray-200 mb-4" />
    <!-- Quick Actions -->
    <div class="grid grid-cols-1 md:grid-cols-4 gap-4 mb-8">
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">VFicheCompletePersonnes</h3>
        <ul class="menu w-full">
          <li @click="useVFicheCompletePersonne.goToListView()">
            <span>
              <LinkIcon />
              View list of VFicheCompletePersonne
            </span>
          </li>
        </ul>
      </div>
<div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">VProjetsEtEquipes</h3>
        <ul class="menu w-full">
          <li @click="useVProjetsEtEquipe.goToListView()">
            <span>
              <LinkIcon />
              View list of VProjetsEtEquipe
            </span>
          </li>
        </ul>
      </div>
<div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">VSynthetiseGlobals</h3>
        <ul class="menu w-full">
          <li @click="useVSynthetiseGlobal.goToListView()">
            <span>
              <LinkIcon />
              View list of VSynthetiseGlobal
            </span>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import LinkIcon from '@/components/icons/LinkIcon.vue'
import { useAdresses } from '@/composables/useAdresses'
import { useParticipers } from '@/composables/useParticipers'
import { usePasseports } from '@/composables/usePasseports'
import { usePersonnes } from '@/composables/usePersonnes'
import { useProjets } from '@/composables/useProjets'
import { useVFicheCompletePersonnes } from '@/composables/useVFicheCompletePersonnes'
import { useVProjetsEtEquipes } from '@/composables/useVProjetsEtEquipes'
import { useVSynthetiseGlobals } from '@/composables/useVSynthetiseGlobals'

const useAdresse = useAdresses()
const useParticiper = useParticipers()
const usePasseport = usePasseports()
const usePersonne = usePersonnes()
const useProjet = useProjets()
const useVFicheCompletePersonne = useVFicheCompletePersonnes()
const useVProjetsEtEquipe = useVProjetsEtEquipes()
const useVSynthetiseGlobal = useVSynthetiseGlobals()
</script>
