<template>
	<view class="content">
		<view class="realname">
			<view class="title">真实姓名</view>
			<view class="text">
				<input class="text" @blur="realnameInput" placeholder="请输入真实姓名" placeholder-style="color:#999999"/>
			</view>
		</view>
		<view class="realname">
			<view class="title">身份证号</view>
			<view class="text">
				<input class="text" @blur="realnameInput" placeholder="请输入身份证号" placeholder-style="color:#999999"/>
			</view>
		</view>
		
		<button type="primary" class="commitbtn" @blur="commit">提交</button>
	</view>
</template>

<script>
	export default{
		data(){
			return{
				
			}
		},
		methods:{
			
		}
	}
</script>

<style scoped>
	.content{
		font-size: medium;
		margin-top: 80rpx;
	}
	.realname{
		display: flex;
		padding: 40rpx 40rpx;
		border-bottom: 2rpx solid #cccccc;
	}
	.commitbtn{
		background-color: #d04b41;
		width: 30%;
		justify-content: center;
		margin-top: 30%;
	}
	.text{
		padding-left: 20rpx;
	}
</style>