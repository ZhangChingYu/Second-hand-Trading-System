<template>
	<view>
		<!-- 搜索框 -->
		<view class="top-search" @click="toSearch">
			<text></text>
			<input placeholder="寻找宝贝"/>
		</view>
		<!-- 顶部轮播图 -->
		<view class="uni-margin-wrap">
			<swiper 
				class="swiper" circular 
				indicator-dots="true" 
				indicator-color = 'rgb(255,255,255)'
				indicator-active-color="#FFFF80"
				autoplay="true" 
				interval="2000"
				duration="500">
				
				<swiper-item v-for="(item,index) of swiperItem" :key="index">
					<view class="swiper-item">
						<image :src="item" mode="widthFix"></image>
					</view>
				</swiper-item>
				
			</swiper>
		</view>
		
		<!-- 主页分类 -->
		<view class="type-home">
			<text><text>书籍</text></text>
			<text><text>数码</text></text>
			<text><text>零食</text></text>
			<text><text>日用</text></text>
		</view>
		
		<!-- 预约物品 -->
		<view class="most-book" v-if="mostbookItem.length != 0">
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
				
				<Goods v-if="mostbookItem.length > 0" class="goods" :goods="mostbookItem[0]"></Goods>
				<Goods v-if="mostbookItem.length > 1" class="goods" :goods="mostbookItem[1]"></Goods>
				
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
		
		<!-- 底部 -->
		<view class="footer">
			<text>{{footerMsg}}</text>
		</view>
	</view>
</template>

<script>
	import Goods from "@/components/goods/index.vue"
	export default {
		components:{Goods},
	    data() {
	        return {
	            swiperItem: [
					'https://t11.baidu.com/it/app=49&f=JPEG&fm=173&fmt=auto&u=766431721%2C238945871?w=640&h=512&s=3B7D76866CF7F4C20E9A567F03009078',
					'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Finews.gtimg.com%2Fnewsapp_bt%2F0%2F13950518577%2F1000&refer=http%3A%2F%2Finews.gtimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669533057&t=180b6b372cd71022722b3d8c3ccbc068',
					'https://img2.baidu.com/it/u=4293062902,2076102987&fm=253&fmt=auto&app=138&f=JPG?w=750&h=500'
				],
				  
	            mostbookItem:[],
				newProductItem:[],
				footerMsg:'-----没有更多数据了-----'
	        }
	    },
		created() {
			this.getMostbookItem();
			this.getNewProduct();
		},
	    methods: {
	        toSearch(){
				uni.redirectTo({
					url:'/pages/search/index'
				})
			},
			async getNewProduct(){
				const that  = this;
				try{
					let res = await this.api.get('/homepage/new/products')
					that.mostbookItem = res;
					that.mostbookItem.forEach(item=>{
						item.coverPic = "data:image/jpg;base64," + item.coverPic;
					})
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
				
			},
			async getMostbookItem(){
				const that  = this;
				try{
					let res = await this.api.get('/homepage/like/products')
					that.newProductItem = res;
					that.newProductItem.forEach(item=>{
						item.coverPic = "data:image/jpg;base64," + item.coverPic;
					})
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
		margin: 0.48rem auto;
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
	
	
	/* 轮播图 */
	.uni-margin-wrap {
		position: relative;
		width: 100%;
		margin: 1rem auto;
	}
	.swiper {

		height: 300rpx;
	}
	.swiper-item  {
		line-height: 300rpx;
		text-align: center;
	}
	
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
		background-image: url('../../static/image/earphone.png');
	}
	
	.type-home > text:nth-child(3){
		background-image: url('../../static/image/chicken.png');
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
		height: 11.68rem;
		margin: 0.48rem auto;
		background-color: #efefef;
	}
	.most-book .title{
		width: 100%;
		height: 2rem;
		position: relative;
		color:#ffffff;
		background-color: #b34c26;
		border-radius: 0.48rem 0.48rem 0 0;
		
	}
	.most-book .title > text {
		height: 1.8rem;
		line-height: 1.8rem;
	}
	 .title > .title-main {
		float: left;
		margin-left: 3rem;
		font-size: 1.2rem;
		font-weight: 700;
		font-style: italic;
	} 
	
	.main-icon{
		width: 1.8rem;
		height: 100%;
		position: absolute;
		display: inline-block;		
		background-size: 100%;
		background-repeat: no-repeat;
	}
	.title-bg {
		left: 1rem;
		background-image: url('../../static/image/clock.png');
	}
	
	.more {
		float: right;
		margin-right: 3rem;
		font-size: 0.88rem;
	}
	.more-bg {
		right: 1rem;
		background-image: url('../../static/image/more.png');
	} 
	
	/* 预定展示 */
	.book-detail {
		width: 100%;
		height: 7rem;
		display: flex;
		justify-content: space-between;
	}
	.goods{
		width: 47.5%;
		height: 8.8rem;
		margin-top: 0.68rem;
	}
		
	/* 最新物品 */
	
	.new-product {
		width: 95vw;
		margin: 0.48rem auto;
		background-color: #efefef;
		border-radius: 0.5rem 0.5rem 0 0;
	}
	.new-bg {
		left: 1rem;
		background-image: url('../../static/image/gift.png');
	}
	
	.new-detail{
		width: 100%;
		margin:0.5rem 0;
		display: flex;
		flex-wrap: wrap;
		justify-content: space-between;
	}
	
	.footer {
		margin: 2px auto;
		text-align: center;
		color: #cacaca;
	}
	
	
</style>
