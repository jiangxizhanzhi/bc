-- ----------------------------
-- for system_use_status
-- ----------------------------
CREATE TABLE `system_use_status`
(
    `id`                  int         NOT NULL AUTO_INCREMENT,
    `cpu`            varchar(32) NOT NULL,
    `disk` varchar(32) NOT NULL,
    `ram`              tinyint(4)  NOT NULL,
    `alter_time`         datetime    NULL,
    `update_time`         datetime    NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;