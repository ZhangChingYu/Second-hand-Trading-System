<template>
	<view>
		<button @click="tologin">立即登录</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			tologin(){
				uni.redirectTo({
					url:'/pages/login/index'
				})
			}
		}
	}
</script>

<style>

</style>
