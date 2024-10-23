ALTER TABLE `oj_course`
    CHANGE COLUMN `job` `role` tinyint(4) NOT NULL DEFAULT 1;

CREATE TABLE `space_strategy`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)  NOT NULL,
    `name`        varchar(255) NOT NULL,
    `user_id`     varchar(64)  NOT NULL,
    `view`        int(11)      NOT NULL,
    `content`     text         NULL DEFAULT NULL,
    `step`        text         NULL DEFAULT NULL,
    `example`     text         NULL DEFAULT NULL,
    `script`      text         NULL DEFAULT NULL,
    `script_type` tinyint      NULL,
    `frame_id`    varchar(64)  NOT NULL,
    `create_time` datetime          DEFAULT NULL,
    `update_time` datetime          DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE,
    INDEX `frame_id` (`frame_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_strategy_frame`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)  NOT NULL,
    `name`        varchar(255) NOT NULL,
    `builtin`     tinyint      NULL,
    `parent`      varchar(64)  NOT NULL,
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


INSERT INTO `space_strategy_frame` (`id`, `resource_id`, `name`, `builtin`, `parent`, `create_time`, `update_time`)
VALUES (NULL, '47d82955780346d98f92250e1e4ecd10', '操作系统安全策略', 1, 'default', now(), now());
INSERT INTO `space_strategy_frame` (`id`, `resource_id`, `name`, `builtin`, `parent`, `create_time`, `update_time`)
VALUES (NULL, 'f41c777715754d3c99c76f17f2b78975', '数据库安全策略', 1, 'default', now(), now());
INSERT INTO `space_strategy_frame` (`id`, `resource_id`, `name`, `builtin`, `parent`, `create_time`, `update_time`)
VALUES (NULL, '52e001b892ae4d38aced84842918a4ba', 'Web中间件安全策略', 1, 'default', now(), now());
INSERT INTO `space_strategy_frame` (`id`, `resource_id`, `name`, `builtin`, `parent`, `create_time`, `update_time`)
VALUES (NULL, '8114e09e4dee4ca6b15eaa4772f36bf9', '交换机策略', 1, 'default', now(), now());
INSERT INTO `space_strategy_frame` (`id`, `resource_id`, `name`, `builtin`, `parent`, `create_time`, `update_time`)
VALUES (NULL, 'e0fafbc784c9486c967ab0952c0c3a3f', '路由器策略', 1, 'default', now(), now());
INSERT INTO `space_strategy_frame` (`id`, `resource_id`, `name`, `builtin`, `parent`, `create_time`, `update_time`)
VALUES (NULL, 'da6a5308594e4cbab220d136bf1002d9', '大数据平台安全策略', 1, 'default', now(), now());
INSERT INTO `space_strategy_frame` (`id`, `resource_id`, `name`, `builtin`, `parent`, `create_time`, `update_time`)
VALUES (NULL, '02ef1b4cf4d94a2bb9d2312defd3ed6f', '防病毒系统安全策略', 1, 'default', now(), now());
INSERT INTO `space_strategy_frame` (`id`, `resource_id`, `name`, `builtin`, `parent`, `create_time`, `update_time`)
VALUES (NULL, '5a4243d5c16b4376ab7a63da386ea993', '终端安全系统安全策略', 1, 'default', now(), now());
INSERT INTO `space_strategy_frame` (`id`, `resource_id`, `name`, `builtin`, `parent`, `create_time`, `update_time`)
VALUES (NULL, '518eccd483a94d218b4994de4c714557', '边界防护设备安全策略', 1, 'default', now(), now());
INSERT INTO `space_strategy_frame` (`id`, `resource_id`, `name`, `builtin`, `parent`, `create_time`, `update_time`)
VALUES (NULL, 'ca6efb4d930b438badc1649f098eb966', '入侵检测系统安全策略', 1, 'default', now(), now());


INSERT INTO `cr_constants_config`
VALUES (NULL, NULL, 1, 44, 'Shell', 'Course');
INSERT INTO `cr_constants_config`
VALUES (NULL, NULL, 2, 44, 'TXT', 'Class');

INSERT INTO `cr_constants_config`
VALUES (NULL, NULL, 1, 45, '前台指挥线', 'Course');
INSERT INTO `cr_constants_config`
VALUES (NULL, NULL, 2, 45, '中台指挥线', 'Course');
INSERT INTO `cr_constants_config`
VALUES (NULL, NULL, 3, 45, '后台执行线', 'Course');
INSERT INTO `cr_constants_config`
VALUES (NULL, NULL, 4, 45, '中台执行线', 'Course');

INSERT INTO `cr_constants_config`
VALUES (NULL, NULL, 1, 46, '驱动', 'Course');
INSERT INTO `cr_constants_config`
VALUES (NULL, NULL, 2, 46, '文件', 'Course');
INSERT INTO `cr_constants_config`
VALUES (NULL, NULL, 3, 46, '进程', 'Course');

INSERT INTO `cr_constants_config`
VALUES (NULL, NULL, 1, 47, '控制类', 'Course');


INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 1, 48, '事前阶段', 'Course');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 2, 48, '事中阶段', 'Course');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 3, 48, '事后阶段', 'Course');

CREATE TABLE `space_tactic_attack`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)  NOT NULL,
    `name`        varchar(255) NOT NULL,
    `tactic_id`   varchar(64)  NOT NULL,
    `category_id` varchar(64)  NULL,
    `role`        tinyint(4)   NULL,
    `tag`         text         NULL,
    `description` text         NULL DEFAULT NULL,
    `scene`       text         NULL DEFAULT NULL,
    `feature`     text         NULL DEFAULT NULL,
    `action`      text         NULL DEFAULT NULL,
    `tool`        text         NULL DEFAULT NULL,
    `create_time` datetime          DEFAULT NULL,
    `update_time` datetime          DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_tactic_defense`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)  NOT NULL,
    `name`        varchar(255) NOT NULL,
    `tactic_id`   varchar(64)  NOT NULL,
    `category_id` varchar(64)  NULL,
    `role`        tinyint(4)   NULL,
    `tag`         text         NULL,
    `description` text         NULL DEFAULT NULL,
    `scene`       text         NULL DEFAULT NULL,
    `monitor`     text         NULL DEFAULT NULL,
    `analyze`     text         NULL DEFAULT NULL,
    `tool`        text         NULL DEFAULT NULL,
    `create_time` datetime          DEFAULT NULL,
    `update_time` datetime          DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_tactic_attack_category`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)  NOT NULL,
    `name`        varchar(255) NOT NULL,
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


CREATE TABLE `space_tactic_defense_category`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)  NOT NULL,
    `name`        varchar(255) NOT NULL,
    `stage`       tinyint(4)   NULL,
    `cover`       varchar(255) NULL,
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;



CREATE TABLE `space_apt_feature`
(
    `id`             int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64)  NOT NULL,
    `name`           varchar(255) NULL,
    `alias`          text         NULL COMMENT '别名',
    `attacker_type`  tinyint(4)   NULL COMMENT '攻击者类型',
    `skill`          tinyint(4)   NULL COMMENT '技术能力',
    `source`         varchar(255) NULL COMMENT '疑似来源',
    `first_time`     varchar(255) NULL COMMENT '最早活动时间',
    `latest_time`    varchar(255) NULL COMMENT '最近活动时间',
    `effected`       text         NULL COMMENT '影响国家和地区',
    `industry`       varchar(255) NULL COMMENT '影响行业',
    `method`         varchar(255) NULL COMMENT '攻击方式',
    `frequency`      tinyint(4)   NULL COMMENT '攻击频率',
    `language`       varchar(255) NULL COMMENT '常用语言',
    `organization`   text         NULL COMMENT '组织概述',
    `tactic`         text         NULL COMMENT '技战法',
    `history_report` text         NULL COMMENT '历史报告',
    `create_time`    datetime DEFAULT NULL,
    `update_time`    datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_apt_threat_detect_rule`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)  NOT NULL,
    `name`        varchar(255) NULL,
    `type`        tinyint(4)   NULL COMMENT '情报类型',
    `object`      varchar(255) NULL COMMENT '情报对象',
    `level`       tinyint(4)   NULL COMMENT '信誉等级',
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_event_integration_rule`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)  NOT NULL,
    `name`        varchar(255) NULL,
    `cycle`       int(11)      NULL COMMENT '统计周期',
    `threshold`   int(11)      NULL COMMENT '告警阈值',
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_finger_print`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)  NOT NULL,
    `name`        varchar(255) NULL,
    `type`        tinyint(4)   NULL COMMENT '类型',
    `content`     text         NULL COMMENT '指纹列表',
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_ioc_threat`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)  NOT NULL,
    `name`        varchar(255) NULL,
    `type`        tinyint(4)   NULL COMMENT '类型',
    `ioc`         text         NULL COMMENT 'IOC列表',
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_loaded_file_threat_rule`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)  NOT NULL,
    `name`        varchar(255) NULL,
    `content`     varchar(255) NULL COMMENT '规则内容',
    `description` varchar(255) NULL COMMENT '规则描述',
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_round_mechanism_rule`
(
    `id`                 int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id`        varchar(64)  NOT NULL,
    `name`               varchar(255) NULL,
    `description`        varchar(255) NULL COMMENT '简述',
    `round_length`       int(11)      NULL COMMENT '每轮时长',
    `init_score`         int(11)      NULL COMMENT '初始分值',
    `max_reset_time`     int(11)      NULL COMMENT '重置最大次数',
    `min_reset_interval` int(11)      NULL COMMENT '重置最小间隔',
    `create_time`        datetime DEFAULT NULL,
    `update_time`        datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_score_ranking_rule`
(
    `id`               int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id`      varchar(64)  NOT NULL,
    `name`             varchar(255) NULL,
    `description`      varchar(255) NULL COMMENT '规则简述',
    `task_score_type`  tinyint(4)   NULL COMMENT '单任务记分',
    `round_score_type` tinyint(4)   NULL COMMENT '单轮次记分',
    `sum_score_type`   tinyint(4)   NULL COMMENT '累计记分',
    `first_blood`      int(11)      NULL COMMENT '一血奖励',
    `second_blood`     int(11)      NULL COMMENT '二血奖励',
    `third_blood`      int(11)      NULL COMMENT '三血奖励',
    `create_time`      datetime DEFAULT NULL,
    `update_time`      datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_source_analyze_rule`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)  NOT NULL,
    `name`        varchar(255) NULL,
    `rule`        text         NULL COMMENT '规则列表',
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_threat_image_rule`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)  NOT NULL,
    `name`        varchar(255) NULL,
    `type`        tinyint(4)   NULL COMMENT '威胁类型',
    `level`       tinyint(4)   NULL COMMENT '威胁等级',
    `asset_type`  tinyint(4)   NULL COMMENT '受害资产类型',
    `information` text         NULL COMMENT '威胁情报',
    `result`      tinyint(4)   NULL COMMENT '结果',
    `tag`         varchar(255) NULL COMMENT '标签',
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_traffic_detect_rule`
(
    `id`           int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id`  varchar(64)  NOT NULL,
    `name`         varchar(255) NULL,
    `rule_content` text         NULL COMMENT '规则内容',
    `rule_id`      varchar(255) NULL COMMENT '规则编号',
    `rule_info`    text         NULL COMMENT '规则信息',
    `threat_level` tinyint(4)   NULL COMMENT '威胁等级',
    `create_time`  datetime DEFAULT NULL,
    `update_time`  datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_traffic_extraction_rule`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)  NOT NULL,
    `name`        varchar(255) NULL,
    `order`       varchar(255) NULL COMMENT '顺序',
    `filename`    varchar(255) NULL COMMENT '文件名',
    `route`       varchar(255) NULL COMMENT '路径',
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_violation_judge_rule`
(
    `id`           int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id`  varchar(64)  NOT NULL,
    `name`         varchar(255) NULL,
    `interval`     int(11)      NULL COMMENT '提交答案间隔',
    `port_check`   tinyint(4)   NULL COMMENT '端口检测',
    `target_check` tinyint(4)   NULL COMMENT '攻击目标检测',
    `create_time`  datetime DEFAULT NULL,
    `update_time`  datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_warning_dictionary`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)  NOT NULL,
    `name`        varchar(255) NULL,
    `rule`        text         NULL COMMENT '规则列表',
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


CREATE TABLE `space_base_algorithm`
(
    `id`          int(11)       NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)   NOT NULL,
    `name`        varchar(255)  NULL,
    `description` varchar(2048) NULL COMMENT '简述',
    `type`        tinyint(4)    NULL COMMENT '类型',
    `code_type`   tinyint(4)    NULL COMMENT '代码语言类型',
    `code`        text          NULL COMMENT '代码',
    `parameter`   text          NULL COMMENT '参数',
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;