create table authentication
(
    id        int auto_increment
        primary key,
    phone     varchar(255) not null comment '用戶手機號',
    real_name varchar(255) not null comment '真實姓名(需與證件照上一致)',
    id_number varchar(255) not null comment '身分證號',
    date      datetime     not null,
    status    int          not null comment '處理狀態(0:沒處理, 1: pass, 2: reject)',
    constraint authentication_id_uindex
        unique (id)
);

INSERT INTO idlebugs.authentication (id, phone, real_name, id_number, date, status) VALUES (5, '15083622395', 'E999B3E585ABE696B9', '6584657463487328947', '2022-12-08 19:27:13', 1);
INSERT INTO idlebugs.authentication (id, phone, real_name, id_number, date, status) VALUES (7, '15049936157', 'E6A58AE596AEE8A99E', '6584657463487328947', '2022-12-09 09:39:39', 0);
INSERT INTO idlebugs.authentication (id, phone, real_name, id_number, date, status) VALUES (8, '15013729832', 'E984A7E7B4ABE7A5BA', '6584657463487328947', '2022-12-09 09:40:44', 2);
INSERT INTO idlebugs.authentication (id, phone, real_name, id_number, date, status) VALUES (9, '15083622395', 'E999B3E585ABE696B9', '6584657463487328947', '2022-12-09 09:58:17', 1);
INSERT INTO idlebugs.authentication (id, phone, real_name, id_number, date, status) VALUES (10, '15083622395', 'E999B3E585ABE696B9', '6584657463487328947', '2022-12-09 10:00:38', 1);
