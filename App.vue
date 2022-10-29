<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
	/*每个页面公共css */
	* {
		padding: 0;
		margin: 0;
		box-sizing: border-box;
	}
	body {
		/* font-family: 'Courier New', Courier, monospace; */
		font-size: 62.5%;
	}
	
	/* 商品展示公共 */
	
	/* 商品信息边框 */
	.detail-item{
		width: 47.5%;
		height: 6.8rem;
		margin: 0.68rem 0 0 0;
		background-color: #fff;
		position: relative;
		border-right: 1px solid darkgray;
		border-bottom: 1px solid darkgray;
	}
	
	/* 商品图片 */
	.detail-item image {
		width: 100%;
		height: 70%;
	
	}
	/* 商品文字描述 */
	.detail-text{
		display: inline-block;
		width: 70%;
		height: 2rem;
		font-size: 0.88rem;
		line-height: 2rem;
		padding-left: 2px;
		text-overflow:ellipsis;
		white-space: nowrap;
		overflow: hidden;
	}
	/* 价格 */
	.price{
		position: absolute;
		bottom: 0.24rem;
		right: 0.24rem;
		color: red;
		font-weight: 700;
		font-size: 1rem;
		background-color: #fff;
	}
</style>
