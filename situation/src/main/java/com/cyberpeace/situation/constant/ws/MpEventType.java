package com.cyberpeace.situation.constant.ws;

import java.util.Arrays;

import com.cyberpeace.common.model.CommonContext;

import lombok.Getter;

/**
 * MP攻击类型
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum MpEventType {

	/**
	 * 进行ROOT提权攻击尝试
	 */
	ATTEMPTED_ADMIN(ActionType.TRY_ATTACK, "进行ROOT提权攻击尝试", "Attempted Administrator Privilege Gain",
			"attempted-admin"),

	/**
	 * 尝试拒绝服务攻击
	 */
	ATTEMPTED_DOS(ActionType.TRY_ATTACK, "尝试拒绝服务攻击", "Attempted Denial of Service",
			"attempted-dos"),

	/**
	 * 进行信息泄露攻击
	 */
	ATTEMPTED_RECON(ActionType.TRY_ATTACK, "进行信息泄露攻击", "Attempted Information Leak",
			"attempted-recon"),

	/**
	 * 发起提权攻击尝试
	 */
	ATTEMPTED_USER(ActionType.TRY_ATTACK, "发起提权攻击尝试", "Attempted User Privilege Gain",
			"attempted-user"),

	/**
	 * 进行构造流量攻击
	 */
	BAD_UNKNOWN(ActionType.NORMAL, "进行构造流量攻击", "Potentially Bad Traffic", "bad-unknown"),

	/**
	 * 发起挖矿病毒攻击
	 */
	COIN_MINING(ActionType.VIRUS_ATTACKS, "发起挖矿病毒攻击", "Crypto Currency Mining Activity Detected",
			"coin-mining"),

	/**
	 * 发起凭证窃取
	 */
	CREDENTIAL_THEFT(ActionType.NORMAL, "发起凭证窃取", "Successful Credential Theft Detected",
			"credential-theft"),

	/**
	 * 进行密码爆破攻击
	 */
	DEFAULT_LOGIN_ATTEMPT(ActionType.NORMAL, "进行密码爆破攻击",
			"Attempt to login by a default username and password", "default-login-attempt"),

	/**
	 * 发起拒绝服务攻击
	 */
	DENIAL_OF_SERVICE(ActionType.DDOS, "发起拒绝服务攻击", "Detection of a Denial of Service Attack",
			"denial-of-service"),

	/**
	 * 发起被感染域攻击
	 */
	DOMAIN_C2(ActionType.NORMAL, "发起被感染域攻击", "Domain Observed Used for C2 Detected", "domain-c2"),

	/**
	 * 发送恶意工具包
	 */
	EXPLOIT_KIT(ActionType.NORMAL, "发送恶意工具包", "Exploit Kit Activity Detected", "exploit-kit"),

	/**
	 * 进行外部域名解析
	 */
	EXTERNAL_IP_CHECK(ActionType.NORMAL, "进行外部域名解析",
			"Device Retrieving External IP Address Detected", "external-ip-check"),

	/**
	 * 进行ICMP攻击
	 */
	ICMP_EVENT(ActionType.NORMAL, "进行ICMP攻击", "Generic ICMP event", "icmp-event"),

	/**
	 * 发送敏感内容
	 */
	KICKASS_PORN(ActionType.NORMAL, "发送敏感内容", "SCORE! Get the lotion!", "kickass-porn"),

	/**
	 * 发起其他攻击
	 */
	MISC_ACTIVITY(ActionType.NORMAL, "发起其他攻击", "Misc activity", "misc-activity"),

	/**
	 * 发起Misc攻击
	 */
	MISC_ATTACK(ActionType.NORMAL, "发起Misc攻击", "Misc Attack", "misc-attack"),

	/**
	 * 进行网络扫描
	 */
	NETWORK_SCAN(ActionType.NETWORK_SCANNING, "进行网络扫描", "Detection of a Network Scan",
			"network-scan"),

	/**
	 * 进行fuzzing
	 */
	NON_STANDARD_PROTOCOL(ActionType.NORMAL, "进行fuzzing",
			"Detection of a non-standard protocol or event", "non-standard-protocol"),

	/**
	 * 发送非可疑流量
	 */
	NOT_SUSPICIOUS(ActionType.NORMAL, "发送非可疑流量", "Not Suspicious Traffic", "not-suspicious"),

	/**
	 * 违反隐私策略
	 */
	POLICY_VIOLATION(ActionType.NORMAL, "违反隐私策略", "Potential Corporate Privacy Violation",
			"policy-violation"),

	/**
	 * 进行通用协议命令解码
	 */
	PROTOCOL_COMMAND_DECODE(ActionType.NORMAL, "进行通用协议命令解码", "Generic Protocol Command Decode",
			"protocol-command-decode"),

	/**
	 * 发送无用程序
	 */
	PUP_ACTIVITY(ActionType.NORMAL, "发送无用程序", "Possibly Unwanted Program Detected", "pup-activity"),

	/**
	 * 发起远程桌面连接
	 */
	RPC_PORTMAP_DECODE(ActionType.NORMAL, "发起远程桌面连接", "Decode of an RPC Query",
			"rpc-portmap-decode"),

	/**
	 * 进行代码注入
	 */
	SHELLCODE_DETECT(ActionType.NORMAL, "进行代码注入", "Executable code was detected",
			"shellcode-detect"),

	/**
	 * 进行社会工程
	 */
	SOCIAL_ENGINEERING(ActionType.NORMAL, "进行社会工程", "Possible Social Engineering Attempted",
			"social-engineering"),

	/**
	 * 发送可疑字符串
	 */
	STRING_DETECT(ActionType.NORMAL, "发送可疑字符串", "A suspicious string was detected",
			"string-detect"),

	/**
	 * 发起ROOT提权攻击成功
	 */
	SUCCESSFUL_ADMIN(ActionType.HOST_ATTACK, "发起ROOT提权攻击成功",
			"Successful Administrator Privilege Gain", "successful-admin"),

	/**
	 * 发起成功拒绝服务攻击
	 */
	SUCCESSFUL_DOS(ActionType.DDOS, "发起成功拒绝服务攻击", "Denial of Service", "successful-dos"),

	/**
	 * 进行大规模信息泄露
	 */
	SUCCESSFUL_RECON_LARGESCALE(ActionType.NORMAL, "进行大规模信息泄露", "Large Scale Information Leak",
			"successful-recon-largescale"),

	/**
	 * 进行小规模信息泄露
	 */
	SUCCESSFUL_RECON_LIMITED(ActionType.NORMAL, "进行小规模信息泄漏", "Information Leak",
			"successful-recon-limited"),

	/**
	 * 发起用户提权攻击成功
	 */
	SUCCESSFUL_USER(ActionType.HOST_ATTACK, "发起提权攻击成功", "Successful User Privilege Gain",
			"successful-user"),

	/**
	 * 发起文件攻击
	 */
	SUSPICIOUS_FILENAME_DETECT(ActionType.NORMAL, "发起文件攻击", "A suspicious filename was detected",
			"suspicious-filename-detect"),

	/**
	 * 发起异常登录
	 */
	SUSPICIOUS_LOGIN(ActionType.NORMAL, "发起异常登录",
			"An attempted login using a suspicious username was detected", "suspicious-login"),

	/**
	 * 进行系统调用检测
	 */
	SYSTEM_CALL_DETECT(ActionType.NORMAL, "进行系统调用检测", "A system call was detected",
			"system-call-detect"),

	/**
	 * 发起恶意行为
	 */
	TARGETED_ACTIVITY(ActionType.NORMAL, "发起恶意行为", "Targeted Malicious Activity was Detected",
			"targeted-activity"),

	/**
	 * 发送异常链接
	 */
	TCP_CONNECTION(ActionType.NORMAL, "发送异常链接", "A TCP connection was detected", "tcp-connection"),

	/**
	 * 发起木马攻击
	 */
	TROJAN_ACTIVITY(ActionType.TROJAN, "发起木马攻击", "A Network Trojan was detected",
			"trojan-activity"),

	/**
	 * 发送未知流量
	 */
	UNKNOWN(ActionType.NORMAL, "发送未知流量", "Unknown Traffic", "unknown"),

	/**
	 * 发起提权攻击失败
	 */
	UNSUCCESSFUL_USER(ActionType.TRY_ATTACK, "发起提权攻击失败", "Unsuccessful User Privilege Gain",
			"unsuccessful-user"),

	/**
	 * 进行异常客户端攻击
	 */
	UNUSUAL_CLIENT_PORT_CONNECTION(ActionType.NORMAL, "进行异常客户端攻击",
			"A client was using an unusual port", "unusual-client-port-connection"),

	/**
	 * 进行web漏洞尝试利用
	 */
	WEB_APPLICATION_ACTIVITY(ActionType.TRY_ATTACK, "进行web漏洞尝试利用",
			"access to a potentially vulnerable web application", "web-application-activity"),

	/**
	 * 发起Web应用攻击
	 */
	WEB_APPLICATION_ATTACK(ActionType.NORMAL, "发起Web应用攻击", "Web Application Attack",
			"web-application-attack");

	/**
	 * 动作类型
	 */
	@Getter
	private ActionType actionType;

	/**
	 * 中文描述
	 */
	@Getter
	private String cnDesc;

	/**
	 * 英文描述
	 */
	@Getter
	private String enDesc;

	/**
	 * 关键字
	 */
	@Getter
	private String keyWord;

	MpEventType(ActionType actionType, String cnDesc, String enDesc, String keyWord) {
		this.actionType = actionType;
		this.cnDesc = cnDesc;
		this.enDesc = enDesc;
		this.keyWord = keyWord;
	}

	/**
	 * 根据英文描述获取枚举
	 *
	 * @param enDesc    英文描述
	 * @param enumClass 枚举类
	 * @return {@code T}
	 */
	public static <T extends MpEventType> T getEnumByEnDesc(String enDesc, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getEnDesc().equals(enDesc))
				.findFirst().orElse(null);
	}

	/**
	 * 根据请求语言获取对应描述
	 *
	 * @return {@code String}
	 */
	public String getMsg() {
		return CommonContext.isChinese() ? cnDesc : enDesc;
	}

}
