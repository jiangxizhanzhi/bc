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
                                             `result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '验证结果',
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
                                              `condition` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '试验条件',
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
