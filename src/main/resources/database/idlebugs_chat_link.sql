CREATE TABLE chat_link  (
  id           int   auto_increment comment '序号'
        primary key,
  number       varchar(255)            not null comment '聊天关系编号',
  from_id     varchar(255)            not null comment '发送者',
  to_id       varchar(255)            not null comment '接收者',
  create_time   datetime                not null comment '创建时间'
);
--聊天关系表