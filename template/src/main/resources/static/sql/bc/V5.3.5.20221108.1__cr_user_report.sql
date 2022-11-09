CREATE TABLE `cr_user_report`
(
    `id`              int(11) NOT NULL AUTO_INCREMENT,
    `user_id`         varchar(64)  DEFAULT NULL COMMENT '训练用户id',
    `room_id`         varchar(64)  DEFAULT NULL COMMENT '房间id',
    `user_name`       varchar(256) DEFAULT NULL COMMENT '用户名称',
    `role_type`       int(11) DEFAULT NULL COMMENT '角色类型',
    `role_name`       varchar(256) DEFAULT NULL COMMENT '角色名称',
    `completed_count` int(11) DEFAULT NULL COMMENT '任务已完成数',
    `mission_count`   int(11) DEFAULT NULL COMMENT '任务总数',
    `comment`         varchar(256) DEFAULT NULL COMMENT '评语',
    `score`           smallint(6) DEFAULT NULL COMMENT '得分',
    `create_time`     datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`     datetime     DEFAULT NULL COMMENT '更新时间',

    PRIMARY KEY (`id`) USING BTREE,
    KEY               `room_id` (`room_id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='下发任务详情';