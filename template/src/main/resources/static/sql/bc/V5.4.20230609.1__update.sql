-- ----------------------------
-- Table structure for cr_rb_common_device
-- ----------------------------
DROP TABLE IF EXISTS `cr_rb_common_device`;
CREATE TABLE `cr_rb_common_device`  (
                                        `id` int NOT NULL AUTO_INCREMENT,
                                        `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `room_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `device_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `collect_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `create_time` datetime NULL DEFAULT NULL,
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE INDEX `resource_id_uni`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '红蓝常用资产表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for cr_team_common_device
-- ----------------------------
DROP TABLE IF EXISTS `cr_team_common_device`;
CREATE TABLE `cr_team_common_device`  (
                                          `id` int NOT NULL AUTO_INCREMENT,
                                          `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                          `room_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                          `device_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                          `collect_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                          `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                          `create_time` datetime NULL DEFAULT NULL,
                                          PRIMARY KEY (`id`) USING BTREE,
                                          UNIQUE INDEX `resource_id_uni`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for cr_team_device
-- ----------------------------
DROP TABLE IF EXISTS `cr_team_device`;
CREATE TABLE `cr_team_device`  (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `room_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `device_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `create_time` datetime NULL DEFAULT NULL,
                                   `update_time` datetime NULL DEFAULT NULL,
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `resource_id_uni`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- 我的笔记添加常量
-- ----------------------------
INSERT INTO `cr_constants_config` (`resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 0, 39, '尝试过', 'Attempted');

