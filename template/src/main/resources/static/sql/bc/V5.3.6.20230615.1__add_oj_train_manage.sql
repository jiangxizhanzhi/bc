SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `oj_train_manage` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `resource_id` varchar(64) NOT NULL COMMENT '资源id',
    `name` varchar(128) NOT NULL COMMENT '名称',
    `parent` varchar(64) DEFAULT NULL COMMENT '父级id',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `course_ids` text COMMENT '课程ids',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT='oj课程培训管理表';

SET FOREIGN_KEY_CHECKS = 1;