import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  // {
  //   path: '/',
  //   name: 'home',
  //   component: HomeView
  // },
  // {
  //   path: '/about',
  //   name: 'about',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  // }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router




// const routes = [
//   {
//     path: "/",
//     component: () => import("@/components/Index"),
//     children: [
//       {
//         name: 'Weight Tracker',
//         path: "/weight",
//         component: () => import("@/components/WeightTracker"),
//       }
//     ],
//   },
//   {
//     path: "/login",
//     component: () => import("@/components/Login"),
//   },
// ];
//
// const router = createRouter({
//   history: createWebHistory(),
//   routes,
// });
//
// export default router;
