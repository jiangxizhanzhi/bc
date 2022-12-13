ALTER TABLE `oj_practice_operation`
    MODIFY COLUMN `practice_set_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '关联习题集id' AFTER `builtin`;

ALTER TABLE `cr_business_template`
    ADD UNIQUE INDEX `cr_business_template_name_type`(`name`, `business_type`) USING BTREE;

delete from `cr_constants_config` where type in (15,22,23,24,37);

INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 15, '初级', 'Primary');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 15, '中级', 'Intermediate');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 15, '高级', 'Advanced');

INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 22, '初级', 'Primary');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 22, '中级', 'Intermediate');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 22, '高级', 'Advanced');

INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 23, '初级', 'Primary');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 23, '中级', 'Intermediate');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 23, '高级', 'Advanced');

INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 24, '初级', 'Primary');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 24, '中级', 'Intermediate');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 24, '高级', 'Advanced');

INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 0, 37, '初级', 'Primary');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 37, '中级', 'Intermediate');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 37, '高级', 'Advanced');

ALTER TABLE `oj_practice_operation`
    MODIFY COLUMN `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;

-- ----------------------------
-- Table structure for oj_course_study_history
-- ----------------------------
DROP TABLE IF EXISTS `oj_course_study_history`;
CREATE TABLE `oj_course_study_history`  (
                                            `id` int(11) NOT NULL AUTO_INCREMENT,
                                            `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                            `course_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                            `course_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                            `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                            `create_time` datetime(0) NULL DEFAULT NULL,
                                            `update_time` datetime(0) NULL DEFAULT NULL,
                                            PRIMARY KEY (`id`) USING BTREE,
                                            UNIQUE INDEX `course_user_uniq`(`course_id`, `user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

ALTER TABLE cr_mission_template MODIFY COLUMN option text COMMENT '选择题选项';
ALTER TABLE cr_mission MODIFY COLUMN option text COMMENT '选择题选项';

ALTER TABLE cr_mission_template MODIFY COLUMN `write_up` LONGTEXT COMMENT '题解';
ALTER TABLE cr_mission MODIFY COLUMN `write_up` LONGTEXT COMMENT '题解';