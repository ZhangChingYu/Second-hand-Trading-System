<template>
	<view class="home">
		<!-- 搜索框 -->
		<view class="top-search" @click="toSearch">
			<text></text>
			<input placeholder="寻找宝贝"/>
		</view>
		<!-- 顶部轮播图 -->
		<view class="home-swiper">
			<u-swiper
				:list="swiperItem"
				indicator
				indicatorMode="line"
				circular
				height="350rpx"
			></u-swiper>
		</view>
		
		<!-- 主页分类 -->
		<view class="type-home">
			<text @click="toCatalog('B')"><text>书籍</text></text>
			<text @click="toCatalog('D')"><text>数码</text></text>
			<text @click="toCatalog('M')"><text>美妆</text></text>
			<text @click="toCatalog('F')"><text>日用</text></text>
		</view>
		
		<!-- 预约物品 -->
		<view class="most-book" v-if="mostbookItem.length > 0">
			<view class="title">
				
				<text class="title-main">
					<text class="title-bg main-icon"></text>
					<text>最多预约</text>
				</text>
				
				<text class="more">
					<text>更多</text>
					<text class="more-bg main-icon"></text>
				</text>
			</view>
			<view class="book-detail">
				<Goods  class="goods" :goods="mostbookItem[0]"></Goods>
				<Goods v-if="mostbookItem.length > 1" class="goods" :goods="mostbookItem[1]"></Goods>
				<!-- <Goods class="goods"
					v-for="(item,index) of mostbookItem" 
					:key="index"
					:goods="item"
				 >
				 </Goods> -->
				
			</view>
		</view>
		
		<!-- 最新物品 -->
		<view class="new-product" v-if="newProductItem.length != 0">
			<view class="title">		
				<text class="title-main">
					<text class="new-bg main-icon"></text>
					<text>最新物品</text>
				</text>
				
			</view>
			
			<!-- 子项 -->
			<view class="new-detail">
				<Goods class="goods"
					v-for="(item,index) of newProductItem" 
					:key="index"
					:goods="item"
				 >
				 </Goods>
				 
			</view>
		</view>
		
		<!-- 推荐物品 -->
		<view class="recommend-product" v-if="recommendProductItem.length != 0">
			<view class="title">		
				<text class="title-main">
					<text class="recommend-bg main-icon"></text>
					<text>推荐物品</text>
				</text>
				
			</view>
			
			<!-- 子项 -->
			<view class="recommend-detail">
				<Goods class="goods"
					v-for="(item,index) of recommendProductItem" 
					:key="index"
					:goods="item"
				 >
				 </Goods>
				 
			</view>
		</view>
		
		<!-- 底部 -->
		<view class="footer">
			<Nomore notips="没有更多了.."></Nomore>
		</view>
	</view>
</template>

<script>
	import Goods from "@/components/goods/index.vue"
	import Nomore from "@/components/nomore/index.vue"
	export default {
		components:{Goods,Nomore},
	    data() {
	        return {
	            swiperItem: [
					'https://cdn.uviewui.com/uview/swiper/swiper3.png',
					'https://cdn.uviewui.com/uview/swiper/swiper2.png',
					'https://cdn.uviewui.com/uview/swiper/swiper1.png',
				],
				  
	            mostbookItem:[],
				newProductItem:[],
				recommendProductItem:[],
				footerMsg:'-----没有更多数据了-----'
	        }
	    },
		// 下拉刷新
		onPullDownRefresh(){
			this.getMostbookItem();
			this.getNewProduct();
			this.getRecommendProduct();
		},
		onShow() {
			this.getMostbookItem();
			this.getNewProduct();
			this.getRecommendProduct();
			
		},
	    methods: {
			toCatalog(type){
				uni.setStorageSync('choosedCatalog',type);
				uni.switchTab({
					url: "/pages/classify/index"
				});
			},
			
	        toSearch(){
				uni.navigateTo({
					url:'/pages/search/index'
				})
			},
			async getNewProduct(){
				const that  = this;
				try{
					let res = await this.api.get('/homepage/new/products')
					that.newProductItem = res;
					
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}	
			},
			async getRecommendProduct(){
				const that  = this;
				try{
					let res = await this.api.get('/homepage/promote/products')
					that.recommendProductItem = res;
					
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}	
			},
		
			async getMostbookItem(){
				const that  = this;
				try{
					let res = await this.api.get('/homepage/like/products')
					that.mostbookItem = res;
					console.log(that.mostbookItem)
					
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
				
				
			}
	    }
	}
</script>

<style scoped>	
	/* 顶部搜索 */
	
	.top-search {
		display: flex;
		align-items: center;
		margin: 20rpx auto 16rpx auto;
		width: 90%;
		height: 64rpx;
		background-color: #ebebeb;
		border-radius: 30rpx;
	}
	.top-search>text {
		width: 50rpx;
		height: 50rpx;
		margin: 0 20rpx;
		line-height: 64rpx;
		background-image: url('../../static/image/search.png');
		background-size: 100%;
		
	}
	.top-search >input {
		flex:0.88;
		height: 100%;
		font-size: 28rpx;
	}
	
	/* 轮播图 */
	/* .home-swiper {
		width: 96%;
	} */
	
	
	/* 分类 */
	.type-home {
		width: 95%;
		height: 4.56rem;
		margin: 0 auto;
		padding-top: 0.48rem;
		display: flex;
		justify-content: space-around;
		position: relative;
		background-color: #fcf2ff;
	}
	.type-home > text {
		position: relative;
		width: 3.26rem;
		height: 3.26rem;
		background-size: 100%;
		background-repeat: no-repeat;
		text-align: center;
	}
	.type-home > text:nth-child(1){
		background-image: url('../../static/image/book.png');	
	}
	.type-home > text:nth-child(2){
		background-image: url('../../static/image/camera.png');
	}
	
	.type-home > text:nth-child(3){
		background-image: url('../../static/image/meizhuang.png');
	}
	.type-home > text:nth-child(4){
		background-image: url('../../static/image/paper.png');
	}
	
	.type-home > text text {
		position: absolute;
		width: 100%;
		top: 100%;
		transform: translateX(-50%);
	}
	
	
	/* 预约 */
	.most-book {
		width: 95vw;
		height: 400rpx;
		margin: 10rpx auto;
		background-color: #efefef;
	}
	.most-book .title{
		width: 100%;
		height: 50rpx;
		position: relative;
		color:#ffffff;
		background-color: #b34c26;
		border-radius: 0.48rem 0.48rem 0 0;
		
	}
	.most-book .title > text {
		height: 50rpx;
		line-height: 50rpx;
	}
	 .title > .title-main {
		float: left;
		margin-left: 70rpx;
		font-size: 30rpx;
		font-weight: 700;
		font-style: italic;
	} 
	
	.main-icon{
		width: 50rpx;
		height: 100%;
		position: absolute;
		display: inline-block;		
		background-size: 100%;
		background-repeat: no-repeat;
	}
	.title-bg {
		left: 20rpx;
		background-image: url('../../static/image/clock.png');
	}
	
	.more {
		float: right;
		margin-right: 70rpx;
		font-size: 26rpx;
	}
	.more-bg {
		right: 20rpx;
		background-image: url('../../static/image/more.png');
	} 
	
	/* 预定展示 */
	.book-detail {
		width: 100%;
		height: 350rpx;
		display: flex;
		justify-content: space-between;
	}
	.goods{
		width: 47.5%;
		height: 340rpx;
		margin-top: 10rpx;
	}
		
	/* 最新物品 */
	
	.new-product {
		width: 95vw;
		margin: 20rpx auto;
		background-color: #efefef;
		border-radius: 10rpx 10rpx 0 0;
	}
	.new-bg {
		left: 30rpx;
		background-image: url('../../static/image/gift.png');
	}
	
	.new-detail{
		width: 100%;
		margin:20rpx 0;
		display: flex;
		flex-wrap: wrap;
		justify-content: space-between;
	}
	
	/* 推荐物品 */
	
	.recommend-product {
		width: 95vw;
		margin: 20rpx auto;
		background-color: #efd1c3;
		border-radius: 10rpx 10rpx 0 0;
	}
	.recommend-bg {
		left: 30rpx;
		background-image: url('../../static/image/recommend.png');
	}
	
	.recommend-detail{
		width: 100%;
		margin:20rpx 0;
		display: flex;
		flex-wrap: wrap;
		justify-content: space-between;
	}
	
	
	
	
</style>
