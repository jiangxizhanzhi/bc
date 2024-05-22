SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for video_progress
-- ----------------------------
CREATE TABLE `video_progress`
(
    `id`            int(11)                                                 NOT NULL AUTO_INCREMENT,
    `path`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '视频路径',
    `business_id`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务id',
    `user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户id',
    `time`          int(11)                                                 NOT NULL COMMENT '进度时间',
    `watched_point` text CHARACTER SET utf8 COLLATE utf8_general_ci         NOT NULL COMMENT '观看过的检查点',
    `create_time`   datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime                                                NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `path_user` (`user_id`, `path`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for behavior_record
-- ----------------------------
DROP TABLE IF EXISTS `behavior_record`;
CREATE TABLE `behavior_record`
(
    `id`          int(11)                                                  NOT NULL AUTO_INCREMENT,
    `module`      smallint(6)                                              NOT NULL COMMENT '模块',
    `user_id`     varchar(65) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL COMMENT '用户id',
    `action`      smallint(6)                                              NOT NULL COMMENT '用户动作类型',
    `target`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '动作目标',
    `result`      smallint(6)                                              NULL DEFAULT NULL COMMENT '结果',
    `link`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '链接',
    `description` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
    `time`        datetime                                                 NULL DEFAULT NULL COMMENT '时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `module_user` (`module`, `user_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 30
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- video_check_point
-- ----------------------------

ALTER TABLE `oj_course_lesson`
    ADD COLUMN `video_check_point` text NULL COMMENT '视频检查点配置' AFTER `video_url`;


SET FOREIGN_KEY_CHECKS = 1;