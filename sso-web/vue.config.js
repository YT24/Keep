const {defineConfig} = require('@vue/cli-service')
const Vue = require("vue");
module.exports = defineConfig({
    transpileDependencies: true,
    lintOnSave: false, // 关闭代码检查
    // 方式1
    // devServer: {
    //  proxy: 'http://localhost:5555'
    // }
    devServer: {
        proxy: {
            '/keep-sso': {
                target: 'http://localhost:5555',
                pathRewrite:{
                   // '^/keep-sso': ''
                },
                ws: false
            }
        }
    }
})
