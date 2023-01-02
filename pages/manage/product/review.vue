<template>
	<view class="review">
		<view class="top">
			<view class="review-title">商品审核列表</view>
			<view class="catalog" v-if="catalogList.length !== 0">
				<u-action-sheet :actions="catalogList" @select="getProductList" :show="show" @close="close" :closeOnClickOverlay="true" :closeOnClickAction="true" :title="title"></u-action-sheet>
				<u-icon name="grid" color="#2979ff" @click="show = true" :label="catalog.name" labelPos="bottom" size="50rpx"></u-icon>
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
				<view class="opetator-box" >
					<view class="reject" @click="openReviewBox(product.number,'reject')">不通过</view>
					<view class="pass" @click="openReviewBox(product.number,'pass')">通过</view>
				</view>
			</view>
		</u-popup>
		
		<u-swipe-action v-if="productList.length !== 0" >
			<u-swipe-action-item  v-for="(item,index) of productList" :show="showOperate" :key="index" :options="options" :index="index" :name="index" @click="operateReview">
			  <view class="swipe-action u-border-top u-border-bottom" @click="getDetail(item.number)">
				  <!-- 内容 -->
				<view class="swipe-action__content">
					<view class="content">
						<text class="swipe-action__content__text content"><text class="proTitle">编号：</text>{{item.number}}</text>
						<text class="swipe-action__content__text content"><text class="proTitle">类别：</text>{{item.catalog}}</text>
					</view>
					
				  
				  <view class="swipe-action__content__text content" ><text class="proTitle">名称：</text>{{item.name}}</view>
				  <view class="content">
				  	<text class="swipe-action__content__text content"><text class="proTitle">手机：</text>{{item.SPhone}}</text>
					<text class="swipe-action__content__text content"><text class="proTitle">日期：</text>{{item.date}}</text>
				  </view>
				 
				</view>
				
			  </view>
			</u-swipe-action-item>
		</u-swipe-action>
		<!-- 无数据 -->
		<u-empty v-else></u-empty>
		
		<!-- 审核界面 -->
		<u-popup :show="reviewShow" @close="close" :round="10" closeable>
			<view class="review-box">
				<view class="number">编号：{{review.number}}</view>
				<view class="decision">
					审核结果：
					<text v-if="review.decision === 'pass'" style="color: limegreen;">通过!</text>
					<text v-else style="color: red;">不通过！</text>
				</view>
				<view class="explain">
					<u--textarea v-model="review.explain" placeholder="审核结果说明" maxlength="50" count autoHeight ></u--textarea>
				</view>
				<view class="btn">
					<u-button type="primary" @click="submit" text="提交"></u-button>
				</view>
			</view>
		</u-popup>
		
	</view>
</template>

<script>
	import {mixin} from '../../../mixin.js'
	import {allUpload,catalogUpload,getCatalog,uploadDetail,submitUpload} from '@/common/request/api.js'
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
				options:[
					{
						text: '通过',
						style: {
							backgroundColor: '#3c9cff'
						}
					},
					{
						text: '不通过',
						style: {
							backgroundColor: '#f56c6c'
						}
					},
				],
				show:false,
				showDetail:false,
				reviewShow:false,
				showOperate:true,
				
				// 审核
				review:{
					number:'',
					decision:'',
					explain:''
				}
			}
		},
		mixins:[mixin],
		mounted() {
			this.getCatalogList();
			this.getProductList(this.catalog)
		},
		computed:{
			proStatus(){
				if(this.product.status == 0) return '已上架'
				else if(this.product.status == 1) return '待审核'
				else return '未审核通过'	
			}
		},
		methods: {
			async submit(){
				if(this.review.explain !== ''){
					this.close();
					if(this.review.number !== '' && this.review.decision !== ''){
						try{
							let res = await submitUpload(this.review);
							
							if(res == 201) {
								this.$toast('处理成功！')
								this.getProductList(this.catalog)
							}
							else if(res == 422) this.$toast('处理失败！')
							this.review.number = '';
							this.review.decision = '';
							this.review.explain = '';
						}catch(e){
							//TODO handle the exception
						}
						
					}else this.$toast('请稍后重试！')
				}else this.$toast('填写审核说明！')
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
			openReviewBox(number,decision){
				this.close();
				this.review.number = number;
				this.review.decision = decision;
				this.reviewShow = true;
			},
			operateReview(option){
				this.showOperate = false;
				if(option.index == 0){
					this.openReviewBox(this.productList[option.name].number,'pass')
				}else if(option.index == 1){
					this.openReviewBox(this.productList[option.name].number,'reject')
				}
			},
			
			async getProductList(index){
				let id = index.id
				let res = '';
				this.catalog.id = id;
				this.catalog.name = index.name;
				// 获取所有
				try{
					this.productList = [];
					if(id === -1) res = await allUpload();
					// 分类获取
					
					else res = await catalogUpload({catalog:index.number})
					this.productList = res;
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
	.content {
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
		// float: left;
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
		margin-left: 50rpx;
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
	
	.opetator-box {
		position: sticky;
		display: flex;
		bottom: 0rpx;
		height: 84rpx;
		background-color: #fff3cd;
		color: #fff;
		line-height: 84rpx;
		text-align: center;
		
		& .reject {
			flex: 1;
			background-color: #d6d6d6;
		}
		& .pass {
			position: relative;
			flex: 1;
			background-color: #aa3f1e;
		}
	}
	
	// 审核
	.review-box {
		padding: 30rpx;
		height: 80vw;
		display: flex;
		flex-direction: column;
		justify-content: space-around;
		
	}
    
</style>
