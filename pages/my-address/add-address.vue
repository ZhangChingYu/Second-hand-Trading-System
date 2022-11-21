<template>
	<view class="add-address">
		<view class="address-item">
			<view>收件人</view>
			<input type="text" value="" placeholder="收件人姓名" v-model="addressObj.name" />
		</view>
		<view class="address-item">
			<view>手机号</view>
			<input type="text" value="" placeholder="11位手机号" v-model="addressObj.tel" />
		</view>
		<view class="address-item">
			<view>所在地区</view>
			<view @tap="showCityPicker">{{addressObj.city}} ></view>
			<mpvue-city-picker ref="mpvueCityPicker" pickerValueDefault="cityPickerValueDefault" @onConfirm="onConfirm">
			</mpvue-city-picker>
		</view>
		<view class="address-item">
			<view>详细地址</view>
			<input type="text" value="" placeholder="5到60个字符" v-model="addressObj.detail" />
		</view>
		<view class="address-item">
			<view>设为默认地址</view>
			<radio-group @change="radioChange">
				<label class="radio">
					<radio color="#d04b41" :checked="addressObj.isDefault" /><text></text>
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
					name: "",
					tel: "",
					city: "请选择",
					detail: "",
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
			showCityPicker() {
				this.$refs.mpvueCityPicker.show();
			},
			onConfirm(e) {
				this.pickerText = JSON.stringify(e);
				console.log(e);
				this.addressObj.city = e.label
			},
			//提交后返回上一页
			commit() {
				if (this.isStatus) {
					//修改
					this.updateAddressFn({
						index:this.i,
						item:this.addressObj
					})
					uni.navigateBack({
						delta:1
					})
				} else {
					//新增
					this.createAddressFn(this.addressObj);
					uni.navigateBack({
						delta: 1
					})
				}
			},
			radioChange(){
				this.addressObj.isDefault = !this.addressObj.isDefault;
			}
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

	.address-item input {
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
</style>
