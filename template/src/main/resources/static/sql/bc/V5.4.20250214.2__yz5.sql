ALTER TABLE `cr_rb_target_command_log`
    ADD COLUMN `command_templates` varchar(1000) NULL COMMENT '执行的模板集合' AFTER `command_user`,
ADD COLUMN `command_parameters` varchar(1000) NULL COMMENT '执行的模板参数集合' AFTER `command_templates`;