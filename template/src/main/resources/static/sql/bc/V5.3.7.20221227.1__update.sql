ALTER TABLE oj_classroom DROP INDEX org_lesson_uniq;

ALTER TABLE oj_lesson_record add column `learned_type` int(2) NOT NULL default 0 COMMENT '课程学习类型0=课程 1=课程表';

ALTER TABLE oj_lesson_record add column `room_id` varchar (64) NULL COMMENT '课堂ID';

drop index user_lesson_uniq on oj_lesson_record;

create unique index user_lesson_uniq
    on oj_lesson_record (user_id, lesson_id, room_id, learned_type);

ALTER TABLE oj_train_plan MODIFY COLUMN `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计划名称';

ALTER TABLE oj_practice_theory_ref MODIFY COLUMN `snapshot` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '理论基础基础信息快照';