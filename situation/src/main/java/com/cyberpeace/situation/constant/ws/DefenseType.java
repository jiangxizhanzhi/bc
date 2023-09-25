package com.cyberpeace.situation.constant.ws;

import com.cyberpeace.common.model.CommonContext;
import lombok.Getter;

import java.util.Arrays;

public enum DefenseType {

	/**
	 * 防御类型
	 */

	DETECTION_AND_ELIMINATION_OF_TROJAN_HORSE_VIRUS(DefenseActionType.VIRUS_DETECTION_AND_TREATMENT, "木马病毒的检测与清除","Detection And Elimination Of Trojan Horse Virus","detection-and-elimination-of-trojan-horse-virus", "火绒安全", "火绒安全病毒查杀，主机全盘扫描"),
	DETECTION_AND_TRANSMISSION_BLOCKING_OF_WORM_VIRUS(DefenseActionType.VIRUS_DETECTION_AND_TREATMENT, "蠕虫病毒的检测与传播阻断","Detection And Transmission Blocking Of Worm Virus","detection-and-transmission-blocking-of-worm-virus", "火绒安全", "火绒安全病毒查杀，主机全盘扫描"),
	DETECTION_AND_MITIGATION_OF_DOS(DefenseActionType.VIRUS_DETECTION_AND_TREATMENT, "Dos的检测与缓解","Detection And Mitigation Of Dos","detection-and-mitigation-of-dos", "防火墙", "防火墙白名单设置地址有限访问"),
	HOST_SECURITY_DETECTION_AND_DEFENSE(DefenseActionType.SECURITY_REINFORCEMENT, "主机安全检测和防御","Host Security Detection And Defense","host-security-detection-and-defense", "火绒安全", "主机安全扫描，识别脆弱环节"),
	VULNERABILITY_PATCH_REPAIR(DefenseActionType.SYSTEM_REPAIR, "漏洞补丁修复","Vulnerability Patch Repair","vulnerability-patch-repair", "D盾", "根据D盾发现的漏洞信息进行补丁升级，修复已知漏洞。"),
	INSTALL_IMMUNIZATION_TOOLS(DefenseActionType.SECURITY_REINFORCEMENT, "安装免疫工具","Install Immunization Tools","install-immunization-tools", "人工", "安装360、火绒安全，webshell查杀工具等安全软件，启动主机防火墙"),
	DETECT_DEFENSE(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "检测防御","Detect Defense","detect-defense", "火绒安全", "火绒安全扫描，安装安全插件"),
	RESTORE_DEFENSE(DefenseActionType.SYSTEM_REPAIR, "恢复防御","Restore Defense","restore-defense", "Windows Defender", "启动实时保护，设置病毒和威胁防护"),
	NETWORK_DEFENSE(DefenseActionType.SECURITY_REINFORCEMENT, "网络防御","Network defense","network-defense", "防火墙", "启动防火墙，关闭高危和不常用端口"),
	SYSTEM_TROUBLESHOOTING(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "系统排查","System Troubleshooting","system-troubleshooting", "火绒剑", "帮助检测系统账户安全"),
	PROCESS_TROUBLESHOOTING(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "进程排查","Process Troubleshooting","process-troubleshooting", "火绒剑", "帮助检测主机是否存在可疑进程"),
	SERVICE_TROUBLESHOOTING(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "服务排查","Service Troubleshooting","service-troubleshooting", "火绒剑", "帮助检测主机是否存在可疑服务"),
	DOCUMENT_TRACE_TROUBLESHOOTING(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "文件痕迹排查","Document Trace Troubleshooting","document-trace-troubleshooting", "人工", "通过系统命令检查文件更新时间，文件的MD5值，历史文件记录等"),
	LOG_ANALYSIS(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "日志分析","Log Analysis","log-analysis", "ELK", "部署ELK系统，手机服务器的安全日志并分析可能存在的安全隐患"),
	MEMORY_ANALYSIS(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "内存分析","Memory Analysis","memory-analysis", "Heob", "分析进程的内存使用情况同时可以检测缓冲区溢出和内存泄漏。"),
	FLOW_ANALYSIS(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "流量分析","Flow Analysis","flow-analysis", "Moloch", "检测和分析网络流量中的潜在威胁"),
	LOG_FILE_REVIEW(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "日志文件审查","Log File Review","log-file-review", "ELK", "帮助收集、索引、搜索和可视化日志数据，增强日志文件审查能力。"),
	HIGH_INTERACTION_HONEYPOT_DEPLOYMENT(DefenseActionType.DEFENSE_DEPLOYMENT, "高交互蜜罐部署","High Interaction Honeypot Deployment","high-interaction-honeypot-deployment", "Hfish", "部署web蜜罐"),
	DECOY_DEPLOYMENT(DefenseActionType.DEFENSE_DEPLOYMENT, "诱饵部署","Decoy Deployment","decoy-deployment", "Hfish", "部署蜜罐"),
	DEVELOPMENT_OF_DEFENSE_STRATEGY(DefenseActionType.DEFENSE_DEPLOYMENT, "防御策略制定","Development Of Defense Strategy","development-of-defense-strategy", "人工", "防火墙针对不常用端口根据使用情况进行开启，针对高危端口进行封闭"),
	THREAT_EVENT_ANALYSIS(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "威胁事件分析","Threat Event Analysis","threat-event-analysis", "人工", "针对恶意行为分析攻击者的目标"),
	BASIC_TRACEABILITY_ANALYSIS(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "基础溯源分析","Basic Traceability Analysis","basic-traceability-analysis", "Moloch", "利用攻击检查主机的互联情况"),
	DEEP_TRACEABILITY_ANALYSIS(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "深度溯源分析","Deep Traceability Analysis","deep-traceability-analysis", "人工", "检查主机互联的端口信息，分析主机的安全日志"),
	THREAT_INTELLIGENCE_COLLECTION(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "威胁情报收集","Threat Intelligence Collection","threat-Intelligence-collection", "Wazuh", "利用工具收集主机的安全事件"),
	EMULATE_WEB_HONEYPOT(DefenseActionType.DEFENSE_DEPLOYMENT, "仿真Web蜜罐","Emulate Web Honeypot","emulate-web-honeypot", "Hfish", "部署Wordpress蜜罐"),
	COMMON_WEB_HONEYPOT(DefenseActionType.DEFENSE_DEPLOYMENT, "通用Web蜜罐","Common Web Honeypot","common-web-honeypot", "Hfish", "部署telnet蜜罐"),
	SYSTEM_SERVICE_HONEYPOT(DefenseActionType.DEFENSE_DEPLOYMENT, "系统服务蜜罐","System Service Honeypot","system-service-honeypot", "Hfish", "部署ssh蜜罐"),
	CAMOUFLAGE_DEFECTIVE_HONEYPOT(DefenseActionType.DEFENSE_DEPLOYMENT, "伪装缺陷蜜罐","Camouflage Defective Honeypot","camouflage-defective-honeypot", "Hfish", "部署弱口令数据库蜜罐"),
	VULNERABILITY_SECURITY_BASELINE_SCANNING(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "漏洞安全基线扫描","Vulnerability Security Baseline Scanning","vulnerability-security-baseline-scanning", "AWVS", "使用AWVS对主机系统进行漏洞扫描，评估主机安全性"),
	RISK_DISCOVERY(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "风险发现","Risk discovery","risk-discovery", "AWVS", "根据扫描结果评估主机的风险等级"),
	SECURITY_REINFORCEMENT(DefenseActionType.SECURITY_REINFORCEMENT, "安全加固","Security Reinforcement","security-reinforcement", "火绒安全", "根据杀软识别主机缺少的补丁进行系统升级和安装"),
	ACTIVE_ANALYSIS(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "主动分析","Active Analysis","active-analysis", "人工", "分析攻击者的入侵痕迹和攻击路径"),
	NETWORK_COMMUNICATION_STATUS_MONITORING(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "网络通信状态监测","Network Communication Status Monitoring","network-communication-status-monitoring", "Moloch", "监控网络设备的状态和属性，监控网络流量的变化和异常情况"),
	NETWORK_FORENSICS(DefenseActionType.SECURITY_FORENSICS, "网络取证","Network Forensics","network-forensics", "Tcpdump", "抓包分析内网流量，抓取、记录和分析网络事件，发现攻击源头。"),
	DATABASE_FORENSICS(DefenseActionType.SECURITY_FORENSICS, "数据库取证","Database Forensics","database-forensics", "SQL Extractor", "分析数据库是否被篡改并及时恢复"),
	EMAIL_FORENSICS(DefenseActionType.SECURITY_FORENSICS, "电子邮件取证","Email Forensics","email-forensics", "Foxmail", "对发件人、收件人身份进行审查，确认邮件准确的手法时间"),
	MALWARE_FORENSICS(DefenseActionType.SECURITY_FORENSICS, "恶意软件取证","Malware Forensics","malware-forensics", "IDA Pro", "病毒样本分析和溯源");

	@Getter
	private DefenseActionType defenseActionType;

	@Getter
	private String cnDesc;

	@Getter
	private String enDesc;

	@Getter
	private String keyWord;

	@Getter
	private String tool;

	@Getter
	private String successDesc;

	DefenseType(DefenseActionType defenseActionType, String cnDesc, String enDesc, String keyWord, String tool,
				String successDesc) {
		this.defenseActionType = defenseActionType;
		this.cnDesc = cnDesc;
		this.enDesc = enDesc;
		this.keyWord = keyWord;
		this.tool = tool;
		this.successDesc = successDesc;
	}

	public static <T extends DefenseType> T getEnumByKeyWord(String keyWord, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getKeyWord().equals(keyWord))
					 .findFirst().orElse(null);
	}

	public String getMsg() {
		return CommonContext.isChinese() ? cnDesc : enDesc;
	}
}
