ALTER TABLE `bs_user_model`
    ADD COLUMN `user_num` int NOT NULL DEFAULT 1 COMMENT '用户人数';
ALTER TABLE `bs_user_instance`
    ADD COLUMN `user_num` int NOT NULL DEFAULT 1 COMMENT '用户人数';

DELETE FROM `cr_constants_config` WHERE type = 41;
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 41, 'Web访问', 'Web访问');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 41, 'FTP访问', 'FTP访问');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 41, '社交网络访问', '社交网络访问');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 4, 41, '流媒体访问', '流媒体访问');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 5, 41, '邮件收发', '邮件收发');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 6, 41, '网络游戏', '网络游戏');