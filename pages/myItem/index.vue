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
			<text @click="toOne(state5)" ref="未通过" :style="{background: fifthcolor,color: fifthfont}">{{state5}}</text>
			<text @click="toOne(state6)" ref="已售空" :style="{background: sixthcolor,color: sixthfont}">{{state6}}</text>
			<text @click="toOne(state7)" ref="已违规" :style="{background: seventhcolor,color: seventhfont}">{{state7}}</text>

		</view>
		
		<!-- 我的商品 -->
		<view class="mybook">
			<!-- 已发布商品 -->
			<view class="onebook"
			v-for="(item,index) of myGoodsItem"
			:key="index">
				<view class="goods-box" @click="toOper(item)">
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
			</view>
			<uni-popup ref="popCenter" type="center" background-color="#fff">
				<view class="oneState">
					<text style="font-size: 36rpx;">商品状态</text>
					<view class="subLine" ></view>
					<text style="font-size: 32rpx;height: 240rpx;line-height: 240rpx;">{{state}}</text>
					<view class="oneOper">
						<button style="background-color: green;color: white;" v-if="isCheck" @click="updateOne">更新信息</button>
						<button style="background-color: #b34c26;color: white;" v-if="haveBook" @click="toBook">查看预约</button>
						<button style="background-color: #efefef;color: black;" v-if="isCheck" @click="offOne">下架商品</button>
						<button style="background-color: #b34c26;color: white;width: 222rpx;" v-if="isDown" @click="recoveryOne">恢复上架</button>
						<button style="background-color: #efefef;color: black;width: 222rpx;" v-if="isDel" @click="deleteOne">删除商品</button>
					</view>
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
				// 商品状态goodsState（待审核、待预约、查预约、已下架）myGoodsItem:[number,name,coverPic,price,goodsState][商品编号，商品名称，商品图片，价格，商品状态]
				state:'',
				status:0,
				myGoodsItem:[],
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
				state5:'未通过',
				fifthcolor:'#efefef',
				fifthfont:'gray',
				state6:'已售空',
				sixthcolor:'#efefef',
				sixthfont:'gray',
				state7:'已违规',
				seventhcolor:'#efefef',
				seventhfont:'gray',
				// 搜索栏关键词
				keyWord:'',
				// 是否有预约
				haveBook: false,
				// 审核是否通过
				isCheck: false,
				// 是否恢复主动下架的商品
				isDown: false,
				// 能否删除
				isDel: false,
				// 一件商品
				one:{},
			}
		},
		mounted() {
			this.user = uni.getStorageSync('user');
			this.getMyGoods();	
		},
		onShow(){
			this.getMyGoods();	
		},
		methods: {
			toMe(){
				uni.redirectTo({
					url:'/pages/my/index'
				})
			},
			
			// 查看预约
			toBook(){
				uni.navigateTo({
					url:'/pages/bookList/index?goodsNum=' + this.one.number
				})
				this.$refs.popCenter.close();
			},
			
			// 更新商品信息
			updateOne(){
				var oneitem = JSON.stringify(this.one);
				uni.navigateTo({
					url:'/pages/updateMess/index?goods=' + oneitem
				})
				this.$refs.popCenter.close();
			},
			
			// 恢复上架
			async recoveryOne(){
				const that  = this;
				try{
					let res = await this.api.put('/my/product/on',{number:this.one.number});
					if(res == 201){
						uni.showToast({
							title: '恢复上架成功！',
							icon: 'success',
							duration: 30000
						})
					}
					else if(res == 422){
						uni.showToast({
							title: '商品不存在/商品回復上架失敗！',
							icon: 'warning',
							duration: 30000
						})
					}
					else{
						uni.showToast({
							title: '商品為不可下架狀態！',
							icon: 'warning',
							duration: 30000
						})
					}
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
				
				this.$refs.popCenter.close();
			},
			
			// 下架商品
			async offOne(){
				const that  = this;
				try{
					let res = await this.api.put('/my/product/off',{number:this.one.number});
					if(res == 201){
						uni.showToast({
							title: '下架成功！',
							icon: 'success',
							duration: 30000
						})
					}
					else if(res == 422){
						uni.showToast({
							title: '商品不存在/商品下架失敗！',
							icon: 'warning',
							duration: 30000
						})
					}
					else if(res == 300){
						uni.showToast({
							title: '商品為不可下架狀態！',
							icon: 'warning',
							duration: 30000
						})
					}
					else{
						uni.showToast({
							title: '商品狀態異常！',
							icon: 'warning',
							duration: 30000
						})
					}
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
				
				this.$refs.popCenter.close();
			},
			
			// 删除商品
			async deleteOne(){
				const that  = this;
				try{
					let res = await this.api.del('/my/product',{number:this.one.number});
					if(res == 204){
						uni.showToast({
							title: '删除成功！',
							icon: 'success',
							duration: 30000
						})
					}
					else if(res == 422){
						uni.showToast({
							title: '商品不存在！',
							icon: 'warning',
							duration: 30000
						})
					}
					else if(res == 400){
						uni.showToast({
							title: '刪除失敗！',
							icon: 'warning',
							duration: 30000
						})
					}
					else{
						uni.showToast({
							title: '商品為不可刪除狀態(需先下架)！',
							icon: 'warning',
							duration: 30000
						})
					}
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
				
				this.$refs.popCenter.close();
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
					this.sixthcolor = '#efefef';
					this.sixthfont = 'gray';
					this.seventhcolor = '#efefef';
					this.seventhfont = 'gray';
					this.status = -1;
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
					this.fifthcolor = '#efefef';
					this.fifthfont = 'gray';
					this.sixthcolor = '#efefef';
					this.sixthfont = 'gray';
					this.seventhcolor = '#efefef';
					this.seventhfont = 'gray';
					this.status = 1;
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
					this.fifthcolor = '#efefef';
					this.fifthfont = 'gray';
					this.sixthcolor = '#efefef';
					this.sixthfont = 'gray';
					this.seventhcolor = '#efefef';
					this.seventhfont = 'gray';
					this.status = 0;
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
					this.fifthcolor = '#efefef';
					this.fifthfont = 'gray';
					this.sixthcolor = '#efefef';
					this.sixthfont = 'gray';
					this.seventhcolor = '#efefef';
					this.seventhfont = 'gray';
					this.status = 5;
				}
				else if(state == '已下架'){
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
					this.sixthcolor = '#efefef';
					this.sixthfont = 'gray';
					this.seventhcolor = '#efefef';
					this.seventhfont = 'gray';
					this.status = 6;
				}
				else if(state == '未通过'){
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
					this.sixthcolor = '#efefef';
					this.sixthfont = 'gray';
					this.seventhcolor = '#efefef';
					this.seventhfont = 'gray';
					this.status = 2;
				}
				else if(state == '已售空'){
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
					this.fifthcolor = '#efefef';
					this.fifthfont = 'gray';
					this.sixthcolor = '#b34c26';
					this.sixthfont = 'white';
					this.seventhcolor = '#efefef';
					this.seventhfont = 'gray';
					this.status = 4;
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
					this.fifthcolor = '#efefef';
					this.fifthfont = 'gray';
					this.sixthcolor = '#efefef';
					this.sixthfont = 'gray';
					this.seventhcolor = '#b34c26';
					this.seventhfont = 'white';
					this.status = 3;
				}
				
				if(this.status == -1){
					this.getMyGoods();
				}
				else{
					try{
						that.myGoodsItem = await this.api.get('/my/products',{phone:this.user.phone,status:this.status});
						for(let i = 0;i<this.myGoodsItem.length;i++){
							this.myGoodsItem[i].coverPic = this.imageSrcformat(that.myGoodsItem[i].coverPic,that.myGoodsItem[i].coverPicFormat);
						}
					}catch(e){
						//TODO handle the exception
						that.$toast(e)
					}
				}
			},
			
			// 商品状态相关操作
			toOper(item){
				this.one = item;
				this.operDetail(item);
				this.$refs.popCenter.open('center');
			},
			
			operDetail(item){
				//let data = encodeURIComponent(JSON.stringify(item));
				var oneitem = JSON.stringify(item)
				if(item.status==0){
					this.state = '商品审核已通过，正在等在预约~';
					this.isCheck = true;
					this.haveBook = false;
					this.isDown = false;
					this.isDel = false;
				}
				else if(item.status==1){
					this.state = '商品正在等待审核，请稍等~';
					this.isCheck = false;
					this.haveBook = false;
					this.isDown = false;
					this.isDel = false;
				}
				else if(item.status==2){
					this.state = '非常抱歉，商品审核未通过！';
					this.isCheck = false;
					this.haveBook = false;
					this.isDown = false;
					this.isDel = false;
				}
				else if(item.status==3){
					this.state = '商品被举报违规，已强制下架！';
					this.isCheck = false;
					this.haveBook = false;
					this.isDown = false;
					this.isDel = true;
				}
				else if(item.status==4){
					this.state = '商品超级抢手，已售空！';
					this.isCheck = true;
					this.haveBook = false;
					this.isDown = false;
					this.isDel = false;
				}
				else if(item.status==5){
					this.state = '商品已被预约，可查看相关预约详情~';
					this.isCheck = true;
					this.haveBook = true;
					this.isDown = false;
					this.isDel = false;
				}
				else{
					this.state = '商品已被您主动下架~';
					this.isDown = true;
					this.isCheck = false;
					this.haveBook = false;
					this.isDel = true;
				}
			},
			
			// 搜索相关我的商品
			async toSomeOf(){
				const that  = this;
				try{
					that.myGoodsItem = await this.api.get('/my/products/key',{phone:this.user.phone,keyword:this.keyWord});
					for(let i = 0;i<this.myGoodsItem.length;i++){
						this.myGoodsItem[i].coverPic = this.imageSrcformat(that.myGoodsItem[i].coverPic,that.myGoodsItem[i].coverPicFormat);
					}
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 获取该用户所有商品
			async getMyGoods(){
				const that  = this;
				try{
					that.myGoodsItem = await this.api.get('/my/products',{phone:this.user.phone});
					for(let i = 0;i<this.myGoodsItem.length;i++){
						this.myGoodsItem[i].coverPic = this.imageSrcformat(that.myGoodsItem[i].coverPic,that.myGoodsItem[i].coverPicFormat);
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
	
	/* 商品状态导航 */
	.middle{
		width: 96vw;
		margin: 0.48rem auto;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		margin-bottom: 40rpx;
	}
	
	.middle>text{
		width: 78rpx;
		height: 66rpx;
		line-height: 66rpx;
		text-align: center;
		border-radius: 15%;
		font-weight: 400;
		font-size: 21rpx;
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
		width: 100%;
		height: 100%;
		border-radius: 15rpx;
		background-color: #efefef;
		padding: 2%;
		flex-flow: row;
		justify-content: space-between;
		display: flex;
		border: 6rpx solid #b34c26;
		position: relative;
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
	
	.oneState{
		display: flex;
		flex-direction: column;
		text-align: center;
		width: 666rpx;
		height: 466rpx;
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
	}
	
	.oneOper{
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
	
	.oneOper>button{
		width: 200rpx;
		height: 80rpx;
		line-height: 80rpx;
		font-size: 30rpx;
		border-radius: 9%;
	}

</style>
