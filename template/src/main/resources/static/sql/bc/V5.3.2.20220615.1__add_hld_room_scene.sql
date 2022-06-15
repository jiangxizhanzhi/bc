SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cr_hld_room_scene
-- ----------------------------
CREATE TABLE `cr_hld_room_scene`  (
                                      `id` int NOT NULL AUTO_INCREMENT,
                                      `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `hld_room_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `mp_scene_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `start_time` datetime NOT NULL,
                                      `end_time` datetime NULL DEFAULT NULL,
                                      `create_time` datetime NULL DEFAULT NULL,
                                      `update_time` datetime NULL DEFAULT NULL,
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;