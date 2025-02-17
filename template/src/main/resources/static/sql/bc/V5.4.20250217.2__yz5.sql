ALTER TABLE `cr_rb_target_command_log`
    ADD COLUMN `command_template_ids` varchar(255) NULL COMMENT '执行的模板ids' AFTER `command_user`;