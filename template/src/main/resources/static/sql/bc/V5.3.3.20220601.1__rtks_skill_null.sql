ALTER TABLE `rtks_skill`
    MODIFY COLUMN `system_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
    MODIFY COLUMN `category_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
    MODIFY COLUMN `courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '[]';