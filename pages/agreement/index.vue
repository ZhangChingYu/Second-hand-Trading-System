<template>
	<view class="main">
		<view class="top">
			<view class="title">用户协议</view>		
		</view>
		<view class="content">
			<rich-text>{{agreement}}</rich-text>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				agreement:'',
			}
		},
		mounted() {
			this.getDetail();
		},
		methods: {
			// 获取协议详情
			async getDetail(){
				const that  = this;
				try{
					that.agreement = await this.api.get('');
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
		margin: 20px 20px 0 20rpx;
	}
	.top {
		width: 100%;
		text-align: center;
	}
	.title {
		margin: 20rpx 0;
		width: 100%;
		font-size: 32rpx;
		color: black;
	}
	.content{
		margin-top: 25rpx;
	}
</style>
