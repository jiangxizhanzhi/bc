SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sjyc_task
-- ----------------------------
DROP TABLE IF EXISTS `sjyc_task`;
CREATE TABLE `sjyc_task`  (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `task_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                              `target` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              `result` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                              `start_time` datetime NULL DEFAULT NULL,
                              `end_time` datetime NULL DEFAULT NULL,
                              `duration` double NULL DEFAULT NULL,
                              `validity_period` int NULL DEFAULT NULL,
                              `trainees` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                              `trainees_count` int NULL DEFAULT NULL,
                              `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              `create_time` datetime NOT NULL,
                              `update_time` datetime NOT NULL,
                              `event_type` int NULL DEFAULT NULL,
                              `event_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sjyc_org_users
-- ----------------------------
DROP TABLE IF EXISTS `sjyc_org_users`;
CREATE TABLE `sjyc_org_users`  (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `class_room_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `org_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `user_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `create_time` datetime NOT NULL,
                                   `update_time` datetime NOT NULL,
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;