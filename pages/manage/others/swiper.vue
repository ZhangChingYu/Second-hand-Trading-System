<template>
	<view class="swiper">
		<view class="swiper-title">轮播图列表（{{swiperList.length}} / 4）</view>
		<view class="container">
			<view class="pic" v-for="(item,index) of swiperList" :key="item.index" @longtap="remove(item.index)">
				<image mode="aspectFill" :src="item.picture"></image>
			</view>
			<view class="pic" v-if="swiperList.length < 4">
				<u-upload
						:fileList="fileList"
						@afterRead="afterRead"
						@delete="deletePic"
						imageMode="aspectFit"
						width="750rpx"
						height="350rpx"
					></u-upload>
			</view>
		</view>
		<!-- 无数据 -->
		<!-- <u-empty v-else></u-empty> -->
		
	</view>
</template>

<script>
import { vShow } from "vue";
import {mixin} from '@/mixin.js'
import {addSwiper,deleteSwiper,updateSwiper,getSwiper} from '@/common/request/api.js'
	export default {
		mixins:[mixin],
		data() {
			return {
				swiperList:[],
				fileList: [],
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
			this.getPictureList();
		}
		,
		methods:{
			async remove(index){
				const that = this;
				uni.showModal({    // 弹框询问是否进行下一步事件
					title: '提示',
					content: '是否删除该图片',
					success: function(r) {
						if (r.confirm) {
							deleteSwiper({index}).then(res=>{
								if(res == 204) {
									that.$toast('删除成功！');
									that.getPictureList();
								}else that.$toast('删除失败！');
							})
								
							
						}
					}
				})
				
			},
			// 删除图片
			deletePic(event) {
				this.fileList.splice(event.index, 1)
			},
			// 新增图片
			async afterRead(event) {
				// 当设置 multiple 为 true 时, file 为数组格式，否则为对象格式
				let file =event.file
				this.fileList.push({
					...file,
					status: 'uploading',
					message: '上传中'
				})
				console.log(file)
				const result = await this.uploadFilePromise(file.url)
				this.fileList.splice(0, 1, Object.assign(file, {
					status: 'success',
					message: '',
					url: result
				}))
				this.fileList = [];
				this.getPictureList()
			},
			uploadFilePromise(url) {
				let ip = this.api.ip + '/homepage/ads'
				return new Promise((resolve, reject) => {
					let a = uni.uploadFile({
						url: ip,
						filePath: url,
						name: 'picture',
						header:{
							'content-type':'application/json',
							'token':uni.getStorageSync('token')
						},
						success: (res) => {
							setTimeout(() => {
								console.log(res)
								resolve(res.data.data)
							}, 1000)
						}
					});
				})
			},
			
			async getPictureList(){
				try{
					let res = await getSwiper();
					res?.forEach(item=>{
						item.picture = this.imageSrcformat(item.picture,item.format);
					})
					this.swiperList = [];
					this.swiperList = res;
					console.log(this.swiperList)
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
	.swiper-title {
		margin: 20rpx 2em;
		font-size: 30rpx;
		color: #be0000;
		text-shadow: 5rpx 5rpx 2rpx #a1bac3;
	}
	
	.container {
		display: flex;
		flex-wrap: wrap;
		justify-content: space-around;
		& .pic {
			margin-top: 30rpx;
			// width: 350rpx;
			width: 100%;
			height: 350rpx;
			border-radius: 16rpx;
			overflow: hidden;
			& image {
				width: 100%;
				height: 100%;
			}
		}
	}
	
	
    
	
	
</style>
