ALTER TABLE `oj_practice_operation`
    MODIFY COLUMN `practice_set_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '关联习题集id' AFTER `builtin`;