ALTER TABLE `oj_train_plan`
    ADD COLUMN `type` tinyint NULL COMMENT '类型';

ALTER TABLE `oj_course_lesson`
    ADD COLUMN `continuous` boolean DEFAULT 0 COMMENT '是否连续';