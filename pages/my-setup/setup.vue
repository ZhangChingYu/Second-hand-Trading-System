<template>
	<view class='setup-content'>
		<view class="setup-content-list">
			<view class="setup-content-item" @click="logout">
				<view>退出登录</view>
				<view>></view>
			</view>
			<view class="setup-content-item">
				<view>消息提醒</view>
				<view>></view>
			</view>
			<view class="setup-content-item">
				<view>帮助</view>
				<view>></view>
			</view>
			<view class="setup-content-item">
				<view>体验反馈</view>
				<view>></view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		
		},
		methods: {
			logout(){
				uni.removeStorageSync('user');
				uni.redirectTo({
					url:'/pages/login/index'
				})	
			}
		},
		}
</script>

<style scoped>
	.setup-content{
		font-size: medium;
		margin: 30rpx 0;
		padding: 0 20rpx;
		
	}
	.setup-content-item{
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 30rpx 0;
		border-bottom: 2px solid #cccccc;
	}
</style>