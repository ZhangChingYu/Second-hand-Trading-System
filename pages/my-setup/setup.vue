<template>
	<view class='setup-content'>
		<view class="setup-content-list">
			<view class="setup-content-item" @click="logout">
				<view>退出登录</view>
				<view>></view>
			</view>
			<view class="setup-content-item" @click="message">
				<view>系统消息</view>
				<view style="display: flex; flex-wrap: wrap;">
					<view v-show="unreadCount!=0" class="unread">{{unreadCount}}</view>
					>
				</view>
			</view>
			<view class="setup-content-item" @click="help">
				<view>帮助</view>
				<view>></view>
			</view>
			<view class="setup-content-item" @click="feedback">
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
				unreadCount: ''
			}
		},
		mounted() {
			this.getUnread()
		},
		methods: {
			logout() {
				uni.removeStorageSync('user');
				uni.redirectTo({
					url: '/pages/login/index'
				})
			},
			feedback() {
				uni.navigateTo({
					url: '/pages/my-setup/feedback'
				})
			},
			help() {
				uni.navigateTo({
					url: '/pages/my-setup/help'
				})
			},
			message() {
				uni.navigateTo({
					url: '/pages/notifications/notifications'
				})
			},
			getUnread() {
				let phone = uni.getStorageSync('user').phone;
				this.api.get('/setting/unread/count', {
					phone
				}).then(res => {
					this.unreadCount = res;
				})
			},
		},
	}
</script>

<style scoped>
	.setup-content {
		font-size: medium;
		margin: 30rpx 0;
		padding: 0 20rpx;

	}

	.setup-content-item {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 30rpx 0;
		border-bottom: 2px solid #cccccc;
	}

	.unread {
		margin-right: 5rpx;
		border-radius: 50%;
		background-color: red;
		color: white;
		height: 40rpx;
		width: 40rpx;
		text-align: center;
	}
</style>
