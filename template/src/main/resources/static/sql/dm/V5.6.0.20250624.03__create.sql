ALTER INDEX "INDEX211725449738900" VISIBLE;

CREATE INDEX "INDEX211725470545300"
ON "RTKS_COURSE_RELATED"("related_id");
ALTER INDEX "INDEX211725470545300" VISIBLE;

CREATE INDEX "INDEX211725481792300"
ON "RTKS_COURSE_RELATED"("resource_id");
ALTER INDEX "INDEX211725481792300" VISIBLE;

ALTER TABLE "INIT_RTKS_KNOWLEDGE" ADD UNIQUE("resource_id") ;

ALTER TABLE "INIT_RTKS_KNOWLEDGE_CATEGORY" ADD UNIQUE("resource_id") ;

ALTER TABLE "INIT_RTKS_LEVEL" ADD UNIQUE("resource_id") ;

ALTER TABLE "INIT_RTKS_ROLE" ADD UNIQUE("resource_id") ;

ALTER TABLE "INIT_RTKS_TASK" ADD UNIQUE("resource_id") ;

ALTER TABLE "LOCAL_SCENE" ADD CONSTRAINT "businessInstanceUniq" UNIQUE("business_instance_id") ;

ALTER TABLE "LOCAL_SCENE" ADD CONSTRAINT "resourceIdUnique" UNIQUE("resource_id") ;

ALTER TABLE "OJ_CLASSROOM" ADD CONSTRAINT "resource_uniq" UNIQUE("resource_id") ;

ALTER TABLE "OJ_CLASSROOM_GROUP_ARCHIVE" ADD CONSTRAINT "room_name_uniq" UNIQUE("classroom_id","name") ;

ALTER TABLE "OJ_CLASSROOM_GROUP_INFO" ADD CONSTRAINT "room_group_uniq" UNIQUE("classroom_id","group_no") ;

ALTER TABLE "OJ_CLASSROOM_GROUP_INFO" ADD UNIQUE("resource_id") ;

ALTER TABLE "OJ_CLASSROOM_SIGN" ADD CONSTRAINT "room_user_uniq" UNIQUE("classroom_id","user_id") ;

ALTER TABLE "OJ_COMMENT_LIKE" ADD CONSTRAINT "user_comment_uniq" UNIQUE("user_id","comment_id") ;

ALTER TABLE "OJ_COURSE" ADD CONSTRAINT "name_uniq" UNIQUE("name") ;

ALTER TABLE "OJ_COURSE" ADD CONSTRAINT "resource_unq" UNIQUE("resource_id") ;

ALTER TABLE "OJ_COURSE_CATEGORY" ADD CONSTRAINT "name" UNIQUE("name") ;

ALTER TABLE "OJ_COURSE_LESSON" ADD CONSTRAINT "course_name_uniq" UNIQUE("course_id","name") ;

ALTER TABLE "OJ_COURSE_LESSON" ADD CONSTRAINT "resourse_id_uniq" UNIQUE("resource_id") ;

ALTER TABLE "OJ_COURSE_STUDY_HISTORY" ADD CONSTRAINT "course_user_uniq" UNIQUE("course_id","user_id") ;

ALTER TABLE "OJ_LESSON_PAPER_EVALUATE" ADD CONSTRAINT "lesson_user_uniq" UNIQUE("lesson_id","submit_user_id") ;

ALTER TABLE "OJ_LESSON_RECORD" ADD CONSTRAINT "user_lesson_uniq" UNIQUE("user_id","lesson_id") ;

ALTER TABLE "OJ_NOTE" ADD CONSTRAINT "user_bt_bi_uniq" UNIQUE("user_id","note_type","business_type","business_id") ;

ALTER TABLE "OJ_NOTE" ADD CONSTRAINT "unique_index" UNIQUE("resource_id") ;

ALTER TABLE "OJ_NOTE_EVALUATE" ADD CONSTRAINT "note_id_uniq" UNIQUE("note_id") ;

ALTER TABLE "OJ_PAPER" ADD CONSTRAINT "resource_id_uniq" UNIQUE("resource_id") ;

ALTER TABLE "OJ_PRACTICE_CATEGORY" ADD CONSTRAINT "type_name_uniq" UNIQUE("practice_type","name") ;

ALTER TABLE "OJ_PRACTICE_CATEGORY" ADD UNIQUE("resource_id") ;

ALTER TABLE "OJ_PRACTICE_OPERATION" ADD CONSTRAINT "set_title_uniq" UNIQUE("practice_set_id","title") ;

ALTER TABLE "OJ_PRACTICE_OPERATION" ADD UNIQUE("resource_id") ;

ALTER TABLE "OJ_PRACTICE_OPERATION_REF" ADD CONSTRAINT "paper_origin_uniq" UNIQUE("paper_id","origin_id") ;

ALTER TABLE "OJ_PRACTICE_RANDOM_FLAG" ADD CONSTRAINT "prac_type_user_uniq" UNIQUE("practice_id","type","user_id") ;

ALTER TABLE "OJ_PRACTICE_SET" ADD UNIQUE("name") ;

ALTER TABLE "OJ_PRACTICE_SET" ADD CONSTRAINT "resource_idx" UNIQUE("resource_id") ;

ALTER TABLE "OJ_PRACTICE_SOLVED" ADD CONSTRAINT "user_type_practice_uniq" UNIQUE("submit_user_id","practice_type","practice_id") ;

ALTER TABLE "OJ_PRACTICE_THEORY" ADD CONSTRAINT "set_content_uniq" UNIQUE("practice_set_id","content") ;

ALTER TABLE "OJ_PRACTICE_THEORY" ADD UNIQUE("resource_id") ;

ALTER TABLE "OJ_PRACTICE_THEORY_REF" ADD UNIQUE("paper_id","origin_id") ;

ALTER TABLE "OJ_RESOURCE_COLLECTION" ADD CONSTRAINT "user_type_id_uniq" UNIQUE("user_id","business_type","business_id") ;

ALTER TABLE "OJ_TRAIN_PLAN" ADD CONSTRAINT "user_name_uniq" UNIQUE("create_user_id","name") ;

ALTER TABLE "OJ_TRAIN_PLAN" ADD UNIQUE("resource_id") ;

ALTER TABLE "RTKS_ABILITY" ADD UNIQUE("resource_id") ;

ALTER TABLE "RTKS_COURSE_NAME" ADD UNIQUE("resource_id") ;

COMMENT ON TABLE "INIT_RTKS_COURSE_RELATED" IS 'rtks知识点课程关联重置表';

COMMENT ON COLUMN "INIT_RTKS_COURSE_RELATED"."related_id" IS '知识点id';

COMMENT ON COLUMN "INIT_RTKS_COURSE_RELATED"."resource_id" IS '课程id';

COMMENT ON COLUMN "INIT_RTKS_COURSE_RELATED"."type" IS '类型：1：知识点;2：技能点（已取消）';

COMMENT ON COLUMN "INIT_RTKS_COURSE_RELATED"."weight" IS '权重';

COMMENT ON TABLE "INIT_RTKS_KNOWLEDGE" IS 'rtks知识点重置表';

COMMENT ON COLUMN "INIT_RTKS_KNOWLEDGE"."category_id" IS '类别id';

COMMENT ON COLUMN "INIT_RTKS_KNOWLEDGE"."content" IS '内容';

COMMENT ON COLUMN "INIT_RTKS_KNOWLEDGE"."courses" IS '关联课程列表';

COMMENT ON COLUMN "INIT_RTKS_KNOWLEDGE"."create_time" IS '创建时间';

COMMENT ON COLUMN "INIT_RTKS_KNOWLEDGE"."id" IS '自增id';

COMMENT ON COLUMN "INIT_RTKS_KNOWLEDGE"."resource_id" IS '资源id';

COMMENT ON COLUMN "INIT_RTKS_KNOWLEDGE"."update_time" IS '更新时间';

COMMENT ON TABLE "INIT_RTKS_KNOWLEDGE_CATEGORY" IS 'rtks知识点类别重置表';

COMMENT ON COLUMN "INIT_RTKS_KNOWLEDGE_CATEGORY"."content" IS '内容';

COMMENT ON COLUMN "INIT_RTKS_KNOWLEDGE_CATEGORY"."create_time" IS '创建时间';

COMMENT ON COLUMN "INIT_RTKS_KNOWLEDGE_CATEGORY"."id" IS '自增id';

COMMENT ON COLUMN "INIT_RTKS_KNOWLEDGE_CATEGORY"."resource_id" IS '资源id';

COMMENT ON COLUMN "INIT_RTKS_KNOWLEDGE_CATEGORY"."update_time" IS '更新时间';

COMMENT ON TABLE "INIT_RTKS_LEVEL" IS 'rtks框架层级重置表';

COMMENT ON COLUMN "INIT_RTKS_LEVEL"."create_time" IS '创建时间';

COMMENT ON COLUMN "INIT_RTKS_LEVEL"."description" IS '描述';

COMMENT ON COLUMN "INIT_RTKS_LEVEL"."frame" IS '框架id';

COMMENT ON COLUMN "INIT_RTKS_LEVEL"."id" IS '自增id';

COMMENT ON COLUMN "INIT_RTKS_LEVEL"."name" IS '名称';

COMMENT ON COLUMN "INIT_RTKS_LEVEL"."parent" IS '父级id';

COMMENT ON COLUMN "INIT_RTKS_LEVEL"."resource_id" IS '资源id';

COMMENT ON COLUMN "INIT_RTKS_LEVEL"."update_time" IS '更新时间';

COMMENT ON TABLE "INIT_RTKS_ROLE" IS 'rtks角色重置表';

COMMENT ON COLUMN "INIT_RTKS_ROLE"."ability_ids" IS '能力列表';

COMMENT ON COLUMN "INIT_RTKS_ROLE"."category" IS '类别';

COMMENT ON COLUMN "INIT_RTKS_ROLE"."create_time" IS '创建时间';

COMMENT ON COLUMN "INIT_RTKS_ROLE"."description" IS '描述';

COMMENT ON COLUMN "INIT_RTKS_ROLE"."id" IS '自增id';

COMMENT ON COLUMN "INIT_RTKS_ROLE"."knowledge_courses" IS '知识点课程列表';

COMMENT ON COLUMN "INIT_RTKS_ROLE"."knowledge_id_weights" IS '知识点权重列表';

COMMENT ON COLUMN "INIT_RTKS_ROLE"."knowledge_ids" IS '知识点列表';

COMMENT ON COLUMN "INIT_RTKS_ROLE"."knowledge_weight" IS '知识点比例';

COMMENT ON COLUMN "INIT_RTKS_ROLE"."level_id" IS '层级id';

COMMENT ON COLUMN "INIT_RTKS_ROLE"."name" IS '名称';

COMMENT ON COLUMN "INIT_RTKS_ROLE"."resource_id" IS '资源id';

COMMENT ON COLUMN "INIT_RTKS_ROLE"."skill_courses" IS '技能课程列表';

COMMENT ON COLUMN "INIT_RTKS_ROLE"."skill_id_weights" IS '技能点权重列表';

COMMENT ON COLUMN "INIT_RTKS_ROLE"."skill_ids" IS '技能点列表';

COMMENT ON COLUMN "INIT_RTKS_ROLE"."skill_missions" IS '技能任务列表';

COMMENT ON COLUMN "INIT_RTKS_ROLE"."task_ids" IS '任务列表';

COMMENT ON COLUMN "INIT_RTKS_ROLE"."update_time" IS '更新时间';

COMMENT ON TABLE "INIT_RTKS_ROLE_SKILL_RELATION" IS '预置rtks角色技能点关联表';

COMMENT ON COLUMN "INIT_RTKS_ROLE_SKILL_RELATION"."create_time" IS '创建时间';

COMMENT ON COLUMN "INIT_RTKS_ROLE_SKILL_RELATION"."id" IS '自增id';

COMMENT ON COLUMN "INIT_RTKS_ROLE_SKILL_RELATION"."role_id" IS '角色id';

COMMENT ON COLUMN "INIT_RTKS_ROLE_SKILL_RELATION"."skill_id" IS '技能id';

COMMENT ON COLUMN "INIT_RTKS_ROLE_SKILL_RELATION"."update_time" IS '更新时间';

COMMENT ON COLUMN "INIT_RTKS_ROLE_SKILL_RELATION"."weight" IS '权重';

COMMENT ON TABLE "INIT_RTKS_TASK" IS 'rtks任务重置表';

COMMENT ON COLUMN "INIT_RTKS_TASK"."content" IS '内容';

COMMENT ON COLUMN "INIT_RTKS_TASK"."create_time" IS '创建时间';

COMMENT ON COLUMN "INIT_RTKS_TASK"."id" IS '自增id';

COMMENT ON COLUMN "INIT_RTKS_TASK"."resource_id" IS '资源id';

COMMENT ON COLUMN "INIT_RTKS_TASK"."update_time" IS '更新时间';

COMMENT ON TABLE "LOCAL_SCENE" IS '本地场景表';

COMMENT ON COLUMN "LOCAL_SCENE"."business_code" IS '业务码';

COMMENT ON COLUMN "LOCAL_SCENE"."business_instance_id" IS '业务id';

COMMENT ON COLUMN "LOCAL_SCENE"."create_time" IS '创建时间';

COMMENT ON COLUMN "LOCAL_SCENE"."destroy_mode" IS '销毁模式：0=手动；1=自动';

COMMENT ON COLUMN "LOCAL_SCENE"."destroy_time" IS '自动调用销毁时间';

COMMENT ON COLUMN "LOCAL_SCENE"."end_time" IS '场景停止时间';

COMMENT ON COLUMN "LOCAL_SCENE"."id" IS '自增id';

COMMENT ON COLUMN "LOCAL_SCENE"."meta" IS 'json格式数据';

COMMENT ON COLUMN "LOCAL_SCENE"."mp_scene_id" IS '中台场景id';

COMMENT ON COLUMN "LOCAL_SCENE"."node_count" IS '节点数量';

COMMENT ON COLUMN "LOCAL_SCENE"."pause_time" IS '场景暂停时间';

COMMENT ON COLUMN "LOCAL_SCENE"."resource_id" IS '资源id';

COMMENT ON COLUMN "LOCAL_SCENE"."scene_attr" IS '场景类型';

COMMENT ON COLUMN "LOCAL_SCENE"."scene_name" IS '场景名';

COMMENT ON COLUMN "LOCAL_SCENE"."scene_status" IS '场景状态';

COMMENT ON COLUMN "LOCAL_SCENE"."scene_ttl" IS '场景存活时间';

COMMENT ON COLUMN "LOCAL_SCENE"."start_time" IS '场景启动时间';

COMMENT ON COLUMN "LOCAL_SCENE"."topo_id" IS '拓扑Id';

COMMENT ON COLUMN "LOCAL_SCENE"."update_time" IS '更新时间';

COMMENT ON COLUMN "LOCAL_SCENE"."user_id" IS '用户id';

COMMENT ON TABLE "OJ_CLASSROOM" IS '实训课程表（课堂安排）';

COMMENT ON COLUMN "OJ_CLASSROOM"."course_id" IS '关联课程id';

COMMENT ON COLUMN "OJ_CLASSROOM"."create_user_id" IS '创建者（课堂教师）';

COMMENT ON COLUMN "OJ_CLASSROOM"."end_time" IS '课堂结束时间';

COMMENT ON COLUMN "OJ_CLASSROOM"."lesson_id" IS '关联课时id';

COMMENT ON COLUMN "OJ_CLASSROOM"."organization_id" IS '关联组织id';

COMMENT ON COLUMN "OJ_CLASSROOM"."organization_ids" IS '关联组织下所有组织id';

COMMENT ON COLUMN "OJ_CLASSROOM"."start_time" IS '课堂开始时间';

COMMENT ON COLUMN "OJ_CLASSROOM"."train_plan_id" IS '关联训练计划id';

COMMENT ON TABLE "OJ_CLASSROOM_GROUP_ARCHIVE" IS '实训课堂（实验课）分组信息快照';

COMMENT ON COLUMN "OJ_CLASSROOM_GROUP_ARCHIVE"."classroom_id" IS '所属课堂id';

COMMENT ON COLUMN "OJ_CLASSROOM_GROUP_ARCHIVE"."groups" IS '分组数据';

COMMENT ON COLUMN "OJ_CLASSROOM_GROUP_ARCHIVE"."name" IS '分组信息快照名称';

COMMENT ON TABLE "OJ_CLASSROOM_GROUP_INFO" IS '实训课堂（实验课）分组信息';

COMMENT ON COLUMN "OJ_CLASSROOM_GROUP_INFO"."classroom_id" IS '所属课堂id';

COMMENT ON COLUMN "OJ_CLASSROOM_GROUP_INFO"."destroy_delay" IS '实验环境销毁期限（单位:h）';

COMMENT ON COLUMN "OJ_CLASSROOM_GROUP_INFO"."group_user_ids" IS '组内用户id';

COMMENT ON COLUMN "OJ_CLASSROOM_GROUP_INFO"."lesson_id" IS '关联实验课id';

COMMENT ON COLUMN "OJ_CLASSROOM_GROUP_INFO"."scene_share_mode" IS '场景是否共享标识';

COMMENT ON COLUMN "OJ_CLASSROOM_GROUP_INFO"."topo_id" IS '实验课使用拓扑id';

COMMENT ON TABLE "OJ_CLASSROOM_SIGN" IS '实训课堂签到记录';

COMMENT ON COLUMN "OJ_CLASSROOM_SIGN"."classroom_id" IS '所属课堂id';

COMMENT ON COLUMN "OJ_CLASSROOM_SIGN"."need_help" IS '学员是否需要请求协助（实验课）';

COMMENT ON COLUMN "OJ_CLASSROOM_SIGN"."node_id" IS '场景中请求协助的节点id（实验课）';

COMMENT ON COLUMN "OJ_CLASSROOM_SIGN"."sign_in" IS '是否签到';

COMMENT ON COLUMN "OJ_CLASSROOM_SIGN"."sign_in_time" IS '签到时间';

COMMENT ON TABLE "OJ_COMMENT" IS '实训留言';

COMMENT ON COLUMN "OJ_COMMENT"."business_id" IS '留言所属业务（资源）id（课程、习题...）';

COMMENT ON COLUMN "OJ_COMMENT"."business_type" IS '留言所属模块类型（课程、习题...）';

COMMENT ON COLUMN "OJ_COMMENT"."content" IS '留言内容';

COMMENT ON COLUMN "OJ_COMMENT"."reply_id" IS '此留言所回复留言id';

COMMENT ON COLUMN "OJ_COMMENT"."root_id" IS '此留言所属楼层的层主留言id';

COMMENT ON COLUMN "OJ_COMMENT"."theme_name" IS '留言所属主题信息（课程名、习题名...）';

COMMENT ON COLUMN "OJ_COMMENT"."user_id" IS '发起留言用户id';

COMMENT ON TABLE "OJ_COMMENT_LIKE" IS '实训留言点赞';

COMMENT ON COLUMN "OJ_COMMENT_LIKE"."comment_id" IS '点赞评论id';

COMMENT ON COLUMN "OJ_COMMENT_LIKE"."user_id" IS '点赞用户id';

COMMENT ON TABLE "OJ_COURSE" IS '实训课程';

COMMENT ON COLUMN "OJ_COURSE"."builtin" IS '课程是否属于内置资源';

COMMENT ON COLUMN "OJ_COURSE"."category_id" IS '关联方向id';

COMMENT ON COLUMN "OJ_COURSE"."course_num" IS '课程编号';

COMMENT ON COLUMN "OJ_COURSE"."course_type" IS '课程类型';

COMMENT ON COLUMN "OJ_COURSE"."create_user_id" IS '创建者id';

COMMENT ON COLUMN "OJ_COURSE"."description" IS '课程描述';

COMMENT ON COLUMN "OJ_COURSE"."difficulty" IS '难度';

COMMENT ON COLUMN "OJ_COURSE"."heat" IS '热度';

COMMENT ON COLUMN "OJ_COURSE"."lecturer" IS '讲师';

COMMENT ON COLUMN "OJ_COURSE"."logo" IS '课程封面id';

COMMENT ON COLUMN "OJ_COURSE"."modify_user_id" IS '最后修改的用户id';

COMMENT ON COLUMN "OJ_COURSE"."name" IS '课程名称';

COMMENT ON COLUMN "OJ_COURSE"."operation_auth_ids" IS '拥有后台操作权限的用户id';

COMMENT ON COLUMN "OJ_COURSE"."operation_auth_type" IS '后台操作权限类型';

COMMENT ON COLUMN "OJ_COURSE"."outline" IS '课程大纲';

COMMENT ON COLUMN "OJ_COURSE"."public" IS '是否公开';

COMMENT ON COLUMN "OJ_COURSE"."read_auth_ids" IS '拥有前台预览权限的组织id';

COMMENT ON COLUMN "OJ_COURSE"."read_auth_type" IS '前台预览权限类型';

COMMENT ON COLUMN "OJ_COURSE"."score" IS '学分';

COMMENT ON TABLE "OJ_COURSE_CATEGORY" IS '实训课程方向/分类';

COMMENT ON COLUMN "OJ_COURSE_CATEGORY"."code" IS '节点编码  父级节点code + '','' + 当前节点编号';

COMMENT ON COLUMN "OJ_COURSE_CATEGORY"."course_type" IS '课程类型0=基础 2=攻击 3=防御';

COMMENT ON COLUMN "OJ_COURSE_CATEGORY"."name" IS '方向名称';

COMMENT ON COLUMN "OJ_COURSE_CATEGORY"."parent_id" IS '父级节点编号';

COMMENT ON TABLE "OJ_COURSE_LESSON" IS '实训课时';

COMMENT ON COLUMN "OJ_COURSE_LESSON"."attachment_ids" IS '关联附件id';

COMMENT ON COLUMN "OJ_COURSE_LESSON"."builtin" IS '是否内置';

COMMENT ON COLUMN "OJ_COURSE_LESSON"."compulsory" IS '是否必修';

COMMENT ON COLUMN "OJ_COURSE_LESSON"."course_id" IS '关联课程id';

COMMENT ON COLUMN "OJ_COURSE_LESSON"."create_user_id" IS '创建用户id';

COMMENT ON COLUMN "OJ_COURSE_LESSON"."destroy_delay" IS '场景销毁期限（实验课使用）';

COMMENT ON COLUMN "OJ_COURSE_LESSON"."difficulty" IS '难度';

COMMENT ON COLUMN "OJ_COURSE_LESSON"."duration" IS '建议时长（单位：m）';

COMMENT ON COLUMN "OJ_COURSE_LESSON"."handout_type" IS '讲义类型';

COMMENT ON COLUMN "OJ_COURSE_LESSON"."handout_url" IS '讲义文件地址';
