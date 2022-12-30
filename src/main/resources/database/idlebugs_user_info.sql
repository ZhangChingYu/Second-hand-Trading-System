create table user_info
(
    id              int auto_increment comment '用戶編號'
        primary key,
    user_name       varchar(255) charset utf8   null comment '用戶名',
    phone           varchar(255) charset utf8   not null comment '手機號',
    password        varchar(255)                not null comment '密碼',
    authority       int                         not null comment '權限
0: 已通過實名認證
1: 未通過實名認證
2: 違規用戶',
    real_name       varchar(255) charset utf8   null comment '真實姓名',
    id_card         varchar(255)                null comment '身分證號',
    stu_num         varchar(255)                null comment '學號',
    register_date   datetime                    not null comment '註冊時間',
    violation_count int                         null comment '違規次數',
    default_address varchar(255) charset utf8   null comment '默認地址',
    address         varchar(10000) charset utf8 null comment '備用地址1',
    email           varchar(255) charset utf8   null comment '郵箱帳號',
    avatar          varchar(255)                null default null comment '头像',
    picture         varchar(255)                null default null comment '实名认证图片',
    is_online       int                         null default null COMMENT '是否在线
    0:不在
    1:在线'
);

INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_address, address, email, avatar, picture,is_online) VALUES (3, '4D656E67204B776F6B2057696E67', '15049936157', 'lKpJ5CnBry', 1, 'E8B4B9E69BBCE69687', '', 'fGsUVuXncI', '2020-09-10 05:09:56', 0, '', '', '12345678910@qq.com', '', '',0);
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_address, address, email, avatar, picture,is_online) VALUES (4, '416E647265612057617368696E67746F6E', '15013729832', 'ei3bpRMxWq', 1, 'E5B0A7E5BC98E79B8A', '', '5A3gLrKW8L', '2006-11-02 17:41:18', 0, '', '', '12345678910@qq.com', '', '',0);
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_address, address, email, avatar, picture,is_online) VALUES (5, '4D616F204A69616C756E', '15057505703', '4DmkewSQq6', 1, 'E68BB1E8949AE784B6', '', '8A7gPjTpN4', '2022-07-23 21:22:36', 1, '', '', '12345678910@qq.com', '', '',0);
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_address, address, email, avatar, picture,is_online) VALUES (6, '5A6F752058697579696E67', '15083622395', 'clYE3F1KSz', 0, '453939394233453538354142453639364239', '', '8L3nfBophu', '2008-03-11 21:12:51', 0, '', '', '12345678910@qq.com', '', '',0);
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_address, address, email, avatar, picture,is_online) VALUES (7, '4A616D69652053616E64657273', '15084266017', 'VO8p848fwA', 1, 'E6BA90E9AA8FE5A587', '', 'r38LEjbzee', '2016-01-18 17:04:45', 3, '', '', '12345678910@qq.com', '', '',0);
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_address, address, email, avatar, picture,is_online) VALUES (8, 'E5A4A9E5A4A9', '15059417755', 'c215mk4b', 0, 'E99499E99C9EE9A39E', null, null, '2022-10-27 16:47:21', null, '', '', '12345678910@qq.com', '', '',0);
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_address, address, email, avatar, picture,is_online) VALUES (9, 'E5A4A9E5A4A9', '15023192020', '27odsa2y8', 0, 'E794B0E59889E6B791', null, null, '2022-11-04 19:07:55', null, null, null, '15023192020@qq.com', '', '',0);
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_address, address, email, avatar, picture,is_online) VALUES (10, 'E5A4A9E5A4A9', '15023591218', '9j3nv1jm', 0, 'E88892E9B8BFE7BEBD', null, null, '2022-11-04 19:41:09', null, null, null, '15023591218@qq.com', '', '',0);
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_address, address, email, avatar, picture,is_online) VALUES (11, 'E5A4A9E5A4A9', '15083729338', '28jv0m72s8s', 0, 'E58DA0E5AD90E69882', null, null, '2022-11-05 13:39:27', null, null, null, '15083729338@qq.com', '', '',0);
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_address, address, email, avatar, picture,is_online) VALUES (12, 'E5A4A9E5A4A9', '15080711348', 'iev93kja82j', 0, 'E8B4BAE9A9B0E8BDA9', null, null, '2022-11-05 14:28:47', null, null, null, '15080711348@qq.com', '', '',0);
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_address, address, email, avatar, picture,is_online) VALUES (13, '4A616D69652053616E64657273', '15078818663', 'tony1234', 0, 'E5AD9DE5BF97E6988E', null, null, '2022-11-22 10:28:13', null, 'E596B5E89684E88DB725313530373838313836363325E9878DE685B6E5B8822DE9878DE685B6E5B88225E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E69DBEE59C92E4BA94E6A39F313032E5AEA4', 'E5A4A7E9A0ADE58592E5AD9025313530373838313836363325E9878DE685B6E5B8822DE9878DE685B6E5B88225E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E69DBEE59C92E4BA94E6A39F313032E5AEA423E781ABE7AEADE99A8A25313530373838313836363325E9878DE685B6E5B8822DE9878DE685B6E5B88225E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E69DBEE59C92E4BA94E6A39F313032E5AEA423E5AFB6E58FAFE5A4A2E5A4A7E5B8AB25313530373838313836363325E9878DE685B6E5B8822DE9878DE685B6E5B88225E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E69DBEE59C92E4BA94E6A39F313032E5AEA423', '', '', '',0);
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_address, address, email, avatar, picture,is_online) VALUES (14, 'E99D92E88AB1E793B7', '15168465388', 'oMztQt8nPn2G', 0, null, null, null, '2022-12-26 13:10:07', null, null, null, '15168465388@qq.com', null, null,0);
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_address, address, email, avatar, picture,is_online) VALUES (15, 'E7979BE5BF83E796BEE9A696', '14774347509', 'VyxDFx43feqH', 0, null, null, null, '2022-12-26 13:12:17', null, null, null, '14774347509@qq.com', null, null,0);
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_address, address, email, avatar, picture,is_online) VALUES (16, 'E682B2E4BCA4E79A84E7BB93E5B180', '18366648704', 'gqMUJwn0BNuY', 0, null, null, null, '2022-12-26 13:13:35', null, null, null, '18366648704@qq.com', null, null,0);
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_address, address, email, avatar, picture,is_online) VALUES (17, 'E4B88BE4B880E6ACA1E79A84E788B1E68385', '13794778207', 'hHnxNdkeUZi2', 0, null, null, null, '2022-12-26 13:14:30', null, null, null, '13794778207@qq.com', null, null,0);
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_address, address, email, avatar, picture,is_online) VALUES (18, 'E8A681E4BFA1E4BBBBE8B081', '13326415967', 'AmiitLXGPoqc', 0, null, null, null, '2022-12-26 13:15:12', null, null, null, '13326415967@qq.com', null, null,0);
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_address, address, email, avatar, picture,is_online) VALUES (19, 'E7A6BBE68891E8808CE58EBB', '16566219259', 'g0nQd2iR5Jpo', 0, null, null, null, '2022-12-26 13:15:45', null, null, null, '16566219259@qq.com', null, null,0);
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_address, address, email, avatar, picture,is_online) VALUES (20, 'E4B880E79CBCE8AFAFE7BB88E7949F', '13869781669', 'RoGlsDDP2aV2', 0, null, null, null, '2022-12-26 13:17:05', null, null, null, '13869781669@qq.com', null, null,0);
