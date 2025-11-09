package com.cyberpeace.common.enums;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum XdBusinessType implements GenericEnum {

    CLOUD_COMPUTE_EXP(1, "云计算资源实验", "实验", 402),
    CLOUD_NETWORK_EXP(2, "云网络资源实验", "实验", 402),
    SCAN_EXP(3, "扫描探测攻击实验", "实验", 402),
    VULN_EXP(4, "漏洞挖掘与利用实验", "实验", 402),
    DDOS_EXP(5, "拒绝服务攻击实验", "实验", 402),
    NEW_NET_BASE_EXP(6, "新型网络基础实验", "实验", 402),
    NEW_NET_ATTACK_AND_DEFEND_EXP(7, "新型网络攻防实验", "实验", 402),

    CLOUD_COMPUTE_PRA(8, "云计算资源训练", "科目", 401),
    CLOUD_NETWORK_PRA(9, "云网络资源训练", "科目", 401),
    SCAN_PRA(10, "扫描探测攻击训练", "科目", 401),
    VULN_PRA(11, "漏洞挖掘与利用训练", "科目", 401),
    DDOS_PRA(12, "拒绝服务攻击训练", "科目", 401),
    NEW_NET_BASE_PRA(13, "新型网络基础训练", "科目", 401),
    NEW_NET_ATTACK_AND_DEFEND_PRA(14, "新型网络攻防训练", "科目", 401),
    TRAIN(15, "综合演训", "综合演训", 403),
    ;

    @Getter
    private Integer code;

    @Getter
    private String desc;

    @Getter
    private String type;

    @Getter
    private Integer businessCode;

    private XdBusinessType(Integer code, String desc, String type, Integer businessCode) {
        this.code = code;
        this.desc = desc;
        this.type = type;
        this.businessCode = businessCode;
    }


    public static XdBusinessType getByCode(Integer code) {
        for (XdBusinessType value : XdBusinessType.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return CLOUD_COMPUTE_EXP;

    }
}