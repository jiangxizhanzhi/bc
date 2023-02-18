
-- ----------------------------
-- Table structure for cr_scene_manage
-- ----------------------------
CREATE TABLE `cr_scene_manage` (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `resource_id` varchar(64) NOT NULL COMMENT 'uuid',
                                   `business_template_id` varchar(64) NOT NULL COMMENT '训练模板id',
                                   `pre_skills` varchar(256) NOT NULL COMMENT '前置技能',
                                   `train_skills` varchar(256) NOT NULL COMMENT '训练目标',
                                   `sub_type` tinyint(4) NOT NULL COMMENT '训练类别',
                                   `duration_time` int(10) unsigned NOT NULL COMMENT '训练时长',
                                   `missions_scheme` longtext NOT NULL COMMENT '任务配置',
                                   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                   `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
                                   KEY `business_template_id` (`business_template_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

CREATE TABLE `marking` (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `resource_id` varchar(64) NOT NULL,
                           `business_id` varchar(64) NOT NULL COMMENT '评分所属业务（资源）id（课程、工具库...）',
                           `user_id` varchar(64) NOT NULL COMMENT '评分用户id',
                           `score` int(11) NOT NULL COMMENT '评分',
                           `mark_type` int(11) NOT NULL COMMENT '评分类型（课程1、工具库2）',
                           `create_time` datetime NOT NULL,
                           `update_time` datetime NOT NULL,
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='评价打分';

DELETE FROM `cr_constants_config` WHERE `type` = 12;
INSERT INTO `cr_constants_config` (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (52, NULL, 0, 12, '理论基础', 'Theoretical Basis');
INSERT INTO `cr_constants_config` (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (53, NULL, 2, 12, '夺旗练习', 'CTF Practice');
INSERT INTO `cr_constants_config` (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (137, NULL, 3, 12, '真实漏洞', 'Vulnerability');