<template>
	<view>
		<!-- 订单信息 -->
		<view class="orderMess">
			<!-- 商品信息 -->
			<view class="goods-box">
				<!-- 图片 -->
				<view class="goods-img">
					<image :src="coverPic" mode="widthFix" @error="doDefault"></image>
				</view>						
				<!-- 信息 -->
				<view class="goods-msg">
					<text class="detail-text">{{order.name}}</text>
					<text class="price">￥ {{order.price.toFixed(2)}}</text>
				</view>
			</view>
		</view>
		
		<!-- 分割线 -->
		<view class="splitLine"></view>
		
		<!-- 相关信息 -->
		<view class="someMess">
			<view class="littleCell goodsCount">
				<text>货物数量：</text>
				<text style="margin-right: 6%;">{{order.count}}</text>
			</view>
			<!-- 子分割线 -->
			<view class="subSplitLine"></view>
			<view class="littleCell goodsState">
				<text style="width: 66%;">货物状态：<text class="weight">*</text></text>
				<text class="state">{{select1}}</text>
				<image @click="open1" src="../../static/image/return_right .png"></image>
				<uni-popup ref="popup" type="bottom" background-color="#fff">
					<view class="oneState">
						<text style="font-size: 30rpx;" >请选择货物状态</text>
						<view class="subLine" ></view>
						<text v-for="(item,index) of goodsState" :key="index" @click="setState(index)">{{item.state}}</text>
					</view>		
				</uni-popup>
			</view>
			<!-- 子分割线 -->
			<view class="subSplitLine"></view>
			<view class="littleCell refundReason">
				<text style="width: 62%;">退款原因：<text class="weight">*</text></text>
				<text v-if="noGoodsState">请先选择货物状态</text>
				<text v-else @click="open2">{{select2}}</text>
				<uni-popup ref="popdown" type="bottom" background-color="#fff">
					<view class="oneState">
						<text style="font-size: 30rpx;">请选择退款原因</text>
						<view class="subLine" ></view>
						<text v-for="(item,index) of refundReason" :key="index" @click="setReason(index)">{{item.reason}}</text>
					</view>		
				</uni-popup>
			</view>
			<!-- 子分割线 -->
			<view class="subSplitLine"></view>
			<view class="littleCell refundAmount">
				<text>退款金额：</text>
				<text class="totalAmount" style="margin-right: 3%;">￥ {{total.toFixed(2)}}</text>
			</view>
		</view>
		
		<!-- 分割线 -->
		<view class="splitLine"></view>
		
		<!-- 补充描述 -->
		<view class="description">
			<text class="descriptionTip">补充描述</text>
			<uni-easyinput type="textarea" autoHeight v-model="refundRequest.description" placeholder="补充描述,有助于商家更好的处理售后问题"></uni-easyinput>
		</view>
		
		<!-- 分割线 -->
		<view class="splitLine"></view>
		
		<!-- 操作按钮 -->
		<view class="somebt">
			<button @click="toSubmit" style="background-color: red;color: white;">提交</button>
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
				coverPic:'',
				order:{
					price:0,
				},
				total:0,
				// 货物状态
				goodsState:[
					{state:'未收到货',},
					{state:'已收到货',}
				],
				select1:'请选择货物状态',
				// 退款原因
				refundReason:[
					{reason:'不喜欢/不想要',},
					{reason:'与卖家协商一致退款',},
					{reason:'商品与描述不符',},
					{reason:'质量问题',},
					{reason:'卖家发错货',},
					{reason:'发票问题',}
				],
				select2:'请选择退款原因',
				refundRequest:{
					// 货物状态
					goodsState:'',
					// 退款原因
					refundReason:'',
					// 补充描述
					description:'',
				},
				// 是否选择货物状态
				noGoodsState:true,
				isState:false,
				isReason:false,
			}
		},
		mounted() {
			this.user = uni.getStorageSync('user');	
			this.getCoverPic();
		},
		onLoad(option){
			this.order = JSON.parse(option.orderGoods);
			this.total = option.total;
			console.log(this.order.price);
			console.log(this.total);
		},
		methods: {
			// 获取商品图片
			async getCoverPic(){
				const that  = this;
				try{
					let res = await this.api.get('/orders/product/pic',{number:this.order.proNumber});
					that.coverPic = res.data;
					this.coverPic = this.imageSrcformat(that.coverPic,'jpg');
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},		
			
			toSubmit(){
				if(this.isReason && this.isState){
					this.refundRequest.goodsState = this.select1;
					this.refundRequest.refundReason = this.select2;
					this.applyRefund();
				}
				else this.$toast('请完善退款信息！');
			},
			
			open1(){
			    this.$refs.popup.open('bottom');
			},
			
			open2(){
			    this.$refs.popdown.open('bottom');
			},
			
			// 选择货物状态
			setState(index){
				this.select1 = this.goodsState[index].state;
				this.$refs.popup.close();
				if(this.select1 != '请选择货物状态') {
					this.noGoodsState = false;
					this.isState = true;
				}
			},
			
			// 选择退款原因
			setReason(index){
				this.select2 = this.refundReason[index].reason;
				this.$refs.popdown.close();
				if(this.select2 != '请选择退款原因' && this.select2 != '请先选择货物状态') this.isReason = true;
			},
			
			// 申请退款
			async applyRefund(){
				try{
					let res = await this.api.put('/orders/after',{state:this.order.state,number:this.order.ordNumber,refundRequest:this.refundRequest});
					if(res.code == "666"){
						uni.showToast({
							title: '已成功提交退款申请！',
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
	.orderMess{
		width: 94%;
		height: 210rpx;
		display: flex;
		margin: 2% 0 0 3%;
		flex-direction: column;
		justify-content: space-between;
		padding: 2%;
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
	
	/* 相关信息 */ 
	.someMess{
		width: 94%;
		display: flex;
		margin: 2% 0 0 3%;
		flex-direction: column;
		justify-content: space-between;
	}
	.littleCell{
		height: 88rpx;
		line-height: 88rpx;	
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		font-size: 28rpx;
	}
	.weight{
		color: red;
		font-size: 28rpx;
		font-weight: 500;
	}
	.totalAmount{
		font-size: 32rpx;
		color: red;
		font-weight: 600;
	}
	.goodsState>image{
		margin-top: 18rpx;
		width: 52rpx;
		height: 52rpx;
	}
	.oneState{
		display: flex;
		flex-direction: column;
		text-align: center;
	}
	.subLine{
		background-color: #b34c26;
		width: 66%;
		height: 6rpx;
		margin-left: 17%;
	}
	.oneState>text{
		height: 80rpx;
		line-height: 80rpx;
		padding: 10rpx;
		font-size: 26rpx;
	}
	
	/* 补充描述 */
	.description{
		width: 94%;
		margin: 2% 0 5% 3%;
	}
	.descriptionTip{
		width: 100%;
		height: 100rpx;
		line-height: 100rpx;
		font-size: 32rpx;
		color: black;
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
