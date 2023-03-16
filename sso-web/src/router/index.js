import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "@/components/Login";
import Index from "@/views/Index"
import Clients from "@/components/Clients";


Vue.use(VueRouter)

const routes = [
    {path: '/', redirect: '/login'},
    {path: '/login', component: Login},
    {
        path: '/index',
        component: Index,
        children: [{
            path: '/clients',
            component: Clients
        }]
    }
]

const router = new VueRouter({
    //mode: 'history',
    base: process.env.BASE_URL,
    routes
})

// 挂在路由导航守卫

router.beforeEach((to, from, next) => {
    if (to.path === '/login') return next()
    const token = window.sessionStorage.getItem('token')
    if (!token) return next('/login')
    next()
})

export default router
