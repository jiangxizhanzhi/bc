/*
 Navicat Premium Data Transfer

 Source Server         : 10.100.1.26-3305
 Source Server Type    : MariaDB
 Source Server Version : 100407
 Source Host           : 10.100.1.26:3305
 Source Schema         : ojj

 Target Server Type    : MariaDB
 Target Server Version : 100407
 File Encoding         : 65001

*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for attachment
-- ----------------------------
CREATE TABLE `attachment`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `file_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `file_path` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `create_time` datetime NOT NULL,
                               `update_time` datetime NOT NULL,
                               `public` int(1) NOT NULL,
                               `business_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_business_template
-- ----------------------------
CREATE TABLE `cr_business_template`  (
                                         `id` int(11) NOT NULL AUTO_INCREMENT,
                                         `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                         `local_scene_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                         `topo_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                         `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                         `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                         `business_type` int(11) NOT NULL,
                                         `difficulty` int(11) NOT NULL,
                                         `update_time` datetime NOT NULL,
                                         `create_time` datetime NOT NULL,
                                         `create_user_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                         `operation_auth_type` int(11) NULL DEFAULT NULL,
                                         `operation_auth_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                         `read_auth_type` int(11) NULL DEFAULT NULL,
                                         `read_auth_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                         PRIMARY KEY (`id`) USING BTREE,
                                         UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_mission
-- ----------------------------
CREATE TABLE `cr_mission`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `create_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `score` smallint(6) NULL DEFAULT NULL,
                               `skill_points` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `option` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `type` tinyint(4) NULL DEFAULT NULL,
                               `category_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `write_up` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `create_time` datetime NULL DEFAULT NULL,
                               `update_time` datetime NULL DEFAULT NULL,
                               `show_writeup` tinyint(1) NULL DEFAULT NULL,
                               `room_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_mission_category
-- ----------------------------
CREATE TABLE `cr_mission_category`  (
                                        `id` int(11) NOT NULL AUTO_INCREMENT,
                                        `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_mission_scheme_template
-- ----------------------------
CREATE TABLE `cr_mission_scheme_template`  (
                                               `id` int(11) NOT NULL AUTO_INCREMENT,
                                               `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                               `create_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                               `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                               `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                               `template_data` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                               `template_config` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                               `create_time` datetime NULL DEFAULT NULL,
                                               `update_time` datetime NULL DEFAULT NULL,
                                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_mission_template
-- ----------------------------
CREATE TABLE `cr_mission_template`  (
                                        `id` int(11) NOT NULL AUTO_INCREMENT,
                                        `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `score` smallint(6) NULL DEFAULT NULL,
                                        `skill_points` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `option` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `type` tinyint(4) NULL DEFAULT NULL,
                                        `category_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `write_up` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `create_time` datetime NULL DEFAULT NULL,
                                        `update_time` datetime NULL DEFAULT NULL,
                                        `template_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `attachment` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_radar_label
-- ----------------------------
CREATE TABLE `cr_radar_label`  (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `resource_id` int(11) NULL DEFAULT NULL,
                                   `label` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_rb_device
-- ----------------------------
CREATE TABLE `cr_rb_device`  (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `room_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `device_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `create_time` datetime NULL DEFAULT NULL,
                                 `update_time` datetime NULL DEFAULT NULL,
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_rb_room
-- ----------------------------
CREATE TABLE `cr_rb_room`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `template_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `template_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `sub_type` int(11) NOT NULL,
                               `pre_skill` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `train_skill` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `start_time` datetime NOT NULL,
                               `end_time` datetime NOT NULL,
                               `difficulty` int(11) NOT NULL,
                               `training_status` int(11) NULL DEFAULT NULL,
                               `scene_file` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `create_user_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `modify_user_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `local_scene_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `copyed_topo_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `create_time` datetime NULL DEFAULT NULL,
                               `update_time` datetime NULL DEFAULT NULL,
                               `enable_recording` tinyint(1) NULL DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE,
                               UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
                               INDEX `team_template_id`(`template_id`) USING BTREE,
                               INDEX `training_status`(`training_status`) USING BTREE,
                               INDEX `create_user_id`(`create_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_rb_template
-- ----------------------------
CREATE TABLE `cr_rb_template`  (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `business_template_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `pre_skill` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `train_skill` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `role_config` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `sub_type` tinyint(4) NOT NULL,
                                   `duration_time` int(11) NOT NULL,
                                   `create_time` datetime NULL DEFAULT NULL,
                                   `update_time` datetime NULL DEFAULT NULL,
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
                                   INDEX `business_template_id`(`business_template_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_rb_time_management
-- ----------------------------
CREATE TABLE `cr_rb_time_management`  (
                                          `id` int(11) NOT NULL AUTO_INCREMENT,
                                          `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                          `room_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                          `user_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                          `astronomical_time` datetime NOT NULL,
                                          `combat_time` datetime NOT NULL,
                                          `rate` smallint(5) UNSIGNED NOT NULL,
                                          `status` smallint(5) UNSIGNED NOT NULL,
                                          `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                          `create_time` datetime(6) NOT NULL,
                                          `update_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                          `update_time` datetime(6) NOT NULL,
                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_role
-- ----------------------------
CREATE TABLE `cr_role`  (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                            `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                            `room_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                            `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `role_group` int(11) NOT NULL,
                            `nodes` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `user_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                            `exercise_paper` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `parent_resource_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `mission_scheme` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `business_type` int(11) NOT NULL,
                            `create_time` datetime NULL DEFAULT NULL,
                            `update_time` datetime NULL DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
                            INDEX `business_instance_id`(`room_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_score
-- ----------------------------
CREATE TABLE `cr_score`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                             `room_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                             `user_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                             `training_score` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                             `training_percentage` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                             `skill_percentage` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                             `skill_score` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                             `comment` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                             `final_score` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                             `create_time` datetime NOT NULL,
                             `update_time` datetime NOT NULL,
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_single_room
-- ----------------------------
CREATE TABLE `cr_single_room`  (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `business_template_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `start_time` datetime NULL DEFAULT NULL,
                                   `end_time` datetime NULL DEFAULT NULL,
                                   `training_record` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                   `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `create_time` datetime NULL DEFAULT NULL,
                                   `update_time` datetime NULL DEFAULT NULL,
                                   `local_scene_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `is_finish` tinyint(1) NOT NULL,
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
                                   UNIQUE INDEX `business_template_id__user_id__uniq`(`business_template_id`, `user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_single_template
-- ----------------------------
CREATE TABLE `cr_single_template`  (
                                       `id` int(11) NOT NULL AUTO_INCREMENT,
                                       `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                       `business_template_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                       `pre_skills` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                       `train_skills` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                       `sub_type` tinyint(4) NOT NULL,
                                       `duration_time` int(10) UNSIGNED NOT NULL,
                                       `missions_scheme` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                       `create_time` datetime NULL DEFAULT NULL,
                                       `update_time` datetime NULL DEFAULT NULL,
                                       PRIMARY KEY (`id`) USING BTREE,
                                       UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
                                       INDEX `business_template_id`(`business_template_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_task
-- ----------------------------
CREATE TABLE `cr_task`  (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `status` tinyint(4) NULL DEFAULT NULL,
                            `mission_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `user_answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `release_time` datetime NULL DEFAULT NULL,
                            `last_submit_time` datetime NULL DEFAULT NULL,
                            `task_start_time` datetime NULL DEFAULT NULL,
                            `room_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `business_type` int(11) NULL DEFAULT NULL,
                            `score` smallint(6) NULL DEFAULT NULL,
                            `create_time` datetime NULL DEFAULT NULL,
                            `update_time` datetime NULL DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
                            INDEX `mission_id`(`mission_id`) USING BTREE,
                            INDEX `user_id`(`user_id`) USING BTREE,
                            INDEX `room_id`(`room_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_task_log
-- ----------------------------
CREATE TABLE `cr_task_log`  (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                `room_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                `mission_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                `action_code` tinyint(4) NULL DEFAULT NULL,
                                `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                `create_time` datetime NULL DEFAULT NULL,
                                `update_time` datetime NULL DEFAULT NULL,
                                `task_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
                                INDEX `mission_id`(`mission_id`) USING BTREE,
                                INDEX `room_id`(`room_id`) USING BTREE,
                                INDEX `user_id`(`user_id`) USING BTREE,
                                INDEX `action_code`(`action_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_task_submit
-- ----------------------------
CREATE TABLE `cr_task_submit`  (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `room_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `task_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `answer_extension` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `evaluator_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `evaluate_time` datetime NULL DEFAULT NULL,
                                   `evaluate_status` tinyint(4) NULL DEFAULT NULL,
                                   `reason` tinyint(4) NULL DEFAULT NULL,
                                   `detail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `score` smallint(6) NULL DEFAULT NULL,
                                   `mission_score` smallint(6) NULL DEFAULT NULL,
                                   `create_time` datetime NULL DEFAULT NULL,
                                   `update_time` datetime NULL DEFAULT NULL,
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
                                   INDEX `task_id`(`task_id`) USING BTREE,
                                   INDEX `room_id`(`room_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_team_room
-- ----------------------------
CREATE TABLE `cr_team_room`  (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                 `team_template_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                 `team_template_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                 `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                 `sub_type` int(11) NOT NULL,
                                 `pre_skills` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                 `train_skills` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                 `start_time` datetime NOT NULL,
                                 `end_time` datetime NOT NULL,
                                 `difficulty` int(11) NOT NULL,
                                 `training_status` int(11) NULL DEFAULT NULL,
                                 `time_line_config` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                 `create_user_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `modify_user_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `local_scene_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `copyed_topo_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                 `create_time` datetime NULL DEFAULT NULL,
                                 `update_time` datetime NULL DEFAULT NULL,
                                 `enable_recording` tinyint(1) NULL DEFAULT NULL,
                                 PRIMARY KEY (`id`) USING BTREE,
                                 UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
                                 INDEX `team_template_id`(`team_template_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_team_template
-- ----------------------------
CREATE TABLE `cr_team_template`  (
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `business_template_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `pre_skills` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `train_skills` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `role_config` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `sub_type` tinyint(4) NOT NULL,
                                     `time_line_config` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `people_number` int(10) NOT NULL,
                                     `duration_time` int(10) NOT NULL,
                                     `create_time` datetime NULL DEFAULT NULL,
                                     `update_time` datetime NULL DEFAULT NULL,
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
                                     INDEX `business_template_id`(`business_template_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_tool_exploit
-- ----------------------------
CREATE TABLE `cr_tool_exploit`  (
                                    `id` int(11) NOT NULL,
                                    `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                    `create_time` datetime NULL DEFAULT current_timestamp(),
                                    `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                    `edb_id` int(11) NULL DEFAULT NULL,
                                    `cve` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                    `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                    `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                    `platform` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                    `date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                    `verified` tinyint(1) NULL DEFAULT NULL,
                                    `exploit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                    `suffix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
                                    `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                    `vulnerable_app` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE INDEX `cr_tool_exploit_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_tool_ghdb
-- ----------------------------
CREATE TABLE `cr_tool_ghdb`  (
                                 `id` int(11) NOT NULL,
                                 `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `ghdb_id` int(11) NULL DEFAULT NULL,
                                 `google_search` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `published` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `google_dork_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                 `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_tool_shellcode
-- ----------------------------
CREATE TABLE `cr_tool_shellcode`  (
                                      `id` int(11) NOT NULL,
                                      `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                      `create_time` datetime NULL DEFAULT current_timestamp(),
                                      `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
                                      `edb_id` int(11) NULL DEFAULT NULL,
                                      `size` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
                                      `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
                                      `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                      `platform` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                      `published` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                      `edb_verified` tinyint(1) NULL DEFAULT NULL,
                                      `shellcode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                      `suffix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
                                      `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_tool_tool
-- ----------------------------
CREATE TABLE `cr_tool_tool`  (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                 `cover` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `homepage` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `category` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `manual` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `image_resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `visible` tinyint(4) NULL DEFAULT NULL,
                                 `tool_type` smallint(6) NULL DEFAULT NULL,
                                 `platforms` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `language` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `introduction` varchar(10000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `public` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `cracked` tinyint(4) NULL DEFAULT NULL,
                                 `tag` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `publish_time` datetime NULL DEFAULT NULL,
                                 `scene` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `version` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `files` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `cover_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                 `serial_version_uid` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                 `create_time` datetime NULL DEFAULT NULL,
                                 `update_time` datetime NULL DEFAULT NULL,
                                 PRIMARY KEY (`id`) USING BTREE,
                                 UNIQUE INDEX `cr_tool_tool_name_uindex`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_tool_toolcategory
-- ----------------------------
CREATE TABLE `cr_tool_toolcategory`  (
                                         `id` int(11) NOT NULL AUTO_INCREMENT,
                                         `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                         `cn_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                         `en_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                         `serial_version_uid` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_tool_toolfile
-- ----------------------------
CREATE TABLE `cr_tool_toolfile`  (
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `file_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `save_path` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `file_url` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_train_record
-- ----------------------------
CREATE TABLE `cr_train_record`  (
                                    `id` int(11) NOT NULL AUTO_INCREMENT,
                                    `business_type` int(10) UNSIGNED NOT NULL,
                                    `business_template_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                    `room_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                    `room_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                    `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                    `start_time` datetime NULL DEFAULT NULL,
                                    `end_time` datetime NULL DEFAULT NULL,
                                    `extra` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                    PRIMARY KEY (`id`) USING BTREE,
                                    INDEX `business_template_id`(`business_template_id`) USING BTREE,
                                    INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------

-- ----------------------------
-- Table structure for init_rtks_course_related
-- ----------------------------
CREATE TABLE `init_rtks_course_related`  (
                                             `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程id',
                                             `related_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点id',
                                             `type` int(11) NOT NULL COMMENT '1：知识点;2：技能点',
                                             `weight` int(11) NOT NULL COMMENT '权重',
                                             INDEX `course_id`(`resource_id`) USING BTREE,
                                             INDEX `related_id`(`related_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for init_rtks_knowledge
-- ----------------------------
CREATE TABLE `init_rtks_knowledge`  (
                                        `id` int(11) NOT NULL AUTO_INCREMENT,
                                        `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                        `category_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                        `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                        `courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                        `create_time` datetime NOT NULL,
                                        `update_time` datetime NOT NULL,
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for init_rtks_knowledge_category
-- ----------------------------
CREATE TABLE `init_rtks_knowledge_category`  (
                                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                                 `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                 `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                                 `create_time` datetime NOT NULL,
                                                 `update_time` datetime NOT NULL,
                                                 PRIMARY KEY (`id`) USING BTREE,
                                                 UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for init_rtks_level
-- ----------------------------
CREATE TABLE `init_rtks_level`  (
                                    `id` int(11) NOT NULL AUTO_INCREMENT,
                                    `resource_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                    `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                    `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                    `parent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                    `create_time` datetime NULL DEFAULT NULL,
                                    `update_time` datetime NULL DEFAULT NULL,
                                    `frame` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for init_rtks_role
-- ----------------------------
CREATE TABLE `init_rtks_role`  (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `description` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                   `create_time` datetime NOT NULL,
                                   `update_time` datetime NOT NULL,
                                   `level_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `category` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `task_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `knowledge_id_weights` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `knowledge_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `skill_id_weights` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `skill_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `ability_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `skill_courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `knowledge_courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for init_rtks_task
-- ----------------------------
CREATE TABLE `init_rtks_task`  (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `create_time` datetime NOT NULL,
                                   `update_time` datetime NOT NULL,
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for local_scene
-- ----------------------------
CREATE TABLE `local_scene`  (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                `mp_scene_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                `business_code` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                `start_time` datetime NULL DEFAULT NULL,
                                `end_time` datetime NULL DEFAULT NULL,
                                `destroy_time` datetime NULL DEFAULT NULL,
                                `destroy_mode` int(11) NULL DEFAULT NULL,
                                `business_instance_id` varchar(65) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                `scene_status` int(11) NULL DEFAULT NULL,
                                `pause_time` datetime NULL DEFAULT NULL,
                                `create_time` datetime NOT NULL,
                                `update_time` datetime NULL DEFAULT NULL,
                                `topo_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                `scene_ttl` int(11) NULL DEFAULT NULL,
                                `node_count` int(11) NULL DEFAULT NULL,
                                `meta` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                `scene_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                `scene_attr` int(11) NULL DEFAULT NULL,
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE INDEX `resourceIdUnique`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_classroom
-- ----------------------------
CREATE TABLE `oj_classroom`  (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                 `start_time` datetime NOT NULL,
                                 `end_time` datetime NOT NULL,
                                 `course_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                 `lesson_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                 `organization_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                 `organization_ids` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                 `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                 `create_time` datetime NOT NULL,
                                 `update_time` datetime NOT NULL,
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_classroom_group_archive
-- ----------------------------
CREATE TABLE `oj_classroom_group_archive`  (
                                               `id` int(11) NOT NULL AUTO_INCREMENT,
                                               `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                               `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                               `groups` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                               `classroom_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                               `create_time` datetime NOT NULL,
                                               `update_time` datetime NOT NULL,
                                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_classroom_group_info
-- ----------------------------
CREATE TABLE `oj_classroom_group_info`  (
                                            `id` int(11) NOT NULL AUTO_INCREMENT,
                                            `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                            `classroom_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                            `group_no` int(64) NOT NULL,
                                            `group_user_ids` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                            `destroy_delay` int(11) NULL DEFAULT NULL,
                                            `scene_share_mode` int(11) NULL DEFAULT NULL,
                                            `lesson_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                            `topo_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                            `create_time` datetime NOT NULL,
                                            `update_time` datetime NOT NULL,
                                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_classroom_sign
-- ----------------------------
CREATE TABLE `oj_classroom_sign`  (
                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                      `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `classroom_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `sign_in` int(1) NOT NULL,
                                      `sign_in_time` datetime NOT NULL,
                                      `need_help` int(1) NULL DEFAULT NULL,
                                      `create_time` datetime NOT NULL,
                                      `update_time` datetime NOT NULL,
                                      PRIMARY KEY (`id`) USING BTREE,
                                      UNIQUE INDEX `room_user_uniq`(`classroom_id`, `user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_comment
-- ----------------------------
CREATE TABLE `oj_comment`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `reply_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `business_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `theme_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `content` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `business_type` int(11) NOT NULL,
                               `root_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `create_time` datetime NOT NULL,
                               `update_time` datetime NOT NULL,
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_comment_like
-- ----------------------------
CREATE TABLE `oj_comment_like`  (
                                    `id` int(11) NOT NULL AUTO_INCREMENT,
                                    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                    `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                    `comment_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                    `create_time` datetime NOT NULL,
                                    `update_time` datetime NOT NULL,
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE INDEX `user_comment_uniq`(`user_id`, `comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_course
-- ----------------------------
CREATE TABLE `oj_course`  (
                              `id` int(11) NOT NULL AUTO_INCREMENT,
                              `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `builtin` int(1) NOT NULL,
                              `operation_auth_type` int(1) NOT NULL,
                              `operation_auth_ids` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `read_auth_type` int(1) NOT NULL,
                              `read_auth_ids` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `description` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                              `logo` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              `outline` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                              `difficulty` int(1) NOT NULL,
                              `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `modify_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `public` int(1) NOT NULL,
                              `course_type` int(1) NULL DEFAULT NULL,
                              `heat` int(11) NOT NULL,
                              `lecturer` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              `score` int(11) NULL DEFAULT NULL,
                              `course_num` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              `category_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `create_time` datetime NOT NULL,
                              `update_time` datetime NOT NULL,
                              PRIMARY KEY (`id`) USING BTREE,
                              UNIQUE INDEX `resource_unq`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_course_category
-- ----------------------------
CREATE TABLE `oj_course_category`  (
                                       `id` int(11) NOT NULL AUTO_INCREMENT,
                                       `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                       `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                       `course_type` int(2) NOT NULL COMMENT '课程类型0=基础 2=攻击 3=防御',
                                       `code` varchar(2096) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '节点编码  父级节点code + \',\' + 当前节点编号',
                                       `update_time` datetime NOT NULL,
                                       `create_time` datetime NOT NULL,
                                       `parent_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级节点编号',
                                       PRIMARY KEY (`id`) USING BTREE,
                                       UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_course_lesson
-- ----------------------------
CREATE TABLE `oj_course_lesson`  (
                                     `id` int(64) NOT NULL AUTO_INCREMENT,
                                     `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `course_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `builtin` int(1) NOT NULL,
                                     `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `type` int(1) NULL DEFAULT NULL,
                                     `handout_type` int(1) NULL DEFAULT NULL,
                                     `handout_url` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                     `video_url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `difficulty` int(1) NOT NULL,
                                     `public` int(1) NOT NULL,
                                     `attachment_ids` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `compulsory` int(1) NOT NULL,
                                     `duration` int(11) NULL DEFAULT NULL,
                                     `destroy_delay` int(11) NULL DEFAULT NULL,
                                     `topo_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `knowledge` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `scene_share_mode` int(1) NULL DEFAULT NULL,
                                     `origin_markdown_file` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                     `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `heat` int(11) NOT NULL,
                                     `paper_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `create_time` datetime NOT NULL,
                                     `update_time` datetime NOT NULL,
                                     `paper_public` int(1) NULL DEFAULT NULL,
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `resourse_id_uniq`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_lesson_config
-- ----------------------------
CREATE TABLE `oj_lesson_config`  (
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `index` int(11) NOT NULL,
                                     `parent_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `node_type` int(1) NOT NULL,
                                     `course_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `lesson_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `create_time` datetime NOT NULL,
                                     `update_time` datetime NOT NULL,
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_lesson_paper_evaluate
-- ----------------------------
CREATE TABLE `oj_lesson_paper_evaluate`  (
                                             `id` int(11) NOT NULL AUTO_INCREMENT,
                                             `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                             `lesson_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                             `score` double(11, 1) NOT NULL,
                                             `record` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                             `submit_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                             `create_time` datetime NOT NULL,
                                             `update_time` datetime NOT NULL,
                                             `total_score` double(11, 1) NOT NULL,
                                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_lesson_record
-- ----------------------------
CREATE TABLE `oj_lesson_record`  (
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `lesson_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `course_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `create_time` datetime NOT NULL,
                                     `update_time` datetime NOT NULL,
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `user_lesson_uniq`(`user_id`, `lesson_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_local_scene
-- ----------------------------
CREATE TABLE `oj_local_scene`  (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `shared` int(1) NOT NULL,
                                   `from_web` int(1) NULL DEFAULT NULL,
                                   `flags` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `business_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `business_type` int(1) NOT NULL,
                                   `local_scene_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `create_time` datetime NULL DEFAULT NULL,
                                   `update_time` datetime NULL DEFAULT NULL,
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_note
-- ----------------------------
CREATE TABLE `oj_note`  (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                            `business_type` int(11) NOT NULL COMMENT '1=课程 2=课时 3=练习 4=课后练习',
                            `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '笔记内容',
                            `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户编号',
                            `business_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源编号',
                            `note_type` int(11) NOT NULL COMMENT '1=笔记 2=实验报告',
                            `operation_auth_type` int(1) NOT NULL COMMENT '授权操作类类型 1=共享 2=自定义',
                            `operation_auth_ids` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `read_auth_type` int(1) NOT NULL COMMENT '同上',
                            `read_auth_ids` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            `create_time` datetime NOT NULL,
                            `update_time` datetime NOT NULL,
                            `public` int(1) NOT NULL COMMENT '是否公开',
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE INDEX `unique_index`(`resource_id`) USING BTREE,
                            UNIQUE INDEX `user_bt_bi_uniq`(`user_id`, `note_type`, `business_type`, `business_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_note_evaluate
-- ----------------------------
CREATE TABLE `oj_note_evaluate`  (
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `score` int(11) NOT NULL,
                                     `passed` int(1) NOT NULL,
                                     `evaluate_comment` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `evaluator_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `evaluate_time` datetime NOT NULL,
                                     `note_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `create_time` datetime NOT NULL,
                                     `update_time` datetime NOT NULL,
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `note_id_uniq`(`note_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_paper
-- ----------------------------
CREATE TABLE `oj_paper`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                             `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                             `description` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                             `logo` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                             `practice_count` int(11) NOT NULL,
                             `total_score` int(11) NOT NULL,
                             `public` int(1) NOT NULL,
                             `type` int(1) NOT NULL,
                             `create_time` datetime NOT NULL,
                             `update_time` datetime NOT NULL,
                             `operation_auth_type` int(1) NULL DEFAULT NULL,
                             `operation_auth_ids` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                             `read_auth_type` int(1) NULL DEFAULT NULL,
                             `read_auth_ids` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `resource_id_uniq`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_practice_category
-- ----------------------------
CREATE TABLE `oj_practice_category`  (
                                         `id` int(11) NOT NULL AUTO_INCREMENT,
                                         `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                         `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
                                         `practice_type` int(1) NOT NULL COMMENT '习题类型',
                                         `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人',
                                         `code` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '节点编号',
                                         `parent_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编号',
                                         `create_time` datetime NOT NULL,
                                         `update_time` datetime NOT NULL,
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_practice_operation
-- ----------------------------
CREATE TABLE `oj_practice_operation`  (
                                          `id` int(11) NOT NULL AUTO_INCREMENT,
                                          `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                          `category_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                          `builtin` int(1) NOT NULL,
                                          `practice_set_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                          `title` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                          `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                          `answer` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                          `difficulty` int(1) NOT NULL,
                                          `knowledge` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                          `public` int(1) NOT NULL,
                                          `score` int(11) NOT NULL,
                                          `attachment_id` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                          `writeup` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                          `writeup_public` int(1) NULL DEFAULT NULL,
                                          `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                          `dynamic_env` int(1) NULL DEFAULT NULL,
                                          `topo_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                          `scene_share_mode` int(1) NULL DEFAULT NULL,
                                          `dynamic_flag` int(1) NULL DEFAULT NULL,
                                          `destroy_delay` int(11) NULL DEFAULT NULL,
                                          `create_time` datetime NOT NULL,
                                          `update_time` datetime NOT NULL,
                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_practice_operation_ref
-- ----------------------------
CREATE TABLE `oj_practice_operation_ref`  (
                                              `id` int(11) NOT NULL AUTO_INCREMENT,
                                              `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                              `snapshot` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                              `origin_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                              `paper_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                              `create_time` datetime NOT NULL,
                                              `update_time` datetime NOT NULL,
                                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_practice_set
-- ----------------------------
CREATE TABLE `oj_practice_set`  (
                                    `id` int(11) NOT NULL AUTO_INCREMENT,
                                    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                    `builtin` int(1) NOT NULL,
                                    `practice_type` int(1) NOT NULL,
                                    `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                    `logo` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                    `public` int(1) NOT NULL,
                                    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                    `operation_auth_type` int(1) NOT NULL,
                                    `operation_auth_ids` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                    `read_auth_type` int(1) NOT NULL,
                                    `read_auth_ids` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                    `create_time` datetime NOT NULL,
                                    `update_time` datetime NOT NULL,
                                    `practice_count` int(11) NULL DEFAULT NULL,
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE INDEX `resource_idx`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_practice_solved
-- ----------------------------
CREATE TABLE `oj_practice_solved`  (
                                       `id` int(11) NOT NULL AUTO_INCREMENT,
                                       `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                       `submit_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提交人',
                                       `submit_answer` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提交的答案',
                                       `score` double(11, 0) NOT NULL COMMENT '得分',
                                       `total_score` double(11, 0) NOT NULL COMMENT '习题分值',
                                       `solved` int(1) NOT NULL COMMENT '是否解出',
                                       `practice_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '习题编号',
                                       `practice_type` int(1) NOT NULL COMMENT '习题类型',
                                       `collection` int(1) NOT NULL COMMENT '是否收藏',
                                       `create_time` datetime NOT NULL,
                                       `update_time` datetime NOT NULL,
                                       PRIMARY KEY (`id`) USING BTREE,
                                       UNIQUE INDEX `user_type_practice_uniq`(`submit_user_id`, `practice_type`, `practice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_practice_theory
-- ----------------------------
CREATE TABLE `oj_practice_theory`  (
                                       `id` int(11) NOT NULL AUTO_INCREMENT,
                                       `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                       `builtin` int(1) NOT NULL,
                                       `category_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                       `title` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                       `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                       `answer` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                       `difficulty` int(1) NOT NULL,
                                       `knowledge` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                       `public` int(1) NOT NULL,
                                       `score` int(11) NOT NULL,
                                       `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                       `theory_type` int(1) NOT NULL,
                                       `option` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                       `blank` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                       `keyword` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                       `practice_set_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                       `create_time` datetime NOT NULL,
                                       `update_time` datetime NOT NULL,
                                       PRIMARY KEY (`id`) USING BTREE,
                                       UNIQUE INDEX `resource_uniq`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_practice_theory_ref
-- ----------------------------
CREATE TABLE `oj_practice_theory_ref`  (
                                           `id` int(11) NOT NULL AUTO_INCREMENT,
                                           `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                           `snapshot` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                           `origin_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                           `option_random` int(1) NULL DEFAULT NULL,
                                           `paper_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                           `create_time` datetime NOT NULL,
                                           `update_time` datetime NOT NULL,
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_resource_collection
-- ----------------------------
CREATE TABLE `oj_resource_collection`  (
                                           `id` int(11) NOT NULL AUTO_INCREMENT,
                                           `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                           `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                           `business_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                           `business_type` int(1) NOT NULL,
                                           `create_time` datetime NOT NULL,
                                           `update_time` datetime NOT NULL,
                                           PRIMARY KEY (`id`) USING BTREE,
                                           UNIQUE INDEX `user_type_id_uniq`(`user_id`, `business_type`, `business_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for radar_label
-- ----------------------------
CREATE TABLE `radar_label`  (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `resource_id` int(11) NULL DEFAULT NULL,
                                `label` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_ability
-- ----------------------------
CREATE TABLE `rtks_ability`  (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                 `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                 `create_time` datetime NOT NULL,
                                 `update_time` datetime NOT NULL,
                                 PRIMARY KEY (`id`) USING BTREE,
                                 UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_course_name
-- ----------------------------
CREATE TABLE `rtks_course_name`  (
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `description` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                     `difficulty` int(11) NULL DEFAULT NULL,
                                     `lesson_count` int(11) NULL DEFAULT NULL,
                                     `e_book` int(11) NULL DEFAULT NULL,
                                     `course_num` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `exercise_count` int(11) NULL DEFAULT NULL,
                                     `theory_count` int(11) NULL DEFAULT NULL,
                                     `direction` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `direction_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `interview` int(11) NULL DEFAULT NULL,
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_course_related
-- ----------------------------
CREATE TABLE `rtks_course_related`  (
                                        `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程id',
                                        `related_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点id',
                                        `type` int(11) NOT NULL COMMENT '1：知识点;2：技能点',
                                        `weight` int(11) NOT NULL COMMENT '权重',
                                        INDEX `course_id`(`resource_id`) USING BTREE,
                                        INDEX `related_id`(`related_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_knowledge
-- ----------------------------
CREATE TABLE `rtks_knowledge`  (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `category_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `create_time` datetime NOT NULL,
                                   `update_time` datetime NOT NULL,
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_knowledge_category
-- ----------------------------
CREATE TABLE `rtks_knowledge_category`  (
                                            `id` int(11) NOT NULL AUTO_INCREMENT,
                                            `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                            `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                            `create_time` datetime NOT NULL,
                                            `update_time` datetime NOT NULL,
                                            PRIMARY KEY (`id`) USING BTREE,
                                            UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_level
-- ----------------------------
CREATE TABLE `rtks_level`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `resource_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `parent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `create_time` datetime NULL DEFAULT NULL,
                               `update_time` datetime NULL DEFAULT NULL,
                               `frame` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE,
                               UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_role
-- ----------------------------
CREATE TABLE `rtks_role`  (
                              `id` int(11) NOT NULL AUTO_INCREMENT,
                              `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              `create_time` datetime NOT NULL,
                              `update_time` datetime NOT NULL,
                              `level_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `category` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `task_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `knowledge_id_weights` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `knowledge_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `skill_id_weights` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `skill_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `ability_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `skill_courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              `knowledge_courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              PRIMARY KEY (`id`) USING BTREE,
                              UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_role_related
-- ----------------------------
CREATE TABLE `rtks_role_related`  (
                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                      `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `related_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `type` int(11) NOT NULL,
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_skill
-- ----------------------------
CREATE TABLE `rtks_skill`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `system_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `category_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `create_time` datetime NOT NULL,
                               `update_time` datetime NOT NULL,
                               PRIMARY KEY (`id`) USING BTREE,
                               UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_skill_category
-- ----------------------------
CREATE TABLE `rtks_skill_category`  (
                                        `id` int(11) NOT NULL AUTO_INCREMENT,
                                        `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                        `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                        `create_time` datetime NOT NULL,
                                        `update_time` datetime NOT NULL,
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE INDEX `resource_id_UNIQUE`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_skill_system
-- ----------------------------
CREATE TABLE `rtks_skill_system`  (
                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                      `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `scene_id` int(11) NOT NULL,
                                      `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `create_time` datetime NOT NULL,
                                      `update_time` datetime NOT NULL,
                                      PRIMARY KEY (`id`) USING BTREE,
                                      UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_task
-- ----------------------------
CREATE TABLE `rtks_task`  (
                              `id` int(11) NOT NULL AUTO_INCREMENT,
                              `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `create_time` datetime NOT NULL,
                              `update_time` datetime NOT NULL,
                              PRIMARY KEY (`id`) USING BTREE,
                              UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_user_role
-- ----------------------------
CREATE TABLE `rtks_user_role`  (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `role_name` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `create_time` datetime NOT NULL,
                                   `update_time` datetime NOT NULL,
                                   `user_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `user_uni`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
