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

    // 模块
    @Getter
    public enum Type {
        COURSE("SN-2002001001"),
        THEORY_PRACTICE("SN-2002002001"),
        OPERATION_PRACTICE("SN-2002003001"),
        DIRECTION("SN-2002004001"),
        APT_REPORT("SN-2002004001"),
        STRATEGY("SN-2003001001"),
        TACTIC_ATTACK("SN-2004001001"),
        TACTIC_DEFENSE("SN-2004002001"),
        TACTIC_SYSTEM("SN-2004003001"),
        VULN("SN-2005001001"),
        APT_FEATURE("SN-2005001002"),
        RULE_LOADED_FILE_THREAT("SN-2005002001"),
        RULE_EVENT_INTEGRATION("SN-2005002002"),
        RULE_TRAFFIC_DETECT("SN-2005002003"),
        RULE_APT_THREAT_DETECT("SN-2005002004"),
        RULE_SCORE_RANKING("SN-2005003001"),
        ROUND_MECHANISM("SN-2005003002"),
        RULE_VIOLATION_JUDGE("SN-2005003003"),
        ALGORITHM("SN-2006001001"),
        TOOL_ATTACK("SN-3002001001"),
        TOOL_CONTROL("SN-3003001001"),
        TOOL_DEFENSE("SN-3004001001"),
        TOOL_OTHER("SN-3005001001"),
        EXP("SN-3006001001"),
        FRAME_ACTION_TARGET("SN-4001001001"),
        FRAME_ACTION_PROCESS("SN-4002001001"),
        AI("SN-5001001001");
        private final String value;
        Type(String value) {
            this.value = value;
        }
    }
    @Getter
    public enum Action {
        CREATE("3"), EDIT("4"), DELETE("5"), EXPORT("6"), IMPORT("7");
        private final String value;
        Action(String value) {
            this.value = value;
        }
    }

    // 定义值的数据结构
    @Getter
    public static class YXLogInfo {
        // getter方法
        private Type type;
        private String content;
        private Action action;

        public YXLogInfo(Type type, String content, Action action) {
            this.type = type;
            this.content = content;
            this.action = action;
        }

    }

    private static final Map<String, YXLogInfo> URLS = new HashMap<>();

    static {
        // 初始化数据
        URLS.put(expandUrl("/api/ojj/oj/admin/course",POST), new YXLogInfo(Type.COURSE, "新增课程", Action.CREATE));
        URLS.put(expandUrl("/api/ojj/oj/admin/course",PATCH), new YXLogInfo(Type.COURSE, "编辑课程", Action.EDIT));
        URLS.put(expandUrl("/api/ojj/oj/admin/course/batch",DELETE), new YXLogInfo(Type.COURSE, "删除课程", Action.DELETE));
        URLS.put(expandUrl("/api/ojj/oj/admin/course/batch_dumps",GET), new YXLogInfo(Type.COURSE, "导出课程", Action.EXPORT));
        URLS.put(expandUrl("/api/ojj/oj/admin/course/batch_loads/",POST), new YXLogInfo(Type.COURSE, "导入课程", Action.IMPORT));








        // todo
    }

    private static String expandUrl(String url, String method) {
        return url + CONNECTOR + method;
    }

    public static YXLogInfo getLogInfo(String url, String method) {
        return URLS.get(expandUrl(url, method.toUpperCase()));
    }
}


