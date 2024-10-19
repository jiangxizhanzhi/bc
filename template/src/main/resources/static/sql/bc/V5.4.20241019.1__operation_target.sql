CREATE TABLE `oj_practice_operation_target` (
                                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                                `resource_id` varchar(64) NOT NULL,
                                                `operation_id` varchar(64) NOT NULL COMMENT '实操题id',
                                                `node_id` varchar(64) NOT NULL COMMENT '镜像id',
                                                `attachment_id` varchar(64) DEFAULT NULL COMMENT '附件id',
                                                `node_command` varchar(255) DEFAULT NULL COMMENT '命令',
                                                `create_time` datetime DEFAULT NULL,
                                                `update_time` datetime DEFAULT NULL,
                                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

