INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 45, '未开始', 'Not Started');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 45, '进行中', 'Ongoing');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 45, '已结束', 'Ended');

ALTER TABLE `oj_classroom`
MODIFY COLUMN `start_time` datetime NULL COMMENT '课堂开始时间',
MODIFY COLUMN `end_time` datetime NULL COMMENT '课堂结束时间';