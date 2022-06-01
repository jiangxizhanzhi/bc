-- ----------------------------
-- for rtks_skill
-- ----------------------------
ALTER TABLE `cr_mission_template`
    ADD UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE;

ALTER TABLE `rtks_skill`
    MODIFY COLUMN `resource_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    MODIFY COLUMN `content` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;

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
    ADD COLUMN `knowledge_weight` tinyint(4) NOT NULL DEFAULT 50,
    ADD COLUMN `skill_missions`   text       NULL     DEFAULT '[]';

-- ----------------------------
-- for attack, engage
-- ----------------------------

CREATE TABLE `rtks_attack_category`
(
    `id`          int          NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(32)  NOT NULL,
    `name_zh`     varchar(255) NOT NULL,
    `name_en`     varchar(255) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;


CREATE TABLE `rtks_attack`
(
    `id`          int          NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(32)  NOT NULL,
    `category_id` varchar(32)  NOT NULL,
    `name_zh`     varchar(255) NOT NULL,
    `name_en`     varchar(255) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `category_id` (`category_id`) USING BTREE,
    INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;

CREATE TABLE `rtks_attack_train_relation`
(
    `id`                   int(11)     NOT NULL AUTO_INCREMENT,
    `resource_id`          varchar(32) NULL,
    `attack_id`            varchar(32) NOT NULL,
    `business_template_id` varchar(32) NOT NULL,
    `create_time`          datetime    NULL,
    `update_time`          datetime    NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `business_template_id` (`business_template_id`) USING BTREE,
    INDEX `attack_id` (`attack_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;


CREATE TABLE `rtks_engage_category`
(
    `id`          int          NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(32)  NOT NULL,
    `name_zh`     varchar(255) NOT NULL,
    `name_en`     varchar(255) NOT NULL,
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
    `name_zh`     varchar(255) NOT NULL,
    `name_en`     varchar(255) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `category_id` (`category_id`) USING BTREE
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
    `name_zh`         varchar(255) NOT NULL,
    `name_en`         varchar(255) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `sub_category_id` (`sub_category_id`) USING BTREE,
    INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;

CREATE TABLE `rtks_engage_train_relation`
(
    `id`                   int(11)     NOT NULL AUTO_INCREMENT,
    `resource_id`          varchar(32) NULL,
    `engage_id`            varchar(32) NOT NULL,
    `business_template_id` varchar(32) NOT NULL,
    `create_time`          datetime    NULL,
    `update_time`          datetime    NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `business_template_id` (`business_template_id`) USING BTREE,
    INDEX `engage_id` (`engage_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;

-- ----------------------------
-- for constants
-- ----------------------------

UPDATE `cr_constants_config`
SET `cn_name` = '初级'
WHERE code = 1
  AND type = 2;

UPDATE `cr_constants_config`
SET `cn_name` = '中级'
WHERE code = 2
  AND type = 2;

UPDATE `cr_constants_config`
SET `cn_name` = '高级'
WHERE code = 3
  AND type = 2;
