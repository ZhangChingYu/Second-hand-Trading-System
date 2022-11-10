create table user_info
(
    id              int auto_increment comment '用戶編號'
        primary key,
    user_name       varchar(255) charset utf8 null comment '用戶名',
    phone           varchar(255) charset utf8 not null comment '手機號',
    password        varchar(255)              not null comment '密碼',
    authority       int                       not null comment '權限
0: 已通過實名認證
1: 未通過實名認證
2: 違規用戶',
    real_name       varchar(255) charset utf8 null comment '真實姓名',
    id_card         varchar(255)              null comment '身分證號',
    stu_num         varchar(255)              null comment '學號',
    register_date   datetime                  not null comment '註冊時間',
    violation_count int                       null comment '違規次數',
    default_addr    varchar(255) charset utf8 null comment '默認地址',
    addr_one        varchar(255) charset utf8 null comment '備用地址1',
    addr_two        varchar(255) charset utf8 null comment '備用地址2',
    addr_three      varchar(255) charset utf8 null comment '備用地址3',
    email           varchar(255) charset utf8 null comment '郵箱帳號'
);

INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_addr, addr_one, addr_two, addr_three, email) VALUES (3, '4D656E67204B776F6B2057696E67', '11111111111', 'lKpJ5CnBry', 1, '4D656E67204B776F6B2057696E67', '', 'fGsUVuXncI', '2020-09-10 05:09:56', 0, '32393520416C616D65646120537472656574', '32393520416C616D65646120537472656574', '32393520416C616D65646120537472656574', '32393520416C616D65646120537472656574', '12345678910@qq.com');
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_addr, addr_one, addr_two, addr_three, email) VALUES (4, '416E647265612057617368696E67746F6E', '22222222222', 'ei3bpRMxWq', 1, '416E647265612057617368696E67746F6E', '', '5A3gLrKW8L', '2006-11-02 17:41:18', 0, '3731352048696E636B6C6579205264', '3731352048696E636B6C6579205264', '3731352048696E636B6C6579205264', '3731352048696E636B6C6579205264', '12345678910@qq.com');
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_addr, addr_one, addr_two, addr_three, email) VALUES (5, '4D616F204A69616C756E', '33333333333', '4DmkewSQq6', 1, '4D616F204A69616C756E', '', '8A7gPjTpN4', '2022-07-23 21:22:36', 1, '323736204C6974746C6520436C6172656E646F6E205374', '323736204C6974746C6520436C6172656E646F6E205374', '323736204C6974746C6520436C6172656E646F6E205374', '323736204C6974746C6520436C6172656E646F6E205374', '12345678910@qq.com');
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_addr, addr_one, addr_two, addr_three, email) VALUES (6, '5A6F752058697579696E67', '4444444444', 'clYE3F1KSz', 1, '5A6F752058697579696E67', '', '8L3nfBophu', '2008-03-11 21:12:51', 0, '363539205368656E6E616E20452052642C20436169205775205765692C204C756F6875204469737472696374', '363539205368656E6E616E20452052642C20436169205775205765692C204C756F6875204469737472696374', '363539205368656E6E616E20452052642C20436169205775205765692C204C756F6875204469737472696374', '363539205368656E6E616E20452052642C20436169205775205765692C204C756F6875204469737472696374', '12345678910@qq.com');
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_addr, addr_one, addr_two, addr_three, email) VALUES (7, '4A616D69652053616E64657273', '5555555555', 'VO8p848fwA', 1, '4A616D69652053616E64657273', '', 'r38LEjbzee', '2016-01-18 17:04:45', 3, '323030205061726B20456E64205374', '323030205061726B20456E64205374', '323030205061726B20456E64205374', '323030205061726B20456E64205374', '12345678910@qq.com');
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_addr, addr_one, addr_two, addr_three, email) VALUES (8, 'E5A4A9E5A4A9', '1635905050', '111', 0, '4A616D657320506F6E64', null, null, '2022-10-27 16:47:21', null, 'null', 'null', 'null', 'null', '12345678910@qq.com');
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_addr, addr_one, addr_two, addr_three, email) VALUES (9, 'E5A4A9E5A4A9', '15023192020', '111', 0, null, null, null, '2022-11-04 19:07:55', null, null, null, null, null, '15023192020@qq.com');
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_addr, addr_one, addr_two, addr_three, email) VALUES (10, 'E5A4A9E5A4A9', '15023591218', '111', 0, null, null, null, '2022-11-04 19:41:09', null, null, null, null, null, '15023591218@qq.com');
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_addr, addr_one, addr_two, addr_three, email) VALUES (11, 'E5A4A9E5A4A9', '15083729338', '111', 0, null, null, null, '2022-11-05 13:39:27', null, null, null, null, null, '15083729338@qq.com');
INSERT INTO idlebugs.user_info (id, user_name, phone, password, authority, real_name, id_card, stu_num, register_date, violation_count, default_addr, addr_one, addr_two, addr_three, email) VALUES (12, 'E5A4A9E5A4A9', '15080711348', '111', 0, null, null, null, '2022-11-05 14:28:47', null, null, null, null, null, '15080711348@qq.com');
