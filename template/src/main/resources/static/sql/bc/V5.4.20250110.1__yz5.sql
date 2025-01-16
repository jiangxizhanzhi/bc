ALTER TABLE `cr_eval_index_ref`
    ADD COLUMN `alarm_match_name` varchar(1024) NULL COMMENT '告警匹配名称' AFTER `even_name`;