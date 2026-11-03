import type { RouteRecordRaw } from 'vue-router'

const personneRoutes: Array<RouteRecordRaw> = [
  {
    path: 'personnes',
    name: 'personnelistview',
    component: () => import('@/views/personne/PersonneListView.vue'),
    
  },
  {
    path: 'personnes/:id',
    name: 'personnedetailsview',
    component: () => import('@/views/personne/PersonneDetailsView.vue'),
    
  },
  {
    path: 'personnes/update/:id',
    name: 'personneupdateview',
    component: () => import('@/views/personne/PersonneUpdateView.vue'),
  },
  {
    path: 'personnes/create',
    name: 'personnecreateview',
    component: () => import('@/views/personne/PersonneCreateView.vue'),
  },
]

export default personneRoutes
