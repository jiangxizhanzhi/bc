ALTER TABLE `oj_lesson_record`
ADD COLUMN `learned_time` datetime(0) NULL COMMENT '开始学习时间' AFTER `learned_type`,
ADD COLUMN `learned_duration` int(10) NULL COMMENT '时间统计(单位秒)' AFTER `learned_time`;