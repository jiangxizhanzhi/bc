CREATE TABLE `ai_user_profile_report` (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `resource_id` varchar(64) NOT NULL,
                           `user_id` varchar(64) NOT NULL COMMENT '用户编号',
                           `dimension` int(11) NOT NULL COMMENT '1=课程学习 2=题目练习 3=综合训练 4=岗位评估',
                           `name` varchar(128) NOT NULL COMMENT '报告名称',
                           `content` longtext DEFAULT NULL COMMENT '报告内容',
                           `update_time` datetime NOT NULL COMMENT '更新时间',
                           `create_time` datetime NOT NULL COMMENT '创建时间',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT='智能体-用户画像报告';