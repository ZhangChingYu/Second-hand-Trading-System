create table exchange_info
(
    id     int auto_increment comment '序號'
        primary key,
    number varchar(255) not null comment '订单編號',
    product_num  varchar(255) not null comment '商品编号',
    orders_num  int not null comment '商品数量',
    price  decimal(10, 2) not null comment '成交价格',
    express_delivery varchar(255)  null comment '快递方式',
    delivery_id varchar(255)  null comment '快递单号',
    freight   decimal(10, 2)  null comment '运费',
    discounts   decimal(10, 2)  null comment '运费',
    build_time  datetime not null comment '订单生成时间',
    payment varchar(255)  null comment '支付方式',
    status  int not null comment '订单状态
    0：待发货
    1：待收货
    2：已收货、待评价，订单已完成
    3：等待退款
    4：退款成功/售后
    5：已评价'
);
--订单数据库