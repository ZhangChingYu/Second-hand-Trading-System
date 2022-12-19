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
// 登陆获取token,后期删除
import {login} from '@/common/request/api.js'
login().then(res=>{
	if(res.code == 666){
		console.log(res)
		let user = res.user;
		let token = res.token;
		uni.setStorage({
			key:'user',data:user
		})
		uni.setStorage({
			key:'token',data:token
		})
		Vue.prototype.$toast('登录成功！',1270);
	}
	
})


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