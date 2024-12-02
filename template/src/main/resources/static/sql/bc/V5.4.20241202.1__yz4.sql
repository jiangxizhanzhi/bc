UPDATE cr_constants_config
SET cn_name = 'ssh接入节点'
WHERE type = 43 AND code = 0;

INSERT INTO `cr_constants_config`(`resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 4, 42, 'console接入节点', 'Access Node By Console');
INSERT INTO `cr_constants_config`(`resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 5, 42, 'rdp接入节点', 'Access Node By Rdp');
INSERT INTO `cr_constants_config`(`resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 6, 42, 'nc接入节点', 'Access Node By Nc');
INSERT INTO `cr_constants_config`(`resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 7, 42, 'telnet接入节点', 'Access Node By Telnet');
INSERT INTO `cr_constants_config`(`resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 8, 42, 'com接入节点', 'Access Node By Com');


