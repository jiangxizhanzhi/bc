ALTER TABLE `oj_train_plan`
    ADD COLUMN `type` tinyint NULL COMMENT '类型';

ALTER TABLE `oj_course_lesson`
    ADD COLUMN `continuous` boolean DEFAULT 0 COMMENT '是否连续';

INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 1, 41, '未开始', 'NOT STARTED');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 2, 41, '进行中', 'ONGOING');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 3, 41, '已结束', 'ENDED');