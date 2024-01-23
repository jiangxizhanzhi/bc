ALTER TABLE cr_rb_combat_log ADD COLUMN `main_log_id` VARCHAR ( 64 ) CHARACTER
    SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主日志ID' AFTER original_log;