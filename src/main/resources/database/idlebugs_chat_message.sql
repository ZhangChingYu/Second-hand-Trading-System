CREATE TABLE chat_message  (
  id           int   auto_increment comment '序号'
        primary key,
  number       varchar(255)            not null comment '聊天关系编号',
  from_id     varchar(255)            not null comment '发送者',
  to_id       varchar(255)            not null comment '接收者',
  content       varchar(255)            not null comment '聊天内容',
  send_time     datetime                not null comment '发送时间',
  types          int                     not null comment '消息类型
  0:文本
  1:图片
  2:视频',
  is_latest     int                     null default null comment '是否为最后一条信息
  1:是
  0:不是',
  is_delete    int                     null default null comment '是否删除
  2:接收者删除
  1:发送者删除
  0:不删'
);
--聊天内容