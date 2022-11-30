create table buyer_info
(
    id    int    auto_increment comment '序号'
        primary key,
    exchange_id varchar(255) not null comment '订单编号 外键',
    phone  varchar(255) not null comment '手机号',
    address  varchar(255) charset utf8 not null  comment '收货地址',
    receipt_time  datetime  null comment '收货时间',
    refund_time   datetime null comment '退款时间',
    remark  varchar(1000)  charset utf8 null comment '备注'
);