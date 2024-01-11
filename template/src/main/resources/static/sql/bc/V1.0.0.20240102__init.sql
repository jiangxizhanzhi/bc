/*
 Navicat Premium Data Transfer

 Source Server         : 10.100.2.55
 Source Server Type    : MariaDB
 Source Server Version : 100328
 Source Host           : 10.100.2.55:3306
 Source Schema         : bc

 Target Server Type    : MariaDB
 Target Server Version : 100328
 File Encoding         : 65001

 Date: 11/01/2024 16:10:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for attachment
-- ----------------------------

CREATE TABLE `attachment`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                               `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                               `file_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名称',
                               `file_path` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件路径',
                               `create_time` datetime NOT NULL COMMENT '创建时间',
                               `update_time` datetime NOT NULL COMMENT '更新时间',
                               `public` int(11) NOT NULL COMMENT '是否公开',
                               `business_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务id',
                               PRIMARY KEY (`id`) USING BTREE,
                               UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '附件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_constants_config
-- ----------------------------

CREATE TABLE `cr_constants_config`  (
                                        `id` int(11) NOT NULL AUTO_INCREMENT,
                                        `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'uuid',
                                        `code` smallint(4) NULL DEFAULT NULL COMMENT '状态码',
                                        `type` tinyint(4) NOT NULL COMMENT '类别码',
                                        `cn_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文名',
                                        `en_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '英文名',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 148 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '常量配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_network
-- ----------------------------

CREATE TABLE `cr_network`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
                               `resource_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网络id',
                               `user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
                               `project` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目',
                               `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网络名称',
                               `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
                               `cidr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网络地址',
                               `gateway` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网关',
                               `mtu` int(11) NULL DEFAULT NULL COMMENT 'MTU',
                               `sid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场景id',
                               `vlan` int(11) NULL DEFAULT NULL COMMENT 'vlan',
                               `network_status` int(11) NULL DEFAULT NULL COMMENT '网络状态0:运行，1：关闭，2：创建中，3:删除，4：错误',
                               `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                               `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
                               PRIMARY KEY (`id`) USING BTREE,
                               UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 225 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_node
-- ----------------------------

CREATE TABLE `cr_node`  (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点id',
                            `instance_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '虚拟机或容器id',
                            `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名称',
                            `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip',
                            `dns` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'dns',
                            `gateway` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'gateway',
                            `cpu` int(11) NULL DEFAULT NULL COMMENT 'CPU',
                            `memory` int(11) NULL DEFAULT NULL COMMENT '内存',
                            `disk` int(11) NULL DEFAULT NULL COMMENT '磁盘',
                            `tid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '镜像id',
                            `sid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场景id',
                            `type` tinyint(4) NULL DEFAULT NULL COMMENT '节点类型',
                            `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
                            `addresses` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                            `status` tinyint(4) NULL DEFAULT NULL COMMENT '节点状态',
                            `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                            `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
                            `nid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 139 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_radar_label
-- ----------------------------

CREATE TABLE `cr_radar_label`  (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `resource_id` int(11) NULL DEFAULT NULL,
                                   `label` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '维度',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '雷达图维度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_scene
-- ----------------------------

CREATE TABLE `cr_scene`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
                             `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场景id',
                             `scene_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场景名称',
                             `start_time` datetime NULL DEFAULT NULL COMMENT '场景启动时间',
                             `end_time` datetime NULL DEFAULT NULL COMMENT '场景停止时间',
                             `destroy_time` datetime NULL DEFAULT NULL COMMENT '场景自动销毁时间',
                             `instance_id` varchar(65) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场景所属实例id',
                             `scene_status` int(11) NULL DEFAULT NULL COMMENT '场景状态：0=运行中，1=关闭，2=创建中，3=已删除，4=错误，5=初始化',
                             `topology_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拓扑id',
                             `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
                             `scene_ttl` int(11) NULL DEFAULT NULL COMMENT '场景存活时间，单位：小时',
                             `instance_type` int(11) NULL DEFAULT NULL COMMENT '实例类型，0=课程，1=实操，2=运维',
                             `meta` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'json格式数据',
                             `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                             `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
                             INDEX `instance_user`(`instance_id`, `user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 208 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_standard
-- ----------------------------

CREATE TABLE `cr_standard`  (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源id',
                                `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
                                `weight` int(11) NULL DEFAULT NULL COMMENT '权重',
                                `description` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细说明',
                                `sort` int(11) NULL DEFAULT NULL COMMENT '顺序',
                                `parent_id` int(11) NULL DEFAULT NULL COMMENT '父节点id',
                                `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cr_topology
-- ----------------------------

CREATE TABLE `cr_topology`  (
                                `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                `topology_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '拓扑id',
                                `topology` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '拓扑结构',
                                `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE INDEX `topology_unique`(`topology_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dict_info
-- ----------------------------

CREATE TABLE `dict_info`  (
                              `id` int(11) NOT NULL AUTO_INCREMENT,
                              `key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              `item_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              `item_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              `parent_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              `create_time` datetime NULL DEFAULT NULL,
                              `update_time` datetime NULL DEFAULT NULL,
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for init_rtks_course_related
-- ----------------------------

CREATE TABLE `init_rtks_course_related`  (
                                             `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程id',
                                             `related_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点id',
                                             `type` int(11) NOT NULL COMMENT '类型：1：知识点;2：技能点（已取消）',
                                             `weight` int(11) NOT NULL COMMENT '权重',
                                             INDEX `course_id`(`resource_id`) USING BTREE,
                                             INDEX `related_id`(`related_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks知识点课程关联重置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for init_rtks_knowledge
-- ----------------------------

CREATE TABLE `init_rtks_knowledge`  (
                                        `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                        `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                                        `category_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别id',
                                        `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
                                        `courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联课程列表',
                                        `create_time` datetime NOT NULL COMMENT '创建时间',
                                        `update_time` datetime NOT NULL COMMENT '更新时间',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 363 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks知识点重置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for init_rtks_knowledge_category
-- ----------------------------

CREATE TABLE `init_rtks_knowledge_category`  (
                                                 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                                 `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                                                 `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
                                                 `create_time` datetime NOT NULL COMMENT '创建时间',
                                                 `update_time` datetime NOT NULL COMMENT '更新时间',
                                                 PRIMARY KEY (`id`) USING BTREE,
                                                 UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks知识点类别重置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for init_rtks_level
-- ----------------------------

CREATE TABLE `init_rtks_level`  (
                                    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                    `resource_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                                    `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
                                    `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
                                    `parent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级id',
                                    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                    `frame` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '框架id',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks框架层级重置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for init_rtks_role
-- ----------------------------

CREATE TABLE `init_rtks_role`  (
                                   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                   `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                                   `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
                                   `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
                                   `create_time` datetime NOT NULL COMMENT '创建时间',
                                   `update_time` datetime NOT NULL COMMENT '更新时间',
                                   `level_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '层级id',
                                   `category` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别',
                                   `task_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务列表',
                                   `knowledge_id_weights` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点权重列表',
                                   `knowledge_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点列表',
                                   `skill_id_weights` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能点权重列表',
                                   `skill_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能点列表',
                                   `ability_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '能力列表',
                                   `skill_courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '技能课程列表',
                                   `knowledge_courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '知识点课程列表',
                                   `knowledge_weight` tinyint(4) NOT NULL DEFAULT 50 COMMENT '知识点比例',
                                   `skill_missions` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '[]' COMMENT '技能任务列表',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 126 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks角色重置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for init_rtks_role_skill_relation
-- ----------------------------

CREATE TABLE `init_rtks_role_skill_relation`  (
                                                  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                                  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
                                                  `skill_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能id',
                                                  `weight` tinyint(4) NOT NULL COMMENT '权重',
                                                  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                                  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                                  PRIMARY KEY (`id`) USING BTREE,
                                                  INDEX `role`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '预置rtks角色技能点关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for init_rtks_task
-- ----------------------------

CREATE TABLE `init_rtks_task`  (
                                   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                   `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                                   `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
                                   `create_time` datetime NOT NULL COMMENT '创建时间',
                                   `update_time` datetime NOT NULL COMMENT '更新时间',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 134 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks任务重置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for local_scene
-- ----------------------------

CREATE TABLE `local_scene`  (
                                `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                                `mp_scene_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中台场景id',
                                `business_code` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务码',
                                `start_time` datetime NULL DEFAULT NULL COMMENT '场景启动时间',
                                `end_time` datetime NULL DEFAULT NULL COMMENT '场景停止时间',
                                `destroy_time` datetime NULL DEFAULT NULL COMMENT '自动调用销毁时间',
                                `destroy_mode` int(11) NULL DEFAULT NULL COMMENT '销毁模式：0=手动；1=自动',
                                `business_instance_id` varchar(65) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务id',
                                `scene_status` int(11) NULL DEFAULT NULL COMMENT '场景状态',
                                `pause_time` datetime NULL DEFAULT NULL COMMENT '场景暂停时间',
                                `create_time` datetime NOT NULL COMMENT '创建时间',
                                `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                `topo_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拓扑Id',
                                `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
                                `scene_ttl` int(11) NULL DEFAULT NULL COMMENT '场景存活时间',
                                `node_count` int(11) NULL DEFAULT NULL COMMENT '节点数量',
                                `meta` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'json格式数据',
                                `scene_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场景名',
                                `scene_attr` int(11) NULL DEFAULT NULL COMMENT '场景类型',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE INDEX `resourceIdUnique`(`resource_id`) USING BTREE,
                                UNIQUE INDEX `businessInstanceUniq`(`business_instance_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '本地场景表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_classroom
-- ----------------------------

CREATE TABLE `oj_classroom`  (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                 `start_time` datetime NOT NULL COMMENT '课堂开始时间',
                                 `end_time` datetime NOT NULL COMMENT '课堂结束时间',
                                 `course_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联课程id',
                                 `lesson_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联课时id',
                                 `organization_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联组织id',
                                 `organization_ids` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联组织下所有组织id',
                                 `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者（课堂教师）',
                                 `create_time` datetime NOT NULL,
                                 `update_time` datetime NOT NULL,
                                 `train_plan_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联训练计划id',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 UNIQUE INDEX `resource_uniq`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训课程表（课堂安排）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_classroom_group_archive
-- ----------------------------

CREATE TABLE `oj_classroom_group_archive`  (
                                               `id` int(11) NOT NULL AUTO_INCREMENT,
                                               `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                               `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分组信息快照名称',
                                               `groups` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分组数据',
                                               `classroom_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属课堂id',
                                               `create_time` datetime NOT NULL,
                                               `update_time` datetime NOT NULL,
                                               PRIMARY KEY (`id`) USING BTREE,
                                               UNIQUE INDEX `room_name_uniq`(`classroom_id`, `name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训课堂（实验课）分组信息快照' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_classroom_group_info
-- ----------------------------

CREATE TABLE `oj_classroom_group_info`  (
                                            `id` int(11) NOT NULL AUTO_INCREMENT,
                                            `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                            `classroom_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属课堂id',
                                            `group_no` int(64) NOT NULL,
                                            `group_user_ids` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组内用户id',
                                            `destroy_delay` int(11) NULL DEFAULT NULL COMMENT '实验环境销毁期限（单位:h）',
                                            `scene_share_mode` int(11) NULL DEFAULT NULL COMMENT '场景是否共享标识',
                                            `lesson_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联实验课id',
                                            `topo_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实验课使用拓扑id',
                                            `create_time` datetime NOT NULL,
                                            `update_time` datetime NOT NULL,
                                            PRIMARY KEY (`id`) USING BTREE,
                                            UNIQUE INDEX `resource_uniq`(`resource_id`) USING BTREE,
                                            UNIQUE INDEX `room_group_uniq`(`classroom_id`, `group_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训课堂（实验课）分组信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_classroom_sign
-- ----------------------------

CREATE TABLE `oj_classroom_sign`  (
                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                      `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `classroom_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属课堂id',
                                      `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `sign_in` int(11) NOT NULL COMMENT '是否签到',
                                      `sign_in_time` datetime NOT NULL COMMENT '签到时间',
                                      `need_help` int(11) NULL DEFAULT NULL COMMENT '学员是否需要请求协助（实验课）',
                                      `create_time` datetime NOT NULL,
                                      `update_time` datetime NOT NULL,
                                      `node_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场景中请求协助的节点id（实验课）',
                                      PRIMARY KEY (`id`) USING BTREE,
                                      UNIQUE INDEX `room_user_uniq`(`classroom_id`, `user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训课堂签到记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_comment
-- ----------------------------

CREATE TABLE `oj_comment`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `resource_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                               `reply_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '此留言所回复留言id',
                               `business_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '留言所属业务（资源）id（课程、习题...）',
                               `theme_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '留言所属主题信息（课程名、习题名...）',
                               `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发起留言用户id',
                               `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '留言内容',
                               `business_type` int(11) NOT NULL COMMENT '留言所属模块类型（课程、习题...）',
                               `root_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '此留言所属楼层的层主留言id',
                               `create_time` datetime NOT NULL,
                               `update_time` datetime NOT NULL,
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '实训留言' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_comment_like
-- ----------------------------

CREATE TABLE `oj_comment_like`  (
                                    `id` int(11) NOT NULL AUTO_INCREMENT,
                                    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                    `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '点赞用户id',
                                    `comment_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '点赞评论id',
                                    `create_time` datetime NOT NULL,
                                    `update_time` datetime NOT NULL,
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE INDEX `user_comment_uniq`(`user_id`, `comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训留言点赞' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_course
-- ----------------------------

CREATE TABLE `oj_course`  (
                              `id` int(11) NOT NULL AUTO_INCREMENT,
                              `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                              `builtin` int(11) NOT NULL COMMENT '课程是否属于内置资源',
                              `operation_auth_type` int(11) NOT NULL COMMENT '后台操作权限类型',
                              `operation_auth_ids` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '拥有后台操作权限的用户id',
                              `read_auth_type` int(11) NOT NULL COMMENT '前台预览权限类型',
                              `read_auth_ids` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '拥有前台预览权限的组织id',
                              `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程名称',
                              `description` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '课程描述',
                              `logo` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程封面id',
                              `outline` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '课程大纲',
                              `difficulty` int(11) NOT NULL COMMENT '难度',
                              `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者id',
                              `modify_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最后修改的用户id',
                              `public` int(11) NOT NULL COMMENT '是否公开',
                              `course_type` int(11) NULL DEFAULT NULL COMMENT '课程类型',
                              `heat` int(11) NOT NULL COMMENT '热度',
                              `lecturer` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '讲师',
                              `score` int(11) NULL DEFAULT NULL COMMENT '学分',
                              `course_num` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程编号',
                              `category_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联方向id',
                              `create_time` datetime NOT NULL,
                              `update_time` datetime NOT NULL,
                              PRIMARY KEY (`id`) USING BTREE,
                              UNIQUE INDEX `resource_unq`(`resource_id`) USING BTREE,
                              UNIQUE INDEX `name_uniq`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训课程' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_course_category
-- ----------------------------

CREATE TABLE `oj_course_category`  (
                                       `id` int(11) NOT NULL AUTO_INCREMENT,
                                       `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                       `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '方向名称',
                                       `course_type` int(11) NOT NULL COMMENT '课程类型0=基础 2=攻击 3=防御',
                                       `code` varchar(2096) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '节点编码  父级节点code + \',\' + 当前节点编号',
                                       `update_time` datetime NOT NULL,
                                       `create_time` datetime NOT NULL,
                                       `parent_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级节点编号',
                                       PRIMARY KEY (`id`) USING BTREE,
                                       UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训课程方向/分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_course_lesson
-- ----------------------------

CREATE TABLE `oj_course_lesson`  (
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `course_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联课程id',
                                     `builtin` int(11) NOT NULL COMMENT '是否内置',
                                     `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课时名称',
                                     `type` int(11) NULL DEFAULT NULL COMMENT '课时类型',
                                     `handout_type` int(11) NULL DEFAULT NULL COMMENT '讲义类型',
                                     `handout_url` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '讲义文件地址',
                                     `video_url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视频课件地址',
                                     `difficulty` int(11) NOT NULL COMMENT '难度',
                                     `public` int(11) NOT NULL COMMENT '是否公开',
                                     `attachment_ids` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联附件id',
                                     `compulsory` int(11) NOT NULL COMMENT '是否必修',
                                     `duration` int(11) NULL DEFAULT NULL COMMENT '建议时长（单位：m）',
                                     `destroy_delay` int(11) NULL DEFAULT NULL COMMENT '场景销毁期限（实验课使用）',
                                     `topo_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联拓扑id（实验课使用）',
                                     `knowledge` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '知识点',
                                     `scene_share_mode` int(11) NULL DEFAULT NULL COMMENT '场景是否共享标识',
                                     `origin_markdown_file` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '上传markdown讲义原文件地址',
                                     `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建用户id',
                                     `heat` int(11) NOT NULL COMMENT '热度',
                                     `paper_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联课后练习id',
                                     `create_time` datetime NOT NULL,
                                     `update_time` datetime NOT NULL,
                                     `paper_public` int(11) NULL DEFAULT NULL COMMENT '课后练习是否公开',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `resourse_id_uniq`(`resource_id`) USING BTREE,
                                     UNIQUE INDEX `course_name_uniq`(`course_id`, `name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训课时' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_course_study_history
-- ----------------------------

CREATE TABLE `oj_course_study_history`  (
                                            `id` int(11) NOT NULL AUTO_INCREMENT,
                                            `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                            `course_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                            `course_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                            `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                            `create_time` datetime NULL DEFAULT NULL,
                                            `update_time` datetime NULL DEFAULT NULL,
                                            PRIMARY KEY (`id`) USING BTREE,
                                            UNIQUE INDEX `course_user_uniq`(`course_id`, `user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_course_study_progress
-- ----------------------------

CREATE TABLE `oj_course_study_progress`  (
                                             `id` int(11) NOT NULL AUTO_INCREMENT,
                                             `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'uuid',
                                             `finish_lesson_count` int(11) NULL DEFAULT NULL COMMENT '完成课时数',
                                             `course_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点id',
                                             `create_user_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人id',
                                             `create_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
                                             `update_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
                                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_lesson_config
-- ----------------------------

CREATE TABLE `oj_lesson_config`  (
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `index` int(11) NOT NULL COMMENT '此节点在父节点下顺序索引',
                                     `parent_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父节点id',
                                     `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目录节点名称',
                                     `node_type` int(11) NOT NULL COMMENT '节点类型',
                                     `course_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联课程id',
                                     `lesson_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联课时id',
                                     `create_time` datetime NOT NULL,
                                     `update_time` datetime NOT NULL,
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1008 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训课时目录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_lesson_paper_evaluate
-- ----------------------------

CREATE TABLE `oj_lesson_paper_evaluate`  (
                                             `id` int(11) NOT NULL AUTO_INCREMENT,
                                             `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                             `lesson_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联课时id',
                                             `score` double(11, 1) NOT NULL COMMENT '得分',
                                             `record` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课后联系提交记录',
                                             `submit_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提交用户id',
                                             `create_time` datetime NOT NULL,
                                             `update_time` datetime NOT NULL,
                                             `total_score` double(11, 1) NOT NULL COMMENT '课后练习总分',
                                             `level` int(11) NULL DEFAULT NULL COMMENT '课后练习完成评级',
                                             `accuracy` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '正确率',
                                             `corrector` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评卷用户id',
                                             PRIMARY KEY (`id`) USING BTREE,
                                             UNIQUE INDEX `lesson_user_uniq`(`lesson_id`, `submit_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训课后练习评估' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_lesson_record
-- ----------------------------

CREATE TABLE `oj_lesson_record`  (
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `lesson_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学习课时id',
                                     `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学习用户id',
                                     `course_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学习课程id',
                                     `create_time` datetime NOT NULL,
                                     `update_time` datetime NOT NULL,
                                     `learned_type` int(11) NOT NULL DEFAULT 0 COMMENT '课程学习类型0=课程 1=课程表',
                                     `room_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课堂id',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `user_lesson_uniq`(`user_id`, `lesson_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1205 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训课时学习记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_lesson_record_auxiliary
-- ----------------------------

CREATE TABLE `oj_lesson_record_auxiliary`  (
                                               `id` int(11) NOT NULL AUTO_INCREMENT,
                                               `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                               `lesson_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                               `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                               `course_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                               `room_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课堂id',
                                               `learned_type` int(11) NOT NULL DEFAULT 0 COMMENT '课程学习类型0=课程 1=课程表',
                                               `create_time` datetime NOT NULL,
                                               `update_time` datetime NOT NULL,
                                               PRIMARY KEY (`id`) USING BTREE,
                                               UNIQUE INDEX `user_lesson_uniq`(`user_id`, `lesson_id`, `room_id`, `learned_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_local_scene
-- ----------------------------

CREATE TABLE `oj_local_scene`  (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                   `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建场景用户id',
                                   `shared` int(11) NOT NULL COMMENT '是否为共享场景',
                                   `from_web` int(11) NULL DEFAULT NULL COMMENT '是否为前台场景',
                                   `flags` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '环境FLAG（实践操作习题使用）',
                                   `business_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联场景的业务（资源）id',
                                   `business_type` int(11) NOT NULL COMMENT '关联场景的业务类型',
                                   `local_scene_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联的local_scene.resource_id',
                                   `create_time` datetime NULL DEFAULT NULL,
                                   `update_time` datetime NULL DEFAULT NULL,
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训场景关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_note
-- ----------------------------

CREATE TABLE `oj_note`  (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `resource_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                            `business_type` int(11) NOT NULL COMMENT '1=课程 2=课时 3=练习 4=课后练习',
                            `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '笔记内容',
                            `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户编号',
                            `business_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源编号',
                            `note_type` int(11) NOT NULL COMMENT '1=笔记 2=实验报告',
                            `operation_auth_type` int(11) NOT NULL COMMENT '授权操作类类型 1=共享 2=自定义',
                            `operation_auth_ids` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权操作用户id',
                            `read_auth_type` int(11) NOT NULL COMMENT '同上',
                            `read_auth_ids` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权访问组织id',
                            `create_time` datetime NOT NULL,
                            `update_time` datetime NOT NULL,
                            `public` int(11) NOT NULL COMMENT '是否公开',
                            `is_submit` int(11) NULL DEFAULT NULL COMMENT '实验报告是否提交',
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE INDEX `unique_index`(`resource_id`) USING BTREE,
                            UNIQUE INDEX `user_bt_bi_uniq`(`user_id`, `note_type`, `business_type`, `business_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 71 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '实训我的笔记/实验报告' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_note_evaluate
-- ----------------------------

CREATE TABLE `oj_note_evaluate`  (
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `score` int(11) NOT NULL COMMENT '得分',
                                     `passed` int(11) NOT NULL COMMENT '是否通过',
                                     `evaluate_comment` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评语',
                                     `evaluator_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评估用户id',
                                     `evaluate_time` datetime NOT NULL COMMENT '评估时间',
                                     `note_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评估实验报告id',
                                     `create_time` datetime NOT NULL,
                                     `update_time` datetime NOT NULL,
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `note_id_uniq`(`note_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训实验报告评估' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_paper
-- ----------------------------

CREATE TABLE `oj_paper`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                             `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '试卷名称',
                             `description` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '试卷描述',
                             `logo` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '试卷封面id',
                             `practice_count` int(11) NOT NULL COMMENT '试卷下习题总数',
                             `total_score` double(11, 1) NULL DEFAULT NULL COMMENT '试卷总分',
                             `public` int(11) NOT NULL COMMENT '是否公开',
                             `type` int(11) NOT NULL COMMENT '试卷类型',
                             `create_time` datetime NOT NULL,
                             `update_time` datetime NOT NULL,
                             `operation_auth_type` int(1) NULL DEFAULT NULL,
                             `operation_auth_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拥有后台操作权限的用户id',
                             `read_auth_type` int(1) NULL DEFAULT NULL,
                             `read_auth_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拥有前台预览权限的组织id',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `resource_id_uniq`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训试卷/课后练习' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_paper_practice_theory
-- ----------------------------

CREATE TABLE `oj_paper_practice_theory`  (
                                             `id` int(11) NOT NULL AUTO_INCREMENT,
                                             `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                             `snapshot` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '理论基础信息快照',
                                             `origin_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '原理论题id',
                                             `paper_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联试卷id',
                                             `submit_answer` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '个人组卷练习提交的答案',
                                             `solved` int(1) NOT NULL COMMENT '个人组卷练习解题状态',
                                             `create_time` datetime NULL DEFAULT NULL,
                                             `update_time` datetime NULL DEFAULT NULL,
                                             PRIMARY KEY (`id`) USING BTREE,
                                             INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 298 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组卷习题详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_practice_category
-- ----------------------------

CREATE TABLE `oj_practice_category`  (
                                         `id` int(11) NOT NULL AUTO_INCREMENT,
                                         `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                         `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
                                         `practice_type` int(1) NOT NULL COMMENT '习题类型',
                                         `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人',
                                         `code` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '节点编号',
                                         `parent_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编号',
                                         `create_time` datetime NULL DEFAULT NULL,
                                         `update_time` datetime NULL DEFAULT NULL,
                                         PRIMARY KEY (`id`) USING BTREE,
                                         UNIQUE INDEX `resource_uniq`(`resource_id`) USING BTREE,
                                         UNIQUE INDEX `type_name_uniq`(`practice_type`, `name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训习题方向/分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_practice_count
-- ----------------------------

CREATE TABLE `oj_practice_count`  (
                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                      `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
                                      `count` int(5) NOT NULL COMMENT '练习次数',
                                      `create_time` datetime NULL DEFAULT NULL,
                                      `update_time` datetime NULL DEFAULT NULL,
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_practice_mistake
-- ----------------------------

CREATE TABLE `oj_practice_mistake`  (
                                        `id` int(11) NOT NULL AUTO_INCREMENT,
                                        `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                        `practice_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目id',
                                        `practice_type` int(11) NOT NULL COMMENT '题目类型',
                                        `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
                                        `correct` int(11) NOT NULL COMMENT '做对次数',
                                        `incorrect` int(11) NOT NULL COMMENT '做错次数',
                                        `create_time` datetime NULL DEFAULT NULL,
                                        `update_time` datetime NULL DEFAULT NULL,
                                        PRIMARY KEY (`id`) USING BTREE,
                                        INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '错题集表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_practice_operation
-- ----------------------------

CREATE TABLE `oj_practice_operation`  (
                                          `id` int(11) NOT NULL AUTO_INCREMENT,
                                          `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                          `category_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联方向id',
                                          `builtin` int(11) NOT NULL COMMENT '是否为内置资源',
                                          `practice_set_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联习题集id',
                                          `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                          `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
                                          `answer` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '答案',
                                          `difficulty` int(11) NOT NULL COMMENT '难度',
                                          `knowledge` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '知识点',
                                          `public` int(11) NOT NULL COMMENT '是否公开',
                                          `score` int(11) NOT NULL COMMENT '得分',
                                          `attachment_id` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联附件id',
                                          `writeup` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '题解',
                                          `writeup_public` int(11) NULL DEFAULT NULL COMMENT '题解是否公开',
                                          `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建用户id',
                                          `dynamic_env` int(11) NULL DEFAULT NULL COMMENT '是否为动态环境',
                                          `topo_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联拓扑id（动态环境下拥有）',
                                          `scene_share_mode` int(11) NULL DEFAULT NULL COMMENT '场景分享标识（动态环境下拥有）',
                                          `dynamic_flag` int(11) NULL DEFAULT NULL COMMENT '是否为动态FLAG',
                                          `destroy_delay` int(11) NULL DEFAULT NULL COMMENT '场景销毁时限（单位：h，动态环境下拥有）',
                                          `create_time` datetime NOT NULL,
                                          `update_time` datetime NOT NULL,
                                          `category` int(1) NULL DEFAULT NULL COMMENT '题型',
                                          `is_true` int(1) NULL DEFAULT NULL COMMENT '是否是真题',
                                          `maintenance_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联的运维题id',
                                          PRIMARY KEY (`id`) USING BTREE,
                                          UNIQUE INDEX `resource_uniq`(`resource_id`) USING BTREE,
                                          UNIQUE INDEX `set_title_uniq`(`practice_set_id`, `title`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训实践操作' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_practice_operation_ref
-- ----------------------------

CREATE TABLE `oj_practice_operation_ref`  (
                                              `id` int(11) NOT NULL AUTO_INCREMENT,
                                              `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                              `snapshot` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '实践操作基础信息快照',
                                              `origin_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '原实践操作id',
                                              `paper_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联试卷id',
                                              `create_time` datetime NOT NULL,
                                              `update_time` datetime NOT NULL,
                                              PRIMARY KEY (`id`) USING BTREE,
                                              UNIQUE INDEX `paper_origin_uniq`(`paper_id`, `origin_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训实践操作引用表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_practice_paper
-- ----------------------------

CREATE TABLE `oj_practice_paper`  (
                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                      `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `practice_status` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目状态',
                                      `count` int(11) NOT NULL COMMENT '出题数量',
                                      `difficulty` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目难度',
                                      `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目类型',
                                      `direction` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `is_finish` int(1) NOT NULL COMMENT '是否完成',
                                      `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组卷人',
                                      `create_time` datetime NULL DEFAULT NULL,
                                      `update_time` datetime NULL DEFAULT NULL,
                                      PRIMARY KEY (`id`) USING BTREE,
                                      INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组卷信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_practice_random_flag
-- ----------------------------

CREATE TABLE `oj_practice_random_flag`  (
                                            `id` int(11) NOT NULL AUTO_INCREMENT,
                                            `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                            `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '答题用户id',
                                            `attachment_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联附件id',
                                            `practice_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联实践操作id',
                                            `flag` varchar(1028) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'FLAG',
                                            `create_time` datetime NOT NULL,
                                            `update_time` datetime NOT NULL,
                                            `type` int(11) NULL DEFAULT NULL COMMENT '区别使用业务（习题、课后练习...）',
                                            PRIMARY KEY (`id`) USING BTREE,
                                            UNIQUE INDEX `prac_type_user_uniq`(`practice_id`, `type`, `user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训实践操作随机FLAG记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_practice_set
-- ----------------------------

CREATE TABLE `oj_practice_set`  (
                                    `id` int(11) NOT NULL AUTO_INCREMENT,
                                    `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                    `builtin` int(11) NOT NULL COMMENT '是否为内置数据',
                                    `practice_type` int(11) NOT NULL COMMENT '习题集类型',
                                    `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '习题集名称',
                                    `desc` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
                                    `logo` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '习题集封面id',
                                    `public` int(11) NOT NULL COMMENT '是否公开',
                                    `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建用户id',
                                    `operation_auth_type` int(11) NOT NULL COMMENT '后台操作权限类型',
                                    `operation_auth_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拥有后台操作权限的用户id',
                                    `read_auth_type` int(11) NOT NULL COMMENT '前台预览权限类型',
                                    `read_auth_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拥有前台预览权限的组织id',
                                    `create_time` datetime NULL DEFAULT NULL,
                                    `update_time` datetime NULL DEFAULT NULL,
                                    `practice_count` int(11) NULL DEFAULT NULL COMMENT '习题数量',
                                    `source` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE INDEX `resource_idx`(`resource_id`) USING BTREE,
                                    UNIQUE INDEX `name_uniq`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训习题集' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_practice_solved
-- ----------------------------

CREATE TABLE `oj_practice_solved`  (
                                       `id` int(11) NOT NULL AUTO_INCREMENT,
                                       `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                       `submit_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提交人',
                                       `submit_answer` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提交的答案',
                                       `score` double(11, 0) NOT NULL COMMENT '得分',
                                       `total_score` double(11, 0) NOT NULL COMMENT '习题分值',
                                       `solved` int(1) NOT NULL COMMENT '是否解出',
                                       `practice_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '习题编号',
                                       `practice_type` int(1) NOT NULL COMMENT '习题类型',
                                       `collection` int(1) NOT NULL COMMENT '是否收藏',
                                       `create_time` datetime NULL DEFAULT NULL,
                                       `update_time` datetime NULL DEFAULT NULL,
                                       `is_paper` int(1) NOT NULL COMMENT '是否是自由组卷产生的记录',
                                       PRIMARY KEY (`id`) USING BTREE,
                                       UNIQUE INDEX `user_type_practice_uniq`(`submit_user_id`, `practice_type`, `practice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 65 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训习题答题记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_practice_theory
-- ----------------------------

CREATE TABLE `oj_practice_theory`  (
                                       `id` int(11) NOT NULL AUTO_INCREMENT,
                                       `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                       `builtin` int(11) NOT NULL COMMENT '是否为内置数据',
                                       `category_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联方向id',
                                       `title` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题（暂未使用）',
                                       `content` varchar(960) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
                                       `answer` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '答案',
                                       `difficulty` int(11) NOT NULL COMMENT '难度',
                                       `knowledge` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '知识点',
                                       `public` int(11) NOT NULL COMMENT '是否公开',
                                       `score` int(11) NOT NULL COMMENT '得分',
                                       `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建用户id',
                                       `theory_type` int(11) NOT NULL COMMENT '理论基础题型',
                                       `option` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '选择题选项',
                                       `blank` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '填空题填空内容',
                                       `keyword` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简答题参考关键字',
                                       `practice_set_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联习题集id',
                                       `is_true` tinyint(1) NULL DEFAULT NULL COMMENT '是否真题',
                                       `create_time` datetime NULL DEFAULT NULL,
                                       `update_time` datetime NULL DEFAULT NULL,
                                       PRIMARY KEY (`id`) USING BTREE,
                                       UNIQUE INDEX `resource_uniq`(`resource_id`) USING BTREE,
                                       UNIQUE INDEX `set_content_uniq`(`practice_set_id`, `content`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3466 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训理论基础' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_practice_theory_ref
-- ----------------------------

CREATE TABLE `oj_practice_theory_ref`  (
                                           `id` int(11) NOT NULL AUTO_INCREMENT,
                                           `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                           `snapshot` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '理论基础基础信息快照',
                                           `origin_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '原理论基础id',
                                           `option_random` int(1) NULL DEFAULT NULL COMMENT '选择题选项是否打乱顺序',
                                           `paper_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联试卷id',
                                           `create_time` datetime NOT NULL,
                                           `update_time` datetime NOT NULL,
                                           PRIMARY KEY (`id`) USING BTREE,
                                           UNIQUE INDEX `paper_origin_uniq`(`paper_id`, `origin_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训理论基础引用表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_resource_collection
-- ----------------------------

CREATE TABLE `oj_resource_collection`  (
                                           `id` int(11) NOT NULL AUTO_INCREMENT,
                                           `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                           `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收藏用户id',
                                           `business_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收藏业务（资源）id',
                                           `business_type` int(11) NOT NULL COMMENT '收藏业务类型',
                                           `create_time` datetime NOT NULL,
                                           `update_time` datetime NOT NULL,
                                           PRIMARY KEY (`id`) USING BTREE,
                                           UNIQUE INDEX `user_type_id_uniq`(`user_id`, `business_type`, `business_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实训资源收藏记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_train_plan
-- ----------------------------

CREATE TABLE `oj_train_plan`  (
                                  `id` int(11) NOT NULL AUTO_INCREMENT,
                                  `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                  `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计划名称',
                                  `create_time` datetime NULL DEFAULT NULL,
                                  `update_time` datetime NULL DEFAULT NULL,
                                  `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建用户id',
                                  PRIMARY KEY (`id`) USING BTREE,
                                  UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE,
                                  UNIQUE INDEX `user_name_uniq`(`create_user_id`, `name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '训练计划' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for op_log
-- ----------------------------

CREATE TABLE `op_log`  (
                           `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                           `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                           `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                           `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                           `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                           `module` tinyint(3) NULL DEFAULT NULL,
                           `method` tinyint(3) NULL DEFAULT NULL,
                           `target` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                           `detail` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                           `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                           `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2594 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for radar_label
-- ----------------------------

CREATE TABLE `radar_label`  (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `resource_id` int(11) NULL DEFAULT NULL,
                                `label` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_ability
-- ----------------------------

CREATE TABLE `rtks_ability`  (
                                 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                 `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                                 `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
                                 `create_time` datetime NOT NULL COMMENT '创建时间',
                                 `update_time` datetime NOT NULL COMMENT '更新时间',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks能力表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_attack
-- ----------------------------

CREATE TABLE `rtks_attack`  (
                                `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                                `category_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别id',
                                `name_zh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文内容',
                                `name_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '英文内容',
                                PRIMARY KEY (`id`) USING BTREE,
                                INDEX `category_id`(`category_id`) USING BTREE,
                                INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 225 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'att&ck框架表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_attack_category
-- ----------------------------

CREATE TABLE `rtks_attack_category`  (
                                         `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                         `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                                         `name_zh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文内容',
                                         `name_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '英文内容',
                                         PRIMARY KEY (`id`) USING BTREE,
                                         INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'att&ck框架类别表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_attack_ics
-- ----------------------------

CREATE TABLE `rtks_attack_ics`  (
                                    `id` int(11) NOT NULL AUTO_INCREMENT,
                                    `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ics id',
                                    `category_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ics类型id',
                                    `name_zh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文名称',
                                    `name_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '英文名称',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    INDEX `category_id`(`category_id`) USING BTREE,
                                    INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 91 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_attack_ics_category
-- ----------------------------

CREATE TABLE `rtks_attack_ics_category`  (
                                             `id` int(11) NOT NULL AUTO_INCREMENT,
                                             `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ics类型id',
                                             `name_zh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文名称',
                                             `name_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '英文名称',
                                             PRIMARY KEY (`id`) USING BTREE,
                                             INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_attack_ics_train_relation
-- ----------------------------

CREATE TABLE `rtks_attack_ics_train_relation`  (
                                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                                   `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'resource_id',
                                                   `attack_ics_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ics id',
                                                   `business_template_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练模板id',
                                                   `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                                   `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                                   PRIMARY KEY (`id`) USING BTREE,
                                                   INDEX `business_template_id`(`business_template_id`) USING BTREE,
                                                   INDEX `attack_ics_id`(`attack_ics_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_attack_train_relation
-- ----------------------------

CREATE TABLE `rtks_attack_train_relation`  (
                                               `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                               `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源id',
                                               `attack_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'attack框架id',
                                               `business_template_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练模板id',
                                               `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                               `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                               PRIMARY KEY (`id`) USING BTREE,
                                               INDEX `business_template_id`(`business_template_id`) USING BTREE,
                                               INDEX `attack_id`(`attack_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'att&ck场景训练关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_course_name
-- ----------------------------

CREATE TABLE `rtks_course_name`  (
                                     `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                     `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                                     `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
                                     `description` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
                                     `difficulty` int(11) NULL DEFAULT NULL COMMENT '难度',
                                     `lesson_count` int(11) NULL DEFAULT NULL COMMENT '课时数量',
                                     `e_book` int(11) NULL DEFAULT NULL COMMENT '类型',
                                     `course_num` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程数量',
                                     `exercise_count` int(11) NULL DEFAULT NULL COMMENT '实践课数量',
                                     `theory_count` int(11) NULL DEFAULT NULL COMMENT '理论课数量',
                                     `direction` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程方向',
                                     `direction_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方向名称',
                                     `interview` int(11) NULL DEFAULT NULL COMMENT '访问权限',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 270 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks初始化课程数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_course_related
-- ----------------------------

CREATE TABLE `rtks_course_related`  (
                                        `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程id',
                                        `related_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点id',
                                        `type` int(11) NOT NULL COMMENT '类型：1：知识点;2：技能点（已取消）',
                                        `weight` int(11) NOT NULL COMMENT '权重',
                                        INDEX `course_id`(`resource_id`) USING BTREE,
                                        INDEX `related_id`(`related_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks知识点课程关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_engage
-- ----------------------------

CREATE TABLE `rtks_engage`  (
                                `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                                `category_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别id',
                                `sub_category_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '子类别id',
                                `name_zh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文名称',
                                `name_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '英文名称',
                                PRIMARY KEY (`id`) USING BTREE,
                                INDEX `sub_category_id`(`sub_category_id`) USING BTREE,
                                INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'engage框架表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_engage_category
-- ----------------------------

CREATE TABLE `rtks_engage_category`  (
                                         `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                         `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                                         `name_zh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文名称',
                                         `name_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '英文名称',
                                         PRIMARY KEY (`id`) USING BTREE,
                                         UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'engage框架类别表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_engage_sub_category
-- ----------------------------

CREATE TABLE `rtks_engage_sub_category`  (
                                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                             `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                                             `category_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别id',
                                             `name_zh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文名称',
                                             `name_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '英文名称',
                                             PRIMARY KEY (`id`) USING BTREE,
                                             INDEX `category_id`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'engage框架子类别表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_engage_train_relation
-- ----------------------------

CREATE TABLE `rtks_engage_train_relation`  (
                                               `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                               `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源id',
                                               `engage_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'engage框架id',
                                               `business_template_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '训练模板id',
                                               `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                               `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                               PRIMARY KEY (`id`) USING BTREE,
                                               INDEX `business_template_id`(`business_template_id`) USING BTREE,
                                               INDEX `engage_id`(`engage_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'engage场景训练关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_knowledge
-- ----------------------------

CREATE TABLE `rtks_knowledge`  (
                                   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                   `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                                   `category_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别id',
                                   `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
                                   `courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联课程列表',
                                   `create_time` datetime NOT NULL COMMENT '创建时间',
                                   `update_time` datetime NOT NULL COMMENT '更新时间',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 363 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks知识点表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_knowledge_category
-- ----------------------------

CREATE TABLE `rtks_knowledge_category`  (
                                            `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                            `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                                            `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
                                            `create_time` datetime NOT NULL COMMENT '创建时间',
                                            `update_time` datetime NOT NULL COMMENT '更新时间',
                                            PRIMARY KEY (`id`) USING BTREE,
                                            UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks知识点类别表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_level
-- ----------------------------

CREATE TABLE `rtks_level`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                               `resource_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                               `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
                               `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
                               `parent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级id',
                               `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                               `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                               `frame` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '框架id',
                               PRIMARY KEY (`id`) USING BTREE,
                               UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks框架层级表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_role
-- ----------------------------

CREATE TABLE `rtks_role`  (
                              `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                              `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                              `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
                              `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
                              `create_time` datetime NOT NULL COMMENT '创建时间',
                              `update_time` datetime NOT NULL COMMENT '更新时间',
                              `level_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '层级id',
                              `category` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别',
                              `task_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务列表',
                              `knowledge_id_weights` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点权重列表',
                              `knowledge_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点列表',
                              `skill_id_weights` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能点权重列表',
                              `skill_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能点列表',
                              `ability_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '能力列表',
                              `skill_courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '技能课程列表',
                              `knowledge_courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '知识点课程列表',
                              `knowledge_weight` tinyint(4) NOT NULL DEFAULT 50 COMMENT '知识点比例',
                              `skill_missions` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '[]' COMMENT '技能任务列表',
                              PRIMARY KEY (`id`) USING BTREE,
                              UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 126 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_role_match_record
-- ----------------------------

CREATE TABLE `rtks_role_match_record`  (
                                           `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                           `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
                                           `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
                                           `match` tinyint(4) NOT NULL COMMENT '匹配度',
                                           `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                           `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                           PRIMARY KEY (`id`) USING BTREE,
                                           UNIQUE INDEX `user_role`(`user_id`, `role_id`) USING BTREE,
                                           INDEX `role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks角色匹配度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_role_related
-- ----------------------------

CREATE TABLE `rtks_role_related`  (
                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                      `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `related_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `type` int(11) NOT NULL,
                                      PRIMARY KEY (`id`) USING BTREE,
                                      INDEX `role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_role_skill_relation
-- ----------------------------

CREATE TABLE `rtks_role_skill_relation`  (
                                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                             `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
                                             `skill_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能id',
                                             `weight` tinyint(4) NOT NULL COMMENT '权重',
                                             `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                             `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                             PRIMARY KEY (`id`) USING BTREE,
                                             INDEX `role`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks角色技能点关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_situation_log
-- ----------------------------

CREATE TABLE `rtks_situation_log`  (
                                       `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                       `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
                                       `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
                                       `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
                                       `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
                                       `type` tinyint(1) NOT NULL COMMENT '类型：1.合格;2优秀',
                                       `create_time` datetime NOT NULL COMMENT '创建时间',
                                       PRIMARY KEY (`id`) USING BTREE,
                                       UNIQUE INDEX `user_role_type`(`user_id`, `role_id`, `type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks角色态势动态表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_skill
-- ----------------------------

CREATE TABLE `rtks_skill`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                               `resource_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                               `content` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
                               `create_time` datetime NOT NULL COMMENT '创建时间',
                               `update_time` datetime NOT NULL COMMENT '更新时间',
                               `system_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '体系id',
                               `category_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '类别id',
                               `courses` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '[]' COMMENT '课程列表',
                               PRIMARY KEY (`id`) USING BTREE,
                               UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks技能点表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_skill_category
-- ----------------------------

CREATE TABLE `rtks_skill_category`  (
                                        `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                        `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                                        `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
                                        `create_time` datetime NOT NULL COMMENT '创建时间',
                                        `update_time` datetime NOT NULL COMMENT '更新时间',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE INDEX `resource_id_UNIQUE`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks技能点类别表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_skill_mission_relation
-- ----------------------------

CREATE TABLE `rtks_skill_mission_relation`  (
                                                `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                                `skill_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技能点id',
                                                `mission_template_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务模板id',
                                                `weight` tinyint(4) NOT NULL COMMENT '权重',
                                                `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                                `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                                PRIMARY KEY (`id`) USING BTREE,
                                                INDEX `skill`(`skill_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks技能点任务关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_skill_system
-- ----------------------------

CREATE TABLE `rtks_skill_system`  (
                                      `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                      `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                                      `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
                                      `create_time` datetime NOT NULL COMMENT '创建时间',
                                      `update_time` datetime NOT NULL COMMENT '更新时间',
                                      `scene_id` int(11) NOT NULL COMMENT '场景id',
                                      PRIMARY KEY (`id`) USING BTREE,
                                      UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks技能点体系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_task
-- ----------------------------

CREATE TABLE `rtks_task`  (
                              `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                              `resource_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
                              `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
                              `create_time` datetime NOT NULL COMMENT '创建时间',
                              `update_time` datetime NOT NULL COMMENT '更新时间',
                              PRIMARY KEY (`id`) USING BTREE,
                              UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 134 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rtks_user_role
-- ----------------------------

CREATE TABLE `rtks_user_role`  (
                                   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                   `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
                                   `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
                                   `role_name` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
                                   `create_time` datetime NOT NULL COMMENT '创建时间',
                                   `update_time` datetime NOT NULL COMMENT '更新时间',
                                   `user_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `user_uni`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'rtks用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------

CREATE TABLE `sys_user`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                             `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
                             `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
                             `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
                             `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '账号是否可用。默认为1（可用）',
                             `avatar` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
                             `admin` tinyint(1) NULL DEFAULT NULL COMMENT '是否管理员。0：非管理员，1：管理员',
                             `expire_time` datetime NULL DEFAULT NULL COMMENT '过期时间',
                             `notes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
                             `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                             `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
                             `online_time` double(16, 2) NULL DEFAULT NULL COMMENT '在线时间',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `username`(`username`) USING BTREE,
                             UNIQUE INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_property
-- ----------------------------

CREATE TABLE `system_property`  (
                                    `id` int(11) NOT NULL AUTO_INCREMENT,
                                    `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置名称',
                                    `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置描述',
                                    `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置值',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE INDEX `idx_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_use_status
-- ----------------------------

CREATE TABLE `system_use_status`  (
                                      `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                      `cpu` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'cpu使用率',
                                      `disk` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '磁盘使用率',
                                      `ram` tinyint(4) NOT NULL COMMENT '内存使用率',
                                      `alter_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                      `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4697 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统使用状态表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
