<template>
	<view class="my-first-page">
		<view class="my-top">
			<view style="width: 30%; ">
				<image class="my-header" v-if="avatar" :src="avatar" @click="userDetail(phone)"></image>
				<image class="my-header" v-else :src="'data:image/jpg;base64,' + user.avatar"
					@click="userDetail(phone)"></image>
			</view>
			<view>
				<view class="my-text">
					<view name="user-name" style="font-weight: 900; font-size: 200%; " v-if="user.userName.length>10">
						{{user.userName.substr(0,10)}}...
					</view>
					<view name="user-name" style="font-weight: 900; font-size: 200%; " v-else>{{user.userName}}</view>
					<view name="user-info" style=" padding-top: 30rpx;padding-bottom: 10rpx;" @click="info">个人信息></view>
				</view>
				<view class="hr"></view>
				<view class="my-text" style="padding-top: 20rpx;">买入：{{buy}}&nbsp;&nbsp;卖出：{{sell}}</view>
			</view>
		</view>
		<view class='icons'>
			<view class="icons-item">
				<image class="icons-img" @click="report" src="../../static/image/my_icon/jubao.png" mode=""></image>
				<text class='icons-name'>举报进度</text>
			</view>
			<view class="icons-item" @click="setup">
				<view v-show="unreadCount!=0" class="unread">{{unreadCount}}</view>
				<image class="icons-img" src="../../static/image/my_icon/shezhi.png" mode=""></image>
				<text class='icons-name'>设置</text>
			</view>
			<view class="icons-item">
				<image class="icons-img" @click="pre" src="../../static/image/my_icon/qianbao.png" mode=""></image>
				<text class='icons-name'>我的预约</text>
			</view>
			<view class="icons-item">
				<image class="icons-img" @click="exchange" src="../../static/image/my_icon/zuanshi.png" mode=""></image>
				<text class='icons-name'>我的订单</text>
			</view>
			<!-- 分割线-->
			<view class="hr"></view>
			<view class="icons-item" @click="collect">
				<image class="icons-img" src="../../static/image/my_icon/shoucang.png" mode=""></image>
				<text class='icons-name'>收藏</text>
			</view>
			<view class="icons-item">
				<image class="icons-img" @click="products" src="../../static/image/my_icon/zuanshi_o.png" mode="">
				</image>
				<text class='icons-name'>我的商品</text>
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
					userName: "",
					avatar: ""
				},
				//前端存储用
				avatar: '',
				buy: '',
				sell: '',
				phone: '', //待删除
				unreadCount: '',
			}
		},

		onLoad() {
			let res = uni.getStorageSync('user');
			console.log(123);
			this.user.userName = res.userName;
			this.user.avatar = res.avatar;
			this.phone = res.phone;
			let that = this;
			let phone = res.phone;
			that.api.get('/manage/user', {
				phone
			}).then(res => {
				that.buy = res.data.buy;
				that.sell = res.data.sell;
				console.log(data);
				console.log(123456789)
			})
			this.getUnread();
		},

		methods: {
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
				let phone = this.phone;
				this.api.get('/setting/authentication', {
					phone
				}).then(res => {
					console.log(res)
					if (res == 202) {
						this.$toast('您的实名认证请求正在审核中，请耐心等待')
					} else if (res == 201) {
						this.$toast('您已通过实名认证，无需再次认证')
					} else if (res == 203) {
						wx.navigateTo({
							url: "../my-realname/realname"
						});
					}
				})
			},
			address() {
				wx.navigateTo({
					url: "../my-address/address"
				});
			},
			//我的商品
			products() {
				wx.navigateTo({
					url: '/pages/myItem/index'
				});
			},
			//预约
			pre() {
				wx.navigateTo({
					url: '/pages/myAppointment/index'
				});
			},
			//举报
			report() {
				wx.navigateTo({
					url: '/pages/report/index'
				});
			},
			//订单
			exchange() {
				wx.navigateTo({
					url: '/pages/purchased/index'
				});
			},
			info() {
				wx.navigateTo({
					url: "../my-info/info"
				});
			},
			userDetail(phone) {
				console.log('phone:', phone)
				wx.navigateTo({
					url: "../userInfo/userInfo?phone=" + phone
				})
			},
			getUnread() {
				let phone=this.phone;
				this.api.get('/setting/unread/count', {
					phone
				}).then(res => {
					this.unreadCount = res;
				})
			}
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
		margin-top: 100rpx;
		margin-left: 60rpx;
		margin-right: 20rpx;
		width: 150rpx;
		height: 150rpx;
		border-radius: 50%
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

	.unread {
		border-radius: 50%;
		background-color: red;
		color: white;
		height: 40rpx;
		width: 40rpx;
		text-align: center;
		position: fixed;
		left: 42%;
		top: 29%;
	}
</style>
