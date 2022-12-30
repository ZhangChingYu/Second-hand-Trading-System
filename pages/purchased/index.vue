<template>
	<view>
		
		<!-- 搜索框 -->
		<view class="top">
			<image class="btReturn" src="../../static/image/previous.png" @click="toMe"></image>
			<view class="top-search">
				<text @click="toSomeOf"></text>
				<input placeholder="搜索订单" v-model="keyWord" value="keyWord"/>
			</view>
		</view>
		
		<!-- 中间订单状态分类 -->
		<view class="middle">
			<text @click="toOne(state0)" ref="全部" :style="{background: allcolor,color: allfont}">{{state0}}</text>
			<text @click="toOne(state1)" ref="已购买" :style="{background: firstcolor,color: firstfont}">{{state1}}</text>
			<text @click="toOne(state2)" ref="待发货" :style="{background: secondcolor,color: secondfont}">{{state2}}</text>
			<text @click="toOne(state3)" ref="待收货" :style="{background: thirdcolor,color: thirdfont}">{{state3}}</text>
			<text @click="toOne(state4)" ref="待退款" :style="{background: fourthcolor,color: fourthfont}">{{state4}}</text>
			<text @click="toOne(state5)" ref="已退款" :style="{background: fifthcolor,color: fifthfont}">{{state5}}</text>
		</view>
		
		<!-- 我的订单 -->
		<view class="mybook">
			<!-- 已购买物品 -->
			<view class="onebook"
			v-for="(item,index) of myOrderItem"
			:key="index"
			>
				<view class="goods-box" @click="toGoodsDetail(item.number)">
					<!-- 图片 -->
					<view class="goods-img">
						<image :src="item.coverPic" mode="widthFix" @error="doDefault"></image>
					</view>						
					<!-- 信息 -->
					<view class="goods-msg">
						<text class="detail-text">{{item.name}}</text>
						<text class="price">￥{{parseFloat(item.price).toFixed(2)}}</text>
					</view>
				</view>
				<view class="oper">
					<view class="book-state" @click="toOper(item)">{{item.state}}</view>
					<view class="deleteBook" @click="deleteOne(item,index)">删除</view>
				</view>	
			</view>
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
				// myOrderItem:[proNumber,ordNumber,name,coverPic,price,state][商品编号，订单编号，商品名称，商品图片，价格，订单状态]
				myOrderItem:[],
				// 订单状态
				state0:'全部',
				allcolor:'#b34c26',
				allfont:'white',
				state1:'已购买',
				firstcolor:'#efefef',
				firstfont:'gray',
				state2:'待发货',
				secondcolor:'#efefef',
				secondfont:'gray',
				state3:'待收货',
				thirdcolor:'#efefef',
				thirdfont:'gray',
				state4:'待退款',
				fourthcolor:'#efefef',
				fourthfont:'gray',
				state5:'已退款',
				fifthcolor:'#efefef',
				fifthfont:'gray',
				// 搜索栏关键词
				keyWord:'',
			}
		},
		mounted() {
			this.user = uni.getStorageSync('user');
			this.getMyOrder();					
		},
		onShow(){
			this.getMyOrder();	
		},
		methods: {
			toMe(){
				uni.redirectTo({
					url:'/pages/my/index'
				})
			},
			
			// 商品详情页
			toGoodsDetail(number){
				uni.navigateTo({
					url:'/pages/detail/index?number='+ number
				})
			},
			
			// 订单状态相关操作->订单详情界面
			toOper(item){
				//let data = encodeURIComponent(JSON.stringify(item));
				var oneitem = JSON.stringify(item)
				uni.navigateTo({
					url:'/pages/orderDetail/index?item=' + oneitem
				})
			},
			
			// 搜索相关我的订单(关键词：商品名称，卖家手机号)
			async toSomeOf(){
				const that  = this;
				try{
					let res = await this.api.get('/orders/fuzzy/name',{name:this.keyWord,phone:this.user.phone,isbuyer:1});
					that.myOrderItem = res.data;
					for(let i = 0;i<this.myOrderItem.length;i++){
						this.myOrderItem[i].coverPic = this.imageSrcformat(that.myOrderItem[i].coverPic,'jpg');
					}
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 订单状态分类
			async toOne(state){
				const that  = this;
				
				if(state == '全部'){
					this.allcolor = '#b34c26';
					this.allfont = 'white';
					this.firstcolor = '#efefef',
					this.firstfont = 'gray';
					this.secondcolor = '#efefef';
					this.secondfont = 'gray';
					this.thirdcolor = '#efefef';
					this.thirdfont = 'gray';
					this.fourthcolor = '#efefef';
					this.fourthfont = 'gray';
					this.fifthcolor = '#efefef';
					this.fifthfont = 'gray';
				}
				else if(state == '已购买'){
					this.allcolor = '#efefef';
					this.allfont = 'gray';
					this.firstcolor = '#b34c26',
					this.firstfont = 'white';
					this.secondcolor = '#efefef';
					this.secondfont = 'gray';
					this.thirdcolor = '#efefef';
					this.thirdfont = 'gray';
					this.fourthcolor = '#efefef';
					this.fourthfont = 'gray';
					this.fifthcolor = '#efefef';
					this.fifthfont = 'gray';
				}
				else if(state == '待发货'){
					this.allcolor = '#efefef';
					this.allfont = 'gray';
					this.firstcolor = '#efefef',
					this.firstfont = 'gray';
					this.secondcolor = '#b34c26';
					this.secondfont = 'white';
					this.thirdcolor = '#efefef';
					this.thirdfont = 'gray';
					this.fourthcolor = '#efefef';
					this.fourthfont = 'gray';
					this.fifthcolor = '#efefef';
					this.fifthfont = 'gray';
				}
				else if(state == '待收货'){
					this.allcolor = '#efefef';
					this.allfont = 'gray';
					this.firstcolor = '#efefef',
					this.firstfont = 'gray';
					this.secondcolor = '#efefef';
					this.secondfont = 'gray';
					this.thirdcolor = '#b34c26';
					this.thirdfont = 'white';	
					this.fourthcolor = '#efefef';
					this.fourthfont = 'gray';
					this.fifthcolor = '#efefef';
					this.fifthfont = 'gray';
				}
				else if(state == '待退款'){
					this.allcolor = '#efefef';
					this.allfont = 'gray';
					this.firstcolor = '#efefef',
					this.firstfont = 'gray';
					this.secondcolor = '#efefef';
					this.secondfont = 'gray';
					this.thirdcolor = '#efefef';
					this.thirdfont = 'gray';
					this.fourthcolor = '#b34c26';
					this.fourthfont = 'white';
					this.fifthcolor = '#efefef';
					this.fifthfont = 'gray';
				}
				else{
					this.allcolor = '#efefef';
					this.allfont = 'gray';
					this.firstcolor = '#efefef',
					this.firstfont = 'gray';
					this.secondcolor = '#efefef';
					this.secondfont = 'gray';
					this.thirdcolor = '#efefef';
					this.thirdfont = 'gray';
					this.fourthcolor = '#efefef';
					this.fourthfont = 'gray';
					this.fifthcolor = '#b34c26';
					this.fifthfont = 'white';
				}
							
				try{
					let res = await this.api.get('/orders/select/buyer',{phone:this.user.phone,state:state});
					that.myOrderItem = res.data;
					for(let i = 0;i<this.myOrderItem.length;i++){
						this.myOrderItem[i].coverPic = this.imageSrcformat(that.myOrderItem[i].coverPic,'jpg');
					}
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 删除某个订单
			deleteOne(item,index){
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
							that.deleteOrder(item,index);
							uni.showToast({
								title: '已成功删除该订单！',
								icon: 'success',
								duration: 30000
							})
						} 
					}
				})
			},
			
			async deleteOrder(item,index){
				const that  = this;
				try{
					let res = await this.api.del('/orders/buyer/delete',{number:item.ordNumber});
					that.myOrderItem.splice(index,1);
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 获取该用户所有订单
			async getMyOrder(){
				const that  = this;
				let state = '全部';
				try{
					let res = await this.api.get('/orders/select/buyer',{phone:this.user.phone,state:state});
					that.myOrderItem = res.data;
					for(let i = 0;i<this.myOrderItem.length;i++){
						this.myOrderItem[i].coverPic = this.imageSrcformat(that.myOrderItem[i].coverPic,'jpg');
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

	/* 顶部搜索 */
	.top{
		display: flex;
		flex-direction: row;
		align-items: center;
		margin: 1rem auto;
		width: 90%;
		height: 2rem;
	}
	.top>image{
		width: 2rem;
		height: 2rem;
	}
	.top-search {
		display: flex;
		align-items: center;
		margin: 1rem auto;
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
	
	/* 订单状态导航 */
	.middle{
		width: 95vw;
		margin: 0.48rem auto;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		margin-bottom: 40rpx;
	}
	
	.middle>text{
		width: 100rpx;
		height: 66rpx;
		line-height: 66rpx;
		text-align: center;
		border-radius: 15%;
		font-weight: 400;
	}
	
	/* 我的订单列表 */
	.mybook {
		width: 95vw;
		margin: 0.48rem auto;
	}
	
	.onebook {
		width: 100%;
		height: 230rpx;
		display: flex;
		justify-content: space-between;
		margin-bottom: 75rpx;
	}
	
	.goods-box{
		width: 75%;
		height: 100%;
		border-radius: 15rpx 0 0 15rpx;
		background-color: #efefef;
		padding: 2%;
		flex-flow: row;
		justify-content: space-between;
		display: flex;
		position: relative;
		border: 6rpx solid #b34c26;
	}
	
	.oper{
		width: 18%;
		height: 230rpx;
		text-align: center;
		display: flex;
		flex-direction: column;
	}
	
	.book-state{
		background-color: #b34c26;
		color: white;
		font-weight: 600;
		font-size: 30rpx;
		height: 180rpx;
		line-height: 180rpx;
		width: 100%;
		border-radius: 0 9% 0 0;
	}
	
	.deleteBook{
		width: 100%;
		color: gray;
		font-weight: 600;
		font-size: 25rpx;
		background-color: #efefef;
		height: 90rpx;
		line-height: 90rpx;
		border-radius: 0 0 9% 0;
	}
	
	/* 商品图片 */
	.goods-img {
		height: 100%;
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
	/* 商品文字描述 */
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
	/* 价格 */
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

</style>
