<template>
	<view class="main">
		<view class="top">
			<image src="../../../static/image/agreement.png" class="topImg"></image>
			<view class="title">用户协议ID： {{one.id}}</view>		
		</view>
		<view class="content">
			<rich-text>{{oneAgreement}}</rich-text>
		</view>
		<view class="bt">
			<button class="up" @click="showOne">发布</button>
			<button class="down" @click="deleteOne">删除</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				one:{},
				oneAgreement:'222222222222222222222222222222222222222',
			}
		},
		mounted() {
			this.getDetail();
		},
		onLoad(option){
			this.one = JSON.parse(option.oneDetail);
		},
		methods: {
			// 获取协议详情
			async getDetail(){
				const that  = this;
				try{
					that.oneAgreement = await this.api.get('',{id:this.one.id});
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 删除协议
			deleteOne(){
				let that = this;
				uni.showModal({
					title: '删除协议',
					// 提示文字
					content: '是否删除该用户协议？',
					// 取消按钮的文字自定义
					cancelText: "取消",
					// 确认按钮的文字自定义
					confirmText: "确认",
					//删除字体的颜色
					confirmColor:'red',
					//取消字体的颜色
					cancelColor:'#000000',
					success: function(res) {
						if(res.confirm) {  
							that.confirmDel();	
						}   								 
					},
				})
			},
			async confirmDel(){
				const that  = this;
				try{
					let res = await this.api.del('',{id:this.one.id});
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 发布协议
			showOne(){
				let that = this;
				uni.showModal({
					title: '协议发布',
					// 提示文字
					content: '是否发布该用户协议？',
					// 取消按钮的文字自定义
					cancelText: "取消",
					// 确认按钮的文字自定义
					confirmText: "确认",
					//删除字体的颜色
					confirmColor:'red',
					//取消字体的颜色
					cancelColor:'#000000',
					success: function(res) {
						if(res.confirm) {  
							that.confirmShow();	
						}   								 
					},
				})
			},
			async confirmShow(){
				const that  = this;
				try{
					let res = await this.api.put('',{id:this.one.id});
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
		}
	}
</script>

<style>
	.main{
		margin: 20px 20px 0;
	}
	.top {
		display: flex;
		align-items: center;
		height: 100rpx;
	}
	.topImg{
		width: 52rpx;
		height: 52rpx;
		margin-right: 10rpx;
	}
	.title {
		margin: 20rpx 0;
		width: 100%;
		font-size: 32rpx;
		color: #be0000;
		text-shadow: 5rpx 5rpx 2rpx #24c3be;
	}
	.content{
		border: #24c3be 2rpx;
	}
	.bt{
		margin-top: 25rpx;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
	.up{
		width: 125rpx;
		height: 60rpx;
		font-size: 30rpx;
		color: #be0000;
		text-shadow: 5rpx 5rpx 2rpx #24c3be;
		text-align: center;
		line-height: 60rpx;
		font-weight: 600;
		background-color: white;
		background-color: antiquewhite;
	}
	.down{
		width: 125rpx;
		height: 60rpx;
		font-size: 30rpx;
		color: gray;
		text-shadow: 5rpx 5rpx 2rpx #24c3be;
		text-align: center;
		line-height: 60rpx;
		font-weight: 600;
		background-color: white;
		background-color: antiquewhite;
	}
</style>
