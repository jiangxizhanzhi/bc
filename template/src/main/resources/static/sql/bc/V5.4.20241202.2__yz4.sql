delete from cr_constants_config where type=42 and code in (4,5,6,7,8);

INSERT INTO `cr_constants_config`(`resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 4, 43, 'console接入节点', 'Access Node By Console');
INSERT INTO `cr_constants_config`(`resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 5, 43, 'rdp接入节点', 'Access Node By Rdp');
INSERT INTO `cr_constants_config`(`resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 6, 43, 'nc接入节点', 'Access Node By Nc');
INSERT INTO `cr_constants_config`(`resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 7, 43, 'telnet接入节点', 'Access Node By Telnet');
INSERT INTO `cr_constants_config`(`resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 8, 43, 'com接入节点', 'Access Node By Com');


