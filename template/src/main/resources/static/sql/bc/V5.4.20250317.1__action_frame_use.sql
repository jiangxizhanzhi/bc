CREATE TABLE `space_action_frame_use`
(
    `id`    int(11)      NOT NULL AUTO_INCREMENT,
    `type`  varchar(255) NOT NULL,
    `date`  varchar(255) NOT NULL,
    `count` int(11)      NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `date_type` (`date`, `type`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;