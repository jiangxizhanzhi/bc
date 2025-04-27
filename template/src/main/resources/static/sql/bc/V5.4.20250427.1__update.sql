ALTER TABLE `space_action_frame`
ADD COLUMN `key_words` varchar(255) NULL;

UPDATE `space_action_frame` SET `key_words` = '["篡改"]' WHERE `resource_id` = '87e630b3b5924a3d9b8307cd924609a2';
UPDATE `space_action_frame` SET `key_words` = '["Beacon", "beacon"]' WHERE `resource_id` = 'fceca45b0be24e73b8c06384470c172b';
UPDATE `space_action_frame` SET `key_words` = '["授权", "网络隔离", "权限", "敏感接口"]' WHERE `resource_id` = '7b573d22c9f9485ca5bf0552dead8336';