const commoneUrl  = "https://localhost:8001"; //公共路径 
// const commoneUrl  = "http://192.168.1.11:8080"; //手机测试公共路径 
// const commoneUrl  = "http://localhost:8080"; //公共路径 
const userId = uni.getStorageSync("user").id //用户id

//post请求封装
function postRequest(url,data,type){
	var promise = new Promise((resolve,reject) => {
		var postData = data;
		const that = this;
		uni.request({
			url:commoneUrl + url,
			data:postData,
			method:'POST',
			
			header:{
				'content-type': 'application/json',
				'token':uni.getStorageSync('token')//token可以不要，看后端
			},
				
			success:function(res){
				if(res.statusCode == 500) responseError(res)
				else if(res.statusCode == 200) resolve(res.data);
				
			},
			fail:function(e)
			{
				reject('网络出错');
			}
		})
	});
	return promise;
}

//get请求封装
function getRequest(url,data,type){
	var promise = new Promise((resolve,reject) => {
		
			var postData = data;
			uni.request({
				url:commoneUrl + url,
				data:postData,
				method:"GET",
				dataType:'json',
				header:{
					'content-type':'application/json',
					'token':uni.getStorageSync('token')
				},
				success:function(res){
					
					if(res.statusCode == 500) responseError(res)
					else if(res.statusCode == 200) resolve(res.data);
				},
				fail:function(e){
					console.log(e)
					reject('网络出错');
				}
			});
	});
	return promise;
}
//put请求封装
function putRequest(url,data){
	var promise = new Promise((resolve,reject) => {
			var postData = data;
			uni.request({
				url:commoneUrl + url,
				data:postData,
				method:"PUT",
				dataType:'json',
				header:{
					'content-type': 'application/json',
					'token': uni.getStorageSync('token')
				},
				success:function(res){
					if(res.statusCode == 500) responseError(res)
					else if(res.statusCode == 200) resolve(res.data);
				},
				fail:function(e){
					reject('网络出错');
				}
			});
	});
	return promise;
}
//del请求封装
function delRequest(url,data){
	var promise = new Promise((resolve,reject) => {
			var postData = data;
			uni.request({
				url:commoneUrl + url,
				data:postData,
				method:"DELETE",
				dataType:'json',
				header:{
					'content-type': 'application/json',
					'token': uni.getStorageSync('token')
				},
				success:function(res){
					if(res.statusCode == 500) responseError(res)
					else if(res.statusCode == 200) resolve(res.data);
				},
				fail:function(e){
					reject('网络出错');
				}
			});
	});
	return promise;
}
function responseError(res){
	const that =  this;
	switch(res.data.message){
		
		case '401':
		case '用户错误，请重新登录':
			uni.showToast({
				
				title: '身份验证失败，请重新登录!',
				duration: 2000,
				icon:'none'
			});
			setTimeout(()=>{
				uni.removeStorageSync('user');
				uni.redirectTo({
					url:'/pages/login/index'
				})
			},1000)
			break;
		default :
			uni.showToast({
				
				title: '服务器错误!',
				duration: 2000,
				icon:'none'
			});
	}
	
	
	
}

module.exports = {
    post: postRequest,
    get: getRequest,
	put: putRequest,
	del: delRequest,
	ip: commoneUrl,
	userId : userId
};
