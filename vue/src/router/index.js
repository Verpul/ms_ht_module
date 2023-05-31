import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from "@/components/Index";
import WeightTracker from "@/components/WeightTracker";
import Purchases from "@/components/Purchases";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    component: Index,
    children: [
      {
        name: "Weight Tracker",
        path: "/weight",
        component: WeightTracker
      },
      {
        name: "Purchases",
        path: "/purchases",
        component: Purchases
      },
    ]
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

