<template>
	<view>
		
		<!-- 搜索框 -->
		<view class="top-search" @click="toSomeOf">
			<text></text>
			<input placeholder="搜索预约"/>
		</view>
		
		<!-- 我的预约 -->
		<view class="mybook">
			<!-- 已预约物品 -->
			<view class="onebook"
			v-for="(item,index) of mybookItem"
			:key="index"
			>
				<view class="goods-box" @click="toGoodsDetail">
					    <!-- 图片 -->
						<view class="goods-img">
							<image :src="item.coverPic" mode="widthFix" @error="doDefault"></image>
						</view>						
						<!-- 信息 -->
						<view class="goods-msg">
							<text class="detail-text">{{item.name}}<span></span></text>
							<text class="price">￥{{item.price.toFixed(2)}}</text>
						</view>
				</view>
				<view class="book-state" @click="toOper(item.state)">{{item.state}}</view>	
			</view>
		</view>		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 预约状态state（已预约、待下单）
				mybookItem:[
					{
						number:"B3267559776586",
						name:'参加培训班',
						coverPic:'https://gw.alicdn.com/bao/uploaded///asearch.alicdn.com/bao/uploaded/O1CN015rH4tD2LKkJrMhIlx_!!0-item_pic.jpg_300x300q90.jpg_.webp',
						price:1250,
						state:'已预约',
					},
					{
						number:"B1637559776586",
						name:'使图片的宽高完全拉伸至填满 image 元素',
						coverPic:'https://gw.alicdn.com/bao/uploaded/i1/510160174/O1CN01gGdwFj1D9jhVnZgEo_!!0-saturn_solar.jpg_300x300q90.jpg_.webp',
						price:268,
						state:'待下单',
					}
				],
			}
		},
		methods: {
			// 商品详情页
			toGoodsDetail(){
				uni.redirectTo({
					url:'/pages/detail/index'
				})
			},
			
			// 预约状态相关操作
			toOper(state){
				// 已预约状态查看商品详情
				if(state=="已预约"){
					uni.redirectTo({
						url:'/pages/detail/index'
					})
				}
				// 待下单状态到达下单界面
				else{
					uni.redirectTo({
						url:'/pages/payment/index'
					})
				}
			},
		}
	}
</script>

<style>

	/* 顶部搜索 */
	.top-search {
		display: flex;
		align-items: center;
		margin: 1rem auto;
		width: 90%;
		height: 2rem;
		background-color: #ebebeb;
		border-radius: 3rem;
	}
	.top-search>text {
		width: 1.48rem;
		height: 1.48rem;
		margin: 0 0.68rem;
		line-height: 2rem;
		background-image: url('../../static/image/search.png');
		background-size: 1.48rem 1.48rem;
		
	}
	.top-search >input {
		flex:0.88;
		height: 100%;
		font-size: 1rem;
	}
	
	/* 我的预约列表 */
	.mybook {
		width: 95vw;
		margin: 0.48rem auto;
	}
	
	.onebook {
		width: 100%;
		height: 230rpx;
		display: flex;
		justify-content: space-between;
		margin-bottom: 1.5rem;
	}
	.goods-box{
		width: 75%;
		height: 100%;
		border-radius: 15rpx 0 0 15rpx;
		background-color: #efefef;
		padding: 2%;
		flex-flow: row;
		justify-content: space-between;
		display: flex;
		position: relative;
	}
	.book-state{
		background-color: #efefef;
		width: 16%;
		height: 230rpx;
		padding: 2%;
		line-height: 7rem;
		text-align: center;
		border-radius: 0 9% 9% 0;
		color: orange;
		font-weight: 700;
		font-size: 1rem;
	}
	
	
	/* 商品图片 */
	.goods-img {
		height: 100%;
		width: 50%;
		overflow: hidden;
		border-radius: 20rpx;
	}
	.goods-img image {
		width: 100%;
		height: 100%;
	 }
	 .goods-msg {
		 width: 45%;
	 }
	
	/* 商品文字描述 */
	
	.detail-text{
		padding-top: 10rpx;
		height: 120rpx;
		font-size: 28rpx;
		line-height: 60rpx;
		padding-left: 5%;
		
		word-break: break-all;
		overflow: hidden;
		-webkit-line-clamp: 2;
		text-overflow: ellipsis;
		display: -webkit-box;
		-webkit-box-orient: vertical;
	}
	/* 价格 */
	.price{
		color: red;
		font-weight: 700;
		height: 2rem;
		line-height: 2rem;
		font-size: 1rem;
		position: absolute;
		bottom: 5%;
		right: 5%;
	}
	
	
	
</style>
