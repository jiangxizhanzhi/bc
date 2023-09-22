package com.cyberpeace.situation.constant.ws;

import com.cyberpeace.common.model.CommonContext;
import lombok.Getter;

import java.util.Arrays;

public enum DefenseType {

	/**
	 * 防御类型
	 */

	DETECTION_AND_ELIMINATION_OF_TROJAN_HORSE_VIRUS(DefenseActionType.VIRUS_DETECTION_AND_TREATMENT, "木马病毒的检测与清除","Detection And Elimination Of Trojan Horse Virus","detection-and-elimination-of-trojan-horse-virus"),
	DETECTION_AND_TRANSMISSION_BLOCKING_OF_WORM_VIRUS(DefenseActionType.VIRUS_DETECTION_AND_TREATMENT, "蠕虫病毒的检测与传播阻断","Detection And Transmission Blocking Of Worm Virus","detection-and-transmission-blocking-of-worm-virus"),
	DETECTION_AND_MITIGATION_OF_DOS(DefenseActionType.VIRUS_DETECTION_AND_TREATMENT, "Dos的检测与缓解","Detection And Mitigation Of Dos","detection-and-mitigation-of-dos"),
	HOST_SECURITY_DETECTION_AND_DEFENSE(DefenseActionType.SECURITY_REINFORCEMENT, "主机安全检测和防御","Host Security Detection And Defense","host-security-detection-and-defense"),
	VULNERABILITY_PATCH_REPAIR(DefenseActionType.SYSTEM_REPAIR, "漏洞补丁修复","Vulnerability Patch Repair","vulnerability-patch-repair"),
	INSTALL_IMMUNIZATION_TOOLS(DefenseActionType.SECURITY_REINFORCEMENT, "安装免疫工具","Install Immunization Tools","install-immunization-tools"),
	DETECT_DEFENSE(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "检测防御","Detect Defense","detect-defense"),
	RESTORE_DEFENSE(DefenseActionType.SYSTEM_REPAIR, "恢复防御","Restore Defense","restore-defense"),
	NETWORK_DEFENSE(DefenseActionType.SECURITY_REINFORCEMENT, "网络防御","Network defense","network-defense"),
	SYSTEM_TROUBLESHOOTING(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "系统排查","System Troubleshooting","system-troubleshooting"),
	PROCESS_TROUBLESHOOTING(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "进程排查","Process Troubleshooting","process-troubleshooting"),
	SERVICE_TROUBLESHOOTING(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "服务排查","Service Troubleshooting","service-troubleshooting"),
	DOCUMENT_TRACE_TROUBLESHOOTING(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "文件痕迹排查","Document Trace Troubleshooting","document-trace-troubleshooting"),
	LOG_ANALYSIS(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "日志分析","Log Analysis","log-analysis"),
	MEMORY_ANALYSIS(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "内存分析","Memory Analysis","memory-analysis"),
	FLOW_ANALYSIS(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "流量分析","Flow Analysis","flow-analysis"),
	LOG_FILE_REVIEW(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "日志文件审查","Log File Review","log-file-review"),
	HIGH_INTERACTION_HONEYPOT_DEPLOYMENT(DefenseActionType.DEFENSE_DEPLOYMENT, "高交互蜜罐部署","High Interaction Honeypot Deployment","high-interaction-honeypot-deployment"),
	DECOY_DEPLOYMENT(DefenseActionType.DEFENSE_DEPLOYMENT, "诱饵部署","Decoy Deployment","decoy-deployment"),
	DEVELOPMENT_OF_DEFENSE_STRATEGY(DefenseActionType.DEFENSE_DEPLOYMENT, "防御策略制定","Development Of Defense Strategy","development-of-defense-strategy"),
	THREAT_EVENT_ANALYSIS(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "威胁事件分析","Threat Event Analysis","threat-event-analysis"),
	BASIC_TRACEABILITY_ANALYSIS(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "基础溯源分析","Basic Traceability Analysis","basic-traceability-analysis"),
	DEEP_TRACEABILITY_ANALYSIS(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "深度溯源分析","Deep Traceability Analysis","deep-traceability-analysis"),
	THREAT_INTELLIGENCE_COLLECTION(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "威胁情报收集","Threat Intelligence Collection","threat-Intelligence-collection"),
	EMULATE_WEB_HONEYPOT(DefenseActionType.DEFENSE_DEPLOYMENT, "仿真Web蜜罐","Emulate Web Honeypot","emulate-web-honeypot"),
	COMMON_WEB_HONEYPOT(DefenseActionType.DEFENSE_DEPLOYMENT, "通用Web蜜罐","Common Web Honeypot","common-web-honeypot"),
	SYSTEM_SERVICE_HONEYPOT(DefenseActionType.DEFENSE_DEPLOYMENT, "系统服务蜜罐","System Service Honeypot","system-service-honeypot"),
	CAMOUFLAGE_DEFECTIVE_HONEYPOT(DefenseActionType.DEFENSE_DEPLOYMENT, "伪装缺陷蜜罐","Camouflage Defective Honeypot","camouflage-defective-honeypot"),
	VULNERABILITY_SECURITY_BASELINE_SCANNING(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "漏洞安全基线扫描","Vulnerability Security Baseline Scanning","vulnerability-security-baseline-scanning"),
	RISK_DISCOVERY(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "风险发现","Risk discovery","risk-discovery"),
	SECURITY_REINFORCEMENT(DefenseActionType.SECURITY_REINFORCEMENT, "安全加固","Security Reinforcement","security-reinforcement"),
	ACTIVE_ANALYSIS(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "主动分析","Active Analysis","active-analysis"),
	NETWORK_COMMUNICATION_STATUS_MONITORING(DefenseActionType.SYSTEM_TROUBLESHOOTING_AND_ANALYSIS, "网络通信状态监测","Network Communication Status Monitoring","network-communication-status-monitoring"),
	NETWORK_FORENSICS(DefenseActionType.SECURITY_FORENSICS, "网络取证","Network Forensics","network-forensics"),
	DATABASE_FORENSICS(DefenseActionType.SECURITY_FORENSICS, "数据库取证","Database Forensics","database-forensics"),
	EMAIL_FORENSICS(DefenseActionType.SECURITY_FORENSICS, "电子邮件取证","Email Forensics","email-forensics"),
	MALWARE_FORENSICS(DefenseActionType.SECURITY_FORENSICS, "恶意软件取证","Malware Forensics","malware-forensics");

	@Getter
	private DefenseActionType defenseActionType;

	@Getter
	private String cnDesc;

	@Getter
	private String enDesc;

	@Getter
	private String keyWord;

	@Getter
	private String successDesc;

	DefenseType(DefenseActionType defenseActionType, String cnDesc, String enDesc, String keyWord) {
		this.defenseActionType = defenseActionType;
		this.cnDesc = cnDesc;
		this.enDesc = enDesc;
		this.keyWord = keyWord;
	}

	public static <T extends DefenseType> T getEnumByKeyWord(String keyWord, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getKeyWord().equals(keyWord))
					 .findFirst().orElse(null);
	}

	public String getMsg() {
		return CommonContext.isChinese() ? cnDesc : enDesc;
	}
}
