ALTER
DATABASE rtks character set utf8 collate utf8_general_ci;

CREATE TABLE `rtks_ability`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `content`     varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `create_time` datetime(0) NOT NULL,
    `update_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

CREATE TABLE `rtks_knowledge`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `category_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `content`     varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `courses`     text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `create_time` datetime(0) NOT NULL,
    `update_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

CREATE TABLE `rtks_knowledge_category`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `content`     varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `create_time` datetime(0) NOT NULL,
    `update_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

CREATE TABLE `rtks_level`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `name`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `parent`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_time` datetime(0) NULL DEFAULT NULL,
    `update_time` datetime(0) NULL DEFAULT NULL,
    `frame`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

CREATE TABLE `init_rtks_level`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `name`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `parent`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_time` datetime(0) NULL DEFAULT NULL,
    `update_time` datetime(0) NULL DEFAULT NULL,
    `frame`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

CREATE TABLE `init_rtks_course_related`
(
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程id',
    `related_id`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点id',
    `type`        int(11) NOT NULL COMMENT '1：知识点;2：技能点',
    `weight`      int(11) NOT NULL COMMENT '权重',
    INDEX         `course_id`(`resource_id`) USING BTREE,
    INDEX         `related_id`(`related_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

CREATE TABLE `init_rtks_knowledge`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `category_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `content`     varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `courses`     text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `create_time` datetime(0) NOT NULL,
    `update_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;


CREATE TABLE `init_rtks_knowledge_category`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `content`     varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `create_time` datetime(0) NOT NULL,
    `update_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

CREATE TABLE `init_rtks_role`
(
    `id`                   int(11) NOT NULL AUTO_INCREMENT,
    `resource_id`          varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`                 varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `description`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_time`          datetime(0) NOT NULL,
    `update_time`          datetime(0) NOT NULL,
    `level_id`             varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `category`             varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `task_ids`             text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `knowledge_id_weights` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `knowledge_ids`        text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `skill_id_weights`     text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `skill_ids`            text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `ability_ids`          text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `skill_courses`        text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
    `knowledge_courses`    text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

CREATE TABLE `init_rtks_task`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `content`     varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `create_time` datetime(0) NOT NULL,
    `update_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

CREATE TABLE `rtks_role`
(
    `id`                   int(11) NOT NULL AUTO_INCREMENT,
    `resource_id`          varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `name`                 varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `description`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_time`          datetime(0) NOT NULL,
    `update_time`          datetime(0) NOT NULL,
    `level_id`             varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `category`             varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `task_ids`             text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `knowledge_id_weights` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '[]',
    `knowledge_ids`        text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `skill_id_weights`     text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '[]',
    `skill_ids`            text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `ability_ids`          text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `skill_courses`        text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
    `knowledge_courses`    text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

CREATE TABLE `rtks_course_related`
(
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程id',
    `related_id`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点id',
    `type`        int(11) NOT NULL COMMENT '1：知识点;2：技能点',
    `weight`      int(11) NOT NULL COMMENT '权重',
    INDEX         `course_id`(`resource_id`) USING BTREE,
    INDEX         `related_id`(`related_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8;


CREATE TABLE `rtks_role_related`
(
    `id`         int(11) NOT NULL AUTO_INCREMENT,
    `role_id`    varchar(255) NOT NULL,
    `related_id` varchar(255) NOT NULL,
    `type`       int(11) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `rtks_skill`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `system_id`   varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `category_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `content`     varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `courses`     text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `create_time` datetime(0) NOT NULL,
    `update_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

CREATE TABLE `rtks_skill_category`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `content`     varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `create_time` datetime(0) NOT NULL,
    `update_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id_UNIQUE`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;


CREATE TABLE `rtks_skill_system`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `scene_id`    int(11) NOT NULL,
    `content`     varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `create_time` datetime(0) NOT NULL,
    `update_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

CREATE TABLE `rtks_task`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `content`     varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `create_time` datetime(0) NOT NULL,
    `update_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

CREATE TABLE `rtks_user_role`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `user_id`     varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `role_id`     varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `role_name`   varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `create_time` datetime(0) NOT NULL,
    `update_time` datetime(0) NOT NULL,
    `user_name`   varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `user_uni`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

commit;