<template>
	<view class="item" v-if="goods" @click="toGoodsDetail">
		
		<view class="pro-name">
			<text>{{goods.name}}</text>
		</view>
		
		<view class="img">
			<image :src="'data:image/jpg;base64,' + goods.coverPic"></image>
			
		</view>
		<view class="footer-price" >
			<!-- <image :src='src'></image>
			<view>
				<text class="name">天天</text>
				<text class="time">20分钟前发布</text>
			</view> -->
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
				src:'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.dtstatic.com%2Fuploads%2Fblog%2F202108%2F22%2F20210822070705_cfd6b.thumb.1000_0.jpg&refer=http%3A%2F%2Fc-ssl.dtstatic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1671932677&t=7cc3fc9cc0a43f50910a48460238adfb'
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
		margin-bottom: 20px;
		box-shadow: 0 9px 10px 1px rgba(0, 0, 0, .2);
		
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
		justify-content: flex-start;
		flex-wrap: wrap;
		align-items: center;
		& image {
			margin: 0 10rpx;
			width: 160rpx;
			height: 160rpx;
			
			background-color: #22d3c8;
		}
	}
	.item .pro-name {
		height: 72rpx;
		margin-left: 1em;
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