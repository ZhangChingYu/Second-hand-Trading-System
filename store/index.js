import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

const store = new Vuex.Store({
	state:{
		isLogin:false,
		tel:'',
		userType:'',
		userid:'',
		token:''
	},
	actions:{
		tologin(context,userid,loginToken){
			context.commit('LoGIN',userid,loginToken);
		}
	},
	mutations:{
		LoGIN(state,userid,loginToken){
			state.isLogin = true;
			state.userid = userid;
			state.token = loginToken;
		}
	},
	getters:{
		
	}
	
})
export default store;