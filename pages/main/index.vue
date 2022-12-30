<template>
	<view class="home">
		<view class="top">
			<text class="welcome">管理员：{{user.userName}}({{user.realName}})</text>
			<text class="logout" @click="logout">退出登陆</text>
		</view>
		<view class="title">
			<text>闲置虫虫二手交易服务端主页</text>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user:{}
			}
		},
		created() {
			this.user = uni.getStorageSync('user');
		},
		methods:{
			logout(){
				uni.showModal({
					title: '提示',
					content: '是否退出登陆？',
					success(res) {
						if(res.confirm){
							uni.clearStorage();
							uni.redirectTo({
								url:'/pages/login/login'
							})
						}
					}
				})
			}
		}
	}
</script>

<style scoped lang="less">
	 .home {
		 min-height: 100vh;
	 }
	 .top {
		 display: flex;
		 justify-content: space-between;
		 align-items: center;
		 padding: 0 20rpx;
		 height: 80rpx;
		 font-size: 26rpx;
		 background-color: #b63220;
		 color: azure;
		 
	 }
	 
	 .title {
		 width: 14em;
		 font-size: 40rpx;
		 color: #b63220;
		 margin: 100rpx auto;
		 font-weight: 600;
		 text-shadow: 5rpx 5rpx 3rpx rgba(0, 0,0 ,.3);
	 }
</style>
