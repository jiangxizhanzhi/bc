-- ----------------------------
-- Table structure for situational_two_dimensional_components
-- ----------------------------
CREATE TABLE `situational_two_dimensional_components`
(
    `id`             int(11)                                                  NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL,
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL comment '组件名称',
    `type`           int(11)                                                  NOT NULL comment '图表类型',
    `description`           varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL comment '描述',
    `thumbnail`      varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL comment '缩略图',
    `component_code` longtext CHARACTER SET utf8 COLLATE utf8_general_ci      NOT NULL comment '组件代码',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL,
    `create_time`    datetime                                                 NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`    datetime                                                 NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX name_uniq(name) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for situational_three_dimensional_components
-- ----------------------------
CREATE TABLE `situational_three_dimensional_components`
(
    `id`                                    int(11)                                                 NOT NULL AUTO_INCREMENT,
    `resource_id`                           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`                                  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '组件名称',
    `three_dimensional_components_category` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL comment '组件类型',
    `thumbnail`                             varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '缩略图',
    `chart_standard_document`               varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '图表标准文件 .fbx或.tiff格式',
    `create_user_id`                        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `create_time`                           datetime                                                NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`                           datetime                                                NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX name_uniq(name) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for situational_three_dimensional_components_category
-- ----------------------------
CREATE TABLE `situational_three_dimensional_components_category`
(
    `id`             int(11)                                                 NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '分组名称',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `create_time`    datetime                                                NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`    datetime                                                NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for situational_park_resource_library
-- ----------------------------
CREATE TABLE `situational_park_resource_library`
(
    `id`                             int(11)                                                 NOT NULL AUTO_INCREMENT,
    `resource_id`                    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`                           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '园区资源名称',
    `park_resource_library_category` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL comment '资源类型',
    `thumbnail`                      varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '缩略图',
    `resource_file`                  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '资源文件 u3d格式',
    `create_user_id`                 varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `create_time`                    datetime                                                NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`                    datetime                                                NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX name_uniq(name) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for situational_park_resource_library_category
-- ----------------------------
CREATE TABLE `situational_park_resource_library_category`
(
    `id`             int(11)                                                 NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '资源类型名称',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `create_time`    datetime                                                NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`    datetime                                                NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX name_uniq(name) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for situational_dynamic_effects_library
-- ----------------------------
CREATE TABLE `situational_dynamic_effects_library`
(
    `id`                               int(11)                                                 NOT NULL AUTO_INCREMENT,
    `resource_id`                      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`                             varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '动效名称',
    `dynamic_effects_library_category` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL comment '动效类型',
    `thumbnail`                        varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '缩略图',
    `source_node_dynamic_file`         varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '源节点动效文件',
    `target_node_dynamic_file`         varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '目标节点动效文件',
    `focus`                            int(1)                                                  NOT NULL comment '聚焦',
    `color`                            varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL comment '颜色',
    `line`                             varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL comment '线形',
    `line_size`                        int(11)                                                 NOT NULL comment '线形大小',
    `create_user_id`                   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `create_time`                      datetime                                                NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`                      datetime                                                NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX name_uniq(name) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for situational_dynamic_effects_library_category
-- ----------------------------
CREATE TABLE `situational_dynamic_effects_library_category`
(
    `id`             int(11)                                                 NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '动效类型名称',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `create_time`    datetime                                                NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`    datetime                                                NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX name_uniq(name) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;