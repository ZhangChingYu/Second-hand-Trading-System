<template>
	<view class="like-content">
		<!--自定义头部-->
		<view class="top">
			<text class="edit" v-text='isNavBar?"完成":"编辑"' @click="isNavBar = !isNavBar"></text>
			<!--分类-->
			<view class="classify-content">
				<text class="cataTitle" @click="showCatalogs" v-if="!showCata">分类&nbsp;▶&nbsp;</text>
				<text class="cataTitle" @click="showCatalogs" v-else>分类&nbsp;◢&nbsp;</text>
				<view class="catalogs" v-show="showCata">
					<ul>
						<li @click="showAll()" @tap="showCatalogs()">全部</li>
						<li v-for="(item,index) of catalogs" :key="index" @click="showCatalog(item.number)">
							{{item.name}}
						</li>
					</ul>
				</view>
			</view>
		</view>

		<template v-if="LikeList.length>0">
			<!--已收藏商品-->
			<view class="shop-list">
				<view class="shop-item" v-for='(item,index) of LikeList' :key='index'>
					<label class="radio" v-if="isNavBar" @tap="selectedItem(index)">
						<radio value="" color="#d04b41" :checked="item.checked"></radio>
					</label>
					<image class="shop-image" :src="'data:image/jpg;base64,' + item.coverPic"
						@tap="toDetail(item.number)">
					</image>
					<view class="shop-info" @tap="toDetail(item.number)">
						<view class="shop-name" v-if="item.name.length>20">{{item.name.substr(0,20)}}...</view>
						<view class="shop-name" v-else>{{item.name}}</view>
						<view class="shop-intro">{{item.intro}}</view>
						<view class="shop-price">￥{{item.price}}</view>
					</view>
				</view>
			</view>
			<!--底部-->
			<view class="manage" v-if="isNavBar">
				<label class="radio" @tap="checkedAllFn()">
					<radio value="" color="#d04b41" :checked="checkedAll()"></radio><text>全选</text>
				</label>
				<view class="delete" @tap="deleteFn()">删除</view>
			</view>
		</template>
		<template v-else>
			<view class="shop-else-list">
				<text>收藏夹为空哦~</text>
			</view>
		</template>
	</view>
</template>

<script>
	import {
		mapState,
		mapActions,
		mapGetters,
		mapMutations
	} from 'vuex'
	export default {
		data() {
			return {
				isNavBar: false,
				LikeList: [],
				selectedList: [],
				catalogs: [],
				showCata: false
			}
		},
		mounted() {
			let that = this;
			let phone = uni.getStorageSync('user').phone;

			this.showAll();

			//获取分类
			that.api.get('/catalog/catalogs').then(res => {
				this.catalogs = res;
			})
		},
		methods: {

			...mapActions(['checkedAllFn', 'deleteFn']),
			...mapMutations(['selectedItem']),

			//分类显示
			showCatalogs() {
				this.showCata = !this.showCata
			},
			showAll() {
				let phone = uni.getStorageSync('user').phone;
				//获取初始收藏列表
				this.api.get('/all/likes', {
					phone
				}).then(res => {
					this.LikeList = res;
					this.LikeList.forEach(item => {
						this.$set(item, 'checked', false);
					})
					console.log(this.LikeList);
				}).catch(err => {});
			},
			showCatalog(catalog) {
				let that = this;
				let phone = uni.getStorageSync('user').phone;
				that.api.get('/catalog/likes', {
					phone,
					catalog
				}).then(res => {
					console.log(res);
					if (res.length != 0) {
						this.LikeList = res;
						this.LikeList.forEach(item => {
							this.$set(item, 'checked', false);
						})
					} else {
						that.$toast('您尚未收藏过该分类的商品');
					}
				})
				this.showCatalogs();
			},

			//全选
			checkAll() {
				for (let i = 0; i < this.LikeList.length; i++) {
					this.LikeList[i].checked = true;
					this.selectedList.push(i);
				}
				console.log(this.selectedList)
			},
			//全不选
			unCheckAll() {
				this.LikeList.forEach(v => {
					v.checked = false;
				})
				this.selectedList = []
			},
			//单选
			selectedItem(index) {
				let id = index;
				let i = this.selectedList.indexOf(id);
				//如果selectedList数组里已经存在就代表他之前就是选中状态，checked=false，并且selectedList删除
				if (i > -1) {
					this.LikeList[index].checked = false;
					return this.selectedList.splice(i, 1);
				}
				//如果之前没有选中，把当前id添加到list
				this.LikeList[index].checked = true;
				this.selectedList.push(id);
				console.log(this.selectedList);
			},
			//判断是否全选
			checkedAll() {
				return this.LikeList.length === this.selectedList.length;
			},
			checkedAllFn() {
				if (this.checkedAll()) {
					this.unCheckAll();
				} else {
					this.checkAll();
				}
			},
			deleteFn() {
				let numbers = [];
				this.selectedList.forEach(item => {
					numbers.push(this.LikeList[item].number);
				})

				//调用api
				let that = this;
				let phone = uni.getStorageSync('user').phone;
				that.api.del('/likes', {
					phone,
					numbers
				});

				//刷新页面
				uni.redirectTo({
					url: 'like'
				});

				uni.showToast({
					title: '删除成功',
					icon: 'none'
				})
			},
			toDetail(number) {
				uni.navigateTo({
					url: `/pages/detail/index?number=${number}`
				})
			}
		}
	}
</script>

<style scoped>
	.like-content {
		font-size: medium;
	}

	.top {
		display: flex;
		justify-content: space-between;
		border-top: 2rpx solid black;
		background-color: #d04b41;
		padding: 20rpx 0;
		width: 100%;
		color: white;
	}

	.edit {
		margin-left: 20rpx;
		justify-content: left;
	}

	.catalogs {
		position: absolute;
		top: 75rpx;
		right: 1%;
		padding: 0 30rpx;
		width: 40%;
		background-color: #a1a1a1;
		border-radius: 0 0 20rpx 20rpx;
		box-sizing: border-box;
		line-height: 84rpx;
		font-size: 30rpx;
		z-index: 2;
		opacity: 85%;
	}

	.shop-item {
		background-color: white;
		display: flex;
		padding: 20rpx;
		margin: 20rpx;
		align-items: center;
		background-color: ;
		border-radius: 10rpx;
		border: 4rpx solid #d04b41;
	}

	.shop-image {
		border-radius: 10rpx;
		border: 2rpx solid coral;
		width: 200rpx;
		height: 150rpx;
		text-overflow: ellipsis;
	}

	.shop-info {
		margin-left: 20rpx;
	}

	.shop-name {
		width: 400rpx;
		font-weight: 500;
		font-size: large;
	}

	.shop-intro {
		color: gray;
		font-size: small;
	}

	.shop-price {
		color: red;
		font-weight: 650;
	}

	.manage {
		border-top: 2rpx solid #d04b41;
		margin: 0 20rpx;
		position: fixed;
		bottom: 0;
		left: 0;
		width: 100%;
		height: 100rpx;
		display: flex;
		justify-content: space-between;
		align-items: center;
		background-color: white;
	}

	.delete {
		background-color: #d04b41;
		color: white;
		height: 100%;
		padding: 0 60rpx;
		font-size: large;
		line-height: 100rpx;
	}

	.shop-else-list {
		background-color: #F7F7F7;
		display: flex;
		align-items: center;
		justify-content: center;
		position: absolute;
		left: 0;
		right: 0;
		top: 0;
		bottom: 0;
	}
</style>
