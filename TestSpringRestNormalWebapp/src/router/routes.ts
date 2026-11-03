import type { RouteRecordRaw } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import SettingsView from '@/views/settings/SettingsView.vue'
import NotFoundView from '@/views/error/NotFoundView.vue'
import MainLayout from '@/layouts/MainLayout.vue'
import adresseRoutes from './entities/adresseRoutes'
import participerRoutes from './entities/participerRoutes'
import passeportRoutes from './entities/passeportRoutes'
import personneRoutes from './entities/personneRoutes'
import projetRoutes from './entities/projetRoutes'
import vFicheCompletePersonneRoutes from './entities/vFicheCompletePersonneRoutes'
import vProjetsEtEquipeRoutes from './entities/vProjetsEtEquipeRoutes'
import vSynthetiseGlobalRoutes from './entities/vSynthetiseGlobalRoutes'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/home',
  },
  {
    path: '/',
    component: MainLayout,
    children: [
      { path: 'home', name: 'home', component: HomeView },
      { path: 'settings', name: 'settings', component: SettingsView },
      ...adresseRoutes,
      ...participerRoutes,
      ...passeportRoutes,
      ...personneRoutes,
      ...projetRoutes,
      ...vFicheCompletePersonneRoutes,
      ...vProjetsEtEquipeRoutes,
      ...vSynthetiseGlobalRoutes,
    ],
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'not-found',
    component: NotFoundView,
  },
]
export default routes
