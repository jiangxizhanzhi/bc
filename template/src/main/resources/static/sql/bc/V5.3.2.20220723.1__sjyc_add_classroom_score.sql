SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oj_classroom_scores
-- ----------------------------
DROP TABLE IF EXISTS `oj_classroom_scores`;
CREATE TABLE `oj_classroom_scores`  (
                                        `id` int NOT NULL AUTO_INCREMENT,
                                        `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                        `task_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                        `classroom_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                        `lesson_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                        `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                        `count` int NOT NULL,
                                        `score` tinyint NOT NULL,
                                        `create_time` datetime NOT NULL,
                                        `update_time` datetime NOT NULL,
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;