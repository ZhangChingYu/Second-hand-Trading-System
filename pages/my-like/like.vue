<template>
	<view class="like-content">

		<template v-if="list.length>0">
			<!--自定义头部-->
			<view class="top">
				<text class="edit" v-text='isNavBar?"完成":"编辑"' @click="isNavBar = !isNavBar"></text>
			</view>

			<!--已收藏商品-->
			<view class="shop-list">
				<view class="shop-item" v-for='(item,index) in list' :key='index'>
					<label class="radio" v-if="isNavBar" @tap="selectedItem(index)">
						<radio value="" color="#d04b41" :checked="item.checked"></radio>
					</label>
					<image class="shop-image" :src="item.imgUrl"></image>
					<view class="shop-info">
						<view class="shop-name">{{item.name}}</view>
						<view class="shop-intro">{{item.intro}}</view>
						<view class="shop-intro">{{item.address}}</view>
						<view class="shop-price">￥{{item.price}}</view>
					</view>
				</view>
			</view>
			<!--底部-->
			<view class="manage" v-if="isNavBar">
				<label class="radio" @tap="checkedAllFn">
					<radio value="" color="#d04b41" :checked="checkedAll"></radio><text>全选</text>
				</label>
				<view class="delete" @tap="deleteFn">删除</view>
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
		mapState,mapActions,mapGetters,mapMutations
	} from 'vuex'
	export default {
		data() {
			return {
				isNavBar: false,
			}
		},
		mounted() {
			let that = this;
			let phone=uni.getStorageSync('user').phone;
			console.log(phone);
			that.api.get('/all/likes',{phone}).then(res=>{
				console.log(res);
				that.getLikes(res);
			}).catch(err=>{});
		},
		computed: {
			...mapState({
				list: state => state.like.list
			}),
			...mapGetters(['checkedAll'])
		},
		methods: {
			
			...mapActions(['checkedAllFn','deleteFn']),
			...mapMutations(['selectedItem']),
			
			getLikes(res){
				console.log(res);
			}
		}
	}
</script>

<style scoped>
	.like-content {
		font-size: medium;
	}

	.top {
		border-top: 2rpx solid black;
		background-color: #d04b41;
		padding: 20rpx 0;
		width: 100%;
	}

	.edit {
		margin-left: 20rpx;
		color: white;
		justify-content: left;
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
	}

	.shop-info {
		margin-left: 20rpx;
	}

	.shop-name {
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
	.shop-else-list{
		background-color: #F7F7F7;
		display: flex;
		align-items: center;
		justify-content: center;
		position: absolute;
		left: 0;
		right: 0;
		top:0;
		bottom: 0;
	}
</style>
