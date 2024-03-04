-- 为平台中已有训练数据设置默认值

ALTER TABLE `cr_mission_template` MODIFY COLUMN `attachment` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL;

ALTER TABLE cr_mission_template MODIFY COLUMN `option` text COMMENT '选择题选项';
ALTER TABLE cr_mission MODIFY COLUMN `option` text COMMENT '选择题选项';

ALTER TABLE cr_mission_template MODIFY COLUMN `write_up` LONGTEXT COMMENT '题解';
ALTER TABLE cr_mission MODIFY COLUMN `write_up` LONGTEXT COMMENT '题解';
