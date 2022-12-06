create table booking
(
    id               int auto_increment comment '序号'
        primary key,
    number           varchar(255)   not null comment '预约编号',
    seller_id        varchar(255)   not null comment '卖家phone',
    buyer_id         varchar(255)   not null comment '买家phone',
    product_id       varchar(255)   not null comment '商品编号',
    orders_num       int            not null comment '预约商品数量',
    price            decimal(10, 2) not null comment '预约商品价格',
    appointment_time datetime       null comment '预约时间',
    confirm_time     datetime       null comment '预约确认时间',
    status           int            not null comment '预约状态
    0:已预约
    1:待下单
    2:待发货
    3:待收货
    //4:已收货
    5:买家取消预约
    6:已退款
    7:卖家拒绝预约'
);

