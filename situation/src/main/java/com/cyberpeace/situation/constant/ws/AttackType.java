package com.cyberpeace.situation.constant.ws;

import com.cyberpeace.common.model.CommonContext;
import lombok.Getter;

import java.util.Arrays;

public enum AttackType {

	/**
	 * 攻击类型
	 */

	TROJANS_IMPLANTATION(ActionType.TROJAN,"木马植入","Trojans Implantation","trojans-implantation", false, true, true, "",  ""),
	SPRINGBOARD_ATTACK(ActionType.SPRINGBOARD_ATTACK,"跳板攻击","Springboard Attack","springboard-attack", false, true, false,  "", ""),
	PASSWORD_CRACKING(ActionType.PASSWORD_CRACKING,"密码破解","Password Cracking","password-cracking", false, true, false, "", ""),
	WEBSITE_INFORMATION_COLLECTION(ActionType.NETWORK_SCANNING,"网站信息收集","Website Information Collection","website-information-collection", false, false, false, "Nmap", "发现官方网站"),
	DIGITAL_TYPE_INJECTION(ActionType.SUCCESS_ATTACK,"数字类型注入","Digital Type Injection","digital-type-injection", false, true, false, "", ""),
	STRING_TYPE_INJECTION(ActionType.SUCCESS_ATTACK,"字串类型注入","String Type Injection","string-type-injection", false, true, false, "", ""),
	HTTP_INJECTION(ActionType.SUCCESS_ATTACK,"HTTP注入","HTTP Injection","http-injection", false, true, true, "", ""),
	NETWORK_WORM(ActionType.LEVERAGING_REMOTE_SERVICES,"网络蠕虫","Network Worm","network-worm", false, false, true, "", ""),
	BOTNET(ActionType.HOST_ATTACK,"僵尸网络","Botnet","botnet", true, false, false, "", ""),
	WEB_PAGES_EMBEDDED_BY_MALICIOUS_CODE(ActionType.SUCCESS_ATTACK,"恶意代码内嵌网页","Web Pages Embedded By Malicious Code","web-pages-embedded-by-malicious-code", false, true, true, "", ""),
	DENIAL_OF_SERVICE(ActionType.DDOS,"拒绝服务","Denial Of Service","denial-of-service", true, false, false, "", ""),
	NETWORK_SCANNING_AND_EAVESDROPPING(ActionType.NETWORK_SCANNING,"网络扫描窃听","Network Scanning And Eavesdropping","Network Scanning And Eavesdropping", false, false, false, "Webcrack", "利用phpstudy后门漏洞上传webshell，获取web服务器的system权限"),
	DATA_TAMPERING(ActionType.SUCCESS_ATTACK,"数据篡改","Data Tampering","data-tampering", false, true, false, "", ""),
	DATA_COUNTERFEITING(ActionType.START_ATTACK,"数据假冒","Data Counterfeiting","data-counterfeiting", false, true, false, "", ""),
	DATA_LEAKAGE(ActionType.START_ATTACK,"数据泄露","Data Leakage","data-leakage", false, true, false, "", ""),
//	DATA_INTERCEPTION(ActionType.START_ATTACK,"数据窃取","Data Interception","data-interception", false, true, false, "", "", ""),
//	DATA_VOLLEY(ActionType.START_ATTACK,"数据拦截","Data Volley","data-volley", false, true, false, "", "", ""),
//	DATA_LOSS(ActionType.START_ATTACK,"数据丢失","Data Loss","data-loss", false, true, false, "", "", ""),
//	DATA_ERROR(ActionType.START_ATTACK,"数据错误","Data Error","data-error", false, true, false, "", "", ""),
//	XML_INJECTION(ActionType.START_ATTACK,"XML注入","XML Injection","xml-injection", false, false, true, "", "", ""),
//	TCP_CHAOTIC_PACKET_ATTACK(ActionType.START_ATTACK,"TCP混乱数据包攻击","TCP Chaotic Packet Attack","tcp-chaotic-packet-attack", false, true, false, "", "", ""),
//	SYN_FLOOD_ATTACK(ActionType.START_ATTACK,"Syn Flood攻击","Syn Flood Attack","syn-flood-attack", true, true, false, "", "", ""),
//	UDP_FLOOD_ATTACK(ActionType.START_ATTACK,"UDP Flood攻击","UDP Flood Attack","udp-flood-attack", true, true, false, "", "", ""),
//	VERTICAL_ULTRA_VIRES(ActionType.START_ATTACK,"垂直越权","Vertical Ultra Vires","vertical-ultra-vires", false, true, false, "", "", ""),
//	HORIZONTAL_ULTRA_VIRES(ActionType.START_ATTACK,"水平越权","Horizontal Ultra Vires","horizontal-ultra-vires", false, true, false, "", "", ""),
//	PORT_FORWARDING(ActionType.START_ATTACK,"端口转发","Port Forwarding","port-forwarding", false, true, false, "", "", ""),
//	ARP_SPOOFING(ActionType.START_ATTACK,"ARP欺骗","ARP Spoofing","arp-spoofing", false, true, false, "", "", ""),
//	DNS_SPOOFING(ActionType.START_ATTACK,"DNS欺骗","DNS Spoofing","dns-spoofing", false, true, false, "", "", ""),
//	COMMUNICATION_NETWORK_SEGMENT_DETECTION(ActionType.NETWORK_SCANNING,"通信网段探测","Communication Network Segment Detection","communication-network-segment-detection", false, true, false, "Nmap", "内网存活主机探测", "信息收集发现网站服务器的路由表，获取内网地址，使用namp对内网网段进行扫描，发现存活主机"),
//	INTRANET_SERVICE_PORT_DETECTION(ActionType.NETWORK_SCANNING,"内网服务端口探测","Intranet Service Port Detection","intranet-service-port-detection", false, false, false, "", "", ""),
//	INTRANET_HORIZONTAL_ATTACK(ActionType.LATERAL_MOVEMENT, "内网横向攻击", "Intranet Horizontal Attack","intranet-horizontal-attack",false, true, false, "", "", ""),
//	UNAUTHORIZED_ACCESS_TO_HORIZONTAL_MOVEMENT(ActionType.LATERAL_MOVEMENT,"未授权访问横向移动","Unauthorized Access To Horizontal Movement","unauthorized-access-to-horizontal-movement", false, true, false, "", "", ""),
//	HORIZONTAL_MOVEMENT_OF_WEAK_PASSWORD(ActionType.LATERAL_MOVEMENT,"弱口令横向移动","Horizontal Movement Of Weak Password","horizontal-movement-of-weak-password", false, true, false, "", "", ""),
//	DATABASE_HORIZONTAL_MOVEMENT(ActionType.LATERAL_MOVEMENT,"数据库横向移动","Database Horizontal Movement","database-horizontal-movement", false, true, false, "", "", ""),
	HORIZONTAL_MOVEMENT_OF_KERNEL_VULNERABILITIES(ActionType.LATERAL_MOVEMENT,"内核漏洞横向移动","Horizontal Movement Of Kernel Vulnerabilities","horizontal-movement-of-kernel-vulnerabilities", false, true, false, "", "");
//	OPERATION_FAILURE_OF_INDUSTRIAL_CONTROL_EQUIPMENT(ActionType.SUCCESS_ATTACK, "工控设备运转故障", "Operation Failure Of Industrial Control Equipment","operation-failure-of-industrial-control-equipment", false, false, false, "", ""),
//	POWER_SYSTEM_INTERRUPTION(ActionType.SUCCESS_ATTACK, "电力系统中断", "Power System Interruption", "power-system-interruption", false, false, false, "", ""),
//	LOGISTICS_AND_TRANSPORTATION_NETWORK_IS_PARALYZED(ActionType.SUCCESS_ATTACK, "物流运输网络瘫痪", "Logistics And Transportation Network Is Paralyzed", "logistics-and-transportation-network-is-paralyzed", false, false, false, "", "");

    @Getter
	private ActionType actionType;

	@Getter
	private String cnDesc;

	@Getter
	private String enDesc;

	@Getter
	private String keyWord;

	/**
	 * 是否导致瘫痪
	 */
	@Getter
	private Boolean isParalysis;

	/**
	 * 是否漏洞利用
	 */
	@Getter
	private Boolean isExploit;

	/**
	 * 是否植入病毒
	 */
	@Getter
	private Boolean isVirus;

	/**
	 * 武器装备/工具
	 */
	@Getter
	private String tool;

	/**
	 * 成功结果描述
	 */
	@Getter
	private String successDesc;

	AttackType(ActionType actionType, String cnDesc, String enDesc, String keyWord, Boolean isParalysis,
			   Boolean isExploit, Boolean isVirus, String tool, String successDesc) {
		this.actionType = actionType;
		this.cnDesc = cnDesc;
		this.enDesc = enDesc;
		this.keyWord = keyWord;
		this.isParalysis = isParalysis;
		this.isExploit = isExploit;
		this.isVirus = isVirus;
		this.tool = tool;
		this.successDesc = successDesc;
	}

	/**
	 * 根据enDesc获取枚举
	 */
	public static <T extends AttackType> T getEnumByEnDesc(String enDesc, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getEnDesc().equals(enDesc))
				.findFirst().orElse(null);
	}

	/**
	 * 根据keyWord获取枚举
	 */
	public static <T extends AttackType> T getEnumByKeyWord(String keyWord, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getKeyWord().equals(keyWord))
					 .findFirst().orElse(null);
	}

	public String getMsg() {
		return CommonContext.isChinese() ? cnDesc : enDesc;
	}

}
