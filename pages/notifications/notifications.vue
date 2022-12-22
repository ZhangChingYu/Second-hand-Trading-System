<template>
	<view class="content">
		<view class="head">
			<label class="radio" @tap="showUnread()" style="margin-top: 20rpx;">
				<radio color="#d04b41" :checked="unreadOnly">仅查看未读消息</radio>
			</label>
			<button class="delete" @click="deleteRead()">删除已读</button>
		</view>
		<view class="notiItem" v-for="(item,index) of notifications" :key="index">
			<view class="title">
				<view @click="showDetail(item.id)">
					<text class="type">{{item.type}}</text>{{item.title}}
				</view>
				<text v-if="item.status==0" style="color: cornflowerblue; border-bottom: 1rpx solid cornflowerblue;"
					@tap="deleteOne(item.id)">删除</text>
				<text v-else style="color: white; background-color: #d04b41; border-radius: 5rpx;">new</text>
			</view>
			<text class="date">{{item.date}}</text>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user: {},
				notifications: [],
				unreadOnly: false,
			}
		},
		mounted() {
			this.user = uni.getStorageSync('user');
			this.refresh();
		},
		methods: {
			showUnread() {
				this.unreadOnly = !this.unreadOnly;
				let phone = this.user.phone;
				if (this.unreadOnly) {
					this.api.get('/setting/notifications/unread', {
						phone
					}).then(res => {
						this.notifications = res;
					})
				} else {
					this.api.get('/setting/notifications', {
						phone
					}).then(res => {
						this.notifications = res;
					})
				}
			},
			deleteOne(id) {
				let phone = this.user.phone;
				this.api.del('/setting/notification', {
					id
				}).then(res => {
					if (res == 204) {
						this.$toast('删除成功')
						this.refresh();
					} else {
						this.$toast('删除失败')
					}
				})
			},
			showDetail(id) {
				uni.navigateTo({
					url: "/pages/singleNotification/singleNotification?id=" + id
				})
			},
			deleteRead() {
				let phone = this.user.phone;
				this.api.del('/setting/notifications', {
					phone
				}).then(res => {
					if (res == 204) {
						this.$toast('删除所有已读消息成功');
						this.refresh();
					}
				})
			},
			refresh(){
				let phone = this.user.phone;
				this.api.get('/setting/notifications', {
					phone
				}).then(res => {
					this.notifications = res;
				})
				this.unreadOnly = false;
			}
		}
	}
</script>

<style>
	.content {
		vertical-align: middle;
		display: flex;
		flex-direction: column;
		align-items: center;
		margin-top: 30rpx;
		font-size: medium;
	}

	.head {
		display: flex;
		justify-content: space-between;
		height: 10%;
		border-bottom: 2rpx solid #d04b41;
		width: 90%;
		justify-items: center;
	}

	.notiItem {
		margin: 20rpx 0;
		border-radius: 15rpx;
		border: 2rpx solid #d04b41;
		display: flex;
		flex-wrap: wrap;
		align-items: center;
		padding: 10rpx 10rpx;
		width: 90%;
	}

	.delete {
		font-size: medium;
		background-color: #d04b41;
		color: white;
		height: 80%;
		margin-bottom: 10rpx;
		margin-left: 200rpx;
	}

	.type {
		border: 3rpx solid #e2be2d;
		background-color: #e8debf;
		margin-right: 2rpx;
		font-size: small;
		color: black;
	}

	.title {
		justify-content: space-between;
		display: flex;
		flex-wrap: wrap;
		font-size: large;
		width: 100%;
		color: #d04b41;
	}

	.date {
		font-size: small;
		color: #9c9c9c;
	}
</style>
