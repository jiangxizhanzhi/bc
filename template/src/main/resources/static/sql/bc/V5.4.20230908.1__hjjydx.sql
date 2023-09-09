ALTER TABLE `oj_course_lesson`
    ADD COLUMN `video_time` varchar(32) NULL COMMENT '视频长度',
    ADD COLUMN `check_point` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '知识检查点';

ALTER TABLE `oj_course`
    ADD COLUMN `lecturee` int(11) NOT NULL DEFAULT 0 COMMENT '授课人数';

CREATE TABLE `oj_prepare_note`
(
    `id`          int                                                       NOT NULL AUTO_INCREMENT,
    `lesson_id`   varchar(32)                                               NOT NULL COMMENT '课时id',
    `user_id`     varchar(32)                                               NOT NULL COMMENT '用户id',
    `note`        longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备课笔记',
    `create_time` datetime                                                  NULL,
    `update_time` datetime                                                  NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `lesson_user` (`lesson_id`, `user_id`) USING BTREE
);

CREATE TABLE `oj_check_point_answer`
(
    `id`          int                                                       NOT NULL AUTO_INCREMENT,
    `lesson_id`   varchar(32)                                               NOT NULL COMMENT '课时id',
    `user_id`     varchar(32)                                               NOT NULL COMMENT '用户id',
    `answer`      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '用户提交答案',
    `create_time` datetime                                                  NULL,
    `update_time` datetime                                                  NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `lesson_user` (`lesson_id`, `user_id`) USING BTREE
);