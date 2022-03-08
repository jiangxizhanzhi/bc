package com.cyberpeace.situation.constant.ws;

import com.cyberpeace.situation.service.AttackLogEnum;
import lombok.Getter;

public enum AttackType implements AttackLogEnum {
    /**
     * 攻击类型
     */
    NOT_SUSPICIOUS(9, "普通攻击", "非可疑流量", "Not Suspicious Traffic", "not-suspicious"),
    UNKNOWN(9, "普通攻击", "未知流量", "Unknown Traffic", "unknown"),
    BAD_UNKNOWN(9, "普通攻击", "构造流量攻击", "Potentially Bad Traffic", "bad-unknown"),
    ATTEMPTED_RECON(5, "尝试攻击", "信息泄露", "Attempted Information Leak", "attempted-recon"),
    SUCCESSFUL_RECON_LIMITED(9, "普通攻击", "小规模信息泄漏", "Information Leak", "successful-recon-limited"),
    SUCCESSFUL_RECON_LARGESCALE(9, "普通攻击", "大规模信息泄露", "Large Scale Information Leak", "successful-recon-largescale"),
    ATTEMPTED_DOS(5, "尝试攻击", "尝试拒绝服务攻击", "Attempted Denial of Service", "attempted-dos"),
    SUCCESSFUL_DOS(3, "DDOS攻击", "成功拒绝服务攻击", "Denial of Service", "successful-dos"),
    ATTEMPTED_USER(5, "尝试攻击", "提权攻击尝试", "Attempted User Privilege Gain", "attempted-user"),
    UNSUCCESSFUL_USER(5, "尝试攻击", "提权攻击失败", "Unsuccessful User Privilege Gain", "unsuccessful-user"),
    SUCCESSFUL_USER(4, "主机攻占", "提权攻击成功", "Successful User Privilege Gain", "successful-user"),
    ATTEMPTED_ADMIN(5, "尝试攻击", "ROOT提权攻击尝试", "Attempted Administrator Privilege Gain", "attempted-admin"),
    SUCCESSFUL_ADMIN(4, "主机攻占", "ROOT提权攻击成功", "Successful Administrator Privilege Gain", "successful-admin"),
    RPC_PORTMAP_DECODE(9, "普通攻击", "远程桌面连接", "Decode of an RPC Query", "rpc-portmap-decode"),
    SHELLCODE_DETECT(9, "普通攻击", "代码注入", "Executable code was detected", "shellcode-detect"),
    STRING_DETECT(9, "普通攻击", "可疑字符串", "A suspicious string was detected", "string-detect"),
    SUSPICIOUS_FILENAME_DETECT(9, "普通攻击", "文件攻击", "A suspicious filename was detected", "suspicious-filename-detect"),
    SUSPICIOUS_LOGIN(9, "普通攻击", "异常登录", "An attempted login using a suspicious username was detected", "suspicious-login"),
    SYSTEM_CALL_DETECT(9, "普通攻击", "系统调用检测", "A system call was detected", "system-call-detect"),
    TCP_CONNECTION(9, "普通攻击", "异常链接", "A TCP connection was detected", "tcp-connection"),
    TROJAN_ACTIVITY(2, "木马攻击", "木马攻击", "A Network Trojan was detected", "trojan-activity"),
    UNUSUAL_CLIENT_PORT_CONNECTION(9, "普通攻击", "异常客户端攻击", "A client was using an unusual port", "unusual-client-port-connection"),
    NETWORK_SCAN(0, "网络扫描", "网络扫描", "Detection of a Network Scan", "network-scan"),
    DENIAL_OF_SERVICE(3, "DDOS攻击", "拒绝服务攻击", "Detection of a Denial of Service Attack", "denial-of-service"),
    NON_STANDARD_PROTOCOL(9, "普通攻击", "fuzzing", "Detection of a non-standard protocol or event", "non-standard-protocol"),
    PROTOCOL_COMMAND_DECODE(9, "普通攻击", "通用协议命令解码", "Generic Protocol Command Decode", "protocol-command-decode"),
    WEB_APPLICATION_ACTIVITY(5, "尝试攻击", "web漏洞尝试利用", "access to a potentially vulnerable web application", "web-application-activity"),
    WEB_APPLICATION_ATTACK(9, "普通攻击", "Web应用攻击", "Web Application Attack", "web-application-attack"),
    MISC_ACTIVITY(9, "普通攻击", "其他攻击", "Misc activity", "misc-activity"),
    MISC_ATTACK(9, "普通攻击", "Misc攻击", "Misc Attack", "misc-attack"),
    ICMP_EVENT(9, "普通攻击", "ICMP攻击", "Generic ICMP event", "icmp-event"),
    KICKASS_PORN(9, "普通攻击", "色情内容", "SCORE! Get the lotion!", "kickass-porn"),
    POLICY_VIOLATION(9, "普通攻击", "违反隐私策略", "Potential Corporate Privacy Violation", "policy-violation"),
    DEFAULT_LOGIN_ATTEMPT(9, "普通攻击", "密码爆破", "Attempt to login by a default username and password", "default-login-attempt"),
    TARGETED_ACTIVITY(9, "普通攻击", "恶意行为", "Targeted Malicious Activity was Detected", "targeted-activity"),
    EXPLOIT_KIT(9, "普通攻击", "恶意工具包", "Exploit Kit Activity Detected", "exploit-kit"),
    EXTERNAL_IP_CHECK(9, "普通攻击", "外部域名解析", "Device Retrieving External IP Address Detected", "external-ip-check"),
    DOMAIN_C2(9, "普通攻击", "被感染域", "Domain Observed Used for C2 Detected", "domain-c2"),
    PUP_ACTIVITY(9, "普通攻击", "无用程序", "Possibly Unwanted Program Detected", "pup-activity"),
    CREDENTIAL_THEFT(9, "普通攻击", "凭证窃取", "Successful Credential Theft Detected", "credential-theft"),
    SOCIAL_ENGINEERING(9, "普通攻击", "社会工程", "Possible Social Engineering Attempted", "social-engineering"),
    ABNORMAL_LINKS(9, "异常链接", "异常链接", "Abnormal links", "Abnormal links"),
    COIN_MINING(1, "病毒攻击", "挖矿病毒", "Crypto Currency Mining Activity Detected", "coin-mining");

    @Getter
    private Integer code;

    @Getter
    private String type;

    @Getter
    private String cnDesc;

    @Getter
    private String enDesc;

    @Getter
    private String keyWord;

    AttackType(Integer code, String type, String cnDesc, String enDesc, String keyWord) {
        this.code = code;
        this.type = type;
        this.cnDesc = cnDesc;
        this.enDesc = enDesc;
        this.keyWord = keyWord;
    }

}
