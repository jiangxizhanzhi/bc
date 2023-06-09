

-- TODO 修改py内容
ALTER TABLE cr_eval_algorithm  add column `mode` int(1) NOT NULL DEFAULT 0 COMMENT '0为其他算法，1为层次分析法';
ALTER TABLE cr_eval_algorithm  add column `builtin` int(1) NOT NULL DEFAULT 0 COMMENT '0为内置算法，1为用户自定义算法';

INSERT INTO `cr_eval_algorithm` VALUES (17, '230ced924ec485dd37c3945b4d4c13d4', '层次分析法', NULL , 0, 4, 0,
'\nimport numpy as np\nimport math\n\n\ndef solution(matrix_data, data, other_arguments):\n    """\n    聚合算法层次分析法模板\n    :param matrix_data: 矩阵数据 [[1, 2, 7, 5, 5],\n                         [1 / 2, 1, 4, 3, 3],\n                         [1 / 7, 1 / 4, 1, 1 / 2, 1 / 3],\n                         [1 / 5, 1 / 3, 2, 1, 1],\n                         [1 / 5, 1 / 3, 3, 1, 1]]\n    :param data: 包含元素均为0~100 int\n    :param other_arguments: List[int, str, bool], 可配置参数列表, 长度、索引一致\n    :return: 聚合算法结果, 0~100\n    """\n    RI = (0, 0, 0.58, 0.9, 1.12, 1.24, 1.32, 1.41, 1.45, 1.49)\n    input_matrix = np.array(matrix_data)\n    n, n1 = input_matrix.shape\n    assert n == n1, \'不是一个方阵\'\n    for i in range(n):\n        for j in range(n):\n            if np.abs(input_matrix[i, j] * input_matrix[j, i] - 1) > 1e-7:\n                raise ValueError(\'不是反互对称矩阵\')\n\n    eigenvalues, eigenvectors = np.linalg.eig(input_matrix)\n\n    max_idx = np.argmax(eigenvalues)\n    max_eigen = eigenvalues[max_idx].real\n    eigen = eigenvectors[:, max_idx].real\n    eigen = eigen / eigen.sum()\n\n    res = 0\n    if n > 9:\n        CR = None\n        return res, CR\n    else:\n        CI = (max_eigen - n) / (n - 1)\n        CR = CI / RI[n]\n    for tmp in eigen:\n        res += data * tmp\n    return math.ceil(res), CR', '[]', 0, '3f655d87112f4ae094bbaf70830e20aa', '2023-05-05 16:25:14', '2023-05-05 16:25:45',1, 0);

INSERT INTO `cr_eval_algorithm` VALUES (18, '92ec4c5891d1c607eb1050593a4798ba', '层次分析法校验一致性', NULL , 0, 4, 0,
'\nimport numpy as np\nimport logging\n\n\ndef solution(matrix_data, data, other_arguments):\n    """\n    聚合算法层次分析法模板\n    :param matrix_data: 矩阵数据 [[1, 2, 7, 5, 5],\n                         [1 / 2, 1, 4, 3, 3],\n                         [1 / 7, 1 / 4, 1, 1 / 2, 1 / 3],\n                         [1 / 5, 1 / 3, 2, 1, 1],\n                         [1 / 5, 1 / 3, 3, 1, 1]]\n    :param data: 包含元素均为0~100 int\n    :param other_arguments: List[int, str, bool], 可配置参数列表, 长度、索引一致\n    :return: 聚合算法结果, 0~100\n    """\n    RI = (0, 0, 0.58, 0.9, 1.12, 1.24, 1.32, 1.41, 1.45, 1.49)\n    logging.info("matrix_data: ", matrix_data)\n    input_matrix = np.array(matrix_data)\n    n, n1 = input_matrix.shape\n    assert n == n1, \'不是一个方阵\'\n    for i in range(n):\n        for j in range(n):\n            if np.abs(input_matrix[i, j] * input_matrix[j, i] - 1) > 1e-7:\n                raise ValueError(\'不是反互对称矩阵\')\n\n    eigenvalues, eigenvectors = np.linalg.eig(input_matrix)\n\n    max_idx = np.argmax(eigenvalues)\n    max_eigen = eigenvalues[max_idx].real\n    if n > 9:\n        return None\n    else:\n        CI = (max_eigen - n) / (n - 1)\n        CR = CI / RI[n]\n    logging.info(\'CR:\', CR)\n    return round(CR, 2)', '[]', 0, '3f655d87112f4ae094bbaf70830e20aa', '2023-05-05 16:25:14', '2023-05-05 16:25:45',1, 0);

-- 新增层次分析法的参数矩阵内容字段
ALTER TABLE cr_eval_indicator  add column `matrix_data` longtext NULL COMMENT '层次分析法矩阵参数';
ALTER TABLE cr_eval_aggregation  add column `matrix_data` longtext NULL COMMENT '层次分析法矩阵参数';
ALTER TABLE cr_eval_index  add column `matrix_data` longtext NULL COMMENT '层次分析法矩阵参数';
ALTER TABLE cr_eval_index_ref  add column `matrix_data` longtext NULL COMMENT '层次分析法矩阵参数';
ALTER TABLE cr_eval_algorithm_ref  add column `mode` int(1) NOT NULL DEFAULT 0 COMMENT '0为其他算法，1为层次分析法';
ALTER TABLE cr_eval_algorithm_ref  add column `matrix_data` longtext NULL COMMENT '层次分析法矩阵参数';

-- ----------------------------
-- Table structure for cr_eval_result
-- ----------------------------
CREATE TABLE `cr_eval_result`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                               `record` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联评估运行实例id',
                               `first_index_res` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '一级指标得分',
                               `second_index_res` longtext NULL COMMENT '二级指标得分',
                               `third_index_res` longtext NULL COMMENT '三级指标得分',
                               `fourth_index_res` longtext NULL COMMENT '四级指标得分',
                               `fifth_index_res` longtext NULL COMMENT '五级指标得分',
                               `exe_cycle` int(11) NOT NULL COMMENT '执行轮次',
                               `update_time` datetime NOT NULL,
                               `create_time` datetime NOT NULL,
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE INDEX `record_exe_cycle_uniq`(`exe_cycle`, `record`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
                                      PRIMARY KEY (`id`) USING BTREE,
                                      INDEX `resource_id`(`resource_id`) USING BTREE
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
                                             PRIMARY KEY (`id`) USING BTREE,
                                             INDEX `resource_id`(`resource_id`) USING BTREE
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
                                        PRIMARY KEY (`id`) USING BTREE,
                                        INDEX `resource_id`(`resource_id`) USING BTREE
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

INSERT INTO `system_property`(`id`, `name`, `desc`, `content`) VALUES (2, 'isShowSituation', '是否显示态势导调', 'false');

CREATE TABLE `cr_notice`
(
    `id`             int(11)     NOT NULL AUTO_INCREMENT,
    `resource_id`    varchar(32) NOT NULL COMMENT 'uuid',
    `create_user_id` varchar(32)   DEFAULT NULL COMMENT '创建人id',
    `context`        varchar(1024) DEFAULT NULL COMMENT '公告内容',
    `role`           tinyint(3)    DEFAULT NULL COMMENT '公告对象',
    `room_id`        varchar(32)   DEFAULT NULL COMMENT '房间id',
    `create_time`    datetime      DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime      DEFAULT NULL COMMENT '更新时间',
    `is_top`         tinyint(3)  NOT NULL COMMENT '是否置顶0否1是',
    `notice_users`   text        NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_resource_id` (`resource_id`) USING BTREE,
    KEY `idx_room_id` (`room_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 114
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- truncate outdated rtks data
-- ----------------------------
truncate rtks_attack;
truncate rtks_attack_category;
truncate rtks_engage_category;
truncate rtks_engage_sub_category;
truncate rtks_engage;

-- ----------------------------
-- for rtks_attack_ics
-- ----------------------------
CREATE TABLE `rtks_attack_ics_category`
(
    `id`          int          NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(32)  NOT NULL COMMENT 'ics类型id',
    `name_zh`     varchar(255) NOT NULL COMMENT '中文名称',
    `name_en`     varchar(255) NOT NULL COMMENT '英文名称',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;


CREATE TABLE `rtks_attack_ics`
(
    `id`          int          NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(32)  NOT NULL COMMENT 'ics id',
    `category_id` varchar(32)  NOT NULL COMMENT 'ics类型id',
    `name_zh`     varchar(255) NOT NULL COMMENT '中文名称',
    `name_en`     varchar(255) NOT NULL COMMENT '英文名称',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `category_id` (`category_id`) USING BTREE,
    INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;

CREATE TABLE `rtks_attack_ics_train_relation`
(
    `id`                   int(11)     NOT NULL AUTO_INCREMENT,
    `resource_id`          varchar(32) NULL COMMENT 'resource_id',
    `attack_ics_id`            varchar(32) NOT NULL COMMENT 'ics id',
    `business_template_id` varchar(32) NOT NULL COMMENT '训练模板id',
    `create_time`          datetime    NULL COMMENT '创建时间',
    `update_time`          datetime    NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `business_template_id` (`business_template_id`) USING BTREE,
    INDEX `attack_ics_id` (`attack_ics_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;

-- ----------------------------
-- alter table
-- ----------------------------
ALTER TABLE `cr_rb_template`
    MODIFY COLUMN `pre_skill` VARCHAR(2048) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练目标',
    MODIFY COLUMN `train_skill` VARCHAR(2048) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练目标';


ALTER TABLE `cr_team_template`
    MODIFY COLUMN `pre_skills` VARCHAR(2048) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '前置技能',
    MODIFY COLUMN `train_skills` VARCHAR(2048) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练目标';

-- ----------------------------
-- insert data
-- ----------------------------
-- ----ATT&CK ICS----
insert into rtks_attack_ics_category
(resource_id, name_zh, name_en)
values ('TA0108', '初始访问', 'Initial Access');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0817', 'TA0108', '偷渡式渗透', 'Drive-by Compromise');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0819', 'TA0108', '利用公众应用程序', 'Exploit Public-Facing Application');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0866', 'TA0108', '利用远程服务', 'Exploitation of Remote Services');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0822', 'TA0108', '外部远程服务', 'External Remote Services');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0883', 'TA0108', '互联网开放设备', 'Internet Accessible Device');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0886', 'TA0108', '远程服务', 'Remote Services');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0847', 'TA0108', '移动介质复制', 'Replication Through Removable Media');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0848', 'TA0108', '恶意主机', 'Rogue Master');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0865', 'TA0108', '鱼叉式网络钓鱼附件', 'Spearphishing Attachment');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0862', 'TA0108', '供应链渗透', 'Supply Chain Compromise');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0864', 'TA0108', '临时网络资产', 'Transient Cyber Asset');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0860', 'TA0108', '无线渗透', 'Wireless Compromise');

insert into rtks_attack_ics_category
(resource_id, name_zh, name_en)
values ('TA0104', '执行', 'Execution');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0858', 'TA0104', '利用特权提升', 'Change Operating Mode');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0807', 'TA0104', '命令行接口', 'Command-Line Interface');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0871', 'TA0104', 'API执行', 'Execution through API');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0823', 'TA0104', '用户图形接口', 'Graphical User Interface');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0874', 'TA0104', '篡改', 'Hooking');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0821', 'TA0104', '修改控制器任务', 'Modify Controller Tasking');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0834', 'TA0104', '原生API接口', 'Native API');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0853', 'TA0104', '脚本', 'Scripting');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0863', 'TA0104', '用户执行', 'User Execution');

insert into rtks_attack_ics_category
(resource_id, name_zh, name_en)
values ('TA0110', '持续危害', 'Persistence');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0891', 'TA0110', '硬编码凭据', 'Hardcoded Credentials');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0889', 'TA0110', '修改程序', 'Modify Program');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0839', 'TA0110', '模块固件', 'Module Firmware');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0873', 'TA0110', '项目文件感染', 'Project File Infection');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0857', 'TA0110', '系统固件', 'System Firmware');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0859', 'TA0110', '有效账户', 'Valid Accounts');

insert into rtks_attack_ics_category
(resource_id, name_zh, name_en)
values ('TA0111', '权限提升', 'Privilege Escalation');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0890', 'TA0111', '利用特权提升', 'Exploitation for Privilege Escalation');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0874', 'TA0111', '篡改', 'Hooking');

insert into rtks_attack_ics_category
(resource_id, name_zh, name_en)
values ('TA0103', '规避', 'Evasion');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0858', 'TA0103', '利用特权提升', 'Change Operating Mode');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0820', 'TA0103', '利用逃避', 'Exploitation for Evasion');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0872', 'TA0103', '主机上的指示移除', 'Indicator Removal on Host');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0849', 'TA0103', '伪装', 'Masquerading');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0851', 'TA0103', '恶意软件', 'Rootkit');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0856', 'TA0103', '欺骗报告消息', 'Spoof Reporting Message');

insert into rtks_attack_ics_category
(resource_id, name_zh, name_en)
values ('TA0102', '发现', 'Discovery');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0840', 'TA0102', '网络连接枚举', 'Network Connection Enumeration');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0842', 'TA0102', '网络嗅探', 'Network Sniffing');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0846', 'TA0102', '远程系统发现', 'Remote System Discovery');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0888', 'TA0102', '远程系统信息发现', 'Remote System Information Discovery');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0887', 'TA0102', '无线嗅探', 'Wireless Sniffing');

insert into rtks_attack_ics_category
(resource_id, name_zh, name_en)
values ('TA0109', '横向移动', 'Lateral Movement');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0812', 'TA0109', '默认凭据', 'Default Credentials');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0866', 'TA0109', '利用远程服务', 'Exploitation of Remote Services');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0891', 'TA0109', '硬编码凭据', 'Hardcoded Credentials');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0867', 'TA0109', '横向移动工具', 'Lateral Tool Transfer');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0843', 'TA0109', '程序下载', 'Program Download');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0886', 'TA0109', '远程服务', 'Remote Services');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0859', 'TA0109', '有效账户', 'Valid Accounts');

insert into rtks_attack_ics_category
(resource_id, name_zh, name_en)
values ('TA0100', '收集', 'Collection');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0830', 'TA0100', '中间对手（AiTM）技术', 'Adversary-in-the-Middle');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0802', 'TA0100', '自动收集', 'Automated Collection');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0811', 'TA0100', '信息存储库中的数据', 'Data from Information Repositories');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0868', 'TA0100', '检测操作模式', 'Detect Operating Mode');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0877', 'TA0100', 'I/O 镜像', 'I/O Image');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0801', 'TA0100', '监视进程状态', 'Monitor Process State');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0861', 'TA0100', '节点和标签的识别', 'Point & Tag Identification');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0845', 'TA0100', '程序上传', 'Program Upload');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0852', 'TA0100', '屏幕捕获', 'Screen Capture');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0887', 'TA0100', '无线嗅探', 'Wireless Sniffing');

insert into rtks_attack_ics_category
(resource_id, name_zh, name_en)
values ('TA0101', '命令与控制', 'Command and Control');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0885', 'TA0101', '常用端口', 'Commonly Used Port');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0884', 'TA0101', '连接代理', 'Connection Proxy');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0869', 'TA0101', '标准应用层协议', 'Standard Application Layer Protocol');

insert into rtks_attack_ics_category
(resource_id, name_zh, name_en)
values ('TA0107', '抑制响应', 'Inhibit Response Function');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0800', 'TA0107', '激活固件更新模式', 'Activate Firmware Update Mode');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0878', 'TA0107', '报警抑制', 'Alarm Suppression');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0803', 'TA0107', '阻止命令消息', 'Block Command Message');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0804', 'TA0107', '阻止报告消息', 'Block Reporting Message');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0805', 'TA0107', '阻止串行 COM', 'Block Serial COM');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0809', 'TA0107', '数据销毁', 'Data Destruction');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0814', 'TA0107', '拒绝服务', 'Denial of Service');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0816', 'TA0107', '设备重启/关机', 'Device Restart/Shutdown');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0835', 'TA0107', '操作 I/O 镜像', 'Manipulate I/O Image');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0838', 'TA0107', '修改报警设置', 'Modify Alarm Settings');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0851', 'TA0107', '恶意软件', 'Rootkit');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0881', 'TA0107', '服务停止', 'Service Stop');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0857', 'TA0107', '系统固件', 'System Firmware');

insert into rtks_attack_ics_category
(resource_id, name_zh, name_en)
values ('TA0106', '损害过程控制', 'Impair Process Control');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0806', 'TA0106', '暴力破解 I/O', 'Brute Force I/O');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0836', 'TA0106', '修改参数', 'Modify Parameter');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0839', 'TA0106', '模块固件', 'Module Firmware');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0856', 'TA0106', '欺骗报告消息', 'Spoof Reporting Message');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0855', 'TA0106', '未经授权的命令消息', 'Unauthorized Command Message');

insert into rtks_attack_ics_category
(resource_id, name_zh, name_en)
values ('TA0105', '影响', 'Impact');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0879', 'TA0105', '财产损失', 'Damage to Property');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0813', 'TA0105', '拒绝控制', 'Denial of Control');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0815', 'TA0105', '拒绝查看', 'Denial of View');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0826', 'TA0105', '可用性损失', 'Loss of Availability');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0827', 'TA0105', '可控性损失', 'Loss of Control');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0828', 'TA0105', '生产力和收益损失', 'Loss of Productivity and Revenue');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0837', 'TA0105', '保护功能损失', 'Loss of Protection');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0880', 'TA0105', '安全功能损失', 'Loss of Safety');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0829', 'TA0105', '视野损失', 'Loss of View');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0831', 'TA0105', '操作控制', 'Manipulation of Control');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0832', 'TA0105', '操作视野', 'Manipulation of View');

insert into rtks_attack_ics
(resource_id, category_id, name_zh, name_en)
values ('T0882', 'TA0105', '运营信息窃取', 'Theft of Operational Information');

-- ----ATT&CK Enterprise----

insert into rtks_attack_category
(resource_id, name_zh, name_en)
values ('TA0043', '侦察', 'Reconnaissance');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1595', 'TA0043', '主动扫描', 'Active Scanning');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1592', 'TA0043', '收集目标主机信息', 'Gather Victim Host Information');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1589', 'TA0043', '收集目标身份信息', 'Gather Victim Identity Information');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1590', 'TA0043', '收集目标网络信息', 'Gather Victim Network Information');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1591', 'TA0043', '收集目标组织信息', 'Gather Victim Org Information');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1598', 'TA0043', '信息钓鱼', 'Phishing for Information');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1597', 'TA0043', '搜索闭源', 'Search Closed Sources');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1596', 'TA0043', '搜索开放数据库', 'Search Open Technical Databases');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1593', 'TA0043', '搜索开放的网站、域名', 'Search Open Websites/Domains');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1594', 'TA0043', '搜索目标拥有的网站', 'Search Victim-Owned Websites');

insert into rtks_attack_category
(resource_id, name_zh, name_en)
values ('TA0042', '资源开发', 'Resource Development');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1583', 'TA0042', '获取基础架构', 'Acquire Infrastructure');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1586', 'TA0042', '入侵账户', 'Compromise Accounts');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1584', 'TA0042', '破坏基础设施', 'Compromise Infrastructure');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1587', 'TA0042', '开发能力', 'Develop Capabilities');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1585', 'TA0042', '建立账户', 'Establish Accounts');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1588', 'TA0042', '获得能力', 'Obtain Capabilities');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1608', 'TA0042', '伪装能力', 'Stage Capabilities');

insert into rtks_attack_category
(resource_id, name_zh, name_en)
values ('TA0001', '初始访问', 'Initial Access');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1189', 'TA0001', '网站挂马攻击', 'Drive-by Compromise');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1190', 'TA0001', '利用互联网上应用程序的缺陷', 'Exploit Public-Facing Application');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1133', 'TA0001', '外部远程服务', 'External Remote Services');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1200', 'TA0001', '硬件接入', 'Hardware Additions');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1598', 'TA0001', '网络钓鱼', 'Phishing');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1091', 'TA0001', '通过可移动介质进行复制', 'Replication Through Removable Media');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1195', 'TA0001', '供应链攻击', 'Supply Chain Compromise');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1199', 'TA0001', '可信关系', 'Trusted Relationship');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1078', 'TA0001', '有效凭证', 'Valid Accounts');

insert into rtks_attack_category
(resource_id, name_zh, name_en)
values ('TA0002', '执行', 'Execution');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1059', 'TA0002', '命令行界面', 'Command and Scripting Interpreter');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1609', 'TA0002', '容器管理命令', 'Container Administration Command');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1610', 'TA0002', '部署容器', 'Deploy Container');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1203', 'TA0002', '利用客户端漏洞获取执行权限', 'Exploitation for Client Execution');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1559', 'TA0002', '跨进程通信', 'Inter-Process Communication');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1106', 'TA0002', '通过API执行', 'Native API');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1053', 'TA0002', '计划任务', 'Scheduled Task/Job');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1648', 'TA0002', '云服务器执行', 'Serverless Execution');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1129', 'TA0002', '通过模块加载执行', 'Shared Modules');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1072', 'TA0002', '利用第三方软件部署工具', 'Software Deployment Tools');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1569', 'TA0002', '系统服务', 'System Services');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1204', 'TA0002', '用户执行', 'User Execution');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1047', 'TA0002', 'Windows管理规范技术', 'Windows Management Instrumentation');

insert into rtks_attack_category
(resource_id, name_zh, name_en)
values ('TA0003', '持久化', 'Persistence');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1098', 'TA0003', '账户修改、监控', 'Account Manipulation');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1197', 'TA0003', 'BITS  作业', 'BITS Jobs');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1547', 'TA0003', '启动或登录自动执行', 'Boot or Logon Autostart Execution');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1037', 'TA0003', '登录脚本', 'Boot or Logon Initialization Scripts');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1176', 'TA0003', '浏览器扩展', 'Browser Extensions');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1554', 'TA0003', '攻击客户的软件二进制文件', 'Compromise Client Software Binary');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1136', 'TA0003', '创建账户', 'Create Account');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1543', 'TA0003', '创建或修改系统进程', 'Create or Modify System Process');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1546', 'TA0003', '事件触发执行', 'Event Triggered Execution');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1133', 'TA0003', '外部远程服务', 'External Remote Services');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1574', 'TA0003', '劫持执行流量', 'Hijack Execution Flow');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1525', 'TA0003', '植入容器镜像', 'Implant Internal Image');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1556', 'TA0003', '修改身份验证流程', 'Modify Authentication Process');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1137', 'TA0003', '启动Office应用程序', 'Office Application Startup');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1542', 'TA0003', '预启动操作系统', 'Pre-OS Boot');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1053', 'TA0003', '计划任务', 'Scheduled Task/Job');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1505', 'TA0003', '服务器软件组件', 'Server Software Component');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1205', 'TA0003', '流量特征', 'Traffic Signaling');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1078', 'TA0003', '有效凭证', 'Valid Accounts');

insert into rtks_attack_category
(resource_id, name_zh, name_en)
values ('TA0004', '特权提升', 'Privilege Escalation');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1548', 'TA0004', '滥用提权控制机制', 'Abuse Elevation Control Mechanism');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1134', 'TA0004', '篡改访问令牌', 'Access Token Manipulation');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1547', 'TA0004', '启动或登录自动执行', 'Boot or Logon Autostart Execution');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1037', 'TA0004', '登录脚本', 'Boot or Logon Initialization Scripts');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1543', 'TA0004', '创建或修改系统进程', 'Create or Modify System Process');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1484', 'TA0004', '修改组策略', 'Domain Policy Modification');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1611', 'TA0004', '主机逃逸', 'Escape to Host');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1546', 'TA0004', '事件触发执行', 'Event Triggered Execution');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1068', 'TA0004', '利用漏洞进行权限升级', 'Exploitation for Privilege Escalation');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1574', 'TA0004', '劫持执行流量', 'Hijack Execution Flow');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1055', 'TA0004', '进程注入', 'Process Injection');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1053', 'TA0004', '计划任务', 'Scheduled Task/Job');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1078', 'TA0004', '有效凭证', 'Valid Accounts');

insert into rtks_attack_category
(resource_id, name_zh, name_en)
values ('TA0005', '逃避防御', 'Defense Evasion');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1548', 'TA0005', '滥用提权控制机制', 'Abuse Elevation Control Mechanism');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1134', 'TA0005', '篡改访问令牌', 'Access Token Manipulation');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1197', 'TA0005', 'BITS  作业', 'BITS Jobs');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1612', 'TA0005', '在主机上构建镜像', 'Build Image on Host');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1622', 'TA0005', '绕过排错程序', 'Debugger Evasion');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1140', 'TA0005', '反混淆/解码文件或信息', 'Deobfuscate/Decode Files or Information');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1610', 'TA0005', '部署容器', 'Deploy Container');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1006', 'TA0005', '直接访问卷', 'Direct Volume Access');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1484', 'TA0005', '修改组策略', 'Domain Policy Modification');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1480', 'TA0005', '执行防护', 'Execution Guardrails');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1211', 'TA0005', '利用漏洞实现防御绕过', 'Exploitation for Defense Evasion');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1222', 'TA0005', '修改文件或目录权限', 'File and Directory Permissions Modification');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1564', 'TA0005', '隐藏工件', 'Hide Artifacts');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1574', 'TA0005', '劫持执行流量', 'Hijack Execution Flow');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1562', 'TA0005', '破坏防御', 'Impair Defenses');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1070', 'TA0005', '删除主机上的指标', 'Indicator Removal');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1202', 'TA0005', '间接命令执行', 'Indirect Command Execution');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1036', 'TA0005', '伪装', 'Masquerading');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1556', 'TA0005', '修改身份验证流程', 'Modify Authentication Process');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1578', 'TA0005', '修改云计算基础架构', 'Modify Cloud Compute Infrastructure');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1112', 'TA0005', '修改注册表', 'Modify Registry');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1601', 'TA0005', '修改系统镜像', 'Modify System Image');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1599', 'TA0005', '网络边界桥接', 'Network Boundary Bridging');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1027', 'TA0005', '混淆的文件或信息', 'Obfuscated Files or Information');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1647', 'TA0005', '属性列表文件修改', 'Plist File Modification');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1542', 'TA0005', '预启动操作系统', 'Pre-OS Boot');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1055', 'TA0005', '进程注入', 'Process Injection');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1620', 'TA0005', '反射代码加载', 'Reflective Code Loading');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1207', 'TA0005', '域控制器', 'Rogue Domain Controller');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1014', 'TA0005', '恶意软件', 'Rootkit');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1553', 'TA0005', '破坏可信控件', 'Subvert Trust Controls');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1218', 'TA0005', '系统二进制文件代理执行', 'System Binary Proxy Execution');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1216', 'TA0005', '系统脚本代理执行', 'System Script Proxy Execution');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1221', 'TA0005', '模板注入', 'Template Injection');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1205', 'TA0005', '流量特征', 'Traffic Signaling');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1127', 'TA0005', '受信任的开发工具', 'Trusted Developer Utilities Proxy Execution');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1535', 'TA0005', '未使用/不支持的云区域', 'Unused/Unsupported Cloud Regions');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1550', 'TA0005', '使用备用身份验证材料', 'Use Alternate Authentication Material');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1078', 'TA0005', '有效凭证', 'Valid Accounts');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1497', 'TA0005', '逃避虚拟化/沙箱检测', 'Virtualization/Sandbox Evasion');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1600', 'TA0005', '弱加密', 'Weaken Encryption');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1220', 'TA0005', 'XSL脚本处理', 'XSL Script Processing');

insert into rtks_attack_category
(resource_id, name_zh, name_en)
values ('TA0006', '凭据访问', 'Credential Access');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1557', 'TA0006', '中间人', 'Adversary-in-the-Middle');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1110', 'TA0006', '暴力破解', 'Brute Force');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1555', 'TA0006', '密钥库中的凭据', 'Credentials from Password Stores');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1212', 'TA0006', '利用漏洞获取凭证访问的权限', 'Exploitation for Credential Access');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1187', 'TA0006', '强制身份验证', 'Forced Authentication');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1606', 'TA0006', '伪造web凭据', 'Forge Web Credentials');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1056', 'TA0006', '捕获用户输入', 'Input Capture');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1556', 'TA0006', '修改身份验证流程', 'Modify Authentication Process');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1111', 'TA0006', '多因素身份认证拦截', 'Multi-Factor Authentication Interception');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1621', 'TA0006', '多因素身份认证请求', 'Multi-Factor Authentication Request Generation');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1040', 'TA0006', '网络嗅探', 'Network Sniffing');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1003', 'TA0006', 'OS凭据转储', 'OS Credential Dumping');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1528', 'TA0006', '窃取应用访问令牌', 'Steal Application Access Token');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1539', 'TA0006', '窃取网络会话Cookie', 'Steal Web Session Cookie');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1649', 'TA0006', '窃取或伪造认证证书', 'Steal or Forge Authentication Certificates');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1558', 'TA0006', '窃取或伪造Kerberos票据', 'Steal or Forge Kerberos Tickets');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1552', 'TA0006', '不安全凭据', 'Unsecured Credentials');

insert into rtks_attack_category
(resource_id, name_zh, name_en)
values ('TA0007', '发现', 'Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1087', 'TA0007', '账户发现', 'Account Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1010', 'TA0007', '应用程序窗口发现', 'Application Window Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1217', 'TA0007', '发现浏览器书签发', 'Browser Bookmark Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1580', 'TA0007', '云基础架构发现', 'Cloud Infrastructure Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1538', 'TA0007', '云服务仪表盘', 'Cloud Service Dashboard');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1526', 'TA0007', '云服务发现', 'Cloud Service Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1619', 'TA0007', '云存储对象发现', 'Cloud Storage Object Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1613', 'TA0007', '容器与资源发现', 'Container and Resource Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1622', 'TA0007', '绕过排错程序', 'Debugger Evasion');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1482', 'TA0007', '域信任发现', 'Domain Trust Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1083', 'TA0007', '文件与目录发现', 'File and Directory Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1615', 'TA0007', '组策略发现', 'Group Policy Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1046', 'TA0007', '服务发现', 'Network Service Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1135', 'TA0007', '网络共享发现', 'Network Share Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1040', 'TA0007', '网络嗅探', 'Network Sniffing');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1201', 'TA0007', '密码策略发现', 'Password Policy Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1120', 'TA0007', '外围设备发现', 'Peripheral Device Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1069', 'TA0007', '组权限发现', 'Permission Groups Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1057', 'TA0007', '进程发现', 'Process Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1012', 'TA0007', '查询注册表', 'Query Registry');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1018', 'TA0007', '远程系统发现', 'Remote System Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1518', 'TA0007', '软件发现', 'Software Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1082', 'TA0007', '系统信息发现', 'System Information Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1614', 'TA0007', '系统位置发现', 'System Location Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1016', 'TA0007', '系统网络配置发现', 'System Network Configuration Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1049', 'TA0007', '系统网络连接发现', 'System Network Connections Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1033', 'TA0007', '系统所有者/用户发现', 'System Owner/User Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1007', 'TA0007', '系统服务发现', 'System Service Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1124', 'TA0007', '系统时间发现', 'System Time Discovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1497', 'TA0007', '逃避虚拟化/沙箱检测', 'Virtualization/Sandbox Evasion');

insert into rtks_attack_category
(resource_id, name_zh, name_en)
values ('TA0008', '横向移动', 'Lateral Movement');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1210', 'TA0008', '利用远程服务', 'Exploitation of Remote Services');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1534', 'TA0008', '内部鱼叉式攻击', 'Internal Spearphishing');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1570', 'TA0008', '工具横向转移', 'Lateral Tool Transfer');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1563', 'TA0008', '远程服务会话劫持', 'Remote Service Session Hijacking');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1021', 'TA0008', '远程服务', 'Remote Services');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1091', 'TA0008', '通过可移动介质进行复制', 'Replication Through Removable Media');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1072', 'TA0008', '利用第三方软件部署工具', 'Software Deployment Tools');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1080', 'TA0008', '污染共享内容', 'Taint Shared Content');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1550', 'TA0008', '使用备用身份验证材料', 'Use Alternate Authentication Material');

insert into rtks_attack_category
(resource_id, name_zh, name_en)
values ('TA0009', '信息收集', 'Collection');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1557', 'TA0009', '中间人', 'Adversary-in-the-Middle');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1560', 'TA0009', '存档已收集的数据', 'Archive Collected Data');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1123', 'TA0009', '音频捕获', 'Audio Capture');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1119', 'TA0009', '自动收集', 'Automated Collection');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1185', 'TA0009', '浏览器中间人', 'Browser Session Hijacking');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1115', 'TA0009', '剪贴板数据', 'Clipboard Data');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1074', 'TA0009', '暂存数据', 'Data Staged');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1530', 'TA0009', '云数据获取', 'Data from Cloud Storage');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1602', 'TA0009', '配置存储库中的数据', 'Data from Configuration Repository');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1213', 'TA0009', '信息存储库中的数据', 'Data from Information Repositories');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1005', 'TA0009', '从本地系统收集敏感数据', 'Data from Local System');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1039', 'TA0009', '网络共享驱动器中的数据', 'Data from Network Shared Drive');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1025', 'TA0009', '来自可移动介质的数据', 'Data from Removable Media');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1114', 'TA0009', '电子邮件收集', 'Email Collection');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1056', 'TA0009', '捕获用户输入', 'Input Capture');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1113', 'TA0009', '屏幕截图', 'Screen Capture');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1125', 'TA0009', '视频捕获', 'Video Capture');

insert into rtks_attack_category
(resource_id, name_zh, name_en)
values ('TA0011', '命令与控制', 'Command and Control');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1071', 'TA0011', '标准应用层协议', 'Application Layer Protocol');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1092', 'TA0011', '通过可移动介质进行通信', 'Communication Through Removable Media');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1132', 'TA0011', '数据编码', 'Data Encoding');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1001', 'TA0011', '数据混淆', 'Data Obfuscation');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1568', 'TA0011', '动态解决方案', 'Dynamic Resolution');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1573', 'TA0011', '加密信道', 'Encrypted Channel');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1008', 'TA0011', '备用通信信道', 'Fallback Channels');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1105', 'TA0011', '远程文件拷贝', 'Ingress Tool Transfer');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1104', 'TA0011', '多阶段通信信道', 'Multi-Stage Channels');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1095', 'TA0011', '标准非应用层协议', 'Non-Application Layer Protocol');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1571', 'TA0011', '非标准端口', 'Non-Standard Port');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1572', 'TA0011', '隧道协议', 'Protocol Tunneling');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1090', 'TA0011', '连接代理', 'Proxy');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1219', 'TA0011', '远程访问工具', 'Remote Access Software');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1205', 'TA0011', '流量特征', 'Traffic Signaling');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1102', 'TA0011', '网络服务', 'Web Service');

insert into rtks_attack_category
(resource_id, name_zh, name_en)
values ('TA0010', '信息盗取', 'Exfiltration');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1020', 'TA0010', '自动化数据渗出', 'Automated Exfiltration');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1030', 'TA0010', '限制数据传输大小', 'Data Transfer Size Limits');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1048', 'TA0010', '备用协议上的数据渗出', 'Exfiltration Over Alternative Protocol');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1041', 'TA0010', '使用命令与控制信道窃取', 'Exfiltration Over C2 Channel');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1011', 'TA0010', '其他网络介质的数据渗漏', 'Exfiltration Over Other Network Medium');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1052', 'TA0010', '物理介质上的数据渗出', 'Exfiltration Over Physical Medium');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1567', 'TA0010', '通过Web服务进行数据渗出', 'Exfiltration Over Web Service');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1029', 'TA0010', '计划传输', 'Scheduled Transfer');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1537', 'TA0010', '将数据传输到云账户中', 'Transfer Data to Cloud Account');

insert into rtks_attack_category
(resource_id, name_zh, name_en)
values ('TA0040', '影响', 'Impact');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1531', 'TA0040', '删除账户访问权限', 'Account Access Removal');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1485', 'TA0040', '销毁数据', 'Data Destruction');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1486', 'TA0040', '数据加密', 'Data Encrypted for Impact');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1565', 'TA0040', '数据修改、监控', 'Data Manipulation');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1491', 'TA0040', '篡改', 'Defacement');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1561', 'TA0040', '删除磁盘数据', 'Disk Wipe');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1499', 'TA0040', '端点拒绝服务', 'Endpoint Denial of Service');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1495', 'TA0040', '固件损坏', 'Firmware Corruption');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1490', 'TA0040', '禁用系统恢复', 'Inhibit System Recovery');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1498', 'TA0040', '网络拒绝服务', 'Network Denial of Service');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1496', 'TA0040', '资源劫持', 'Resource Hijacking');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1489', 'TA0040', '服务停止', 'Service Stop');

insert into rtks_attack
(resource_id, category_id, name_zh, name_en)
values ('T1529', 'TA0040', '系统关闭/重启', 'System Shutdown/Reboot');

-- ------- Engage ---------

insert into rtks_engage_category
(resource_id, name_zh, name_en)
values ('SGO0001', '准备', 'Prepare');

insert into rtks_engage_sub_category
(resource_id, category_id, name_zh, name_en)
values ('SAP0001', 'SGO0001', '规划', 'Plan');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('SAC0004', 'SGO0001', 'SAP0001', '网络威胁情报', 'Cyber Threat Intelligence');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('SAC0012', 'SGO0001', 'SAP0001', '参与环境', 'Engagement Environment');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('SAC0005', 'SGO0001', 'SAP0001', '选通标准', 'Gating Criteria');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('SAC0001', 'SGO0001', 'SAP0001', '作战目标', 'Operational Objective');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('SAC0002', 'SGO0001', 'SAP0001', '角色创建', 'Persona Creation');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('SAC0003', 'SGO0001', 'SAP0001', '故事板', 'Storyboarding');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('SAC0009', 'SGO0001', 'SAP0001', '威胁模型', 'Threat Model');

insert into rtks_engage_category
(resource_id, name_zh, name_en)
values ('EGO0001', '暴露', 'Expose');

insert into rtks_engage_sub_category
(resource_id, category_id, name_zh, name_en)
values ('EAP0001', 'EGO0001', '收集', 'Collect');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0001', 'EGO0001', 'EAP0001', 'API监控', 'API Monitoring');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0002', 'EGO0001', 'EAP0001', '网络监控', 'Network Monitoring');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0014', 'EGO0001', 'EAP0001', '软件操作', 'Software Manipulation');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0003', 'EGO0001', 'EAP0001', '系统活动监控', 'System Activity Monitoring');

insert into rtks_engage_sub_category
(resource_id, category_id, name_zh, name_en)
values ('EAP0002', 'EGO0001', '发现', 'Detect');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0023', 'EGO0001', 'EAP0002', '引入漏洞', 'Introduced Vulnerabilities');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0005', 'EGO0001', 'EAP0002', '诱饵', 'Lures');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0013', 'EGO0001', 'EAP0002', '引爆恶意软件', 'Malware Detonation');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0004', 'EGO0001', 'EAP0002', '网络分析', 'Network Analysis');

insert into rtks_engage_category
(resource_id, name_zh, name_en)
values ('EGO0002', '影响', 'Affect');

insert into rtks_engage_sub_category
(resource_id, category_id, name_zh, name_en)
values ('EAP0003', 'EGO0002', '防御', 'Prevent');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0019', 'EGO0002', 'EAP0003', '基线', 'Baseline');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0017', 'EGO0002', 'EAP0003', '硬件操作', 'Hardware Manipulation');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0020', 'EGO0002', 'EAP0003', '隔离', 'Isolation');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0016', 'EGO0002', 'EAP0003', '网络操纵', 'Network Manipulation');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0018', 'EGO0002', 'EAP0003', '安全控制', 'Security Controls');

insert into rtks_engage_sub_category
(resource_id, category_id, name_zh, name_en)
values ('EAP0004', 'EGO0002', '转移', 'Direct');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0021', 'EGO0002', 'EAP0004', '攻击向量转移', 'Attack Vector Migration');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0009', 'EGO0002', 'EAP0004', '邮件管理', 'Email Manipulation');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0023', 'EGO0002', 'EAP0004', '引入漏洞', 'Introduced Vulnerabilities');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0005', 'EGO0002', 'EAP0004', '诱饵', 'Lures');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0013', 'EGO0002', 'EAP0004', '引爆恶意软件', 'Malware Detonation');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0016', 'EGO0002', 'EAP0004', '网络操纵', 'Network Manipulation');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0010', 'EGO0002', 'EAP0004', '外设管理', 'Peripheral Management');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0018', 'EGO0002', 'EAP0004', '安全控制', 'Security Controls');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0014', 'EGO0002', 'EAP0004', '软件操作', 'Software Manipulation');

insert into rtks_engage_sub_category
(resource_id, category_id, name_zh, name_en)
values ('EAP0005', 'EGO0002', '破坏', 'Disrupt');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0020', 'EGO0002', 'EAP0005', '隔离', 'Isolation');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0005', 'EGO0002', 'EAP0005', '诱饵', 'Lures');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0016', 'EGO0002', 'EAP0005', '网络操纵', 'Network Manipulation');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0014', 'EGO0002', 'EAP0005', '软件操作', 'Software Manipulation');

insert into rtks_engage_category
(resource_id, name_zh, name_en)
values ('EGO0003', '引出', 'Elicit');

insert into rtks_engage_sub_category
(resource_id, category_id, name_zh, name_en)
values ('EAP0006', 'EGO0003', '保证', 'Reassure');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0006', 'EGO0003', 'EAP0006', '应用多样性', 'Application Diversity');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0022', 'EGO0003', 'EAP0006', '工件多样性', 'Artifact Diversity');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0008', 'EGO0003', 'EAP0006', '磨合', 'Burn-In');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0009', 'EGO0003', 'EAP0006', '邮件管理', 'Email Manipulation');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0015', 'EGO0003', 'EAP0006', '信息处理', 'Information Manipulation');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0007', 'EGO0003', 'EAP0006', '网络多样性', 'Network Diversity');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0010', 'EGO0003', 'EAP0006', '外设管理', 'Peripheral Management');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0011', 'EGO0003', 'EAP0006', '杂项信息', 'Pocket Litter');

insert into rtks_engage_sub_category
(resource_id, category_id, name_zh, name_en)
values ('EAP0007', 'EGO0003', '激励', 'Motivate');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0006', 'EGO0003', 'EAP0007', '应用多样性', 'Application Diversity');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0022', 'EGO0003', 'EAP0007', '工件多样性', 'Artifact Diversity');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0015', 'EGO0003', 'EAP0007', '信息处理', 'Information Manipulation');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0023', 'EGO0003', 'EAP0007', '引入漏洞', 'Introduced Vulnerabilities');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0013', 'EGO0003', 'EAP0007', '引爆恶意软件', 'Malware Detonation');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0007', 'EGO0003', 'EAP0007', '网络多样性', 'Network Diversity');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('EAC0012', 'EGO0003', 'EAP0007', '人物角色', 'Persona');

insert into rtks_engage_category
(resource_id, name_zh, name_en)
values ('SGO0002', '理解', 'Understand');

insert into rtks_engage_sub_category
(resource_id, category_id, name_zh, name_en)
values ('SAP0002', 'SGO0002', '分析', 'Analyze');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('SAC0006', 'SGO0002', 'SAP0002', '行动后审查', 'After-Action Review');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('SAC0004', 'SGO0002', 'SAP0002', '网络威胁情报', 'Cyber Threat Intelligence');

insert into rtks_engage
(resource_id, category_id, sub_category_id, name_zh, name_en)
values ('SAC0009', 'SGO0002', 'SAP0002', '威胁模型', 'Threat Model');

-- ----------------------------
-- 标记
-- ----------------------------
CREATE TABLE `cr_mark`
(
    `id`          int          NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(32)  NOT NULL COMMENT 'id',
    `name`        varchar(255) NOT NULL COMMENT '名称',
    `description` varchar(255) NOT NULL COMMENT '描述',
    `evidence`    text         NULL COMMENT '证据',
    `user_id`     varchar(32)  NOT NULL COMMENT '用户id',
    `room_id`     varchar(32)  NOT NULL COMMENT '训练id',
    `type`        tinyint(4)   NOT NULL COMMENT '训练类型',
    `create_time` datetime     NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime     NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE,
    INDEX `room_id` (`room_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;

-- ----------------------------
-- rtks岗位导入
-- ----------------------------
CREATE TABLE `init_rtks_role_skill_relation`
(
    `id`          int(11)     NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `role_id`     varchar(32) NOT NULL COMMENT '角色id',
    `skill_id`    varchar(32) NOT NULL COMMENT '技能id',
    `weight`      tinyint(4)  NOT NULL COMMENT '权重',
    `create_time` datetime    NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime    NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `role` (`role_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '预置rtks角色技能点关联表';

INSERT INTO `cr_constants_config` VALUES (138, NULL, 1, 39, '已解出', 'Solved');
INSERT INTO `cr_constants_config` VALUES (139, NULL, 2, 39, '未开始', 'Not started');
INSERT INTO `cr_constants_config` VALUES (140, NULL, 0, 39, '尝试过', 'Attempted');
INSERT INTO `cr_constants_config` VALUES (141, NULL, 1, 40, '课程', 'Course');
INSERT INTO `cr_constants_config` VALUES (142, NULL, 2, 40, '课时', 'Class');
INSERT INTO `cr_constants_config` VALUES (143, NULL, 3, 40, '实操题', 'Practice');
INSERT INTO `cr_constants_config` VALUES (144, NULL, 4, 40, '课后练习', 'AfterClass');
INSERT INTO `cr_constants_config` VALUES (145, NULL, 5, 40, '理论题', 'Theory');

-- ----------------------------
-- 红点标记
-- ----------------------------
CREATE TABLE `cr_badge`
(
    `id`          int(11)     NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `type`        tinyint(4)  NOT NULL COMMENT '类型',
    `business_id` varchar(65) NOT NULL COMMENT '业务id',
    `user_id`     varchar(32) NOT NULL COMMENT '用户id',
    `extra`       text        NULL COMMENT '记录业务相关信息',
    `create_time` datetime    NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime    NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `business_type_user` (`business_id`, `type`, `user_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '业务红点提示';

-- ----------------------------
-- 复盘场景id
-- ----------------------------
ALTER TABLE `cr_rb_room`
    ADD COLUMN `replay_scene_id` varchar(32) NULL COMMENT '复盘场景id' AFTER `logo`;

ALTER TABLE `cr_team_room`
    ADD COLUMN `replay_scene_id` varchar(32) NULL COMMENT '复盘场景id' AFTER `logo`;

-- ----------------------------
-- 红蓝房间常用资产表
-- ----------------------------
CREATE TABLE `cr_rb_common_device`  (
                                        `id` int NOT NULL AUTO_INCREMENT,
                                        `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `room_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `device_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `collect_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `create_time` datetime NULL DEFAULT NULL,
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE INDEX `resource_id_uni`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '红蓝常用资产表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- 团队房间常用资产表
-- ----------------------------
CREATE TABLE `cr_team_common_device`  (
                                          `id` int NOT NULL AUTO_INCREMENT,
                                          `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                          `room_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                          `device_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                          `collect_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                          `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                          `create_time` datetime NULL DEFAULT NULL,
                                          PRIMARY KEY (`id`) USING BTREE,
                                          UNIQUE INDEX `resource_id_uni`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- 团队房间资产占用表
-- ----------------------------
CREATE TABLE `cr_team_device`  (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `room_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `device_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `create_time` datetime NULL DEFAULT NULL,
                                   `update_time` datetime NULL DEFAULT NULL,
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `resource_id_uni`(`resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- 我的笔记添加常量
-- ----------------------------
INSERT INTO `cr_constants_config` (`resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 0, 39, '尝试过', 'Attempted');

-- ----------------------------
-- oj_practice_paper direction 题目方向字段加长
-- ----------------------------
ALTER TABLE `oj_practice_paper`
MODIFY COLUMN `direction` VARCHAR(3000) NOT NULL;
