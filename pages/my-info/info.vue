<template>
	<view class="info-content">
		<view class="info-content-list">
			<view class="info-content-item" style="border-bottom: 3rpx solid black; font-weight: 600;">
				<view>个人信息管理</view>
			</view>
			<view class="info-content-item">
				<view>头像</view>
				<image v-if="avatar" :src="avatar" style="width: 100rpx; height: 100rpx;" @tap="onGetImgClick"></image>
				<image v-else :src="'data:image/jpg;base64,' + userUpdate.avatar" style="width: 100rpx; height: 100rpx;" @tap="onGetImgClick"></image>
			</view>
			<view class="info-content-item">
				<view>用户名：</view>
				<input type="text" value="" placeholder="请输入用户名" v-model="userUpdate.userName"/>
			</view>
			<view class="info-content-item">
				<view>真实姓名：</view>
				<view style="color: grey;">{{userUpdate.realName}}&nbsp;&nbsp;&nbsp;</view>
			</view>
			<view class="info-content-item">
				<view>违规次数：</view>
				<view style="color: grey;">{{userUpdate.violationCount}}&nbsp;&nbsp;&nbsp;</view>
			</view>
			<view class="info-content-item" @click="changePass">
				<view>修改密码</view>
				<view>&nbsp;&nbsp;&nbsp;></view>
			</view>
			<view class="info-content-item">
				<view>学号：</view>
				<input type="text" value="" placeholder="请输入学号" v-model="userUpdate.stuNum"/>
			</view>
			<view class="info-content-item">
				<view>绑定手机号：</view>
				<input type="text" value="" placeholder="请输入手机号" v-model="userUpdate.phone"/>
			</view>
			<view class="info-content-item">
				<view>绑定邮箱：</view>
				<input type="text" value="" placeholder="请输入邮箱" v-model="userUpdate.email"/>
			</view>
			<view class="info-content-item">
				<view>个人评分</view>
				<view style="color: grey;">{{grade}}&nbsp;&nbsp;&nbsp;</view>
			</view>
			<button class="commitbtn" @click="commit">确认修改</button>
		</view>
	</view>
</template>

<script>
	export default{
		data(){
			return{
				//暂存修改信息
				userUpdate:{
					violationCount:'',
					userName:'',
					realName:'',
					email:'',
					stuNum:'',
					phone:''
				},
				//用于storage信息更新
				user:{},
				avatar:'',
				grade:''
			}
		},
		mounted(){
			let res=uni.getStorageSync('user');
			this.user=res;
			this.userUpdate=res;
			
			//获取评分
			let that=this;
			let phone=this.user.phone;
			that.api.get('/seller/grade',{phone}).then(res=>{
				this.grade=res;
			})
			
			//获取头像
			this.avatar=uni.getStorageSync('avatar');
		},
		methods:{
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
							that.avatar = res.tempFilePaths[0];
							that.uploadImgFile(res.tempFilePaths[0],that);
							//更新store
							uni.setStorage({
								key:'avatar',data:that.avatar
							})
						} else {
							uni.compressImage({
								src: res.tempFilePaths[0],
								quality: 80,
								success: res => {
									console.log(res, '=========res');
									that.uploadImgFile(res.tempFilePath, that)
									//更新store
									uni.setStorage({
										key:'avatar',data:that.avatar
									})
								}
							})
						}
					}
				});
			},
			uploadImgFile(filePath, that) {
				uni.uploadFile({
					url: 'http://localhost:8080//setting/swapRelatedAvatar',
					filePath: filePath,
					name: 'avatar',
					formData: {
						avatar: filePath,
						phone:this.user.phone
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
					}
				});
			},
			commit(){
				let that=this;
				that.api.post('/setting/edit',that.user).then(res=>{
					console.log(res);
				})
				this.user=this.userUpdate;
				uni.setStorage({
					key:'user',data:this.user,
				})
				this.$toast('更新成功!');
				
				//跳转回上一级并修改个人信息页用户名
				let pages=getCurrentPages();
				let nowPage=pages[pages.length - 1];
				let prePage=pages[pages.length -2];
				prePage.$vm.user.userName=this.user.userName;
				prePage.$vm.avatar=this.avatar;
				
				uni.navigateBack({
					delta:1
				})
			},
			changePass(){
				uni.navigateTo({
					url:'/pages/findback/findback'
				})
			}
		}
	}
</script>

<style scoped>
	.info-content{
		font-size: medium;
		margin: 30rpx 0;
		padding: 0 30rpx;
		
	}
	.info-content-item{
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 30rpx 0;
		border-bottom: 1px solid #cccccc;
	}
	.commitbtn{
		background-color: #d04b41;
		width: 30%;
		justify-content: center;
		margin-top: 5%;
		color: white;
	}
</style>