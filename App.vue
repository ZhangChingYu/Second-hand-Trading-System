<script>
	

	export default {
		onLaunch: function() {
			console.log('App Launch')
			uni.setStorageSync('choosedCatalog',-1);
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style lang="scss">

	/* uni.scss */
	@import 'uni_modules/uview-ui/index.scss';
	@import 'uni_modules/uview-ui/theme.scss';
	
	/*每个页面公共css */
	* {
		padding: 0;
		margin: 0;
		box-sizing: border-box;
	}
	a {
		text-decoration: none;
	}
	li {
		list-style: none;
	}
	body {
		font-size: 62.5%;
	}
</style>
