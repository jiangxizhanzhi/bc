-- 红队攻击动效
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, -1, '信息收集', 'information collection');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, -1, '漏洞探测与挖掘', 'Vulnerability detection and mining');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, -1, '漏洞验证与利用', 'Vulnerability verification and exploitation');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 4, -1, '提升权限', 'Promotion permission');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 5, -1, '内网入侵', 'Intranet intrusion');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 6, -1, '痕迹清除', 'Trace removal');

-- 蓝队防守动效
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, -2, '日志分析', 'log analysis');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, -2, '流量分析', 'flow analysis');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, -2, '磁盘内存分析', 'Disk memory analysis');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 4, -2, '木马查杀与分析', 'Trojan horse killing and analysis');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 5, -2, '漏洞修复', 'Bug repair');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 6, -2, '安全配置与加固', 'Security configuration and hardening');

-- 公告目标类型
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, -3, '红队训练', 'Red Team');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, -3, '蓝队训练', 'Blue Team');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, -3, '红蓝对抗', 'Cyber Wargame');

-- 角色资产归属记录表
CREATE TABLE `cr_role_node` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL COMMENT 'uuid',
  `room_id` varchar(64) NOT NULL COMMENT '训练实例id',
  `role_id` varchar(64) NOT NULL COMMENT '角色id',
  `role_type` int(11) NOT NULL COMMENT '角色类型',
  `node_id` varchar(64) NOT NULL COMMENT '节点id',
  `node_category` int(11) NOT NULL COMMENT '节点类型',
  `original_role_id` varchar(64) NOT NULL COMMENT '原归属角色id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`),
  KEY `room_id` (`room_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- 训练公告
CREATE TABLE `cr_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL COMMENT 'uuid',
  `room_id` varchar(64) NOT NULL COMMENT '训练实例id',
  `business_type` int(11) NOT NULL COMMENT '业务类型',
  `content` varchar(2048) NOT NULL COMMENT '公告内容',
  `group` varchar(64) NOT NULL COMMENT '公告目标对象',
  `status` int(11) NOT NULL COMMENT '状态',
  `attachment_id` varchar(64) NULL COMMENT '附件id',
  `release_time` datetime NOT NULL COMMENT '原归属角色id',
  `create_user_id` varchar(64) NOT NULL COMMENT '角色id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`),
  KEY `room_id` (`room_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- 红蓝对抗训练事件
CREATE TABLE `cr_train_action` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL COMMENT 'uuid',
  `room_id` varchar(64) NOT NULL COMMENT '训练实例id',
  `type` int(11) NOT NULL COMMENT '类型',
  `role_id` varchar(64) NOT NULL COMMENT '训练角色id',
  `role_type` int(11) NOT NULL COMMENT '角色类型',
  `content` varchar(2048) NOT NULL COMMENT '事件内容',
  `module` int(11) NOT NULL COMMENT '模块',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`),
  KEY `room_id` (`room_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- 团队训练区分红队/蓝队训练
alter table cr_team_room add train_category int null comment '训练分类';

-- 训练任务完成动效相关
alter table cr_mission add event_type int null comment '任务完成事件类型';
CREATE TABLE `cr_mission_event_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mission_id` varchar(64) NOT NULL COMMENT '任务id',
  `device_id` varchar(64) NOT NULL COMMENT '场景节点id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `mission_id` (`mission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC
