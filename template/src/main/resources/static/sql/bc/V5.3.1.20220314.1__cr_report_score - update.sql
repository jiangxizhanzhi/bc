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
ALTER TABLE  `cr_report_score`
    MODIFY COLUMN `score` decimal(10, 2) UNSIGNED ZEROFILL NOT NULL DEFAULT 000000000.00;


SET FOREIGN_KEY_CHECKS = 1;
