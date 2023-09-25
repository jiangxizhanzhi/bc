package com.cyberpeace.situation.constant.ws;

import com.cyberpeace.common.model.CommonContext;
import lombok.Getter;

import java.util.Arrays;

public enum AttackType {

	/**
	 * 攻击类型
	 */

	TROJANS_IMPLANTATION(ActionType.TROJAN,"木马植入","Trojans Implantation","trojans-implantation", false, true, true, "Cobalt Strike",  "拿到主机权限后可以命令执行，利用Cobalt Strike生成木马，上线CS实现持久控制。"),
	SPRINGBOARD_ATTACK(ActionType.SPRINGBOARD_ATTACK,"跳板攻击","Springboard Attack","springboard-attack", false, true, false,  "Exp", "JumpServer 在 3.0.0-3.6.3 版本存在未授权访问漏洞。由于系统权限配置存在不合理，导致未授权攻击者可以直接访问 /api/v1/terminal/sessions/ 相关路由，获取内网网段。"),
	PASSWORD_CRACKING(ActionType.PASSWORD_CRACKING,"密码破解","Password Cracking","password-cracking", false, true, false, "Hydra", "利用收集到的凭证信息或积累的密码本爆破出内网PC主机的RDP口令，实现内网主机远程登陆。"),
	WEBSITE_INFORMATION_COLLECTION(ActionType.NETWORK_SCANNING,"网站信息收集","Website Information Collection","website-information-collection", false, false, false, "Nmap", "发现官方网站"),
	DIGITAL_TYPE_INJECTION(ActionType.SUCCESS_ATTACK,"数字类型注入","Digital Type Injection","digital-type-injection", false, true, false, "Sqlmap", "利用CMS注入漏洞获取user数据库字段和键值，造成信息泄漏。"),
	STRING_TYPE_INJECTION(ActionType.SUCCESS_ATTACK,"字串类型注入","String Type Injection","string-type-injection", false, true, false, "Sqlmap", "利用注入漏洞获取网站用户数据表user_system,获取企业员工基本信息。"),
	HTTP_INJECTION(ActionType.SUCCESS_ATTACK,"HTTP注入","HTTP Injection","http-injection", false, true, true, "Sqlmap", "利用weblogic的ssrf漏洞可以探测内网隔离的服务主机，获取服务器的端口开放情况。"),
	NETWORK_WORM(ActionType.LEVERAGING_REMOTE_SERVICES,"网络蠕虫","Network Worm","network-worm", false, false, true, "病毒样本", "蠕虫病毒在感染受害机后会不断复制传播，内网缺少防护设备的前提下会不断感染存在漏洞的主机。"),
	BOTNET(ActionType.HOST_ATTACK,"僵尸网络","Botnet","botnet", true, false, false, "BYOB", "僵尸网络向不知情的受害者发送垃圾邮件，利用键盘记录系统窃取密码或敏感信息。"),
	WEB_PAGES_EMBEDDED_BY_MALICIOUS_CODE(ActionType.SUCCESS_ATTACK,"恶意代码内嵌网页","Web Pages Embedded By Malicious Code","web-pages-embedded-by-malicious-code", false, true, true, "Exp", "利用web服务嵌套恶意html，钓鱼上线内网主机"),
	DENIAL_OF_SERVICE(ActionType.DDOS,"拒绝服务","Denial Of Service","denial-of-service", true, false, false, "LOIC", "大量数据包会使网络出现瘫痪，导致网站服务、api、应用程序等无法访问。"),
	NETWORK_SCANNING_AND_EAVESDROPPING(ActionType.NETWORK_SCANNING,"网络扫描窃听","Network Scanning And Eavesdropping","Network Scanning And Eavesdropping", false, false, false, "Webcrack", "利用phpstudy后门漏洞上传webshell，获取web服务器的system权限"),
	DATA_TAMPERING(ActionType.SUCCESS_ATTACK,"数据篡改","Data Tampering","data-tampering", false, true, false, "Subline text", "修改企业邮箱地址及联系方式，获取供应商信息。"),
	DATA_COUNTERFEITING(ActionType.START_ATTACK,"数据假冒","Data Counterfeiting","data-counterfeiting", false, true, false, "Subline text", "注册虚假邮箱并替换官网首页的邮箱链接。"),
	DATA_LEAKAGE(ActionType.START_ATTACK,"数据泄漏","Data Leakage","data-leakage", false, true, false, "Exp", "通达OA v11.9 getdata接口存在任意命令执行漏洞，攻击者通过漏洞可以执行服务器任意命令控制服务器权限，获取OA主机的备份数据，拿到用户信息。"),
	DATA_INTERCEPTION(ActionType.START_ATTACK,"数据窃取","Data Interception","data-interception", false, true, false, "Mimikatz", "利用Mimikatz实现窃取保存在主机内存中的明文密码，获取主机凭证信息。"),
	DATA_VOLLEY(ActionType.START_ATTACK,"数据拦截","Data Volley","data-volley", false, true, false, "Exp", "内网存在Apache Kafka服务，通过exp进行JNDI 注入来实现远程代码执行，获取服务器权限"),
	DATA_LOSS(ActionType.START_ATTACK,"数据丢失","Data Loss","data-loss", false, true, false, "人工", "篡改或删除数据有可能会导致主机服务无法正常运行"),
	DATA_ERROR(ActionType.START_ATTACK,"数据错误","Data Error","data-error", false, true, false, "人工", "程序篡改后组态工程无法正常工作，导致底层传感器过载出现异常，引发安全事故。"),
	XML_INJECTION(ActionType.START_ATTACK,"XML注入","XML Injection","xml-injection", false, false, true, "Burpsuite", "内网OA服务后台逻辑对 XXE 漏洞防护存在缺陷，导致可绕过现有防护实现 XML 外部实体注入，造成敏感信息泄露，发现企业员工身份信息。"),
	TCP_CHAOTIC_PACKET_ATTACK(ActionType.START_ATTACK,"TCP混乱数据包攻击","TCP Chaotic Packet Attack","tcp-chaotic-packet-attack", false, true, false, "Python脚本", "发送伪造源IP的TCP数据包，只不过TCP头的TCP Flags部分是混乱的，可以是syn,ack,syn+ack,syn+rst等等，消耗服务器CPU内存的同时还会堵塞带宽。"),
	SYN_FLOOD_ATTACK(ActionType.START_ATTACK,"Syn Flood攻击","Syn Flood Attack","syn-flood-attack", true, true, false, "Python脚本", "利用TCP协议的三次握手机制，向服务器发送海量的变源IP地址或变源端口的TCP SYN报文，服务器响应了这些报文后就会生成大量的半连接，当系统资源被耗尽后，服务器将无法提供正常的服务。"),
	UDP_FLOOD_ATTACK(ActionType.START_ATTACK,"UDP Flood攻击","UDP Flood Attack","udp-flood-attack", true, true, false, "Python脚本", "利用大量的UDP数据包向内网服务器发送攻击流量，导致网络拥塞甚至系统崩溃。"),
	VERTICAL_ULTRA_VIRES(ActionType.START_ATTACK,"垂直越权","Vertical Ultra Vires","vertical-ultra-vires", false, true, false, "Exp", "利用exp进行提权，获取管理权限后实现持久控制。"),
	HORIZONTAL_ULTRA_VIRES(ActionType.START_ATTACK,"水平越权","Horizontal Ultra Vires","horizontal-ultra-vires", false, true, false, "Burpsuite", "在拿到用户信息后成功登陆某应用系统管理后台，通过抓取修改资料的数据包后拿到用户ID，遍历Id参数批量删除、更改其他用户数据。"),
	PORT_FORWARDING(ActionType.START_ATTACK,"端口转发","Port Forwarding","port-forwarding", false, true, false, "Frp", "在获取到内网主机的路由表后发现多个内网区域并存在隔离，假设代理隧道并进行内网穿透。"),
	ARP_SPOOFING(ActionType.START_ATTACK,"ARP欺骗","ARP Spoofing","arp-spoofing", false, true, false, "disniff", "通过欺骗内网PC的网关MAC地址，使访问者PC错以为攻击者更改后的MAC地址是网关的MAC，导致网络不通。"),
	DNS_SPOOFING(ActionType.START_ATTACK,"DNS欺骗","DNS Spoofing","dns-spoofing", false, true, false, "ettercap", "利用dns欺骗修改请求地址，使受害机访问构造的恶意网站，上线Cobalt Strike"),
	COMMUNICATION_NETWORK_SEGMENT_DETECTION(ActionType.NETWORK_SCANNING,"通信网段探测","Communication Network Segment Detection","communication-network-segment-detection", false, true, false, "Nmap", "信息收集发现网站服务器的路由表，获取内网地址，使用namp对内网网段进行扫描，发现存活主机"),
	INTRANET_SERVICE_PORT_DETECTION(ActionType.NETWORK_SCANNING,"内网服务端口探测","Intranet Service Port Detection","intranet-service-port-detection", false, false, false, "Fscan", "使用fscan对常用端口及服务进行探测，获取服务版本信息"),
	INTRANET_HORIZONTAL_ATTACK(ActionType.LATERAL_MOVEMENT, "内网横向攻击", "Intranet Horizontal Attack","intranet-horizontal-attack",false, true, false, "Psexec", "利用SMB服务，通过明文传递来远程执行命令，上线CS。"),
	UNAUTHORIZED_ACCESS_TO_HORIZONTAL_MOVEMENT(ActionType.LATERAL_MOVEMENT,"未授权访问横向移动","Unauthorized Access To Horizontal Movement","unauthorized-access-to-horizontal-movement", false, true, false, "Navicat", "利用未授权访问漏洞远程访问数据库，获取内网资产和用户信息。"),
	HORIZONTAL_MOVEMENT_OF_WEAK_PASSWORD(ActionType.LATERAL_MOVEMENT,"弱口令横向移动","Horizontal Movement Of Weak Password","horizontal-movement-of-weak-password", false, true, false, "Msf", "整理收集到的凭证信息，利用MSF对内网网段进行密码喷洒，获取弱口令主机会话。"),
	DATABASE_HORIZONTAL_MOVEMENT(ActionType.LATERAL_MOVEMENT,"数据库横向移动","Database Horizontal Movement","database-horizontal-movement", false, true, false, "SharpSQLTools", "利用CRL组件在SQ Server数据库上执行任意C#代码，上线CS"),
	HORIZONTAL_MOVEMENT_OF_KERNEL_VULNERABILITIES(ActionType.LATERAL_MOVEMENT,"内核漏洞横向移动","Horizontal Movement Of Kernel Vulnerabilities","horizontal-movement-of-kernel-vulnerabilities", false, true, false, "Exp", "发现内网审计系统存在log4j漏洞，漏洞利用拿到系统权限，发现服务以容器的形式启动，尝试脏牛提权后成功逃逸，拿到宿主机的root权限。"),
	OPERATION_FAILURE_OF_INDUSTRIAL_CONTROL_EQUIPMENT(ActionType.SUCCESS_ATTACK, "工控设备运转故障", "Operation Failure Of Industrial Control Equipment","operation-failure-of-industrial-control-equipment", false, false, false, "LOIC", "大量数据包会使网络出现瘫痪，导致网站服务、api、应用程序等无法访问。"),
	POWER_SYSTEM_INTERRUPTION(ActionType.SUCCESS_ATTACK, "电力系统中断", "Power System Interruption", "power-system-interruption", false, false, false, "LOIC", "大量数据包会使网络出现瘫痪，导致网站服务、api、应用程序等无法访问。"),
	LOGISTICS_AND_TRANSPORTATION_NETWORK_IS_PARALYZED(ActionType.SUCCESS_ATTACK, "物流运输网络瘫痪", "Logistics And Transportation Network Is Paralyzed", "logistics-and-transportation-network-is-paralyzed", false, false, false, "LOIC", "大量数据包会使网络出现瘫痪，导致网站服务、api、应用程序等无法访问。");

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

	public String getLeak() {
		return "漏洞";
	}

}
