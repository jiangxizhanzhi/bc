package com.cyberpeace.situation.constant.ws;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum ActionTypeZHL implements GenericEnum {

    ZHL_ATTACK1(1, "信息收集"),
    ZHL_ATTACK2(2, "漏洞探测与挖掘"),
    ZHL_ATTACK3(3, "漏洞验证与利用"),
    ZHL_ATTACK4(4, "提升权限"),
    ZHL_ATTACK5(5, "内网入侵"),
    ZHL_ATTACK6(6, "痕迹清除"),

    ZHL_DEFENSE1(7, "日志分析"),
    ZHL_DEFENSE2(8, "流量分析"),
    ZHL_DEFENSE3(9, "磁盘内存分析"),
    ZHL_DEFENSE4(10, "木马查杀与分析"),
    ZHL_DEFENSE5(11, "漏洞修复"),
    ZHL_DEFENSE6(12, "安全配置与加固");

    @Getter
    private final Integer code;

    @Getter
    private final String desc;

    ActionTypeZHL(Integer type, String desc) {
        this.code = type;
        this.desc = desc;
    }

}
