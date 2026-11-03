import type { RouteRecordRaw } from 'vue-router'

const participerRoutes: Array<RouteRecordRaw> = [
  {
    path: 'participers',
    name: 'participerlistview',
    component: () => import('@/views/participer/ParticiperListView.vue'),
    
  },
  {
    path: 'participers/:id',
    name: 'participerdetailsview',
    component: () => import('@/views/participer/ParticiperDetailsView.vue'),
    
  },
  {
    path: 'participers/update/:id',
    name: 'participerupdateview',
    component: () => import('@/views/participer/ParticiperUpdateView.vue'),
  },
  {
    path: 'participers/create',
    name: 'participercreateview',
    component: () => import('@/views/participer/ParticiperCreateView.vue'),
  },
]

export default participerRoutes
