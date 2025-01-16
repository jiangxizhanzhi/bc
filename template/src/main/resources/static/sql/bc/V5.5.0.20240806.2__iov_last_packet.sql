CREATE TABLE `iov_last_raw_packet`
(
    `id`          int(11)     NOT NULL AUTO_INCREMENT,
    `user_id`     varchar(32) NOT NULL COMMENT '用户id',
    `type`        tinyint     NOT NULL COMMENT '类型',
    `raw`         longtext    NULL COMMENT '报文',
    `create_time` datetime    NOT NULL COMMENT '',
    `update_time` datetime    NOT NULL COMMENT '',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `user_id` (`user_id`) USING BTREE,
    UNIQUE INDEX `type` (`type`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

insert into `iov_last_raw_packet`(user_id, type, raw, create_time, update_time)
values ('', 1, '', now(), now());
insert into `iov_last_raw_packet`(user_id, type, raw, create_time, update_time)
values ('', 2, '', now(), now());
insert into `iov_last_raw_packet`(user_id, type, raw, create_time, update_time)
values ('', 3, '', now(), now());