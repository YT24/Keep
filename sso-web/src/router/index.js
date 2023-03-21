import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "@/components/Login";
import Index from "@/views/Index"
import Clients from "@/components/Clients";
import Roles from "@/views/menu/Roles";
import Users from "@/views/user/Users";


Vue.use(VueRouter)

const routes = [
    {path: '/', redirect: '/login'},
    {path: '/login', component: Login},
    {path: '/index', component: Index,
        children: [
            {path: '/clients', component: Clients},
            {path: '/roles',component: Roles},
            {path: '/users',component: Users}
        ]
    },

]

const router = new VueRouter({
    //mode: 'history',
    base: process.env.BASE_URL,
    routes
})


const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (to) {
    return VueRouterPush.call(this, to).catch(err => err)
}
// 挂在路由导航守卫
router.beforeEach((to, from, next) => {
    if (to.path === '/login') return next()
    const token = window.sessionStorage.getItem('token')
    if (!token) return next('/login')
    next()
})

export default router
