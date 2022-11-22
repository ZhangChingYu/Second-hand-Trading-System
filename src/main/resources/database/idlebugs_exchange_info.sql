create table exchange_info
(
    id     int auto_increment comment '序號'
        primary key,
    number varchar(255) not null comment '订单編號',
    name  varchar(255) not null comment '商品名称',
    product_num  varchar(255) not null comment '商品编号',
    orders_num  int not null comment '商品数量',
    price  varchar(255) not null comment '成交价格',
    build_time  datetime not null comment '订单生成时间',
    state  int not null comment '订单状态
    0：待付款
    1：待发货
    2：待收货
    3：已收货、待评价
    4：退款/售后'
);