INSERT INTO cr_constants_config (`id`, `resource_id`, `code`, `type`, `cn_name`, `en_name`) VALUES (NULL, 'other', NULL, 43, '其他工具', 'Practice');

ALTER TABLE `oj_course_category`
    ADD COLUMN `builtin` tinyint NOT NULL DEFAULT 0;

DELETE FROM `oj_course_category` WHERE `resource_id` = '44c69b28f27a4c109324b27fdc916acd';
DELETE FROM `oj_course_category` WHERE `resource_id` = 'fd32b536340943348a8d52abb0bb00f9';
DELETE FROM `oj_course_category` WHERE `resource_id` = '5eb0b1e5fc8647e5aa25be83d6e6819f';
DELETE FROM `oj_course_category` WHERE `resource_id` = 'f7fd5d9bbbe144b1b3b96eb7036effd1';
DELETE FROM `oj_course_category` WHERE `resource_id` = 'f1121fc48d604c74826d48730749aaf3';

INSERT INTO `oj_course_category` (`resource_id`, `name`, `course_type`, `code`, `update_time`, `create_time`, `parent_id`, `builtin`) VALUES ('f7083ba6d75a4039a95f5a6a8a3de190', '移动安全', 0, 'f7083ba6d75a4039a95f5a6a8a3de190', '2023-09-11 09:01:56', '2023-09-11 09:01:56', NULL, 1);
INSERT INTO `oj_course_category` (`resource_id`, `name`, `course_type`, `code`, `update_time`, `create_time`, `parent_id`, `builtin`) VALUES ('9fb038e595ec42edb9fc998bcec30224', '网络安全', 0, '9fb038e595ec42edb9fc998bcec30224', '2023-09-11 09:01:56', '2023-09-11 09:01:56', NULL, 1);
INSERT INTO `oj_course_category` (`resource_id`, `name`, `course_type`, `code`, `update_time`, `create_time`, `parent_id`, `builtin`) VALUES ('e5d588e17377422abbc3c365a4dd28e8', '操作系统安全', 0, 'e5d588e17377422abbc3c365a4dd28e8', '2023-09-11 09:01:56', '2023-09-11 09:01:56', NULL, 1);
INSERT INTO `oj_course_category` (`resource_id`, `name`, `course_type`, `code`, `update_time`, `create_time`, `parent_id`, `builtin`) VALUES ('1f444c88a734450faa281353df79b941', '数据库安全', 0, '1f444c88a734450faa281353df79b941', '2023-09-11 09:01:56', '2023-09-11 09:01:56', NULL, 1);
INSERT INTO `oj_course_category` (`resource_id`, `name`, `course_type`, `code`, `update_time`, `create_time`, `parent_id`, `builtin`) VALUES ('e7f56223da754aa8836673f8202ce49c', 'Web应用安全', 0, 'e7f56223da754aa8836673f8202ce49c', '2023-09-11 09:01:56', '2023-09-11 09:01:56', NULL, 1);
INSERT INTO `oj_course_category` (`resource_id`, `name`, `course_type`, `code`, `update_time`, `create_time`, `parent_id`, `builtin`) VALUES ('6272c5e258884c40ad95eede732fbbcd', '密码学', 0, '6272c5e258884c40ad95eede732fbbcd', '2023-09-11 09:01:56', '2023-09-11 09:01:56', NULL, 1);
INSERT INTO `oj_course_category` (`resource_id`, `name`, `course_type`, `code`, `update_time`, `create_time`, `parent_id`, `builtin`) VALUES ('e5f9ffac0a9147ec891f8bdaeaeebf95', '规章制度与标准规范', 0, 'e5f9ffac0a9147ec891f8bdaeaeebf95', '2023-09-11 09:01:56', '2023-09-11 09:01:56', NULL, 1);
INSERT INTO `oj_course_category` (`resource_id`, `name`, `course_type`, `code`, `update_time`, `create_time`, `parent_id`, `builtin`) VALUES ('8160f9b662f941e19f4af2590647bd09', '安全管理', 0, '8160f9b662f941e19f4af2590647bd09', '2023-09-11 09:01:56', '2023-09-11 09:01:56', NULL, 1);
INSERT INTO `oj_course_category` (`resource_id`, `name`, `course_type`, `code`, `update_time`, `create_time`, `parent_id`, `builtin`) VALUES ('86489f15a0724851a28c30557c43f5b1', '安全装备', 0, '86489f15a0724851a28c30557c43f5b1', '2023-09-11 09:01:56', '2023-09-11 09:01:56', NULL, 1);
INSERT INTO `oj_course_category` (`resource_id`, `name`, `course_type`, `code`, `update_time`, `create_time`, `parent_id`, `builtin`) VALUES ('4f3814a233b545dba920025a1ad5b1d8', '网络装备', 0, '4f3814a233b545dba920025a1ad5b1d8', '2023-09-11 09:01:56', '2023-09-11 09:01:56', NULL, 1);

INSERT INTO `oj_course_category` (`resource_id`, `name`, `course_type`, `code`, `update_time`, `create_time`, `parent_id`, `builtin`) VALUES ('44c69b28f27a4c109324b27fdc916acd', '安全入门', 0, '44c69b28f27a4c109324b27fdc916acd', '2023-09-11 09:01:56', '2023-09-11 09:01:56', NULL, 1);
INSERT INTO `oj_course_category` (`resource_id`, `name`, `course_type`, `code`, `update_time`, `create_time`, `parent_id`, `builtin`) VALUES ('fd32b536340943348a8d52abb0bb00f9', 'Web安全', 0, 'fd32b536340943348a8d52abb0bb00f9', '2023-09-11 09:01:56', '2023-09-11 09:01:56', NULL, 1);
INSERT INTO `oj_course_category` (`resource_id`, `name`, `course_type`, `code`, `update_time`, `create_time`, `parent_id`, `builtin`) VALUES ('5eb0b1e5fc8647e5aa25be83d6e6819f', '取证分析', 0, '5eb0b1e5fc8647e5aa25be83d6e6819f', '2023-09-11 09:01:56', '2023-09-11 09:01:56', NULL, 1);
INSERT INTO `oj_course_category` (`resource_id`, `name`, `course_type`, `code`, `update_time`, `create_time`, `parent_id`, `builtin`) VALUES ('f7fd5d9bbbe144b1b3b96eb7036effd1', '安全编程', 0, 'f7fd5d9bbbe144b1b3b96eb7036effd1', '2023-09-11 09:01:56', '2023-09-11 09:01:56', NULL, 1);
INSERT INTO `oj_course_category` (`resource_id`, `name`, `course_type`, `code`, `update_time`, `create_time`, `parent_id`, `builtin`) VALUES ('f1121fc48d604c74826d48730749aaf3', '逆向分析', 0, 'f1121fc48d604c74826d48730749aaf3', '2023-09-11 09:01:56', '2023-09-11 09:01:56', NULL, 1);


