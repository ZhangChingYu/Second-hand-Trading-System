(global["webpackJsonp"]=global["webpackJsonp"]||[]).push([["pages/detail/index"],{293:function(e,t,n){"use strict";(function(e){n(5);r(n(4));var t=r(n(294));function r(e){return e&&e.__esModule?e:{default:e}}wx.__webpack_require_UNI_MP_PLUGIN__=n,e(t.default)}).call(this,n(1)["createPage"])},294:function(e,t,n){"use strict";n.r(t);var r=n(295),o=n(297);for(var u in o)"default"!==u&&function(e){n.d(t,e,(function(){return o[e]}))}(u);n(299);var a,s=n(11),c=Object(s["default"])(o["default"],r["render"],r["staticRenderFns"],!1,null,"12dbf08d",null,!1,r["components"],a);c.options.__file="pages/detail/index.vue",t["default"]=c.exports},295:function(e,t,n){"use strict";n.r(t);var r=n(296);n.d(t,"render",(function(){return r["render"]})),n.d(t,"staticRenderFns",(function(){return r["staticRenderFns"]})),n.d(t,"recyclableRender",(function(){return r["recyclableRender"]})),n.d(t,"components",(function(){return r["components"]}))},296:function(e,t,n){"use strict";var r;n.r(t),n.d(t,"render",(function(){return o})),n.d(t,"staticRenderFns",(function(){return a})),n.d(t,"recyclableRender",(function(){return u})),n.d(t,"components",(function(){return r}));try{r={uSkeleton:function(){return Promise.all([n.e("common/vendor"),n.e("uni_modules/uview-ui/components/u-skeleton/u-skeleton")]).then(n.bind(null,518))},uSwiper:function(){return Promise.all([n.e("common/vendor"),n.e("uni_modules/uview-ui/components/u-swiper/u-swiper")]).then(n.bind(null,405))},uniPopup:function(){return n.e("uni_modules/uni-popup/components/uni-popup/uni-popup").then(n.bind(null,526))},"u-Input":function(){return Promise.all([n.e("common/vendor"),n.e("uni_modules/uview-ui/components/u--input/u--input")]).then(n.bind(null,533))},uButton:function(){return Promise.all([n.e("common/vendor"),n.e("uni_modules/uview-ui/components/u-button/u-button")]).then(n.bind(null,539))},uEmpty:function(){return Promise.all([n.e("common/vendor"),n.e("uni_modules/uview-ui/components/u-empty/u-empty")]).then(n.bind(null,427))},uPopup:function(){return Promise.all([n.e("common/vendor"),n.e("uni_modules/uview-ui/components/u-popup/u-popup")]).then(n.bind(null,549))}}}catch(s){if(-1===s.message.indexOf("Cannot find module")||-1===s.message.indexOf(".vue"))throw s;console.error(s.message),console.error("1. 排查组件名称拼写是否正确"),console.error("2. 排查组件是否符合 easycom 规范，文档：https://uniapp.dcloud.net.cn/collocation/pages?id=easycom"),console.error("3. 若组件不符合 easycom 规范，需手动引入，并在 components 中注册该组件")}var o=function(){var e=this,t=e.$createElement,n=(e._self._c,""!==e.product&&e.product.price?e.product.price.toFixed(2):null);e.$mp.data=Object.assign({},{$root:{g0:n}})},u=!1,a=[];o._withStripped=!0},297:function(e,t,n){"use strict";n.r(t);var r=n(298),o=n.n(r);for(var u in r)"default"!==u&&function(e){n.d(t,e,(function(){return r[e]}))}(u);t["default"]=o.a},298:function(e,t,n){"use strict";(function(e){Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r=u(n(39)),o=n(155);function u(e){return e&&e.__esModule?e:{default:e}}function a(e,t,n,r,o,u,a){try{var s=e[u](a),c=s.value}catch(i){return void n(i)}s.done?t(c):Promise.resolve(c).then(r,o)}function s(e){return function(){var t=this,n=arguments;return new Promise((function(r,o){var u=e.apply(t,n);function s(e){a(u,r,o,s,c,"next",e)}function c(e){a(u,r,o,s,c,"throw",e)}s(void 0)}))}}var c=function(){n.e("components/evaluation/index").then(function(){return resolve(n(557))}.bind(null,n)).catch(n.oe)},i={mixins:[o.mixin],components:{Evaluation:c},data:function(){return{number:"",product:"",evaluationList:[],user:{},isLike:!1,priceType:"一口价",reportText:"举报",exit:"返回",evaluation:"",storage:4,show:!1,isBooked:!1,bookNumber:"",Bnumber:"",sellerId:"",showR:!1,replyContent:"",replyFatherId:"",reportReason:[{reason:"广告或垃圾信息"},{reason:"违法或政治敏感信息"},{reason:"色情类信息"},{reason:"欺诈类信息"},{reason:"不举报了"}],oneReport:""}},onLoad:function(e){this.number=e.number},mounted:function(){var t=this;this.user=e.getStorageSync("user"),this.getMessage(),this.getBooked(),this.getEvaluation(),this.$bus.$on("reply",(function(e){t.replyFatherId=e,t.showR=!0,console.log(t.replyFatherId)}))},methods:{replyEvaluation:function(){var e=this;return s(r.default.mark((function t(){var n,o;return r.default.wrap((function(t){while(1)switch(t.prev=t.next){case 0:if(t.prev=0,""==e.replyContent||""==e.replyFatherId){t.next=23;break}return e.close(),t.next=5,e.api.post("/product/reply",{number:e.number,phone:e.user.phone,father:e.replyFatherId,content:e.replyContent});case 5:n=t.sent,e.replyContent="",e.replyFatherId="",o="请稍后重试！",t.t0=n,t.next=201===t.t0?12:400===t.t0?16:422===t.t0?18:404===t.t0?20:22;break;case 12:return console.log(12345),o="回复成功！",e.getEvaluation(),t.abrupt("break",22);case 16:return o="回复失败：无权限！",t.abrupt("break",22);case 18:return o="回复失败：商品不存在！",t.abrupt("break",22);case 20:return o="回复失败：数据库更新失败！",t.abrupt("break",22);case 22:e.$toast(o);case 23:t.next=28;break;case 25:t.prev=25,t.t1=t["catch"](0),e.$toast(t.t1);case 28:case"end":return t.stop()}}),t,null,[[0,25]])})))()},sentEvaluation:function(){var e=this;return s(r.default.mark((function t(){var n,o;return r.default.wrap((function(t){while(1)switch(t.prev=t.next){case 0:if(console.log("发布的内容："+e.evaluation),t.prev=1,""!=e.evaluation){t.next=5;break}return e.$toast("请输入内容！"),t.abrupt("return");case 5:return t.next=7,e.api.post("/product/comment",{number:e.number,phone:e.user.phone,content:e.evaluation});case 7:n=t.sent,e.evaluation="",o="请稍后重试！",t.t0=n,t.next=201===t.t0?13:400===t.t0?16:422===t.t0?18:404===t.t0?20:22;break;case 13:return o="发布成功！",e.getEvaluation(),t.abrupt("break",22);case 16:return o="发布失败：无权限！",t.abrupt("break",22);case 18:return o="发布失败：商品不存在！",t.abrupt("break",22);case 20:return o="发布失败：数据库更新失败！",t.abrupt("break",22);case 22:e.$toast(o),t.next=28;break;case 25:t.prev=25,t.t1=t["catch"](1),e.$toast(t.t1);case 28:case"end":return t.stop()}}),t,null,[[1,25]])})))()},getEvaluation:function(){var e=this;return s(r.default.mark((function t(){var n;return r.default.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.prev=0,t.next=3,e.api.get("/product/comment",{number:e.number});case 3:n=t.sent,e.evaluationList=[],e.evaluationList=n,t.next=11;break;case 8:t.prev=8,t.t0=t["catch"](0),e.$toast(t.t0);case 11:case"end":return t.stop()}}),t,null,[[0,8]])})))()},close:function(){this.show=!1,this.showR=!1},back1:function(){e.navigateBack({delta:1})},getMessage:function(){var e=this;return s(r.default.mark((function t(){var n,o;return r.default.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return e,t.prev=1,t.next=4,e.api.get("/product/detail",{number:e.number});case 4:return n=t.sent,t.next=7,e.api.get("/like",{number:e.number,phone:e.user.phone});case 7:for(e.isLike=t.sent,n.seller_pic=e.imageSrcformat(n.seller_pic,"jpg"),o=0;o<n.pictures.length;o++)n.pictures[o]=e.imageSrcformat(n.pictures[o],n.pictureFormats[o]);e.product=n,t.next=16;break;case 13:t.prev=13,t.t0=t["catch"](1),e.$toast(t.t0);case 16:case"end":return t.stop()}}),t,null,[[1,13]])})))()},report:function(){this.$refs.popdown.open("bottom")},setReason:function(e){this.oneReport=this.reportReason[e].reason,this.$refs.popdown.close(),"不举报了"!=this.oneReport&&""!=this.oneReport&&this.postReport()},postReport:function(){var e=this;return s(r.default.mark((function t(){var n,o,u;return r.default.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return n=e,t.prev=1,t.next=4,e.api.post("/product/report",{number:e.number,phone:e.user.phone,content:e.oneReport});case 4:o=t.sent,u="",t.t0=o,t.next=201===t.t0?9:400===t.t0?11:422===t.t0?13:15;break;case 9:return u="举报发送成功！",t.abrupt("break",15);case 11:return u="举报失败！",t.abrupt("break",15);case 13:return u="发送失败！",t.abrupt("break",15);case 15:n.$toast(u),t.next=21;break;case 18:t.prev=18,t.t1=t["catch"](1),n.$toast(t.t1);case 21:case"end":return t.stop()}}),t,null,[[1,18]])})))()},getBooked:function(){var e=this;return s(r.default.mark((function t(){var n;return r.default.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.prev=0,t.next=3,e.api.get("/booking/buyer/judgment",{phone:e.user.phone,number:e.number});case 3:n=t.sent,e.isBooked=n.data,t.next=10;break;case 7:t.prev=7,t.t0=t["catch"](0),e.$toast(t.t0);case 10:case"end":return t.stop()}}),t,null,[[0,7]])})))()},like:function(){var e=this;return s(r.default.mark((function t(){var n,o,u;return r.default.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return n=e,e.isLike=!e.isLike,t.prev=2,t.next=5,e.api.post("/like",{number:e.number,phone:e.user.phone});case 5:o=t.sent,u="",t.t0=o,t.next=201===t.t0?10:204===t.t0?12:403===t.t0?14:404===t.t0?17:20;break;case 10:return u="收藏成功！",t.abrupt("break",20);case 12:return u="取消收藏成功！",t.abrupt("break",20);case 14:return u="用户无权限！",e.isLike=!e.isLike,t.abrupt("break",20);case 17:return u="收藏失败！",e.isLike=!e.isLike,t.abrupt("break",20);case 20:n.$toast(u),t.next=26;break;case 23:t.prev=23,t.t1=t["catch"](2),n.$toast(t.t1);case 26:case"end":return t.stop()}}),t,null,[[2,23]])})))()},contact:function(){var t=this;return s(r.default.mark((function n(){var o;return r.default.wrap((function(n){while(1)switch(n.prev=n.next){case 0:return n.prev=0,n.next=3,t.api.get("/booking/seller/info",{number:t.number});case 3:o=n.sent,"666"==o.code&&(t.sellerId=o.data.phone),e.navigateTo({url:"/pages/message/chat?phone=".concat(t.sellerId,"&userName=").concat(t.product.seller_name,"&avatar=").concat(t.product.seller_pic)}),n.next=11;break;case 8:n.prev=8,n.t0=n["catch"](0),t.$toast(n.t0);case 11:case"end":return n.stop()}}),n,null,[[0,8]])})))()},showBook:function(){this.show=!0},book:function(){var e=this;return s(r.default.mark((function t(){var n;return r.default.wrap((function(t){while(1)switch(t.prev=t.next){case 0:if(e.bookNumber&&!(e.bookNumber>e.product.storage)){t.next=5;break}e.bookNumber="",e.$toast("请输入合法的数量"),t.next=25;break;case 5:return e.close(),t.prev=6,t.next=9,e.api.get("/booking/seller/info",{number:e.number});case 9:if(n=t.sent,"666"==n.code&&(e.sellerId=n.data.phone),console.log(e.sellerId),""!=e.sellerId){t.next=15;break}return e.$toast("预约失败"),t.abrupt("return");case 15:return t.next=17,e.api.post("/booking/add",{sellerId:e.sellerId,buyerId:e.user.phone,productId:e.number,productName:e.product.name,ordersNum:e.bookNumber,price:e.product.price});case 17:t.sent,"666"==n.code&&"操作成功！"==n.msg&&(e.$toast("预约成功！"),e.isBooked=!0),t.next=25;break;case 21:t.prev=21,t.t0=t["catch"](6),e.$toast("预约失败"),e.isBooked=!1;case 25:case"end":return t.stop()}}),t,null,[[6,21]])})))()},cancelBook:function(){e.navigateTo({url:"/pages/myAppointment/index"})}}};t.default=i}).call(this,n(1)["default"])},299:function(e,t,n){"use strict";n.r(t);var r=n(300),o=n.n(r);for(var u in r)"default"!==u&&function(e){n.d(t,e,(function(){return r[e]}))}(u);t["default"]=o.a},300:function(e,t,n){}},[[293,"common/runtime","common/vendor"]]]);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/detail/index.js.map