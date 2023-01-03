<template>
	<view>
		<!-- 收货地址 -->
		<view class="myAddress">
			<!-- 地图定位 -->
			<image @click="map" src="../../static/image/location.png" class="location"></image>
			<view class="someMess">
				<!-- 默认地址的收件人姓名，号码，详细地址 -->
				<view class="userName"><text>收货人：{{myAddress.receiverName}} {{myAddress.receiverPhone}}</text></view>
				<view class="userAddress"><text>收货地址：{{myAddress.region}} {{myAddress.addressDetail}}</text></view>	
			</view>
			<!-- 地址管理 -->
			<image @click="toAddressManagement" src="../../static/image/return_right .png" class="addressControl"></image>
		</view>
		
		<!-- 分割线 -->
		<view class="splitLine"></view>
		
		<!-- 订单信息 -->
		<view class="orderMess">
			<!-- 卖家信息 -->
			<view class="sellerMess" @click="toSeller">
				<!-- 卖家头像 -->
				<image :src="sellerMess.avatar" class="location"></image>
				<!-- 卖家昵称 -->
				<text>{{sellerMess.userName}}</text>
			</view>
			<!-- 子分割线 -->
			<view class="subSplitLine"></view>
			<!-- 商品信息 -->
			<view class="goods-box" @click="toGoodsDetail">
				<!-- 图片 -->
				<view class="goods-img">
					<image :src="'data:image/jpg;base64,' + coverPic" mode="widthFix" @error="doDefault"></image>
				</view>						
				<!-- 信息 -->
				<view class="goods-msg">
					<text class="detail-text">{{oneBook.name}}</text>
					<text class="price">￥ {{parseFloat(oneBook.price).toFixed(2)}}</text>
				</view>
			</view>
			<!-- 子分割线 -->
			<view class="subSplitLine"></view>
			<view class="goodsCount">
				<text>数量：</text>
				<text class="count">{{oneBook.count}}</text>
			</view>
			<!-- 子分割线 -->
			<view class="subSplitLine"></view>
			<!-- 配送方式 -->
			<view class="delivery">
				<text>配送方式</text>
				<view class="method" v-for="(item, index) in items" :key="item.value">
					<radio-group @change="radioChange">
						<radio :value="item.value" :checked="index === current" style="transform:scale(0.8)"/>{{item.name}}
					</radio-group>
				</view>
			</view>
		</view>
		
		<!-- 分割线 -->
		<view class="splitLine"></view>
		
		<!-- 优惠 -->
		<view class="discount">
			<!-- 优惠券 -->
			<view class="coupon">
				<text style="width: 66%;">优惠券：</text>
				<text class="oneCoupon">无可用</text>
				<image @click="" src="../../static/image/return_right .png"></image>	
			</view>
			<!-- 子分割线 -->
			<view class="subSplitLine"></view>
			<!-- 优惠金额 -->
			<view class="discountAmount">
				<text>优惠金额：</text>
				<text style="margin-right: 5%;font-size: 25rpx;">￥ {{parseFloat(discount).toFixed(2)}}</text>
			</view>
		</view>
		
		<!-- 分割线 -->
		<view class="splitLine"></view>
		
		<!-- 支付方式 -->
		<view class="payMethod">
			<text class="payTitle">支付方式</text>
			<!-- 支付宝 -->
			<view class="Alipay" @click="pickAlipay">
				<image src="../../static/image/Alipay.png"></image>
				<text :style="{background: Alipaycolor,color:Alipayfont}">支付宝支付</text>
			</view>
			<view></view>
			<!-- 子分割线 -->
			<view class="subSplitLine"></view>
			<!-- 微信 -->
			<view class="WeChat" @click="pickWeChat">
				<image src="../../static/image/WeChat.png"></image>
				<text :style="{background: WeChatcolor,color:WeChatfont}">微信支付</text>
			</view>
		</view>
		
		<!-- 分割线 -->
		<view class="splitLine"></view>
		
		<!-- 合计 -->
		<view class="total">
			<view>合计：<text class="totalAmount">￥ {{parseFloat(total).toFixed(2)}}</text></view>
			<button @click="toPay">确定下单</button>
			<uni-popup ref="popdown" type="bottom" background-color="#fff">
				<view class="pay">
					<image src="../../static/image/return (2).png" @click="returnPay"></image>
					<view class="payMoney">
						<text>￥ </text>
						<text>{{parseFloat(total).toFixed(2)}}</text>
					</view>
					<button @click="payMoney">确认支付</button>
				</view>		
			</uni-popup>
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
				myAddress:{
					receiverName:'',
					receiverPhone:'',
					region:'',
					addressDetail:'',
				},
				title:'确认订单',
				items: [{
					value: 'self',
				    name: '自取',
					},
				    {
				    value: 'express',
				    name: '快递 ￥ 6.00',
					},
				],
				current: 0,
				// 待下单的商品信息（从我的预约获取）
				oneBook:{},
				coverPic:'',
				// 卖家信息：avatar(头像)，userName(昵称)
				sellerMess:{},
				// 支付宝支付
				isAlipay: false,
				Alipaycolor: 'white',
				Alipayfont: 'black',
				// 微信支付
				isWeChat: false,
				WeChatcolor: 'white',
				WeChatfont: 'black',
				// 优惠金额
				discount: 0,
				// 总金额
				total: 0,
			}
		},
		mounted() {
			this.user = uni.getStorageSync('user');	
			this.getAddress();
			this.getSeller();	
			this.getCoverPic();
		},
		onLoad(option){
			//this.oneBook = JSON.parse(decodeURIComponent(option.oneBookItem));
			this.oneBook = JSON.parse(decodeURIComponent(option.item));
			this.total = this.oneBook.price * this.oneBook.count;
		},
		onShow(){
			this.getAddress();
		},
		methods: {
			map(){
				const that = this;
				uni.chooseLocation({
					latitude:29.59744,
					longitude:106.298386,
					success: function (res) {
						that.myAddress.region = res.name;
						that.myAddress.addressDetail = res.address;
					},
					fail:(res=>{
						console.log(res);
					})
				});
			},
			
			// 获取商品图片
			async getCoverPic(){
				const that  = this;
				try{
					let res = await this.api.get('/orders/product/pic',{number:this.oneBook.number});
					that.coverPic = res.msg;
					//this.coverPic = this.imageSrcformat(that.coverPic,res.data);
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 返回我的预约
			toMyAppointment(){
				uni.redirectTo({
					url:'/pages/myAppointment/index'
				})
			},			
			
			// 去往地址管理
			toAddressManagement(){
				uni.navigateTo({
					url: "../my-address/address"
				})
			},
			
			// 获取默认地址
			async getAddress(){
				const that  = this;
				try{
					that.myAddress = await this.api.get('/default/address',{phone:this.user.phone});
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
			
			// 配送方式选择
			radioChange: function(evt) {
			    for (let i = 0; i < this.items.length; i++) {
			        if (this.items[i].value === evt.detail.value) {
			            this.current = i;
			            break;
			        }
			    }
				if(this.current==0) this.total = this.oneBook.price * this.oneBook.count;
				else this.total = this.oneBook.price * this.oneBook.count + 6;
			},
			
			// 支付方式选择
			pickAlipay(){
				this.Alipaycolor = '#b34c26';
				this.Alipayfont = 'white';
				this.WeChatcolor = 'white';
				this.WeChatfont = 'black';
				this.isAlipay = true;
				this.isWeChat = false;
			},
			pickWeChat(){
				this.Alipaycolor = 'white';
				this.Alipayfont = 'black';
				this.WeChatcolor = '#b34c26';
				this.WeChatfont = 'white';
				this.isAlipay = false;
				this.isWeChat = true;
			},
			
			// 确认下单
			toPay(){
				if(this.myAddress.receiverName != '' && this.myAddress.receiverPhone != '' && this.myAddress.region != '' && this.myAddress.addressDetail != ''){
					if(this.isAlipay || this.isWeChat){
						this.$refs.popdown.open('bottom');
					}
					else this.$toast('请选择支付方式~');
				}
				else this.$toast('请填写收货人和收货地址~');
			},
			
			// 付款
			payMoney(){
				this.$refs.popdown.close();
				this.generateOrder();
			},
			
			// 不付款
			returnPay(){
				this.$refs.popdown.close();
			},
			
			// 生成订单
			async generateOrder(){
				
				let delivery = '快递';
				if(this.current == 0) delivery = '自取';
				
				let pay = '';
				if(this.isAlipay) pay = '支付宝';
				else pay = '微信';
				
				// this.submit();
				
				const that  = this;
				try{
					let res = await this.api.post('/orders/build',{bookNum:this.oneBook.bookNum,myAddress:this.myAddress,expressDelivery:delivery,price:this.total,payment:pay,discounts:this.discount});	
					if(res.code == '666'){
						uni.showToast({
							title: '下单成功！',
							icon: 'success',
							duration: 30000
						})
						uni.navigateBack({
							delta:1,//返回层数，2则上上页
						})	
					}				
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 获取该卖家信息
			async getSeller(){
				const that  = this;
				try{
					let res = await this.api.get('/booking/seller/info',{number:this.oneBook.number});
					that.sellerMess = res.data;
					this.sellerMess.avatar = this.imageSrcformat(that.sellerMess.avatar,'jpg');
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 商品详情页(该商品编号)
			toGoodsDetail(){
				uni.redirectTo({
					url:'/pages/detail/index?number='+ this.oneBook.number
				})
			},
		}
	}
</script>

<style>	
	/* 地址 */
	.myAddress{
		width: 94%;
		height: 180rpx;
		line-height: 180rpx;
		display: flex;
		margin: 2% 0 0 3%;
		flex-direction: row;
		justify-content: space-between;
	}
	.location{
		width: 88rpx;
		height: 88rpx;
		margin-top: 46rpx;
	}
	.addressControl{
		width: 52rpx;
		height: 52rpx;
		margin-top: 64rpx;
	}
	.someMess{
		text-align: center;
		font-size: 30rpx;
		font-weight: 300;
		width: 100%;
		height: 80rpx;
		line-height: 80rpx;
		font-weight: 200;
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
		width: 100%;
		height: 6rpx;
	}
	
	/* 订单信息 */
	.orderMess{
		width: 94%;
		height: 520rpx;
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
	/* --商品数量 */
	.goodsCount{
		width: 95%;
		height: 70rpx;
		line-height: 70rpx;
		flex-flow: row;
		display: flex;
		justify-content: space-between;
		right: 5%;
	}
	.count{
		
	}
	/* --配送方式 */
	.delivery{
		width: 100%;
		height: 70rpx;
		line-height: 70rpx;
		flex-flow: row;
		display: flex;
		justify-content: space-between;
	}
	.delivery>text{
		width: 50%;
		height: 100%;
	}
	.method{
		width: 50%;
		height: 100%;
		flex-flow: row;
		display: flex;
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
	}
	.coupon>image{
		width: 52rpx;
		height: 52rpx;
	}
	.oneCoupon{
		margin:0 2% 0 9%;
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
		height: 236rpx;
		display: flex;
		margin: 2% 0 0 3%;
		flex-direction: column;
		justify-content: space-between;
	}
	.payTitle{
		width: 100%;
		height: 60rpx;
		line-height: 60rpx;
	}
	.payMethod image{
		width: 60rpx;
		height: 60rpx;
	}
	/* 支付宝 */
	.Alipay{
		width: 100%;
		height: 80rpx;
		line-height: 80rpx;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
	/* 微信 */
	.WeChat{
		width: 100%;
		height: 80rpx;
		line-height: 80rpx;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
	.Alipay>text,.WeChat>text{
		font-size: 30rpx;
		width: 30%;
		text-align: center;
		border-radius: 50%;
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
		background-color: red;
		font-size: 32rpx;
		color: white;
		height: 62rpx;
		width: 50%;
		line-height: 62rpx;
		margin-right: 1%;
	}
	.pay{
			display: flex;
			flex-direction: column;
			width: 100%;
		}
		.pay>image{
			width: 52rpx;
			height: 52rpx;
			margin-left: 25rpx;
			margin-top: 25rpx;
		}
		.payMoney{
			display: flex;
			flex-direction: row;
			color: red;
			text-align: center;
			margin: 66rpx 40% 66rpx 40%;
			width: 20%;
			font-size: 42rpx;
		}
		.pay>button{
			width: 360rpx;
			height: 62rpx;
			font-size: 28rpx;
			color: white;
			background-color: greenyellow;
			margin-bottom: 25rpx;
		}
</style>
