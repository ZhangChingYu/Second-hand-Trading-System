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
		margin: 2px auto;
		text-align: center;
		color: #cacaca;
	}
</style>