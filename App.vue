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

<style lang='scss'>
	/*每个页面公共css */
	/* @import '@/static/css/common.css'; */
		@import "@/uni_modules/uview-ui/index.scss";
	* {
		margin: 0;
		padding: 0;
		box-sizing: border-box;
	}
	page {
		width: 100vw;
		min-height: 100vh;
	}
</style>
