import {createWebHistory, createRouter} from "vue-router";

const routes = [
  {
    path: "/",
    component: () => import("@/components/Index"),
    children: [
      {
        name: 'Weight Tracker',
        path: "/weight",
        component: () => import("@/components/WeightTracker"),
      }
    ],
  },
  {
    path: "/login",
    component: () => import("@/components/Login"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;