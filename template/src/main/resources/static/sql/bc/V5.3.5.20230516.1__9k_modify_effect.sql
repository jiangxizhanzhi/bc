-- 动效新增简介字段
ALTER TABLE situation_dynamic_effects
    ADD description VARCHAR(2048) CHARACTER
        SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介' AFTER NAME;

-- 动效新增类型字段
ALTER TABLE situation_dynamic_effects
    ADD type TINYINT ( 1 ) NOT NULL DEFAULT 2 COMMENT '动效类型 1-预置 2-用户上传' AFTER description;

-- 动效新增动作类型字段，用于管理预置动效
ALTER TABLE situation_dynamic_effects
    ADD action_type TINYINT ( 2 ) NULL DEFAULT NULL COMMENT '预置动作类型' AFTER type;

-- 修改动效文件字段
ALTER TABLE situation_dynamic_effects CHANGE source_node_dynamic_file dynamic_file VARCHAR ( 512 ) CHARACTER
    SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '动效文件';
ALTER TABLE situation_dynamic_effects CHANGE source_node_dynamic_dir dynamic_dir VARCHAR ( 512 ) CHARACTER
    SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '动效文件解压目录';

-- 动效映射新增类型字段
ALTER TABLE situation_dynamic_effects_mapping CHANGE category type TINYINT ( 1 ) NOT NULL DEFAULT 2 COMMENT '映射类型 1-预置 2-用户上传';

-- 删除事件类型的动效属性字段
ALTER TABLE situation_event_type DROP COLUMN source_action_type;
ALTER TABLE situation_event_type DROP COLUMN target_action_type;