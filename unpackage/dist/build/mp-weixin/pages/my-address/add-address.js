(global["webpackJsonp"]=global["webpackJsonp"]||[]).push([["pages/my-address/add-address"],{"00d9":function(e,t,n){"use strict";var r=n("22de"),i=n.n(r);i.a},"22de":function(e,t,n){},"7b82":function(e,t,n){"use strict";n.r(t);var r=n("bbf3"),i=n("9b3d");for(var s in i)"default"!==s&&function(e){n.d(t,e,(function(){return i[e]}))}(s);n("00d9");var a,o=n("f0c5"),c=Object(o["a"])(i["default"],r["b"],r["c"],!1,null,null,null,!1,r["a"],a);t["default"]=c.exports},"9ac5":function(e,t,n){"use strict";(function(e){n("df27");r(n("66fd"));var t=r(n("7b82"));function r(e){return e&&e.__esModule?e:{default:e}}wx.__webpack_require_UNI_MP_PLUGIN__=n,e(t.default)}).call(this,n("543d")["createPage"])},"9b3d":function(e,t,n){"use strict";n.r(t);var r=n("feac"),i=n.n(r);for(var s in r)"default"!==s&&function(e){n.d(t,e,(function(){return r[e]}))}(s);t["default"]=i.a},bbf3:function(e,t,n){"use strict";var r;n.d(t,"b",(function(){return i})),n.d(t,"c",(function(){return s})),n.d(t,"a",(function(){return r}));var i=function(){var e=this,t=e.$createElement;e._self._c},s=[]},feac:function(e,t,n){"use strict";(function(e){Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;n("66fd");var r=n("26cb");function i(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function s(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?i(Object(n),!0).forEach((function(t){a(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):i(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function a(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}var o=function(){Promise.all([n.e("common/vendor"),n.e("components/uni/mpvue-citypicker/mpvueCityPicker")]).then(function(){return resolve(n("7713"))}.bind(null,n)).catch(n.oe)},c={data:function(){return{cityPickerValueDefault:[0,0,1],addressObj:{receiverName:"",receiverPhone:"",region:"请选择",addressDetail:"",rank:"",isDefault:!1},i:-1,isStatus:!1}},onLoad:function(t){if(t.data){e.setNavigationBarTitle({title:"修改地址"});var n=JSON.parse(t.data);this.addressObj=n.item,this.i=n.index,this.isStatus=!0}},components:{mpvueCityPicker:o},methods:s(s({},(0,r.mapActions)(["createAddressFn","updateAddressFn"])),{},{deleteAdd:function(){var t=this,n=this,r=e.getStorageSync("user").phone,i=this.addressObj.rank;n.api.del("/setting/address",{phone:r,rank:i}).then((function(n){t.$toast("删除成功"),e.navigateTo({url:"/pages/my-address/address"})})).catch((function(e){console.log(e)}))},showCityPicker:function(){this.$refs.mpvueCityPicker.show()},onConfirm:function(e){this.pickerText=JSON.stringify(e),this.addressObj.region=e.label},select:function(){this.addressObj.isDefault=!this.addressObj.isDefault},commit:function(){var t,n=this,r=e.getStorageSync("user").phone,i=this.addressObj;(this.$set(i,"phone",r),console.log(i),this.isStatus)?(n.api.put("/setting/address",i).then((function(e){console.log(e)})).catch((function(e){console.log(e)})),e.navigateTo({url:"/pages/my-address/address"})):(t=1==i.isDefault?1:0,n.api.post("/setting/address",{phone:r,isDefaultAddress:t,receiverName:i.receiverName,receiverPhone:i.receiverPhone,region:i.region,addressDetail:i.addressDetail}).then((function(e){console.log(e)})).catch((function(e){console.log(e)})),e.navigateTo({url:"/pages/my-address/address"}))}})};t.default=c}).call(this,n("543d")["default"])}},[["9ac5","common/runtime","common/vendor"]]]);