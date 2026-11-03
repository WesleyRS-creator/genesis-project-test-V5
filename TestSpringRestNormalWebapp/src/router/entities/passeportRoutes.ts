import type { RouteRecordRaw } from 'vue-router'

const passeportRoutes: Array<RouteRecordRaw> = [
  {
    path: 'passeports',
    name: 'passeportlistview',
    component: () => import('@/views/passeport/PasseportListView.vue'),
    
  },
  {
    path: 'passeports/:id',
    name: 'passeportdetailsview',
    component: () => import('@/views/passeport/PasseportDetailsView.vue'),
    
  },
  {
    path: 'passeports/update/:id',
    name: 'passeportupdateview',
    component: () => import('@/views/passeport/PasseportUpdateView.vue'),
  },
  {
    path: 'passeports/create',
    name: 'passeportcreateview',
    component: () => import('@/views/passeport/PasseportCreateView.vue'),
  },
]

export default passeportRoutes
