ALTER TABLE `space_tactic_defense`
    ADD COLUMN `tmp` tinyint(4) NULL DEFAULT 0 AFTER `builtin`;

ALTER TABLE `space_action_frame_order`
    ADD COLUMN `nodes` text NULL AFTER `sender_name`;