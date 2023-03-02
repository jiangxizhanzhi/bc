ALTER TABLE oj_classroom DROP INDEX org_lesson_uniq;

ALTER TABLE oj_lesson_record add column `learned_type` int(2) NOT NULL default 0 COMMENT '课程学习类型0=课程 1=课程表';

ALTER TABLE oj_lesson_record add column `room_id` varchar (64) NULL COMMENT '课堂ID';

ALTER TABLE oj_train_plan MODIFY COLUMN `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计划名称';

ALTER TABLE oj_practice_theory_ref MODIFY COLUMN `snapshot` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '理论基础基础信息快照';

ALTER TABLE cr_rb_room  add column `support_partial_start` tinyint(1) NULL COMMENT '是否部分启动' AFTER `enable_recording`;

DELETE FROM cr_constants_config WHERE `type` = 12;
INSERT INTO cr_constants_config (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (52, NULL, 0, 12, '理论题', 'Theory');
INSERT INTO cr_constants_config (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (53, NULL, 2, 12, '实操题', 'Practice');
