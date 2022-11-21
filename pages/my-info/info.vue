<template>
	<view class="info-content">
		<view class="info-content-list">
			<view class="info-content-item" style="border-bottom: 3rpx solid black; font-weight: 600;">
				<view>个人信息管理</view>
			</view>
			<view class="info-content-item">
				<view>头像</view>
				<image src="../../static/image/my_icon/header.png" style="width: 100rpx; height: 100rpx;"></image>
			</view>
			<view class="info-content-item">
				<view>用户名</view>
				<view>></view>
			</view>
			<view class="info-content-item">
				<view>真实姓名：王兰花</view>
			</view>
			<view class="info-content-item">
				<view>违规次数：0</view>
			</view>
			<view class="info-content-item">
				<view>修改密码</view>
				<view>></view>
			</view>
			<view class="info-content-item">
				<view>学号</view>
				<view>></view>
			</view>
			<view class="info-content-item">
				<view>绑定手机号</view>
				<view>></view>
			</view>
			<view class="info-content-item">
				<view>绑定第三方账号</view>
				<view>></view>
			</view>
		</view>
	</view>
</template>

<script>
</script>

<style scoped>
	.info-content{
		font-size: medium;
		margin: 30rpx 0;
		padding: 0 30rpx;
		
	}
	.info-content-item{
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 30rpx 0;
		border-bottom: 1px solid #cccccc;
	}
</style>