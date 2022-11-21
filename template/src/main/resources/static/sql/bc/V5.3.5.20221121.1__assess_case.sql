-- ----------------------------
-- Table structure for assess_case
-- ----------------------------
CREATE TABLE `assess_case`
(
    `id`             int(11)                                                 NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '评估材料名称',
    `overview`       longtext CHARACTER SET utf8 COLLATE utf8_general_ci     NOT NULL comment '评估案例概述',
    `cover`          varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '封面',
    `tag`            longtext                                                NOT NULL comment '标签',
    `update_time`    datetime                                                NOT NULL,
    `create_time`    datetime                                                NOT NULL,
    `create_user_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for assess_material
-- ----------------------------
CREATE TABLE `assess_material`
(
    `id`             int(11)                                                 NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL comment '',
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '评估材料名称',
    `description`    longtext CHARACTER SET utf8 COLLATE utf8_general_ci     NOT NULL comment '描述',
    `category`       int(1)                                                  NOT NULL comment '类型',
    `attachment`     varchar(256)                                            NOT NULL comment '材料附件',
    `update_time`    datetime                                                NOT NULL,
    `create_time`    datetime                                                NOT NULL,
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for assess_case_config
-- ----------------------------
CREATE TABLE `assess_case_config`
(
    `id`          int(11)                                                 NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `index`       int(11)                                                 NOT NULL,
    `parent_id`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `name`        varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `node_type`   int(1)                                                  NOT NULL,
    `case_id`     varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL comment '案例id',
    `material_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL comment '材料id',
    `create_time` datetime                                                NOT NULL,
    `update_time` datetime                                                NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;