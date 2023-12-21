
ALTER TABLE situation_attack_message ADD COLUMN line_process_color varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '过程线路颜色';
ALTER TABLE situation_guide_scheme ADD COLUMN result_content varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结果内容';
ALTER TABLE situation_guide_history ADD COLUMN result_content varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结果内容';

