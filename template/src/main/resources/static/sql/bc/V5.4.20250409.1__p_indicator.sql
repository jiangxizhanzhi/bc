/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.179.10_145
 Source Server Type    : MySQL
 Source Server Version : 80039
 Source Host           : 192.168.179.10:30002
 Source Schema         : CTF

 Target Server Type    : MySQL
 Target Server Version : 80039
 File Encoding         : 65001

 Date: 08/04/2025 20:58:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for p_indicator
-- ----------------------------
DROP TABLE IF EXISTS `p_indicator`;
CREATE TABLE `p_indicator`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `parent_id` int NULL DEFAULT NULL,
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `type` tinyint(1) NULL DEFAULT 1 COMMENT '所属类型 1 系统默认 2 用户自定义',
  `indicator_type` tinyint(1) NULL DEFAULT 1 COMMENT '指标所属类型1 攻击方 2 防守方',
  `indicator_from` tinyint(1) NULL DEFAULT NULL COMMENT '指标来源1 知识库 2 ATTACK库 持续补充',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2193 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '指标表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of p_indicator
-- ----------------------------
INSERT INTO `p_indicator` VALUES (101, '信息收集分析', NULL, '信息收集分析', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (102, '资产扫描', NULL, '资产扫描', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (103, '域名扫描', NULL, '域名扫描', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (104, '漏洞扫描', NULL, '漏洞扫描', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (105, '主流漏洞基本原理', NULL, '主流漏洞基本原理', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (106, '技战术规划', NULL, '技战术规划', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (107, '战术规划', NULL, '战术规划', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (108, '战斗基本原则', NULL, '战斗基本原则', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (109, '技术规划', NULL, '技术规划', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (110, '网络安全基本知识', NULL, '网络安全基本知识', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (111, '行动风险评估', NULL, '行动风险评估', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (112, '风险评估', NULL, '风险评估', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (113, '信息安全风险评估方法', NULL, '信息安全风险评估方法', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (114, '应急响应', NULL, '应急响应', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (115, '应急响应基本流程', NULL, '应急响应基本流程', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (116, '资源开发', NULL, '资源开发', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (117, '访问权限获取', NULL, '访问权限获取', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (118, '计算机网络基本知识', NULL, '计算机网络基本知识', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (119, '基础架构获取', NULL, '基础架构获取', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (120, '账户获取', NULL, '账户获取', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (121, '网络安全基本工具使用', NULL, '网络安全基本工具使用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (122, '工具利用', NULL, '工具利用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (123, '初步渗透', NULL, '初步渗透', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (124, '网络钓鱼', NULL, '网络钓鱼', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (125, '社会工程学基本知识', NULL, '社会工程学基本知识', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (126, '漏洞利用', NULL, '漏洞利用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (127, '可移动介质利用', NULL, '可移动介质利用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (128, '可移动介质基本知识', NULL, '可移动介质基本知识', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (129, '供应链利用', NULL, '供应链利用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (130, '有效账户利用', NULL, '有效账户利用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (131, '渗透攻击', NULL, '渗透攻击', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (132, '命令和脚本利用', NULL, '命令和脚本利用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (133, 'powershell基本知识', NULL, 'powershell基本知识', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (134, '容器利用', NULL, '容器利用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (135, '容器基础知识', NULL, '容器基础知识', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (136, '进程通信', NULL, '进程通信', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (137, '操作系统基本知识', NULL, '操作系统基本知识', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (138, '计划任务利用', NULL, '计划任务利用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (139, '计划任务基础知识', NULL, '计划任务基础知识', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (140, '系统服务利用', NULL, '系统服务利用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (141, '系统服务安全基础', NULL, '系统服务安全基础', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (142, '用户行为利用', NULL, '用户行为利用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (143, '用户行为分析原理和方法', NULL, '用户行为分析原理和方法', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (144, '持续控制', NULL, '持续控制', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (145, '账户操控', NULL, '账户操控', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (146, '账户管理基础', NULL, '账户管理基础', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (147, '启动/登录初始化控制', NULL, '启动/登录初始化控制', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (148, '浏览器扩展利用', NULL, '浏览器扩展利用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (149, '客户端软件修改', NULL, '客户端软件修改', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (150, '软件逆向工程', NULL, '软件逆向工程', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (151, '系统进程创建修改', NULL, '系统进程创建修改', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (152, '特定事件触发执行', NULL, '特定事件触发执行', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (153, '劫持执行技术', NULL, '劫持执行技术', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (154, '网络协议基本知识', NULL, '网络协议基本知识', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (155, '身份验证过程修改', NULL, '身份验证过程修改', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (156, '身份验证机制与协议', NULL, '身份验证机制与协议', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (157, 'Office Microsoft利用', NULL, 'Office Microsoft利用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (158, 'office软件工作原理', NULL, 'office软件工作原理', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (159, '操作系统引导机制利用', NULL, '操作系统引导机制利用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (160, '服务器组件利用', NULL, '服务器组件利用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (161, '服务器基本知识', NULL, '服务器基本知识', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (162, '流量信号利用', NULL, '流量信号利用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (163, '网络流量分析基本知识', NULL, '网络流量分析基本知识', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (164, '权限提升', NULL, '权限提升', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (165, '控制机制提权', NULL, '控制机制提权', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (166, '账户权限与访问控制原理', NULL, '账户权限与访问控制原理', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (167, '令牌仿冒', NULL, '令牌仿冒', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (168, '令牌机制原理', NULL, '令牌机制原理', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (169, '域策略利用', NULL, '域策略利用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (170, '域环境基本知识', NULL, '域环境基本知识', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (171, '主机逃逸', NULL, '主机逃逸', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (172, '主机安全基础知识', NULL, '主机安全基础知识', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (173, '进程注入', NULL, '进程注入', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (174, '进程通信基本原理机制', NULL, '进程通信基本原理机制', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (175, '凭据访问', NULL, '凭据访问', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (176, '中间人攻击', NULL, '中间人攻击', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (177, '暴力破解', NULL, '暴力破解', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (178, '密码学基本知识', NULL, '密码学基本知识', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (179, '存储凭据获取', NULL, '存储凭据获取', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (180, '凭据管理与存储机制', NULL, '凭据管理与存储机制', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (181, '凭据伪造', NULL, '凭据伪造', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (182, '输入捕获', NULL, '输入捕获', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (183, '键盘记录技术原理', NULL, '键盘记录技术原理', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (184, '凭据转储', NULL, '凭据转储', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (185, 'Kerberos伪造', NULL, 'Kerberos伪造', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (186, 'Kerberos协议基础知识', NULL, 'Kerberos协议基础知识', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (187, '环境探查', NULL, '环境探查', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (188, '账户发现', NULL, '账户发现', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (189, '自动化扫描工具使用方法', NULL, '自动化扫描工具使用方法', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (190, '云环境发现', NULL, '云环境发现', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (191, '云计算基础知识', NULL, '云计算基础知识', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (192, '系统环境发现', NULL, '系统环境发现', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (193, '权限组发现', NULL, '权限组发现', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (194, '虚拟化/沙盒规避', NULL, '虚拟化/沙盒规避', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (195, '虚拟化技术基础', NULL, '虚拟化技术基础', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (196, '横向移动', NULL, '横向移动', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (197, '远程连接劫持', NULL, '远程连接劫持', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (198, '横向工具利用', NULL, '横向工具利用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (199, '横向移动技术原理', NULL, '横向移动技术原理', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (200, '远程服务利用', NULL, '远程服务利用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (201, '身份验证材料利用', NULL, '身份验证材料利用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (202, '关键信息收集', NULL, '关键信息收集', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (203, '数据存档', NULL, '数据存档', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (204, '数据存档格式与原理', NULL, '数据存档格式与原理', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (205, '自动收集', NULL, '自动收集', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (206, '常用网络爬虫与自动化工具', NULL, '常用网络爬虫与自动化工具', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (207, '存储库数据收集', NULL, '存储库数据收集', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (208, '存储库类型与结构', NULL, '存储库类型与结构', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (209, '数据暂存', NULL, '数据暂存', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (210, '数据暂存机制与原理', NULL, '数据暂存机制与原理', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (211, '邮件收集', NULL, '邮件收集', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (212, '邮件协议与工作原理', NULL, '邮件协议与工作原理', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (213, '多媒体数据收集', NULL, '多媒体数据收集', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (214, '多媒体数据格式与编码', NULL, '多媒体数据格式与编码', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (215, '命令控制', NULL, '命令控制', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (216, '协议控制', NULL, '协议控制', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (217, '数据编码', NULL, '数据编码', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (218, '数据编码基础知识', NULL, '数据编码基础知识', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (219, '数据混淆', NULL, '数据混淆', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (220, '数据混淆基础', NULL, '数据混淆基础', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (221, '代理', NULL, '代理', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (222, '代理技术基础', NULL, '代理技术基础', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (223, '数据窃取', NULL, '数据窃取', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (224, '自动渗透', NULL, '自动渗透', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (225, '漏洞挖掘与利用', NULL, '漏洞挖掘与利用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (226, '协议替代渗透', NULL, '协议替代渗透', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (227, '网络介质数据窃取', NULL, '网络介质数据窃取', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (228, '数据窃取技术原理', NULL, '数据窃取技术原理', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (229, '物理介质数据窃取', NULL, '物理介质数据窃取', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (230, '硬件破解原理', NULL, '硬件破解原理', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (231, '打击侵害', NULL, '打击侵害', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (232, '权限删除', NULL, '权限删除', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (233, '提权技术原理', NULL, '提权技术原理', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (234, '数据销毁', NULL, '数据销毁', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (235, '物理销毁和逻辑销毁区别', NULL, '物理销毁和逻辑销毁区别', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (236, '数据篡改', NULL, '数据篡改', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (237, '数据结构与存储机制', NULL, '数据结构与存储机制', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (238, '污损', NULL, '污损', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (239, '数据污损技术原理', NULL, '数据污损技术原理', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (240, '磁盘擦除', NULL, '磁盘擦除', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (241, '磁盘结构和存储机制', NULL, '磁盘结构和存储机制', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (242, '拒绝服务', NULL, '拒绝服务', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (243, '拒绝服务攻击原理', NULL, '拒绝服务攻击原理', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (244, '防御规避', NULL, '防御规避', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (245, '控制机制绕过', NULL, '控制机制绕过', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (246, '安全控制机制原理', NULL, '安全控制机制原理', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (247, '安全设备绕过', NULL, '安全设备绕过', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (248, '安全设备工作原理', NULL, '安全设备工作原理', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (249, '文件信息隐藏', NULL, '文件信息隐藏', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (250, '文件隐藏技术', NULL, '文件隐藏技术', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (251, '域策略逃避', NULL, '域策略逃避', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (252, '域策略和组策略配置与应用', NULL, '域策略和组策略配置与应用', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (253, '痕迹隐藏', NULL, '痕迹隐藏', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (254, '痕迹清除技术', NULL, '痕迹清除技术', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (255, '伪装', NULL, '伪装', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (256, '文件信息混淆', NULL, '文件信息混淆', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (257, '编码转换与混淆技术原理', NULL, '编码转换与混淆技术原理', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (258, '痕迹消除', NULL, '痕迹消除', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (259, '数据清除', NULL, '数据清除', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (260, '云计算基础架构修改', NULL, '云计算基础架构修改', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (261, '系统映像修改', NULL, '系统映像修改', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (262, '安全机制破坏', NULL, '安全机制破坏', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (263, '常见加密机制原理', NULL, '常见加密机制原理', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (264, '二进制代理执行', NULL, '二进制代理执行', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (265, '二进制文件格式分析', NULL, '二进制文件格式分析', 1, 1, 1);
INSERT INTO `p_indicator` VALUES (267, '资产管理', NULL, '资产管理', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (268, '网络管理', NULL, '网络管理', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (269, '计算机网络基础知识', NULL, '计算机网络基础知识', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (270, '系统运维', NULL, '系统运维', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (271, '行动评估', NULL, '行动评估', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (272, '方案设计与实施', NULL, '方案设计与实施', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (273, '解决方案设计', NULL, '解决方案设计', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (274, '方案实施', NULL, '方案实施', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (275, '技术加固', NULL, '技术加固', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (276, '应用强化', NULL, '应用强化', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (277, '安全加固技术与原则', NULL, '安全加固技术与原则', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (278, '凭据加固', NULL, '凭据加固', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (279, '消息强化', NULL, '消息强化', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (280, '消息加密与签名原理', NULL, '消息加密与签名原理', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (281, '平台加固', NULL, '平台加固', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (282, '平台安全架构设计原理', NULL, '平台安全架构设计原理', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (283, '内容分析', NULL, '内容分析', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (284, '文件分析', NULL, '文件分析', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (285, '文件结构与存储原理', NULL, '文件结构与存储原理', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (286, '标识符分析', NULL, '标识符分析', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (287, '安全编程基础实践', NULL, '安全编程基础实践', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (288, '消息分析', NULL, '消息分析', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (289, '消息验证机制原理', NULL, '消息验证机制原理', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (290, '网络分析', NULL, '网络分析', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (291, '流量分析', NULL, '流量分析', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (292, '证书分析', NULL, '证书分析', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (293, '证书管理与PKI体系原理', NULL, '证书管理与PKI体系原理', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (294, '平台监控', NULL, '平台监控', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (295, '固件分析', NULL, '固件分析', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (296, '固件基础知识', NULL, '固件基础知识', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (297, '操作系统监控', NULL, '操作系统监控', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (298, '行为分析', NULL, '行为分析', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (299, '系统行为分析', NULL, '系统行为分析', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (300, '用户行为分析', NULL, '用户行为分析', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (301, '常见用户行为模式', NULL, '常见用户行为模式', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (302, '威胁建模', NULL, '威胁建模', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (303, '情报分析', NULL, '情报分析', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (304, '威胁情报基本知识', NULL, '威胁情报基本知识', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (305, '程序隔离', NULL, '程序隔离', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (306, '文件隔离', NULL, '文件隔离', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (307, '文件访问控制原理', NULL, '文件访问控制原理', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (308, '进程隔离', NULL, '进程隔离', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (309, '进程隔离原理', NULL, '进程隔离原理', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (311, '网络流量过滤', NULL, '网络流量过滤', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (312, 'DNS拒绝', NULL, 'DNS拒绝', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (313, 'DNS协议基础知识', NULL, 'DNS协议基础知识', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (314, '环境搭建', NULL, '环境搭建', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (315, '蜜网搭建', NULL, '蜜网搭建', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (316, '蜜网概念和原理', NULL, '蜜网概念和原理', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (317, '诱饵部署', NULL, '诱饵部署', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (318, '诱饵设计与制作', NULL, '诱饵设计与制作', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (319, '攻击诱导', NULL, '攻击诱导', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (320, '网络环境诱导', NULL, '网络环境诱导', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (321, '网络安全架构设计', NULL, '网络安全架构设计', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (322, '应用诱导', NULL, '应用诱导', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (323, '恶意行为驱逐', NULL, '恶意行为驱逐', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (324, '凭据驱逐', NULL, '凭据驱逐', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (325, '进程驱逐', NULL, '进程驱逐', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (326, '进程管理基础知识', NULL, '进程管理基础知识', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (327, '系统恢复', NULL, '系统恢复', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (328, '网络恢复', NULL, '网络恢复', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (329, '进程恢复', NULL, '进程恢复', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (330, '分析优化', NULL, '分析优化', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (331, '情报提炼分析', NULL, '情报提炼分析', 1, 2, 1);
INSERT INTO `p_indicator` VALUES (332, '威胁建模优化', NULL, '威胁建模优化', 1, 2, 1);

SET FOREIGN_KEY_CHECKS = 1;
