<template>
	<view class="manage">
		<view class="product-manage box">
			<view class="title">商品管理</view>
			<MenuItem :list="productItem"></MenuItem>
		</view>
		
		<view class="user-manage box">
			<view class="title">用户管理</view>
			<MenuItem :list="userItem"></MenuItem>
		</view>
		
		<view class="user-manage box">
			<view class="title">其他</view>
			<MenuItem :list="otherItem"></MenuItem>
		</view>
		
	</view>
</template>

<script>
	import MenuItem from '@/components/menuItem/index.vue'
	export default {
		components:{MenuItem},
		data() {
			return {
				// 管理中心菜单
				// 商品管理
				productItem: [
					{
						title: '商品查询',
						url: 'pages/manage/product/query',
						icon: 'https://t11.baidu.com/it/u=2685890614,3511995960&fm=173&app=49&f=JPEG?w=500&h=502&s=9404FC160FE24E8492A6E97C0300806B'
					},{
						title: '商品类别',
						url: 'pages/manage/product/catalog',
						icon: 'https://img1.baidu.com/it/u=3162166177,1798046746&fm=253&fmt=auto&app=138&f=JPEG?w=650&h=458'
					},{
						title: '上架审核',
						url: 'pages/manage/product/review',
						icon: 'https://img1.baidu.com/it/u=3994578752,3321787456&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500'
					}
				],
				// 用户管理:用户审核、用户信息、账号...
				userItem:[],
				// 其他管理:首页轮播图、公告、用户协议....
				otherItem:[],
			}
		},
		methods: {
			left() {
			console.log('left');
			},
			right() {
				console.log('right');
			}
		}
	}
</script>

<style lang="scss" scoped>

// 整体
.manage {
	display: flex;
	flex-direction: column;
	align-items: center;
}
.box {
	margin-top: 50rpx;
	width: 90%;
}
.title {
	width: 95%;
	margin-bottom: 10px;
	font-weight: 500;
	font-size: 36rpx;
	color: gray;
	}

// 商品管理中心
	
	
</style>