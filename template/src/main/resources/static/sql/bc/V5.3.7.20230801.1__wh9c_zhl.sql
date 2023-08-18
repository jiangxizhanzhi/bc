-- 新增评估模板关联红蓝id
alter table cr_eval_template add red_vs_blue_id varchar(64) NOT NULL COMMENT  '红蓝训练id';
-- 新增评估记录指标分数
alter table cr_eval_record add template_id  varchar(64) NULL DEFAULT NULL COMMENT  '测评id';
alter table cr_eval_record add index_score  int(11) NULL DEFAULT NULL COMMENT  '指标分数';
alter table cr_eval_record add final_score  int(11) NULL DEFAULT NULL COMMENT  '最终分数';


--新增红蓝与当前mp_scene_id表
CREATE TABLE `cr_rb_room_scene`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                               `rb_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '红蓝id',
                               `mp_scene_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '当前红蓝场景id',
                               `update_time` datetime NOT NULL,
                               `create_time` datetime NOT NULL,
                                UNIQUE INDEX `unique_rb_scene_id`(`rb_id`, `mp_scene_id`) USING BTREE,
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

--新增评估记录结果
-- ----------------------------
-- Table structure for cr_eval_result
-- ----------------------------
CREATE TABLE `cr_eval_result`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                               `record` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联评估运行实例id',
                               `weight` int(11) NULL DEFAULT NULL COMMENT '权重',
                               `index_score` int(11) NULL DEFAULT NULL COMMENT '指标分数',
                               `final_score` int(11) NULL DEFAULT NULL COMMENT '分数',
                               `type`  int(11) NOT NULL COMMENT  '评分类型',
                               `update_time` datetime NOT NULL,
                               `create_time` datetime NOT NULL,
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

--新增评估记录导出记录
-- ----------------------------
-- Table structure for cr_eval_export_record
-- ----------------------------
CREATE TABLE `cr_eval_export_record`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                               `record` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联评估运行实例id',
                               `export_file_md5` int(11) NULL DEFAULT NULL COMMENT '导出文件md5',
                               `update_time` datetime NOT NULL,
                               `create_time` datetime NOT NULL,
                                UNIQUE INDEX `unique_record_id`(`record`) USING BTREE,
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- 角色资产归属记录表-过程
CREATE TABLE `cr_role_asset_node_copy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL COMMENT 'uuid',
  `room_id` varchar(64) NOT NULL COMMENT '训练实例id',
  `role_type` int(11) NOT NULL COMMENT '角色类型',
  `device_id` varchar(64) NOT NULL COMMENT '节点id',
  `node_category` int(11) NOT NULL COMMENT '节点类型',
  `original_role_type` int(11) NOT NULL COMMENT '原归属角色类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`),
  KEY `room_id` (`room_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;