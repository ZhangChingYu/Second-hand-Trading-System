<template>
	<view class="goods-box" @click="toGoodsDetail">
		<!-- 图片 -->
			<view class="goods-img">
				<image :src="cover" mode="widthFix" @error="doDefault"></image>
			</view>
			
			<!-- 信息 -->
			<view class="goods-msg">
				<text class="detail-text">{{goods.name}}</text>
				<text class="price">￥{{goods.price.toFixed(2)}}</text>
			</view>
	</view>
</template>

<script>
	export default {
		name:"goods",
		props:['goods'],
		data() {
			return {
				noCoverUrl:'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fkxmic.com%2FUploads%2Fproduct%2Fdefault%2Fprod_0316195442362.jpeg&refer=http%3A%2F%2Fkxmic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1671518632&t=e47ac78682011bcbacd5075d519a459a',
				cover:''
			};
		},
		created() {
			this.cover = this.goods.coverPic;
		},
		methods:{
			toGoodsDetail(){
				uni.redirectTo({
					url:'/pages/detail/index'
				})
			},
			doDefault(){
				this.cover = 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fkxmic.com%2FUploads%2Fproduct%2Fdefault%2Fprod_0316195442362.jpeg&refer=http%3A%2F%2Fkxmic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1671518632&t=e47ac78682011bcbacd5075d519a459a"';
			}
		}
	}
</script>

<style scoped>
	
/* 商品展示公共 */
	
	/* 商品信息边框 */
	.goods-box{
		width: 100%; 
		/* height: 6.8rem; */
		 height: 100%; 
		
		background-color: #fff;
		position: relative;
		border-right: 1px solid darkgray;
		border-bottom: 1px solid darkgray;
	}
	
	 /* 商品图片 */
	.goods-img {
		height: 80%;
		overflow: hidden;
	}
	.goods-img image {
		width: 100%;
	 }
	
	/* 商品文字描述 */
	.detail-text{
		display: inline-block;
		width: 70%;
		height: 2rem;
		font-size: 0.88rem;
		line-height: 2rem;
		padding-left: 2px;
		text-overflow:ellipsis;
		white-space: nowrap;
		overflow: hidden;
	}
	/* 价格 */
	.price{
		position: absolute;
		bottom: 0.24rem;
		right: 0.24rem;
		color: red;
		font-weight: 700;
		font-size: 1rem;
		background-color: #fff;
	}
</style>