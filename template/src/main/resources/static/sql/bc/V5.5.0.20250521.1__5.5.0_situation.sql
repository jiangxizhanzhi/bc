DROP TABLE IF EXISTS `scene_property_config`;
CREATE TABLE `scene_property_config`  (
                                          `id` int(11) NOT NULL AUTO_INCREMENT,
                                          `scene` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '场景',
                                          `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置描述',
                                          `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置值',
                                          PRIMARY KEY (`id`) USING BTREE,
                                          UNIQUE INDEX `idx_name`(`scene`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;