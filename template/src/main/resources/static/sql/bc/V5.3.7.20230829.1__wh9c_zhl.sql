CREATE TABLE `cr_eval_task_log` (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `resource_id` varchar(64) NOT NULL COMMENT 'uuid',
                               `room_id` varchar(64) DEFAULT NULL COMMENT '训练房间id',
                               `mission_id` varchar(64) DEFAULT NULL COMMENT '任务id',
                               `action_code` tinyint(4) DEFAULT NULL COMMENT '动作类别',
                               `user_id` varchar(64) DEFAULT NULL COMMENT '动作用户id',
                               `task_id` varchar(64) DEFAULT NULL COMMENT '任务id',
                               `eval_template_id` varchar(32) DEFAULT NULL,
                               `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                               `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                               PRIMARY KEY (`id`) USING BTREE,
                               UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
                               KEY `mission_id` (`mission_id`) USING BTREE,
                               KEY `eval_template_id` (`eval_template_id`) USING BTREE,
                               KEY `user_id` (`user_id`) USING BTREE,
                               KEY `action_code` (`action_code`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='评估_任务动作记录';