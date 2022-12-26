<template>
	<view class="reportMan">
		<view class="top">
			<view class="report-title">举报审核列表</view>
			<view class="reload">
				<u-icon name="reload" color="#2979ff" @click="getReport" labelPos="bottom" size="50rpx"></u-icon>
			</view>
			
			<!-- 中间举报状态分类 -->
			<view class="sort">
				<text class="one" @click="toOne(state0)" ref="全部" :style="{color: allfont}">{{state0}}</text>
				<text class="one" @click="toOne(state1)" ref="已通过" :style="{color: firstfont}">{{state1}}</text>
				<text class="one" @click="toOne(state2)" ref="待审核" :style="{color: secondfont}">{{state2}}</text>
				<text class="one" @click="toOne(state3)" ref="未通过" :style="{color: thirdfont}">{{state3}}</text>
			</view>
		</view>
		
		<view class="report-list">
			<uni-table ref="table" :loading="loading" border stripe emptyText="暂无更多数据" @selection-change="selectionChange">
				<uni-tr>
					<uni-th width="60" align="center">编号</uni-th>
					<uni-th align="center">商品编号</uni-th>
					<uni-th align="center">举报日期</uni-th>
					<uni-th align="center">操作</uni-th>
				</uni-tr>
				<uni-tr v-for="(item, index) in reportItem" :key="index">
					<uni-td align="center">{{item.id}}</uni-td>
					<uni-td align="center">{{item.number}}</uni-td>
					<uni-td align="center">{{item.date}}</uni-td>
					<uni-td align="center">
						<button @click="toDetails(item)" class="oper">查看</button>
					</uni-td>
				</uni-tr>
			</uni-table>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 商品举报列表
				reportItem:[],
				// 举报状态
				status:-1,
				state:'',
				one:{},
				state0:'全部',
				allfont:'#b34c26',
				state1:'已通过',
				firstfont:'gray',
				state2:'待审核',
				secondfont:'gray',
				state3:'未通过',
				thirdfont:'gray',
			}
		},
		mounted() {
			this.getReport();	
		},
		onShow(){
			this.getReport();
		},
		methods: {
			// 查看举报详情
			toDetails(item){
				var oneitem = JSON.stringify(item);
				uni.navigateTo({
					url:'/pages/manage/others/reportDetail?oneReport=' + oneitem
				})	
			},
			
			// 举报状态分类
			async toOne(state){
				const that  = this;
				
				if(state == '全部'){
					this.allfont = '#b34c26';
					this.firstfont = 'gray';
					this.secondfont = 'gray';
					this.thirdfont = 'gray';
					this.status = -1;
				}
				else if(state == '已通过'){
					this.allfont = 'gray';
					this.firstfont = '#b34c26';
					this.secondfont = 'gray';
					this.thirdfont = 'gray';	
					this.status = 1;
				}
				else if(state == '待审核'){
					this.allfont = 'gray';
					this.firstfont = 'gray';
					this.secondfont = '#b34c26';
					this.thirdfont = 'gray';
					this.status = 0;
				}
				else{
					this.allfont = 'gray';
					this.firstfont = 'gray';
					this.secondfont = 'gray';
					this.thirdfont = '#b34c26';	
					this.status = 2;
				}
				
				if(this.status == -1){
					this.getReport();
				}
				else{
					try{
						that.reportItem = await this.api.get('/product/status/reports',{status:this.status});
					}catch(e){
						//TODO handle the exception
						that.$toast(e)
					}
				}				
			},
			
			// 获取所有举报记录
			async getReport(){
				const that  = this;
				try{
					that.reportItem = await this.api.get('/product/reports');
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
		}
	}
</script>

<style>
	.reportMan {
		margin: 20px 20px 0;
	}
	.top {
		display: flex;
		align-items: center;
		height: 100rpx;
	}
	.report-title {
		margin: 20rpx 0;
		width: 35%;
		font-size: 32rpx;
		color: #be0000;
		padding-bottom: 36rpx;
		text-shadow: 5rpx 5rpx 2rpx #24c3be;
	}
	
	.reload{
		margin-left: 10rpx;
		padding-bottom: 36rpx;
	}
	
	.sort{
		margin-left: 50rpx;
		font-size: 28rpx;
		color: #be0000;
		font-weight: 600;
		text-shadow: 5rpx 5rpx 2rpx #24c3be;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		width: 65%;
	}
	
	.one{
		text-align: center;
	}
	
	.report-list{
		font-size: 28rpx;
	}
	
	.oper{
		width: 125rpx;
		height: 60rpx;
		font-size: 28rpx;
		color: #be0000;
		text-shadow: 5rpx 5rpx 2rpx #24c3be;
		text-align: center;
		line-height: 60rpx;
		font-weight: 600;
		background-color: white;
		border: none;
	}
</style>