CREATE TABLE `space_apt_report`
(
    `id`             int(11)       NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64)   NOT NULL,
    `name`           varchar(255)  NULL,
    `apt_org`        varchar(255)  NULL,
    `public_time`    varchar(255)  NULL,
    `public_company` varchar(255)  NULL,
    `report_path`    varchar(1024) NULL,
    `type`           tinyint       NULL,
    `create_time`    datetime DEFAULT NULL,
    `update_time`    datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


ALTER TABLE `space_apt_threat_detect_rule`
    MODIFY COLUMN `type` varchar(255) NULL DEFAULT NULL COMMENT '情报类型',
    ADD COLUMN `apt_code`    varchar(255)  NULL,
    ADD COLUMN `content`     text          NULL,
    ADD COLUMN `description` varchar(2048) NULL;


ALTER TABLE `space_loaded_file_threat_rule`
    ADD COLUMN `type`         varchar(255) NULL,
    ADD COLUMN `execute_type` smallint(6)  NULL;

ALTER TABLE `space_warning_dictionary`
    ADD COLUMN `tool_type`   varchar(255)  NULL,
    ADD COLUMN `tool_level`  varchar(255)  NULL,
    ADD COLUMN `level`       tinyint(6)    NULL,
    ADD COLUMN `description` varchar(2048) NULL;


ALTER TABLE `space_apt_feature`
    ADD COLUMN `logo` varchar(1024) NULL;