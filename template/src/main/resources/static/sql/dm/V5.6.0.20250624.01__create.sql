CREATE TABLE "FILE_CHUNK"
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

CREATE TABLE "INIT_RTKS_COURSE_RELATED"
(
 "resource_id" VARCHAR(64) NOT NULL,
 "related_id" VARCHAR(64) NOT NULL,
 "type" INT NOT NULL,
 "weight" INT NOT NULL
);
CREATE TABLE "INIT_RTKS_KNOWLEDGE"
(
 "id" INT IDENTITY(1,1) NOT NULL,
 "resource_id" VARCHAR(64) NOT NULL,
 "category_id" VARCHAR(40) NOT NULL,
 "content" VARCHAR(512) NOT NULL,
 "courses" TEXT NOT NULL,
 "create_time" TIMESTAMP(0) NOT NULL,
 "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "INIT_RTKS_KNOWLEDGE_CATEGORY"
(
 "id" INT IDENTITY(1,1) NOT NULL,
 "resource_id" VARCHAR(64) NOT NULL,
 "content" VARCHAR(512) NOT NULL,
 "create_time" TIMESTAMP(0) NOT NULL,
 "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "INIT_RTKS_LEVEL"
(
 "id" INT IDENTITY(1,1) NOT NULL,
 "resource_id" VARCHAR(255) NOT NULL,
 "name" VARCHAR(255) NOT NULL,
 "description" VARCHAR(255) NULL,
 "parent" VARCHAR(255) NULL,
 "create_time" TIMESTAMP(0) NULL,
 "update_time" TIMESTAMP(0) NULL,
 "frame" VARCHAR(255) NULL
);
CREATE TABLE "INIT_RTKS_ROLE"
(
 "id" INT IDENTITY(1,1) NOT NULL,
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
CREATE TABLE "INIT_RTKS_ROLE_SKILL_RELATION"
(
 "id" INT IDENTITY(1,1) NOT NULL,
 "role_id" VARCHAR(32) NOT NULL,
 "skill_id" VARCHAR(32) NOT NULL,
 "weight" TINYINT NOT NULL,
 "create_time" TIMESTAMP(0) NULL,
 "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "INIT_RTKS_TASK"
(
 "id" INT IDENTITY(1,1) NOT NULL,
 "resource_id" VARCHAR(64) NOT NULL,
 "content" VARCHAR(512) NOT NULL,
 "create_time" TIMESTAMP(0) NOT NULL,
 "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "LOCAL_SCENE"
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
CREATE TABLE "OJ_CLASSROOM"
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
CREATE TABLE "OJ_CLASSROOM_GROUP_ARCHIVE"
(
 "id" INT IDENTITY(1,1) NOT NULL,
 "resource_id" VARCHAR(64) NOT NULL,
 "name" VARCHAR(128) NOT NULL,
 "groups" CLOB NOT NULL,
 "classroom_id" VARCHAR(64) NOT NULL,
 "create_time" TIMESTAMP(0) NOT NULL,
 "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "OJ_CLASSROOM_GROUP_INFO"
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
CREATE TABLE "OJ_CLASSROOM_SIGN"
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
CREATE TABLE "OJ_COMMENT"
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
CREATE TABLE "OJ_COMMENT_LIKE"
(
 "id" INT IDENTITY(1,1) NOT NULL,
 "resource_id" VARCHAR(64) NOT NULL,
 "user_id" VARCHAR(64) NOT NULL,
 "comment_id" VARCHAR(64) NOT NULL,
 "create_time" TIMESTAMP(0) NOT NULL,
 "update_time" TIMESTAMP(0) NOT NULL
);
CREATE TABLE "OJ_COURSE"
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
CREATE TABLE "OJ_COURSE_CATEGORY"
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
CREATE TABLE "OJ_COURSE_LESSON"
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
CREATE TABLE "OJ_COURSE_STUDY_HISTORY"
(
 "id" INT IDENTITY(5,1) NOT NULL,
 "resource_id" VARCHAR(64) NOT NULL,
 "course_id" VARCHAR(64) NOT NULL,
 "course_name" VARCHAR(256) NOT NULL,
 "user_id" VARCHAR(64) NOT NULL,
 "create_time" TIMESTAMP(0) NULL,
 "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "OJ_LESSON_CONFIG"
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
CREATE TABLE "OJ_LESSON_PAPER_EVALUATE"
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
CREATE TABLE "OJ_LESSON_RECORD"
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
CREATE TABLE "OJ_LOCAL_SCENE"
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
CREATE TABLE "OJ_NOTE"
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
CREATE TABLE "OJ_NOTE_EVALUATE"
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
CREATE TABLE "OJ_PAPER"
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
CREATE TABLE "OJ_PAPER_PRACTICE_THEORY"
(
 "id" INT IDENTITY(1,1) NOT NULL,
 "resource_id" VARCHAR(64) NOT NULL,
 "snapshot" CLOB NOT NULL,
 "origin_id" VARCHAR(64) NOT NULL,
 "paper_id" VARCHAR(64) NOT NULL,
 "submit_answer" CLOB NULL,
 "solved" INT NOT NULL,
 "create_time" TIMESTAMP(0) NULL,
 "update_time" TIMESTAMP(0) NULL
);
CREATE TABLE "OJ_PRACTICE_CATEGORY"
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
CREATE TABLE "OJ_PRACTICE_MISTAKE"
(
 "id" INT IDENTITY(1,1) NOT NULL,
 "resource_id" VARCHAR(64) NOT NULL,
 "practice_id" VARCHAR(64) NOT NULL,
 "practice_type" INT NOT NULL,
 "user_id" VARCHAR(64) NOT NULL,
 "correct" INT NOT NULL,
 "incorrect" INT NOT NULL,
 "create_time" TIMESTAMP(0) NULL,
 "update_time" TIMESTAMP(0) NULL
);
