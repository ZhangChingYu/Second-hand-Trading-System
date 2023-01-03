<template>
	<view>
		<!-- 订单信息 -->
		<view class="orderMess">
			<!-- 卖家信息 -->
			<view class="sellerMess">
				<!-- 卖家头像 -->
				<image @click="" :src="sellerMess.avatar" class="location"></image>
				<!-- 卖家昵称 -->
				<text>{{sellerMess.userName}}</text>
			</view>
			<!-- 子分割线 -->
			<view class="subSplitLine"></view>
			<!-- 商品信息 -->
			<view class="goods-box">
				<!-- 图片 -->
				<view class="goods-img">
					<image :src="'data:image/jpg;base64,' + coverPic" mode="widthFix" @error="doDefault"></image>
				</view>						
				<!-- 信息 -->
				<view class="goods-msg">
					<text class="detail-text">{{orderGoods.name}}</text>
					<text class="price">￥ {{parseFloat(orderGoods.price).toFixed(2)}}</text>
				</view>
			</view>
		</view>
		
		<!-- 分割线 -->
		<view class="subSplitLine"></view>
		
		<!-- 商品评分 -->
		<view class="score">
			<view class="subScore"> 
				<text>描述相符</text>
				<uni-rate allow-half :value="5" v-model="oneEvaluate.score1"/>
			</view>
			<view class="subScore">
				<text>物流服务</text>
				<uni-rate allow-half :value="5" v-model="oneEvaluate.score2"/>
			</view>
			<view class="subScore">
				<text>服务态度</text>
				<uni-rate allow-half :value="5" v-model="oneEvaluate.score3"/>
			</view>	
		</view>
		
		<!-- 分割线 -->
		<view class="subSplitLine"></view>
		
		<!-- 商品评价 -->
		<view class="evaluate">
			<uni-easyinput type="textarea" autoHeight v-model="oneEvaluate.evaluate" placeholder="从多个角度评价宝贝,可以帮助更多想买的人"></uni-easyinput>
		</view>
		
		<!-- 匿名选择 -->
		<view class="isAnonymous">
			<checkbox-group @change="handleChange"><checkbox :checked="oneEvaluate.isAnonymous" style="transform:scale(0.8)"/>匿名</checkbox-group>
			<text>( 匿名会隐藏头像和昵称 )</text>
		</view>
		
		<!-- 分割线 -->
		<view class="subSplitLine"></view>
		
		<!-- 操作按钮 -->
		<view class="somebt">
			<button @click="toSubmit" style="background-color: red;color: white;">发布评价</button>
		</view>
	</view>
</template>

<script>
	import {mixin} from '../../mixin.js'
	export default {
		mixins:[mixin],
		data() {
			return {
				user:{},
				orderGoods:{},
				coverPic:'',
				sellerMess:{},	
				current:0,
				// 返回的评价
				oneEvaluate:{
					score1:5,
					score2:5,
					score3:5,
					// 是否匿名
					isAnonymous:false,
					evaluate:'',
				},
			}
		},
		mounted() {
			this.user = uni.getStorageSync('user');	
			this.getSeller();
			this.getCoverPic();
		},
		onLoad(option){
			this.orderGoods = JSON.parse(decodeURIComponent(option.orderGoods));
		},
		methods: {
			// 获取商品图片
			async getCoverPic(){
				const that  = this;
				try{
					let res = await this.api.get('/orders/product/pic',{number:this.orderGoods.proNumber});
					that.coverPic = res.msg;
					//this.coverPic = this.imageSrcformat(that.coverPic,res.data);
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 获取该卖家信息
			async getSeller(){
				const that  = this;
				try{
					let res = await this.api.get('/booking/seller/info',{number:this.orderGoods.proNumber});
					that.sellerMess = res.data;
					this.sellerMess.avatar = this.imageSrcformat(that.sellerMess.avatar,'jpg');
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 是否匿名评价
			handleChange(e){
			   this.oneEvaluate.isAnonymous = !this.oneEvaluate.isAnonymous;
			},
			
			// 发布评价
			async toSubmit(){
				const that  = this;
				try{
					let res = await this.api.post('/evaluation',{number:this.orderGoods.proNumber,phone:this.user.phone,oneEvaluate:this.oneEvaluate});
					if(res == 201){
						uni.showToast({
							title: '评价成功！',
							icon: 'success',
							duration: 30000
						})
						uni.navigateBack({
							delta:2, //返回层数，2则上上页
						})
					}
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
		}
	}
</script>

<style>
	/* 分割线 */
	.splitLine{
		background-color: #efefef;
		width: 100%;
		height: 20rpx;
	}
	
	/* 子分割线 */
	.subSplitLine{
		background-color: #efefef;
		width: 94%;
		height: 6rpx;
		margin-left: 3%;
	}
	
	/* 订单信息 */
	.orderMess{
		width: 94%;
		height: 356rpx;
		display: flex;
		margin: 2% 0 0 3%;
		flex-direction: column;
		justify-content: space-between;
	}
	/* --卖家信息 */
	.sellerMess{
		width: 100%;
		height: 120rpx;
		line-height: 140rpx;
		display: flex;
		flex-direction: row;
	}
	.sellerMess>image{
		height: 111rpx;
		width: 111rpx;
		border-radius: 100%;
		margin-top: 5rpx;
	}
	.sellerMess>text{
		height: 120rpx;
		line-height: 120rpx;
		font-size: 36rpx;
		font-weight: 600;
		margin-left: 5%;
	}
	/* --商品信息 */
	.goods-box{
		width: 100%;
		height: 210rpx;
		flex-flow: row;
		justify-content: space-between;
		display: flex;
		position: relative;
		margin: 8rpx 0;
	}
	/* ----商品图片 */
	.goods-img {
		height: 96%;
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
	/* ----商品文字描述 */
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
	/* ----价格 */
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
	
	/* 商品评分 */
	.score{
		width: 94%;
		height: 210rpx;
		display: flex;
		margin: 2% 0 0 3%;
		flex-direction: column;
		justify-content: space-between;
	}	
	.subScore{
		height: 70rpx;
		line-height: 70rpx;
		display: flex;
		flex-direction: row;
		justify-content: center;
	}
	.subScore>text{
		margin-right: 5%;
	}
	
	/* 商品评价 */
	.evaluate{
		width: 94%;
		margin: 2% 0 0 3%;
	}
	
	/* 匿名选择 */
	.isAnonymous{
		margin-top: 3%;
		height: 70rpx;
		line-height: 70rpx;
		width: 94%;
		margin: 2% 0 0 3%;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
	.isAnonymous>text{
		width: 75%;
		color: gray;
		font-size: 28rpx;
	}
	
	/* 操作按钮 */
	.somebt{
		width: 94%;
		height: 70rpx;
		line-height: 70rpx;
		display: flex;
		margin: 2% 0 0 3%;
		flex-direction: row;
		justify-content: center;
		font-size: 36rpx;
	}
	.somebt>button{
		font-size: 32rpx;
		height: 62rpx;
		width: 50%;
		line-height: 62rpx;
		position:fixed;
		bottom: 25rpx;
	}
</style>
