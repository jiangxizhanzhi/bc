
ALTER TABLE cr_eval_index  add column `cal_score` INT(11) NULL  COMMENT '调试指标得分';
ALTER TABLE cr_eval_index  add column `test_score` INT(11) NULL  COMMENT '试验指标得分';
ALTER TABLE cr_eval_index  add column `score` INT(11) NULL  COMMENT '指标得分';
ALTER TABLE cr_eval_index  add column `cal_message` varchar(255) NULL  COMMENT '调试指标计算错误信息';
ALTER TABLE cr_eval_index  add column `test_message` varchar(255) NULL  COMMENT '试验指标计算错误信息';

ALTER TABLE cr_eval_collection  add column `score` INT(11) NULL  COMMENT '指标得分';
ALTER TABLE cr_eval_collection  add column `message` varchar(255) NULL  COMMENT '指标计算错误信息';
ALTER TABLE cr_eval_collection  add column `progress` INT(11) NULL default 1  COMMENT '指标进度';

ALTER TABLE cr_eval_aggregation  add column `score` INT(11) NULL  COMMENT '指标得分';
ALTER TABLE cr_eval_aggregation  add column `message` varchar(255) NULL  COMMENT '指标计算错误信息';

CREATE TABLE `cr_eval_action_template` (
                                           `id` int(11) NOT NULL AUTO_INCREMENT,
                                           `resource_id` varchar(32) DEFAULT NULL COMMENT 'uuid',
                                           `cloud_template_id` varchar(32) DEFAULT NULL COMMENT '云模板id',
                                           `rule_id` varchar(32) DEFAULT NULL COMMENT '关联的规则id',
                                           `name` varchar(255) DEFAULT NULL COMMENT '事件名称',
                                           `action_type` tinyint(2) DEFAULT NULL COMMENT '动作类型',
                                           `level` tinyint(2) DEFAULT NULL COMMENT '告警事件级别',
                                           `attack_type` tinyint(4) DEFAULT NULL COMMENT '攻击类型级别',
                                           `source_id` varchar(32) DEFAULT NULL COMMENT '攻击源节点id',
                                           `target_id` varchar(32) DEFAULT NULL COMMENT '攻击目标节点id',
                                           `result` tinyint(2) DEFAULT NULL COMMENT '攻击结果',
                                           `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                           `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='云场景模板动作表';


CREATE TABLE `cr_eval_cloud_template` (
                                          `id` int(11) NOT NULL AUTO_INCREMENT,
                                          `resource_id` varchar(32) DEFAULT NULL COMMENT 'uuid',
                                          `name` varchar(255) DEFAULT NULL COMMENT '试验名称',
                                          `target` varchar(2048) DEFAULT NULL COMMENT '试验目标',
                                          `logo` varchar(255) DEFAULT NULL COMMENT '封面url',
                                          `type` tinyint(4) DEFAULT NULL COMMENT '试验类型',
                                          `time` int(5) DEFAULT NULL COMMENT '计划时长',
                                          `difficulty` tinyint(4) DEFAULT NULL COMMENT '训练难度',
                                          `cloud_type` tinyint(4) DEFAULT NULL COMMENT '云类型',
                                          `topo_id` varchar(40) DEFAULT NULL COMMENT '关联topo uuid',
                                          `step_ids` text DEFAULT NULL COMMENT '步骤id列表',
                                          `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建者id',
                                          `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                          `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='云场景模板表';


CREATE TABLE `cr_eval_dimension_template` (
                                              `id` int(11) NOT NULL AUTO_INCREMENT,
                                              `resource_id` varchar(32) DEFAULT NULL COMMENT 'uuid',
                                              `type` tinyint(4) NOT NULL COMMENT '类型',
                                              `name` varchar(255) NOT NULL COMMENT '事件名称',
                                              `event_type` tinyint(4) NOT NULL COMMENT '事件类型',
                                              `rule_parameter` varchar(255) NOT NULL COMMENT '规则参数',
                                              `rule_id` varchar(32) NOT NULL COMMENT '关联的规则id',
                                              `cloud_template_id` varchar(32) DEFAULT NULL COMMENT '云模板id',
                                              `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                              `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='云场景模板标注表';


CREATE TABLE `cr_eval_host_template` (
                                         `id` int(11) NOT NULL AUTO_INCREMENT,
                                         `resource_id` varchar(32) DEFAULT NULL COMMENT 'uuid',
                                         `host_name` varchar(255) DEFAULT NULL COMMENT '主机名称',
                                         `host_id` varchar(32) DEFAULT NULL COMMENT '主机id',
                                         `type` varchar(10) DEFAULT NULL COMMENT '类型',
                                         `ip` varchar(10) DEFAULT NULL COMMENT 'ip地址',
                                         `rule_id` varchar(255) DEFAULT NULL COMMENT '规则id',
                                         `cloud_template_id` varchar(32) DEFAULT NULL COMMENT '云模板id',
                                         `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                         `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='云场景模板主机表';


CREATE TABLE `cr_eval_rule_template` (
                                         `id` int(11) NOT NULL AUTO_INCREMENT,
                                         `resource_id` varchar(32) DEFAULT NULL COMMENT 'uuid',
                                         `name` varchar(255) DEFAULT NULL COMMENT '规则名称',
                                         `description` varchar(2048) DEFAULT NULL COMMENT '规则描述',
                                         `cloud_template_id` varchar(32) DEFAULT NULL COMMENT '云模板id',
                                         `status` tinyint(2) DEFAULT NULL COMMENT '状态',
                                         `labels` text DEFAULT NULL COMMENT '标签',
                                         `grade` tinyint(2) DEFAULT NULL COMMENT '等级',
                                         `boolean_relation` tinyint(2) DEFAULT NULL COMMENT '布尔关系',
                                         `tasks` text DEFAULT NULL COMMENT '关联任务id',
                                         `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建者id',
                                         `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                         `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='云场景模板规则表';


CREATE TABLE `cr_eval_step_template` (
                                         `id` int(11) NOT NULL AUTO_INCREMENT,
                                         `resource_id` varchar(32) DEFAULT NULL COMMENT 'uuid',
                                         `name` varchar(255) DEFAULT NULL COMMENT '阶段名称',
                                         `process` text DEFAULT NULL COMMENT '验证过程',
                                         `setting` text DEFAULT NULL COMMENT '载荷设置',
                                         `cloud_template_id` varchar(32) DEFAULT NULL COMMENT '云模板id',
                                         `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                         `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='云场景模板规则表';