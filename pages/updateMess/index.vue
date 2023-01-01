<template>
	<view class="updateMess">
		
		<view class="main">
			<!-- 图片 -->
			<view class="goodsImg">
				<image :src="coverPic" mode="widthFix" @error="doDefault"></image>
			</view>
			
			<!-- 相关信息 -->
			<view class="name">
				<text class="label">商品名称</text>
				<input type="text" v-model="onename"/>
			</view>
			
			<!-- 子分割线 -->
			<view class="subSplitLine"></view>
			
			<view class="storage">
				<text class="label">商品库存</text>
				<input type="text" v-model="storage" auto-focus/>
			</view>
			
			<!-- 子分割线 -->
			<view class="subSplitLine"></view>
			
			<view class="price">
				<text class="label">商品价格</text>
				<input type="text" v-model="parseFloat(price).toFixed(2)"/>
			</view>
			
			<!-- 子分割线 -->
			<view class="subSplitLine"></view>
			
			<view class="address">
				<text class="label">发货地址</text>
				<input type="text" v-model="address" value="address" placeholder="请输入该商品的发货地址~"/>
			</view>
			
			<!-- 分割线 -->
			<view class="splitLine"></view>
			
			<!-- 补充描述 -->
			<view class="description">
				<text class="descriptionTip">补充描述</text>
				<uni-easyinput type="textarea" autoHeight v-model="intro" placeholder="补充描述,有助于买家更好的了解商品"></uni-easyinput>
			</view>
			
			<!-- 分割线 -->
			<view class="splitLine"></view>
			
			<!-- 操作按钮 -->
			<view class="somebt">
				<button @click="toReturn" style="background-color: gray;color: black;">返回</button>
				<button @click="toSubmit" style="background-color: red;color: white;">提交</button>
			</view>		
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user:{},
				// 商品的已有信息
				one:{},
				// 商品图片（不可修改）
				coverPic:'https://gw.alicdn.com/bao/uploaded///asearch.alicdn.com/bao/uploaded/O1CN015rH4tD2LKkJrMhIlx_!!0-item_pic.jpg_300x300q90.jpg_.webp',
				// 更新的商品信息(名称，库存，价格，发货地址，描述)
				onename:'',
				storage:0,
				price:0,
				address:'',
				intro:'',
			}
		},
		mounted() {
			this.user = uni.getStorageSync('user');	
			this.getCoverPic();
		},
		onLoad(option){
			this.one = JSON.parse(option.goods);
			this.onename = this.one.name;
			this.price = this.one.price;
			this.storage = this.one.storage;
			this.intro = this.one.intro;
		},
		methods: {
			// 获取商品图片
			async getCoverPic(){
				const that  = this;
				try{
					let res = await this.api.get('/orders/product/pic',{number:this.one.number});
					that.coverPic = res.msg;
					this.coverPic = this.imageSrcformat(that.coverPic,res.data);
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 返回上一页
			toReturn(){
				uni.navigateBack({
					delta:1,//返回层数，2则上上页
				})
			},
			
			// 提交更新信息
			async toSubmit(){
				const that  = this;
				try{
					let res = await this.api.put('/my/product',{number:this.one.number,name:this.onename,storage:this.storage,price:this.price,address:this.address,intro:this.intro});
					if(res == 201){
						uni.showToast({
							title: '修改成功！',
							icon: 'success',
							duration: 30000
						})
						uni.navigateBack({
							delta:1, //返回层数，2则上上页
						})
					}
					else{
						uni.showToast({
							title: '商品不存在/更新失敗！',
							icon: 'warning',
							duration: 30000
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
	.updateMess{
		width: 100vw;
		height: 100vh;
		align-items: center;
	}
	
	.main {
		display: flex;
		flex-direction: column;
		width: 94%;
		margin-left: 3%;
		margin-top: 3%;
	}
	
	.goodsImg{
		width: 100%;
		text-align: center;
		margin-bottom: 5%;
	}
	
	.goodsImg>image{
		width: 100%;
		height: 100%;
	}
	
	.main .name,.main .storage,.main .price,.main .address{
		height: 80rpx;
		width: 90vw;	
	}
	
	.label {
		width: 10vw;
		height: 80rpx;
		line-height: 80rpx;
		text-align: center;
		font-family: 'Courier New';
		padding: 0 2vw;
	}
	
	.main input {
		width: 60vw;
		height: 70rpx;
		font-family: 'Courier New';
		font-size: 28rpx;
		text-align: left;
		padding: 0 0.5rem;
		float: right;
	}
	
	/* 补充描述 */
	.description{
		width: 100%;
		margin-bottom:5%;
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
		height: 125rpx;
		display: flex;
		margin: 10% 0 0 3%;
		flex-direction: row;
		justify-content: center;
		font-size: 36rpx;
	}
	.somebt>button{
		font-size: 32rpx;
		height: 62rpx;
		width: 36%;
		line-height: 62rpx;
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

</style>
