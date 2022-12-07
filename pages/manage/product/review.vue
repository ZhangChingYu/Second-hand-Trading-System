<template>
	<view class="review">
		<view class="review-title">商品审核列表</view>
		<u-swipe-action v-if="productList.length !== 0">
			<u-swipe-action-item  v-for="(item,index) of productList" :key="index" :options="options" :index="index" :name="index" @click="operateReview">
			  <view class="swipe-action u-border-top u-border-bottom" @click="getDetail">
				  <!-- 内容 -->
				<view class="swipe-action__content">
				  <text class="swipe-action__content__text">{{item.number}}</text>
				  <text class="swipe-action__content__text">{{item.name}}</text>
				</view>
				
			  </view>
			</u-swipe-action-item>
		</u-swipe-action>
		<!-- 无数据 -->
		<u-empty v-else></u-empty>
		
	</view>
</template>

<script>
	import {getReviewProducts,getCatalog} from '@/common/request/api.js'
	export default {
		data() {
			return {
				productList:[],
				options:[
					{
						text: '通过',
						style: {
							backgroundColor: '#3c9cff'
						}
					},
					{
						text: '不通过',
						style: {
							backgroundColor: '#f56c6c'
						}
					},
				]
			}
		},
		mounted() {
			this.getProductList();
		},
		methods: {
			getDetail(index){
				console.log()
			},
			operateReview(option){
				if(option.index == 0){
					this.$toast(`通过了第${option.name + 1}个数据`);
				}else if(option.index == 1){
					this.$toast(`未通过了第${option.name + 1}个数据`);
				}
			},
			
			async getProductList(){
				try{
					this.productList = await getCatalog();
				}catch(e){
					//TODO handle the exception
					this.$toast(e)
				}
			},
		}
	}
</script>

<style lang="scss" scoped>
    .u-page {
        padding: 0;
    }

    .u-demo-block__title {
        padding: 10px 0 2px 15px;
    }

    .swipe-action {
        &__content {
             padding: 25rpx 0;
    
            &__text {
                 font-size: 16px;
                 color: $u-main-color;
                 padding-left: 30rpx;
             }
        }
	}
	.review {
		margin: 30px 30px 0;
	}
	.review-title {
		margin: 20rpx 0;
		font-size: 30rpx;
		color: #be0000;
		text-shadow: 5rpx 5rpx 2rpx #24c3be;
	}
	
    
</style>
