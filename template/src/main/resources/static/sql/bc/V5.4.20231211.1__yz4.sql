INSERT INTO `bc_5_4_1_yz4`.`cr_constants_config`(`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (146, NULL, 1, 41, '武器工具测试', 'Weapon Tool Testing');
INSERT INTO `bc_5_4_1_yz4`.`cr_constants_config`(`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (147, NULL, 2, 41, '攻击流量分析', 'Attack Traffic Analysis');
INSERT INTO `bc_5_4_1_yz4`.`cr_constants_config`(`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (148, NULL, 3, 41, '漏洞测试', 'Vulnerability Testing');
INSERT INTO `bc_5_4_1_yz4`.`cr_constants_config`(`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (149, NULL, 4, 41, '攻防验证', 'Attack And Defense Verification');
INSERT INTO `bc_5_4_1_yz4`.`cr_constants_config`(`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (150, NULL, 5, 41, '技战法研究', 'Research On Technical Warfare Methods');


-- ----------------------------
-- Table structure for cr_eval_tool
-- ----------------------------
DROP TABLE IF EXISTS `cr_eval_tool`;
CREATE TABLE `cr_eval_tool`  (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
                                 `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '试验名称',
                                 `action` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预定义动作',
                                 `action` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预定义动作',
                                 `create_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
                                 `update_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cr_eval_tool
-- ----------------------------
INSERT INTO `cr_eval_tool` VALUES (1, '1', 'nmap', 'nmap -sS -A -T4 [ip]', '2023-12-11 17:54:35', '2023-12-11 17:54:35');
INSERT INTO `cr_eval_tool` VALUES (2, '2', 'hydra', 'hydra -l [username] -P password_list [ip] [protocol]', '2023-12-11 17:54:35', '2023-12-11 17:54:35');
INSERT INTO `cr_eval_tool` VALUES (3, '3', 'sqlmap', 'sqlmap -u “[url]” –dbs', '2023-12-11 17:54:35', '2023-12-11 17:54:35');


DROP TABLE IF EXISTS `cr_eval_mission_template`;
CREATE TABLE `cr_eval_mission_template`  (
                                             `id` int(11) NOT NULL AUTO_INCREMENT,
                                             `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'uuid',
                                             `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名称',
                                             `step` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实施步骤',
                                             `tools` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '工具',
                                             `limit_time` int(5) NULL DEFAULT NULL COMMENT '任务限时',
                                             `remaining_time` int(5) NULL DEFAULT NULL COMMENT '任务剩余时间',
                                             `result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '验证结果',
                                             `status`  smallint(5) UNSIGNED NOT NULL DEFAULT 0 COMMENT '任务状态',
                                             `task_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联taskid',
                                             `create_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
                                             `update_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
                                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for cr_eval_scheme_template
-- ----------------------------
DROP TABLE IF EXISTS `cr_eval_scheme_template`;
CREATE TABLE `cr_eval_scheme_template`  (
                                            `id` int(11) NOT NULL AUTO_INCREMENT,
                                            `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
                                            `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技战法名称',
                                            `description` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '技战法描述',
                                            `type` tinyint(1) NULL DEFAULT NULL COMMENT '典型攻击技战法、自定义技战法',
                                            `template_data` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '技战法阶段数据',
                                            `template_category` tinyint(1) NULL DEFAULT NULL COMMENT '模板类型',
                                            `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '典型攻击技战法左侧数据',
                                            `create_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
                                            `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                            `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
                                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for cr_eval_template_template
-- ----------------------------
DROP TABLE IF EXISTS `cr_eval_template_template`;
CREATE TABLE `cr_eval_template_template`  (
              `id` int(11) NOT NULL AUTO_INCREMENT,
              `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
              `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '试验名称',
              `target` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '试验目标',
              `logo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'logo',
              `exp_condition` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '试验条件',
              `method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '试验方法',
              `type` tinyint(1) NULL DEFAULT NULL COMMENT '测试类型',
              `scheme` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务方案附件',
              `topo_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联topo uuid',
              `missions_scheme` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '任务方案',
              `create_user_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人id',
              `create_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
              `update_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;



ALTER TABLE `cr_eval_template`
    ADD COLUMN `missions_scheme` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '任务方案';

ALTER TABLE `cr_eval_task`
    ADD COLUMN `mission_scheme` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '任务方案';


ALTER TABLE `cr_eval_template`
    ADD COLUMN `exp_condition` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL  COMMENT '试验条件';
ALTER TABLE `cr_eval_template`
    ADD COLUMN `method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL  COMMENT '试验方法';
ALTER TABLE `cr_eval_template`
    ADD COLUMN `scheme` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL  COMMENT '任务方案附件';

CREATE TABLE `cr_eval_mission_template_log`  (
              `id` int(11) NOT NULL AUTO_INCREMENT,
              `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
              `user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户',
              `operate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作',
              `mission_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务id',
              `mission_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名称',
              `task_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联模板任务id',
              `create_user_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人id',
              `create_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
              `update_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


CREATE TABLE `cr_eval_node_log`  (
              `id` int(11) NOT NULL AUTO_INCREMENT,
              `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
              `user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户',
              `operate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作',
              `node_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点id',
              `node_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名称',
              `task_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联模板任务id',
              `create_user_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人id',
              `create_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
              `update_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

CREATE TABLE `cr_eval_tool_log`  (
              `id` int(11) NOT NULL AUTO_INCREMENT,
              `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
              `user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户',
              `operate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作',
              `tool_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工具id',
              `tool_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工具名称',
              `create_user_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人id',
              `create_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
              `update_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 42, '开始任务', 'Start Task');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 42, '暂停任务', 'Suspend Task');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 42, '继续任务', 'Continue Task');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 4, 42, '中止任务', 'Abort A Mission');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 5, 42, '任务失败', 'Task Fail');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 6, 42, '完成任务', 'End Task');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 7, 42, '任务超时', 'Task Overtime');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 8, 42, '新增任务', 'New Task');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 0, 43, '接入节点', 'Access Node');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 43, '关闭靶标', 'Closing Target');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 43, '重启靶标', 'Reset Target');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 43, '重建靶标', 'Recreate Target');