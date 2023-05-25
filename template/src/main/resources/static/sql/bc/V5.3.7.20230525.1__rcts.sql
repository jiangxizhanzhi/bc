ALTER TABLE `cr_train_record`
    ADD COLUMN `trainee` text NOT NULL COMMENT '参训学员' AFTER `extra`;

CREATE TABLE `rtks_daily_match_log`
(
    `id`          int         NOT NULL AUTO_INCREMENT,
    `user_id`     varchar(32) NOT NULL,
    `role_id`     varchar(32) NOT NULL,
    `match`       smallint    NOT NULL,
    `create_time` datetime    NULL DEFAULT NULL,
    `update_time` datetime    NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `user_id` (`user_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;