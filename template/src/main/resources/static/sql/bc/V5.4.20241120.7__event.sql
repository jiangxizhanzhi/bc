ALTER TABLE `space_event_integration_rule`
    ADD COLUMN `type`        varchar(255)  NULL,
    ADD COLUMN `description` varchar(1024) NULL,
    ADD COLUMN `content`     varchar(2048) NULL,
    ADD COLUMN `tool`        varchar(1024) NULL;

ALTER TABLE `cr_eval_algorithm`
    ADD COLUMN `language` tinyint NOT NULL DEFAULT 1;