<template>
	<view class="content">
		<view style="display: flex;">
			<image class="avatar" :src="'data:image/jpg;base64,' + user.avatar" mode="scaleToFill"></image>
			<view class="name">
				<view>用户名：{{user.userName}}</view>
				<view>真实姓名：{{user.realName}}</view>
			</view>
		</view>
		<view class="info-content-item">违规次数：{{user.violationCount}}</view>
		<view class="info-content-item">密码：{{user.password}}</view>
		<view class="info-content-item">学号：{{user.stuNum}}</view>
		<view class="info-content-item">绑定手机号：{{user.phone}}</view>
		<view class="info-content-item">绑定邮箱：{{user.email}}</view>
		<view class="info-content-item">权限：{{user.authority}}</view>
		<view class="info-content-item">评分：{{grade}}</view>

		<view class="btns">
			<button @click="reset()">重置密码</button>
			<button @click="change()">修改用户权限</button>
		</view>

		<!--修改弹窗-->
		<view class="popup" v-show="showChange">
			<view class="popup-info">
				<view>
					<text>请选择您要更改为权限：</text>
					<view v-for="(item,index) of permission" :key="index"
						style="justify-items: flex-start; margin-top: 20rpx;">
						<label class="radio" @tap="changePer(item.number)">
							<radio value="" color="#d04b41" :checked="changeper==item.number"></radio>
						</label>
						<text>{{item.name}}</text>
					</view>
				</view>
				<view class="popup-btn">
					<view><button type="default" class="popBtn" @tap="cancel">取消</button></view>
					<view><button type="default" class="popBtn" @tap="affirm">确认</button></view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user: {},
				permission: [],
				changeper: -1,
				showChange: false,
				grade: ''
			}
		},
		onLoad(e) {
			let that = this;
			let phone = e.data;
			console.log('e:',e);
			console.log('phone:',phone);
			that.api.get('/seller/grade', {
				phone
			}).then(res => {
				that.grade = res;
			})
			that.api.get('/manage/user',{phone}).then(res=>{
				this.user=res.data;
			})
			that.api.get('/manage/select/authority').then(res => {
				that.permission = res.data;
			})
		},
		methods: {
			changePer(num) {
				this.changeper = num;
			},
			// 点击弹窗取消
			cancel() {
				this.showChange = false;
			},
			// 点击弹窗确认
			affirm() {
				this.showChange = false;
				let ids = [];
				ids.push(this.user.id);
				let number = this.changeper;
				let that = this;
				let phone=this.user.phone;
				that.api.put('/manage/user/authority', {
					ids,
					number
				}).then(res => {
					console.log(res);
				})
				that.api.get('/manage/user',{phone}).then(res=>{
					this.user=res.data;
				})
			},
			//修改用户权限
			change() {
				this.showChange = true
			},
			//重置密码
			reset() {
				let that = this;
				let id = [];
				let phone=this.user.phone;
				id.push(this.user.id)
				that.api.put('/manage/user/password', id);
				that.$toast('已重置密码')
				
				that.api.get('/manage/user',{phone}).then(res=>{
					this.user=res.data;
				})
			}
		}
	}
</script>

<style>
	.content {
		align-items: center;
		margin-top: 30rpx;
	}

	.avatar {
		width: 150rpx;
		height: 150rpx;
		border: 2rpx solid #d04b41;
		border-radius: 15rpx;
		margin-left: 5%;
	}

	.name {
		width: 80%;
	}

	.name>view {
		width: auto;
		margin: 5% 10%;
		border-bottom: 1px solid #cccccc;
	}

	.popup {
		position: fixed;
		left: 0;
		right: 0;
		top: 0;
		height: 100vh;
		background-color: rgba(0, 0, 0, 0.6);
		z-index: 9998;
	}

	.info-content-item {
		margin: 20rpx 5%;
		padding: 20rpx 5rpx;
		border-bottom: 1px solid #cccccc;
		border-left: 5rpx solid #d04b41;
	}

	.btns {
		display: flex;
		justify-content: center;
		margin-top: 10%;
	}

	.btns>button {
		margin: 10rpx 30rpx;
		color: #d04b41;
		background-color: white;
		border: 3rpx solid #d04b41;
		width: 40%;
	}

	.popup-info {
		position: fixed;
		width: 550upx;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
		font-size: 30upx;
		padding: 40upx;
		border-radius: 20upx;
		background-color: #fff;
		z-index: 9999;
	}

	.popup-btn {
		margin-top: 100rpx;
		display: flex;
		justify-content: center;
	}

	.popBtn {
		margin: 10rpx 60rpx;
		border: 2rpx solid #d04b41;
		font-size: medium;
		height: 70rpx;
	}
</style>
