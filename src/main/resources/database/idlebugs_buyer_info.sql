create table buyer_info
(
    id    int    auto_increment comment '序号'
        primary key,
    number varchar(255) not null comment '订单編号',
    phone  varchar(255) not null comment '手机号',
    address  varchar(255) not null comment '收货地址',
    payment_time  datetime  null comment '付款时间',
    receipt_time  datetime  null comment '收货时间',
    price  varchar(255)  null comment '备注'
);