CREATE TABLE "ai_user_profile_report"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "user_id" VARCHAR(64) NOT NULL,
    "dimension" INT NOT NULL,
    "name" VARCHAR(128) NOT NULL,
    "content" CLOB NULL,
    "update_time" TIMESTAMP(0) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "attachment"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "file_name" VARCHAR(128) NOT NULL,
    "file_path" VARCHAR(256) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL,
    "public" INT NOT NULL,
    "business_id" VARCHAR(64) NULL
);
CREATE TABLE "behavior_record"
(
    "id" INT IDENTITY(30,1) NOT NULL,
    "module" SMALLINT NOT NULL,
    "user_id" VARCHAR(65) NOT NULL,
    "action" SMALLINT NOT NULL,
    "target" VARCHAR(255) NULL,
    "result" SMALLINT NULL,
    "link" VARCHAR(255) NULL,
    "description" VARCHAR(1024) NULL,
    "time" TIMESTAMP(0) NULL
);
CREATE TABLE "cr_badge"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "type" TINYINT NOT NULL,
    "business_id" VARCHAR(65) NOT NULL,
    "user_id" VARCHAR(32) NOT NULL,
    "extra" TEXT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "cr_business_template"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "local_scene_id" VARCHAR(40) NULL,
    "topo_id" VARCHAR(40) NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "description" TEXT NOT NULL,
    "business_type" INT NOT NULL,
    "difficulty" INT NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "create_user_id" VARCHAR(40) NOT NULL,
    "operation_auth_type" INT NULL,
    "operation_auth_ids" TEXT NULL,
    "read_auth_type" INT NULL,
    "read_auth_ids" TEXT NULL,
    "logo" VARCHAR(255) NULL
);
CREATE TABLE "cr_constants_config"
(
    "id" INT IDENTITY(156,1) NOT NULL,
    "resource_id" VARCHAR(32) NULL,
    "code" SMALLINT NULL,
    "type" TINYINT NOT NULL,
    "cn_name" VARCHAR(255) NOT NULL,
    "en_name" VARCHAR(255) NOT NULL
);
CREATE TABLE "cr_eval_aggregation"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "name" VARCHAR(256) NULL,
    "description" TEXT NULL,
    "eval_indicator_id" VARCHAR(64) NULL,
    "collection_ids" TEXT NULL,
    "aggregating_algorithm_id" VARCHAR(64) NULL,
    "aggregating_algorithm_params" VARCHAR(2000) NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "matrix_data" CLOB NULL
);
CREATE TABLE "cr_eval_algorithm"
(
    "id" INT IDENTITY(18,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "name" VARCHAR(256) NULL,
    "description" TEXT NULL,
    "state" INT NULL,
    "type" INT NULL,
    "algorithm_language" INT NULL,
    "code" TEXT NULL,
    "params" VARCHAR(2000) NULL,
    "params_num" INT NULL,
    "create_user_id" VARCHAR(64) NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "mode" INT DEFAULT 0
                              NOT NULL,
    "builtin" INT DEFAULT 0
                              NOT NULL
);
CREATE TABLE "cr_eval_algorithm_ref"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "name" VARCHAR(256) NOT NULL,
    "description" VARCHAR(2048) NULL,
    "type" INT NOT NULL,
    "algorithm_language" INT NOT NULL,
    "code" TEXT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "mode" INT DEFAULT 0
                              NOT NULL,
    "matrix_data" CLOB NULL
);
CREATE TABLE "cr_eval_collection"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "name" VARCHAR(256) NULL,
    "eval_indicator_id" VARCHAR(64) NULL,
    "format_algorithm_id" VARCHAR(64) NULL,
    "format_algorithm_params" VARCHAR(2000) NULL,
    "detection_algorithm_id" VARCHAR(64) NULL,
    "detection_algorithm_params" VARCHAR(2000) NULL,
    "score_algorithm_id" VARCHAR(64) NULL,
    "score_algorithm_params" VARCHAR(2000) NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "cr_eval_index"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "name" VARCHAR(256) NULL,
    "description" TEXT NULL,
    "level" INT NULL,
    "eval_system_id" VARCHAR(64) NULL,
    "eval_indicator_id" VARCHAR(64) NULL,
    "type" INT NULL,
    "aggregating_algorithm_id" VARCHAR(64) NULL,
    "aggregating_algorithm_params" VARCHAR(2000) NULL,
    "children_ids" TEXT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "matrix_data" CLOB NULL
);
CREATE TABLE "cr_eval_index_ref"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "name" VARCHAR(256) NOT NULL,
    "type" INT NOT NULL,
    "score" INT NULL,
    "level" INT NOT NULL,
    "record_id" VARCHAR(64) NOT NULL,
    "algorithm_id" VARCHAR(64) NOT NULL,
    "next_ids" TEXT NOT NULL,
    "start_time" TIMESTAMP(0) NULL,
    "end_time" TIMESTAMP(0) NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "algorithm_params" CLOB NULL,
    "specialist_value" INT NULL,
    "specialist_type" INT NULL,
    "specialist_attr" INT NULL,
    "specialist_attr_value" INT NULL,
    "progress" INT NULL,
    "matrix_data" CLOB NULL
);
CREATE TABLE "cr_eval_indicator"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "name" VARCHAR(256) NULL,
    "attribute" INT NULL,
    "attribute_value" INT NULL,
    "datatype" INT NULL,
    "connlist" TEXT NULL,
    "description" TEXT NULL,
    "aggregating_algorithm_id" VARCHAR(64) NULL,
    "aggregating_algorithm_params" VARCHAR(2000) NULL,
    "aggregation_ids" TEXT NULL,
    "collection_ids" TEXT NULL,
    "create_user_id" VARCHAR(64) NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "matrix_data" CLOB NULL
);
CREATE TABLE "cr_eval_probe_data"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "scene_id" VARCHAR(64) NOT NULL,
    "device_id" VARCHAR(64) NOT NULL,
    "probe_id" VARCHAR(64) NOT NULL,
    "content" TEXT NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "collection_time" TIMESTAMP(0) NULL,
    "device_ip" VARCHAR(64) NULL
);
CREATE TABLE "cr_eval_record"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "name" VARCHAR(256) NOT NULL,
    "description" VARCHAR(2048) NULL,
    "status" INT NOT NULL,
    "score" INT NULL,
    "start_time" TIMESTAMP(0) NULL,
    "end_time" TIMESTAMP(0) NULL,
    "system_id" VARCHAR(64) NOT NULL,
    "task_id" VARCHAR(64) NOT NULL,
    "message" CLOB NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "eval_time" TIMESTAMP(0) NULL,
    "eval_end_time" TIMESTAMP(0) NULL
);
CREATE TABLE "cr_eval_result"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "record" VARCHAR(128) NOT NULL,
    "first_index_res" VARCHAR(256) NOT NULL,
    "second_index_res" CLOB NULL,
    "third_index_res" CLOB NULL,
    "fourth_index_res" CLOB NULL,
    "fifth_index_res" CLOB NULL,
    "exe_cycle" INT NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "cr_eval_system"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "name" VARCHAR(256) NULL,
    "description" TEXT NULL,
    "create_user_id" VARCHAR(64) NULL,
    "connlist" TEXT NULL,
    "first_index_num" INT NULL,
    "second_index_num" INT NULL,
    "third_index_num" INT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "cr_eval_task"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "template_id" VARCHAR(40) NOT NULL,
    "template_name" VARCHAR(255) NOT NULL,
    "task_name" VARCHAR(255) NULL,
    "local_scene_id" VARCHAR(40) NULL,
    "mp_scene_id" VARCHAR(64) NOT NULL,
    "copyed_topo_id" VARCHAR(40) NOT NULL,
    "start_time" TIMESTAMP(0) NOT NULL,
    "end_time" TIMESTAMP(0) NULL,
    "enable_recording" TINYINT NOT NULL,
    "create_user_id" VARCHAR(40) NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "cr_eval_task_step"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "task_id" VARCHAR(64) NOT NULL,
    "stage" TINYINT NOT NULL,
    "title" VARCHAR(255) NOT NULL,
    "process" CLOB NULL,
    "type" TINYINT NOT NULL,
    "start_time" TIMESTAMP(0) NULL,
    "end_time" TIMESTAMP(0) NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "cr_eval_template"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "category_code" INT NOT NULL,
    "step_config" TEXT NOT NULL,
    "enable_recording" TINYINT NULL,
    "current_task_id" VARCHAR(64) NULL,
    "name" VARCHAR(255) NOT NULL,
    "description" TEXT NULL,
    "topo_id" VARCHAR(40) NOT NULL,
    "logo" VARCHAR(64) NULL,
    "update_time" TIMESTAMP(0) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "create_user_id" VARCHAR(40) NOT NULL,
    "operation_auth_ids" TEXT NULL,
    "operation_auth_type" TINYINT DEFAULT 2
        NOT NULL,
    "read_auth_type" TINYINT DEFAULT 2
        NOT NULL,
    "read_auth_ids" TEXT NULL
);
CREATE TABLE "cr_eval_variable_ref"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "name" VARCHAR(256) NOT NULL,
    "score" INT NULL,
    "device_id" VARCHAR(64) NOT NULL,
    "probe_id" VARCHAR(64) NOT NULL,
    "record_id" VARCHAR(64) NOT NULL,
    "progress" INT NULL,
    "format_start_time" TIMESTAMP(0) NULL,
    "format_end_time" TIMESTAMP(0) NULL,
    "match_start_time" TIMESTAMP(0) NULL,
    "match_end_time" TIMESTAMP(0) NULL,
    "mark_start_time" TIMESTAMP(0) NULL,
    "mark_end_time" TIMESTAMP(0) NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "format_algorithm_id" VARCHAR(64) NOT NULL,
    "match_algorithm_id" VARCHAR(64) NOT NULL,
    "mark_algorithm_id" VARCHAR(64) NOT NULL,
    "format_params" CLOB NULL,
    "match_params" CLOB NULL,
    "mark_params" CLOB NULL,
    "type" INT DEFAULT 1
        NULL
);
CREATE TABLE "cr_eval_variable_ref_probe_data"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "variable_id" VARCHAR(64) NOT NULL,
    "probe_data_id" VARCHAR(64) NOT NULL,
    "formatted_value" TEXT NULL,
    "status" INT NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "cr_mark"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(32) NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "description" VARCHAR(255) NOT NULL,
    "evidence" TEXT NULL,
    "user_id" VARCHAR(32) NOT NULL,
    "room_id" VARCHAR(32) NOT NULL,
    "type" TINYINT NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "cr_mission"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(32) NOT NULL,
    "create_user_id" VARCHAR(32) NULL,
    "title" VARCHAR(255) NULL,
    "description" TEXT NULL,
    "score" SMALLINT NULL,
    "skill_points" VARCHAR(40) NULL,
    "answer" VARCHAR(4096) NULL,
    "option" TEXT NULL,
    "type" TINYINT NULL,
    "category_id" VARCHAR(32) NULL,
    "write_up" CLOB NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "show_writeup" TINYINT NULL,
    "room_id" VARCHAR(32) NULL,
    "mission_template_id" VARCHAR(32) NULL
);
CREATE TABLE "cr_mission_category"
(
    "id" INT IDENTITY(16,1) NOT NULL,
    "resource_id" VARCHAR(64) NULL,
    "name" VARCHAR(255) NULL,
    "key" VARCHAR(255) NULL
);
CREATE TABLE "cr_mission_scheme_template"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(32) NOT NULL,
    "create_user_id" VARCHAR(32) NULL,
    "title" VARCHAR(255) NULL,
    "description" VARCHAR(2048) NULL,
    "template_data" TEXT NULL,
    "template_config" VARCHAR(255) NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "cr_mission_situation"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "mission_template_id" VARCHAR(32) NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "ts_id" INT NOT NULL,
    "ts_type" VARCHAR(255) NOT NULL,
    "ts_desc" VARCHAR(255) NOT NULL,
    "target_device" TEXT NOT NULL,
    "source_device" VARCHAR(255) NULL,
    "area" VARCHAR(255) NOT NULL,
    "extra_effect" VARCHAR(255) NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "cr_mission_template"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NULL,
    "title" VARCHAR(255) NULL,
    "description" TEXT NULL,
    "score" SMALLINT NULL,
    "skill_points" VARCHAR(40) NULL,
    "answer" VARCHAR(4096) NULL,
    "option" TEXT NULL,
    "type" TINYINT NULL,
    "category_id" VARCHAR(64) NULL,
    "write_up" CLOB NULL,
    "create_user_id" VARCHAR(64) NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "template_id" VARCHAR(64) NULL,
    "attachment" VARCHAR(4096) NULL,
    "business_template_id" VARCHAR(32) NULL
);
CREATE TABLE "cr_notice"
(
    "id" INT IDENTITY(114,1) NOT NULL,
    "resource_id" VARCHAR(32) NOT NULL,
    "create_user_id" VARCHAR(32) NULL,
    "context" VARCHAR(1024) NULL,
    "role" TINYINT NULL,
    "room_id" VARCHAR(32) NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "is_top" TINYINT NOT NULL,
    "notice_users" TEXT NOT NULL
);
CREATE TABLE "cr_radar_label"
(
    "id" INT IDENTITY(8,1) NOT NULL,
    "resource_id" INT NULL,
    "label" VARCHAR(255) NULL
);
CREATE TABLE "cr_rb_common_device"
(
    "id" INT IDENTITY(23,1) NOT NULL,
    "resource_id" VARCHAR(64) NULL,
    "room_id" VARCHAR(64) NULL,
    "device_id" VARCHAR(64) NULL,
    "collect_user_id" VARCHAR(64) NULL,
    "create_user_id" VARCHAR(64) NULL,
    "create_time" TIMESTAMP(0) NULL
);
CREATE TABLE "cr_rb_device"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NULL,
    "room_id" VARCHAR(64) NULL,
    "device_id" VARCHAR(64) NULL,
    "create_user_id" VARCHAR(64) NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "cr_rb_room"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "template_id" VARCHAR(40) NOT NULL,
    "template_name" VARCHAR(255) NULL,
    "name" VARCHAR(255) NOT NULL,
    "description" VARCHAR(2048) NOT NULL,
    "sub_type" INT NOT NULL,
    "pre_skill" TEXT NOT NULL,
    "train_skill" TEXT NOT NULL,
    "start_time" TIMESTAMP(0) NOT NULL,
    "end_time" TIMESTAMP(0) NOT NULL,
    "difficulty" INT NOT NULL,
    "training_status" INT NULL,
    "scene_file" TEXT NULL,
    "create_user_id" VARCHAR(40) NULL,
    "modify_user_id" VARCHAR(40) NULL,
    "local_scene_id" VARCHAR(40) NULL,
    "copyed_topo_id" VARCHAR(40) NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "enable_recording" TINYINT NULL,
    "support_partial_start" TINYINT NULL,
    "logo" VARCHAR(255) NULL,
    "replay_scene_id" VARCHAR(32) NULL,
    "scene_id_record" TEXT NULL,
    "time_setting" VARCHAR(255) NULL
);
CREATE TABLE "cr_rb_template"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "business_template_id" VARCHAR(40) NOT NULL,
    "pre_skill" TEXT NOT NULL,
    "train_skill" TEXT NOT NULL,
    "role_config" TEXT NOT NULL,
    "sub_type" TINYINT NOT NULL,
    "duration_time" INT NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "cr_rb_time_management"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "room_id" VARCHAR(40) NOT NULL,
    "user_id" VARCHAR(40) NULL,
    "astronomical_time" TIMESTAMP(0) NOT NULL,
    "combat_time" TIMESTAMP(0) NOT NULL,
    "rate" INT NOT NULL,
    "status" INT NOT NULL,
    "create_user_id" VARCHAR(64) NULL,
    "create_time" TIMESTAMP(6) NOT NULL,
    "update_user_id" VARCHAR(64) NULL,
    "update_time" TIMESTAMP(6) NOT NULL,
    "length" INT DEFAULT 0
        NOT NULL,
    "recover_time" TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6)
        NOT NULL,
    "end_time" TIMESTAMP(6) NULL
);
CREATE TABLE "cr_role"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "room_id" VARCHAR(40) NOT NULL,
    "description" VARCHAR(2048) NULL,
    "role_group" INT NOT NULL,
    "nodes" TEXT NULL,
    "nodes_snapshot" TEXT NULL,
    "user_ids" TEXT NOT NULL,
    "exercise_paper" VARCHAR(4000) NULL,
    "parent_resource_id" VARCHAR(40) NULL,
    "mission_scheme" VARCHAR(4000) NULL,
    "business_type" INT NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "cr_score"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "room_id" VARCHAR(40) NOT NULL,
    "user_id" VARCHAR(40) NOT NULL,
    "training_score" VARCHAR(4000) NULL,
    "training_percentage" VARCHAR(40) NOT NULL,
    "skill_percentage" VARCHAR(40) NOT NULL,
    "skill_score" VARCHAR(40) NOT NULL,
    "comment" CLOB NULL,
    "final_score" VARCHAR(40) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "cr_single_room"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "business_template_id" VARCHAR(64) NOT NULL,
    "start_time" TIMESTAMP(0) NULL,
    "end_time" TIMESTAMP(0) NULL,
    "training_record" CLOB NULL,
    "user_id" VARCHAR(64) NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "local_scene_id" CHAR(32) NOT NULL,
    "is_finish" TINYINT NOT NULL
);
CREATE TABLE "cr_single_template"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "business_template_id" VARCHAR(64) NOT NULL,
    "pre_skills" TEXT NOT NULL,
    "train_skills" TEXT NOT NULL,
    "sub_type" TINYINT NOT NULL,
    "duration_time" BIGINT NOT NULL,
    "missions_scheme" CLOB NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "cr_task"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NULL,
    "status" TINYINT NULL,
    "mission_id" VARCHAR(64) NULL,
    "user_id" VARCHAR(64) NULL,
    "user_answer" CLOB NULL,
    "release_time" TIMESTAMP(0) NULL,
    "last_submit_time" TIMESTAMP(0) NULL,
    "task_start_time" TIMESTAMP(0) NULL,
    "room_id" VARCHAR(64) NULL,
    "role_id" VARCHAR(64) NULL,
    "business_type" INT NULL,
    "score" SMALLINT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "mission_template_id" VARCHAR(32) NULL,
    "remark" CLOB NULL
);
CREATE TABLE "cr_task_log"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "room_id" VARCHAR(64) NULL,
    "mission_id" VARCHAR(64) NULL,
    "action_code" TINYINT NULL,
    "user_id" VARCHAR(64) NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "task_id" VARCHAR(64) NULL
);
CREATE TABLE "cr_task_submit"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "room_id" VARCHAR(64) NULL,
    "task_id" VARCHAR(64) NULL,
    "answer" CLOB NULL,
    "answer_extension" CLOB NULL,
    "evaluator_id" VARCHAR(64) NULL,
    "evaluate_time" TIMESTAMP(0) NULL,
    "evaluate_status" TINYINT NULL,
    "reason" TINYINT NULL,
    "detail" VARCHAR(255) NULL,
    "score" SMALLINT NULL,
    "mission_score" SMALLINT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "cr_team_common_device"
(
    "id" INT IDENTITY(8,1) NOT NULL,
    "resource_id" VARCHAR(64) NULL,
    "room_id" VARCHAR(64) NULL,
    "device_id" VARCHAR(64) NULL,
    "collect_user_id" VARCHAR(64) NULL,
    "create_user_id" VARCHAR(64) NULL,
    "create_time" TIMESTAMP(0) NULL
);
CREATE TABLE "cr_team_device"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NULL,
    "room_id" VARCHAR(64) NULL,
    "device_id" VARCHAR(64) NULL,
    "create_user_id" VARCHAR(64) NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "cr_team_room"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "team_template_id" VARCHAR(40) NOT NULL,
    "team_template_name" VARCHAR(255) NULL,
    "name" VARCHAR(255) NOT NULL,
    "description" VARCHAR(2048) NOT NULL,
    "sub_type" INT NOT NULL,
    "pre_skills" VARCHAR(4000) NOT NULL,
    "train_skills" VARCHAR(4000) NOT NULL,
    "start_time" TIMESTAMP(0) NOT NULL,
    "end_time" TIMESTAMP(0) NOT NULL,
    "difficulty" INT NOT NULL,
    "training_status" INT NULL,
    "time_line_config" VARCHAR(4000) NOT NULL,
    "create_user_id" VARCHAR(40) NULL,
    "modify_user_id" VARCHAR(40) NULL,
    "local_scene_id" VARCHAR(40) NULL,
    "copyed_topo_id" VARCHAR(40) NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "enable_recording" TINYINT NULL,
    "logo" VARCHAR(255) NULL,
    "replay_scene_id" VARCHAR(32) NULL,
    "area_path" TEXT NULL,
    "scene_id_record" TEXT NULL
);
CREATE TABLE "cr_team_template"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "business_template_id" VARCHAR(40) NOT NULL,
    "pre_skills" TEXT NOT NULL,
    "train_skills" TEXT NOT NULL,
    "role_config" TEXT NOT NULL,
    "sub_type" TINYINT NOT NULL,
    "time_line_config" VARCHAR(4000) NOT NULL,
    "people_number" INT NOT NULL,
    "duration_time" INT NOT NULL,
    "situation_config" CLOB NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "cr_tool_exploit"
(
    "id" INT NOT NULL,
    "status" VARCHAR(255) NULL,
    "create_time" TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP()
        NULL,
    "title" VARCHAR(255) NULL,
    "edb_id" INT NULL,
    "cve" VARCHAR(255) NULL,
    "author" VARCHAR(255) NULL,
    "type" VARCHAR(255) NULL,
    "platform" VARCHAR(255) NULL,
    "date" VARCHAR(255) NULL,
    "verified" TINYINT NULL,
    "exploit" VARCHAR(255) NULL,
    "suffix" VARCHAR(255) DEFAULT ''
        NULL,
    "content" CLOB NULL,
    "vulnerable_app" VARCHAR(255) NULL
);
CREATE TABLE "cr_tool_ghdb"
(
    "id" INT NOT NULL,
    "title" VARCHAR(255) NULL,
    "ghdb_id" INT NULL,
    "google_search" VARCHAR(255) NULL,
    "author" VARCHAR(255) NULL,
    "published" VARCHAR(255) NULL,
    "google_dork_description" VARCHAR(255) NULL,
    "content" CLOB NULL,
    "status" VARCHAR(255) NULL,
    "create_time" VARCHAR(255) NULL
);
CREATE TABLE "cr_tool_shellcode"
(
    "id" INT NOT NULL,
    "status" VARCHAR(255) NULL,
    "create_time" TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP()
        NULL,
    "title" VARCHAR(255) DEFAULT ''
        NULL,
    "edb_id" INT NULL,
    "size" VARCHAR(255) DEFAULT ''
        NULL,
    "author" VARCHAR(255) DEFAULT ''
        NULL,
    "type" VARCHAR(255) NULL,
    "platform" VARCHAR(64) NULL,
    "published" VARCHAR(255) NULL,
    "edb_verified" TINYINT NULL,
    "shellcode" VARCHAR(255) NULL,
    "suffix" VARCHAR(255) DEFAULT ''
        NULL,
    "content" CLOB NULL
);
CREATE TABLE "cr_tool_tool"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NULL,
    "name" VARCHAR(64) NOT NULL,
    "cover" VARCHAR(64) NULL,
    "homepage" VARCHAR(1024) NULL,
    "category" VARCHAR(255) NULL,
    "manual" VARCHAR(256) NULL,
    "image_resource_id" VARCHAR(64) NULL,
    "visible" TINYINT NULL,
    "tool_type" SMALLINT NULL,
    "platforms" VARCHAR(128) NULL,
    "language" VARCHAR(64) NULL,
    "introduction" TEXT NULL,
    "public" VARCHAR(64) NULL,
    "cracked" TINYINT NULL,
    "tag" VARCHAR(256) NULL,
    "publish_time" TIMESTAMP(0) NULL,
    "scene" VARCHAR(128) NULL,
    "version" VARCHAR(512) NULL,
    "files" VARCHAR(128) NULL,
    "cover_url" VARCHAR(128) NULL,
    "serial_version_uid" CLOB NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
ALTER TABLE "ai_user_profile_report" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "attachment" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "behavior_record" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_badge" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_business_template" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_constants_config" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_eval_aggregation" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_eval_algorithm" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_eval_algorithm_ref" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_eval_collection" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_eval_index" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_eval_index_ref" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_eval_indicator" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_eval_probe_data" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_eval_record" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_eval_result" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_eval_system" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_eval_task" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_eval_task_step" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_eval_template" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_eval_variable_ref" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_eval_variable_ref_probe_data" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_mark" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_mission" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_mission_category" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_mission_scheme_template" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_mission_situation" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_mission_template" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_notice" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_radar_label" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_rb_common_device" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_rb_device" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_rb_room" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_rb_template" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_rb_time_management" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_role" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_score" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_single_room" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_single_template" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_task" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_task_log" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_task_submit" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_team_common_device" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_team_device" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_team_room" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_team_template" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_tool_exploit" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_tool_ghdb" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_tool_shellcode" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_tool_tool" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

CREATE INDEX "module_user"
    ON "behavior_record"("module","user_id");
ALTER INDEX "module_user" VISIBLE;

CREATE INDEX "business_type_user"
    ON "cr_badge"("business_id","type","user_id");
ALTER INDEX "business_type_user" VISIBLE;

CREATE INDEX "variable_id"
    ON "cr_eval_variable_ref_probe_data"("variable_id");
ALTER INDEX "variable_id" VISIBLE;

ALTER TABLE "attachment" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_business_template" ADD CONSTRAINT "cr_business_template_name_type" UNIQUE("name","business_type") ;

ALTER TABLE "cr_business_template" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_eval_aggregation" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_eval_algorithm" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_eval_algorithm_ref" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_eval_collection" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_eval_index" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_eval_index_ref" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_eval_indicator" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_eval_record" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_eval_result" ADD CONSTRAINT "record_exe_cycle_uniq" UNIQUE("exe_cycle","record") ;

ALTER TABLE "cr_eval_system" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_eval_task" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_eval_task_step" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_eval_template" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_eval_variable_ref" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_mark" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_mission" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_mission_situation" ADD CONSTRAINT "mission_template_id" UNIQUE("mission_template_id") ;

ALTER TABLE "cr_mission_template" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_notice" ADD CONSTRAINT "idx_resource_id" UNIQUE("resource_id") ;

ALTER TABLE "cr_rb_common_device" ADD CONSTRAINT "resource_id_uni" UNIQUE("resource_id") ;

ALTER TABLE "cr_rb_device" ADD CONSTRAINT "room_device_user" UNIQUE("room_id","device_id","create_user_id") ;

ALTER TABLE "cr_rb_device" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_rb_room" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_rb_template" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_rb_time_management" ADD UNIQUE("room_id") ;

ALTER TABLE "cr_rb_time_management" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_role" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_score" ADD CONSTRAINT "room_user" UNIQUE("room_id","user_id") ;

ALTER TABLE "cr_score" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_single_room" ADD CONSTRAINT "business_template_id__user_id__uniq" UNIQUE("business_template_id","user_id") ;

ALTER TABLE "cr_single_room" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_single_template" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_task" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_task_log" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_task_submit" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_team_common_device" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_team_device" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_team_room" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_team_template" ADD UNIQUE("resource_id") ;

ALTER TABLE "cr_tool_tool" ADD CONSTRAINT "cr_tool_tool_name_uindex" UNIQUE("name") ;

COMMENT ON TABLE "ai_user_profile_report" IS '智能体-用户画像报告';

COMMENT ON COLUMN "ai_user_profile_report"."content" IS '报告内容';

COMMENT ON COLUMN "ai_user_profile_report"."create_time" IS '创建时间';

COMMENT ON COLUMN "ai_user_profile_report"."dimension" IS '1=课程学习 2=题目练习 3=综合训练 4=岗位评估';

COMMENT ON COLUMN "ai_user_profile_report"."name" IS '报告名称';

COMMENT ON COLUMN "ai_user_profile_report"."update_time" IS '更新时间';

COMMENT ON COLUMN "ai_user_profile_report"."user_id" IS '用户编号';

COMMENT ON TABLE "attachment" IS '附件表';

COMMENT ON COLUMN "attachment"."business_id" IS '业务id';

COMMENT ON COLUMN "attachment"."create_time" IS '创建时间';

COMMENT ON COLUMN "attachment"."file_name" IS '文件名称';

COMMENT ON COLUMN "attachment"."file_path" IS '文件路径';

COMMENT ON COLUMN "attachment"."id" IS '自增id';

COMMENT ON COLUMN "attachment"."public" IS '是否公开';

COMMENT ON COLUMN "attachment"."resource_id" IS '资源id';

COMMENT ON COLUMN "attachment"."update_time" IS '更新时间';

COMMENT ON COLUMN "behavior_record"."action" IS '用户动作类型';

COMMENT ON COLUMN "behavior_record"."description" IS '描述';

COMMENT ON COLUMN "behavior_record"."link" IS '链接';

COMMENT ON COLUMN "behavior_record"."module" IS '模块';

COMMENT ON COLUMN "behavior_record"."result" IS '结果';

COMMENT ON COLUMN "behavior_record"."target" IS '动作目标';

COMMENT ON COLUMN "behavior_record"."time" IS '时间';

COMMENT ON COLUMN "behavior_record"."user_id" IS '用户id';

COMMENT ON TABLE "cr_badge" IS '业务红点提示';

COMMENT ON COLUMN "cr_badge"."business_id" IS '业务id';

COMMENT ON COLUMN "cr_badge"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_badge"."extra" IS '记录业务相关信息';

COMMENT ON COLUMN "cr_badge"."id" IS '自增id';

COMMENT ON COLUMN "cr_badge"."type" IS '类型';

COMMENT ON COLUMN "cr_badge"."update_time" IS '更新时间';

COMMENT ON COLUMN "cr_badge"."user_id" IS '用户id';

COMMENT ON TABLE "cr_business_template" IS '训练模板';

COMMENT ON COLUMN "cr_business_template"."business_type" IS '所属训练类型';

COMMENT ON COLUMN "cr_business_template"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_business_template"."create_user_id" IS '创建人uuid';

COMMENT ON COLUMN "cr_business_template"."description" IS '描述';

COMMENT ON COLUMN "cr_business_template"."difficulty" IS '训练难度';

COMMENT ON COLUMN "cr_business_template"."local_scene_id" IS '后台测试场景id';

COMMENT ON COLUMN "cr_business_template"."logo" IS '封面url';

COMMENT ON COLUMN "cr_business_template"."name" IS '训练模板名称';

COMMENT ON COLUMN "cr_business_template"."operation_auth_ids" IS '拥有后台操作权限的用户id';

COMMENT ON COLUMN "cr_business_template"."operation_auth_type" IS '权限状态';

COMMENT ON COLUMN "cr_business_template"."read_auth_ids" IS '拥有前台预览权限的组织id';

COMMENT ON COLUMN "cr_business_template"."read_auth_type" IS '浏览权限';

COMMENT ON COLUMN "cr_business_template"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_business_template"."topo_id" IS '关联topo uuid';

COMMENT ON COLUMN "cr_business_template"."update_time" IS '更新时间';

COMMENT ON TABLE "cr_constants_config" IS '常量配置';

COMMENT ON COLUMN "cr_constants_config"."cn_name" IS '中文名';

COMMENT ON COLUMN "cr_constants_config"."code" IS '状态码';

COMMENT ON COLUMN "cr_constants_config"."en_name" IS '英文名';

COMMENT ON COLUMN "cr_constants_config"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_constants_config"."type" IS '类别码';

COMMENT ON TABLE "cr_eval_algorithm" IS '测评算法';

COMMENT ON COLUMN "cr_eval_algorithm"."algorithm_language" IS '算法语言
0：python';

COMMENT ON COLUMN "cr_eval_algorithm"."builtin" IS '0为内置算法，1为用户自定义算法';

COMMENT ON COLUMN "cr_eval_algorithm"."code" IS '算法内容';

COMMENT ON COLUMN "cr_eval_algorithm"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_eval_algorithm"."create_user_id" IS '创建人uuid';

COMMENT ON COLUMN "cr_eval_algorithm"."description" IS '描述';

COMMENT ON COLUMN "cr_eval_algorithm"."mode" IS '0为其他算法，1为层次分析法';

COMMENT ON COLUMN "cr_eval_algorithm"."name" IS '名称';

COMMENT ON COLUMN "cr_eval_algorithm"."params" IS '参数列表';

COMMENT ON COLUMN "cr_eval_algorithm"."params_num" IS '参数个数';

COMMENT ON COLUMN "cr_eval_algorithm"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_eval_algorithm"."state" IS ' 0：代表正常，1：代表编辑过，页面看不到';

COMMENT ON COLUMN "cr_eval_algorithm"."type" IS '格式化算法：1，匹配算法：2，评分算法：3，聚合算法：4';

COMMENT ON COLUMN "cr_eval_algorithm"."update_time" IS '更新时间';

COMMENT ON TABLE "cr_eval_algorithm_ref" IS '算法关联';

COMMENT ON COLUMN "cr_eval_algorithm_ref"."algorithm_language" IS '算法语言';

COMMENT ON COLUMN "cr_eval_algorithm_ref"."code" IS '算法内容';

COMMENT ON COLUMN "cr_eval_algorithm_ref"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_eval_algorithm_ref"."description" IS '描述';

COMMENT ON COLUMN "cr_eval_algorithm_ref"."matrix_data" IS '层次分析法矩阵参数';

COMMENT ON COLUMN "cr_eval_algorithm_ref"."mode" IS '0为其他算法，1为层次分析法';

COMMENT ON COLUMN "cr_eval_algorithm_ref"."name" IS '名称';

COMMENT ON COLUMN "cr_eval_algorithm_ref"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_eval_algorithm_ref"."type" IS '格式化算法：1，匹配算法：2，评分算法：3，聚合算法：4';

COMMENT ON COLUMN "cr_eval_algorithm_ref"."update_time" IS '更新时间';

COMMENT ON TABLE "cr_eval_probe_data" IS '探针数据';

COMMENT ON COLUMN "cr_eval_probe_data"."collection_time" IS '收集时间';

COMMENT ON COLUMN "cr_eval_probe_data"."content" IS '虚拟机信息';

COMMENT ON COLUMN "cr_eval_probe_data"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_eval_probe_data"."device_id" IS '虚拟机uuid';

COMMENT ON COLUMN "cr_eval_probe_data"."device_ip" IS '虚拟机ip';

COMMENT ON COLUMN "cr_eval_probe_data"."probe_id" IS '探针id';

COMMENT ON COLUMN "cr_eval_probe_data"."scene_id" IS '场景uuid';

COMMENT ON COLUMN "cr_eval_probe_data"."update_time" IS '更新时间';

COMMENT ON COLUMN "cr_eval_result"."exe_cycle" IS '执行轮次';

COMMENT ON COLUMN "cr_eval_result"."fifth_index_res" IS '五级指标得分';

COMMENT ON COLUMN "cr_eval_result"."first_index_res" IS '一级指标得分';

COMMENT ON COLUMN "cr_eval_result"."fourth_index_res" IS '四级指标得分';

COMMENT ON COLUMN "cr_eval_result"."record" IS '关联评估运行实例id';

COMMENT ON COLUMN "cr_eval_result"."resource_id" IS '资源id';

COMMENT ON COLUMN "cr_eval_result"."second_index_res" IS '二级指标得分';

COMMENT ON COLUMN "cr_eval_result"."third_index_res" IS '三级指标得分';

COMMENT ON TABLE "cr_eval_system" IS '测评指标体系详情';

COMMENT ON COLUMN "cr_eval_system"."connlist" IS '连接线';

COMMENT ON COLUMN "cr_eval_system"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_eval_system"."create_user_id" IS '创建人uuid';

COMMENT ON COLUMN "cr_eval_system"."description" IS '描述';

COMMENT ON COLUMN "cr_eval_system"."first_index_num" IS '一级指标数';

COMMENT ON COLUMN "cr_eval_system"."name" IS '名称';

COMMENT ON COLUMN "cr_eval_system"."second_index_num" IS '二级指标数';

COMMENT ON COLUMN "cr_eval_system"."third_index_num" IS '三级指标数';

COMMENT ON COLUMN "cr_eval_system"."update_time" IS '更新时间';

COMMENT ON TABLE "cr_eval_task" IS '测评任务详情';

COMMENT ON COLUMN "cr_eval_task"."copyed_topo_id" IS '拓扑id';

COMMENT ON COLUMN "cr_eval_task"."create_time" IS '开始时间';

COMMENT ON COLUMN "cr_eval_task"."create_user_id" IS '创建人id';

COMMENT ON COLUMN "cr_eval_task"."enable_recording" IS '是否录屏';

COMMENT ON COLUMN "cr_eval_task"."end_time" IS '结束时间';

COMMENT ON COLUMN "cr_eval_task"."local_scene_id" IS '启动场景id';

COMMENT ON COLUMN "cr_eval_task"."mp_scene_id" IS '中台场景id';

COMMENT ON COLUMN "cr_eval_task"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_eval_task"."start_time" IS '开始时间';

COMMENT ON COLUMN "cr_eval_task"."task_name" IS '测评实例名称';

COMMENT ON COLUMN "cr_eval_task"."template_id" IS '测评模板id';

COMMENT ON COLUMN "cr_eval_task"."template_name" IS '模板名称';

COMMENT ON COLUMN "cr_eval_task"."update_time" IS '结束时间';

COMMENT ON TABLE "cr_eval_task_step" IS '测评任务步骤详情';

COMMENT ON COLUMN "cr_eval_task_step"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_eval_task_step"."end_time" IS '结束时间';

COMMENT ON COLUMN "cr_eval_task_step"."process" IS '步骤内容';

COMMENT ON COLUMN "cr_eval_task_step"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_eval_task_step"."stage" IS '任务步骤';

COMMENT ON COLUMN "cr_eval_task_step"."start_time" IS '开始时间';

COMMENT ON COLUMN "cr_eval_task_step"."task_id" IS '测评任务id';

COMMENT ON COLUMN "cr_eval_task_step"."title" IS '步骤标题';

COMMENT ON COLUMN "cr_eval_task_step"."type" IS '问题类型';

COMMENT ON COLUMN "cr_eval_task_step"."update_time" IS '更新时间';

COMMENT ON TABLE "cr_eval_template" IS '测评模板';

COMMENT ON COLUMN "cr_eval_template"."category_code" IS '测评类型';

COMMENT ON COLUMN "cr_eval_template"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_eval_template"."create_user_id" IS '创建人uuid';

COMMENT ON COLUMN "cr_eval_template"."current_task_id" IS '当前执行任务uuid';

COMMENT ON COLUMN "cr_eval_template"."description" IS '描述';

COMMENT ON COLUMN "cr_eval_template"."enable_recording" IS '是否录屏';

COMMENT ON COLUMN "cr_eval_template"."logo" IS '封面url';

COMMENT ON COLUMN "cr_eval_template"."name" IS '测评名称';

COMMENT ON COLUMN "cr_eval_template"."operation_auth_ids" IS '拥有后台操作权限的用户id';

COMMENT ON COLUMN "cr_eval_template"."operation_auth_type" IS '授权类型';

COMMENT ON COLUMN "cr_eval_template"."read_auth_ids" IS '拥有前台预览权限的组织id';

COMMENT ON COLUMN "cr_eval_template"."read_auth_type" IS '授权可见类型';

COMMENT ON COLUMN "cr_eval_template"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_eval_template"."step_config" IS '测评步骤';

COMMENT ON COLUMN "cr_eval_template"."topo_id" IS '关联拓扑uuid';

COMMENT ON COLUMN "cr_eval_template"."update_time" IS '更新时间';

COMMENT ON TABLE "cr_eval_variable_ref_probe_data" IS '评估结果数据';

COMMENT ON COLUMN "cr_eval_variable_ref_probe_data"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_eval_variable_ref_probe_data"."formatted_value" IS '获取数据';

COMMENT ON COLUMN "cr_eval_variable_ref_probe_data"."probe_data_id" IS '探针数据id';

COMMENT ON COLUMN "cr_eval_variable_ref_probe_data"."status" IS '数据状态';

COMMENT ON COLUMN "cr_eval_variable_ref_probe_data"."time" IS '数据获取时间';

COMMENT ON COLUMN "cr_eval_variable_ref_probe_data"."update_time" IS '更新时间';

COMMENT ON COLUMN "cr_eval_variable_ref_probe_data"."variable_id" IS '评估结果id';

COMMENT ON TABLE "cr_mission_scheme_template" IS '后台任务方案';

COMMENT ON COLUMN "cr_mission_scheme_template"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_mission_scheme_template"."create_user_id" IS '创建人id';

COMMENT ON COLUMN "cr_mission_scheme_template"."description" IS '描述';

COMMENT ON COLUMN "cr_mission_scheme_template"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_mission_scheme_template"."template_config" IS '任务模板配置';

COMMENT ON COLUMN "cr_mission_scheme_template"."template_data" IS '任务阶段数据';

COMMENT ON COLUMN "cr_mission_scheme_template"."title" IS '方案标题';

COMMENT ON COLUMN "cr_mission_scheme_template"."update_time" IS '更新时间';

COMMENT ON COLUMN "cr_mission_situation"."area" IS '靶标区域';

COMMENT ON COLUMN "cr_mission_situation"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_mission_situation"."extra_effect" IS '额外的效果';

COMMENT ON COLUMN "cr_mission_situation"."mission_template_id" IS '任务模板名称';

COMMENT ON COLUMN "cr_mission_situation"."name" IS '任务名称';

COMMENT ON COLUMN "cr_mission_situation"."source_device" IS '源目标';

COMMENT ON COLUMN "cr_mission_situation"."target_device" IS '动效目标节点';

COMMENT ON COLUMN "cr_mission_situation"."ts_desc" IS '动效描述';

COMMENT ON COLUMN "cr_mission_situation"."ts_id" IS '动效id';

COMMENT ON COLUMN "cr_mission_situation"."ts_type" IS '动效类型';

COMMENT ON COLUMN "cr_mission_situation"."update_time" IS '更新时间';

COMMENT ON TABLE "cr_radar_label" IS '雷达图维度表';

COMMENT ON COLUMN "cr_radar_label"."label" IS '维度';

COMMENT ON TABLE "cr_rb_common_device" IS '红蓝常用资产表';

COMMENT ON TABLE "cr_rb_device" IS '红蓝对抗用户-登录节点关联';

COMMENT ON COLUMN "cr_rb_device"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_rb_device"."create_user_id" IS '用户id';

COMMENT ON COLUMN "cr_rb_device"."device_id" IS '节点id';

COMMENT ON COLUMN "cr_rb_device"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_rb_device"."room_id" IS '房间id';

COMMENT ON COLUMN "cr_rb_device"."update_time" IS '结束时间';

COMMENT ON TABLE "cr_score" IS '训练评分';

COMMENT ON COLUMN "cr_score"."comment" IS '评价';

COMMENT ON COLUMN "cr_score"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_score"."final_score" IS '最终得分';

COMMENT ON COLUMN "cr_score"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_score"."room_id" IS '房间id';

COMMENT ON COLUMN "cr_score"."skill_percentage" IS '技能分占比';

COMMENT ON COLUMN "cr_score"."skill_score" IS '技能分';

COMMENT ON COLUMN "cr_score"."training_percentage" IS '训练分占比';

COMMENT ON COLUMN "cr_score"."training_score" IS '训练得分';

COMMENT ON COLUMN "cr_score"."update_time" IS '更新时间';

COMMENT ON COLUMN "cr_score"."user_id" IS '用户id';

COMMENT ON TABLE "cr_single_room" IS '单兵训练房间详情';

COMMENT ON COLUMN "cr_single_room"."business_template_id" IS '训练模板id';

COMMENT ON COLUMN "cr_single_room"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_single_room"."end_time" IS '结束时间';

COMMENT ON COLUMN "cr_single_room"."is_finish" IS '结束状态';

COMMENT ON COLUMN "cr_single_room"."local_scene_id" IS '本地场景id';

COMMENT ON COLUMN "cr_single_room"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_single_room"."start_time" IS '开始时间';

COMMENT ON COLUMN "cr_single_room"."training_record" IS '训练记录';

COMMENT ON COLUMN "cr_single_room"."update_time" IS '结束时间';

COMMENT ON COLUMN "cr_single_room"."user_id" IS '参与用户id';

CREATE TABLE "cr_tool_toolcategory"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NULL,
    "cn_name" VARCHAR(64) NULL,
    "en_name" VARCHAR(64) NULL,
    "serial_version_uid" CLOB NULL
);
CREATE TABLE "cr_tool_toolfile"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "file_name" VARCHAR(256) NULL,
    "save_path" VARCHAR(256) NULL,
    "file_url" VARCHAR(64) NULL
);
CREATE TABLE "cr_train_record"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "business_type" BIGINT NOT NULL,
    "business_template_id" VARCHAR(64) NOT NULL,
    "room_name" VARCHAR(255) NULL,
    "room_id" VARCHAR(64) NULL,
    "user_id" VARCHAR(64) NOT NULL,
    "start_time" TIMESTAMP(0) NULL,
    "end_time" TIMESTAMP(0) NULL,
    "extra" CLOB NULL
);
CREATE TABLE "file_chunk"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "md5" VARCHAR(255) NOT NULL,
    "filename" VARCHAR(255) NOT NULL,
    "filesize" BIGINT NOT NULL,
    "total" INT NOT NULL,
    "index" INT NOT NULL,
    "status" TINYINT NULL,
    "create_time" TIMESTAMP(0) NULL
);
CREATE TABLE "init_rtks_course_related"
(
    "resource_id" VARCHAR(64) NOT NULL,
    "related_id" VARCHAR(64) NOT NULL,
    "type" INT NOT NULL,
    "weight" INT NOT NULL
);
CREATE TABLE "init_rtks_knowledge"
(
    "id" INT IDENTITY(363,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "category_id" VARCHAR(40) NOT NULL,
    "content" VARCHAR(512) NOT NULL,
    "courses" TEXT NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "init_rtks_knowledge_category"
(
    "id" INT IDENTITY(20,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "content" VARCHAR(512) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "init_rtks_level"
(
    "id" INT IDENTITY(43,1) NOT NULL,
    "resource_id" VARCHAR(255) NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "description" VARCHAR(255) NULL,
    "parent" VARCHAR(255) NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "frame" VARCHAR(255) NULL
);
CREATE TABLE "init_rtks_role"
(
    "id" INT IDENTITY(126,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "description" TEXT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL,
    "level_id" VARCHAR(64) NOT NULL,
    "category" VARCHAR(64) NOT NULL,
    "task_ids" TEXT NOT NULL,
    "knowledge_id_weights" TEXT NOT NULL,
    "knowledge_ids" TEXT NOT NULL,
    "skill_id_weights" TEXT NOT NULL,
    "skill_ids" TEXT NOT NULL,
    "ability_ids" TEXT NOT NULL,
    "skill_courses" TEXT NULL,
    "knowledge_courses" TEXT NULL,
    "knowledge_weight" TINYINT DEFAULT 50
        NOT NULL,
    "skill_missions" TEXT DEFAULT '''[]'''
        NULL
);
CREATE TABLE "init_rtks_role_skill_relation"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "role_id" VARCHAR(32) NOT NULL,
    "skill_id" VARCHAR(32) NOT NULL,
    "weight" TINYINT NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "init_rtks_task"
(
    "id" INT IDENTITY(134,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "content" VARCHAR(512) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "local_scene"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(32) NOT NULL,
    "mp_scene_id" VARCHAR(32) NULL,
    "business_code" VARCHAR(11) NOT NULL,
    "start_time" TIMESTAMP(0) NULL,
    "end_time" TIMESTAMP(0) NULL,
    "destroy_time" TIMESTAMP(0) NULL,
    "destroy_mode" INT NULL,
    "business_instance_id" VARCHAR(65) NOT NULL,
    "scene_status" INT NULL,
    "pause_time" TIMESTAMP(0) NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NULL,
    "topo_id" VARCHAR(64) NULL,
    "user_id" VARCHAR(64) NULL,
    "scene_ttl" INT NULL,
    "node_count" INT NULL,
    "meta" CLOB NULL,
    "scene_name" VARCHAR(256) NULL,
    "scene_attr" INT NULL
);
CREATE TABLE "oj_classroom"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "start_time" TIMESTAMP(0) NULL,
    "end_time" TIMESTAMP(0) NULL,
    "course_id" VARCHAR(64) NOT NULL,
    "lesson_id" VARCHAR(64) NOT NULL,
    "organization_id" VARCHAR(64) NOT NULL,
    "organization_ids" CLOB NOT NULL,
    "create_user_id" VARCHAR(64) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL,
    "train_plan_id" VARCHAR(64) NULL
);
CREATE TABLE "oj_classroom_group_archive"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "name" VARCHAR(128) NOT NULL,
    "groups" CLOB NOT NULL,
    "classroom_id" VARCHAR(64) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "oj_classroom_group_info"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "classroom_id" VARCHAR(64) NOT NULL,
    "group_no" INT NOT NULL,
    "group_user_ids" CLOB NOT NULL,
    "destroy_delay" INT NULL,
    "scene_share_mode" INT NULL,
    "lesson_id" VARCHAR(64) NOT NULL,
    "topo_id" VARCHAR(64) NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "oj_classroom_sign"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "classroom_id" VARCHAR(64) NOT NULL,
    "user_id" VARCHAR(64) NOT NULL,
    "sign_in" INT NOT NULL,
    "sign_in_time" TIMESTAMP(0) NOT NULL,
    "need_help" INT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL,
    "node_id" VARCHAR(64) NULL
);
CREATE TABLE "oj_comment"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "reply_id" VARCHAR(64) NULL,
    "business_id" VARCHAR(64) NOT NULL,
    "theme_name" VARCHAR(128) NOT NULL,
    "user_id" VARCHAR(64) NOT NULL,
    "content" VARCHAR(1024) NOT NULL,
    "business_type" INT NOT NULL,
    "root_id" VARCHAR(64) NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "oj_comment_like"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "user_id" VARCHAR(64) NOT NULL,
    "comment_id" VARCHAR(64) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "oj_course"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "builtin" INT NOT NULL,
    "operation_auth_type" INT NOT NULL,
    "operation_auth_ids" TEXT NULL,
    "read_auth_type" INT NOT NULL,
    "read_auth_ids" TEXT NULL,
    "name" VARCHAR(256) NOT NULL,
    "description" CLOB NULL,
    "logo" VARCHAR(512) NULL,
    "outline" CLOB NULL,
    "difficulty" INT NOT NULL,
    "create_user_id" VARCHAR(64) NOT NULL,
    "modify_user_id" VARCHAR(64) NOT NULL,
    "public" INT NOT NULL,
    "course_type" INT NULL,
    "heat" INT NOT NULL,
    "lecturer" VARCHAR(64) NULL,
    "score" INT NULL,
    "course_num" VARCHAR(64) NULL,
    "category_id" VARCHAR(64) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "oj_course_category"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "name" VARCHAR(128) NOT NULL,
    "course_type" INT NOT NULL,
    "code" VARCHAR(2096) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "parent_id" VARCHAR(64) NULL
);
CREATE TABLE "oj_course_lesson"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "course_id" VARCHAR(64) NOT NULL,
    "builtin" INT NOT NULL,
    "name" VARCHAR(256) NOT NULL,
    "type" INT NULL,
    "handout_type" INT NULL,
    "handout_url" CLOB NULL,
    "video_url" VARCHAR(512) NULL,
    "video_check_point" TEXT NULL,
    "difficulty" INT NOT NULL,
    "public" INT NOT NULL,
    "attachment_ids" VARCHAR(1024) NULL,
    "compulsory" INT NOT NULL,
    "duration" INT NULL,
    "destroy_delay" INT NULL,
    "topo_id" VARCHAR(64) NULL,
    "knowledge" VARCHAR(2048) NULL,
    "scene_share_mode" INT NULL,
    "origin_markdown_file" CLOB NULL,
    "create_user_id" VARCHAR(64) NULL,
    "heat" INT NOT NULL,
    "paper_id" VARCHAR(64) NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL,
    "paper_public" INT NULL
);
CREATE TABLE "oj_course_study_history"
(
    "id" INT IDENTITY(5,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "course_id" VARCHAR(64) NOT NULL,
    "course_name" VARCHAR(256) NOT NULL,
    "user_id" VARCHAR(64) NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "oj_lesson_config"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "index" INT NOT NULL,
    "parent_id" VARCHAR(64) NULL,
    "name" VARCHAR(256) NULL,
    "node_type" INT NOT NULL,
    "course_id" VARCHAR(64) NOT NULL,
    "lesson_id" VARCHAR(64) NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "oj_lesson_paper_evaluate"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "lesson_id" VARCHAR(64) NOT NULL,
    "score" NUMBER(11,1) NOT NULL,
    "record" CLOB NOT NULL,
    "submit_user_id" VARCHAR(64) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL,
    "total_score" NUMBER(11,1) NOT NULL,
    "level" INT NULL,
    "accuracy" VARCHAR(64) NULL,
    "corrector" VARCHAR(64) NULL
);
CREATE TABLE "oj_lesson_record"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "lesson_id" VARCHAR(64) NOT NULL,
    "user_id" VARCHAR(64) NOT NULL,
    "course_id" VARCHAR(64) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL,
    "learned_type" INT DEFAULT 0
        NOT NULL,
    "room_id" VARCHAR(64) NULL
);
CREATE TABLE "oj_local_scene"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "user_id" VARCHAR(64) NOT NULL,
    "shared" INT NOT NULL,
    "from_web" INT NULL,
    "flags" CLOB NULL,
    "business_id" VARCHAR(128) NOT NULL,
    "business_type" INT NOT NULL,
    "local_scene_id" VARCHAR(64) NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "oj_note"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "business_type" INT NOT NULL,
    "content" CLOB NOT NULL,
    "user_id" VARCHAR(64) NOT NULL,
    "business_id" VARCHAR(64) NOT NULL,
    "note_type" INT NOT NULL,
    "operation_auth_type" INT NOT NULL,
    "operation_auth_ids" TEXT NULL,
    "read_auth_type" INT NOT NULL,
    "read_auth_ids" TEXT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL,
    "public" INT NOT NULL,
    "is_submit" INT NULL
);
CREATE TABLE "oj_note_evaluate"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "score" INT NOT NULL,
    "passed" INT NOT NULL,
    "evaluate_comment" VARCHAR(2048) NULL,
    "evaluator_id" VARCHAR(64) NOT NULL,
    "evaluate_time" TIMESTAMP(0) NOT NULL,
    "note_id" VARCHAR(64) NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "oj_paper"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "name" VARCHAR(128) NOT NULL,
    "description" VARCHAR(1024) NULL,
    "logo" VARCHAR(512) NULL,
    "practice_count" INT NOT NULL,
    "total_score" NUMBER(11,1) NULL,
    "public" INT NOT NULL,
    "type" INT NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL,
    "operation_auth_type" INT NULL,
    "operation_auth_ids" TEXT NULL,
    "read_auth_type" INT NULL,
    "read_auth_ids" TEXT NULL
);
CREATE TABLE "oj_paper_practice_theory"
(
    "id" INT IDENTITY(298,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "snapshot" CLOB NOT NULL,
    "origin_id" VARCHAR(64) NOT NULL,
    "paper_id" VARCHAR(64) NOT NULL,
    "submit_answer" CLOB NULL,
    "solved" INT NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "oj_practice_category"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "name" VARCHAR(128) NOT NULL,
    "practice_type" INT NOT NULL,
    "create_user_id" VARCHAR(64) NOT NULL,
    "code" VARCHAR(1024) NOT NULL,
    "parent_id" VARCHAR(64) NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "oj_practice_mistake"
(
    "id" INT IDENTITY(13,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "practice_id" VARCHAR(64) NOT NULL,
    "practice_type" INT NOT NULL,
    "user_id" VARCHAR(64) NOT NULL,
    "correct" INT NOT NULL,
    "incorrect" INT NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "oj_practice_operation"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "category_id" VARCHAR(64) NOT NULL,
    "builtin" INT NOT NULL,
    "practice_set_id" VARCHAR(64) NULL,
    "title" VARCHAR(255) NOT NULL,
    "content" CLOB NULL,
    "answer" VARCHAR(1024) NULL,
    "difficulty" INT NOT NULL,
    "knowledge" VARCHAR(1024) NULL,
    "public" INT NOT NULL,
    "score" INT NOT NULL,
    "attachment_id" VARCHAR(1024) NULL,
    "writeup" CLOB NULL,
    "writeup_public" INT NULL,
    "create_user_id" VARCHAR(64) NOT NULL,
    "dynamic_env" INT NULL,
    "topo_id" VARCHAR(64) NULL,
    "scene_share_mode" INT NULL,
    "dynamic_flag" INT NULL,
    "destroy_delay" INT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "oj_practice_operation_ref"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "snapshot" CLOB NOT NULL,
    "origin_id" VARCHAR(64) NOT NULL,
    "paper_id" VARCHAR(64) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "oj_practice_paper"
(
    "id" INT IDENTITY(35,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "practice_status" VARCHAR(64) NOT NULL,
    "count" INT NOT NULL,
    "difficulty" VARCHAR(64) NOT NULL,
    "type" VARCHAR(255) NOT NULL,
    "direction" VARCHAR(3000) NOT NULL,
    "is_finish" INT NOT NULL,
    "user_id" VARCHAR(64) NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "ai_create_flag" TINYINT DEFAULT 0
        NOT NULL
);
CREATE TABLE "oj_practice_random_flag"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "user_id" VARCHAR(64) NOT NULL,
    "attachment_id" VARCHAR(64) NOT NULL,
    "practice_id" VARCHAR(64) NOT NULL,
    "flag" VARCHAR(1028) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL,
    "type" INT NULL
);
CREATE TABLE "oj_practice_set"
(
    "id" INT IDENTITY(16,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "builtin" INT NOT NULL,
    "practice_type" INT NOT NULL,
    "name" VARCHAR(128) NOT NULL,
    "logo" VARCHAR(512) NULL,
    "public" INT NOT NULL,
    "create_user_id" VARCHAR(64) NOT NULL,
    "operation_auth_type" INT NOT NULL,
    "operation_auth_ids" TEXT NULL,
    "read_auth_type" INT NOT NULL,
    "read_auth_ids" TEXT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL,
    "practice_count" INT NULL
);
CREATE TABLE "oj_practice_solved"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "submit_user_id" VARCHAR(64) NOT NULL,
    "submit_answer" CLOB NOT NULL,
    "score" NUMBER(11,0) NOT NULL,
    "total_score" NUMBER(11,0) NOT NULL,
    "solved" INT NOT NULL,
    "practice_id" VARCHAR(64) NOT NULL,
    "practice_type" INT NOT NULL,
    "collection" INT NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL,
    "is_paper" INT NOT NULL
);
CREATE TABLE "oj_practice_theory"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "builtin" INT NOT NULL,
    "category_id" VARCHAR(64) NOT NULL,
    "title" VARCHAR(2048) NOT NULL,
    "content" VARCHAR(960) NOT NULL,
    "answer" VARCHAR(2048) NOT NULL,
    "difficulty" INT NOT NULL,
    "knowledge" VARCHAR(1024) NULL,
    "public" INT NOT NULL,
    "score" INT NOT NULL,
    "create_user_id" VARCHAR(64) NOT NULL,
    "theory_type" INT NOT NULL,
    "option" CLOB NULL,
    "blank" VARCHAR(2048) NULL,
    "keyword" VARCHAR(1024) NULL,
    "practice_set_id" VARCHAR(64) NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "oj_practice_theory_ref"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "snapshot" CLOB NOT NULL,
    "origin_id" VARCHAR(64) NOT NULL,
    "option_random" INT NULL,
    "paper_id" VARCHAR(64) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "oj_resource_collection"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "user_id" VARCHAR(64) NOT NULL,
    "business_id" VARCHAR(64) NOT NULL,
    "business_type" INT NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "oj_train_plan"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "name" VARCHAR(256) NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "create_user_id" VARCHAR(64) NULL
);
CREATE TABLE "radar_label"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" INT NULL,
    "label" VARCHAR(255) NULL
);
CREATE TABLE "rtks_ability"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "content" VARCHAR(512) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "rtks_attack"
(
    "id" INT IDENTITY(225,1) NOT NULL,
    "resource_id" VARCHAR(32) NOT NULL,
    "category_id" VARCHAR(32) NOT NULL,
    "name_zh" VARCHAR(255) NOT NULL,
    "name_en" VARCHAR(255) NOT NULL
);
CREATE TABLE "rtks_attack_category"
(
    "id" INT IDENTITY(15,1) NOT NULL,
    "resource_id" VARCHAR(32) NOT NULL,
    "name_zh" VARCHAR(255) NOT NULL,
    "name_en" VARCHAR(255) NOT NULL
);
CREATE TABLE "rtks_attack_ics"
(
    "id" INT IDENTITY(91,1) NOT NULL,
    "resource_id" VARCHAR(32) NOT NULL,
    "category_id" VARCHAR(32) NOT NULL,
    "name_zh" VARCHAR(255) NOT NULL,
    "name_en" VARCHAR(255) NOT NULL
);
CREATE TABLE "rtks_attack_ics_category"
(
    "id" INT IDENTITY(13,1) NOT NULL,
    "resource_id" VARCHAR(32) NOT NULL,
    "name_zh" VARCHAR(255) NOT NULL,
    "name_en" VARCHAR(255) NOT NULL
);
CREATE TABLE "rtks_attack_ics_train_relation"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(32) NULL,
    "attack_ics_id" VARCHAR(32) NOT NULL,
    "business_template_id" VARCHAR(32) NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
ALTER TABLE "cr_tool_toolcategory" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_tool_toolfile" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "cr_train_record" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "file_chunk" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "init_rtks_knowledge" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "init_rtks_knowledge_category" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "init_rtks_level" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "init_rtks_role" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "init_rtks_role_skill_relation" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "init_rtks_task" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "local_scene" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_classroom" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_classroom_group_archive" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_classroom_group_info" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_classroom_sign" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_comment" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_comment_like" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_course" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_course_category" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_course_lesson" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_course_study_history" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_lesson_config" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_lesson_paper_evaluate" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_lesson_record" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_local_scene" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_note" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_note_evaluate" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_paper" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_paper_practice_theory" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_practice_category" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_practice_mistake" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_practice_operation" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_practice_operation_ref" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_practice_paper" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_practice_random_flag" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_practice_set" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_practice_solved" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_practice_theory" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_practice_theory_ref" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_resource_collection" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "oj_train_plan" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "radar_label" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_ability" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_attack" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_attack_category" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_attack_ics" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_attack_ics_category" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_attack_ics_train_relation" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

CREATE INDEX "md5"
    ON "file_chunk"("md5");
ALTER INDEX "md5" VISIBLE;

CREATE INDEX "course_id"
    ON "init_rtks_course_related"("resource_id");
ALTER INDEX "course_id" VISIBLE;

CREATE INDEX "related_id"
    ON "init_rtks_course_related"("related_id");
ALTER INDEX "related_id" VISIBLE;

CREATE INDEX "role"
    ON "init_rtks_role_skill_relation"("role_id");
ALTER INDEX "role" VISIBLE;

CREATE INDEX "resource_id"
    ON "oj_paper_practice_theory"("resource_id");
ALTER INDEX "resource_id" VISIBLE;

CREATE INDEX "INDEX352751231431700"
    ON "oj_practice_mistake"("resource_id");
ALTER INDEX "INDEX352751231431700" VISIBLE;

CREATE INDEX "INDEX352751319842900"
    ON "oj_practice_paper"("resource_id");
ALTER INDEX "INDEX352751319842900" VISIBLE;

CREATE INDEX "INDEX352751410345600"
    ON "rtks_attack"("resource_id");
ALTER INDEX "INDEX352751410345600" VISIBLE;

CREATE INDEX "category_id"
    ON "rtks_attack"("category_id");
ALTER INDEX "category_id" VISIBLE;

CREATE INDEX "INDEX352751560386900"
    ON "rtks_attack_category"("resource_id");
ALTER INDEX "INDEX352751560386900" VISIBLE;

CREATE INDEX "INDEX352751650178000"
    ON "rtks_attack_ics"("resource_id");
ALTER INDEX "INDEX352751650178000" VISIBLE;

CREATE INDEX "INDEX352751740715900"
    ON "rtks_attack_ics"("category_id");
ALTER INDEX "INDEX352751740715900" VISIBLE;

CREATE INDEX "INDEX352751860220300"
    ON "rtks_attack_ics_category"("resource_id");
ALTER INDEX "INDEX352751860220300" VISIBLE;

CREATE INDEX "attack_ics_id"
    ON "rtks_attack_ics_train_relation"("attack_ics_id");
ALTER INDEX "attack_ics_id" VISIBLE;

CREATE INDEX "business_template_id"
    ON "rtks_attack_ics_train_relation"("business_template_id");
ALTER INDEX "business_template_id" VISIBLE;

CREATE INDEX "aggregating_algorithm_id"
    ON "cr_eval_aggregation"("aggregating_algorithm_id");
ALTER INDEX "aggregating_algorithm_id" VISIBLE;

CREATE INDEX "eval_indicator_id"
    ON "cr_eval_aggregation"("eval_indicator_id");
ALTER INDEX "eval_indicator_id" VISIBLE;

CREATE INDEX "INDEX352820150035300"
    ON "cr_eval_collection"("eval_indicator_id");
ALTER INDEX "INDEX352820150035300" VISIBLE;

CREATE INDEX "detection_algorithm_id"
    ON "cr_eval_collection"("detection_algorithm_id");
ALTER INDEX "detection_algorithm_id" VISIBLE;

CREATE INDEX "format_algorithm_id"
    ON "cr_eval_collection"("format_algorithm_id");
ALTER INDEX "format_algorithm_id" VISIBLE;

CREATE INDEX "score_algorithm_id"
    ON "cr_eval_collection"("score_algorithm_id");
ALTER INDEX "score_algorithm_id" VISIBLE;

CREATE INDEX "INDEX352820224787800"
    ON "cr_eval_index"("eval_indicator_id");
ALTER INDEX "INDEX352820224787800" VISIBLE;

CREATE INDEX "INDEX352820257149900"
    ON "cr_eval_index"("aggregating_algorithm_id");
ALTER INDEX "INDEX352820257149900" VISIBLE;

CREATE INDEX "eval_system_id"
    ON "cr_eval_index"("eval_system_id");
ALTER INDEX "eval_system_id" VISIBLE;

CREATE INDEX "record_id"
    ON "cr_eval_index_ref"("record_id");
ALTER INDEX "record_id" VISIBLE;

CREATE INDEX "INDEX352820373607200"
    ON "cr_eval_indicator"("aggregating_algorithm_id");
ALTER INDEX "INDEX352820373607200" VISIBLE;

CREATE INDEX "INDEX352820420892500"
    ON "cr_eval_record"("task_id");
ALTER INDEX "INDEX352820420892500" VISIBLE;

CREATE INDEX "INDEX352820449534300"
    ON "cr_eval_variable_ref"("record_id");
ALTER INDEX "INDEX352820449534300" VISIBLE;

CREATE INDEX "INDEX352820508973400"
    ON "cr_mark"("room_id");
ALTER INDEX "INDEX352820508973400" VISIBLE;

CREATE INDEX "INDEX352820599669400"
    ON "cr_mission"("room_id");
ALTER INDEX "INDEX352820599669400" VISIBLE;

CREATE INDEX "INDEX352820725732200"
    ON "cr_mission"("mission_template_id");
ALTER INDEX "INDEX352820725732200" VISIBLE;

CREATE INDEX "INDEX352820871717900"
    ON "cr_mission_template"("business_template_id");
ALTER INDEX "INDEX352820871717900" VISIBLE;

CREATE INDEX "template_id"
    ON "cr_mission_template"("template_id");
ALTER INDEX "template_id" VISIBLE;

CREATE INDEX "idx_room_id"
    ON "cr_notice"("room_id");
ALTER INDEX "idx_room_id" VISIBLE;

CREATE INDEX "create_user_id"
    ON "cr_rb_room"("create_user_id");
ALTER INDEX "create_user_id" VISIBLE;

CREATE INDEX "team_template_id"
    ON "cr_rb_room"("template_id");
ALTER INDEX "team_template_id" VISIBLE;

CREATE INDEX "training_status"
    ON "cr_rb_room"("training_status");
ALTER INDEX "training_status" VISIBLE;

CREATE INDEX "INDEX352821202143600"
    ON "cr_rb_template"("business_template_id");
ALTER INDEX "INDEX352821202143600" VISIBLE;

CREATE INDEX "business_instance_id"
    ON "cr_role"("room_id");
ALTER INDEX "business_instance_id" VISIBLE;

CREATE INDEX "INDEX352821413060500"
    ON "cr_task"("user_id");
ALTER INDEX "INDEX352821413060500" VISIBLE;

CREATE INDEX "INDEX352821503509700"
    ON "cr_task"("room_id");
ALTER INDEX "INDEX352821503509700" VISIBLE;

CREATE INDEX "mission_id"
    ON "cr_task"("mission_id");
ALTER INDEX "mission_id" VISIBLE;

CREATE INDEX "INDEX352821594606800"
    ON "cr_task_log"("user_id");
ALTER INDEX "INDEX352821594606800" VISIBLE;

CREATE INDEX "INDEX352821683965100"
    ON "cr_task_log"("room_id");
ALTER INDEX "INDEX352821683965100" VISIBLE;

CREATE INDEX "INDEX352821835160900"
    ON "cr_task_log"("mission_id");
ALTER INDEX "INDEX352821835160900" VISIBLE;

CREATE INDEX "action_code"
    ON "cr_task_log"("action_code");
ALTER INDEX "action_code" VISIBLE;

CREATE INDEX "INDEX352821925321700"
    ON "cr_task_submit"("room_id");
ALTER INDEX "INDEX352821925321700" VISIBLE;

CREATE INDEX "task_id"
    ON "cr_task_submit"("task_id");
ALTER INDEX "task_id" VISIBLE;

CREATE INDEX "INDEX352822076134800"
    ON "cr_team_room"("team_template_id");
ALTER INDEX "INDEX352822076134800" VISIBLE;

CREATE INDEX "INDEX352822165337200"
    ON "cr_team_template"("business_template_id");
ALTER INDEX "INDEX352822165337200" VISIBLE;

ALTER TABLE "init_rtks_knowledge" ADD UNIQUE("resource_id") ;

ALTER TABLE "init_rtks_knowledge_category" ADD UNIQUE("resource_id") ;

ALTER TABLE "init_rtks_level" ADD UNIQUE("resource_id") ;

ALTER TABLE "init_rtks_role" ADD UNIQUE("resource_id") ;

ALTER TABLE "init_rtks_task" ADD UNIQUE("resource_id") ;

ALTER TABLE "local_scene" ADD CONSTRAINT "businessInstanceUniq" UNIQUE("business_instance_id") ;

ALTER TABLE "local_scene" ADD CONSTRAINT "resourceIdUnique" UNIQUE("resource_id") ;

ALTER TABLE "oj_classroom" ADD CONSTRAINT "resource_uniq" UNIQUE("resource_id") ;

ALTER TABLE "oj_classroom_group_archive" ADD CONSTRAINT "room_name_uniq" UNIQUE("classroom_id","name") ;

ALTER TABLE "oj_classroom_group_info" ADD CONSTRAINT "room_group_uniq" UNIQUE("classroom_id","group_no") ;

ALTER TABLE "oj_classroom_group_info" ADD UNIQUE("resource_id") ;

ALTER TABLE "oj_classroom_sign" ADD CONSTRAINT "room_user_uniq" UNIQUE("classroom_id","user_id") ;

ALTER TABLE "oj_comment_like" ADD CONSTRAINT "user_comment_uniq" UNIQUE("user_id","comment_id") ;

ALTER TABLE "oj_course" ADD CONSTRAINT "name_uniq" UNIQUE("name") ;

ALTER TABLE "oj_course" ADD CONSTRAINT "resource_unq" UNIQUE("resource_id") ;

ALTER TABLE "oj_course_category" ADD CONSTRAINT "name" UNIQUE("name") ;

ALTER TABLE "oj_course_lesson" ADD CONSTRAINT "course_name_uniq" UNIQUE("course_id","name") ;

ALTER TABLE "oj_course_lesson" ADD CONSTRAINT "resourse_id_uniq" UNIQUE("resource_id") ;

ALTER TABLE "oj_course_study_history" ADD CONSTRAINT "course_user_uniq" UNIQUE("course_id","user_id") ;

ALTER TABLE "oj_lesson_paper_evaluate" ADD CONSTRAINT "lesson_user_uniq" UNIQUE("lesson_id","submit_user_id") ;

ALTER TABLE "oj_lesson_record" ADD CONSTRAINT "user_lesson_uniq" UNIQUE("user_id","lesson_id") ;

ALTER TABLE "oj_note" ADD CONSTRAINT "user_bt_bi_uniq" UNIQUE("user_id","note_type","business_type","business_id") ;

ALTER TABLE "oj_note" ADD CONSTRAINT "unique_index" UNIQUE("resource_id") ;

ALTER TABLE "oj_note_evaluate" ADD CONSTRAINT "note_id_uniq" UNIQUE("note_id") ;

ALTER TABLE "oj_paper" ADD CONSTRAINT "resource_id_uniq" UNIQUE("resource_id") ;

ALTER TABLE "oj_practice_category" ADD CONSTRAINT "type_name_uniq" UNIQUE("practice_type","name") ;

ALTER TABLE "oj_practice_category" ADD UNIQUE("resource_id") ;

ALTER TABLE "oj_practice_operation" ADD CONSTRAINT "set_title_uniq" UNIQUE("practice_set_id","title") ;

ALTER TABLE "oj_practice_operation" ADD UNIQUE("resource_id") ;

ALTER TABLE "oj_practice_operation_ref" ADD CONSTRAINT "paper_origin_uniq" UNIQUE("paper_id","origin_id") ;

ALTER TABLE "oj_practice_random_flag" ADD CONSTRAINT "prac_type_user_uniq" UNIQUE("practice_id","type","user_id") ;

ALTER TABLE "oj_practice_set" ADD UNIQUE("name") ;

ALTER TABLE "oj_practice_set" ADD CONSTRAINT "resource_idx" UNIQUE("resource_id") ;

ALTER TABLE "oj_practice_solved" ADD CONSTRAINT "user_type_practice_uniq" UNIQUE("submit_user_id","practice_type","practice_id") ;

ALTER TABLE "oj_practice_theory" ADD CONSTRAINT "set_content_uniq" UNIQUE("practice_set_id","content") ;

ALTER TABLE "oj_practice_theory" ADD UNIQUE("resource_id") ;

ALTER TABLE "oj_practice_theory_ref" ADD UNIQUE("paper_id","origin_id") ;

ALTER TABLE "oj_resource_collection" ADD CONSTRAINT "user_type_id_uniq" UNIQUE("user_id","business_type","business_id") ;

ALTER TABLE "oj_train_plan" ADD CONSTRAINT "user_name_uniq" UNIQUE("create_user_id","name") ;

ALTER TABLE "oj_train_plan" ADD UNIQUE("resource_id") ;

ALTER TABLE "rtks_ability" ADD UNIQUE("resource_id") ;

COMMENT ON TABLE "init_rtks_course_related" IS 'rtks知识点课程关联重置表';

COMMENT ON COLUMN "init_rtks_course_related"."related_id" IS '知识点id';

COMMENT ON COLUMN "init_rtks_course_related"."resource_id" IS '课程id';

COMMENT ON COLUMN "init_rtks_course_related"."type" IS '类型：1：知识点;2：技能点（已取消）';

COMMENT ON COLUMN "init_rtks_course_related"."weight" IS '权重';

COMMENT ON TABLE "init_rtks_knowledge" IS 'rtks知识点重置表';

COMMENT ON COLUMN "init_rtks_knowledge"."category_id" IS '类别id';

COMMENT ON COLUMN "init_rtks_knowledge"."content" IS '内容';

COMMENT ON COLUMN "init_rtks_knowledge"."courses" IS '关联课程列表';

COMMENT ON COLUMN "init_rtks_knowledge"."create_time" IS '创建时间';

COMMENT ON COLUMN "init_rtks_knowledge"."id" IS '自增id';

COMMENT ON COLUMN "init_rtks_knowledge"."resource_id" IS '资源id';

COMMENT ON COLUMN "init_rtks_knowledge"."update_time" IS '更新时间';

COMMENT ON TABLE "init_rtks_knowledge_category" IS 'rtks知识点类别重置表';

COMMENT ON COLUMN "init_rtks_knowledge_category"."content" IS '内容';

COMMENT ON COLUMN "init_rtks_knowledge_category"."create_time" IS '创建时间';

COMMENT ON COLUMN "init_rtks_knowledge_category"."id" IS '自增id';

COMMENT ON COLUMN "init_rtks_knowledge_category"."resource_id" IS '资源id';

COMMENT ON COLUMN "init_rtks_knowledge_category"."update_time" IS '更新时间';

COMMENT ON TABLE "init_rtks_level" IS 'rtks框架层级重置表';

COMMENT ON COLUMN "init_rtks_level"."create_time" IS '创建时间';

COMMENT ON COLUMN "init_rtks_level"."description" IS '描述';

COMMENT ON COLUMN "init_rtks_level"."frame" IS '框架id';

COMMENT ON COLUMN "init_rtks_level"."id" IS '自增id';

COMMENT ON COLUMN "init_rtks_level"."name" IS '名称';

COMMENT ON COLUMN "init_rtks_level"."parent" IS '父级id';

COMMENT ON COLUMN "init_rtks_level"."resource_id" IS '资源id';

COMMENT ON COLUMN "init_rtks_level"."update_time" IS '更新时间';

COMMENT ON TABLE "init_rtks_role" IS 'rtks角色重置表';

COMMENT ON COLUMN "init_rtks_role"."ability_ids" IS '能力列表';

COMMENT ON COLUMN "init_rtks_role"."category" IS '类别';

COMMENT ON COLUMN "init_rtks_role"."create_time" IS '创建时间';

COMMENT ON COLUMN "init_rtks_role"."description" IS '描述';

COMMENT ON COLUMN "init_rtks_role"."id" IS '自增id';

COMMENT ON COLUMN "init_rtks_role"."knowledge_courses" IS '知识点课程列表';

COMMENT ON COLUMN "init_rtks_role"."knowledge_id_weights" IS '知识点权重列表';

COMMENT ON COLUMN "init_rtks_role"."knowledge_ids" IS '知识点列表';

COMMENT ON COLUMN "init_rtks_role"."knowledge_weight" IS '知识点比例';

COMMENT ON COLUMN "init_rtks_role"."level_id" IS '层级id';

COMMENT ON COLUMN "init_rtks_role"."name" IS '名称';

COMMENT ON COLUMN "init_rtks_role"."resource_id" IS '资源id';

COMMENT ON COLUMN "init_rtks_role"."skill_courses" IS '技能课程列表';

COMMENT ON COLUMN "init_rtks_role"."skill_id_weights" IS '技能点权重列表';

COMMENT ON COLUMN "init_rtks_role"."skill_ids" IS '技能点列表';

COMMENT ON COLUMN "init_rtks_role"."skill_missions" IS '技能任务列表';

COMMENT ON COLUMN "init_rtks_role"."task_ids" IS '任务列表';

COMMENT ON COLUMN "init_rtks_role"."update_time" IS '更新时间';

COMMENT ON TABLE "init_rtks_role_skill_relation" IS '预置rtks角色技能点关联表';

COMMENT ON COLUMN "init_rtks_role_skill_relation"."create_time" IS '创建时间';

COMMENT ON COLUMN "init_rtks_role_skill_relation"."id" IS '自增id';

COMMENT ON COLUMN "init_rtks_role_skill_relation"."role_id" IS '角色id';

COMMENT ON COLUMN "init_rtks_role_skill_relation"."skill_id" IS '技能id';

COMMENT ON COLUMN "init_rtks_role_skill_relation"."update_time" IS '更新时间';

COMMENT ON COLUMN "init_rtks_role_skill_relation"."weight" IS '权重';

COMMENT ON TABLE "init_rtks_task" IS 'rtks任务重置表';

COMMENT ON COLUMN "init_rtks_task"."content" IS '内容';

COMMENT ON COLUMN "init_rtks_task"."create_time" IS '创建时间';

COMMENT ON COLUMN "init_rtks_task"."id" IS '自增id';

COMMENT ON COLUMN "init_rtks_task"."resource_id" IS '资源id';

COMMENT ON COLUMN "init_rtks_task"."update_time" IS '更新时间';

COMMENT ON TABLE "local_scene" IS '本地场景表';

COMMENT ON COLUMN "local_scene"."business_code" IS '业务码';

COMMENT ON COLUMN "local_scene"."business_instance_id" IS '业务id';

COMMENT ON COLUMN "local_scene"."create_time" IS '创建时间';

COMMENT ON COLUMN "local_scene"."destroy_mode" IS '销毁模式：0=手动；1=自动';

COMMENT ON COLUMN "local_scene"."destroy_time" IS '自动调用销毁时间';

COMMENT ON COLUMN "local_scene"."end_time" IS '场景停止时间';

COMMENT ON COLUMN "local_scene"."id" IS '自增id';

COMMENT ON COLUMN "local_scene"."meta" IS 'json格式数据';

COMMENT ON COLUMN "local_scene"."mp_scene_id" IS '中台场景id';

COMMENT ON COLUMN "local_scene"."node_count" IS '节点数量';

COMMENT ON COLUMN "local_scene"."pause_time" IS '场景暂停时间';

COMMENT ON COLUMN "local_scene"."resource_id" IS '资源id';

COMMENT ON COLUMN "local_scene"."scene_attr" IS '场景类型';

COMMENT ON COLUMN "local_scene"."scene_name" IS '场景名';

COMMENT ON COLUMN "local_scene"."scene_status" IS '场景状态';

COMMENT ON COLUMN "local_scene"."scene_ttl" IS '场景存活时间';

COMMENT ON COLUMN "local_scene"."start_time" IS '场景启动时间';

COMMENT ON COLUMN "local_scene"."topo_id" IS '拓扑Id';

COMMENT ON COLUMN "local_scene"."update_time" IS '更新时间';

COMMENT ON COLUMN "local_scene"."user_id" IS '用户id';

COMMENT ON TABLE "oj_classroom" IS '实训课程表（课堂安排）';

COMMENT ON COLUMN "oj_classroom"."course_id" IS '关联课程id';

COMMENT ON COLUMN "oj_classroom"."create_user_id" IS '创建者（课堂教师）';

COMMENT ON COLUMN "oj_classroom"."end_time" IS '课堂结束时间';

COMMENT ON COLUMN "oj_classroom"."lesson_id" IS '关联课时id';

COMMENT ON COLUMN "oj_classroom"."organization_id" IS '关联组织id';

COMMENT ON COLUMN "oj_classroom"."organization_ids" IS '关联组织下所有组织id';

COMMENT ON COLUMN "oj_classroom"."start_time" IS '课堂开始时间';

COMMENT ON COLUMN "oj_classroom"."train_plan_id" IS '关联训练计划id';

COMMENT ON TABLE "oj_classroom_group_archive" IS '实训课堂（实验课）分组信息快照';

COMMENT ON COLUMN "oj_classroom_group_archive"."classroom_id" IS '所属课堂id';

COMMENT ON COLUMN "oj_classroom_group_archive"."groups" IS '分组数据';

COMMENT ON COLUMN "oj_classroom_group_archive"."name" IS '分组信息快照名称';

COMMENT ON TABLE "oj_classroom_group_info" IS '实训课堂（实验课）分组信息';

COMMENT ON COLUMN "oj_classroom_group_info"."classroom_id" IS '所属课堂id';

COMMENT ON COLUMN "oj_classroom_group_info"."destroy_delay" IS '实验环境销毁期限（单位:h）';

COMMENT ON COLUMN "oj_classroom_group_info"."group_user_ids" IS '组内用户id';

COMMENT ON COLUMN "oj_classroom_group_info"."lesson_id" IS '关联实验课id';

COMMENT ON COLUMN "oj_classroom_group_info"."scene_share_mode" IS '场景是否共享标识';

COMMENT ON COLUMN "oj_classroom_group_info"."topo_id" IS '实验课使用拓扑id';

COMMENT ON TABLE "oj_classroom_sign" IS '实训课堂签到记录';

COMMENT ON COLUMN "oj_classroom_sign"."classroom_id" IS '所属课堂id';

COMMENT ON COLUMN "oj_classroom_sign"."need_help" IS '学员是否需要请求协助（实验课）';

COMMENT ON COLUMN "oj_classroom_sign"."node_id" IS '场景中请求协助的节点id（实验课）';

COMMENT ON COLUMN "oj_classroom_sign"."sign_in" IS '是否签到';

COMMENT ON COLUMN "oj_classroom_sign"."sign_in_time" IS '签到时间';

COMMENT ON TABLE "oj_comment" IS '实训留言';

COMMENT ON COLUMN "oj_comment"."business_id" IS '留言所属业务（资源）id（课程、习题...）';

COMMENT ON COLUMN "oj_comment"."business_type" IS '留言所属模块类型（课程、习题...）';

COMMENT ON COLUMN "oj_comment"."content" IS '留言内容';

COMMENT ON COLUMN "oj_comment"."reply_id" IS '此留言所回复留言id';

COMMENT ON COLUMN "oj_comment"."root_id" IS '此留言所属楼层的层主留言id';

COMMENT ON COLUMN "oj_comment"."theme_name" IS '留言所属主题信息（课程名、习题名...）';

COMMENT ON COLUMN "oj_comment"."user_id" IS '发起留言用户id';

COMMENT ON TABLE "oj_comment_like" IS '实训留言点赞';

COMMENT ON COLUMN "oj_comment_like"."comment_id" IS '点赞评论id';

COMMENT ON COLUMN "oj_comment_like"."user_id" IS '点赞用户id';

COMMENT ON TABLE "oj_course" IS '实训课程';

COMMENT ON COLUMN "oj_course"."builtin" IS '课程是否属于内置资源';

COMMENT ON COLUMN "oj_course"."category_id" IS '关联方向id';

COMMENT ON COLUMN "oj_course"."course_num" IS '课程编号';

COMMENT ON COLUMN "oj_course"."course_type" IS '课程类型';

COMMENT ON COLUMN "oj_course"."create_user_id" IS '创建者id';

COMMENT ON COLUMN "oj_course"."description" IS '课程描述';

COMMENT ON COLUMN "oj_course"."difficulty" IS '难度';

COMMENT ON COLUMN "oj_course"."heat" IS '热度';

COMMENT ON COLUMN "oj_course"."lecturer" IS '讲师';

COMMENT ON COLUMN "oj_course"."logo" IS '课程封面id';

COMMENT ON COLUMN "oj_course"."modify_user_id" IS '最后修改的用户id';

COMMENT ON COLUMN "oj_course"."name" IS '课程名称';

COMMENT ON COLUMN "oj_course"."operation_auth_ids" IS '拥有后台操作权限的用户id';

COMMENT ON COLUMN "oj_course"."operation_auth_type" IS '后台操作权限类型';

COMMENT ON COLUMN "oj_course"."outline" IS '课程大纲';

COMMENT ON COLUMN "oj_course"."public" IS '是否公开';

COMMENT ON COLUMN "oj_course"."read_auth_ids" IS '拥有前台预览权限的组织id';

COMMENT ON COLUMN "oj_course"."read_auth_type" IS '前台预览权限类型';

COMMENT ON COLUMN "oj_course"."score" IS '学分';

COMMENT ON TABLE "oj_course_category" IS '实训课程方向/分类';

COMMENT ON COLUMN "oj_course_category"."code" IS '节点编码  父级节点code + '','' + 当前节点编号';

COMMENT ON COLUMN "oj_course_category"."course_type" IS '课程类型0=基础 2=攻击 3=防御';

COMMENT ON COLUMN "oj_course_category"."name" IS '方向名称';

COMMENT ON COLUMN "oj_course_category"."parent_id" IS '父级节点编号';

COMMENT ON TABLE "oj_course_lesson" IS '实训课时';

COMMENT ON COLUMN "oj_course_lesson"."attachment_ids" IS '关联附件id';

COMMENT ON COLUMN "oj_course_lesson"."builtin" IS '是否内置';

COMMENT ON COLUMN "oj_course_lesson"."compulsory" IS '是否必修';

COMMENT ON COLUMN "oj_course_lesson"."course_id" IS '关联课程id';

COMMENT ON COLUMN "oj_course_lesson"."create_user_id" IS '创建用户id';

COMMENT ON COLUMN "oj_course_lesson"."destroy_delay" IS '场景销毁期限（实验课使用）';

COMMENT ON COLUMN "oj_course_lesson"."difficulty" IS '难度';

COMMENT ON COLUMN "oj_course_lesson"."duration" IS '建议时长（单位：m）';

COMMENT ON COLUMN "oj_course_lesson"."handout_type" IS '讲义类型';

COMMENT ON COLUMN "oj_course_lesson"."handout_url" IS '讲义文件地址';

COMMENT ON COLUMN "oj_course_lesson"."heat" IS '热度';

COMMENT ON COLUMN "oj_course_lesson"."knowledge" IS '知识点';

COMMENT ON COLUMN "oj_course_lesson"."name" IS '课时名称';

COMMENT ON COLUMN "oj_course_lesson"."origin_markdown_file" IS '上传markdown讲义原文件地址';

COMMENT ON COLUMN "oj_course_lesson"."paper_id" IS '关联课后练习id';

COMMENT ON COLUMN "oj_course_lesson"."paper_public" IS '课后练习是否公开';

COMMENT ON COLUMN "oj_course_lesson"."public" IS '是否公开';

COMMENT ON COLUMN "oj_course_lesson"."scene_share_mode" IS '场景是否共享标识';

COMMENT ON COLUMN "oj_course_lesson"."topo_id" IS '关联拓扑id（实验课使用）';

COMMENT ON COLUMN "oj_course_lesson"."type" IS '课时类型';

COMMENT ON COLUMN "oj_course_lesson"."video_check_point" IS '视频检查点配置';

COMMENT ON COLUMN "oj_course_lesson"."video_url" IS '视频课件地址';

COMMENT ON TABLE "oj_lesson_config" IS '实训课时目录';

COMMENT ON COLUMN "oj_lesson_config"."course_id" IS '关联课程id';

COMMENT ON COLUMN "oj_lesson_config"."index" IS '此节点在父节点下顺序索引';

COMMENT ON COLUMN "oj_lesson_config"."lesson_id" IS '关联课时id';

COMMENT ON COLUMN "oj_lesson_config"."name" IS '目录节点名称';

COMMENT ON COLUMN "oj_lesson_config"."node_type" IS '节点类型';

COMMENT ON COLUMN "oj_lesson_config"."parent_id" IS '父节点id';

COMMENT ON TABLE "oj_lesson_paper_evaluate" IS '实训课后练习评估';

COMMENT ON COLUMN "oj_lesson_paper_evaluate"."accuracy" IS '正确率';

COMMENT ON COLUMN "oj_lesson_paper_evaluate"."corrector" IS '评卷用户id';

COMMENT ON COLUMN "oj_lesson_paper_evaluate"."lesson_id" IS '关联课时id';

COMMENT ON COLUMN "oj_lesson_paper_evaluate"."level" IS '课后练习完成评级';

COMMENT ON COLUMN "oj_lesson_paper_evaluate"."record" IS '课后联系提交记录';

COMMENT ON COLUMN "oj_lesson_paper_evaluate"."score" IS '得分';

COMMENT ON COLUMN "oj_lesson_paper_evaluate"."submit_user_id" IS '提交用户id';

COMMENT ON COLUMN "oj_lesson_paper_evaluate"."total_score" IS '课后练习总分';

COMMENT ON TABLE "oj_lesson_record" IS '实训课时学习记录';

COMMENT ON COLUMN "oj_lesson_record"."course_id" IS '学习课程id';

COMMENT ON COLUMN "oj_lesson_record"."learned_type" IS '课程学习类型0=课程 1=课程表';

COMMENT ON COLUMN "oj_lesson_record"."lesson_id" IS '学习课时id';

COMMENT ON COLUMN "oj_lesson_record"."room_id" IS '课堂ID';

COMMENT ON COLUMN "oj_lesson_record"."user_id" IS '学习用户id';

COMMENT ON TABLE "oj_local_scene" IS '实训场景关联表';

COMMENT ON COLUMN "oj_local_scene"."business_id" IS '关联场景的业务（资源）id';

COMMENT ON COLUMN "oj_local_scene"."business_type" IS '关联场景的业务类型';

COMMENT ON COLUMN "oj_local_scene"."flags" IS '环境FLAG（实践操作习题使用）';

COMMENT ON COLUMN "oj_local_scene"."from_web" IS '是否为前台场景';

COMMENT ON COLUMN "oj_local_scene"."local_scene_id" IS '关联的local_scene.resource_id';

COMMENT ON COLUMN "oj_local_scene"."shared" IS '是否为共享场景';

COMMENT ON COLUMN "oj_local_scene"."user_id" IS '创建场景用户id';

COMMENT ON TABLE "oj_note" IS '实训我的笔记/实验报告';

COMMENT ON COLUMN "oj_note"."business_id" IS '资源编号';

COMMENT ON COLUMN "oj_note"."business_type" IS '1=课程 2=课时 3=练习 4=课后练习';

COMMENT ON COLUMN "oj_note"."content" IS '笔记内容';

COMMENT ON COLUMN "oj_note"."is_submit" IS '实验报告是否提交';

COMMENT ON COLUMN "oj_note"."note_type" IS '1=笔记 2=实验报告';

COMMENT ON COLUMN "oj_note"."operation_auth_ids" IS '拥有后台操作权限的用户id';

COMMENT ON COLUMN "oj_note"."operation_auth_type" IS '授权操作类类型 1=共享 2=自定义';

COMMENT ON COLUMN "oj_note"."public" IS '是否公开';

COMMENT ON COLUMN "oj_note"."read_auth_ids" IS '拥有前台预览权限的组织id';

COMMENT ON COLUMN "oj_note"."read_auth_type" IS '同上';

COMMENT ON COLUMN "oj_note"."user_id" IS '用户编号';

COMMENT ON TABLE "oj_note_evaluate" IS '实训实验报告评估';

COMMENT ON COLUMN "oj_note_evaluate"."evaluate_comment" IS '评语';

COMMENT ON COLUMN "oj_note_evaluate"."evaluate_time" IS '评估时间';

COMMENT ON COLUMN "oj_note_evaluate"."evaluator_id" IS '评估用户id';

COMMENT ON COLUMN "oj_note_evaluate"."note_id" IS '评估实验报告id';

COMMENT ON COLUMN "oj_note_evaluate"."passed" IS '是否通过';

COMMENT ON COLUMN "oj_note_evaluate"."score" IS '得分';

COMMENT ON TABLE "oj_paper" IS '实训试卷/课后练习';

COMMENT ON COLUMN "oj_paper"."description" IS '试卷描述';

COMMENT ON COLUMN "oj_paper"."logo" IS '试卷封面id';

COMMENT ON COLUMN "oj_paper"."name" IS '试卷名称';

COMMENT ON COLUMN "oj_paper"."operation_auth_ids" IS '拥有后台操作权限的用户id';

COMMENT ON COLUMN "oj_paper"."practice_count" IS '试卷下习题总数';

COMMENT ON COLUMN "oj_paper"."public" IS '是否公开';

COMMENT ON COLUMN "oj_paper"."read_auth_ids" IS '拥有前台预览权限的组织id';

COMMENT ON COLUMN "oj_paper"."total_score" IS '试卷总分';

COMMENT ON COLUMN "oj_paper"."type" IS '试卷类型';

COMMENT ON TABLE "oj_paper_practice_theory" IS '组卷习题详情表';

COMMENT ON COLUMN "oj_paper_practice_theory"."origin_id" IS '原理论题id';

COMMENT ON COLUMN "oj_paper_practice_theory"."paper_id" IS '关联试卷id';

COMMENT ON COLUMN "oj_paper_practice_theory"."snapshot" IS '理论基础信息快照';

COMMENT ON COLUMN "oj_paper_practice_theory"."solved" IS '个人组卷练习解题状态';

COMMENT ON COLUMN "oj_paper_practice_theory"."submit_answer" IS '个人组卷练习提交的答案';

COMMENT ON TABLE "oj_practice_category" IS '实训习题方向/分类';

COMMENT ON COLUMN "oj_practice_category"."code" IS '节点编号';

COMMENT ON COLUMN "oj_practice_category"."create_user_id" IS '创建人';

COMMENT ON COLUMN "oj_practice_category"."name" IS '分类名称';

COMMENT ON COLUMN "oj_practice_category"."parent_id" IS '编号';

COMMENT ON COLUMN "oj_practice_category"."practice_type" IS '习题类型';

COMMENT ON TABLE "oj_practice_mistake" IS '错题集表';

COMMENT ON COLUMN "oj_practice_mistake"."correct" IS '做对次数';

COMMENT ON COLUMN "oj_practice_mistake"."incorrect" IS '做错次数';

COMMENT ON COLUMN "oj_practice_mistake"."practice_id" IS '题目id';

COMMENT ON COLUMN "oj_practice_mistake"."practice_type" IS '题目类型';

COMMENT ON COLUMN "oj_practice_mistake"."user_id" IS '用户id';

COMMENT ON TABLE "oj_practice_operation" IS '实训实践操作';

COMMENT ON COLUMN "oj_practice_operation"."answer" IS '答案';

COMMENT ON COLUMN "oj_practice_operation"."attachment_id" IS '关联附件id';

COMMENT ON COLUMN "oj_practice_operation"."builtin" IS '是否为内置资源';

COMMENT ON COLUMN "oj_practice_operation"."category_id" IS '关联方向id';

COMMENT ON COLUMN "oj_practice_operation"."content" IS '内容';

COMMENT ON COLUMN "oj_practice_operation"."create_user_id" IS '创建用户id';

COMMENT ON COLUMN "oj_practice_operation"."destroy_delay" IS '场景销毁时限（单位：h，动态环境下拥有）';

COMMENT ON COLUMN "oj_practice_operation"."difficulty" IS '难度';

COMMENT ON COLUMN "oj_practice_operation"."dynamic_env" IS '是否为动态环境';

COMMENT ON COLUMN "oj_practice_operation"."dynamic_flag" IS '是否为动态FLAG';

COMMENT ON COLUMN "oj_practice_operation"."knowledge" IS '知识点';

COMMENT ON COLUMN "oj_practice_operation"."practice_set_id" IS '关联习题集id';

COMMENT ON COLUMN "oj_practice_operation"."public" IS '是否公开';

COMMENT ON COLUMN "oj_practice_operation"."scene_share_mode" IS '场景分享标识（动态环境下拥有）';

COMMENT ON COLUMN "oj_practice_operation"."score" IS '得分';

COMMENT ON COLUMN "oj_practice_operation"."topo_id" IS '关联拓扑id（动态环境下拥有）';

COMMENT ON COLUMN "oj_practice_operation"."writeup" IS '题解';

COMMENT ON COLUMN "oj_practice_operation"."writeup_public" IS '题解是否公开';

COMMENT ON TABLE "oj_practice_operation_ref" IS '实训实践操作引用表';

COMMENT ON COLUMN "oj_practice_operation_ref"."origin_id" IS '原实践操作id';

COMMENT ON COLUMN "oj_practice_operation_ref"."paper_id" IS '关联试卷id';

COMMENT ON COLUMN "oj_practice_operation_ref"."snapshot" IS '实践操作基础信息快照';

COMMENT ON TABLE "oj_practice_paper" IS '组卷信息表';

COMMENT ON COLUMN "oj_practice_paper"."ai_create_flag" IS '是否是大模型服务创建的卷子，0-否 1-是';

COMMENT ON COLUMN "oj_practice_paper"."count" IS '出题数量';

COMMENT ON COLUMN "oj_practice_paper"."difficulty" IS '题目难度';

COMMENT ON COLUMN "oj_practice_paper"."is_finish" IS '是否完成';

COMMENT ON COLUMN "oj_practice_paper"."practice_status" IS '题目状态';

COMMENT ON COLUMN "oj_practice_paper"."type" IS '题目类型';

COMMENT ON COLUMN "oj_practice_paper"."user_id" IS '组卷人';

COMMENT ON TABLE "oj_practice_random_flag" IS '实训实践操作随机FLAG记录';

COMMENT ON COLUMN "oj_practice_random_flag"."attachment_id" IS '关联附件id';

COMMENT ON COLUMN "oj_practice_random_flag"."flag" IS 'FLAG';

COMMENT ON COLUMN "oj_practice_random_flag"."practice_id" IS '关联实践操作id';

COMMENT ON COLUMN "oj_practice_random_flag"."type" IS '区别使用业务（习题、课后练习...）';

COMMENT ON COLUMN "oj_practice_random_flag"."user_id" IS '答题用户id';

COMMENT ON TABLE "oj_practice_set" IS '实训习题集';

COMMENT ON COLUMN "oj_practice_set"."builtin" IS '是否为内置数据';

COMMENT ON COLUMN "oj_practice_set"."create_user_id" IS '创建用户id';

COMMENT ON COLUMN "oj_practice_set"."logo" IS '习题集封面id';

COMMENT ON COLUMN "oj_practice_set"."name" IS '习题集名称';

COMMENT ON COLUMN "oj_practice_set"."operation_auth_ids" IS '拥有后台操作权限的用户id';

COMMENT ON COLUMN "oj_practice_set"."operation_auth_type" IS '后台操作权限类型';

COMMENT ON COLUMN "oj_practice_set"."practice_count" IS '习题数量';

COMMENT ON COLUMN "oj_practice_set"."practice_type" IS '习题集类型';

COMMENT ON COLUMN "oj_practice_set"."public" IS '是否公开';

COMMENT ON COLUMN "oj_practice_set"."read_auth_ids" IS '拥有前台预览权限的组织id';

COMMENT ON COLUMN "oj_practice_set"."read_auth_type" IS '前台预览权限类型';

COMMENT ON TABLE "oj_practice_solved" IS '实训习题答题记录';

COMMENT ON COLUMN "oj_practice_solved"."collection" IS '是否收藏';

COMMENT ON COLUMN "oj_practice_solved"."is_paper" IS '是否是自由组卷产生的记录';

COMMENT ON COLUMN "oj_practice_solved"."practice_id" IS '习题编号';

COMMENT ON COLUMN "oj_practice_solved"."practice_type" IS '习题类型';

COMMENT ON COLUMN "oj_practice_solved"."score" IS '得分';

COMMENT ON COLUMN "oj_practice_solved"."solved" IS '是否解出';

COMMENT ON COLUMN "oj_practice_solved"."submit_answer" IS '提交的答案';

COMMENT ON COLUMN "oj_practice_solved"."submit_user_id" IS '提交人';

COMMENT ON COLUMN "oj_practice_solved"."total_score" IS '习题分值';

COMMENT ON TABLE "oj_practice_theory" IS '实训理论基础';

COMMENT ON COLUMN "oj_practice_theory"."answer" IS '答案';

COMMENT ON COLUMN "oj_practice_theory"."blank" IS '填空题填空内容';

COMMENT ON COLUMN "oj_practice_theory"."builtin" IS '是否为内置数据';

COMMENT ON COLUMN "oj_practice_theory"."category_id" IS '关联方向id';

COMMENT ON COLUMN "oj_practice_theory"."content" IS '内容';

COMMENT ON COLUMN "oj_practice_theory"."create_user_id" IS '创建用户id';

COMMENT ON COLUMN "oj_practice_theory"."difficulty" IS '难度';

COMMENT ON COLUMN "oj_practice_theory"."keyword" IS '简答题参考关键字';

COMMENT ON COLUMN "oj_practice_theory"."knowledge" IS '知识点';

COMMENT ON COLUMN "oj_practice_theory"."option" IS '选择题选项';

COMMENT ON COLUMN "oj_practice_theory"."practice_set_id" IS '关联习题集id';

COMMENT ON COLUMN "oj_practice_theory"."public" IS '是否公开';

COMMENT ON COLUMN "oj_practice_theory"."score" IS '得分';

COMMENT ON COLUMN "oj_practice_theory"."theory_type" IS '理论基础题型';

COMMENT ON COLUMN "oj_practice_theory"."title" IS '标题（暂未使用）';

COMMENT ON TABLE "oj_practice_theory_ref" IS '实训理论基础引用表';

COMMENT ON COLUMN "oj_practice_theory_ref"."option_random" IS '选择题选项是否打乱顺序';

COMMENT ON COLUMN "oj_practice_theory_ref"."origin_id" IS '原理论基础id';

COMMENT ON COLUMN "oj_practice_theory_ref"."paper_id" IS '关联试卷id';

COMMENT ON COLUMN "oj_practice_theory_ref"."snapshot" IS '理论基础基础信息快照';

COMMENT ON TABLE "oj_resource_collection" IS '实训资源收藏记录';

COMMENT ON COLUMN "oj_resource_collection"."business_id" IS '收藏业务（资源）id';

COMMENT ON COLUMN "oj_resource_collection"."business_type" IS '收藏业务类型';

COMMENT ON COLUMN "oj_resource_collection"."user_id" IS '收藏用户id';

COMMENT ON TABLE "oj_train_plan" IS '训练计划';

COMMENT ON COLUMN "oj_train_plan"."create_user_id" IS '创建用户id';

COMMENT ON COLUMN "oj_train_plan"."name" IS '计划名称';

COMMENT ON TABLE "rtks_ability" IS 'rtks能力表';

COMMENT ON COLUMN "rtks_ability"."content" IS '内容';

COMMENT ON COLUMN "rtks_ability"."create_time" IS '创建时间';

COMMENT ON COLUMN "rtks_ability"."id" IS '自增id';

COMMENT ON COLUMN "rtks_ability"."resource_id" IS '资源id';

COMMENT ON COLUMN "rtks_ability"."update_time" IS '更新时间';

COMMENT ON TABLE "rtks_attack" IS 'att&ck框架表';

COMMENT ON COLUMN "rtks_attack"."category_id" IS '类别id';

COMMENT ON COLUMN "rtks_attack"."id" IS '自增id';

COMMENT ON COLUMN "rtks_attack"."name_en" IS '英文内容';

COMMENT ON COLUMN "rtks_attack"."name_zh" IS '中文内容';

COMMENT ON COLUMN "rtks_attack"."resource_id" IS '资源id';

COMMENT ON TABLE "rtks_attack_category" IS 'att&ck框架类别表';

COMMENT ON COLUMN "rtks_attack_category"."id" IS '自增id';

COMMENT ON COLUMN "rtks_attack_category"."name_en" IS '英文内容';

COMMENT ON COLUMN "rtks_attack_category"."name_zh" IS '中文内容';

COMMENT ON COLUMN "rtks_attack_category"."resource_id" IS '资源id';

COMMENT ON COLUMN "rtks_attack_ics"."category_id" IS 'ics类型id';

COMMENT ON COLUMN "rtks_attack_ics"."name_en" IS '英文名称';

COMMENT ON COLUMN "rtks_attack_ics"."name_zh" IS '中文名称';

COMMENT ON COLUMN "rtks_attack_ics"."resource_id" IS 'ics id';

COMMENT ON COLUMN "rtks_attack_ics_category"."name_en" IS '英文名称';

COMMENT ON COLUMN "rtks_attack_ics_category"."name_zh" IS '中文名称';

COMMENT ON COLUMN "rtks_attack_ics_category"."resource_id" IS 'ics类型id';

COMMENT ON COLUMN "rtks_attack_ics_train_relation"."attack_ics_id" IS 'ics id';

COMMENT ON COLUMN "rtks_attack_ics_train_relation"."business_template_id" IS '训练模板id';

COMMENT ON COLUMN "rtks_attack_ics_train_relation"."create_time" IS '创建时间';

COMMENT ON COLUMN "rtks_attack_ics_train_relation"."resource_id" IS 'resource_id';

COMMENT ON COLUMN "rtks_attack_ics_train_relation"."update_time" IS '更新时间';

COMMENT ON TABLE "cr_eval_aggregation" IS '测评指标项';

COMMENT ON COLUMN "cr_eval_aggregation"."aggregating_algorithm_id" IS '聚合算法id';

COMMENT ON COLUMN "cr_eval_aggregation"."aggregating_algorithm_params" IS '聚合算法参数';

COMMENT ON COLUMN "cr_eval_aggregation"."collection_ids" IS '关联采集变量id
关联采集变量id';

COMMENT ON COLUMN "cr_eval_aggregation"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_eval_aggregation"."description" IS '描述';

COMMENT ON COLUMN "cr_eval_aggregation"."eval_indicator_id" IS '关联指标项id';

COMMENT ON COLUMN "cr_eval_aggregation"."matrix_data" IS '层次分析法矩阵参数';

COMMENT ON COLUMN "cr_eval_aggregation"."name" IS '名称';

COMMENT ON COLUMN "cr_eval_aggregation"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_eval_aggregation"."update_time" IS '更新时间';

COMMENT ON TABLE "cr_eval_collection" IS '指标项关联';

COMMENT ON COLUMN "cr_eval_collection"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_eval_collection"."detection_algorithm_id" IS '检测算法id';

COMMENT ON COLUMN "cr_eval_collection"."detection_algorithm_params" IS '检测算法参数';

COMMENT ON COLUMN "cr_eval_collection"."eval_indicator_id" IS '关联指标项id';

COMMENT ON COLUMN "cr_eval_collection"."format_algorithm_id" IS '格式化算法id';

COMMENT ON COLUMN "cr_eval_collection"."format_algorithm_params" IS '格式化算法参数';

COMMENT ON COLUMN "cr_eval_collection"."name" IS '名称';

COMMENT ON COLUMN "cr_eval_collection"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_eval_collection"."score_algorithm_id" IS '评分算法id';

COMMENT ON COLUMN "cr_eval_collection"."score_algorithm_params" IS '评分算法参数';

COMMENT ON COLUMN "cr_eval_collection"."update_time" IS '更新时间';

COMMENT ON TABLE "cr_eval_index" IS '算法体系';

COMMENT ON COLUMN "cr_eval_index"."aggregating_algorithm_id" IS '聚合算法id';

COMMENT ON COLUMN "cr_eval_index"."aggregating_algorithm_params" IS '聚合算法参数';

COMMENT ON COLUMN "cr_eval_index"."children_ids" IS '子指标id';

COMMENT ON COLUMN "cr_eval_index"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_eval_index"."description" IS '描述';

COMMENT ON COLUMN "cr_eval_index"."eval_indicator_id" IS '指标项id';

COMMENT ON COLUMN "cr_eval_index"."eval_system_id" IS '体系id';

COMMENT ON COLUMN "cr_eval_index"."level" IS '指标级别：1、2、3';

COMMENT ON COLUMN "cr_eval_index"."matrix_data" IS '层次分析法矩阵参数';

COMMENT ON COLUMN "cr_eval_index"."name" IS '名称';

COMMENT ON COLUMN "cr_eval_index"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_eval_index"."type" IS '归一化类型, 百分比:1,周期:2,延时:3';

COMMENT ON COLUMN "cr_eval_index"."update_time" IS '更新时间';

COMMENT ON TABLE "cr_eval_index_ref" IS '算法体系关联';

COMMENT ON COLUMN "cr_eval_index_ref"."algorithm_id" IS '聚合算法id';

COMMENT ON COLUMN "cr_eval_index_ref"."algorithm_params" IS '聚合算法参数';

COMMENT ON COLUMN "cr_eval_index_ref"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_eval_index_ref"."end_time" IS '结束时间';

COMMENT ON COLUMN "cr_eval_index_ref"."level" IS '指标级别：1、2、3';

COMMENT ON COLUMN "cr_eval_index_ref"."matrix_data" IS '层次分析法矩阵参数';

COMMENT ON COLUMN "cr_eval_index_ref"."name" IS '名称';

COMMENT ON COLUMN "cr_eval_index_ref"."next_ids" IS '子节点关联id';

COMMENT ON COLUMN "cr_eval_index_ref"."progress" IS '当前进程';

COMMENT ON COLUMN "cr_eval_index_ref"."record_id" IS '测评记录uuid';

COMMENT ON COLUMN "cr_eval_index_ref"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_eval_index_ref"."score" IS '得分';

COMMENT ON COLUMN "cr_eval_index_ref"."specialist_attr" IS '专有属性类型';

COMMENT ON COLUMN "cr_eval_index_ref"."specialist_attr_value" IS '专有属性值';

COMMENT ON COLUMN "cr_eval_index_ref"."specialist_type" IS '专有参数类型';

COMMENT ON COLUMN "cr_eval_index_ref"."specialist_value" IS '专有参数';

COMMENT ON COLUMN "cr_eval_index_ref"."start_time" IS '开始时间';

COMMENT ON COLUMN "cr_eval_index_ref"."type" IS '归一化类型, 百分比:1,周期:2,延时:3';

COMMENT ON COLUMN "cr_eval_index_ref"."update_time" IS '更新时间';

COMMENT ON TABLE "cr_eval_indicator" IS '指标项详情';

COMMENT ON COLUMN "cr_eval_indicator"."aggregating_algorithm_id" IS '聚合算法id';

COMMENT ON COLUMN "cr_eval_indicator"."aggregating_algorithm_params" IS '聚合算法参数';

COMMENT ON COLUMN "cr_eval_indicator"."aggregation_ids" IS '关联聚合项id';

COMMENT ON COLUMN "cr_eval_indicator"."attribute" IS '指标项属性类型';

COMMENT ON COLUMN "cr_eval_indicator"."attribute_value" IS '属性值';

COMMENT ON COLUMN "cr_eval_indicator"."collection_ids" IS '关联变量项id';

COMMENT ON COLUMN "cr_eval_indicator"."connlist" IS '连接线';

COMMENT ON COLUMN "cr_eval_indicator"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_eval_indicator"."create_user_id" IS '创建人uuid';

COMMENT ON COLUMN "cr_eval_indicator"."datatype" IS '数据类型';

COMMENT ON COLUMN "cr_eval_indicator"."description" IS '描述';

COMMENT ON COLUMN "cr_eval_indicator"."matrix_data" IS '层次分析法矩阵参数';

COMMENT ON COLUMN "cr_eval_indicator"."name" IS '名称';

COMMENT ON COLUMN "cr_eval_indicator"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_eval_indicator"."update_time" IS '更新时间';

COMMENT ON TABLE "cr_eval_record" IS '测评记录';

COMMENT ON COLUMN "cr_eval_record"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_eval_record"."description" IS '描述';

COMMENT ON COLUMN "cr_eval_record"."end_time" IS '结束时间';

COMMENT ON COLUMN "cr_eval_record"."eval_end_time" IS '测评阶段结束时间';

COMMENT ON COLUMN "cr_eval_record"."eval_time" IS '测评阶段开始时间';

COMMENT ON COLUMN "cr_eval_record"."message" IS '测评消息';

COMMENT ON COLUMN "cr_eval_record"."name" IS '测评名称';

COMMENT ON COLUMN "cr_eval_record"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_eval_record"."score" IS '分数';

COMMENT ON COLUMN "cr_eval_record"."start_time" IS '开始时间';

COMMENT ON COLUMN "cr_eval_record"."status" IS '状态';

COMMENT ON COLUMN "cr_eval_record"."system_id" IS '测评体系id';

COMMENT ON COLUMN "cr_eval_record"."task_id" IS '测评任务id';

COMMENT ON COLUMN "cr_eval_record"."update_time" IS '更新时间';

COMMENT ON TABLE "cr_eval_variable_ref" IS '评估详情';

COMMENT ON COLUMN "cr_eval_variable_ref"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_eval_variable_ref"."device_id" IS '虚拟机id';

COMMENT ON COLUMN "cr_eval_variable_ref"."format_algorithm_id" IS '格式化算法id';

COMMENT ON COLUMN "cr_eval_variable_ref"."format_end_time" IS '格式算法执行结束时间';

COMMENT ON COLUMN "cr_eval_variable_ref"."format_params" IS '格式化算法参数';

COMMENT ON COLUMN "cr_eval_variable_ref"."format_start_time" IS '格式算法执行开始时间';

COMMENT ON COLUMN "cr_eval_variable_ref"."mark_algorithm_id" IS '评分算法id';

COMMENT ON COLUMN "cr_eval_variable_ref"."mark_end_time" IS '评分执行结束时间';

COMMENT ON COLUMN "cr_eval_variable_ref"."mark_params" IS '评分算法参数';

COMMENT ON COLUMN "cr_eval_variable_ref"."mark_start_time" IS '评分执行开始时间';

COMMENT ON COLUMN "cr_eval_variable_ref"."match_algorithm_id" IS '模型算法id';

COMMENT ON COLUMN "cr_eval_variable_ref"."match_end_time" IS '模型执行结束时间';

COMMENT ON COLUMN "cr_eval_variable_ref"."match_params" IS '模型算法参数';

COMMENT ON COLUMN "cr_eval_variable_ref"."match_start_time" IS '模型执行开始时间';

COMMENT ON COLUMN "cr_eval_variable_ref"."name" IS '算法名称';

COMMENT ON COLUMN "cr_eval_variable_ref"."probe_id" IS '探针id';

COMMENT ON COLUMN "cr_eval_variable_ref"."progress" IS '进程数';

COMMENT ON COLUMN "cr_eval_variable_ref"."record_id" IS '测评记录id';

COMMENT ON COLUMN "cr_eval_variable_ref"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_eval_variable_ref"."score" IS '分数';

COMMENT ON COLUMN "cr_eval_variable_ref"."type" IS '评分类型';

COMMENT ON COLUMN "cr_eval_variable_ref"."update_time" IS '更新时间';

COMMENT ON COLUMN "cr_mark"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_mark"."description" IS '描述';

COMMENT ON COLUMN "cr_mark"."evidence" IS '证据';

COMMENT ON COLUMN "cr_mark"."name" IS '名称';

COMMENT ON COLUMN "cr_mark"."resource_id" IS 'id';

COMMENT ON COLUMN "cr_mark"."room_id" IS '训练id';

COMMENT ON COLUMN "cr_mark"."type" IS '训练类型';

COMMENT ON COLUMN "cr_mark"."update_time" IS '更新时间';

COMMENT ON COLUMN "cr_mark"."user_id" IS '用户id';

COMMENT ON TABLE "cr_mission" IS '前台房间任务';

COMMENT ON COLUMN "cr_mission"."answer" IS '答案';

COMMENT ON COLUMN "cr_mission"."category_id" IS '题目分类';

COMMENT ON COLUMN "cr_mission"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_mission"."create_user_id" IS '创建人id';

COMMENT ON COLUMN "cr_mission"."description" IS '描述';

COMMENT ON COLUMN "cr_mission"."mission_template_id" IS '任务模板id';

COMMENT ON COLUMN "cr_mission"."option" IS '选择题选项';

COMMENT ON COLUMN "cr_mission"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_mission"."room_id" IS '房间id';

COMMENT ON COLUMN "cr_mission"."score" IS '分值';

COMMENT ON COLUMN "cr_mission"."show_writeup" IS '题解是否开放';

COMMENT ON COLUMN "cr_mission"."skill_points" IS '技能点数';

COMMENT ON COLUMN "cr_mission"."title" IS '任务标题';

COMMENT ON COLUMN "cr_mission"."type" IS '题目类型（选择、判断等）';

COMMENT ON COLUMN "cr_mission"."update_time" IS '更新时间';

COMMENT ON COLUMN "cr_mission"."write_up" IS '题解';

COMMENT ON TABLE "cr_mission_template" IS '后台任务模板';

COMMENT ON COLUMN "cr_mission_template"."answer" IS '答案';

COMMENT ON COLUMN "cr_mission_template"."attachment" IS '附件集合';

COMMENT ON COLUMN "cr_mission_template"."business_template_id" IS '训练模板id';

COMMENT ON COLUMN "cr_mission_template"."category_id" IS '题目类别';

COMMENT ON COLUMN "cr_mission_template"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_mission_template"."create_user_id" IS '创建人id';

COMMENT ON COLUMN "cr_mission_template"."description" IS '描述';

COMMENT ON COLUMN "cr_mission_template"."option" IS '选择题选项';

COMMENT ON COLUMN "cr_mission_template"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_mission_template"."score" IS '分值';

COMMENT ON COLUMN "cr_mission_template"."skill_points" IS '技能点数';

COMMENT ON COLUMN "cr_mission_template"."template_id" IS '暂未用到';

COMMENT ON COLUMN "cr_mission_template"."title" IS '任务标题';

COMMENT ON COLUMN "cr_mission_template"."type" IS '题目类型（选择、判断等）';

COMMENT ON COLUMN "cr_mission_template"."update_time" IS '更新时间';

COMMENT ON COLUMN "cr_mission_template"."write_up" IS '题解';

COMMENT ON COLUMN "cr_notice"."context" IS '公告内容';

COMMENT ON COLUMN "cr_notice"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_notice"."create_user_id" IS '创建人id';

COMMENT ON COLUMN "cr_notice"."is_top" IS '是否置顶0否1是';

COMMENT ON COLUMN "cr_notice"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_notice"."role" IS '公告对象';

COMMENT ON COLUMN "cr_notice"."room_id" IS '房间id';

COMMENT ON COLUMN "cr_notice"."update_time" IS '更新时间';

COMMENT ON TABLE "cr_rb_room" IS '红蓝对抗房间详情';

COMMENT ON COLUMN "cr_rb_room"."copyed_topo_id" IS '拓扑id';

COMMENT ON COLUMN "cr_rb_room"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_rb_room"."create_user_id" IS '创建人id';

COMMENT ON COLUMN "cr_rb_room"."description" IS '描述';

COMMENT ON COLUMN "cr_rb_room"."difficulty" IS '难度';

COMMENT ON COLUMN "cr_rb_room"."enable_recording" IS '是否录屏';

COMMENT ON COLUMN "cr_rb_room"."end_time" IS '结束时间';

COMMENT ON COLUMN "cr_rb_room"."local_scene_id" IS '启动场景id';

COMMENT ON COLUMN "cr_rb_room"."logo" IS '封面url';

COMMENT ON COLUMN "cr_rb_room"."modify_user_id" IS '更新人id';

COMMENT ON COLUMN "cr_rb_room"."name" IS '房间名';

COMMENT ON COLUMN "cr_rb_room"."pre_skill" IS '前置技能';

COMMENT ON COLUMN "cr_rb_room"."replay_scene_id" IS '复盘场景id';

COMMENT ON COLUMN "cr_rb_room"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_rb_room"."scene_file" IS '场景文件';

COMMENT ON COLUMN "cr_rb_room"."scene_id_record" IS '训练过程中的所有场景id';

COMMENT ON COLUMN "cr_rb_room"."start_time" IS '开始时间';

COMMENT ON COLUMN "cr_rb_room"."sub_type" IS '训练类别';

COMMENT ON COLUMN "cr_rb_room"."support_partial_start" IS '是否部分启动';

COMMENT ON COLUMN "cr_rb_room"."template_id" IS '红蓝对抗模板id';

COMMENT ON COLUMN "cr_rb_room"."template_name" IS '对抗模板名称';

COMMENT ON COLUMN "cr_rb_room"."time_setting" IS '用户授时配置';

COMMENT ON COLUMN "cr_rb_room"."train_skill" IS '训练目标';

COMMENT ON COLUMN "cr_rb_room"."training_status" IS '对抗状态';

COMMENT ON COLUMN "cr_rb_room"."update_time" IS '更新时间';

COMMENT ON TABLE "cr_rb_template" IS '红蓝对抗模板详情';

COMMENT ON COLUMN "cr_rb_template"."business_template_id" IS '训练模板id';

COMMENT ON COLUMN "cr_rb_template"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_rb_template"."duration_time" IS '训练时长';

COMMENT ON COLUMN "cr_rb_template"."pre_skill" IS '前置技能';

COMMENT ON COLUMN "cr_rb_template"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_rb_template"."role_config" IS '角色节点和任务配置';

COMMENT ON COLUMN "cr_rb_template"."sub_type" IS '训练类别';

COMMENT ON COLUMN "cr_rb_template"."train_skill" IS '训练目标';

COMMENT ON COLUMN "cr_rb_template"."update_time" IS '更新时间';

COMMENT ON TABLE "cr_rb_time_management" IS '红蓝对抗作战时间管理';

COMMENT ON COLUMN "cr_rb_time_management"."astronomical_time" IS '天文时间';

COMMENT ON COLUMN "cr_rb_time_management"."combat_time" IS '作战时间';

COMMENT ON COLUMN "cr_rb_time_management"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_rb_time_management"."create_user_id" IS '创建人id';

COMMENT ON COLUMN "cr_rb_time_management"."end_time" IS '结束时间';

COMMENT ON COLUMN "cr_rb_time_management"."length" IS '已记录训练时长';

COMMENT ON COLUMN "cr_rb_time_management"."rate" IS '时间比率';

COMMENT ON COLUMN "cr_rb_time_management"."recover_time" IS '恢复时间';

COMMENT ON COLUMN "cr_rb_time_management"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_rb_time_management"."room_id" IS '对抗房间id';

COMMENT ON COLUMN "cr_rb_time_management"."status" IS '对抗赛状态';

COMMENT ON COLUMN "cr_rb_time_management"."update_time" IS '更新时间';

COMMENT ON COLUMN "cr_rb_time_management"."update_user_id" IS '更新人id';

COMMENT ON TABLE "cr_role" IS '训练角色详情';

COMMENT ON COLUMN "cr_role"."business_type" IS '训练类型';

COMMENT ON COLUMN "cr_role"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_role"."description" IS '描述';

COMMENT ON COLUMN "cr_role"."exercise_paper" IS '训练手册';

COMMENT ON COLUMN "cr_role"."mission_scheme" IS '任务数据';

COMMENT ON COLUMN "cr_role"."name" IS '角色名';

COMMENT ON COLUMN "cr_role"."nodes" IS '角色节点';

COMMENT ON COLUMN "cr_role"."nodes_snapshot" IS '角色节点快照';

COMMENT ON COLUMN "cr_role"."parent_resource_id" IS '父角色id';

COMMENT ON COLUMN "cr_role"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_role"."role_group" IS '权限组';

COMMENT ON COLUMN "cr_role"."room_id" IS '训练房间id';

COMMENT ON COLUMN "cr_role"."update_time" IS '更新时间';

COMMENT ON COLUMN "cr_role"."user_ids" IS '角色用户';

COMMENT ON TABLE "cr_task" IS '下发任务详情';

COMMENT ON COLUMN "cr_task"."business_type" IS '训练类型';

COMMENT ON COLUMN "cr_task"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_task"."last_submit_time" IS '最后提交时间';

COMMENT ON COLUMN "cr_task"."mission_id" IS '任务id';

COMMENT ON COLUMN "cr_task"."mission_template_id" IS '任务模板id';

COMMENT ON COLUMN "cr_task"."release_time" IS '下发时间';

COMMENT ON COLUMN "cr_task"."remark" IS '备注';

COMMENT ON COLUMN "cr_task"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_task"."role_id" IS '角色id';

COMMENT ON COLUMN "cr_task"."room_id" IS '房间id';

COMMENT ON COLUMN "cr_task"."score" IS '得分';

COMMENT ON COLUMN "cr_task"."status" IS '执行状态';

COMMENT ON COLUMN "cr_task"."task_start_time" IS '任务开始时间';

COMMENT ON COLUMN "cr_task"."update_time" IS '更新时间';

COMMENT ON COLUMN "cr_task"."user_answer" IS '用户答案';

COMMENT ON COLUMN "cr_task"."user_id" IS '训练用户id';

COMMENT ON TABLE "cr_task_log" IS '任务动作记录';

COMMENT ON COLUMN "cr_task_log"."action_code" IS '动作类别';

COMMENT ON COLUMN "cr_task_log"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_task_log"."mission_id" IS '任务id';

COMMENT ON COLUMN "cr_task_log"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_task_log"."room_id" IS '训练房间id';

COMMENT ON COLUMN "cr_task_log"."task_id" IS '任务id';

COMMENT ON COLUMN "cr_task_log"."update_time" IS '更新时间';

COMMENT ON COLUMN "cr_task_log"."user_id" IS '动作用户id';

COMMENT ON TABLE "cr_task_submit" IS '任务提交记录与评估';

COMMENT ON COLUMN "cr_task_submit"."answer" IS '提交答案';

COMMENT ON COLUMN "cr_task_submit"."answer_extension" IS '提交答案附加信息';

COMMENT ON COLUMN "cr_task_submit"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_task_submit"."detail" IS '评估详情';

COMMENT ON COLUMN "cr_task_submit"."evaluate_status" IS '评估状态';

COMMENT ON COLUMN "cr_task_submit"."evaluate_time" IS '评估时间';

COMMENT ON COLUMN "cr_task_submit"."evaluator_id" IS '评估人';

COMMENT ON COLUMN "cr_task_submit"."mission_score" IS '任务分值';

COMMENT ON COLUMN "cr_task_submit"."reason" IS '评估理由';

COMMENT ON COLUMN "cr_task_submit"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_task_submit"."room_id" IS '训练房间id';

COMMENT ON COLUMN "cr_task_submit"."score" IS '得分';

COMMENT ON COLUMN "cr_task_submit"."task_id" IS '任务id';

COMMENT ON COLUMN "cr_task_submit"."update_time" IS '更新时间';

COMMENT ON TABLE "cr_team_room" IS '团队训练房间详情';

COMMENT ON COLUMN "cr_team_room"."area_path" IS '攻击路径';

COMMENT ON COLUMN "cr_team_room"."copyed_topo_id" IS '拓扑id';

COMMENT ON COLUMN "cr_team_room"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_team_room"."create_user_id" IS '创建人id';

COMMENT ON COLUMN "cr_team_room"."description" IS '描述';

COMMENT ON COLUMN "cr_team_room"."difficulty" IS '难度';

COMMENT ON COLUMN "cr_team_room"."enable_recording" IS '录屏状态';

COMMENT ON COLUMN "cr_team_room"."end_time" IS '结束时间';

COMMENT ON COLUMN "cr_team_room"."local_scene_id" IS '当前启动场景id';

COMMENT ON COLUMN "cr_team_room"."logo" IS '封面url';

COMMENT ON COLUMN "cr_team_room"."modify_user_id" IS '修改人id';

COMMENT ON COLUMN "cr_team_room"."name" IS '房间名';

COMMENT ON COLUMN "cr_team_room"."pre_skills" IS '前置技能';

COMMENT ON COLUMN "cr_team_room"."replay_scene_id" IS '复盘场景id';

COMMENT ON COLUMN "cr_team_room"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_team_room"."scene_id_record" IS '训练过程中的所有场景id';

COMMENT ON COLUMN "cr_team_room"."start_time" IS '开始时间';

COMMENT ON COLUMN "cr_team_room"."sub_type" IS '训练类别';

COMMENT ON COLUMN "cr_team_room"."team_template_id" IS '团队模板id';

COMMENT ON COLUMN "cr_team_room"."team_template_name" IS '团队模板名称';

COMMENT ON COLUMN "cr_team_room"."time_line_config" IS '训练时间线信息';

COMMENT ON COLUMN "cr_team_room"."train_skills" IS '训练目标';

COMMENT ON COLUMN "cr_team_room"."training_status" IS '训练状态';

COMMENT ON COLUMN "cr_team_room"."update_time" IS '更新时间';

COMMENT ON TABLE "cr_team_template" IS '团队训练模板详情';

COMMENT ON COLUMN "cr_team_template"."business_template_id" IS '训练模板id';

COMMENT ON COLUMN "cr_team_template"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_team_template"."duration_time" IS '训练时长';

COMMENT ON COLUMN "cr_team_template"."people_number" IS '训练参与人数';

COMMENT ON COLUMN "cr_team_template"."pre_skills" IS '前置技能';

COMMENT ON COLUMN "cr_team_template"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_team_template"."role_config" IS '角色信息配置';

COMMENT ON COLUMN "cr_team_template"."situation_config" IS '态势配置';

COMMENT ON COLUMN "cr_team_template"."sub_type" IS '训练类别';

COMMENT ON COLUMN "cr_team_template"."time_line_config" IS '时间线配置';

COMMENT ON COLUMN "cr_team_template"."train_skills" IS '训练目标';

COMMENT ON COLUMN "cr_team_template"."update_time" IS '更新时间';

CREATE TABLE "rtks_attack_train_relation"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(32) NULL,
    "attack_id" VARCHAR(32) NOT NULL,
    "business_template_id" VARCHAR(32) NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "rtks_course_name"
(
    "id" INT IDENTITY(270,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "name" VARCHAR(256) NULL,
    "description" CLOB NULL,
    "difficulty" INT NULL,
    "lesson_count" INT NULL,
    "e_book" INT NULL,
    "course_num" VARCHAR(64) NULL,
    "exercise_count" INT NULL,
    "theory_count" INT NULL,
    "direction" VARCHAR(64) NULL,
    "direction_name" VARCHAR(64) NULL,
    "interview" INT NULL
);
CREATE TABLE "rtks_course_related"
(
    "resource_id" VARCHAR(64) NOT NULL,
    "related_id" VARCHAR(64) NOT NULL,
    "type" INT NOT NULL,
    "weight" INT NOT NULL
);
CREATE TABLE "rtks_engage"
(
    "id" INT IDENTITY(52,1) NOT NULL,
    "resource_id" VARCHAR(32) NOT NULL,
    "category_id" VARCHAR(32) NOT NULL,
    "sub_category_id" VARCHAR(32) NOT NULL,
    "name_zh" VARCHAR(255) NOT NULL,
    "name_en" VARCHAR(255) NOT NULL
);
CREATE TABLE "rtks_engage_category"
(
    "id" INT IDENTITY(6,1) NOT NULL,
    "resource_id" VARCHAR(32) NOT NULL,
    "name_zh" VARCHAR(255) NOT NULL,
    "name_en" VARCHAR(255) NOT NULL
);
CREATE TABLE "rtks_engage_sub_category"
(
    "id" INT IDENTITY(10,1) NOT NULL,
    "resource_id" VARCHAR(32) NOT NULL,
    "category_id" VARCHAR(32) NOT NULL,
    "name_zh" VARCHAR(255) NOT NULL,
    "name_en" VARCHAR(255) NOT NULL
);
CREATE TABLE "rtks_engage_train_relation"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(32) NULL,
    "engage_id" VARCHAR(32) NOT NULL,
    "business_template_id" VARCHAR(32) NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "rtks_knowledge"
(
    "id" INT IDENTITY(363,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "category_id" VARCHAR(40) NOT NULL,
    "content" VARCHAR(512) NOT NULL,
    "courses" TEXT NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "rtks_knowledge_category"
(
    "id" INT IDENTITY(20,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "content" VARCHAR(512) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "rtks_level"
(
    "id" INT IDENTITY(43,1) NOT NULL,
    "resource_id" VARCHAR(255) NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "description" VARCHAR(255) NULL,
    "parent" VARCHAR(255) NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL,
    "frame" VARCHAR(255) NULL
);
CREATE TABLE "rtks_role"
(
    "id" INT IDENTITY(126,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "description" TEXT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL,
    "level_id" VARCHAR(64) NOT NULL,
    "category" VARCHAR(64) NOT NULL,
    "task_ids" TEXT NOT NULL,
    "knowledge_id_weights" TEXT NOT NULL,
    "knowledge_ids" TEXT NOT NULL,
    "skill_id_weights" TEXT NOT NULL,
    "skill_ids" TEXT NOT NULL,
    "ability_ids" TEXT NOT NULL,
    "skill_courses" TEXT NULL,
    "knowledge_courses" TEXT NULL,
    "knowledge_weight" TINYINT DEFAULT 50
        NOT NULL,
    "skill_missions" TEXT DEFAULT '''[]'''
        NULL,
    "avatar" VARCHAR(2048) NOT NULL
);
CREATE TABLE "rtks_role_match_record"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "user_id" VARCHAR(32) NOT NULL,
    "role_id" VARCHAR(32) NOT NULL,
    "match" TINYINT NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "rtks_role_related"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "role_id" VARCHAR(255) NOT NULL,
    "related_id" VARCHAR(255) NOT NULL,
    "type" INT NOT NULL
);
CREATE TABLE "rtks_role_skill_relation"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "role_id" VARCHAR(32) NOT NULL,
    "skill_id" VARCHAR(32) NOT NULL,
    "weight" TINYINT NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "rtks_situation_log"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "user_id" VARCHAR(32) NOT NULL,
    "user_name" VARCHAR(255) NOT NULL,
    "role_id" VARCHAR(32) NOT NULL,
    "role_name" VARCHAR(255) NOT NULL,
    "type" TINYINT NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "rtks_skill"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(255) NOT NULL,
    "content" VARCHAR(2048) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL,
    "system_id" VARCHAR(40) DEFAULT ''
                               NOT NULL,
    "category_id" VARCHAR(40) DEFAULT ''
                               NOT NULL,
    "courses" TEXT DEFAULT '''[]'''
                               NOT NULL
);
CREATE TABLE "rtks_skill_category"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "content" VARCHAR(512) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "rtks_skill_mission_relation"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "skill_id" VARCHAR(32) NOT NULL,
    "mission_template_id" VARCHAR(32) NOT NULL,
    "weight" TINYINT NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "rtks_skill_system"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "content" VARCHAR(512) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL,
    "scene_id" INT NOT NULL
);
CREATE TABLE "rtks_task"
(
    "id" INT IDENTITY(134,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "content" VARCHAR(512) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "rtks_user_role"
(
    "id" INT IDENTITY(11,1) NOT NULL,
    "user_id" VARCHAR(64) NOT NULL,
    "role_id" VARCHAR(64) NOT NULL,
    "role_name" VARCHAR(512) NOT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL,
    "user_name" VARCHAR(128) NOT NULL
);
CREATE TABLE "situation_attack_message"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(64) NOT NULL,
    "scene_id" VARCHAR(64) NULL,
    "type" VARCHAR(64) NULL,
    "description" VARCHAR(255) NULL,
    "source_id" VARCHAR(64) NULL,
    "target_id" VARCHAR(64) NULL,
    "interval_time" INT NULL,
    "duration_time" INT NULL,
    "look_at_target" VARCHAR(10) NULL,
    "line_color" VARCHAR(64) NULL,
    "line_width" INT NULL,
    "line_type" VARCHAR(255) NULL,
    "media_animation" TEXT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "situation_config"
(
    "id" INT IDENTITY(37,1) NOT NULL,
    "scope" TEXT NULL,
    "node_id" VARCHAR(50) NULL,
    "node_name" VARCHAR(255) NULL,
    "node_type" TINYINT NOT NULL,
    "attribute" TINYINT NOT NULL,
    "attribute_value" VARCHAR(255) NOT NULL,
    "resource_id" VARCHAR(50) NOT NULL
);
CREATE TABLE "situation_dynamic_scheme"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(32) NOT NULL,
    "scene_id" VARCHAR(32) NULL,
    "name" VARCHAR(255) NULL,
    "scheme_data" TEXT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "situation_guide_history"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(32) NOT NULL,
    "scene_id" VARCHAR(32) NULL,
    "room_id" VARCHAR(32) NULL,
    "guide_id" VARCHAR(32) NULL,
    "business_type" INT NOT NULL,
    "event_name" VARCHAR(255) NULL,
    "type" INT NOT NULL,
    "event_type" INT NOT NULL,
    "category" INT NULL,
    "source" VARCHAR(128) NULL,
    "target" VARCHAR(128) NULL,
    "result" INT NULL,
    "mission_id" VARCHAR(32) NULL,
    "task_id" VARCHAR(32) NULL,
    "role" INT NULL,
    "trigger_condition" INT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "situation_guide_scheme"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(32) NOT NULL,
    "room_id" VARCHAR(32) NULL,
    "business_type" INT NOT NULL,
    "event_name" VARCHAR(255) NULL,
    "type" INT NOT NULL,
    "event_type" INT NOT NULL,
    "category" INT NULL,
    "source" VARCHAR(128) NULL,
    "target" VARCHAR(128) NULL,
    "result" INT NULL,
    "role" INT NULL,
    "mission_id" VARCHAR(32) NULL,
    "trigger_condition" INT NULL,
    "create_time" TIMESTAMP(0) NOT NULL,
    "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "situation_scene_config"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "resource_id" VARCHAR(32) NULL,
    "scene_id" VARCHAR(32) NULL,
    "flow_analysis" TINYINT NULL,
    "flow_auto_run" TINYINT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "system_property"
(
    "id" INT IDENTITY(3,1) NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "desc" VARCHAR(255) NULL,
    "content" VARCHAR(255) NOT NULL
);
CREATE TABLE "system_use_status"
(
    "id" INT IDENTITY(6,1) NOT NULL,
    "cpu" VARCHAR(32) NOT NULL,
    "disk" VARCHAR(32) NOT NULL,
    "ram" TINYINT NOT NULL,
    "alter_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "video_progress"
(
    "id" INT IDENTITY(1,1) NOT NULL,
    "path" VARCHAR(255) NOT NULL,
    "business_id" VARCHAR(255) NULL,
    "user_id" VARCHAR(32) NOT NULL,
    "time" INT NOT NULL,
    "watched_point" TEXT NOT NULL,
    "create_time" TIMESTAMP(0) NULL,
    "update_time" TIMESTAMP(0) NULL
);
ALTER TABLE "rtks_attack_train_relation" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_course_name" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_engage" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_engage_category" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_engage_sub_category" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_engage_train_relation" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_knowledge" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_knowledge_category" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_level" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_role" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_role_match_record" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_role_related" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_role_skill_relation" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_situation_log" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_skill" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_skill_category" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_skill_mission_relation" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_skill_system" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_task" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "rtks_user_role" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "situation_attack_message" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "situation_config" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "situation_dynamic_scheme" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "situation_guide_history" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "situation_guide_scheme" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "situation_scene_config" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "system_property" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "system_use_status" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

ALTER TABLE "video_progress" ADD CONSTRAINT  NOT CLUSTER  PRIMARY KEY("id") ;

CREATE INDEX "INDEX352819822261800"
    ON "rtks_attack_train_relation"("business_template_id");
ALTER INDEX "INDEX352819822261800" VISIBLE;

CREATE INDEX "attack_id"
    ON "rtks_attack_train_relation"("attack_id");
ALTER INDEX "attack_id" VISIBLE;

CREATE INDEX "INDEX352819836147100"
    ON "rtks_course_related"("related_id");
ALTER INDEX "INDEX352819836147100" VISIBLE;

CREATE INDEX "INDEX352819854223900"
    ON "rtks_course_related"("resource_id");
ALTER INDEX "INDEX352819854223900" VISIBLE;

CREATE INDEX "INDEX352819872007200"
    ON "rtks_engage"("resource_id");
ALTER INDEX "INDEX352819872007200" VISIBLE;

CREATE INDEX "sub_category_id"
    ON "rtks_engage"("sub_category_id");
ALTER INDEX "sub_category_id" VISIBLE;

CREATE INDEX "INDEX352820012446900"
    ON "rtks_engage_sub_category"("category_id");
ALTER INDEX "INDEX352820012446900" VISIBLE;

CREATE INDEX "INDEX352820039879600"
    ON "rtks_engage_train_relation"("business_template_id");
ALTER INDEX "INDEX352820039879600" VISIBLE;

CREATE INDEX "engage_id"
    ON "rtks_engage_train_relation"("engage_id");
ALTER INDEX "engage_id" VISIBLE;

CREATE INDEX "role_id"
    ON "rtks_role_related"("role_id");
ALTER INDEX "role_id" VISIBLE;

CREATE INDEX "INDEX352820068507100"
    ON "rtks_role_skill_relation"("role_id");
ALTER INDEX "INDEX352820068507100" VISIBLE;

CREATE INDEX "skill"
    ON "rtks_skill_mission_relation"("skill_id");
ALTER INDEX "skill" VISIBLE;

CREATE INDEX "INDEX352795738793100"
    ON "cr_train_record"("business_template_id");
ALTER INDEX "INDEX352795738793100" VISIBLE;

CREATE INDEX "room_id"
    ON "cr_train_record"("room_id");
ALTER INDEX "room_id" VISIBLE;

CREATE INDEX "user_id"
    ON "cr_train_record"("user_id");
ALTER INDEX "user_id" VISIBLE;

CREATE INDEX "INDEX352859476907000"
    ON "cr_single_template"("business_template_id");
ALTER INDEX "INDEX352859476907000" VISIBLE;

ALTER TABLE "rtks_course_name" ADD UNIQUE("resource_id") ;

ALTER TABLE "rtks_engage_category" ADD UNIQUE("resource_id") ;

ALTER TABLE "rtks_knowledge" ADD UNIQUE("resource_id") ;

ALTER TABLE "rtks_knowledge_category" ADD UNIQUE("resource_id") ;

ALTER TABLE "rtks_level" ADD UNIQUE("resource_id") ;

ALTER TABLE "rtks_role" ADD UNIQUE("resource_id") ;

ALTER TABLE "rtks_role_match_record" ADD CONSTRAINT "user_role" UNIQUE("user_id","role_id") ;

ALTER TABLE "rtks_situation_log" ADD CONSTRAINT "user_role_type" UNIQUE("user_id","role_id","type") ;

ALTER TABLE "rtks_skill" ADD UNIQUE("resource_id") ;

ALTER TABLE "rtks_skill_category" ADD CONSTRAINT "resource_id_UNIQUE" UNIQUE("resource_id") ;

ALTER TABLE "rtks_skill_system" ADD UNIQUE("resource_id") ;

ALTER TABLE "rtks_task" ADD UNIQUE("resource_id") ;

ALTER TABLE "rtks_user_role" ADD CONSTRAINT "user_uni" UNIQUE("user_id") ;

ALTER TABLE "situation_attack_message" ADD UNIQUE("resource_id") ;

ALTER TABLE "situation_config" ADD UNIQUE("resource_id") ;

ALTER TABLE "situation_dynamic_scheme" ADD UNIQUE("resource_id") ;

ALTER TABLE "situation_guide_history" ADD UNIQUE("resource_id") ;

ALTER TABLE "situation_guide_scheme" ADD UNIQUE("mission_id") ;

ALTER TABLE "situation_guide_scheme" ADD UNIQUE("resource_id") ;

ALTER TABLE "situation_scene_config" ADD CONSTRAINT "scene_id" UNIQUE("scene_id") ;

ALTER TABLE "system_property" ADD CONSTRAINT "idx_name" UNIQUE("name") ;

ALTER TABLE "video_progress" ADD CONSTRAINT "path_user" UNIQUE("user_id","path") ;

COMMENT ON TABLE "rtks_attack_train_relation" IS 'att&ck场景训练关联表';

COMMENT ON COLUMN "rtks_attack_train_relation"."attack_id" IS 'attack框架id';

COMMENT ON COLUMN "rtks_attack_train_relation"."business_template_id" IS '训练模板id';

COMMENT ON COLUMN "rtks_attack_train_relation"."create_time" IS '创建时间';

COMMENT ON COLUMN "rtks_attack_train_relation"."id" IS '自增id';

COMMENT ON COLUMN "rtks_attack_train_relation"."resource_id" IS '资源id';

COMMENT ON COLUMN "rtks_attack_train_relation"."update_time" IS '更新时间';

COMMENT ON TABLE "rtks_course_name" IS 'rtks初始化课程数据表';

COMMENT ON COLUMN "rtks_course_name"."course_num" IS '课程数量';

COMMENT ON COLUMN "rtks_course_name"."description" IS '描述';

COMMENT ON COLUMN "rtks_course_name"."difficulty" IS '难度';

COMMENT ON COLUMN "rtks_course_name"."direction" IS '课程方向';

COMMENT ON COLUMN "rtks_course_name"."direction_name" IS '方向名称';

COMMENT ON COLUMN "rtks_course_name"."e_book" IS '类型';

COMMENT ON COLUMN "rtks_course_name"."exercise_count" IS '实践课数量';

COMMENT ON COLUMN "rtks_course_name"."id" IS '自增id';

COMMENT ON COLUMN "rtks_course_name"."interview" IS '访问权限';

COMMENT ON COLUMN "rtks_course_name"."lesson_count" IS '课时数量';

COMMENT ON COLUMN "rtks_course_name"."name" IS '名称';

COMMENT ON COLUMN "rtks_course_name"."resource_id" IS '资源id';

COMMENT ON COLUMN "rtks_course_name"."theory_count" IS '理论课数量';

COMMENT ON TABLE "rtks_course_related" IS 'rtks知识点课程关联表';

COMMENT ON COLUMN "rtks_course_related"."related_id" IS '知识点id';

COMMENT ON COLUMN "rtks_course_related"."resource_id" IS '课程id';

COMMENT ON COLUMN "rtks_course_related"."type" IS '类型：1：知识点;2：技能点（已取消）';

COMMENT ON COLUMN "rtks_course_related"."weight" IS '权重';

COMMENT ON TABLE "rtks_engage" IS 'engage框架表';

COMMENT ON COLUMN "rtks_engage"."category_id" IS '类别id';

COMMENT ON COLUMN "rtks_engage"."id" IS '自增id';

COMMENT ON COLUMN "rtks_engage"."name_en" IS '英文名称';

COMMENT ON COLUMN "rtks_engage"."name_zh" IS '中文名称';

COMMENT ON COLUMN "rtks_engage"."resource_id" IS '资源id';

COMMENT ON COLUMN "rtks_engage"."sub_category_id" IS '子类别id';

COMMENT ON TABLE "rtks_engage_category" IS 'engage框架类别表';

COMMENT ON COLUMN "rtks_engage_category"."id" IS '自增id';

COMMENT ON COLUMN "rtks_engage_category"."name_en" IS '英文名称';

COMMENT ON COLUMN "rtks_engage_category"."name_zh" IS '中文名称';

COMMENT ON COLUMN "rtks_engage_category"."resource_id" IS '资源id';

COMMENT ON TABLE "rtks_engage_sub_category" IS 'engage框架子类别表';

COMMENT ON COLUMN "rtks_engage_sub_category"."category_id" IS '类别id';

COMMENT ON COLUMN "rtks_engage_sub_category"."id" IS '自增id';

COMMENT ON COLUMN "rtks_engage_sub_category"."name_en" IS '英文名称';

COMMENT ON COLUMN "rtks_engage_sub_category"."name_zh" IS '中文名称';

COMMENT ON COLUMN "rtks_engage_sub_category"."resource_id" IS '资源id';

COMMENT ON TABLE "rtks_engage_train_relation" IS 'engage场景训练关联表';

COMMENT ON COLUMN "rtks_engage_train_relation"."business_template_id" IS '训练模板id';

COMMENT ON COLUMN "rtks_engage_train_relation"."create_time" IS '创建时间';

COMMENT ON COLUMN "rtks_engage_train_relation"."engage_id" IS 'engage框架id';

COMMENT ON COLUMN "rtks_engage_train_relation"."id" IS '自增id';

COMMENT ON COLUMN "rtks_engage_train_relation"."resource_id" IS '资源id';

COMMENT ON COLUMN "rtks_engage_train_relation"."update_time" IS '更新时间';

COMMENT ON TABLE "rtks_knowledge" IS 'rtks知识点表';

COMMENT ON COLUMN "rtks_knowledge"."category_id" IS '类别id';

COMMENT ON COLUMN "rtks_knowledge"."content" IS '内容';

COMMENT ON COLUMN "rtks_knowledge"."courses" IS '关联课程列表';

COMMENT ON COLUMN "rtks_knowledge"."create_time" IS '创建时间';

COMMENT ON COLUMN "rtks_knowledge"."id" IS '自增id';

COMMENT ON COLUMN "rtks_knowledge"."resource_id" IS '资源id';

COMMENT ON COLUMN "rtks_knowledge"."update_time" IS '更新时间';

COMMENT ON TABLE "rtks_knowledge_category" IS 'rtks知识点类别表';

COMMENT ON COLUMN "rtks_knowledge_category"."content" IS '内容';

COMMENT ON COLUMN "rtks_knowledge_category"."create_time" IS '创建时间';

COMMENT ON COLUMN "rtks_knowledge_category"."id" IS '自增id';

COMMENT ON COLUMN "rtks_knowledge_category"."resource_id" IS '资源id';

COMMENT ON COLUMN "rtks_knowledge_category"."update_time" IS '更新时间';

COMMENT ON TABLE "rtks_level" IS 'rtks框架层级表';

COMMENT ON COLUMN "rtks_level"."create_time" IS '创建时间';

COMMENT ON COLUMN "rtks_level"."description" IS '描述';

COMMENT ON COLUMN "rtks_level"."frame" IS '框架id';

COMMENT ON COLUMN "rtks_level"."id" IS '自增id';

COMMENT ON COLUMN "rtks_level"."name" IS '名称';

COMMENT ON COLUMN "rtks_level"."parent" IS '父级id';

COMMENT ON COLUMN "rtks_level"."resource_id" IS '资源id';

COMMENT ON COLUMN "rtks_level"."update_time" IS '更新时间';

COMMENT ON TABLE "rtks_role" IS 'rtks角色表';

COMMENT ON COLUMN "rtks_role"."ability_ids" IS '能力列表';

COMMENT ON COLUMN "rtks_role"."avatar" IS '头像';

COMMENT ON COLUMN "rtks_role"."category" IS '类别';

COMMENT ON COLUMN "rtks_role"."create_time" IS '创建时间';

COMMENT ON COLUMN "rtks_role"."description" IS '描述';

COMMENT ON COLUMN "rtks_role"."id" IS '自增id';

COMMENT ON COLUMN "rtks_role"."knowledge_courses" IS '知识点课程列表';

COMMENT ON COLUMN "rtks_role"."knowledge_id_weights" IS '知识点权重列表';

COMMENT ON COLUMN "rtks_role"."knowledge_ids" IS '知识点列表';

COMMENT ON COLUMN "rtks_role"."knowledge_weight" IS '知识点比例';

COMMENT ON COLUMN "rtks_role"."level_id" IS '层级id';

COMMENT ON COLUMN "rtks_role"."name" IS '名称';

COMMENT ON COLUMN "rtks_role"."resource_id" IS '资源id';

COMMENT ON COLUMN "rtks_role"."skill_courses" IS '技能课程列表';

COMMENT ON COLUMN "rtks_role"."skill_id_weights" IS '技能点权重列表';

COMMENT ON COLUMN "rtks_role"."skill_ids" IS '技能点列表';

COMMENT ON COLUMN "rtks_role"."skill_missions" IS '技能任务列表';

COMMENT ON COLUMN "rtks_role"."task_ids" IS '任务列表';

COMMENT ON COLUMN "rtks_role"."update_time" IS '更新时间';

COMMENT ON TABLE "rtks_role_skill_relation" IS 'rtks角色技能点关联表';

COMMENT ON COLUMN "rtks_role_skill_relation"."create_time" IS '创建时间';

COMMENT ON COLUMN "rtks_role_skill_relation"."id" IS '自增id';

COMMENT ON COLUMN "rtks_role_skill_relation"."role_id" IS '角色id';

COMMENT ON COLUMN "rtks_role_skill_relation"."skill_id" IS '技能id';

COMMENT ON COLUMN "rtks_role_skill_relation"."update_time" IS '更新时间';

COMMENT ON COLUMN "rtks_role_skill_relation"."weight" IS '权重';

COMMENT ON TABLE "rtks_situation_log" IS 'rtks角色态势动态表';

COMMENT ON COLUMN "rtks_situation_log"."create_time" IS '创建时间';

COMMENT ON COLUMN "rtks_situation_log"."id" IS '自增id';

COMMENT ON COLUMN "rtks_situation_log"."role_id" IS '角色id';

COMMENT ON COLUMN "rtks_situation_log"."role_name" IS '角色名';

COMMENT ON COLUMN "rtks_situation_log"."type" IS '类型：1.合格;2优秀';

COMMENT ON COLUMN "rtks_situation_log"."user_id" IS '用户id';

COMMENT ON COLUMN "rtks_situation_log"."user_name" IS '用户名';

COMMENT ON TABLE "rtks_skill" IS 'rtks技能点表';

COMMENT ON COLUMN "rtks_skill"."category_id" IS '类别id';

COMMENT ON COLUMN "rtks_skill"."content" IS '名称';

COMMENT ON COLUMN "rtks_skill"."courses" IS '课程列表';

COMMENT ON COLUMN "rtks_skill"."create_time" IS '创建时间';

COMMENT ON COLUMN "rtks_skill"."id" IS '自增id';

COMMENT ON COLUMN "rtks_skill"."resource_id" IS '资源id';

COMMENT ON COLUMN "rtks_skill"."system_id" IS '体系id';

COMMENT ON COLUMN "rtks_skill"."update_time" IS '更新时间';

COMMENT ON TABLE "rtks_skill_category" IS 'rtks技能点类别表';

COMMENT ON COLUMN "rtks_skill_category"."content" IS '内容';

COMMENT ON COLUMN "rtks_skill_category"."create_time" IS '创建时间';

COMMENT ON COLUMN "rtks_skill_category"."id" IS '自增id';

COMMENT ON COLUMN "rtks_skill_category"."resource_id" IS '资源id';

COMMENT ON COLUMN "rtks_skill_category"."update_time" IS '更新时间';

COMMENT ON TABLE "rtks_skill_mission_relation" IS 'rtks技能点任务关联表';

COMMENT ON COLUMN "rtks_skill_mission_relation"."create_time" IS '创建时间';

COMMENT ON COLUMN "rtks_skill_mission_relation"."id" IS '自增id';

COMMENT ON COLUMN "rtks_skill_mission_relation"."mission_template_id" IS '任务模板id';

COMMENT ON COLUMN "rtks_skill_mission_relation"."skill_id" IS '技能点id';

COMMENT ON COLUMN "rtks_skill_mission_relation"."update_time" IS '更新时间';

COMMENT ON COLUMN "rtks_skill_mission_relation"."weight" IS '权重';

COMMENT ON TABLE "rtks_skill_system" IS 'rtks技能点体系表';

COMMENT ON COLUMN "rtks_skill_system"."content" IS '内容';

COMMENT ON COLUMN "rtks_skill_system"."create_time" IS '创建时间';

COMMENT ON COLUMN "rtks_skill_system"."id" IS '自增id';

COMMENT ON COLUMN "rtks_skill_system"."resource_id" IS '资源id';

COMMENT ON COLUMN "rtks_skill_system"."scene_id" IS '场景id';

COMMENT ON COLUMN "rtks_skill_system"."update_time" IS '更新时间';

COMMENT ON TABLE "rtks_task" IS 'rtks任务表';

COMMENT ON COLUMN "rtks_task"."content" IS '内容';

COMMENT ON COLUMN "rtks_task"."create_time" IS '创建时间';

COMMENT ON COLUMN "rtks_task"."id" IS '自增id';

COMMENT ON COLUMN "rtks_task"."resource_id" IS '资源id';

COMMENT ON COLUMN "rtks_task"."update_time" IS '更新时间';

COMMENT ON TABLE "rtks_user_role" IS 'rtks用户角色表';

COMMENT ON COLUMN "rtks_user_role"."create_time" IS '创建时间';

COMMENT ON COLUMN "rtks_user_role"."id" IS '自增id';

COMMENT ON COLUMN "rtks_user_role"."role_id" IS '角色id';

COMMENT ON COLUMN "rtks_user_role"."role_name" IS '角色名称';

COMMENT ON COLUMN "rtks_user_role"."update_time" IS '更新时间';

COMMENT ON COLUMN "rtks_user_role"."user_id" IS '用户id';

COMMENT ON COLUMN "rtks_user_role"."user_name" IS '用户名';

COMMENT ON TABLE "situation_config" IS '态势自定义属性表';

COMMENT ON COLUMN "situation_config"."attribute" IS '参数类型';

COMMENT ON COLUMN "situation_config"."attribute_value" IS '参数值';

COMMENT ON COLUMN "situation_config"."id" IS '自增id';

COMMENT ON COLUMN "situation_config"."node_id" IS '节点id';

COMMENT ON COLUMN "situation_config"."node_name" IS '节点名称';

COMMENT ON COLUMN "situation_config"."node_type" IS '节点类型';

COMMENT ON COLUMN "situation_config"."resource_id" IS '资源id';

COMMENT ON COLUMN "situation_config"."scope" IS '作用域';

COMMENT ON TABLE "situation_scene_config" IS '态势场景配置表';

COMMENT ON COLUMN "situation_scene_config"."create_time" IS '创建时间';

COMMENT ON COLUMN "situation_scene_config"."flow_analysis" IS '是否分析流量采集数据';

COMMENT ON COLUMN "situation_scene_config"."flow_auto_run" IS '是否自动运行仿真流量';

COMMENT ON COLUMN "situation_scene_config"."id" IS '自增id';

COMMENT ON COLUMN "situation_scene_config"."resource_id" IS '资源id';

COMMENT ON COLUMN "situation_scene_config"."scene_id" IS '场景id';

COMMENT ON COLUMN "situation_scene_config"."update_time" IS '更新时间';

COMMENT ON COLUMN "system_property"."content" IS '配置值';

COMMENT ON COLUMN "system_property"."desc" IS '配置描述';

COMMENT ON COLUMN "system_property"."name" IS '配置名称';

COMMENT ON TABLE "system_use_status" IS '系统使用状态表';

COMMENT ON COLUMN "system_use_status"."alter_time" IS '修改时间';

COMMENT ON COLUMN "system_use_status"."cpu" IS 'cpu使用率';

COMMENT ON COLUMN "system_use_status"."disk" IS '磁盘使用率';

COMMENT ON COLUMN "system_use_status"."id" IS '自增id';

COMMENT ON COLUMN "system_use_status"."ram" IS '内存使用率';

COMMENT ON COLUMN "system_use_status"."update_time" IS '更新时间';

COMMENT ON COLUMN "video_progress"."business_id" IS '业务id';

COMMENT ON COLUMN "video_progress"."create_time" IS '创建时间';

COMMENT ON COLUMN "video_progress"."path" IS '视频路径';

COMMENT ON COLUMN "video_progress"."time" IS '进度时间';

COMMENT ON COLUMN "video_progress"."update_time" IS '更新时间';

COMMENT ON COLUMN "video_progress"."user_id" IS '用户id';

COMMENT ON COLUMN "video_progress"."watched_point" IS '观看过的检查点';

COMMENT ON TABLE "cr_train_record" IS '训练历史';

COMMENT ON COLUMN "cr_train_record"."business_template_id" IS '训练模板id';

COMMENT ON COLUMN "cr_train_record"."business_type" IS '训练类型';

COMMENT ON COLUMN "cr_train_record"."end_time" IS '结束时间';

COMMENT ON COLUMN "cr_train_record"."extra" IS '附加信息';

COMMENT ON COLUMN "cr_train_record"."room_id" IS '房间id';

COMMENT ON COLUMN "cr_train_record"."room_name" IS '房间名';

COMMENT ON COLUMN "cr_train_record"."start_time" IS '开始时间';

COMMENT ON COLUMN "cr_train_record"."user_id" IS '参与人id';

COMMENT ON TABLE "cr_single_template" IS '单兵训模板详情';

COMMENT ON COLUMN "cr_single_template"."business_template_id" IS '训练模板id';

COMMENT ON COLUMN "cr_single_template"."create_time" IS '创建时间';

COMMENT ON COLUMN "cr_single_template"."duration_time" IS '训练时长';

COMMENT ON COLUMN "cr_single_template"."missions_scheme" IS '任务配置';

COMMENT ON COLUMN "cr_single_template"."pre_skills" IS '前置技能';

COMMENT ON COLUMN "cr_single_template"."resource_id" IS 'uuid';

COMMENT ON COLUMN "cr_single_template"."sub_type" IS '训练类别';

COMMENT ON COLUMN "cr_single_template"."train_skills" IS '训练目标';

COMMENT ON COLUMN "cr_single_template"."update_time" IS '更新时间';

CREATE INDEX "INDEX352858964420900"
    ON "rtks_role_match_record"("role_id");
ALTER INDEX "INDEX352858964420900" VISIBLE;

CREATE INDEX "INDEX352859059869000"
    ON "situation_attack_message"("scene_id");
ALTER INDEX "INDEX352859059869000" VISIBLE;

CREATE INDEX "INDEX352859149529500"
    ON "situation_dynamic_scheme"("scene_id");
ALTER INDEX "INDEX352859149529500" VISIBLE;

CREATE INDEX "INDEX352859234879700"
    ON "situation_guide_history"("room_id");
ALTER INDEX "INDEX352859234879700" VISIBLE;

CREATE INDEX "guide_id"
    ON "situation_guide_history"("guide_id");
ALTER INDEX "guide_id" VISIBLE;

CREATE INDEX "INDEX352859386605400"
    ON "situation_guide_scheme"("room_id");
ALTER INDEX "INDEX352859386605400" VISIBLE;

COMMENT ON TABLE "rtks_role_match_record" IS 'rtks角色匹配度表';

COMMENT ON COLUMN "rtks_role_match_record"."create_time" IS '创建时间';

COMMENT ON COLUMN "rtks_role_match_record"."id" IS '自增id';

COMMENT ON COLUMN "rtks_role_match_record"."match" IS '匹配度';

COMMENT ON COLUMN "rtks_role_match_record"."role_id" IS '角色id';

COMMENT ON COLUMN "rtks_role_match_record"."update_time" IS '更新时间';

COMMENT ON COLUMN "rtks_role_match_record"."user_id" IS '用户id';

COMMENT ON TABLE "situation_attack_message" IS '态势动效配置表';

COMMENT ON COLUMN "situation_attack_message"."create_time" IS '创建时间';

COMMENT ON COLUMN "situation_attack_message"."description" IS '描述';

COMMENT ON COLUMN "situation_attack_message"."duration_time" IS '持续时间（s）';

COMMENT ON COLUMN "situation_attack_message"."id" IS '自增id';

COMMENT ON COLUMN "situation_attack_message"."interval_time" IS '间隔时间（s）';

COMMENT ON COLUMN "situation_attack_message"."line_color" IS '颜色';

COMMENT ON COLUMN "situation_attack_message"."line_type" IS '线型';

COMMENT ON COLUMN "situation_attack_message"."line_width" IS '线宽';

COMMENT ON COLUMN "situation_attack_message"."look_at_target" IS '是否聚焦';

COMMENT ON COLUMN "situation_attack_message"."media_animation" IS '媒体动效列表';

COMMENT ON COLUMN "situation_attack_message"."resource_id" IS '资源id';

COMMENT ON COLUMN "situation_attack_message"."scene_id" IS '场景id';

COMMENT ON COLUMN "situation_attack_message"."source_id" IS '源节点id';

COMMENT ON COLUMN "situation_attack_message"."target_id" IS '目标节点id';

COMMENT ON COLUMN "situation_attack_message"."type" IS '动效类型';

COMMENT ON COLUMN "situation_attack_message"."update_time" IS '更新时间';

COMMENT ON TABLE "situation_dynamic_scheme" IS '态势动效方案表';

COMMENT ON COLUMN "situation_dynamic_scheme"."create_time" IS '创建时间';

COMMENT ON COLUMN "situation_dynamic_scheme"."id" IS '自增id';

COMMENT ON COLUMN "situation_dynamic_scheme"."name" IS '动效方案名称';

COMMENT ON COLUMN "situation_dynamic_scheme"."resource_id" IS '资源id';

COMMENT ON COLUMN "situation_dynamic_scheme"."scene_id" IS '场景id';

COMMENT ON COLUMN "situation_dynamic_scheme"."scheme_data" IS '动效方案数据';

COMMENT ON COLUMN "situation_dynamic_scheme"."update_time" IS '更新时间';

COMMENT ON TABLE "situation_guide_history" IS '态势导调历史记录表';

COMMENT ON COLUMN "situation_guide_history"."business_type" IS '训练类型 3:团队 4:红蓝';

COMMENT ON COLUMN "situation_guide_history"."category" IS '攻防类型';

COMMENT ON COLUMN "situation_guide_history"."create_time" IS '创建时间';

COMMENT ON COLUMN "situation_guide_history"."event_name" IS '事件名称';

COMMENT ON COLUMN "situation_guide_history"."event_type" IS '事件类型';

COMMENT ON COLUMN "situation_guide_history"."guide_id" IS '导调id';

COMMENT ON COLUMN "situation_guide_history"."id" IS '自增id';

COMMENT ON COLUMN "situation_guide_history"."mission_id" IS '任务id';

COMMENT ON COLUMN "situation_guide_history"."resource_id" IS '资源id';

COMMENT ON COLUMN "situation_guide_history"."result" IS '攻击结果 1:成功 2失败';

COMMENT ON COLUMN "situation_guide_history"."role" IS '所属角色 4:红方 5:蓝方';

COMMENT ON COLUMN "situation_guide_history"."room_id" IS '房间id';

COMMENT ON COLUMN "situation_guide_history"."scene_id" IS '场景id';

COMMENT ON COLUMN "situation_guide_history"."source" IS '攻击源';

COMMENT ON COLUMN "situation_guide_history"."target" IS '攻击目标';

COMMENT ON COLUMN "situation_guide_history"."task_id" IS '下发任务id';

COMMENT ON COLUMN "situation_guide_history"."trigger_condition" IS '触发条件1:任务提交 2:任务判断成功';

COMMENT ON COLUMN "situation_guide_history"."type" IS '类型1:任务导调 2:手动导调';

COMMENT ON COLUMN "situation_guide_history"."update_time" IS '更新时间';

COMMENT ON TABLE "situation_guide_scheme" IS '态势导调方案表';

COMMENT ON COLUMN "situation_guide_scheme"."business_type" IS '训练类型 3:团队 4:红蓝';

COMMENT ON COLUMN "situation_guide_scheme"."category" IS '攻防类型';

COMMENT ON COLUMN "situation_guide_scheme"."create_time" IS '创建时间';

COMMENT ON COLUMN "situation_guide_scheme"."event_name" IS '事件名称';

COMMENT ON COLUMN "situation_guide_scheme"."event_type" IS '事件类型';

COMMENT ON COLUMN "situation_guide_scheme"."id" IS '自增id';

COMMENT ON COLUMN "situation_guide_scheme"."mission_id" IS '任务id';

COMMENT ON COLUMN "situation_guide_scheme"."resource_id" IS '资源id';

COMMENT ON COLUMN "situation_guide_scheme"."result" IS '攻击结果 1:成功 2:失败';

COMMENT ON COLUMN "situation_guide_scheme"."role" IS '所属角色 4:红方 5:蓝方';

COMMENT ON COLUMN "situation_guide_scheme"."room_id" IS '房间id';

COMMENT ON COLUMN "situation_guide_scheme"."source" IS '攻击源';

COMMENT ON COLUMN "situation_guide_scheme"."target" IS '攻击目标';

COMMENT ON COLUMN "situation_guide_scheme"."trigger_condition" IS '触发条件1:任务提交 2:任务判断成功';

COMMENT ON COLUMN "situation_guide_scheme"."type" IS '类型1:任务导调 2:手动导调';

COMMENT ON COLUMN "situation_guide_scheme"."update_time" IS '更新时间';

