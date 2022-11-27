create table booking
(
    id    int    auto_increment comment  '序号'
        primary key,
    seller_id int not null comment '卖家id',
    buyer_id  int not null comment '买家id',
    product_id  int not null comment '商品id',
    status varchar(255) not null comment '预约状态'
);