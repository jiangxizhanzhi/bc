-- oj_soc_category soc分类信息表，参考使用，暂不进行查询
DROP TABLE IF EXISTS `oj_soc_category` CASCADE;
CREATE TABLE `oj_soc_category`  (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `first_category` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '一级分类',
    `second_category` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '二级分类',
    `third_category` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '三级分类',
    `create_time` datetime NULL DEFAULT NOW(),
    `update_time` datetime NULL DEFAULT NOW(),
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
INSERT INTO `oj_soc_category` VALUES (1, 'course0010001', '课程分类', '信息安全基础', '操作系统基础', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (2, 'course0010002', '课程分类', '信息安全基础', '安全术语', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (3, 'course0010003', '课程分类', '信息安全基础', '安全意识', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (4, 'course0010004', '课程分类', '信息安全基础', '保密意识', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (5, 'course0010005', '课程分类', '信息安全基础', '等级保护', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (6, 'course0010006', '课程分类', '信息安全基础', '网络安全法', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (7, 'course0010007', '课程分类', '信息安全基础', '密码学', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (8, 'course0010008', '课程分类', '信息安全基础', '网络安全', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (9, 'course0010009', '课程分类', '信息安全基础', '网络安全协议', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (10, 'course0010010', '课程分类', '信息安全基础', '系统安全', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (11, 'course0010011', '课程分类', '信息安全基础', '安全行业信息', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (12, 'course0020001', '课程分类', '应用安全', 'Web安全', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (13, 'course0020002', '课程分类', '应用安全', '渗透测试', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (14, 'course0020003', '课程分类', '应用安全', '代码审计', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (15, 'course0020004', '课程分类', '应用安全', '移动安全', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (16, 'course0020005', '课程分类', '应用安全', '二进制安全', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (17, 'course0020006', '课程分类', '应用安全', '物联网安全', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (18, 'course0020007', '课程分类', '应用安全', '工控安全', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (19, 'course0020008', '课程分类', '应用安全', '云计算安全', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (20, 'course0030001', '课程分类', '数据安全', '数据存储安全', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (21, 'course0030002', '课程分类', '数据安全', '数字水印', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (22, 'course0040001', '课程分类', '安全运维', '安全加固', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (23, 'course0040002', '课程分类', '安全运维', '安全防护技术', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (24, 'course0040003', '课程分类', '安全运维', '应急响应', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (25, 'course0050001', '课程分类', '安全编程', '编程技术', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (26, 'course0050002', '课程分类', '安全编程', '数据库技术', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (27, 'course0050003', '课程分类', '安全编程', '安全开发', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (28, 'course0060001', '课程分类', '网络空间安全基础', '信息技术基础', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (29, 'course0060002', '课程分类', '网络空间安全基础', '信息安全基础', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (30, 'course0070001', '课程分类', '网络空间安全专业', '内容安全知识', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (31, 'course0080001', '课程分类', '网络空间安全前沿技术', '嵌入式系统安全', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (32, 'course0080002', '课程分类', '网络空间安全前沿技术', '工业控制系统安全', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (33, 'course0080003', '课程分类', '网络空间安全前沿技术', '可信计算', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (34, 'course0080004', '课程分类', '网络空间安全前沿技术', '移动通信安全', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (35, 'course0080005', '课程分类', '网络空间安全前沿技术', '区块链', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (36, 'course0080006', '课程分类', '网络空间安全前沿技术', '云原生安全', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (37, 'course0080007', '课程分类', '网络空间安全前沿技术', '数据安全', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (38, 'course0090001', '课程分类', '网络空间安全实践', '竞赛专题', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (39, 'course0090002', '课程分类', '网络空间安全实践', '安全运维', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (40, 'course0090003', '课程分类', '网络空间安全实践', '安全测试', '2025-04-27 14:38:09', '2025-04-27 14:38:09');
INSERT INTO `oj_soc_category` VALUES (41, 'course0090004', '课程分类', '网络空间安全实践', '红蓝对抗', '2025-04-27 14:38:09', '2025-04-27 14:38:09');

-- oj_course_soc_category 课程和soc分类关系表，用于实际分类查询
DROP TABLE IF EXISTS `oj_course_soc_category` CASCADE;
CREATE TABLE `oj_course_soc_category`  (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
   `course_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程资源id',
   `course_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程名称',
   `soc_category_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'soc分类id',
   `soc_third_category` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'soc三级分类标签',
   `soc_second_category` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'soc二级分类标签',
   `create_time` datetime NULL DEFAULT NOW(),
   `update_time` datetime NULL DEFAULT NOW(),
   PRIMARY KEY (`id`) USING BTREE,
   UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
   INDEX `idx_course_id`(`course_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


INSERT INTO `oj_course_soc_category` VALUES (1, 'soccategoryISB0100INFOSECOV', 'ISB0100INFOSECOV', '信息安全概述', 'course0060002', '信息安全基础', '网络空间安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (2, 'soccategoryISB0101AQYSVD5', 'ISB0101AQYSVD5', '涉密单位保密意识', 'course0010004', '保密意识', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (3, 'soccategoryISB0101AQYSVD6', 'ISB0101AQYSVD6', '信息安全案例分析', 'course0010011', '安全行业信息', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (4, 'soccategoryISB0200INFOMATH', 'ISB0200INFOMATH', '信息安全数学基础', 'course0060002', '信息安全基础', '网络空间安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (5, 'soccategoryISB0300INFOSECLGL', 'ISB0300INFOSECLGL', '信息安全法律基础', 'course0010006', '网络安全法', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (6, 'soccategoryISB0101AQYSVD3', 'ISB0101AQYSVD3', '安全管理意识', 'course0010003', '安全意识', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (7, 'soccategoryISB0500LAWMGT', 'ISB0500LAWMGT', '信息安全管理基础', 'course0060002', '信息安全基础', '网络空间安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (8, 'soccategoryISB0101AQYSVD4', 'ISB0101AQYSVD4', '个人信息安全意识', 'course0010003', '安全意识', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (9, 'soccategoryISB0101AQYSVD2', 'ISB0101AQYSVD2', '信息安全技术知识科普', 'course0010002', '安全术语', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (10, 'soccategoryISB0101AQYSVD1', 'ISB0101AQYSVD1', '安全意识基础', 'course0010003', '安全意识', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (11, 'soccategoryISTB0102EDA', 'ISTB0102EDA', 'EDA技术基础', 'course0060001', '信息技术基础', '网络空间安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (12, 'soccategorySS1205ICS', 'SS1205ICS', '工控系统网络安全', 'course0020007', '工控安全', '应用安全', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (13, 'soccategoryDEFOPS07SURVE', 'DEFOPS07SURVE', '安全监控', 'course0060002', '信息安全基础', '网络空间安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (14, 'soccategorySS0801TCB', 'SS0801TCB', '可信计算', 'course0080003', '可信计算', '网络空间安全前沿技术', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (15, 'soccategorySS0900AINFOSEC', 'SS0900AINFOSEC', '应用信息系统安全', 'course0010010', '系统安全', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (16, 'soccategorySS0400AC', 'SS0400AC', '访问控制', 'course0010010', '系统安全', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (17, 'soccategorySS0100SRI', 'SS0100SRI', '系统可靠性技术', 'course0040002', '安全防护技术', '安全运维', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (18, 'soccategorySS0100SSC', 'SS0100SSC', '系统安全概念', 'course0010010', '系统安全', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (19, 'soccategorySS0100SES', 'SS0100SES', '系统设备安全', 'course0010010', '系统安全', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (20, 'soccategorySS0502WIN', 'SS0502WIN', 'Windows系统安全', 'course0010010', '系统安全', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (21, 'soccategorySS1001EDFT', 'SS1001EDFT', '电子数据取证技术', 'course0010010', '系统安全', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (22, 'soccategorySS0600DBSEC', 'SS0600DBSEC', '数据库安全', 'course0050002', '数据库技术', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (23, 'soccategorySS1201ICS', 'SS1201ICS', '工业控制系统安全', 'course0020007', '工控安全', '应用安全', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (24, 'soccategorySS11001ANDROID', 'SS11001ANDROID', 'Android安全', 'course0020004', '移动安全', '应用安全', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (25, 'soccategorySS0503LINUX', 'SS0503LINUX', 'Linux系统安全', 'course0040002', '安全防护技术', '安全运维', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (26, 'soccategorySS0501OSSEC', 'SS0501OSSEC', '操作系统安全', 'course0040002', '安全防护技术', '安全运维', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (27, 'soccategorySS0700SSEC', 'SS0700SSEC', '软件安全', 'course0050003', '安全开发', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (28, 'soccategoryDEFOPS04THIN', 'DEFOPS04THIN', '威胁情报', 'course0060002', '信息安全基础', '网络空间安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (29, 'soccategorySS1207ICS', 'SS1207ICS', '工控安全技术', 'course0020007', '工控安全', '应用安全', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (30, 'soccategorySS1208ICS', 'SS1208ICS', '工控安全攻防', 'course0020007', '工控安全', '应用安全', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (31, 'soccategorySS1206ICS', 'SS1206ICS', '工控网络安全意识', 'course0020007', '工控安全', '应用安全', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (32, 'soccategorySS1203ICS', 'SS1203ICS', '工业网络基础', 'course0020007', '工控安全', '应用安全', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (33, 'soccategorySS1209ICS', 'SS1209ICS', '工控政策标准解读', 'course0010011', '安全行业信息', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (34, 'soccategorySS1204ICS', 'SS1204ICS', '工业安全管理体系', 'course0010011', '安全行业信息', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (35, 'soccategoryDEFEA02LOGAUDIT', 'DEFEA02LOGAUDIT', '日志审计', 'course0010010', '系统安全', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (36, 'soccategorySS1202ICS', 'SS1202ICS', '工业控制基础', 'course0020007', '工控安全', '应用安全', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (37, 'soccategoryDEFOPS06BAK', 'DEFOPS06BAK', '容灾与备份', 'course0040001', '安全加固', '安全运维', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (38, 'soccategoryCS0500CSCON', 'CS0500CSCON', '内容安全概念', 'course0070001', '内容安全知识', '网络空间安全专业', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (39, 'soccategoryCS0306BIGDATA', 'CS0306BIGDATA', 'HCIE-BigData', 'course0070001', '内容安全知识', '网络空间安全专业', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (40, 'soccategoryCS0300NRFXYGL', 'CS0300NRFXYGL', '内容分析与识别', 'course0070001', '内容安全知识', '网络空间安全专业', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (41, 'soccategoryCS0600PRIPROT', 'CS0600PRIPROT', '隐私保护', 'course0070001', '内容安全知识', '网络空间安全专业', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (42, 'soccategoryCS0303PYTHONDV', 'CS0303PYTHONDV', 'Python数据可视化', 'course0070001', '内容安全知识', '网络空间安全专业', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (43, 'soccategoryCS0201MLADM', 'CS0201MLADM', '基于python语言的机器学习及数据挖掘课程', 'course0070001', '内容安全知识', '网络空间安全专业', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (44, 'soccategoryCS0200NDC', 'CS0200NDC', '网络数据获取', 'course0070001', '内容安全知识', '网络空间安全专业', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (45, 'soccategoryCS0501MIS', 'CS0501MIS', '多媒体信息隐藏', 'course0070001', '内容安全知识', '网络空间安全专业', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (46, 'soccategoryCS0302DAALGO', 'CS0302DAALGO', '数据分析算法', 'course0070001', '内容安全知识', '网络空间安全专业', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (47, 'soccategoryCS0301NLA', 'CS0301NLA', '自然语言分析', 'course0070001', '内容安全知识', '网络空间安全专业', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (48, 'soccategoryCS0304SJFXCL', 'CS0304SJFXCL', '基于python语言的数据处理分析', 'course0070001', '内容安全知识', '网络空间安全专业', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (49, 'soccategoryCS0307SOCCAP', 'CS0307SOCCAP', '基于大数据的SOC平台的搭建与实践', 'course0070001', '内容安全知识', '网络空间安全专业', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (50, 'soccategoryCS0305HADOOP', 'CS0305HADOOP', 'Hadoop大数据架构', 'course0070001', '内容安全知识', '网络空间安全专业', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (51, 'soccategoryCSSJAQGL001', 'CSSJAQGL001', '数据安全管理', 'course0070001', '内容安全知识', '网络空间安全专业', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (52, 'soccategoryISC0102XMAN17', 'ISC0102XMAN17', 'XMAN-2017-专题', 'course0090001', '竞赛专题', '网络空间安全实践', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (53, 'soccategoryISC0102XMAN16', 'ISC0102XMAN16', 'XMAN-2016-专题', 'course0090001', '竞赛专题', '网络空间安全实践', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (54, 'soccategoryISC0101TOOLS', 'ISC0101TOOLS', 'CTF基础知识与常用工具', 'course0090001', '竞赛专题', '网络空间安全实践', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (55, 'soccategoryISC0100CRYPTO', 'ISC0100CRYPTO', 'CTF-CRYPTO', 'course0090001', '竞赛专题', '网络空间安全实践', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (56, 'soccategoryISC0100MISC', 'ISC0100MISC', 'CTF-MISC', 'course0090001', '竞赛专题', '网络空间安全实践', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (57, 'soccategoryISC0100RE', 'ISC0100RE', 'CTF-REVERSE', 'course0090001', '竞赛专题', '网络空间安全实践', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (58, 'soccategoryISC0100PWN', 'ISC0100PWN', 'CTF-PWN', 'course0090001', '竞赛专题', '网络空间安全实践', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (59, 'soccategoryISC0100WEB', 'ISC0100WEB', 'CTF-WEB', 'course0090001', '竞赛专题', '网络空间安全实践', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (60, 'soccategoryISTB0303ECC', 'ISTB0303ECC', '纠错编码', 'course0050003', '安全开发', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (61, 'soccategoryISTB0103VFBOOTSTRAP', 'ISTB0103VFBOOTSTRAP', 'BootStrap基础入门', 'course0050001', '编程技术', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (62, 'soccategoryISTB0103VFJAVASCRIPT', 'ISTB0103VFJAVASCRIPT', 'JS基础入门', 'course0050001', '编程技术', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (63, 'soccategoryISTB0103VFHTML', 'ISTB0103VFHTML', 'HTML基础入门', 'course0050001', '编程技术', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (64, 'soccategoryISTB0501ES', 'ISTB0501ES', '嵌入式系统', 'course0060001', '信息技术基础', '网络空间安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (65, 'soccategoryISTB0401POCC', 'ISTB0401POCC', '计算机组成原理', 'course0060001', '信息技术基础', '网络空间安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (66, 'soccategoryISTB0404DBINTRO', 'ISTB0404DBINTRO', '数据库概论', 'course0050002', '数据库技术', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (67, 'soccategoryISTB0103VFMYSQL', 'ISTB0103VFMYSQL', 'Mysql基础入门', 'course0050002', '数据库技术', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (68, 'soccategoryISTB0103VFJAVASE', 'ISTB0103VFJAVASE', 'JavaSE基础入门', 'course0050001', '编程技术', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (69, 'soccategoryISTB0103VFJSP', 'ISTB0103VFJSP', 'JSP基础入门', 'course0050001', '编程技术', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (70, 'soccategoryISTB0103VFSERVLET', 'ISTB0103VFSERVLET', 'Servlet基础入门', 'course0050001', '编程技术', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (71, 'soccategoryISTB0103VFJQUERY', 'ISTB0103VFJQUERY', 'Jquery基础入门', 'course0050001', '编程技术', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (72, 'soccategoryISTB0103VFCSS', 'ISTB0103VFCSS', 'CSS基础入门', 'course0050001', '编程技术', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (73, 'soccategoryISTB0103VFORACLE', 'ISTB0103VFORACLE', 'Oracle基础入门', 'course0050002', '数据库技术', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (74, 'soccategoryISTB0103VFREDIS', 'ISTB0103VFREDIS', 'Redis基础入门', 'course0050001', '编程技术', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (75, 'soccategoryISTB0101ELECT', 'ISTB0101ELECT', '电子电路基础', 'course0060001', '信息技术基础', '网络空间安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (76, 'soccategoryISTB0103JAVASCRIPT', 'ISTB0103JAVASCRIPT', 'JavaScript程序设计', 'course0050001', '编程技术', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (77, 'soccategoryISTB0405NCM', 'ISTB0405NCM', '网络配置与管理', 'course0060001', '信息技术基础', '网络空间安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (78, 'soccategoryISTB0405VT', 'ISTB0405VT', '虚拟化技术', 'course0060001', '信息技术基础', '网络空间安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (79, 'soccategoryISTB0103JAVAP', 'ISTB0103JAVAP', 'Java程序设计', 'course0050001', '编程技术', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (80, 'soccategoryISTB0402OSPRIN', 'ISTB0402OSPRIN', '操作系统原理', 'course0060001', '信息技术基础', '网络空间安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (81, 'soccategoryISTB0405WLFWJS', 'ISTB0405WLFWJS', '通用网络服务架设', 'course0060001', '信息技术基础', '网络空间安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (82, 'soccategoryISTB0402VFLINUX', 'ISTB0402VFLINUX', 'Linux基础入门', 'course0060001', '信息技术基础', '网络空间安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (83, 'soccategoryISTB0103CRPL', 'ISTB0103CRPL', 'C语言程序设计', 'course0050001', '编程技术', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (84, 'soccategoryISTB0103NP', 'ISTB0103NP', '网络编程', 'course0050001', '编程技术', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (85, 'soccategoryISTB0405CNT', 'ISTB0405CNT', '计算机组网技术', 'course0060001', '信息技术基础', '网络空间安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (86, 'soccategoryISTB0103PPL', 'ISTB0103PPL', 'Python程序设计', 'course0050001', '编程技术', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (87, 'soccategoryISTB0404DBEXP', 'ISTB0404DBEXP', '数据库实验', 'course0060001', '信息技术基础', '网络空间安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (88, 'soccategoryISTB0103GOLANG', 'ISTB0103GOLANG', 'Golang程序设计', 'course0050001', '编程技术', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (89, 'soccategoryISTB0103HTML', 'ISTB0103HTML', 'HTML语言程序设计', 'course0050001', '编程技术', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (90, 'soccategoryISTB0103PHP', 'ISTB0103PHP', 'PHP程序设计', 'course0050001', '编程技术', '安全编程', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (91, 'soccategoryNS0101NSA', 'NS0101NSA', '网络安全意识', 'course0010003', '安全意识', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (92, 'soccategoryNS0200IDSA', 'NS0200IDSA', '入侵检测与态势感知', 'course0010008', '网络安全', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (93, 'soccategoryNS0600WNS', 'NS0600WNS', '无线网络安全', 'course0010008', '网络安全', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (94, 'soccategoryATTVUL01BINVUL', 'ATTVUL01BINVUL', '二进制漏洞挖掘与利用', 'course0010008', '网络安全', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (95, 'soccategoryNS0400NETSEC', 'NS0400NETSEC', '网络安全防护', 'course0010008', '网络安全', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (96, 'soccategoryNS0100SEC', 'NS0100SEC', '网络安全概念', 'course0010008', '网络安全', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (97, 'soccategoryNS0504WEBSEC-NEW', 'NS0504WEBSEC-NEW', 'WEB安全', 'course0020001', 'Web安全', '应用安全', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (98, 'soccategoryNS0503DVWA', 'NS0503DVWA', 'WEB安全专题训练（DVWA）', 'course0020001', 'Web安全', '应用安全', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (99, 'soccategoryATTVUL02PHP', 'ATTVUL02PHP', 'PHP代码审计', 'course0020003', '代码审计', '应用安全', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (100, 'soccategoryVULNPRACTICE', 'VULNPRACTICE', '漏洞实战课程', 'course0010008', '网络安全', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (101, 'soccategoryNS0403NAADTAP', 'NS0403NAADTAP', '网络攻防技术与实践', 'course0010008', '网络安全', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (102, 'soccategoryNS0301NSAML', 'NS0301NSAML', '网络安全与机器学习', 'course0010008', '网络安全', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (103, 'soccategoryNS0501WEBUG', 'NS0501WEBUG', 'WEB安全专题训练（WeBug）', 'course0020001', 'Web安全', '应用安全', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (104, 'soccategoryNS0505WEBSZ', 'NS0505WEBSZ', 'Web安全实战分析', 'course0020001', 'Web安全', '应用安全', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (105, 'soccategoryATTIA03FATT', 'ATTIA03FATT', '钓鱼攻击', 'course0010008', '网络安全', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (106, 'soccategoryATTIP05PERMT', 'ATTIP05PERMT', '权限维持', 'course0010008', '网络安全', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (107, 'soccategoryATTPP03COMCON', 'ATTPP03COMCON', '命令与控制', 'course0010008', '网络安全', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (108, 'soccategoryNS0300ICA', 'NS0300ICA', '工控协议分析', 'course0010008', '网络安全', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (109, 'soccategoryATTIA06NSATT', 'ATTIA06NSATT', '近源攻击', 'course0010008', '网络安全', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (110, 'soccategoryNS0600WLJY', 'NS0600WLJY', '物理近源攻击', 'course0010008', '网络安全', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (111, 'soccategoryCRYPT07AUT', 'CRYPT07AUT', '认证', 'course0010007', '密码学', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (112, 'soccategoryCRYPT05PK', 'CRYPT05PK', '公钥密码', 'course0010007', '密码学', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (113, 'soccategoryCRYPT03STREAMCIP', 'CRYPT03STREAMCIP', '流（序列）密码', 'course0010007', '密码学', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (114, 'soccategoryCRYPT01CRYPTO', 'CRYPT01CRYPTO', '密码学概念', 'course0010007', '密码学', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (115, 'soccategoryCRYPT09GL', 'CRYPT09GL', '密钥管理', 'course0010007', '密码学', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (116, 'soccategoryCRYPT02BLOCIPH', 'CRYPT02BLOCIPH', '分组密码', 'course0010007', '密码学', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (117, 'soccategoryCRYPT04HASH', 'CRYPT04HASH', '哈希（Hash）函数', 'course0010007', '密码学', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (118, 'soccategoryCRYPT06DS', 'CRYPT06DS', '数字签名', 'course0010007', '密码学', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (119, 'soccategoryCRYPT08CRYPRO', 'CRYPT08CRYPRO', '密码协议', 'course0010007', '密码学', '信息安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (120, 'soccategoryDMXLL001', 'DMXLL001', '大模型应用', 'course0060001', '信息技术基础', '网络空间安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (121, 'soccategoryLLM00001', 'LLM00001', '大模型基础', 'course0060001', '信息技术基础', '网络空间安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (122, 'soccategoryDMX00001', 'DMX00001', '机器学习', 'course0060001', '信息技术基础', '网络空间安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (123, 'soccategoryLLM00002', 'LLM00002', '大模型核心原理', 'course0060001', '信息技术基础', '网络空间安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');
INSERT INTO `oj_course_soc_category` VALUES (124, 'soccategoryKW00*', 'KW00*', '多模态模型介绍', 'course0060001', '信息技术基础', '网络空间安全基础', '2025-04-27 18:13:54', '2025-04-27 18:13:54');




