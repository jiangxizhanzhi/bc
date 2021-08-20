ALTER
DATABASE rtks character set utf8 collate utf8_general_ci;

CREATE TABLE `rtks_ability`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(255) DEFAULT NULL,
    `content`     varchar(255) DEFAULT NULL,
    `create_Time` datetime     DEFAULT NULL,
    `update_Time` datetime     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `rtks_knowledge`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(255)  DEFAULT NULL,
    `category_id` varchar(255)  DEFAULT NULL,
    `content`     varchar(255)  DEFAULT NULL,
    `courses`     varchar(4000) DEFAULT NULL,
    `create_Time` datetime      DEFAULT NULL,
    `update_Time` datetime      DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `rtks_knowledge_category`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64)  NOT NULL,
    `content`     varchar(255) NOT NULL,
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `resource_id` (`resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `rtks_level`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(255) NOT NULL,
    `name`        varchar(255) NOT NULL,
    `description` varchar(255) DEFAULT NULL,
    `parent`      varchar(255) DEFAULT NULL,
    `create_time` datetime     DEFAULT NULL,
    `update_time` datetime     DEFAULT NULL,
    `frame`       varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `resource_id` (`resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `rtks_role`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(255) NOT NULL,
    `name`        varchar(255) NOT NULL,
    `description` varchar(255) DEFAULT NULL,
    `create_time` datetime     DEFAULT NULL,
    `update_time` datetime     DEFAULT NULL,
    `level_id`    varbinary(255) NOT NULL,
    `category`    int(11) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `resource_id` (`resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

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
    `resource_id` varchar(255)  DEFAULT NULL,
    `system_id`   varchar(255)  DEFAULT NULL,
    `category_id` varchar(255)  DEFAULT NULL,
    `content`     varchar(255)  DEFAULT NULL,
    `courses`     varchar(4000) DEFAULT NULL,
    `create_Time` datetime      DEFAULT NULL,
    `update_Time` datetime      DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `rtks_skill_category`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(255) DEFAULT NULL,
    `content`     varchar(255) DEFAULT NULL,
    `create_Time` datetime     DEFAULT NULL,
    `update_Time` datetime     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `rtks_skill_system`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(255) DEFAULT NULL,
    `content`     varchar(255) DEFAULT NULL,
    `create_Time` datetime     DEFAULT NULL,
    `update_Time` datetime     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `rtks_task`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(255) DEFAULT NULL,
    `content`     varchar(255) DEFAULT NULL,
    `create_Time` datetime     DEFAULT NULL,
    `update_Time` datetime     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

commit;