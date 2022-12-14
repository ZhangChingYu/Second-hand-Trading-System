create table buyer_evaluate
(
    id           int auto_increment comment '序號'
        primary key,
    number       varchar(255)   not null comment '商品編號',
    buyer_phone  varchar(255)   not null comment '買家手機號',
    seller_phone varchar(255)   not null comment '賣家手機號',
    score1       int            not null comment '描述相符得分(0~5)',
    score2       int            not null comment '物流服務得分(0~5)',
    score3       int            not null comment '服務態度得分(0~5)',
    is_anonymous int            not null comment '是否匿名評價(1:是, 0:否)',
    evaluate     varchar(10000) null comment '文字評價',
    date         datetime       not null,
    constraint buyer_evaluate_id_uindex
        unique (id)
);

INSERT INTO idlebugs.buyer_evaluate (id, number, buyer_phone, seller_phone, score1, score2, score3, is_anonymous, evaluate, date) VALUES (1, 'C1670300792286', '15083729338', '15080711348', 5, 5, 5, 0, null, '2022-12-10 17:21:34');
INSERT INTO idlebugs.buyer_evaluate (id, number, buyer_phone, seller_phone, score1, score2, score3, is_anonymous, evaluate, date) VALUES (2, 'E1667630531853', '15083729338', '15080711348', 5, 5, 5, 0, null, '2022-12-10 17:23:58');
