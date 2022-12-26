import App from './App'

import store from 'store/index.js'
import './common/uni.css'

// 引入自己封装的api
import api from './common/request/request.js'
Vue.prototype.api = api

import uView from '@/uni_modules/uview-ui'
Vue.use(uView)

Vue.prototype.$toast = function(title,duration=850,icon='none'){
	uni.showToast({
		title: title,
		icon:icon,
		duration:duration
	});
}



// #ifndef VUE3
import Vue from 'vue'
Vue.config.productionTip = false
Vue.prototype.$toast = function(title,duration=850,icon='none'){
	uni.showToast({
		title: title,
		icon:icon,
		duration:duration
	});
}

App.mpType = 'app'
const app = new Vue({
    ...App
})
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