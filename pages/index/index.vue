<template>
	<view class="content">
		<image class="logo" src="/static/logo.png"></image>
		<view class="text-area">
			
			<h1>我们服务端使用<a href="https://www.uviewui.com/components/button.html">uView组件库</a>，好处就是面向cv编程，提高效率</h1>
			
		</view>
		<view>
			例如：<br>
				<u-button type="primary" :plain="true" text="镂空"></u-button>
				<u-button type="primary" :plain="true" :hairline="true" text="细边"></u-button>
				<u-button type="primary" :disabled="disabled" text="禁用"></u-button>
				<u-button type="primary" loading loadingText="加载中"></u-button>
				<u-button type="primary" icon="map" text="图标按钮"></u-button>
				
				<u-loading-icon></u-loading-icon>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
			}
		},
		onLoad() {

		},
		methods: {

		}
	}
</script>

<style>
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
</style>
