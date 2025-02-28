ALTER TABLE `oj_course`
    ADD COLUMN `rank` tinyint DEFAULT 1;

INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 1, 51, '入门', 'Course');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 2, 51, '常规', 'Class');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 3, 51, '进阶', 'Theory');

INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 0, 52, '静态', 'Course');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 1, 52, '动态', 'Course');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 2, 52, '随机', 'Class');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 3, 52, '非FLAG', 'Theory');

UPDATE `cr_constants_config` SET `cn_name` = '基础算法' WHERE `type` = 5 and `code` = 1;
UPDATE `cr_constants_config` SET `cn_name` = '图算法' WHERE `type` = 5 and `code` = 2;
UPDATE `cr_constants_config` SET `cn_name` = '数据挖掘算法' WHERE `type` = 5 and `code` = 3;
UPDATE `cr_constants_config` SET `cn_name` = '机器学习和深度学习算法' WHERE `type` = 5 and `code` = 4;
UPDATE `cr_constants_config` SET `cn_name` = '其他类型算法' WHERE `type` = 5 and `code` = 5;