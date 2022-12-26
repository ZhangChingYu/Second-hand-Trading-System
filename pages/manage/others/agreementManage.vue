<template>
	<view class="main">
		
		<view class="top">
			<image src="../../../static/image/agreement.png" class="topImg"></image>
			<view class="title">历史用户协议</view>		
		</view>
		
		<view class="list">
			<uni-table ref="table" :loading="loading" border stripe emptyText="暂无更多数据" @selection-change="selectionChange">
				<uni-tr>
					<uni-th width="60" align="center">版本</uni-th>
					<uni-th align="center" width="200">发布日期</uni-th>
					<uni-th align="center">操作</uni-th>
				</uni-tr>
				<uni-tr v-for="(item, index) in agreements" :key="index">
					<uni-td align="center">{{item.version}}</uni-td>
					<uni-td align="center">{{item.date}}</uni-td>
					<uni-td align="center">
						<view class="someOper">
							<button @click="seeOne(item)" class="see">查看</button>
							<button @click="deleteOne(item,index)" class="delete">删除</button>
						</view>
					</uni-td>
				</uni-tr>
			</uni-table>
		</view>
		
		<view class="top" style="margin-top: 25rpx;">
			<image src="../../../static/image/refresh.png" class="topImg"></image>
			<view class="title">更新用户协议</view>		
		</view>
		
		<view class="id">
			<uni-easyinput type="textarea" autoHeight v-model="version" placeholder="请输入用户协议版本编号~"></uni-easyinput>
		</view>
		
		<view class="agreement">
			<uni-easyinput type="textarea" autoHeight v-model="agreement" placeholder="请输入相关用户协议内容~" maxlength="-1"></uni-easyinput>
		</view>
		
		<view class="bt">
			<button class="up" @click="newOne">发 布</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				agreements:[],
				version:'',
				agreement:'',
			}
		},
		mounted() {
			this.getAgreements();
		},
		onShow(){
			this.getAgreements();
		},
		methods: {
			// 获取所有用户协议
			async getAgreements(){
				const that  = this;
				try{
					that.agreements = await this.api.get('/principles');
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 发布新的用户协议
			newOne(){
				let that = this;
				if(this.agreement != '' && this.version != ''){
					uni.showModal({
						title: '协议发布',
						// 提示文字
						content: '是否发布新的用户协议？',
						// 取消按钮的文字自定义
						cancelText: "取消",
						// 确认按钮的文字自定义
						confirmText: "确认",
						//删除字体的颜色
						confirmColor:'red',
						//取消字体的颜色
						cancelColor:'#000000',
						success: function(res) {
							if(res.confirm) {  
								that.postOne();	
							}   								 
						},
					})
				}
				else this.$toast('请先完善新的用户协议！')
			},
			async postOne(){
				const that  = this;
				try{
					let res = await this.api.post('/principle',{version:this.version,content:this.agreement});
					if(res == 200) this.$toast('发布成功！');
					else if(res == 404) this.$toast('发布失败，版本已存在！');
					else this.$toast('发布失败！');
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
			
			// 查看某个协议
			seeOne(item){
				var oneitem = JSON.stringify(item);
				uni.navigateTo({
					url:'/pages/manage/others/agreementDetail?oneDetail=' + oneitem
				})	
			},
			
			// 删除某个协议
			deleteOne(item,index){
				let that = this;
				uni.showModal({
					title: '删除协议',
					// 提示文字
					content: '是否删除该用户协议？',
					// 取消按钮的文字自定义
					cancelText: "取消",
					// 确认按钮的文字自定义
					confirmText: "确认",
					//删除字体的颜色
					confirmColor:'red',
					//取消字体的颜色
					cancelColor:'#000000',
					success: function(res) {
						if(res.confirm) {  
							that.confirmDel(item,index);	
						}   								 
					},
				})
			},
			async confirmDel(item,index){
				const that  = this;
				try{
					let res = await this.api.del('/principle',{version:item.version});
					if(res == 204) {
						this.$toast('删除成功！');
						this.getAgreements();
					}
					else this.toast('删除失败！');
				}catch(e){
					//TODO handle the exception
					that.$toast(e)
				}
			},
		}
	}
</script>

<style>
	.main{
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
	.title {
		margin: 20rpx 0;
		width: 100%;
		font-size: 32rpx;
		color: #be0000;
		text-shadow: 5rpx 5rpx 2rpx #24c3be;
	}
	.someOper{
		display: flex;
		flex-direction: row;
	}
	.see{
		width: 125rpx;
		height: 60rpx;
		font-size: 28rpx;
		color: #be0000;
		text-shadow: 5rpx 5rpx 2rpx #24c3be;
		text-align: center;
		line-height: 60rpx;
		font-weight: 600;
		background-color: white;
	}
	.delete{
		width: 125rpx;
		height: 60rpx;
		font-size: 30rpx;
		color: gray;
		text-shadow: 5rpx 5rpx 2rpx #24c3be;
		text-align: center;
		line-height: 60rpx;
		font-weight: 600;
		background-color: white;
	}
	.bt{
		margin-top: 25rpx;
	}
	.id{
		height: 80rpx;
	}
	.up{
		height: 60rpx;
		font-size: 32rpx;
		color: #be0000;
		text-shadow: 5rpx 5rpx 2rpx #24c3be;
		text-align: center;
		line-height: 60rpx;
		font-weight: 600;
		background-color: antiquewhite;
	}
</style>