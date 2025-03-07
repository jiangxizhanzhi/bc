ALTER TABLE `cr_eval_index_ref`
    ADD COLUMN `event_fifth_index_context` text NULL COMMENT '五级指标告警设置' AFTER `specialist_fifth_index_context`;