<template>
	<view class="content">
		<view class="realname">
			<view class="title">真实姓名</view>
			<view class="text">
				<input type="text" value="" placeholder="请输入真实姓名" placeholder-style="color:#999999"
					v-model="realName" />
			</view>
		</view>
		<view class="realname">
			<view class="title">身份证号</view>
			<view class="text">
				<input type="text" value="" placeholder="请输入身份证号" placeholder-style="color:#999999" v-model="idCard" />
			</view>
		</view>
		<view class="img-content">
			<image class="img-icon" v-if='imgSrc' :src="imgSrc" @tap="onGetImgClick" mode="aspectFill"></image>
			<view class="box" v-else @tap="onGetImgClick">
				<image src="../../static/image/上传.png" style="height: 300rpx; width: 300rpx;"></image>
				<text style="color: grey;">点击上传图片</text>
			</view>
		</view>
		<button type="primary" class="commitbtn" @click="commit">提交</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				imgSrc: '', //图片地址
				phone: '',
				realName: '',
				idCard: ''
			}
		},
		mounted() {
			this.phone = uni.getStorageSync('user').phone;
		},
		methods: {
			onGetImgClick() {
				const that = this
				uni.chooseImage({
					count: 1, // 最多可以选择的图片张数，默认9
					sizeType: ['original', 'compressed'], //original 原图，compressed 压缩图，默认二者都有
					sourceType: ['album', 'camera'], //album 从相册选图，camera 使用相机，默认二者都有。如需直接开相机或直接选相册，请只使用一个选项
					success: function(res) {
						console.log('chooseImage-----》》》》》》》》', res);
						//判断图片格式
						let tempStr = res.tempFilePaths[0].split('.');
						let lowerStr = tempStr[1].toLowerCase();
						if (lowerStr != 'png' && lowerStr !== 'jpg' && lowerStr !== 'jpeg') {
							uni.showToast({
								title: '请上传PNG、JPG、JPEG格式的图片',
								icon: 'none',
								duration: 3000
							});
							return;
						}
						console.log(res.tempFiles, 'beforre--------');
						if (res.tempFiles[0]['size'] > 20 * 1024 * 1024) {
							uni.showToast({
								title: '图片大小不能超过20M',
								icon: 'none',
								duration: 3000
							});
							return;
						}
						/*uni.showLoading({
							title: '上传中...'
						})*/
						if (res.tempFiles[0]['size'] < 5 * 1024 * 1024) { //图片小于5M不压缩，大于5M压缩
							console.log(res.tempFilePaths[0], 'imginfo');
							that.imgSrc = res.tempFilePaths[0];
							//that.uploadImgFile(res.tempFilePaths[0], that)
						} else {
							uni.compressImage({
								src: res.tempFilePaths[0],
								quality: 80,
								success: res => {
									console.log(res, '=========res');
									that.uploadImgFile(res.tempFilePath, that)
								}
							})
						}
					}
				});
			},
			uploadImgFile(filePath, that) {
				uni.uploadFile({
					url: '地址',
					filePath: filePath,
					name: 'file',
					formData: {
						file: filePath,
						phone: that.phone,
						realName: that.realName,
						idCard: that.idCard
					},
					header: {
						'Content-Type': 'multipart/form-data',
						'token': uni.getStorageSync('token')
					},
					success: response => {
						let res = JSON.parse(response.data);

						console.log(res, '----res');
						if (res.code == 200) {
							that.showInfo = res.data
							console.log('请求成功_______________', res);
							// 调用下载接口
							//that.downloadImg(res.data.attachId);

						}
					},
					fail: err => {
						//uni.hideLoading()
						console.log('请求失败_______________', err);
						console.log(that.formData)
					}
				});
			},
			commit() {
				console.log('点击提交')
				let that = this;
				that.uploadImgFile(this.imgSrc, that);
			}
		}
	}
</script>

<style scoped>
	.content {
		font-size: medium;
		margin-top: 80rpx;
	}

	.realname {
		display: flex;
		padding: 40rpx 40rpx;
		border-bottom: 2rpx solid #cccccc;
	}

	.commitbtn {
		background-color: #d04b41;
		width: 30%;
		justify-content: center;
		margin-top: 30%;
	}

	.text {
		padding-left: 20rpx;
	}

	.img-content {
		border: 2rpx solid #cccccc;
		border-radius: 20rpx;
		width: 90%;
		height: 400rpx;
		margin-left: 5%;
		margin-top: 30rpx;
		justify-content: center;
		align-items: center;
	}

	.img-icon {
		border: 2rpx solid #cccccc;
		border-radius: 20rpx;
		width: 90%;
		height: 400rpx;
		margin-left: 5%;
	}

	.box {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		margin-top: 20rpx;
	}
</style>
