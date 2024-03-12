
ALTER TABLE cr_eval_system  add column `copy_num` INT(11) NULL  COMMENT '复制次数';


ALTER TABLE cr_eval_index  add column `cal_score` INT(11) NULL  COMMENT '调试指标得分';
ALTER TABLE cr_eval_index  add column `test_score` INT(11) NULL  COMMENT '试验指标得分';
ALTER TABLE cr_eval_index  add column `score` INT(11) NULL  COMMENT '指标得分';
ALTER TABLE cr_eval_index  add column `cal_message` varchar(255) NULL  COMMENT '调试指标计算错误信息';
ALTER TABLE cr_eval_index  add column `test_message` varchar(255) NULL  COMMENT '试验指标计算错误信息';

ALTER TABLE cr_eval_collection  add column `score` INT(11) NULL  COMMENT '指标得分';
ALTER TABLE cr_eval_collection  add column `message` varchar(255) NULL  COMMENT '指标计算错误信息';
ALTER TABLE cr_eval_collection  add column `progress` INT(11) NULL default 1  COMMENT '指标进度';

ALTER TABLE cr_eval_aggregation  add column `score` INT(11) NULL  COMMENT '指标得分';
ALTER TABLE cr_eval_aggregation  add column `message` varchar(255) NULL  COMMENT '指标计算错误信息';
