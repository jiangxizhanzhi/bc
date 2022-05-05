-- ----------------------------
-- for rtks_skill
-- ----------------------------
ALTER TABLE `cr_mission_scheme_template`
    ADD UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE;

ALTER TABLE `rtks_skill`
    DROP COLUMN `system_id`,
    DROP COLUMN `category_id`,
    DROP COLUMN `courses`;

CREATE TABLE `rtks_skill_mission_relation`
(
    `id`                  int         NOT NULL AUTO_INCREMENT,
    `skill_id`            varchar(32) NOT NULL,
    `mission_template_id` varchar(32) NOT NULL,
    `weight`              tinyint(4)  NOT NULL,
    `create_time`         datetime    NULL,
    `update_time`         datetime    NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `skill` (`skill_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;

ALTER TABLE `cr_mission_template`
    MODIFY COLUMN `attachment` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    ADD COLUMN `business_template_id` varchar(32) NULL DEFAULT NULL;

ALTER TABLE `cr_mission`
    ADD COLUMN `mission_template_id` varchar(32) NULL DEFAULT NULL;

ALTER TABLE `cr_task`
    ADD COLUMN `mission_template_id` varchar(32) NULL DEFAULT NULL;


CREATE TABLE `rtks_role_skill_relation`
(
    `id`          int(11)     NOT NULL AUTO_INCREMENT,
    `role_id`     varchar(32) NOT NULL,
    `skill_id`    varchar(32) NOT NULL,
    `weight`      tinyint(4)  NOT NULL,
    `create_time` datetime    NULL,
    `update_time` datetime    NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `role` (`role_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;


ALTER TABLE `rtks_role`
    DROP COLUMN `category`,
    DROP COLUMN `skill_id_weights`,
    DROP COLUMN `skill_ids`,
    DROP COLUMN `skill_courses`,
    ADD COLUMN `knowledge_weight` tinyint(4) NOT NULL DEFAULT 50,
    ADD COLUMN `skill_missions`   text       NULL     DEFAULT '[]';

-- ----------------------------
-- for attack, engage
-- ----------------------------

CREATE TABLE `rtks_attack_category`
(
    `id`          int          NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(32)  NOT NULL,
    `name`        varchar(255) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;


CREATE TABLE `rtks_attack`
(
    `id`          int          NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(32)  NOT NULL,
    `category_id` varchar(32)  NOT NULL,
    `name`        varchar(255) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;

CREATE TABLE `rtks_attack_train_relation`
(
    `id`                   int(11)     NOT NULL AUTO_INCREMENT,
    `attack_id`            varchar(32) NOT NULL,
    `business_template_id` varchar(32) NOT NULL,
    `create_time`          datetime    NULL,
    `update_time`          datetime    NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;


CREATE TABLE `rtks_engage_category`
(
    `id`          int          NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(32)  NOT NULL,
    `name`        varchar(255) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;


CREATE TABLE `rtks_engage_sub_category`
(
    `id`          int          NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(32)  NOT NULL,
    `category_id` varchar(32)  NOT NULL,
    `name`        varchar(255) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;

CREATE TABLE `rtks_engage`
(
    `id`              int          NOT NULL AUTO_INCREMENT,
    `resource_id`     varchar(32)  NOT NULL,
    `category_id`     varchar(32)  NOT NULL,
    `sub_category_id` varchar(32)  NOT NULL,
    `name`            varchar(255) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;

CREATE TABLE `rtks_engage_train_relation`
(
    `id`                   int(11)     NOT NULL AUTO_INCREMENT,
    `engage_id`            varchar(32) NOT NULL,
    `business_template_id` varchar(32) NOT NULL,
    `create_time`          datetime    NULL,
    `update_time`          datetime    NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;