ALTER TABLE oj_classroom DROP INDEX org_lesson_uniq;

ALTER TABLE oj_lesson_record add column `learned_type` int(2) NOT NULL default 0 COMMENT '课程学习类型0=课程 1=课程表';