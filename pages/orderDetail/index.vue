<template>
	<view>
		<!-- 订单信息 -->
		<view class="orderMess">
			<!-- 卖家信息 -->
			<view class="sellerMess" @click="toSeller">
				<!-- 卖家头像 -->
				<image @click="" :src="sellerMess.avatar" class="location"></image>
				<!-- 卖家昵称 -->
				<text>{{sellerMess.userName}}</text>
			</view>
			<!-- 子分割线 -->
			<view class="subSplitLine"></view>
			<!-- 商品信息 -->
			<view class="goods-box" @click="toGoodsDetail">
				<!-- 图片 -->
				<view class="goods-img">
					<image :src="coverPic" mode="widthFix" @error="doDefault"></image>
				</view>						
				<!-- 信息 -->
				<view class="goods-msg">
					<text class="detail-text">{{oneOrderGoods.name}}</text>
					<text class="price">￥ {{parseFloat(oneOrderGoods.price).toFixed(2)}}</text>
				</view>
			</view>
		</view>
		
		<!-- 分割线 -->
		<view class="splitLine"></view>
		
		<!-- 收货地址 -->
		<view class="myAddress">
			<view class="userName">
				<image src="../../static/image/consignee.png"></image>
				<text>收货人：{{order.consignee}} {{order.phone}}</text>
			</view>
			<view class="userAddress"><text>收货地址：{{order.address}}</text></view>	
		</view>
		
		<!-- 分割线 -->
		<view class="splitLine"></view>
		
		<!-- 购买数量 -->
		<view class="count">
			<text>数量：</text>
			<text>{{oneOrderGoods.count}}</text>
		</view>
		
		<!-- 分割线 -->
		<view class="splitLine"></view>
		
		<!-- 配送方式 -->
		<view class="delivery">
			<text>配送方式</text>
			<text v-if="noSelf">{{order.delivery}} ￥ 6.00</text>
			<text v-else>{{order.delivery}}</text>
		</view>
		
		<!-- 子分割线 -->
		<view class="subSplitLine"></view>
		
		<!-- 优惠 -->
		<view class="discount">
			<!-- 优惠券 -->
			<view class="coupon">
				<text>优惠券：</text>
				<text>无可用</text>	
			</view>
			<!-- 优惠金额 -->
			<view class="discountAmount">
				<text>优惠金额：</text>
				<text>￥ {{parseFloat(order.discount).toFixed(2)}}</text>
			</view>
		</view>
		
		<!-- 子分割线 -->
		<view class="subSplitLine"></view>
		
		<!-- 支付方式 -->
		<view class="payMethod">
			<text>支付方式</text>
			<text>{{order.pay}}支付</text>
		</view>
		
		<!-- 子分割线 -->
		<view class="subSplitLine"></view>
		
		<!-- 相关时间信息 -->
		<view class="orderTime">
			<view class="littleCell payTime">
				<text>付款时间：</text>
				<text>{{order.payTime}}</text>
			</view>
			<view class="littleCell orderId">
				<text>订单编号：</text>
				<text>{{oneOrderGoods.ordNumber}}</text>
			</view>
			<view class="littleCell deliveryTime" v-if="isDelivery">
				<text>发货时间：</text>
				<text>{{order.deliveryTime}}</text>
			</view>
			<view class="littleCell deliveryId" v-if="noSelf">
				<text>快递单号：</text>
				<text>{{order.deliveryId}}</text>
			</view>
			<view class="littleCell confirmTime" v-if="isConfirm">
				<text>成交时间：</text>
				<text>{{order.confirmTime}}</text>
			</view>
			<view class="littleCell applyTime" v-if="isApply">
				<text>申请退款时间：</text>
				<text>{{order.applyTime}}</text>
			</view>
			<view class="littleCell refundTime" v-if="isRefund">
				<text>退款成功时间：</text>
				<text>{{order.refundTime}}</text>
			</view>
		</view>
		
		<!-- 分割线 -->
		<view class="splitLine"></view>
		
		<!-- 合计 -->
		<view class="total">
			<view>合计：<text class="totalAmount">￥ {{parseFloat(order.total).toFixed(2)}}</text></view>
			<button @click="toConfirm" style="background-color: red;" v-if="bt">确认收货</button>
			<button @click="toCancel" style="background-color: gray;" v-if="bt">取消订单</button>
			<button @click="cancelRefund" style="background-color: burlywood;" v-if="bt3">取消退款</button>
			<button @click="toComment" style="background-color: seagreen;" v-if="bt4">评价</button>
			<button @click="toDelete" style="background-color: gray;" v-if="bt5">删除</button>
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
				// 订单里的商品
				coverPic:'',
				oneOrderGoods:{},
				// 订单其他信息
				order:{},
				// 支付方式显示
				payImage:"",
				// 卖家信息：avatar(头像)，userName(昵称)
				sellerMess:{},
				// 订单时间信息的显示
				isDelivery:false,
				noSelf:false,
				isConfirm:false,
				isApply:false,
				isRefund:false,
				// 底部操作按钮的显示
				btState:'',
				bt:false,
				bt3:false,
				bt4:false,
				bt5:false,
			}
		},
		mounted() {
			this.user = uni.getStorageSync('user');	
			this.getSeller();
			this.getCoverPic();
			this.getOneOrder();	
		},
		onLoad(option){
			this.oneOrderGoods = JSON.parse(option.item);
			this.btState = this.oneOrderGoods.state;
			
			if(this.order.delivery == "快递"){
				this.noSelf = true;
			}
			
			// 按钮的显示与隐藏
			if(this.btState == "待发货") this.bt = true;
			else if(this.btState == "待收货"){
				this.bt = true;
				this.isDelivery = true;
			}
			else if(this.btState == '待退款'){
				this.bt3 = true;
				this.isApply =true;
			}
			else if(this.btState == '已退款') {
				this.bt5 = true;
				this.isRefund = true;
			}
			else {
				this.bt4 = true;
				this.bt5 = true;
				this.isConfirm = true;
			}
			
		},
		methods: {
			// 获取商品图片
			async getCoverPic(){
				const that  = this;
				try{
					let res = await this.api.get('/orders/product/pic',{number:this.oneOrderGoods.proNumber});
					that.coverPic = res.msg;
					this.coverPic = this.imageSrcformat(that.coverPic,res.data);
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 获取该卖家信息
			async getSeller(){
				const that  = this;
				try{
					let res = await this.api.get('/booking/seller/info',{number:this.oneOrderGoods.proNumber});
					that.sellerMess = res.data;
					this.sellerMess.avatar = this.imageSrcformat(that.sellerMess.avatar,'jpg');
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 获取该用户某个订单信息
			async getOneOrder(){
				const that  = this;
				try{
					let res = await this.api.get('/orders/details',{number:this.oneOrderGoods.ordNumber});
					that.order = res.data;
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},	
			
			// 查看卖家信息
			toSeller(){
				uni.navigateTo({
					url:'/pages/userInfo/userInfo?phone=' + this.sellerMess.phone
				})
			},
			
			// 确认收货
			toConfirm(){
				let that = this;
				uni.showModal({
					title: '确认收货',
					// 提示文字
					content: '是否确认已经收到货物并检查无误？',
					// 取消按钮的文字自定义
					cancelText: "取消",
					// 确认按钮的文字自定义
					confirmText: "确认",
					// 字体的颜色
					confirmColor:'red',
					//取消字体的颜色
					cancelColor:'#000000',
					success: function(res) {
						if (res.confirm) {
							that.confirmGet();
						} 
					}
				})
			},
			
			async confirmGet(){
				const that  = this;
				try{
					let res = await this.api.put('/orders/received',{number:this.oneOrderGoods.ordNumber});
					uni.showToast({
						title: '已确认收货！',
						icon: 'success',
						duration: 30000
					})
					uni.navigateBack({
						delta:1,//返回层数，2则上上页
					})
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 取消订单并退款(申请退款)
			toCancel(){
				var oneGoods = JSON.stringify(this.oneOrderGoods)
				uni.navigateTo({
					url:'/pages/applyRefund/index?orderGoods='+ oneGoods + '&total='+ this.order.total
				})
			},
			
			// 取消退款
			cancelRefund(){
				let that = this;
				uni.showModal({
					title: '取消退款',
					// 提示文字
					content: '是否确认取消退款请求？',
					// 取消按钮的文字自定义
					cancelText: "取消",
					// 确认按钮的文字自定义
					confirmText: "确认",
					// 字体的颜色
					confirmColor:'red',
					//取消字体的颜色
					cancelColor:'#000000',
					success: function(res) {
						if (res.confirm) {
							that.cancelRefund();
						} 
					}
				})
			},
			
			async cancelRefund(){
				const that  = this;
				try{
					let res = await this.api.put('/orders/cancel',{number:this.oneOrderGoods.ordNumber});
					uni.showToast({
						title: '已成功取消退款！',
						icon: 'success',
						duration: 30000
					})
					uni.navigateBack({
							delta:1,//返回层数，2则上上页
					})
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 评价商品
			toComment(){
				var oneSeller = JSON.stringify(this.sellerMess)
				var oneGoods = JSON.stringify(this.oneOrderGoods)
				uni.navigateTo({
					url:'/pages/evaluate/index?sellerMess='+ oneSeller + '&orderGoods='+ oneGoods
				})
			},
			
			// 删除该订单
			toDelete(){
				let that = this;
				uni.showModal({
					title: '提示',
					// 提示文字
					content: '确认删除该条订单信息吗？',
					// 取消按钮的文字自定义
					cancelText: "取消",
					// 确认按钮的文字自定义
					confirmText: "删除",
					//删除字体的颜色
					confirmColor:'red',
					//取消字体的颜色
					cancelColor:'#000000',
					success: function(res) {
						if (res.confirm) {
							that.deleteOrder();
							uni.showToast({
								title: '已成功删除该订单！',
								icon: 'success',
								duration: 30000
							})
						} 
					}
				})
			},
			
			async deleteOrder(){
				const that  = this;
				try{
					let res = await this.api.del('/orders/buyer/delete',{number:this.oneOrderGoods.ordNumber});
					uni.navigateBack({
							delta:1,//返回层数，2则上上页
					})
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 商品详情页(该商品编号)
			toGoodsDetail(){
				uni.redirectTo({
					url:'/pages/detail/index?number='+ this.oneOrderGoods.number
				})
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
	
	/* 收货地址 */
	.myAddress{
		width: 94%;
		height: 180rpx;
		display: flex;
		margin: 2% 0 0 3%;
		flex-direction: column;
	}
	.myAddress view{
		width: 100%;
		height: 90rpx;
		text-align: center;
	}
	.myAddress text{
		text-align: center;
		font-size: 30rpx;
		font-weight: 300;
		width: 100%;
		height: 80rpx;
		line-height: 80rpx;
		font-weight: 200;
	}
	.userName{
		width: 100%;
		height: 80rpx;
		line-height: 80rpx;
	}
	.userName>image{
		margin-top: 7rpx;
		width: 66rpx;
		height: 66rpx;
	}
	
	/* 数量 */
	.count{
		width: 94%;
		height: 70rpx;
		line-height: 70rpx;
		display: flex;
		margin: 2% 0 0 3%;
		flex-direction: row;
		justify-content: space-between;
	}
	
	/* 配送方式 */
	.delivery{
		width: 94%;
		height: 70rpx;
		line-height: 70rpx;
		display: flex;
		margin: 2% 0 0 3%;
		flex-direction: row;
		justify-content: space-between;
	}
	
	/* 优惠 */
	.discount{
		width: 94%;
		height: 136rpx;
		display: flex;
		margin: 2% 0 0 3%;
		flex-direction: column;
	}
	.coupon{
		width: 100%;
		height: 60rpx;
		line-height: 60rpx;
		flex-flow: row;
		display: flex;
		justify-content: space-between;
	}
	.discountAmount{
		width: 100%;
		height: 60rpx;
		line-height: 60rpx;
		flex-flow: row;
		justify-content: space-between;
		display: flex;
	}
	
	/* 支付方式 */
	.payMethod{
		width: 94%;
		height: 60rpx;
		line-height: 60rpx;
		text-align: center;
		display: flex;
		margin: 2% 0 0 3%;
		flex-direction: row;
		justify-content: space-between;
	}
	.payMethod>image{
		width: 52rpx;
		height: 52rpx;
	}
	.payMethod>text{
		line-height: 60rpx;
		height: 60rpx;
	}

	/* 时间信息 */
	.orderTime{
		width: 94%;
		text-align: center;
		display: flex;
		margin: 2% 0 0 3%;
		flex-direction: column;
		justify-content: space-between;
	}
	.littleCell{
		height: 60rpx;
		line-height: 60rpx;	
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
	
	/* 合计 */
	.total{
		width: 94%;
		height: 70rpx;
		line-height: 70rpx;
		display: flex;
		margin: 2% 0 0 3%;
		flex-direction: row;
		justify-content: space-between;
		font-size: 36rpx;
	}
	.totalAmount{
		font-size: 40rpx;
		color: red;
		font-weight: 600;
	}
	.total>button{
		font-size: 20rpx;
		width: 20%;
		color: white;
		height: 62rpx;
		line-height: 62rpx;
	}
</style>
