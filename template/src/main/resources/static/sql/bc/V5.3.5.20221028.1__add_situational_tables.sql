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
    `target_node_dynamic_file` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '目标节点动效文件',
    `focus`                    int(1) NOT NULL comment '聚焦',
    `color`                    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL comment '颜色',
    `line`                     varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL comment '线形',
    `line_size`                int(11) NOT NULL comment '线形大小',
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