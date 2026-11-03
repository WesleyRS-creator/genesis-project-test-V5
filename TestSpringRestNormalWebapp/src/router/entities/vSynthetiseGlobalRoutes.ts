import type { RouteRecordRaw } from 'vue-router'

const vSynthetiseGlobalRoutes: Array<RouteRecordRaw> = [
  {
    path: 'vSynthetiseGlobals',
    name: 'vSynthetiseGloballistview',
    component: () => import('@/views/vSynthetiseGlobal/VSynthetiseGlobalListView.vue'),
    
  },
  {
    path: 'vSynthetiseGlobals/:id',
    name: 'vSynthetiseGlobaldetailsview',
    component: () => import('@/views/vSynthetiseGlobal/VSynthetiseGlobalDetailsView.vue'),
    
  },
]

export default vSynthetiseGlobalRoutes
