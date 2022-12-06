create table seller_info
(
    id            int auto_increment comment '序號'
        primary key,
    exchange_id   varchar(255)              not null comment '订单编号  订单编号 外键',
    phone         varchar(255)              not null comment '手机号',
    address       varchar(255) charset utf8 null comment '发货地址',
    shipping_time datetime                  null comment '发货时间',
    refund_time   datetime                  null comment '退款时间'
);

