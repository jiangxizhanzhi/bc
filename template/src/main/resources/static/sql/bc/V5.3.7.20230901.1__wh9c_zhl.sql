ALTER TABLE cr_eval_record add column `type` int(11) NOT NULL COMMENT '类型';
ALTER TABLE cr_eval_record add column `grade` varchar(512) DEFAULT '[]' COMMENT '评分等级';
