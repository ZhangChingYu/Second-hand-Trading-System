<template>
	<view class="catalog">
		<view class="catalog-title">商品类别列表</view>
		<u-swipe-action v-if="classList.length !== 0">
			<u-swipe-action-item  v-for="(item,index) of classList" :key="index":options="options" :name="index" @click="operateCatalog">
			  <view class="swipe-action u-border-top u-border-bottom">
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
		<!-- 新增 -->
		<view class="add-catalog">
			<u-icon class="add-catalog" name="plus" color="#2979ff" size="28" @click="show = true"></u-icon>
			<u-popup :show="show" mode="center"closeable="true" @close="show = false">
				<view class="add-box">
					
				</view>
			</u-popup>
		</view>
	</view>
</template>

<script>
import { vShow } from "vue";
import {getCatalog} from '@/common/request/api.js'
	export default {
		data() {
			return {
				show:false,
				classList:[],
				options:[
					{
						text: '编辑',
						style: {
							backgroundColor: '#3c9cff'
						}
					},
					{
						text: '删除',
						style: {
							backgroundColor: '#f56c6c'
						}
					},
				]
			}
		},
		mounted() {
			this.getClassList();
		}
		,
		methods:{
			operateCatalog(option){
				if(option.index == 0){
					this.show = true;
				}else if(option.index == 1){
					this.$toast(`删除了第${option.name + 1}个数据`);
				}
			},
			async getClassList(){
				try{
					this.classList = await getCatalog();
					console.log(this.classList)
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
	.catalog {
		margin: 30px 30px 0;
	}
	.catalog-title {
		margin: 20rpx 0;
		font-size: 30rpx;
		color: #be0000;
		text-shadow: 5rpx 5rpx 2rpx #24c3be;
	}
	
	.add-catalog {
		box-sizing: border-box;
		margin-top: 30rpx;
		padding-left: 20rpx	;
		
		& .add-box {
			width: 80vw;
			height: 500rpx;
		}
	}
    
	
	
</style>
