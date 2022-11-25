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
				<view>{{user.userName}}  ></view>
			</view>
			<view class="info-content-item">
				<view>真实姓名：</view>
				<view>{{user.realName}}&nbsp;&nbsp;&nbsp;</view>
			</view>
			<view class="info-content-item">
				<view>违规次数：</view>
				<view>{{user.violationCount}}&nbsp;&nbsp;&nbsp;</view>
			</view>
			<view class="info-content-item">
				<view>修改密码</view>
				<view>&nbsp;&nbsp;&nbsp;></view>
			</view>
			<view class="info-content-item">
				<view>学号：</view>
				<view>{{user.stuNum}}&nbsp;&nbsp;&nbsp;></view>
			</view>
			<view class="info-content-item">
				<view>绑定手机号：</view>
				<view>{{user.phone}}&nbsp;&nbsp;&nbsp;></view>
			</view>
			<view class="info-content-item">
				<view>绑定邮箱：</view>
				<view>{{user.email}}&nbsp;&nbsp;&nbsp;></view>
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
				user:{
					violationCount:'',
					userName:'',
					realName:'',
					email:'',
					stuNum:'',
					phone:''
				}
			}
		},
		mounted(){
			let res=uni.getStorageSync('user');
			console.log(res);
			this.user=res;
		},
		methods:{
			commit(){
				
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