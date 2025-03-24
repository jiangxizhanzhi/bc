ALTER TABLE `space_strategy`
    ADD COLUMN `suggest` text NULL;


UPDATE `cr_tool_toolcategory` SET `cn_name` = '漏洞扫描系统工具' WHERE `resource_id` = '7c55604624ce4c5d97b6ff07dc6b7e82';
UPDATE `cr_tool_toolcategory` SET `cn_name` = 'Linux远控工具' WHERE `resource_id` = '432b67ef344a416ba76247d15e0e8fff';
UPDATE `cr_tool_toolcategory` SET `cn_name` = '网络设备远控工具' WHERE `resource_id` = '3133cc29ee2e4ab5aa147087ed8aab48';
UPDATE `cr_tool_toolcategory` SET `cn_name` = '网站控制工具' WHERE `resource_id` = '8b454b8b48e0431da3b20cfe3fddd6b6';
UPDATE `cr_tool_toolcategory` SET `cn_name` = '固件控制工具' WHERE `resource_id` = 'bc88c226ee98491f8a40dbbb49fa1012';
UPDATE `cr_tool_toolcategory` SET `cn_name` = '蜜罐系统工具' WHERE `resource_id` = '1ccb48f7e4f54c458c6078325cca1f9c';
UPDATE `cr_tool_toolcategory` SET `cn_name` = '网络取证与溯源工具' WHERE `resource_id` = 'b9bae6f452d9447a94ca86a70076c8e6';

INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, '1dbce28271504d97b08010fcce42e65b', '漏洞扫描工具', '', NULL, 'probe');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, 'c597b431b9ce466080b4e097a6d82723', '操作系统攻击工具', '', NULL, 'attack');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, 'b2c472e6d934456a9fcad85eea38281e', '应用软件攻击工具', '', NULL, 'attack');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, '31a4bd84e5944e97ab443733536a1f79', '边界防护系统攻击工具', '', NULL, 'attack');
INSERT INTO `cr_tool_toolcategory` (`id`, `resource_id`, `cn_name`, `en_name`, `serial_version_uid`, `type`) VALUES (NULL, 'e68409fa7e1643dea441ad304bbb5c16', '边界防护设备远控工具', '', NULL, 'control');