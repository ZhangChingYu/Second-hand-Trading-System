<template>
	<view class="product-publish">
		<view class="product-image">
			添加照片
		</view>
		
		<view class="product-text">
			<view class="product-name">
				<label for="product-name">物品名称</label>
				<input id="product-name"/>
			</view>
			<textarea
			 class="product-detail-text uni-textarea" 
			 placeholder="详细描述你的宝贝吧~"
			 maxlength="100"
			 ></textarea>
		</view>
		
		<view class="product-message">
			<view class="publish-position">
				<text class="icon-weizhi iconfont"></text>
				<text> 重庆大学A区</text>
			</view>
			<!-- </view> -->
			<!-- 分类 -->
			<view class="product-catalog">
				<text>分类</text>
				<text>选择分类 </text>
			</view>
			<!-- 价格 -->
			<view class="product-catalog">
				<text>价格</text>
				<text>开个价</text>
			</view>
		</view>
		
	</view>
</template>

<script>
	import '../../static/iconfont.css'
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			
		}
	}
</script>

<style lang="less" scoped>
	.product-publish {
		min-height: 100vh;
		box-sizing: border-box;
		padding: 0 16rpx;
	}
	
	/* 图片 */
	.product-image {
		height: 240rpx;
		background-color: antiquewhite;
	}
	// 文字信息
	.product-text {
		height: 400rpx;
		background-color: aqua;
		overflow: hidden;
		
		// 名称
		& .product-name {
			display: flex;
			line-height: 64rpx;
			font-size: 36rpx;
			margin: 10rpx;
			& label {
				width: 160rpx;
				height: 64rpx;
				
				background-color: aliceblue;
			}
			& input {
				flex: 1;
				
				height: 64rpx;
				background-color: #fff;
			}
		}
		& .product-detail-text {
			margin: 0 auto;
			// padding: 10rpx;
			width: 95%;
			font-size: 30rpx;
			// text-indent: 2em;
			line-height: 44rpx;
			background-color: #fff;
		}
	}
	
	.product-message {
		height: 360rpx;
		border-top: 5rpx solid black;
		background-color: burlywood;
		
		& .publish-positon {
			background-color: #fff;
			line-height: 64px;
			font-size: 28rpx;
		}
	}

</style>
