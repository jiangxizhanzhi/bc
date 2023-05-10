

-- TODO 修改py内容
INSERT INTO `cr_eval_algorithm` VALUES (17, '230ced924ec485dd37c3945b4d4c13d4', '层次分析法', NULL , 0, 4, 0, 'from typing import List\ndef solution(data: List[int], other_arguments) -> int:\n    \"\"\"\n    聚合算法模板\n    :param data: 包含元素均为0~100 int\n    :param other_arguments: List[int, str, bool], 可配置参数列表, 长度、索引一致\n    :return: 聚合算法结果, 0~100\n    \"\"\"\n', '[]', 0, '3f655d87112f4ae094bbaf70830e20aa', '2023-05-05 16:25:14', '2023-05-05 16:25:45');

-- 新增层次分析法的参数矩阵内容字段
ALTER TABLE cr_eval_indicator  add column `matrix_data` longtext NULL COMMENT '层次分析法矩阵参数';
ALTER TABLE cr_eval_aggregation  add column `matrix_data` longtext NULL COMMENT '层次分析法矩阵参数';
ALTER TABLE cr_eval_index  add column `matrix_data` longtext NULL COMMENT '层次分析法矩阵参数';
