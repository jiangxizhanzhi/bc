CREATE TABLE `wiki`
(
    `id`          int          NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(32)  NOT NULL,
    `title`       varchar(255) NOT NULL,
    `description` varchar(255) NULL,
    `content`     text         NULL,
    `logo`        varchar(255) NULL,
    `creator`     varchar(32)  NOT NULL,
    `create_time` datetime     NULL,
    `update_time` datetime     NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `skill` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;