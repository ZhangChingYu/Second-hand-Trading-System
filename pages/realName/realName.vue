<template>
	<view class="content">
		<view class="select">
			<view v-show="!showSelects" @tap="showSelects=true">审核状态▶</view>
			<view v-show="showSelects" style="color: #d04b41;" @tap="showSelects=false">审核状态◢</view>
			<view style="color: #d04b41;" @click="refresh()">刷新</view>
		</view>

		<view class="selects" v-show="showSelects">
			<ul>
				<li @tap="showSelect(0)">未处理<text v-if="select==0" style="color: #d04b41;">√</text></li>
				<li @tap="showSelect(1)">已通过<text v-if="select==1" style="color: #d04b41;">√</text></li>
				<li @tap="showSelect(2)">未通过<text v-if="select==2" style="color: #d04b41;">√</text></li>
			</ul>
		</view>

		<view class="authentication" v-for="(item,index) of authentications" :key="index">
			<view class="left">
				<view>真实姓名：{{item.realName}}</view>
				<view>电话号码：{{item.phone}}</view>
				<view>申请日期：{{item.date}}</view>
			</view>
			<button class="right" @click="showDetail(item.id,item.status)" v-show="select!=2">查看详情</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				authentications: [],
				showSelects: false,
				select: 0,
			}
		},
		mounted() {
			this.api.get('/authentication/requests').then(res => {
				this.authentications = res;
			})
		},
		methods: {
			showSelect(status) {
				this.select = status;
				this.api.get('/authentication/requests/status', {
					status
				}).then(res => {
					this.authentications = res;
				})
			},
			showDetail(id, status) {
				uni.navigateTo({
					url: '/pages/realNameDetail/realNameDetail?id=' + id + '&status=' + status
				})
			},
			refresh() {
				this.api.get('/authentication/requests').then(res => {
					this.authentications = res;
					this.select=0
				})
			}
		}
	}
</script>

<style>
	.content {
		margin: 5% 5%;
		font-size: small;
	}

	.select {
		width: 100%;
		border-bottom: 2rpx solid #ccc;
		justify-content: space-between;
		display: flex;
		flex-wrap: wrap;
	}

	.selects {
		position: fixed;
		top: 80rpx;
		border: 1rpx solid grey;
		padding: 10rpx 40rpx;
		z-index: 9997;
		background-color: white;
	}

	.authentication {
		display: flex;
		flex-wrap: wrap;
		justify-content: space-between;
		border-top: 10rpx solid #d04b41;
		border-bottom: 2rpx solid #ccc;
		margin: 5% 5%;
		padding: 5% 0;
	}

	.right {
		border-radius: 15%;
		background-color: #d04b41;
		color: white;
		width: 170rpx;
		height: 60rpx;
		font-size: small;
	}
</style>
