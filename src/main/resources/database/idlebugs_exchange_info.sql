create table exchange_info
(
    id                  int auto_increment      comment  '序號'
            primary key,
    number              varchar(255)            not null comment '订单編號',
    product_num         varchar(255)            not null comment '商品编号',
    name                varchar(500) charset utf8  not null comment '商品名稱',
    orders_num          int                     not null comment '商品数量',
    price               decimal(10, 2)          not null comment '成交价格',
    express_delivery    varchar(255) charset utf8 null comment '快递方式',
    delivery_id         varchar(255)            null comment '快递单号',
    freight             decimal(10, 2)          null comment '运费',
    discounts           decimal(10, 2)          null comment '优惠',
    build_time          datetime                not null comment '订单生成时间',
    payment             varchar(255)   charset utf8    null comment '支付方式',
    is_delete           int                     not null   comment   '是否删除订单
    0:未删除
    1，seller已删除
    2：buyer已删除',
    status  varchar(255)   charset utf8         not null comment '订单状态
    待发货
    待收货
    已购买
    待退款
    已退款'
);

