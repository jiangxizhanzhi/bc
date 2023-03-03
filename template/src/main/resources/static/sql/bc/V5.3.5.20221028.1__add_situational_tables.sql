-- 通用表格
-- ----------------------------
-- Table structure for situation_category
-- ----------------------------
CREATE TABLE `situation_category`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '态势类型',
    `task_type`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务类型',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
    `create_time`    datetime NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `name_uniq`(`name`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
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

-- 数据源管理
-- ----------------------------
-- Table structure for situation_datasource_template
-- ----------------------------
CREATE TABLE `situation_datasource_template`
(
    `id`             int NULL DEFAULT NULL COMMENT '自增ID',
    `type`           varchar(768) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库类型',
    `datasource`     varchar(768) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源',
    `connect_url`    varchar(768) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '连接URL',
    `connect_db_url` varchar(768) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库连接URL'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of situation_datasource_template
-- ----------------------------
INSERT INTO `situation_datasource_template`
VALUES (1, 'MYSQL', 'com.mysql.cj.jdbc.Driver',
        'jdbc:mysql://%s:%s?autoReconnect=true&useUnicode=true&characterEncoding=utf8',
        'jdbc:mysql://%s:%s/%s?autoReconnect=true&useUnicode=true&characterEncoding=utf8');
INSERT INTO `situation_datasource_template`
VALUES (2, 'ORACLE', 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin:@%s:%s', 'jdbc:oracle:thin:@%s:%s');
INSERT INTO `situation_datasource_template`
VALUES (3, 'DB2', 'com.ibm.db2.jcc.DB2Driver', 'jdbc:db2://%s:%s', 'jdbc:db2://%s:%s');
INSERT INTO `situation_datasource_template`
VALUES (4, 'SQLSERVER', 'com.microsoft.sqlserver.jdbc.SQLServerDriver', 'jdbc:sqlserver://%s:%s',
        'jdbc:sqlserver://%s:%s');
INSERT INTO `situation_datasource_template`
VALUES (5, 'HIVE', 'org.apache.hadoop.hive.jdbc.HiveDriver', 'jdbc:hive2://%s:%s', 'jdbc:hive2://%s:%s/%s');

-- ----------------------------
-- Table structure for situation_datasource_record
-- ----------------------------
CREATE TABLE `situation_datasource_record`
(
    `id`              int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`     varchar(192) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源ID',
    `name`            varchar(768) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库连接名',
    `type`            varchar(192) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库类型',
    `port`            varchar(192) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '端口',
    `connect_address` varchar(192) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '连接地址',
    `connect_status`  tinyint(1) NOT NULL DEFAULT 1 COMMENT '连接状态',
    `user_name`       varchar(192) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
    `password`        varchar(192) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
    `create_user_id`  varchar(192) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`     datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`     datetime NULL DEFAULT NULL COMMENT '更新时间',
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
    INDEX             `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_api_request
-- ----------------------------
CREATE TABLE `situation_api_request`
(
    `id`              int                                                     NOT NULL AUTO_INCREMENT,
    `resource_id`     varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NOT NULL COMMENT '资源ID',
    `name`            varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT 'API名称',
    `description`     text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT 'API描述',
    `request_url`     longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT 'URL',
    `request_method`  varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NOT NULL COMMENT '方法类型',
    `connect_status`  tinyint(1) NOT NULL DEFAULT 1 COMMENT '连接状态',
    `request_params`  longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '请求参数',
    `request_headers` longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '请求头',
    `response_key`    varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '返回参数KEY值',
    `response_params` longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '返回参数',
    `create_user_id`  varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NOT NULL COMMENT '创建人',
    `create_time`     datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`     datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX             `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;


-- 组件管理
-- ----------------------------
-- Table structure for situation_color_theme
-- ----------------------------
CREATE TABLE `situation_color_theme`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`           varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主题名称',
    `option`         text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '主题配置',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '创建人ID',
    `create_time`    datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of situation_color_theme
-- ----------------------------
INSERT INTO `situation_color_theme`
VALUES (1, '5b45a9b2d3f643e39b1a94be012ad2c6', '强烈色系',
        '{\"color\":[\"#f37c0f\",\"#d81752\",\"#F4C947\",\"#DB335A\",\"#AA00E4\"]}', '3f655d87112f4ae094bbaf70830e20aa',
        '2022-12-08 11:08:26', '2023-02-07 17:09:58');
INSERT INTO `situation_color_theme`
VALUES (2, '6f635c642aef40a68ad6e1410db87c7f', '三元色系', '{\"color\":[\"#0FC5F3\",\"#17D8A1\",\"#F4C947\"]}',
        '3f655d87112f4ae094bbaf70830e20aa', '2022-12-10 15:33:31', '2023-02-07 17:08:38');
INSERT INTO `situation_color_theme`
VALUES (3, '5b65947dc9114faeae7ee960a2a80d0a', '暗色系列',
        '{\"color\":[\"#754545\",\"#9B3838\",\"#996A6A\",\"#50971A\",\"#C22626\",\"#50E3C2\",\"#4A4A4A\",\"#D0021B\"]}',
        '3f655d87112f4ae094bbaf70830e20aa', '2022-12-12 11:37:14', '2023-02-07 17:08:20');
INSERT INTO `situation_color_theme`
VALUES (4, '47b46b30d7094ecfb87d7e0d4369567d', '绚丽色系',
        '{\"color\":[\"#3D7EEB\",\"#0FC5F3\",\"#17D8A1\",\"#F4C947\",\"#DB335A\",\"#AA00E4\",\"#F96C70\"]}',
        '3f655d87112f4ae094bbaf70830e20aa', '2023-01-03 09:22:53', '2023-02-07 17:09:46');
INSERT INTO `situation_color_theme`
VALUES (5, '90ce54e25be344a8b5178816f0fa5c3d', '清淡色系',
        '{\"color\":[\"#3D7EEB\",\"#0FC5F3\",\"#17D8A1\",\"#F4C947\",\"#FFFFFF\"]}', '3f655d87112f4ae094bbaf70830e20aa',
        '2023-02-07 01:03:36', '2023-02-07 17:09:25');

-- ----------------------------
-- Table structure for situation_two_dimensional_components_category
-- ----------------------------
CREATE TABLE `situation_two_dimensional_components_category`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '二维组件类型名称',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime                                                NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `name_uniq`(`name`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of situation_two_dimensional_components_category
-- ----------------------------
INSERT INTO `situation_two_dimensional_components_category`
VALUES (1, '05b739fc686a073756399f79c34039b5', '折线图', NULL, '2022-12-16 16:23:19', NULL);
INSERT INTO `situation_two_dimensional_components_category`
VALUES (2, '1102f907c7650ad541c381f59c700f5f', '柱状图', NULL, '2022-12-16 16:23:17', NULL);
INSERT INTO `situation_two_dimensional_components_category`
VALUES (3, '2eec84346538e972a962b165e499f52d', '饼图', NULL, '2022-12-16 16:23:15', NULL);
INSERT INTO `situation_two_dimensional_components_category`
VALUES (4, '2f4f15de59cd2dbadc5d8a582e9f6849', '散点图', NULL, '2022-12-16 16:23:13', NULL);
INSERT INTO `situation_two_dimensional_components_category`
VALUES (5, '3b6e90254227833303d908336b105ade', '雷达图', NULL, '2022-12-16 16:23:12', NULL);
INSERT INTO `situation_two_dimensional_components_category`
VALUES (6, '446bbba739678a4e6e103e7d214d55aa', '桑基图', NULL, '2022-12-16 16:23:10', NULL);
INSERT INTO `situation_two_dimensional_components_category`
VALUES (7, '6753aa8cad6fa9760308871e707ea033', '漏斗图', NULL, '2022-12-16 16:23:08', NULL);
INSERT INTO `situation_two_dimensional_components_category`
VALUES (8, '8568759a4d514ff730ed6eb1721a2f22', '旭日图', NULL, '2022-12-16 16:23:06', NULL);
INSERT INTO `situation_two_dimensional_components_category`
VALUES (9, '8568759a4d514ff730ed6eb1721a2f23', 'K线图', NULL, '2022-12-16 16:23:03', NULL);
INSERT INTO `situation_two_dimensional_components_category`
VALUES (10, '8568759a4d514ff730ed6eb1721a2f24', '矩形树图', NULL, '2022-12-16 16:23:50', NULL);
INSERT INTO `situation_two_dimensional_components_category`
VALUES (11, '8568759a4d514ff730ed6eb1721a2f25', '盒须图', NULL, '2022-12-16 16:23:53', NULL);
INSERT INTO `situation_two_dimensional_components_category`
VALUES (12, '8568759a4d514ff730ed6eb1721a2f26', '关系图', NULL, '2022-12-16 16:24:10', NULL);
INSERT INTO `situation_two_dimensional_components_category`
VALUES (13, '8568759a4d514ff730ed6eb1721a2f27', '仪表图', NULL, '2022-12-16 16:24:22', NULL);
INSERT INTO `situation_two_dimensional_components_category`
VALUES (14, '8568759a4d514ff730ed6eb1721a2f28', '地图', NULL, '2023-01-29 14:20:24', NULL);

-- ----------------------------
-- Table structure for situation_two_dimensional_components
-- ----------------------------
CREATE TABLE `situation_two_dimensional_components`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组件名称',
    `type`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '图表类型',
    `description`    varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
    `thumbnail`      varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '缩略图',
    `component_code` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组件代码',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    `config`         longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '配置',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `name_uniq`(`name`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_icon_category
-- ----------------------------
CREATE TABLE `situation_icon_category`
(
    `id`          int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`        varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型名称',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of situation_icon_category
-- ----------------------------
INSERT INTO `situation_icon_category`
VALUES (1, '7683ba68d9c64551a96f12be931ff52e', '网络安全类', '2022-12-15 09:54:43', '2022-12-15 09:54:45');
INSERT INTO `situation_icon_category`
VALUES (2, '7683ba68d9c64551a96f12be931ff52r', '路由器类图标', '2022-12-15 09:55:01', '2022-12-15 09:55:03');
INSERT INTO `situation_icon_category`
VALUES (3, '7683ba68d9c64551a96f12be931ff52t', '护盾图标', '2022-12-15 09:55:43', '2023-01-04 18:18:24');

-- ----------------------------
-- Table structure for situation_icon
-- ----------------------------
CREATE TABLE `situation_icon`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`           varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图标名称',
    `icon_url`       varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图标地址',
    `color`          varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '背景色',
    `type`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '类型',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '创建人',
    `create_time`    datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_drawing_category
-- ----------------------------
CREATE TABLE `situation_drawing_category`
(
    `id`          int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`        varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型名称',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

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
-- Table structure for situation_drawing
-- ----------------------------
CREATE TABLE `situation_drawing`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`           varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图形名称',
    `type`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '图形类型',
    `drawing_url`    varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图形URL',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_common_components
-- ----------------------------
CREATE TABLE `situation_common_components`
(
    `id`             int                                                    NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源ID',
    `name`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组件名称',
    `component_type` int                                                    NOT NULL COMMENT '组件类型',
    `type`           int NULL DEFAULT NULL COMMENT '组件内部类型',
    `img`            text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '背景图URL',
    `url`            text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '指向资源URL',
    `create_time`    datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_decoration
-- ----------------------------
CREATE TABLE `situation_decoration`
(
    `id`             INT                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`    VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`           VARCHAR(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '装饰类名称',
    `type`           VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '装饰类类型',
    `decoration_url` VARCHAR(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '装饰类URL',
    `create_user_id` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`    DATETIME NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    DATETIME NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- 模板管理
-- ----------------------------
-- Table structure for situation_components_layer
-- ----------------------------
CREATE TABLE `situation_components_layer`
(
    `id`           int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `component_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '组件ID',
    `template_id`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板ID',
    `name`         varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图层名称',
    `order`        int NULL DEFAULT NULL COMMENT '图层排序',
    `combination`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '组合',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_dashboard_components_setting
-- ----------------------------
CREATE TABLE `situation_dashboard_components_setting`
(
    `id`                 int                                                    NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源ID',
    `type`               varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '视角类型',
    `component_index`    int                                                    NOT NULL DEFAULT 0 COMMENT '图层顺序',
    `datasource_id`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源ID',
    `datasource_setting` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '数据源配置',
    `components_id`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件ID',
    `component_setting`  longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '组件配置',
    `create_time`        datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`        datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_dashboard_template
-- ----------------------------
CREATE TABLE `situation_dashboard_template`
(
    `id`                 int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`               varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板名称',
    `type`               varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '看板类型',
    `level`              int NULL DEFAULT NULL COMMENT '模板等级',
    `thumbnail`          varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '缩略图URL',
    `height`             varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '画布高度',
    `width`              varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '画布宽度',
    `components_setting` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '组件配置',
    `bg_type`            int NULL DEFAULT 0 COMMENT '背景类型',
    `bg_img`             varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '背景图',
    `bg_template`        text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '背景模板',
    `bg_color`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '填充色',
    `topic`              varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主题',
    `view_angle`         text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '视角配置',
    `layer_show`         longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图层信息',
    `edit_status`        tinyint(1) NOT NULL DEFAULT 1 COMMENT '模板编辑权限',
    `refresh`            int NULL DEFAULT NULL COMMENT '刷新间隔',
    `create_user_id`     varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`        datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`        datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resourceId`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of situation_dashboard_template
-- ----------------------------
INSERT INTO `situation_dashboard_template`
VALUES (1, 'c3765b250f704fcca9d34233399cf615', '定制态势-红蓝对抗', '05b739fc686a073756399f79c34039b5', 3,
        '/cpms/upload/images/8ea6a5b484f34838b8d34d61d49bfd76.jpg', '1080', '3840', '[]', 0, NULL, NULL, '#FFFFFF',
        NULL, '{}', NULL, 0, 5, NULL, '2022-12-28 16:05:14', '2022-12-28 16:05:14');
INSERT INTO `situation_dashboard_template`
VALUES (2, '7fe161bb8863435c9f04aa99d24ffa5f', '定制态势-作战试验-攻击', '1102f907c7650ad541c381f59c700f5f', 3,
        '/cpms/upload/images/13186af6814f460589e7000c836a4de9.png', '1080', '3840', '[]', 0, NULL, NULL, '#FFFFFF',
        NULL, '{}', NULL, 0, 5, NULL, '2022-12-28 16:04:53', '2022-12-28 16:04:53');
INSERT INTO `situation_dashboard_template`
VALUES (3, 'a67104c93b02486890148b55c12693f1', '定制态势-作战实验-防御', '1102f907c7650ad541c381f59c700f5f', 3,
        '/cpms/upload/images/66298022be8b447e8e470bbdd31d07fe.png', '1080', '3840', '[]', 0, NULL, NULL, '#FFFFFF',
        NULL, '{}', NULL, 0, 5, NULL, '2023-01-07 15:48:56', '2023-01-07 15:48:58');
INSERT INTO `situation_dashboard_template`
VALUES (4, 'a67104c93b02486890148b55c12693f2', '定制态势-专项比武', '2eec84346538e972a962b165e499f52d', 3,
        '/cpms/upload/images/2318259ae6e04624a74e70eebdf09c90.png', '1080', '3840', '[]', 0, NULL, NULL, '#FFFFFF',
        NULL, '{}', NULL, 0, 5, NULL, '2023-01-07 15:52:13', '2023-01-07 15:52:15');
INSERT INTO `situation_dashboard_template`
VALUES (5, 'a67104c93b02486890148b55c12693f3', '定制态势-夺旗竞赛', 'f6842d1d76b14217abb9c118d9c5cbfd', 3,
        '/cpms/upload/images/be76b93e739447079d33b12363fafab4.png', '1080', '3840', '[]', 0, NULL, NULL, '#FFFFFF',
        NULL, '{}', NULL, 0, 5, NULL, '2023-01-07 15:54:37', '2023-01-07 15:54:38');
INSERT INTO `situation_dashboard_template`
VALUES (6, 'a67104c93b02486890148b55c12693f4', '定制态势-夺旗竞赛-题目', 'f6842d1d76b14217abb9c118d9c5cbfd', 3,
        '/cpms/upload/images/1c89df029dc04ed8b9200590c863a411.png', '1080', '3840', '[]', 0, NULL, NULL, '#FFFFFF',
        NULL, '{}', NULL, 0, 5, NULL, '2023-01-07 15:54:37', '2023-01-07 15:54:38');
INSERT INTO `situation_dashboard_template`
VALUES (7, 'a67104c93b02486890148b55c12693f5', '定制态势-自主训练', 'c71399e5569b4c2dba78c0c1e1fbae91', 3,
        '/cpms/upload/images/01fcc6e34c574558bd25d6706c355d40.png', '1080', '3840', '[]', 0, NULL, NULL, '#FFFFFF',
        NULL, '{}', NULL, 0, 5, NULL, '2023-01-07 15:54:37', '2023-01-07 15:54:38');
INSERT INTO `situation_dashboard_template`
VALUES (8, 'ffe23f5a8a1c42b9889d8fbc04dd6551', '定制态势-联管中心综合态势', 'a4b3555767d14f01b88ee4f8ca471a3e', 1,
        '/cpms/upload/images/f91e40adeccf48c99e619d765340e1ad.png', '1080', '3840', '[]', 0, NULL, NULL, '#FFFFFF',
        NULL, '{}', NULL, 0, 5, NULL, '2023-01-07 15:54:37', '2023-01-07 15:54:38');
INSERT INTO `situation_dashboard_template`
VALUES (9, 'a67104c93b02486890148b55c12693f6', '定制态势-分靶场态势', 'a4b3555767d14f01b88ee4f8ca471a3e', 2,
        '/cpms/upload/images/38eda0a19e5a43b1a7fdbdfc1bbb760e.png', '1080', '3840', '[]', 0, NULL, NULL, '#FFFFFF',
        NULL, '{}', NULL, 0, 5, NULL, '2023-01-07 15:54:37', '2023-01-07 15:54:38');

-- ----------------------------
-- Table structure for situation_dashboard_template_ref
-- ----------------------------
CREATE TABLE `situation_dashboard_template_ref`
(
    `id`                 int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`               varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板名称',
    `type`               varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '看板类型',
    `level`              int NULL DEFAULT NULL COMMENT '模板等级',
    `thumbnail`          varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '缩略图URL',
    `height`             varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '画布高度',
    `width`              varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '画布宽度',
    `components_setting` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '组件配置',
    `bg_type`            int NULL DEFAULT 0 COMMENT '背景类型',
    `bg_img`             varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '背景图',
    `bg_template`        text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '背景模板',
    `bg_color`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '填充色',
    `topic`              varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主题',
    `view_angle`         text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '视角配置',
    `layer_show`         longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图层信息',
    `edit_status`        tinyint(1) NOT NULL DEFAULT 1 COMMENT '模板编辑权限',
    `refresh`            int NULL DEFAULT NULL COMMENT '刷新间隔',
    `origin_template_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '初始模板ID',
    `create_user_id`     varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`        datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`        datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resourceId`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- 大屏管理
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
    `map_source`     tinyint(1) NULL DEFAULT NULL COMMENT '地图源 1-谷歌 2-百度 3-谷歌',
    `create_time`    datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_publish_instance
-- ----------------------------
CREATE TABLE `situation_publish_instance`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '可视化大屏名称',
    `description`    longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '大屏描述',
    `events`         text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '特定事件',
    `attribute_id`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '属性ID',
    `scheme_id`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '想定方案ID',
    `sub_range_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分靶场名称',
    `sub_range_id`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分靶场ID',
    `task_id`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务ID',
    `task_name`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名称',
    `subject_id`     varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科目ID',
    `subject_name`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科目名称',
    `template_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板ID',
    `thumbnail`      varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '缩略图',
    `third_url`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '三方靶标URL',
    `xd`             tinyint                                                 NOT NULL COMMENT '是否为想定大屏',
    `status`         tinyint                                                 NOT NULL COMMENT '发布状态',
    `public_status`  tinyint                                                 NOT NULL COMMENT '下发状态',
    `edit`           tinyint                                                 NOT NULL COMMENT '是否可编辑',
    `category`       varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '大屏类型',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `name_uniq`(`name`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- 拓扑管理
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

-- GIS标绘
-- ----------------------------
-- Table structure for situation_plot_pixel
-- ----------------------------
CREATE TABLE `situation_plot_pixel`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`           varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标绘图元名称',
    `plot_url`       varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '背景图',
    `color`          varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '背景色',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of situation_plot_pixel
-- ----------------------------
INSERT INTO `situation_plot_pixel`
VALUES (1, '9f7fc6ca811a4246842cc9cd4d0edb1f', 'GIS图元', '/cpms/upload/images/3f4ea1aa0e8f4a96b23cd62ce7aabff5.png',
        '#49EB3D', '3f655d87112f4ae094bbaf70830e20aa', '2023-01-05 19:38:31', '2023-01-05 19:38:31');
INSERT INTO `situation_plot_pixel`
VALUES (2, 'f4b45a7d9fd54c76b657687abed3b9a2', '地点图元', '/cpms/upload/images/afe0e02f4943499fb14ca4c6659b52a1.png',
        '#EB3D56', '3f655d87112f4ae094bbaf70830e20aa', '2023-01-05 19:38:49', '2023-01-05 19:38:49');
INSERT INTO `situation_plot_pixel`
VALUES (3, 'cdd13cde0da8477f8f1e6095090049d7', '方块图元', '/cpms/upload/images/1a0dcc5be054489fa807344c9bb418e4.png',
        '#3D7EEB', '3f655d87112f4ae094bbaf70830e20aa', '2023-01-05 19:39:04', '2023-01-05 19:39:04');
INSERT INTO `situation_plot_pixel`
VALUES (4, 'db0208f981604c91838853850268e79f', '红旗图元', '/cpms/upload/images/2877ff4358e746dab500283d4d7da0b3.png',
        '#EB433D', '3f655d87112f4ae094bbaf70830e20aa', '2023-01-05 19:39:14', '2023-01-05 19:39:42');
INSERT INTO `situation_plot_pixel`
VALUES (5, 'a5bdb2ed30ec4336b10e80c9490c2531', '箭头图元', '/cpms/upload/images/bd7e042a009645fe90cfb7dddc3846be.png',
        '#3D7EEB', '3f655d87112f4ae094bbaf70830e20aa', '2023-01-05 19:39:35', '2023-01-05 19:39:35');
INSERT INTO `situation_plot_pixel`
VALUES (6, '47e489c096054feaabb8034abefea24e', '聚焦图元', '/cpms/upload/images/45b856478d474e3aa78f23194d23de7a.png',
        '#3DEB81', '3f655d87112f4ae094bbaf70830e20aa', '2023-01-05 19:39:57', '2023-01-05 19:39:57');
INSERT INTO `situation_plot_pixel`
VALUES (7, 'a7255644fa7341298d91f4a8445fa5fb', '手势图元', '/cpms/upload/images/2f8681aa69ca4e42888706cfe5004b05.png',
        '#A03DEB', '3f655d87112f4ae094bbaf70830e20aa', '2023-01-05 19:40:16', '2023-01-05 19:40:16');
INSERT INTO `situation_plot_pixel`
VALUES (8, '11779254f770461a8903bef06f6155ef', '星星图元', '/cpms/upload/images/e6ac305149e04a0d96502e3cc53121b1.png',
        '#EB3D3D', '3f655d87112f4ae094bbaf70830e20aa', '2023-01-05 19:40:35', '2023-01-05 19:40:35');
INSERT INTO `situation_plot_pixel`
VALUES (9, 'bc7d92f190704cd5baa8c512074e2e9b', '区域图元', '/cpms/upload/images/16c85958c4bb42f2802fd099f4552072.png',
        '#3DEBC6', '3f655d87112f4ae094bbaf70830e20aa', '2023-01-05 19:42:04', '2023-01-05 19:42:04');
INSERT INTO `situation_plot_pixel`
VALUES (10, '0f1e1812965141e5ab4a39dfbba7f3de', '高度图元', '/cpms/upload/images/4a173b0e88724a62a9e9d7974b5d2f67.png',
        '#3D7EEB', '3f655d87112f4ae094bbaf70830e20aa', '2023-01-05 19:42:17', '2023-01-05 19:42:17');

-- ----------------------------
-- Table structure for situation_gis_mark
-- ----------------------------
CREATE TABLE `situation_gis_mark`
(
    `id`           int                                                    NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源ID',
    `config`       text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标会配置',
    `dashboard_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '大屏ID',
    `create_time`  datetime NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间',
    `delete_time`  datetime NULL DEFAULT NULL COMMENT '删除时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- 动效管理
-- ----------------------------
-- Table structure for situation_dynamic_effects_library_category
-- ----------------------------
CREATE TABLE `situation_dynamic_effects_library_category`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '动效类型名称',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `name_uniq`(`name`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of situation_dynamic_effects_library_category
-- ----------------------------
INSERT INTO `situation_dynamic_effects_library_category`
VALUES (1, '1588719333268889602', '常用动效库', '3f655d87112f4ae094bbaf70830e20aa', '2022-11-05 10:26:20',
        '2022-11-05 13:49:08');

-- ----------------------------
-- Table structure for situation_dynamic_effects_library
-- ----------------------------
CREATE TABLE `situation_dynamic_effects_library`
(
    `id`                       int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`              varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`                     varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '动效名称',
    `category`                 varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '动效类型',
    `thumbnail`                varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '缩略图',
    `source_node_dynamic_file` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '源节点动效文件',
    `source_node_dynamic_dir`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '动效文件解压目录',
    `create_user_id`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`              datetime NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`              datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `name_uniq`(`name`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_dynamic_effects_mapping
-- ----------------------------
CREATE TABLE `situation_dynamic_effects_mapping`
(
    `id`                  int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`         varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`                varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '动效映射名称',
    `category`            varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '动效类型',
    `attack_type`         varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '攻击类型',
    `source_node_dynamic` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '源动效',
    `target_node_dynamic` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '目标动效',
    `focus`               tinyint                                                 NOT NULL DEFAULT 0 COMMENT '聚焦',
    `color`               varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '颜色',
    `line`                varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '线形',
    `line_size`           int                                                     NOT NULL COMMENT '线段大小',
    `create_user_id`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`         datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`         datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
    UNIQUE INDEX `name`(`name`) USING BTREE,
    INDEX                 `attack_type`(`attack_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- 攻防类型
-- ----------------------------
-- Table structure for situation_event_type
-- ----------------------------
CREATE TABLE `situation_event_type`
(
    `id`                 int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `code`               varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '事件编码',
    `name`               varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '事件名称',
    `parent_node`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父类型',
    `source_action_type` tinyint(1) NULL DEFAULT NULL COMMENT '源节点动作类型',
    `target_action_type` tinyint(1) NULL DEFAULT NULL COMMENT '目标节点动作类型',
    `create_time`        datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`        datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of situation_event_type
-- ----------------------------
INSERT INTO `situation_event_type`
VALUES (1, '001', '拒绝服务攻击', '1', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (2, '002', '漏洞攻击', '1', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (3, '003', '后门攻击', '1', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (4, '004', '网络扫描', '1', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (5, '005', '暴力破解', '1', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (6, '006', '钓鱼攻击', '1', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (7, '007', 'ARP攻击', '1', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (8, '008', '内网扫描攻击', '1', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (9, '009', '内网漏洞攻击', '1', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (10, '010', '内网暴力破解', '1', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (11, '011', '内网其它攻击', '1', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (12, '012', '复合型攻击', '1', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (13, '013', '其它网络攻击', '1', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (14, '014', '病毒木马', '2', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (15, '015', '僵尸网络', '2', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (16, '016', '蠕虫传播', '2', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (17, '017', '勒索软件', '2', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (18, '018', '挖矿软件', '2', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (19, '019', '网页木马', '2', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (20, '020', 'Webshell', '2', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (21, '021', '漏洞利用型恶意程序', '2', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (22, '022', '未知恶意程序', '2', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (23, '023', '其它恶意程序', '2', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (24, '024', '命令控制通信', '2', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (25, '025', '木马随机域名', '2', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (26, '026', '隐蔽隧道', '2', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (27, '027', '恶意网址', '2', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (28, '028', '恶意IP', '2', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (29, '029', '木马反向连接', '2', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (30, '030', '木马心跳连接', '2', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (31, '031', '不可信证书', '1', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (32, '032', '其它恶意连接', '1', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (33, '033', '钓鱼邮件', '3', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (34, '034', '勒索邮件', '3', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (35, '035', '垃圾邮件', '3', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (36, '036', '病毒邮件', '3', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (37, '037', '异常邮件', '3', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (38, '038', '敏感邮件', '3', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (39, '039', '访问控制', '1', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (40, '040', '上网行为控制', '4', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (41, '041', '黑白名单', '4', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (42, '042', '终端审计', '4', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (43, '043', '内容审计', '4', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (44, '044', '设备接入违规', '4', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (45, '045', '服务器违规外联', '4', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (46, '046', '权限违规', '5', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (47, '047', '行为违规', '4', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (48, '048', '其它违规行为', '4', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (49, '049', '异常会话', '4', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (50, '050', '异常网络连接', '1', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (51, '051', '异常端口服务', '6', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (52, '052', '异常流量', '6', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (53, '053', '异常数据接收', '6', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (54, '054', '异常数据外发', '6', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (55, '055', '其它异常行为', '4', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (56, '056', '网络可用性故障', '1', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (57, '057', '网络广播风暴', '1', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (58, '058', '网络地址冲突', '1', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (59, '059', '硬件故障', '4', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (60, '060', '性能告警', '4', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (61, '061', '数据库错误', '7', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (62, '062', '软件/服务运行错误', '4', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (63, '063', '其它运行故障', '4', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (64, '064', '网络配置错误', '1', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (65, '065', 'Windows配置错误', '4', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (66, '066', 'Linux配置错误', '4', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (67, '067', '应用服务配置错误', '4', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (68, '068', '数据库 配置错误', '7', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (69, '069', '其它配置错误', '4', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (70, '070', '账号登录异常', '5', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (71, '071', '账号认证异常', '5', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (72, '072', '账号管理异常', '5', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (73, '073', '账号操作异常', '5', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (74, '074', '账号失陷', '5', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (75, '075', 'Windows主机安全', '4', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (76, '076', 'Linux主机安全', '4', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (77, '077', '资产脆弱性', '4', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (78, '078', '其它主机安全', '4', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (79, '079', '数据篡改', '7', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (80, '080', '数据泄露', '7', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (81, '081', '数据窃取', '7', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (82, '082', '数据丢失', '7', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (83, '083', '数据假冒', '7', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (84, '084', '业务注册风险', '4', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (85, '085', '业务登录风险', '4', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (86, '086', '业务运行风险', '4', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (87, '087', '业务违规', '4', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (88, '088', '注入攻击', '1', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (89, '089', '跨站攻击', '1', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (90, '090', 'Web漏洞攻击', '1', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (91, '091', '文件包含攻击', '2', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (92, '092', '网页木马', '2', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (93, '093', 'Webshell', '1', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (94, '094', '爬虫攻击', '1', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (95, '095', 'Web扫描攻击', '1', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (96, '096', '协议违规', '1', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (97, '097', '网站信息泄露', '1', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (98, '098', '网页篡改', '1', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (99, '099', 'CC攻击', '1', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (100, '100', '其它Web攻击', '1', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (101, '101', '复合Web攻击', '1', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (102, '102', '其它类型攻击', '1', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (103, '103', '其它APT攻击', '1', 9, 9, '2022-11-30 18:28:25', '2022-11-30 18:28:28');
INSERT INTO `situation_event_type`
VALUES (104, '104', 'APT投递', '1', 9, 9, '2022-11-30 18:29:03', '2022-11-30 18:29:05');
INSERT INTO `situation_event_type`
VALUES (105, '105', 'APT回连', '1', 9, 9, '2022-12-01 10:31:02', '2022-12-01 10:31:04');
INSERT INTO `situation_event_type`
VALUES (106, '1', '网络攻击', NULL, NULL, NULL, '2022-12-02 02:33:00', '2022-12-02 02:33:02');
INSERT INTO `situation_event_type`
VALUES (107, '2', '恶意样本', NULL, NULL, NULL, '2022-12-02 18:34:59', '2022-12-02 18:35:01');
INSERT INTO `situation_event_type`
VALUES (108, '3', '社会工程', NULL, NULL, NULL, '2022-12-03 10:36:58', '2022-12-03 10:37:00');
INSERT INTO `situation_event_type`
VALUES (109, '4', '业务安全', NULL, NULL, NULL, '2022-12-04 02:38:57', '2022-12-04 02:38:59');
INSERT INTO `situation_event_type`
VALUES (110, '5', '账号安全', NULL, NULL, NULL, '2022-12-04 18:40:56', '2022-12-04 18:40:58');
INSERT INTO `situation_event_type`
VALUES (111, '6', '攻击入侵', NULL, NULL, NULL, '2022-12-05 10:42:55', '2022-12-05 10:42:57');
INSERT INTO `situation_event_type`
VALUES (112, '7', '数据安全', NULL, NULL, NULL, '2022-12-06 02:44:54', '2022-12-06 02:44:56');

-- 配置管理
-- ----------------------------
-- Table structure for situation_gis
-- ----------------------------
CREATE TABLE `situation_gis`
(
    `id`          INT(10) NOT NULL AUTO_INCREMENT,
    `resource_id` VARCHAR(64)                                             NOT NULL,
    `config`      TEXT CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '配置信息',
    `thumbnail`   VARCHAR(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '预览图',
    `template_id` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '关联模板',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- Topo配置
-- ----------------------------
-- Table structure for situation_topo
-- ----------------------------
CREATE TABLE `situation_topo`
(
    `id`          INT(10) NOT NULL AUTO_INCREMENT,
    `resource_id` VARCHAR(64)                                             NOT NULL,
    `config`      TEXT CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '配置信息',
    `thumbnail`   VARCHAR(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '预览图',
    `template_id` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '关联模板',
    `create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` DATETIME NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
)ENGINE = INNODB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;