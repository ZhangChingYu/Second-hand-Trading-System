<template>
	<view class="register">
		
		<h1 class="title">{{title}}</h1>
		
		<view class="main">
			
			<view class="myId">
				<text class="label">昵称</text>
				<input type="text" v-model="forms.userName" value="forms.userName" placeholder="例如 : 白白 ( 可选填 ) " @blur="checkName" @focus="err.name = false"/>
			</view>
			<text class=".gray-color">——————————————————————</text>
			
			<view class="country">
				<text class="label">国家/地区</text>
				<input placeholder="中国大陆 ( + 86 ) " disabled="true"/>
			</view>
			<text class=".gray-color">——————————————————————</text>
			
			<view class="username">
				<text class="label">手机号</text>
				<input type="text" placeholder="请输入您的手机号 ( 必填 ) " v-model="forms.phone" value="forms.phone" @blur="checkPhone" @focus="err.username = false" auto-focus/>
			</view>
			<text class=".gray-color">——————————————————————</text>
			
			<view class="mailbox">
				<text class="label">邮箱</text>
				<input type="text" placeholder="请输入您的邮箱 ( 可选填 )" v-model="forms.email" value="forms.email" @blur="checkEmail" @focus="err.email = false"/>
			</view>
			<text class=".gray-color">——————————————————————</text>
			
			<view class="password">
				<text class="label">密码</text>
				<input type="password" placeholder="请输入您的密码 ( 8 - 16 位) " v-model="forms.password" value="forms.password" @blur="checkPsd" @focus="err.password = false" auto-focus/>
			</view>
			<text class=".gray-color">——————————————————————</text>
			
		</view>	
		
		<view class="agreement">
			<label class="radio"><checkbox-group @change="handleChange"><checkbox :checked="agree" style="transform:scale(0.8)" id="box"/>我已阅读并同意<span @tap.stop="toAgreement">{{agreement}}</span></checkbox-group></label>
		</view>
		
		<button class="confirm" @click="MessSubmit" :style="{background:bgcolor, color:color}">同意并继续</button>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title:'欢迎注册闲置虫虫',
				agree:false,
				agreement:'《软件许可及服务协议》',
				forms:{
					userName:'',
					phone:'',
					email:'',
					password:'',
				},
				// 记录账号、邮箱或密码填写是否符合要求
				err:{
					password:false,
					email:false,
					userName:false,
					phone:false,
				},
				bgcolor:'gainsboro',
				color: 'gray',
			}
		},
		methods: {
			
			toLogin(){
				uni.redirectTo({
					url:'/pages/login/index'
				})
			},
			
			//检查昵称(不能包含空格，且长度限制)
			checkName(){
				let r = /^\S*$/;
				if(!r.test(this.forms.userName)) {
					return true;
				}
				else if(this.forms.userName == ""){
					this.err.userName = false;
					return true;
				}
				else {
					this.err.userName = true;
					this.$toast('昵称不能包含空格！');
					this.forms.userName = "";
					return false;
				}
			},
			
			// 检查手机号
			checkPhone(){
				let r = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
				if(r.test(this.forms.phone) ) {
					 return true;
				 }
				 else if(this.forms.phone == ""){
					 this.err.phone = true;
					 return false;
				 }
				 else{
				 	this.err.phone = true;
					this.$toast('手机号码格式输入错误！');
				 	return false;
				 }
			},
			
			// 检查邮箱
			checkEmail(){
				let r = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
				if(r.test(this.forms.email)){
					return true;
				}
				else if(this.forms.email == ""){
					this.err.email = false;
					return true;
				}
				else{
					this.err.email = true;
					this.$toast('邮箱格式输入错误！');
					return false;
				}
				
			},
			
			// 检查密码
			checkPsd(){
				let r = /^\w{8,16}$/;
				if(r.test(this.forms.password)) {
					return true;
				}
				else if(this.forms.password == ""){
					this.err.password = true;
					return false;
				}
				else {
					this.err.password = true;
					this.$toast('密码格式输入错误！8-16位字母、数字和下划线组合');
					return false;
				}
			},
			
			// 是否同意协议
			handleChange(e){
				this.agree = !this.agree;
				if(this.agree){
					this.bgcolor = "limegreen";
					this.color = "white";
				}
				else {
					this.bgcolor = "gainsboro";
					this.color = "gray";
				}
			},
		
		    // 相关协议
			toAgreement(){
				uni.navigateTo({
					url:'/pages/agreement/index'
				})
			},
			
			// 同意并注册
			MessSubmit(){
				let that = this;
				
				if(this.forms.phone == "" || this.forms.password == ""){
					this.$toast('请完善注册信息！');
				}
				else if(!this.agree){
					this.$toast('请勾选同意相关协议！');
				}
				
				if(this.agree && !this.err.userName && !this.err.phone && !this.err.email && !this.err.password){
					that.api.post('/user',that.forms).then(res=>{
						that.getRegister(res);
					}).catch(err=>{
						uni.hideToast();
						that.$toast(err);
					})	
				}
			},
			
			getRegister(res){
				console.log(res);
				uni.hideToast();
				
				if(res == 201){
				
					uni.showToast({
						title: '注册成功！',
						icon: 'success',
						duration: 30000
					})
					
					// 跳转登录页面
					setTimeout(()=>{
						uni.redirectTo({
							url:'/pages/login/index?username=' + this.forms.phone
						});
					},500);
				
				}
				else if(res == 422){
					this.$toast('该手机号已注册！');
				}
				else if(res == 421){
					this.$toast('昵称不可包含空格！');
				} 
				else this.$toast('注册失败，信息未成功添加！');
			},
		}
	}
</script>

<style scoped>
	.register{
		width: 100vw;
		height: 100vh;
		display: flex;
		flex-direction: column;
		justify-content: space-around;
		align-items: center;
	}
	
	.title{
		font-size: 2rem;
		color: orangered;
		font-weight: 650;
	}	
	.exit{
		font-size: 0.5rem;
		color: grey;
		height: 2rem;
		font-weight: 350;
		border: 0.2rem gray;
		background-color: gainsboro;
	}
	
	.main {
		display: flex;
		flex-direction: column;
		width: 90%;
		height: 16.48rem;
		padding: 1rem;
	}
	
	.main .myId,.main .country,.main .username,.main .mailbox,.main .password {
		height: 2.5rem;
		position: relative;
		width: 90vw;
		
	}
	
	.label {
		width: 10vw;
		height: 2.5rem;
		line-height: 2.5rem;
		text-align: center;
		font-family: 'Courier New';
		padding: 0 2vw;
	}
	
	.main input {
		width: 60vw;
		height: 2.2rem;
		font-family: 'Courier New';
		font-size: 0.8rem;
		text-align: left;
		padding: 0 0.5rem;
		float: right;
	}
	
	.gray-color {
		color: gainsboro;
		width: 90vw;
		text-align: center;
	}
	
	.radio span{
		color: royalblue;
	}
	
	button {
		width: 52%;
		height: 2.48rem;
		border-radius: 0.5rem;
		margin: 0.5rem;
		line-height: 2.48rem;
		font-size: 0.8rem;
		font-weight: 400;
		border: none;
	}

</style>
