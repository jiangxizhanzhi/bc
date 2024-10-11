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

 Date: 25/01/2022 11:00:33
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cr_constants_config
-- ----------------------------
CREATE TABLE `cr_constants_config`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `code`        tinyint(4) NULL DEFAULT NULL,
    `type`        tinyint(4) NOT NULL,
    `cn_name`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `en_name`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 24
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cr_constants_config
-- ----------------------------


SET
FOREIGN_KEY_CHECKS = 1;