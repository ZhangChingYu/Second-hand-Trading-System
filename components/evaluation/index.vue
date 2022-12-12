<template>
	<view class="item">
		<view class="item-msg">
			<image :src="imageSrcformat(evaluation.headPic)"></image>
			<text class="name">{{evaluation.userName}}</text>
			<text class="item-time">{{evaluation.date}}</text>
		</view>
		<view class="item-content">{{evaluation.content}}</view>
		<view class="item-reply">
			<Evareply 
				v-for="(item,index) of evaluation.subComments"
				:key="item.id"
				:evaluation="item"
			></Evareply>
		</view>
	</view>
</template>

<script>
	import Evareply from '@/components/evaluationReply/index.vue'
	import {mixin} from '../../mixin.js'
	export default {
		components:{Evareply},
		props:['evaluation'],
		name:"evaluation",
		mixins:[mixin],
		data() {
			return {
				
			};
		},
		methods:{
			// 时间格式化
			
		}
	}
</script>

<style lang="less" scoped>
 .item {
		margin: 20rpx auto;
		width: 95%;
		
	}
	.item-msg{
		overflow: hidden;
		font-size: 30rpx;
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