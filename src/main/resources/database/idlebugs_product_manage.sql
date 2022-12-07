create table product_manage
(
    id           int auto_increment comment '商品序號(統計用，無意義)'
        primary key,
    name         varchar(500) charset utf8  not null comment '商品名稱',
    s_phone      varchar(255)               not null comment '賣家手機號',
    number       varchar(255)               not null comment '商品編號(有規律)',
    storage      int                        not null comment '商品庫存數',
    picture      int(4)                     not null comment '商品圖片',
    intro        varchar(1000) charset utf8 null comment '商品描述',
    price        decimal(10, 2)             not null comment '商品價格',
    status       int                        not null comment '商品狀態
0: 已上架，審核通過
1: 未上架，提交審核中
2: 未上架，審核不通過
3: 未上架，檢舉違規的商品',
    report_count int default 0              null comment '商品經舉報的次數',
    catalog      varchar(255)               not null comment '商品類型編號',
    address      varchar(255)               null comment '商品貨源(可以是校區)',
    like_count   int                        null comment '收藏數'
);

INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (1, 'E88083E7A094E79C9FE79BB8E88BB1E8AA9EE4B880', '15059417755', 'B1667557259691', 1, 1, 'E585A8E696B0EFBC8CE782BAE4BDBFE794A8E9818E', 12.00, 0, 0, 'B', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E7ABB9E59C9233E6A39F', 1);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (2, 'E4BA8CE6898BE69BB8', '15059417755', 'B1667557541428', 6, 2, 'E58FAFE8ADB0E583B9EFBC8C38E58583E4B880E69CAC', 8.00, 0, 0, 'B', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E7ABB9E59C9233E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (3, 'E6809DE683B3E98193E5BEB7E88887E6B395E6B2BB32303231E5B9B4E78988', '15059417755', 'B1667557719337', 1, 1, 'E4BA8CE6898BEFBC8CE585A7E69C89E5B091E9878FE7AD86E8A898EFBC8CE4BB8BE6848FE88085E6858EE68B8D', 7.90, 0, 0, 'B', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E7ABB9E59C9233E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (4, 'E4B8ADE585ACE5B08FE5ADB8E69599E5B8ABE8B387E6A0BCE8AD89', '15059417755', 'B1667557940336', 100, 1, 'E6A8B9E4BABAE88083E8A9A6E69BB8E5B18B3A20E585A8E696B0E4B8ADE585ACE5B08FE5ADB8E69599E5B8ABE8B387E6A0BCE8AD89E7AD86E8A9A6E7A791E79BAE28E69599E69D902BE8A9A6E58DB72BE7B6B2E8AAB229', 68.00, 0, 0, 'B', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E7ABB9E59C9233E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (5, 'E4BA8CE6898BE887AAE88083E69599E69D903034373239E5A4A7E5ADB8E8AA9EE6968732303138E78988E5BE90E4B8ADE78E89E999B6E59E8BE582B3E58C97E4BAACE5A4A7E5ADB8E587BAE78988E7A4BE', '15059417755', 'B1667558776457', 2, 2, 'E887AAE88083E79A84E68980E69C89E7A791E79BAEE983BDE69C89EFBC8CE99C80E8A681E585B6E4BB96E79A84E58FAFE4BBA5E6809DE8818AE68891E38082AE983BDE698AFE6ADA3E78988E4BA8CE6898BE69BB82121E4B883E585ABE68890E5B7A6E58FB3E696B0AE5B7B2E7B693E794A8E98592E7B2BEE6B688E6AF92E4BA86EFBC8CE694BEE5BF83E68B8D', 14.66, 0, 0, 'B', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E7ABB9E59C9233E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (6, 'E59C8BE5AEB6E69599E5B8ABE8B387E6A0BCE88083E8A9A6', '15059417755', 'B1667559081116', 1, 1, null, 15.00, 0, 0, 'B', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E7ABB9E59C9233E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (7, 'E5A4A7E5ADB8E88BB1E8AA9E33', '15059417755', 'B1667559211912', 1, 2, 'E7B69CE59088E8A893E7B7B42BE7B69CE59088E69599E7A88BE585A9E69CAC3130E58583', 10.00, 0, 0, 'B', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E7ABB9E59C9233E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (8, '3364734D617832303230', '15059417755', 'B1667559352213', 1, 2, 'E5BE9EE696B0E6898BE588B0E9AB98E6898B20E585A8E5BDA9E58DB0E588B7', 15.00, 0, 0, 'B', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E7ABB9E59C9233E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (9, 'E5A4A7E5ADB8E8A888E7AE97E6A99FE7ACACE4BA8CE78988', '15059417755', 'B1667559423966', 1, 1, null, 7.90, 0, 0, 'B', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E7ABB9E59C9233E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (10, 'E5A4A7E5ADB8E7949FE5BF83E79086E581A5E5BAB7E69599E882B2E7ACACE4BA8CE78988', '15059417755', 'B1667559776586', 1, 1, 'E585A8E696B0E6B292E69C89E794A8E9818EEFBC8CE588B0E8B2A8E5898DE5B0B1E98080E8AAB2E4BA86', 7.90, 0, 0, 'B', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E7ABB9E59C9233E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (11, 'E4B88DE88081E4B98BE8AC8E20E7A59EE7B693E985B0E883BA20E88892E6BDA4E5ACB0E58592E4B9B3', '15023192020', 'M1667560395202', 1, 2, 'E8B2B7E4BE86E58385E58385E68B86E4BA86E58C85E8A39DE6B292E69C89E4BDBFE794A8E9818EEFBC8CE585A8E696B0EFBC8CE794B1E696BCE5BE8CE99DA2E58F88E8B2B7E4BA86E4B880E5A597E99692E7BDAEE4BA86EFBC8CE697A5E69C9FE789B9E588A5E696B0E9AEAE', 25.00, 0, 0, 'M', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E6A285E59C9232E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (12, 'E99DA2E8869C', '15023192020', 'M1667560511478', 1, 1, 'E986ABE794A8E99DA2E8869CEFBC8CE68BAFE69591E6958FE6849FE8828CEFBC8CE585A8E696B0EFBC8CE583B9E6A0BCE5A5BDE59586E9878F', 13.00, 0, 0, 'M', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E6A285E59C9232E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (13, 'E5AE89E7BE8EE981AEE79195333020E5BEB7E5A683E7B4ABE89887E5B9B3E8A1A1E99DA2E99C9C3330', '15023192020', 'M1667560712890', 1, 2, 'E981AEE79195E8A9A6E794A8E9818EE4B880E6ACA120E99DA2E99C9CE98284E589A9E4B880E58D8A20E98081E998BFE88A99E9AD9AE7B1BDE9AD9AE5AD90E986ACE79DA1E79CA0E8A39CE6B0B4E99DA2E8869C', 30.00, 0, 0, 'M', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E6A285E59C9232E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (14, 'E99B99E79CBCE79AAEE8B2BC', '15023192020', 'M1667560953952', 1, 1, 'E895BEE7B5B2E69687E99B99E79CBCE79AAEE8B2BC313932E59B9E', 8.00, 0, 0, 'M', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E6A285E59C9232E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (15, '626DE8828CE6B4BBE7B399E7B1B3E6B0B4', '15023192020', 'M1667561043409', 1, 1, 'E794A8E9818EE585A9E6ACA120E68EA7E6B2B9', 45.00, 0, 0, 'M', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E6A285E59C9232E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (16, 'E9BB91E889B2E68DB2E995B7E9ABAE', '15023192020', 'M1667561192779', 1, 2, 'E5B8B6E89197E68B8DE9818EE4B880E6ACA1E785A72028E995B7E5BAA6E785A7E78987E58FAFE8A68B29', 10.00, 0, 0, 'M', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E6A285E59C9232E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (17, 'E9BB91E889B2E79BB4E58187E9ABAE', '15023192020', 'M1667561271483', 1, 3, 'E5B8B6E89197E68B8DE9818EE4B880E6ACA1E785A72028E995B7E5BAA6E785A7E78987E58FAFE8A68B29', 10.00, 0, 0, 'M', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E6A285E59C9232E6A39F', 1);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (18, 'E585A8E696B0E69CAAE68B86E5B081E8B0B7E99BA8E58589E6849FE6B0B4E4B8ADE6A8A333356D6C', '15023192020', 'M1667561476993', 1, 2, 'E4BF9DE8B3AAE69C9FE98284E69C893136E5808BE69C88', 10.00, 0, 0, 'M', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E6A285E59C9232E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (19, 'E4BBA5E7B6A0E9A6ACE99EADE88D89E985AEE8BFB7E8BFADE9A699E599B4E99CA7', '15023192020', 'M1667561685784', 1, 7, 'E4BBA5E7B6A0E9A6ACE99EADE88D89E985AEE8BFB7E8BFADE9A699E4BFAEE8ADB7E599B4E99CA7AE8A39CE6B0B4E4BF9DE6BF9520E694B6E7B8AEE6AF9BE5AD94E6BAABE5928CE7B4B0E886A9E4B88DE588BAE6BF80', 88.00, 0, 0, 'M', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E6A285E59C9232E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (20, 'E791AAE9BA97E4B98BE9A699E5BEB7E588A9E5A89CE9A699E6B0B437356D6CE793B7E793B6E5B08FE79CBEE9A699E6B0B4', '15023192020', 'M1667562021876', 10, 4, 'E791AAE9BA97E4B98BE9A699E5BEB7E588A9E5A89CE9A699E6B0B437356D6CE793B7E793B6E5B08FE79CBEE9A699E6B0B4AE58FAFE9858DE5B08FE7A5A8EFBC8CE7A6AEE79B92EFBC8CE7A6AEE8A28B4D41524C592F44656C696E61E791AAE9BA97E4B98BE9A699E5BEB7E588A9E5A89CE78EABE791B0E6BF83E9A699E6B0B4E999B6E793B7E793B62045445037356D6CAE5898DE8AABF3A20E5A4A7E9BB83E88D94E69E9DAE4B8ADE8AABF3A20E59C9FE880B3E585B6E78EABE791B0E9A699E88D89AE5B0BEE8AABF3A20E9BA9DE9A699', 199.00, 0, 0, 'M', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E6A285E59C9232E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (21, 'E6898BE6A99F20E7B485E7B1B36E6F746534', '15023591218', 'D1667562292603', 1, 1, 'E7B485E7B1B36E6F746534203939E68890E696B0E58299E794A8E6A99FA363447E79A8420E58A9FE883BDE6ADA3E5B8B8', 220.00, 0, 0, 'D', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E69DBEE59C9235E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (22, 'E8978DE88ABDE880B3E6A99F', '15023591218', 'D1667622743045', 1, 3, null, 105.00, 0, 0, 'D', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E69DBEE59C9235E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (23, 'E596B5E596B5E68993E58DB0E6A99F', '15023591218', 'D1667622843352', 1, 3, 'E4BAACE69DB1E8B3BCE8B2B7', 125.00, 0, 0, 'D', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E69DBEE59C9235E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (24, '6970686F6E657872', '15023591218', 'D1667623026865', 1, 5, 'E7B485E889B2AE7BE8EE69DBFE784A1E98E96EFBC8C31323867AE69588E78E873834A3935E696B0', 1200.00, 0, 0, 'D', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E69DBEE59C9235E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (25, 'E7B4A2E5B0BC3634303020E596AEE58F8DE79BB8E6A99FE5B088E6A5AD344BE9AB98E6B885E695B8E7A2BCE8A696E9A0BBE79BB8E6A99F', '15023591218', 'D1667623433074', 24, 5, 'E4B880E98FA1E8B5B0E5A4A9E4B88B2031382D3133356D6D20E594AEE583B93A33393030E5858320E697A5E5B8B8E5A4A0E794A8A3130323047E5BBA3E8A792E98FA1E9A0AD20E594AEE583B93A2034313030AE4BABAE5838FE5A597E8A39D20E594AEE583B93A2034323030A3138353047E995B7E784A6E69785E9818AE5A597E8A39D20E594AEE583B93A2035353030', 3499.00, 0, 0, 'D', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E69DBEE59C9235E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (26, '4570736F6E2FE6849BE699AEE7949FE585A8E9AB98E6B885E68A95E5BDB1E58480', '15023591218', 'D1667623916794', 1, 9, 'E4BB8AE5B9B4E59B9BE69C88E4BBBDE59CA8E4B880E5808BE695B8E7A2BCE5BA97E8A3A1E8B2B7E79A84EFBC8CE8B2B7E4BE86E59B9BE5808BE69C88E7B8BDE69982E5B8B8E58FAAE79C8BE4BA86323548EFBC8CE6A99FE599A8E4BF9DE8ADB7E5AE8CE5A5BDEFBC8CE784A1E69A97E79785EFBC8CE8A696E9A0BBE6B885E699B0E5BAA6E7B595E5B08DE58FAFE4BBA5E38082AE8B2B7E4BA86E98081E59C96E78987E8A3A1E79A84E9AB98E6B885E8A696E9A0BBE58886E5B18FE599A8E5928CE980A3E68EA5E7B79AEFBC8CE58385E887AAE68F90EFBC8CE583B9E6A0BCE58FAFE4BBA5E5B08FE58880EFBC8CE4B88DE68EA5E58F97E5B1A0E9BE8DE58880EFBC8CE58F8DE6ADA3E4B99FE698AFE99AA8E7B7A3E587BAE38082', 1690.00, 0, 0, 'D', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E69DBEE59C9235E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (27, 'E7B4A2E5B0BCE5908CE6ACBE20E9818BE58B95E69C83E5B088E794A8E695B8E7A2BCE79BB8E6A99F', '15023591218', 'D1667624117426', 1, 5, 'E4BA94E58D83E890ACE8B685E6B885E5838FE7B4A0EFBC8CE4BCB8E7B8AEE98FA1E9A0ADEFBC8CE59FBAE69CACE585A8E696B0E38082E4B880E58FA3E583B9343535', 450.00, 0, 0, 'D', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E69DBEE59C9235E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (28, '5BE5808BE4BABAE99691E7BDAEE587BAE585A8E696B0E79BB8E6A99F215D', '15023591218', 'D1667624567402', 1, 6, 'E4BDB3E883BDE5BEA9E58FA4E79BB8E6A99F34383030E890ACE5838FE7B4A0434344E695B8E7A2BCE79BB8E6A99FE5ADB8E7949FE696B0E6898BE585A5E99680E5BEA9E58FA4E9AB98E6B885E88081E5BC8FE58DA1E78987E79BB8E6A99FAE58A9FE883BDE58F83E695B8A312E38E5808DE8AE8AE784A6E58FAFE694BEE5A4A7E7B8AEE5B08F20E69C89E5BEAEE8B79DE6A8A1E5BC8FA322EE981A9E59088E5ADB8E7949FE8A898E98C84E7949FE6B4BB20E5B08FE799BDE69893E4B88AE6898B20E58FAFE78EA9E58A9BE9AB98A332E34383030E890ACE5838FE7B4A020322E37E88BB1E5908BE9AB98E6B885E5B18FE5B995A342EE5A49CE5A496E68B8DE6949DE6B885E699B0E58FAFE8A68B20E58FAFE5AE9AE69982E68B8DE6949D20E58F8DE68789E9809FE5BAA6E5BFABA352EE696B9E4BEBFE6949CE5B8B620E5A496E8A780E7B2BEE7BE8EAE694AFE68C81E8A696E9A0BBE98C84E8A3BDEFBC8CE5B9B3E69982E68B8DE68B8DE8A696E9A0BB766C6F67E4BB80E9BABCE79A84E983BDE68CBAE4B88DE98CAFEFBC8CE99D9EE5B8B8E981A9E59088E59CA8E6A0A1E5ADB8E7949FE4BDBFE794A821', 159.00, 0, 0, 'D', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E69DBEE59C9235E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (29, 'E4BDB3E883BD69787573E7B3BBE58897E5908CE6ACBE636364E695B8E7A2BCE5BEA9E58FA4E79BB8E6A99F', '15023591218', 'D1667625072376', 1, 5, 'E69893E7838AE58D83E792BDE5908CE6ACBEE585A8E696B0E5ADB8E7949FE4BEBFE6949CE79BB8E6A99F636364E695B8E7A2BCE5BEA9E58FA4E79BB8E6A99FAE981A9E59088E6A0A1E59C92E7B480E98C84E7949FE6B4BBE795A2E6A5ADE5ADA3E68B8DE785A7EFBC8CE69785E8A18CE4BEBFE6949CEFBC8CE99AA8E6898BE68B8DE785A7E8A898E98C84E7B2BEE5BDA9E79EACE99693A38E5808DE784A6E8B79D20E694BEE5A4A7E7B8AEE5B08F2B34383030E890ACE5838FE7B4A0AE9858DE4BBB6E69C89E99BBBE6B1A02BE6898BE7B9A92BE998B2E6B0B4E79BB8E6A99FE8A28B2BE695B8E6939AE7B79AE8AAAAE6988EE69BB82BE5AE89E58D93547970652D43E8BD89E68EA5E9A0AD2B333247E585A7E5AD98E58DA128E8B2B7E79A84E69982E58099E4B880E8B5B7E8B2B7E79A8429AE5B9ABE69C8BE58F8BE587BAE79A84E585A8E696B0E79BB8E6A99FE585A8E696B0EFBC8CE585A8E696B0EFBC8CE585A8E696B0212121', 260.00, 0, 0, 'D', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E69DBEE59C9235E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (30, '50616E61736F6E69632FE69DBEE4B88B20E5AEB6E5BAADE695B8E7A2BCE79BB8E6A99F', '15023591218', 'D1667625289197', 1, 1, 'E69C89E683B3E8A681E79A84E5AEB6E5BAADE695B8E7A2BCE79BB8E6A99FEFBC8CE4B99DE68890E696B0EFBC8CE6B292E6808EE9BABCE794A8E9818EEFBC8CE69C89E585A7E5AD98E58DA1AE6849FE88888E8B6A3E79A84E8A9B1E8AB8BE5928CE68891E7A781E8818AE590A77E', 120.00, 0, 0, 'D', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E69DBEE59C9235E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (31, 'E5AEBFE8888DE99691E7BDAEEFBC8CE583B9E6A0BCE7AD89E59084E696B9E99DA2E5958FE9A18CE6ADA1E8BF8EE4BE86E5958F21', '15083729338', 'F1667627075191', 1, 2, 'E5B9BEE4B98EE585A8E696B0AE58FAFE4BBA5E694BEE9A696E9A3BEEFBC8CE5B08FE789A9E4BBB621AE58FAFE4BBA5E694BEE68ABDE7B49921AE58FAFE4BBA5E795B6E6898BE6A99FE694AFE69EB62120E981A9E59088E8BFBDE58A87E9BBA821AE58385E99990E887AAE68F9021212121', 15.90, 0, 0, 'F', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E898ADE59C9234E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (32, 'E5AEBFE8888DE7BDAEE789A9E69EB6', '15083729338', 'F1667627231025', 2, 3, 'E7AF80E79C81E7A9BAE99693E694B6E7B48DEFBC8CE585A8E696B0', 9.90, 0, 0, 'F', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E898ADE59C9234E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (33, 'E5BA8AE9A0ADE5A4A7E99DA0E8838CE6B299E799BCE5A28AE5AEBFE8888DE5BA8AE4B88AE79C8BE69BB8E5BA8AE99DA0E69E95E9A0ADE8ADB7E885B0E99DA0E69E95E5A18CE5A18CE7B1B3E68AB1E69E95', '15083729338', 'F1667627703197', 12, 5, 'E5A49AE58A9FE883BDE885B0E99DA0EFBC8CE7ACA6E59088E4BABAE9AB94E5B7A5E5ADB8EFBC8CE8B2BCE59088E885B0E983A8E69BB2E78DBBE38082E9AB98E5AF86E5BAA6E6B0B4E699B6E7B5A8E69D90E8B3AAEFBC8CE6898BE6849FE7B4B0E886A9E69F94E8BB9FEFBC8CE58FAFE6A99FE6B497E38082E78DA8E7AB8BE585A7E886BDE8A8ADE8A888EFBC8CE99AB1E5BDA2E68B89E98D8AE696B9E4BEBFE68B86E6B497E38082E9A0ADE69E95E98895E689A3E8AABFE7AF80EFBC8CE58FAFE68B86E58DB8E4BDBFE794A8E38082AE6ACBEE5BC8F3A2020E68190E9BE8DE38081E883A1E898BFE89494E38081E59388E5A3ABE5A587E38081E59090E58FB8E9BAB5E58C85E38081E7B289E58594E5AD90AE5B0BAE5AFB83A20E4B8ADE8999F33352A3630636DEFBC9BE5A4A7E8999F34302A3730636DAE789B9E583B9E587BAEFBC8CE680A5E99C80E690ACE981B7EFBC8CE983BDE698AFE696B0E79A84EFBC8CE8AB8BE58BBFE8ADB0E583B9E8AC9DE8AC9D2121', 9.90, 0, 0, 'F', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E898ADE59C9234E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (34, 'E7BE8EE79A84E99BBBE785AEE98D8B312E324CE5AEBFE8888DE7A59EE599A8333034E4B88DE98FBDE98BBCE99BBBE785AEE892B8E98D8BE99BBBE781ABE98D8BE998B2E4B9BEE78792', '15083729338', 'F1667628145101', 1, 3, 'E5BFABE795A2E6A5ADE4BA86E6B885E7A9BAE99691E7BDAEE789A9E59381EFBC8CE58385E694AFE68C81E887AAE68F90E38082A312E32E58D87E79A84E99BBBE785AEE98D8BEFBC8CE5B8B6E892B8E7B1A0EFBC8C31E6AA9433303077EFBC8C32E6AA9436303077EFBC8CE8B685E6BAABE887AAE58B95E696B7E99BBBEFBC8CE4B9BEE78792E887AAE58B95E696B7E99BBBE38082E5BA95E983A8E69C89E99BBBE4BDBFE794A8E79795E8B7A1EFBC8CE4B88DE5BDB1E99FBFE6ADA3E5B8B8E4BDBFE794A8E38082', 35.00, 0, 0, 'F', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E898ADE59C9234E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (35, 'E5ADB8E7949FE5AEBFE8888DE7AF80E883BDE8BFB7E4BDA0E58FB0E5BC8FE5B08FE5A4AAE999BDE58F96E69A96E599A8E9809FE786B1', '15083729338', 'F1667628382876', 1, 5, 'E794A8E9818EE585A9E6ACA1E587BAE99691E7BDAEEFBC8CE5AFAC3139E9AB983233E98790E7B1B3EFBC8CE58385E887AAE68F90EFBC8CE4B88DE8ADB0E583B9EFBC8CE683B3E5A5BDE5868DE68B8D', 9.90, 0, 0, 'F', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E898ADE59C9234E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (36, 'E5BAB7E4BDB3E6898BE68C81E68E9BE78799E6A99F', '15083729338', 'F1667628578170', 1, 3, 'E5BAB7E4BDB3E6898BE68C81E68E9BE78799E6A99FEFBC8CE5B08FE59E8BEFBC8CE58FAAE794A8E9818EE4B880E6ACA1EFBC8CE5B9BEE4B98EE585A8E696B0EFBC8CE596AEE4BD8DE5AEBFE8888DE8A681E98080E688BFEFBC8CE69DB1E8A5BFE5A4AAE5A49AE587BAE99691E7BDAEEFBC8C3235E4B88DE8AC9BE583B9EFBC8CE789A9E59381E594AEE587BAE4B88DE98080E4B88DE68F9BEFBC8CE4BB8BE6848FE58BBFE68B8DE38082', 25.00, 0, 0, 'F', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E898ADE59C9234E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (37, 'E587BAE5A381E78788E5A4A7E5ADB8E7949FE5AEBFE8888D', '15083729338', 'F1667628800430', 1, 2, 'E58FAFE7A381E590B8EFBC8CE4B99FE58FAFE4BBA5E794A8334DE9BB8FE8B2BC28E4B88DE68F90E4BE9BEFBC8CE887AAE5B7B1E8B2B7E5938829AE58FAFE4BBA5E8AABFE7AF80E6988EE69A97E585A9E6AA94E38082AE4B98BE5898DE59CA8E5ADB8E6A0A1E794A8E79A84EFBC8CE8BF91E69C9FE794A8E4B88DE588B0E4BA86EFBC8CE4BEBFE5AE9CE587BAEFBC8CE99990E887AAE68F90E38082AE99691E7BDAEE789A9E59381E4B88DE98080E4B88DE68F9BEFBC8CE590B9E6AF9BE6B182E796B5E88085E8AB8BE7B99EE98193E38082', 5.00, 0, 0, 'F', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E898ADE59C9234E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (38, 'E5AEBFE8888DE99691E7BDAE', '15083729338', 'F1667628981269', 9, 1, 'E8B293E8B293E4B88DE8B3A32121E795A2E6A5ADE595A621E5A5BDE5A49AE6B292E794A8E9818EE79A84E5928CE99691E7BDAEE79A84E69DB1E8A5BF21E5A5B3E5A4A7E5ADB8E7949FE5AEBFE8888DE5A4A7E794A9E8B3A321E59C96E4B88AE4BBBBE4BD95E69DB1E8A5BFE983BDE8B3A321E8B293E8B293E4B88DE8B3A321', 5.00, 0, 0, 'F', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E898ADE59C9234E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (39, '5BE99BBBE785AEE98D8B5D', '15083729338', 'F1667629178624', 1, 3, 'E5AEBFE8888DE5ADB8E7949FE6AA94E596AEE8BAABE8B2B4E6978FE983BDE58FAFE4BBA5E69C89EFBC8CE78292E88F9CEFBC8CE6B3A1E9BAB5EFBC8CE78789E6B9AFE983BDE58FAFE4BBA5EFBC8CE585B7E58299E7858EE89B8BEFBC8CE5819AE58FAFE6A882E99B9EE7BF85EFBC8CE78789E78E89E7B1B3E883A1E898BFE89494E68E92E9AAA8E6B9AFE79A84E58A9FE883BDE380823232434D312D32E4BABA', 16.00, 0, 0, 'F', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E898ADE59C9234E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (40, 'E5BA8AE5A28AE8BB9FE5A28A', '15083729338', 'F1667629608309', 1, 3, '36302A313230E7BE8AE7BE94E7B5A8E5A28AE5AD90E586ACE5A4A9E58AA0E58E9AE8A4A5E5AD90E5AEBFE8888DE8A2ABE5AD90', 15.00, 0, 0, 'F', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E898ADE59C9234E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (41, 'E89887E6B38AE788BEE99BBBE785AEE98D8B', '15080711348', 'E1667629991425', 1, 3, 'E89887E6B38AE788BEE99BBBE785AEE98D8BE5B08FE59E8BE5AEB6E794A8E5A49AE58A9FE883BDE99BBBE98D8BE58886E9AB94E5BC8FE5B08FE99BBBE98D8BE5AEBFE8888DE5ADB8E7949FE892B8E785AEE4B880E9AB94EFBC8CE6B292E794A8E9818EE5B9BEE59B9EEFBC8CE58FAFE4BBA5E785AEE6B3A1E9BAB5E6B0B4E9A483E5B08FE781ABE98D8BEFBC8CE58A9FE883BDE9BD8AE585A8EFBC8CE4B99DE68890E696B0E38082AE58385E694AFE68C81E887AAE68F90EFBC8CE4B88DE883BDE887AAE68F90E79A84E4B88DE8A681E4BE86E5958FEFBC8CE594AEE587BAE4B88DE98080E4B88DE68F9BE38082', 59.00, 0, 0, 'E', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E69DBEE59C9231E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (42, 'E99BBBE786B1E5A4BE', '15080711348', 'E1667630136081', 1, 1, 'E59CA8E5ADB8E6A0A1E5AEBFE8888DE794A8E79A84E5A4AAE5B091EFBC8CE99691E7BDAEAE6849FE88888E8B6A3E79A84E8A9B1E6ADA1E8BF8EE7A781E688917E', 20.00, 0, 0, 'E', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E69DBEE59C9231E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (43, 'E996B1E8AE80E78788E585B7', '15080711348', 'E1667630238607', 1, 1, 'E5ADB8E7949FE5AEBFE8888DE794A8E78788EFBC8CE586B7E69A96E78788E983BDE69C89EFBC8CE99691E7BDAEE789A9E59381EFBC8CE585A8E696B0', 20.00, 0, 0, 'E', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E69DBEE59C9231E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (44, 'E58AA0E6BF95E599A8E5AEB6E794A8E99D9CE99FB3E887A5E5AEA4737562E58585E99BBBE5A4A7E5AEB9E599A8E9878FE5AD95E5A9A6E5ACB0E58592E6B7A8E58C96E8BEA6E585ACE5AEA4E6A18CE99DA2E5AEBFE8888DE5ADB8E7949FE5BA8AE9A0ADE8BFB7E4BDA0E5B08FE59E8BE5A5B3E4BEBFE6949CE5BC8FE8BB8AE8BC89E599B4E99CA7E7A9BAE6B0A3E58AA0E6BF95E599A8', '15080711348', 'E1667630531853', 1, 1, 'E6B292E6808EE9BABCE794A8E9818EEFBC8CE99691E7BDAEE789A9E59381EFBC8CE5B9BEE4B98EE585A8E696B0', 21.06, 0, 0, 'E', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E69DBEE59C9231E6A39F', 0);
INSERT INTO idlebugs.product_manage (id, name, s_phone, number, storage, picture, intro, price, status, report_count, catalog, address, like_count) VALUES (45, 'E980A3E8A1A3E8A399', '15023192020', 'C1669690298810', 1, 6, 'E794B1E696BCE5B0BAE7A2BCE8B2B7E5B08FE4BA8620E7A9BFE4B88DE4BA8620E585A8E696B020E8B3AAE9878FE5928CE78988E59E8BE983BDE5BE88E5A5BD20E79C8BE4B88AE79A84E5B08FE5A790E5A790E58AA05151E59694285151E8999F3A203334343635373238373729', 49.90, 1, 1, 'C', 'E9878DE685B6E5A4A7E5ADB8E8998EE6BAAAE6A0A1E58D80E6A285E59C9233E6A39F', 0);