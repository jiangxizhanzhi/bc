package com.cyberpeace.situation.constant.ws;

import com.cyberpeace.common.model.CommonContext;
import lombok.Getter;

import java.util.Arrays;

public enum DefenseType {

	/**
	 * 防御类型
	 */

	DETECTION_AND_ELIMINATION_OF_TROJAN_HORSE_VIRUS("木马病毒的检测与清除","Detection And Elimination Of Trojan Horse Virus","detection-and-elimination-of-trojan-horse-virus"),
	DETECTION_AND_TRANSMISSION_BLOCKING_OF_WORM_VIRUS("蠕虫病毒的检测与传播阻断","Detection And Transmission Blocking Of Worm Virus","detection-and-transmission-blocking-of-worm-virus"),
	DETECTION_AND_MITIGATION_OF_DOS("Dos的检测与缓解","Detection And Mitigation Of Dos","detection-and-mitigation-of-dos"),
	HOST_SECURITY_DETECTION_AND_DEFENSE("主机安全检测和防御","Host Security Detection And Defense","host-security-detection-and-defense"),
	VULNERABILITY_PATCH_REPAIR("漏洞补丁修复","Vulnerability Patch Repair","vulnerability-patch-repair"),
	INSTALL_IMMUNIZATION_TOOLS("安装免疫工具","Install Immunization Tools","install-immunization-tools"),
	DETECT_DEFENSE("检测防御","Detect Defense","detect-defense"),
	HOMOGENEOUS_DEFENSE("同质化防御","Homogeneous Defense","Homogeneous-defense"),
	RESTORE_DEFENSE("恢复防御","Restore Defense","restore-defense"),
	NETWORK_DEFENSE("网络防御","Network defense","network-defense"),
	SYSTEM_TROUBLESHOOTING("系统排查","System Troubleshooting","system-troubleshooting"),
	PROCESS_TROUBLESHOOTING("进程排查","Process Troubleshooting","process-troubleshooting"),
	SERVICE_TROUBLESHOOTING("服务排查","Service Troubleshooting","service-troubleshooting"),
	DOCUMENT_TRACE_TROUBLESHOOTING("文件痕迹排查","Document Trace Troubleshooting","document-trace-troubleshooting"),
	LOG_ANALYSIS("日志分析","Log Analysis","log-analysis"),
	MEMORY_ANALYSIS("内存分析","Memory Analysis","memory-analysis"),
	FLOW_ANALYSIS("流量分析","Flow Analysis","flow-analysis"),
	LOG_FILE_REVIEW("日志文件审查","Log File Review","log-file-review"),
	HIGH_INTERACTION_HONEYPOT_DEPLOYMENT("高交互蜜罐部署","High Interaction Honeypot Deployment","high-interaction-honeypot-deployment"),
	DECOY_DEPLOYMENT("诱饵部署","Decoy Deployment","decoy-deployment"),
	DEVELOPMENT_OF_DEFENSE_STRATEGY("防御策略制定","Development Of Defense Strategy","development-of-defense-strategy"),
	THREAT_EVENT_ANALYSIS("威胁事件分析","Threat Event Analysis","threat-event-analysis"),
	BASIC_TRACEABILITY_ANALYSIS("基础溯源分析","Basic Traceability Analysis","basic-traceability-analysis"),
	DEEP_TRACEABILITY_ANALYSIS("深度溯源分析","Deep Traceability Analysis","deep-traceability-analysis"),
	THREAT_INTELLIGENCE_COLLECTION("威胁情报收集","Threat Intelligence Collection","threat-Intelligence-collection"),
	EMULATE_WEB_HONEYPOT("仿真Web蜜罐","Emulate Web Honeypot","emulate-web-honeypot"),
	COMMON_WEB_HONEYPOT("通用Web蜜罐","Common Web Honeypot","common-web-honeypot"),
	SYSTEM_SERVICE_HONEYPOT("系统服务蜜罐","System Service Honeypot","system-service-honeypot"),
	CAMOUFLAGE_DEFECTIVE_HONEYPOT("伪装缺陷蜜罐","Camouflage Defective Honeypot","camouflage-defective-honeypot"),
	VULNERABILITY_SECURITY_BASELINE_SCANNING("漏洞安全基线扫描","Vulnerability Security Baseline Scanning","vulnerability-security-baseline-scanning"),
	RISK_DISCOVERY("风险发现","Risk discovery","risk-discovery"),
	SECURITY_REINFORCEMENT("安全加固","Security Reinforcement","security-reinforcement"),
	ACTIVE_ANALYSIS("主动分析","Active Analysis","active-analysis"),
	NETWORK_COMMUNICATION_STATUS_MONITORING("网络通信状态监测","Network Communication Status Monitoring","network-communication-status-monitoring"),
	NETWORK_FORENSICS("网络取证","Network Forensics","network-forensics"),
	DATABASE_FORENSICS("数据库取证","Database Forensics","database-forensics"),
	EMAIL_FORENSICS("电子邮件取证","Email Forensics","email-forensics"),
	MALWARE_FORENSICS("恶意软件取证","Malware Forensics","malware-forensics"),
	EMERGENCY_RESPONSE("应急响应","Emergency Response","emergency-response"),
	THREATEN_HUNTING("威胁狩猎","Threaten Hunting","threaten-hunting"),
	INVESTIGATION_AND_EVIDENCE_COLLECTION("调查取证","Investigation And Evidence Collection","investigation-and-evidence-collection");

	@Getter
	private String cnDesc;

	@Getter
	private String enDesc;

	@Getter
	private String keyWord;

	DefenseType(String cnDesc, String enDesc, String keyWord) {
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
