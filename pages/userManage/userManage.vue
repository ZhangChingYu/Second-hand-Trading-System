<template>
	<view class="content">
		<!--遮挡-->
		<view style="width: 100%; height: 180rpx; z-index: 9996; position: fixed; top: 0; background-color: white;">
		</view>
		<!--搜索-->
		<view class="search">
			<input placeholder="请输入用户名/真实姓名" v-model="searchValue" />
			<image src="../../static/image/searchUser.png" @click="search()" mode="heightFix"></image>
		</view>

		<!--筛选-->
		<view class="catalogs">
			<!--权限-->
			<view class="cataContent">
				<span class="catalog" v-show="catalog!=1" style="color: #7f7774;" @tap="showCata(1)">权限▶</span>
				<span class="catalog" v-show="catalog==1" style="color: #d04b41;" @tap="showCata(1)">权限◢</span>
			</view>
			<!--违规-->
			<view class="cataContent">
				<span class="catalog" v-show="catalog!=2" style="color: #7f7774;" @tap="showCata(2)">违规▶</span>
				<span class="catalog" v-show="catalog==2" style="color: #d04b41;" @tap="showCata(2)">违规◢</span>
			</view>
			<!--交易-->
			<view class="cataContent">
				<span class="catalog" v-show="catalog!=3" style="color: #7f7774;" @tap="showCata(3)">交易▶</span>
				<span class="catalog" v-show="catalog==3" style="color: #d04b41;" @tap="showCata(3)">交易◢</span>
			</view>
		</view>
		<!--选项-->
		<view class="selects" v-show="catalog==1" style="margin-left: 0%; margin-right: 66%;">
			<ul>
				<li @tap="showSelect(1,-1)">全部<text v-if="per==-1" style="color: #d04b41;">√</text></li>
				<li v-for="(item,index) of permission" :key="index" @click="showSelect(1,item.number)">
					{{item.name}}
					<text v-if="per==item.number" style="color: #d04b41;">√</text>
				</li>
			</ul>
		</view>
		<view class="selects" v-show="catalog==2" style="margin-left: 33%; margin-right: 33%;">
			<ul>
				<li @tap="showSelect(2,-1)">全部<text v-if="vio==-1" style="color: #d04b41;">√</text></li>
				<li v-for="(item,index) of violation" :key="index" @click="showSelect(2,item.number)">
					{{item.range}}
					<text v-if="vio==item.number" style="color: #d04b41;">√</text>
				</li>
			</ul>
		</view>
		<view class="selects" v-show="catalog==3" style="margin-left: 66%;">
			<ul>
				<li @tap="showSelect(3,-1)">全部<text v-if="tra==-1" style="color: #d04b41;">√</text></li>
				<li v-for="(item,index) of trade" :key="index" @click="showSelect(3,item.number)">
					{{item.range}}
					<text v-if="tra==item.number" style="color: #d04b41;">√</text>
				</li>
			</ul>
		</view>

		<!--用户数据条-->
		<view class="userContent">
			<!--全选-->
			<view class="chooseAll">
				<label class="radio" @tap="checkedAllFn()">
					<radio value="" color="#d04b41" :checked="checkedAll()"></radio><text>全选</text>
				</label>
			</view>
			<!--每条数据-->
			<view class="user" v-for="(item,index) of users" :key="index">
				<label class="radio" @tap="selectedItem(index)" style="margin-left: 10rpx;">
					<radio value="" color="#d04b41" :checked="item.checked"></radio>
				</label>
				<image class="avatar" :src="'data:image/jpg;base64,' + item.avatar" @tap="toDetail(item.phone)"
					mode="scaleToFill">
				</image>
				<view class="userInfo" @tap="toDetail(item.phone)">
					<view class="text" v-if="item.userName.length>5">用户名：{{item.userName.substr(0,5)}}...</view>
					<view class="text" v-else>用户名：{{item.userName}}</view>
					<view class="text">真实姓名：{{item.realName}}</view>
					<view class="text">违规次数：{{item.violationCount}}</view>
					<view class="text">权限：{{item.authority}}</view>
					<view class="text">买入：{{item.buy}}</view>
					<view class="text">卖出：{{item.sell}}</view>
				</view>
			</view>
		</view>

		<!--底部操作选项-->
		<view class="btns">
			<button @click="delUser()">删除选中用户</button>
			<button @click="change()">修改用户权限</button>
		</view>

		<!--修改弹窗-->
		<view class="popup" v-show="showChange">
			<view class="popup-info">
				<view>
					<text>请选择您要更改为权限：</text>
					<view v-for="(item,index) of permission" :key="index"
						style="justify-items: flex-start; margin-top: 20rpx;">
						<label class="radio" @tap="changePer(item.number)">
							<radio value="" color="#d04b41" :checked="changeper==item.number"></radio>
						</label>
						<text>{{item.name}}</text>
					</view>
				</view>
				<view class="popup-btn">
					<view><button type="default" class="popBtn" @tap="cancel">取消</button></view>
					<view><button type="default" class="popBtn" @tap="affirm">确认</button></view>
				</view>
			</view>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				searchValue: '',
				catalog: 0,
				permission: [], //权限
				per: -1,
				violation: [], //违规
				vio: -1,
				trade: [], //交易
				tra: -1,
				users: [],
				selectedList: [],
				showChange: false,
				changeper: -1, //更改权限
			}
		},
		mounted() {
			let that = this;
			//获取所有用户、权限、违规和交易目录
			that.api.get('/manage/user/all').then(res => {
				that.users = res.data;
				console.log('users:', that.users)
			})
			that.api.get('/manage/select/authority').then(res => {
				that.permission = res.data;
				console.log('authority:', that.permission)
			})
			that.api.get('/manage/select/violation').then(res => {
				that.violation = res.data;
				console.log('violation:', that.violation)
			})
			that.api.get('/manage/select/trade').then(res => {
				that.trade = res.data;
				console.log('trade:', that.trade)
			})
		},
		methods: {
			//搜索用户
			search() {
				let that = this;
				if (this.searchValue != '') {
					let name = this.searchValue;
					that.api.get('/manage/user/name', {
						name
					}).then(res => {
						that.users = res.data;
					})
				} else {
					that.api.get('/manage/user/all').then(res => {
						that.users = res.data;
						console.log('users:', that.users)
					})
				}
			},
			//删除用户
			delUser() {
				let selects=this.selectedList;
				let that=this;
				uni.showModal({
					title: '警告',
					content: '确认要删除选中用户吗？（数据不可复原）',
					success: function(res) {
						if (res.confirm) {
							console.log('用户点击确定');
							let ids = [];
							selects.forEach(item => {
								ids.push(that.users[item].id);
							})
							that.api.del('/manage/user/delete', ids).then(res => {
								console.log(res);
							})
							//刷新users
							that.api.get('/manage/user/all').then(res => {
								that.users = res.data;
								that.per = -1;
								that.vio = -1;
								that.tra = -1;
							})
						} else if (res.cancel) {
							console.log('用户点击取消')
						}
					}
				})
			},
			//修改用户权限
			change() {
				this.showChange = true
			},
			//点击筛选条件
			showCata(catalog) {
				if (catalog == this.catalog) {
					this.catalog = 0;
					return 0;
				}
				this.catalog = catalog;
			},
			//筛选
			showSelect(catalog, number) {
				console.log(catalog, number)
				switch (catalog) {
					//选中权限
					case 1:
						this.per = number;
						break;
					case 2:
						this.vio = number;
						break;
					case 3:
						this.tra = number;
						break;
				}
				let number1 = this.per;
				let number2 = this.vio;
				let number3 = this.tra;
				let that = this;
				that.api.get('/manage/user/select', {
					number1,
					number2,
					number3
				}).then(res => {
					this.users = res.data;
					console.log(res);
					console.log(number1,number2,number3)
				})
			},
			//判断是否全选
			checkedAll() {
				return this.users.length <= this.selectedList.length;
			},
			//点击全选
			checkedAllFn() {
				if (this.checkedAll()) {
					this.unCheckAll();
				} else {
					this.checkAll();
				}
			},
			//单选
			selectedItem(index) {
				let id = index;
				let i = this.selectedList.indexOf(id);
				//如果selectedList数组里已经存在就代表他之前就是选中状态，checked=false，并且selectedList删除
				if (i > -1) {
					this.users[index].checked = false;
					return this.selectedList.splice(i, 1);
				}
				//如果之前没有选中，把当前id添加到list
				this.users[index].checked = true;
				this.selectedList.push(id);
				console.log(this.selectedList);
			},
			//全选
			checkAll() {
				for (let i = 0; i < this.users.length; i++) {
					this.users[i].checked = true;
					this.selectedList.push(i);
				}
				console.log(this.selectedList)
			},
			//全不选
			unCheckAll() {
				this.users.forEach(v => {
					v.checked = false;
				})
				this.selectedList = []
			},
			toDetail(phone) {
				console.log(phone)
				uni.navigateTo({
					url: "/pages/userDetail/userDetail?data=" + phone
				})
			},
			// 点击弹窗取消
			cancel() {
				this.showChange = false;
			},
			// 点击弹窗确认
			affirm() {
				this.showChange = false;
				let ids = [];
				this.selectedList.forEach(item => {
					ids.push(this.users[item].id);
				})
				let number = this.changeper;
				let that = this;
				that.api.put('/manage/user/authority', {
					ids,
					number
				}).then(res => {
					console.log(res);
				})
				//刷新users
				that.api.get('/manage/user/all').then(res => {
					this.users = res.data;
					this.per = -1;
					this.vio = -1;
					this.tra = -1;
				})
			},
			changePer(num) {
				this.changeper = num;
			}
		}
	}
</script>

<style>
	.content {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}

	.search {
		width: 90%;
		display: flex;
		justify-content: center;
		margin-top: 20rpx;
		border: 2rpx solid #d04b41;
		border-radius: 15rpx;
		position: fixed;
		top: 20rpx;
		background-color: white;
		z-index: 9997;
	}

	.search>input {
		width: 80%;
		height: 60rpx;
	}

	.search>image {
		width: 10%;
		height: 60rpx;
		padding-left: 20rpx;
		border-left: 1rpx solid #d04b41;
	}

	.catalogs {
		display: flex;
		width: 100%;
		border: 2rpx solid gray;
		position: fixed;
		top: 120rpx;
		height: 60rpx;
		align-items: center;
		background-color: white;
		z-index: 9997;
	}

	.cataContent {
		width: 33%;
		text-align: center;
	}

	.userContent {
		width: 90%;
		margin-top: 200rpx;
		margin-bottom: 150rpx;
		font-size: medium;
	}

	.user {
		margin-top: 10rpx;
		display: flex;
		border: 2rpx solid #d04b41;
		border-radius: 15rpx;
		height: 200rpx;
		justify-content: center;
		align-items: center;
	}

	.avatar {
		width: 150rpx;
		height: 150rpx;
		border: 2rpx solid #d04b41;
		border-radius: 15rpx;
	}

	.userInfo {
		width: 70%;
		margin-left: 10rpx;
		display: flex;
		flex-wrap: wrap;
	}

	.text {
		font-size: small;
		width: 45%;
		margin-left: 10rpx;
		margin-bottom: 10rpx;
		text-decoration: underline;
		text-decoration-color: #d04b41;
	}

	.selects {
		position: fixed;
		top: 180rpx;
		border: 1rpx solid grey;
		padding: 10rpx 40rpx;
		z-index: 9997;
		background-color: white;
	}

	.btns {
		display: flex;
		height: 100rpx;
		position: fixed;
		bottom: 0;
		width: 100%;
		border-top: 2rpx solid #d04b41;
		justify-content: center;
		background-color: #d04b41;
	}

	.btns>button {
		margin: 10rpx 30rpx;
		color: #d04b41;
		background-color: white;
		border: 3rpx solid #d04b41;
	}

	.popup {
		position: fixed;
		left: 0;
		right: 0;
		top: 0;
		height: 100vh;
		background-color: rgba(0, 0, 0, 0.6);
		z-index: 9998;
	}

	.popup-info {
		position: fixed;
		width: 550upx;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
		font-size: 30upx;
		padding: 40upx;
		border-radius: 20upx;
		background-color: #fff;
		z-index: 9999;
	}

	.popup-btn {
		margin-top: 100rpx;
		display: flex;
		justify-content: center;
	}

	.popBtn {
		margin: 10rpx 60rpx;
		border: 2rpx solid #d04b41;
		font-size: medium;
		height: 70rpx;
	}
</style>
