create table feedback
(
    id          int    auto_increment   comment  '序号'
        primary key,
    number      varchar(255)            not null comment '反馈编号',
    phone       varchar(255)            not null comment '反馈手机',
    feed_time   datetime                null comment '反馈时间',
    content     varchar(1000) charset utf8 null comment '反馈内容',
    status      int                     not null comment '状态
    1：已读
    0：未读'
);