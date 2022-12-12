<template>
	<view>

		<!-- 中间举报状态分类 -->
		<view class="middle">
			<image class="btReturn" src="../../static/image/previous.png" @click="toMe"></image>
			<text @click="toOne(state0)" ref="全部" :style="{background: allcolor,color: allfont}">{{state0}}</text>
			<text @click="toOne(state1)" ref="已通过" :style="{background: firstcolor,color: firstfont}">{{state1}}</text>
			<text @click="toOne(state2)" ref="待审核" :style="{background: secondcolor,color: secondfont}">{{state2}}</text>
			<text @click="toOne(state3)" ref="未通过" :style="{background: thirdcolor,color: thirdfont}">{{state3}}</text>
		</view>
		
		<!-- 我的举报 -->
		<view class="myReport">
			<!-- 已举报物品 -->
			<view class="oneReport"
			v-for="(item,index) of myReportItem"
			:key="index"
			>
				<!-- 商品全部信息 -->
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
					<view class="goods-box" @click="toGoodsDetail">
						<!-- 图片 -->
						<view class="goods-img">
							<image :src="item.coverPic" mode="widthFix" @error="doDefault"></image>
						</view>						
						<!-- 信息 -->
						<view class="goods-msg">
							<text class="detail-text">{{item.name}}</text>
							<text class="price">￥ {{item.price.toFixed(2)}}</text>
						</view>
					</view>				
				</view>	
				
				<!-- 子分割线 -->
				<view class="subSplitLine"></view>
				
				<view class="reportReason">
					<text>举报原因：</text>
					<text>{{item.reason}}</text>
				</view>
				
				<!-- 子分割线 -->
				<view class="subSplitLine"></view>
				
				<view class="oper">
					<view class="report-state">{{item.state}}</view>
					<view class="deleteReport" @click="deleteOne(item,index)">删除</view>
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
				// 举报进度state（待审核，已通过，未通过）myReportItem:[number,name,coverPic,price,state,reason][商品编号，商品名称，商品图片，价格，举报进度,举报原因]
				myReportItem:[
					{
											number:"B3267559776586",
											name:'参加培训班',
											coverPic:'https://gw.alicdn.com/bao/uploaded///asearch.alicdn.com/bao/uploaded/O1CN015rH4tD2LKkJrMhIlx_!!0-item_pic.jpg_300x300q90.jpg_.webp',
											price:1250,
											state:'待审核',
											reason:'广告或垃圾信息',
										},
										{
											number:"B1637559776586",
											name:'使图片的宽高完全拉伸至填满 image 元素',
											coverPic:'https://gw.alicdn.com/bao/uploaded/i1/510160174/O1CN01gGdwFj1D9jhVnZgEo_!!0-saturn_solar.jpg_300x300q90.jpg_.webp',
											price:268,
											state:'已通过',
											reason:'广告或垃圾信息',
										},
										{
																number:"B3267559776586",
																name:'参加培训班',
																coverPic:'https://gw.alicdn.com/bao/uploaded///asearch.alicdn.com/bao/uploaded/O1CN015rH4tD2LKkJrMhIlx_!!0-item_pic.jpg_300x300q90.jpg_.webp',
																price:1250,
																state:'未通过',
																reason:'广告或垃圾信息',
															}
				],
				// 卖家信息：avatar(头像)，userName(昵称)
				sellerMess:{
					avatar:"../../static/image/avatar.png",
					userName:"徐必成",
				},
				// 举报状态
				state0:'全部',
				allcolor:'#b34c26',
				allfont:'white',
				state1:'已通过',
				firstcolor:'#efefef',
				firstfont:'gray',
				state2:'待审核',
				secondcolor:'#efefef',
				secondfont:'gray',
				state3:'未通过',
				thirdcolor:'#efefef',
				thirdfont:'gray',
			}
		},
		mounted() {
			this.user = uni.getStorageSync('user');
			this.getMyReport();	
		},
		methods: {
			toMe(){
				uni.redirectTo({
					url:'/pages/me/index'
				})
			},
			
			// 商品详情页
			toGoodsDetail(number){
				uni.navigateTo({
					url:'/pages/detail/index?goodsNum='+ number
				})
			},
			
			// 举报状态分类
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
				}
				else if(state == '已通过'){
					this.allcolor = '';
					this.allfont = 'gray';
					this.firstcolor = '#b34c26',
					this.firstfont = 'white';
					this.secondcolor = '#efefef';
					this.secondfont = 'gray';
					this.thirdcolor = '#efefef';
					this.thirdfont = 'gray';	
				}
				else if(state == '待审核'){
					this.allcolor = '#efefef';
					this.allfont = 'gray';
					this.firstcolor = '#efefef',
					this.firstfont = 'gray';
					this.secondcolor = '#b34c26';
					this.secondfont = 'white';
					this.thirdcolor = '#efefef';
					this.thirdfont = 'gray';	
				}
				else{
					this.allcolor = '#efefef';
					this.allfont = 'gray';
					this.firstcolor = '#efefef',
					this.firstfont = 'gray';
					this.secondcolor = '#efefef';
					this.secondfont = 'gray';
					this.thirdcolor = '#b34c26';
					this.thirdfont = 'white';	
				}
				
				try{
					that.myReportItem = await this.api.get('/',{phone:this.user.phone,state:state})			
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 删除某条举报记录
			deleteOne(item,index){	
				if(item.state == '待审核'){
					uni.showModal({
						title: '提示',
						// 提示文字
						content: '该举报记录正在审核，删除将会取消并删除该条举报记录，是否确认删除？',
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
								this.cancelReport(item,index);
								uni.showToast({
									title: '已成功取消并删除该举报记录！',
									icon: 'success',
									duration: 30000
								})	
							} 										 
						}
					})
				}
				else{
					uni.showModal({
						title: '提示',
						// 提示文字
						content: '确认删除该条举报记录吗？',
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
								this.deleteReport(item,index);
								uni.showToast({
									title: '已成功删除该举报记录！',
									icon: 'success',
									duration: 30000
								})	
							} 	
						}
					})
				}
			},
			
			async deleteReport(item,index){
				const that  = this;
				try{
					let res = await this.api.del('/',{});
					that.myReportItem.splice(index,1);
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			async cancelReport(item,index){
				const that  = this;
				try{
					let res = await this.api.put('/',{});
					that.myReportItem.splice(index,1);
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 获取该用户所有举报记录
			async getMyReport(){
				const that  = this;
				try{
					that.myReportItem = await this.api.get('/',{phone:this.user.phone})				
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
	
	/* 举报状态导航 */
	.middle{
		width: 94vw;
		margin: 0.48rem auto;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		margin-bottom: 40rpx;
	}
	
	.middle>image{
		width: 2rem;
		height: 2rem;
	}
	
	.middle>text{
		width: 125rpx;
		height: 66rpx;
		line-height: 66rpx;
		text-align: center;
		border-radius: 15%;
		font-weight: 400;
	}
	
	/* 我的举报列表 */
	.myReport {
		width: 90vw;
		margin: 2% 0 0 3%;
	}
	
	.oneReport {
		width: 100%;
		display: flex;
		padding: 2%;
		justify-content: space-between;
		flex-direction: column;
		margin-bottom: 50rpx;
		border: 6rpx solid #b34c26;
		border-radius: 15rpx;
	}
	
	/* 商品全部信息 */
	.orderMess{
		width: 100%;
		height: 350rpx;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
	}
	/* --卖家信息 */
	.sellerMess{
		width: 100%;
		height: 93rpx;
		line-height: 93rpx;
		display: flex;
		flex-direction: row;
	}
	.sellerMess>image{
		height: 88rpx;
		width: 88rpx;
		border-radius: 100%;
		margin-top: 5rpx;
	}
	.sellerMess>text{
		height: 93rpx;
		line-height: 93rpx;
		font-size: 32rpx;
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
	
	/* 举报原因 */
	.reportReason{
		width: 100%;
		height: 80rpx;
		text-align: center;
	}
	.reportReason>text{
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
		display: flex;
		text-align: center;
		flex-direction: row;
		justify-content: space-around;
	}
	.report-state{
		width: 45%;
		height: 80rpx;
		line-height: 80rpx;
		background-color: #b34c26;
		border-radius: 15rpx;
		color: white;
		font-size: 32rpx;
	}
	.deleteReport{
		width: 45%;
		height: 80rpx;
		line-height: 80rpx;
		background-color: #efefef;
		border-radius: 15rpx;
		color: black;
		font-size: 32rpx;
	}
</style>
