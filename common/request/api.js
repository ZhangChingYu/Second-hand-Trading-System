import request from './request.js'


// 登陆
export const login = () => request.post('/login',{usename:'15059417755',password:'c215mk4b'})

//获取商品类别
export const getCatalog = () => request.get('/catalog/catalogs')

//获取审核商品列表
export const getReviewProducts = () => request.get('/')





