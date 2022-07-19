SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for situation_attack_message
-- ----------------------------
CREATE TABLE `situation_attack_message`  (
                                             `id` int NOT NULL AUTO_INCREMENT,
                                             `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                             `scene_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                             `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                             `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                             `source_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                             `target_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                             `interval_time` int NULL DEFAULT NULL,
                                             `duration_time` int NULL DEFAULT NULL,
                                             `look_at_target` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                             `line_color` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                             `line_width` int NULL DEFAULT NULL,
                                             `line_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                             `media_animation` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                             `create_time` datetime NULL DEFAULT NULL,
                                             `update_time` datetime NULL DEFAULT NULL,
                                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_dynamic_scheme
-- ----------------------------
CREATE TABLE `situation_dynamic_scheme`  (
                                             `id` int NOT NULL AUTO_INCREMENT,
                                             `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                             `scene_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                             `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                             `scheme_data` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                             `schedule` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                             `create_time` datetime NULL DEFAULT NULL,
                                             `update_time` datetime NULL DEFAULT NULL,
                                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for situation_scene_config
-- ----------------------------
CREATE TABLE `situation_scene_config`  (
                                           `id` int NOT NULL AUTO_INCREMENT,
                                           `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                           `scene_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                           `scheme_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                           `flow_analysis` tinyint(1) NULL DEFAULT NULL,
                                           `flow_auto_run` tinyint(1) NULL DEFAULT NULL,
                                           `create_time` datetime NULL DEFAULT NULL,
                                           `update_time` datetime NULL DEFAULT NULL,
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;