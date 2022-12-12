export default {
	namespaced:true,
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
}