define({ "api": [
  {
    "type": "POST",
    "url": "/product",
    "title": "上傳商品",
    "name": "UploadProduct",
    "group": "商品上傳",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>用戶上傳商品功能，用戶須通過實名認證方可使用此功能。本系統上傳商品的流程是: 用戶上傳商品--&gt;商品進入審核階段--&gt;審核成功/失敗--&gt;商品上架/不予上架。用戶可以在&quot;我的商品&quot;中查看此流程進度。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>商品名稱</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶(賣家)手機號</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "storage",
            "description": "<p>庫存量</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "intro",
            "description": "<p>商品描述</p>"
          },
          {
            "group": "Parameter",
            "type": "Double",
            "optional": false,
            "field": "price",
            "description": "<p>商品價格</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "catalog",
            "description": "<p>商品分類(編碼:B,M,C....)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "address",
            "description": "<p>發貨地</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "商品上傳成功",
          "content": "{\n   \"status\":201,\n   \"number\":\"C1670300792286(上傳圖片用)\"\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "用戶無權限",
          "content": "{\n  \"status\":403,\n   \"number\":null\n}",
          "type": "json"
        },
        {
          "title": "商品編號重複(稍後再嘗試即可)",
          "content": "{\n  \"status\":422,\n   \"number\":null\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "商品上傳"
  },
  {
    "type": "POST",
    "url": "/product/picture",
    "title": "上傳商品圖片",
    "name": "UploadProductPicture",
    "group": "商品上傳",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>用戶上傳商品描述圖片，一次傳一張。當商品信息被成功天加入數據庫中方可使用此功能。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "number",
            "description": "<p>商品編號(商品信息成功上傳後返回)</p>"
          },
          {
            "group": "Parameter",
            "type": "MultipartFile",
            "optional": false,
            "field": "picture",
            "description": "<p>商品圖片</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "商品圖片上傳成功",
          "content": "{\n   201\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "圖片保存失敗",
          "content": "{\n   404\n}",
          "type": "json"
        },
        {
          "title": "數據庫更新失敗",
          "content": "{\n    422\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "商品上傳"
  },
  {
    "type": "get",
    "url": "/all/likes",
    "title": "顯示所有收藏商品",
    "name": "AllLikes",
    "group": "商品收藏",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>顯示所有收藏商品，默認排序:收藏日期新到舊</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功",
          "content": "[\n   {\n     \"number\":\"B1667559776586\",\n     \"coverPicFormat\":\"jpg\",\n     \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)\n     \"name\":學生心理健康教育第二版\",\n     \"price\":7.9\n   },\n     ....,{}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "商品收藏"
  },
  {
    "type": "get",
    "url": "/catalog/likes",
    "title": "依照商品分類顯示收藏商品",
    "name": "CatalogLikes",
    "group": "商品收藏",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>依照商品分類顯示收藏商品</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "catalog",
            "description": "<p>分類編碼(B,M,...)</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功",
          "content": "[\n   {\n     \"number\":\"B1667559776586\",\n     \"coverPicFormat\":\"jpg\",\n     \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)\n     \"name\":學生心理健康教育第二版\",\n     \"price\":7.9\n   },\n     ....,{}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "商品收藏"
  },
  {
    "type": "get",
    "url": "/like",
    "title": "確認該商品是否已收藏",
    "name": "CheckLike",
    "group": "商品收藏",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>檢查商品是否已收藏的功能可用於前端界面顯示愛心顏色的判斷標準。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "number",
            "description": "<p>商品編碼</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "商品已收藏",
          "content": "{\n   ture\n}",
          "type": "json"
        },
        {
          "title": "商品未收藏",
          "content": "{\n   false\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "商品收藏"
  },
  {
    "type": "delete",
    "url": "/likes",
    "title": "批量取消收藏",
    "name": "DeleteLikes",
    "group": "商品收藏",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>批量取消收藏</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "numbers",
            "description": "<p>[&quot;商品編碼1&quot;, &quot;商品編碼2&quot;,....]</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "取消收藏成功",
          "content": "{\n   204\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "取消收藏失敗",
          "content": "{\n   404\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "商品收藏"
  },
  {
    "type": "get",
    "url": "/all/likes/order",
    "title": "切換不同的排序方式",
    "name": "OrderLikes",
    "group": "商品收藏",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>切換不同的排序方式，type的值:0(添加日期:新--&gt;舊), 1(添加日期:舊--&gt;新), 2(商品價格:低--&gt;高), 3(商品價格:高--&gt;低)</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "type",
            "description": "<p>排序方式編號</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功",
          "content": "[\n   {\n     \"number\":\"B1667559776586\",\n     \"coverPicFormat\":\"jpg\",\n     \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)\n     \"name\":學生心理健康教育第二版\",\n     \"price\":7.9\n   },\n     ....,{}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "商品收藏"
  },
  {
    "type": "post",
    "url": "/like",
    "title": "添加/取消收藏",
    "name": "PressLike",
    "group": "商品收藏",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>添加/取消收藏是當用戶點下收藏鍵時，通過商品收藏狀態自動判斷該進行收藏/取消收藏的動作。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "number",
            "description": "<p>商品編碼</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "收藏成功",
          "content": "{\n   201\n}",
          "type": "json"
        },
        {
          "title": "取消收藏成功",
          "content": "{\n   204\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "用戶無權限",
          "content": "{\n   403\n}",
          "type": "json"
        },
        {
          "title": "收藏失敗",
          "content": "{\n   404\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "商品收藏"
  },
  {
    "type": "get",
    "url": "/catalog/products",
    "title": "分類檢索商品",
    "name": "CatalogProduct",
    "group": "商品查詢",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>分類檢索商品</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "catalog",
            "description": "<p>商品分類編碼(B,M,E...)</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功",
          "content": "[\n   {\n     \"number\":\"B1667559776586\",\n     \"coverPicFormat\":\"jpg\",\n     \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)\n     \"name\":學生心理健康教育第二版\",\n     \"price\":7.9\n   },\n     ....,{}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "商品查詢"
  },
  {
    "type": "get",
    "url": "/homepage",
    "title": "主頁面商品顯示",
    "name": "Homepage",
    "group": "商品查詢",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>主頁面商品顯示，返回商品信息的List</p>",
    "success": {
      "examples": [
        {
          "title": "請求成功",
          "content": "[\n   {\n     \"number\":\"B1667559776586\",\n     \"coverPicFormat\":\"jpg\",\n     \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)\n     \"name\":學生心理健康教育第二版\",\n     \"price\":7.9\n   },\n     ....,{}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "商品查詢"
  },
  {
    "type": "get",
    "url": "/product/detail",
    "title": "商品詳細信息顯示",
    "name": "ProductDetail",
    "group": "商品查詢",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>商品詳細信息顯示，picture_count返回該商品的圖片數，並由pictures以List的形式傳給前端</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "number",
            "description": "<p>商品編碼</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功",
          "content": "{\n   \"name\":\"大學生心理健康教育第二版\",\n   \"seller_name\":\"天天\",\n   \"seller_pic\":\"iVBORw0KGgoAAAANSUhEUgAAAf8AAAH/CAYAAABZ8dS+AAAABGdBTUEAALGPC/...\",\n   \"address\":\"重慶大學虎溪校區竹園3棟\",\n   \"date\":\"2022-11-04 19:02:56\",\n   \"price\":7.9,\n   \"intro\":\"全新沒有用過，到貨前就退課了\",\n   \"like_count\":0,\n   \"picture_count\":1,\n   \"storage\":1,\n   \"pictureFormats\":[\"jpg\"]\n   \"pictures\":[\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAA.....(base64編碼，原文過長不全部展示)\",...,\"...\"]\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "商品查詢"
  },
  {
    "type": "get",
    "url": "/search/products",
    "title": "搜索欄檢索",
    "name": "SearchProduct",
    "group": "商品查詢",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>搜索欄檢索</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "keyword",
            "description": "<p>搜索欄輸入內容</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功",
          "content": "[\n   {\n     \"number\":\"B1667559776586\",\n     \"coverPicFormat\":\"jpg\",\n     \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)\n     \"name\":學生心理健康教育第二版\",\n     \"price\":7.9\n   },\n     ....,{}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "商品查詢"
  },
  {
    "type": "get",
    "url": "/homepage/promote/products",
    "title": "根據用戶數據推薦的商品(top 10)",
    "name": "TopTenCustomizedProducts",
    "group": "商品查詢",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>根據用戶數據推薦的商品(top 10)，沒有真的實現，只是隨便返回10個</p>",
    "success": {
      "examples": [
        {
          "title": "請求成功",
          "content": "[\n   {\n     \"number\":\"B1667559776586\",\n     \"coverPicFormat\":\"jpg\",\n     \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)\n     \"name\":學生心理健康教育第二版\",\n     \"price\":7.9\n   },\n     ....,{}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "商品查詢"
  },
  {
    "type": "get",
    "url": "/homepage/new/products",
    "title": "最新商品(top 10)",
    "name": "TopTenNewestProducts",
    "group": "商品查詢",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>最新商品(top 10)，返回最新上架的十個商品</p>",
    "success": {
      "examples": [
        {
          "title": "請求成功",
          "content": "[\n   {\n     \"number\":\"B1667559776586\",\n     \"coverPicFormat\":\"jpg\",\n     \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)\n     \"name\":學生心理健康教育第二版\",\n     \"price\":7.9\n   },\n     ....,{}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "商品查詢"
  },
  {
    "type": "GET",
    "url": "/product/comment",
    "title": "顯示商品的所有留言",
    "name": "GetProductComment",
    "group": "商品留言",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>獲取某商品的所有留言信息，留言有兩層: 第一層是根留言，第二層是根留言下所有回覆。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "number",
            "description": "<p>商品編號</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子: 編號的商品)",
          "content": "[\n   {\n       \"id\":1,\n       \"userName\":\"Jamie Sanders\",\n       \"headPic\":\"iVBORw0KGgoAAAANSUhEUgAAAf8AAAH/CAYAA.....\",\n       \"date\":\"2022-12-05 11:57:25\",\n       \"content\":\"請問適合梨形身材嗎?\",\n       \"subComments\":\n         [\n             {\n                 \"id\":4,\n                 \"father_id\":1,\n                 \"userName\":\"天天\",\n                 \"fatherName\":\"Jamie Sanders\",\n                 \"headPic\":\"iVBORw0KGgoAAAANSUhEUgAAAf8AAAH/CAYAA.....\",\n                 \"date\":\"2022-12-05 12:02:12\",\n                 \"content\":\"同問\"\n             },\n             {\n                \"id\":5,\n                 \"father_id\":1,\n                 \"userName\":\"天天\",\n                 \"fatherName\":\"Jamie Sanders\",\n                 \"headPic\":\"iVBORw0KGgoAAAANSUhEUgAAAf8AAAH/CAYAA.....\",\n                 \"date\":\"2022-12-05 12:02:13\",\n                 \"content\":\"同問\"\n             },\n             {\n                 \"id\":6,\n                 \"father_id\":5,\n                 \"userName\":\"Jamie Sanders\",\n                 \"fatherName\":\"天天\",\n                 \"headPic\":\"iVBORw0KGgoAAAANSUhEUgAAAf8AAAH/CAYAAABZ8dS+AAAABGdBTUEAA...\",\n                 \"date\":\"2022-12-09 12:10:05\",\n                 \"content\":\"有需要同問兩次嗎?無語\"\n             }\n         ]\n   },\n   {\n       \"id\":2,\n       \"userName\":\"天天\",\n       \"headPic\":\"iVBORw0KGgoAAAANSUhEUgAAAf8AAAH/CAYAAABZ8dS+AAAABGdBTUEAA...\",\n       \"date\":\"2022-12-05 11:58:54\",\n       \"content\":\"請問裙子的材質是什麼?夏天會不會不透氣?\",\n       \"subComments\":[]\n   },\n   {\n       \"id\":3,\n       \"userName\":\"天天\",\n       \"headPic\":\"iVBORw0KGgoAAAANSUhEUgAAAf8AAAH/CAYAAABZ8dS+AAAABGdBTUEAA...\",\n       \"date\":\"2022-12-05 12:00:01\",\n       \"content\":\"裙子穿脫方便嗎?會不會卡卡的?\",\n       \"subComments\":[]\n   }\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "商品留言"
  },
  {
    "type": "POST",
    "url": "/product/comment",
    "title": "用戶發布留言",
    "name": "UserPostComment",
    "group": "商品留言",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>用戶在商品詳情頁面下方發布留言的功能，須通過實名認證的用戶方可發布留言。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "number",
            "description": "<p>商品編號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>留言內容</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "發布成功(例子)",
          "content": "{\n   201\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "發布失敗(用戶無權限)",
          "content": "{\n   400\n}",
          "type": "json"
        },
        {
          "title": "發布失敗(商品不存在)",
          "content": "{\n   422\n}",
          "type": "json"
        },
        {
          "title": "發布失敗(數據庫添加失敗)",
          "content": "{\n   404\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "商品留言"
  },
  {
    "type": "POST",
    "url": "/product/reply",
    "title": "用戶回覆留言",
    "name": "UserPostReply",
    "group": "商品留言",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>用戶在某則留言下方回覆，須通過實名認證的用戶方可回覆留言。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "number",
            "description": "<p>商品編號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "father",
            "description": "<p>回覆的留言編號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>留言內容</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "發布成功(例子)",
          "content": "{\n   201\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "發布失敗(用戶無權限)",
          "content": "{\n   400\n}",
          "type": "json"
        },
        {
          "title": "發布失敗(商品不存在/父留言不存在)",
          "content": "{\n   422\n}",
          "type": "json"
        },
        {
          "title": "發布失敗(數據庫添加失敗)",
          "content": "{\n   404\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "商品留言"
  },
  {
    "type": "POST",
    "url": "/evaluation",
    "title": "買家進行商品評價",
    "name": "BuyerEvaluateProduct",
    "group": "商品評價",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>買家購買商品並檢驗過後，對商品進行進一步的評價，得分為(0~5)之間。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "number",
            "description": "<p>商品編號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>買家手機</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "score1",
            "description": "<p>描述相符得分</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "score2",
            "description": "<p>物流服務得分</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "score3",
            "description": "<p>態度服務得分</p>"
          },
          {
            "group": "Parameter",
            "type": "Boolean",
            "optional": false,
            "field": "isAnonymous",
            "description": "<p>評價是否匿名(true:是，false:否)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "evaluate",
            "description": "<p>文字評價</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "評價成功(例子)",
          "content": "{\n     201\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "評價失敗",
          "content": "{\n   404\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "商品評價"
  },
  {
    "type": "POST",
    "url": "/setting/address",
    "title": "添加地址",
    "name": "AddAddress",
    "group": "地址管理",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>添加地址</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>當前用戶手機號</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "isDefaultAddress",
            "description": "<p>是否需要設置為默認地址(需要:1,不需要:0)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>收件者的稱呼</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "receiverPhone",
            "description": "<p>收件者的手機號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "region",
            "description": "<p>地區</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "addressDetail",
            "description": "<p>詳細地址</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "添加成功(因為算是數據庫更新，所以返回800)",
          "content": "{\n   800\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "添加失敗",
          "content": "{\n   400\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "地址管理"
  },
  {
    "type": "DELETE",
    "url": "/setting/address",
    "title": "刪除某地址",
    "name": "DeleteAddress",
    "group": "地址管理",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>刪除某地址</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "rank",
            "description": "<p>要刪除的地址序列號</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "刪除地址成功",
          "content": "{\n   800\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "刪除失敗(沒有更新的數據or要刪除的地址不存在)",
          "content": "{\n   400\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "地址管理"
  },
  {
    "type": "GET",
    "url": "/default/address",
    "title": "獲取默認地址",
    "name": "GetDefaultAddress",
    "group": "地址管理",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>獲取用戶的默認地址</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "成功返回地址信息(例子)",
          "content": "{\n   \"receiverName\":\"喵薄荷\",\n   \"receiverPhone\":\"15078818663\",\n   \"region\":\"重慶\",\n   \"addressDetail\":\"重慶大學虎溪校區松園5棟102室\",\n   \"rank\":0\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "默認地址不存在",
          "content": "{\n   null\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "地址管理"
  },
  {
    "type": "PUT",
    "url": "/setting/default/address",
    "title": "將某地址設為默認地址",
    "name": "SetAsDefaultAddress",
    "group": "地址管理",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>將某地址設為默認地址</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "rank",
            "description": "<p>要設為默認地址的序列號</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "更改地址成功",
          "content": "{\n   800\n}",
          "type": "json"
        },
        {
          "title": "更改成功(rank=0)",
          "content": "{\n   200\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "更改失敗(沒有更新數據or地址不存在)",
          "content": "{\n   400\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "地址管理"
  },
  {
    "type": "GET",
    "url": "/setting/addresses",
    "title": "顯示用戶所有地址",
    "name": "ShowAllAddress",
    "group": "地址管理",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>顯示用戶所有地址，返回信息rank是更新與刪除地址所需要的參數</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子)",
          "content": "[\n   {\n        \"receiverName\":\"喵薄荷\",\n        \"receiverPhone\":\"15078818663\",\n        \"region\":\"重慶\",\n        \"addressDetail\":\"重慶大學虎溪校區松園5棟102室\",\n        \"rank\":0\n   },\n   {\n        \"receiverName\":\"大頭兒子\",\n        \"receiverPhone\":\"15078818663\",\n        \"region\":\"重慶\",\n        \"addressDetail\":\"重慶大學虎溪校區松園5棟102室\",\n        \"rank\":1\n   },\n   {\n        \"receiverName\":\"神奇寶可夢\",\n        \"receiverPhone\":\"15078818663\",\n        \"region\":\"重慶\",\n        \"addressDetail\":\"重慶大學虎溪校區松園5棟102室\",\n        \"rank\":2\n    }\n]",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "若沒有任何地址存在",
          "content": "{\n   null\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "地址管理"
  },
  {
    "type": "PUT",
    "url": "/setting/address",
    "title": "編輯地址",
    "name": "UpdateAddress",
    "group": "地址管理",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>編輯地址</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "rank",
            "description": "<p>地址序列號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>收件者的稱呼</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "receiverPhone",
            "description": "<p>收件者的手機號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "region",
            "description": "<p>地區</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "addressDetail",
            "description": "<p>詳細地址</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "更新成功",
          "content": "{\n   800\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "更新失敗(沒有更新)",
          "content": "{\n   400\n}",
          "type": "json"
        },
        {
          "title": "請求數據不存在(默認地址)",
          "content": "{\n   4001",
          "type": "json"
        },
        {
          "title": "請求數據不存在(其他地址)",
          "content": "{\n   4002\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "地址管理"
  },
  {
    "type": "DELETE",
    "url": "/my/product",
    "title": "刪除我的商品",
    "name": "DeleteMyProduct",
    "group": "我的商品",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>刪除我的商品的功能是完全刪除某一商品的所有信息，即數據庫和磁盤裡的圖片。為了不影響其他用戶交易，該功能需商品處於下架狀態(狀態碼為3或6)方可操作。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "number",
            "description": "<p>商品編號</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子)",
          "content": "{\n   204\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "商品不存在",
          "content": "{\n   422\n}",
          "type": "json"
        },
        {
          "title": "刪除失敗(圖片數據/數據庫數據)",
          "content": "{\n   400\n}",
          "type": "json"
        },
        {
          "title": "商品為不可刪除狀態(需先下架)",
          "content": "{\n   412\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "我的商品"
  },
  {
    "type": "PUT",
    "url": "/my/product/off",
    "title": "用戶下架商品",
    "name": "OffShelfMyProduct",
    "group": "我的商品",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>用戶下架商品的是個暫時性的操作，可以通過[商品恢復上架]使商品重新上架。只有狀態碼為0, 4, 5的商品可以進行下架操作，下架的商品不會刪除其商品信息，但若要刪除某商品，該商品必須為下架狀態。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "number",
            "description": "<p>商品編號</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子)",
          "content": "{\n   201\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "商品不存在/商品下架失敗",
          "content": "{\n   422\n}",
          "type": "json"
        },
        {
          "title": "商品為不可下架狀態",
          "content": "{\n   300\n}",
          "type": "json"
        },
        {
          "title": "商品狀態異常",
          "content": "{\n   400\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "我的商品"
  },
  {
    "type": "PUT",
    "url": "/my/product/on",
    "title": "用戶商品恢復上架",
    "name": "OnShelfMyProduct",
    "group": "我的商品",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>此功能提供用戶回復商品上架，只適用於用戶手動下架的商品，即狀態碼為6的商品。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "number",
            "description": "<p>商品編號</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子)",
          "content": "{\n   201\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "商品不存在/商品回復上架失敗",
          "content": "{\n   422\n}",
          "type": "json"
        },
        {
          "title": "商品為不可下架狀態",
          "content": "{\n   400\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "我的商品"
  },
  {
    "type": "GET",
    "url": "/my/products",
    "title": "顯示用戶所有商品",
    "name": "ShowAllMyProducts",
    "group": "我的商品",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>顯示用戶所有上傳過的商品</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(手機號為1635905050的例子)",
          "content": "[\n   {\n         \"status\":0,\n         \"name\":\"考研真相英語一\",\n         \"price\":12.0,\n         \"coverPicFormat\":\"jpg\",\n         \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgAAg....(base64編碼，原文過長不全部展示)\n   },\n   {\n        \"status\":0,\n        \"name\":\"二手書\",\n        \"price\":8.0,\n        \"coverPicFormat\":\"jpg\",\n        \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXh.......\n   },\n   {\n        \"status\":0,\n        \"name\":\"思想道德與法治2021年版\",\n        \"price\":7.9,\n        \"coverPicFormat\":\"jpg\",\n        \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYR......\n    },\n    {\n        \"status\":0,\n        \"name\":\"中公小學教師資格證\",\n        \"price\":68.0,\n        \"coverPicFormat\":\"jpg\",\n        \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4Q......\n    },\n    {\n        \"status\":0,\n        \"name\":\"二手自考教材04729大學語文2018版徐中玉陶型傳北京大學出版社\",\n        \"price\":14.66,\n        \"coverPicFormat\":\"jpg\",\n        \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBMRXhpZgAATU0AKgAAAAgAAg......\n    },\n    {\n        \"status\":0,\n        \"name\":\"國家教師資格考試\",\n        \"price\":15.0,\n        \"coverPicFormat\":\"jpg\",\n        \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0.......\n    }\n]",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "沒有任何上傳的商品",
          "content": "{\n   null\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "我的商品"
  },
  {
    "type": "GET",
    "url": "/my/products/catalog",
    "title": "根據分類顯示用戶商品",
    "name": "ShowMyProductsByCatalog",
    "group": "我的商品",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>根據分類顯示用戶上傳過的商品</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "catalog",
            "description": "<p>商品分類(B,M,C...)</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(\"B\"的例子)",
          "content": "[\n   {\n         \"status\":0,\n         \"name\":\"考研真相英語一\",\n         \"price\":12.0,\n         \"coverPicFormat\":\"jpg\",\n         \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgAAg....(base64編碼，原文過長不全部展示)\n   },\n   {\n        \"status\":0,\n        \"name\":\"二手書\",\n        \"price\":8.0,\n        \"coverPicFormat\":\"jpg\",\n        \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXh.......\n   },\n   {\n        \"status\":0,\n        \"name\":\"思想道德與法治2021年版\",\n        \"price\":7.9,\n        \"coverPicFormat\":\"jpg\",\n        \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYR......\n    },\n    {\n        \"status\":0,\n        \"name\":\"中公小學教師資格證\",\n        \"price\":68.0,\n        \"coverPicFormat\":\"jpg\",\n        \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4Q......\n    },\n    {\n        \"status\":0,\n        \"name\":\"二手自考教材04729大學語文2018版徐中玉陶型傳北京大學出版社\",\n        \"price\":14.66,\n        \"coverPicFormat\":\"jpg\",\n        \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBMRXhpZgAATU0AKgAAAAgAAg......\n    },\n    {\n        \"status\":0,\n        \"name\":\"國家教師資格考試\",\n        \"price\":15.0,\n        \"coverPicFormat\":\"jpg\",\n        \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0.......\n    }\n]",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "沒有符合的商品",
          "content": "{\n   null\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "我的商品"
  },
  {
    "type": "GET",
    "url": "/my/products/key",
    "title": "根據關鍵字模糊查詢用戶商品",
    "name": "ShowMyProductsByKey",
    "group": "我的商品",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>根據輸入的關鍵字顯示用戶所有上傳過的商品</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "keyword",
            "description": "<p>關鍵字</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子\"考\")",
          "content": "[\n   {\n         \"status\":0,\n         \"name\":\"考研真相英語一\",\n         \"price\":12.0,\n         \"coverPicFormat\":\"jpg\",\n         \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgAAg....(base64編碼，原文過長不全部展示)\n   },\n    {\n        \"status\":0,\n        \"name\":\"二手自考教材04729大學語文2018版徐中玉陶型傳北京大學出版社\",\n        \"price\":14.66,\n        \"coverPicFormat\":\"jpg\",\n        \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBMRXhpZgAATU0AKgAAAAgAAg......\n    },\n    {\n        \"status\":0,\n        \"name\":\"國家教師資格考試\",\n        \"price\":15.0,\n        \"coverPicFormat\":\"jpg\",\n        \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0.......\n    }\n]",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "沒有符合的商品",
          "content": "{\n   null\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "我的商品"
  },
  {
    "type": "GET",
    "url": "/my/products",
    "title": "根據狀態顯示用戶商品",
    "name": "ShowMyProductsByStatus",
    "group": "我的商品",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>根據狀態顯示用戶上傳過的商品</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "status",
            "description": "<p>商品狀態</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(\"0\"的例子)",
          "content": "[\n   {\n         \"status\":0,\n         \"name\":\"考研真相英語一\",\n         \"price\":12.0,\n         \"coverPicFormat\":\"jpg\",\n         \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgAAg....(base64編碼，原文過長不全部展示)\n   },\n   {\n        \"status\":0,\n        \"name\":\"二手書\",\n        \"price\":8.0,\n        \"coverPicFormat\":\"jpg\",\n        \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXh.......\n   },\n   {\n        \"status\":0,\n        \"name\":\"思想道德與法治2021年版\",\n        \"price\":7.9,\n        \"coverPicFormat\":\"jpg\",\n        \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYR......\n    },\n    {\n        \"status\":0,\n        \"name\":\"中公小學教師資格證\",\n        \"price\":68.0,\n        \"coverPicFormat\":\"jpg\",\n        \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4Q......\n    },\n    {\n        \"status\":0,\n        \"name\":\"二手自考教材04729大學語文2018版徐中玉陶型傳北京大學出版社\",\n        \"price\":14.66,\n        \"coverPicFormat\":\"jpg\",\n        \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBMRXhpZgAATU0AKgAAAAgAAg......\n    },\n    {\n        \"status\":0,\n        \"name\":\"國家教師資格考試\",\n        \"price\":15.0,\n        \"coverPicFormat\":\"jpg\",\n        \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0.......\n    }\n]",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "沒有符合的商品",
          "content": "{\n   null\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "我的商品"
  },
  {
    "type": "PUT",
    "url": "/my/product",
    "title": "用戶更新商品信息",
    "name": "UpdateMyProduct",
    "group": "我的商品",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>用戶更新商品信息(目前沒有提供更新商品圖片的功能，若有需要請前端人員告訴我一聲，謝謝)</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "number",
            "description": "<p>商品編號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>商品名稱</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "storage",
            "description": "<p>庫存量</p>"
          },
          {
            "group": "Parameter",
            "type": "Double",
            "optional": false,
            "field": "price",
            "description": "<p>商品價格</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "address",
            "description": "<p>發貨地</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "intro",
            "description": "<p>商品描述</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子)",
          "content": "{\n   201\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "商品不存在/更新失敗",
          "content": "{\n   422\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "我的商品"
  },
  {
    "type": "post",
    "url": "/catalog",
    "title": "添加分類",
    "name": "AddCatalog",
    "group": "服務端:商品分類管理",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>添加分類</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "number",
            "description": "<p>分類編碼</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>分類名稱</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "添加成功釋例",
          "content": "{\n  201\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "添加失敗(分類編碼已存在)",
          "content": "{\n  422\n}",
          "type": "json"
        },
        {
          "title": "添加失敗(信息未成功添加)",
          "content": "{\n  404\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "服務端:商品分類管理"
  },
  {
    "type": "get",
    "url": "/catalogs",
    "title": "顯示所有分類",
    "name": "AllCatalog",
    "group": "服務端:商品分類管理",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>顯示所有分類</p>",
    "success": {
      "examples": [
        {
          "title": "請求成功釋例",
          "content": "[\n     {\"id\":1,\"name\":\"書籍\",\"number\":\"B\"},\n     {\"id\":2,\"name\":\"美妝\",\"number\":\"M\"},\n     {\"id\":3,\"name\":\"數碼\",\"number\":\"D\"},\n     {\"id\":4,\"name\":\"家居\",\"number\":\"F\"},\n     {\"id\":5,\"name\":\"電器\",\"number\":\"E\"},\n     {\"id\":6,\"name\":\"服裝\",\"number\":\"C\"},\n     {\"id\":7,\"name\":\"其它\",\"number\":\"O\"}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "服務端:商品分類管理"
  },
  {
    "type": "delete",
    "url": "/catalog",
    "title": "刪除分類",
    "name": "DeleteCatalog",
    "group": "服務端:商品分類管理",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>刪除分類，不建議直接刪除分類，因為很多商品都與分類掛勾，此功能只適用不小心添加了一個不想要的分類，提供刪除的手段。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "id",
            "description": "<p>分類序號</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "分類刪除成功",
          "content": "{\n  204\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "刪除失敗(沒有選中的對象，數據庫為發生改動)",
          "content": "{\n  400\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "服務端:商品分類管理"
  },
  {
    "type": "put",
    "url": "/catalog",
    "title": "更新分類信息",
    "name": "UpdateCatalog",
    "group": "服務端:商品分類管理",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>更新分類信息，這裡只許更動分類名稱，也就是name的部分，以面系統無法識別某些商品的分類而報錯。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "id",
            "description": "<p>分類序號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "number",
            "description": "<p>新的分類編碼</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>新的分類名稱</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "更新成功",
          "content": "{\n  800\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "更新失敗",
          "content": "{\n  808\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "服務端:商品分類管理"
  },
  {
    "type": "GET",
    "url": "/product/uploads",
    "title": "顯示所有商品上架請求",
    "name": "GetAllProductUploadRequests",
    "group": "服務端:商品管理",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>顯示所有商品上架請求，只顯示Outline</p>",
    "success": {
      "examples": [
        {
          "title": "請求成功(例子)",
          "content": "[\n   {\n        \"number\":\"C1669690298810\",\n        \"status\":1,\n        \"catalog\":\"C\",\n        \"name\":\"連衣裙\",\n        \"SPhone\":\"15023192020\",\n        \"date\":\"2022-11-29 10:51:38\"\n    },\n    {\n        \"number\":\"C1670300792286\",\n        \"status\":1,\n        \"catalog\":\"C\",\n        \"name\":\"複合面料衛衣(黑)\",\n        \"SPhone\":\"15080711348\",\n        \"date\":\"2022-12-06 12:26:32\"\n     }...{}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "服務端:商品管理"
  },
  {
    "type": "GET",
    "url": "/product/uploads/catalog",
    "title": "根據商品分類顯示所有商品上架請求",
    "name": "GetProductUploadRequestsByCatalog",
    "group": "服務端:商品管理",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>根據商品分類顯示所有商品上架請求，只顯示Outline</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "catalog",
            "description": "<p>商品分類(B, C, M, ...)</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子:C)",
          "content": "[\n   {\n        \"number\":\"C1669690298810\",\n        \"status\":1,\n        \"catalog\":\"C\",\n        \"name\":\"連衣裙\",\n        \"SPhone\":\"15023192020\",\n        \"date\":\"2022-11-29 10:51:38\"\n    },\n    {\n        \"number\":\"C1670300792286\",\n        \"status\":1,\n        \"catalog\":\"C\",\n        \"name\":\"複合面料衛衣(黑)\",\n        \"SPhone\":\"15080711348\",\n        \"date\":\"2022-12-06 12:26:32\"\n     }...{}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "服務端:商品管理"
  },
  {
    "type": "PUT",
    "url": "/product/upload",
    "title": "審核商品上架請求",
    "name": "ProcessProductUploadRequest",
    "group": "服務端:商品管理",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>針對某一條商品上架請求給出審查結果，該結果會由系統自動發松通知給用戶，用戶可在[設置]--&gt;[系統信息]查看通知。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "number",
            "description": "<p>商品編號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "decision",
            "description": "<p>判決結果(pass/reject)只能有這兩種</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "explain",
            "description": "<p>判決結果的說明</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "處理成功(例子)",
          "content": "{\n     201\n }",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "處理失敗",
          "content": "{\n   422\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "服務端:商品管理"
  },
  {
    "type": "GET",
    "url": "/product/upload",
    "title": "查看商品上架請求詳情",
    "name": "ReadProductUploadRequest",
    "group": "服務端:商品管理",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>查看商品上架請求詳情，查看完詳情後再給出審核結果。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "number",
            "description": "<p>商品編號</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子:C1669690298810)",
          "content": "{\n     \"number\":\"C1669690298810\",\n     \"name\":\"連衣裙\",\n     \"status\":1,\n     \"catalog\":\"C\",\n     \"sellerName\":\"田嘉淑(真名)\",\n     \"address\":\"重慶大學虎溪校區梅園3棟\",\n     \"date\":\"2022-11-29 10:51:38\",\n     \"price\":49.9,\n     \"storage\":1,\n     \"intro\":\"由於尺碼買小了 穿不了 全新 質量和版型都很好 看上的小姐姐加QQ喔(QQ號: 3446572877)\",\n     \"picture_count\":6,\n     \"pictureFormats\":[\"jpg\",\"jpg\",\"jpg\",\"jpg\",\"jpg\",\"jpg\"],\n     \"pictures\":\n         [\n             \"/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsKCwsNDhIQDQ4R....\",\n             ...,\n         ]\n }",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "服務端:商品管理"
  },
  {
    "type": "GET",
    "url": "/authentication/requests",
    "title": "顯示所有實名認證請求",
    "name": "GetAllAuthenticationRueqest",
    "group": "服務端:用戶管理",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>顯示所有未處理的實名認證請求</p>",
    "success": {
      "examples": [
        {
          "title": "請求成功(例子)",
          "content": "[\n    {\n         \"id\":7,\n         \"phone\":\"15049936157\",\n         \"realName\":\"楊單詞\",\n         \"date\":\"2022-12-09 09:39:39\",\n         \"status\":0\n     },\n     {\n         \"id\":8,\n         \"phone\":\"15013729832\",\n         \"realName\":\"鄧紫祺\",\n         \"date\":\"2022-12-09 09:40:44\",\n         \"status\":0\n     },\n     {\n         \"id\":10,\n         \"phone\":\"15083622395\",\n         \"realName\":\"陳八方\",\n         \"date\":\"2022-12-09 10:00:38\",\n         \"status\":0\n     }\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "服務端:用戶管理"
  },
  {
    "type": "GET",
    "url": "/authentication/requests/status",
    "title": "根據處理狀態顯示實名認證請求",
    "name": "GetAuthenticationRequestByStatus",
    "group": "服務端:用戶管理",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>根據處理狀態顯示實名認證請求</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "status",
            "description": "<p>處理狀態(0:未處理, 1:通過, 2: 不通過)</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子:0)",
          "content": "[\n     {\n          \"id\":7,\n          \"phone\":\"15049936157\",\n          \"realName\":\"楊單詞\",\n          \"date\":\"2022-12-09 09:39:39\",\n          \"status\":0\n      },\n      {\n          \"id\":8,\n          \"phone\":\"15013729832\",\n          \"realName\":\"鄧紫祺\",\n          \"date\":\"2022-12-09 09:40:44\",\n          \"status\":0\n      },\n      {\n          \"id\":10,\n          \"phone\":\"15083622395\",\n          \"realName\":\"陳八方\",\n          \"date\":\"2022-12-09 10:00:38\",\n          \"status\":0\n      }\n ]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "服務端:用戶管理"
  },
  {
    "type": "PUT",
    "url": "/authentication/request",
    "title": "處理實名認證請求",
    "name": "ProcessAuthenticationRequest",
    "group": "服務端:用戶管理",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>管理員在察看具體信息後給出判決decision(pass:通過, reject:不通過)，系統會自動對用戶信息做相應的更動，並發送通知告知結果。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "id",
            "description": "<p>實名認證請求id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "decision",
            "description": "<p>判決(pass/reject)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "explain",
            "description": "<p>管理員對判決給出相應的解釋</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "處理成功",
          "content": "{\n     201\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "用戶數據更新失敗/數據庫更新失敗",
          "content": "{\n     422\n}",
          "type": "json"
        },
        {
          "title": "圖片操作失敗",
          "content": "{\n     400\n}",
          "type": "json"
        },
        {
          "title": "請求格式錯誤(decision只能是pass或reject)",
          "content": "{\n     404\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "服務端:用戶管理"
  },
  {
    "type": "GET",
    "url": "/authentication/request",
    "title": "管理員閱讀實名認證請求",
    "name": "ReadAuthenticationRequest",
    "group": "服務端:用戶管理",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>管理員閱讀實名認證請求，並據此給出判決。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "id",
            "description": "<p>實名認證請求id</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子:10)",
          "content": "{\n     \"id\":10,\n     \"phone\":\"15083622395\",\n     \"date\":\"2022-12-09 10:00:38\",\n     \"realName\":\"陳八方\",\n     \"format\":\"jpg(圖片格式，顯示圖片用)\",\n     \"idCardPic\":\"iVBORw0KGgoAAAANSUhEUgAAAf8AAAH/CAYAAABZ8dS+AAAA...\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "服務端:用戶管理"
  },
  {
    "type": "GET",
    "url": "/comment/reports",
    "title": "獲取所有留言舉報請求",
    "name": "GetAllCommentReport",
    "group": "服務端:舉報審核",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>獲取所有留言舉報請求</p>",
    "success": {
      "examples": [
        {
          "title": "請求成功(例子)",
          "content": "[\n     {\n         \"id\":1,\n         \"status\":0,\n         \"reporterPhone\":\"15059417755\",\n         \"commentId\":\"1(舉報的留言編號)\",\n         \"date\":\"Dec 5, 2022, 4:47:48 PM\"\n     }...{}\n ]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "服務端:舉報審核"
  },
  {
    "type": "GET",
    "url": "/product/reports",
    "title": "獲取所有商品舉報請求",
    "name": "GetAllProductReport",
    "group": "服務端:舉報審核",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>獲取所有商品舉報請求</p>",
    "success": {
      "examples": [
        {
          "title": "請求成功(例子)",
          "content": "[\n     {\n         \"id\":1,\n         \"status\":1,\n         \"reporterPhone\":\"15059417755\",\n         \"number\":\"C1669690298810\",\n         \"date\":\"Dec 5, 2022, 4:47:48 PM\"\n     }...{}\n ]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "服務端:舉報審核"
  },
  {
    "type": "GET",
    "url": "/comment/reports/same",
    "title": "獲取針對同一則留言的舉報請求",
    "name": "GetCommentReportByCommentId",
    "group": "服務端:舉報審核",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>獲取針對同一則留言的舉報請求</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "commentId",
            "description": "<p>處理狀態</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子: 1)",
          "content": "[\n     {\n         \"id\":1,\n         \"status\":1,\n         \"reporterPhone\":\"15059417755\",\n         \"commentId\":\"1\",\n         \"date\":\"Dec 5, 2022, 4:47:48 PM\"\n     }...{}\n ]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "服務端:舉報審核"
  },
  {
    "type": "GET",
    "url": "/comment/reports/status",
    "title": "根據處理狀態獲取留言舉報請求",
    "name": "GetCommentReportByStatus",
    "group": "服務端:舉報審核",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>根據處理狀態獲取留言舉報請求(0:未處理, 1:舉報成立, 2:舉報不成立)</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "status",
            "description": "<p>處理狀態</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子: 1)",
          "content": "[\n     {\n         \"id\":1,\n         \"status\":1,\n         \"reporterPhone\":\"15059417755\",\n         \"commentId\":\"1\",\n         \"date\":\"Dec 5, 2022, 4:47:48 PM\"\n     }...{}\n ]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "服務端:舉報審核"
  },
  {
    "type": "GET",
    "url": "/product/status/reports",
    "title": "根據處理狀態獲取商品舉報請求",
    "name": "GetProductReportByStatus",
    "group": "服務端:舉報審核",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>根據處理狀態獲取商品舉報請求(0:未處理, 1:舉報成立, 2:舉報不成立)</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "status",
            "description": "<p>處理狀態</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子: 1)",
          "content": "[\n     {\n         \"id\":1,\n         \"status\":1,\n         \"reporterPhone\":\"15059417755\",\n         \"number\":\"C1669690298810\",\n         \"date\":\"Dec 5, 2022, 4:47:48 PM\"\n     }...{}\n ]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "服務端:舉報審核"
  },
  {
    "type": "PUT",
    "url": "/comment/report",
    "title": "處理留言舉報",
    "name": "ProcessCommentReport",
    "group": "服務端:舉報審核",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>管理員給出留言舉報的判決，在結果出來後系統會自動發送信息給相關用戶(舉報者/被舉報者)，用戶可在[設置]--&gt;[系統消息]中察看。對於違規留言系統會自動刪除該留言。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "id",
            "description": "<p>留言舉報序號id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "decision",
            "description": "<p>判決結果(pass/reject)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "explain",
            "description": "<p>判決說明</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "處理成功(例子)",
          "content": "{\n    201\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "處理失敗(數據庫更新失敗)",
          "content": "{\n   400\n}",
          "type": "json"
        },
        {
          "title": "處理失敗(請求不合法)",
          "content": "{\n   404\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "服務端:舉報審核"
  },
  {
    "type": "PUT",
    "url": "/product/report/result",
    "title": "處理商品舉報",
    "name": "ProcessProductReport",
    "group": "服務端:舉報審核",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>管理員給出商品舉報的判決，在結果出來後系統會自動發送信息給相關用戶(舉報者/被舉報者)，用戶可在[設置]--&gt;[系統消息]中察看。對於違規商品，若檢舉次數超過5次系統會做強制下架處理，沒有超過則會發消息通知賣家整改。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "id",
            "description": "<p>商品舉報序號id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "decision",
            "description": "<p>判決結果(pass/reject)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "explain",
            "description": "<p>判決說明</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "處理成功(例子)",
          "content": "{\n    201\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "處理失敗(數據庫更新失敗)",
          "content": "{\n   400\n}",
          "type": "json"
        },
        {
          "title": "處理失敗(請求不合法)",
          "content": "{\n   404\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "服務端:舉報審核"
  },
  {
    "type": "GET",
    "url": "/comment/report",
    "title": "閱讀留言舉報請求詳情",
    "name": "ReadCommentReport",
    "group": "服務端:舉報審核",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>閱讀完留言舉報的請求詳情後，在據此給出判決。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "id",
            "description": "<p>留言舉報序號id</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子: 1)",
          "content": "{\n    \"id\":1,\n    \"status\":1,\n    \"reporterPhone\":\"15059417755\",\n    \"reportName\":\"错霞飞(真名)\",\n    \"date\":\"Dec 5, 2022, 4:47:48 PM\",\n    \"content\":\"留言不友善\",\n    \"comment\": (這裡面放的是留言信息)\n         {\n             \"id\": 1,\n             \"father_id\":0,\n             \"number\":\"C1669690298810\",\n             \"content\":\"請問適合梨形身材嗎?\",\n             \"date\":\"Dec 05 11:57:25 CST 2022\",\n             \"phone\":\"15078818663\"\n         }\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "服務端:舉報審核"
  },
  {
    "type": "GET",
    "url": "/product/report",
    "title": "閱讀商品舉報請求詳情",
    "name": "ReadProductReport",
    "group": "服務端:舉報審核",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>閱讀完商品舉報的請求詳情後，在據此給出判決。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "id",
            "description": "<p>商品舉報序號id</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子: 1)",
          "content": "{\n    \"id\":1,\n    \"status\":1,\n    \"reporterPhone\":\"15059417755\",\n    \"reportName\":\"错霞飞(真名)\",\n    \"date\":\"Dec 5, 2022, 4:47:48 PM\",\n    \"content\":\"裙子嚴重脫線，洗一次基本就沒法穿了\",\n    \"productNumber\":\"C1669690298810\",\n    \"productName\":\"連衣裙\",\n    \"reportCount\":1(該商品的舉報次數)\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "服務端:舉報審核"
  },
  {
    "type": "POST",
    "url": "/comment/report",
    "title": "用戶舉報留言",
    "name": "UserSendCommentReport",
    "group": "用戶舉報",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>用戶舉報某一則留言，須為實名認證用戶。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "number",
            "description": "<p>留言序號id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶(舉報者)手機號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>舉報原因</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "舉報發送成功(例子)",
          "content": "{\n     201\n }",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "發送失敗(用戶無權限)",
          "content": "{\n   400\n}",
          "type": "json"
        },
        {
          "title": "發送失敗(數據庫未更新)",
          "content": "{\n   404\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "用戶舉報"
  },
  {
    "type": "POST",
    "url": "/product/report",
    "title": "用戶舉報商品",
    "name": "UserSendProductReport",
    "group": "用戶舉報",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>用戶舉報某一商品，暫時將此功能限制在: 唯有購買過該商品的用戶方可舉報。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "number",
            "description": "<p>商品編號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶(舉報者)手機號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>舉報原因</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "舉報發送成功(例子)",
          "content": "{\n     201\n }",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "發送失敗(用戶無權限)",
          "content": "{\n   400\n}",
          "type": "json"
        },
        {
          "title": "發送失敗(數據庫未更新)",
          "content": "{\n   422\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "用戶舉報"
  },
  {
    "type": "post",
    "url": "/user",
    "title": "用戶註冊",
    "name": "Register",
    "group": "用戶註冊",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>由用戶輸入手機號、郵箱及密碼來完成註冊，且一個手機號只能註冊一次，若手機號被重複註冊則會判定註冊失敗。 手機號須為11位手機號，其他型號本系統不予支持。所有前後端交互數據皆由UTF-8進行編碼與解碼。 為避免中英文混和而產生亂碼，數據庫除日期、純數字、純英文數據外，接先進行UTF-8編碼後存入數據庫，讀取時再進行相應的解碼顯示。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號(手機號不可重複註冊)</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": "<p>用戶想使用的密碼</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "email",
            "description": "<p>用戶郵箱帳號</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "註冊成功釋例",
          "content": "{\n  201\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "註冊失敗(手機號已存在)",
          "content": "{\n  422\n}",
          "type": "json"
        },
        {
          "title": "註冊失敗(信息未成功添加)",
          "content": "{\n  404\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "用戶註冊"
  },
  {
    "type": "GET",
    "url": "/setting/authentication",
    "title": "獲取用戶實名認證狀態",
    "name": "GetUserAuthenticationStatus",
    "group": "設置:實名認證",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>獲取用戶實名認證狀態</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "獲取成功(已通過)",
          "content": "{\n     201\n}",
          "type": "json"
        },
        {
          "title": "獲取成功(審核中)",
          "content": "{\n     202\n}",
          "type": "json"
        },
        {
          "title": "獲取成功(非實名認證用戶)",
          "content": "{\n     203\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "設置:實名認證"
  },
  {
    "type": "POST",
    "url": "/setting/authentication",
    "title": "發送實名認證請求",
    "name": "UserPostAuthenticationRequest",
    "group": "設置:實名認證",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>用戶申請實名認證功能。本系統實名認證審核的流程是: 用戶上傳身分信息--&gt;信息進入審核階段--&gt;審核成功/失敗--&gt;用戶權限更改/不更改。用戶可以在[設置]--&gt;[系統消息]察看結果。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "realName",
            "description": "<p>用戶真實姓名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "idNumber",
            "description": "<p>身分證號碼</p>"
          },
          {
            "group": "Parameter",
            "type": "File",
            "optional": false,
            "field": "idCardPic",
            "description": "<p>身分證照片(這些都用form-data傳就行)</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "信息上傳成功",
          "content": "{\n     201\n}",
          "type": "json"
        },
        {
          "title": "已是實名認證用戶",
          "content": "{\n     200\n}",
          "type": "json"
        },
        {
          "title": "已經發送過了，請耐心等待結果",
          "content": "{\n     203\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "用戶不存在/數據庫添加失敗",
          "content": "{\n     422\n}",
          "type": "json"
        },
        {
          "title": "圖片上傳失敗",
          "content": "{\n     404\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "設置:實名認證"
  },
  {
    "type": "GET",
    "url": "/setting/help/catalog/question",
    "title": "獲取對應問題解答",
    "name": "GetHelpAnswer",
    "group": "設置:幫助",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>用戶找到問題後，獲得問題的解答。</p>",
    "success": {
      "examples": [
        {
          "title": "成功(以\"為何上傳商品後沒有成功上架\"為例)",
          "content": "{\n     閒置重重的商品上架流程為: 用戶上傳商品-->管理員審核通過-->用戶商品上架。\n     可以在個人中心查看商品的審核進度，若顯示[審核中]請用戶耐心等待管理員審核結果，一般不會超過24h。\n     若顯示[已駁回]表示管理員判斷該商品有違規嫌疑，具體細節由系統發送通知說明。\n }",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失敗",
          "content": "{\n   null\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "設置:幫助"
  },
  {
    "type": "GET",
    "url": "/setting/help/catalog",
    "title": "獲取相應問題類型的問題",
    "name": "GetHelpQuestions",
    "group": "設置:幫助",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>用戶鎖定問題位置後，進一步找到問題。</p>",
    "success": {
      "examples": [
        {
          "title": "成功返回問相應類型的問題(以\"商品上傳\"為例)",
          "content": "[\n    {\n         \"index\":\"0\",\n         \"catalog\":\"如何上傳商品\"\n    },\n    {\n         \"index\":\"1\",\n         \"catalog\":\"為何上傳商品後沒有成功上架\"\n    }\n ]",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失敗",
          "content": "{\n   null\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "設置:幫助"
  },
  {
    "type": "GET",
    "url": "/setting/help",
    "title": "獲取幫助問題的類型",
    "name": "GetQuestionCatalogs",
    "group": "設置:幫助",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>獲取幫助問題的類型，用戶可通過問題類型鎖定問題位置。</p>",
    "success": {
      "examples": [
        {
          "title": "成功返回問題類型(例子)",
          "content": "[\n    {\n         \"index\":\"0\",\n         \"catalog\":\"商品上傳\"\n    },\n    {\n         \"index\":\"1\",\n         \"catalog\":\"關於\"\n    }\n ]",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失敗",
          "content": "{\n   null\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "設置:幫助"
  },
  {
    "type": "GET",
    "url": "/setting/notification/isRead",
    "title": "判斷某消息是否已讀",
    "name": "CheckNotificationIsRead",
    "group": "設置:用戶反饋",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>判斷某消息是否已讀，可做為圖標提示時使用(例如未讀消息上方有小紅點)。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "id",
            "description": "<p>信息序號id</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子:3)",
          "content": "{\n    false\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "設置:用戶反饋"
  },
  {
    "type": "GET",
    "url": "/setting/notifications",
    "title": "顯示所有系統信息",
    "name": "GetAllNotification",
    "group": "設置:用戶反饋",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>顯示所有系統信息，這裡只顯示Outline，status表示是否已讀(0:未讀, 1:已讀)。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子:15023192020)",
          "content": "[\n    {\n         \"id\":3,\n         \"type\":\"warn\",\n         \"title\":\"商品舉報通知\",\n         \"date\":\"Dec 5, 2022, 6:07:20 PM\",\n         \"status\":0\n     },...,{}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "設置:用戶反饋"
  },
  {
    "type": "GET",
    "url": "/setting/notifications/unread",
    "title": "顯示所有未讀系統信息",
    "name": "GetUnreadNotification",
    "group": "設置:用戶反饋",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>顯示所有未讀系統信息</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子:15023192020)",
          "content": "[\n    {\n         \"id\":3,\n         \"type\":\"warn\",\n         \"title\":\"商品舉報通知\",\n         \"date\":\"Dec 5, 2022, 6:07:20 PM\",\n         \"status\":0\n     },...,{}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "設置:用戶反饋"
  },
  {
    "type": "DELETE",
    "url": "/setting/notification",
    "title": "用戶刪除系統消息",
    "name": "UserDeleteNotification",
    "group": "設置:用戶反饋",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>用戶刪除一則系統消息</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "id",
            "description": "<p>信息序號id</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "刪除成功(例子)",
          "content": "{\n     204\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "刪除失敗",
          "content": "{\n   422\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "設置:用戶反饋"
  },
  {
    "type": "DELETE",
    "url": "/setting/notifications",
    "title": "刪除所有已讀系統消息",
    "name": "UserDeleteReadNotifications",
    "group": "設置:用戶反饋",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>用戶刪除所有已讀的系統消息</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "刪除成功(例子)",
          "content": "{\n     204\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "刪除失敗",
          "content": "{\n   422\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "設置:用戶反饋"
  },
  {
    "type": "GET",
    "url": "/setting/notification",
    "title": "用戶閱讀系統消息",
    "name": "UserReadNotification",
    "group": "設置:用戶反饋",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>用戶閱讀系統消息，系統會返回消息詳情，並將該消息閱讀狀態設置成已讀。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "id",
            "description": "<p>信息序號id</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子:3)",
          "content": "{\n     \"id\":3,\n     \"type\":\"warn\",\n     \"title\":\"商品舉報通知\",\n     \"to\":\"天天15023192020\",\n     \"content\":\"本系統非常遺憾的通知您，您的商品 [連衣裙] 已被系統管理確認存在違規行為，請及時處理以免商品被迫下架。謝謝您的配合。\",\n     \"date\":\"Dec 5, 2022, 6:07:20 PM\",\n     \"from\":\"系统管理员\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "設置:用戶反饋"
  },
  {
    "type": "POST",
    "url": "/setting/feedback",
    "title": "用戶反饋",
    "name": "WriteFeedback",
    "group": "設置:用戶反饋",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>用戶反饋功能</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>用戶手機號</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>反饋內容</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "發送成功",
          "content": "{\n   200\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "錯誤用戶",
          "content": "{\n   400\n}",
          "type": "json"
        },
        {
          "title": "找不到檔案/讀寫錯誤/路徑創建失敗",
          "content": "{\n   422\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "設置:用戶反饋"
  },
  {
    "type": "GET",
    "url": "/evaluations",
    "title": "根據賣家顯示其商品評價",
    "name": "GetEvaluationsBySeller",
    "group": "賣家信息主頁",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>根據賣家顯示其商品評價</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "sellerPhone",
            "description": "<p>賣家手機號</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子:非匿名評價)",
          "content": "[\n     {\n         \"number\":\"(商品編號)\",\n         \"productName\":\"(商品名稱)\",\n         \"date\":\"(評價日期)\",\n         \"score1\":(描述相符得分),\n         \"score2\":(物流服務得分),\n         \"score3\":(服務態度得分),\n         \"evaluate\":\"(文字評價)\",\n         \"isAnonymous\":false,\n         \"buyer\": {\n             \"buyerPhone\":\"(買家手機號)\",\n             \"buyerName\":\"(買家用戶名)\",\n             \"buyerHeadPic\":\"(買家頭像)\"\n         }\n     },....{}\n ]",
          "type": "json"
        },
        {
          "title": "請求成功(例子:匿名評價)",
          "content": "[\n      {\n          \"number\":\"(商品編號)\",\n          \"productName\":\"(商品名稱)\",\n          \"date\":\"(評價日期)\",\n          \"score1\":(描述相符得分),\n          \"score2\":(物流服務得分),\n          \"score3\":(服務態度得分),\n          \"evaluate\":\"(文字評價)\",\n          \"isAnonymous\":ture\n      },....{}\n  ]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "賣家信息主頁"
  },
  {
    "type": "GET",
    "url": "/seller/info",
    "title": "根據賣家顯示其基本信息(公開)",
    "name": "GetSellerBasicInfo",
    "group": "賣家信息主頁",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>根據賣家顯示其可公開的基本信息</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>賣家手機號</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(例子:非匿名評價)",
          "content": "{\n    \"phone\":\"(用戶手機號)\",\n    \"email\":\"(用戶郵箱)\",\n    \"userName\":\"(用戶名)\",\n    \"tradeCount\":(歷史交易數，只當賣家的交易數，買東西的不算),\n    \"format\":\"jpg(用戶頭像文件格式)\",\n    \"headPic\":\"(用戶頭像base64編碼)\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "賣家信息主頁"
  },
  {
    "type": "GET",
    "url": "/seller/grade",
    "title": "根據賣家顯示其系統評分",
    "name": "GetSystemSellerGrade",
    "group": "賣家信息主頁",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>根據賣家顯示其商品評價，此功能以(用戶違規數/用戶商品評價得分/用戶商品舉報數)三個數值進行加權計算獲取得分，滿分為10分。</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>賣家手機號</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "請求成功(返回Double)",
          "content": "{\n    10.00\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/example.js",
    "groupTitle": "賣家信息主頁"
  }
] });
