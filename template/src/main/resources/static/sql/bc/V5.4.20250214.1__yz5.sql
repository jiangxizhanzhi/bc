ALTER TABLE `cr_rb_target_command_parameters`
    ADD COLUMN `parameter_remarks` varchar(1000) NULL COMMENT '参数备注' AFTER `parameter_type`;