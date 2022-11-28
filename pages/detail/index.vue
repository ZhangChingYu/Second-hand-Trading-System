<template>
	<view class="detail" v-if="product">
		<!-- <view class="top-exit" @click="back1">{{exit}}</view> -->
		<view class="top-message">
			<view class="saller">
				<image class="avatar" :src="src"></image>
				<text class="saller-name">{{product.seller_name}}</text>
			</view>
			<view class="saller-price">
				<text>{{priceType}}</text>
				<text v-if="product.price" class="sprice">￥{{product.price.toFixed(2)}}</text>
			</view>
			
			<view class="publish-message">
				<view v-if="product.date" class="publish-time">{{product.date}}发布</view>
				
				<view class="publish-address">
					<text class="iconfont">&#xe8ff;</text>
					<text>{{product.address}}</text>
					</view>
			</view>
			
			
		</view>
		
		<!-- 商品图片和文字描述 -->
		<view class="goods-message">
			<h2 class="goods-title">{{product.name}}</h2>
			<view class="goods-describe">{{product.intro}}</view>
			<view class="uni-margin-wrap goods-pic">
				<swiper 
					class="swiper" circular 
					indicator-dots="true" 
					indicator-color = 'rgb(255,255,255)'
					indicator-active-color="#FFFF80"
					autoplay="true" 
					interval="2000"
					duration="500">
					
					<swiper-item v-for="(item,index) of product.pictures" :key="index">
						<view class="swiper-item">
							<image :src="'data:image/jpg;base64,' + item" mode="aspectFit"></image>
						</view>
					</swiper-item>
					
				</swiper>
			</view>
			
		</view>
		
		<view class="goods-otherMsg">
			<view class="report" @click="report">
				{{reportText}}
			</view>
			<view >收藏：{{product.like_count}}</view>
			<view >留言：1</view>
			<view >浏览：42</view>
		</view>
		<view class="evaluation">
			<h3>留言 · 1</h3>
			<view class="item">
				<view class="item-msg">
					<image :src="src1"></image>
					<text class="name">花开富贵</text>
					<text class="item-time">2分钟前</text>
				</view>
				<view class="item-content">虎溪校区可以面交吗</view>
			</view>
		</view>
		
		<view class="footer-operator">
			<view class="like" @click="like" v-if="!isLike">
				<text class="iconfont">&#xe643;</text>
				<text >收藏</text>
			</view>
			<view class="notlike" @click="like" v-else>
				<text class="iconfont hasLiake">&#xe625;</text>
				<text >取消收藏</text>
			</view>
			<view class="contact" @click="contact">
				<text class="iconfont">&#xe66f;</text>
				<text>联系卖家</text>
			</view>
			<view class="book-goods" @click="book">预约商品</view>
			
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
					number:'',
					product:{},
					user:{},
					isLike:false,
					
					priceType:'一口价',
					reportText:'举报',
					exit:"返回",
					
					src:'https://gw.alicdn.com/bao/uploaded/i1/409278028/O1CN01FvxEsF29AsLyowUSq_!!409278028.jpg_300x300q90.jpg_.webp',
					src1:'https://t10.baidu.com/it/u=957786636,1203753998&fm=58'
				}
		},
		onLoad: function (option) { 
			this.number = option.number
		},
		mounted() {
			this.user = uni.getStorageSync('user');
			this.getMessage();
			
		},
		methods: {
			back1(){
				uni.navigateBack({
						delta:1,//返回层数
					})
			},
			async getMessage(){
				const that = this;			
				try{
					this.product = await this.api.get('/product/detail',{'number':this.number});
					this.isLike = await this.api.get('/like',{number:this.number,phone:this.user.phone})
				}catch(e){
					//TODO handle the exception
					this.$toast(e)
				}
			},
			// 举报
			report(){
				this.$toast('举报');
			},
			// 收藏
			async like(){
				let that = this;
				
				this.isLike = !this.isLike;
				try{
					let res = await this.api.post('/like',{number:this.number,phone:this.user.phone});
					let title = '';
					switch(res){
						case 201:
							title = '收藏成功！';
							break;
							
						case 204:
							title = '取消收藏成功！';
							break;
							
						case 403:
							title = '用户无权限！';
							this.isLike = !this.isLike;
							break;
							
						case 404:
							title = '收藏失败！';
							this.isLike = !this.isLike;
							break;
					}
					that.$toast(title);
				}catch(e){
					//TODO handle the exception
					that.$toast(e);
				}
			},
			
			// 联系卖家
			contact(){
				this.$toast('联系卖家');
			},
			
			// 预约商品
			book(){
				this.$toast('预约商品');
			}
			
		},
	}
</script>

<style scoped>
	@font-face {
	  font-family: 'iconfont';
	  src:	url('@/static/iconfont.woff2?t=1669107535257') format('woff2'),
			url('@/static/iconfont.woff?t=1669107535257') format('woff'),
	        url('@/static/iconfont.ttf?t=1669107535257') format('truetype');
	}
	
	.iconfont {
		margin: 0 0.38rem;
	    font-family: "iconfont" !important;
	    font-style: normal;
	    -webkit-font-smoothing: antialiased;
	    -moz-osx-font-smoothing: grayscale;
	}
	
	.detail {
		min-height: 100vh;
	}
	.detail .top-exit {
		position: absolute;
		top: 0;
		left: 0;
		width: 3em;
		height: 1.5em;
		font-size: 1rem;
		background-color: #c13535;
		color: #fff;
		border-radius: 0 0 1.5em 0;
	}
	/* 顶部基本信息 */
	.top-message {
		
		height: 9rem;
	}
	.top-message .saller {
		margin-left: 3rem;
		display: flex;
		align-items: center;
		height: 6rem;
		width: 60%;
	}
	.saller .avatar {
		width: 4.5rem;
		height: 4.5rem;
		border-radius: 50%;
	}
	.saller .saller-name {
		margin-left: 1rem;
		display: inline-block;
		font-size: 1rem;
		font-weight: 600;
		line-height: 6rem;
	}
	
	/* 价格 */
	.saller-price {
		position: absolute;
		top: 1rem;
		right: 1rem;
		display: flex;
		flex-direction: column;
		justify-content: space-around;
		align-items: center;
		font-size: 1.36rem;
		
	}
	.saller-price .sprice {
		margin: 0.48rem auto;
		color: red;
		
	}
	
	
	/* 发布时间地点 */
	.publish-message {
		margin:0 1rem;
		height: 3rem;
		/* width: 60%; */
		line-height: 3rem;
		font-size: 0.88rem;
		color: #b9b9b9;
		
	}
	.publish-message .publish-time{
		float: left;
		margin: 0 1rem;
	}
	.publish-message .publish-address{
		float: left;
	}
	
	/* 商品描述 */
	.goods-message {
		margin: 0 auto;
		display: flex;
		flex-direction: column;
		width: 95%;
		/* height: 35rem; */
		overflow: hidden;
		background-color: #eeeeee;
		border-radius: 1rem 1rem 0 0;
	}
	
	/* 商品名称 */
	.goods-message .goods-title {
		margin: 0.88rem;
		/* height: 3rem; */
		max-height: 4.5rem;
		font-size: 1rem;
		font-weight: 600;
		line-height: 1.5rem;
	}
	.goods-message .goods-describe {
		margin: 0 0.48rem 0.68rem;
		max-height: 2.5rem;
		text-indent: 2em;
		line-height: 1.25rem;
		overflow: hidden;
	}
	.goods-message .goods-pic {
		text-align: center;
		flex: 1;
	}
	
	/* 商品图片 */
	
	.swiper {
		height: 60vw;
	}
	
	.goods-pic .swiper-item image {
		width: 90%;
		height: 60vw;
		
	}
	
	/* 其他信息 */
	.goods-otherMsg {
		margin-top: 1rem;
		height: 1.5rem;
		line-height: 1.5rem;
	}
	.goods-otherMsg view:not(.report) {
		float: right;
		margin: 0 1rem;
		font-size: 0.8rem;
		color: #9d9d9d;
	}
	/* 举报 */
	.report {
		/* position: fixed; */
		float: left;
		margin-left: 2rem;
		width: 4em;
		border-radius: 0.5em; 
		background-color: #d0cecf;
		color: #fff;
		text-align: center;
	}
	
	/* 留言 */
	.evaluation {
		/* text-align: center; */
		margin-bottom: 3rem;
	}
	.evaluation h3 {
		margin: 1rem 0;
		padding-left: 1rem;
		font-size: 1rem;
		
		border-top:2px solid #9d9d9d ;
		border-bottom:2px solid #e7e7e7 ;
	}
	.evaluation .item {
		margin: 0.48rem auto;
		height: 2.5rem;
		width: 95%;
	}
	.item-msg{
		overflow: hidden;
		font-size: 1rem;
	}
	.item-msg image {
		float: left;
		width: 2.5rem;
		height: 2.5rem;
		border-radius: 50%;
	}
	.item-msg .name {
		float: left;
		margin: 0 1rem;
		display: inline-block;
		height: 2.5rem;
		line-height: 2.5rem;
		font-weight: 600;
		
	}
	.item-msg .item-time {
		float: right;
		margin-right: 1rem;
		line-height: 2.5rem;
		color: #959595;
	}
	
	/* 留言内容 */
	.item-content {
		margin: 0.3rem 1rem 0.4rem 1.5rem;
		font-size: 0.88rem;
		text-indent: 2rem;
	}
	
	/* 底部操作部分 */
	.footer-operator {
		position: sticky;
		display: flex;
		flex-wrap: wrap;
		justify-content: space-around;
		align-items: center;
		padding: 0.25rem 0;
		bottom: 0;
		/* left: 0; */
		width: 100%;
		height: 3rem;
		font-size: 1.1rem;
		line-height: 2.5rem;
		background-color: #fff;
	}
	.footer-operator .iconfont{
		font-size: 1.2rem;
	}
	.footer-operator .like,.footer-operator .notlike {
		width: 7rem;
		background-color: #fff;
	}
	.hasLiake {
		color: red;
	},
	.footer-operator .contact {
		width: 7rem;
		background-color: #fff;
	}
	.footer-operator .book-goods {
		width: 10rem;
		background-color: #c13535;
		border-radius: 0.5rem;
		color: #fff;
		text-align: center;
	}
	
	
	
	
	
	
</style>
