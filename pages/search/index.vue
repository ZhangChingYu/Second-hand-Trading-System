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
			
		<view v-else>详情</view>
		
	</view>
	

</template>

<script>
	export default {
		data() {
			return {
				
				historyWords: [],
				popularWords: [],
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
					this.historyWords = [];
				}
			},
			
			queryPopular() {
				try{
					this.popularWords = uni.getStorageSync('popularWords') || [];
				}catch(e){
					//TODO handle the exception
					this.popularWords = [];
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
			
			search(){
				let keyWords = this.searchValue;
				this.isIndex = false;
				this.historyWords = this.historyWords.filter(item=>item != keyWords);
				this.historyWords.unshift(keyWords);
				// 存储
				uni.setStorageSync('historyWords',this.historyWords);
				
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

<style>
	
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
</style>