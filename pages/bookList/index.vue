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
						<image @click="" :src="item.avatar" class="location"></image>
						<!-- 卖家昵称 -->
						<text>{{item.userName}}</text>
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
							<input />
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
				oneBookList:[
					{
						avatar:"../../static/image/avatar.png",
						userName:"徐必成",
						count:2,
						state:'待处理',
					},
					{
						avatar:"../../static/image/avatar.png",
						userName:"徐必成",
						count:5,
						state:'待下单',
					},
					{
						avatar:"../../static/image/avatar.png",
						userName:"徐必成",
						count:6,
						state:'待发货',
					},
					{
						avatar:"../../static/image/avatar.png",
						userName:"徐必成",
						count:7,
						state:'待收货',
					},
					{
						avatar:"../../static/image/avatar.png",
						userName:"徐必成",
						count:8,
						state:'待退款',
					},
					{
						avatar:"../../static/image/avatar.png",
						userName:"徐必成",
						count:9,
						state:'已退款',
					},
					{
						avatar:"../../static/image/avatar.png",
						userName:"徐必成",
						count:10,
						state:'已卖出',
					},
					{
						avatar:"../../static/image/avatar.png",
						userName:"徐必成",
						count:4,
						state:'已拒绝',
					},
				],
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
			
			openState1(){
			    this.$refs.pop1.open('center');
			},
			
			// 获取该商品的预约列表
			async getOneBookList(){
				const that  = this;
				let state = '全部';
				try{
					that.oneBookList = await this.api.get('/booking/select/bookings',{number:this.oneNum,state:state})				
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
					that.oneBookList = await this.api.get('/booking/select/bookings',{number:this.oneNum,state:state})			
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 不同的状态对应的处理操作
			operOne(item,index){
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
								this.confirmBook(item,index);
								this.getOneBookList();	
							} 
							else {  
								this.refuseBook(item,index);
								this.getOneBookList();	
							}  								 
						},
					})
				}
				else if(item.state == '已拒绝' || item.state == '已卖出' || item.state == '已退款'){
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
								
							} 
						},
					})
				}
				else if(item.state == '待发货'){
					this.openState1();
				}
			},
			
			// 卖家同意预约
			async confirmBook(item,index){
				const that  = this;
				try{
					let res = await this.api.put('/booking/acquire',{number:this.item.bookNum});
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 卖家拒绝预约
			async refuseBook(item,index){
				const that  = this;
				try{
					let res = await this.api.put('/orders/cancel/booking',{number:this.item.bookNum,isbuyer:0});
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
</style>
