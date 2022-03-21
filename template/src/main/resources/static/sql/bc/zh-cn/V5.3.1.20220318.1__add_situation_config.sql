SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for situation_config
-- ----------------------------
CREATE TABLE IF NOT EXISTS `situation_config`  (
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `scope` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `node_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `node_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `node_type` tinyint(4) NOT NULL,
                                     `attribute` tinyint(4) NOT NULL,
                                     `attribute_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `resource_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;