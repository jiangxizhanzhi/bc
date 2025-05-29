package com.cyberpeace.common.constant;

/**
 * 菜单对应值，由前端指定, 不能轻易变更
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `MenuConstant` 的 Java 接口，主要用于存储系统中菜单项的常量值。这些常量值在前后端交互中作为菜单标识符使用，具有固定的含义且不易更改。以下是简要分析： 
 * ### 主要特点： 
 * 1. **功能定位**： 
 * - 该接口定义了系统的菜单项名称，包括前台（Web）和后台（CMS）两个部分。 
 * - 前台菜单项主要用于面向用户的界面，如课程、靶场训练等。 
 * - 后台菜单项主要用于管理员或后台人员的功能模块，如用户管理、系统配置、日志管理等。 
 * 2. **结构清晰**： 
 * - 每个菜单项都有注释说明其用途，方便开发者理解。 
 * - 前台和后台的菜单项通过命名区分，例如 `WEB_` 和 `CMS_` 前缀。 
 * 3. **字符串常量**： 
 * - 所有菜单项的值均为 `String` 类型，便于在代码中直接引用。 
 * 4. **用途广泛**： 
 * - 这些常量可以用于前端路由、后端接口调用、权限控制等多个场景。 
 * ### 示例分类： 
 * - **前台菜单**：主要面向普通用户，如课程管理、靶场训练等。 
 * - **后台菜单**：主要面向管理员，如用户管理、日志记录、系统配置等。 
 * ### 总结： 
 * 该接口的设计目的是提供一个统一的菜单项常量集合，确保前后端开发的一致性，并减少硬编码的风险。通过这种方式，可以提高代码的可维护性和可读性。 
**/
public interface MenuConstant {

    /**
     * 前台页面
     *
     * @see String
     */
    // 所有前端
    String WEB_ALL = "front_all";

    /**
     * Web OJ 课程
     *
     * @see String
     */ // 我的职业
    String WEB_OJ_COURSE = "oj-course";

    /**
     * Web OJ 所有课程
     *
     * @see String
     */ // 全部课程
    String WEB_OJ_ALL_COURSE = "oj-all-course";

    /**
     * Web OJ 知识课程
     *
     * @see String
     */ //知识课程
    String WEB_OJ_KNOWLEDGE_COURSE = "oj-knowledge-course";

    /**
     * WebOJ理论实践
     *
     * @see String
     */ //理论题
    String WEB_OJ_THEORY_PRACTICE = "oj-practice";

    /**
     * WebOJ实践实操
     *
     * @see String
     */ //实操题
    String WEB_OJ_PRACTICE_OPERATION = "oj-practice-capture-flag";

    /**
     * Web靶场单训练
     *
     * @see String
     */ //单兵训练
    String WEB_CR_SINGLE_TRAIN = "single-list";

    /**
     * Web靶场团队训练
     *
     * @see String
     */ //团队训练
    String WEB_CR_TEAM_TRAIN = "group-training";

    /**
     * Web靶场红与蓝
     *
     * @see String
     */ //红蓝对抗
    String WEB_CR_RED_VS_BLUE = "red-vs-blue";

    /**
     * Web靶场工具工具
     *
     * @see String
     */ //工具库
    String WEB_CR_TOOL_TOOLS = "tool-magazine";

    /**
     * Web靶场工具exp （英文）
     *
     * @see String
     */ //EXP库
    String WEB_CR_TOOL_EXP = "tool-exp";

    /**
     * Web靶场工具shell代码
     *
     * @see String
     */ //ShellCode库
    String WEB_CR_TOOL_SHELLCODE = "tool-shell";

    /**
     * Web靶场工具GHDB 数据库
     *
     * @see String
     */ //GHDB库
    String WEB_CR_TOOL_GHDB = "ghdb-library";

    /**
     * Web 人员中心
     *
     * @see String
     */ //个人中心
    String WEB_PERSON_CENTER = "personal-folder";

    /**
     * Web靶场评估
     *
     * @see String
     */ //靶场测评
    String WEB_CR_EVALUATION = "range-test-self";

    /**
     * 后台页面
     *
     * @see String
     */
    // 仪表盘
    String CMS_INDEX = "index";

    /**
     * CMS 课程
     *
     * @see String
     */ //课程管理
    String CMS_COURSES = "courses";

    /**
     * CMS 教室
     *
     * @see String
     */ //课程表
    String CMS_CLASS_ROOM = "class-card";

    /**
     * CMS 课程统计
     *
     * @see String
     */ //学习统计
    String CMS_COURSE_STATISTICS = "learn-statistics";

    /**
     * CMS 系列RTKSncwf框架
     *
     * @see String
     */ //NCWF框架
    String CMS_RTKS_NCWF_FRAME = "career-system";

    /**
     * CMS 系列攻击框架
     *
     * @see String
     */ //ATT&CK框架
    String CMS_ATTACK_FRAME = "att-ck-frame";

    /**
     * CMS 系列聘框架
     *
     * @see String
     */ //Engage框架
    String CMS_ENGAGE_FRAME = "engage-frame";

    /**
     * CMS 练习
     *
     * @see String
     */ //习题集
    String CMS_EXERCISES = "exercises";

    /**
     * CMS 系列理论实践
     *
     * @see String
     */ //理论基础
    String CMS_THEORY_PRACTICE = "theory-base";

    /**
     * CMS 系列实践实操
     *
     * @see String
     */ //实践操作
    String CMS_PRACTICE_OPERATION = "flags-solve";

    /**
     * CMS 设备管理
     *
     * @see String
     */ //镜像管理
    String CMS_DEVICE_MANAGE = "image-manage";

    /**
     * CMS 实体设备管理
     *
     * @see String
     */ //实装管理
    String CMS_ENTITY_DEVICE_MANAGE = "real-manage";

    /**
     * CMS 系列Service管理
     *
     * @see String
     */ //服务管理
    String CMS_SERVICE_MANAGE = "service-manage";

    /**
     * CMS 系列靶场任务方案
     *
     * @see String
     */ //任务方案
    String CMS_CR_MISSION_SCHEME = "mission-arrange-management";

    /**
     * CMS 后台流量
     *
     * @see String
     */ //背景流量
    String CMS_BACKGROUND_TRAFFIC = "static-traffic-list";

    /**
     * CMS 攻击流量
     *
     * @see String
     */ //攻击流量
    String CMS_ATTACK_TRAFFIC = "dynamic-traffic-list";

    /**
     * CMS 流量收集
     *
     * @see String
     */ //流量集合
    String CMS_TRAFFIC_COLLECTION = "traffic-manage-folder";

    /**
     * CMS 系列靶场单训练
     *
     * @see String
     */ //单兵训练
    String CMS_CR_SINGLE_TRAIN = "cr-individual-training";

    /**
     * CMS 系列靶场团队训练
     *
     * @see String
     */ //团队训练
    String CMS_CR_TEAM_TRAIN = "cr-team-training";

    /**
     * CMS 红色与蓝色
     *
     * @see String
     */ //红蓝对抗
    String CMS_RED_VS_BLUE = "red-blue";

    /**
     * CMS 工具
     *
     * @see String
     */ //工具库
    String CMS_TOOL = "tool";

    /**
     * CMS 系统配置
     *
     * @see String
     */ //系统配置
    String CMS_SYSTEM_CONFIG = "system-config";

    /**
     * CMS 审计日志
     *
     * @see String
     */ //审计日志
    String CMS_AUDIT_LOG = "audit-log";

    /**
     * CMS 系统日志
     *
     * @see String
     */ //运行日志
    String CMS_SYSTEM_LOG = "system-log";

    /**
     * CMS 用户管理
     *
     * @see String
     */ //用户管理
    String CMS_USER_MANAGE = "user-manage";

    /**
     * CMS 系列岗位管理
     *
     * @see String
     */ //角色管理
    String CMS_ROLE_MANAGE = "role-management";

    /**
     * CMS 系统集群管理
     *
     * @see String
     */ //计算实例管理
    String CMS_SYSTEM_CLUSTER_MANAGE = "cluster-management";

    /**
     * CMS 系统网络管理
     *
     * @see String
     */ //网路实例管理
    String CMS_SYSTEM_NETWORK_MANAGE = "network-management";

    /**
     * CMS 系统实体管理
     *
     * @see String
     */ //实装设备管理
    String CMS_SYSTEM_ENTITY_MANAGE = "real-equipment-management";

    /**
     * CMS 系统实例监控器
     *
     * @see String
     */ //靶场实例监控
    String CMS_SYSTEM_INSTANCE_MONITOR = "instance-monitoring";

    /**
     * CMS System 云平台监控器
     *
     * @see String
     */ //云平台监控
    String CMS_SYSTEM_CLOUD_PLATFORM_MONITOR = "cloud-platform-monitoring";

    /**
     * CMS 系统资源使用管理
     *
     * @see String
     */ //资源用量管理
    String CMS_SYSTEM_RESOURCE_USAGE_MANAGEMENT = "resource-usage-management";

    /**
     * CMS 系统通知
     *
     * @see String
     */ //系统公告
    String CMS_SYSTEM_NOTICE = "system-notice-folder";

    /**
     * CMS 通用探头
     *
     * @see String
     */ //通用探针
    String CMS_UNIVERSAL_PROBE = "universal-probe";

    /**
     * CMS 系列自定义探针
     *
     * @see String
     */ //自定义探针
    String CMS_CUSTOM_PROBE = "custom-probe";

    /**
     * CMS 采集模板探测
     *
     * @see String
     */ //采集模板
    String CMS_COLLECTION_TEMPLATE_PROBE = "collection-template";

    /**
     * CMS 评论管理
     *
     * @see String
     */ //留言管理
    String CMS_COMMENT_MANAGE = "message-management";

    /**
     * CMS 系列靶场评估算法
     *
     * @see String
     */ //评估算法管理
    String CMS_CR_EVALUATION_ALGORITHM = "assessment-algorithm";

    /**
     * CMS 系列靶场评估指示器
     *
     * @see String
     */ //指标项管理
    String CMS_CR_EVALUATION_INDICATOR = "assessment-model";

    /**
     * CMS 系列靶场评估指示器系统
     *
     * @see String
     */ //指标体系管理
    String CMS_CR_EVALUATION_INDICATOR_SYSTEM = "assessment-indicators";

    /**
     * CMS 系列靶场单训练统计学
     *
     * @see String
     */ //单兵训练统计
    String CMS_CR_SINGLE_TRAIN_STATISTICS = "individual-training-statistics";

    /**
     * CMS 系列靶场团队训练统计学
     *
     * @see String
     */ //团队训练统计
    String CMS_CR_TEAM_TRAIN_STATISTICS = "team-training-statistics";

    /**
     * CMS 系列靶场RB训练统计学
     *
     * @see String
     */ //红蓝对抗统计
    String CMS_CR_RB_TRAIN_STATISTICS = "red-vs-blue-statistics";
}

