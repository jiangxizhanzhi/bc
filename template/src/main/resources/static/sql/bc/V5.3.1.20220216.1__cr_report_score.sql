/*
 Navicat Premium Data Transfer

 Source Server         : 10.100.1.26-ojj
 Source Server Type    : MySQL
 Source Server Version : 100407
 Source Host           : 10.100.1.26:3305
 Source Schema         : bc_jyj

 Target Server Type    : MySQL
 Target Server Version : 100407
 File Encoding         : 65001

 Date: 16/02/2022 14:20:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cr_report_score
-- ----------------------------
DROP TABLE IF EXISTS `cr_report_score`;
CREATE TABLE `cr_report_score`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `report_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `report_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `subject` tinyint(4) NOT NULL,
  `anonymous` tinyint(4) NULL DEFAULT NULL,
  `submit_time` datetime(0) NOT NULL,
  `status` tinyint(4) NOT NULL,
  `report_file` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `score` bigint(16) UNSIGNED ZEROFILL NOT NULL DEFAULT 0000000000000000,
  `comment` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `score_time` datetime(0) NULL DEFAULT NULL,
  `rater_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `rater_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `user_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `report_file_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_report_score_config
-- ----------------------------
DROP TABLE IF EXISTS `cr_report_score_config`;
CREATE TABLE `cr_report_score_config`  (
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `key` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
