CREATE TABLE `space_action_frame`
(
    `id`          int(11)       NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)   NOT NULL,
    `name`        varchar(255)  NOT NULL,
    `type`        tinyint(4)    NULL COMMENT '',
    `role`        tinyint(4)    NULL COMMENT '',
    `attack`      varchar(2048) NULL COMMENT '',
    `defense`     varchar(2048) NULL COMMENT '',
    `tag`         varchar(2048) NULL COMMENT '',
    `topo`        text          NULL COMMENT '',
    `description` varchar(2048) NULL COMMENT '',
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_action_target`
(
    `id`          int(11)       NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)   NOT NULL,
    `name`        varchar(255)  NOT NULL,
    `description` varchar(2048) NULL COMMENT '',
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_action_method`
(
    `id`          int(11)       NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)   NOT NULL,
    `name`        varchar(255)  NOT NULL,
    `description` varchar(2048) NULL COMMENT '',
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_action`
(
    `id`          int(11)       NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)   NOT NULL,
    `name`        varchar(255)  NOT NULL,
    `type`        tinyint(4)    NULL COMMENT '类型，攻击、防御',
    `description` varchar(2048) NULL COMMENT '',
    `environment` text          NULL COMMENT '',
    `action`      text          NULL COMMENT '',
    `analyze`     text          NULL COMMENT '',
    `tool`        text          NULL COMMENT '',
    `strategy`    text          NULL COMMENT '',
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


ALTER TABLE `space_strategy_frame`
    ADD INDEX `parent` (`parent`) USING BTREE;


CREATE TABLE `space_action_relation`
(
    `id`          int(11)     NOT NULL AUTO_INCREMENT,
    `upper_id`   varchar(32) NOT NULL,
    `lower_id`   varchar(32) NOT NULL,
    `create_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `upper_id` (`upper_id`) USING BTREE,
    INDEX `lower_id` (`lower_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;