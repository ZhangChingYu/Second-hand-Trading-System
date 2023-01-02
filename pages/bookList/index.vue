<template>
	<view>
		<!-- 中间预约状态分类 -->
		<view class="middle">
			<text @click="toOne(state0)" ref="全部" :style="{background: allcolor,color: allfont}">{{state0}}</text>
			<text @click="toOne(state1)" ref="待处理" :style="{background: firstcolor,color: firstfont}">{{state1}}</text>
			<text @click="toOne(state2)" ref="待发货" :style="{background: secondcolor,color: secondfont}">{{state2}}</text>
			<text @click="toOne(state3)" ref="待退款" :style="{background: thirdcolor,color: thirdfont}">{{state3}}</text>
			<text @click="toOne(state4)" ref="已卖出" :style="{background: fourthcolor,color: fourthfont}">{{state4}}</text>
			<text @click="toOne(state5)" ref="其他" :style="{background: fifthcolor,color: fifthfont}">{{state5}}</text>
		</view>
		
		<!-- 某个商品预约列表 -->
		<view class="oneList">
			<view class="oneGoods"
			v-for="(item,index) of oneBookList"
			:key="index"
			>
				<!-- 预约全部信息 -->
				<view class="bookMess">
					<!-- 卖家信息 -->
					<view class="buyerMess" @click="contactBuyer">
						<!-- 卖家头像 -->
						<image @click="" :src="'data:image/jpg;base64,' + item.avatar" class="location"></image>
						<!-- 卖家昵称 -->
						<text>{{item.nickName}}</text>
					</view>
					<!-- 商品信息 -->
					<view class="goods-box" @click="toGoodsDetail">
						<text>预约数量：</text>
						<text>{{item.count}}</text>
					</view>				
				</view>	
				
				<!-- 子分割线 -->
				<view class="subSplitLine"></view>
				
				<view class="oper">
					<view class="book-state">{{item.state}}</view>
					<view class="operBook" @click="operOne(item,index)">处理操作</view>
					<uni-popup ref="pop1" type="center" background-color="#fff">
						<view class="oneState">
							<text style="font-size: 30rpx;">请填写快递单号</text>
							<view class="subLine" ></view>
							<input type="text" v-model="deliveryId" placeholder="请输入快递单号~"/>
							<button @click="confirmOrder(item,index)">确认发货</button>
						</view>		
					</uni-popup>
					<uni-popup ref="pop2" type="center" background-color="#fff">
						<view class="twoState">
							<text style="font-size: 30rpx;">退款申请</text>
							<view class="subLine" ></view>						
							<text>退款金额： ￥ {{parseFloat(applyRefund.total).toFixed(2)}}</text>
							<text>货物状态： {{applyRefund.goodsState}}</text>
							<text>退款原因： {{applyRefund.refundReason}}</text>
							<text>申请退款时间： {{applyRefund.refundTime}}</text>
							<view class="subLine" ></view>
							<text>补充描述</text>
							<view class="subLine" ></view>
							<text style="height: 160rpx;line-height: 160rpx;">{{applyRefund.description}}</text>
							<view class="somebt">
								<button @click="refuse(item,index)" style="background-color: gray;color: white;">拒绝</button>
								<button @click="confirm(item,index)" style="background-color: #b34c26;color: white;">同意</button>
							</view>
						</view>		
					</uni-popup>
					<uni-popup ref="pop3" type="center" background-color="#fff">
						<view class="threeState">
							<text style="font-size: 30rpx;">订单详情</text>
							<view class="subLine" ></view>
							<text>收货人： {{order.consignee}} {{order.phone}}</text>
							<text>收货地址： {{order.address}}</text>
							<text>配送方式： {{order.delivery}}</text>
							<text>优惠： ￥ {{parseFloat(order.discount).toFixed(2)}}</text>
							<text>成交价格： ￥ {{parseFloat(order.total).toFixed(2)}}</text>
							<text>支付方式： {{order.pay}}</text>
							<text>订单生成时间： {{order.payTime}}</text>
							<text>发货时间： {{order.deliveryTime}}</text>
							<text v-if="noSelf">快递单号： {{order.deliveryId}}</text>
							<text v-if="isConfirm">收货时间： {{order.confirmTime}}</text>
							<button v-if="!isConfirm" class="returnPage" @click="returnPage">返回</button>
							<button v-if="isConfirm" class="deleteOrder" @click="deleteOrder(item,index)">删除订单</button>
						</view>		
					</uni-popup>
				</view>
			</view>
		</view>		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user:{},
				// 预约状态
				state0:'全部',
				allcolor:'#b34c26',
				allfont:'white',
				state1:'待处理',
				firstcolor:'#efefef',
				firstfont:'gray',
				state2:'待发货',
				secondcolor:'#efefef',
				secondfont:'gray',
				state3:'待退款',
				thirdcolor:'#efefef',
				thirdfont:'gray',
				state4:'已卖出',
				fourthcolor:'#efefef',
				fourthfont:'gray',
				state5:'其他',
				fifthcolor:'#efefef',
				fifthfont:'gray',
				// 某个商品编号
				oneNum:'',
				// 某个商品的预约列表
				oneBookList:[],
				// 快递单号
				deliveryId:'',
				// 退款申请
				applyRefund:{
					total:0,
				},
				// 订单其他信息
				order:{
					total:0,
					discount:0,
				},
				// 订单时间信息的显示
				noSelf:false,
				isConfirm:false,
				one:{},
				index:0,
			}
		},
		mounted() {
			this.user = uni.getStorageSync('user');
			this.getOneBookList();	
		},
		onLoad(option){
			this.oneNum = option.goodsNum;
		},
		methods: {
			
			openState1(item,index){
			    this.$refs.pop1[0].open('center');
				this.one = item;
				this.index = index;
			},
			
			async openState2(item,index){
				this.one = item;
				this.index = index;
				const that  = this;
				try{
					let res = await this.api.get('/orders/after/reason',{number:item.number});
					that.applyRefund = res.data;
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			    this.$refs.pop2[0].open('center');
			},
			
			async openState3(item,index){
				this.one = item;
				this.index = index;
				const that  = this;
				try{
					let res = await this.api.get('/orders/details',{number:item.number});
					that.order = res.data;
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
				if(this.order.delivery == '快递') this.noSelf = true;
				else this.noSelf = false;
			    this.$refs.pop3[0].open('center');
			},
			
			// 卖家确认发货
			async confirmOrder(item,index){
				if(this.deliveryId != ''){
					console.log(this.one.number);
					try{
						// 订单编号
						let res = await this.api.put('/orders/receiving',{number:this.one.number,deliveryId:this.deliveryId});
						this.oneBookList[this.index].state = '待收货';
						this.$refs.pop1[0].close();
					}catch(e){
						//TODO handle the exception
						that.$toast(e)
					}
				}
				else this.$toast('请填写快递单号~');	
			},
			
			// 获取该商品的预约列表
			async getOneBookList(){
				const that  = this;
				let state = '全部';
				try{
					let res = await this.api.get('/booking/select/bookings',{number:this.oneNum,state:state});
					that.oneBookList = res.data;
					//for(let i = 0;i<this.oneBookList.length;i++){
						//this.oneBookList[i].avatar = this.imageSrcformat(that.oneBookList[i].avatar,'jpg');
					//}
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 联系买家
			contactBuyer(){
				
			},
			
			// 预约状态分类
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
				else if(state == '待处理'){
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
				else if(state == '待退款'){
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
				else if(state == '已卖出'){
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
					let res = await this.api.get('/booking/select/bookings',{number:this.oneNum,state:state});
					that.oneBookList = res.data;
					for(let i = 0;i<this.oneBookList.length;i++){
						this.oneBookList[i].avatar = this.imageSrcformat(that.oneBookList[i].avatar,'jpg');
					}
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 不同的状态对应的处理操作
			operOne(item,index){
				let that = this;
				if(item.state == '待处理'){
					uni.showModal({
						title: '预约处理',
						// 提示文字
						content: '是否同意该买家的预约请求？',
						// 取消按钮的文字自定义
						cancelText: "拒绝",
						// 确认按钮的文字自定义
						confirmText: "同意",
						//删除字体的颜色
						confirmColor:'red',
						//取消字体的颜色
						cancelColor:'#000000',
						success: function(res) {
							if(res.confirm) {  
								that.confirmBook(item,index);
								that.getOneBookList();	
							} 
							else {  
								that.refuseBook(item,index);
								that.getOneBookList();	
							}  								 
						},
					})
				}
				else if(item.state == '已退款'){
					uni.showModal({
						title: '提示',
						// 提示文字
						content: '是否删除该条预约记录？',
						// 取消按钮的文字自定义
						cancelText: "取消",
						// 确认按钮的文字自定义
						confirmText: "确认",
						//删除字体的颜色
						confirmColor:'red',
						//取消字体的颜色
						cancelColor:'#000000',
						success: function(res) { 
							if(res.confirm) {
								uni.showToast({
									title: '成功删除该条预约记录！',
									icon: 'success',
									duration: 30000
								})
							} 
						},
					})
				}
				else if(item.state == '待发货'){
					this.openState1(item,index);
				}
				else if(item.state == '待退款'){
					this.openState2(item,index);
				}
				else if(item.state == '待下单'){
					this.$toast('请等待买家下单哟~');
				}
				else if(item.state == '待收货'){
					this.isConfirm = false;
					this.openState3(item,index);
				}
				else{
					this.isConfirm = true;
					this.openState3(item,index);
				}
			},
			
			// 卖家同意预约
			async confirmBook(item,index){
				const that  = this;
				try{
					let res = await this.api.put('/booking/acquire',{number:item.number});
					this.oneBookList[index].state = '待下单';
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 卖家拒绝预约
			async refuseBook(item,index){
				const that  = this;
				try{
					let res = await this.api.put('/orders/cancel/booking',{number:item.number,isbuyer:0});
					that.oneBookList.splice(index,1);
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 卖家同意退款
			async confirm(item,index){
				const that  = this;
				try{
					let res = await this.api.put('/orders/refund',{number:this.one.number});
					this.oneBookList[this.index].state = '已退款';
					this.$refs.pop2[0].close();
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 卖家拒绝退款
			async refuse(item,index){
				const that  = this;
				try{
					let res = await this.api.put('/orders/disagree',{number:this.one.number});
					this.oneBookList[this.index].state = res.data;
					this.$refs.pop2[0].close();
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			returnPage(){
				this.$refs.pop3[0].close();
			},
			
			// 删除已卖出的订单
			async deleteOrder(item,index){
				const that  = this;
				try{
					let res = await this.api.put('/orders/seller/delete',{number:this.one.number});
					that.oneBookList.splice(this.index,1);
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},		
		}
	}
</script>

<style>
	/* 子分割线 */
	.subSplitLine{
		background-color: #efefef;
		width: 94%;
		height: 6rpx;
		margin-left: 3%;
	}
	
	/* 预约状态导航 */
	.middle{
		width: 94vw;
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
	
	/* 某个商品预约列表 */
	.oneList {
		width: 90vw;
		margin: 2% 0 0 3%;
	}
	
	.oneGoods {
		width: 100%;
		display: flex;
		padding: 2%;
		justify-content: space-between;
		flex-direction: column;
		margin-bottom: 50rpx;
		border: 6rpx solid #b34c26;
		border-radius: 15rpx;
	}
	
	/* 预约全部信息 */
	.bookMess{
		width: 100%;
		height: 80rpx;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
	/* --卖家信息 */
	.buyerMess{
		width: 100%;
		height: 80rpx;
		line-height: 80rpx;
		display: flex;
		flex-direction: row;
	}
	.buyerMess>image{
		height: 66rpx;
		width: 66rpx;
		border-radius: 100%;
		margin-top: 5rpx;
	}
	.buyerMess>text{
		height: 80rpx;
		line-height: 80rpx;
		font-size: 32rpx;
		font-weight: 600;
		margin-left: 5%;
	}
	
	/* 预约数量 */
	.goods-box{
		width: 100%;
		height: 80rpx;
		text-align: center;
	}
	.goods-box>text{
		font-size: 28rpx;
		color: gray;
		height: 80rpx;
		line-height: 80rpx;
	}
	
	/* 操作按钮 */
	.oper{
		margin-top: 2%;
		width: 100%;
		height: 80rpx;
		padding-top: 7rpx;
		display: flex;
		text-align: center;
		flex-direction: row;
		justify-content: space-around;
	}
	.book-state{
		width: 45%;
		height: 66rpx;
		line-height: 66rpx;
		background-color: #efefef;
		border-radius: 15rpx;
		color: black;
		font-size: 32rpx;
	}
	.operBook{
		width: 45%;
		height: 66rpx;
		line-height: 66rpx;
		background-color: #b34c26;
		border-radius: 15rpx;
		color: white;
		font-size: 32rpx;
	}
	.oneState{
		display: flex;
		flex-direction: column;
		text-align: center;
		width: 666rpx;
		height: 466rpx;
	}
	
	.subLine{
		background-color: #b34c26;
		width: 78%;
		height: 6rpx;
		margin-left: 11%;
	}
	
	.oneState>text{
		height: 80rpx;
		line-height: 80rpx;
		padding: 10rpx;
	}
	
	.oneState>input{
		height: 100rpx;
		width: 486rpx;
		margin-left: 90rpx;
		padding: 10rpx;
		background-color: #efefef;
		color: gray;
		font-size: 32rpx;
		margin-top: 88rpx;
		margin-bottom: 88rpx;
	}
	
	.oneState>button{
		background-color: #b34c26;
		color: white;
		width: 250rpx;
		height: 60rpx;
		font-size: 28rpx;
		line-height: 60rpx;
		text-align: center;
	}
	
	.twoState{
		display: flex;
		flex-direction: column;
		text-align: center;
		width: 666rpx;
		height: 850rpx;
	}
	
	.twoState>text{
		height: 80rpx;
		line-height: 80rpx;
		padding: 10rpx;
	}
	
	.somebt{
		display: flex;
		flex-direction: row;
		text-align: center;
		justify-content: space-between;
	}
	
	.somebt>button{
		width: 250rpx;
		height: 60rpx;
		font-size: 28rpx;
		line-height: 60rpx;
		text-align: center;
	}
	
	.threeState{
		display: flex;
		flex-direction: column;
		text-align: center;
		width: 666rpx;
		height: 975rpx;
	}
	
	.threeState>text{
		height: 60rpx;
		line-height: 60rpx;
		padding: 5rpx;
	}
	
	.returnPage{
		margin-top: 80rpx;
		width: 250rpx;
		height: 60rpx;
		font-size: 28rpx;
		line-height: 60rpx;
		text-align: center;
		background-color: gray;
		color: white;
	}
	
	.deleteOrder{
		margin-top: 10rpx;
		width: 250rpx;
		height: 60rpx;
		font-size: 28rpx;
		line-height: 60rpx;
		text-align: center;
		background-color: #b34c26;
		color: white;
	}
</style>
