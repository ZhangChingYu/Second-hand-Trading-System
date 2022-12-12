<template>
	<view class="helpContent">
		<view v-for="(item,index) of catalog" :key="index">
			<view class="helpItem" @tap="helpFor(index)">
				<text>{{item.catalog}}</text>
				<text v-if="!item.show">▶</text>
				<text v-else>◢</text>
			</view>
			<view v-if="item.show" class="secItemcontent" v-for="(secItem,index) of secCatelog"
				@tap="showHelp(secItem)">
				{{secItem.question}}
			</view>
		</view>
		<!--弹出弹窗显示帮助内容，uni-popup组件只能在最外层的view使用-->
		<uni-popup ref="popup" type="bottom" backgroundColor="#ffffff" maskBackgroundColor="#ffffff">{{popText}}</uni-popup>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				catalog: [],
				secCatelog: [],
				popText:''
			}
		},
		mounted() {
			let that = this;
			that.api.get('/setting/help').then(res => {
				this.catalog = res;
				this.catalog.forEach(v => {
					this.$set(v, 'show', false);
				})
				console.log('catalog:', this.catalog)
			})
		},
		methods: {
			helpFor(index) {
				let state = this.catalog[index].show;
				this.catalog.forEach(v => {
					if (v.show) {
						v.show = false
					}
				})
				this.catalog[index].show = !state;
				if (this.catalog[index].show == true) {
					let that = this;
					let catalog = this.catalog[index].catalog;
					that.api.get('/setting/help/catalog', {
						catalog
					}).then(res => {
						this.secCatelog = res;
						console.log('sec:', this.secCatelog)
					})
				}
			},
			showHelp(secCatalog) {
				let that = this;
				let question = secCatalog.question;
				that.api.get('/setting/help/catalog/question', {
					question
				}).then(res => {
					this.popText=res;
					this.open();
				})
			},
			open() {
				// 通过组件定义的ref调用uni-popup方法 ,如果传入参数 ，type 属性将失效 ，仅支持 ['top','left','bottom','right','center']
				this.$refs.popup.open('center')
			}
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
