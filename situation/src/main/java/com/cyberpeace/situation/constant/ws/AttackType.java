package com.cyberpeace.situation.constant.ws;

import java.util.Arrays;

import com.cyberpeace.common.model.CommonContext;

import lombok.Getter;

public enum AttackType {

	/**
	 * 攻击类型
	 */

    ATTEMPTED_ADMIN(ActionType.TRY_ATTACK, "ROOT提权攻击尝试", "Attempted Administrator Privilege Gain", "attempted-admin"),
    ATTEMPTED_DOS(ActionType.TRY_ATTACK, "尝试拒绝服务攻击", "Attempted Denial of Service", "attempted-dos"),
    ATTEMPTED_RECON(ActionType.TRY_ATTACK, "信息泄露", "Attempted Information Leak", "attempted-recon"),
    ATTEMPTED_USER(ActionType.TRY_ATTACK, "提权攻击尝试", "Attempted User Privilege Gain", "attempted-user"),
    BAD_UNKNOWN(ActionType.NORMAL, "构造流量攻击", "Potentially Bad Traffic", "bad-unknown"),
    COIN_MINING(ActionType.VIRUS_ATTACKS, "挖矿病毒", "Crypto Currency Mining Activity Detected", "coin-mining"),
    CREDENTIAL_THEFT(ActionType.NORMAL, "凭证窃取", "Successful Credential Theft Detected", "credential-theft"),
    DEFAULT_LOGIN_ATTEMPT(ActionType.NORMAL, "密码爆破", "Attempt to login by a default username and password", "default-login-attempt"),
    DENIAL_OF_SERVICE(ActionType.DDOS, "拒绝服务攻击", "Detection of a Denial of Service Attack", "denial-of-service"),
    DOMAIN_C2(ActionType.NORMAL, "被感染域", "Domain Observed Used for C2 Detected", "domain-c2"),
    EXPLOIT_KIT(ActionType.NORMAL, "恶意工具包", "Exploit Kit Activity Detected", "exploit-kit"),
    EXTERNAL_IP_CHECK(ActionType.NORMAL, "外部域名解析", "Device Retrieving External IP Address Detected", "external-ip-check"),
    ICMP_EVENT(ActionType.NORMAL, "ICMP攻击", "Generic ICMP event", "icmp-event"),
    KICKASS_PORN(ActionType.NORMAL, "敏感内容", "SCORE! Get the lotion!", "kickass-porn"),
    MISC_ACTIVITY(ActionType.NORMAL, "其他攻击", "Misc activity", "misc-activity"),
    MISC_ATTACK(ActionType.NORMAL, "Misc攻击", "Misc Attack", "misc-attack"),
    NETWORK_SCAN(ActionType.NETWORK_SCANNING, "网络扫描", "Detection of a Network Scan", "network-scan"),
    NON_STANDARD_PROTOCOL(ActionType.NORMAL, "fuzzing", "Detection of a non-standard protocol or event", "non-standard-protocol"),
    NOT_SUSPICIOUS(ActionType.NORMAL, "非可疑流量", "Not Suspicious Traffic", "not-suspicious"),
    POLICY_VIOLATION(ActionType.NORMAL, "违反隐私策略", "Potential Corporate Privacy Violation", "policy-violation"),
    PROTOCOL_COMMAND_DECODE(ActionType.NORMAL, "通用协议命令解码", "Generic Protocol Command Decode", "protocol-command-decode"),
    PUP_ACTIVITY(ActionType.NORMAL, "无用程序", "Possibly Unwanted Program Detected", "pup-activity"),
    RPC_PORTMAP_DECODE(ActionType.NORMAL, "远程桌面连接", "Decode of an RPC Query", "rpc-portmap-decode"),
    SHELLCODE_DETECT(ActionType.NORMAL, "代码注入", "Executable code was detected", "shellcode-detect"),
    SOCIAL_ENGINEERING(ActionType.NORMAL, "社会工程", "Possible Social Engineering Attempted", "social-engineering"),
    STRING_DETECT(ActionType.NORMAL, "可疑字符串", "A suspicious string was detected", "string-detect"),
    SUCCESSFUL_ADMIN(ActionType.HOST_ATTACK, "ROOT提权攻击成功", "Successful Administrator Privilege Gain", "successful-admin"),
    SUCCESSFUL_DOS(ActionType.DDOS, "成功拒绝服务攻击", "Denial of Service", "successful-dos"),
    SUCCESSFUL_RECON_LARGESCALE(ActionType.NORMAL, "大规模信息泄露", "Large Scale Information Leak", "successful-recon-largescale"),
    SUCCESSFUL_RECON_LIMITED(ActionType.NORMAL, "小规模信息泄漏", "Information Leak", "successful-recon-limited"),
    SUCCESSFUL_USER(ActionType.HOST_ATTACK, "提权攻击成功", "Successful User Privilege Gain", "successful-user"),
    SUSPICIOUS_FILENAME_DETECT(ActionType.NORMAL, "文件攻击", "A suspicious filename was detected", "suspicious-filename-detect"),
    SUSPICIOUS_LOGIN(ActionType.NORMAL, "异常登录", "An attempted login using a suspicious username was detected", "suspicious-login"),
    SYSTEM_CALL_DETECT(ActionType.NORMAL, "系统调用检测", "A system call was detected", "system-call-detect"),
    TARGETED_ACTIVITY(ActionType.NORMAL, "恶意行为", "Targeted Malicious Activity was Detected", "targeted-activity"),
    TCP_CONNECTION(ActionType.NORMAL, "异常链接", "A TCP connection was detected", "tcp-connection"),
    TROJAN_ACTIVITY(ActionType.TROJAN, "木马攻击", "A Network Trojan was detected", "trojan-activity"),
    UNKNOWN(ActionType.NORMAL, "未知流量", "Unknown Traffic", "unknown"),
    UNSUCCESSFUL_USER(ActionType.TRY_ATTACK, "提权攻击失败", "Unsuccessful User Privilege Gain", "unsuccessful-user"),
    UNUSUAL_CLIENT_PORT_CONNECTION(ActionType.NORMAL, "异常客户端攻击", "A client was using an unusual port", "unusual-client-port-connection"),
    WEB_APPLICATION_ACTIVITY(ActionType.TRY_ATTACK, "web漏洞尝试利用", "access to a potentially vulnerable web application", "web-application-activity"),
    WEB_APPLICATION_ATTACK(ActionType.NORMAL, "Web应用攻击", "Web Application Attack", "web-application-attack"),
    MEDIA_ANIMATION(ActionType.NONE,"多媒体动效","Media animation","media-animation"),
    HIGHLIGHT_AREA(ActionType.HIGHLIGHT_AREA,"区域高亮","Highlight area","highlight-area");

	@Getter
	private ActionType actionType;

	@Getter
	private String cnDesc;

	@Getter
	private String enDesc;

	@Getter
	private String keyWord;

	AttackType(ActionType actionType, String cnDesc, String enDesc, String keyWord) {
		this.actionType = actionType;
		this.cnDesc = cnDesc;
		this.enDesc = enDesc;
		this.keyWord = keyWord;
	}

	/**
	 * 根据enDesc获取枚举
	 */
	public static <T extends AttackType> T getEnumByEnDesc(String enDesc, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getEnDesc().equals(enDesc))
				.findFirst().orElse(null);
	}

	public String getMsg() {
		return CommonContext.isChinese() ? cnDesc : enDesc;
	}

}
