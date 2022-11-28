<template>
	<view class="search-page">
		<!-- 搜索框 -->
		<view class="top-box">
			<span v-if="!isIndex" class="top-exit" @click="toSearchIndex"> {{exitTitle}}</span>
			<view class="top-search" >
			<text @click="search"></text>
			<input placeholder="寻找宝贝" v-model="searchValue" confirm-type="search" @confirm="search">
		</view>
		</view>
		
		
		
		<view class="search-wrap" v-if="isIndex">
			<view class="history-wrap" v-if="historyWords.length != 0">
				<view class="search-header">
					<text class="search-title">历史搜索</text>
					<text class="search-clear" @click="clearHistory">清除</text>
				</view>
				
				<view class="search-content">
					<view
					  class="search-item"
					  hover-class="hover-history-item"
					  v-for="(item,index) of historyWords"
					  :key="index"
					  @click="historyTap(item)"
					  @longtap="clearOneHistory(index)"
					>
						{{item}}
					</view>
				</view>
			</view>
			<view class="popular-wrap">
				<view class="search-header">
					<text class="search-title">热门搜索</text>
				</view>
				
				<view class="search-content">
					<view
					  class="search-item"
					  hover-class="hover-history-item"
					  v-for="(item,index) of popularWords"
					  :key="index"
					  @click="historyTap(item)"
					  
					>
						{{item}}
					</view>
				</view>
			</view>
		</view>
			
		<view v-else class="search-res-page">
			
			<view class="res-title">共找到{{searchResult.length}}条结果</view>
			<view class="search-result">
				<!-- 每个搜索结果 -->
				<view 
					class="result-item"
					v-for="(item,index) of searchResult"
					:key="index"
				>
					<Search :goods="item"></Search>
				</view>	
			</view>
			<Nomore notips="没有更多了...."></Nomore>
		</view>
		
	</view>
	

</template>

<script>
	import Goods from '@/components/goods/index.vue'
	import Search from '@/components/search/index.vue'
	import Nomore from '@/components/nomore/index.vue'
	
	export default {
		components:{Goods,Search,Nomore},
		data() {
			return {
				
				historyWords: [],
				popularWords: [],
				searchResult:[],
				searchValue: '',
				isIndex : true,
				exitTitle:'返回'
				    
			}
		},
		mounted(){
			console.log(123456)
			// uni.setStorageSync('historyWords',['鸡','电脑','iPhone12','车载手表','自然堂','小米10','华为','氢跑鞋','娃娃']);
			// uni.setStorageSync('popularWords',['iPhone12','车载手表','自然堂','小米10']);
			this.queryHistory();
			this.queryPopular();
			console.log(this.popularWords)
		},
		
		methods:{
			queryHistory() {
				try{
					this.historyWords = uni.getStorageSync('historyWords') || [];
				}catch(e){
					//TODO handle the exception
					this.$toast(e);
					this.historyWords = [];
				}
			},
			
			queryPopular() {
				try{
					this.popularWords = uni.getStorageSync('popularWords') || [];
				}catch(e){
					//TODO handle the exception
					this.popularWords = [];
					this.$toast(e);
				}
			},
		
			clearHistory(){
				const that = this;
				uni.showModal({    // 弹框询问是否进行下一步事件
				        title: '提示',
				        content: '是否删除该记录',
				        success: function(res) {
				            if (res.confirm) {
				                that.historyWords = [];
				                uni.removeStorageSync('historyWords');
				
				            } else if (res.cancel) {
				                console.log('用户点击取消');
				                return
				            }
				        }
				    });
			},
		
			historyTap(item){
				this.searchValue = item;
				this.search();
			},
			
			async search(){
				let keyword = this.searchValue;
				this.isIndex = false;
				if(keyword.trim() !== ''){
					this.historyWords = this.historyWords.filter(item=>item != keyword);
					this.historyWords.unshift(keyword);
					// 存储
					uni.setStorageSync('historyWords',this.historyWords);
				}
				
				// 发送请求
				try{
					let res = await this.api.get('/search/products',{keyword});
					this.searchResult = res;
					console.log(this.searchResult);
				}catch(e){
					//TODO handle the exception
					this.$toast(e)
				}
				
				
			},
			clearOneHistory(index){
				const that = this;
				
				uni.showModal({    // 弹框询问是否进行下一步事件
				        title: '提示',
				        content: '是否删除该记录',
				        success: function(res) {
				            if (res.confirm) {
				                that.historyWords.splice(index,1);
				                uni.setStorageSync('historyWords',that.historyWords);
				
				            } else if (res.cancel) {
				                console.log('用户点击取消');
				                return
				            }
				        }
				    });
				
			},
			toSearchIndex(){
				this.isIndex = true
			}
		}		
	}

</script>

<style >
	
	/* 顶部搜索 */
	.top-box {
		display: flex;
		justify-content: space-around;
	}
	.top-box .top-exit {
		display: inline-block;
		margin: 0.48rem auto;
		height: 2rem;
		width: 2.2rem;
		border-radius: 5px;
		background-color: #dddddd;
		text-align: center;
		line-height: 2rem;
		
		
	}
	.top-search {
		display: flex;
		align-items: center;
		justify-content: space-around;
		margin: 0.48rem auto;
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
	/* ------------------- */
	
	
	.search-page {
	  box-sizing: border-box;
	  width: 100vw;
	  height: 100vh;
	  padding: 0 30rpx;
	}

	
	.search-page .search-wrap {
	  margin-top: 44rpx;
	}

	.search-page .history-wrap {
	  margin-bottom: 20px;
	}

	.search-page .search-header {
	  display: flex;
	  flex-flow: row nowrap;
	  justify-content: space-between;
	  align-items: center;
	}

	.search-page .search-title {
	  font-size: 30rpx;
	  font-family: PingFangSC-Semibold, PingFang SC;
	  font-weight: 600;
	  color: rgba(51, 51, 51, 1);
	  line-height: 42rpx;
	}

	.search-page .search-clear {
	  font-size: 24rpx;
	  font-family: PingFang SC;
	  line-height: 32rpx;
	  color: #999999;
	  font-weight: normal;
	}

	.search-page .search-content {
	  overflow: hidden;
	  display: flex;
	  flex-flow: row wrap;
	  justify-content: flex-start;
	  align-items: flex-start;
	  margin-top: 24rpx;
	}

	.search-page .search-item {
	  color: #333333;
	  font-size: 24rpx;
	  line-height: 32rpx;
	  font-weight: normal;
	  margin-right: 24rpx;
	  margin-bottom: 24rpx;
	  background: #f5f5f5;
	  border-radius: 38rpx;
	  padding: 12rpx 24rpx;
	}

	.search-page .hover-history-item {
	  position: relative;
	  top: 3rpx;
	  left: 3rpx;
	  box-shadow: 0px 0px 8px rgba(0, 0, 0, 0.1) inset;
	}

	.add-notes__confirm {
	  color: #fa4126 !important;
	}
	
	/* 搜索结果 */
	
	
	.search-result {
		/* margin: 2rem auto; */
	}
	
	.search-res-page .res-title {
		margin: 1rem 0;
		font-size: 1.2rem;
		text-shadow: 2px 2px 3px rgba(0, 0, 0, 0.3);
	}
	.search-result .result-item {
		margin: 2rem auto;
		width: 100%;
		height: 50vw;
		border-radius: 1rem 1rem 0 0;
		background-color: #ffffff;
		border-bottom: 2px solid #dddddd ;
		border-right: 2px solid #dddddd ;
		
	}

</style>
