INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 1, 53, '分值', 'Course');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 2, 53, '百分比', 'Theory');

INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 1, 54, '零和计分', 'Course');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 2, 54, '累计计分', 'Course');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 3, 54, '各自累计计分', 'Theory');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 4, 54, '平均计分', 'Theory');

UPDATE `cr_constants_config` SET `cn_name` = '评分算法' WHERE `type` = 5 and `code` = 1;
UPDATE `cr_constants_config` SET `cn_name` = '格式化算法' WHERE `type` = 5 and `code` = 2;
UPDATE `cr_constants_config` SET `cn_name` = '模型匹配算法' WHERE `type` = 5 and `code` = 3;
UPDATE `cr_constants_config` SET `cn_name` = '聚合算法' WHERE `type` = 5 and `code` = 4;
UPDATE `cr_constants_config` SET `cn_name` = '其他' WHERE `type` = 5 and `code` = 5;