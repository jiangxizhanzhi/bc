CREATE TABLE `space_call_count`
(
    `id`    int(11)      NOT NULL AUTO_INCREMENT,
    `name`  varchar(255) NULL,
    `calls` int(11)      NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name` (`name`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;