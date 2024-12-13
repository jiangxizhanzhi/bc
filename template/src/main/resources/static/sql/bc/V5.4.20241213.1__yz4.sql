ALTER TABLE `bc`.`cr_eval_mission_template`
    ADD COLUMN `stage_status_update_time` datetime(0) NULL DEFAULT NULL COMMENT '阶段状态更新时间' AFTER `end_time`;