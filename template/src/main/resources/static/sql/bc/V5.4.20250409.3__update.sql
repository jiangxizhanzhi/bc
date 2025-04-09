ALTER TABLE `oj_course`
    ADD COLUMN `attack` varchar(2048) NULL,
    ADD COLUMN `defense` varchar(2048) NULL;

ALTER TABLE `oj_practice_operation`
    ADD COLUMN `attack` varchar(2048) NULL,
    ADD COLUMN `defense` varchar(2048) NULL;

ALTER TABLE `oj_practice_theory`
    ADD COLUMN `attack` varchar(2048) NULL,
    ADD COLUMN `defense` varchar(2048) NULL;

ALTER TABLE `cr_eval_algorithm`
    ADD COLUMN `instructions` text         NULL,
    ADD COLUMN `advantage`    text         NULL,
    ADD COLUMN `apply_type`   varchar(255) NULL;

INSERT INTO `cr_constants_config` (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 'a', NULL, 56, '专业能力评估算法', 'Other');
INSERT INTO `cr_constants_config` (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 'b', NULL, 56, '岗位能力评估算法', 'Other');
INSERT INTO `cr_constants_config` (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 'c', NULL, 56, '个人能力评估算法', 'Other');
INSERT INTO `cr_constants_config` (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 'd', NULL, 56, '团队能力评估算法', 'Other');
INSERT INTO `cr_constants_config` (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 'e', NULL, 56, '安全装备防御效能评估算法', 'Other');
INSERT INTO `cr_constants_config` (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 'f', NULL, 56, '安全防御体系防御效能评估算法', 'Other');
INSERT INTO `cr_constants_config` (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 'g', NULL, 56, '入侵检测装备策略有效性评估算法', 'Other');
INSERT INTO `cr_constants_config` (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 'h', NULL, 56, 'Web应用安全装备策略有效性评估算法', 'Other');
INSERT INTO `cr_constants_config` (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 'i', NULL, 56, '防火墙策略有效性评估算法', 'Other');
INSERT INTO `cr_constants_config` (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 'j', NULL, 56, '防病毒装备策略有效性评估算法', 'Other');