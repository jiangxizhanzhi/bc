ALTER TABLE `oj_train_plan`
    ADD COLUMN `type` tinyint NULL COMMENT '类型';

ALTER TABLE `oj_course_lesson`
    ADD COLUMN `continuous` boolean DEFAULT 0 COMMENT '是否连续',
    ADD COLUMN `shared`     tinyint NULL DEFAULT 0 COMMENT '是否共享',
    ADD COLUMN `guidance`   text    NULL COMMENT '指导',
    ADD COLUMN `radio`      tinyint NULL;

INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 1, 41, '未开始', 'NOT STARTED');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 2, 41, '进行中', 'ONGOING');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 3, 41, '已结束', 'ENDED');

CREATE TABLE `oj_my_repository`
(
    `id`      int(11)     NOT NULL AUTO_INCREMENT,
    `user_id` varchar(32) NOT NULL,
    `my`      text        NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `user_id` (`user_id`)
);

ALTER TABLE `oj_train_plan`
    ADD COLUMN `organization_id`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL DEFAULT '',
    ADD COLUMN `organization_ids` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '';

CREATE TABLE `oj_forum_post`
(
    `id`          int(11)                                                  NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL,
    `title`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '标题',
    `description` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
    `content`     text CHARACTER SET utf8 COLLATE utf8_general_ci          NULL DEFAULT NULL COMMENT '正文',
    `user_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL COMMENT '发布人',
    `type`        tinyint(4)                                               NULL DEFAULT NULL COMMENT '类型',
    `create_time` datetime                                                 NULL DEFAULT NULL COMMENT '发布时间',
    `update_time` datetime                                                 NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE,
    INDEX `type_user` (`type`, `user_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;


ALTER TABLE `oj_classroom`
    ADD COLUMN `enable_recording` tinyint NOT NULL DEFAULT 1 COMMENT '是否开启录屏' AFTER `train_plan_id`;


CREATE TABLE `oj_classroom_scene_record`
(
    `id`           int(11)     NOT NULL AUTO_INCREMENT,
    `classroom_id` varchar(32) NOT NULL,
    `user_id`      varchar(32) NOT NULL,
    `group_id`     varchar(32) NOT NULL,
    `scene_ids`    text        NOT NULL,
    `create_time`  datetime    NULL DEFAULT NULL,
    `update_time`  datetime    NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `class_user` (`classroom_id`, `user_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;