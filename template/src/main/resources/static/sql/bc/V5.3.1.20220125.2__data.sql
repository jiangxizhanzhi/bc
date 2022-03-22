INSERT INTO `cr_constants_config`
VALUES (1, '05b739fc686a073756399f79c34039b5', NULL, 1, '情报侦查研判', 'Intelligence Investigation');
INSERT INTO `cr_constants_config`
VALUES (2, '1102f907c7650ad541c381f59c700f5f', NULL, 1, '密码破解', 'Password Cracking');
INSERT INTO `cr_constants_config`
VALUES (3, '2eec84346538e972a962b165e499f52d', NULL, 1, '终端渗透控制', 'Terminal Penetration Control');
INSERT INTO `cr_constants_config`
VALUES (4, '2f4f15de59cd2dbadc5d8a582e9f6849', NULL, 1, '威胁发现和检测', 'Threat Discovery And Detection');
INSERT INTO `cr_constants_config`
VALUES (5, '3b6e90254227833303d908336b105ade', NULL, 1, '内网渗透控制', 'Intranet Penetration Control');
INSERT INTO `cr_constants_config`
VALUES (6, '446bbba739678a4e6e103e7d214d55aa', NULL, 1, '网络应用控制破解', 'Network Application Control Cracking');
INSERT INTO `cr_constants_config`
VALUES (7, '6753aa8cad6fa9760308871e707ea033', NULL, 1, '网络侦查', 'Network Investigation');
INSERT INTO `cr_constants_config`
VALUES (8, '8568759a4d514ff730ed6eb1721a2f22', NULL, 1, '反侦查', 'Anti Detection');
INSERT INTO `cr_constants_config`
VALUES (9, 'a2e58506b8d588d7e1cef85f7350728a', NULL, 1, '漏洞挖掘利用', 'Exploit');
INSERT INTO `cr_constants_config`
VALUES (10, 'a7914af4e20435cb7ad7b8b4ae0bac6a', NULL, 1, '应急响应', 'Emergency Response');
INSERT INTO `cr_constants_config`
VALUES (11, 'b27501c31132b288df1bef259e2625e6', NULL, 1, '网站渗透控制', 'Website Penetration Control');
INSERT INTO `cr_constants_config`
VALUES (12, 'bd43486dee157d5e5106d547801e2ea2', NULL, 1, '系统加固', 'System Hardening');
INSERT INTO `cr_constants_config`
VALUES (13, 'c4173e927825d4ff52b112e268fb01ad', NULL, 1, '数据分析', 'Data Analysis');
INSERT INTO `cr_constants_config`
VALUES (14, 'd0cf5fc50285e1b64c1dfc3adb9ba39d', NULL, 1, '技术反制', 'Technological Countermeasures');
INSERT INTO `cr_constants_config`
VALUES (15, 'dff8a9b76f5536babae3d4c22943004d', NULL, 1, '隐蔽能力', 'Concealment');
INSERT INTO `cr_constants_config`
VALUES (16, NULL, 1, 2, '低', 'Primary');
INSERT INTO `cr_constants_config`
VALUES (17, NULL, 2, 2, '中', 'Advanced');
INSERT INTO `cr_constants_config`
VALUES (18, NULL, 3, 2, '高', 'Senior');
INSERT INTO `cr_constants_config`
VALUES (19, NULL, 1, 3, '渗透测试', 'Penetration Test');
INSERT INTO `cr_constants_config`
VALUES (20, NULL, 2, 3, '运维防护', 'Security Operations');
INSERT INTO `cr_constants_config`
VALUES (21, NULL, 3, 3, '系统仿真', 'System Emulation');
INSERT INTO `cr_constants_config`
VALUES (22, NULL, 4, 3, '综合场景', 'Countermea');
INSERT INTO `cr_constants_config`
VALUES (23, NULL, 5, 3, '其他', 'Others');



INSERT INTO `cr_radar_label` (`id`, `resource_id`, `label`)
VALUES (1, 1, '侦测跟踪');
INSERT INTO `cr_radar_label` (`id`, `resource_id`, `label`)
VALUES (2, 2, '武器构建');
INSERT INTO `cr_radar_label` (`id`, `resource_id`, `label`)
VALUES (3, 3, '载荷投递');
INSERT INTO `cr_radar_label` (`id`, `resource_id`, `label`)
VALUES (4, 4, '漏洞利用');
INSERT INTO `cr_radar_label` (`id`, `resource_id`, `label`)
VALUES (5, 5, '安装植入');
INSERT INTO `cr_radar_label` (`id`, `resource_id`, `label`)
VALUES (6, 6, '命令与控制');
INSERT INTO `cr_radar_label` (`id`, `resource_id`, `label`)
VALUES (7, 7, '目标达成');