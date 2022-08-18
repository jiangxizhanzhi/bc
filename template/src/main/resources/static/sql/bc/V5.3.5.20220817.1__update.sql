
-- oj_classroom
alter table oj_classroom modify start_time datetime not null comment '课堂开始时间';

alter table oj_classroom modify end_time datetime not null comment '课堂结束时间';

alter table oj_classroom modify course_id varchar(64) not null comment '关联课程id';

alter table oj_classroom modify lesson_id varchar(64) not null comment '关联课时id';

alter table oj_classroom modify organization_id varchar(64) not null comment '关联组织id';

alter table oj_classroom modify organization_ids varchar(1024) not null comment '关联组织下所有组织id';

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


-- oj_constants_config
alter table oj_constants_config modify code tinyint null comment '常量值';

alter table oj_constants_config modify type tinyint not null comment '常量类型';

alter table oj_constants_config modify cn_name varchar(255) not null comment '常量中文名称';

alter table oj_constants_config modify en_name varchar(255) not null comment '常量英文名称';

alter table oj_constants_config comment '实训模块常量数据';


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
