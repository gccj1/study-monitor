import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from "axios";
import 'flag-icons/css/flag-icons.min.css';
import 'element-plus/theme-chalk/dark/css-vars.css';
import {createPinia} from "pinia";
import  '@/assets/css/element.less';
axios.defaults.baseURL = 'http://localhost:8080'

const app = createApp(App)

app.use(router)
app.use(createPinia())

app.mount('#app')
