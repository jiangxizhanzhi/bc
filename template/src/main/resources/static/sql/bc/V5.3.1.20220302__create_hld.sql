SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cr_hld_room
-- ----------------------------
CREATE TABLE IF NOT EXISTS `cr_hld_room` (
    `id` int NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `template_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `task_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `local_scene_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `copyed_topo_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `role_config` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `create_user_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `modify_user_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_time` datetime NULL DEFAULT NULL,
    `update_time` datetime NULL DEFAULT NULL,
    `status` int NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
    INDEX `business_template_id`(`local_scene_id`) USING BTREE
    ) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;