ALTER
DATABASE ojj character set utf8 collate utf8_general_ci;

CREATE TABLE `test_migrate`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `content`     varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `create_time` datetime(0) NOT NULL,
    `update_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;
 
commit;