-- ----------------------------
-- Table structure for situation_two_dimensional_components
-- ----------------------------
CREATE TABLE `situation_two_dimensional_components`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '组件名称',
    `type`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL comment '图表类型',
    `description`    varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL comment '描述',
    `thumbnail`      varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '缩略图',
    `component_code` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '组件代码',
    `config` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '配置',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_time`    datetime NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`    datetime NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX name_uniq(name) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for situation_two_dimensional_components_category
-- ----------------------------
CREATE TABLE `situation_two_dimensional_components_category`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '二维组件类型名称',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_time`    datetime NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`    datetime NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX name_uniq(name) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of situation_two_dimensional_components_category
-- ----------------------------
INSERT INTO `situation_two_dimensional_components_category`
VALUES (1, '05b739fc686a073756399f79c34039b5', '折线图', NULL, NULL, NULL);
INSERT INTO `situation_two_dimensional_components_category`
VALUES (2, '1102f907c7650ad541c381f59c700f5f', '柱状图', NULL, NULL, NULL);
INSERT INTO `situation_two_dimensional_components_category`
VALUES (3, '2eec84346538e972a962b165e499f52d', '饼图', NULL, NULL, NULL);
INSERT INTO `situation_two_dimensional_components_category`
VALUES (4, '2f4f15de59cd2dbadc5d8a582e9f6849', '散点图', NULL, NULL, NULL);
INSERT INTO `situation_two_dimensional_components_category`
VALUES (5, '3b6e90254227833303d908336b105ade', '雷达图', NULL, NULL, NULL);
INSERT INTO `situation_two_dimensional_components_category`
VALUES (6, '446bbba739678a4e6e103e7d214d55aa', '桑基图', NULL, NULL, NULL);
INSERT INTO `situation_two_dimensional_components_category`
VALUES (7, '6753aa8cad6fa9760308871e707ea033', '漏斗图', NULL, NULL, NULL);
INSERT INTO `situation_two_dimensional_components_category`
VALUES (8, '8568759a4d514ff730ed6eb1721a2f22', '旭日图', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for situation_three_dimensional_components
-- ----------------------------
CREATE TABLE `situation_three_dimensional_components`
(
    `id`                                    int(11) NOT NULL AUTO_INCREMENT,
    `resource_id`                           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`                                  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '组件名称',
    `three_dimensional_components_category` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL comment '组件类型',
    `thumbnail`                             varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '缩略图',
    `chart_standard_document`               varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '图表标准文件 .fbx或.tiff格式',
    `create_user_id`                        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_time`                           datetime NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`                           datetime NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX name_uniq(name) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for situation_three_dimensional_components_category
-- ----------------------------
CREATE TABLE `situation_three_dimensional_components_category`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '分组名称',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_time`    datetime NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`    datetime NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for situation_park_resource_library
-- ----------------------------
CREATE TABLE `situation_park_resource_library`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '园区资源名称',
    `category`       varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL comment '资源类型',
    `thumbnail`      varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '缩略图',
    `resource_url`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '资源url',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_time`    datetime NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`    datetime NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX name_uniq(name) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for situation_park_resource_library_category
-- ----------------------------
CREATE TABLE `situation_park_resource_library_category`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '资源类型名称',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_time`    datetime NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`    datetime NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX name_uniq(name) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for situation_dynamic_effects_library
-- ----------------------------
CREATE TABLE `situation_dynamic_effects_library`
(
    `id`                       int(11) NOT NULL AUTO_INCREMENT,
    `resource_id`              varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`                     varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '动效名称',
    `category`                 varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL comment '动效类型',
    `thumbnail`                varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '缩略图',
    `source_node_dynamic_file` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '源节点动效文件',
    `create_user_id`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_time`              datetime NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`              datetime NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX name_uniq(name) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for situation_dynamic_effects_library_category
-- ----------------------------
CREATE TABLE `situation_dynamic_effects_library_category`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '动效类型名称',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_time`    datetime NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`    datetime NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX name_uniq(name) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for situation_publish_instance
-- ----------------------------
CREATE TABLE `situation_publish_instance`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '可视化大屏名称',
    `description`    longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '大屏描述',
    `plan_id`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL comment '想定方案id',
    `task_id`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL comment '任务id',
    `subject_id`     varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL comment '科目id',
    `template_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL comment '模板id',
    `category`       varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL comment '任务类型id',
    `thumbnail`      varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '缩略图',
    `status`         tinyint(4) NULL DEFAULT NULL comment '发布状态',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_time`    datetime NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`    datetime NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX name_uniq(name) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for situation_category
-- ----------------------------
CREATE TABLE `situation_category`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '态势类型',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_time`    datetime NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`    datetime NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX name_uniq(name) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of situation_category
-- ----------------------------
INSERT INTO `situation_category`
VALUES (1, '05b739fc686a073756399f79c34039b5', '红蓝对抗', NULL, NULL, NULL);
INSERT INTO `situation_category`
VALUES (2, '1102f907c7650ad541c381f59c700f5f', '实验任务', NULL, NULL, NULL);
INSERT INTO `situation_category`
VALUES (3, '2eec84346538e972a962b165e499f52d', '专项比武', NULL, NULL, NULL);


CREATE TABLE  `situation_datasource_template` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `type` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `datasource` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `connect_url` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `connect_db_url` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    UNIQUE KEY `type` (`type`) USING BTREE,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB
    AUTO_INCREMENT = 1
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ROW_FORMAT = Dynamic;


insert into `situation_datasource_template` (`id`, `type`, `datasource`, `connect_url`, `connect_db_url`) values('1','MYSQL','com.mysql.cj.jdbc.Driver','jdbc:mysql://%s:%s?autoReconnect=true&useUnicode=true&characterEncoding=utf8','jdbc:mysql://%s:%s/%s?autoReconnect=true&useUnicode=true&characterEncoding=utf8');
insert into `situation_datasource_template` (`id`, `type`, `datasource`, `connect_url`, `connect_db_url`) values('2','ORACLE','oracle.jdbc.driver.OracleDriver','jdbc:oracle:thin:@%s:%s','jdbc:oracle:thin:@%s:%s');
insert into `situation_datasource_template` (`id`, `type`, `datasource`, `connect_url`, `connect_db_url`) values('3','DB2','com.ibm.db2.jcc.DB2Driver','jdbc:db2://%s:%s','jdbc:db2://%s:%s');
insert into `situation_datasource_template` (`id`, `type`, `datasource`, `connect_url`, `connect_db_url`) values('4','SQLSERVER','com.microsoft.sqlserver.jdbc.SQLServerDriver','jdbc:sqlserver://%s:%s','jdbc:sqlserver://%s:%s');
insert into `situation_datasource_template` (`id`, `type`, `datasource`, `connect_url`, `connect_db_url`) values('5','HIVE','org.apache.hadoop.hive.jdbc.HiveDriver','jdbc:hive2://%s:%s','jdbc:hive2://%s:%s/%s');


CREATE TABLE  `situation_datasource_record` (
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数据库连接名',
                                     `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数据库类型',
                                     `port` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '端口',
                                     `connect_address` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '连接地址',
                                     `user_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
                                     `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
                                     `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人',
                                     `create_time` datetime DEFAULT NULL,
                                     `update_time` datetime DEFAULT NULL,
                                     UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
                                     PRIMARY KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB
    AUTO_INCREMENT = 1
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ROW_FORMAT = Dynamic;

CREATE TABLE  `situation_api_request` (
                               `id` int(10) NOT NULL AUTO_INCREMENT,
                               `resource_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '资源id',
                               `name` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT 'api名称',
                               `description` longtext CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'api描述',
                               `request_url` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT 'url',
                               `request_method` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '方法类型',
                               `request_params` longtext CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '参数',
                               `request_headers` longtext CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '请求头',
                               `response_params` longtext CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '返回参数',
                               `create_user_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '创建人',
                               `create_time` datetime DEFAULT NULL,
                               `update_time` datetime DEFAULT NULL,
                                UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
                                PRIMARY KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB
    AUTO_INCREMENT = 1
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ROW_FORMAT = Dynamic;

CREATE TABLE `situation_dashboard_template` (
                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                      `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板名称',
                                      `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '看板类型',
                                      `level` int(10)  DEFAULT NULL COMMENT '模板等级',
                                      `thumbnail` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '缩略图url',
                                      `height` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                      `width` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                      `components_setting` longtext CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '组件配置',
                                      `bg_color` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                      `refresh` int(10) DEFAULT NULL,
                                      `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
                                      `create_time` datetime DEFAULT NULL,
                                      `update_time` datetime DEFAULT NULL,
                                      PRIMARY KEY (`id`) USING BTREE,
                                      UNIQUE KEY `resourceId` (`resource_id`) USING BTREE
) ENGINE=InnoDB
    AUTO_INCREMENT = 1
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ROW_FORMAT = Dynamic;

CREATE TABLE `situation_dashboard_components_setting` (
                                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                                `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                `type` int(10) NOT NULL,
                                                `datasource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                                `datasource_setting` longtext CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                                `components_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                                `component_setting` longtext CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                                `create_time` datetime DEFAULT NULL,
                                                `update_time` datetime DEFAULT NULL,
                                                PRIMARY KEY (`id`) USING BTREE,
                                                UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB
    AUTO_INCREMENT = 1
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ROW_FORMAT = Dynamic;

CREATE TABLE `situation_common_components` (
                                 `id` int(10) NOT NULL AUTO_INCREMENT,
                                 `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                 `component_type` int(10) NOT NULL COMMENT '组件类型',
                                 `type` int(10) NOT NULL COMMENT 'gis类型',
                                 `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'gis名称',
                                 `img` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '背景图url',
                                 `url` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'url',
                                 `create_time` datetime DEFAULT NULL,
                                 `update_time` datetime DEFAULT NULL,
                                 PRIMARY KEY (`id`) USING BTREE,
                                 UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT = 1
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ROW_FORMAT = Dynamic;

CREATE TABLE `situation_dynamic_effects_mapping` (
                                                     `id` int(10) NOT NULL AUTO_INCREMENT,
                                                     `resource_id` varchar(64) NOT NULL,
                                                     `name` varchar(512) NOT NULL COMMENT '动效映射名称',
                                                     `category` varchar(64) NOT NULL COMMENT '动效类型',
                                                     `attack_type` varchar(64) NOT NULL COMMENT '攻击类型',
                                                     `source_node_dynamic` varchar(64) NOT NULL COMMENT '源动效',
                                                     `target_node_dynamic` varchar(64) NOT NULL COMMENT '目标动效',
                                                     `focus` int(10) NOT NULL COMMENT '聚焦',
                                                     `color` varchar(64) NOT NULL COMMENT '颜色',
                                                     `line` varchar(64) NOT NULL COMMENT '线形',
                                                     `line_size` int(10) NOT NULL COMMENT '线段大小',
                                                     `create_user_id` varchar(64) DEFAULT NULL,
                                                     `create_time` datetime DEFAULT NULL,
                                                     `update_time` datetime DEFAULT NULL,
                                                     PRIMARY KEY (`id`) USING BTREE,
                                                     UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
                                                     UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT = 1
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ROW_FORMAT = Dynamic;

CREATE TABLE `situation_dashboard_template_ref` (
                                                    `id` INT(11) NOT NULL AUTO_INCREMENT,
                                                    `resource_id` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                    `name` VARCHAR(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板名称',
                                                    `type` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '看板类型',
                                                    `level` INT(10)  DEFAULT NULL COMMENT '模板等级',
                                                    `thumbnail` VARCHAR(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '缩略图url',
                                                    `height` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                                    `width` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                                    `components_setting` LONGTEXT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '组件配置',
                                                    `bg_color` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                                    `refresh` INT(10) DEFAULT NULL,
                                                    `create_user_id` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
                                                    `create_time` DATETIME DEFAULT NULL,
                                                    `update_time` DATETIME DEFAULT NULL,
                                                    PRIMARY KEY (`id`) USING BTREE,
                                                    UNIQUE KEY `resourceId` (`resource_id`) USING BTREE
) ENGINE=INNODB
    AUTO_INCREMENT = 1
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_sub_range
-- ----------------------------
CREATE TABLE `situation_sub_range`  (
                                        `id` int NOT NULL AUTO_INCREMENT,
                                        `sub_range_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                        `sub_range_name` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                        `sub_range_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                        `location` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                        `region` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                        `address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                        `longitude` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `latitude` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `station_count` int NULL DEFAULT 0,
                                        `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `create_time` datetime NULL DEFAULT NULL,
                                        `update_time` datetime NULL DEFAULT NULL,
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE INDEX `sub_range_id`(`sub_range_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE `situation_event_type_context` (
                                                `id` INT NOT NULL AUTO_INCREMENT,
                                                `code` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                `name` VARCHAR(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                `parent_node` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                                `create_time` DATETIME DEFAULT NULL,
                                                `update_time` DATETIME DEFAULT NULL,
                                                PRIMARY KEY (`id`) USING BTREE,
                                                UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=INNODB AUTO_INCREMENT = 1
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_gis_mark
-- ----------------------------
CREATE TABLE `situation_gis_mark`  (
                                       `id` int NOT NULL AUTO_INCREMENT,
                                       `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                       `config` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                       `dashboard_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                       `create_time` datetime NULL DEFAULT current_timestamp,
                                       `update_time` datetime NULL DEFAULT NULL,
                                       `delete_time` datetime NULL DEFAULT NULL,
                                       PRIMARY KEY (`id`) USING BTREE,
                                       UNIQUE INDEX `resource_id`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_center
-- ----------------------------
CREATE TABLE `situation_center`  (
                                     `id` int NOT NULL AUTO_INCREMENT,
                                     `center_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `center_name` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `location` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `region` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `longitude` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `latitude` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
                                     `create_time` datetime NULL DEFAULT NULL,
                                     `update_time` datetime NULL DEFAULT NULL,
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `center_id`(`center_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_station
-- ----------------------------
CREATE TABLE `situation_station`  (
                                      `id` int NOT NULL AUTO_INCREMENT,
                                      `sub_range_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `station_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `station_name` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `station_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `location` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `region` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `longitude` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `latitude` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
                                      `create_time` datetime NULL DEFAULT NULL,
                                      `update_time` datetime NULL DEFAULT NULL,
                                      PRIMARY KEY (`id`) USING BTREE,
                                      UNIQUE INDEX `station_id`(`station_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_dashboard_attribute
-- ----------------------------
CREATE TABLE `situation_dashboard_attribute`  (
                                                  `id` int NOT NULL AUTO_INCREMENT,
                                                  `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                  `dashboard_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                  `level` int NOT NULL,
                                                  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                  `sub_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                  `sub_range_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                  `sub_range_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                  `task_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                  `task_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                  `task_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                  `subject_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                  `subject_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                  `start_time` datetime NULL DEFAULT NULL,
                                                  `topo_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                  `refresh` int NULL DEFAULT NULL,
                                                  `longitude` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                  `latitude` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                  `altitude` int(10) UNSIGNED ZEROFILL NULL DEFAULT NULL,
                                                  `create_time` datetime NULL DEFAULT NULL,
                                                  `update_time` datetime NULL DEFAULT NULL,
                                                  PRIMARY KEY (`id`) USING BTREE,
                                                  UNIQUE INDEX `dashboard_id`(`dashboard_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;