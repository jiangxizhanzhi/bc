SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cr_eval_aggregation
-- ----------------------------
CREATE TABLE `cr_eval_aggregation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `eval_indicator_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联指标项id',
  `collection_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联采集变量id\r\n关联采集变量id',
  `aggregating_algorithm_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '聚合算法id',
  `aggregating_algorithm_params` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '聚合算法参数',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
  INDEX `eval_indicator_id`(`eval_indicator_id`) USING BTREE,
  INDEX `aggregating_algorithm_id`(`aggregating_algorithm_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_eval_algorithm_ref
-- ----------------------------
CREATE TABLE `cr_eval_algorithm_ref`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` int(11) NOT NULL,
  `algorithm_language` int(11) NOT NULL,
  `code` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_eval_algorithm
-- ----------------------------
CREATE TABLE `cr_eval_algorithm`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `state` int(11) NULL DEFAULT NULL COMMENT ' 0：代表正常，1：代表编辑过，页面看不到',
  `type` int(11) NULL DEFAULT NULL COMMENT '格式化算法：1，匹配算法：2，评分算法：3，聚合算法：4',
  `algorithm_language` int(11) NULL DEFAULT NULL,
  `code` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '算法语言\r\n1：python',
  `params` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `params_num` int(11) NULL DEFAULT NULL,
  `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_eval_collection
-- ----------------------------
CREATE TABLE `cr_eval_collection`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `eval_indicator_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联指标项id',
  `format_algorithm_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '格式化算法id',
  `format_algorithm_params` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '格式化算法参数',
  `detection_algorithm_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检测算法id',
  `detection_algorithm_params` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检测算法参数',
  `score_algorithm_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评分算法id',
  `score_algorithm_params` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评分算法参数',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
  INDEX `eval_indicator_id`(`eval_indicator_id`) USING BTREE,
  INDEX `format_algorithm_id`(`format_algorithm_id`) USING BTREE,
  INDEX `detection_algorithm_id`(`detection_algorithm_id`) USING BTREE,
  INDEX `score_algorithm_id`(`score_algorithm_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_eval_index_ref
-- ----------------------------
CREATE TABLE `cr_eval_index_ref`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` int(11) NOT NULL,
  `score` int(11) NULL DEFAULT NULL,
  `level` int(11) NOT NULL,
  `record_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `algorithm_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `next_ids` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `start_time` datetime NULL DEFAULT NULL,
  `end_time` datetime NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `algorithm_params` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `specialist_value` int(11) NULL DEFAULT NULL,
  `specialist_type` int(11) NULL DEFAULT NULL,
  `specialist_attr` int(11) NULL DEFAULT NULL,
  `specialist_attr_value` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
  INDEX `record_id`(`record_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_eval_index
-- ----------------------------
CREATE TABLE `cr_eval_index`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `level` int(11) NULL DEFAULT NULL COMMENT '指标级别：1、2、3',
  `eval_system_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '体系id',
  `eval_indicator_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指标项id',
  `type` int(11) NULL DEFAULT NULL COMMENT '归一化类型, 百分比:1,周期:2,延时:3',
  `aggregating_algorithm_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '聚合算法id',
  `aggregating_algorithm_params` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '聚合算法参数',
  `children_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子指标id',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
  INDEX `eval_system_id`(`eval_system_id`) USING BTREE,
  INDEX `eval_indicator_id`(`eval_indicator_id`) USING BTREE,
  INDEX `aggregating_algorithm_id`(`aggregating_algorithm_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_eval_indicator
-- ----------------------------
CREATE TABLE `cr_eval_indicator`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `attribute` int(11) NULL DEFAULT NULL,
  `attribute_value` int(11) NULL DEFAULT NULL,
  `datatype` int(11) NULL DEFAULT NULL,
  `connlist` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '连接线',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `aggregating_algorithm_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '聚合算法id',
  `aggregating_algorithm_params` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '聚合算法参数',
  `aggregation_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联聚合项id',
  `collection_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联变量项id',
  `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
  INDEX `aggregating_algorithm_id`(`aggregating_algorithm_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_eval_probe_data
-- ----------------------------
CREATE TABLE `cr_eval_probe_data`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `scene_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `device_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `probe_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `collection_time` datetime NULL DEFAULT NULL,
  `device_ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_eval_record
-- ----------------------------
CREATE TABLE `cr_eval_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NOT NULL,
  `score` int(11) NULL DEFAULT NULL,
  `start_time` datetime NULL DEFAULT NULL,
  `end_time` datetime NULL DEFAULT NULL,
  `system_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `task_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `message` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `eval_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
  INDEX `room_id`(`task_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_eval_system
-- ----------------------------
CREATE TABLE `cr_eval_system`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `connlist` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '连接线',
  `first_index_num` int(11) NULL DEFAULT NULL,
  `second_index_num` int(11) NULL DEFAULT NULL,
  `third_index_num` int(11) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_eval_task
-- ----------------------------
CREATE TABLE `cr_eval_task`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `template_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `template_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `task_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `local_scene_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mp_scene_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `copyed_topo_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NULL DEFAULT NULL,
  `enable_recording` tinyint(1) NOT NULL,
  `create_user_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_eval_task_step
-- ----------------------------
CREATE TABLE `cr_eval_task_step`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `task_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stage` tinyint(4) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `process` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `type` tinyint(4) NOT NULL,
  `start_time` datetime NULL DEFAULT NULL,
  `end_time` datetime NULL DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_eval_template
-- ----------------------------
CREATE TABLE `cr_eval_template`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `category_code` int(11) NOT NULL,
  `step_config` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `enable_recording` tinyint(1) NULL DEFAULT NULL,
  `current_task_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `topo_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `logo` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime NOT NULL,
  `create_time` datetime NOT NULL,
  `create_user_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_eval_variable_ref_probe_data
-- ----------------------------
CREATE TABLE `cr_eval_variable_ref_probe_data`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `variable_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `probe_data_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `formatted_value` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `variable_id`(`variable_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_eval_variable_ref
-- ----------------------------
CREATE TABLE `cr_eval_variable_ref`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `score` int(11) NULL DEFAULT NULL,
  `device_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `probe_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `record_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `progress` int(11) NULL DEFAULT NULL,
  `format_start_time` datetime NULL DEFAULT NULL,
  `format_end_time` datetime NULL DEFAULT NULL,
  `match_start_time` datetime NULL DEFAULT NULL,
  `match_end_time` datetime NULL DEFAULT NULL,
  `mark_start_time` datetime NULL DEFAULT NULL,
  `mark_end_time` datetime NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `format_algorithm_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `match_algorithm_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `mark_algorithm_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `format_params` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `match_params` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mark_params` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
  INDEX `record_id`(`record_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
