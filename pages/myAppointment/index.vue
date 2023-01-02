<template>
	<view>
		
		<!-- 搜索框 -->
		<view class="top">
			<image class="btReturn" src="../../static/image/previous.png" @click="toMe"></image>
			<view class="top-search">
				<text @click="toSomeOf"></text>
				<input placeholder="搜索预约" v-model="keyWord" value="keyWord"/>
			</view>
		</view>
		
		<!-- 中间预约状态分类 -->
		<view class="middle">
			<text @click="toOne(state0)" ref="全部" :style="{background: allcolor,color: allfont}">{{state0}}</text>
			<text @click="toOne(state1)" ref="已预约" :style="{background: firstcolor,color: firstfont}">{{state1}}</text>
			<text @click="toOne(state2)" ref="待下单" :style="{background: secondcolor,color: secondfont}">{{state2}}</text>
			<text @click="toOne(state3)" ref="已拒绝" :style="{background: thirdcolor,color: thirdfont}">{{state3}}</text>
		</view>
		
		<!-- 我的预约 -->
		<view class="mybook">
			<!-- 已预约物品 -->
			<view class="onebook"
			v-for="(item,index) of mybookItem"
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
				// 预约状态state（已预约、待下单、已拒绝）mybookItem:[number,name,coverPic,price,state][商品编号，商品名称，商品图片，价格，预约状态]
				mybookItem:[],
				// 全部预约
				allBook:[],
				// 预约状态
				state0:'全部',
				allcolor:'#b34c26',
				allfont:'white',
				state1:'已预约',
				firstcolor:'#efefef',
				firstfont:'gray',
				state2:'待下单',
				secondcolor:'#efefef',
				secondfont:'gray',
				state3:'已拒绝',
				thirdcolor:'#efefef',
				thirdfont:'gray',
				// 搜索栏关键词
				keyWord:'',
			}
		},
		mounted() {
			this.user = uni.getStorageSync('user');
			this.getMybook();	
			this.allBook = that.mybookItem;
		},
		onShow(){
			this.allcolor = '#b34c26';
			this.allfont = 'white';
			this.firstcolor = '#efefef',
			this.firstfont = 'gray';
			this.secondcolor = '#efefef';
			this.secondfont = 'gray';
			this.thirdcolor = '#efefef';
			this.thirdfont = 'gray';
			this.getMybook();
		},
		methods: {
			toMe(){
				uni.redirectTo({
					url:'/pages/my/index'
				})
			},
			
			// 商品详情页(该商品编号)
			toGoodsDetail(number){
				uni.navigateTo({
					url:'/pages/detail/index?number='+ number
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
				}
				else if(state == '已预约'){
					this.allcolor = '';
					this.allfont = 'gray';
					this.firstcolor = '#b34c26',
					this.firstfont = 'white';
					this.secondcolor = '#efefef';
					this.secondfont = 'gray';
					this.thirdcolor = '#efefef';
					this.thirdfont = 'gray';	
				}
				else if(state == '待下单'){
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
					let res = await this.api.get('/booking/select/buyer',{phone:this.user.phone,state:state});
					that.mybookItem = res.data;
					for(let i = 0;i<this.mybookItem.length;i++){
						this.mybookItem[i].coverPic = this.imageSrcformat(that.mybookItem[i].coverPic,'jpg');
					}
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 删除某个预约
			deleteOne(item,index){
				let that = this;
				uni.showModal({
					title: '提示',
					// 提示文字
					content: '确认删除该条预约信息吗？',
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
							if(item.state == '已拒绝'){
								that.deleteBook(item,index);
							}
							else  that.cancelBook(item,index);
							
							uni.showToast({
								title: '已成功删除该预约！',
								icon: 'success',
								duration: 30000
							})
						} 
					}
				})
			},
			
			async deleteBook(item,index){
				const that  = this;
				try{
					let res = await this.api.del('/booking/buyer/delete',{number:item.bookNum});
					that.mybookItem.splice(index,1);
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			async cancelBook(item,index){
				const that  = this;
				let id = 1;
				try{
					let res = await this.api.put('/orders/cancel/booking',{number:item.bookNum,isbuyer:id});
					that.mybookItem.splice(index,1);
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 预约状态相关操作
			toOper(item){
				//let data = encodeURIComponent(JSON.stringify(item));
				var oneitem = JSON.stringify(item)
				// 已预约状态查看商品详情
				if(item.state=="已预约" || item.state=="已拒绝"){
					uni.navigateTo({
						url:'/pages/detail/index?number='+ item.number
					})
				}
				// 待下单状态到达下单界面
				else{
					uni.navigateTo({
						url:'/pages/payment/index?item=' + encodeURIComponent(oneitem)
					})
				}
			},
			
			// 搜索相关我的预约(关键词：商品名称，卖家手机号)
			async toSomeOf(){
				const that  = this;
				try{
					let res = await this.api.get('/booking/fuzzy/name',{name:this.keyWord,phone:this.user.phone,isbuyer:1});
					that.mybookItem = res.data;
					for(let i = 0;i<this.mybookItem.length;i++){
						this.mybookItem[i].coverPic = this.imageSrcformat(that.mybookItem[i].coverPic,'jpg');
					}
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 获取该用户所有预约
			async getMybook(){
				const that  = this;
				let state = '全部';
				try{
					let res = await this.api.get('/booking/select/buyer',{phone:this.user.phone,state:state});
					that.mybookItem = res.data;
					for(let i = 0;i<this.mybookItem.length;i++){
						this.mybookItem[i].coverPic = this.imageSrcformat(that.mybookItem[i].coverPic,'jpg');
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
	
	/* 预约状态导航 */
	.middle{
		width: 86vw;
		margin: 0.48rem auto;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		margin-bottom: 40rpx;
	}
	
	.middle>text{
		width: 125rpx;
		height: 66rpx;
		line-height: 66rpx;
		text-align: center;
		border-radius: 15%;
		font-weight: 400;
	}
	
	/* 我的预约列表 */
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
