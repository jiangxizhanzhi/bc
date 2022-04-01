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
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cr_report_score
-- ----------------------------
ALTER TABLE `cr_report_score`
    ADD COLUMN `normal_score` decimal(10, 2) NOT NULL DEFAULT 000000000.00 AFTER `score`,
    ADD COLUMN `estimate_score` decimal(10, 2) NOT NULL DEFAULT 000000000.00 AFTER `normal_score`;


SET
FOREIGN_KEY_CHECKS = 1;
