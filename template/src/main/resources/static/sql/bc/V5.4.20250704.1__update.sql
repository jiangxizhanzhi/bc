ALTER TABLE `space_action_frame`
    ADD COLUMN `builtin` tinyint(1) NOT NULL DEFAULT 0 AFTER `analyze`;