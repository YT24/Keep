import axios from "axios";
import Vue from "vue";

Vue.prototype.$http = axios

axios.create({
    baseURL: process.env.VUE_APP_BASE_API,
    timeout: 30000 // 请求超时时间
})

// 2。axios请求拦截 设置Authorization
axios.interceptors.request.use(config => {
    config.headers.Authorization = window.sessionStorage.getItem("token")
    return config;
})