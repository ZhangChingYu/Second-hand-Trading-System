<template>
	<view class="my-first-page">
		<view class="my-top">
			<view style="width: 30%; ">
				<image class="my-header" src="../../static/image/my_icon/header.png"></image>
			</view>
			<view>
				<view class="my-text">
					<view name="user-name" style="font-weight: 900; font-size: 200%; " v-if="user.userName.length>10">{{user.userName.substr(0,10)}}...</view>
					<view v-else>{{user.userName}}</view>
					<view name="user-info" style=" padding-top: 30rpx;padding-bottom: 10rpx;"
						@click="info">个人信息></view>
				</view>
				<view class="hr"></view>
				<view class="my-text" style="padding-top: 20rpx;">已交易：24</view>
			</view>
		</view>
		<view class='icons'>
			<view class="icons-item">
				<image class="icons-img" src="../../static/image/my_icon/jubao.png" mode=""></image>
				<text class='icons-name'>举报</text>
			</view>
			<view class="icons-item" @click="setup">
				<image class="icons-img" src="../../static/image/my_icon/shezhi.png" mode=""></image>
				<text class='icons-name'>设置</text>
			</view>
			<view class="icons-item">
				<image class="icons-img" src="../../static/image/my_icon/qianbao.png" mode=""></image>
				<text class='icons-name'>钱包</text>
			</view>
			<view class="icons-item">
				<image class="icons-img" src="../../static/image/my_icon/zuanshi.png" mode=""></image>
				<text class='icons-name'>已购买</text>
			</view>
			<!-- 分割线-->
			<view class="hr"></view>
			<view class="icons-item" @click="collect">
				<image class="icons-img" src="../../static/image/my_icon/shoucang.png" mode=""></image>
				<text class='icons-name'>收藏</text>
			</view>
			<view class="icons-item">
				<image class="icons-img" src="../../static/image/my_icon/zuanshi_o.png" mode=""></image>
				<text class='icons-name'>已售出</text>
			</view>
			<view class="icons-item" @click="address">
				<image class="icons-img" src="../../static/image/my_icon/daohangdizhi.png" mode=""></image>
				<text class='icons-name'>收货地址</text>
			</view>
			<view class="icons-item" @click="realname">
				<image class="icons-img" src="../../static/image/my_icon/shimingrenzheng.png" mode=""></image>
				<text class='icons-name'>实名认证</text>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		ref
	} from 'vue'
	import {
		mapActions
	} from 'vuex'
	export default {
		data() {
			return {
				user: {
					userName: ""
				}
			}
		},

		onLoad() {
			let res = uni.getStorageSync('user');
			console.log(123);
			this.user.userName = res.userName;

		},

		methods: {
			get(res) {

			},
			collect() {
				wx.navigateTo({
					url: "../my-like/like"
				});
			},
			setup() {
				wx.navigateTo({
					url: "../my-setup/setup"
				});
			},
			realname() {
				wx.navigateTo({
					url: "../my-realname/realname"
				});
			},
			address() {
				wx.navigateTo({
					url: "../my-address/address"
				});
			},
			info() {
				wx.navigateTo({
					url: "../my-info/info"
				});
			},
		}
	}
</script>

<style scoped>
	.my-text {
		color: white;
		padding-top: 70rpx;
		padding-left: 20rpx;
		justify-content: space-between;
		font-size: 150%;
	}

	.my-top {
		background-color: #d04b41;
		height: 300rpx;
		display: flex;
	}

	.my-header {
		padding-top: 100rpx;
		padding-left: 100rpx;
		width: 100rpx;
		height: 100rpx;
	}

	.icons {
		display: flex;
		flex-wrap: wrap;
		padding-left: 5%;
		padding-right: 5%;
	}

	.icons-item {
		width: 25%;
		display: flex;
		flex-direction: column; //垂直
		align-items: center;
		justify-content: center;
		padding-top: 20rpx;
	}

	.icons-img {
		width: 110rpx;
		height: 110rpx;
	}

	.icons-name {
		padding-top: 10rpx;
		padding-bottom: 10rpx;
	}

	.hr {
		border: 2rpx solid #a2a2a2;
		width: 90%;
		margin: 0 auto;
		opacity: 0.3;
	}
</style>
