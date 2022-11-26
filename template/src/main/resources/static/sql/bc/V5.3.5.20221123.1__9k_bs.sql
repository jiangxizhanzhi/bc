CREATE TABLE `bs_user_model`
(
    `id`                int(11)       NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `resource_id`       varchar(32)   NOT NULL COMMENT '编号',
    `user_name`         varchar(255)  NOT NULL COMMENT '用户名称',
    `description`       varchar(2048) NULL COMMENT '简介',
    `user_type_id`      varchar(32)   NOT NULL COMMENT '用户类型',
    `access_os`         tinyint(1)    NOT NULL COMMENT '访问设备',
    `access_config`     tinyint(1)    NOT NULL COMMENT '访问配置',
    `behavior_list`     text          NOT NULL DEFAULT '[]' COMMENT '访问行为',
    `access_habit`      varchar(2048) NOT NULL DEFAULT '[]' COMMENT '访问习惯',
    `bandwidth_limited` tinyint(1)    NOT NULL DEFAULT 0 COMMENT '是否控制带宽',
    `bandwidth`         smallint(6)   NULL COMMENT '访问带宽',
    `create_time`       datetime      NULL COMMENT '创建时间',
    `update_time`       datetime      NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
);

CREATE TABLE bs_user_simulator
(
    `id`                int(11)       NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `resource_id`       varchar(32)   NOT NULL COMMENT '编号',
    `user_name`         varchar(255)  NOT NULL COMMENT '用户名称',
    `user_type_id`      varchar(32)   NOT NULL COMMENT '用户类型id',
    `user_type`         varchar(255)  NOT NULL COMMENT '用户类型名称',
    `start_time`        time          NULL COMMENT '开始时间',
    `end_time`          time          NULL COMMENT '结束时间',
    `access_os`         tinyint(1)    NOT NULL COMMENT '访问设备',
    `access_config`     tinyint(1)    NOT NULL COMMENT '访问配置',
    `behavior_list`     text          NULL     DEFAULT '[]' COMMENT '访问行为',
    `behavior_variable` text          NULL     DEFAULT '' COMMENT '行为变量',
    `behavior_count`    int(11)       NOT NULL COMMENT '行为数量',
    `access_habit`      varchar(2048) NOT NULL DEFAULT '[]' COMMENT '访问习惯',
    `bandwidth_limited` tinyint(1)    NOT NULL DEFAULT 0 COMMENT '是否控制带宽',
    `bandwidth`         smallint(6)   NULL     DEFAULT NULL COMMENT '访问带宽',
    `executed_index`    int(11)       NULL     DEFAULT NULL COMMENT '最后已执行编号（倒序）',
    `status`            tinyint(1)    NOT NULL COMMENT '执行状态',
    `device_id`         varchar(255)  NULL     DEFAULT NULL COMMENT '执行设备id',
    `device_name`       varchar(255)  NULL     DEFAULT NULL COMMENT '执行设备名称',
    `device_ip`         varchar(255)  NULL     DEFAULT NULL COMMENT '执行设备ip',
    `is_entity`         boolean       NOT NULL COMMENT '执行设备是否为实体',
    `scene_id`          varchar(32)   NOT NULL COMMENT '场景id',
    `scene_name`        varchar(255)  NOT NULL COMMENT '场景名称',
    `user_model_id`     varchar(32)   NOT NULL COMMENT '用户行为模型id',
    `create_time`       datetime      NULL     DEFAULT NULL COMMENT '创建时间',
    `update_time`       datetime      NULL     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
);

CREATE TABLE `bs_user_type`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(32)  NOT NULL,
    `name`        varchar(255) NOT NULL,
    `create_time` datetime     NULL DEFAULT NULL,
    `update_time` datetime     NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
);

CREATE TABLE `bs_device_occupation`
(
    `id`           int(11)      NOT NULL AUTO_INCREMENT,
    `device_id`    varchar(255) NOT NULL COMMENT '实体设备id',
    `device_type`  tinyint(1)   NOT NULL COMMENT '实体设备类型',
    `port`         varchar(6)   NOT NULL COMMENT '端口号',
    `is_occupied`  boolean      NOT NULL DEFAULT 0 COMMENT '是否被占用',
    `scene_id`     varchar(32)  NULL COMMENT '设备所在场景id',
    `simulator_id` varchar(32)  NULL COMMENT '用户行为模拟id',
    `create_time`  datetime     NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`  datetime     NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `device_id` (`device_id`) USING BTREE,
    UNIQUE INDEX `simulator_id` (`simulator_id`) USING BTREE
);

-- 用户行为模拟 -设备类型
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 0, 39, '全部', 'All');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 39, '默认仪表', 'Default');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 39, 'IXIA仪表', 'IXIA');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 39, '龙测仪表', 'Dragon Test');
-- 用户行为模拟 -网络类型
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 40, '公网', 'Internet');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 40, '场景内网', 'Intranet');
-- 用户行为模拟 -访问习惯
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 42, 'Windows', 'Windows');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 42, 'Linux', 'Linux');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 42, 'Mac', 'Mac');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 4, 42, 'iOS', 'iOS');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 5, 42, 'Android', 'Android');
-- 用户行为模拟 -访问配置
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 43, '自定义', 'Customer');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 43, '随机', 'Random');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 43, '正态分布', 'Normal Distribution');
-- 用户行为模拟 -前台流量状态
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 0, 44, '未开始', 'Not Started');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 44, '运行中', 'Running');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 44, '暂停', 'Paused');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 44, '已结束', 'Stopped');