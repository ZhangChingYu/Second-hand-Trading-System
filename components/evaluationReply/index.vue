<template>
	<view class="eva-reply">
		<view class="item-msg">
			<image :src="imageSrcformat(evaluation.headPic)"></image>
			<text class="name">
				{{evaluation.userName}}
				<text style="color: burlywood;">回复</text>
				{{evaluation.fatherName}}
			</text>
			<text class="item-time">{{evaluation.date}}</text>
		</view>
		<view class="item-content">{{evaluation.content}}</view>
		<view class="item-reply">
			
		</view>
	</view>
</template>

<script>
	import {mixin} from '../../mixin.js'
	export default {
		name:"evaluationReply",
		props:['evaluation'],
		mixins:[mixin],
		data() {
			return {
				
			};
		},
		computed:{
			nameMsg(){
				return this.evaluation.userName + ' 回复 ' + this.evaluation.fatherName;
			}
		}
	}
</script>

<style lang="less" scoped>
 .eva-reply {
		margin-left: 50rpx;
		background-color: #eaeaea;
		border-radius: 10rpx;
		
	}
	.item-msg{
		overflow: hidden;
		font-size: 24rpx;
	}
	.item-msg image {
		float: left;
		width: 80rpx;
		height: 80rpx;
		border-radius: 50%;
	}
	.item-msg .name {
		float: left;
		margin: 0 20rpx;
		display: inline-block;
		height: 60rpx;
		line-height: 60rpx;
		font-weight: 600;
		
	}
	.item-msg .item-time {
		float: right;
		margin-right: 20rpx;
		line-height: 60rpx;
		font-size: 24rpx;
		color: #959595;
	}
	
	/* 留言内容 */
	.item-content {
		margin: 10rpx 20rpx 10rpx 30rpx;
		font-size: 24rpx;
		text-indent: 2rem;
	}
</style>