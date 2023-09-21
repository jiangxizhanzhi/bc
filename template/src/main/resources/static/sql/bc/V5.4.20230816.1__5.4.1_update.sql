ALTER TABLE cr_rb_time_management ADD COLUMN length int(11) NOT NULL COMMENT '已记录训练时长';
ALTER TABLE cr_rb_time_management ADD COLUMN recover_time datetime(6) not null comment '恢复时间';
ALTER TABLE cr_rb_time_management ADD COLUMN end_time datetime(6) null comment '结束时间';

CREATE TABLE `situation_guide_scheme` (
                                          `id` INT ( 11 ) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                          `resource_id` VARCHAR ( 32 ) NOT NULL COMMENT '资源id',
                                          `room_id` VARCHAR ( 32 ) DEFAULT NULL COMMENT '房间id',
                                          `business_type` INT ( 11 ) NOT NULL COMMENT '训练类型 3:团队 4:红蓝',
                                          `event_name` VARCHAR ( 255 ) DEFAULT NULL COMMENT '事件名称',
                                          `type` INT ( 11 ) NOT NULL COMMENT '类型1:任务导调 2:手动导调',
                                          `event_type` INT ( 11 ) NOT NULL COMMENT '事件类型',
                                          `category` INT ( 11 ) DEFAULT NULL COMMENT '攻防类型',
                                          `source` VARCHAR ( 128 ) DEFAULT NULL COMMENT '攻击源',
                                          `target` VARCHAR ( 128 ) DEFAULT NULL COMMENT '攻击目标',
                                          `result` INT ( 11 ) DEFAULT NULL COMMENT '攻击结果 1:成功 2:失败',
                                          `role` INT ( 11 ) DEFAULT NULL COMMENT '所属角色 4:红方 5:蓝方',
                                          `mission_id` VARCHAR ( 32 ) DEFAULT NULL COMMENT '任务id',
                                          `trigger_condition` INT ( 11 ) DEFAULT NULL COMMENT '触发条件1:任务提交 2:任务判断成功',
                                          `create_time` datetime NOT NULL COMMENT '创建时间',
                                          `update_time` datetime NOT NULL COMMENT '更新时间',
                                          PRIMARY KEY ( `id` ) USING BTREE,
                                          UNIQUE KEY `resource_id` ( `resource_id` ) USING BTREE,
                                          KEY `room_id` ( `room_id` ) USING BTREE,
                                          UNIQUE  KEY `mission_id` ( `mission_id` ) USING BTREE
) ENGINE = INNODB DEFAULT CHARSET = utf8 ROW_FORMAT = DYNAMIC COMMENT = '态势导调方案表';

CREATE TABLE `situation_guide_history` (
                                           `id` INT ( 11 ) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                           `resource_id` VARCHAR ( 32 ) NOT NULL COMMENT '资源id',
                                           `scene_id` VARCHAR ( 32 ) DEFAULT NULL COMMENT '场景id',
                                           `room_id` VARCHAR ( 32 ) DEFAULT NULL COMMENT '房间id',
                                           `guide_id` varchar(32) DEFAULT NULL COMMENT '导调id',
                                           `business_type` INT ( 11 ) NOT NULL COMMENT '训练类型 3:团队 4:红蓝',
                                           `event_name` VARCHAR ( 255 ) DEFAULT NULL COMMENT '事件名称',
                                           `type` INT ( 11 ) NOT NULL COMMENT '类型1:任务导调 2:手动导调',
                                           `event_type` INT ( 11 ) NOT NULL COMMENT '事件类型',
                                           `category` INT ( 11 ) DEFAULT NULL COMMENT '攻防类型',
                                           `source` VARCHAR ( 128 ) DEFAULT NULL COMMENT '攻击源',
                                           `target` VARCHAR ( 128 ) DEFAULT NULL COMMENT '攻击目标',
                                           `result` INT ( 11 ) DEFAULT NULL COMMENT '攻击结果 1:成功 2失败',
                                           `mission_id` VARCHAR ( 32 ) DEFAULT NULL COMMENT '任务id',
                                           `task_id` VARCHAR ( 32 ) DEFAULT NULL COMMENT '下发任务id',
                                           `role` INT ( 11 ) DEFAULT NULL COMMENT '所属角色 4:红方 5:蓝方',
                                           `trigger_condition` INT ( 11 ) DEFAULT NULL COMMENT '触发条件1:任务提交 2:任务判断成功',
                                           `create_time` datetime NOT NULL COMMENT '创建时间',
                                           `update_time` datetime NOT NULL COMMENT '更新时间',
                                           PRIMARY KEY ( `id` ) USING BTREE,
                                           UNIQUE KEY `resource_id` ( `resource_id` ) USING BTREE,
                                           KEY `room_id` ( `room_id` ) USING BTREE,
                                           KEY `guide_id` ( `guide_id` ) USING BTREE
) ENGINE = INNODB DEFAULT CHARSET = utf8 ROW_FORMAT = DYNAMIC COMMENT = '态势导调历史记录表';

-- 习题集封面 --
insert into oj_practice_set
(`id`, `resource_id`, `builtin`, `practice_type`, `name`, `logo`, `public`, `create_user_id`, `operation_auth_type`, `operation_auth_ids`, `read_auth_type`, `read_auth_ids`, `create_time`, `update_time`, `practice_count`)
    value (DEFAULT, '584c596b5a8e481facb94189f5ccf224', 1, 2, 'MISC小白练习集', '/cpms/built_in/d70ce24a65d404f83902d30ba9170141.png', 1, '3f655d87112f4ae094bbaf70830e20aa', 1, '[]', 1, '[]', NOW(), NOW(), 0);
insert into oj_practice_set
(`id`, `resource_id`, `builtin`, `practice_type`, `name`, `logo`, `public`, `create_user_id`, `operation_auth_type`, `operation_auth_ids`, `read_auth_type`, `read_auth_ids`, `create_time`, `update_time`, `practice_count`)
    value (DEFAULT, '66220449b9134c8186f56f7cdd9a4b71', 1, 2, 'mobile之破解APK', '/cpms/built_in/087f055159acb93d61c76af7dce6c247.png', 1, '3f655d87112f4ae094bbaf70830e20aa', 1, '[]', 1, '[]', NOW(), NOW(), 0);
insert into oj_practice_set
(`id`, `resource_id`, `builtin`, `practice_type`, `name`, `logo`, `public`, `create_user_id`, `operation_auth_type`, `operation_auth_ids`, `read_auth_type`, `read_auth_ids`, `create_time`, `update_time`, `practice_count`)
    value (DEFAULT, '7395331dcce742c3beb1ac49264cdbaf', 1, 2, 'web新手入门', '/cpms/built_in/868a5880f98a398ffb3baebbd143669a.png', 1, '3f655d87112f4ae094bbaf70830e20aa', 1, '[]', 1, '[]', NOW(), NOW(), 0);
insert into oj_practice_set
(`id`, `resource_id`, `builtin`, `practice_type`, `name`, `logo`, `public`, `create_user_id`, `operation_auth_type`, `operation_auth_ids`, `read_auth_type`, `read_auth_ids`, `create_time`, `update_time`, `practice_count`)
    value (DEFAULT, 'c357afe336ce48418d4d9ca77a1a3cbe', 1, 2, 'windows pwn专题', '/cpms/built_in/bd6c7609de1cb381ca85d7b77271e06d.png', 1, '3f655d87112f4ae094bbaf70830e20aa', 1, '[]', 1, '[]', NOW(), NOW(), 0);
insert into oj_practice_set
(`id`, `resource_id`, `builtin`, `practice_type`, `name`, `logo`, `public`, `create_user_id`, `operation_auth_type`, `operation_auth_ids`, `read_auth_type`, `read_auth_ids`, `create_time`, `update_time`, `practice_count`)
    value (DEFAULT, '32a7da91927d46afa92e1ac60707d263', 1, 2, '密码中的奥秘', '/cpms/built_in/395e346fb897f168897f0ae10c16dd82.png', 1, '3f655d87112f4ae094bbaf70830e20aa', 1, '[]', 1, '[]', NOW(), NOW(), 0);
insert into oj_practice_set
(`id`, `resource_id`, `builtin`, `practice_type`, `name`, `logo`, `public`, `create_user_id`, `operation_auth_type`, `operation_auth_ids`, `read_auth_type`, `read_auth_ids`, `create_time`, `update_time`, `practice_count`)
    value (DEFAULT, '8169e1626c0b4319881706ab40d72536', 1, 2, '逆向高手进阶', '/cpms/built_in/2cb98e3c05ed82b14f14bd6669d657c8.png', 1, '3f655d87112f4ae094bbaf70830e20aa', 1, '[]', 1, '[]', NOW(), NOW(), 0);
insert into oj_practice_set
(`id`, `resource_id`, `builtin`, `practice_type`, `name`, `logo`, `public`, `create_user_id`, `operation_auth_type`, `operation_auth_ids`, `read_auth_type`, `read_auth_ids`, `create_time`, `update_time`, `practice_count`)
    value (DEFAULT, '5a2203da9adf4bb48d81baee4fb751df', 1, 0, 'Linux操作系统安全', '/cpms/built_in/67688023345274f80db7955cbe9c795b.png', 1, '3f655d87112f4ae094bbaf70830e20aa', 1, '[]', 1, '[]', NOW(), NOW(), 0);
insert into oj_practice_set
(`id`, `resource_id`, `builtin`, `practice_type`, `name`, `logo`, `public`, `create_user_id`, `operation_auth_type`, `operation_auth_ids`, `read_auth_type`, `read_auth_ids`, `create_time`, `update_time`, `practice_count`)
    value (DEFAULT, '64e2f15f8c7348bb949615a5993838b7', 1, 0, 'windows操作系统安全', '/cpms/built_in/35d21862e14216c24adf371a3bc80471.png', 1, '3f655d87112f4ae094bbaf70830e20aa', 1, '[]', 1, '[]', NOW(), NOW(), 0);
insert into oj_practice_set
(`id`, `resource_id`, `builtin`, `practice_type`, `name`, `logo`, `public`, `create_user_id`, `operation_auth_type`, `operation_auth_ids`, `read_auth_type`, `read_auth_ids`, `create_time`, `update_time`, `practice_count`)
    value (DEFAULT, '48bbfebb649441dab66ec89a8978b98e', 1, 0, '安全设备运维', '/cpms/built_in/c60f5d351e56819062fd20ffb280fca7.png', 1, '3f655d87112f4ae094bbaf70830e20aa', 1, '[]', 1, '[]', NOW(), NOW(), 0);
insert into oj_practice_set
(`id`, `resource_id`, `builtin`, `practice_type`, `name`, `logo`, `public`, `create_user_id`, `operation_auth_type`, `operation_auth_ids`, `read_auth_type`, `read_auth_ids`, `create_time`, `update_time`, `practice_count`)
    value (DEFAULT, '4ef4a5d6d1aa460e87ad29738ed1287f', 1, 0, '密码测评', '/cpms/built_in/ee060a37073490952dee92af1ed745e2.png', 1, '3f655d87112f4ae094bbaf70830e20aa', 1, '[]', 1, '[]', NOW(), NOW(), 0);
insert into oj_practice_set
(`id`, `resource_id`, `builtin`, `practice_type`, `name`, `logo`, `public`, `create_user_id`, `operation_auth_type`, `operation_auth_ids`, `read_auth_type`, `read_auth_ids`, `create_time`, `update_time`, `practice_count`)
    value (DEFAULT, 'e687cf39c4534399b4b4f9d6b5200f54', 1, 0, '数据加密与解密', '/cpms/built_in/843866b0b31525fe19dcd0aaa426a6f4.png', 1, '3f655d87112f4ae094bbaf70830e20aa', 1, '[]', 1, '[]', NOW(), NOW(), 0);
insert into oj_practice_set
(`id`, `resource_id`, `builtin`, `practice_type`, `name`, `logo`, `public`, `create_user_id`, `operation_auth_type`, `operation_auth_ids`, `read_auth_type`, `read_auth_ids`, `create_time`, `update_time`, `practice_count`)
    value (DEFAULT, '1606526a65fa4d96ad17682d6102fcbe', 1, 0, '等级保护', '/cpms/built_in/8f09a0a674df2c60cecf6ff07455ba53.png', 1, '3f655d87112f4ae094bbaf70830e20aa', 1, '[]', 1, '[]', NOW(), NOW(), 0);
insert into oj_practice_set
(`id`, `resource_id`, `builtin`, `practice_type`, `name`, `logo`, `public`, `create_user_id`, `operation_auth_type`, `operation_auth_ids`, `read_auth_type`, `read_auth_ids`, `create_time`, `update_time`, `practice_count`)
    value (DEFAULT, 'ee3fc36981fe45ee93486f66f7da5cd1', 1, 0, '综合训练', '/cpms/built_in/c8cbe36d4d5f67e706066796b5ec0bae.png', 1, '3f655d87112f4ae094bbaf70830e20aa', 1, '[]', 1, '[]', NOW(), NOW(), 0);
insert into oj_practice_set
(`id`, `resource_id`, `builtin`, `practice_type`, `name`, `logo`, `public`, `create_user_id`, `operation_auth_type`, `operation_auth_ids`, `read_auth_type`, `read_auth_ids`, `create_time`, `update_time`, `practice_count`)
    value (DEFAULT, '84fe54a3c9b44747baca92ab0db27651', 1, 0, '网络协议', '/cpms/built_in/813593fa19f82e0bb193bb42a7d9c556.png', 1, '3f655d87112f4ae094bbaf70830e20aa', 1, '[]', 1, '[]', NOW(), NOW(), 0);
insert into oj_practice_set
(`id`, `resource_id`, `builtin`, `practice_type`, `name`, `logo`, `public`, `create_user_id`, `operation_auth_type`, `operation_auth_ids`, `read_auth_type`, `read_auth_ids`, `create_time`, `update_time`, `practice_count`)
    value (DEFAULT, '6ab2c73f6dd64707b645868a70660dfa', 1, 0, '防火墙技术', '/cpms/built_in/dbda9927598efeb0e3ddbfc97e7f2a0c.png', 1, '3f655d87112f4ae094bbaf70830e20aa', 1, '[]', 1, '[]', NOW(), NOW(), 0);

-- 行业态势
CREATE TABLE `cr_mission_situation`
(
    `id`                  int(11)      NOT NULL AUTO_INCREMENT,
    `mission_template_id` varchar(32)  NOT NULL COMMENT '任务模板名称',
    `name`                varchar(255) NOT NULL COMMENT '任务名称',
    `ts_id`               int(11)      NOT NULL COMMENT '动效id',
    `ts_type`             varchar(255) NOT NULL COMMENT '动效类型',
    `ts_desc`             varchar(255) NOT NULL COMMENT '动效描述',
    `target_device`       text         NOT NULL COMMENT '动效目标节点',
    `source_device`       varchar(255) NULL DEFAULT NULL COMMENT '源目标',
    `area`                varchar(255) NOT NULL COMMENT '靶标区域',
    `extra_effect`        varchar(255) NULL DEFAULT NULL COMMENT '额外的效果',
    `create_time`         datetime     NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`         datetime     NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `mission_template_id` (`mission_template_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- TeamTemplate
ALTER TABLE `cr_team_template`
    ADD COLUMN `situation_config` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '态势配置' AFTER `duration_time`;
-- TeamRoom
ALTER TABLE `cr_team_room`
    ADD COLUMN `area_path` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '攻击路径';


ALTER TABLE `cr_rb_room` ADD COLUMN `scene_id_record` varchar(255) null comment '训练过程中的所有场景id';

ALTER TABLE `cr_team_room` ADD COLUMN `scene_id_record` varchar(255) null comment '训练过程中的所有场景id';