CREATE TABLE `rtks_situation_log`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `user_id`     varchar(32)  NOT NULL,
    `user_name`   varchar(255) NOT NULL,
    `role_id`     varchar(32)  NOT NULL,
    `role_name`   varchar(255) NOT NULL,
    `type`        tinyint(1)   NOT NULL,
    `create_time` datetime     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `user_role_type` (`user_id`, `role_id`, `type`) USING BTREE
);


CREATE TABLE `rtks_role_match_record`
(
    `id`          int(11)     NOT NULL AUTO_INCREMENT,
    `user_id`     varchar(32) NOT NULL,
    `role_id`     varchar(32) NOT NULL,
    `match`       tinyint(4)  NOT NULL,
    `create_time` datetime    NULL,
    `update_time` datetime    NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `user_role` (`user_id`, `role_id`) USING BTREE,
    INDEX `role_id` (`role_id`) USING BTREE
);

ALTER TABLE `cr_mission_template`
    ADD INDEX `template_id` (`template_id`) USING BTREE,
    ADD INDEX `business_template_id` (`business_template_id`) USING BTREE;

ALTER TABLE `cr_mission`
    ADD UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE,
    ADD INDEX `room_id` (`room_id`) USING BTREE,
    ADD INDEX `mission_template_id` (`mission_template_id`) USING BTREE;

ALTER TABLE `cr_train_record`
    ADD INDEX `room_id` (`room_id`) USING BTREE;

ALTER TABLE `attachment`
    ADD UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE;

ALTER TABLE `cr_rb_device`
    ADD UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE,
    ADD UNIQUE INDEX `room_device_user` (`room_id`, `device_id`, `create_user_id`) USING BTREE;

ALTER TABLE `cr_rb_time_management`
    ADD UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE,
    ADD UNIQUE INDEX `room_id` (`room_id`) USING BTREE;

ALTER TABLE `cr_score`
    ADD UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE,
    ADD UNIQUE INDEX `room_user` (`room_id`, `user_id`) USING BTREE;


ALTER TABLE `rtks_engage_category`
    ADD UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE;

ALTER TABLE `rtks_role_related`
    ADD INDEX `role_id` (`role_id`) USING BTREE;

ALTER TABLE `situation_attack_message`
    ADD UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE,
    ADD INDEX `scene_id` (`scene_id`) USING BTREE;

ALTER TABLE `situation_config`
    ADD UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE;

ALTER TABLE `situation_dynamic_scheme`
    ADD UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE,
    ADD INDEX `scene_id` (`scene_id`) USING BTREE;

ALTER TABLE `situation_scene_config`
    ADD UNIQUE INDEX `scene_id` (`scene_id`) USING BTREE;
