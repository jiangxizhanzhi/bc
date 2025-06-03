CREATE TABLE `iov_vehicle_type`
(
    `id`           int(11)       NOT NULL AUTO_INCREMENT,
    `name`         varchar(255)  NULL,
    `create_time`  datetime DEFAULT NULL,
    `update_time`  datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

INSERT INTO `iov_vehicle_type` (`name`, `create_time`, `update_time`) VALUES ('比亚迪秦-PLUS', '2025-05-27 17:50:08', '2025-05-27 17:50:08');

UPDATE `iov_frame` SET `model`= 1 where id > 0;

UPDATE `iov_dut` SET `model`=1 where id > 0;

UPDATE `iov_traffic` SET `model`=1 where id > 0;