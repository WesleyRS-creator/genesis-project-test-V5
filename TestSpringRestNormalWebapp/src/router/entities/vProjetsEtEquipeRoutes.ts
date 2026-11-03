import type { RouteRecordRaw } from 'vue-router'

const vProjetsEtEquipeRoutes: Array<RouteRecordRaw> = [
  {
    path: 'vProjetsEtEquipes',
    name: 'vProjetsEtEquipelistview',
    component: () => import('@/views/vProjetsEtEquipe/VProjetsEtEquipeListView.vue'),
    
  },
  {
    path: 'vProjetsEtEquipes/:id',
    name: 'vProjetsEtEquipedetailsview',
    component: () => import('@/views/vProjetsEtEquipe/VProjetsEtEquipeDetailsView.vue'),
    
  },
]

export default vProjetsEtEquipeRoutes
