export default{
	state:{
		list:[
			{
				checked:false,
				id: 1,
				name: "拉面1",
				intro: "简介按时把说不定",
				address:"地址碍事梨ofhad两艘if",
				price:"35",
				imgUrl:"../../static/image/shop3.jfif"
			},
			{
				checked:false,
				id: 2,
				name: "拉面2",
				intro: "简介抒发感受梵蒂冈",
				address:"地址能覆盖那些承诺",
				price:"30",
				imgUrl:"../../static/image/shop1.jpg"
			},
		],
		selectedList:[]
	},
	getters:{
		//判断是否全选
		checkedAll(state){
			return state.list.length === state.selectedList.length;
		}
	},
	mutations:{
		//全选
		checkAll(state){
			state.selectedList = state.list.map(v=>{
				v.checked = true;
				return v.id;
			})
		},
		//全不选
		unCheckAll(state){
			state.list.forEach(v=>{
				v.checked=false;
			})
			state.selectedList=[]
		},
		//单选
		selectedItem(state,index){
			let id=state.list[index].id;
			let i=state.selectedList.indexOf(id);
			//如果selectedList数组里已经存在就代表他之前就是选中状态，checked=false，并且selectedList删除
			if(i>-1){
				state.list[index].checked=false;
				return state.selectedList.splice(i,1);
			}
			//如果之前没有选中，把当前id添加到list
			state.list[index].checked=true;
			state.selectedList.push(id);
		},
		delete(state){
			state.list=state.list.filter(v=>{
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