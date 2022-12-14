create table hot_key
(
    id          int auto_increment
        primary key,
    create_date datetime     not null comment '關鍵詞的生成日期',
    recent_date datetime     not null comment '最新被點擊的日期',
    click_count int          not null comment '點擊次數',
    content     varchar(255) not null comment '關鍵詞內容(長度不超過8個字)',
    constraint hot_key_id_uindex
        unique (id)
)
    comment '紀錄熱門搜索關鍵詞的表';

INSERT INTO idlebugs.hot_key (id, create_date, recent_date, click_count, content) VALUES (1, '2022-12-14 12:11:17', '2022-12-14 12:12:42', 2, 'E4BA8CE6898B');
