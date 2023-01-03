<template>
	<view class="message">	
		<!-- <u-list
			@scrolltolower="scrolltolower"
		> --> 
		<u-list>
			<u-list-item
				v-for="item in chatList"
				:key="item.phone"
			>
				<u-cell
					:title="item.userName"
					:label = "item.lastChat"
					@click="toChat(item)"
				>
				<!-- <u-badge  max="99" value="3"></u-badge> -->
					<u-avatar
						slot="icon"
						shape="square"
						size="50"
						:src="item.avatar"
						customStyle="margin: -3px 5px -3px 0"
					></u-avatar>
				</u-cell>
			</u-list-item>
		</u-list>
		<u-loadmore :status="status" />
	</view>
</template>

<script>
	
	export default {
		data() {
			return {
				status:'nomore',
				user:{},
				chatList:[
					{
						phone:'15355960093',
						userName:'天天',
						avatar:'https://cdn.uviewui.com/uview/album/1.jpg',
						lastChat:'在干嘛呢？'
					},
					{
						phone:'18187865081',
						userName:'小赵',
						avatar:'https://cdn.uviewui.com/uview/album/4.jpg',
						lastChat:'请问在吗'
					},
					{
						phone:'12377645990',
						userName:'大睡',
						avatar:'https://cdn.uviewui.com/uview/album/10.jpg',
						lastChat:'这个衣服质量怎么样，可以面交吗？'
					},
					{
						phone:'15309887783',
						userName:'泥鳅',
						avatar:'https://cdn.uviewui.com/uview/album/5.jpg',
						lastChat:'OK，好的好的'
					},
					{
						phone:'13547773398',
						userName:'平安是福',
						avatar:'https://cdn.uviewui.com/uview/album/2.jpg',
						lastChat:'嗯嗯。'
					},
				],
				
			}
		},
		mounted() {
			this.user = uni.getStorageSync('user');
			this.getChatList()
		},
		onPullDownRefresh(){
			this.getChatList()
		},
		
		methods: {
			async getChatList(){
				try{
					let res = await this.api.get('/chat/getChatList',{fromId:this.user.phone});
					console.log(res);
					// this.chatList = res.data
				}catch(e){
					//TODO handle the exception
					this.$toast(e);
				}
			},
			
			toChat(item){
				uni.navigateTo({
					url:`/pages/message/chat?phone=${item.phone}&userName=${item.userName}&avatar=${item.avatar}`
				})
			}
		},
	}
</script>

<style>

	
</style>
