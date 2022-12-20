import request from './request.js'


// 登陆
export const login = () => request.post('/login',{username:'15059417755',password:'c215mk4b'})

//获取商品类别
export const getCatalog = () => request.get('/catalogs')

//新增商品类别
export const addCatalog = (data) => request.post('/catalog',data)

//更新商品类别
export const updateCatalog = (data) => request.put('/catalog',data)

//删除商品类别
export const removeCatalog = (data) => request.del('/catalog',data)

//查询所有商品上架列表
export const allUpload = () => request.get('/product/uploads')

//查询某类别商品上架列表
export const catalogUpload = (data) => request.get('/product/uploads/catalog',data)

//查看商品上架请求详情
export const uploadDetail = (data) => request.get('/product/upload',data)

//提交商品上架请求
export const submitUpload = (data) => request.put('/product/upload',data)

//获取所有商品
export const getAllProducts = () => request.get('/products')

//分类获取商品
export const getCatalogProducts = (data) => request.get('/catalog/products',data)

