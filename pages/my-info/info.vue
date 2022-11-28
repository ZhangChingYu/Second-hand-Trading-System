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
				<view>用户名：</view>
				<input type="text" value="" placeholder="请输入用户名" v-model="userUpdate.userName"/>
			</view>
			<view class="info-content-item">
				<view>真实姓名：</view>
				<view style="color: grey;">{{userUpdate.realName}}&nbsp;&nbsp;&nbsp;</view>
			</view>
			<view class="info-content-item">
				<view>违规次数：</view>
				<view style="color: grey;">{{userUpdate.violationCount}}&nbsp;&nbsp;&nbsp;</view>
			</view>
			<view class="info-content-item">
				<view>修改密码</view>
				<view>&nbsp;&nbsp;&nbsp;></view>
			</view>
			<view class="info-content-item">
				<view>学号：</view>
				<input type="text" value="" placeholder="请输入学号" v-model="userUpdate.stuNum"/>
			</view>
			<view class="info-content-item">
				<view>绑定手机号：</view>
				<input type="text" value="" placeholder="请输入手机号" v-model="userUpdate.phone"/>
			</view>
			<view class="info-content-item">
				<view>绑定邮箱：</view>
				<input type="text" value="" placeholder="请输入邮箱" v-model="userUpdate.email"/>
			</view>
			<view class="info-content-item">
				<view>绑定第三方账号</view>
				<view>&nbsp;&nbsp;&nbsp;></view>
			</view>
			<button class="commitbtn" @click="commit">确认修改</button>
		</view>
	</view>
</template>

<script>
	export default{
		data(){
			return{
				//暂存修改信息
				userUpdate:{
					violationCount:'',
					userName:'',
					realName:'',
					email:'',
					stuNum:'',
					phone:''
				},
				//用于storage信息更新
				user:{}
			}
		},
		mounted(){
			let res=uni.getStorageSync('user');
			this.user=res;
			this.userUpdate=res;
		},
		methods:{
			commit(){
				let that=this;
				that.api.post('/personal/edit',that.user).then(res=>{
					console.log(res);
				})
				this.user=this.userUpdate;
				uni.setStorage({
					key:'user',data:this.user,
				})
				this.$toast('更新成功!');
				
				uni.redirectTo({
					url:'/pages/my/index'
				})
			}
		}
	}
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
	.commitbtn{
		background-color: #d04b41;
		width: 30%;
		justify-content: center;
		margin-top: 5%;
		color: white;
	}
</style>