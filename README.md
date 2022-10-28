# Second-hand-Trading-System
### 登录功能：
  目前支持账号登陆和微信登录。
    账号登陆支持手机号和邮箱，限制密码为8-16位数字与字母组合由于未得到后端api,暂定为/login，提供账号名，密码和登录账号类型，需要登陆用户id和token以及登录状态
    微信登录暂定为/wxlogin，提供rawData和code(由微信登陆服务商获得），需要信息与账号登陆一致
  ```javascript
    //登录成功状态码约定为666
    if(res.data.data.code === 666){
		let userid = res.data.data.userid;
		let token = res.data.data.token;

		// 在store中存登录信息
		this.tologin(userid,token);

		// 在本地存储登录信息
		// localStorage.setItem('userid',userid);
		// sessionStorage.setItem('token',token);

		this.$toast('登录成功！',1270);
		// 跳转主页
		setTimeout(()=>{
			uni.redirectTo({
				url: `/pages/home/index?id=${userid}&token=${token}`
			});
		},500);
	}
	else this.$toast('登录失败！',1270);
  ```
  
  ### 暂未与 忘记密码 和 注册 对接
