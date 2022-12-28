<template>
	<view class="content">
		<view @tap="back">
			返回 </view>
				<view class="username">
					<view class="title">手机号：</view>
					<view class="userid">
						<input type="text" @blur="usernameInput" maxlength="11" placeholder="请输入电话号码"
							placeholder-style="color:#999999" />
					</view>
				</view>
				<!-- 分割线-->
				<view class="hr"></view>
				<!--新密码-->
				<view class="username">
					<view class="title">新密码：</view>
					<view class="userid">
						<input type="text" @blur="passwordInput" placeholder="请输入新密码"
							placeholder-style="color:#999999" />
					</view>
				</view>
				<!-- 分割线-->
				<view class="hr"></view>
				<!--验证码-->
				<view class="username" style="display: flex;flex-direction: row;">
					<view class="title">验证码：</view>
					<view class="userid">
						<input type="text" @blur="captchaInput" placeholder="请输入验证码"
							placeholder-style="color:#999999" />
					</view>
					<button type="primary" class="getcaptchaBtn" :disabled="captchaBtnState" @click="sendcode"
						size="mini" style="background-color: rgb(236, 102, 102);">获取验证码</button>
				</view>
				<!-- 分割线-->
				<view class="hr"></view>
				<!--确认按钮-->
				<button type="primary" class="confirmBtn" :disabled="confirmBtnState" @click="confirm"
					style="background-color: rgb(236, 102, 102);">确认</button>
				<!--邮箱找回-->
				<view>
					<view>
						<navigator style="text-align: center;font-size: small;margin-top: 80rpx;" open-type="navigate"
							url="/pages/email/email">邮箱找回</navigator>
					</view>
				</view>
		</view>
</template>

<script>
	import {
		mapActions
	} from 'vuex'
	export default {
		data() {
			return {
				confirmBtnState: true,
				captchaBtnState: true,
				user: {
					phone: "",
					password: "",
					captcha: "",
					code: "",
				}
			}
		},
		methods: {
			...mapActions(['tologin']),
			usernameInput(e) {
				console.log(e);
				var val = e.detail.value;
				let r = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
				if (r.test(val)) {
					this.captchaBtnState = false;
					this.user.phone = val;
				}
			},
			passwordInput(e) {
				var val = e.detail.value;
				let r = /^\w{8,16}$/;
				if (r.test(val)) {
					this.user.password = val;
					if ((this.captchaBtnState == false) && (this.user.captcha != '')) {
						this.confirmBtnState = false
					}
				}
			},
			captchaInput(e) {
				var val = e.detail.value;
				if (val != '') {
					this.user.captcha = val;
					if ((this.user.password != "") && (this.user.captcha != "")) {
						this.confirmBtnState = false
					}
				} else {
					this.confirmBtnState = true
				}
			},
			back() {
				wx.navigateTo({
					url: '../login/index',
				})
			},
			confirm() {
				let that = this;
				that.api.post('/lost', this.user).then(res => {
					console.log(res);
					if (res.code === "666") {
						let user = res.user;
						let token = res.token;
						this.tologin(user, token);
						uni.setStorage({
							key: 'user',
							data: JSON.stringify(user)
						})
						uni.setStorage({
							key: 'token',
							data: token
						})
						wx.showToast({
							title: '验证通过，密码已更新',
							duration: 2000,
							success: function() {
								wx.navigateTo({
									url: '../login/index',
								})
							}
						})
					} else {
						wx.showToast({
							title: res.msg,
							duration: 2000
						})
					}
				})
			},
			sendcode() {
				let that = this;
				let phone = this.user.phone;
				console.log(phone);
				that.api.get('/captcha', {
					phone
				}).then(res => {
					//获取验证码，短信发送
					console.log(res);
					that.$toast(res.msg);
					this.user.captcha = res.code;
				})
			}
		}
	}
</script>

<style>
	.content {
		margin-top: 80rpx;
		/*相对像素*/
	}
	.username {
		display: flex;
		flex-direction: row;
		padding-left: 40rpx;
		padding-top: 40rpx;
		padding-bottom: 20rpx;
		width: 100%;
	}
	.title {
		width: 150rpx;
		font-weight: hold;
	}
	.hr {
		border: 2rpx solid #cccccc;
		width: 90%;
		margin: 0 auto;
		opacity: 0.3;
	}
	.getcaptchaBtn {
		margin-right: 50rpx;
		height: 90%;
	}
	.confirmBtn {
		margin-top: 300rpx;
	}
</style>