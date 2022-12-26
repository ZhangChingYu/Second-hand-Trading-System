<template>
	<view class="content">
		<!--实名认证信息-->
		<view class="info">
			<view>真实姓名：{{authentication.realName}}</view>
			<view>电话号码：{{authentication.phone}}</view>
			<view>申请日期：{{authentication.date}}</view>
			<view>证件照：</view>
			<image class="picture" :src="'data:image/'+authentication.format+';base64,' + authentication.idCardPic"
				mode="widthFix"></image>
		</view>

		<!--操作-->
		<text class="title" v-show="status==0">审核</text>
		<view class="operation" v-show="status==0">
			<view class="decision">
				<text>审核结果：</text>
				<label class="radio" @tap="decision=0">
					<radio color="#d04b41" :checked="decision==0">通过</radio>
				</label>
				<label class="radio" @tap="decision=1">
					<radio color="#d04b41" :checked="decision==1">不通过</radio>
				</label>
			</view>
			<view class="explain">
				请说明理由：
				<textarea v-model="explain" class="text"></textarea>
			</view>
			<button class="commitbtn" @click="commit()">确认</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				authentication: {},
				status: '',
				decision: -1,
				explain: ''
			}
		},
		onLoad(e) {
			let id = e.id;
			this.api.get('/authentication/request', {
				id
			}).then(res => {
				this.authentication = res;
				console.log(res)
			})
			this.status = e.status;
		},
		methods: {
			commit() {
				if (this.decision == -1 || this.explain == '') {
					this.$toast('请填写完整信息')
				} else {
					let id = this.authentication.id;
					let decision = '';
					if (this.decision == 0) {
						decision = 'pass';
					} else {
						decision = 'reject';
					}
					let explain = this.explain;
					this.api.put('/authentication/request', {
						id,
						decision,
						explain
					}).then(res => {
						console.log(res)
						if (res == 201) {
							this.$toast('审核成功');
						} else {
							this.$toast('审核失败');
						}
					})
					uni.navigateBack({
						delta: 1,
					})
					let pages = getCurrentPages();
					let currPage = pages[pages.length - 1] //当前页面
					let prePage = pages[pages.length - 2] //上一个页面
					prePage.$vm.refresh();
				}
			}
		}
	}
</script>

<style>
	.content {
		margin: 5% 5%;
		justify-content: center;
		align-items: center;
		font-size: small;
	}

	.picture {
		border: 2rpx solid #d04b41;
		width: 100%;
	}
	
	.info{
		margin-bottom: 10%;
	}

	.info>view {
		margin-top: 5%;
		border-bottom: 2rpx solid #ccc;
		border-left: 5rpx solid #d04b41;
		padding-bottom: 3%;
	}

	.operation {
		margin-top: 5%;
		padding-top: 5%;
		border-top: 3rpx solid #d04b41;
	}
	
	.title{
		font-size: medium;
		font-weight: 600;
		color: #d04b41;
	}

	.operation>view {
		margin-bottom: 3%;
	}

	.text {
		width: 95%;
		height: 400rpx;
		background-color: #f1e9e2;
		border: #e8e5e1;
		padding: 2% 3%;
	}

	.commitbtn {
		background-color: #d04b41;
		width: 30%;
		justify-content: center;
		margin-top: 5%;
		color: white;
		font-size: small;
	}
</style>
