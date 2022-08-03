package com.cyberpeace.situation.constant.ws;

import java.util.Arrays;

import com.cyberpeace.common.model.CommonContext;

import lombok.Getter;

public enum MpEventType {

	/**
	 * 攻击类型
	 */
	ATTEMPTED_ADMIN(ActionType.TRY_ATTACK, "进行ROOT提权攻击尝试", "Attempted Administrator Privilege Gain", "attempted-admin"),
	ATTEMPTED_DOS(ActionType.TRY_ATTACK, "尝试拒绝服务攻击", "Attempted Denial of Service", "attempted-dos"),
	ATTEMPTED_RECON(ActionType.TRY_ATTACK, "进行信息泄露攻击", "Attempted Information Leak", "attempted-recon"),
	ATTEMPTED_USER(ActionType.TRY_ATTACK, "发起提权攻击尝试", "Attempted User Privilege Gain", "attempted-user"),
	BAD_UNKNOWN(ActionType.NORMAL, "进行构造流量攻击", "Potentially Bad Traffic", "bad-unknown"),
	COIN_MINING(ActionType.VIRUS_ATTACKS, "发起挖矿病毒攻击", "Crypto Currency Mining Activity Detected", "coin-mining"),
	CREDENTIAL_THEFT(ActionType.NORMAL, "发起凭证窃取", "Successful Credential Theft Detected", "credential-theft"),
	DEFAULT_LOGIN_ATTEMPT(ActionType.NORMAL, "进行密码爆破攻击", "Attempt to login by a default username and password", "default-login-attempt"),
	DENIAL_OF_SERVICE(ActionType.DDOS, "发起拒绝服务攻击", "Detection of a Denial of Service Attack", "denial-of-service"),
	DOMAIN_C2(ActionType.NORMAL, "发起被感染域攻击", "Domain Observed Used for C2 Detected", "domain-c2"),
	EXPLOIT_KIT(ActionType.NORMAL, "发送恶意工具包", "Exploit Kit Activity Detected", "exploit-kit"),
	EXTERNAL_IP_CHECK(ActionType.NORMAL, "进行外部域名解析", "Device Retrieving External IP Address Detected", "external-ip-check"),
	ICMP_EVENT(ActionType.NORMAL, "进行ICMP攻击", "Generic ICMP event", "icmp-event"),
	KICKASS_PORN(ActionType.NORMAL, "发送敏感内容", "SCORE! Get the lotion!", "kickass-porn"),
	MISC_ACTIVITY(ActionType.NORMAL, "发起其他攻击", "Misc activity", "misc-activity"),
	MISC_ATTACK(ActionType.NORMAL, "发起Misc攻击", "Misc Attack", "misc-attack"),
	NETWORK_SCAN(ActionType.NETWORK_SCANNING, "进行网络扫描", "Detection of a Network Scan", "network-scan"),
	NON_STANDARD_PROTOCOL(ActionType.NORMAL, "进行fuzzing", "Detection of a non-standard protocol or event", "non-standard-protocol"),
	NOT_SUSPICIOUS(ActionType.NORMAL, "发送非可疑流量", "Not Suspicious Traffic", "not-suspicious"),
	POLICY_VIOLATION(ActionType.NORMAL, "违反隐私策略", "Potential Corporate Privacy Violation", "policy-violation"),
	PROTOCOL_COMMAND_DECODE(ActionType.NORMAL, "进行通用协议命令解码", "Generic Protocol Command Decode", "protocol-command-decode"),
	PUP_ACTIVITY(ActionType.NORMAL, "发送无用程序", "Possibly Unwanted Program Detected", "pup-activity"),
	RPC_PORTMAP_DECODE(ActionType.NORMAL, "发起远程桌面连接", "Decode of an RPC Query", "rpc-portmap-decode"),
	SHELLCODE_DETECT(ActionType.NORMAL, "进行代码注入", "Executable code was detected", "shellcode-detect"),
	SOCIAL_ENGINEERING(ActionType.NORMAL, "进行社会工程", "Possible Social Engineering Attempted", "social-engineering"),
	STRING_DETECT(ActionType.NORMAL, "发送可疑字符串", "A suspicious string was detected", "string-detect"),
	SUCCESSFUL_ADMIN(ActionType.HOST_ATTACK, "发起ROOT提权攻击成功", "Successful Administrator Privilege Gain", "successful-admin"),
	SUCCESSFUL_DOS(ActionType.DDOS, "发起成功拒绝服务攻击", "Denial of Service", "successful-dos"),
	SUCCESSFUL_RECON_LARGESCALE(ActionType.NORMAL, "进行大规模信息泄露", "Large Scale Information Leak", "successful-recon-largescale"),
	SUCCESSFUL_RECON_LIMITED(ActionType.NORMAL, "进行小规模信息泄漏", "Information Leak", "successful-recon-limited"),
	SUCCESSFUL_USER(ActionType.HOST_ATTACK, "发起提权攻击成功", "Successful User Privilege Gain", "successful-user"),
	SUSPICIOUS_FILENAME_DETECT(ActionType.NORMAL, "发起文件攻击", "A suspicious filename was detected", "suspicious-filename-detect"),
	SUSPICIOUS_LOGIN(ActionType.NORMAL, "发起异常登录", "An attempted login using a suspicious username was detected", "suspicious-login"),
	SYSTEM_CALL_DETECT(ActionType.NORMAL, "进行系统调用检测", "A system call was detected", "system-call-detect"),
	TARGETED_ACTIVITY(ActionType.NORMAL, "发起恶意行为", "Targeted Malicious Activity was Detected", "targeted-activity"),
	TCP_CONNECTION(ActionType.NORMAL, "发送异常链接", "A TCP connection was detected", "tcp-connection"),
	TROJAN_ACTIVITY(ActionType.TROJAN, "发起木马攻击", "A Network Trojan was detected", "trojan-activity"),
	UNKNOWN(ActionType.NORMAL, "发送未知流量", "Unknown Traffic", "unknown"),
	UNSUCCESSFUL_USER(ActionType.TRY_ATTACK, "发起提权攻击失败", "Unsuccessful User Privilege Gain", "unsuccessful-user"),
	UNUSUAL_CLIENT_PORT_CONNECTION(ActionType.NORMAL, "进行异常客户端攻击", "A client was using an unusual port", "unusual-client-port-connection"),
	WEB_APPLICATION_ACTIVITY(ActionType.TRY_ATTACK, "进行web漏洞尝试利用", "access to a potentially vulnerable web application", "web-application-activity"),
	WEB_APPLICATION_ATTACK(ActionType.NORMAL, "发起Web应用攻击", "Web Application Attack", "web-application-attack");


	@Getter
	private ActionType actionType;

	@Getter
	private String cnDesc;

	@Getter
	private String enDesc;

	@Getter
	private String keyWord;

	MpEventType(ActionType actionType, String cnDesc, String enDesc, String keyWord) {
		this.actionType = actionType;
		this.cnDesc = cnDesc;
		this.enDesc = enDesc;
		this.keyWord = keyWord;
	}

	/**
	 * 根据enDesc获取枚举
	 */
	public static <T extends MpEventType> T getEnumByEnDesc(String enDesc, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getEnDesc().equals(enDesc))
				.findFirst().orElse(null);
	}

	public String getMsg() {
		return CommonContext.isChinese() ? cnDesc : enDesc;
	}

}
