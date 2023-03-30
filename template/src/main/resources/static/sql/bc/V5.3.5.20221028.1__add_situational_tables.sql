-- 通用表格
-- ----------------------------
-- Table structure for situation_common_category
-- ----------------------------
CREATE TABLE `situation_common_category`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '态势类型',
    `task_type`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联任务类型',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
    `create_time`    datetime NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `name_uniq`(`name`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- 数据源管理
-- ----------------------------
-- Table structure for situation_datasource_drive
-- ----------------------------
CREATE TABLE `situation_datasource_drive`
(
    `id`             int NULL DEFAULT NULL COMMENT '自增ID',
    `type`           varchar(768) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库类型',
    `datasource`     varchar(768) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源',
    `connect_url`    varchar(768) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '连接URL',
    `connect_db_url` varchar(768) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库连接URL'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_datasource_db
-- ----------------------------
CREATE TABLE `situation_datasource_db`
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
-- Table structure for situation_datasource_api
-- ----------------------------
CREATE TABLE `situation_datasource_api`
(
    `id`              int                                                     NOT NULL AUTO_INCREMENT,
    `resource_id`     varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NOT NULL COMMENT '资源ID',
    `name`            varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT 'API名称',
    `description`     text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT 'API描述',
    `request_url`     longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT 'URL',
    `request_method`  varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci  NOT NULL COMMENT '方法类型',
    `request_headers` longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '请求头',
    `request_params`  longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '请求参数',
    `response_key`    varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '返回参数KEY值',
    `response_params` longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '返回参数',
    `connect_status`  tinyint(1) NOT NULL DEFAULT 1 COMMENT '连接状态',
    `create_user_id`  varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '创建人',
    `create_time`     datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`     datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX             `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;


-- 组件管理
-- ----------------------------
-- Table structure for situation_components_theme
-- ----------------------------
CREATE TABLE `situation_components_theme`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`           varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主题名称',
    `option`         text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '主题配置',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '创建人ID',
    `create_time`    datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_components_chart_category
-- ----------------------------
CREATE TABLE `situation_components_chart_category`
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
-- Table structure for situation_components_chart
-- ----------------------------
CREATE TABLE `situation_components_chart`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组件名称',
    `type`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '图表类型',
    `description`    varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
    `thumbnail`      varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '缩略图',
    `component_code` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组件代码',
    `config`         longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '配置',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `name_uniq`(`name`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_components_animation
-- ----------------------------
CREATE TABLE `situation_components_animation`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '动画名称',
    `chart_id`       varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '关联组件id',
    `description`    varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '动画描述',
    `config`         longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '动画配置内容',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `name_uniq`(`name` ASC) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_components_icon_category
-- ----------------------------
CREATE TABLE `situation_components_icon_category`
(
    `id`          int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`        varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型名称',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_components_icon
-- ----------------------------
CREATE TABLE `situation_components_icon`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`           varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图标名称',
    `type`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '类型',
    `icon_url`       varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图标地址',
    `color`          varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '背景色',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '创建人',
    `create_time`    datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_components_drawing_category
-- ----------------------------
CREATE TABLE `situation_components_drawing_category`
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
-- Table structure for situation_components_drawing
-- ----------------------------
CREATE TABLE `situation_components_drawing`
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
    UNIQUE INDEX `resource_id`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_components_common
-- ----------------------------
DROP TABLE IF EXISTS `situation_components_common`;
CREATE TABLE `situation_components_common`
(
    `id`             int                                                    NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源ID',
    `name`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组件名称',
    `component_type` int                                                    NOT NULL COMMENT '组件类型',
    `type`           int NULL DEFAULT NULL COMMENT '组件内部类型',
    `img`            text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '背景图URL',
    `url`            text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '指向资源URL',
    `config`         text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '组件配置',
    `create_time`    datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_components_decoration
-- ----------------------------
CREATE TABLE `situation_components_decoration`
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
-- Table structure for situation_dashboard_layer
-- ----------------------------
CREATE TABLE `situation_dashboard_layer`
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
    `bg_type`            int NULL DEFAULT 0 COMMENT '背景类型',
    `bg_template`        text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '背景模板',
    `bg_img`             varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '背景图',
    `bg_color`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '填充色',
    `topic`              varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主题',
    `components_setting` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '组件配置',
    `layer_show`         longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图层信息',
    `view_angle`         text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '视角配置',
    `refresh`            int NULL DEFAULT NULL COMMENT '刷新间隔',
    `config`             text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '模板配置',
    `custom_code`        tinyint                                                 NOT NULL DEFAULT 1 COMMENT '定制类型',
    `edit_status`        tinyint(1) NOT NULL DEFAULT 1 COMMENT '模板编辑权限',
    `create_user_id`     varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`        datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`        datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resourceId`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

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
    `bg_type`            int NULL DEFAULT 0 COMMENT '背景类型',
    `bg_template`        text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '背景模板',
    `bg_img`             varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '背景图',
    `bg_color`           varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '填充色',
    `topic`              varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主题',
    `components_setting` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '组件配置',
    `layer_show`         longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图层信息',
    `view_angle`         text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '视角配置',
    `refresh`            int NULL DEFAULT NULL COMMENT '刷新间隔',
    `config`             text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '模板配置',
    `custom_code`        tinyint                                                 NOT NULL DEFAULT 1 COMMENT '定制类型',
    `edit_status`        tinyint(1) NOT NULL DEFAULT 1 COMMENT '模板编辑权限',
    `origin_template_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '初始模板ID',
    `create_user_id`     varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`        datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`        datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resourceId`(`resource_id` ASC) USING BTREE
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
    `start_time`     datetime NULL DEFAULT NULL COMMENT '开始时间',
    `end_time`       datetime NULL DEFAULT NULL COMMENT '结束时间',
    `refresh`        int                                                     NOT NULL COMMENT '数据刷新时间',
    `view_angle`     text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '视角参数',
    `config`         text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '配置参数',
    `map_source`     tinyint(1) NULL DEFAULT NULL COMMENT '地图源 1-谷歌 2-百度 3-谷歌',
    `create_time`    datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_dashboard_instance
-- ----------------------------
CREATE TABLE `situation_dashboard_instance`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '可视化大屏名称',
    `description`    longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '大屏描述',
    `category`       varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '大屏类型',
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
    `events`         text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '特定事件',
    `replay_mode`    tinyint                                                 NOT NULL DEFAULT 0 COMMENT '重演模式',
    `replay_status`  tinyint                                                 NOT NULL DEFAULT 0 COMMENT '重演状态',
    `xd`             tinyint                                                 NOT NULL DEFAULT 0 COMMENT '是否为想定大屏',
    `status`         tinyint                                                 NOT NULL COMMENT '发布状态',
    `public_status`  tinyint                                                 NOT NULL COMMENT '下发状态',
    `edit`           tinyint                                                 NOT NULL COMMENT '是否可编辑',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `name_uniq`(`name` ASC) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- 拓扑管理
-- ----------------------------
-- Table structure for situation_topo_info
-- ----------------------------
CREATE TABLE `situation_topo_info`
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
-- Table structure for situation_gis_mark_pixel
-- ----------------------------
CREATE TABLE `situation_gis_mark_pixel`
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
-- Table structure for situation_dynamic_effects_category
-- ----------------------------
CREATE TABLE `situation_dynamic_effects_category`
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
-- Table structure for situation_dynamic_effects
-- ----------------------------
CREATE TABLE `situation_dynamic_effects`
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

-- 配置文件管理
-- ----------------------------
-- Table structure for situation_gis_config
-- ----------------------------
CREATE TABLE `situation_gis_config`
(
    `id`          int                                                     NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '配置名称',
    `config`      text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '配置信息',
    `thumbnail`   varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '预览图',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_topo_config
-- ----------------------------
CREATE TABLE `situation_topo_config`
(
    `id`          int                                                     NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '配置名称',
    `config`      text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '配置信息',
    `thumbnail`   varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '预览图',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
)ENGINE = INNODB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- 数据工具管理
-- ----------------------------
-- Table structure for situation_db_tool_category
-- ----------------------------
CREATE TABLE `situation_db_tool_category`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '工具类型',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
    `create_time`    datetime NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `name_uniq`(`name`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_db_tool
-- ----------------------------
CREATE TABLE `situation_db_tool`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '工具名称',
    `description`    text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '工具描述',
    `tool_type`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工具类型',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    `attachment`     varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `name_uniq`(`name`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- 数据权限管理
-- ----------------------------
-- Table structure for situation_db_access_authority
-- ----------------------------
CREATE TABLE `situation_db_access_authority`
(
    `id`             int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `name`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统名称',
    `db_type`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据类型',
    `sys_ip`         varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统ip',
    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- 数据库记录管理
CREATE TABLE `situation_db_access`
(
    `id`               int                                                     NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `resource_id`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '资源ID',
    `db_name`          varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据库名称',
    `table_name`       varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表名',
    `operation_type`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作类型',
    `operation_time`   datetime NULL DEFAULT NULL COMMENT '操作时间',
    `operation_detail` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '操作详情',
    `create_user_id`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`      datetime NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`      datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;