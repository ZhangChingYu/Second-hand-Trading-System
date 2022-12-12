export default {
	namespaced:true,
	state:{
		choosedCatalog:''
	},
	actions:{
		setCatalog(context,catalog){
			context.commit('LoGIN',user,catalog);
		}
	},
	mutations:{
		SETCATALOG(state,catalog){
			state.choosedCatalog = catalog;
		}
	},
	getters:{
		
	}
}