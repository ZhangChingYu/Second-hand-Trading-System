import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

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
export default store;