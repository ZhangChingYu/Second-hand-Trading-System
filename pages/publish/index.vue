<template>
	<view class="product-publish">
		<view class="product-image" >
			
			<u-upload
				:fileList="fileList"
				@afterRead="afterRead"
				@delete="deletePic"
				name="1"
				:disabled="prictureLimit"
				multiple
				
				:maxSize="20*1024*1024"
			></u-upload>
			
		</view>
		<u-icon name="photo"></u-icon>
		<view class="product-text">
			<view class="product-name">
				<label for="product-name">物品名称</label>
				<input id="product-name" v-model="form.name">
			</view>
			<textarea
			 class="product-detail-text uni-textarea" 
			 placeholder="详细描述你的宝贝吧~"
			 maxlength="100"
			 v-model="form.intro"
			 ></textarea>
		</view>
		
		<view class="product-message">
			<view class="publish-position" @click="getPosition">
				<text class="icon-weizhi iconfont"></text>
				<text style="margin-left: 0.5em;"> {{form.address}}</text>
			</view>
			<!-- </view> -->
			<!-- 分类 -->
			<view class="product-catalog" @click="chooseCatalog">
				<text>分类</text>
				<text >{{selected}}<text style="margin-left: 1em;" class="icon-xuanzeqizhankai iconfont"></text> </text>
				
				<!-- 类型选择框 -->
				<view class="catalog" v-if="showClassBox">
					<!-- 三角形 -->
					<view class="tran"></view>
					<view v-if="classList.length == 0">
						暂无数据。
					</view>
					<ul>
						<li v-for="(item,index) of classList" :key="index"   @click.stop="chooseClass(item.number,item.name)">
							{{item.name}}
						</li>	
						
					</ul>
				</view>
			</view>
			<!-- 价格 -->
			<view class="product-price">
				<text>价格</text>
				<view class="input-price" >
					<input 
					type="digit" 
					confirm-type="done" 
					auto-blur="true"
					v-model.number="form.price"
					
					/>
					<text>元</text>
				</view>
				
			</view>
			
			<view class="product-storage">
				<text>数量</text>
				<input type="number" v-model.number="form.storage">
			</view>
			
			
		</view>
		
		<view class="submit" @click="submit">发布</view>
		
	</view>
</template>

<script>
	import '../../static/iconfont.css'
	export default {
		data() {
			return {
				form:{
					name:'',
					phone:'',
					storage:'',
					intro:'',
					price:'',
					catalog:'',
					address:'点击获取位置'
				},
				
				fileList :[],
				classList:[],
				
				isChoosed:false,
				selected:'选择分类',
				
				showClassBox:false,
				prictureLimit: false,
				size:0,
				
				number:''
				
			}
		},
		
		mounted() {
			this.getClassList();
		},
		watch:{
			size(val,oVal){
				if(val < 20*1024*1024 ) this.prictureLimit = false;
				console.log('size：' + oVal + '-->' + val);
			}
		},
		methods: {
			async submit(){
				let {phone} = uni.getStorageSync('user')
				this.form.phone = phone;
				
				if(this.form.name == '' ||
				   this.form.phone == '' ||
				   this.form.storage == ''||
				   this.form.pictures == [] ||
				   this.form.intro == '' ||
				   this.form.price == '' ||
				   this.form.catalog == '' || this.isChoosed == false ||
				   this.form.address == '点击获取位置'
					
				){
					this.$toast('请填写完整信息！')
					// console.log(this.form)
				}
				
				else{
					// TODO
					this.submitBasicMsg();
				}
				
			},
			// 上传商品其他信息 
			async submitBasicMsg(){
				try{
					let res = await this.api.post('/product',{...this.form});
					let title = ''
					switch(res.status){
						case 201:
							this.number = res.number;
							// 上传图片
							this.submitPictures()
							break;
						case 403:
							this.$toast('用户无权限！')
							break
						case 422:
							this.$toast('请稍后重试！')
							break;
					}
					
				}catch(e){
					//TODO handle the exception
					this.$toast(e)
				}
			},
			
			async submitPictures(){
				let len = this.fileList.length;
				for(let i = 0;i < len;i++){
					try{
						let res = await this.uploadFilePromise(this.fileList[i].url,this.number,i);
						if(res == 201){
							this.fileList[i].status = 'success';
						}else if(res == 404) {
							this.$toast('图片保存失败！')
						}else {
							this.$toast('数据库更新失败!')
						}
					}catch(e){
						//TODO handle the exception
						this.$toast(e)
					}
				}
				this.$toast('商品发布成功，正在等待审核！')
			},
			
			uploadFilePromise(url,number,index) {
				let ip = this.api.ip + '/product/picture';
				return new Promise((resolve, reject) => {
					let a = uni.uploadFile({
						url: ip,
						filePath: url,
						name: 'picture',
						formData: {number},
						header:{
							'content-type':'application/json',
							'token':uni.getStorageSync('token')
						},
						success: (res) => {
							setTimeout(() => {
								console.log(res)
								resolve(res.data)
							}, 1000)
						}
					});
				})
			},

			
			// 删除图片
			deletePic(event) {
				this.size -= this[`fileList`][event.index].size;
				this[`fileList`].splice(event.index, 1)
			},
			// 新增图片
			async afterRead(event) {
				// 当设置 multiple 为 true 时, file 为数组格式，否则为对象格式
				let lists = [].concat(event.file)
				console.log(lists)
				let fileListLen = this[`fileList`].length
				
				lists.map((item) => {
					if(this.checkSize(item)){
						this[`fileList`].push({
						...item,
						status: '',
						message: '等待上传'
					})
					}else {
						this.size -= item.size;
					}
					
				})
					// for (let i = 0; i < lists.length; i++) {
				// 	// const result = await this.uploadFilePromise(lists[i].url)
					
				// 	let res = await this.uploadImage(lists[i]);
				// 	let item = this[`fileList`][fileListLen]
				// 		this[`fileList`].splice(fileListLen, 1, Object.assign(item, {
				// 			status: 'success',
				// 			message: '',
				// 			url: res
				// 		}))	
					
					
					
				// 	fileListLen++
				// }
			},
			checkSize(item){
				this.size += item.size;
				if(this.size > 20*1024*1024){
					this.prictureLimit = true;
					this.$toast('图片总大小超过20M');
					return false;
				}
				return true;
				
			},

			// ---------
	
			getPosition(){
				const that = this;
				uni.chooseLocation({
					latitude:29.59744,
					longitude:106.298386,
					success: function (res) {
						that.form.address = res.address +' ' +res.name;
						
					},
					fail:(res=>{
						console.log(res);
					})
				});
			},
			
			async getClassList(){
				try{
					this.classList = await this.api.get('/catalogs');
				}catch(e){
					//TODO handle the exception
					this.$toast(e)
				}
			},
			chooseCatalog(){
				this.showClassBox = !this.showClassBox;
			},
			chooseClass(number,name){
				this.selected = name;
				this.form.catalog = number;
				this.isChoosed = true;
				this.showClassBox = false;
			},
			
			
			
		}
	}
</script>

<style lang="less" scoped>
	.product-publish {
		min-height: 100vh;
		box-sizing: border-box;
		padding: 0 16rpx;
	}
	
	/* 图片 */
	.product-image {
		display: flex;
		justify-content: center;
		align-items: center;
		// height: 240rpx;
		background-color: #ebebeb;
		
		
	}
	// 文字信息
	.product-text {
		height: 400rpx;
		overflow: hidden;
		
		// 名称
		& .product-name {
			display: flex;
			line-height: 64rpx;
			font-size: 36rpx;
			margin: 10rpx;
			& label {
				width: 160rpx;
				height: 64rpx;
				
			}
			& input {
				flex: 1;
				border: 5rpx solid #ececec;
				height: 64rpx;
			}
		}
		& .product-detail-text {
			margin: 5rpx auto;
			// padding: 10rpx;
			width: 95%;
			font-size: 30rpx;
			// text-indent: 2em;
			line-height: 44rpx;
			border: 5rpx solid #ececec;
		}
	}
	
	.product-message {
		box-sizing: border-box;
		padding: 0 30rpx;
		font-size: 30rpx;
		line-height: 84rpx;
		
		& .publish-position {
			margin: 20rpx 0rpx;
			line-height: 30px;
			font-size: 30rpx;
			// background-color: #fff;
			border-bottom: 5rpx solid #7f7f7f;
			}
		
		& .product-catalog {
			position: relative;
			display: flex;
			margin: 20rpx 0rpx;
			justify-content: space-between;
			height: 84rpx;
			border-bottom: 5rpx solid #7f7f7f;
			
			& .catalog {
				position: absolute;
				background-color: #e1e1e1;
				width: 90%;
				// bottom: -220rpx;
				top: 84rpx;
				right: 0;
				border-radius: 10rpx;
				z-index: 10;
				
				& .tran {
					position: absolute;
					top: -36rpx;
					right: 50rpx;
					width: 0;
					height: 0;
					border-top: 20rpx solid transparent;
					border-right: 20rpx solid transparent;
					border-bottom: 20rpx solid #e1e1e1;
					border-left: 20rpx solid transparent;
					
				}
				
				& ul {
					
					display: flex;
					flex-wrap: wrap;
					justify-content: flex-start;
					min-height: 84rpx;
					
					& li {
						margin: 10rpx 15rpx;
					}
				}
			}
			
		}
		& .product-price {
			margin: 20rpx 0rpx;
			display: flex;
			justify-content: space-between;
			height: 84rpx;
			border-bottom: 5rpx solid #7f7f7f;
			
			& .input-price {
				display: flex;
				align-items: center;
				font-size:36rpx;
				line-height: 84rpx;
				
				& input {
					box-sizing: border-box;
					padding:0 16rpx;
					margin-right: 5rpx;
					height: 64rpx;
					display: inline-block;
					width: 250rpx;
					text-align: right;
					font-size:44rpx;
					border-radius: 10rpx;
					border: 5rpx solid #ececec;
					color: red;
					
					background-color: #ffffff;
				}
				
				
				
			}
			
			
		}
	}
	
	// 库存
	.product-storage {
		margin: 20rpx 0rpx;
		display: flex;
		justify-content: space-between;
		height: 84rpx;
		border-bottom: 5rpx solid #7f7f7f;
		& input {
			box-sizing: border-box;
			padding:0 16rpx;
			margin-right: 5rpx;
			height: 64rpx;
			display: inline-block;
			width: 250rpx;
			text-align: right;
			font-size:44rpx;
			border-radius: 10rpx;
			border: 5rpx solid #ececec;
			background-color: #ffffff;
		}
	}

	.submit {
		margin: 64rpx auto;
		background-color: #b34c26;
		color: #fff;
		width: 400rpx;
		height: 70rpx;
		text-align: center;
		font-size: 36rpx;
		line-height: 70rpx;
		border-radius: 20rpx;
	}
</style>
