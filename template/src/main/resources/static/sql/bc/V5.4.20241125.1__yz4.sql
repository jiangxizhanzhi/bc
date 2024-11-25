ALTER TABLE `cr_eval_mission_template_log`
    ADD COLUMN `mission_result` varchar(255) NULL COMMENT '任务结果' AFTER `update_time`;