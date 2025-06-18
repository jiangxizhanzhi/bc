package com.cyberpeace.integratation.service.bo.mp;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class YXLogUrlEnum {

    public static final String CONNECTOR = "__method__";
    public static final String POST = "POST";
    public static final String DELETE = "DELETE";
    public static final String GET = "GET";
    public static final String PATCH = "PATCH";
    public static final String PUT = "PUT";

    // 模块
    @Getter
    public enum Type {
        COURSE("SN-2002001001", "课程"),
        THEORY_PRACTICE("SN-2002002001", "理论考题"),
        OPERATION_PRACTICE("SN-2002003001", "在线试验"),
        DIRECTION("SN-2002004001", "方向"),
        APT_REPORT("SN-2002004001","APT报告"),
        STRATEGY("SN-2003001001","策略"),
        TACTIC_ATTACK("SN-2004001001","攻击战法"),
        TACTIC_ATTACK_CATEGORY("SN-2004001002","攻击战法类别"),
        TACTIC_DEFENSE("SN-2004002001","防御战法"),
        TACTIC_DEFENSE_CATEGORY("SN-2004002002","防御战法类别"),
        TACTIC_SYSTEM("SN-2004003001", "技战法"),
        VULN("SN-2005001001","漏洞"),
        APT_FEATURE("SN-2005001002","APT组织"),
        RULE_LOADED_FILE_THREAT("SN-2005002001", "病毒威胁防御规则"),
        RULE_EVENT_INTEGRATION("SN-2005002002", "主机攻击防御规则"),
        RULE_TRAFFIC_DETECT("SN-2005002003", "网络攻击防御规则"),
        RULE_APT_THREAT_DETECT("SN-2005002004", "特定APT威胁检测规则"),
        RULE_SCORE_RANKING("SN-2005003001", "计分排行规则"),
        ROUND_MECHANISM("SN-2005003002", "轮次机制规则"),
        RULE_VIOLATION_JUDGE("SN-2005003003", "违规判定规则"),
        ALGORITHM("SN-2006001001","能力评估算法"),
        TOOL("SN-3001001001", "工具"),
        TOOL_CATEGORY("SN-3002001001", "工具分类"),
        FRAME("SN-4001001001", "行动框架"),
        FRAME_ACTION_TARGET_TYPE("SN-4001001002", "攻击目标类型"),
        FRAME_ACTION_PROCESS_TYPE("SN-4001001003", "威胁事件类型"),
        AI("SN-5001001001", "");
        private final String value;
        private final String desc;
        Type(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }
    @Getter
    public enum Action {
        CREATE("3","新增"), EDIT("4","编辑"), DELETE("5","删除"), EXPORT("6","导出"), IMPORT("7","导入");
        private final String value;
        private final String desc;
        Action(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

    // 定义值的数据结构
    @Getter
    public static class YXLogInfo {
        // getter方法
        private String type;
        private String content;
        private String action;

        public YXLogInfo(Type type, Action action) {
            this.type = type.value;
            this.action = action.value;
            this.content = action.desc + type.desc;
        }

    }

    private static final Map<String, YXLogInfo> URLS = new HashMap<>();

    static {
        // 初始化数据
        URLS.put(expandUrl("/ojj/oj/admin/course",POST), new YXLogInfo(Type.COURSE, Action.CREATE));
        URLS.put(expandUrl("/ojj/oj/admin/course",PATCH), new YXLogInfo(Type.COURSE, Action.EDIT));
        URLS.put(expandUrl("/ojj/oj/admin/course/batch",DELETE), new YXLogInfo(Type.COURSE, Action.DELETE));
        URLS.put(expandUrl("/ojj/oj/admin/course/batch_dumps",GET), new YXLogInfo(Type.COURSE, Action.EXPORT));
        URLS.put(expandUrl("/ojj/oj/admin/course/batch_loads/",POST), new YXLogInfo(Type.COURSE, Action.IMPORT));

        URLS.put(expandUrl("/ojj/oj/admin/theory",POST), new YXLogInfo(Type.THEORY_PRACTICE, Action.CREATE));
        URLS.put(expandUrl("/ojj/oj/admin/theory",PATCH), new YXLogInfo(Type.THEORY_PRACTICE, Action.EDIT));
        URLS.put(expandUrl("/ojj/oj/admin/theory",DELETE), new YXLogInfo(Type.THEORY_PRACTICE, Action.DELETE));
        URLS.put(expandUrl("/ojj/oj/admin/theory/batch_dumps",GET), new YXLogInfo(Type.THEORY_PRACTICE, Action.EXPORT));
        URLS.put(expandUrl("/ojj/oj/admin/theory/batch_loads",POST), new YXLogInfo(Type.THEORY_PRACTICE, Action.IMPORT));

        URLS.put(expandUrl("/ojj/oj/admin/practice/operation",POST), new YXLogInfo(Type.OPERATION_PRACTICE, Action.CREATE));
        URLS.put(expandUrl("/ojj/oj/admin/practice/operation",PATCH), new YXLogInfo(Type.OPERATION_PRACTICE, Action.EDIT));
        URLS.put(expandUrl("/ojj/oj/admin/practice/operation",DELETE), new YXLogInfo(Type.OPERATION_PRACTICE, Action.DELETE));
        URLS.put(expandUrl("/ojj/oj/admin/practice/operation/batch_dumps",GET), new YXLogInfo(Type.OPERATION_PRACTICE, Action.EXPORT));
        URLS.put(expandUrl("/ojj/oj/admin/practice/operation/batch_loads",POST), new YXLogInfo(Type.OPERATION_PRACTICE, Action.IMPORT));

        URLS.put(expandUrl("/ojj/oj/admin/course/direction",POST), new YXLogInfo(Type.DIRECTION, Action.CREATE));
        URLS.put(expandUrl("/ojj/oj/admin/course/direction",PATCH), new YXLogInfo(Type.DIRECTION, Action.EDIT));
        URLS.put(expandUrl("/ojj/oj/admin/course/direction/batch",DELETE), new YXLogInfo(Type.DIRECTION, Action.DELETE));

        URLS.put(expandUrl("/ojj/space/theory/space_apt_report",POST), new YXLogInfo(Type.APT_REPORT, Action.CREATE));
        URLS.put(expandUrl("/ojj/space/theory/space_apt_report",PATCH), new YXLogInfo(Type.APT_REPORT, Action.EDIT));
        URLS.put(expandUrl("/ojj/space/theory/space_apt_report",DELETE), new YXLogInfo(Type.APT_REPORT, Action.DELETE));
        URLS.put(expandUrl("/ojj/space/theory/space_apt_report/batch_dump",GET), new YXLogInfo(Type.APT_REPORT, Action.EXPORT));
        URLS.put(expandUrl("/ojj/space/theory/space_apt_report/batch_load",POST), new YXLogInfo(Type.APT_REPORT, Action.IMPORT));

        URLS.put(expandUrl("/ojj/space/strategy",POST), new YXLogInfo(Type.STRATEGY, Action.CREATE));
        URLS.put(expandUrl("/ojj/space/strategy",PATCH), new YXLogInfo(Type.STRATEGY, Action.EDIT));
        URLS.put(expandUrl("/ojj/space/strategy",DELETE), new YXLogInfo(Type.STRATEGY, Action.DELETE));

        URLS.put(expandUrl("/ojj/space/tactic/attack",POST), new YXLogInfo(Type.TACTIC_ATTACK, Action.CREATE));
        URLS.put(expandUrl("/ojj/space/tactic/attack",PATCH), new YXLogInfo(Type.TACTIC_ATTACK, Action.EDIT));
        URLS.put(expandUrl("/ojj/space/tactic/attack",DELETE), new YXLogInfo(Type.TACTIC_ATTACK, Action.DELETE));

        URLS.put(expandUrl("/ojj/space/tactic/attack/category",POST), new YXLogInfo(Type.TACTIC_ATTACK_CATEGORY, Action.CREATE));
        URLS.put(expandUrl("/ojj/space/tactic/attack/category",PATCH), new YXLogInfo(Type.TACTIC_ATTACK_CATEGORY, Action.EDIT));
        URLS.put(expandUrl("/ojj/space/tactic/attack/category",DELETE), new YXLogInfo(Type.TACTIC_ATTACK_CATEGORY, Action.DELETE));

        URLS.put(expandUrl("/ojj/space/tactic/defense",POST), new YXLogInfo(Type.TACTIC_DEFENSE, Action.CREATE));
        URLS.put(expandUrl("/ojj/space/tactic/defense",PATCH), new YXLogInfo(Type.TACTIC_DEFENSE, Action.EDIT));
        URLS.put(expandUrl("/ojj/space/tactic/defense",DELETE), new YXLogInfo(Type.TACTIC_DEFENSE, Action.DELETE));

        URLS.put(expandUrl("/ojj/space/tactic/defense/category",POST), new YXLogInfo(Type.TACTIC_DEFENSE_CATEGORY, Action.CREATE));
        URLS.put(expandUrl("/ojj/space/tactic/defense/category",PATCH), new YXLogInfo(Type.TACTIC_DEFENSE_CATEGORY, Action.EDIT));
        URLS.put(expandUrl("/ojj/space/tactic/defense/category",DELETE), new YXLogInfo(Type.TACTIC_DEFENSE_CATEGORY, Action.DELETE));

        URLS.put(expandUrl("/ojj/space/tactic/system",POST), new YXLogInfo(Type.TACTIC_SYSTEM, Action.CREATE));
        URLS.put(expandUrl("/ojj/space/tactic/system",PATCH), new YXLogInfo(Type.TACTIC_SYSTEM, Action.EDIT));
        URLS.put(expandUrl("/ojj/space/tactic/system",DELETE), new YXLogInfo(Type.TACTIC_SYSTEM, Action.DELETE));

        URLS.put(expandUrl("/vuln/admin/vuln/",POST), new YXLogInfo(Type.VULN, Action.CREATE));
        URLS.put(expandUrl("/vuln/admin/vuln/batch/",DELETE), new YXLogInfo(Type.VULN, Action.DELETE));
        URLS.put(expandUrl("/vuln/admin/vuln/export/",GET), new YXLogInfo(Type.VULN, Action.EXPORT));
        URLS.put(expandUrl("/vuln/admin/vuln/import/",POST), new YXLogInfo(Type.VULN, Action.IMPORT));

        URLS.put(expandUrl("/ojj/space/rule/space_apt_feature",POST), new YXLogInfo(Type.APT_FEATURE, Action.CREATE));
        URLS.put(expandUrl("/ojj/space/rule/space_apt_feature",PATCH), new YXLogInfo(Type.APT_FEATURE, Action.EDIT));
        URLS.put(expandUrl("/ojj/space/rule/space_apt_feature",DELETE), new YXLogInfo(Type.APT_FEATURE, Action.DELETE));

        URLS.put(expandUrl("/ojj/space/rule/space_loaded_file_threat_rule",POST), new YXLogInfo(Type.RULE_LOADED_FILE_THREAT, Action.CREATE));
        URLS.put(expandUrl("/ojj/space/rule/space_loaded_file_threat_rule",PATCH), new YXLogInfo(Type.RULE_LOADED_FILE_THREAT, Action.EDIT));
        URLS.put(expandUrl("/ojj/space/rule/space_loaded_file_threat_rule",DELETE), new YXLogInfo(Type.RULE_LOADED_FILE_THREAT, Action.DELETE));
        URLS.put(expandUrl("/ojj/space/rule/space_loaded_file_threat_rule/excel",GET), new YXLogInfo(Type.RULE_LOADED_FILE_THREAT, Action.EXPORT));

        URLS.put(expandUrl("/ojj/space/rule/space_event_integration_rule",POST), new YXLogInfo(Type.RULE_EVENT_INTEGRATION, Action.CREATE));
        URLS.put(expandUrl("/ojj/space/rule/space_event_integration_rule",PATCH), new YXLogInfo(Type.RULE_EVENT_INTEGRATION, Action.EDIT));
        URLS.put(expandUrl("/ojj/space/rule/space_event_integration_rule",DELETE), new YXLogInfo(Type.RULE_EVENT_INTEGRATION, Action.DELETE));
        URLS.put(expandUrl("/ojj/space/rule/space_event_integration_rule/excel",GET), new YXLogInfo(Type.RULE_EVENT_INTEGRATION, Action.EXPORT));

        URLS.put(expandUrl("/ojj/space/rule/space_traffic_detect_rule",POST), new YXLogInfo(Type.RULE_TRAFFIC_DETECT, Action.CREATE));
        URLS.put(expandUrl("/ojj/space/rule/space_traffic_detect_rule",PATCH), new YXLogInfo(Type.RULE_TRAFFIC_DETECT, Action.EDIT));
        URLS.put(expandUrl("/ojj/space/rule/space_traffic_detect_rule",DELETE), new YXLogInfo(Type.RULE_TRAFFIC_DETECT, Action.DELETE));
        URLS.put(expandUrl("/ojj/space/rule/space_traffic_detect_rule/excel",GET), new YXLogInfo(Type.RULE_TRAFFIC_DETECT, Action.EXPORT));

        URLS.put(expandUrl("/ojj/space/rule/space_apt_threat_detect_rule",POST), new YXLogInfo(Type.RULE_APT_THREAT_DETECT, Action.CREATE));
        URLS.put(expandUrl("/ojj/space/rule/space_apt_threat_detect_rule",PATCH), new YXLogInfo(Type.RULE_APT_THREAT_DETECT, Action.EDIT));
        URLS.put(expandUrl("/ojj/space/rule/space_apt_threat_detect_rule",DELETE), new YXLogInfo(Type.RULE_APT_THREAT_DETECT, Action.DELETE));
        URLS.put(expandUrl("/ojj/space/rule/space_apt_threat_detect_rule/excel",GET), new YXLogInfo(Type.RULE_APT_THREAT_DETECT, Action.EXPORT));

        URLS.put(expandUrl("/ojj/space/rule/space_score_ranking_rule",POST), new YXLogInfo(Type.RULE_SCORE_RANKING, Action.CREATE));
        URLS.put(expandUrl("/ojj/space/rule/space_score_ranking_rule",PATCH), new YXLogInfo(Type.RULE_SCORE_RANKING, Action.EDIT));
        URLS.put(expandUrl("/ojj/space/rule/space_score_ranking_rule",DELETE), new YXLogInfo(Type.RULE_SCORE_RANKING, Action.DELETE));

        URLS.put(expandUrl("/ojj/space/rule/space_round_mechanism_rule",POST), new YXLogInfo(Type.ROUND_MECHANISM, Action.CREATE));
        URLS.put(expandUrl("/ojj/space/rule/space_round_mechanism_rule",PATCH), new YXLogInfo(Type.ROUND_MECHANISM, Action.EDIT));
        URLS.put(expandUrl("/ojj/space/rule/space_round_mechanism_rule",DELETE), new YXLogInfo(Type.ROUND_MECHANISM, Action.DELETE));

        URLS.put(expandUrl("/ojj/space/rule/space_violation_judge_rule",POST), new YXLogInfo(Type.RULE_VIOLATION_JUDGE, Action.CREATE));
        URLS.put(expandUrl("/ojj/space/rule/space_violation_judge_rule",PATCH), new YXLogInfo(Type.RULE_VIOLATION_JUDGE, Action.EDIT));
        URLS.put(expandUrl("/ojj/space/rule/space_violation_judge_rule",DELETE), new YXLogInfo(Type.RULE_VIOLATION_JUDGE, Action.DELETE));

        URLS.put(expandUrl("/ojj/cr/eval/algorithm",POST), new YXLogInfo(Type.ALGORITHM, Action.CREATE));
        URLS.put(expandUrl("/ojj/cr/eval/algorithm",PUT), new YXLogInfo(Type.ALGORITHM, Action.EDIT));
        URLS.put(expandUrl("/ojj/cr/eval/algorithm",DELETE), new YXLogInfo(Type.ALGORITHM, Action.DELETE));

        URLS.put(expandUrl("/ojj/cr/admin/tools",POST), new YXLogInfo(Type.TOOL, Action.CREATE));
        URLS.put(expandUrl("/ojj/cr/admin/tools",PATCH), new YXLogInfo(Type.TOOL, Action.EDIT));
        URLS.put(expandUrl("/ojj/cr/admin/tools",DELETE), new YXLogInfo(Type.TOOL, Action.DELETE));

        URLS.put(expandUrl("/ojj/cr/admin/tool/category",POST), new YXLogInfo(Type.TOOL_CATEGORY, Action.CREATE));
        URLS.put(expandUrl("/ojj/cr/admin/tool/category",PATCH), new YXLogInfo(Type.TOOL_CATEGORY, Action.EDIT));
        URLS.put(expandUrl("/ojj/cr/admin/tool/category",DELETE), new YXLogInfo(Type.TOOL_CATEGORY, Action.DELETE));

        URLS.put(expandUrl("/ojj/space/action_frame/action_frame",POST), new YXLogInfo(Type.FRAME, Action.CREATE));
        URLS.put(expandUrl("/ojj/space/action_frame/action_frame",PATCH), new YXLogInfo(Type.FRAME, Action.EDIT));
        URLS.put(expandUrl("/ojj/space/action_frame/action_frame",DELETE), new YXLogInfo(Type.FRAME, Action.DELETE));

        URLS.put(expandUrl("/ojj/space/action_frame/target_type",POST), new YXLogInfo(Type.FRAME_ACTION_TARGET_TYPE, Action.CREATE));
        URLS.put(expandUrl("/ojj/space/action_frame/target_type",PATCH), new YXLogInfo(Type.FRAME_ACTION_TARGET_TYPE, Action.EDIT));
        URLS.put(expandUrl("/ojj/space/action_frame/target_type",DELETE), new YXLogInfo(Type.FRAME_ACTION_TARGET_TYPE, Action.DELETE));

        URLS.put(expandUrl("/ojj/space/action_frame/event_type",POST), new YXLogInfo(Type.FRAME_ACTION_PROCESS_TYPE, Action.CREATE));
        URLS.put(expandUrl("/ojj/space/action_frame/event_type",PATCH), new YXLogInfo(Type.FRAME_ACTION_PROCESS_TYPE, Action.EDIT));
        URLS.put(expandUrl("/ojj/space/action_frame/event_type",DELETE), new YXLogInfo(Type.FRAME_ACTION_PROCESS_TYPE, Action.DELETE));
    }

    public static YXLogInfo getLogInfo(String url, String method) {
        return URLS.get(expandUrl(url, method.toUpperCase()));
    }

    // 请求URL+请求方法合一起为map的key
    private static String expandUrl(String url, String method) {
        return url + CONNECTOR + method;
    }

}


