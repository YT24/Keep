/**
 * 整个项目文件入口
 */
import Vue from 'vue'
import App from './App.vue' // 所有组件的父组件
import router from './router'
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css'

Vue.use(Element)
Vue.use(router)
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
