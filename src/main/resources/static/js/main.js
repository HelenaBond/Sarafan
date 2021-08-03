// благодаря babel можем писать тут без ";" и сокращать синтаксис фунуций
import Vue from 'vue'

// в ковычках пишем имя из json файла
import VueResource from 'vue-resource'

// воспринимает static.js как корневой т.к. указали модули в файле webpack.config.js
import App from 'pages/App.vue'

Vue.use(VueResource)

new Vue({
    el: '#app',
    render: a => a(App)
})