import Vue from 'vue'
import App from './App.vue'
import router from './router'
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios'
import './assets/global.css'

Vue.prototype.$http = axios
// 1。API 请求的默认前缀
axios.create({
  baseURL: process.env.VUE_APP_BASE_API,
  timeout: 30000 // 请求超时时间
})
// 2。axios请求拦截 设置Authorization
axios.interceptors.request.use(config =>{
  config.headers.Authorization = window.sessionStorage.getItem("token")
  return config;
})

Vue.use(Element)
Vue.use(router)
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
