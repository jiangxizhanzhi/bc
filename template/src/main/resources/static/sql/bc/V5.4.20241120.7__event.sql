ALTER TABLE `space_event_integration_rule`
    ADD COLUMN `type`        varchar(255)  NULL,
    ADD COLUMN `description` varchar(1024) NULL,
    ADD COLUMN `content`     varchar(2048) NULL,
    ADD COLUMN `tool`        varchar(1024) NULL;

ALTER TABLE `space_loaded_file_threat_rule`
    MODIFY COLUMN `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则内容',
    MODIFY COLUMN `description` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则描述';

ALTER TABLE `space_event_integration_rule`
    MODIFY COLUMN `description` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    MODIFY COLUMN `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL;

ALTER TABLE `space_round_mechanism_rule`
    MODIFY COLUMN `description` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简述';

ALTER TABLE `space_score_ranking_rule`
    MODIFY COLUMN `description` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则简述';

ALTER TABLE `space_tactic_defense_plan`
    MODIFY COLUMN `description` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL;