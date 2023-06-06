package com.cyberpeace.common.constant;

/**
 * 菜单对应值，由前端指定, 不能轻易变更
 */
public interface MenuConstant {

    /**
     * 前台页面
     */
    // 所有前端
    String WEB_ALL = "front_all";

    // 我的职业
    String WEB_OJ_COURSE = "oj-course";

    // 全部课程
    String WEB_OJ_ALL_COURSE = "course-folder";

    //知识课程
    String WEB_OJ_KNOWLEDGE_COURSE = "oj-knowledge-course";

    //理论题
    String WEB_OJ_THEORY_PRACTICE = "oj-practice";

    //实操题
    String WEB_OJ_PRACTICE_OPERATION = "oj-practice-capture-flag";

    //单兵训练
    String WEB_CR_SINGLE_TRAIN = "single-list";

    //团队训练
    String WEB_CR_TEAM_TRAIN = "group-training";

    //红蓝对抗
    String WEB_CR_RED_VS_BLUE = "red-vs-blue-web-folder";

    //工具库
    String WEB_CR_TOOL_TOOLS = "tool-magazine";

    //EXP库
    String WEB_CR_TOOL_EXP = "tool-exp";

    //ShellCode库
    String WEB_CR_TOOL_SHELLCODE = "tool-shell";

    //GHDB库
    String WEB_CR_TOOL_GHDB = "ghdb-library";

    //个人中心
    String WEB_PERSON_CENTER = "personal-folder";

    //靶场测评
    String WEB_CR_EVALUATION = "range-test";

    /**
     * 后台页面
     */
    // 仪表盘
    String CMS_INDEX = "index";

    //课程管理
    String CMS_COURSES = "courses";

    //课程表
    String CMS_CLASS_ROOM = "class-card";

    //学习统计
    String CMS_COURSE_STATISTICS = "learn-statistics";

    //NCWF框架
    String CMS_RTKS_NCWF_FRAME = "career-system";

    //ATT&CK框架
    String CMS_ATTACK_FRAME = "att-ck-frame";

    //Engage框架
    String CMS_ENGAGE_FRAME = "engage-frame";

    //习题集管理
    String CMS_EXERCISES = "exercises";

    //理论基础
    String CMS_THEORY_PRACTICE = "theory-base";

    //实践操作
    String CMS_PRACTICE_OPERATION = "flags-solve";

    //镜像管理
    String CMS_DEVICE_MANAGE = "image-manage";

    //实装管理
    String CMS_ENTITY_DEVICE_MANAGE = "real-manage";

    //服务管理
    String CMS_SERVICE_MANAGE = "service-manage";

    //任务方案
    String CMS_CR_MISSION_SCHEME = "cr-mission-arrange-folder";

    //场景管理
    String CMS_CR_SCENE_MANAGE = "cr-scene-management-folder";

    //背景流量
    String CMS_BACKGROUND_TRAFFIC = "static-traffic-list";

    //攻击流量
    String CMS_ATTACK_TRAFFIC = "dynamic-traffic-list";

    //流量集合
    String CMS_TRAFFIC_COLLECTION = "traffic-manage-folder";

    //单兵训练
    String CMS_CR_SINGLE_TRAIN = "cr-individual-training";

    //团队训练
    String CMS_CR_TEAM_TRAIN = "cr-team-training";

    //红蓝对抗 定制化菜单-修改-场景编排
    String CMS_RED_VS_BLUE = "red-blue";

    //红蓝对抗 定制化菜单-修改-对抗训练
    String CMS_RED_VS_BLUE_ARRANGE = "red-vs-blue-arrange";

    //工具库
    String CMS_TOOL = "tool";

    //系统配置
    String CMS_SYSTEM_CONFIG = "system-config";

    //审计日志
    String CMS_AUDIT_LOG = "audit-log";

    //运行日志
    String CMS_SYSTEM_LOG = "system-log";

    //用户管理
    String CMS_USER_MANAGE = "user-manage";

    //角色管理
    String CMS_ROLE_MANAGE = "role-management";

    //计算实例管理
    String CMS_SYSTEM_CLUSTER_MANAGE = "cluster-management";

    //网路实例管理
    String CMS_SYSTEM_NETWORK_MANAGE = "network-management";

    //实装设备管理
    String CMS_SYSTEM_ENTITY_MANAGE = "real-equipment-management";

    //靶场实例监控
    String CMS_SYSTEM_INSTANCE_MONITOR = "instance-monitoring";

    //云平台监控
    String CMS_SYSTEM_CLOUD_PLATFORM_MONITOR = "cloud-platform-monitoring";

    //资源用量管理
    String CMS_SYSTEM_RESOURCE_USAGE_MANAGEMENT = "resource-usage-management";

    //系统公告
    String CMS_SYSTEM_NOTICE = "system-notice-folder";

    //通用探针
    String CMS_UNIVERSAL_PROBE = "universal-probe";

    //自定义探针
    String CMS_CUSTOM_PROBE = "custom-probe";

    //采集模板
    String CMS_COLLECTION_TEMPLATE_PROBE = "collection-template";

    //留言管理
    String CMS_COMMENT_MANAGE = "message-management";

    //评估算法管理
    String CMS_CR_EVALUATION_ALGORITHM = "assessment-algorithm";

    //指标项管理
    String CMS_CR_EVALUATION_INDICATOR = "assessment-model";

    //指标体系管理
    String CMS_CR_EVALUATION_INDICATOR_SYSTEM = "assessment-indicators";

    //单兵训练统计
    String CMS_CR_SINGLE_TRAIN_STATISTICS = "individual-training-statistics";

    //团队训练统计
    String CMS_CR_TEAM_TRAIN_STATISTICS = "team-training-statistics";

    //红蓝对抗统计
    String CMS_CR_RB_TRAIN_STATISTICS = "red-vs-blue-statistics";
}
