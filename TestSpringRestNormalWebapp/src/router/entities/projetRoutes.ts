import type { RouteRecordRaw } from 'vue-router'

const projetRoutes: Array<RouteRecordRaw> = [
  {
    path: 'projets',
    name: 'projetlistview',
    component: () => import('@/views/projet/ProjetListView.vue'),
    
  },
  {
    path: 'projets/:id',
    name: 'projetdetailsview',
    component: () => import('@/views/projet/ProjetDetailsView.vue'),
    
  },
  {
    path: 'projets/update/:id',
    name: 'projetupdateview',
    component: () => import('@/views/projet/ProjetUpdateView.vue'),
  },
  {
    path: 'projets/create',
    name: 'projetcreateview',
    component: () => import('@/views/projet/ProjetCreateView.vue'),
  },
]

export default projetRoutes
