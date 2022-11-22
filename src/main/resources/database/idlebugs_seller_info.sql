create table seller_info
(
    id    int  auto_increment comment '序號'
        primary key,
    number varchar(255) not null comment '订单編號',
    phone  varchar(255) not null comment '手机号',
    address  varchar(255) comment '发货地址',
    shipping_time  datetime  comment '发货时间'
);