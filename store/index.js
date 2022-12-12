import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

import user from '@/store/modules/user.js'
// import product from '@/store/modules/product.js'

const store = new Vuex.Store({
	
	modules:{
		user,
		// product
	}
	
})
export default store;