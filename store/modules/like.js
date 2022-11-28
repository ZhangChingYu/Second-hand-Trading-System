export default{
	state:{
		LikeList:[],
		selectedList:[]
	},
	getters:{
		//判断是否全选
		checkedAll(state){
			return LikeList.length === state.selectedList.length;
		}
	},
	mutations:{
		//全选
		checkAll(state){
			state.selectedList = LikeList.map(v=>{
				v.checked = true;
				return v.id;
			})
		},
		//全不选
		unCheckAll(state){
			LikeList.forEach(v=>{
				v.checked=false;
			})
			state.selectedList=[]
		},
		//单选
		selectedItem(index){
			let id=index;
			let i=state.selectedList.indexOf(id);
			//如果selectedList数组里已经存在就代表他之前就是选中状态，checked=false，并且selectedList删除
			if(i>-1){
				LikeList[index].checked=false;
				return state.selectedList.splice(i,1);
			}
			//如果之前没有选中，把当前id添加到list
			LikeList[index].checked=true;
			state.selectedList.push(id);
		},
		delete(state){
			LikeList=LikeList.filter(v=>{
				return state.selectedList.indexOf(v.id)===-1;
			})
		}
	},
	actions:{
		checkedAllFn({commit,getters}){
			getters.checkedAll  ?  commit("unCheckAll") : commit("checkAll");
		},
		deleteFn({commit}){
			commit('delete');
			commit('unCheckAll')
			uni.showToast({
				title:'删除成功',
				icon:'none'
			})
		}
	}
}