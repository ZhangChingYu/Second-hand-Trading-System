create table comment_report
(
    id         int auto_increment
        primary key,
    comment_id int            not null comment '被舉報的留言id',
    phone      varchar(255)   not null comment '舉報者的手機',
    content    varchar(10000) not null comment '舉報原因',
    date       datetime       not null,
    status     int            not null comment '0: 還沒處理
1: pass (留言違規)
2: reject (留言沒有違規)',
    constraint comment_report_id_uindex
        unique (id)
);

INSERT INTO idlebugs.comment_report (id, comment_id, phone, content, date, status) VALUES (1, 6, '15059417755', 'E79599E8A880E4B88DE58F8BE59684', '2022-12-12 13:43:18', 0);
