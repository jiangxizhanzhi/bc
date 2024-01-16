-- ----------------------------
-- Records of dict_info
-- ----------------------------
INSERT INTO `dict_info` VALUES (1, 'avatar_01', '头像1', '/cpms/built_in/user_logos/1.png', 'avatar', '2023-12-07 17:28:54', '2023-12-07 17:28:56');
INSERT INTO `dict_info` VALUES (2, 'avatar_02', '头像2', '/cpms/built_in/user_logos/2.png', 'avatar', '2023-12-07 17:28:54', '2023-12-07 17:28:56');
INSERT INTO `dict_info` VALUES (3, 'avatar_03', '头像3', '/cpms/built_in/user_logos/3.png', 'avatar', '2023-12-07 17:28:54', '2023-12-07 17:28:56');
INSERT INTO `dict_info` VALUES (4, 'avatar_04', '头像4', '/cpms/built_in/user_logos/4.png', 'avatar', '2023-12-07 17:28:54', '2023-12-07 17:28:56');
INSERT INTO `dict_info` VALUES (5, 'avatar_05', '头像5', '/cpms/built_in/user_logos/5.png', 'avatar', '2023-12-07 17:28:54', '2023-12-07 17:28:56');
INSERT INTO `dict_info` VALUES (6, 'avatar_06', '头像6', '/cpms/built_in/user_logos/6.png', 'avatar', '2023-12-07 17:28:54', '2023-12-07 17:28:56');
INSERT INTO `dict_info` VALUES (7, 'avatar_07', '头像7', '/cpms/built_in/user_logos/7.png', 'avatar', '2023-12-07 17:28:54', '2023-12-07 17:28:56');
INSERT INTO `dict_info` VALUES (8, 'avatar_08', '头像8', '/cpms/built_in/user_logos/8.png', 'avatar', '2023-12-07 17:28:54', '2023-12-07 17:28:56');
INSERT INTO `dict_info` VALUES (9, 'avatar_09', '头像9', '/cpms/built_in/user_logos/9.png', 'avatar', '2023-12-07 17:28:54', '2023-12-07 17:28:56');
INSERT INTO `dict_info` VALUES (10, 'system_name', '系统名称', '网络与信息安全管理员实训平台', 'system_config', '2024-01-04 17:54:44', '2024-01-11 16:46:59');
INSERT INTO `dict_info` VALUES (11, 'system_logo', '系统Logo', '/cpms/upload/images/fe965729ee1a4f38ae94f4257548bfcf.png', 'system_config', '2024-01-04 17:55:29', '2024-01-11 16:46:59');
INSERT INTO `dict_info` VALUES (12, 'system_copyright_information', '版权信息', '南京赛宁信息技术有限公司', 'system_config', '2024-01-04 17:56:19', '2024-01-11 16:46:59');

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '3f655d87112f4ae094bbaf70830e20aa', 'admin', '超级管理员', '$2a$10$XCg8XmwMDAb5.x/bHHHmg.ZHfWWCvufE1zmtSd9FHZE.777a4wyjC', 1, '/cpms/built_in/user_logos/7.png', 1, NULL, NULL, NULL, '2024-01-16 09:29:35', 0.00);

-- ----------------------------
-- Records of system_property
-- ----------------------------
INSERT INTO `system_property` VALUES (1, 'export_builtin', '是否可以导出内置资源', 'false');
INSERT INTO `system_property` VALUES (2, 'isShowSituation', '是否显示态势导调', 'true');
INSERT INTO `system_property` VALUES (3, 'maxUserCount', '最大用户数（实时生效）', '15');
INSERT INTO `system_property` VALUES (4, 'maxOnlineUserCount', '最大在线用户数（实时生效）', '5');
INSERT INTO `system_property` VALUES (5, 'maxLoginCount', '单个账号最大同时登录数（重启后生效）', '1');
INSERT INTO `system_property` VALUES (6, 'sceneDestroyTime', '场景自动销毁时间（小时，重启后生效）', '2');
INSERT INTO `system_property` VALUES (7, 'maxSceneCount', '最大场景并发数（重启后生效）', '10');
INSERT INTO `system_property` VALUES (8, 'maxSceneCountPerUser', '单人启动最大场景数（重启后生效）', '2');

-- ----------------------------
-- Records of cr_constants_config
-- ----------------------------
INSERT INTO `cr_constants_config` VALUES (1, NULL, 0, 9, '初级', 'Primary');
INSERT INTO `cr_constants_config` VALUES (2, NULL, 1, 9, '中级', 'Intermediate');
INSERT INTO `cr_constants_config` VALUES (3, NULL, 2, 9, '高级', 'Advanced');
INSERT INTO `cr_constants_config` VALUES (4, NULL, 0, 10, '简单', 'Easy');
INSERT INTO `cr_constants_config` VALUES (5, NULL, 1, 10, '中等', 'Medium');
INSERT INTO `cr_constants_config` VALUES (6, NULL, 2, 10, '困难', 'Difficult');
INSERT INTO `cr_constants_config` VALUES (7, NULL, 1, 11, '已解出', 'Solved');
INSERT INTO `cr_constants_config` VALUES (8, NULL, 0, 11, '未解出', 'Unsolved');
INSERT INTO `cr_constants_config` VALUES (9, NULL, 0, 12, '理论题', 'Theory');
INSERT INTO `cr_constants_config` VALUES (10, NULL, 2, 12, '实操题', 'Practice');
INSERT INTO `cr_constants_config` VALUES (11, NULL, 3, 13, '课程', 'Course');
INSERT INTO `cr_constants_config` VALUES (12, NULL, 4, 13, '课时', 'Class');
INSERT INTO `cr_constants_config` VALUES (13, NULL, 0, 13, '理论题', 'Theory');
INSERT INTO `cr_constants_config` VALUES (14, NULL, 2, 13, '实操题', 'Practice');
INSERT INTO `cr_constants_config` VALUES (15, NULL, 0, 14, '未请求协助', 'Assistance Not Requested');
INSERT INTO `cr_constants_config` VALUES (16, NULL, 1, 14, '请求协助', 'Request Assistance');
INSERT INTO `cr_constants_config` VALUES (17, NULL, 2, 14, '协助中', 'Assistance In Progress');
INSERT INTO `cr_constants_config` VALUES (18, NULL, 3, 14, '结束协助', 'End Assistance');
INSERT INTO `cr_constants_config` VALUES (19, NULL, 0, 16, '理论课', 'Theory Class');
INSERT INTO `cr_constants_config` VALUES (20, NULL, 1, 16, '实验课', 'Experimental Class');
INSERT INTO `cr_constants_config` VALUES (21, NULL, 0, 17, '未配置', 'Not Configured');
INSERT INTO `cr_constants_config` VALUES (22, NULL, 1, 17, '选修', 'Elective');
INSERT INTO `cr_constants_config` VALUES (23, NULL, 2, 17, '必修', 'Required');
INSERT INTO `cr_constants_config` VALUES (24, NULL, 1, 18, '课程', 'Course');
INSERT INTO `cr_constants_config` VALUES (25, NULL, 2, 18, '课时', 'Class');
INSERT INTO `cr_constants_config` VALUES (26, NULL, 3, 18, '实操题', 'Practice');
INSERT INTO `cr_constants_config` VALUES (27, NULL, 1, 19, '笔记', 'Note');
INSERT INTO `cr_constants_config` VALUES (28, NULL, 2, 19, '实验报告', 'Report');
INSERT INTO `cr_constants_config` VALUES (29, NULL, 0, 20, '理论基础', 'Theoretical Basis');
INSERT INTO `cr_constants_config` VALUES (30, NULL, 1, 20, '真实漏洞', 'Real Vulnerability');
INSERT INTO `cr_constants_config` VALUES (31, NULL, 2, 20, '实践操作', 'Operation Practice');
INSERT INTO `cr_constants_config` VALUES (32, NULL, 3, 20, '课程', 'Course');
INSERT INTO `cr_constants_config` VALUES (33, NULL, 4, 20, '课时', 'Class');
INSERT INTO `cr_constants_config` VALUES (34, NULL, 0, 21, '单选', 'Single Choice');
INSERT INTO `cr_constants_config` VALUES (35, NULL, 1, 21, '多选', 'Multiple Choice');
INSERT INTO `cr_constants_config` VALUES (36, NULL, 2, 21, '判断', 'True Or False');
INSERT INTO `cr_constants_config` VALUES (37, NULL, 3, 21, '填空', 'Completion');
INSERT INTO `cr_constants_config` VALUES (38, NULL, 4, 21, '简答', 'Q & A');
INSERT INTO `cr_constants_config` VALUES (39, NULL, 0, 25, '未开始', 'Not Started');
INSERT INTO `cr_constants_config` VALUES (40, NULL, 1, 25, '进行中', 'In Progress');
INSERT INTO `cr_constants_config` VALUES (41, NULL, 2, 25, '已结束', 'Ended');
INSERT INTO `cr_constants_config` VALUES (42, NULL, 0, 26, '个人参赛', 'Personal');
INSERT INTO `cr_constants_config` VALUES (43, NULL, 1, 26, '团队参赛', 'Group');
INSERT INTO `cr_constants_config` VALUES (44, NULL, 0, 27, '理论赛', 'Theoretical Competition');
INSERT INTO `cr_constants_config` VALUES (45, NULL, 1, 27, '运维赛', 'Operation And Maintenance Competition');
INSERT INTO `cr_constants_config` VALUES (46, NULL, 2, 27, '夺旗赛', 'Flag Competition');
INSERT INTO `cr_constants_config` VALUES (47, NULL, 3, 27, '攻防赛', 'Offensive And Defensive Competition');
INSERT INTO `cr_constants_config` VALUES (48, NULL, 4, 27, '渗透赛', 'Infiltration Competition');
INSERT INTO `cr_constants_config` VALUES (49, NULL, 5, 27, '人工智能赛', 'AI Competition');
INSERT INTO `cr_constants_config` VALUES (50, NULL, 0, 28, '未开始', 'Not Started');
INSERT INTO `cr_constants_config` VALUES (51, NULL, 1, 28, '进行中', 'In Progress');
INSERT INTO `cr_constants_config` VALUES (52, NULL, 2, 28, '已结束', 'Ended');
INSERT INTO `cr_constants_config` VALUES (53, NULL, 3, 28, '暂停中', 'Suspended');
INSERT INTO `cr_constants_config` VALUES (54, NULL, 1, 31, '课程', 'Course');
INSERT INTO `cr_constants_config` VALUES (55, NULL, 2, 31, '课时', 'Class');
INSERT INTO `cr_constants_config` VALUES (56, NULL, 3, 31, '实践操作', 'Operation Practice');
INSERT INTO `cr_constants_config` VALUES (57, NULL, 1, 36, '单兵训练', 'Single Person Training');
INSERT INTO `cr_constants_config` VALUES (58, NULL, 3, 36, '团队训练', 'Team Training');
INSERT INTO `cr_constants_config` VALUES (59, NULL, 4, 36, '红蓝对抗', 'Red Blue Confrontation');
INSERT INTO `cr_constants_config` VALUES (60, NULL, 1, 38, '未评估', 'No Assess');
INSERT INTO `cr_constants_config` VALUES (61, NULL, 2, 38, '评估中', 'Assessing');
INSERT INTO `cr_constants_config` VALUES (62, NULL, 3, 38, '已评估', 'Has Assessed');
INSERT INTO `cr_constants_config` VALUES (63, NULL, 4, 38, '异常', 'Abnormal');
INSERT INTO `cr_constants_config` VALUES (64, NULL, 1, 15, '初级', 'Primary');
INSERT INTO `cr_constants_config` VALUES (65, NULL, 2, 15, '中级', 'Intermediate');
INSERT INTO `cr_constants_config` VALUES (66, NULL, 3, 15, '高级', 'Advanced');
INSERT INTO `cr_constants_config` VALUES (67, NULL, 1, 22, '初级', 'Primary');
INSERT INTO `cr_constants_config` VALUES (68, NULL, 2, 22, '中级', 'Intermediate');
INSERT INTO `cr_constants_config` VALUES (69, NULL, 3, 22, '高级', 'Advanced');
INSERT INTO `cr_constants_config` VALUES (70, NULL, 1, 23, '初级', 'Primary');
INSERT INTO `cr_constants_config` VALUES (71, NULL, 2, 23, '中级', 'Intermediate');
INSERT INTO `cr_constants_config` VALUES (72, NULL, 3, 23, '高级', 'Advanced');
INSERT INTO `cr_constants_config` VALUES (73, NULL, 1, 24, '初级', 'Primary');
INSERT INTO `cr_constants_config` VALUES (74, NULL, 2, 24, '中级', 'Intermediate');
INSERT INTO `cr_constants_config` VALUES (75, NULL, 3, 24, '高级', 'Advanced');
INSERT INTO `cr_constants_config` VALUES (76, NULL, 0, 37, '初级', 'Primary');
INSERT INTO `cr_constants_config` VALUES (77, NULL, 1, 37, '中级', 'Intermediate');
INSERT INTO `cr_constants_config` VALUES (78, NULL, 2, 37, '高级', 'Advanced');
INSERT INTO `cr_constants_config` VALUES (79, NULL, 1, 39, '已解出', 'Solved');
INSERT INTO `cr_constants_config` VALUES (80, NULL, 2, 39, '未开始', 'Not started');
INSERT INTO `cr_constants_config` VALUES (81, NULL, 0, 39, '尝试过', 'Attempted');
INSERT INTO `cr_constants_config` VALUES (82, NULL, 1, 40, '课程', 'Course');
INSERT INTO `cr_constants_config` VALUES (83, NULL, 2, 40, '课时', 'Class');
INSERT INTO `cr_constants_config` VALUES (84, NULL, 3, 40, '实操题', 'Practice');
INSERT INTO `cr_constants_config` VALUES (85, NULL, 5, 40, '理论题', 'Theory');
INSERT INTO `cr_constants_config` VALUES (86, NULL, 0, 41, '实操解题', 'Operation');
INSERT INTO `cr_constants_config` VALUES (87, NULL, 1, 41, '实操运维', 'Maintenance');