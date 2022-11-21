define({ "api": [
  {
    "type": "post",
    "url": "/catalog",
    "title": "添加分類",
    "name": "AddCatalog",
    "group": "商品分類管理",
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
    "filename": "Src/example.js",
    "groupTitle": "商品分類管理"
  },
  {
    "type": "get",
    "url": "/catalog/catalogs",
    "title": "顯示所有分類",
    "name": "AllCatalog",
    "group": "商品分類管理",
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
    "filename": "Src/example.js",
    "groupTitle": "商品分類管理"
  },
  {
    "type": "delete",
    "url": "/catalog",
    "title": "刪除分類",
    "name": "DeleteCatalog",
    "group": "商品分類管理",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>刪除分類</p>",
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
    "filename": "Src/example.js",
    "groupTitle": "商品分類管理"
  },
  {
    "type": "put",
    "url": "/catalog",
    "title": "更新分類信息",
    "name": "UpdateCatalog",
    "group": "商品分類管理",
    "permission": [
      {
        "name": "none"
      }
    ],
    "description": "<p>更新分類信息</p>",
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
    "filename": "Src/example.js",
    "groupTitle": "商品分類管理"
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
          "content": "[\n   {\n     \"number\":\"B1667559776586\",\n     \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)\n     \"name\":學生心理健康教育第二版\",\n     \"price\":7.9\n   },\n     ....,{}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "Src/example.js",
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
          "content": "[\n   {\n     \"number\":\"B1667559776586\",\n     \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)\n     \"name\":學生心理健康教育第二版\",\n     \"price\":7.9\n   },\n     ....,{}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "Src/example.js",
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
    "filename": "Src/example.js",
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
    "filename": "Src/example.js",
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
          "content": "[\n   {\n     \"number\":\"B1667559776586\",\n     \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)\n     \"name\":學生心理健康教育第二版\",\n     \"price\":7.9\n   },\n     ....,{}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "Src/example.js",
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
    "filename": "Src/example.js",
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
          "content": "[\n   {\n     \"number\":\"B1667559776586\",\n     \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)\n     \"name\":學生心理健康教育第二版\",\n     \"price\":7.9\n   },\n     ....,{}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "Src/example.js",
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
          "content": "[\n   {\n     \"number\":\"B1667559776586\",\n     \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)\n     \"name\":學生心理健康教育第二版\",\n     \"price\":7.9\n   },\n     ....,{}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "Src/example.js",
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
          "content": "{\n   \"name\":\"大學生心理健康教育第二版\",\n   \"seller_name\":\"天天\",\n   \"address\":\"重慶大學虎溪校區竹園3棟\",\n   \"date\":\"2022-11-04 19:02:56\",\n   \"price\":7.9,\n   \"intro\":\"全新沒有用過，到貨前就退課了\",\n   \"like_count\":0,\n   \"picture_count\":1,\n   \"pictures\":[\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAA.....(base64編碼，原文過長不全部展示)\",...,\"...\"]\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "Src/example.js",
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
          "content": "[\n   {\n     \"number\":\"B1667559776586\",\n     \"coverPic\":\"/9j/4AAQSkZJRgABAQAASABIAAD/4QBYRXhpZgAATU0AKgAAAAgA...(base64編碼，原文過長不全部展示)\n     \"name\":學生心理健康教育第二版\",\n     \"price\":7.9\n   },\n     ....,{}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "Src/example.js",
    "groupTitle": "商品查詢"
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
    "filename": "Src/example.js",
    "groupTitle": "用戶註冊"
  }
] });