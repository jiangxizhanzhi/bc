package com.cyberpeace.situation.constant.ws;

import com.cyberpeace.situation.service.AttackLogEnum;
import lombok.Getter;

public enum AttackType implements AttackLogEnum {
    /**
     * 攻击类型
     */
    NOT_SUSPICIOUS_TRAFFIC(9, "普通攻击", "非可疑流量", "not-suspicious"),
    UNKNOWN_TRAFFIC(9, "普通攻击", "未知流量", "unknown"),
    POTENTIALLY_BAD_TRAFFIC(9, "普通攻击", "构造流量攻击", "bad-unknown"),
    ATTEMPTED_INFORMATION_LEAK(5, "尝试攻击", "信息泄露", "attempted-recon"),
    INFORMATION_LEAK(9, "普通攻击", "小规模信息泄漏", "successful-recon-limited"),
    LARGE_SCALE_INFORMATION_LEAK(9, "普通攻击", "大规模信息泄露", "successful-recon-largescale"),
    ATTEMPTED_DENIAL_OF_SERVICE(5, "尝试攻击", "尝试拒绝服务攻击", "attempted-dos"),
    DENIAL_OF_SERVICE(3, "DDOS攻击", "成功拒绝服务攻击", "successful-dos"),
    ATTEMPTED_USER_PRIVILEGE_GAIN(5, "尝试攻击", "提权攻击尝试", "attempted-user"),
    UNSUCCESSFUL_USER_PRIVILEGE_GAIN(5, "尝试攻击", "提权攻击失败", "unsuccessful-user"),
    SUCCESSFUL_USER_PRIVILEGE_GAIN(4, "主机攻占", "提权攻击成功", "successful-user"),
    ATTEMPTED_ADMINISTRATOR_PRIVILEGE_GAIN(5, "尝试攻击", "ROOT提权攻击尝试", "attempted-admin"),
    SUCCESSFUL_ADMINISTRATOR_PRIVILEGE_GAIN(4, "主机攻占", "ROOT提权攻击成功", "successful-admin"),
    DECODE_OF_AN_RPC_QUERY(9, "普通攻击", "远程桌面连接", "rpc-portmap-decode"),
    EXECUTABLE_CODE_WAS_DETECTED(9, "普通攻击", "代码注入", "shellcode-detect"),
    A_SUSPICIOUS_STRING_WAS_DETECTED(9, "普通攻击", "可疑字符串", "string-detect"),
    A_SUSPICIOUS_FILENAME_WAS_DETECTED(9, "普通攻击", "文件攻击", "suspicious-filename-detect"),
    AN_ATTEMPTED_LOGIN_USING_A_SUSPICIOUS_USERNAME_WAS_DETECTED(9, "普通攻击", "异常登录", "suspicious-login"),
    A_SYSTEM_CALL_WAS_DETECTED(9, "普通攻击", "系统调用检测", "system-call-detect"),
    A_TCP_CONNECTION_WAS_DETECTED(9, "普通攻击", "异常链接", "tcp-connection"),
    A_NETWORK_TROJAN_WAS_DETECTED(2, "木马攻击", "木马攻击", "trojan-activity"),
    A_CLIENT_WAS_USING_AN_UNUSUAL_PORT(9, "普通攻击", "异常客户端攻击", "unusual-client-port-connection"),
    DETECTION_OF_A_NETWORK_SCAN(0, "网络扫描", "网络扫描", "network-scan"),
    DETECTION_OF_A_DENIAL_OF_SERVICE_ATTACK(3, "DDOS攻击", "拒绝服务攻击", "denial-of-service"),
    DETECTION_OF_A_NON_STANDARD_PROTOCOL_OR_EVENT(9, "普通攻击", "fuzzing", "non-standard-protocol"),
    GENERIC_PROTOCOL_COMMAND_DECODE(9, "普通攻击", "通用协议命令解码", "protocol-command-decode"),
    ACCESS_TO_A_POTENTIALLY_VULNERABLE_WEB_APPLICATION(5, "尝试攻击", "web漏洞尝试利用", "web-application-activity"),
    WEB_APPLICATION_ATTACK(9, "普通攻击", "Web应用攻击", "web-application-attack"),
    MISC_ACTIVITY(9, "普通攻击", "其他攻击", "misc-activity"),
    MISC_ATTACK(9, "普通攻击", "Misc攻击", "misc-attack"),
    GENERIC_ICMP_EVENT(9, "普通攻击", "ICMP攻击", "icmp-event"),
    SCORE_GET_THE_LOTION(9, "普通攻击", "色情内容", "kickass-porn"),
    POTENTIAL_CORPORATE_PRIVACY_VIOLATION(9, "普通攻击", "违反隐私策略", "policy-violation"),
    ATTEMPT_TO_LOGIN_BY_A_DEFAULT_USERNAME_AND_PASSWORD(9, "普通攻击", "密码爆破", "default-login-attempt"),
    TARGETED_MALICIOUS_ACTIVITY_WAS_DETECTED(9, "普通攻击", "恶意行为", "targeted-activity"),
    EXPLOIT_KIT_ACTIVITY_DETECTED(9, "普通攻击", "恶意工具包", "exploit-kit"),
    DEVICE_RETRIEVING_EXTERNAL_IP_ADDRESS_DETECTED(9, "普通攻击", "外部域名解析", "external-ip-check"),
    DOMAIN_OBSERVED_USED_FOR_C2_DETECTED(9, "普通攻击", "被感染域", "domain-c2"),
    POSSIBLY_UNWANTED_PROGRAM_DETECTED(9, "普通攻击", "无用程序", "pup-activity"),
    SUCCESSFUL_CREDENTIAL_THEFT_DETECTED(9, "普通攻击", "凭证窃取", "credential-theft"),
    POSSIBLE_SOCIAL_ENGINEERING_ATTEMPTED(9, "普通攻击", "社会工程", "social-engineering"),
    CRYPTO_CURRENCY_MINING_ACTIVITY_DETECTED(1, "病毒攻击", "挖矿病毒", "coin-mining");

    @Getter
    private Integer code;

    @Getter
    private String type;

    @Getter
    private String desc;

    @Getter
    private String keyWord;

    AttackType(Integer code, String type, String desc, String keyWord) {
        this.code = code;
        this.type = type;
        this.desc = desc;
        this.keyWord = keyWord;
    }

}
