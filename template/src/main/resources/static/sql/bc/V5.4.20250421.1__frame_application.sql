CREATE TABLE `space_action_frame_application`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)  NOT NULL,
    `rvb_name`    varchar(255) NULL COMMENT '',
    `rvb_id`      varchar(255) NULL COMMENT '',
    `frame_id`    varchar(255) NULL COMMENT '',
    `name`        varchar(255) NULL COMMENT '',
    `type`        tinyint(4)   NULL COMMENT '',
    `category_id` varchar(255) NULL COMMENT '',
    `tag`         varchar(255) NULL COMMENT '',
    `description` varchar(255) NULL COMMENT '',
    `frame`       text         NULL COMMENT '',
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


CREATE TABLE `space_action_frame_release`
(
    `id`             int(11)      NOT NULL AUTO_INCREMENT,
    `application_id` varchar(255) NULL COMMENT '',
    `frame_id`       varchar(255) NULL COMMENT '',
    `frame_name`     varchar(255) NULL COMMENT '',
    `rvb_id`         varchar(255) NULL COMMENT '',
    `step`           varchar(255) NULL COMMENT '',
    `user_id`        varchar(255) NULL COMMENT '',
    `user_name`      varchar(255) NULL COMMENT '',
    `tactics`        text         NULL COMMENT '',
    `create_time`    datetime DEFAULT NULL,
    `update_time`    datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `application_stage` (`application_id`, `step`) USING BTREE,
    INDEX `rvb` (`rvb_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;



CREATE TABLE `space_action_frame_order`
(
    `id`             int(11)      NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(64)  NOT NULL,
    `name`           varchar(255) NULL,
    `application_id` varchar(255) NULL COMMENT '',
    `rvb_id`         varchar(255) NULL COMMENT '',
    `frame_name`     varchar(255) NULL COMMENT '',
    `step`           varchar(255) NULL COMMENT '',
    `tactic_id`      varchar(255) NULL COMMENT '',
    `tactic_type`    tinyint(4)   NULL COMMENT '',
    `user_id`        varchar(255) NULL COMMENT '',
    `user_name`      varchar(255) NULL COMMENT '',
    `status`         tinyint(4)   NULL COMMENT '',
    `create_time`    datetime DEFAULT NULL,
    `update_time`    datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE,
    INDEX `rvb` (`rvb_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


INSERT INTO `cr_constants_config`
VALUES (DEFAULT, NULL, 4, 8, 'List', '');
INSERT INTO `cr_constants_config`
VALUES (DEFAULT, NULL, 5, 8, 'Json', '');
INSERT INTO `cr_constants_config`
VALUES (DEFAULT, NULL, 6, 8, 'JsonString', '');
INSERT INTO `cr_constants_config`
VALUES (DEFAULT, NULL, 7, 8, 'JsonArray', '');

INSERT INTO `space_strategy_frame` (`id`, `resource_id`, `name`, `builtin`, `parent`, `create_time`, `update_time`)
VALUES (NULL, '9a0b01c76c4c479c95976a8cd51512de', '等保2.0要求（GB/T 22239一2019）', 1, 'default', now(), now());


ALTER TABLE `space_action_frame_event_type`
    ADD COLUMN `key_words`         varchar(255) NULL,
    ADD COLUMN `key_words_primary` varchar(255) NULL;

UPDATE `space_action_frame_event_type`
SET `key_words`         = '["恶意软件"]',
    `key_words_primary` = '["攻击", "软件", "恶意"]'
WHERE `resource_id` = '90668e9eca30457a802aa5e67ffc7518';
UPDATE `space_action_frame_event_type`
SET `key_words`         = '["拒绝服务"]',
    `key_words_primary` = '["攻击", "拒绝", "服务"]'
WHERE `resource_id` = '53fc8673ad41465da9e5b60bb4c6893b';
UPDATE `space_action_frame_event_type`
SET `key_words`         = '["网络入侵", "未经授权"]',
    `key_words_primary` = '["授权", "访问", "入侵"]'
WHERE `resource_id` = '84a7c3928a024920bbfe14ed5c626ddd';
UPDATE `space_action_frame_event_type`
SET `key_words`         = '["Web应用", "web应用"]',
    `key_words_primary` = '["Web", "攻击", "web"]'
WHERE `resource_id` = 'b1f837f036ec4e42be403613ad093c5d';
UPDATE `space_action_frame_event_type`
SET `key_words`         = '["供应链"]',
    `key_words_primary` = '["攻击"]'
WHERE `resource_id` = 'fa573fb9f32a408d9f614c6a1447ccff';
UPDATE `space_action_frame_event_type`
SET `key_words`         = '["数据泄露", "信息泄露"]',
    `key_words_primary` = '["数据", "信息", "泄漏"]'
WHERE `resource_id` = 'f011cde825ac4f8ab7dbed2e1b7da7b8';
UPDATE `space_action_frame_event_type`
SET `key_words`         = '["0Day", "0day"]',
    `key_words_primary` = '["攻击"]'
WHERE `resource_id` = '32a5925828b248d2847ff1fc6a61933d';
UPDATE `space_action_frame_event_type`
SET `key_words`         = '["APT", "apt"]',
    `key_words_primary` = '["攻击"]'
WHERE `resource_id` = '02a65acf9e824dce8896b16eef9ad339';

ALTER TABLE `space_action_frame_target_type`
    ADD COLUMN `key_words`         varchar(255) NULL,
    ADD COLUMN `key_words_primary` varchar(255) NULL;
UPDATE `space_action_frame_target_type`
SET `key_words`         = '["物理"]',
    `key_words_primary` = '["攻击"]'
WHERE `resource_id` = 'a1bcdea895bc49e6a5586f051f5f06e1';
UPDATE `space_action_frame_target_type`
SET `key_words`         = '["网络"]',
    `key_words_primary` = '["攻击"]'
WHERE `resource_id` = '776dc812a43a42b7b4a9050fa3e71b89';
UPDATE `space_action_frame_target_type`
SET `key_words`         = '["主机"]',
    `key_words_primary` = '["攻击"]'
WHERE `resource_id` = '2265596f453b4be5946c3cf48bf5ee4c';
UPDATE `space_action_frame_target_type`
SET `key_words`         = '["应用"]',
    `key_words_primary` = '["攻击"]'
WHERE `resource_id` = '6fcb908b2df64b31936b9188fedf8033';