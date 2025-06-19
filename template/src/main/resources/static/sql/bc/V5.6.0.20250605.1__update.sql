alter table oj_practice_paper add column `ai_create_flag` tinyint(1) not null default 0 comment '是否是大模型服务创建的卷子，0-否 1-是';

alter table rtks_role add column avatar varchar(2048) not null comment '头像';

update rtks_role set avatar = '/cpms/rtks_role/Default.png' where 1 = 1;
update rtks_role set avatar = '/cpms/rtks_role/PenetrationTestingEngineer.png' where resource_id = 'c3fe96a8de569cdb92e273c8809a73b7';
update rtks_role set avatar = '/cpms/rtks_role/SecurityDevelopmentEngineer.png' where resource_id = 'e25dc8c9d15ddd621f16616083d68726';
update rtks_role set avatar = '/cpms/rtks_role/SecurityForensicsEngineer.png' where resource_id = 'aeec2fc773691f459105b0323c3e1711';
update rtks_role set avatar = '/cpms/rtks_role/ReverseAnalysisEngineer.png' where resource_id = 'f7753c4236c6b6981a577118a686aeb3';
update rtks_role set avatar = '/cpms/rtks_role/SecurityOperationMaintenanceEngineer.png' where resource_id = '542fc3e2caf0fa9b4935432ffe4daafa';

CREATE TABLE `ai_user_profile_report` (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `resource_id` varchar(64) NOT NULL,
                           `user_id` varchar(64) NOT NULL COMMENT '用户编号',
                           `dimension` int(11) NOT NULL COMMENT '1=课程学习 2=题目练习 3=综合训练 4=岗位评估',
                           `name` varchar(128) NOT NULL COMMENT '报告名称',
                           `content` longtext DEFAULT NULL COMMENT '报告内容',
                           `update_time` datetime NOT NULL COMMENT '更新时间',
                           `create_time` datetime NOT NULL COMMENT '创建时间',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT='智能体-用户画像报告';


INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 45, '未开始', 'Not Started');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 45, '进行中', 'Ongoing');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 45, '已结束', 'Ended');

ALTER TABLE `oj_classroom`
MODIFY COLUMN `start_time` datetime NULL COMMENT '课堂开始时间',
MODIFY COLUMN `end_time` datetime NULL COMMENT '课堂结束时间';

INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 46, '待提交', 'Not Submitted');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 46, '待批改', 'Not Evaluated');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 46, '已批改', 'Evaluated');

INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 47, '优', 'Excellent');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 47, '良', 'Good');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 47, '中', 'Average');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 4, 47, '差', 'Poor');

