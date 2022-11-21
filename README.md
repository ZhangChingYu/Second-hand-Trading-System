# Second-hand-Trading-System

#### 项目结构
- commonn  项目公共文件
    > uni.css   
	> ......
- components 项目组件
	> goods   
		> index.vue
- pages 项目页面文件
	> home  
		> index.vue

	> login  
		> index.vue

	> ......
- static 项目静态文件
	> image

	> ......
- store vuex文件
	> index.js

	> ......
- components 项目组件文件
	> ......
- ......  	
- App.vue 项目主文件
- index.html 项目页面容器
- main.js 项目入口js文件
- mannifest.js 项目配置
- pages.json 项目全局页面配置
- uni.scss uni样式文件


### 登录功能：
  目前支持账号登陆和微信登录。
    账号登陆支持手机号和邮箱，限制密码为8-16位数字与字母组合暂定为/login，提供账号名，密码和登录账号类型，需要登陆用户id和token以及登录状态   
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
		setTimeout(()=-{
			uni.redirectTo({
				url: `/pages/home/index?id=${userid}&token=${token}`
			});
		},500);
	}
	else this.$toast('登录失败！',1270);
  ```
  
  ### 暂未与 忘记密码 和 注册 对接

## 主页
   目前仅实现静态页面
