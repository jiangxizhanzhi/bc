-- cr_eval_index

ALTER TABLE `cr_eval_index`
    ADD COLUMN `cal_score` int(11) NULL DEFAULT NULL COMMENT '调试指标得分' AFTER `matrix_data`,
ADD COLUMN `test_score` int(11) NULL DEFAULT NULL COMMENT '试验指标得分' AFTER `cal_score`,
ADD COLUMN `score` int(11) NULL DEFAULT NULL COMMENT '指标得分' AFTER `test_score`,
ADD COLUMN `cal_message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调试指标计算错误信息' AFTER `score`,
ADD COLUMN `test_message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '试验指标计算错误信息' AFTER `cal_message`;

-- cr_eval_aggregation

ALTER TABLE `cr_eval_aggregation`
    ADD COLUMN `score` int(11) NULL DEFAULT NULL COMMENT '指标得分' AFTER `matrix_data`,
ADD COLUMN `message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指标计算错误信息' AFTER `score`;

-- cr_eval_collection

ALTER TABLE `cr_eval_collection`
    ADD COLUMN `score` int(11) NULL DEFAULT NULL COMMENT '指标得分' AFTER `update_time`,
ADD COLUMN `message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指标计算错误信息' AFTER `score`,
ADD COLUMN `progress` int(11) NULL DEFAULT 1 COMMENT '指标进度' AFTER `message`;