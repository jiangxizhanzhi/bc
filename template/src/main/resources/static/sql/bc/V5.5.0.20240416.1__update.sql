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

-- ----------------------------
-- 删除内置习题集
-- ----------------------------
delete
from oj_practice_set
where builtin = 1
  and resource_id in
      ('584c596b5a8e481facb94189f5ccf224', '66220449b9134c8186f56f7cdd9a4b71', '7395331dcce742c3beb1ac49264cdbaf',
       'c357afe336ce48418d4d9ca77a1a3cbe', '32a7da91927d46afa92e1ac60707d263', '8169e1626c0b4319881706ab40d72536',
       '5a2203da9adf4bb48d81baee4fb751df', '64e2f15f8c7348bb949615a5993838b7', '48bbfebb649441dab66ec89a8978b98e',
       '4ef4a5d6d1aa460e87ad29738ed1287f', 'e687cf39c4534399b4b4f9d6b5200f54', '1606526a65fa4d96ad17682d6102fcbe',
       'ee3fc36981fe45ee93486f66f7da5cd1', '84fe54a3c9b44747baca92ab0db27651', '6ab2c73f6dd64707b645868a70660dfa')
  and resource_id not in
      (select distinct practice_set_id from oj_practice_theory)
  and resource_id not in
      (select distinct practice_set_id from oj_practice_operation);

SET FOREIGN_KEY_CHECKS = 1;