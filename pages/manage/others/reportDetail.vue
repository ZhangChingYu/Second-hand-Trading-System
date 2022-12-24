<template>
	<view class="details">
		
		<view class="top">
			<image src="../../../static/image/report.png" class="topImg"></image>
			<view class="report-title">举报记录ID： {{oneReport.id}}</view>		
		</view>
		
		<view class="more">
			<uni-table ref="table" :loading="loading" border stripe emptyText="暂无更多数据" @selection-change="selectionChange">
				<uni-tr>
					<uni-td width="80" align="center">举报人</uni-td>
					<uni-td align="center">{{details.reportName}}</uni-td>
				</uni-tr>
				<uni-tr>
					<uni-td align="center">联系电话</uni-td>
					<uni-td align="center">{{details.reporterPhone}}</uni-td>
				</uni-tr>
				<uni-tr>
					<uni-td align="center">举报时间</uni-td>
					<uni-td align="center">{{details.date}}</uni-td>
				</uni-tr>
				<uni-tr>
					<uni-td align="center">举报原因</uni-td>
					<uni-td align="center">{{details.content}}</uni-td>
				</uni-tr>
				<uni-tr>
					<uni-td align="center">商品名称</uni-td>
					<uni-td align="center">{{details.productName}}</uni-td>
				</uni-tr>
				<uni-tr>
					<uni-td align="center">商品编号</uni-td>
					<uni-td align="center">{{details.productNumber}}</uni-td>
				</uni-tr>
				<uni-tr>
					<uni-td align="center">举报次数</uni-td>
					<uni-td align="center">{{details.reportCount}}</uni-td>
				</uni-tr>
			</uni-table>
		</view>
		
		<view class="bottom">
			<view class="state">处理状态：{{state}}</view>
			<view class="oper" v-if="noOper">
				<button class="reject" @click="reject">拒绝</button>
				<button class="pass" @click="pass">通过</button>
			</view>	
		</view>
		
		<view class="explain" v-if="noOper">
			<uni-easyinput type="textarea" autoHeight v-model="explain" placeholder="请给出判决说明~"></uni-easyinput>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				oneReport:{},
				details:{},
				state:'待处理',
				noOper:true,
				explain:'',
				decision:'',
			}
		},
		mounted() {
			this.getDetail();
		},
		onLoad(option){
			this.oneReport = JSON.parse(option.oneReport);
			console.log(this.oneReport);
			if(this.oneReport.status == 1){
				this.state = '已通过';
				this.noOper = false;
			}
			else if(this.oneReport.status == 2){
				this.state = '未通过';
				this.noOper = false;
			}
			else{
				this.state = '待处理';
				this.noOper = true;
			}
			console.log(this.noOper);
		},
		methods: {
			// 获取举报详情
			async getDetail(){
				const that  = this;
				try{
					that.details = await this.api.get('/product/report',{id:this.oneReport.id});
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 举报处理
			pass(){
				this.decision = 'pass';
				this.result()
			},
			
			reject(){
				this.decision = 'reject';
				this.result();
			},
			
			async result(){
				if(this.explain != ''){
					const that  = this;
					try{
						let res = await this.api.put('/product/report/result',{id:this.oneReport.id,decision:this.decision,explain:this.explain});
						if(res == 201){
							uni.showToast({
								title: '处理成功！',
								icon: 'success',
								duration: 30000
							})
							uni.navigateBack({
								delta:1, //返回层数，2则上上页
							})
						}
						else if(res == 404){
							uni.showToast({
								title: '请求不合法！',
								icon: 'warning',
								duration: 30000
							})
						}
						else {
							uni.showToast({
								title: '处理失败！',
								icon: 'warning',
								duration: 30000
							})
						}
					}catch(e){
						//TODO handle the exception
						that.$toast(e)
					}
				}
				else this.$toast('请先填写判决说明~')
			},
		}
	}
</script>

<style>
	.details {
		margin: 20px 20px 0;
	}
	.top {
		display: flex;
		align-items: center;
		height: 100rpx;
	}
	.topImg{
		width: 52rpx;
		height: 52rpx;
		margin-right: 10rpx;
	}
	.report-title {
		margin: 20rpx 0;
		width: 100%;
		font-size: 32rpx;
		color: #be0000;
		text-shadow: 5rpx 5rpx 2rpx #24c3be;
	}
	.bottom{
		display: flex;
		flex-direction: row;
		margin: 25rpx 0;
		justify-content: space-between;
	}
	.state{
		margin: 20rpx 0;
		font-size: 32rpx;
		color: #be0000;
		text-shadow: 5rpx 5rpx 2rpx #24c3be;
	}
	.oper{
		margin: 5rpx 0;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
	.pass{
		margin-left: 25rpx;
		width: 125rpx;
		height: 60rpx;
		font-size: 30rpx;
		color: #be0000;
		text-shadow: 5rpx 5rpx 2rpx #24c3be;
		text-align: center;
		line-height: 60rpx;
		font-weight: 600;
		background-color: white;
		background-color: antiquewhite;
	}
	.reject{
		width: 125rpx;
		height: 60rpx;
		font-size: 30rpx;
		color: gray;
		text-shadow: 5rpx 5rpx 2rpx #24c3be;
		text-align: center;
		line-height: 60rpx;
		font-weight: 600;
		background-color: white;
		background-color: antiquewhite;
	}
	.explain{
		width: 100%;
		margin-top: 25rpx;
	}
</style>