ALTER TABLE `iov_frame`
MODIFY COLUMN `model` varchar(36) NOT NULL COMMENT '车型';

CREATE TABLE `iov_vehicle_type`
(
    `id`           int(11)       NOT NULL AUTO_INCREMENT,
    `resource_id`  varchar(64)   NOT NULL,
    `name`         varchar(255)  NULL,
    `create_time`  datetime DEFAULT NULL,
    `update_time`  datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

INSERT INTO `iov_vehicle_type` (`resource_id`, `name`, `create_time`, `update_time`) VALUES ('9526946147e2fdd1f63380ff3118328e', '比亚迪秦-PLUS', '2025-05-27 17:50:08', '2025-05-27 17:50:08');

UPDATE `iov_frame` SET `model`='9526946147e2fdd1f63380ff3118328e' where id > 0;