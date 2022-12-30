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
						icon: 'https://img2.baidu.com/it/u=4170078635,925514702&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=526'
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
				userItem:[
					{
						title: '用户信息管理',
						url: 'pages/userManage/userManage',
						icon: 'http://img95.699pic.com/element/40149/2920.png_860.png'
					},{
						title: '实名认证管理',
						url: 'pages/realName/realName',
						icon: 'https://bpic.588ku.com/element_origin_min_pic/20/08/26/632c752cfe4b9a6bf92cb50442e26f67.jpg'
					},
				],
				// 其他管理:首页轮播图、公告、用户协议....
				otherItem:[
					{
						title: '举报审核',
						url: 'pages/manage/others/reportManage',
						icon: 'https://img0.baidu.com/it/u=3327668379,4177683952&fm=253&fmt=auto&app=138&f=JPEG?w=439&h=499',
					},{
						title: '用户协议',
						url: 'pages/manage/others/agreementManage',
						icon: 'https://img1.baidu.com/it/u=2274835460,799751483&fm=253&fmt=auto&app=138&f=JPEG?w=396&h=500'
<<<<<<< HEAD
					},
=======
					},{
						title: '轮播图',
						url: 'pages/manage/others/swiper',
						icon: 'https://img2.baidu.com/it/u=1982708071,4271452793&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500'
					}
>>>>>>> 57cc9a6c8fae09bb9e027ca0c05549014acf7d4a
				],
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