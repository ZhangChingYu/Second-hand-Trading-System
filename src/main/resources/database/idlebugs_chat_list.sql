CREATE TABLE chat_list  (
  id               int auto_increment comment '序号'
        primary key,
  number           varchar(255)   not null comment   '聊天主表id',
  from_id         varchar(255)    not null comment  '发送者',
  to_id           varchar(255)    not null comment  '接收者',
  to_name         varchar(255)     null default null comment '接收者昵称',
  to_avatar       varchar(255)     null default null comment '接收者头像',
  from_window       int            null default null comment '发送方是否在窗口
  1: 在
  0: 不在',
  to_window         int            null default null comment '接收方是否在窗口
  1: 在
  0: 不在',
  unread            int            null default null comment '未读数',
  status            int            null default null comment '是否删除
  1:删除
  0:不删'
);
--聊天列表