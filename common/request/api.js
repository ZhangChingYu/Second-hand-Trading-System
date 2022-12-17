import request from './request.js'


// 登陆
export const login = () => request.post('/login',{username:'15059417755',password:'c215mk4b'})

//获取审核商品列表
export const getReviewProducts = () => request.get('/')

//获取商品类别
export const getCatalog = () => request.get('/catalogs')

//新增商品类别
export const addCatalog = (data) => request.post('/catalog',data)

//更新商品类别
export const updateCatalog = (data) => request.put('/catalog',data)

//删除商品类别
export const removeCatalog = (data) => request.del('/catalog',data)




