create table booking
(
    id               int auto_increment comment '序号'
        primary key,
    number           varchar(255)   not null comment '预约编号',
    seller_id        varchar(255)   not null comment '卖家phone',
    buyer_id         varchar(255)   not null comment '买家phone',
    product_id       varchar(255)   not null comment '商品编号',
    name             varchar(500) charset utf8  not null comment '商品名稱',
    orders_num       int            not null comment '预约商品数量',
    price            decimal(10, 2) not null comment '预约商品价格',
    appointment_time datetime       null comment '预约时间',
    confirm_time     datetime       null comment '预约确认时间',
    status           int            not null comment '预约状态
    已预约
    待下单
    已拒绝'
);

