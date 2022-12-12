<template>
	<view class="no-more">
		<view> {{notips}}</view>
	</view>
</template>

<script>
	export default {
		name:"nomore",
		data() {
			return {
				
			};
		},
		props:['notips']
	}
</script>

<style>
	.no-more {
		margin: 30rpx auto;
		text-align: center;
		font-size: 24rpx;
		color: #cacaca;
	}
</style>