create table product_catalog
(
    id     int auto_increment comment '商品分類序號'
        primary key,
    name   varchar(255) charset utf8 not null comment '分類名稱',
    number varchar(255)              not null comment '商品分類編號(有規律)'
);

INSERT INTO idlebugs.product_catalog (id, name, number) VALUES (1, 'E69BB8E7B18D', 'B');
INSERT INTO idlebugs.product_catalog (id, name, number) VALUES (2, 'E7BE8EE5A69D', 'M');
INSERT INTO idlebugs.product_catalog (id, name, number) VALUES (3, 'E695B8E7A2BC', 'D');
INSERT INTO idlebugs.product_catalog (id, name, number) VALUES (4, 'E5AEB6E5B185', 'F');
INSERT INTO idlebugs.product_catalog (id, name, number) VALUES (5, 'E99BBBE599A8', 'E');
INSERT INTO idlebugs.product_catalog (id, name, number) VALUES (6, 'E69C8DE8A39D', 'C');
INSERT INTO idlebugs.product_catalog (id, name, number) VALUES (7, 'E585B6E5AE83', 'O');
