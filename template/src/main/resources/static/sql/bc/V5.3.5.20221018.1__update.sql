ALTER TABLE oj_course_lesson ADD COLUMN lesson_type tinyint(1) NOT NULL COMMENT '新课时类型';

alter table oj_course add column `direction_int` longtext DEFAULT NULL comment '方向简介';
alter table oj_course_lesson add column `recording_status` int NOT NULL DEFAULT 1 comment '录制状态';
alter table oj_course_lesson add column `description` longtext DEFAULT NULL comment '内容描述';
alter table oj_practice_operation add column `type` int NOT NULL DEFAULT 1 comment '实操题类型';

alter table cr_eval_record add create_user_id varchar(64) null comment '创建者ID';
alter table cr_eval_record add create_role varchar(256) null comment '创建者所属角色';
alter table cr_eval_record add create_role_name varchar(256) null comment '创建者所属角色名称';

-- cr_eval_template
alter table `cr_eval_template`
	add column user_ids text default '[]' comment '角色用户' after step_config;

-- cr_eval_system
alter table `cr_eval_system`
	add column read_auth_type int default 1 comment '前台预览权限类型' after third_index_num,
	add column read_auth_ids longtext default '[]' comment '拥有前台预览权限的用户id' after read_auth_type;

-- cr_eval_task
alter table `cr_eval_task`
	add column step_config text default '' comment '步骤配置' after task_name;
	
-- cr_eval_task_step
alter table `cr_eval_task_step`
	add column role_name varchar(255) default '' comment '角色名' after end_time,
	add column user_ids text default '[]' comment '角色用户';


INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 0, 39, '文档节', 'Document');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 39, '视频节', 'Video');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 39, '单机实验节', 'Single Experiment');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 39, '网络实验节', 'Network Experiment');

INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 0, 40, '文档类', 'Document');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 40, '视频类', 'Video');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 40, '单机实验类', 'Single Experiment');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 40, '网络实验类', 'Network Experiment');
