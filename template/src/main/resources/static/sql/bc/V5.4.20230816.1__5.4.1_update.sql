ALTER TABLE cr_rb_time_management ADD COLUMN length int(11) NOT NULL COMMENT '已记录训练时长';
ALTER TABLE cr_rb_time_management ADD COLUMN recover_time datetime(6) not null comment '恢复时间';
ALTER TABLE cr_rb_time_management ADD COLUMN end_time datetime(6) null comment '结束时间';


CREATE TABLE `situation_guide_scheme` (
                                          `id` INT ( 11 ) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                          `resource_id` VARCHAR ( 32 ) NOT NULL COMMENT '资源id',
                                          `scene_id` VARCHAR ( 32 ) DEFAULT NULL COMMENT '场景id',
                                          `name` VARCHAR ( 255 ) DEFAULT NULL COMMENT '事件名称',
                                          `type` INT ( 11 ) NOT NULL COMMENT '类型1:任务导调 2:手动导调',
                                          `event_type` INT ( 11 ) NOT NULL COMMENT '事件类型',
                                          `category` INT ( 11 ) DEFAULT NULL COMMENT '攻防类型',
                                          `source` VARCHAR ( 32 ) DEFAULT NULL COMMENT '攻击源',
                                          `target` VARCHAR ( 32 ) DEFAULT NULL COMMENT '攻击目标',
                                          `result` INT ( 11 ) DEFAULT NULL COMMENT '攻击结果 1:成功 2失败',
                                          `mission_id` VARCHAR ( 32 ) DEFAULT NULL COMMENT '任务id',
                                          `condition` INT ( 11 ) DEFAULT NULL COMMENT '触发条件1:任务提交 2:任务判断成功',
                                          `create_time` datetime NOT NULL COMMENT '创建时间',
                                          `update_time` datetime NOT NULL COMMENT '更新时间',
                                          PRIMARY KEY ( `id` ) USING BTREE,
                                          UNIQUE KEY `resource_id` ( `resource_id` ) USING BTREE,
                                          KEY `scene_id` ( `scene_id` ) USING BTREE
) ENGINE = INNODB DEFAULT CHARSET = utf8 ROW_FORMAT = DYNAMIC COMMENT = '态势导调方案表';

CREATE TABLE `situation_guide_history` (
                                           `id` INT ( 11 ) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                           `resource_id` VARCHAR ( 32 ) NOT NULL COMMENT '资源id',
                                           `scene_id` VARCHAR ( 32 ) DEFAULT NULL COMMENT '场景id',
                                           `name` VARCHAR ( 255 ) DEFAULT NULL COMMENT '事件名称',
                                           `type` INT ( 11 ) NOT NULL COMMENT '类型1:任务导调 2:手动导调',
                                           `event_type` INT ( 11 ) NOT NULL COMMENT '事件类型',
                                           `category` INT ( 11 ) DEFAULT NULL COMMENT '攻防类型',
                                           `source` VARCHAR ( 32 ) DEFAULT NULL COMMENT '攻击源',
                                           `target` VARCHAR ( 32 ) DEFAULT NULL COMMENT '攻击目标',
                                           `result` INT ( 11 ) DEFAULT NULL COMMENT '攻击结果 1:成功 2失败',
                                           `mission_id` VARCHAR ( 32 ) DEFAULT NULL COMMENT '任务id',
                                           `condition` INT ( 11 ) DEFAULT NULL COMMENT '触发条件1:任务提交 2:任务判断成功',
                                           `create_time` datetime NOT NULL COMMENT '创建时间',
                                           `update_time` datetime NOT NULL COMMENT '更新时间',
                                           PRIMARY KEY ( `id` ) USING BTREE,
                                           UNIQUE KEY `resource_id` ( `resource_id` ) USING BTREE,
                                           KEY `scene_id` ( `scene_id` ) USING BTREE
) ENGINE = INNODB DEFAULT CHARSET = utf8 ROW_FORMAT = DYNAMIC COMMENT = '态势导调历史记录表';