
COMMENT ON COLUMN "CR_MISSION"."category_id" IS '题目分类';

COMMENT ON COLUMN "CR_MISSION"."create_time" IS '创建时间';

COMMENT ON COLUMN "CR_MISSION"."create_user_id" IS '创建人id';

COMMENT ON COLUMN "CR_MISSION"."description" IS '描述';

COMMENT ON COLUMN "CR_MISSION"."mission_template_id" IS '任务模板id';

COMMENT ON COLUMN "CR_MISSION"."option" IS '选择题选项';

COMMENT ON COLUMN "CR_MISSION"."resource_id" IS 'uuid';

COMMENT ON COLUMN "CR_MISSION"."room_id" IS '房间id';

COMMENT ON COLUMN "CR_MISSION"."score" IS '分值';

COMMENT ON COLUMN "CR_MISSION"."show_writeup" IS '题解是否开放';

COMMENT ON COLUMN "CR_MISSION"."skill_points" IS '技能点数';

COMMENT ON COLUMN "CR_MISSION"."title" IS '任务标题';

COMMENT ON COLUMN "CR_MISSION"."type" IS '题目类型（选择、判断等）';

COMMENT ON COLUMN "CR_MISSION"."update_time" IS '更新时间';

COMMENT ON COLUMN "CR_MISSION"."write_up" IS '题解';

COMMENT ON TABLE "CR_MISSION_TEMPLATE" IS '后台任务模板';

COMMENT ON COLUMN "CR_MISSION_TEMPLATE"."answer" IS '答案';

COMMENT ON COLUMN "CR_MISSION_TEMPLATE"."attachment" IS '附件集合';

COMMENT ON COLUMN "CR_MISSION_TEMPLATE"."business_template_id" IS '训练模板id';

COMMENT ON COLUMN "CR_MISSION_TEMPLATE"."category_id" IS '题目类别';

COMMENT ON COLUMN "CR_MISSION_TEMPLATE"."create_time" IS '创建时间';

COMMENT ON COLUMN "CR_MISSION_TEMPLATE"."create_user_id" IS '创建人id';

COMMENT ON COLUMN "CR_MISSION_TEMPLATE"."description" IS '描述';

COMMENT ON COLUMN "CR_MISSION_TEMPLATE"."option" IS '选择题选项';

COMMENT ON COLUMN "CR_MISSION_TEMPLATE"."resource_id" IS 'uuid';

COMMENT ON COLUMN "CR_MISSION_TEMPLATE"."score" IS '分值';

COMMENT ON COLUMN "CR_MISSION_TEMPLATE"."skill_points" IS '技能点数';

COMMENT ON COLUMN "CR_MISSION_TEMPLATE"."template_id" IS '暂未用到';

COMMENT ON COLUMN "CR_MISSION_TEMPLATE"."title" IS '任务标题';

COMMENT ON COLUMN "CR_MISSION_TEMPLATE"."type" IS '题目类型（选择、判断等）';

COMMENT ON COLUMN "CR_MISSION_TEMPLATE"."update_time" IS '更新时间';

COMMENT ON COLUMN "CR_MISSION_TEMPLATE"."write_up" IS '题解';

COMMENT ON COLUMN "CR_NOTICE"."context" IS '公告内容';

COMMENT ON COLUMN "CR_NOTICE"."create_time" IS '创建时间';

COMMENT ON COLUMN "CR_NOTICE"."create_user_id" IS '创建人id';

COMMENT ON COLUMN "CR_NOTICE"."is_top" IS '是否置顶0否1是';

COMMENT ON COLUMN "CR_NOTICE"."resource_id" IS 'uuid';

COMMENT ON COLUMN "CR_NOTICE"."role" IS '公告对象';

COMMENT ON COLUMN "CR_NOTICE"."room_id" IS '房间id';

COMMENT ON COLUMN "CR_NOTICE"."update_time" IS '更新时间';

COMMENT ON TABLE "CR_RB_ROOM" IS '红蓝对抗房间详情';

COMMENT ON COLUMN "CR_RB_ROOM"."copyed_topo_id" IS '拓扑id';

COMMENT ON COLUMN "CR_RB_ROOM"."create_time" IS '创建时间';

COMMENT ON COLUMN "CR_RB_ROOM"."create_user_id" IS '创建人id';

COMMENT ON COLUMN "CR_RB_ROOM"."description" IS '描述';

COMMENT ON COLUMN "CR_RB_ROOM"."difficulty" IS '难度';

COMMENT ON COLUMN "CR_RB_ROOM"."enable_recording" IS '是否录屏';

COMMENT ON COLUMN "CR_RB_ROOM"."end_time" IS '结束时间';

COMMENT ON COLUMN "CR_RB_ROOM"."local_scene_id" IS '启动场景id';

COMMENT ON COLUMN "CR_RB_ROOM"."logo" IS '封面url';

COMMENT ON COLUMN "CR_RB_ROOM"."modify_user_id" IS '更新人id';

COMMENT ON COLUMN "CR_RB_ROOM"."name" IS '房间名';

COMMENT ON COLUMN "CR_RB_ROOM"."pre_skill" IS '前置技能';

COMMENT ON COLUMN "CR_RB_ROOM"."replay_scene_id" IS '复盘场景id';

COMMENT ON COLUMN "CR_RB_ROOM"."resource_id" IS 'uuid';

COMMENT ON COLUMN "CR_RB_ROOM"."scene_file" IS '场景文件';

COMMENT ON COLUMN "CR_RB_ROOM"."scene_id_record" IS '训练过程中的所有场景id';

COMMENT ON COLUMN "CR_RB_ROOM"."start_time" IS '开始时间';

COMMENT ON COLUMN "CR_RB_ROOM"."sub_type" IS '训练类别';

COMMENT ON COLUMN "CR_RB_ROOM"."support_partial_start" IS '是否部分启动';

COMMENT ON COLUMN "CR_RB_ROOM"."template_id" IS '红蓝对抗模板id';

COMMENT ON COLUMN "CR_RB_ROOM"."template_name" IS '对抗模板名称';

COMMENT ON COLUMN "CR_RB_ROOM"."time_setting" IS '用户授时配置';

COMMENT ON COLUMN "CR_RB_ROOM"."train_skill" IS '训练目标';

COMMENT ON COLUMN "CR_RB_ROOM"."training_status" IS '对抗状态';

COMMENT ON COLUMN "CR_RB_ROOM"."update_time" IS '更新时间';

COMMENT ON TABLE "CR_RB_TEMPLATE" IS '红蓝对抗模板详情';

COMMENT ON COLUMN "CR_RB_TEMPLATE"."business_template_id" IS '训练模板id';

COMMENT ON COLUMN "CR_RB_TEMPLATE"."create_time" IS '创建时间';

COMMENT ON COLUMN "CR_RB_TEMPLATE"."duration_time" IS '训练时长';

COMMENT ON COLUMN "CR_RB_TEMPLATE"."pre_skill" IS '前置技能';

COMMENT ON COLUMN "CR_RB_TEMPLATE"."resource_id" IS 'uuid';

COMMENT ON COLUMN "CR_RB_TEMPLATE"."role_config" IS '角色节点和任务配置';

COMMENT ON COLUMN "CR_RB_TEMPLATE"."sub_type" IS '训练类别';

COMMENT ON COLUMN "CR_RB_TEMPLATE"."train_skill" IS '训练目标';

COMMENT ON COLUMN "CR_RB_TEMPLATE"."update_time" IS '更新时间';

COMMENT ON TABLE "CR_RB_TIME_MANAGEMENT" IS '红蓝对抗作战时间管理';

COMMENT ON COLUMN "CR_RB_TIME_MANAGEMENT"."astronomical_time" IS '天文时间';

COMMENT ON COLUMN "CR_RB_TIME_MANAGEMENT"."combat_time" IS '作战时间';

COMMENT ON COLUMN "CR_RB_TIME_MANAGEMENT"."create_time" IS '创建时间';

COMMENT ON COLUMN "CR_RB_TIME_MANAGEMENT"."create_user_id" IS '创建人id';

COMMENT ON COLUMN "CR_RB_TIME_MANAGEMENT"."end_time" IS '结束时间';

COMMENT ON COLUMN "CR_RB_TIME_MANAGEMENT"."length" IS '已记录训练时长';

COMMENT ON COLUMN "CR_RB_TIME_MANAGEMENT"."rate" IS '时间比率';

COMMENT ON COLUMN "CR_RB_TIME_MANAGEMENT"."recover_time" IS '恢复时间';

COMMENT ON COLUMN "CR_RB_TIME_MANAGEMENT"."resource_id" IS 'uuid';

COMMENT ON COLUMN "CR_RB_TIME_MANAGEMENT"."room_id" IS '对抗房间id';

COMMENT ON COLUMN "CR_RB_TIME_MANAGEMENT"."status" IS '对抗赛状态';

COMMENT ON COLUMN "CR_RB_TIME_MANAGEMENT"."update_time" IS '更新时间';

COMMENT ON COLUMN "CR_RB_TIME_MANAGEMENT"."update_user_id" IS '更新人id';

COMMENT ON TABLE "CR_ROLE" IS '训练角色详情';

COMMENT ON COLUMN "CR_ROLE"."business_type" IS '训练类型';

COMMENT ON COLUMN "CR_ROLE"."create_time" IS '创建时间';

COMMENT ON COLUMN "CR_ROLE"."description" IS '描述';

COMMENT ON COLUMN "CR_ROLE"."exercise_paper" IS '训练手册';

COMMENT ON COLUMN "CR_ROLE"."mission_scheme" IS '任务数据';

COMMENT ON COLUMN "CR_ROLE"."name" IS '角色名';

COMMENT ON COLUMN "CR_ROLE"."nodes" IS '角色节点';

COMMENT ON COLUMN "CR_ROLE"."nodes_snapshot" IS '角色节点快照';

COMMENT ON COLUMN "CR_ROLE"."parent_resource_id" IS '父角色id';

COMMENT ON COLUMN "CR_ROLE"."resource_id" IS 'uuid';

COMMENT ON COLUMN "CR_ROLE"."role_group" IS '权限组';

COMMENT ON COLUMN "CR_ROLE"."room_id" IS '训练房间id';

COMMENT ON COLUMN "CR_ROLE"."update_time" IS '更新时间';

COMMENT ON COLUMN "CR_ROLE"."user_ids" IS '角色用户';

COMMENT ON TABLE "CR_TASK" IS '下发任务详情';

COMMENT ON COLUMN "CR_TASK"."business_type" IS '训练类型';

COMMENT ON COLUMN "CR_TASK"."create_time" IS '创建时间';

COMMENT ON COLUMN "CR_TASK"."last_submit_time" IS '最后提交时间';

COMMENT ON COLUMN "CR_TASK"."mission_id" IS '任务id';

COMMENT ON COLUMN "CR_TASK"."mission_template_id" IS '任务模板id';

COMMENT ON COLUMN "CR_TASK"."release_time" IS '下发时间';

COMMENT ON COLUMN "CR_TASK"."remark" IS '备注';

COMMENT ON COLUMN "CR_TASK"."resource_id" IS 'uuid';

COMMENT ON COLUMN "CR_TASK"."role_id" IS '角色id';

COMMENT ON COLUMN "CR_TASK"."room_id" IS '房间id';

COMMENT ON COLUMN "CR_TASK"."score" IS '得分';

COMMENT ON COLUMN "CR_TASK"."status" IS '执行状态';

COMMENT ON COLUMN "CR_TASK"."task_start_time" IS '任务开始时间';

COMMENT ON COLUMN "CR_TASK"."update_time" IS '更新时间';

COMMENT ON COLUMN "CR_TASK"."user_answer" IS '用户答案';

COMMENT ON COLUMN "CR_TASK"."user_id" IS '训练用户id';

COMMENT ON TABLE "CR_TASK_LOG" IS '任务动作记录';

COMMENT ON COLUMN "CR_TASK_LOG"."action_code" IS '动作类别';

COMMENT ON COLUMN "CR_TASK_LOG"."create_time" IS '创建时间';

COMMENT ON COLUMN "CR_TASK_LOG"."mission_id" IS '任务id';

COMMENT ON COLUMN "CR_TASK_LOG"."resource_id" IS 'uuid';

COMMENT ON COLUMN "CR_TASK_LOG"."room_id" IS '训练房间id';

COMMENT ON COLUMN "CR_TASK_LOG"."task_id" IS '任务id';

COMMENT ON COLUMN "CR_TASK_LOG"."update_time" IS '更新时间';

COMMENT ON COLUMN "CR_TASK_LOG"."user_id" IS '动作用户id';

COMMENT ON TABLE "CR_TASK_SUBMIT" IS '任务提交记录与评估';

COMMENT ON COLUMN "CR_TASK_SUBMIT"."answer" IS '提交答案';

COMMENT ON COLUMN "CR_TASK_SUBMIT"."answer_extension" IS '提交答案附加信息';

COMMENT ON COLUMN "CR_TASK_SUBMIT"."create_time" IS '创建时间';

COMMENT ON COLUMN "CR_TASK_SUBMIT"."detail" IS '评估详情';

COMMENT ON COLUMN "CR_TASK_SUBMIT"."evaluate_status" IS '评估状态';

COMMENT ON COLUMN "CR_TASK_SUBMIT"."evaluate_time" IS '评估时间';

COMMENT ON COLUMN "CR_TASK_SUBMIT"."evaluator_id" IS '评估人';

COMMENT ON COLUMN "CR_TASK_SUBMIT"."mission_score" IS '任务分值';

COMMENT ON COLUMN "CR_TASK_SUBMIT"."reason" IS '评估理由';

COMMENT ON COLUMN "CR_TASK_SUBMIT"."resource_id" IS 'uuid';

COMMENT ON COLUMN "CR_TASK_SUBMIT"."room_id" IS '训练房间id';

COMMENT ON COLUMN "CR_TASK_SUBMIT"."score" IS '得分';

COMMENT ON COLUMN "CR_TASK_SUBMIT"."task_id" IS '任务id';

COMMENT ON COLUMN "CR_TASK_SUBMIT"."update_time" IS '更新时间';

COMMENT ON TABLE "CR_TEAM_ROOM" IS '团队训练房间详情';

COMMENT ON COLUMN "CR_TEAM_ROOM"."area_path" IS '攻击路径';

COMMENT ON COLUMN "CR_TEAM_ROOM"."copyed_topo_id" IS '拓扑id';

COMMENT ON COLUMN "CR_TEAM_ROOM"."create_time" IS '创建时间';

COMMENT ON COLUMN "CR_TEAM_ROOM"."create_user_id" IS '创建人id';

COMMENT ON COLUMN "CR_TEAM_ROOM"."description" IS '描述';

COMMENT ON COLUMN "CR_TEAM_ROOM"."difficulty" IS '难度';

COMMENT ON COLUMN "CR_TEAM_ROOM"."enable_recording" IS '录屏状态';

COMMENT ON COLUMN "CR_TEAM_ROOM"."end_time" IS '结束时间';

COMMENT ON COLUMN "CR_TEAM_ROOM"."local_scene_id" IS '当前启动场景id';

COMMENT ON COLUMN "CR_TEAM_ROOM"."logo" IS '封面url';

COMMENT ON COLUMN "CR_TEAM_ROOM"."modify_user_id" IS '修改人id';

COMMENT ON COLUMN "CR_TEAM_ROOM"."name" IS '房间名';

COMMENT ON COLUMN "CR_TEAM_ROOM"."pre_skills" IS '前置技能';

COMMENT ON COLUMN "CR_TEAM_ROOM"."replay_scene_id" IS '复盘场景id';

COMMENT ON COLUMN "CR_TEAM_ROOM"."resource_id" IS 'uuid';

COMMENT ON COLUMN "CR_TEAM_ROOM"."scene_id_record" IS '训练过程中的所有场景id';

COMMENT ON COLUMN "CR_TEAM_ROOM"."start_time" IS '开始时间';

COMMENT ON COLUMN "CR_TEAM_ROOM"."sub_type" IS '训练类别';

COMMENT ON COLUMN "CR_TEAM_ROOM"."team_template_id" IS '团队模板id';

COMMENT ON COLUMN "CR_TEAM_ROOM"."team_template_name" IS '团队模板名称';

COMMENT ON COLUMN "CR_TEAM_ROOM"."time_line_config" IS '训练时间线信息';

COMMENT ON COLUMN "CR_TEAM_ROOM"."train_skills" IS '训练目标';

COMMENT ON COLUMN "CR_TEAM_ROOM"."training_status" IS '训练状态';

COMMENT ON COLUMN "CR_TEAM_ROOM"."update_time" IS '更新时间';

COMMENT ON TABLE "CR_TEAM_TEMPLATE" IS '团队训练模板详情';

COMMENT ON COLUMN "CR_TEAM_TEMPLATE"."business_template_id" IS '训练模板id';

COMMENT ON COLUMN "CR_TEAM_TEMPLATE"."create_time" IS '创建时间';

COMMENT ON COLUMN "CR_TEAM_TEMPLATE"."duration_time" IS '训练时长';

COMMENT ON COLUMN "CR_TEAM_TEMPLATE"."people_number" IS '训练参与人数';

COMMENT ON COLUMN "CR_TEAM_TEMPLATE"."pre_skills" IS '前置技能';

COMMENT ON COLUMN "CR_TEAM_TEMPLATE"."resource_id" IS 'uuid';

COMMENT ON COLUMN "CR_TEAM_TEMPLATE"."role_config" IS '角色信息配置';

COMMENT ON COLUMN "CR_TEAM_TEMPLATE"."situation_config" IS '态势配置';

COMMENT ON COLUMN "CR_TEAM_TEMPLATE"."sub_type" IS '训练类别';

COMMENT ON COLUMN "CR_TEAM_TEMPLATE"."time_line_config" IS '时间线配置';

COMMENT ON COLUMN "CR_TEAM_TEMPLATE"."train_skills" IS '训练目标';

COMMENT ON COLUMN "CR_TEAM_TEMPLATE"."update_time" IS '更新时间';

COMMENT ON TABLE "CR_TRAIN_RECORD" IS '训练历史';

COMMENT ON COLUMN "CR_TRAIN_RECORD"."business_template_id" IS '训练模板id';

COMMENT ON COLUMN "CR_TRAIN_RECORD"."business_type" IS '训练类型';

COMMENT ON COLUMN "CR_TRAIN_RECORD"."end_time" IS '结束时间';

COMMENT ON COLUMN "CR_TRAIN_RECORD"."extra" IS '附加信息';

COMMENT ON COLUMN "CR_TRAIN_RECORD"."room_id" IS '房间id';

COMMENT ON COLUMN "CR_TRAIN_RECORD"."room_name" IS '房间名';

COMMENT ON COLUMN "CR_TRAIN_RECORD"."start_time" IS '开始时间';

COMMENT ON COLUMN "CR_TRAIN_RECORD"."user_id" IS '参与人id';

CREATE INDEX "INDEX211727916498000"
ON "CR_SINGLE_TEMPLATE"("business_template_id");
ALTER INDEX "INDEX211727916498000" VISIBLE;

COMMENT ON TABLE "CR_SINGLE_TEMPLATE" IS '单兵训模板详情';

COMMENT ON COLUMN "CR_SINGLE_TEMPLATE"."business_template_id" IS '训练模板id';

COMMENT ON COLUMN "CR_SINGLE_TEMPLATE"."create_time" IS '创建时间';

COMMENT ON COLUMN "CR_SINGLE_TEMPLATE"."duration_time" IS '训练时长';

COMMENT ON COLUMN "CR_SINGLE_TEMPLATE"."missions_scheme" IS '任务配置';
