create table favorite_info
(
    id     int auto_increment comment '序號'
        primary key,
    number varchar(255) not null comment '商品編號',
    phone  varchar(255) not null comment '用戶手機號'
);

