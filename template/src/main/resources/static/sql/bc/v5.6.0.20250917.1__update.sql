CREATE TABLE `course_lesson_user_relation` (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `resource_id` varchar(64) NOT NULL,
                           `course_id` varchar(64) NOT NULL COMMENT '课程id',
                           `lesson_id` varchar(64) NOT NULL COMMENT '课时id',
                           `user_id` varchar(64) NOT NULL COMMENT '用户id',
                           `course_name` varchar(64) NOT NULL COMMENT '课程名称',
                           `scored_flag` tinyint(1) NOT NULL default 0 COMMENT '是否打分',
                           `update_time` datetime NOT NULL COMMENT '更新时间',
                           `create_time` datetime NOT NULL COMMENT '创建时间',
                           PRIMARY KEY (`id`) USING BTREE,
                           UNIQUE KEY (`resource_id`),
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT='课程-课时-用户关系记录';
