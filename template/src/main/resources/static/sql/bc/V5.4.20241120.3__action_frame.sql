ALTER TABLE `space_action_method`
    ADD COLUMN `tag` varchar(1024) NULL;

ALTER TABLE `space_apt_threat_detect_rule`
    ADD COLUMN `tool` varchar(1024) NULL;
ALTER TABLE `space_loaded_file_threat_rule`
    ADD COLUMN `tool` varchar(1024) NULL;
ALTER TABLE `space_traffic_detect_rule`
    ADD COLUMN `tool` varchar(1024) NULL;

ALTER TABLE `oj_course_lesson`
    ADD COLUMN `video_duration` int NULL DEFAULT 0;

ALTER TABLE `space_strategy`
    ADD COLUMN `method` text NULL;

ALTER TABLE `space_action`
    ADD COLUMN `id_number` varchar(255) NULL,
    ADD COLUMN `parent_number` varchar(255) NULL,
    ADD COLUMN `platform` varchar(1024) NULL;