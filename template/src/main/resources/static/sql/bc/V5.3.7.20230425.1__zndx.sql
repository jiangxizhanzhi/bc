-- ----------------------------
-- Table structure for oj_practice_wrong
-- ----------------------------
DROP TABLE IF EXISTS `oj_practice_wrong`;
CREATE TABLE `oj_practice_wrong`  (
                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                      `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `submit_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
                                      `practice_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '习题id',
                                      `practice_type` int(1) NOT NULL COMMENT '习题类型',
                                      `create_time` datetime(0) NULL DEFAULT NULL,
                                      `update_time` datetime(0) NULL DEFAULT NULL,
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;