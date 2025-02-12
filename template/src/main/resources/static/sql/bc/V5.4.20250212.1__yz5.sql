DROP TABLE IF EXISTS `cr_rb_target_command_templates`;
CREATE TABLE `cr_rb_target_command_templates`  (
                                                   `pk` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                   `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ID',
                                                   `role` tinyint(8) NULL DEFAULT NULL COMMENT '角色',
                                                   `main_category` int(8) NULL DEFAULT NULL COMMENT '一级分类',
                                                   `sub_category` int(8) NULL DEFAULT NULL COMMENT '二级分类',
                                                   `third_category` int(8) NULL DEFAULT NULL COMMENT '三级分类',
                                                   `image_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '镜像类型：vm，container,simple, entity',
                                                   `business_tag` int(11) NULL DEFAULT NULL COMMENT '业务标签,1:终端防护靶标,2:路由交换靶标,3:边界安全防护设备靶标,4:流量分析靶标,5:应用服务靶标',
                                                   `brand` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备品牌',
                                                   `command_template` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '命令模板（带占位符）',
                                                   `command_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '命令名称',
                                                   `command_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '命令描述',
                                                   `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
                                                   `update_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间',
                                                   PRIMARY KEY (`pk`) USING BTREE,
                                                   UNIQUE INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12644 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '靶标命令执行模板' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cr_rb_target_command_parameters`;
CREATE TABLE `cr_rb_target_command_parameters`  (
                                                    `pk` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                    `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ID',
                                                    `command_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联的命令模板id',
                                                    `parameter_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数名称',
                                                    `parameter_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数描述',
                                                    `parameter_type` tinyint(2) NULL DEFAULT NULL COMMENT '参数类型(1:String 2:Integer)',
                                                    `default_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '默认值',
                                                    `is_required` tinyint(2) NULL DEFAULT NULL COMMENT '是否必填(1:true 2:false)',
                                                    `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
                                                    `update_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间',
                                                    PRIMARY KEY (`pk`) USING BTREE,
                                                    UNIQUE INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12644 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '靶标命令执行参数' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `cr_rb_target_command_log`;
CREATE TABLE `cr_rb_target_command_log`  (
                                             `pk` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                             `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'id',
                                             `node_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '节点id',
                                             `scene_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场景id',
                                             `protocol` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行协议',
                                             `command_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行人',
                                             `command_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '执行的命令',
                                             `excuted_time` datetime(0) NULL DEFAULT NULL COMMENT '执行时间',
                                             `status` tinyint(2) NULL DEFAULT NULL COMMENT '执行状态(0:失败 1:成功)',
                                             `result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '执行结果日志',
                                             `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                             `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
                                             PRIMARY KEY (`pk`) USING BTREE,
                                             UNIQUE INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12647 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '靶标命令执行结果日志' ROW_FORMAT = Dynamic;