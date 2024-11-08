ALTER TABLE `oj_practice_operation`
    ADD COLUMN `type` tinyint NULL COMMENT '类型';

ALTER TABLE `oj_practice_theory`
    ADD COLUMN `type` tinyint NULL COMMENT '类型',
    ADD COLUMN `video` varchar(255) NULL COMMENT '视频';

INSERT INTO `cr_constants_config` (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, NULL, 1, 49, '攻击', 'Course');
INSERT INTO `cr_constants_config` (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, NULL, 2, 49, '防守', 'Course');
INSERT INTO `cr_constants_config` (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, NULL, 3, 49, '其他', 'Course');

INSERT INTO `cr_constants_config` (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, NULL, 1, 50, '视频题', 'Course');
INSERT INTO `cr_constants_config` (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, NULL, 2, 50, '图片题', 'Course');
INSERT INTO `cr_constants_config` (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, NULL, 3, 50, '文字题', 'Course');
