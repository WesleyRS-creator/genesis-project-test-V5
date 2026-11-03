import type { RouteRecordRaw } from 'vue-router'

const adresseRoutes: Array<RouteRecordRaw> = [
  {
    path: 'adresses',
    name: 'adresselistview',
    component: () => import('@/views/adresse/AdresseListView.vue'),
    
  },
  {
    path: 'adresses/:id',
    name: 'adressedetailsview',
    component: () => import('@/views/adresse/AdresseDetailsView.vue'),
    
  },
  {
    path: 'adresses/update/:id',
    name: 'adresseupdateview',
    component: () => import('@/views/adresse/AdresseUpdateView.vue'),
  },
  {
    path: 'adresses/create',
    name: 'adressecreateview',
    component: () => import('@/views/adresse/AdresseCreateView.vue'),
  },
]

export default adresseRoutes
