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
    status           varchar(255)   charset utf8            not null comment '预约状态
    已预约
    待下单
    已拒绝'
);
INSERT INTO idlebugs.booking
(id, `number`, seller_id, buyer_id, product_id, name, orders_num, price, appointment_time, confirm_time, status)
VALUES(1, 'YY2022121019073693091', '15059417755', '15049936157', 'B1667557259691', 'E88083E7A094E79C9FE79BB8E88BB1E8AA9EE4B880', 1, 12.00, '2022-12-10 19:07:31', NULL, 'E5B7B2E9A284E7BAA6');
INSERT INTO idlebugs.booking
(id, `number`, seller_id, buyer_id, product_id, name, orders_num, price, appointment_time, confirm_time, status)
VALUES(2, 'YY2022121019204071549', '15059417755', '15013729832', 'B1667557541428', 'E4BA8CE6898BE69BB8', 1, 12.00, '2022-12-10 19:20:50', NULL, 'E5B7B2E9A284E7BAA6');
INSERT INTO idlebugs.booking
(id, `number`, seller_id, buyer_id, product_id, name, orders_num, price, appointment_time, confirm_time, status)
VALUES(3, 'YY2022121019223577028', '15059417755', '15013729832', 'B1667557541428', 'E4BA8CE6898BE69BB8', 1, 8.00, '2022-12-10 19:22:01', NULL, 'E5B7B2E9A284E7BAA6');
INSERT INTO idlebugs.booking
(id, `number`, seller_id, buyer_id, product_id, name, orders_num, price, appointment_time, confirm_time, status)
VALUES(4, 'YY2022121019223576028', '15059417755', '15057505703', 'B1667557719337', 'E6809DE683B3E98193E5BEB7E88887E6B395E6B2BB32303231E5B9B4E78988', 1, 7.90, '2022-12-10 19:22:01', NULL, 'E5B7B2E9A284E7BAA6');
INSERT INTO idlebugs.booking
(id, `number`, seller_id, buyer_id, product_id, name, orders_num, price, appointment_time, confirm_time, status)
VALUES(5, 'YY2022121019223577027', '15059417755', '15057505703', 'B1667558776457', 'E4BA8CE6898BE887AAE88083E69599E69D903034373239E5A4A7E5ADB8E8AA9EE6968732303138E78988E5BE90E4B8ADE78E89E999B6E59E8BE582B3E58C97E4BAACE5A4A7E5ADB8E587BAE78988E7A4BE', 2, 29.32, '2022-12-10 19:22:01', '2022-12-10 19:45:47', 'E5B7B2E68B92E7BB9D');
INSERT INTO idlebugs.booking
(id, `number`, seller_id, buyer_id, product_id, name, orders_num, price, appointment_time, confirm_time, status)
VALUES(6, 'YY2022121019223577018', '15059417755', '15023192020', 'B1667557940336', 'E4B8ADE585ACE5B08FE5ADB8E69599E5B8ABE8B387E6A0BCE8AD89', 5, 340.00, '2022-12-10 19:22:01', NULL, 'E5B7B2E9A284E7BAA6');
INSERT INTO idlebugs.booking
(id, `number`, seller_id, buyer_id, product_id, name, orders_num, price, appointment_time, confirm_time, status)
VALUES(7, 'YY2022121019223577228', '15059417755', '15084266017', 'B1667559081116', 'E59C8BE5AEB6E69599E5B8ABE8B387E6A0BCE88083E8A9A6', 1, 15.00, '2022-12-10 19:22:01', NULL, 'E5B7B2E9A284E7BAA6');
INSERT INTO idlebugs.booking
(id, `number`, seller_id, buyer_id, product_id, name, orders_num, price, appointment_time, confirm_time, status)
VALUES(8, 'YY2022121019223577128', '15059417755', '15083622395', 'B1667559211912', 'E5A4A7E5ADB8E88BB1E8AA9E33', 1, 10.00, '2022-12-10 19:22:01', NULL, 'E5B7B2E9A284E7BAA6');

