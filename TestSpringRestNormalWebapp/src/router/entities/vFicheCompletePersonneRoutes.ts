import type { RouteRecordRaw } from 'vue-router'

const vFicheCompletePersonneRoutes: Array<RouteRecordRaw> = [
  {
    path: 'vFicheCompletePersonnes',
    name: 'vFicheCompletePersonnelistview',
    component: () => import('@/views/vFicheCompletePersonne/VFicheCompletePersonneListView.vue'),
    
  },
  {
    path: 'vFicheCompletePersonnes/:id',
    name: 'vFicheCompletePersonnedetailsview',
    component: () => import('@/views/vFicheCompletePersonne/VFicheCompletePersonneDetailsView.vue'),
    
  },
]

export default vFicheCompletePersonneRoutes
