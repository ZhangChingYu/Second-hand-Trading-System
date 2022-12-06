<template>
	<view>
		<!-- 有数据 -->
		<ul class="content" v-if="list.length != 0">
			<li v-for="(item,index) of list" :key="index">
				<u-icon 
				:name="item.icon" 
				:label="item.title" 
				labelPos="bottom"
				labelSize="26rpx"
				size="150rpx"></u-icon>
			</li>
		</ul>
		<!-- 无数据 -->
		<u-empty v-else></u-empty>
	</view>

</template>

<script>
	export default {
		props:['list'],
		name:"menuItem",
		data() {
			return {
				
			};
		}
	}
</script>

<style lang="less" scoped>
	.content {
		display: flex;
		flex-wrap: wrap;
		justify-content: flex-start;
		width: 100%;
		
		& li {
			margin-right: 25rpx;
			margin-bottom: 30rpx;
		}
		& li:nth-child(4n) {
			margin-right: 0;
		}
	}
	
	
	
</style>