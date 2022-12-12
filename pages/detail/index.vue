<template>
	<view class="detail" v-if="product">
		<view class="top-message">
			<view class="saller">
				<image class="avatar":src="product.seller_pic"></image>
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
			
			<view class="home-swiper">
				<u-swiper
					:list="product.pictures"
					indicator
					indicatorMode="line"
					circular
					height="750rpx"
				></u-swiper>
			</view>
			
		</view>
		
		<view class="goods-otherMsg">
			<view class="report" @click="report">
				{{reportText}}
			</view>
			<view >收藏：{{product.like_count}}</view>
			<view >留言：{{evaluationList.length}}</view>
		</view>
		<view class="evaluation">
			<h3>留言 · {{evaluationList.length}}</h3>
			<view class="add-evaluation">
				<view class="send-input">
					<u--input
					    placeholder="输入你的留言~"
					    border="surround"
					    v-model="evaluation"
						@confirm="sentEvaluation"
						confirm-type="send"
					  ></u--input>
				</view>
				<view class="send-btn" @click="sentEvaluation">
					 <u-button type="primary" text="留言"></u-button>
				</view>
				 
			</view>
			<view v-if="evaluationList.length !== 0">
				<Evaluation v-for="(item,index) of evaluationList" :key="item.id" :evaluation="item"></Evaluation>
			</view>
			<view v-else class="no-result">
				<u-empty></u-empty>
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
			<view v-if="!isBooked" class="book-goods" @click="showBook">预约商品</view>
			<view v-else class="book-goods" @click="cancelBook()">已预约</view>
			
		</view>
		<!-- 预约窗口 -->
		<u-popup :show="show" :round="10" mode="bottom" @close="close" @open="open">
				
				<view class="popup-content">
					<view class="book-count">
						<label for="book-input">数量：</label>
						<input type="number" v-model.number="bookNumber" id="book-input" :placeholder="'最大：'+ product.storage || 1" />
					</view>
					<view class="book-submit" @click="book">预约</view>
					
				</view>
		</u-popup>
		
	</view>
</template>

<script>
	import {mixin} from '../../mixin.js'
	import Evaluation from '@/components/evaluation/index.vue'
	export default {
		mixins:[mixin],
		components:{Evaluation},
		data() {
			return {
					number:'',
					product:{},
					evaluationList:[],
					user:{},
					isLike:false,
					
					priceType:'一口价',
					reportText:'举报',
					exit:"返回",
					
					src:'https://gw.alicdn.com/bao/uploaded/i1/409278028/O1CN01FvxEsF29AsLyowUSq_!!409278028.jpg_300x300q90.jpg_.webp',
					
					// 留言输入
					evaluation:'',
					
					// 商品库存
					storage:4,
					
					// 预约窗口
					show:false,
					// 是否已经预约
					isBooked:false,
					bookNumber:'',//预约数量
				}
		},
		onLoad: function (option) { 
			this.number = option.number
		},
		mounted() {
			this.user = uni.getStorageSync('user');
			this.getMessage();
			this.getEvaluation();
			console.log(1,this.evaluationList)
			
		},
		methods: {
			// 发布留言
			async sentEvaluation(){
				console.log(1232345)
				try{
					if(this.evaluation == ''){
						this.$toast('请输入内容！');
						return;
					}
					let res = this.api.post('/product/comment',{number:this.number,phone:this.user.phone,content:this.evaluation});
					let title = '';
					switch(res){
						case 201:
							title = '发布成功！'
							getEvaluation()
							break;
						case 400:
							title = '发布失败：无权限！'
							break;
						case 422:
							title = '发布失败：商品不存在！'
							break;
						case 404:
							title = '发布失败：数据库更新失败！'
							break;
					}
					this.$toast(title);
				}catch(e){
					//TODO handle the exception
				}
			},
			// 获取留言
			async getEvaluation(){
				try{
					this.evaluationList = await this.api.get('/product/comment',{number:this.number});
				}catch(e){
					//TODO handle the exception
					this.$toast(e)
				}
			},
			
			
			open(){
		        console.log('open');
		    },
		    close() {
		        this.show = false;
		        console.log('close');
		    },
			back1(){
				uni.navigateBack({
						delta:1,//返回层数
					})
			},
			async getMessage(){
				const that = this;			
				try{
					let p = await this.api.get('/product/detail',{'number':this.number});
					this.isLike = await this.api.get('/like',{number:this.number,phone:this.user.phone})
					
					p.seller_pic = this.imageSrcformat(p.seller_pic,'jpg')
					for(let i = 0;i<p.pictures.length;i++){
						p.pictures[i] = this.imageSrcformat(p.pictures[i],p.pictureFormats[i]);
					}
					this.product = p;
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
			showBook(){
				this.show = true;
			},
			
			book(){
				this.$toast('预约商品');
				this.close()
				
				//预约成功
				this.isBooked = true;
			},
			cancelBook(){
				this.$toast('取消预约');
				this.isBooked = false;
			},
			
			
			
		},
	}
</script>

<style lang="less" scoped>
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
	
	/* 顶部基本信息 */
	.top-message {
		
		overflow: hidden;
	}
	.top-message .saller {
		margin-left: 60rpx;
		display: flex;
		align-items: center;
		height: 180rpx;
		width: 60%;
	}
	.saller .avatar {
		width: 150rpx;
		height: 150rpx;
		border-radius: 50%;
	}
	.saller .saller-name {
		margin-left: 15rpx;
		display: inline-block;
		font-size: 30rpx;
		font-weight: 600;
		line-height: 150rpx;
	}
	
	/* 价格 */
	.saller-price {
		position: absolute;
		top: 30rpx;
		right: 50rpx;
		display: flex;
		flex-direction: column;
		justify-content: space-around;
		align-items: center;
		font-size: 40rpx;
		
	}
	.saller-price .sprice {
		margin: 15rpx auto;
		color: red;
		
	}
	
	/* 发布时间地点 */
	.publish-message {
		margin:0 40rpx;
		line-height: 60rpx;
		font-size: 24rpx;
		color: #b9b9b9;
		
	}
	.publish-message .publish-time{
		float: left;
		margin: 0 20rpx;
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
		overflow: hidden;
		background-color: #eeeeee;
		border-radius: 20rpx 20rpx 0 0;
	}
	
	/* 商品名称 */
	.goods-message .goods-title {
		margin: 20rpx;
		max-height: 100rpx;
		font-size: 36rpx;
		font-weight: 600;
		line-height: 1.5em;
		word-break: break-all;
		overflow: hidden;
		-webkit-line-clamp: 2;
		text-overflow: ellipsis;
		display: -webkit-box;
		-webkit-box-orient: vertical;
	}
	.goods-message .goods-describe {
		margin: 10rpx;
		max-height: 170rpx;
		text-indent: 2em;
		line-height: 1.5em;
		font-size: 24rpx;
		word-break: break-all;
		overflow: hidden;
		-webkit-line-clamp: 5;
		text-overflow: ellipsis;
		display: -webkit-box;
		-webkit-box-orient: vertical;
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
		margin-top: 20rpx;
		height: 50rpx;
		line-height: 50rpx;
	}
	.goods-otherMsg view:not(.report) {
		float: right;
		margin: 0 30rpx;
		font-size: 28rpx;
		color: #9d9d9d;
	}
	/* 举报 */
	.report {
		/* position: fixed; */
		float: left;
		margin-left: 50rpx;
		width: 80rpx;
		border-radius: 10rpx; 
		background-color: #d0cecf;
		color: #fff;
		font-size: 24rpx;
		text-align: center;
	}
	
	/* 留言 */
	.evaluation {
		margin-bottom: 120rpx;
	}
	.evaluation h3 {
		margin: 20rpx 0;
		padding-left: 40rpx;
		height: 50rpx;
		font-size: 30rpx;
		color: #fff;
		background:linear-gradient(to right, #c13535,#ffd5c0);
		border-top:2px solid #9d9d9d ;
	}
	.add-evaluation {
		display: flex;
		margin: 0 auto;
		width: 650rpx;
		& .send-input {
			width: 500rpx;
		}
		& .send-btn {
			width: 150rpx;
		}
	}
	
	
	
	/* 底部操作部分 */
	.footer-operator {
		position: sticky;
		display: flex;
		flex-wrap: wrap;
		justify-content: space-around;
		align-items: center;
		bottom: 0;
		width: 100%;
		height: 120rpx;
		font-size: 32rpx;
		line-height: 84rpx;
		background-color: #ffffff;
	}
	.footer-operator .iconfont{
		font-size: 1.2rem;
	}
	
	.hasLiake {
		color: red;
	}
	.footer-operator .like,
	.footer-operator .notlike,
	.footer-operator .contact {
		display: flex;
		justify-content: center;
		width: 200rpx;
	}
	
	.footer-operator .book-goods {
		width: 300rpx;
		background-color: #c13535;
		border-radius: 0.5rem;
		color: #fff;
		text-align: center;
	}
	
	/* 预约弹窗 */
	.popup-content {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		height: 50vw;
		font-size: 36rpx;
		
		line-height: 84rpx;
		
		
		& .book-count {
			width: 50%;
			height: 84rpx;
			display: flex;
			
			& label {
				display: inline-block;
				width: 3em;
			}
			& input {
				flex: 1;
				padding-left: 1em;
				box-sizing: border-box;
				height: 84rpx;
				border: 4rpx solid #d0cecf;
				border-radius: 10rpx;
			}
		}
		& .book-submit {
			margin-top: 50rpx;
			width: 50%;
			height: 84rpx;
			
			max-width: 200px;
			max-height: 50px;
			background-color: #c13535;
			color: #fff;
			border-radius: 15rpx;
			text-align: center;
		}
	}
	/* 没有留言 */
	.no-result {
	}
	
	
	
	
	
</style>
