<template>
	<view class="content">
		<view style="display: flex; width: 100%;">
			<image class="avatar" :src="'data:image/jpg;base64,' + user.avatar" style="margin-left: 5%;"></image>
			<view class="name">
				<view style="font-size: medium; margin-top: 0; margin-bottom: 0; color: #d04b41;">{{grade}}&nbsp;星用户</view>
				<view>用户名：{{user.userName}}</view>
				<view>买入数：{{user.buy}}&nbsp;&nbsp;卖出数：{{user.sell}}</view>
			</view>
		</view>
		<view class="info-content-item">违规次数：{{user.violationCount}}</view>
		<view class="info-content-item">邮箱：{{user.email}}</view>
		<view class="info-content-item">当前状态：{{user.authority}}</view>
		
		<!--评论展示-->
		<view class="comment">
			<view style="color: #ccc; justify-content: space-between; display: flex; border-bottom: 1rpx solid #cccccc;">
				<view>ta的相关评论</view>
				<view v-if="!showCom" @click="show()">点击展开</view>
				<view v-else style="color: #d04b41;" @click="show()">点击收起</view>
			</view>
			<view v-show="showCom" class="comment-item" v-for="(item,index) of evaluations" :key="index">
				<image v-if="!item.isAnonymous" class="avatar" :src="'data:image/jpg;base64,' + item.buyer.buyerHeadPic"></image>
				<image v-else class="avatar" src="../../static/image/my_icon/header.png"></image>
				<view class="head" @click="toDetail(item.number)">
					<view v-if="!item.isAnonymous" style="color: black; font-weight: 650; font-size: medium;">{{item.buyer.buyerName}}</view>
					<view v-else style="color: black; font-weight: 650; font-size: medium;">匿名用户</view>
					<view>商品编号：{{item.number}}</view>
					<view v-if="item.productName.length<11">商品名称：{{item.productName}}</view>
					<view v-else >商品名称：{{item.productName.substr(0,10)}}......</view>
					<view>评价日期：{{item.date}}</view>
				</view>
				<view class="score">
					<text>描述相符得分:{{item.score1}}</text>
					<text>物流服务得分:{{item.score2}}</text>
					<text>服务态度得分:{{item.score3}}</text>
				</view>
				<view class="evaluate">评价：{{item.evaluate}}</view>
			</view>
		</view>

		<button class="btn" @click="chat">与TA沟通</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				phone: '',
				user: {},
				showCom:false,
				evaluations:[],
				grade:''
			}
		},
		onLoad(e) {
			this.phone = e.phone;
			let that = this;
			let phone=this.phone;
			let sellerPhone=phone;
			console.log('id:', this.phone)
			that.api.get('/evaluations',{sellerPhone}).then(res=>{
				this.evaluations=res;
				console.log('res:',res)
			})
			that.api.get('/seller/grade',{phone}).then(res=>{
				this.grade=res;
			})
			that.api.get('/manage/user',{phone}).then(res=>{
				this.user=res.data;
				console.log(res)
			})
		},
		methods: {
			show(){
				this.showCom=!this.showCom;
			},
			toDetail(number){
				uni.navigateTo({
					url: `/pages/detail/index?number=${number}`
				})
			},
			//聊天
			chat(){
				
			}
		}
	}
</script>

<style>
	.content {
		vertical-align: middle;
		display: flex;
		flex-direction: column;
		align-items: center;
		margin-top: 30rpx;
		font-size: medium;
	}

	.avatar {
		width: 150rpx;
		border: 2rpx solid #d04b41;
		border-radius: 15rpx;
		height: 150rpx;
	}

	.name {
		width: 80%;
	}

	.name>view {
		width: auto;
		margin: 5% 10%;
		border-bottom: 1px solid #cccccc;
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

	.info-content-item {
		margin: 20rpx 5%;
		padding: 20rpx 5rpx;
		border-bottom: 1px solid #cccccc;
		border-left: 5rpx solid #d04b41;
		width: 90%;
	}
	
	.comment{
		width: 90%;
		margin-top: 5%;
	}
	
	.comment-item{
		margin: 20rpx 0;
		border-radius: 15rpx;
		border: 2rpx solid #d04b41;
		display: flex;
		flex-wrap: wrap;
		align-items: center;
		padding: 10rpx 10rpx;
	}
	
	.head{
		width: 70%;
		color: #cccccc;
		font-size: x-small;
		margin-left: 10rpx;
	}
	
	.score{
		width: 100%;
		color: #d04b41;
	}
	
	.score >text{
		margin-right: 20rpx;
		font-size: small;
	}
	
	.evaluate{
		width: 100%;
	}

	.btn {
		justify-self: center;
		margin: 10% 30rpx;
		color: #d04b41;
		background-color: white;
		border: 3rpx solid #d04b41;
		width: 40%;
	}
</style>
