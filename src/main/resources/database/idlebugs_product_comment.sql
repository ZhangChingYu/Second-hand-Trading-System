create table product_comment
(
    id        int auto_increment
        primary key,
    father_id int            null,
    number    varchar(255)   not null comment 'product number',
    content   varchar(10000) not null,
    date      datetime       not null,
    phone     varchar(255)   null comment 'author phone'
);

INSERT INTO idlebugs.product_comment (id, father_id, number, content, date, phone) VALUES (1, 0, 'C1669690298810', 'E8AB8BE5958FE981A9E59088E6A2A8E5BDA2E8BAABE69D90E5978E3F', '2022-12-05 11:57:25', '15078818663');
INSERT INTO idlebugs.product_comment (id, father_id, number, content, date, phone) VALUES (2, 0, 'C1669690298810', 'E8AB8BE5958FE8A399E5AD90E79A84E69D90E8B3AAE698AFE4BB80E9BABC3FE5A48FE5A4A9E69C83E4B88DE69C83E4B88DE9808FE6B0A33F', '2022-12-05 11:58:54', '15059417755');
INSERT INTO idlebugs.product_comment (id, father_id, number, content, date, phone) VALUES (3, 0, 'C1669690298810', 'E8A399E5AD90E7A9BFE884ABE696B9E4BEBFE5978E3FE69C83E4B88DE69C83E58DA1E58DA1E79A843F', '2022-12-05 12:00:01', '15023591218');
INSERT INTO idlebugs.product_comment (id, father_id, number, content, date, phone) VALUES (4, 1, 'C1669690298810', 'E5908CE5958F', '2022-12-05 12:02:12', '15059417755');
INSERT INTO idlebugs.product_comment (id, father_id, number, content, date, phone) VALUES (5, 1, 'C1669690298810', 'E5908CE5958F', '2022-12-05 12:15:31', '15059417755');
INSERT INTO idlebugs.product_comment (id, father_id, number, content, date, phone) VALUES (6, 5, 'C1669690298810', 'E69C89E99C80E8A681E5908CE5958FE585A9E6ACA1E5978E3FE784A1E8AA9E', '2022-12-09 12:10:05', '15078818663');
