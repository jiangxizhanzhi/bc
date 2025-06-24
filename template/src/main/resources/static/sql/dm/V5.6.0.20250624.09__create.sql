CREATE TABLE "CR_MISSION_SITUATION"
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
CREATE TABLE "CR_MISSION_TEMPLATE"
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
CREATE TABLE "CR_NOTICE"
(
 "id" INT IDENTITY(1,1) NOT NULL,
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
CREATE TABLE "CR_RADAR_LABEL"
(
 "id" INT IDENTITY(1,1) NOT NULL,
 "resource_id" INT NULL,
 "label" VARCHAR(255) NULL
);
CREATE TABLE "CR_RB_COMMON_DEVICE"
(
 "id" INT IDENTITY(1,1) NOT NULL,
 "resource_id" VARCHAR(64) NULL,
 "room_id" VARCHAR(64) NULL,
 "device_id" VARCHAR(64) NULL,
 "collect_user_id" VARCHAR(64) NULL,
 "create_user_id" VARCHAR(64) NULL,
 "create_time" TIMESTAMP(0) NULL
);
CREATE TABLE "CR_RB_DEVICE"
(
 "id" INT IDENTITY(1,1) NOT NULL,
 "resource_id" VARCHAR(64) NULL,
 "room_id" VARCHAR(64) NULL,
 "device_id" VARCHAR(64) NULL,
 "create_user_id" VARCHAR(64) NULL,
 "create_time" TIMESTAMP(0) NULL,
 "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "CR_RB_ROOM"
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
CREATE TABLE "CR_RB_TEMPLATE"
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
CREATE TABLE "CR_RB_TIME_MANAGEMENT"
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
 "recover_time" TIMESTAMP(6) DEFAULT current_timestamp(6)
 NOT NULL,
 "end_time" TIMESTAMP(6) NULL
);
CREATE TABLE "CR_ROLE"
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
CREATE TABLE "CR_SCORE"
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
CREATE TABLE "CR_SINGLE_ROOM"
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
CREATE TABLE "CR_SINGLE_TEMPLATE"
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
CREATE TABLE "CR_TASK"
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
CREATE TABLE "CR_TASK_LOG"
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
CREATE TABLE "CR_TASK_SUBMIT"
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
CREATE TABLE "CR_TEAM_COMMON_DEVICE"
(
 "id" INT IDENTITY(1,1) NOT NULL,
 "resource_id" VARCHAR(64) NULL,
 "room_id" VARCHAR(64) NULL,
 "device_id" VARCHAR(64) NULL,
 "collect_user_id" VARCHAR(64) NULL,
 "create_user_id" VARCHAR(64) NULL,
 "create_time" TIMESTAMP(0) NULL
);
CREATE TABLE "CR_TEAM_DEVICE"
(
 "id" INT IDENTITY(1,1) NOT NULL,
 "resource_id" VARCHAR(64) NULL,
 "room_id" VARCHAR(64) NULL,
 "device_id" VARCHAR(64) NULL,
 "create_user_id" VARCHAR(64) NULL,
 "create_time" TIMESTAMP(0) NULL,
 "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "CR_TEAM_ROOM"
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
CREATE TABLE "CR_TEAM_TEMPLATE"
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
CREATE TABLE "CR_TOOL_EXPLOIT"
(
 "id" INT NOT NULL,
 "status" VARCHAR(255) NULL,
 "create_time" TIMESTAMP(0) DEFAULT current_timestamp()
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
CREATE TABLE "CR_TOOL_GHDB"
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
CREATE TABLE "CR_TOOL_SHELLCODE"
(
 "id" INT NOT NULL,
 "status" VARCHAR(255) NULL,
 "create_time" TIMESTAMP(0) DEFAULT current_timestamp()
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
CREATE TABLE "CR_TOOL_TOOL"
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
CREATE TABLE "CR_TOOL_TOOLCATEGORY"
(
 "id" INT IDENTITY(1,1) NOT NULL,
 "resource_id" VARCHAR(64) NULL,
 "cn_name" VARCHAR(64) NULL,
 "en_name" VARCHAR(64) NULL,
 "serial_version_uid" CLOB NULL
);
