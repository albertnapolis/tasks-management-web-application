import { createRouter, createWebHistory } from 'vue-router'
import SigninComponent from '@/components/SigninComponent.vue'
import SignupComponent from '@/components/SignupComponent.vue'
import DashboardComponent from '@/components/DashboardComponent.vue'
import PageNotFound from '@/components/PageNotFound.vue'
import TaskComponent from '@/components/TaskComponent.vue'
import StatisticComponent from '@/components/StatisticComponent.vue'
import EmailComponent from '@/components/EmailComponent.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/sign-in', component: SigninComponent, meta: { requiresGuest: true } },
    { path: '/sign-up', component: SignupComponent, meta: { requiresGuest: true } },
    { 
      path: '/', 
      component: DashboardComponent,
      children: [
        {
          path: 'tasks',
          component: TaskComponent
        },

        {
          path: 'reports',
          component: StatisticComponent
        }
      ],
      meta: { requiresAuth: true }},
    { path: '/email', component: EmailComponent },
    { path: '/:catchAll(.*)', component: PageNotFound}
  ]
})

router.beforeEach((to, from, next) => {
  const isAuthenticated = !!localStorage.getItem("authToken")

  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!isAuthenticated) {
      next('/sign-in')
    } else {
      next()
    }
  } else if (to.matched.some(record => record.meta.requiresGuest)) {
    if (isAuthenticated) {
      next('/')
    } else {
      next()
    }
  } else {
    next()
  }
})

export default router
