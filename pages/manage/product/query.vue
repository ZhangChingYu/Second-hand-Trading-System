<template>
	<view class="review">
		<view class="top">
			<view class="review-title">商品列表</view>
			<view class="catalog" v-if="catalogList.length !== 0">
				<u-action-sheet :actions="catalogList" @select="getProductList" :show="show" @close="close" :closeOnClickOverlay="true" :closeOnClickAction="true" :title="title"></u-action-sheet>
				<u-icon name="grid" color="#2979ff" @click="show = true" :label="catalog.name" labelPos="bottom" size="50rpx"></u-icon>
			</view>
			<view class="reload">
				<u-icon name="reload" color="#2979ff" @click="reGet" label="刷新" labelPos="bottom" size="50rpx"></u-icon>
				
			</view>
		</view>
		
		<u-popup :show="showDetail" @close="close" closeable="true" round="10">
			<view class="detail" v-if="product !== ''">
				<view class="top-message">
					<view class="seller">
						<text class="innerTitle">发布者：</text>
						<text>{{product.sellerName}}</text>
					</view>
					<view class="basic-msg">
						<view class="num inner">
							<text><text class="innerTitle">编号：</text>{{product.number}}</text>
							<text><text class="innerTitle">类别：</text>{{product.catalog}}</text>
						</view>
						
						<view class="count inner">
							<text><text class="innerTitle">数量：</text>{{product.storage}}</text>
							<text><text class="innerTitle">状态：</text>{{proStatus}}</text>
							<text class="price">￥{{product.price.toFixed(2)}}</text>
						</view>
					</view>
					
					
					<view class="publish-message">
						<view v-if="product.date" class="publish-time">{{product.date}}发布</view>
						
						<view class="publish-address">
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
				
			</view>
		</u-popup>
		
		<u-swipe-action v-if="productList.length !== 0" >
			<u-swipe-action-item  v-for="(item,index) of productList" :show="showOperate" :key="index"  :index="index" :name="index" >
			  <view class="swipe-action u-border-top u-border-bottom" @click="getDetail(item.number)">
				  <!-- 内容 -->
				<view class="swipe-action__content">
	
					<view class="inner-content swipe-action__content__text">
						<view class="cover">
							<image :src="item.coverPic" lazy-load="true"></image>
						</view>
						<view class="content">
							<view ><text class="proTitle">编号：</text>{{item.number}}</view>
							<view ><text class="proTitle">名称：</text>{{item.name}}</view>
							<view ><text class="proTitle">价格：</text>￥{{item.price.toFixed(2)}}</view>
						</view>
					</view>
					
				  
				 
				</view>
				
			  </view>
			</u-swipe-action-item>
		</u-swipe-action>
		<!-- 无数据 -->
		
		<view  v-else-if="loading == false" class="empty">
			<u-empty></u-empty>
		</view>
		
		<view class="loading">
			<u-loading-icon  :show="loading"></u-loading-icon>
		</view>
		
		
	</view>
</template>

<script>
	import {mixin} from '../../../mixin.js'
	import {getAllProducts,getCatalogProducts,getCatalog,uploadDetail,submitUpload} from '@/common/request/api.js'
	export default {
		data() {
			return {
				title:'商品分类',
				catalog:{
					id:-1,
					name:'全部'
				},
				product:'',
				productList:[],
				catalogList:[],
				show:false,
				showDetail:false,
				loading:false,
				
			}
		},
		mixins:[mixin],
		mounted() {
			this.reGet();
		},
		computed:{
			proStatus(){
				if(this.product.status == 0) return '已上架'
				else if(this.product.status == 1) return '待审核'
				else return '未审核通过'
				
			}
		},
		methods: {
			reGet(){
				this.getCatalogList();
				console.log(this.catalog)
				this.getProductList(this.catalog)
			},
			close(){
				this.show = false;
				this.showDetail = false;
				this.reviewShow = false;
			},
			async getDetail(number){
				try{
					this.product = '';
					let res = await uploadDetail({number});
					for(let i = 0;i<res.pictures.length;i++){
						res.pictures[i] = this.imageSrcformat(res.pictures[i],res.pictureFormats[i]);
					}
					this.product = res;
					this.showDetail = true;
				}catch(e){
					//TODO handle the exception
					this.$toast(e)
				}
				
			},
		
			async getProductList(index){
				let id = index.id
				let res = '';
				this.catalog.id = id;
				this.catalog.name = index.name;
				this.catalog.number = index.number
				// 加载
				this.loading = true;
				
				// 获取所有
				try{
					this.productList = [];
					if(id === -1) res = await getAllProducts();
					// 分类获取
					
					else res = await getCatalogProducts({catalog:index.number})
					for(let i = 0;i<res.length;i++){
						res[i].coverPic = this.imageSrcformat(res[i].coverPic,res[i].coverPicFormat);
					}
					this.productList = res;
					// 加载完成后,关闭动画
					this.$nextTick(()=>{
						this.loading = false;
					})
				}catch(e){
					//TODO handle the exception
					this.$toast(e)
				}
			},
			async getCatalogList(){
				try{
					this.catalogList = await getCatalog();
					this.catalogList.unshift({id:-1,name:'全部'})
				}catch(e){
					//TODO handle the exception
					this.$toast(e)
				}
			},
		}
	}
</script>

<style lang="scss" scoped>
    .u-page {
        padding: 0;
    }

    .u-demo-block__title {
        padding: 10px 0 2px 15px;
    }

    .swipe-action {
        &__content {
             padding: 25rpx 0;
    
            &__text {
                 font-size: 16px;
                 color: $u-main-color;
                 padding-left: 30rpx;
             }
        }
	}
	.inner-content{
		display: flex;
		justify-content: space-around;
		align-items: center;
		
		& .cover {
			width: 128rpx;
			height: 128rpx;
			border-radius: 16rpx;
			overflow: hidden;
			& image {
				width: 100%;
				height: 100%;
			}
		}
	}
	.content {
		flex: 0.9;
		font-size: 24rpx !important;
	}
	.review {
		margin: 30px 30px 0;
	}
	.top {
		display: flex;
		align-items: center;
		height: 100rpx;
		
	}
	.review-title {
		margin: 20rpx 0;
		width: 7em;
		font-size: 30rpx;
		color: #be0000;
		text-shadow: 5rpx 5rpx 2rpx #24c3be;
	}
	.proTitle{
		font-weight: 600;
		color: #a24c21;
	}
	.catalog {
		margin: 0 50rpx;
	}
	
	// 详细信息
	.detail {
		min-height: 80vh;
		display: flex;
		flex-direction: column;
	}
	
	/* 顶部基本信息 */
	.top-message {
		
		overflow: hidden;
	}
	.seller {
		margin-left: 64rpx;
		& .innerTitle {
			color: #5abbd8;
			font-weight: 600;
		}
	}
	
	.basic-msg {
		margin-top: 10rpx;
		width: 90vw;
		height: 100rpx;
		background-color: #7ee8ff;
		font-size: 26rpx;
		
		& .inner {
			background-color: #fff;
			display: flex;
			justify-content: space-around;
			align-items: center;
			& .innerTitle {
				color: #5abbd8;
				font-weight: 600;
			}
		}
		& .num {
			height: 40rpx;
		}
		& .count {
			height: 60rpx;
			
			& .price {
				color: red;
				font-size: 36rpx;
				font-weight: 600;
			}
		}
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
	// 空数据
	.empty {
		margin-top: 200rpx;
	}
	
	// 加载
	.loading {
		margin-top: 200rpx;
	}
	
</style>
