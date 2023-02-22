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

 Date: 22/02/2023 16:56:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cr_rb_malice_process
-- ----------------------------
DROP TABLE IF EXISTS `cr_rb_malice_process`;
CREATE TABLE `cr_rb_malice_process`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `room_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `machine_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `process` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 195 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
