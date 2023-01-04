import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import TEST from '@/components/TEST'
import login from '@/components/login'
import register from '@/components/register'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/test',
      name: 'TEST',
      component: TEST
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
    ,
    {
      path: '/register',
      name: 'register',
      component: register
    }
  ]
})
