package com.cyberpeace.cr.redvsblue.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@TableName("cr_rb_combat_log")
public class RbCombatLog extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String roomId;

	private Integer eventType;

	private Integer eventCategory;

	private Integer attackPhase;

	private Integer defensePhase;

	private Integer attackAction;

	private Integer defenseAction;

	private Integer actionType;

	private Boolean isLostHost;

	private Integer sourceGroup;

	private String sourceIp;

	private String sourceName;

	private Integer destGroup;

	private String destIp;

	private String destName;

	private String agentName;

	private String agentIp;

	private String eventContent;

	private Date eventTime;

	private String originalLog;

	/**
	 * 事件类型
	 */
	public enum EventType {

		OTHER(1, "其它"),
		BRUTEFORCE_EXT(2, "外网暴力破解"),
		BRUTEFORCE_INTER(3, "内网暴力破解"),
		EXCEP_LOGIN(4, "异常登录"),
		BOUNCE_SHELL(5, "反弹Shell"),
		WIN_BOUNCE_SHELL(6, "Windows反弹Shell"),
		PRIVILEGE_ESCALATION(7, "本地提权"),
		BACKDOOR_DIAGNOSE(8, "Linux后门检测"),
		BACKDOOR_DIAGNOSE_WIN(9, "Windows后门检测"),
		WEBSHELL(10, "Web后门"),
		HONEYPOT(11, "动态蜜罐"),
		WEB_COMMAND(12, "linux Web命令执行"),
		WEB_COMMAND_WIN(13, "Windows Web命令执行"),
		VIRUS_DETECT(14, "Linux病毒查杀"),
		VIRUS_DETECT_WIN(15, "Windows病毒查杀"),
		MEM_BACKDOOR(16, "内存后门"),
		SHELL_LOG(17, "操作审计日志–Linux"),
		ACCESS_LOG(18, "登入登出日志–Linux&Windows"),
		NET_CONNECT(19, "网络连接日志–Linux&Windows"),
		PROC_CREATE(20, "进程启动日志–Linux&Windows"),
		SECURITY_PATCH(21, "风险扫描事件日志–Linux"),
		SYSTEM_AUDIT(22, "系统审计日志–Linux&Windows"),
		DNS_ACCESS(23, "DNS解析日志–Linux"),
		ACCOUNT_CHANGE(24, "账号变更–Linux"),
		AGENT_OFFLINE_CHECK(25, "Agent安全防护日志"),
		MALIC_OPERA(26, "可疑操作"),
		DEFENSE_MISSION(27, "防御任务"),
		FIREWALL_POLICY(28, "防火墙策略"), // 赛搏日志
		ATTACK_MISSION(29, "攻击任务");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		EventType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}
