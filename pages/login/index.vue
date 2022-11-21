<template>
	<view class="login">
		
		<h1 class="title">{{title}}</h1>
		
		<view class="main">
			<view class="usename">
				<input type="text" v-model="forms.usename"  @blur="checkInput" @focus="err.usename = false" auto-focus placeholder="手机号/邮箱">
				<text v-if="err.usename">×</text>
			</view> 
			<view class="password">
				<input type="password" v-model="forms.password" @blur="checkPsd" @focus="err.password = false" placeholder="密码">
				<text v-if="err.password">×</text>
			</view>
			
			<button @click="login">登录</button>
			<view class="btn">
				<text @click="register" style="color: red;">注册</text>
				<text>|</text>
				<text @click="forgetPsd" class=".gray-color">忘记密码</text>
			</view>
		</view>
		
		<view class="other">
			<text class=".gray-color">——————  其他方式登录  ——————</text>
			<view class="other-login">
				<image @click="WXLogin" src="../../static/image/weixin.png"></image>
				<image @click="QQLogin" src="../../static/image/QQ.png"></image>
			</view>
		</view>
	</view>
</template>


<script>
	import {mapActions} from 'vuex'
	export default {
		data(){
			return{
				title:'欢迎来到闲置虫虫',
				forms:{
					usename:'1234567890@qq.com',
					password:'lKpJ5CnBry',
				},
				// 判断是邮箱还是手机号/微信号
				userType:'',			
				// 记录账号或密码填写是否符合要求
				err:{
					password:false,
					usename:false
				}
			}
		},
		methods:{
			
			...mapActions(['tologin']),
			
			logining(){
				uni.showLoading({
					title: '正在登录...'
				});
			},
			loginHide(){
				uni.hideLoading();
			},
		
			
			// 检查用户名
			checkInput(){
				
				// 验证手机号
				let r = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
				if(r.test(this.forms.usename))
				 {
					 this.userType = 'tel';
					 return true;
				 }
				
				// 验证邮箱
				r = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
				if(r.test(this.forms.usename)){
					this.userType = 'email';
					return true;
				}
				else{
					this.userType = '';
					this.err.usename = true;
					return false;
				}
			},
			// 检查密码
			checkPsd(){
				let r = /^\w{8,16}$/;
				if(!r.test(this.forms.password)) this.err.password = true;
			},
			
			// 登录/注册/忘记密码
			login(){
				let that = this;
					// 判断账号密码是否填写合法
				if(!this.err.password && !this.err.usename){
					this.logining();
					
										
					// 我自己封装的api
					that.api.post('/login',that.forms).then(res=>{
						that.getlogin(res);
					}).catch(err=>{
						this.loginHide();
						that.$toast(err);
					})
					
					
				}else {
					if(this.err.password) this.$toast('密码8-16位数字或字母');
					else this.$toast('填写不符合要求')
				}
				
				
				
			},
			register(){
				this.$toast('注册');
			},
			forgetPsd(){
				this.$toast('忘记密码');
			},
			
			getlogin(res){
				console.log(res);
				this.loginHide();
				
				if(res.code === "666"){
					let user = res.user;
					let token = res.token;
				
				// 在store中存登录信息
				this.tologin(user,token);
				
				// 在本地存储登录信息
				
				uni.setStorage({
					key:'user',data:JSON.stringify(user)
				})
				uni.setStorage({
					key:'token',data:token
				})
				
				this.$toast('登录成功！',1270);
				// 跳转主页
				setTimeout(()=>{
					uni.redirectTo({
						url: `/pages/home/index?user=${user}&token=${token}`
					});
				},500);
				
				}
				else  this.$toast(res.msg,1270);
				
				
			},
			
						
			// 第三方登录
			WXLogin(){
				// this.notImplMsg();
				// 获取code 小程序专有，用户登录凭证。
				let raw_Data = '';
				let code = '';
				let that = this;
				uni.getUserProfile({
					desc: "获取用户基本资料",
					success : (res)=>{
						
						console.log(res)
						raw_Data = res.rawData;
						
						//获取成功基本资料后开启登录，基本资料首先要授权
						
						that.logining();
						uni.login({
						  provider: 'weixin',
						  success: function (loginRes) {
						    console.log(loginRes);
							
							if(loginRes.errMsg === "login:ok"){
								code = loginRes.code;
								
								// 发送登录请求
								
								// 用我自己封装的api
								that.api.get('/wxlogin',{code})
								.then(res=>{
									that.getlogin(res);
								}).catch(err=>{
									that.$toast(err);
								})
							}
						  }
						});
					},
					 // 用户取消登录后的提示
					fail: (res)=>{
						uni.showModal({
							title:"授权用户信息失败！",
							// 是否显示取消按钮，默认为 true
							showCancel:false
						});
					 }
					});
					
			},
			QQLogin(){
				this.$toast('功能开发中');
			}
			
		},
}
</script>

<style scoped>
	 .login{
		 width: 100vw;
		 height: 100vh;
		 display: flex;
		 
		 flex-direction: column;
		 
		 justify-content: space-around;
		 align-items: center;
	 }
	
	.title {
		font-size: 2rem;
		color: orangered;
		font-weight: 650;
	}
	
	.main {
		display: flex;
		flex-direction: column;
		align-items: center;
		width: 80%;
		height: 16.48rem;
		padding: 1rem;
		
	}
	.main .usename,.main .password {
		width: 90%;
		position: relative;
		
	}
	.main .usename > text,.main .password > text {
		position: absolute;
		top:1.24rem;
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
		margin: 0.5rem;
		font-family: 'Courier New';
		font-size: 1rem;
		padding-left:0.68rem;
	}
	.main > button {
		width: 85%;
		height: 2.48rem;
		background-color: orangered;
		border-radius: 0.5rem;
		margin: 0.5rem;
		color: aliceblue;
		line-height: 2.48rem;
		font-weight: 600;
	}
	.main > .btn text {
		margin: 0.86rem 0.48rem;
		font-size: 0.92rem;
	}
	
	.main > text {
		color: red;
	}
	
/* 第三方登陆方式	 */
	.other {
		width: 20rem;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: space-around;
		padding: 1rem;
		position: relative;
		height: 8.6rem;
		align-items: center;	
		}
	.other > text {
		/* width:80% ; */
	}
	
	.other-login > image {
		
		width: 2.48rem;
		height: 2.48rem;
		border-radius: 50%;
		margin: 2rem;
		border: 1px solid lightblue;
	}
	
	.gray-color {
		color: gray;
	}
	
	
	
</style>
