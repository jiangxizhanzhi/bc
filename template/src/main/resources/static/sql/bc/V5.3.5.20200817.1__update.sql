
-- oj_classroom
alter table oj_classroom modify start_time datetime not null comment '课堂开始时间';

alter table oj_classroom modify end_time datetime not null comment '课堂结束时间';

alter table oj_classroom modify course_id varchar(64) not null comment '关联课程id';

alter table oj_classroom modify lesson_id varchar(64) not null comment '关联课时id';

alter table oj_classroom modify organization_id varchar(64) not null comment '关联组织id';

alter table oj_classroom modify organization_ids longtext not null comment '关联组织下所有组织id';

alter table oj_classroom modify create_user_id varchar(64) not null comment '创建者（课堂教师）';

alter table oj_classroom modify train_plan_id varchar(64) null comment '关联训练计划id';

alter table oj_classroom comment '实训课程表（课堂安排）';


-- oj_classroom_group_archive
alter table oj_classroom_group_archive modify name varchar(128) not null comment '分组信息快照名称';

alter table oj_classroom_group_archive modify `groups` longtext not null comment '分组数据';

alter table oj_classroom_group_archive modify classroom_id varchar(64) not null comment '所属课堂id';

alter table oj_classroom_group_archive comment '实训课堂（实验课）分组信息快照';


-- oj_classroom_group_info
alter table oj_classroom_group_info modify classroom_id varchar(64) not null comment '所属课堂id';

alter table oj_classroom_group_info modify group_user_ids longtext not null comment '组内用户id';

alter table oj_classroom_group_info modify destroy_delay int null comment '实验环境销毁期限（单位:h）';

alter table oj_classroom_group_info modify scene_share_mode int null comment '场景是否共享标识';

alter table oj_classroom_group_info modify lesson_id varchar(64) not null comment '关联实验课id';

alter table oj_classroom_group_info modify topo_id varchar(64) null comment '实验课使用拓扑id';

alter table oj_classroom_group_info comment '实训课堂（实验课）分组信息';


-- oj_classroom_sign
alter table oj_classroom_sign modify classroom_id varchar(64) not null comment '所属课堂id';

alter table oj_classroom_sign modify sign_in int not null comment '是否签到';

alter table oj_classroom_sign modify sign_in_time datetime not null comment '签到时间';

alter table oj_classroom_sign modify need_help int null comment '学员是否需要请求协助（实验课）';

alter table oj_classroom_sign modify node_id varchar(64) null comment '场景中请求协助的节点id（实验课）';

alter table oj_classroom_sign comment '实训课堂签到记录';


-- oj_comment
alter table oj_comment modify reply_id varchar(64) null comment '此留言所回复留言id';

alter table oj_comment modify business_id varchar(64) not null comment '留言所属业务（资源）id（课程、习题...）';

alter table oj_comment modify theme_name varchar(128) not null comment '留言所属主题信息（课程名、习题名...）';

alter table oj_comment modify user_id varchar(64) not null comment '发起留言用户id';

alter table oj_comment modify content varchar(1024) not null comment '留言内容';

alter table oj_comment modify business_type int not null comment '留言所属模块类型（课程、习题...）';

alter table oj_comment modify root_id varchar(64) null comment '此留言所属楼层的层主留言id';

alter table oj_comment comment '实训留言';


-- oj_comment_like
alter table oj_comment_like modify user_id varchar(64) not null comment '点赞用户id';

alter table oj_comment_like modify comment_id varchar(64) not null comment '点赞评论id';

alter table oj_comment_like comment '实训留言点赞';


-- oj_course
alter table oj_course modify builtin int not null comment '课程是否属于内置资源';

alter table oj_course modify operation_auth_type int not null comment '后台操作权限类型';

alter table oj_course modify operation_auth_ids longtext not null comment '拥有后台操作权限的用户id';

alter table oj_course modify read_auth_type int not null comment '前台预览权限类型';

alter table oj_course modify read_auth_ids varchar(4096) not null comment '拥有前台预览权限的组织id';

alter table oj_course modify name varchar(256) not null comment '课程名称';

alter table oj_course modify description longtext null comment '课程描述';

alter table oj_course modify logo varchar(512) null comment '课程封面id';

alter table oj_course modify outline longtext charset utf8mb4 null comment '课程大纲';

alter table oj_course modify difficulty int not null comment '难度';

alter table oj_course modify create_user_id varchar(64) not null comment '创建者id';

alter table oj_course modify modify_user_id varchar(64) not null comment '最后修改的用户id';

alter table oj_course modify public int not null comment '是否公开';

alter table oj_course modify course_type int null comment '课程类型';

alter table oj_course modify heat int not null comment '热度';

alter table oj_course modify lecturer varchar(64) null comment '讲师';

alter table oj_course modify score int null comment '学分';

alter table oj_course modify course_num varchar(64) null comment '课程编号';

alter table oj_course modify category_id varchar(64) not null comment '关联方向id';

alter table oj_course comment '实训课程';


-- oj_course_category
alter table oj_course_category modify name varchar(128) not null comment '方向名称';

alter table oj_course_category comment '实训课程方向/分类';


-- oj_course_lesson
alter table oj_course_lesson modify course_id varchar(64) not null comment '关联课程id';

alter table oj_course_lesson modify builtin int not null comment '是否内置';

alter table oj_course_lesson modify name varchar(256) not null comment '课时名称';

alter table oj_course_lesson modify type int null comment '课时类型';

alter table oj_course_lesson modify handout_type int null comment '讲义类型';

alter table oj_course_lesson modify handout_url longtext charset utf8mb4 null comment '讲义文件地址';

alter table oj_course_lesson modify video_url varchar(512) null comment '视频课件地址';

alter table oj_course_lesson modify difficulty int not null comment '难度';

alter table oj_course_lesson modify public int not null comment '是否公开';

alter table oj_course_lesson modify attachment_ids varchar(1024) null comment '关联附件id';

alter table oj_course_lesson modify compulsory int not null comment '是否必修';

alter table oj_course_lesson modify duration int null comment '建议时长（单位：m）';

alter table oj_course_lesson modify destroy_delay int null comment '场景销毁期限（实验课使用）';

alter table oj_course_lesson modify topo_id varchar(64) null comment '关联拓扑id（实验课使用）';

alter table oj_course_lesson modify knowledge varchar(2048) null comment '知识点';

alter table oj_course_lesson modify scene_share_mode int null comment '场景是否共享标识';

alter table oj_course_lesson modify origin_markdown_file longtext null comment '上传markdown讲义原文件地址';

alter table oj_course_lesson modify create_user_id varchar(64) null comment '创建用户id';

alter table oj_course_lesson modify heat int not null comment '热度';

alter table oj_course_lesson modify paper_id varchar(64) null comment '关联课后练习id';

alter table oj_course_lesson modify paper_public int null comment '课后练习是否公开';

alter table oj_course_lesson comment '实训课时';


-- oj_lesson_config
alter table oj_lesson_config modify `index` int not null comment '此节点在父节点下顺序索引';

alter table oj_lesson_config modify parent_id varchar(64) null comment '父节点id';

alter table oj_lesson_config modify name varchar(256) null comment '目录节点名称';

alter table oj_lesson_config modify node_type int not null comment '节点类型';

alter table oj_lesson_config modify course_id varchar(64) not null comment '关联课程id';

alter table oj_lesson_config modify lesson_id varchar(64) null comment '关联课时id';

alter table oj_lesson_config comment '实训课时目录';


-- oj_lesson_paper_evaluate
alter table oj_lesson_paper_evaluate modify lesson_id varchar(64) not null comment '关联课时id';

alter table oj_lesson_paper_evaluate modify score double(11,1) not null comment '得分';

alter table oj_lesson_paper_evaluate modify record longtext not null comment '课后联系提交记录';

alter table oj_lesson_paper_evaluate modify submit_user_id varchar(64) not null comment '提交用户id';

alter table oj_lesson_paper_evaluate modify total_score double(11,1) not null comment '课后练习总分';

alter table oj_lesson_paper_evaluate modify level int null comment '课后练习完成评级';

alter table oj_lesson_paper_evaluate modify accuracy varchar(64) null comment '正确率';

alter table oj_lesson_paper_evaluate modify corrector varchar(64) null comment '评卷用户id';

alter table oj_lesson_paper_evaluate comment '实训课后练习评估';


-- oj_lesson_record
alter table oj_lesson_record modify lesson_id varchar(64) not null comment '学习课时id';

alter table oj_lesson_record modify user_id varchar(64) not null comment '学习用户id';

alter table oj_lesson_record modify course_id varchar(64) not null comment '学习课程id';

alter table oj_lesson_record comment '实训课时学习记录';


-- oj_local_scene
alter table oj_local_scene modify user_id varchar(64) not null comment '创建场景用户id';

alter table oj_local_scene modify shared int not null comment '是否为共享场景';

alter table oj_local_scene modify from_web int null comment '是否为前台场景';

alter table oj_local_scene modify flags longtext null comment '环境FLAG（实践操作习题使用）';

alter table oj_local_scene modify business_id varchar(128) not null comment '关联场景的业务（资源）id';

alter table oj_local_scene modify business_type int not null comment '关联场景的业务类型';

alter table oj_local_scene modify local_scene_id varchar(64) not null comment '关联的local_scene.resource_id';

alter table oj_local_scene comment '实训场景关联表';


-- oj_note
alter table oj_note modify operation_auth_ids varchar(2048) null comment '授权操作用户id';

alter table oj_note modify read_auth_ids varchar(2048) null comment '授权访问组织id';

alter table oj_note comment '实训我的笔记/实验报告';


-- oj_note_evaluate
alter table oj_note_evaluate modify score int not null comment '得分';

alter table oj_note_evaluate modify passed int not null comment '是否通过';

alter table oj_note_evaluate modify evaluate_comment varchar(2048) null comment '评语';

alter table oj_note_evaluate modify evaluator_id varchar(64) not null comment '评估用户id';

alter table oj_note_evaluate modify evaluate_time datetime not null comment '评估时间';

alter table oj_note_evaluate modify note_id varchar(64) null comment '评估实验报告id';

alter table oj_note_evaluate comment '实训实验报告评估';


-- oj_paper
alter table oj_paper modify name varchar(128) not null comment '试卷名称';

alter table oj_paper modify description varchar(1024) null comment '试卷描述';

alter table oj_paper modify logo varchar(512) null comment '试卷封面id';

alter table oj_paper modify practice_count int not null comment '试卷下习题总数';

alter table oj_paper modify total_score double(11,1) null comment '试卷总分';

alter table oj_paper modify public int not null comment '是否公开';

alter table oj_paper modify type int not null comment '试卷类型';

alter table oj_paper comment '实训试卷/课后练习';


-- oj_practice_category
alter table oj_practice_category comment '实训习题方向/分类';


-- oj_practice_operation
alter table oj_practice_operation modify category_id varchar(64) not null comment '关联方向id';

alter table oj_practice_operation modify builtin int not null comment '是否为内置资源';

alter table oj_practice_operation modify practice_set_id varchar(64) not null comment '关联习题集id';

alter table oj_practice_operation modify title varchar(128) null comment '标题';

alter table oj_practice_operation modify content longtext charset utf8mb4 null comment '内容';

alter table oj_practice_operation modify answer varchar(1024) null comment '答案';

alter table oj_practice_operation modify difficulty int not null comment '难度';

alter table oj_practice_operation modify knowledge varchar(1024) null comment '知识点';

alter table oj_practice_operation modify public int not null comment '是否公开';

alter table oj_practice_operation modify score int not null comment '得分';

alter table oj_practice_operation modify attachment_id varchar(1024) null comment '关联附件id';

alter table oj_practice_operation modify writeup longtext charset utf8mb4 null comment '题解';

alter table oj_practice_operation modify writeup_public int null comment '题解是否公开';

alter table oj_practice_operation modify create_user_id varchar(64) not null comment '创建用户id';

alter table oj_practice_operation modify dynamic_env int null comment '是否为动态环境';

alter table oj_practice_operation modify topo_id varchar(64) null comment '关联拓扑id（动态环境下拥有）';

alter table oj_practice_operation modify scene_share_mode int null comment '场景分享标识（动态环境下拥有）';

alter table oj_practice_operation modify dynamic_flag int null comment '是否为动态FLAG';

alter table oj_practice_operation modify destroy_delay int null comment '场景销毁时限（单位：h，动态环境下拥有）';

alter table oj_practice_operation comment '实训实践操作';


-- oj_practice_operation_ref
alter table oj_practice_operation_ref modify snapshot longtext not null comment '实践操作基础信息快照';

alter table oj_practice_operation_ref modify origin_id varchar(64) not null comment '原实践操作id';

alter table oj_practice_operation_ref modify paper_id varchar(64) not null comment '关联试卷id';

alter table oj_practice_operation_ref comment '实训实践操作引用表';


-- oj_practice_random_flag
alter table oj_practice_random_flag modify user_id varchar(64) not null comment '答题用户id';

alter table oj_practice_random_flag modify attachment_id varchar(64) not null comment '关联附件id';

alter table oj_practice_random_flag modify practice_id varchar(64) not null comment '关联实践操作id';

alter table oj_practice_random_flag modify flag varchar(1028) not null comment 'FLAG';

alter table oj_practice_random_flag modify type int null comment '区别使用业务（习题、课后练习...）';

alter table oj_practice_random_flag comment '实训实践操作随机FLAG记录';


-- oj_practice_set
alter table oj_practice_set modify builtin int not null comment '是否为内置数据';

alter table oj_practice_set modify practice_type int not null comment '习题集类型';

alter table oj_practice_set modify name varchar(128) not null comment '习题集名称';

alter table oj_practice_set modify logo varchar(512) null comment '习题集封面id';

alter table oj_practice_set modify public int not null comment '是否公开';

alter table oj_practice_set modify create_user_id varchar(64) not null comment '创建用户id';

alter table oj_practice_set modify operation_auth_type int not null comment '后台操作权限类型';

alter table oj_practice_set modify operation_auth_ids varchar(1024) null comment '拥有后台操作权限的用户id';

alter table oj_practice_set modify read_auth_type int not null comment '前台预览权限类型';

alter table oj_practice_set modify read_auth_ids varchar(1024) null comment '拥有前台预览权限的组织id';

alter table oj_practice_set modify practice_count int null comment '习题数量';

alter table oj_practice_set comment '实训习题集';


-- oj_practice_solved
alter table oj_practice_solved comment '实训习题答题记录';


-- oj_practice_theory
alter table oj_practice_theory modify builtin int not null comment '是否为内置数据';

alter table oj_practice_theory modify category_id varchar(64) not null comment '关联方向id';

alter table oj_practice_theory modify title varchar(2048) not null comment '标题（暂未使用）';

alter table oj_practice_theory modify content varchar(960) not null comment '内容';

alter table oj_practice_theory modify answer varchar(2048) not null comment '答案';

alter table oj_practice_theory modify difficulty int not null comment '难度';

alter table oj_practice_theory modify knowledge varchar(1024) null comment '知识点';

alter table oj_practice_theory modify public int not null comment '是否公开';

alter table oj_practice_theory modify score int not null comment '得分';

alter table oj_practice_theory modify create_user_id varchar(64) not null comment '创建用户id';

alter table oj_practice_theory modify theory_type int not null comment '理论基础题型';

alter table oj_practice_theory modify `option` longtext charset utf8mb4 null comment '选择题选项';

alter table oj_practice_theory modify blank varchar(2048) null comment '填空题填空内容';

alter table oj_practice_theory modify keyword varchar(1024) null comment '简答题参考关键字';

alter table oj_practice_theory modify practice_set_id varchar(64) null comment '关联习题集id';

alter table oj_practice_theory comment '实训理论基础';


-- oj_practice_theory_ref
alter table oj_practice_theory_ref modify snapshot longtext not null comment '理论基础基础信息快照';

alter table oj_practice_theory_ref modify origin_id varchar(64) not null comment '原理论基础id';

alter table oj_practice_theory_ref modify option_random int(1) null comment '选择题选项是否打乱顺序';

alter table oj_practice_theory_ref modify paper_id varchar(64) not null comment '关联试卷id';

alter table oj_practice_theory_ref comment '实训理论基础引用表';


-- oj_resource_collection
alter table oj_resource_collection modify user_id varchar(64) not null comment '收藏用户id';

alter table oj_resource_collection modify business_id varchar(64) not null comment '收藏业务（资源）id';

alter table oj_resource_collection modify business_type int not null comment '收藏业务类型';

alter table oj_resource_collection comment '实训资源收藏记录';


-- oj_train_plan
alter table oj_train_plan modify name varchar(128) null comment '计划名称';

alter table oj_train_plan modify create_user_id varchar(64) null comment '创建用户id';

alter table oj_train_plan comment '训练计划';

ALTER TABLE `cr_constants_config` COMMENT '常量配置',
    CHANGE `resource_id` `resource_id` VARCHAR(32) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT 'uuid',
    CHANGE `code` `code` SMALLINT(4) NULL COMMENT '状态码',
    CHANGE `type` `type` TINYINT(4) NOT NULL COMMENT '类别码',
    CHANGE `cn_name` `cn_name` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文名',
    CHANGE `en_name` `en_name` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '英文名';

ALTER TABLE `cr_business_template` COMMENT '训练模板',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `local_scene_id` `local_scene_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '后台测试场景id',
    CHANGE `topo_id` `topo_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联topo uuid',
    CHANGE `name` `name` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练模板名称',
    CHANGE `description` `description` TEXT CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述',
    CHANGE `business_type` `business_type` INT(11) NOT NULL COMMENT '所属训练类型',
    CHANGE `difficulty` `difficulty` INT(11) NOT NULL COMMENT '训练难度',
    CHANGE `update_time` `update_time` DATETIME NOT NULL COMMENT '更新时间',
    CHANGE `create_time` `create_time` DATETIME NOT NULL COMMENT '创建时间',
    CHANGE `create_user_id` `create_user_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人uuid',
    CHANGE `operation_auth_type` `operation_auth_type` INT(11) NULL COMMENT '权限状态',
    CHANGE `operation_auth_ids` `operation_auth_ids` TEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '权限列表',
    CHANGE `read_auth_type` `read_auth_type` INT(11) NULL COMMENT '浏览权限',
    CHANGE `read_auth_ids` `read_auth_ids` TEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '浏览权限列表',
    CHANGE `logo` `logo` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '封面url';

ALTER TABLE `cr_eval_template` COMMENT '测评模板',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `category_code` `category_code` INT(11) NOT NULL COMMENT '测评类型',
    CHANGE `step_config` `step_config` TEXT CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '测评步骤',
    CHANGE `enable_recording` `enable_recording` TINYINT(1) NULL COMMENT '是否录屏',
    CHANGE `current_task_id` `current_task_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '当前执行任务uuid',
    CHANGE `name` `name` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '测评名称',
    CHANGE `description` `description` TEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
    CHANGE `topo_id` `topo_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联拓扑uuid',
    CHANGE `logo` `logo` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '封面url',
    CHANGE `update_time` `update_time` DATETIME NOT NULL COMMENT '更新时间',
    CHANGE `create_time` `create_time` DATETIME NOT NULL COMMENT '创建时间',
    CHANGE `create_user_id` `create_user_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人uuid';

ALTER TABLE `cr_eval_aggregation` COMMENT '测评指标项',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间';

ALTER TABLE `cr_eval_algorithm` COMMENT '测评算法',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `algorithm_language` `algorithm_language` INT(11) NULL COMMENT '算法语言\r\n0：python',
    CHANGE `code` `code` TEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '算法内容',
    CHANGE `params` `params` VARCHAR(2000) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '参数列表',
    CHANGE `params_num` `params_num` INT(11) NULL COMMENT '参数个数',
    CHANGE `create_user_id` `create_user_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '创建人uuid',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间';

ALTER TABLE `cr_eval_algorithm_ref` COMMENT '算法关联',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `name` `name` VARCHAR(256) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
    CHANGE `description` `description` VARCHAR(2048) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
    CHANGE `type` `type` INT(11) NOT NULL COMMENT '格式化算法：1，匹配算法：2，评分算法：3，聚合算法：4',
    CHANGE `algorithm_language` `algorithm_language` INT(11) NOT NULL COMMENT '算法语言',
    CHANGE `code` `code` TEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '算法内容',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间';

ALTER TABLE `cr_eval_collection` COMMENT '指标项关联',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间';

ALTER TABLE `cr_eval_index` COMMENT '算法体系',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间';

ALTER TABLE `cr_eval_index_ref` COMMENT '算法体系关联',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `name` `name` VARCHAR(256) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
    CHANGE `type` `type` INT(11) NOT NULL COMMENT '归一化类型, 百分比:1,周期:2,延时:3',
    CHANGE `score` `score` INT(11) NULL COMMENT '得分',
    CHANGE `level` `level` INT(11) NOT NULL COMMENT '指标级别：1、2、3',
    CHANGE `record_id` `record_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '测评记录uuid',
    CHANGE `algorithm_id` `algorithm_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '聚合算法id',
    CHANGE `next_ids` `next_ids` VARCHAR(512) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '子节点关联id',
    CHANGE `start_time` `start_time` DATETIME NULL COMMENT '开始时间',
    CHANGE `end_time` `end_time` DATETIME NULL COMMENT '结束时间',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间',
    CHANGE `algorithm_params` `algorithm_params` LONGTEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '聚合算法参数',
    CHANGE `specialist_value` `specialist_value` INT(11) NULL COMMENT '专有参数',
    CHANGE `specialist_type` `specialist_type` INT(11) NULL COMMENT '专有参数类型',
    CHANGE `specialist_attr` `specialist_attr` INT(11) NULL COMMENT '专有属性类型',
    CHANGE `specialist_attr_value` `specialist_attr_value` INT(11) NULL COMMENT '专有属性值',
    CHANGE `progress` `progress` INT(11) NULL COMMENT '当前进程';

ALTER TABLE `cr_eval_indicator` COMMENT '指标项详情',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `attribute` `attribute` INT(11) NULL COMMENT '指标项属性类型',
    CHANGE `attribute_value` `attribute_value` INT(11) NULL COMMENT '属性值',
    CHANGE `datatype` `datatype` INT(11) NULL COMMENT '数据类型',
    CHANGE `create_user_id` `create_user_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '创建人uuid',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间';

ALTER TABLE `cr_eval_probe_data` COMMENT '探针数据',
    CHANGE `scene_id` `scene_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '场景uuid',
    CHANGE `device_id` `device_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '虚拟机uuid',
    CHANGE `probe_id` `probe_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '探针id',
    CHANGE `content` `content` TEXT CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '虚拟机信息',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间',
    CHANGE `collection_time` `collection_time` DATETIME NULL COMMENT '收集时间',
    CHANGE `device_ip` `device_ip` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '虚拟机ip';

ALTER TABLE `cr_eval_record` COMMENT '测评记录',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `name` `name` VARCHAR(256) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '测评名称',
    CHANGE `description` `description` VARCHAR(2048) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
    CHANGE `status` `status` INT(11) NOT NULL COMMENT '状态',
    CHANGE `score` `score` INT(11) NULL COMMENT '分数',
    CHANGE `start_time` `start_time` DATETIME NULL COMMENT '开始时间',
    CHANGE `end_time` `end_time` DATETIME NULL COMMENT '结束时间',
    CHANGE `system_id` `system_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '测评体系id',
    CHANGE `task_id` `task_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '测评任务id',
    CHANGE `message` `message` LONGTEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '测评消息',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间',
    CHANGE `eval_time` `eval_time` DATETIME NULL COMMENT '测评阶段开始时间',
    CHANGE `eval_end_time` `eval_end_time` DATETIME NULL COMMENT '测评阶段结束时间';

ALTER TABLE `cr_eval_system` COMMENT '测评指标体系详情',
    CHANGE `create_user_id` `create_user_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '创建人uuid',
    CHANGE `first_index_num` `first_index_num` INT(11) NULL COMMENT '一级指标数',
    CHANGE `second_index_num` `second_index_num` INT(11) NULL COMMENT '二级指标数',
    CHANGE `third_index_num` `third_index_num` INT(11) NULL COMMENT '三级指标数',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间';

ALTER TABLE `cr_eval_task` COMMENT '测评任务详情',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `template_id` `template_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '测评模板id',
    CHANGE `template_name` `template_name` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模板名称',
    CHANGE `task_name` `task_name` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '测评实例名称',
    CHANGE `local_scene_id` `local_scene_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '启动场景id',
    CHANGE `mp_scene_id` `mp_scene_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中台场景id',
    CHANGE `copyed_topo_id` `copyed_topo_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '拓扑id',
    CHANGE `start_time` `start_time` DATETIME NOT NULL COMMENT '开始时间',
    CHANGE `end_time` `end_time` DATETIME NULL COMMENT '结束时间',
    CHANGE `enable_recording` `enable_recording` TINYINT(1) NOT NULL COMMENT '是否录屏',
    CHANGE `create_user_id` `create_user_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人id',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '开始时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '结束时间';

ALTER TABLE `cr_eval_task_step` COMMENT '测评任务步骤详情',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `task_id` `task_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '测评任务id',
    CHANGE `stage` `stage` TINYINT(4) NOT NULL COMMENT '任务步骤',
    CHANGE `title` `title` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '步骤标题',
    CHANGE `process` `process` LONGTEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '步骤内容',
    CHANGE `type` `type` TINYINT(4) NOT NULL COMMENT '问题类型',
    CHANGE `start_time` `start_time` DATETIME NULL COMMENT '开始时间',
    CHANGE `end_time` `end_time` DATETIME NULL COMMENT '结束时间',
    CHANGE `create_time` `create_time` DATETIME NOT NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NOT NULL COMMENT '更新时间';

ALTER TABLE `cr_eval_variable_ref` COMMENT '评估详情',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `name` `name` VARCHAR(256) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '算法名称',
    CHANGE `score` `score` INT(11) NULL COMMENT '分数',
    CHANGE `device_id` `device_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '虚拟机id',
    CHANGE `probe_id` `probe_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '探针id',
    CHANGE `record_id` `record_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '测评记录id',
    CHANGE `progress` `progress` INT(11) NULL COMMENT '进程数',
    CHANGE `format_start_time` `format_start_time` DATETIME NULL COMMENT '格式算法执行开始时间',
    CHANGE `format_end_time` `format_end_time` DATETIME NULL COMMENT '格式算法执行结束时间',
    CHANGE `match_start_time` `match_start_time` DATETIME NULL COMMENT '模型执行开始时间',
    CHANGE `match_end_time` `match_end_time` DATETIME NULL COMMENT '模型执行结束时间',
    CHANGE `mark_start_time` `mark_start_time` DATETIME NULL COMMENT '评分执行开始时间',
    CHANGE `mark_end_time` `mark_end_time` DATETIME NULL COMMENT '评分执行结束时间',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间',
    CHANGE `format_algorithm_id` `format_algorithm_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '格式化算法id',
    CHANGE `match_algorithm_id` `match_algorithm_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模型算法id',
    CHANGE `mark_algorithm_id` `mark_algorithm_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评分算法id',
    CHANGE `format_params` `format_params` LONGTEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '格式化算法参数',
    CHANGE `match_params` `match_params` LONGTEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '模型算法参数',
    CHANGE `mark_params` `mark_params` LONGTEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '评分算法参数';

ALTER TABLE `cr_eval_variable_ref_probe_data` COMMENT '评估结果数据',
    CHANGE `variable_id` `variable_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评估结果id',
    CHANGE `probe_data_id` `probe_data_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '探针数据id',
    CHANGE `formatted_value` `formatted_value` TEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '获取数据',
    CHANGE `status` `status` INT(11) NOT NULL COMMENT '数据状态',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间',
    CHANGE `time` `time` DATETIME NOT NULL COMMENT '数据获取时间';

ALTER TABLE `cr_mission` COMMENT '前台房间任务',
    CHANGE `resource_id` `resource_id` VARCHAR(32) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `create_user_id` `create_user_id` VARCHAR(32) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '创建人id',
    CHANGE `title` `title` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '任务标题',
    CHANGE `description` `description` TEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
    CHANGE `score` `score` SMALLINT(6) NULL COMMENT '分值',
    CHANGE `skill_points` `skill_points` VARCHAR(40)
    CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '技能点数',
    CHANGE `answer` `answer` VARCHAR(4096) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '答案',
    CHANGE `option` `option` VARCHAR(512) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '选择题选项',
    CHANGE `type` `type` TINYINT(4) NULL COMMENT '题目类型（选择、判断等）',
    CHANGE `category_id` `category_id` VARCHAR(32) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '题目分类',
    CHANGE `write_up` `write_up` TEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '题解',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间',
    CHANGE `show_writeup` `show_writeup` TINYINT(1) NULL COMMENT '题解是否开放',
    CHANGE `room_id` `room_id` VARCHAR(32) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '房间id',
    CHANGE `mission_template_id` `mission_template_id` VARCHAR(32) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '任务模板id';

ALTER TABLE `cr_mission_scheme_template` COMMENT '后台任务方案',
    CHANGE `resource_id` `resource_id` VARCHAR(32) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `create_user_id` `create_user_id` VARCHAR(32) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '创建人id',
    CHANGE `title` `title` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '方案标题',
    CHANGE `description` `description` VARCHAR(2048) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
    CHANGE `template_data` `template_data` TEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '任务阶段数据',
    CHANGE `template_config` `template_config` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '任务模板配置',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间';

ALTER TABLE `cr_mission_template` COMMENT '后台任务模板',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT 'uuid',
    CHANGE `title` `title` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '任务标题',
    CHANGE `description` `description` TEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
    CHANGE `score` `score` SMALLINT(6) NULL COMMENT '分值',
    CHANGE `skill_points` `skill_points` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '技能点数',
    CHANGE `answer` `answer` VARCHAR(4096) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '答案',
    CHANGE `option` `option` VARCHAR(512) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '选择题选项',
    CHANGE `type` `type` TINYINT(4) NULL COMMENT '题目类型（选择、判断等）',
    CHANGE `category_id` `category_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '题目类别',
    CHANGE `write_up` `write_up` TEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '题解',
    CHANGE `create_user_id` `create_user_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '创建人id',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间',
    CHANGE `template_id` `template_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '暂未用到',
    CHANGE `attachment` `attachment` VARCHAR(4096) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '附件集合',
    CHANGE `business_template_id` `business_template_id` VARCHAR(32) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '训练模板id';

ALTER TABLE `cr_rb_device` COMMENT '红蓝对抗用户-登录节点关联',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT 'uuid',
    CHANGE `room_id` `room_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '房间id',
    CHANGE `device_id` `device_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '节点id',
    CHANGE `create_user_id` `create_user_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '用户id',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '结束时间';

ALTER TABLE `cr_rb_room` COMMENT '红蓝对抗房间详情',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `template_id` `template_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '红蓝对抗模板id',
    CHANGE `template_name` `template_name` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '对抗模板名称',
    CHANGE `name` `name` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '房间名',
    CHANGE `description` `description` VARCHAR(2048) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述',
    CHANGE `sub_type` `sub_type` INT(11) NOT NULL COMMENT '训练类别',
    CHANGE `pre_skill` `pre_skill` TEXT CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '前置技能',
    CHANGE `train_skill` `train_skill` TEXT CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练目标',
    CHANGE `start_time` `start_time` DATETIME NOT NULL COMMENT '开始时间',
    CHANGE `end_time` `end_time` DATETIME NOT NULL COMMENT '结束时间',
    CHANGE `difficulty` `difficulty` INT(11) NOT NULL COMMENT '难度',
    CHANGE `training_status` `training_status` INT(11) NULL COMMENT '对抗状态',
    CHANGE `scene_file` `scene_file` TEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '场景文件',
    CHANGE `create_user_id` `create_user_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '创建人id',
    CHANGE `modify_user_id` `modify_user_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '更新人id',
    CHANGE `local_scene_id` `local_scene_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '启动场景id',
    CHANGE `copyed_topo_id` `copyed_topo_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '拓扑id',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间',
    CHANGE `enable_recording` `enable_recording` TINYINT(1) NULL COMMENT '是否录屏',
    CHANGE `logo` `logo` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '封面url';

ALTER TABLE `cr_rb_template` COMMENT '红蓝对抗模板详情',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `business_template_id` `business_template_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练模板id',
    CHANGE `pre_skill` `pre_skill` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '前置技能',
    CHANGE `train_skill` `train_skill` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练目标',
    CHANGE `role_config` `role_config` TEXT CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色节点和任务配置',
    CHANGE `sub_type` `sub_type` TINYINT(4) NOT NULL COMMENT '训练类别',
    CHANGE `duration_time` `duration_time` INT(11) NOT NULL COMMENT '训练时长',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间';

ALTER TABLE `cr_rb_time_management`  COMMENT '红蓝对抗作战时间管理',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `room_id` `room_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对抗房间id',
    CHANGE `astronomical_time` `astronomical_time` DATETIME NOT NULL COMMENT '天文时间',
    CHANGE `combat_time` `combat_time` DATETIME NOT NULL COMMENT '作战时间',
    CHANGE `rate` `rate` SMALLINT(5) UNSIGNED NOT NULL COMMENT '时间比率',
    CHANGE `status` `status` SMALLINT(5) UNSIGNED NOT NULL COMMENT '对抗赛状态',
    CHANGE `create_user_id` `create_user_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '创建人id',
    CHANGE `create_time` `create_time` DATETIME(6) NOT NULL COMMENT '创建时间',
    CHANGE `update_user_id` `update_user_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '更新人id',
    CHANGE `update_time` `update_time` DATETIME(6) NOT NULL COMMENT '更新时间';

ALTER TABLE `cr_role` COMMENT '训练角色详情',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `name` `name` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
    CHANGE `room_id` `room_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练房间id',
    CHANGE `description` `description` VARCHAR(2048) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
    CHANGE `role_group` `role_group` INT(11) NOT NULL COMMENT '权限组',
    CHANGE `nodes` `nodes` VARCHAR(4000) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '角色节点',
    CHANGE `user_ids` `user_ids` TEXT CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色用户',
    CHANGE `exercise_paper` `exercise_paper` VARCHAR(4000) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '训练手册',
    CHANGE `parent_resource_id` `parent_resource_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '父角色id',
    CHANGE `mission_scheme` `mission_scheme` VARCHAR(4000) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '任务数据',
    CHANGE `business_type` `business_type` INT(11) NOT NULL COMMENT '训练类型',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间';

ALTER TABLE `cr_score` COMMENT '训练评分',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `room_id` `room_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '房间id',
    CHANGE `user_id` `user_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
    CHANGE `training_score` `training_score` VARCHAR(4000) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '训练得分',
    CHANGE `training_percentage` `training_percentage` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练分占比',
    CHANGE `skill_percentage` `skill_percentage` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能分占比',
    CHANGE `skill_score` `skill_score` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能分',
    CHANGE `comment` `comment` LONGTEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '评价',
    CHANGE `final_score` `final_score` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最终得分',
    CHANGE `create_time` `create_time` DATETIME NOT NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NOT NULL COMMENT '更新时间';

ALTER TABLE `cr_single_room` COMMENT '单兵训练房间详情',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `business_template_id` `business_template_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练模板id',
    CHANGE `start_time` `start_time` DATETIME NULL COMMENT '开始时间',
    CHANGE `end_time` `end_time` DATETIME NULL COMMENT '结束时间',
    CHANGE `training_record` `training_record` LONGTEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '训练记录',
    CHANGE `user_id` `user_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参与用户id',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '结束时间',
    CHANGE `local_scene_id` `local_scene_id` CHAR(32) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '本地场景id',
    CHANGE `is_finish` `is_finish` TINYINT(1) NOT NULL COMMENT '结束状态';

ALTER TABLE `cr_single_template` COMMENT '单兵训模板详情',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `business_template_id` `business_template_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练模板id',
    CHANGE `pre_skills` `pre_skills` VARCHAR(256) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '前置技能',
    CHANGE `train_skills` `train_skills` VARCHAR(256) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练目标',
    CHANGE `sub_type` `sub_type` TINYINT(4) NOT NULL COMMENT '训练类别',
    CHANGE `duration_time` `duration_time` INT(10) UNSIGNED NOT NULL COMMENT '训练时长',
    CHANGE `missions_scheme` `missions_scheme` LONGTEXT CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务配置',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间';

ALTER TABLE `cr_task` COMMENT '下发任务详情',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT 'uuid',
    CHANGE `status` `status` TINYINT(4) NULL COMMENT '执行状态',
    CHANGE `mission_id` `mission_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '任务id',
    CHANGE `user_id` `user_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '训练用户id',
    CHANGE `user_answer` `user_answer` LONGTEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '用户答案',
    CHANGE `release_time` `release_time` DATETIME NULL COMMENT '下发时间',
    CHANGE `last_submit_time` `last_submit_time` DATETIME NULL COMMENT '最后提交时间',
    CHANGE `task_start_time` `task_start_time` DATETIME NULL COMMENT '任务开始时间',
    CHANGE `room_id` `room_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '房间id',
    CHANGE `role_id` `role_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '角色id',
    CHANGE `business_type` `business_type` INT(11) NULL COMMENT '训练类型',
    CHANGE `score` `score` SMALLINT(6) NULL COMMENT '得分',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间',
    CHANGE `mission_template_id` `mission_template_id` VARCHAR(32) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '任务模板id';

ALTER TABLE `cr_task_log` COMMENT '任务动作记录',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `room_id` `room_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '训练房间id',
    CHANGE `mission_id` `mission_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '任务id',
    CHANGE `action_code` `action_code` TINYINT(4) NULL COMMENT '动作类别',
    CHANGE `user_id` `user_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '动作用户id',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间',
    CHANGE `task_id` `task_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '任务id';

ALTER TABLE `cr_task_submit` COMMENT '任务提交记录与评估',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `room_id` `room_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '训练房间id',
    CHANGE `task_id` `task_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '任务id',
    CHANGE `answer` `answer` LONGTEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '提交答案',
    CHANGE `answer_extension` `answer_extension` LONGTEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '提交答案附加信息',
    CHANGE `evaluator_id` `evaluator_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '评估人',
    CHANGE `evaluate_time` `evaluate_time` DATETIME NULL COMMENT '评估时间',
    CHANGE `evaluate_status` `evaluate_status` TINYINT(4) NULL COMMENT '评估状态',
    CHANGE `reason` `reason` TINYINT(4) NULL COMMENT '评估理由',
    CHANGE `detail` `detail` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '评估详情',
    CHANGE `score` `score` SMALLINT(6) NULL COMMENT '得分',
    CHANGE `mission_score` `mission_score` SMALLINT(6) NULL COMMENT '任务分值',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间';

ALTER TABLE `cr_team_room` COMMENT '团队训练房间详情',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `team_template_id` `team_template_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '团队模板id',
    CHANGE `team_template_name` `team_template_name` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '团队模板名称',
    CHANGE `name` `name` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '房间名',
    CHANGE `description` `description` VARCHAR(2048) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述',
    CHANGE `sub_type` `sub_type` INT(11) NOT NULL COMMENT '训练类别',
    CHANGE `pre_skills` `pre_skills` VARCHAR(4000) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '前置技能',
    CHANGE `train_skills` `train_skills` VARCHAR(4000) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练目标',
    CHANGE `start_time` `start_time` DATETIME NOT NULL COMMENT '开始时间',
    CHANGE `end_time` `end_time` DATETIME NOT NULL COMMENT '结束时间',
    CHANGE `difficulty` `difficulty` INT(11) NOT NULL COMMENT '难度',
    CHANGE `training_status` `training_status` INT(11) NULL COMMENT '训练状态',
    CHANGE `time_line_config` `time_line_config` VARCHAR(4000) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练时间线信息',
    CHANGE `create_user_id` `create_user_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '创建人id',
    CHANGE `modify_user_id` `modify_user_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '修改人id',
    CHANGE `local_scene_id` `local_scene_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '当前启动场景id',
    CHANGE `copyed_topo_id` `copyed_topo_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '拓扑id',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间',
    CHANGE `enable_recording` `enable_recording` TINYINT(1) NULL COMMENT '录屏状态',
    CHANGE `logo` `logo` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '封面url';

ALTER TABLE `cr_team_template` COMMENT '团队训练模板详情',
    CHANGE `resource_id` `resource_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
    CHANGE `business_template_id` `business_template_id` VARCHAR(40) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练模板id',
    CHANGE `pre_skills` `pre_skills` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '前置技能',
    CHANGE `train_skills` `train_skills` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练目标',
    CHANGE `role_config` `role_config` TEXT CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色信息配置',
    CHANGE `sub_type` `sub_type` TINYINT(4) NOT NULL COMMENT '训练类别',
    CHANGE `time_line_config` `time_line_config` VARCHAR(4000) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '时间线配置',
    CHANGE `people_number` `people_number` INT(10) NOT NULL COMMENT '训练参与人数',
    CHANGE `duration_time` `duration_time` INT(10) NOT NULL COMMENT '训练时长',
    CHANGE `create_time` `create_time` DATETIME NULL COMMENT '创建时间',
    CHANGE `update_time` `update_time` DATETIME NULL COMMENT '更新时间';

ALTER TABLE `cr_train_record` COMMENT '训练历史',
    CHANGE `business_type` `business_type` INT(10) UNSIGNED NOT NULL COMMENT '训练类型',
    CHANGE `business_template_id` `business_template_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练模板id',
    CHANGE `room_name` `room_name` VARCHAR(255) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '房间名',
    CHANGE `room_id` `room_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '房间id',
    CHANGE `user_id` `user_id` VARCHAR(64) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参与人id',
    CHANGE `start_time` `start_time` DATETIME NULL COMMENT '开始时间',
    CHANGE `end_time` `end_time` DATETIME NULL COMMENT '结束时间',
    CHANGE `extra` `extra` LONGTEXT CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '附加信息';

ALTER TABLE `cr_radar_label`
    MODIFY COLUMN `label` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '维度' AFTER `resource_id`,
    COMMENT = '雷达图维度表';


ALTER TABLE  `attachment`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `file_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名称' AFTER `resource_id`,
    MODIFY COLUMN `file_path` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件路径' AFTER `file_name`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `file_path`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    MODIFY COLUMN `public` int NOT NULL COMMENT '是否公开' AFTER `update_time`,
    MODIFY COLUMN `business_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务id' AFTER `public`,
    COMMENT = '附件表';


ALTER TABLE `init_rtks_course_related`
    MODIFY COLUMN `type` int NOT NULL COMMENT '类型：1：知识点;2：技能点（已取消）' AFTER `related_id`,
    COMMENT = 'rtks知识点课程关联重置表';

ALTER TABLE `init_rtks_knowledge`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `category_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别id' AFTER `resource_id`,
    MODIFY COLUMN `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容' AFTER `category_id`,
    MODIFY COLUMN `courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联课程列表' AFTER `content`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `courses`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks知识点重置表';


ALTER TABLE `init_rtks_knowledge_category`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容' AFTER `resource_id`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `content`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks知识点类别重置表';



ALTER TABLE `init_rtks_level`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称' AFTER `resource_id`,
    MODIFY COLUMN `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述' AFTER `name`,
    MODIFY COLUMN `parent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级id' AFTER `description`,
    MODIFY COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `parent`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    MODIFY COLUMN `frame` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '框架id' AFTER `update_time`,
    COMMENT = 'rtks框架层级重置表';



ALTER TABLE `init_rtks_role`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称' AFTER `resource_id`,
    MODIFY COLUMN `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述' AFTER `name`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `description`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    MODIFY COLUMN `level_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '层级id' AFTER `update_time`,
    MODIFY COLUMN `category` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别' AFTER `level_id`,
    MODIFY COLUMN `task_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务列表' AFTER `category`,
    MODIFY COLUMN `knowledge_id_weights` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点权重列表' AFTER `task_ids`,
    MODIFY COLUMN `knowledge_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点列表' AFTER `knowledge_id_weights`,
    MODIFY COLUMN `skill_id_weights` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能点权重列表' AFTER `knowledge_ids`,
    MODIFY COLUMN `skill_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能点列表' AFTER `skill_id_weights`,
    MODIFY COLUMN `ability_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '能力列表' AFTER `skill_ids`,
    MODIFY COLUMN `skill_courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '技能课程列表' AFTER `ability_ids`,
    MODIFY COLUMN `knowledge_courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '知识点课程列表' AFTER `skill_courses`,
    MODIFY COLUMN `knowledge_weight` tinyint NOT NULL DEFAULT 50 COMMENT '知识点比例' AFTER `knowledge_courses`,
    MODIFY COLUMN `skill_missions` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '[]' COMMENT '技能任务列表' AFTER `knowledge_weight`,
    COMMENT = 'rtks角色重置表';



ALTER TABLE `init_rtks_task`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容' AFTER `resource_id`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `content`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks任务重置表';


ALTER TABLE  `local_scene`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `mp_scene_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中台场景id' AFTER `resource_id`,
    MODIFY COLUMN `business_code` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务码' AFTER `mp_scene_id`,
    MODIFY COLUMN `start_time` datetime NULL DEFAULT NULL COMMENT '场景启动时间' AFTER `business_code`,
    MODIFY COLUMN `end_time` datetime NULL DEFAULT NULL COMMENT '场景停止时间' AFTER `start_time`,
    MODIFY COLUMN `destroy_time` datetime NULL DEFAULT NULL COMMENT '自动调用销毁时间' AFTER `end_time`,
    MODIFY COLUMN `destroy_mode` int NULL DEFAULT NULL COMMENT '销毁模式：0=手动；1=自动' AFTER `destroy_time`,
    MODIFY COLUMN `business_instance_id` varchar(65) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务id' AFTER `destroy_mode`,
    MODIFY COLUMN `scene_status` int NULL DEFAULT NULL COMMENT '场景状态' AFTER `business_instance_id`,
    MODIFY COLUMN `pause_time` datetime NULL DEFAULT NULL COMMENT '场景暂停时间' AFTER `scene_status`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `pause_time`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    MODIFY COLUMN `topo_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拓扑Id' AFTER `update_time`,
    MODIFY COLUMN `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id' AFTER `topo_id`,
    MODIFY COLUMN `scene_ttl` int NULL DEFAULT NULL COMMENT '场景存活时间' AFTER `user_id`,
    MODIFY COLUMN `node_count` int NULL DEFAULT NULL COMMENT '节点数量' AFTER `scene_ttl`,
    MODIFY COLUMN `meta` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'json格式数据' AFTER `node_count`,
    MODIFY COLUMN `scene_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场景名' AFTER `meta`,
    MODIFY COLUMN `scene_attr` int NULL DEFAULT NULL COMMENT '场景类型' AFTER `scene_name`,
    COMMENT = '本地场景表';

ALTER TABLE `rtks_ability`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容' AFTER `resource_id`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `content`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks能力表';

ALTER TABLE `rtks_attack`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `category_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别id' AFTER `resource_id`,
    MODIFY COLUMN `name_zh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文内容' AFTER `category_id`,
    MODIFY COLUMN `name_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '英文内容' AFTER `name_zh`,
    COMMENT = 'att&ck框架表';

ALTER TABLE `rtks_attack_category`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `name_zh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文内容' AFTER `resource_id`,
    MODIFY COLUMN `name_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '英文内容' AFTER `name_zh`,
    COMMENT = 'att&ck框架类别表';

ALTER TABLE `rtks_attack_train_relation`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `attack_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'attack框架id' AFTER `resource_id`,
    MODIFY COLUMN `business_template_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练模板id' AFTER `attack_id`,
    MODIFY COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `business_template_id`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'att&ck场景训练关联表';


ALTER TABLE `rtks_course_name`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称' AFTER `resource_id`,
    MODIFY COLUMN `description` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述' AFTER `name`,
    MODIFY COLUMN `difficulty` int NULL DEFAULT NULL COMMENT '难度' AFTER `description`,
    MODIFY COLUMN `lesson_count` int NULL DEFAULT NULL COMMENT '课时数量' AFTER `difficulty`,
    MODIFY COLUMN `e_book` int NULL DEFAULT NULL COMMENT '类型' AFTER `lesson_count`,
    MODIFY COLUMN `course_num` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程数量' AFTER `e_book`,
    MODIFY COLUMN `exercise_count` int NULL DEFAULT NULL COMMENT '实践课数量' AFTER `course_num`,
    MODIFY COLUMN `theory_count` int NULL DEFAULT NULL COMMENT '理论课数量' AFTER `exercise_count`,
    MODIFY COLUMN `direction` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程方向' AFTER `theory_count`,
    MODIFY COLUMN `direction_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方向名称' AFTER `direction`,
    MODIFY COLUMN `interview` int NULL DEFAULT NULL COMMENT '访问权限' AFTER `direction_name`,
    COMMENT = 'rtks初始化课程数据表';


ALTER TABLE `rtks_course_related`
    MODIFY COLUMN `type` int NOT NULL COMMENT '类型：1：知识点;2：技能点（已取消）' AFTER `related_id`,
    COMMENT = 'rtks知识点课程关联表';


ALTER TABLE `rtks_engage`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `category_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别id' AFTER `resource_id`,
    MODIFY COLUMN `sub_category_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '子类别id' AFTER `category_id`,
    MODIFY COLUMN `name_zh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文名称' AFTER `sub_category_id`,
    MODIFY COLUMN `name_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '英文名称' AFTER `name_zh`,
    COMMENT = 'engage框架表';

ALTER TABLE `rtks_engage_category`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `name_zh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文名称' AFTER `resource_id`,
    MODIFY COLUMN `name_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '英文名称' AFTER `name_zh`,
    COMMENT = 'engage框架类别表';


ALTER TABLE `rtks_engage_sub_category`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `category_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别id' AFTER `resource_id`,
    MODIFY COLUMN `name_zh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文名称' AFTER `category_id`,
    MODIFY COLUMN `name_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '英文名称' AFTER `name_zh`,
    COMMENT = 'engage框架子类别表';


ALTER TABLE `rtks_engage_train_relation`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `engage_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'engage框架id' AFTER `resource_id`,
    MODIFY COLUMN `business_template_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练模板id' AFTER `engage_id`,
    MODIFY COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `business_template_id`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'engage场景训练关联表';


ALTER TABLE `rtks_knowledge`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `category_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别id' AFTER `resource_id`,
    MODIFY COLUMN `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容' AFTER `category_id`,
    MODIFY COLUMN `courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联课程列表' AFTER `content`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `courses`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks知识点表';


ALTER TABLE `rtks_knowledge_category`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容' AFTER `resource_id`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `content`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks知识点类别表';



ALTER TABLE `rtks_level`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称' AFTER `resource_id`,
    MODIFY COLUMN `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述' AFTER `name`,
    MODIFY COLUMN `parent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级id' AFTER `description`,
    MODIFY COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `parent`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    MODIFY COLUMN `frame` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '框架id' AFTER `update_time`,
    COMMENT = 'rtks框架层级表';



ALTER TABLE `rtks_role`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称' AFTER `resource_id`,
    MODIFY COLUMN `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述' AFTER `name`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `description`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    MODIFY COLUMN `level_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '层级id' AFTER `update_time`,
    MODIFY COLUMN `category` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别' AFTER `level_id`,
    MODIFY COLUMN `task_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务列表' AFTER `category`,
    MODIFY COLUMN `knowledge_id_weights` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点权重列表' AFTER `task_ids`,
    MODIFY COLUMN `knowledge_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点列表' AFTER `knowledge_id_weights`,
    MODIFY COLUMN `skill_id_weights` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能点权重列表' AFTER `knowledge_ids`,
    MODIFY COLUMN `skill_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能点列表' AFTER `skill_id_weights`,
    MODIFY COLUMN `ability_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '能力列表' AFTER `skill_ids`,
    MODIFY COLUMN `skill_courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '技能课程列表' AFTER `ability_ids`,
    MODIFY COLUMN `knowledge_courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '知识点课程列表' AFTER `skill_courses`,
    MODIFY COLUMN `knowledge_weight` tinyint NOT NULL DEFAULT 50 COMMENT '知识点比例' AFTER `knowledge_courses`,
    MODIFY COLUMN `skill_missions` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '[]' COMMENT '技能任务列表' AFTER `knowledge_weight`,
    COMMENT = 'rtks角色表';



ALTER TABLE `rtks_role_match_record`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id' AFTER `id`,
    MODIFY COLUMN `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id' AFTER `user_id`,
    MODIFY COLUMN `match` tinyint NOT NULL COMMENT '匹配度' AFTER `role_id`,
    MODIFY COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `match`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks角色匹配度表';



ALTER TABLE `rtks_role_skill_relation`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id' AFTER `id`,
    MODIFY COLUMN `skill_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能id' AFTER `role_id`,
    MODIFY COLUMN `weight` tinyint NOT NULL COMMENT '权重' AFTER `skill_id`,
    MODIFY COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `weight`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks角色技能点关联表';



ALTER TABLE `rtks_situation_log`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id' AFTER `id`,
    MODIFY COLUMN `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名' AFTER `user_id`,
    MODIFY COLUMN `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id' AFTER `user_name`,
    MODIFY COLUMN `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名' AFTER `role_id`,
    MODIFY COLUMN `type` tinyint(1) NOT NULL COMMENT '类型：1.合格;2优秀' AFTER `role_name`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `type`,
    COMMENT = 'rtks角色态势动态表';



ALTER TABLE `rtks_skill`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `content` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称' AFTER `resource_id`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `content`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    MODIFY COLUMN `system_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '体系id' AFTER `update_time`,
    MODIFY COLUMN `category_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '类别id' AFTER `system_id`,
    MODIFY COLUMN `courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '[]' COMMENT '课程列表' AFTER `category_id`,
    COMMENT = 'rtks技能点表';


ALTER TABLE `rtks_skill_category`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容' AFTER `resource_id`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `content`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks技能点类别表';


ALTER TABLE `rtks_skill_mission_relation`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `skill_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能点id' AFTER `id`,
    MODIFY COLUMN `mission_template_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务模板id' AFTER `skill_id`,
    MODIFY COLUMN `weight` tinyint NOT NULL COMMENT '权重' AFTER `mission_template_id`,
    MODIFY COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `weight`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks技能点任务关联表';


ALTER TABLE `rtks_skill_system`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `scene_id` int NOT NULL COMMENT '场景id' AFTER `update_time`,
    MODIFY COLUMN `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容' AFTER `resource_id`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `content`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks技能点体系表';


ALTER TABLE `rtks_task`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容' AFTER `resource_id`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `content`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks任务表';


ALTER TABLE `rtks_user_role`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id' AFTER `id`,
    MODIFY COLUMN `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id' AFTER `user_id`,
    MODIFY COLUMN `role_name` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称' AFTER `role_id`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `role_name`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    MODIFY COLUMN `user_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名' AFTER `update_time`,
    COMMENT = 'rtks用户角色表';

ALTER TABLE `situation_attack_message`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `scene_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场景id' AFTER `resource_id`,
    MODIFY COLUMN `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '动效类型' AFTER `scene_id`,
    MODIFY COLUMN `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述' AFTER `type`,
    MODIFY COLUMN `source_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源节点id' AFTER `description`,
    MODIFY COLUMN `target_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标节点id' AFTER `source_id`,
    MODIFY COLUMN `interval_time` int NULL DEFAULT NULL COMMENT '间隔时间（s）' AFTER `target_id`,
    MODIFY COLUMN `duration_time` int NULL DEFAULT NULL COMMENT '持续时间（s）' AFTER `interval_time`,
    MODIFY COLUMN `look_at_target` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否聚焦' AFTER `duration_time`,
    MODIFY COLUMN `line_color` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '颜色' AFTER `look_at_target`,
    MODIFY COLUMN `line_width` int NULL DEFAULT NULL COMMENT '线宽' AFTER `line_color`,
    MODIFY COLUMN `line_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '线型' AFTER `line_width`,
    MODIFY COLUMN `media_animation` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '媒体动效列表' AFTER `line_type`,
    MODIFY COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `media_animation`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = '态势动效配置表';



ALTER TABLE `situation_config`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `scope` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '作用域' AFTER `id`,
    MODIFY COLUMN `node_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点id' AFTER `scope`,
    MODIFY COLUMN `node_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名称' AFTER `node_id`,
    MODIFY COLUMN `node_type` tinyint NOT NULL COMMENT '节点类型' AFTER `node_name`,
    MODIFY COLUMN `attribute` tinyint NOT NULL COMMENT '参数类型' AFTER `node_type`,
    MODIFY COLUMN `attribute_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数值' AFTER `attribute`,
    MODIFY COLUMN `resource_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `attribute_value`,
    COMMENT = '态势自定义属性表';


ALTER TABLE `situation_dynamic_scheme`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `scene_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场景id' AFTER `resource_id`,
    MODIFY COLUMN `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '动效方案名称' AFTER `scene_id`,
    MODIFY COLUMN `scheme_data` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '动效方案数据' AFTER `name`,
    MODIFY COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `scheme_data`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = '态势动效方案表';


ALTER TABLE `situation_scene_config`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `scene_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场景id' AFTER `resource_id`,
    MODIFY COLUMN `flow_analysis` tinyint(1) NULL DEFAULT NULL COMMENT '是否分析流量采集数据' AFTER `scene_id`,
    MODIFY COLUMN `flow_auto_run` tinyint(1) NULL DEFAULT NULL COMMENT '是否自动运行仿真流量' AFTER `flow_analysis`,
    MODIFY COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `flow_auto_run`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = '态势场景配置表';

ALTER TABLE `system_use_status`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `cpu` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'cpu使用率' AFTER `id`,
    MODIFY COLUMN `disk` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '磁盘使用率' AFTER `cpu`,
    MODIFY COLUMN `ram` tinyint NOT NULL COMMENT '内存使用率' AFTER `disk`,
    MODIFY COLUMN `alter_time` datetime NULL DEFAULT NULL COMMENT '修改时间' AFTER `ram`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `alter_time`,
    COMMENT = '系统使用状态表';


UPDATE cr_constants_config t SET t.en_name = 'Q & A' WHERE t.code = 4 and t.type = 21;
UPDATE cr_constants_config t SET t.en_name = 'Completion' WHERE t.code = 3 and t.type = 21;

alter table oj_paper modify read_auth_ids longtext null comment '授权访问组织id';

INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 38, '未评估', 'No Assess');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 38, '评估中', 'Assessing');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 38, '已评估', 'Has Assessed');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 4, 38, '异常', 'Abnormal');
