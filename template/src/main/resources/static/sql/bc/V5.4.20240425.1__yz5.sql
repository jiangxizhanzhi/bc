ALTER TABLE cr_eval_template ADD COLUMN type tinyint(4) NULL DEFAULT 0 COMMENT '模板类型';
ALTER TABLE cr_eval_task ADD COLUMN phase text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '试验阶段';

CREATE TABLE `cr_eval_tool`  (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
                                 `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '试验名称',
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


ALTER TABLE `cr_eval_template`
    ADD COLUMN `exp_condition` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL  COMMENT '试验条件';
ALTER TABLE `cr_eval_template`
    ADD COLUMN `method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL  COMMENT '试验方法';
ALTER TABLE `cr_eval_template`
    ADD COLUMN `target` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL  COMMENT '试验目标';

INSERT INTO cr_constants_config (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (146, NULL, 0, 41, '边界及终端防护靶标', 'Boundary and terminal protection targets');
INSERT INTO cr_constants_config (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (147, NULL, 1, 41, '入侵检测靶标', 'Intrusion detection targets');
INSERT INTO cr_constants_config (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (148, NULL, 2, 41, '日志分析靶标', 'Log analysis target');
INSERT INTO cr_constants_config (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (149, NULL, 3, 41, '网络集中管控靶标', 'Network centralized control target');
INSERT INTO cr_constants_config (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (150, NULL, 4, 41, '流量分析靶标', 'Traffic analysis targets');
INSERT INTO cr_constants_config (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (151, NULL, 5, 41, '应用服务靶标', 'Application service target');
