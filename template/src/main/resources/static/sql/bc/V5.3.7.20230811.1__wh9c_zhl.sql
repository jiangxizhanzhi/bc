-- 训练评估-下发任务详情
CREATE TABLE `cr_eval_train_task` (
                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                      `resource_id` varchar(64) DEFAULT NULL COMMENT 'uuid',
                                      `status` tinyint(4) DEFAULT NULL COMMENT '执行状态',
                                      `mission_id` varchar(64) DEFAULT NULL COMMENT '任务id',
                                      `user_id` varchar(64) DEFAULT NULL COMMENT '训练用户id',
                                      `user_answer` longtext COMMENT '用户答案',
                                      `release_time` datetime DEFAULT NULL COMMENT '下发时间',
                                      `last_submit_time` datetime DEFAULT NULL COMMENT '最后提交时间',
                                      `task_start_time` datetime DEFAULT NULL COMMENT '任务开始时间',
                                      `room_id` varchar(64) DEFAULT NULL COMMENT '房间id',
                                      `role_id` varchar(64) DEFAULT NULL COMMENT '角色id',
                                      `business_type` int(11) DEFAULT NULL COMMENT '训练类型',
                                      `score` smallint(6) DEFAULT NULL COMMENT '得分',
                                      `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                      `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                      `mission_template_id` varchar(32) DEFAULT NULL COMMENT '任务模板id',
                                      `eval_template_id` varchar(32) DEFAULT NULL COMMENT '评测模版id',
                                      PRIMARY KEY (`id`) USING BTREE,
                                      UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
                                      KEY `mission_id` (`mission_id`) USING BTREE,
                                      KEY `user_id` (`user_id`) USING BTREE,
                                      KEY `room_id` (`room_id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='下发任务详情(评估)';
-- 训练评估-任务提交记录与评估
CREATE TABLE `cr_eval_task_submit` (
                                       `id` int(11) NOT NULL AUTO_INCREMENT,
                                       `resource_id` varchar(64) NOT NULL COMMENT 'uuid',
                                       `room_id` varchar(64) DEFAULT NULL COMMENT '训练房间id',
                                       `task_id` varchar(64) DEFAULT NULL COMMENT '任务id',
                                       `answer` longtext COMMENT '提交答案',
                                       `answer_extension` longtext COMMENT '提交答案附加信息',
                                       `evaluator_id` varchar(64) DEFAULT NULL COMMENT '评估人',
                                       `evaluate_time` datetime DEFAULT NULL COMMENT '评估时间',
                                       `evaluate_status` tinyint(4) DEFAULT NULL COMMENT '评估状态',
                                       `reason` tinyint(4) DEFAULT NULL COMMENT '评估理由',
                                       `detail` varchar(255) DEFAULT NULL COMMENT '评估详情',
                                       `score` smallint(6) DEFAULT NULL COMMENT '得分',
                                       `mission_score` smallint(6) DEFAULT NULL COMMENT '任务分值',
                                       `eval_template_id` varchar(32) DEFAULT NULL COMMENT '评测模版id',
                                       `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                       `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                       PRIMARY KEY (`id`) USING BTREE,
                                       UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
                                       KEY `task_id` (`task_id`) USING BTREE,
                                       KEY `room_id` (`room_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='任务提交记录与评估(评估)';
-- 训练评估-训练评分
CREATE TABLE `cr_eval_score` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `resource_id` varchar(64) NOT NULL COMMENT 'uuid',
                                 `room_id` varchar(40) NOT NULL COMMENT '房间id',
                                 `user_id` varchar(40) NOT NULL COMMENT '用户id',
                                 `training_score` varchar(4000) DEFAULT NULL COMMENT '训练得分',
                                 `training_percentage` varchar(40) NOT NULL COMMENT '训练分占比',
                                 `skill_percentage` varchar(40) NOT NULL COMMENT '技能分占比',
                                 `skill_score` varchar(40) NOT NULL COMMENT '技能分',
                                 `comment` longtext COMMENT '评价',
                                 `final_score` varchar(40) NOT NULL COMMENT '最终得分',
                                 `eval_template_id` varchar(32) DEFAULT NULL COMMENT '评测模版id',
                                 `create_time` datetime NOT NULL COMMENT '创建时间',
                                 `update_time` datetime NOT NULL COMMENT '更新时间',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
                                 UNIQUE KEY `room_user` (`room_id`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='训练评分(评估)';
ALTER TABLE cr_tool_toolfile add column `version` varchar(256) DEFAULT NULL COMMENT '版本号';

-- 修改
ALTER TABLE `cr_eval_rule` change  `room_id` `eval_template_id` varchar(32);
