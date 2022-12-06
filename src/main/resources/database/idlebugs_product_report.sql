create table product_report
(
    id      int auto_increment
        primary key,
    number  varchar(255)   not null comment 'product number',
    phone   varchar(255)   not null comment 'report user''s phone',
    content varchar(10000) not null comment 'reason of the report',
    date    datetime       not null,
    status  int            not null comment '0: report hasn''t been handled
1: pass report (the product is illegal)
2: reject report (the product is fine)'
);

INSERT INTO idlebugs.product_report (id, number, phone, content, date, status) VALUES (1, 'C1669690298810', '15059417755', 'E8A399E5AD90E59AB4E9878DE884ABE7B79AEFBC8CE6B497E4B880E6ACA1E59FBAE69CACE5B0B1E6B292E6B395E7A9BFE4BA86', '2022-12-05 16:47:48', 1);
