<template>
	<view class="helpContent">
		<view v-for="(item,index) of catalog" :key="index">
			<view class="helpItem" @tap="helpFor(index)">
				<text>{{item}}</text>
				<text v-if="show!=index">▶</text>
				<text v-else>◢</text>
			</view>
			<view v-show="show==index" class="secItemcontent" v-for="(secItem,secindex) of secCatelog"
				@tap="showHelp(secItem)">
				{{secItem}}
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				catalog: [],
				secCatelog: [],
				popText: '',
				show: -1
			}
		},
		mounted() {
			let that = this;
			that.api.get('/setting/help').then(res => {
				this.catalog = res;
				console.log('catalog:', this.catalog)
			})
		},
		methods: {
			helpFor(index) {
				if (index == this.show) {
					this.show = -1;
				} else {
					this.show = index;
					let catalog = this.catalog[index];
					let that = this;
					that.api.get('/setting/help/catalog', {
						catalog
					}).then(res => {
						this.secCatelog = res;
						console.log('sec:', this.secCatelog)
					})
				}
				console.log(this.show)
			},
			showHelp(secCatalog) {
				let that = this;
				let question = secCatalog;
				that.api.get('/setting/help/catalog/question', {
					question
				}).then(res => {
					uni.showModal({
						content: res,
						showCancel:false
					})
				})
			},
		}
	}
</script>

<style>
	.helpContent {
		font-size: medium;
		margin-top: 5%;
	}

	.helpItem {
		display: flex;
		justify-content: space-between;
		margin: 5% 5%;
		border-bottom: 2rpx solid #d04b41;
		font-weight: 600;
		height: 100rpx;
	}

	.secItemcontent {
		margin-bottom: 5%;
		margin-left: 10%;
		margin-right: 5%;
		height: 50rpx;
		border-bottom: 1rpx solid #cccccc;
	}
</style>
