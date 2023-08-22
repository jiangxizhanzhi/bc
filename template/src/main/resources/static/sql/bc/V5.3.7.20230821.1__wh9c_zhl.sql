-- 测评角色数据
CREATE TABLE `cr_eval_role_score` (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `resource_id` varchar(64) NOT NULL COMMENT 'uuid',
                                `role_id` varchar(256) NOT NULL COMMENT '角色名称',
                                `score` int(11) NOT NULL COMMENT '分数',
                                `role_group` int(11) NOT NULL COMMENT '红蓝角色',
                                `record` varchar(64) NOT NULL COMMENT '评估记录id',
                                `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
                                KEY `record` (`record`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC ;