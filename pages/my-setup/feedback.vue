<template>
	<view class="feedback-content">
		<view class="title">请告诉我们您的宝贵意见</view>
		<textarea v-model="content" class="content"/>
		<button class="commitbtn" @click="commit">提交</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				content:''
			}
		},
		methods: {
			commit(){
				let that=this;
				let phone=uni.getStorageSync('user').phone;
				let content=this.content;
				that.api.post('/setting/feedback',{phone,content}).then(res=>{
					console.log(res);
				})
				uni.navigateBack({
					delta:1
				})
			}
		}
	}
</script>

<style>
	.feedback-content{
		display: flex;
		flex-direction: column;
		margin-top: 5%;
		align-items: center;
	}
	.title{
		justify-content: center;
		font-size: medium;
		margin-bottom: 5%;
		}
	.content{
		font-size: medium;
		width: 90%;
		height: 400rpx;
		margin: 0 8%;
		background-color: #ebe3dc;
		border: #3d3c3b;
		padding: 2% 3%;
	}
	
	.commitbtn{
		background-color: #d04b41;
		width: 30%;
		justify-content: center;
		margin-top: 5%;
		color: white;
	}
</style>
