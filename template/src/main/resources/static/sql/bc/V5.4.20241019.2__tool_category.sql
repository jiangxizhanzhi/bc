ALTER TABLE `oj_course`
    ADD COLUMN `job` tinyint NOT NULL DEFAULT 1;

ALTER TABLE `cr_tool_tool`
    ADD COLUMN `disabled` tinyint NULL DEFAULT 0,
    ADD COLUMN `type` varchar(255) NULL;

ALTER TABLE `cr_tool_toolcategory`
    ADD COLUMN `type` varchar(255) NULL,
    ADD INDEX `type`(`type`) USING BTREE,
    ADD UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE;


INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 1, 41, '安管管控', 'Course');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 2, 41, '检测预警', 'Class');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 3, 41, '应急响应', 'Theory');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 4, 41, '安全服务', 'Practice');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 5, 41, '检测评估', 'Practice');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 6, 41, '基线防护', 'Practice');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 7, 41, '指挥控制', 'Practice');


INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 1, 42, '简单', 'Course');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 2, 42, '一般', 'Class');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 3, 42, '困难', 'Theory');



INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, 'd948effb593a46cd9fab1aa9cee159d3', '网络踩点工具', '', NULL, 'probe');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, '6f03c5306f4e4ee791dee359dcdbf885', '网络测绘工具', '', NULL, 'probe');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, 'd8ed7cce638f40e599db1dd04bc6233a', '设备指纹定位工具', '', NULL, 'probe');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, '1e6df1843ba348e684ee3bae0a4b65c1', '网络应用侦察工具', '', NULL, 'probe');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, 'f8f8a636c5514accbac0510016cec28c', '即时通信侦察工具', '', NULL, 'probe');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, '75662acb37a44f55a4078d47d1af52a4', '云平台侦察工具', '', NULL, 'probe');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, '7c55604624ce4c5d97b6ff07dc6b7e82', '漏洞扫描系统', '', NULL, 'attack');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, '937315a1d7604e00879211c1c3352743', '数据库攻击工具', '', NULL, 'attack');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, 'd1edaaa2b3aa47e0a643811a41d853e1', '网站攻击工具', '', NULL, 'attack');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, '374f6ab86ede417e9cd2f42b6d516b61', '网络设备攻击工具', '', NULL, 'attack');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, 'a4692569218a4278adaab0b6cd47e51f', '虚拟化系统攻击工具', '', NULL, 'attack');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, '0017f05264674b8fbdd58ad591f5d22d', 'Windows远控工具', '', NULL, 'control');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, '432b67ef344a416ba76247d15e0e8fff', 'Linux远控', '', NULL, 'control');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, '3133cc29ee2e4ab5aa147087ed8aab48', '网络设备远控', '', NULL, 'control');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, '8b454b8b48e0431da3b20cfe3fddd6b6', '网站控制', '', NULL, 'control');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, 'bc88c226ee98491f8a40dbbb49fa1012', '固件控制', '', NULL, 'control');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, '0d8a6eb4784a46a2a1357391e4cacaac', '驻留/后门工具', '', NULL, 'control');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, '1b7d9b9a902d43a09ae08c17c2327660', '网站密取工具', '', NULL, 'control');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, 'e46cf528a5874bf4ae1e230f08fa6b06', '内网密取工具', '', NULL, 'control');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, 'b0f3371535934a34a237dedd9413bcaa', '安全漏洞扫描工具', '', NULL, 'defense');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, '1ccb48f7e4f54c458c6078325cca1f9c', '蜜罐系统', '', NULL, 'defense');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, 'b9bae6f452d9447a94ca86a70076c8e6', '网络取证与溯源', '', NULL, 'defense');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, 'ebb2d44d23c447a7a134517d9ab93b4e', '终端安全检测工具', '', NULL, 'defense');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, '87957cc962c1492cbb90e49547b0669f', '主机安全检测工具', '', NULL, 'defense');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, '1ff5953626d443b38faee498d465f808', 'APT攻击行为检测工具', '', NULL, 'defense');

INSERT INTO cr_constants_config (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 'probe', NULL, 43, '探测类工具', 'Practice');
INSERT INTO cr_constants_config (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 'attack', NULL, 43, '攻击类工具', 'Practice');
INSERT INTO cr_constants_config (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 'control', NULL, 43, '控制类工具', 'Practice');
INSERT INTO cr_constants_config (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 'defense', NULL, 43, '防御类工具', 'Practice');