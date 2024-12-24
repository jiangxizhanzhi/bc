CREATE TABLE `cr_eval_mission_template_log`  (
                                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                                 `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
                                                 `user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户',
                                                 `operate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作',
                                                 `mission_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务id',
                                                 `mission_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名称',
                                                 `task_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联模板任务id',
                                                 `create_user_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人id',
                                                 `create_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
                                                 `update_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
                                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

ALTER TABLE `cr_eval_mission_template_log`
    ADD COLUMN `mission_result` text NULL COMMENT '任务结果' AFTER `update_time`;