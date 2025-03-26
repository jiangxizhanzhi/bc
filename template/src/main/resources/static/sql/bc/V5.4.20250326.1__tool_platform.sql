INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 1, 55, 'Windows', 'Windows');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 2, 55, 'Linux', 'Linux');
INSERT INTO `cr_constants_config` VALUES (NULL, NULL, 3, 55, 'Others', 'Others');

UPDATE `cr_tool_tool` SET `platforms` = TRIM(BOTH ',' FROM REPLACE(REPLACE(CONCAT(',', platforms, ','), ',Mac,', ','), ',,', ',')) WHERE platforms LIKE '%Mac%';
UPDATE `cr_tool_tool` SET `platforms` = TRIM(BOTH ',' FROM REPLACE(REPLACE(CONCAT(',', platforms, ','), ',macOS,', ','), ',,', ',')) WHERE platforms LIKE '%macOS%';
UPDATE `cr_tool_tool` SET `platforms` = TRIM(BOTH ',' FROM REPLACE(REPLACE(CONCAT(',', platforms, ','), ',IOS,', ','), ',,', ',')) WHERE platforms LIKE '%IOS%';
UPDATE `cr_tool_tool` SET `platforms` = TRIM(BOTH ',' FROM REPLACE(REPLACE(CONCAT(',', platforms, ','), ',Android,', ','), ',,', ',')) WHERE platforms LIKE '%Android%';
UPDATE `cr_tool_tool` SET `platforms` = TRIM(BOTH ',' FROM REPLACE(REPLACE(CONCAT(',', platforms, ','), ',android,', ','), ',,', ',')) WHERE platforms LIKE '%android%';
