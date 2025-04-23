ALTER TABLE `space_action_frame_order`
ADD UNIQUE INDEX `app_step_tactic`(`application_id`, `step`, `tactic_id`) USING BTREE;

ALTER TABLE `space_action_frame_application`
MODIFY COLUMN `tag` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
MODIFY COLUMN `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL;

ALTER TABLE `space_action_frame`
MODIFY COLUMN `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL;