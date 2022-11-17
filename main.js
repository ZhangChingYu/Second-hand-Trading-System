import App from './App'

import store from 'store/index.js'
import './common/uni.css'

// 引入自己封装的api
import api from './common/api.js'
Vue.prototype.api = api



// #ifndef VUE3
import Vue from 'vue'
Vue.config.productionTip = false
App.mpType = 'app'
Vue.prototype.$toast = function(title,duration=850,icon='none'){
	uni.showToast({
		title: title,
		icon:icon,
		duration:duration
	});
}
const app = new Vue({
	store,
    ...App
})
app.$store = store;
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif