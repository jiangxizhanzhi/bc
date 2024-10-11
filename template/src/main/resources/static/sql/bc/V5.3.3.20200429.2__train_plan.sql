/** 训练计划表*/
CREATE TABLE `oj_train_plan` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `resource_id` varchar(64) NOT NULL,
                                 `name` varchar(128) DEFAULT NULL,
                                 `create_time` datetime DEFAULT NULL,
                                 `update_time` datetime DEFAULT NULL,
                                 `create_user_id` varchar(64) DEFAULT NULL,
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/** 课程表新增 训练计划字段*/
ALTER TABLE oj_classroom ADD COLUMN train_plan_id VARCHAR(64) DEFAULT NULL;