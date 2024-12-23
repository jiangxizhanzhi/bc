ALTER TABLE `cr_eval_template`
    ADD COLUMN `method_file` varchar(1000) NULL COMMENT '试验方法附件' AFTER `method`;