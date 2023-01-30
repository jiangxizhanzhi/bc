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
    `config`         longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '配置',
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
    `resource_url`   varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '资源url',
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
    `source_node_dynamic_dir`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '动效文件解压目录',
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
    `id`             int                                                     NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '可视化大屏名称',
    `description`    longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '大屏描述',
    `attribute_id`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `scheme_id`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '想定方案id',
    `sub_range_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `sub_range_id`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `task_id`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务ID',
    `task_name`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名称',
    `subject_id`     varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科目ID',
    `subject_name`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科目名称',
    `template_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板id',
    `thumbnail`      varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '缩略图',
    `third_url`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '三方靶标url',
    `status`         tinyint                                                 NOT NULL COMMENT '发布状态',
    `public_status`  tinyint                                                 NOT NULL,
    `edit`           tinyint                                                 NOT NULL,
    `category`       varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_time`    datetime NULL DEFAULT current_timestamp,
    `update_time`    datetime NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `name_uniq`(`name` ASC) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- Table structure for situation_category
-- ----------------------------
CREATE TABLE `situation_category`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '态势类型',
    `task_type`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务类型',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_time`    datetime NULL DEFAULT current_timestamp,
    `update_time`    datetime NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `name_uniq`(`name` ASC) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of situation_category
-- ----------------------------
INSERT INTO `situation_category`
VALUES (1, 'a4b3555767d14f01b88ee4f8ca471a3e', '综合态势', '', NULL, '2022-12-24 20:44:09', '2022-12-24 20:44:09');
INSERT INTO `situation_category`
VALUES (2, '2353d9aef8d24c3abe624c7d77426017', '性能鉴定', 'SY01', NULL, '2023-01-02 10:18:45', '2023-01-02 10:21:43');
INSERT INTO `situation_category`
VALUES (3, 'fd3106fcfd604e7ead952ec401cd7a68', '性能认证', 'SY02', NULL, '2023-01-02 10:19:06', '2023-01-02 10:21:46');
INSERT INTO `situation_category`
VALUES (4, '1102f907c7650ad541c381f59c700f5f', '作战试验', 'SY03', NULL, '2023-01-02 10:21:29', '2023-01-02 10:21:37');
INSERT INTO `situation_category`
VALUES (5, '77c2cb9b6f2e4defbaf8a225fb249ce5', '其它试验', 'SY99', NULL, '2023-01-02 10:21:29', '2023-01-02 10:21:37');
INSERT INTO `situation_category`
VALUES (6, '05b739fc686a073756399f79c34039b5', '红蓝对抗', 'XL03', NULL, '2023-01-02 10:21:31', '2023-01-02 10:21:34');
INSERT INTO `situation_category`
VALUES (7, 'f6842d1d76b14217abb9c118d9c5cbfd', '夺旗竞赛', 'XL02', NULL, '2023-01-02 10:19:47', '2023-01-02 10:21:49');
INSERT INTO `situation_category`
VALUES (8, 'c71399e5569b4c2dba78c0c1e1fbae91', '自主训练', 'XL04', NULL, '2023-01-02 10:20:20', '2023-01-02 10:21:51');
INSERT INTO `situation_category`
VALUES (9, '2eec84346538e972a962b165e499f52d', '专项比武', 'XL01', NULL, '2023-01-02 10:21:26', '2023-01-02 10:21:39');
INSERT INTO `situation_category`
VALUES (10, '1163d8cf19a1497abc971e0786798a5d', '其它训练', 'XL99', NULL, '2023-01-02 10:21:42', '2023-01-02 10:21:54');
INSERT INTO `situation_category`
VALUES (11, '26c4cf546d1f4557ac29f9fa8e8a6c3a', '三方靶标', NULL, NULL, '2022-12-27 21:15:55', '2023-01-02 10:21:41');


CREATE TABLE `situation_datasource_template`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `type`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `datasource`     varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `connect_url`    varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `connect_db_url` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    UNIQUE KEY `type` (`type`) USING BTREE,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB
    AUTO_INCREMENT = 1
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ROW_FORMAT = Dynamic;


insert into `situation_datasource_template` (`id`, `type`, `datasource`, `connect_url`, `connect_db_url`)
values ('1', 'MYSQL', 'com.mysql.cj.jdbc.Driver',
        'jdbc:mysql://%s:%s?autoReconnect=true&useUnicode=true&characterEncoding=utf8',
        'jdbc:mysql://%s:%s/%s?autoReconnect=true&useUnicode=true&characterEncoding=utf8');
insert into `situation_datasource_template` (`id`, `type`, `datasource`, `connect_url`, `connect_db_url`)
values ('2', 'ORACLE', 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin:@%s:%s', 'jdbc:oracle:thin:@%s:%s');
insert into `situation_datasource_template` (`id`, `type`, `datasource`, `connect_url`, `connect_db_url`)
values ('3', 'DB2', 'com.ibm.db2.jcc.DB2Driver', 'jdbc:db2://%s:%s', 'jdbc:db2://%s:%s');
insert into `situation_datasource_template` (`id`, `type`, `datasource`, `connect_url`, `connect_db_url`)
values ('4', 'SQLSERVER', 'com.microsoft.sqlserver.jdbc.SQLServerDriver', 'jdbc:sqlserver://%s:%s',
        'jdbc:sqlserver://%s:%s');
insert into `situation_datasource_template` (`id`, `type`, `datasource`, `connect_url`, `connect_db_url`)
values ('5', 'HIVE', 'org.apache.hadoop.hive.jdbc.HiveDriver', 'jdbc:hive2://%s:%s', 'jdbc:hive2://%s:%s/%s');


CREATE TABLE `situation_datasource_record`
(
    `id`              int(11) NOT NULL AUTO_INCREMENT,
    `resource_id`     varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `name`            varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数据库连接名',
    `type`            varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '数据库类型',
    `port`            varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '端口',
    `connect_address` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '连接地址',
    `user_name`       varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '用户名',
    `password`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '密码',
    `create_user_id`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  DEFAULT NULL COMMENT '创建人',
    `create_time`     datetime                                                DEFAULT NULL,
    `update_time`     datetime                                                DEFAULT NULL,
    UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
    PRIMARY KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB
    AUTO_INCREMENT = 1
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ROW_FORMAT = Dynamic;

CREATE TABLE `situation_api_request`
(
    `id`              int                                                     NOT NULL AUTO_INCREMENT,
    `resource_id`     varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NOT NULL COMMENT '资源id',
    `name`            varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT 'api名称',
    `description`     text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT 'api描述',
    `request_url`     varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NOT NULL COMMENT 'url',
    `request_method`  varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NOT NULL COMMENT '方法类型',
    `request_params`  text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '参数',
    `request_headers` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '请求头',
    `response_key`    varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '返回参数key值',
    `response_params` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '返回参数',
    `create_user_id`  varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NOT NULL COMMENT '创建人',
    `create_time`     datetime NULL DEFAULT NULL,
    `update_time`     datetime NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX             `id`(`id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for situation_dashboard_template
-- ----------------------------
CREATE TABLE `situation_dashboard_template`
(
    `id`                 int                                                     NOT NULL AUTO_INCREMENT,
    `resource_id`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`               varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板名称',
    `type`               varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '看板类型',
    `level`              int NULL DEFAULT NULL COMMENT '模板等级',
    `thumbnail`          varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '缩略图url',
    `height`             varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `width`              varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `components_setting` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '组件配置',
    `bg_color`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `topic`              varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主题',
    `view_angle`         text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
    `edit_status`        tinyint(1) NOT NULL DEFAULT 1 COMMENT '模板编辑权限',
    `refresh`            int NULL DEFAULT NULL,
    `create_user_id`     varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
    `create_time`        datetime NULL DEFAULT NULL,
    `update_time`        datetime NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resourceId`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_dashboard_components_setting
-- ----------------------------
CREATE TABLE `situation_dashboard_components_setting`
(
    `id`                 int                                                    NOT NULL AUTO_INCREMENT,
    `resource_id`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `type`               varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `component_index`    int                                                    NOT NULL DEFAULT 0,
    `datasource_id`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `datasource_setting` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
    `components_id`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `component_setting`  longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
    `create_time`        datetime NULL DEFAULT NULL,
    `update_time`        datetime NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE `situation_common_components`
(
    `id`             int(10) NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `component_type` int(10) NOT NULL COMMENT '组件类型',
    `type`           int(10) NOT NULL COMMENT 'gis类型',
    `name`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'gis名称',
    `img`            varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '背景图url',
    `url`            varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'url',
    `create_time`    datetime DEFAULT NULL,
    `update_time`    datetime DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT = 1
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ROW_FORMAT = Dynamic;

CREATE TABLE `situation_dynamic_effects_mapping`
(
    `id`                  int(10) NOT NULL AUTO_INCREMENT,
    `resource_id`         varchar(64)  NOT NULL,
    `name`                varchar(512) NOT NULL COMMENT '动效映射名称',
    `category`            varchar(64)  NOT NULL COMMENT '动效类型',
    `attack_type`         varchar(64)  NOT NULL COMMENT '攻击类型',
    `source_node_dynamic` varchar(64)  NOT NULL COMMENT '源动效',
    `target_node_dynamic` varchar(64)  NOT NULL COMMENT '目标动效',
    `focus`               boolean      NOT NULL COMMENT '聚焦',
    `color`               varchar(64)  NOT NULL COMMENT '颜色',
    `line`                varchar(64)  NOT NULL COMMENT '线形',
    `line_size`           int(10) NOT NULL COMMENT '线段大小',
    `create_user_id`      varchar(64) DEFAULT NULL,
    `create_time`         datetime    DEFAULT NULL,
    `update_time`         datetime    DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
    UNIQUE KEY `name` (`name`) USING BTREE,
    INDEX                 `attack_type` (`attack_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT = 1
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for situation_dashboard_template_ref
-- ----------------------------
CREATE TABLE `situation_dashboard_template_ref`
(
    `id`                 int                                                     NOT NULL AUTO_INCREMENT,
    `resource_id`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`               varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板名称',
    `type`               varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '看板类型',
    `level`              int NULL DEFAULT NULL COMMENT '模板等级',
    `thumbnail`          varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '缩略图url',
    `height`             varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `width`              varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `components_setting` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '组件配置',
    `bg_color`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `topic`              varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主题',
    `view_angle`         text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
    `edit_status`        tinyint(1) NOT NULL DEFAULT 1 COMMENT '模板编辑权限',
    `refresh`            int NULL DEFAULT NULL,
    `create_user_id`     varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
    `create_time`        datetime NULL DEFAULT NULL,
    `update_time`        datetime NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resourceId`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE `situation_event_type`
(
    `id`                 INT                                                     NOT NULL AUTO_INCREMENT,
    `code`               VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`               VARCHAR(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `parent_node`        VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `source_action_type` TINYINT(1) NULL COMMENT '源动作类型',
    `target_action_type` TINYINT(1) NULL COMMENT '目标动作类型',
    `create_time`        DATETIME                                               DEFAULT NULL,
    `update_time`        DATETIME                                               DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=INNODB AUTO_INCREMENT = 1
    CHARACTER SET = utf8
    COLLATE = utf8_general_ci
    ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_gis_mark
-- ----------------------------
CREATE TABLE `situation_gis_mark`
(
    `id`           int                                                    NOT NULL AUTO_INCREMENT,
    `resource_id`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `config`       text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `dashboard_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `create_time`  datetime NULL DEFAULT current_timestamp,
    `update_time`  datetime NULL DEFAULT NULL,
    `delete_time`  datetime NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_dashboard_attribute
-- ----------------------------
CREATE TABLE `situation_dashboard_attribute`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `dashboard_type` tinyint                                                 NOT NULL COMMENT '大屏类型 1-定制 2-自编',
    `dashboard_mode` tinyint                                                 NOT NULL COMMENT '大屏模式 1-gis/topo 2-gis 3-topo',
    `level`          tinyint                                                 NOT NULL COMMENT '大屏层级',
    `title`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '大屏标题',
    `sub_title`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '大屏副标题',
    `scheme_id`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '可视化方案ID',
    `scheme_name`    varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '可视化方案名称',
    `scheme_desc`    text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '可视化方案描述',
    `env_id`         varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '环境配置方案ID',
    `task_type`      varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务类型',
    `task_sub_type`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务子类型',
    `start_time`     datetime NULL DEFAULT NULL COMMENT '科目开始时间',
    `refresh`        int                                                     NOT NULL COMMENT '数据刷新时间',
    `view_angle`     text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '视角参数',
    `create_time`    datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE `situation_color_theme`
(
    `id`             int(10) NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`           varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `option`         text DEFAULT NULL,
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `create_time`    datetime NULL DEFAULT NULL,
    `update_time`    datetime NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id` ASC) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_icon
-- ----------------------------
CREATE TABLE `situation_icon`
(
    `id`             int(10) NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`           varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图标名称',
    `icon_url`       varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图标地址',
    `color`          varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT 'icon背景色',
    `type`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '类型',
    `create_user_id` varchar(64)                                             NOT NULL COMMENT '创建者',
    `create_time`    datetime DEFAULT NULL,
    `update_time`    datetime DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_icon_category
-- ----------------------------
CREATE TABLE `situation_icon_category`
(
    `id`          int(10) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`        varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型名称',
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_icon_category
-- ----------------------------
CREATE TABLE `situation_plot_pixel`
(
    `id`             int(10) NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`           varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标绘图元名称',
    `plot_url`       varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '背景图',
    `color`          varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '背景色',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime                                               DEFAULT NULL,
    `update_time`    datetime                                               DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_components_layer
-- ----------------------------
CREATE TABLE `situation_components_layer`
(
    `id`           int                                                     NOT NULL AUTO_INCREMENT,
    `resource_id`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `component_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '组件id',
    `template_id`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板id',
    `name`         varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图层名称',
    `order`        int                                                    DEFAULT NULL COMMENT '图层排序',
    `combination`  text CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '组合',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id` ASC) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_drawing
-- ----------------------------
CREATE TABLE `situation_drawing`
(
    `id`             INT(10) NOT NULL AUTO_INCREMENT,
    `resource_id`    VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源id',
    `name`           VARCHAR(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图形名称',
    `type`           VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '图形类型',
    `drawing_url`    VARCHAR(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图形url',
    `create_user_id` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人',
    `create_time`    DATETIME                                               DEFAULT NULL,
    `update_time`    DATETIME                                               DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id` ASC) USING BTREE
) ENGINE=INNODB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_drawing_category
-- ----------------------------
CREATE TABLE `situation_drawing_category`
(
    `id`          INT(10) NOT NULL AUTO_INCREMENT,
    `resource_id` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`        VARCHAR(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型名称',
    `create_time` DATETIME DEFAULT NULL,
    `update_time` DATETIME DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id` ASC) USING BTREE
) ENGINE=INNODB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of situation_drawing_category
-- ----------------------------
INSERT INTO `situation_drawing_category`
VALUES (1, '3d301075defc4b818783e1fb76afe411', '图标资源', '2023-01-04 13:56:47', '2023-01-04 13:56:49');
INSERT INTO `situation_drawing_category`
VALUES (2, '3d301075defc4b818783e1fb76afe412', '边框资源', '2023-01-04 13:57:02', '2023-01-04 13:57:03');
INSERT INTO `situation_drawing_category`
VALUES (3, '3d301075defc4b818783e1fb76afe413', '装饰线资源', '2023-01-04 13:57:18', '2023-01-04 13:57:19');

-- ----------------------------
-- Table structure for situation_topo_config
-- ----------------------------
CREATE TABLE `situation_topo_config`
(
    `id`           int                                                    NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源ID',
    `sub_range_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分靶场ID',
    `task_id`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务ID',
    `subject_id`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科目ID',
    `env_id`       varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '环境配置方案ID',
    `config`       text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '拓扑参数',
    `create_time`  datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

ALTER TABLE `situation_datasource_record` ADD COLUMN `connect_status` BOOLEAN DEFAULT 1 NOT NULL COMMENT '连接状态'  AFTER `connect_address`;

ALTER TABLE `situation_api_request` ADD COLUMN `connect_status` BOOLEAN DEFAULT 1 NOT NULL COMMENT '连接状态' AFTER `request_method`;

ALTER TABLE `situation_api_request` CHANGE `request_url` `request_url` LONGTEXT NOT NULL COMMENT 'url', CHANGE `request_params` `request_params` LONGTEXT NULL COMMENT '参数', CHANGE `request_headers` `request_headers` LONGTEXT NULL COMMENT '请求头', CHANGE `response_params` `response_params` LONGTEXT NULL COMMENT '返回参数';

ALTER TABLE `situation_dashboard_template` ADD COLUMN `layer_show` LONGTEXT NULL COMMENT '图层信息' AFTER `view_angle`;

ALTER TABLE `situation_dashboard_template_ref` ADD COLUMN `layer_show` LONGTEXT NULL COMMENT '图层信息' AFTER `view_angle`;