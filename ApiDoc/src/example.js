/**
 * @api {post} /user 用戶註冊
 * @apiName Register
 * @apiGroup 用戶註冊
 * @apiPermission none
 * 
 * @apiDescription 由用戶輸入手機號、郵箱及密碼來完成註冊，且一個手機號只能註冊一次，若手機號被重複註冊則會判定註冊失敗。 手機號須為11位手機號，其他型號本系統不予支持。所有前後端交互數據皆由UTF-8進行編碼與解碼。 為避免中英文混和而產生亂碼，數據庫除日期、純數字、純英文數據外，接先進行UTF-8編碼後存入數據庫，讀取時再進行相應的解碼顯示。
 * 
 * @apiParam {String} phone 用戶手機號(手機號不可重複註冊)
 * @apiParam {String} password 用戶想使用的密碼
 * @apiParam {String} [email] 用戶郵箱帳號
 *
 * @apiSuccessExample 註冊成功釋例
 *     {
 *       201
 *     }
 * @apiErrorExample 註冊失敗(手機號已存在)
 *     {
 *       422
 *     }
 * @apiErrorExample 註冊失敗(信息未成功添加)
 *     {
 *       404
 *     }
 */

/** 
 * @api {get} /catalogs 顯示所有分類
 * @apiName AllCatalog
 * @apiGroup 服務端:商品分類管理
 * @apiPermission none
 * 
 * @apiDescription 顯示所有分類
 *
 * @apiSuccessExample 請求成功釋例
 *     [
 *          {"id":1,"name":"書籍","number":"B"},
 *          {"id":2,"name":"美妝","number":"M"},
 *          {"id":3,"name":"數碼","number":"D"},
 *          {"id":4,"name":"家居","number":"F"},
 *          {"id":5,"name":"電器","number":"E"},
 *          {"id":6,"name":"服裝","number":"C"},
 *          {"id":7,"name":"其它","number":"O"}
 *     ]
 */

/**  
 * @api {post} /catalog 添加分類
 * @apiName AddCatalog
 * @apiGroup 服務端:商品分類管理
 * @apiPermission none
 * 
 * @apiDescription 添加分類
 * 
 * @apiParam {String} number 分類編碼
 * @apiParam {String} name 分類名稱
 *
 * @apiSuccessExample 添加成功釋例
 *     {
 *       201
 *     }
 * @apiErrorExample 添加失敗(分類編碼已存在)
 *     {
 *       422
 *     }
 * @apiErrorExample 添加失敗(信息未成功添加)
 *     {
 *       404
 *     }
 */

/** 
 * @api {put} /catalog 更新分類信息
 * @apiName UpdateCatalog
 * @apiGroup 服務端:商品分類管理
 * @apiPermission none
 * 
 * @apiDescription 更新分類信息，這裡只許更動分類名稱，也就是name的部分，以面系統無法識別某些商品的分類而報錯。
 * 
 * @apiParam {Integer} id 分類序號
 * @apiParam {String} number 新的分類編碼
 * @apiParam {String} name 新的分類名稱
 *
 * @apiSuccessExample 更新成功
 *     {
 *       800
 *     }
 * @apiErrorExample 更新失敗
 *     {
 *       808
 *     }
 */

/**  
 * @api {delete} /catalog 刪除分類
 * @apiName DeleteCatalog
 * @apiGroup 服務端:商品分類管理
 * @apiPermission none
 * 
 * @apiDescription 刪除分類，不建議直接刪除分類，因為很多商品都與分類掛勾，此功能只適用不小心添加了一個不想要的分類，提供刪除的手段。
 * 
 * @apiParam {Integer} id 分類序號
 *
 * @apiSuccessExample 分類刪除成功
 *     {
 *       204
 *     }
 * @apiErrorExample 刪除失敗(沒有選中的對象，數據庫為發生改動)
 *     {
 *       400
 *     }
 */

/**  
 * @api {get} /homepage 主頁面商品顯示
 * @apiName Homepage
 * @apiGroup 商品查詢
 * @apiPermission none
 * 
 * @apiDescription 主頁面商品顯示，返回商品信息的List
 *
 * @apiSuccessExample 請求成功
 *     [
 *        {
 *          "number":"B1667559776586",
 *          "coverPicFormat":"jpg",
 *          "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)
 *          "name":學生心理健康教育第二版",
 *          "price":7.9
 *        },
 *          ....,{}
 *     ]
 */

/**  
 * @api {get} /catalog/products 分類檢索商品
 * @apiName CatalogProduct
 * @apiGroup 商品查詢
 * @apiPermission none
 * 
 * @apiDescription 分類檢索商品
 * 
 * @apiParam {String} catalog 商品分類編碼(B,M,E...)
 *
 * @apiSuccessExample 請求成功
 *     [
 *        {
 *          "number":"B1667559776586",
 *          "coverPicFormat":"jpg",
 *          "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)
 *          "name":學生心理健康教育第二版",
 *          "price":7.9
 *        },
 *          ....,{}
 *     ]
 */

/**  
 * @api {get} /search/products 搜索欄檢索
 * @apiName SearchProduct
 * @apiGroup 商品查詢
 * @apiPermission none
 * 
 * @apiDescription 搜索欄檢索
 * 
 * @apiParam {String} keyword 搜索欄輸入內容
 *
 * @apiSuccessExample 請求成功
 *     [
 *        {
 *          "number":"B1667559776586",
 *          "coverPicFormat":"jpg",
 *          "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)
 *          "name":學生心理健康教育第二版",
 *          "price":7.9
 *        },
 *          ....,{}
 *     ]
 */

/**
 * @api {get} /hotkeys 獲取熱門搜索詞
 * @apiName GetHotKeys
 * @apiGroup 商品查詢
 * @apiPermission none
 *
 * @apiDescription 獲取熱門搜索詞功能會從關鍵字數據庫中篩選出點擊頻率最高的3~5個關鍵詞進行顯示。
 *
 * @apiSuccessExample 請求成功
 *     [
 *        {
 *          "id":1,
 *          "content":"二手",
 *        },
 *          ....,{}
 *     ]
 */

/**
 * @api {get} /hotkey/products 熱門搜索詞檢索
 * @apiName HotKeySearchProducts
 * @apiGroup 商品查詢
 * @apiPermission none
 *
 * @apiDescription 熱門搜索詞檢索，通過點擊熱門關鍵詞檢索相應的商品，每次點擊都會增加該關鍵詞的點擊數。
 *
 * @apiParam {Integer} id 熱門關鍵詞id
 *
 * @apiSuccessExample 請求成功
 *     [
 *        {
 *          "number":"B1667559776586",
 *          "coverPicFormat":"jpg",
 *          "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)
 *          "name":學生心理健康教育第二版",
 *          "price":7.9
 *        },
 *          ....,{}
 *     ]
 */

/**
 * @api {get} /homepage/new/products 最新商品(top 10)
 * @apiName TopTenNewestProducts
 * @apiGroup 商品查詢
 * @apiPermission none
 *
 * @apiDescription 最新商品(top 10)，返回最新上架的十個商品
 *
 * @apiSuccessExample 請求成功
 *     [
 *        {
 *          "number":"B1667559776586",
 *          "coverPicFormat":"jpg",
 *          "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)
 *          "name":學生心理健康教育第二版",
 *          "price":7.9
 *        },
 *          ....,{}
 *     ]
 */

/**
 * @api {get} /homepage/promote/products 根據用戶數據推薦的商品(top 10)
 * @apiName TopTenCustomizedProducts
 * @apiGroup 商品查詢
 * @apiPermission none
 *
 * @apiDescription 根據用戶數據推薦的商品(top 10)，沒有真的實現，只是隨便返回10個
 *
 * @apiSuccessExample 請求成功
 *     [
 *        {
 *          "number":"B1667559776586",
 *          "coverPicFormat":"jpg",
 *          "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)
 *          "name":學生心理健康教育第二版",
 *          "price":7.9
 *        },
 *          ....,{}
 *     ]
 */

/**  
 * @api {get} /product/detail 商品詳細信息顯示
 * @apiName ProductDetail
 * @apiGroup 商品查詢
 * @apiPermission none
 * 
 * @apiDescription 商品詳細信息顯示，picture_count返回該商品的圖片數，並由pictures以List的形式傳給前端
 * 
 * @apiParam {String} number 商品編碼
 *
 * @apiSuccessExample 請求成功
 *     {
 *        "name":"大學生心理健康教育第二版",
 *        "seller_name":"天天",
 *        "seller_pic":"iVBORw0KGgoAAAANSUhEUgAAAf8AAAH/CAYAAABZ8dS+AAAABGdBTUEAALGPC/...",
 *        "address":"重慶大學虎溪校區竹園3棟",
 *        "date":"2022-11-04 19:02:56",
 *        "price":7.9,
 *        "intro":"全新沒有用過，到貨前就退課了",
 *        "like_count":0,
 *        "picture_count":1,
 *        "storage":1,
 *        "pictureFormats":["jpg"]
 *        "pictures":["/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAA.....(base64編碼，原文過長不全部展示)",...,"..."]
 *     }
 */

/**  
 * @api {get} /like 確認該商品是否已收藏
 * @apiName CheckLike
 * @apiGroup 商品收藏
 * @apiPermission none
 * 
 * @apiDescription 檢查商品是否已收藏的功能可用於前端界面顯示愛心顏色的判斷標準。
 * 
 * @apiParam {String} phone 用戶手機號
 * @apiParam {String} number 商品編碼
 *
 * @apiSuccessExample 商品已收藏
 *     {
 *        ture
 *     }
 * @apiSuccessExample 商品未收藏
 *     {
 *        false
 *     }
 */

/**  
 * @api {post} /like 添加/取消收藏
 * @apiName PressLike
 * @apiGroup 商品收藏
 * @apiPermission none
 * 
 * @apiDescription 添加/取消收藏是當用戶點下收藏鍵時，通過商品收藏狀態自動判斷該進行收藏/取消收藏的動作。
 * 
 * @apiParam {String} phone 用戶手機號
 * @apiParam {String} number 商品編碼
 *
 * @apiSuccessExample 收藏成功
 *     {
 *        201
 *     }
 * @apiSuccessExample 取消收藏成功
 *     {
 *        204
 *     }
 * @apiErrorExample 用戶無權限
 *     {
 *        403
 *     }
 * @apiErrorExample 收藏失敗
 *     {
 *        404
 *     }
 */

/**  
 * @api {get} /all/likes 顯示所有收藏商品
 * @apiName AllLikes
 * @apiGroup 商品收藏
 * @apiPermission none
 * 
 * @apiDescription 顯示所有收藏商品，默認排序:收藏日期新到舊
 * 
 * @apiParam {String} phone 用戶手機號
 *
 * @apiSuccessExample 請求成功
 *     [
 *        {
 *          "number":"B1667559776586",
 *          "coverPicFormat":"jpg",
 *          "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)
 *          "name":學生心理健康教育第二版",
 *          "price":7.9
 *        },
 *          ....,{}
 *     ]
 */

/**  
 * @api {get} /all/likes/order 切換不同的排序方式
 * @apiName OrderLikes
 * @apiGroup 商品收藏
 * @apiPermission none
 * 
 * @apiDescription 切換不同的排序方式，type的值:0(添加日期:新-->舊), 1(添加日期:舊-->新), 2(商品價格:低-->高), 3(商品價格:高-->低)
 * 
 * @apiParam {String} phone 用戶手機號
 * @apiParam {Integer} type 排序方式編號
 *
 * @apiSuccessExample 請求成功
 *     [
 *        {
 *          "number":"B1667559776586",
 *          "coverPicFormat":"jpg",
 *          "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)
 *          "name":學生心理健康教育第二版",
 *          "price":7.9
 *        },
 *          ....,{}
 *     ]
 */

/**  
 * @api {get} /catalog/likes 依照商品分類顯示收藏商品
 * @apiName CatalogLikes
 * @apiGroup 商品收藏
 * @apiPermission none
 * 
 * @apiDescription 依照商品分類顯示收藏商品
 * 
 * @apiParam {String} phone 用戶手機號
 * @apiParam {String} catalog 分類編碼(B,M,...)
 *
 * @apiSuccessExample 請求成功
 *     [
 *        {
 *          "number":"B1667559776586",
 *          "coverPicFormat":"jpg",
 *          "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)
 *          "name":學生心理健康教育第二版",
 *          "price":7.9
 *        },
 *          ....,{}
 *     ]
 */

/**  
 * @api {delete} /likes 批量取消收藏
 * @apiName DeleteLikes
 * @apiGroup 商品收藏
 * @apiPermission none
 * 
 * @apiDescription 批量取消收藏
 * 
 * @apiParam {String} phone 用戶手機號
 * @apiParam {String} numbers ["商品編碼1", "商品編碼2",....]
 *
 * @apiSuccessExample 取消收藏成功
 *     {
 *        204
 *     }
 * @apiErrorExample 取消收藏失敗
 *     {
 *        404
 *     }
 */

/**  
 * @api {GET} /default/address 獲取默認地址
 * @apiName GetDefaultAddress
 * @apiGroup 地址管理
 * @apiPermission none
 * 
 * @apiDescription 獲取用戶的默認地址
 * 
 * @apiParam {String} phone 用戶手機號
 *
 * @apiSuccessExample 成功返回地址信息(例子)
 *     {
 *        "receiverName":"喵薄荷",
 *        "receiverPhone":"15078818663",
 *        "region":"重慶",
 *        "addressDetail":"重慶大學虎溪校區松園5棟102室",
 *        "rank":0
 *     }
 * @apiErrorExample 默認地址不存在
 *     {
 *        null
 *     }
 */

/**  
 * @api {GET} /setting/addresses 顯示用戶所有地址
 * @apiName ShowAllAddress
 * @apiGroup 地址管理
 * @apiPermission none
 * 
 * @apiDescription 顯示用戶所有地址，返回信息rank是更新與刪除地址所需要的參數
 * 
 * @apiParam {String} phone 用戶手機號
 *
 * @apiSuccessExample 請求成功(例子)
 *     [
 *        {
 *             "receiverName":"喵薄荷",
 *             "receiverPhone":"15078818663",
 *             "region":"重慶",
 *             "addressDetail":"重慶大學虎溪校區松園5棟102室",
 *             "rank":0
 *        },
 *        {
 *             "receiverName":"大頭兒子",
 *             "receiverPhone":"15078818663",
 *             "region":"重慶",
 *             "addressDetail":"重慶大學虎溪校區松園5棟102室",
 *             "rank":1
 *        },
 *        {
 *             "receiverName":"神奇寶可夢",
 *             "receiverPhone":"15078818663",
 *             "region":"重慶",
 *             "addressDetail":"重慶大學虎溪校區松園5棟102室",
 *             "rank":2
 *         }
 *     ]
 * @apiErrorExample 若沒有任何地址存在
 *     {
 *        null
 *     }
 */

/**  
 * @api {POST} /setting/address 添加地址
 * @apiName AddAddress
 * @apiGroup 地址管理
 * @apiPermission none
 * 
 * @apiDescription 添加地址
 * 
 * @apiParam {String} phone 當前用戶手機號
 * @apiParam {Integer} isDefaultAddress 是否需要設置為默認地址(需要:1,不需要:0)
 * @apiParam {String} name 收件者的稱呼
 * @apiParam {String} receiverPhone 收件者的手機號
 * @apiParam {String} region 地區
 * @apiParam {String} addressDetail 詳細地址
 *
 * @apiSuccessExample 添加成功(因為算是數據庫更新，所以返回800)
 *     {
 *        800
 *     }
 * @apiErrorExample 添加失敗
 *     {
 *        400
 *     }
 */

/**  
 * @api {PUT} /setting/address 編輯地址
 * @apiName UpdateAddress
 * @apiGroup 地址管理
 * @apiPermission none
 * 
 * @apiDescription 編輯地址
 * 
 * @apiParam {String} phone 用戶手機號
 * @apiParam {Integer} rank 地址序列號 
 * @apiParam {String} name 收件者的稱呼
 * @apiParam {String} receiverPhone 收件者的手機號
 * @apiParam {String} region 地區
 * @apiParam {String} addressDetail 詳細地址
 *
 * @apiSuccessExample 更新成功
 *     {
 *        800
 *     }
 * @apiErrorExample 更新失敗(沒有更新)
 *     {
 *        400
 *     }
 * @apiErrorExample 請求數據不存在(默認地址)
 *     {
 *        4001
 * @apiErrorExample 請求數據不存在(其他地址)
 *     {
 *        4002
 *     }
 */

/**  
 * @api {PUT} /setting/default/address 將某地址設為默認地址
 * @apiName SetAsDefaultAddress
 * @apiGroup 地址管理
 * @apiPermission none
 * 
 * @apiDescription 將某地址設為默認地址
 * 
 * @apiParam {String} phone 用戶手機號
 * @apiParam {Integer} rank 要設為默認地址的序列號
 *
 * @apiSuccessExample 更改地址成功
 *     {
 *        800
 *     }
 * @apiSuccessExample 更改成功(rank=0)
 *     {
 *        200
 *     }
 * @apiErrorExample 更改失敗(沒有更新數據or地址不存在)
 *     {
 *        400
 *     }
 */

/**  
 * @api {DELETE} /setting/address 刪除某地址
 * @apiName DeleteAddress
 * @apiGroup 地址管理
 * @apiPermission none
 * 
 * @apiDescription 刪除某地址
 * 
 * @apiParam {String} phone 用戶手機號
 * @apiParam {Integer} rank 要刪除的地址序列號
 *
 * @apiSuccessExample 刪除地址成功
 *     {
 *        800
 *     }
 * @apiErrorExample 刪除失敗(沒有更新的數據or要刪除的地址不存在)
 *     {
 *        400
 *     }
 */

/**
 * @api {POST} /product 上傳商品
 * @apiName UploadProduct
 * @apiGroup 商品上傳
 * @apiPermission none
 *
 * @apiDescription 用戶上傳商品功能，用戶須通過實名認證方可使用此功能。本系統上傳商品的流程是: 用戶上傳商品-->商品進入審核階段-->審核成功/失敗-->商品上架/不予上架。用戶可以在"我的商品"中查看此流程進度。
 *
 * @apiParam {String} name 商品名稱
 * @apiParam {String} phone 用戶(賣家)手機號
 * @apiParam {Integer} storage 庫存量
 * @apiParam {String} intro 商品描述
 * @apiParam {Double} price 商品價格
 * @apiParam {String} catalog 商品分類(編碼:B,M,C....)
 * @apiParam {String} address 發貨地
 *
 * @apiSuccessExample 商品上傳成功
 *     {
 *        "status":201,
 *        "number":"C1670300792286(上傳圖片用)"
 *     }
 * @apiErrorExample 用戶無權限
 *     {
 *       "status":403,
 *        "number":null
 *     }
 * @apiErrorExample 商品編號重複(稍後再嘗試即可)
 *     {
 *       "status":422,
 *        "number":null
 *     }
 */

/**
 * @api {POST} /product/picture 上傳商品圖片
 * @apiName UploadProductPicture
 * @apiGroup 商品上傳
 * @apiPermission none
 *
 * @apiDescription 用戶上傳商品描述圖片，一次傳一張。當商品信息被成功天加入數據庫中方可使用此功能。
 *
 * @apiParam {String} number 商品編號(商品信息成功上傳後返回)
 * @apiParam {MultipartFile} picture 商品圖片
 *
 * @apiSuccessExample 商品圖片上傳成功
 *     {
 *        201
 *     }
 * @apiErrorExample 圖片保存失敗
 *     {
 *        404
 *     }
 * @apiErrorExample 數據庫更新失敗
 *     {
 *         422
 *     }
 */

/**
 * @api {POST} /setting/feedback 用戶反饋
 * @apiName WriteFeedback
 * @apiGroup 設置:用戶反饋
 * @apiPermission none
 *
 * @apiDescription 用戶反饋功能
 *
 * @apiParam {String} phone 用戶手機號
 * @apiParam {String} content 反饋內容
 *
 * @apiSuccessExample 發送成功
 *     {
 *        200
 *     }
 * @apiErrorExample 錯誤用戶
 *     {
 *        400
 *     }
 * @apiErrorExample 找不到檔案/讀寫錯誤/路徑創建失敗
 *     {
 *        422
 *     }
 */

/**
 * @api {GET} /setting/help 獲取幫助問題的類型
 * @apiName GetQuestionCatalogs
 * @apiGroup 設置:幫助
 * @apiPermission none
 *
 * @apiDescription 獲取幫助問題的類型，用戶可通過問題類型鎖定問題位置。
 *
 * @apiSuccessExample 成功返回問題類型(例子)
 *    [
 *        {
 *             "index":"0",
 *             "catalog":"商品上傳"
 *        },
 *        {
 *             "index":"1",
 *             "catalog":"關於"
 *        }
 *     ]
 * @apiErrorExample 失敗
 *     {
 *        null
 *     }
 */

/**
 * @api {GET} /setting/help/catalog 獲取相應問題類型的問題
 * @apiName GetHelpQuestions
 * @apiGroup 設置:幫助
 * @apiPermission none
 *
 * @apiDescription 用戶鎖定問題位置後，進一步找到問題。
 *
 * @apiSuccessExample 成功返回問相應類型的問題(以"商品上傳"為例)
 *    [
 *        {
 *             "index":"0",
 *             "catalog":"如何上傳商品"
 *        },
 *        {
 *             "index":"1",
 *             "catalog":"為何上傳商品後沒有成功上架"
 *        }
 *     ]
 * @apiErrorExample 失敗
 *     {
 *        null
 *     }
 */

/**
 * @api {GET} /setting/help/catalog/question 獲取對應問題解答
 * @apiName GetHelpAnswer
 * @apiGroup 設置:幫助
 * @apiPermission none
 *
 * @apiDescription 用戶找到問題後，獲得問題的解答。
 *
 * @apiSuccessExample 成功(以"為何上傳商品後沒有成功上架"為例)
 *    {
 *         閒置重重的商品上架流程為: 用戶上傳商品-->管理員審核通過-->用戶商品上架。
 *         可以在個人中心查看商品的審核進度，若顯示[審核中]請用戶耐心等待管理員審核結果，一般不會超過24h。
 *         若顯示[已駁回]表示管理員判斷該商品有違規嫌疑，具體細節由系統發送通知說明。
 *     }
 * @apiErrorExample 失敗
 *     {
 *        null
 *     }
 */

/**
 * @api {GET} /my/products 顯示用戶所有商品
 * @apiName ShowAllMyProducts
 * @apiGroup 我的商品
 * @apiPermission none
 *
 * @apiDescription 顯示用戶所有上傳過的商品
 *
 * @apiParam {String} phone 用戶手機號
 *
 * @apiSuccessExample 請求成功(手機號為1635905050的例子)
 *     [
 *        {
 *              "number":"B1667557259691",
 *              "status":0,
 *              "name":"考研真相英語一",
 *              "price":12.0,
 *              "coverPicFormat":"jpg",
 *              "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgAAg....(base64編碼，原文過長不全部展示)
 *        },
 *        {
 *             "number":"B1667557541428"
 *             "status":0,
 *             "name":"二手書",
 *             "price":8.0,
 *             "coverPicFormat":"jpg",
 *             "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXh.......
 *        },
 *        {
 *             "number":"B1667557719337"
 *             "status":0,
 *             "name":"思想道德與法治2021年版",
 *             "price":7.9,
 *             "coverPicFormat":"jpg",
 *             "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYR......
 *         },
 *         {
 *             "number":"B1667557940336"
 *             "status":0,
 *             "name":"中公小學教師資格證",
 *             "price":68.0,
 *             "coverPicFormat":"jpg",
 *             "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4Q......
 *         },
 *         {
 *             "number":"B1667558776457"
 *             "status":0,
 *             "name":"二手自考教材04729大學語文2018版徐中玉陶型傳北京大學出版社",
 *             "price":14.66,
 *             "coverPicFormat":"jpg",
 *             "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBMRXhpZgAATU0AKgAAAAgAAg......
 *         },
 *         {
 *             "number":"B1667559352213",
 *             "status":0,
 *             "name":"國家教師資格考試",
 *             "price":15.0,
 *             "coverPicFormat":"jpg",
 *             "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0.......
 *         }
 *     ]
 * @apiErrorExample 沒有任何上傳的商品
 *     {
 *        null
 *     }
 */

/**
 * @api {GET} /my/products/key 根據關鍵字模糊查詢用戶商品
 * @apiName ShowMyProductsByKey
 * @apiGroup 我的商品
 * @apiPermission none
 *
 * @apiDescription 根據輸入的關鍵字顯示用戶所有上傳過的商品
 *
 * @apiParam {String} phone 用戶手機號
 * @apiParam {String} keyword 關鍵字
 *
 * @apiSuccessExample 請求成功(例子"考")
 *     [
 *        {
 *              "status":0,
 *              "name":"考研真相英語一",
 *              "price":12.0,
 *              "coverPicFormat":"jpg",
 *              "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgAAg....(base64編碼，原文過長不全部展示)
 *        },
 *         {
 *             "status":0,
 *             "name":"二手自考教材04729大學語文2018版徐中玉陶型傳北京大學出版社",
 *             "price":14.66,
 *             "coverPicFormat":"jpg",
 *             "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBMRXhpZgAATU0AKgAAAAgAAg......
 *         },
 *         {
 *             "status":0,
 *             "name":"國家教師資格考試",
 *             "price":15.0,
 *             "coverPicFormat":"jpg",
 *             "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0.......
 *         }
 *     ]
 * @apiErrorExample 沒有符合的商品
 *     {
 *        null
 *     }
 */

/**
 * @api {GET} /my/products/catalog 根據分類顯示用戶商品
 * @apiName ShowMyProductsByCatalog
 * @apiGroup 我的商品
 * @apiPermission none
 *
 * @apiDescription 根據分類顯示用戶上傳過的商品
 *
 * @apiParam {String} phone 用戶手機號
 * @apiParam {String} catalog 商品分類(B,M,C...)
 *
 * @apiSuccessExample 請求成功("B"的例子)
 *     [
 *        {
 *              "status":0,
 *              "name":"考研真相英語一",
 *              "price":12.0,
 *              "coverPicFormat":"jpg",
 *              "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgAAg....(base64編碼，原文過長不全部展示)
 *        },
 *        {
 *             "status":0,
 *             "name":"二手書",
 *             "price":8.0,
 *             "coverPicFormat":"jpg",
 *             "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXh.......
 *        },
 *        {
 *             "status":0,
 *             "name":"思想道德與法治2021年版",
 *             "price":7.9,
 *             "coverPicFormat":"jpg",
 *             "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYR......
 *         },
 *         {
 *             "status":0,
 *             "name":"中公小學教師資格證",
 *             "price":68.0,
 *             "coverPicFormat":"jpg",
 *             "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4Q......
 *         },
 *         {
 *             "status":0,
 *             "name":"二手自考教材04729大學語文2018版徐中玉陶型傳北京大學出版社",
 *             "price":14.66,
 *             "coverPicFormat":"jpg",
 *             "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBMRXhpZgAATU0AKgAAAAgAAg......
 *         },
 *         {
 *             "status":0,
 *             "name":"國家教師資格考試",
 *             "price":15.0,
 *             "coverPicFormat":"jpg",
 *             "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0.......
 *         }
 *     ]
 * @apiErrorExample 沒有符合的商品
 *     {
 *        null
 *     }
 */

/**
 * @api {GET} /my/products 根據狀態顯示用戶商品
 * @apiName ShowMyProductsByStatus
 * @apiGroup 我的商品
 * @apiPermission none
 *
 * @apiDescription 根據狀態顯示用戶上傳過的商品
 *
 * @apiParam {String} phone 用戶手機號
 * @apiParam {Integer} status 商品狀態
 *
 * @apiSuccessExample 請求成功("0"的例子)
 *     [
 *        {
 *              "status":0,
 *              "name":"考研真相英語一",
 *              "price":12.0,
 *              "coverPicFormat":"jpg",
 *              "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgAAg....(base64編碼，原文過長不全部展示)
 *        },
 *        {
 *             "status":0,
 *             "name":"二手書",
 *             "price":8.0,
 *             "coverPicFormat":"jpg",
 *             "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXh.......
 *        },
 *        {
 *             "status":0,
 *             "name":"思想道德與法治2021年版",
 *             "price":7.9,
 *             "coverPicFormat":"jpg",
 *             "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYR......
 *         },
 *         {
 *             "status":0,
 *             "name":"中公小學教師資格證",
 *             "price":68.0,
 *             "coverPicFormat":"jpg",
 *             "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4Q......
 *         },
 *         {
 *             "status":0,
 *             "name":"二手自考教材04729大學語文2018版徐中玉陶型傳北京大學出版社",
 *             "price":14.66,
 *             "coverPicFormat":"jpg",
 *             "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBMRXhpZgAATU0AKgAAAAgAAg......
 *         },
 *         {
 *             "status":0,
 *             "name":"國家教師資格考試",
 *             "price":15.0,
 *             "coverPicFormat":"jpg",
 *             "coverPic":"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0.......
 *         }
 *     ]
 * @apiErrorExample 沒有符合的商品
 *     {
 *        null
 *     }
 */

/**
 * @api {PUT} /my/product/off 用戶下架商品
 * @apiName OffShelfMyProduct
 * @apiGroup 我的商品
 * @apiPermission none
 *
 * @apiDescription 用戶下架商品的是個暫時性的操作，可以通過[商品恢復上架]使商品重新上架。只有狀態碼為0, 4, 5的商品可以進行下架操作，下架的商品不會刪除其商品信息，但若要刪除某商品，該商品必須為下架狀態。
 *
 * @apiParam {String} number 商品編號
 *
 * @apiSuccessExample 請求成功(例子)
 *     {
 *        201
 *     }
 * @apiErrorExample 商品不存在/商品下架失敗
 *     {
 *        422
 *     }
 * @apiErrorExample 商品為不可下架狀態
 *     {
 *        300
 *     }
 * @apiErrorExample 商品狀態異常
 *     {
 *        400
 *     }
 */

/**
 * @api {PUT} /my/product/on 用戶商品恢復上架
 * @apiName OnShelfMyProduct
 * @apiGroup 我的商品
 * @apiPermission none
 *
 * @apiDescription 此功能提供用戶回復商品上架，只適用於用戶手動下架的商品，即狀態碼為6的商品。
 *
 * @apiParam {String} number 商品編號
 *
 * @apiSuccessExample 請求成功(例子)
 *     {
 *        201
 *     }
 * @apiErrorExample 商品不存在/商品回復上架失敗
 *     {
 *        422
 *     }
 * @apiErrorExample 商品為不可下架狀態
 *     {
 *        400
 *     }
 */

/**
 * @api {DELETE} /my/product 刪除我的商品
 * @apiName DeleteMyProduct
 * @apiGroup 我的商品
 * @apiPermission none
 *
 * @apiDescription 刪除我的商品的功能是完全刪除某一商品的所有信息，即數據庫和磁盤裡的圖片。為了不影響其他用戶交易，該功能需商品處於下架狀態(狀態碼為3或6)方可操作。
 *
 * @apiParam {String} number 商品編號
 *
 * @apiSuccessExample 請求成功(例子)
 *     {
 *        204
 *     }
 * @apiErrorExample 商品不存在
 *     {
 *        422
 *     }
 * @apiErrorExample 刪除失敗(圖片數據/數據庫數據)
 *     {
 *        400
 *     }
 * @apiErrorExample 商品為不可刪除狀態(需先下架)
 *     {
 *        412
 *     }
 */

/**
 * @api {PUT} /my/product 用戶更新商品信息
 * @apiName UpdateMyProduct
 * @apiGroup 我的商品
 * @apiPermission none
 *
 * @apiDescription 用戶更新商品信息(目前沒有提供更新商品圖片的功能，若有需要請前端人員告訴我一聲，謝謝)
 *
 * @apiParam {String} number 商品編號
 * @apiParam {String} name 商品名稱
 * @apiParam {Integer} storage 庫存量
 * @apiParam {Double} price 商品價格
 * @apiParam {String} address 發貨地
 * @apiParam {String} intro 商品描述
 *
 * @apiSuccessExample 請求成功(例子)
 *     {
 *        201
 *     }
 * @apiErrorExample 商品不存在/更新失敗
 *     {
 *        422
 *     }
 */

/**
 * @api {POST} /product/comment 用戶發布留言
 * @apiName UserPostComment
 * @apiGroup 商品留言
 * @apiPermission none
 *
 * @apiDescription 用戶在商品詳情頁面下方發布留言的功能，須通過實名認證的用戶方可發布留言。
 *
 * @apiParam {String} number 商品編號
 * @apiParam {String} phone 用戶手機號
 * @apiParam {String} content 留言內容
 *
 * @apiSuccessExample 發布成功(例子)
 *     {
 *        201
 *     }
 * @apiErrorExample 發布失敗(用戶無權限)
 *     {
 *        400
 *     }
 * @apiErrorExample 發布失敗(商品不存在)
 *     {
 *        422
 *     }
 * @apiErrorExample 發布失敗(數據庫添加失敗)
 *     {
 *        404
 *     }
 */

/**
 * @api {POST} /product/reply 用戶回覆留言
 * @apiName UserPostReply
 * @apiGroup 商品留言
 * @apiPermission none
 *
 * @apiDescription 用戶在某則留言下方回覆，須通過實名認證的用戶方可回覆留言。
 *
 * @apiParam {String} number 商品編號
 * @apiParam {String} phone 用戶手機號
 * @apiParam {Integer} father 回覆的留言編號
 * @apiParam {String} content 留言內容
 *
 * @apiSuccessExample 發布成功(例子)
 *     {
 *        201
 *     }
 * @apiErrorExample 發布失敗(用戶無權限)
 *     {
 *        400
 *     }
 * @apiErrorExample 發布失敗(商品不存在/父留言不存在)
 *     {
 *        422
 *     }
 * @apiErrorExample 發布失敗(數據庫添加失敗)
 *     {
 *        404
 *     }
 */


/**
 * @api {GET} /product/comment 顯示商品的所有留言
 * @apiName GetProductComment
 * @apiGroup 商品留言
 * @apiPermission none
 *
 * @apiDescription 獲取某商品的所有留言信息，留言有兩層: 第一層是根留言，第二層是根留言下所有回覆。
 *
 * @apiParam {String} number 商品編號
 *
 * @apiSuccessExample 請求成功(例子: 編號的商品)
 *     [
 *        {
 *            "id":1,
 *            "userName":"Jamie Sanders",
 *            "headPic":"iVBORw0KGgoAAAANSUhEUgAAAf8AAAH/CAYAA.....",
 *            "date":"2022-12-05 11:57:25",
 *            "content":"請問適合梨形身材嗎?",
 *            "subComments":
 *              [
 *                  {
 *                      "id":4,
 *                      "father_id":1,
 *                      "userName":"天天",
 *                      "fatherName":"Jamie Sanders",
 *                      "headPic":"iVBORw0KGgoAAAANSUhEUgAAAf8AAAH/CAYAA.....",
 *                      "date":"2022-12-05 12:02:12",
 *                      "content":"同問"
 *                  },
 *                  {
 *                     "id":5,
 *                      "father_id":1,
 *                      "userName":"天天",
 *                      "fatherName":"Jamie Sanders",
 *                      "headPic":"iVBORw0KGgoAAAANSUhEUgAAAf8AAAH/CAYAA.....",
 *                      "date":"2022-12-05 12:02:13",
 *                      "content":"同問"
 *                  },
 *                  {
 *                      "id":6,
 *                      "father_id":5,
 *                      "userName":"Jamie Sanders",
 *                      "fatherName":"天天",
 *                      "headPic":"iVBORw0KGgoAAAANSUhEUgAAAf8AAAH/CAYAAABZ8dS+AAAABGdBTUEAA...",
 *                      "date":"2022-12-09 12:10:05",
 *                      "content":"有需要同問兩次嗎?無語"
 *                  }
 *              ]
 *        },
 *        {
 *            "id":2,
 *            "userName":"天天",
 *            "headPic":"iVBORw0KGgoAAAANSUhEUgAAAf8AAAH/CAYAAABZ8dS+AAAABGdBTUEAA...",
 *            "date":"2022-12-05 11:58:54",
 *            "content":"請問裙子的材質是什麼?夏天會不會不透氣?",
 *            "subComments":[]
 *        },
 *        {
 *            "id":3,
 *            "userName":"天天",
 *            "headPic":"iVBORw0KGgoAAAANSUhEUgAAAf8AAAH/CAYAAABZ8dS+AAAABGdBTUEAA...",
 *            "date":"2022-12-05 12:00:01",
 *            "content":"裙子穿脫方便嗎?會不會卡卡的?",
 *            "subComments":[]
 *        }
 *     ]
 */

/**
 * @api {GET} /product/uploads 顯示所有商品上架請求
 * @apiName GetAllProductUploadRequests
 * @apiGroup 服務端:商品管理
 * @apiPermission none
 *
 * @apiDescription 顯示所有商品上架請求，只顯示Outline
 *
 * @apiSuccessExample 請求成功(例子)
 *     [
 *        {
 *             "number":"C1669690298810",
 *             "status":1,
 *             "catalog":"C",
 *             "name":"連衣裙",
 *             "SPhone":"15023192020",
 *             "date":"2022-11-29 10:51:38"
 *         },
 *         {
 *             "number":"C1670300792286",
 *             "status":1,
 *             "catalog":"C",
 *             "name":"複合面料衛衣(黑)",
 *             "SPhone":"15080711348",
 *             "date":"2022-12-06 12:26:32"
 *          }...{}
 *     ]
 */

/**
 * @api {GET} /product/uploads/catalog 根據商品分類顯示所有商品上架請求
 * @apiName GetProductUploadRequestsByCatalog
 * @apiGroup 服務端:商品管理
 * @apiPermission none
 *
 * @apiDescription 根據商品分類顯示所有商品上架請求，只顯示Outline
 *
 * @apiParam {String} catalog 商品分類(B, C, M, ...)
 *
 * @apiSuccessExample 請求成功(例子:C)
 *     [
 *        {
 *             "number":"C1669690298810",
 *             "status":1,
 *             "catalog":"C",
 *             "name":"連衣裙",
 *             "SPhone":"15023192020",
 *             "date":"2022-11-29 10:51:38"
 *         },
 *         {
 *             "number":"C1670300792286",
 *             "status":1,
 *             "catalog":"C",
 *             "name":"複合面料衛衣(黑)",
 *             "SPhone":"15080711348",
 *             "date":"2022-12-06 12:26:32"
 *          }...{}
 *     ]
 */

/**
 * @api {GET} /product/upload 查看商品上架請求詳情
 * @apiName ReadProductUploadRequest
 * @apiGroup 服務端:商品管理
 * @apiPermission none
 *
 * @apiDescription 查看商品上架請求詳情，查看完詳情後再給出審核結果。
 *
 * @apiParam {String} number 商品編號
 *
 * @apiSuccessExample 請求成功(例子:C1669690298810)
 *     {
 *          "number":"C1669690298810",
 *          "name":"連衣裙",
 *          "status":1,
 *          "catalog":"C",
 *          "sellerName":"田嘉淑(真名)",
 *          "address":"重慶大學虎溪校區梅園3棟",
 *          "date":"2022-11-29 10:51:38",
 *          "price":49.9,
 *          "storage":1,
 *          "intro":"由於尺碼買小了 穿不了 全新 質量和版型都很好 看上的小姐姐加QQ喔(QQ號: 3446572877)",
 *          "picture_count":6,
 *          "pictureFormats":["jpg","jpg","jpg","jpg","jpg","jpg"],
 *          "pictures":
 *              [
 *                  "/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsKCwsNDhIQDQ4R....",
 *                  ...,
 *              ]
 *      }
 */

/**
 * @api {PUT} /product/upload 審核商品上架請求
 * @apiName ProcessProductUploadRequest
 * @apiGroup 服務端:商品管理
 * @apiPermission none
 *
 * @apiDescription 針對某一條商品上架請求給出審查結果，該結果會由系統自動發松通知給用戶，用戶可在[設置]-->[系統信息]查看通知。
 *
 * @apiParam {String} number 商品編號
 * @apiParam {String} decision 判決結果(pass/reject)只能有這兩種
 * @apiParam {String} explain 判決結果的說明
 *
 * @apiSuccessExample 處理成功(例子)
 *     {
 *          201
 *      }
 * @apiErrorExample 處理失敗
 *     {
 *        422
 *     }
 */

/**
 * @api {POST} /product/report 用戶舉報商品
 * @apiName UserSendProductReport
 * @apiGroup 用戶舉報
 * @apiPermission none
 *
 * @apiDescription 用戶舉報某一商品，暫時將此功能限制在: 唯有購買過該商品的用戶方可舉報。
 *
 * @apiParam {String} number 商品編號
 * @apiParam {String} phone 用戶(舉報者)手機號
 * @apiParam {String} content 舉報原因
 *
 * @apiSuccessExample 舉報發送成功(例子)
 *     {
 *          201
 *      }
 * @apiErrorExample 發送失敗(用戶無權限)
 *     {
 *        400
 *     }
 * @apiErrorExample 發送失敗(數據庫未更新)
 *     {
 *        422
 *     }
 */

/**
 * @api {POST} /comment/report 用戶舉報留言
 * @apiName UserSendCommentReport
 * @apiGroup 用戶舉報
 * @apiPermission none
 *
 * @apiDescription 用戶舉報某一則留言，須為實名認證用戶。
 *
 * @apiParam {Integer} number 留言序號id
 * @apiParam {String} phone 用戶(舉報者)手機號
 * @apiParam {String} content 舉報原因
 *
 * @apiSuccessExample 舉報發送成功(例子)
 *     {
 *          201
 *      }
 * @apiErrorExample 發送失敗(用戶無權限)
 *     {
 *        400
 *     }
 * @apiErrorExample 發送失敗(數據庫未更新)
 *     {
 *        404
 *     }
 */

/**
 * @api {GET} /my/comment/reports 獲取用戶所有留言舉報
 * @apiName GetUserAllCommentReport
 * @apiGroup 用戶舉報
 * @apiPermission none
 *
 * @apiDescription 獲取用戶所有留言舉報
 *
 * @apiParam {String} phone 用戶手機號
 *
 * @apiSuccessExample 舉報發送成功(例子:15059417755)
 *     [
 *         {
 *             "id": 1,
 *             "targetName": "Jamie Sanders",
 *             "headPicFormat": "jpg",
 *             "targetHeadPic": "iVBORw0KGgoAAAANSUhEUgAAAf8AAAH/CAYAAABZ8dS+AAAABGdBTUEAALGPC/...",
 *             "commentContent": "有需要同問兩次嗎?無語",
 *             "content": "留言不友善",
 *             "status": 0
 *         },...,{}
 *     ]
 */

/**
 * @api {GET} /my/comment/reports/status 根據處理狀態獲取用戶的留言舉報
 * @apiName GetUserCommentReportByStatus
 * @apiGroup 用戶舉報
 * @apiPermission none
 *
 * @apiDescription 根據處理狀態獲取用戶的留言舉報
 *
 * @apiParam {String} phone 用戶手機號
 * @apiParam {Integer} status 舉報處理狀態(0:未處理, 1:已通過, 2:不通過)
 *
 * @apiSuccessExample 舉報發送成功(例子:phone = 15059417755,status = 0)
 *     [
 *         {
 *             "id": 1,
 *             "targetName": "Jamie Sanders",
 *             "headPicFormat": "jpg",
 *             "targetHeadPic": "iVBORw0KGgoAAAANSUhEUgAAAf8AAAH/CAYAAABZ8dS+AAAABGdBTUEAALGPC/...",
 *             "commentContent": "有需要同問兩次嗎?無語",
 *             "content": "留言不友善",
 *             "status": 0
 *         },...,{}
 *     ]
 */

/**
 * @api {GET} /my/product/reports 獲取用戶所有商品舉報
 * @apiName GetUserAllProductReport
 * @apiGroup 用戶舉報
 * @apiPermission none
 *
 * @apiDescription 獲取用戶所有商品舉報
 *
 * @apiParam {String} phone 用戶手機號
 *
 * @apiSuccessExample 舉報發送成功(例子:15059417755)
 *     [
 *         {
 *             "id": 1,
 *             "sellerName": "天天",
 *             "headPicFormat": "jpg",
 *             "sellerHeadPic": "iVBORw0KGgoAAAANSUhEUgAAAf8AAAH/CAYAAABZ8dS+AAAABGdBTUEAALGPC/...",
 *             "productName": "連衣裙",
 *             "coverPicFormat": "jpg",
 *             "productCover": "/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQ...",
 *             "price": 49.9,
 *             "content": "裙子嚴重脫線，洗一次基本就沒法穿了",
 *             "status": 1
 *         },...,{}
 *     ]
 */

/**
 * @api {GET} /my/product/reports/status 根據處理狀態獲取用戶的商品舉報
 * @apiName GetUserProductReportByStatus
 * @apiGroup 用戶舉報
 * @apiPermission none
 *
 * @apiDescription 根據處理狀態獲取用戶的商品舉報
 *
 * @apiParam {String} phone 用戶手機號
 * @apiParam {Integer} status 舉報處理狀態(0:未處理, 1:已通過, 2:不通過)
 *
 * @apiSuccessExample 舉報發送成功(例子:phone = 15059417755,status = 1)
 *    [
 *         {
 *             "id": 1,
 *             "sellerName": "天天",
 *             "headPicFormat": "jpg",
 *             "sellerHeadPic": "iVBORw0KGgoAAAANSUhEUgAAAf8AAAH/CAYAAABZ8dS+AAAABGdBTUEAALGPC/...",
 *             "productName": "連衣裙",
 *             "coverPicFormat": "jpg",
 *             "productCover": "/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQ...",
 *             "price": 49.9,
 *             "content": "裙子嚴重脫線，洗一次基本就沒法穿了",
 *             "status": 1
 *         },...,{}
 *     ]
 */

/**
 * @api {DELETE} /comment/report 用戶刪除留言舉報
 * @apiName UserDeleteCommentReport
 * @apiGroup 用戶舉報
 * @apiPermission none
 *
 * @apiDescription 用戶刪除某一留言舉報
 *
 * @apiParam {Integer} id 留言舉報序號id
 *
 * @apiSuccessExample 舉報刪除成功(例子)
 *     {
 *          204
 *      }
 * @apiErrorExample 舉報刪除失敗(數據庫未更新)
 *     {
 *        422
 *     }
 */

/**
 * @api {DELETE} /product/report 用戶刪除商品舉報
 * @apiName UserDeleteProductReport
 * @apiGroup 用戶舉報
 * @apiPermission none
 *
 * @apiDescription 用戶刪除某一商品舉報
 *
 * @apiParam {Integer} id 商品舉報序號id
 *
 * @apiSuccessExample 舉報刪除成功(例子)
 *     {
 *          204
 *      }
 * @apiErrorExample 舉報刪除失敗(數據庫未更新)
 *     {
 *        422
 *     }
 * @apiErrorExample 舉報刪除失敗(商品舉報數未更新)
 *     {
 *        404
 *     }
 */

/**
 * @api {GET} /product/reports 獲取所有商品舉報請求
 * @apiName GetAllProductReport
 * @apiGroup 服務端:舉報審核
 * @apiPermission none
 *
 * @apiDescription 獲取所有商品舉報請求
 *
 * @apiSuccessExample 請求成功(例子)
 *     [
 *          {
 *              "id":1,
 *              "status":1,
 *              "reporterPhone":"15059417755",
 *              "number":"C1669690298810",
 *              "date":"Dec 5, 2022, 4:47:48 PM"
 *          }...{}
 *      ]
 */

/**
 * @api {GET} /product/status/reports 根據處理狀態獲取商品舉報請求
 * @apiName GetProductReportByStatus
 * @apiGroup 服務端:舉報審核
 * @apiPermission none
 *
 * @apiDescription 根據處理狀態獲取商品舉報請求(0:未處理, 1:舉報成立, 2:舉報不成立)
 *
 * @apiParam {Integer} status 處理狀態
 *
 * @apiSuccessExample 請求成功(例子: 1)
 *     [
 *          {
 *              "id":1,
 *              "status":1,
 *              "reporterPhone":"15059417755",
 *              "number":"C1669690298810",
 *              "date":"Dec 5, 2022, 4:47:48 PM"
 *          }...{}
 *      ]
 */

/**
 * @api {GET} /product/report 閱讀商品舉報請求詳情
 * @apiName ReadProductReport
 * @apiGroup 服務端:舉報審核
 * @apiPermission none
 *
 * @apiDescription 閱讀完商品舉報的請求詳情後，在據此給出判決。
 *
 * @apiParam {Integer} id 商品舉報序號id
 *
 * @apiSuccessExample 請求成功(例子: 1)
 *     {
 *         "id":1,
 *         "status":1,
 *         "reporterPhone":"15059417755",
 *         "reportName":"错霞飞(真名)",
 *         "date":"Dec 5, 2022, 4:47:48 PM",
 *         "content":"裙子嚴重脫線，洗一次基本就沒法穿了",
 *         "productNumber":"C1669690298810",
 *         "productName":"連衣裙",
 *         "reportCount":1(該商品的舉報次數)
 *     }
 */

/**
 * @api {PUT} /product/report/result 處理商品舉報
 * @apiName ProcessProductReport
 * @apiGroup 服務端:舉報審核
 * @apiPermission none
 *
 * @apiDescription 管理員給出商品舉報的判決，在結果出來後系統會自動發送信息給相關用戶(舉報者/被舉報者)，用戶可在[設置]-->[系統消息]中察看。對於違規商品，若檢舉次數超過5次系統會做強制下架處理，沒有超過則會發消息通知賣家整改。
 *
 * @apiParam {Integer} id 商品舉報序號id
 * @apiParam {String} decision 判決結果(pass/reject)
 * @apiParam {String} explain 判決說明
 *
 * @apiSuccessExample 處理成功(例子)
 *     {
 *         201
 *     }
 * @apiErrorExample 處理失敗(數據庫更新失敗)
 *     {
 *        400
 *     }
 * @apiErrorExample 處理失敗(請求不合法)
 *     {
 *        404
 *     }
 */

/**
 * @api {GET} /comment/reports 獲取所有留言舉報請求
 * @apiName GetAllCommentReport
 * @apiGroup 服務端:舉報審核
 * @apiPermission none
 *
 * @apiDescription 獲取所有留言舉報請求
 *
 * @apiSuccessExample 請求成功(例子)
 *     [
 *          {
 *              "id":1,
 *              "status":0,
 *              "reporterPhone":"15059417755",
 *              "commentId":"1(舉報的留言編號)",
 *              "date":"Dec 5, 2022, 4:47:48 PM"
 *          }...{}
 *      ]
 */

/**
 * @api {GET} /comment/reports/status 根據處理狀態獲取留言舉報請求
 * @apiName GetCommentReportByStatus
 * @apiGroup 服務端:舉報審核
 * @apiPermission none
 *
 * @apiDescription 根據處理狀態獲取留言舉報請求(0:未處理, 1:舉報成立, 2:舉報不成立)
 *
 * @apiParam {Integer} status 處理狀態
 *
 * @apiSuccessExample 請求成功(例子: 1)
 *     [
 *          {
 *              "id":1,
 *              "status":1,
 *              "reporterPhone":"15059417755",
 *              "commentId":"1",
 *              "date":"Dec 5, 2022, 4:47:48 PM"
 *          }...{}
 *      ]
 */

/**
 * @api {GET} /comment/reports/same 獲取針對同一則留言的舉報請求
 * @apiName GetCommentReportByCommentId
 * @apiGroup 服務端:舉報審核
 * @apiPermission none
 *
 * @apiDescription 獲取針對同一則留言的舉報請求
 *
 * @apiParam {Integer} commentId 處理狀態
 *
 * @apiSuccessExample 請求成功(例子: 1)
 *     [
 *          {
 *              "id":1,
 *              "status":1,
 *              "reporterPhone":"15059417755",
 *              "commentId":"1",
 *              "date":"Dec 5, 2022, 4:47:48 PM"
 *          }...{}
 *      ]
 */

/**
 * @api {GET} /comment/report 閱讀留言舉報請求詳情
 * @apiName ReadCommentReport
 * @apiGroup 服務端:舉報審核
 * @apiPermission none
 *
 * @apiDescription 閱讀完留言舉報的請求詳情後，在據此給出判決。
 *
 * @apiParam {Integer} id 留言舉報序號id
 *
 * @apiSuccessExample 請求成功(例子: 1)
 *     {
 *         "id":1,
 *         "status":1,
 *         "reporterPhone":"15059417755",
 *         "reportName":"错霞飞(真名)",
 *         "date":"Dec 5, 2022, 4:47:48 PM",
 *         "content":"留言不友善",
 *         "comment": (這裡面放的是留言信息)
 *              {
 *                  "id": 1,
 *                  "father_id":0,
 *                  "number":"C1669690298810",
 *                  "content":"請問適合梨形身材嗎?",
 *                  "date":"Dec 05 11:57:25 CST 2022",
 *                  "phone":"15078818663"
 *              }
 *     }
 */

/**
 * @api {PUT} /comment/report 處理留言舉報
 * @apiName ProcessCommentReport
 * @apiGroup 服務端:舉報審核
 * @apiPermission none
 *
 * @apiDescription 管理員給出留言舉報的判決，在結果出來後系統會自動發送信息給相關用戶(舉報者/被舉報者)，用戶可在[設置]-->[系統消息]中察看。對於違規留言系統會自動刪除該留言。
 *
 * @apiParam {Integer} id 留言舉報序號id
 * @apiParam {String} decision 判決結果(pass/reject)
 * @apiParam {String} explain 判決說明
 *
 * @apiSuccessExample 處理成功(例子)
 *     {
 *         201
 *     }
 * @apiErrorExample 處理失敗(數據庫更新失敗)
 *     {
 *        400
 *     }
 * @apiErrorExample 處理失敗(請求不合法)
 *     {
 *        404
 *     }
 */

/**
 * @api {GET} /setting/notifications 顯示所有系統信息
 * @apiName GetAllNotification
 * @apiGroup 設置:用戶反饋
 * @apiPermission none
 *
 * @apiDescription 顯示所有系統信息，這裡只顯示Outline，status表示是否已讀(0:未讀, 1:已讀)。
 *
 * @apiParam {String} phone 用戶手機號
 *
 * @apiSuccessExample 請求成功(例子:15023192020)
 *     [
 *         {
 *              "id":3,
 *              "type":"warn",
 *              "title":"商品舉報通知",
 *              "date":"Dec 5, 2022, 6:07:20 PM",
 *              "status":0
 *          },...,{}
 *     ]
 */

/**
 * @api {GET} /setting/notifications/unread 顯示所有未讀系統信息
 * @apiName GetUnreadNotification
 * @apiGroup 設置:用戶反饋
 * @apiPermission none
 *
 * @apiDescription 顯示所有未讀系統信息
 *
 * @apiParam {String} phone 用戶手機號
 *
 * @apiSuccessExample 請求成功(例子:15023192020)
 *     [
 *         {
 *              "id":3,
 *              "type":"warn",
 *              "title":"商品舉報通知",
 *              "date":"Dec 5, 2022, 6:07:20 PM",
 *              "status":0
 *          },...,{}
 *     ]
 */

/**
 * @api {GET} /setting/notification/isRead 判斷某消息是否已讀
 * @apiName CheckNotificationIsRead
 * @apiGroup 設置:用戶反饋
 * @apiPermission none
 *
 * @apiDescription 判斷某消息是否已讀，可做為圖標提示時使用(例如未讀消息上方有小紅點)。
 *
 * @apiParam {Integer} id 信息序號id
 *
 * @apiSuccessExample 請求成功(例子:3)
 *     {
 *         false
 *     }
 */

/**
 * @api {GET} /setting/notification 用戶閱讀系統消息
 * @apiName UserReadNotification
 * @apiGroup 設置:用戶反饋
 * @apiPermission none
 *
 * @apiDescription 用戶閱讀系統消息，系統會返回消息詳情，並將該消息閱讀狀態設置成已讀。
 *
 * @apiParam {Integer} id 信息序號id
 *
 * @apiSuccessExample 請求成功(例子:3)
 *     {
 *          "id":3,
 *          "type":"warn",
 *          "title":"商品舉報通知",
 *          "to":"天天15023192020",
 *          "content":"本系統非常遺憾的通知您，您的商品 [連衣裙] 已被系統管理確認存在違規行為，請及時處理以免商品被迫下架。謝謝您的配合。",
 *          "date":"Dec 5, 2022, 6:07:20 PM",
 *          "from":"系统管理员"
 *     }
 */

/**
 * @api {DELETE} /setting/notification 用戶刪除系統消息
 * @apiName UserDeleteNotification
 * @apiGroup 設置:用戶反饋
 * @apiPermission none
 *
 * @apiDescription 用戶刪除一則系統消息
 *
 * @apiParam {Integer} id 信息序號id
 *
 * @apiSuccessExample 刪除成功(例子)
 *     {
 *          204
 *     }
 * @apiErrorExample 刪除失敗
 *     {
 *        422
 *     }
 */

/**
 * @api {DELETE} /setting/notifications 刪除所有已讀系統消息
 * @apiName UserDeleteReadNotifications
 * @apiGroup 設置:用戶反饋
 * @apiPermission none
 *
 * @apiDescription 用戶刪除所有已讀的系統消息
 *
 * @apiParam {String} phone 用戶手機號
 *
 * @apiSuccessExample 刪除成功(例子)
 *     {
 *          204
 *     }
 * @apiErrorExample 刪除失敗
 *     {
 *        422
 *     }
 */

/**
 * @api {POST} /evaluation 買家進行商品評價
 * @apiName BuyerEvaluateProduct
 * @apiGroup 商品評價
 * @apiPermission none
 *
 * @apiDescription 買家購買商品並檢驗過後，對商品進行進一步的評價，得分為(0~5)之間。
 *
 * @apiParam {String} number 商品編號
 * @apiParam {String} phone 買家手機
 * @apiParam {Object} oneEvaluate 評價信息
 * @apiParam {Integer} score1 評價信息:描述相符得分
 * @apiParam {Integer} score2 評價信息:物流服務得分
 * @apiParam {Integer} score3 評價信息:態度服務得分
 * @apiParam {Boolean} isAnonymous 評價信息:評價是否匿名(true:是，false:否)
 * @apiParam {String} evaluate 評價信息:文字評價
 *
 * @apiSuccessExample 評價成功(例子)
 *     {
 *          201
 *     }
 * @apiErrorExample 評價失敗
 *     {
 *        404
 *     }
 */

/**
 * @api {GET} /setting/authentication 獲取用戶實名認證狀態
 * @apiName GetUserAuthenticationStatus
 * @apiGroup 設置:實名認證
 * @apiPermission none
 *
 * @apiDescription 獲取用戶實名認證狀態
 *
 * @apiParam {String} phone 用戶手機號
 *
 * @apiSuccessExample 獲取成功(已通過)
 *     {
 *          201
 *     }
 * @apiSuccessExample 獲取成功(審核中)
 *     {
 *          202
 *     }
 * @apiSuccessExample 獲取成功(非實名認證用戶)
 *     {
 *          203
 *     }
 */


/**
 * @api {POST} /setting/authentication 發送實名認證請求
 * @apiName UserPostAuthenticationRequest
 * @apiGroup 設置:實名認證
 * @apiPermission none
 *
 * @apiDescription 用戶申請實名認證功能。本系統實名認證審核的流程是: 用戶上傳身分信息-->信息進入審核階段-->審核成功/失敗-->用戶權限更改/不更改。用戶可以在[設置]-->[系統消息]察看結果。
 *
 * @apiParam {String} phone 用戶手機號
 * @apiParam {String} realName 用戶真實姓名
 * @apiParam {String} idNumber 身分證號碼
 * @apiParam {File} idCardPic 身分證照片(這些都用form-data傳就行)
 *
 * @apiSuccessExample 信息上傳成功
 *     {
 *          201
 *     }
 * @apiSuccessExample 已是實名認證用戶
 *     {
 *          200
 *     }
 * @apiSuccessExample 已經發送過了，請耐心等待結果
 *     {
 *          203
 *     }
 * @apiErrorExample 用戶不存在/數據庫添加失敗
 *     {
 *          422
 *     }
 * @apiErrorExample 圖片上傳失敗
 *     {
 *          404
 *     }
 */

/**
 * @api {GET} /authentication/requests 顯示所有實名認證請求
 * @apiName GetAllAuthenticationRueqest
 * @apiGroup 服務端:用戶管理
 * @apiPermission none
 *
 * @apiDescription 顯示所有未處理的實名認證請求
 *
 * @apiSuccessExample 請求成功(例子)
 *     [
 *         {
 *              "id":7,
 *              "phone":"15049936157",
 *              "realName":"楊單詞",
 *              "date":"2022-12-09 09:39:39",
 *              "status":0
 *          },
 *          {
 *              "id":8,
 *              "phone":"15013729832",
 *              "realName":"鄧紫祺",
 *              "date":"2022-12-09 09:40:44",
 *              "status":0
 *          },
 *          {
 *              "id":10,
 *              "phone":"15083622395",
 *              "realName":"陳八方",
 *              "date":"2022-12-09 10:00:38",
 *              "status":0
 *          }
 *     ]
 */

/**
 * @api {GET} /authentication/requests/status 根據處理狀態顯示實名認證請求
 * @apiName GetAuthenticationRequestByStatus
 * @apiGroup 服務端:用戶管理
 * @apiPermission none
 *
 * @apiDescription 根據處理狀態顯示實名認證請求
 *
 * @apiParam {Integer} status 處理狀態(0:未處理, 1:通過, 2: 不通過)
 *
 * @apiSuccessExample 請求成功(例子:0)
 *    [
 *         {
 *              "id":7,
 *              "phone":"15049936157",
 *              "realName":"楊單詞",
 *              "date":"2022-12-09 09:39:39",
 *              "status":0
 *          },
 *          {
 *              "id":8,
 *              "phone":"15013729832",
 *              "realName":"鄧紫祺",
 *              "date":"2022-12-09 09:40:44",
 *              "status":0
 *          },
 *          {
 *              "id":10,
 *              "phone":"15083622395",
 *              "realName":"陳八方",
 *              "date":"2022-12-09 10:00:38",
 *              "status":0
 *          }
 *     ]
 */

/**
 * @api {GET} /authentication/request 管理員閱讀實名認證請求
 * @apiName ReadAuthenticationRequest
 * @apiGroup 服務端:用戶管理
 * @apiPermission none
 *
 * @apiDescription 管理員閱讀實名認證請求，並據此給出判決。
 *
 * @apiParam {Integer} id 實名認證請求id
 *
 * @apiSuccessExample 請求成功(例子:10)
 *     {
 *          "id":10,
 *          "phone":"15083622395",
 *          "date":"2022-12-09 10:00:38",
 *          "realName":"陳八方",
 *          "format":"jpg(圖片格式，顯示圖片用)",
 *          "idCardPic":"iVBORw0KGgoAAAANSUhEUgAAAf8AAAH/CAYAAABZ8dS+AAAA..."
 *     }
 */

/**
 * @api {PUT} /authentication/request 處理實名認證請求
 * @apiName ProcessAuthenticationRequest
 * @apiGroup 服務端:用戶管理
 * @apiPermission none
 *
 * @apiDescription 管理員在察看具體信息後給出判決decision(pass:通過, reject:不通過)，系統會自動對用戶信息做相應的更動，並發送通知告知結果。
 *
 * @apiParam {Integer} id 實名認證請求id
 * @apiParam {String} decision 判決(pass/reject)
 * @apiParam {String} explain 管理員對判決給出相應的解釋
 *
 * @apiSuccessExample 處理成功
 *     {
 *          201
 *     }
 * @apiErrorExample 用戶數據更新失敗/數據庫更新失敗
 *     {
 *          422
 *     }
 * @apiErrorExample 圖片操作失敗
 *     {
 *          400
 *     }
 * @apiErrorExample 請求格式錯誤(decision只能是pass或reject)
 *     {
 *          404
 *     }
 */

/**
 * @api {GET} /seller/grade 根據賣家顯示其系統評分
 * @apiName GetSystemSellerGrade
 * @apiGroup 賣家信息主頁
 * @apiPermission none
 *
 * @apiDescription 根據賣家顯示其商品評價，此功能以(用戶違規數/用戶商品評價得分/用戶商品舉報數)三個數值進行加權計算獲取得分，滿分為10分。
 *
 * @apiParam {String} phone 賣家手機號
 *
 * @apiSuccessExample 請求成功(返回Double)
 *     {
 *         10.00
 *     }
 */

/**
 * @api {GET} /seller/info 根據賣家顯示其基本信息(公開)
 * @apiName GetSellerBasicInfo
 * @apiGroup 賣家信息主頁
 * @apiPermission none
 *
 * @apiDescription 根據賣家顯示其可公開的基本信息
 *
 * @apiParam {String} phone 賣家手機號
 *
 * @apiSuccessExample 請求成功(例子:非匿名評價)
 *     {
 *         "phone":"(用戶手機號)",
 *         "email":"(用戶郵箱)",
 *         "userName":"(用戶名)",
 *         "tradeCount":(歷史交易數，只當賣家的交易數，買東西的不算),
 *         "format":"jpg(用戶頭像文件格式)",
 *         "headPic":"(用戶頭像base64編碼)"
 *     }
 */


/**
 * @api {GET} /evaluations 根據賣家顯示其商品評價
 * @apiName GetEvaluationsBySeller
 * @apiGroup 賣家信息主頁
 * @apiPermission none
 *
 * @apiDescription 根據賣家顯示其商品評價
 *
 * @apiParam {String} sellerPhone 賣家手機號
 *
 * @apiSuccessExample 請求成功(例子:非匿名評價)
 *     [
 *          {
 *              "number":"(商品編號)",
 *              "productName":"(商品名稱)",
 *              "date":"(評價日期)",
 *              "score1":(描述相符得分),
 *              "score2":(物流服務得分),
 *              "score3":(服務態度得分),
 *              "evaluate":"(文字評價)",
 *              "isAnonymous":false,
 *              "buyer": {
 *                  "buyerPhone":"(買家手機號)",
 *                  "buyerName":"(買家用戶名)",
 *                  "buyerHeadPic":"(買家頭像)"
 *              }
 *          },....{}
 *      ]
 * @apiSuccessExample 請求成功(例子:匿名評價)
 *    [
 *          {
 *              "number":"(商品編號)",
 *              "productName":"(商品名稱)",
 *              "date":"(評價日期)",
 *              "score1":(描述相符得分),
 *              "score2":(物流服務得分),
 *              "score3":(服務態度得分),
 *              "evaluate":"(文字評價)",
 *              "isAnonymous":ture
 *          },....{}
 *      ]
 */
