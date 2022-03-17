ALTER TABLE `cr_role`
    MODIFY COLUMN `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    MODIFY COLUMN `description` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL;

ALTER TABLE `cr_mission_scheme_template`
    MODIFY COLUMN `description` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL;

ALTER TABLE `cr_rb_room`
    MODIFY COLUMN `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    MODIFY COLUMN `description` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;

ALTER TABLE `cr_team_room`
    MODIFY COLUMN `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    MODIFY COLUMN `description` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;