<template>
	<view class="catalog">
		<view class="catalog-title">商品类别列表</view>
		<u-swipe-action v-if="classList.length !== 0">
			<u-swipe-action-item  v-for="(item,index) of classList" :key="index":options="options" :name="index" @click="operateCatalog">
			  <view class="swipe-action u-border-top u-border-bottom">
				  <!-- 内容 -->
				<view class="swipe-action__content">
				  <text class="swipe-action__content__text">{{item.number}}</text>
				  <text class="swipe-action__content__text">{{item.name}}</text>
				</view>
				
			  </view>
			</u-swipe-action-item>
		</u-swipe-action>
		<!-- 无数据 -->
		<u-empty v-else></u-empty>
		<!-- 新增 -->
		<view class="add-catalog">
			<u-icon class="add-catalog" name="plus" color="#2979ff" size="28" @click="addpop"></u-icon>
			<u-popup :show="show" mode="center"closeable="true" @close="close">
				<view class="title">
					<text v-if="isAdd">新增分类</text>
					<text v-else>修改分类</text>
				</view>
				<view class="add-box">
					<view class="id" v-if="!isAdd">
						<label for="id">id</label>
						<u--input v-model="updateId" disabled id="id"/>
					</view>
					
					<view class="number">
						<label for="number">编号</label>
						<u--input v-model="form.number" placeholder="如,E"  id="number"/>
					</view>
					
					<view class="name">
						<label for="name">名称</label>
						<u--input v-model="form.name" placeholder="如,电器"  id="name"/>
					</view>
					<view class="add-submit" @click="submit">
						<text>提交</text>
					</view>
				</view>
			</u-popup>
		</view>
	</view>
</template>

<script>
import { vShow } from "vue";
import {getCatalog, addCatalog,updateCatalog,removeCatalog} from '@/common/request/api.js'
	export default {
		data() {
			return {
				show:false,
				isAdd:true,
				updateId:'',
				classList:[],
				form:{
					number:'',
					name:''
				},
				options:[
					{
						text: '编辑',
						style: {
							backgroundColor: '#3c9cff'
						}
					},
					{
						text: '删除',
						style: {
							backgroundColor: '#f56c6c'
						}
					},
				]
			}
		},
		mounted() {
			this.getClassList();
		}
		,
		methods:{
			async removeCatalog(index){
				try{
					let res = await removeCatalog({id:this.classList[index].id})
					if(res == 204) {
						this.$toast('分类删除成功！')
						this.classList.splice(index,1)
					}
					else if(res == 400) this.$toast('删除失败！')
					
				}catch(e){
					//TODO handle the exception
					this.$toast(e)
				}
			},
			async submit(){
				if(this.form.name != '' && this.form.number != ''){
					try{
						if(this.isAdd){
							let res = await addCatalog({name:this.form.name,number:this.form.number})
							if(res == 201) this.$toast('添加成功！')
							else if(res == 422) this.$toast('添加失败：已存在分类！')
						}
						else{
							let res = await updateCatalog({id:this.updateId,name:this.form.name,number:this.form.number})
							if(res == 800) this.$toast('更新成功！')
							else if(res == 808) this.$toast('更新失败！')
						}
						
					}catch(e){
						//TODO handle the exception
						this.$toast(e)
					}
					
					this.getClassList();
					this.close();
				}
			},
			close() {
				this.updateId = '';
				this.form.name = '';
				this.form.number = '';
				this.show = false;
			},
			addpop(){
				this.isAdd = true;
				this.show = true;
			},
			operateCatalog(option){
				if(option.index == 0){
					this.isAdd = false;
					this.updateId = this.classList[option.name].id;
					this.form.name = this.classList[option.name].name;
					this.form.number = this.classList[option.name].number;
					this.show = true;
					
				}else if(option.index == 1){
					this.removeCatalog(option.name);
				}
			},
			async getClassList(){
				try{
					this.classList = await getCatalog();
					console.log(this.classList)
				}catch(e){
					//TODO handle the exception
					this.$toast(e)
				}
			},
		}
	}
</script>

<style lang="scss" scoped>
    .u-page {
        padding: 0;
    }

    .u-demo-block__title {
        padding: 10px 0 2px 15px;
    }

    .swipe-action {
        &__content {
             padding: 25rpx 0;
    
            &__text {
                 font-size: 16px;
                 color: $u-main-color;
                 padding-left: 30rpx;
             }
        }
	}
	.catalog {
		margin: 30px 30px 0;
	}
	.catalog-title {
		margin: 20rpx 0;
		font-size: 30rpx;
		color: #be0000;
		text-shadow: 5rpx 5rpx 2rpx #24c3be;
	}
	.title {
		width: 5em;
		margin: 20rpx auto;
	}
	
	.add-catalog {
		box-sizing: border-box;
		margin-top: 30rpx;
		padding-left: 20rpx	;
		
		& .add-box {
			width: 80vw;
			height: 500rpx;
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: space-around;
		}
		& .id, .number, .name {
			width: 80%;
			height: 50rpx;
			display: flex;
			
			& label {
				width: 3em;
			}
			& input {
				flex: 1;
			}
		}
		& .add-submit {
			width: 200rpx;
			height: 60rpx;
			background-color: #c35a50;
			border-radius: 15rpx;
			color: #fff;
			text-align: center;
			line-height: 60rpx;
		}
	}
    
	
	
</style>
