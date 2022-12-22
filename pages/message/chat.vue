<template>
	<view class="chat-box">
		
		<scroll-view class="chat" 
		 scroll-y="true" 
		 :scroll-into-view="scrollToView"
		 :scroll-with-animation="needScrollAnimation"
		 :style="{height:scrollViewHeight}">
			<!-- 一条聊天记录  -->
			<view class="chat-item" v-for="(item,index) in chatMsg" :key="item.id" :id="'msg' + item.id">
				<!-- 时间  -->
				<view class="time" v-if="item.isShowTime">{{handleTime(item.time)}}</view>
				<!-- b - 对方的消息  -->
				<view class="content-wrapper-left" v-if="item.fromId === other.id" >
					<!-- 头像 -->
					<!-- <image :src="other.avatar" class="avator avator-left"></image> -->
					<view class="avator-left avator">
						<u-avatar size="80rpx" :src="other.avatar"></u-avatar>
					</view>
					<!-- 0 - 文字 -->
					<view class="chat-content-left chat-content" v-if="item.types === '0'">{{item.content}}</view>
					<!-- 1 - 图片 -->
					<view class="chat-image-left" v-if="item.types === '1'">......</view>
					<!-- 2 - 语音 -->
					<view class="chat-voice-left" v-if="item.types === '2'">......</view>
					<!-- 3 - 位置信息 -->
					<view class="chat-site-left" v-if="item.types === '3'">......</view>	
				</view>
				
				<!--a - 自己的信息-->
				<view class="content-wrapper-right" v-else-if="item.fromId === user.phone">
					<view class="avator-right avatar">
						<u-avatar size="80rpx" :src="imageSrcformat(user.avatar,'jpg')"></u-avatar>
					</view>
					<!-- 0 - 文字 -->
					<view class="chat-content-right chat-content" v-if="item.types === '0'">{{item.content}}</view>
					<!-- 1 - 图片 -->
					<view class="chat-image-right" v-if="item.types === '1'">......</view>
					<!-- 2 - 语音 -->
					<view class="chat-voice-right" v-if="item.types === '2'">......</view>
					<!-- 3 - 位置信息 -->
					<view class="chat-site chat-site-right" v-if="item.types === '3'">......</view>
					
				</view>
			</view>
		
		</scroll-view>
		
	
		<view class="operate-box">
			<view class="ope-top">
				<view class="mic-box" @click="mic = !mic">
					<u-icon name="mic" size="60rpx"></u-icon>
				</view>
				
				<view class="input-box">
					<view v-if="mic">按住说话</view>
					<input v-else 
					 placeholder="想跟TA说点什么..." 
					 v-model="sendMsg.content" 
					 focus="true" 
					 confirm-type="send" 
					 confirm-hold="true" 
					 @confirm="send"
					/>
				</view>
				<view v-if="sendMsg.content" class="send-btn" @click="send">
					<text>发送</text>
				</view>
				<view v-else class="plus-box" @click="switchMenu" >
					<u-icon v-if="tapPlus" name="close" size="60rpx" ></u-icon>
					<u-icon v-else name="plus" size="60rpx"></u-icon>
				</view>
				
			</view>
			<view v-if="tapPlus" class="ope-container">
				<view><u-icon name="camera-fill"label='拍摄'  labelPos="bottom" labelSize="28rpx"size="100rpx"></u-icon></view>
				<view><u-icon name="photo-fill" label='图片'  labelPos="bottom" labelSize="28rpx" size="100rpx"></u-icon></view>
				<view><u-icon name="../../../../static/image/video_call.png" label='视频通话'  labelPos="bottom" labelSize="28rpx" size="100rpx"></u-icon></view>
				<view><u-icon name="phone-fill"label='语音通话'  labelPos="bottom" labelSize="28rpx"  size="100rpx"></u-icon></view>
				<view><u-icon name="map-fill" label='位置'  labelPos="bottom" labelSize="28rpx" size="100rpx"></u-icon></view>
				<view><u-icon name="heart-fill"label='发送宝贝'  labelPos="bottom" labelSize="28rpx" size="100rpx"></u-icon></view>
				
			</view>
		</view>
	</view>
</template>

<script>
	import {mixin} from '../../mixin.js'
	export default {
		mixins:[mixin],
		data() {
			return {
				other:{
					id:'',
					name:'',
					avatar:''
				},
				sendMsg:{
					
				},
				user:{},
				chatMsg:[
					{
						id:1,
						fromId:'15083622395',  //这个只是对方或者我的手机号
						types:'0',			//判断是文字信息、图片、视频...
						content:'你好！',	//消息内容
						isShowTime: true,  //在聊天窗口是否显示时间 ，比如一般的短时间内的聊天不显示时间
						time:'2022-11-05 14:06:22' ,//消息时间
						//如果是图片，音频，视频等需要格式，如format:'jpg'
					},
					{
						id:2,
						fromId:'18187865081', 
						types:'0',
						content:'在的',
						isShowTime:true,
						time:'2022-11-05 14:06:22'
					},
					{
						id:3,
						fromId:'15083622395',
						types:'0',
						content:'这个衣服是新的吗？',
						isShowTime:false,
						time:'2022-11-05 14:06:22'
					},
					{
						id:4,
						fromId:'15083622395',
						types:'0',
						content:'是九成新吗',
						isShowTime:true,
						time:'2022-11-05 14:06:22'
					},
					{
						id:5,
						fromId:'18187865081',
						types:'0',
						content:'是的。',
						isShowTime:false,
						time:'2022-11-05 14:06:22'
					},
					
					{
						id:6,
						fromId:'15083622395',  //这个只是对方或者我的手机号
						types:'0',			//判断是文字信息、图片、视频...
						content:'你好！',	//消息内容
						isShowTime: true,  //在聊天窗口是否显示时间 ，比如一般的短时间内的聊天不显示时间
						time:'2022-11-05 14:06:22' ,//消息时间
						//如果是图片，音频，视频等需要格式，如format:'jpg'
					},
					{
						id:7,
						fromId:'18187865081', 
						types:'0',
						content:'在的',
						isShowTime:true,
						time:'2022-11-05 14:06:22'
					},
					{
						id:8,
						fromId:'15083622395',
						types:'0',
						content:'这个衣服是新的吗？',
						isShowTime:false,
						time:'2022-11-05 14:06:22'
					},
					{
						id:9,
						fromId:'15083622395',
						types:'0',
						content:'是九成新吗',
						isShowTime:true,
						time:'2022-11-05 14:06:22'
					},
					{
						id:10,
						fromId:'18187865081',
						types:'0',
						content:'是的。',
						isShowTime:false,
						time:'2022-11-05 14:06:22'
					},
					{
						id:11,
						fromId:'15083622395',
						types:'0',
						content:'这个衣服是新的吗？',
						isShowTime:false,
						time:'2022-11-05 14:06:22'
					},
					{
						id:12,
						fromId:'15083622395',
						types:'0',
						content:'是九成新吗',
						isShowTime:true,
						time:'2022-11-05 14:06:22'
					},
					{
						id:13,
						fromId:'18187865081',
						types:'0',
						content:'是的。',
						isShowTime:false,
						time:'2022-11-05 14:06:22'
					}
				],
				// 点击加号
				tapPlus:false,
				// 语音输入
				mic:false,
				
				//页面滚动定位
				scrollToView:'',
				scrollViewHeight:'calc(100vh - 128rpx)',
				height:'',
				// 动画
				needScrollAnimation:true,
			}
		},
		onLoad: function (option) {
			this.other.id = option.phone;
			this.other.name = option.userName;
			this.other.avatar = option.avatar;
			console.log('对方的手机号是:',this.other.id);
			uni.setNavigationBarTitle({
				title:this.other.name
			});
			this.user = uni.getStorageSync('user');
			console.log(this.user.phone)
			this.scrollToView = 'msg' + this.chatMsg[this.chatMsg.length - 1].id;
			
			// 发送的消息
			this.sendMsg = {
				recivId:this.other.id,
				fromId:this.user.phone,  //这个只是对方或者我的手机号
				content:'',	//消息内容
				time:'' ,//消息时间
			}
		},
		methods: {
			resetSendMsg(){
				this.sendMsg.content = '';	//消息内容
				this.sendMsg.time = '' ;//消息时间
			},
			send(){
				this.sendMsg["time"] = uni.$u.timeFormat(new Date(), 'yyyy-mm-dd hh:MM:ss');
				
				console.log("发送的数据:",this.sendMsg)
				this.chatMsg.push({id:this.chatMsg[this.chatMsg.length - 1].id + 1,types:'0',isShowTime: true,...this.sendMsg});
				this.resetSendMsg();
				this.$nextTick(function(){
					this.scrollToView = '';
					this.scrollToView = 'msg' + this.chatMsg[this.chatMsg.length - 1].id;
					console.log(this.scrollToView)
				});
			},
			switchMenu(){
				this.tapPlus = !this.tapPlus;
				this.getHeight(".operate-box")
			},
			// 获取指定选择器元素的高度
			getHeight(classNa){
				setTimeout(() => {
					const query = uni.createSelectorQuery().in(this);
					query.select(classNa).boundingClientRect(data => {
						this.scrollViewHeight = `calc(100vh - ${data.height}px)`;
						this.scrollToView = '';
						this.$nextTick(function(){
							this.scrollToView = 'msg' + this.chatMsg[this.chatMsg.length - 1].id;
						})
					}).exec();
				},10);
			
			},
			
			handleTime(time){
				return time;
			}
		}
	}
</script>

<style lang="less" scoped>
	.chat-box {
		
		
	}
	.chat {
		padding: 20rpx 20rpx 128rpx;
		// min-height: 100vh;
		// height:calc(100vh - 128rpx);
	}
	// 内容样式
	// 文字
	.chat-content {
		box-sizing: border-box;
		padding: 20rpx;
		min-height: 80rpx;
		max-width: 500rpx;
		border-radius: 15rpx;
		font-size: 30rpx;
	}
	// 头像
	.avator {
		width: 80rpx;
		height: 80rpx;
	}
	.chat-item {
		margin: 20rpx 0;
		
		& .time {
			margin: 10rpx;
			text-align: center;
			color: #969696;
			font-size: 24rpx;
		}
		// 对方的消息显示靠左边
		& .content-wrapper-left{
			overflow: hidden;
			& .avator-left {
				float: left;
			}
			& .chat-content-left {
				margin-left: 24rpx;
				float: left;
				background-color: #dcd3d3;
			}
		}
		
		//我的消息显示靠右边
		& .content-wrapper-right {
			overflow: hidden;
			& .avator-right {
				float: right;
				margin-right: 24rpx;
			}
			& .chat-content-right {
				margin-right: 20rpx;
				float: right;
				background-color: #4daadc;
				color: #fff;
			}
		}
	}
	
	// 底部操作栏
	.operate-box {
		position: fixed;
		bottom: 0rpx;
		background-color: #eeeeee;
		font-size: 28rpx;
		width:100%;
		box-sizing: border-box;
	}
	.ope-top {
		
		display: flex;
		justify-content: space-around;
		align-items: center;
		box-sizing: border-box;
		height: 128rpx;
		
		
		& .input-box {
			flex: 0.9;
			height: 100rpx;
			& input,view {
				margin-top: 20rpx;
				background-color: #fff;
				height: 60rpx;
				border-radius: 10rpx;
				box-sizing: border-box;
				padding: 0 5rpx;
			}
			& view {
				line-height: 60rpx;
				text-align: center;
				color: #969696;
			}
		}
		& .send-btn {
			width: 100rpx;
			height: 60rpx;
			font-size: 28rpx;
			
			& text {
				display: block;
				width: 100%;
				height: 100%;
				border-radius: 5px;
				background-color: #53b5ff;
				color: #fff;
				text-align: center;
				line-height: 60rpx;
			}
		}
		& .mic-box {
			width: 60rpx;
			height: 60rpx;
		}
		& .plus-box {
			width: 60rpx;
			height: 60rpx;
		}
	}
	.ope-container {
		display: flex;
		padding: 30rpx;
		flex-wrap: wrap;
		box-sizing: border-box;
		
		& view {
			margin: 30rpx;
			color: #969696;
		}
		
	}
</style>
