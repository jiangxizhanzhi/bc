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
    `create_time`    datetime                                                NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`    datetime                                                NULL     DEFAULT NULL,
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
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `case_id`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL comment '评估案例id',
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '评估材料名称',
    `description`    longtext CHARACTER SET utf8 COLLATE utf8_general_ci     NOT NULL comment '描述',
    `materialType`   int(1)                                                  NOT NULL comment '类型',
    `attachment`     varchar(256)                                            NOT NULL comment '材料附件',
    `attachment_name`varchar(256)                                            NOT NULL comment '材料附件原文件名',
    `create_time`    datetime                                                NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`    datetime                                                NULL     DEFAULT NULL,
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for assess_material_config
-- ----------------------------
CREATE TABLE `assess_material_config`
(
    `id`          int(11)                                                NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `case_id`     varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL comment '案例id',
    `material_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL comment '材料id',
    `create_time` datetime                                               NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime                                               NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for assess_data
-- ----------------------------

CREATE TABLE `assess_data`
(
    `id`             int                                                    NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
    `description`    text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人uuid',
    `tag`            varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签',
    `logo`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '封面',
    `type`           int NULL DEFAULT NULL COMMENT '评估数据类型。1：输入，2：结果',
    `input_type`     int NULL DEFAULT NULL COMMENT '输入数据集类型。1：mysql，2：excel',
    `input_data`     varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据集路径',
    `file_name`      varchar(512) DEFAULT NULL COMMENT '上传文件名称',
    `result_data`    varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结果路径',
    `create_time`    datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '评估数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for assess_model
-- ----------------------------

CREATE TABLE `assess_model`
(
    `id`             int                                                    NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
    `description`    text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人uuid',
    `vision_id`      varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '版本号',
    `tag`            varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签',
    `logo`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '封面',
    `interface_type` int NULL DEFAULT NULL COMMENT '输入数据集类型。1：超链接，2：数据库，3：可执行文件，4：其他类型',
    `input_data`     varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据集路径',
    `attachment_url` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件地址',
    `file_name`      varchar(512) DEFAULT NULL COMMENT '上传文件名称',
    `url_domain`     varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '超链接域名',
    `params`         varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数列表',
    `create_time`    datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '评估数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for assess_tag
-- ----------------------------

CREATE TABLE `assess_tag`
(
    `id`          int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源id',
    `content`     varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签内容',
    `create_time` datetime                                                NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id_UNIQUE`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '评估标签表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for assess_file_data
-- ----------------------------

CREATE TABLE `assess_file_data`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `resource_id` varchar(64)  NOT NULL COMMENT '资源id',
    `related_id`  varchar(64)  NOT NULL COMMENT '关联id',
    `path`        varchar(512) NOT NULL COMMENT '文件地址',
    `content`     text DEFAULT NULL COMMENT '文件解析内容',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `update_time` datetime     NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `resource_id_UNIQUE` (`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '评估文件解析内容表' ROW_FORMAT = DYNAMIC;