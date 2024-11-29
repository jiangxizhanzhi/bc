ALTER TABLE `oj_practice_operation`
    ADD COLUMN `need_outer` TINYINT NULL DEFAULT 0 COMMENT '是否需要外部操作机，0=不需要，1=需要'