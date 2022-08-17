ALTER TABLE  `attachment`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `file_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名称' AFTER `resource_id`,
    MODIFY COLUMN `file_path` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件路径' AFTER `file_name`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `file_path`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    MODIFY COLUMN `public` int NOT NULL COMMENT '是否公开' AFTER `update_time`,
    MODIFY COLUMN `business_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务id' AFTER `public`,
    COMMENT = '附件表';


ALTER TABLE `init_rtks_course_related`
    MODIFY COLUMN `type` int NOT NULL COMMENT '类型：1：知识点;2：技能点（已取消）' AFTER `related_id`,
    COMMENT = 'rtks知识点课程关联重置表';

ALTER TABLE `init_rtks_knowledge`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `category_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别id' AFTER `resource_id`,
    MODIFY COLUMN `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容' AFTER `category_id`,
    MODIFY COLUMN `courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联课程列表' AFTER `content`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `courses`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks知识点重置表';


ALTER TABLE `init_rtks_knowledge_category`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容' AFTER `resource_id`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `content`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks知识点类别重置表';



ALTER TABLE `init_rtks_level`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称' AFTER `resource_id`,
    MODIFY COLUMN `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述' AFTER `name`,
    MODIFY COLUMN `parent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级id' AFTER `description`,
    MODIFY COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `parent`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    MODIFY COLUMN `frame` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '框架id' AFTER `update_time`,
    COMMENT = 'rtks框架层级重置表';



ALTER TABLE `init_rtks_role`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称' AFTER `resource_id`,
    MODIFY COLUMN `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述' AFTER `name`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `description`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    MODIFY COLUMN `level_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '层级id' AFTER `update_time`,
    MODIFY COLUMN `category` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别' AFTER `level_id`,
    MODIFY COLUMN `task_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务列表' AFTER `category`,
    MODIFY COLUMN `knowledge_id_weights` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点权重列表' AFTER `task_ids`,
    MODIFY COLUMN `knowledge_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点列表' AFTER `knowledge_id_weights`,
    MODIFY COLUMN `skill_id_weights` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能点权重列表' AFTER `knowledge_ids`,
    MODIFY COLUMN `skill_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能点列表' AFTER `skill_id_weights`,
    MODIFY COLUMN `ability_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '能力列表' AFTER `skill_ids`,
    MODIFY COLUMN `skill_courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '技能课程列表' AFTER `ability_ids`,
    MODIFY COLUMN `knowledge_courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '知识点课程列表' AFTER `skill_courses`,
    MODIFY COLUMN `knowledge_weight` tinyint NOT NULL DEFAULT 50 COMMENT '知识点比例' AFTER `knowledge_courses`,
    MODIFY COLUMN `skill_missions` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '技能任务列表' AFTER `knowledge_weight`,
    COMMENT = 'rtks角色重置表';



ALTER TABLE `init_rtks_task`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容' AFTER `resource_id`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `content`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks任务重置表';


ALTER TABLE  `local_scene`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `mp_scene_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中台场景id' AFTER `resource_id`,
    MODIFY COLUMN `business_code` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务码' AFTER `mp_scene_id`,
    MODIFY COLUMN `start_time` datetime NULL DEFAULT NULL COMMENT '场景启动时间' AFTER `business_code`,
    MODIFY COLUMN `end_time` datetime NULL DEFAULT NULL COMMENT '场景停止时间' AFTER `start_time`,
    MODIFY COLUMN `destroy_time` datetime NULL DEFAULT NULL COMMENT '自动调用销毁时间' AFTER `end_time`,
    MODIFY COLUMN `destroy_mode` int NULL DEFAULT NULL COMMENT '销毁模式：0=手动；1=自动' AFTER `destroy_time`,
    MODIFY COLUMN `business_instance_id` varchar(65) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务id' AFTER `destroy_mode`,
    MODIFY COLUMN `scene_status` int NULL DEFAULT NULL COMMENT '场景状态' AFTER `business_instance_id`,
    MODIFY COLUMN `pause_time` datetime NULL DEFAULT NULL COMMENT '场景暂停时间' AFTER `scene_status`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `pause_time`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    MODIFY COLUMN `topo_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拓扑Id' AFTER `update_time`,
    MODIFY COLUMN `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id' AFTER `topo_id`,
    MODIFY COLUMN `scene_ttl` int NULL DEFAULT NULL COMMENT '场景存活时间' AFTER `user_id`,
    MODIFY COLUMN `node_count` int NULL DEFAULT NULL COMMENT '节点数量' AFTER `scene_ttl`,
    MODIFY COLUMN `meta` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'json格式数据' AFTER `node_count`,
    MODIFY COLUMN `scene_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场景名' AFTER `meta`,
    MODIFY COLUMN `scene_attr` int NULL DEFAULT NULL COMMENT '场景类型' AFTER `scene_name`,
    COMMENT = '本地场景表';

ALTER TABLE `rtks_ability`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容' AFTER `resource_id`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `content`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks能力表';

ALTER TABLE `rtks_attack`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `category_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别id' AFTER `resource_id`,
    MODIFY COLUMN `name_zh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文内容' AFTER `category_id`,
    MODIFY COLUMN `name_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '英文内容' AFTER `name_zh`,
    COMMENT = 'att&ck框架表';

ALTER TABLE `rtks_attack_category`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `name_zh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文内容' AFTER `resource_id`,
    MODIFY COLUMN `name_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '英文内容' AFTER `name_zh`,
    COMMENT = 'att&ck框架类别表';

ALTER TABLE `rtks_attack_train_relation`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `attack_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'attack框架id' AFTER `resource_id`,
    MODIFY COLUMN `business_template_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练模板id' AFTER `attack_id`,
    MODIFY COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `business_template_id`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'att&ck场景训练关联表';


ALTER TABLE `rtks_course_name`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称' AFTER `resource_id`,
    MODIFY COLUMN `description` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述' AFTER `name`,
    MODIFY COLUMN `difficulty` int NULL DEFAULT NULL COMMENT '难度' AFTER `description`,
    MODIFY COLUMN `lesson_count` int NULL DEFAULT NULL COMMENT '课时数量' AFTER `difficulty`,
    MODIFY COLUMN `e_book` int NULL DEFAULT NULL COMMENT '类型' AFTER `lesson_count`,
    MODIFY COLUMN `course_num` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程数量' AFTER `e_book`,
    MODIFY COLUMN `exercise_count` int NULL DEFAULT NULL COMMENT '实践课数量' AFTER `course_num`,
    MODIFY COLUMN `theory_count` int NULL DEFAULT NULL COMMENT '理论课数量' AFTER `exercise_count`,
    MODIFY COLUMN `direction` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程方向' AFTER `theory_count`,
    MODIFY COLUMN `direction_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方向名称' AFTER `direction`,
    MODIFY COLUMN `interview` int NULL DEFAULT NULL COMMENT '访问权限' AFTER `direction_name`,
    COMMENT = 'rtks初始化课程数据表';


ALTER TABLE `rtks_course_related`
    MODIFY COLUMN `type` int NOT NULL COMMENT '类型：1：知识点;2：技能点（已取消）' AFTER `related_id`,
    COMMENT = 'rtks知识点课程关联表';


ALTER TABLE `rtks_engage`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `category_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别id' AFTER `resource_id`,
    MODIFY COLUMN `sub_category_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '子类别id' AFTER `category_id`,
    MODIFY COLUMN `name_zh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文名称' AFTER `sub_category_id`,
    MODIFY COLUMN `name_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '英文名称' AFTER `name_zh`,
    COMMENT = 'engage框架表';

ALTER TABLE `rtks_engage_category`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `name_zh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文名称' AFTER `resource_id`,
    MODIFY COLUMN `name_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '英文名称' AFTER `name_zh`,
    COMMENT = 'engage框架类别表';


ALTER TABLE `rtks_engage_sub_category`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `category_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别id' AFTER `resource_id`,
    MODIFY COLUMN `name_zh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文名称' AFTER `category_id`,
    MODIFY COLUMN `name_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '英文名称' AFTER `name_zh`,
    COMMENT = 'engage框架子类别表';


ALTER TABLE `rtks_engage_train_relation`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `engage_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'engage框架id' AFTER `resource_id`,
    MODIFY COLUMN `business_template_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练模板id' AFTER `engage_id`,
    MODIFY COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `business_template_id`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'engage场景训练关联表';


ALTER TABLE `rtks_knowledge`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `category_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别id' AFTER `resource_id`,
    MODIFY COLUMN `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容' AFTER `category_id`,
    MODIFY COLUMN `courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联课程列表' AFTER `content`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `courses`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks知识点表';


ALTER TABLE `rtks_knowledge_category`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容' AFTER `resource_id`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `content`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks知识点类别表';



ALTER TABLE `rtks_level`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称' AFTER `resource_id`,
    MODIFY COLUMN `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述' AFTER `name`,
    MODIFY COLUMN `parent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级id' AFTER `description`,
    MODIFY COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `parent`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    MODIFY COLUMN `frame` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '框架id' AFTER `update_time`,
    COMMENT = 'rtks框架层级表';



ALTER TABLE `rtks_role`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称' AFTER `resource_id`,
    MODIFY COLUMN `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述' AFTER `name`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `description`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    MODIFY COLUMN `level_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '层级id' AFTER `update_time`,
    MODIFY COLUMN `category` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别' AFTER `level_id`,
    MODIFY COLUMN `task_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务列表' AFTER `category`,
    MODIFY COLUMN `knowledge_id_weights` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点权重列表' AFTER `task_ids`,
    MODIFY COLUMN `knowledge_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点列表' AFTER `knowledge_id_weights`,
    MODIFY COLUMN `skill_id_weights` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能点权重列表' AFTER `knowledge_ids`,
    MODIFY COLUMN `skill_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能点列表' AFTER `skill_id_weights`,
    MODIFY COLUMN `ability_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '能力列表' AFTER `skill_ids`,
    MODIFY COLUMN `skill_courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '技能课程列表' AFTER `ability_ids`,
    MODIFY COLUMN `knowledge_courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '知识点课程列表' AFTER `skill_courses`,
    MODIFY COLUMN `knowledge_weight` tinyint NOT NULL DEFAULT 50 COMMENT '知识点比例' AFTER `knowledge_courses`,
    MODIFY COLUMN `skill_missions` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '技能任务列表' AFTER `knowledge_weight`,
    COMMENT = 'rtks角色表';



ALTER TABLE `rtks_role_match_record`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id' AFTER `id`,
    MODIFY COLUMN `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id' AFTER `user_id`,
    MODIFY COLUMN `match` tinyint NOT NULL COMMENT '匹配度' AFTER `role_id`,
    MODIFY COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `match`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks角色匹配度表';



ALTER TABLE `rtks_role_skill_relation`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id' AFTER `id`,
    MODIFY COLUMN `skill_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能id' AFTER `role_id`,
    MODIFY COLUMN `weight` tinyint NOT NULL COMMENT '权重' AFTER `skill_id`,
    MODIFY COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `weight`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks角色技能点关联表';



ALTER TABLE `rtks_situation_log`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id' AFTER `id`,
    MODIFY COLUMN `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名' AFTER `user_id`,
    MODIFY COLUMN `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id' AFTER `user_name`,
    MODIFY COLUMN `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名' AFTER `role_id`,
    MODIFY COLUMN `type` tinyint(1) NOT NULL COMMENT '类型：1.合格;2优秀' AFTER `role_name`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `type`,
    COMMENT = 'rtks角色态势动态表';



ALTER TABLE `rtks_skill`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `content` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称' AFTER `resource_id`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `content`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    MODIFY COLUMN `system_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '体系id' AFTER `update_time`,
    MODIFY COLUMN `category_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '类别id' AFTER `system_id`,
    MODIFY COLUMN `courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程列表' AFTER `category_id`,
    COMMENT = 'rtks技能点表';


ALTER TABLE `rtks_skill_category`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容' AFTER `resource_id`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `content`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks技能点类别表';


ALTER TABLE `rtks_skill_mission_relation`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `skill_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能点id' AFTER `id`,
    MODIFY COLUMN `mission_template_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务模板id' AFTER `skill_id`,
    MODIFY COLUMN `weight` tinyint NOT NULL COMMENT '权重' AFTER `mission_template_id`,
    MODIFY COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `weight`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks技能点任务关联表';


ALTER TABLE `rtks_skill_system`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `scene_id` int NOT NULL COMMENT '场景id' AFTER `update_time`,
    MODIFY COLUMN `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容' AFTER `resource_id`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `content`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks技能点体系表';


ALTER TABLE `rtks_task`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容' AFTER `resource_id`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `content`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = 'rtks任务表';


ALTER TABLE `rtks_user_role`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id' AFTER `id`,
    MODIFY COLUMN `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id' AFTER `user_id`,
    MODIFY COLUMN `role_name` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称' AFTER `role_id`,
    MODIFY COLUMN `create_time` datetime NOT NULL COMMENT '创建时间' AFTER `role_name`,
    MODIFY COLUMN `update_time` datetime NOT NULL COMMENT '更新时间' AFTER `create_time`,
    MODIFY COLUMN `user_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名' AFTER `update_time`,
    COMMENT = 'rtks用户角色表';

ALTER TABLE `situation_attack_message`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `scene_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场景id' AFTER `resource_id`,
    MODIFY COLUMN `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '动效类型' AFTER `scene_id`,
    MODIFY COLUMN `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述' AFTER `type`,
    MODIFY COLUMN `source_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源节点id' AFTER `description`,
    MODIFY COLUMN `target_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标节点id' AFTER `source_id`,
    MODIFY COLUMN `interval_time` int NULL DEFAULT NULL COMMENT '间隔时间（s）' AFTER `target_id`,
    MODIFY COLUMN `duration_time` int NULL DEFAULT NULL COMMENT '持续时间（s）' AFTER `interval_time`,
    MODIFY COLUMN `look_at_target` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否聚焦' AFTER `duration_time`,
    MODIFY COLUMN `line_color` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '颜色' AFTER `look_at_target`,
    MODIFY COLUMN `line_width` int NULL DEFAULT NULL COMMENT '线宽' AFTER `line_color`,
    MODIFY COLUMN `line_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '线型' AFTER `line_width`,
    MODIFY COLUMN `media_animation` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '媒体动效列表' AFTER `line_type`,
    MODIFY COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `media_animation`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = '态势动效配置表';



ALTER TABLE `situation_config`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `scope` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '作用域' AFTER `id`,
    MODIFY COLUMN `node_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点id' AFTER `scope`,
    MODIFY COLUMN `node_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名称' AFTER `node_id`,
    MODIFY COLUMN `node_type` tinyint NOT NULL COMMENT '节点类型' AFTER `node_name`,
    MODIFY COLUMN `attribute` tinyint NOT NULL COMMENT '参数类型' AFTER `node_type`,
    MODIFY COLUMN `attribute_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数值' AFTER `attribute`,
    MODIFY COLUMN `resource_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `attribute_value`,
    COMMENT = '态势自定义属性表';


ALTER TABLE `situation_dynamic_scheme`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `scene_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场景id' AFTER `resource_id`,
    MODIFY COLUMN `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '动效方案名称' AFTER `scene_id`,
    MODIFY COLUMN `scheme_data` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '动效方案数据' AFTER `name`,
    MODIFY COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `scheme_data`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = '态势动效方案表';


ALTER TABLE `situation_scene_config`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源id' AFTER `id`,
    MODIFY COLUMN `scene_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场景id' AFTER `resource_id`,
    MODIFY COLUMN `flow_analysis` tinyint(1) NULL DEFAULT NULL COMMENT '是否分析流量采集数据' AFTER `scene_id`,
    MODIFY COLUMN `flow_auto_run` tinyint(1) NULL DEFAULT NULL COMMENT '是否自动运行仿真流量' AFTER `flow_analysis`,
    MODIFY COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `flow_auto_run`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `create_time`,
    COMMENT = '态势场景配置表';

ALTER TABLE `system_use_status`
    MODIFY COLUMN `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id' FIRST,
    MODIFY COLUMN `cpu` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'cpu使用率' AFTER `id`,
    MODIFY COLUMN `disk` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '磁盘使用率' AFTER `cpu`,
    MODIFY COLUMN `ram` tinyint NOT NULL COMMENT '内存使用率' AFTER `disk`,
    MODIFY COLUMN `alter_time` datetime NULL DEFAULT NULL COMMENT '修改时间' AFTER `ram`,
    MODIFY COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间' AFTER `alter_time`,
    COMMENT = '系统使用状态表';

