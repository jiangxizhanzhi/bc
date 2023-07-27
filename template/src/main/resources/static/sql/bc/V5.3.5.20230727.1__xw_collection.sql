CREATE TABLE `bs_behavior_collection`
(
    `id`             int(11)       NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `resource_id`    varchar(32)   NOT NULL COMMENT '编号',
    `name`           varchar(255)  NOT NULL COMMENT '集合名称',
    `description`    varchar(2048) NULL COMMENT '简介',
    `access_os`      tinyint(1)    NOT NULL COMMENT '录制设备',
    `behavior_list`  text          NOT NULL COMMENT '行为列表',
    `behavior_count` smallint      NOT NULL COMMENT '行为数',
    `step_count`     int(11)       NOT NULL COMMENT '步骤数',
    `create_time`    datetime      NULL COMMENT '创建时间',
    `update_time`    datetime      NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE,
    INDEX `access_os` (`access_os`) USING BTREE
);