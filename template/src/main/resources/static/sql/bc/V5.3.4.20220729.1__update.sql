-- ----------------------------
-- 20220719.1__situation_add_scheme.sql
-- ----------------------------
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- Table structure for situation_attack_message
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

-- Table structure for situation_dynamic_scheme
CREATE TABLE `situation_dynamic_scheme`  (
                                             `id` int NOT NULL AUTO_INCREMENT,
                                             `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                             `scene_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                             `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                             `scheme_data` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                             `create_time` datetime NULL DEFAULT NULL,
                                             `update_time` datetime NULL DEFAULT NULL,
                                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- Table structure for situation_scene_config
CREATE TABLE `situation_scene_config`  (
                                           `id` int NOT NULL AUTO_INCREMENT,
                                           `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                           `scene_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                           `flow_analysis` tinyint(1) NULL DEFAULT NULL,
                                           `flow_auto_run` tinyint(1) NULL DEFAULT NULL,
                                           `create_time` datetime NULL DEFAULT NULL,
                                           `update_time` datetime NULL DEFAULT NULL,
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;


-- ----------------------------
-- 20220721.1__modify_cr_eval.sql
-- ----------------------------
alter table cr_eval_record add column `eval_end_time` datetime NULL DEFAULT NULL;
alter table cr_eval_index_ref add column `progress` int(11) NULL DEFAULT NULL;
alter table cr_eval_index_ref modify column `algorithm_params` longtext DEFAULT NULL;
alter table cr_eval_variable_ref modify column `format_params` longtext DEFAULT NULL;
alter table cr_eval_variable_ref modify column `match_params` longtext DEFAULT NULL;
alter table cr_eval_variable_ref modify column `mark_params` longtext DEFAULT NULL;
alter table cr_eval_algorithm_ref modify column `code` text DEFAULT NULL;
alter table cr_eval_variable_ref_probe_data modify column `formatted_value` text DEFAULT NULL;
alter table cr_eval_probe_data modify column `content` text NOT NULL;
alter table cr_eval_record modify column `message` longtext DEFAULT NULL;

-- ----------------------------
-- 20220723.1__rtks_situation
-- ----------------------------
CREATE TABLE `rtks_situation_log`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `user_id`     varchar(32)  NOT NULL,
    `user_name`   varchar(255) NOT NULL,
    `role_id`     varchar(32)  NOT NULL,
    `role_name`   varchar(255) NOT NULL,
    `type`        tinyint(1)   NOT NULL,
    `create_time` datetime     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `user_role_type` (`user_id`, `role_id`, `type`) USING BTREE
);


CREATE TABLE `rtks_role_match_record`
(
    `id`          int(11)     NOT NULL AUTO_INCREMENT,
    `user_id`     varchar(32) NOT NULL,
    `role_id`     varchar(32) NOT NULL,
    `match`       tinyint(4)  NOT NULL,
    `create_time` datetime    NULL,
    `update_time` datetime    NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `user_role` (`user_id`, `role_id`) USING BTREE,
    INDEX `role_id` (`role_id`) USING BTREE
);

ALTER TABLE `cr_mission_template`
    ADD INDEX `template_id` (`template_id`) USING BTREE,
    ADD INDEX `business_template_id` (`business_template_id`) USING BTREE;

ALTER TABLE `cr_mission`
    ADD UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE,
    ADD INDEX `room_id` (`room_id`) USING BTREE,
    ADD INDEX `mission_template_id` (`mission_template_id`) USING BTREE;

ALTER TABLE `cr_train_record`
    ADD INDEX `room_id` (`room_id`) USING BTREE;

ALTER TABLE `attachment`
    ADD UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE;

ALTER TABLE `cr_rb_device`
    ADD UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE,
    ADD UNIQUE INDEX `room_device_user` (`room_id`, `device_id`, `create_user_id`) USING BTREE;

ALTER TABLE `cr_rb_time_management`
    ADD UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE,
    ADD UNIQUE INDEX `room_id` (`room_id`) USING BTREE;

ALTER TABLE `cr_score`
    ADD UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE,
    ADD UNIQUE INDEX `room_user` (`room_id`, `user_id`) USING BTREE;


ALTER TABLE `rtks_engage_category`
    ADD UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE;

ALTER TABLE `rtks_role_related`
    ADD INDEX `role_id` (`role_id`) USING BTREE;

ALTER TABLE `situation_attack_message`
    ADD UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE,
    ADD INDEX `scene_id` (`scene_id`) USING BTREE;

ALTER TABLE `situation_config`
    ADD UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE;

ALTER TABLE `situation_dynamic_scheme`
    ADD UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE,
    ADD INDEX `scene_id` (`scene_id`) USING BTREE;

ALTER TABLE `situation_scene_config`
    ADD UNIQUE INDEX `scene_id` (`scene_id`) USING BTREE;


-- ----------------------------
-- 20220725.1__delete_improper_initial_data
-- ----------------------------
DELETE
FROM `rtks_user_role`
WHERE `id` = 7
  and role_id = '542fc3e2caf0fa9b4935432ffe4daafa';
DELETE
FROM `rtks_user_role`
WHERE `id` = 8
  and role_id = 'eca75b7f8a701bc544911330f7f7c7b2';
DELETE
FROM `rtks_user_role`
WHERE `id` = 9
  and role_id = '9d6c15369048cbd3575bf9b3cae0c502';
DELETE
FROM `rtks_user_role`
WHERE `id` = 10
  and role_id = 'e25dc8c9d15ddd621f16616083d68726';

-- ----------------------------
-- 20220726.1__add_cr_constants_config
-- ----------------------------
ALTER TABLE `cr_constants_config` CHANGE `code` `code` SMALLINT(4) NULL;
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 0, 14, '未请求协助', 'Assistance Not Requested');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 14, '请求协助', 'Request Assistance');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 14, '协助中', 'Assistance In Progress');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 14, '结束协助', 'End Assistance');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 15, '初级', 'Primary');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 15, '中级', 'Advanced');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 15, '高级', 'Senior');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 0, 16, '理论课', 'Theory Class');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 16, '实验课', 'Experimental Class');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 0, 17, '未配置', 'Not Configured');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 17, '选修', 'Elective');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 17, '必修', 'Required');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 18, '课程', 'Course');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 18, '课时', 'Class');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 18, '实操题', 'Practice');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 19, '笔记', 'Note');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 19, '实验报告', 'Report');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 0, 20, '理论基础', 'Theoretical Basis');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 20, '真实漏洞', 'Real Vulnerability');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 20, '实践操作', 'Operation Practice');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 20, '课程', 'Course');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 4, 20, '课时', 'Class');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 0, 21, '单选', 'Single Choice');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 21, '多选', 'Multiple Choice');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 21, '判断', 'True Or False');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 21, '填空', 'Fill In The Blanks');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 4, 21, '简答', 'Short Answer');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 22, '初级', 'Primary');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 22, '中级', 'Advanced');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 22, '高级', 'Senior');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 23, '初级', 'Primary');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 23, '中级', 'Advanced');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 23, '高级', 'Senior');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 24, '初级', 'Primary');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 24, '中级', 'Advanced');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 24, '高级', 'Senior');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 0, 25, '未开始', 'Not Started');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 25, '进行中', 'In Progress');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 25, '已结束', 'Ended');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 0, 26, '个人参赛', 'Personal');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 26, '团队参赛', 'Group');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 0, 27, '理论赛', 'Theoretical Competition');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 27, '运维赛', 'Operation And Maintenance Competition');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 27, '夺旗赛', 'Flag Competition');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 27, '攻防赛', 'Offensive And Defensive Competition');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 4, 27, '渗透赛', 'Infiltration Competition');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 5, 27, '人工智能赛', 'AI Competition');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 0, 28, '未开始', 'Not Started');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 28, '进行中', 'In Progress');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 28, '已结束', 'Ended');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 28, '暂停中', 'Suspended');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 31, '课程', 'Course');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 31, '课时', 'Class');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 31, '实践操作', 'Operation Practice');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 36, '单兵训练', 'Single Person Training');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 36, '团队训练', 'Team Training');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 4, 36, '红蓝对抗', 'Red Blue Confrontation');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 0, 37, '初级', 'Primary');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 37, '中级', 'Advanced');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 37, '高级', 'Senior');


-- ----------------------------
-- 20220726.2__modify_practice_fields_length
-- ----------------------------
-- 禅道7140
ALTER TABLE `oj_practice_theory` MODIFY COLUMN `answer` varchar(4096) NOT NULL;
ALTER TABLE `oj_practice_theory` MODIFY COLUMN `blank` varchar(4096) DEFAULT NULL;
ALTER TABLE `oj_practice_theory` MODIFY COLUMN `title` varchar(2048) NOT NULL;


# bug-9981
ALTER TABLE `oj_practice_theory` MODIFY COLUMN `content` varchar(960) NOT NULL;

