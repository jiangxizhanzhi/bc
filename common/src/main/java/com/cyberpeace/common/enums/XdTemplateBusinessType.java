package com.cyberpeace.common.enums;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum XdTemplateBusinessType implements GenericEnum {

    CLOUD_COMPUTE(1, "云计算资源科目场景"),
    CLOUD_NETWORK(2, "云网络资源科目场景"),
    SCAN(3, "扫描探测攻击场景"),
    VULN(4, "漏洞挖掘与利用场景"),
    DDOS(5, "拒绝服务攻击场景"),
    NEW_NET_BASE(6, "新型网络基础场景"),
    NEW_NET_ATTACK_AND_DEFEND(7, "新型网络攻防场景"),
    TRAIN(8, "综合演训场景"),
    ;

    @Getter
    private Integer code;

    @Getter
    private String desc;

    private XdTemplateBusinessType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
