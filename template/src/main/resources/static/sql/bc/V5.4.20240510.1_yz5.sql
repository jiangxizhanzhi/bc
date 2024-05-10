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