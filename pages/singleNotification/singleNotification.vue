<template>
	<view class="content">
		<view class="title">{{datas.title}}</view>
		<view class="date">{{datas.date}}</view>
		<view class="to">亲爱的{{datas.to}}用户：</view>
		<view class="text">{{datas.content}}</view>
		<view class="from">来自{{datas.from}}</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				datas:{}
			}
		},
		onLoad(e) {
			let id=e.id;
			this.api.get('/setting/notification',{id}).then(res=>{
				this.datas=res;
				let pages=getCurrentPages();
				let curr=pages[pages.length-1];
				let pre=pages[pages.length-2];
				let ppre=pages[pages.length-3];
				let pppre=pages[pages.length-4];
				let phone=uni.getStorageSync('user').phone;
				pre.$vm.refresh();
				this.api.get('/setting/unread/count',{phone}).then(res1=>{
					ppre.$vm.unreadCount=res1;
					pppre.$vm.unreadCount=res1;
				})
			})
		},
		methods: {
			
		}
	}
</script>

<style scoped>
	.content{
		justify-content: center;
		align-items: center;
		margin-top: 2%;
		font-size: medium;
	}
	.title{
		font-size: large;
		font-weight: 700;
		color: #d04b41;
		text-align: center;
	}
	.date{
		text-align: center;
		color: #747474;
		font-size: small;
	}
	.to{
		margin: 10% 5%;
	}
	.text{
		margin: 0 10%;
	}
	.from{
		text-align: right;
		margin-right: 5%;
		margin-top: 10%;
	}
</style>
