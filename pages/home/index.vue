<template>
	<view>
		<!-- 搜索框 -->
		<view class="top-search">
			<text></text>
			<input placeholder="寻找宝贝"/>
		</view>
		<!-- 顶部轮播图 -->
		<view class="uni-margin-wrap">
			<swiper 
				class="swiper" circular 
				indicator-dots="true" 
				indicator-color = 'rgb(255,255,255)'
				indicator-active-color="#FFFF80"
				autoplay="autoplay" 
				interval="2000"
				duration="500">
				
				<swiper-item v-for="(item,index) of swiperItem" :key="index">
					<view class="swiper-item">
						<image :src="item"></image>
					</view>
				</swiper-item>
				
			</swiper>
		</view>
		
		
	</view>
</template>

<script>
	export default {
	    data() {
	        return {
	            swiperItem: [
					'https://img2.baidu.com/it/u=120693445,2125794843&fm=253&fmt=auto&app=120&f=JPEG?w=350&h=449',
					'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Finews.gtimg.com%2Fnewsapp_bt%2F0%2F13950518577%2F1000&refer=http%3A%2F%2Finews.gtimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669533057&t=180b6b372cd71022722b3d8c3ccbc068',
					'https://img2.baidu.com/it/u=4293062902,2076102987&fm=253&fmt=auto&app=138&f=JPG?w=750&h=500'
					  ],
	            indicatorDots: true,
	            autoplay: true,
	            interval: 2000,
	            duration: 500
	        }
	    },
	    methods: {
	        changeIndicatorDots(e) {
	            this.indicatorDots = !this.indicatorDots
	        },
	        changeAutoplay(e) {
	            this.autoplay = !this.autoplay
	        },
	        intervalChange(e) {
	            this.interval = e.target.value
	        },
	        durationChange(e) {
	            this.duration = e.target.value
	        }
	    }
	}
</script>

<style>	
	/* 顶部搜索 */
	.top-search {
		display: flex;
		align-items: center;
		margin: 0.48rem auto;
		width: 90%;
		height: 2rem;
		background-color: gainsboro;
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
	
	
	/* 轮播图 */
	.uni-margin-wrap {
		position: relative;
		width: 100%;
		margin: 1rem auto;
	}
	.swiper {

		height: 300rpx;
	}
	.swiper-item  {
		line-height: 300rpx;
		text-align: center;
	}
	
	
</style>
