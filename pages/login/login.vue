<template>
	<view class="login">

		<h1 class="title">欢迎来到闲置虫虫<br />(服务端)</h1>

		<view class="main">
			<view class="username">
				<input type="text" v-model="forms.username" @blur="checkInput" @focus="err.username = false" auto-focus
					placeholder="管理员账号">
				<text v-if="err.username">×</text>
			</view>
			<view class="password">
				<input type="password" v-model="forms.password" @blur="checkPsd" @focus="err.password = false"
					placeholder="密码">
				<text v-if="err.password">×</text>
			</view>

			<button @click="login">登录</button>
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
				forms: {
					username: '15078818663',
					password: 'tony1234',
				},
				// 判断是邮箱还是手机号/微信号
				userType: '',
				// 记录账号或密码填写是否符合要求
				err: {
					password: false,
					username: false
				}
			}
		},
		methods: {

			...mapActions(['tologin']),

			logining() {
				uni.showLoading({
					title: '正在登录...'
				});
			},
			loginHide() {
				uni.hideLoading();
			},


			// 检查用户名
			checkInput() {
				return true;
			},
			// 检查密码
			checkPsd() {
				let r = /^\w{8,16}$/;
				if (!r.test(this.forms.password)) this.err.password = true;
			},

			// 登录/注册/忘记密码
			async login() {
				let that = this;
				// 判断账号密码是否填写合法
				if (!this.err.password && !this.err.username) {
					this.logining();

					try {
						let res = await that.api.post('/login', that.forms);
						that.getlogin(res);

					} catch (err) {
						//TODO handle the exception
						this.loginHide();
						that.$toast(err);
					}

				} else {
					if (this.err.password) this.$toast('密码8-16位数字或字母');
					else this.$toast('填写不符合要求')
				}

			},

			getlogin(res) {
				console.log(res);
				this.loginHide();
				if (res.code === "666") {
					let user = res.user;
					let token = res.token;

					// 在store中存登录信息
					//this.tologin(user, token);

					// 在本地存储登录信息

					uni.setStorage({
						key: 'user',
						data: user
					})
					uni.setStorage({
						key: 'token',
						data: token
					})
					this.$toast('登录成功！', 1270);
					
					// 跳转主页
					uni.navigateTo({
						url: '/pages/manage/index'
					})

				} else this.$toast(res.msg, 1270);


			},

		},
	}
</script>

<style scoped>
	.login {
		width: 100vw;
		height: 100vh;
		display: flex;
		margin-top: 50rpx;

		flex-direction: column;

		align-items: center;
	}

	.title {
		font-size: 2rem;
		color: orangered;
		font-weight: 650;
		text-align: center;
	}

	.main {
		margin-top: 300rpx;
		display: flex;
		flex-direction: column;
		align-items: center;
		width: 80%;
		padding: 60rpx;

	}

	.main .username,
	.main .password {
		width: 90%;
		position: relative;

	}

	.main .username>text,
	.main .password>text {
		position: absolute;
		top: 1.24rem;
		right: 0.16rem;
		color: red;
		font-size: 0.88rem;
		font-weight: 600;
	}

	.main input {
		width: 100%;
		height: 2.5rem;
		background-color: azure;
		border: 1px solid darkgray;
		border-radius: 0.2rem;
		margin-bottom: 20rpx;
		font-family: 'Courier New';
		font-size: 1rem;
		padding-left: 0.68rem;
	}

	.main>button {
		width: 85%;
		height: 2.48rem;
		background-color: orangered;
		border-radius: 0.5rem;
		margin: 0.5rem;
		color: aliceblue;
		line-height: 2.48rem;
		font-weight: 600;
	}

	.main>.btn text {
		margin: 0.86rem 0.48rem;
		font-size: 0.92rem;
	}

	.main>text {
		color: red;
	}
</style>
