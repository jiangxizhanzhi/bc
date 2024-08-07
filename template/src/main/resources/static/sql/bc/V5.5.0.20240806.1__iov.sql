CREATE TABLE `iov_dut`
(
    `id`              int(11)                                                 NOT NULL AUTO_INCREMENT,
    `resource_id`     varchar(32)                                             NOT NULL COMMENT 'id',
    `name`            varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
    `ecu`             tinyint                                                 NOT NULL COMMENT 'ECU单元',
    `model`           tinyint                                                 NOT NULL COMMENT '车型',
    `logo`            varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'logo',
    `area`            tinyint                                                 NOT NULL COMMENT '域类型',
    `interface_type`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务id',
    `interface_param` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务id',
    `status`          tinyint                                                 NOT NULL COMMENT '状态',
    `enabled`         tinyint                                                 NOT NULL COMMENT '是否可用',
    `create_time`     datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`     datetime                                                NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE,
    INDEX `model_area` (`model`, `area`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

CREATE TABLE `iov_frame`
(
    `id`          int(11)                                                 NOT NULL AUTO_INCREMENT,
    `resource_id` varchar(32)                                             NOT NULL COMMENT 'id',
    `name`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
    `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '拓扑描述',
    `model`       tinyint                                                 NOT NULL COMMENT '车型',
    `scale`       smallint                                                NOT NULL COMMENT '拓扑规模',
    `creator`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户id',
    `create_time` datetime                                                NULL     DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime                                                NULL     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `resource_id` (`resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;


INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 41, '比亚迪秦-PLUS', 'BYD QIN PLUS');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 41, 'Tesla', 'Tesla');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 42, '车身控制域', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 42, '高级辅助驾驶域', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 42, '动力域', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 4, 42, '智能座舱域', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 5, 42, '底盘域', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 6, 42, '智能网联域', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 11, 43, '车门', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 12, 43, '车窗', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 13, 43, '后视镜', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 14, 43, '雨刮器', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 21, 43, '轮胎压力监测系统', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 22, 43, '摄像头传感器', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 23, 43, '超声波传感器', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 24, 43, '气候控制单元', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 31, 43, '变速箱控制单元', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 32, 43, '发动机控制单元', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 33, 43, '电池控制单元', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 41, 43, 'GPS导航', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 42, 43, '视频播放器', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 43, 43, '音乐流媒体', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 44, 43, 'USB', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 45, 43, '蓝牙', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 46, 43, 'WIFI', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 51, 43, '转向控制单元', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 52, 43, '悬架控制单元', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 53, 43, '制动控制单元', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 61, 43, '远程访问', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 62, 43, '速度控制', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 63, 43, '车辆碰撞通知', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 64, 43, '紧急呼叫', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 65, 43, 'GPU定位车辆', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 1, 44, 'CAN', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 2, 44, 'WIFI', '');
INSERT INTO `cr_constants_config` VALUES (DEFAULT, NULL, 3, 44, '蓝牙', '');