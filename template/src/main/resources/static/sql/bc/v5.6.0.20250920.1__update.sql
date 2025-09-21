CREATE TABLE `oj_train_queue` (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `resource_id` varchar(64) NOT NULL,
                           `room_id` varchar(64) NOT NULL COMMENT '排队/占用的房间id',
                           `room_name` varchar(64) NOT NULL COMMENT '房间名称',
                           `entity_device_id` varchar(64) NOT NULL COMMENT '实体设备资源id',
                           `apply_time` datetime NOT NULL COMMENT '申请时间',
                           `status` tinyint(4) NOT NULL COMMENT '状态：0-排队中 1-使用中 2取消';
                           `operator_user_id` varchar(64) COMMENT '操作人id`
                           `update_time` datetime NOT NULL COMMENT '更新时间',
                           `create_time` datetime NOT NULL COMMENT '创建时间',
                           PRIMARY KEY (`id`) USING BTREE,
                           UNIQUE KEY (`resource_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT='训练场景启动排队表';