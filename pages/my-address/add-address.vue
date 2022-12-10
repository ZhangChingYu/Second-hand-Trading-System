<template>
	<view class="add-address">
		<button class="deleteBtn" @tap="deleteAdd">删除该地址</button>
		<view class="address-item">
			<view class="title">收件人</view>
			<input type="text" value="" placeholder="收件人姓名" v-model="addressObj.receiverName" />
		</view>
		<view class="address-item">
			<view class="title">手机号</view>
			<input type="text" value="" placeholder="11位手机号" v-model="addressObj.receiverPhone" />
		</view>
		<view class="address-item">
			<view class="title">所在地区</view>
			<view @tap="showCityPicker">{{addressObj.region}} ></view>
			<mpvue-city-picker ref="mpvueCityPicker" pickerValueDefault="cityPickerValueDefault" @onConfirm="onConfirm">
			</mpvue-city-picker>
		</view>
		<view class="address-item">
			<view class="title">详细地址</view>
			<input type="text" value="" placeholder="5到60个字符" v-model="addressObj.addressDetail" />
		</view>
		<view class="address-item">
			<view class="title">设为默认地址</view>
			<radio-group>
				<label class="radio">
					<radio color="#d04b41" :checked="addressObj.isDefault"  @tap="select()"/><text></text>
				</label>
			</radio-group>
		</view>

		<button class="commitbtn" @tap="commit">保存</button>

	</view>
</template>

<script>
	import {
		ref
	} from "vue"
	import {
		mapActions
	} from 'vuex'
	import mpvueCityPicker from '../../components/uni/mpvue-citypicker/mpvueCityPicker.vue'
	export default {
		data() {
			return {
				cityPickerValueDefault: [0, 0, 1],
				addressObj: {
					receiverName: "",
					receiverPhone: "",
					region: "请选择",
					addressDetail: "",
					rank:'',
					isDefault: false
				},
				i: -1,
				//是否修改地址
				isStatus: false
			}
		},
		onLoad(e) {
			if (e.data) {
				uni.setNavigationBarTitle({
					title: "修改地址"
				})
				let result = JSON.parse(e.data);
				this.addressObj = result.item;
				this.i = result.index;
				this.isStatus = true
			}
		},
		components: {
			mpvueCityPicker
		},
		methods: {
			...mapActions(["createAddressFn","updateAddressFn"]),
			
			deleteAdd(){
				let that=this;
				let phone=uni.getStorageSync('user').phone;
				let rank=this.addressObj.rank;
				that.api.del('/setting/address',{phone,rank}).then(res=>{
					this.$toast('删除成功');
					uni.navigateTo({
						url:'/pages/my-address/address'
					})
				}).catch(err=>{
					console.log(err);
				})
			},
			showCityPicker() {
				this.$refs.mpvueCityPicker.show();
			},
			onConfirm(e) {
				this.pickerText = JSON.stringify(e);
				this.addressObj.region = e.label
			},
			select(){
				this.addressObj.isDefault=!this.addressObj.isDefault;
			},
			//提交后返回上一页
			commit() {
				let that=this;
				let phone=uni.getStorageSync('user').phone;
				let addressObj=this.addressObj;
				this.$set(addressObj,'phone',phone);
				console.log(addressObj)
				if (this.isStatus) {
					//修改
					that.api.put('/setting/address',addressObj).then(res=>{
						console.log(res);
					}).catch(err=>{
						console.log(err);
					})
					uni.navigateTo({
						url:'/pages/my-address/address'
					})
				} else {
					//新增
					let isDefault;
					if(addressObj.isDefault==true){
						isDefault = 1;
					}else{
						isDefault = 0;
					}
					
					that.api.post('/setting/address',{"phone":phone,
					"isDefaultAddress":isDefault,
					"receiverName":addressObj.receiverName,
					"receiverPhone":addressObj.receiverPhone,
					"region":addressObj.region,
					"addressDetail":addressObj.addressDetail}).then(res=>{
						console.log(res);
					}).catch(err=>{
						console.log(err);
					})
					uni.navigateTo({
						url:'/pages/my-address/address'
					})
				}
			},
		}
	}
</script>

<style>
	.add-address {
		padding-left: 30rpx;
		padding-right: 30rpx;
		font-size: medium;
	}
	.address-item {
		display: flex;
		justify-content: space-between; //左右排开
		align-items: center; //垂直居中
		padding: 25rpx 0;
		width: 100%;
		border-bottom: 2rpx solid #cccccc;
	}
	
	.title{
		width: 250rpx;
	}
	.address-item input {
		text-align: right;
		flex: 1;
		text-align: left;
		padding-left: 10rpx;
	}
	.commitbtn {
		color: white;
		background-color: #d04b41;
		width: 30%;
		justify-content: center;
		margin-top: 30%;
	}
	.deleteBtn{
		background-color: white;
		color: gray;
		font-size: small;
		width: 30%;
		height: 50rpx;
		margin-left: 80%;
		margin-top: 10rpx;
	}
</style>