/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MariaDB
 Source Server Version : 100600
 Source Host           : localhost:3307
 Source Schema         : bc_5_3_4

 Target Server Type    : MariaDB
 Target Server Version : 100600
 File Encoding         : 65001

 Date: 18/06/2024 14:48:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for file_chunk
-- ----------------------------
DROP TABLE IF EXISTS `file_chunk`;
CREATE TABLE `file_chunk`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `md5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                               `filesize` bigint(20) NOT NULL,
                               `total` int(11) NOT NULL,
                               `index` int(11) NOT NULL,
                               `status` tinyint(4) NULL DEFAULT NULL,
                               `create_time` datetime NULL DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE,
                               INDEX `md5`(`md5`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
