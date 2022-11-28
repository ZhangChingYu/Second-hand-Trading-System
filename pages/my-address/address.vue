<template>
	<view class="my-path-list">
		<view class="path-list">
			<view class="path-item" v-for='(item,index) in addressList' :key='index' @tap="toAddAddress(index)">
				<view class="item-main">
					<view class="item-name" style="padding-right: 10rpx;" >{{item.receiverName}}</view>
					<view>{{item.receiverPhone}}</view>
					<view class="active" v-if='item.isDefault'>默认</view>
				</view>
				<view class="item-main">
					<view>{{item.region}} {{item.addressDetail}}</view>
				</view>
			</view>
		</view>
		
		<view class="add-path">
			<view class="add-path-btn" @tap="goAddAddress">新增地址</view>
		</view>
	</view>
</template>

<script>
	import {mapState} from 'vuex'
	export default{
		data(){
			return{
				addressList:[],
			}
		},
		mounted() {
			let that = this;
			let phone=uni.getStorageSync('user').phone;
			//获取地址列表
			that.api.get('/setting/addresses',{phone}).then(res=>{
				this.addressList=res;
				this.addressList.forEach(item=>{
					this.$set(item,'isDefault',false);
				})
				
				//设置默认地址
				that.api.get('/default/address',{phone}).then(res=>{
					this.addressList[res.rank].isDefault=true;
				})
			})
		},
		methods:{
			//修改
			toAddAddress(index){
				
				let addressObj=JSON.stringify({
					index:index,
					item:this.addressList[index]
				})
				
				uni.navigateTo({
					url:"/pages/my-address/add-address?data="+addressObj+""
				})
			},
			//新增
			goAddAddress(){
				uni.navigateTo({
					url:"/pages/my-address/add-address"
				})
			}
		}
	}
</script>

<style scoped>
	.add-path{
		padding: 20rpx 0;
		width: 100%;
		display: flex;
		justify-content: center;
	}
	.add-path-btn{
		border: 2rpx solid #d04b41;
		color: #d04b41;
		font-size: medium;
		border-radius: 30rpx;
		padding: 6rpx 60rpx;
	}
	.path-list{
		font-size: medium;
		padding-left: 20rpx ;
		padding-top: 10rpx;
	}
	.path-item{
		margin-top: 10rpx;
		padding: 10rpx 10rpx;
		border:1rpx solid #d04b41;
		border-radius: 10rpx;
	}
	.item-main{
		display: flex;
		align-items: center;
		padding: 10rpx 10rpx;
	}
	.item-name{
		padding-right: 10rpx;
	}
	.active{
		padding: 2rpx 10rpx;
		background-color: #d04b41;
		color: white;
		border-radius: 15rpx;
		text-align: center;
		width: 100rpx;
		margin-left: 50rpx;
	}
</style>