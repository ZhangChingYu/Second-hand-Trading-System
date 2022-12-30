<template>
	<view class="item" v-if="goods" @click="toGoodsDetail">
		
		<view class="pro-name">
			<text>{{goods.name}}</text>
		</view>
		
		<view class="img">
			<image lazy-load="true" :src="'data:image/jpg;base64,' + goods.coverPic"></image>
			
		</view>
		<view class="footer-price" >
			<text class="price">￥{{goods.price.toFixed(2)}}</text>
		</view>
	</view>
</template>

<script>
	export default {
		name:"classitem",
		props:['goods'],
		data() {
			return {
			};
		},
		methods:{
			toGoodsDetail(){
				const that = this;
				uni.navigateTo({
					url:`/pages/detail/index?number=${that.goods.number}`
				})
			},
		}
	}
</script>

<style scoped lang="less">

    .item {
		height: 360rpx;
		// margin-bottom: 10px;
		// box-shadow: 0 9px 10px 1px rgba(0, 0, 0, .2);
		border-radius: 20rpx;
	}
	 .item .footer-price {
		height: 108rpx;
		// background-color: #d0b4ff;
		display: flex;
		justify-content: space-around;
		align-items: center;
		& image {
			width: 100rpx;
			height: 100rpx;
			border-radius: 50%;
			background-color: #22d3c8;
		}
		& view {
			display: flex;
			flex-direction: column;
			justify-content: space-around;
			
			.name {
				font-size: 30rpx;
				font-weight: 600;
			}
			.time {
				font-size: 24rpx;
				color: #a5a5a5;
			}
		}
		& .price {
			font-size: 36rpx;
			color: red;
			font-weight: 600;
		}
	}
	
	.item .img {
		height: 180rpx;
		// background-color: bisque;
		display: flex;
		justify-content: center;
		flex-wrap: wrap;
		align-items: center;
		& image {
			margin: 0 10rpx;
			width: 160rpx;
			height: 160rpx;
		}
	}
	.item .pro-name {
		height: 72rpx;
		margin-left: 1em;
		text-align: center;
		// background-color: aliceblue;
		& text {
			display: block;
			line-height: 72rpx;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
		}
	}
</style>