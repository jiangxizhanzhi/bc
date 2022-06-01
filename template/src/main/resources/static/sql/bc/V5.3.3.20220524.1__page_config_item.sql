CREATE TABLE `page_config_item` (
                                    `id` int(11) NOT NULL AUTO_INCREMENT,
                                    `type` int(11) NOT NULL,
                                    `cn_name` varchar(128) NOT NULL,
                                    `en_name` varchar(128) NOT NULL,
                                    `value` varchar(128) NOT NULL,
                                    `create_time` datetime NOT NULL,
                                    `update_time` datetime NOT NULL,
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;