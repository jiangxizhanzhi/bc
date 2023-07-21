CREATE TABLE `system_property` (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `name` varchar(255) NOT NULL COMMENT '配置名称',
                                   `desc` varchar(255) DEFAULT NULL COMMENT '配置描述',
                                   `content` varchar(255) NOT NULL COMMENT '配置值',
                                   `type` tinyint(3) DEFAULT NULL COMMENT '类型',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `idx_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
INSERT INTO `bc`.`system_property`(`id`, `name`, `desc`, `content`, `type`) VALUES (1, 'jingsai', '安全竞赛', '', 1);
INSERT INTO `bc`.`system_property`(`id`, `name`, `desc`, `content`, `type`) VALUES (2, 'yanlian', '实战演练', '', 1);
INSERT INTO `bc`.`system_property`(`id`, `name`, `desc`, `content`, `type`) VALUES (3, 'shixun', '安全实训', '', 1);
INSERT INTO `bc`.`system_property`(`id`, `name`, `desc`, `content`, `type`) VALUES (4, 'pinggu', '渗透评估', '', 1);

