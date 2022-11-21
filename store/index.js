import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

//地址
import address from './modules/address.js'
//收藏
import like from './modules/like.js'

export default new Vuex.Store({
	
	modules:{
		address,
		like,
	}
})

const store = new Vuex.Store({
	state:{
		isLogin:false,
		tel:'',
		userType:'',
		user:{},
		token:''
	},
	actions:{
		tologin(context,user,loginToken){
			context.commit('LoGIN',user,loginToken);
		}
	},
	mutations:{
		LoGIN(state,user,loginToken){
			state.user = user;
			state.token = loginToken;
		}
	},
	getters:{
		
	}
	
})
