ALTER TABLE `space_action_frame`
    ADD COLUMN `frame` longtext NULL;


CREATE TABLE `space_tactic_defense_plan`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)  NOT NULL,
    `name`        varchar(255) NULL,
    `description` varchar(255) NULL COMMENT '',
    `plan`        text         NULL,
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;