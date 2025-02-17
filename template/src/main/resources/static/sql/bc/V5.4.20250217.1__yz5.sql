ALTER TABLE `cr_rb_target_command_log`
    MODIFY COLUMN `command_templates` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '执行的模板集合' AFTER `command_user`,
    MODIFY COLUMN `command_parameters` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '执行的模板参数集合' AFTER `command_templates`;