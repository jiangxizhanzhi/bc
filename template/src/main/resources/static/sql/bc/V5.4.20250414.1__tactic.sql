CREATE TABLE `space_tactic_defense_system`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)  NOT NULL,
    `name`        varchar(255) NULL,
    `stage`       tinyint      NULL COMMENT '',
    `definition`  text         NULL COMMENT '',
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


UPDATE `cr_tool_tool`
SET `visible` = 1
WHERE `visible` is null;

ALTER TABLE `space_tactic_attack`
    ADD COLUMN `system_id` varchar(64)  NULL,
    ADD COLUMN `source`    varchar(255) NULL,
    ADD COLUMN `strategy` text NULL,
    MODIFY COLUMN `tactic_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL;

ALTER TABLE `space_tactic_defense`
    ADD COLUMN `system_id` varchar(64)  NULL,
    ADD COLUMN `source`    varchar(255) NULL,
    MODIFY COLUMN `tactic_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
    ADD COLUMN `strategy` text NULL,
    ADD COLUMN `action`    text         NULL;

CREATE TABLE `space_action_frame_event_type`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)  NOT NULL,
    `name`        varchar(255) NULL,
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_action_frame_target_type`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)  NOT NULL,
    `name`        varchar(255) NULL,
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


ALTER TABLE `space_action_frame`
    ADD COLUMN `category_id` varchar(255) NULL;