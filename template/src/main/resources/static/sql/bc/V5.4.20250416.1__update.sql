UPDATE `space_apt_threat_detect_rule` SET `type`='后门' WHERE `type`='backdoor';
UPDATE `space_apt_threat_detect_rule` SET `type`='证书' WHERE `type`='certificate';
UPDATE `space_apt_threat_detect_rule` SET `type`='下载器' WHERE `type`='downloader';
UPDATE `space_apt_threat_detect_rule` SET `type`='勒索软件' WHERE `type`='ransomware';
UPDATE `space_apt_threat_detect_rule` SET `type`='木马' WHERE `type`='trojan';
UPDATE `space_apt_threat_detect_rule` SET `type`='病毒' WHERE `type`='virus';
UPDATE `space_apt_threat_detect_rule` SET `type`='间谍软件' WHERE `type`='spyware';
UPDATE `space_apt_threat_detect_rule` SET `type`='蠕虫' WHERE `type`='wom';
UPDATE `space_apt_threat_detect_rule` SET `type`='宏病毒' WHERE `type`='macro virus';
UPDATE `space_apt_threat_detect_rule` SET `type`='恶意软件' WHERE `type`='malware';


ALTER TABLE `space_loaded_file_threat_rule`
    ADD COLUMN `stage` tinyint DEFAULT 3;

ALTER TABLE `space_event_integration_rule`
    ADD COLUMN `stage` tinyint DEFAULT 3;

ALTER TABLE `space_traffic_detect_rule`
    ADD COLUMN `stage` tinyint DEFAULT 3,
    ADD COLUMN `attack_type` tinyint;


INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 57, '模型', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 57, '加固', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 57, '检测', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 4, 57, '隔离', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 5, 57, '欺骗', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 6, 57, '驱逐', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 7, 57, '恢复', '');

INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 58, '异常流量', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 58, '未授权访问', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 58, '其他', '');