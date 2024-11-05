CREATE TABLE `space_algorithm_expertise`
(
    `id`               int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id`      varchar(64)  NOT NULL,
    `name`             varchar(255) NOT NULL,
    `course`           text         NULL COMMENT '',
    `course_percent`   smallint     NULL COMMENT '',
    `training`         text         NULL COMMENT '',
    `training_percent` smallint     NULL COMMENT '',
    `create_time`      datetime DEFAULT NULL,
    `update_time`      datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_algorithm_role`
(
    `id`               int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id`      varchar(64)  NOT NULL,
    `name`             varchar(255) NOT NULL,
    `course`           text         NULL COMMENT '',
    `course_percent`   smallint     NULL COMMENT '',
    `training`         text         NULL COMMENT '',
    `training_percent` smallint     NULL COMMENT '',
    `create_time`      datetime DEFAULT NULL,
    `update_time`      datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_algorithm_individual`
(
    `id`                int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id`       varchar(64)  NOT NULL,
    `name`              varchar(255) NOT NULL,
    `expertise_percent` smallint     NULL COMMENT '',
    `role_percent`      smallint     NULL COMMENT '',
    `create_time`       datetime DEFAULT NULL,
    `update_time`       datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_algorithm_team`
(
    `id`                int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id`       varchar(64)  NOT NULL,
    `name`              varchar(255) NOT NULL,
    `expertise_percent` smallint     NULL COMMENT '',
    `role_percent`      smallint     NULL COMMENT '',
    `create_time`       datetime DEFAULT NULL,
    `update_time`       datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `space_algorithm_equipment`
(
    `id`                  int(11)       NOT NULL AUTO_INCREMENT,
    `resource_id`         varchar(64)   NOT NULL,
    `name`                varchar(255)  NULL,
    `description`         varchar(2048) NULL COMMENT '',
    `asset_management`    int(11)       NULL COMMENT '',
    `behavior_management` int(11)       NULL COMMENT '',
    `module_isolation`    int(11)       NULL COMMENT '',
    `risk_discovery`      int(11)       NULL COMMENT '',
    `threat_monitoring`   int(11)       NULL COMMENT '',
    `strategy`            int(11)       NULL COMMENT '',
    `capability`          int(11)       NULL COMMENT '',
    `create_time`         datetime DEFAULT NULL,
    `update_time`         datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;
