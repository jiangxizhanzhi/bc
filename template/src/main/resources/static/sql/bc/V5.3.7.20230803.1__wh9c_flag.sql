-- flag任务关联表
CREATE TABLE `cr_flag_task` (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `resource_id` varchar(64) DEFAULT NULL COMMENT 'uuid',
                                `mission_id` varchar(64) DEFAULT NULL COMMENT '任务id',
                                `task_id` varchar(64) DEFAULT NULL COMMENT '任务id',
                                `attachment_id` varchar(64) DEFAULT NULL COMMENT '附件id',
                                `flag_value` varchar(256) DEFAULT NULL COMMENT 'flag值',
                                `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='下发flag任务关联表';
-- 红队,蓝队练习作战时间管理
CREATE TABLE `cr_team_time_management` (
                                         `id` int(11) NOT NULL AUTO_INCREMENT,
                                         `resource_id` varchar(64) NOT NULL COMMENT 'uuid',
                                         `room_id` varchar(40) NOT NULL COMMENT '房间id',
                                         `user_id` varchar(40) DEFAULT NULL,
                                         `astronomical_time` datetime NOT NULL COMMENT '天文时间',
                                         `combat_time` datetime NOT NULL COMMENT '作战时间',
                                         `rate` smallint(5) unsigned NOT NULL COMMENT '时间比率',
                                         `status` smallint(5) unsigned NOT NULL COMMENT '对抗赛状态',
                                         `create_user_id` varchar(64) DEFAULT NULL COMMENT '创建人id',
                                         `create_time` datetime(6) NOT NULL COMMENT '创建时间',
                                         `update_user_id` varchar(64) DEFAULT NULL COMMENT '更新人id',
                                         `update_time` datetime(6) NOT NULL COMMENT '更新时间',
                                         PRIMARY KEY (`id`) USING BTREE,
                                         UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
                                         UNIQUE KEY `room_id` (`room_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='红队,蓝队练习作战时间管理';

-- 测评规则
CREATE TABLE `cr_eval_rule` (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `resource_id` varchar(64) NOT NULL COMMENT 'uuid',
                                `name` varchar(256) NOT NULL COMMENT '规则名称',
                                `description` varchar(2048) DEFAULT NULL COMMENT '描述',
                                `score` int(11) NOT NULL COMMENT '分数',
                                `room_id` varchar(64) NOT NULL COMMENT '房间id',
                                `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
                                KEY `room_id` (`room_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='测评规则';
-- 武器工具库增加权限
ALTER TABLE cr_tool_tool add column `create_user_id` varchar(64) NOT NULL COMMENT '创建者id';
ALTER TABLE cr_tool_tool add column `operation_auth_type` int (11) NULL COMMENT '后台操作权限类型';
ALTER TABLE cr_tool_tool add column `operation_auth_ids` longtext NULL COMMENT '拥有后台操作权限的用户id';
ALTER TABLE cr_tool_tool add column `read_auth_type` int (1) NULL COMMENT '前台查看权限类型';
ALTER TABLE cr_tool_tool add column `read_auth_ids` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '拥有前台查看权限的组织';

-- 武器工具库附件
ALTER TABLE cr_tool_toolfile add column `version` varchar(256) DEFAULT NULL COMMENT '版本号';
ALTER TABLE cr_tool_toolfile add column `create_time`  datetime DEFAULT NULL COMMENT '创建时间';
ALTER TABLE cr_tool_toolfile add column `update_time`  datetime DEFAULT NULL COMMENT '更新时间';


