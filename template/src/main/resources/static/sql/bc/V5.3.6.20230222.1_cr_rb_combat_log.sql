/*
 Navicat Premium Data Transfer

 Source Server         : 1.26
 Source Server Type    : MySQL
 Source Server Version : 100334
 Source Host           : 10.100.1.26:3305
 Source Schema         : 536_kyy

 Target Server Type    : MySQL
 Target Server Version : 100334
 File Encoding         : 65001

 Date: 22/02/2023 16:56:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cr_rb_combat_log
-- ----------------------------
DROP TABLE IF EXISTS `cr_rb_combat_log`;
CREATE TABLE `cr_rb_combat_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `room_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '红蓝实例id',
  `event_type` int(2) UNSIGNED NOT NULL DEFAULT 1 COMMENT '事件类型',
  `event_category` int(2) UNSIGNED NOT NULL DEFAULT 1 COMMENT '事件类别',
  `attack_phase` int(2) UNSIGNED NOT NULL DEFAULT 1 COMMENT '攻击阶段',
  `defense_phase` int(2) UNSIGNED NOT NULL DEFAULT 1 COMMENT '防御阶段',
  `attack_action` int(2) UNSIGNED NOT NULL DEFAULT 1 COMMENT '攻击行为',
  `defense_action` int(2) UNSIGNED NOT NULL DEFAULT 1 COMMENT '防御行为',
  `action_type` int(2) UNSIGNED NOT NULL DEFAULT 1 COMMENT '行为类型',
  `is_lost_host` int(2) NOT NULL DEFAULT 0 COMMENT '是否为失陷标志',
  `source_group` int(2) NULL DEFAULT NULL COMMENT '攻击阵营',
  `source_ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '攻击机ip',
  `source_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '攻击机名称',
  `dest_group` int(2) NULL DEFAULT NULL COMMENT '防御阵营',
  `dest_ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '防御机ip',
  `dest_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '防御机名称',
  `agent_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主机名称',
  `agent_ip` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主机ip',
  `event_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '事件内容',
  `event_time` datetime(0) NULL DEFAULT current_timestamp() COMMENT '事件时间',
  `original_log` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '原始日志',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1470 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
