<template>
	<view>
		
		<!-- 搜索框 -->
		<view class="top">
			<image class="btReturn" src="../../static/image/previous.png" @click="toMe"></image>
			<view class="top-search">
				<text @click="toSomeOf"></text>
				<input placeholder="搜索商品" v-model="keyWord" value="keyWord"/>
			</view>
		</view>
		
		<!-- 中间商品状态分类 -->
		<view class="middle">
			<text @click="toOne(state0)" ref="全部" :style="{background: allcolor,color: allfont}">{{state0}}</text>
			<text @click="toOne(state1)" ref="待审核" :style="{background: firstcolor,color: firstfont}">{{state1}}</text>
			<text @click="toOne(state2)" ref="待预约" :style="{background: secondcolor,color: secondfont}">{{state2}}</text>
			<text @click="toOne(state3)" ref="查预约" :style="{background: thirdcolor,color: thirdfont}">{{state3}}</text>
			<text @click="toOne(state4)" ref="已下架" :style="{background: fourthcolor,color: fourthfont}">{{state4}}</text>
		</view>
		
		<!-- 我的商品 -->
		<view class="mybook">
			<!-- 已发布商品 -->
			<view class="onebook"
			v-for="(item,index) of myGoodsItem"
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
						<text class="price">￥{{item.price.toFixed(2)}}</text>
					</view>
				</view>
				<view class="book-state" @click="toOper(item)">{{item.goodsState}}</view>
			</view>
		</view>		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user:{},
				// 商品状态goodsState（待审核、待预约、查预约、已下架）myGoodsItem:[number,name,coverPic,price,goodsState][商品编号，商品名称，商品图片，价格，商品状态]
				myGoodsItem:[
					{
											number:"B3267559776586",
											name:'参加培训班',
											coverPic:'https://gw.alicdn.com/bao/uploaded///asearch.alicdn.com/bao/uploaded/O1CN015rH4tD2LKkJrMhIlx_!!0-item_pic.jpg_300x300q90.jpg_.webp',
											price:1250,
											goodsState:'待审核',
										},
										{
											number:"B1637559776586",
											name:'使图片的宽高完全拉伸至填满 image 元素',
											coverPic:'https://gw.alicdn.com/bao/uploaded/i1/510160174/O1CN01gGdwFj1D9jhVnZgEo_!!0-saturn_solar.jpg_300x300q90.jpg_.webp',
											price:268,
											goodsState:'待预约',
										},
										{
																number:"B3267559776586",
																name:'参加培训班',
																coverPic:'https://gw.alicdn.com/bao/uploaded///asearch.alicdn.com/bao/uploaded/O1CN015rH4tD2LKkJrMhIlx_!!0-item_pic.jpg_300x300q90.jpg_.webp',
																price:1250,
																goodsState:'查预约',
															},
															{
																number:"B1637559776586",
																name:'使图片的宽高完全拉伸至填满 image 元素',
																coverPic:'https://gw.alicdn.com/bao/uploaded/i1/510160174/O1CN01gGdwFj1D9jhVnZgEo_!!0-saturn_solar.jpg_300x300q90.jpg_.webp',
																price:268,
																goodsState:'已下架',
															},
				],
				// 商品状态
				state0:'全部',
				allcolor:'#b34c26',
				allfont:'white',
				state1:'待审核',
				firstcolor:'#efefef',
				firstfont:'gray',
				state2:'待预约',
				secondcolor:'#efefef',
				secondfont:'gray',
				state3:'查预约',
				thirdcolor:'#efefef',
				thirdfont:'gray',
				state4:'已下架',
				fourthcolor:'#efefef',
				fourthfont:'gray',
				// 搜索栏关键词
				keyWord:'',
			}
		},
		mounted() {
					this.user = uni.getStorageSync('user');
					this.getMyGoods();					
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
				}
				else if(state == '待审核'){
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
				}
				else if(state == '待预约'){
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
				}
				else if(state == '查预约'){
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
					this.fourthcolor = '#b34c26';
					this.fourthfont = 'white';
				}
				
				try{
					that.myGoodsItem = await this.api.get('/my/products',{phone:this.user.phone,state:state})			
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 商品状态相关操作
			toOper(item){
				//let data = encodeURIComponent(JSON.stringify(item));
				var oneitem = JSON.stringify(item)
				// 待审核状态查看商品发布信息详情
				if(item.goodsState=="待审核"){
					
				}
				// 待预约状态到达商品详情界面
				else if(item.goodsState=="待预约"){
					uni.navigateTo({
						url:'/pages/detail/index?goodsNum='+ item.number
					})
				}
				// 查预约状态到达该商品预约列表界面
				else if(item.goodsState=="查预约"){
					uni.navigateTo({
						url:'/pages/bookList/index?goodsNum='+ item.number
					})
				}
				// 已下架状态
				else{
					this.$toast('商品已下架，相关信息已删除！')
				}
			},
			
			// 搜索相关我的商品
			async toSomeOf(){
				const that  = this;
				try{
					that.myGoodsItem = await this.api.get('/my/products/key',{phone:this.user.phone,keyword:this.keyWord})			
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 获取该用户所有商品
			async getMyGoods(){
				const that  = this;
				try{
					that.myGoodsItem = await this.api.get('/my/products',{phone:this.user.phone})				
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
	
	/* 商品状态导航 */
	.middle{
		width: 86vw;
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
	
	/* 我的商品列表 */
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
		border: 6rpx solid #b34c26;
		position: relative;
	}
	
	.book-state{
		width: 18%;
		height: 100%;
		padding: 2%;
		text-align: center;
		background-color: #efefef;
		color: gray;
		border: 6rpx solid #b34c26;
		font-weight: 600;
		font-size: 36rpx;
		line-height: 230rpx;
		border-radius: 0 9% 9% 0;
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
