CREATE TABLE `cr_rb_attack_path` (
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
                                     `scene_id` varchar(64) DEFAULT NULL COMMENT '场景id',
                                     `source_node_id` varchar(64) DEFAULT NULL COMMENT '起始节点id',
                                     `source_node_name` varchar(255) DEFAULT NULL COMMENT '起始节点名称',
                                     `target_node_id` varchar(64) DEFAULT NULL COMMENT '目标节点id',
                                     `target_node_name` varchar(255) DEFAULT NULL COMMENT '目标节点名称',
                                     `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                     `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                     `template_id` varchar(64) DEFAULT NULL COMMENT '模板id',
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='攻击路径信息';