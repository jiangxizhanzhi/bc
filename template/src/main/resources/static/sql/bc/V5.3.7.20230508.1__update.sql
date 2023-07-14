-- 禅道13054
alter table cr_eval_index_ref modify next_ids text not null comment '子节点关联id';

CREATE TABLE `system_property` (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `name` varchar(255) NOT NULL COMMENT '配置名称',
                                   `desc` varchar(255) DEFAULT NULL COMMENT '配置描述',
                                   `content` varchar(255) NOT NULL COMMENT '配置值',
                                   `type` tinyint(3) DEFAULT NULL COMMENT '类型',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `idx_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;