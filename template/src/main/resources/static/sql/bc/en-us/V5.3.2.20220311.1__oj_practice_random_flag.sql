
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `oj_practice_random_flag` (
                                           `id` int(11) NOT NULL AUTO_INCREMENT,
                                           `resource_id` varchar(64) NOT NULL,
                                           `user_id` varchar(64) NOT NULL,
                                           `attachment_id` varchar(64) NOT NULL,
                                           `practice_id` varchar(64) NOT NULL,
                                           `flag` varchar(1028) NOT NULL,
                                           `create_time` datetime NOT NULL,
                                           `update_time` datetime NOT NULL,
                                           `type` int(1) DEFAULT NULL,
                                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;