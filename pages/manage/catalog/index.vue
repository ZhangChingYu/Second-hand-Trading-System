<template>
	<view>
		<u-swipe-action v-if="classList.length !== 0">
			<u-swipe-action-item  v-for="(item,index) of classList" :key="index" :options="options">
			  <view class="swipe-action u-border-top u-border-bottom">
				  <!-- 内容 -->
				<view class="swipe-action__content">
				  <text class="swipe-action__content__text">{{item}}</text>
				</view>
				
			  </view>
			</u-swipe-action-item>
		</u-swipe-action>
		<!-- 无数据 -->
		<u-empty v-else></u-empty>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				classList:['12132哇','sadfkja','阿斯顿回复'],
				options:[
					{
						text: '收藏',
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
			async getClassList(){
				try{
					this.classList = await this.api.get('/catalog/catalogs');
				}catch(e){
					//TODO handle the exception
					this.$toast(e)
				}
			},
		}
	}
</script>

<style lang="scss">
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
                 font-size: 15px;
                 color: $u-main-color;
                 padding-left: 30rpx;
             }
        }
    }
</style>
