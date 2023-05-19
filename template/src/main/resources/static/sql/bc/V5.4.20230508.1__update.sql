

-- TODO 修改py内容
INSERT INTO `cr_eval_algorithm` VALUES (17, '230ced924ec485dd37c3945b4d4c13d4', '层次分析法', NULL , 0, 4, 0, 'from typing import List\ndef solution(data: List[int], other_arguments) -> int:\n    \"\"\"\n    聚合算法模板\n    :param data: 包含元素均为0~100 int\n    :param other_arguments: List[int, str, bool], 可配置参数列表, 长度、索引一致\n    :return: 聚合算法结果, 0~100\n    \"\"\"\n', '[]', 0, '3f655d87112f4ae094bbaf70830e20aa', '2023-05-05 16:25:14', '2023-05-05 16:25:45');

-- 新增层次分析法的参数矩阵内容字段
ALTER TABLE cr_eval_indicator  add column `matrix_data` longtext NULL COMMENT '层次分析法矩阵参数';
ALTER TABLE cr_eval_aggregation  add column `matrix_data` longtext NULL COMMENT '层次分析法矩阵参数';
ALTER TABLE cr_eval_index  add column `matrix_data` longtext NULL COMMENT '层次分析法矩阵参数';

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for system_property
-- ----------------------------
DROP TABLE IF EXISTS `system_property`;
CREATE TABLE `system_property`  (
                                    `id` int(11) NOT NULL AUTO_INCREMENT,
                                    `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置名称',
                                    `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置描述',
                                    `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置值',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE INDEX `idx_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_property
-- ----------------------------
INSERT INTO `system_property` VALUES (1, 'export_builtin', '是否可以导出内置资源', 'false');

SET FOREIGN_KEY_CHECKS = 1;


ALTER TABLE oj_note add column `is_submit` int(1) NULL COMMENT '实验报告是否提交';
ALTER TABLE oj_practice_solved add column `is_paper` int(1) NOT NULL COMMENT '是否是自由组卷产生的记录';

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oj_practice_paper
-- ----------------------------
DROP TABLE IF EXISTS `oj_practice_paper`;
CREATE TABLE `oj_practice_paper`  (
                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                      `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `practice_status` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目状态',
                                      `count` int(11) NOT NULL COMMENT '出题数量',
                                      `difficulty` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目难度',
                                      `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目类型',
                                      `direction` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目方向',
                                      `is_finish` int(1) NOT NULL COMMENT '是否完成',
                                      `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组卷人',
                                      `create_time` datetime(0) NULL,
                                      `update_time` datetime(0) NULL,
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组卷信息表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oj_paper_practice_theory
-- ----------------------------
DROP TABLE IF EXISTS `oj_paper_practice_theory`;
CREATE TABLE `oj_paper_practice_theory`  (
                                             `id` int(11) NOT NULL AUTO_INCREMENT,
                                             `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                             `snapshot` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '理论基础信息快照',
                                             `origin_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '原理论题id',
                                             `paper_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联试卷id',
                                             `submit_answer` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '个人组卷练习提交的答案',
                                             `solved` int(1) NOT NULL COMMENT '个人组卷练习解题状态',
                                             `create_time` datetime(0) NULL DEFAULT NULL,
                                             `update_time` datetime(0) NULL DEFAULT NULL,
                                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 298 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组卷习题详情表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oj_practice_mistake
-- ----------------------------
DROP TABLE IF EXISTS `oj_practice_mistake`;
CREATE TABLE `oj_practice_mistake`  (
                                        `id` int(11) NOT NULL AUTO_INCREMENT,
                                        `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                        `practice_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目id',
                                        `practice_type` int(11) NOT NULL COMMENT '题目类型',
                                        `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
                                        `correct` int(11) NOT NULL COMMENT '做对次数',
                                        `incorrect` int(11) NOT NULL COMMENT '做错次数',
                                        `create_time` datetime(0) NULL DEFAULT NULL,
                                        `update_time` datetime(0) NULL DEFAULT NULL,
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '错题集表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

UPDATE `cr_constants_config`
SET `cn_name` = '简单', `en_name` = 'Easy'
WHERE code = 0
  AND type = 10;

UPDATE `cr_constants_config`
SET `cn_name` = '中等', `en_name` = 'Medium'
WHERE code = 1
  AND type = 10;

UPDATE `cr_constants_config`
SET `cn_name` = '困难', `en_name` = 'Difficult'
WHERE code = 2
  AND type = 10;
