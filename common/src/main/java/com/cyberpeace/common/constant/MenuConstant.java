package com.cyberpeace.common.constant;

/**
 * 菜单对应值，由前端指定, 不能轻易变更
 *
 * @author SN
 * @date 2024/11/07
 */
public interface MenuConstant {

    /**
     * 前台页面
     */
    // 所有前端
    String WEB_ALL = "front_all";

    /**
     * Web OJ 课程
     */// 我的职业
    String WEB_OJ_COURSE = "oj-course";

    /**
     * Web OJ 所有课程
     */// 全部课程
    String WEB_OJ_ALL_COURSE = "oj-all-course";

    /**
     * Web OJ 知识课程
     *///知识课程
    String WEB_OJ_KNOWLEDGE_COURSE = "oj-knowledge-course";

    /**
     * WebOJ理论练习
     *///理论题
    String WEB_OJ_THEORY_PRACTICE = "oj-practice";

    /**
     * WebOJ练习实操
     *///实操题
    String WEB_OJ_PRACTICE_OPERATION = "oj-practice-capture-flag";

    /**
     * Web靶场单训练
     *///单兵训练
    String WEB_CR_SINGLE_TRAIN = "single-list";

    /**
     * Web靶场团队训练
     *///团队训练
    String WEB_CR_TEAM_TRAIN = "group-training";

    /**
     * Web靶场红与蓝
     *///红蓝对抗
    String WEB_CR_RED_VS_BLUE = "red-vs-blue";

    /**
     * Web靶场工具工具
     *///工具库
    String WEB_CR_TOOL_TOOLS = "tool-magazine";

    /**
     * Web靶场工具exp （英文）
     *///EXP库
    String WEB_CR_TOOL_EXP = "tool-exp";

    /**
     * Web靶场工具shell代码
     *///ShellCode库
    String WEB_CR_TOOL_SHELLCODE = "tool-shell";

    /**
     * Web靶场工具GHDB 数据库
     *///GHDB库
    String WEB_CR_TOOL_GHDB = "ghdb-library";

    /**
     * Web 人员中心
     *///个人中心
    String WEB_PERSON_CENTER = "personal-folder";

    /**
     * Web靶场评估
     *///靶场测评
    String WEB_CR_EVALUATION = "range-test-self";

    /**
     * 后台页面
     */
    // 仪表盘
    String CMS_INDEX = "index";

    /**
     * CMS 课程
     *///课程管理
    String CMS_COURSES = "courses";

    /**
     * CMS 教室
     *///课程表
    String CMS_CLASS_ROOM = "class-card";

    /**
     * CMS 课程统计
     *///学习统计
    String CMS_COURSE_STATISTICS = "learn-statistics";

    /**
     * CMS 系列RTKSncwf框架
     *///NCWF框架
    String CMS_RTKS_NCWF_FRAME = "career-system";

    /**
     * CMS 系列攻击框架
     *///ATT&CK框架
    String CMS_ATTACK_FRAME = "att-ck-frame";

    /**
     * CMS 系列聘框架
     *///Engage框架
    String CMS_ENGAGE_FRAME = "engage-frame";

    /**
     * CMS 练习
     *///习题集
    String CMS_EXERCISES = "exercises";

    /**
     * CMS 系列理论练习
     *///理论基础
    String CMS_THEORY_PRACTICE = "theory-base";

    /**
     * CMS 系列练习实操
     *///实践操作
    String CMS_PRACTICE_OPERATION = "flags-solve";

    /**
     * CMS 设备管理
     *///镜像管理
    String CMS_DEVICE_MANAGE = "image-manage";

    /**
     * CMS 实体设备管理
     *///实装管理
    String CMS_ENTITY_DEVICE_MANAGE = "real-manage";

    /**
     * CMS 系列Service管理
     *///服务管理
    String CMS_SERVICE_MANAGE = "service-manage";

    /**
     * CMS 系列靶场任务方案
     *///任务方案
    String CMS_CR_MISSION_SCHEME = "mission-arrange-management";

    /**
     * CMS 后台流量
     *///背景流量
    String CMS_BACKGROUND_TRAFFIC = "static-traffic-list";

    /**
     * CMS 攻击流量
     *///攻击流量
    String CMS_ATTACK_TRAFFIC = "dynamic-traffic-list";

    /**
     * CMS 流量收集
     *///流量集合
    String CMS_TRAFFIC_COLLECTION = "traffic-manage-folder";

    /**
     * CMS 系列靶场单训练
     *///单兵训练
    String CMS_CR_SINGLE_TRAIN = "cr-individual-training";

    /**
     * CMS 系列靶场团队训练
     *///团队训练
    String CMS_CR_TEAM_TRAIN = "cr-team-training";

    /**
     * CMS 红色与蓝色
     *///红蓝对抗
    String CMS_RED_VS_BLUE = "red-blue";

    /**
     * CMS 工具
     *///工具库
    String CMS_TOOL = "tool";

    /**
     * CMS 系统配置
     *///系统配置
    String CMS_SYSTEM_CONFIG = "system-config";

    /**
     * CMS 审计日志
     *///审计日志
    String CMS_AUDIT_LOG = "audit-log";

    /**
     * CMS 系统日志
     *///运行日志
    String CMS_SYSTEM_LOG = "system-log";

    /**
     * CMS 用户管理
     *///用户管理
    String CMS_USER_MANAGE = "user-manage";

    /**
     * CMS 角色管理
     *///角色管理
    String CMS_ROLE_MANAGE = "role-management";

    /**
     * CMS 系统集群管理
     *///计算实例管理
    String CMS_SYSTEM_CLUSTER_MANAGE = "cluster-management";

    /**
     * CMS 系统网络管理
     *///网路实例管理
    String CMS_SYSTEM_NETWORK_MANAGE = "network-management";

    /**
     * CMS 系统实体管理
     *///实装设备管理
    String CMS_SYSTEM_ENTITY_MANAGE = "real-equipment-management";

    /**
     * CMS 系统实例监控器
     *///靶场实例监控
    String CMS_SYSTEM_INSTANCE_MONITOR = "instance-monitoring";

    /**
     * CMS System 云平台监控器
     *///云平台监控
    String CMS_SYSTEM_CLOUD_PLATFORM_MONITOR = "cloud-platform-monitoring";

    /**
     * CMS 系统资源使用管理
     *///资源用量管理
    String CMS_SYSTEM_RESOURCE_USAGE_MANAGEMENT = "resource-usage-management";

    /**
     * CMS 系统通知
     *///系统公告
    String CMS_SYSTEM_NOTICE = "system-notice-folder";

    /**
     * CMS 通用探头
     *///通用探针
    String CMS_UNIVERSAL_PROBE = "universal-probe";

    /**
     * CMS 定制探头
     *///自定义探针
    String CMS_CUSTOM_PROBE = "custom-probe";

    /**
     * CMS 采集模板探测
     *///采集模板
    String CMS_COLLECTION_TEMPLATE_PROBE = "collection-template";

    /**
     * CMS 评论管理
     *///留言管理
    String CMS_COMMENT_MANAGE = "message-management";

    /**
     * CMS 系列靶场评估算法
     *///评估算法管理
    String CMS_CR_EVALUATION_ALGORITHM = "assessment-algorithm";

    /**
     * CMS 系列靶场评估指示器
     *///指标项管理
    String CMS_CR_EVALUATION_INDICATOR = "assessment-model";

    /**
     * CMS 系列靶场评估指示器系统
     *///指标体系管理
    String CMS_CR_EVALUATION_INDICATOR_SYSTEM = "assessment-indicators";

    /**
     * CMS 系列靶场单训练统计学
     *///单兵训练统计
    String CMS_CR_SINGLE_TRAIN_STATISTICS = "individual-training-statistics";

    /**
     * CMS 系列靶场团队训练统计学
     *///团队训练统计
    String CMS_CR_TEAM_TRAIN_STATISTICS = "team-training-statistics";

    /**
     * CMS 系列靶场RB训练统计学
     *///红蓝对抗统计
    String CMS_CR_RB_TRAIN_STATISTICS = "red-vs-blue-statistics";
}
