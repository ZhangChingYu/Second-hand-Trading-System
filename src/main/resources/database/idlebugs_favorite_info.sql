create table favorite_info
(
    id     int auto_increment comment '序號'
        primary key,
    number varchar(255) not null comment '商品編號',
    phone  varchar(255) not null comment '用戶手機號'
);

INSERT INTO idlebugs.favorite_info (id, number, phone) VALUES (1, 'B1667557259691', '15078818663');
INSERT INTO idlebugs.favorite_info (id, number, phone) VALUES (2, 'M1667561271483', '15078818663');
