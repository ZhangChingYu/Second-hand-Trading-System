<template>
	<view class="calssify">
		
		<view class="top-contain">
			<!-- 遮罩层 -->
			<view class="showModel" v-if="showModel" @click="closeModel"></view>
			
			<!-- 分类 -->
			<view class="class-contain">
				<view class="class-title">
					<text v-show="!classify" @click="classTitle">分类<text class="iconfont .icon-xuanzeqizhankai"></text></text>
					<text class="title-open" v-show="classify"  @click="classTitle">分类<text class="iconfont ..icon-xuanzeqishouqi"></text></text>
				</view>
				<view class="class-item c" v-show="classify">
					<ul>
						<li @click="switchClass(-1)">全部</li>
						<li v-for="(item,index) of classList" :key="index"   @click="switchClass(item.number)">
							{{item.name}}
						</li>	
						
					</ul>
				</view>
			</view>
			
			<!-- 排序 -->
			<view class="sort-contain">
				<view class="class-title">
					<text v-show="!sort"  @click="sortTitle">排序<text class="iconfont .icon-xuanzeqizhankai"></text></text>
					<text class="title-open" v-show="sort"  @click="sortTitle">排序<text class="iconfont ..icon-xuanzeqishouqi"></text></text>
				</view>
				<view class="class-item s" v-show="sort">
					<ul>
						<li @click=getSort(1)>默认</li>
						<li @click=getSort(2)>价格升序</li>
						<li @click=getSort(3)>价格降序</li>
					</ul>
				</view>
			</view>
		</view>
		
		
		<!-- 商品列表 -->
		<view v-if="showProductList.length !== 0" class="class-result">
			
			
			 <Classitem class="goods"
				v-for="(item,index) of showProductList"
				:key="index"
				:goods="item"
			 ></Classitem>
			<!-- 没有更多 -->
			<Nomore v-if="isGet" notips="没有更多了..."></Nomore>	
		</view>
		<view v-else class="no-result">
			<u-empty mode="list"></u-empty>
		</view>
	</view>
</template>

<script>
	import Classitem from '@/components/classitem/index.vue'
	import Nomore from '@/components/nomore/index.vue'
	import '@/static/iconfont.css'
	import Goods from '@/components/goods/index.vue'
	
	export default {
		components:{Goods,Classitem,Nomore},
		data() {
			return {
				user:{},
				classify:false,
				sort:false,
				isGet: false,
				
				class_selected:-2,
				sort_selected: 1,
				
				productList:[],
				showProductList:[],
				classList:[]
			}
		},
		onShow() {
			
		
			let catalog = uni.getStorageSync('choosedCatalog');
			
			console.log(catalog);
			this.getProductList(catalog);
			this.getClassList();
		},
		onHide: function() {
			console.log('App Hide'),
			uni.setStorageSync('choosedCatalog',-1);
		},
		// 下拉刷新
		onPullDownRefresh(){
			// 获取全部商品
			let catalog = this.class_selected;
			this.class_selected = -2;
			console.log(catalog)
			this.getProductList(catalog);
			this.getClassList();
		},
		mounted() {
			this.user = uni.getStorageSync('user');
		},
		
		computed:{
			showModel(){
				return this.classify || this.sort;
			}
		},
		onLoad: function (option) {
			if(option.cnumber){
				this.defaultClass = option.cnumber
			}
			
		},
		methods: {
			getSort(number){
				this.sort = false;
				if(this.sort_selected == number) return;
				this.sort_selected = number;
				this.loading();
				if(number == 1) this.showProductList = this.productList;
				else if(number == 2){
					this.showProductList.sort((a,b)=>{
						console.log(1)
						return a.price - b.price;
					})
				}
				else if(number == 3){
					this.showProductList.sort((a,b)=>{
						return b.price - a.price;
					})
				}
				console.log(this.showProductList)
				this.$forceUpdate()
				this.$nextTick(()=>{
					this.loaded();
				})
			},
			switchClass(catalog,index){
				this.classify = false;
				if(this.class_selected == catalog) return;
				this.isGet = false;
				
				this.getProductList(catalog);
				this.isGet = true;
			},
			
			closeModel(){
				this.classify = false; 
				this.sort = false;
			},
			
			
			async getProductList(catalog){
				this.sort_selected = 1;
				if(this.class_selected != catalog) this.class_selected = catalog;
				// 加载提示
				this.loading();
				this.productList = [];
				this.showProductList = [];
				try{
					if(catalog != -1) {
						this.productList = await this.api.get('/catalog/products',{catalog});
					}else{
						this.productList =  await this.api.get('/homepage',{phone:this.user.phone});
					}
					this.showProductList = this.productList;
					
					console.log(this.showProductList)
				}catch(e){
					//TODO handle the exception
					this.$toast(e);
				}
				// 渲染完成后,关闭加载提示
				this.$nextTick(()=>{
					this.loaded();
				})
				
				
			},
			async getClassList(){
				try{
					this.classList = await this.api.get('/catalogs');
				}catch(e){
					//TODO handle the exception
					this.$toast(e)
				}
			},
			
			
			classTitle(){
				this.sort = false;
				this.classify = !this.classify;
			},
			sortTitle(){
				this.classify = false;
				this.sort = !this.sort;
			},
			loading(){
				uni.showLoading({
					title: '加载中...'
				});
			},
			loaded(){
				uni.hideLoading();
			},
		}
	}
</script>

<style scoped lang="less">
	.calssify {
		min-height: 100vh;
		background-color: #f4f4f4;
	}
	
	.top-contain {
		position: fixed;
		top: 0;
		width: 100%;
		display: flex;
		justify-content: space-around;
		align-items: center;
		height: 84rpx;
		border-bottom: 5rpx solid darkgray;
		border-radius: 10rpx;
		background-color: #fff;
		
	}
	.class-title {
		line-height: 84rpx;
		font-size: 30rpx;
		
	}
	.class-item {
		position: absolute;
		top: 84rpx;
		left: 2%;
		padding: 0 30rpx;
		width: 96%;
		background-color: #f4f4f4;
		border-radius: 0 0 20rpx 20rpx;
		box-sizing: border-box;
		line-height: 84rpx;
		font-size: 30rpx;
		z-index: 2;
	}
	.class-item ul li {
		border-bottom: 2rpx solid #fff;
	}
	//标题展开的样式
	.title-open {
		color: #b34c26;
	}
	
	// 三角
	.c ::before {
		content: '';
		position: absolute;
		top: -40rpx;
		left: 150rpx;
		width: 0;
		height: 0;
		border-top: 20rpx solid transparent;
		border-right: 20rpx solid transparent;
		border-bottom: 20rpx solid #f4f4f4;
		border-left: 20rpx solid transparent;
	}
	.s ::before {
		content: '';
		position: absolute;
		top: -40rpx;
		right: 150rpx;
		width: 0;
		height: 0;
		border-top: 20rpx solid transparent;
		border-right: 20rpx solid transparent;
		border-bottom: 20rpx solid #f4f4f4;
		border-left: 20rpx solid transparent;
	}

	
	// 遮罩
	.showModel {
		position: absolute;
		top: 84rpx;
		left: 0;
		width: 100vw;
		height: 100vh;
		background: rgba(0,0,0,.5);
		z-index:1;
	}
	
	
	
	/* 商品展示区域 */
	.class-result {
		padding: 100rpx 10rpx 10rpx 10rpx;
		width: 96%;
		// margin:128rpx 0 20rpx;
		display: flex;
		flex-wrap: wrap;
		justify-content: space-between;
		
		
		& .goods{
		width: 47.5%;
		// width: 90%;
		height: 340rpx;
		margin: 16rpx 5rpx;
		background-color: #fff;
		// z-index: -1;
		}
		
	}
	/* 没有结果 */
	.no-result {
		padding-top: 200rpx;
	}
	
	
	
	
</style>
