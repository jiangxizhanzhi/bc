package com.cyberpeace.cr.crbase.constant;

import com.cyberpeace.constant.BaseI18n;
import lombok.Getter;

import java.util.Arrays;

/**
 * 靶场常用常量
 */
public interface CrCommonConstants {

	/**
	 * 场景模板导入导出的数据文件名
	 */
	String RESOURCE_FILE_NAME = "data.json";

	/**
	 * 场景模板资源导入导出包图片文件夹名
	 */
	String RESOURCE_IMAGE_DIR = "images";

	Integer GENERATED_UUID_LENGTH = 32;

	String MP_SCENE_ID_KEY = "resource_id";

	String TOPO_DATA_KEY = "scene_config";

	Integer STUDENT_ROLE = 1;

	Integer TERMINAL_TYPE_CODE = 4;

	int SKILL_POINTS_SIZE = ConfigReader.readInt("cr.skill.point.size");

	int SKILL_POINT_MIN_VALUE = ConfigReader.readInt("cr.skill.point.min");

	int SKILL_POINT_MAX_VALUE = ConfigReader.readInt("cr.skill.point.max");

	int MIN_STAGE_NAME_LEN = ConfigReader.readInt("cr.stage.name.len.min");

	int MAX_STAGE_NAME_LEN = ConfigReader.readInt("cr.stage.name.len.max");

	int SCORE_MAX_VALUE = ConfigReader.readInt("cr.score.max");

	int MIN_TRAINING_NAME_LEN = ConfigReader.readInt("cr.training.name.len.min");

	int MAX_TRAINING_NAME_LEN = ConfigReader.readInt("cr.training.name.len.max");

	String NULL = "null";

	Integer MIN_TAG_COUNT = ConfigReader.readInt("cr.tag.count.min");

	Integer MAX_TAG_COUNT = ConfigReader.readInt("cr.tag.count.max");

	Integer ZERO_STR_LEN = ConfigReader.readInt("cr.str.len.zero");

	Integer MIN_STR_LEN = ConfigReader.readInt("cr.str.len.min");

	Integer MAX_NAME_STR_LEN = ConfigReader.readInt("cr.str.len.name.max");

	Integer MAX_TEXT_LEN = ConfigReader.readInt("cr.text.len.max");

	Integer MAX_OPTION_STR_LEN = ConfigReader.readInt("cr.option.len.max");

	Integer MAX_DESC_LEN = ConfigReader.readInt("cr.desc.len.max");

	Integer MIN_SKILL_WEIGHT = ConfigReader.readInt("rtks.skill.weight.min");

	Integer MAX_SKILL_WEIGHT = ConfigReader.readInt("rtks.skill.weight.max");

	Integer MIN_MISSION_WEIGHT = ConfigReader.readInt("rtks.mission.weight.min");

	Integer MAX_MISSION_WEIGHT = ConfigReader.readInt("rtks.mission.weight.max");


	/**
	 * 攻击方式
	 */
	public enum  AttackPattern {
		OTHER(1, "其它"),
		BRUTEFORCE_EXT(2, "外网暴力破解"),
		BRUTEFORCE_INTER(3, "内网暴力破解"),
		BOUNCE_SHELL(4, "反弹Shell"),
		PRIVILEGE_ESCALATION(5, "本地提权"),
		WEB_COMMAND(6, "web命令执行");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		AttackPattern(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
	

	//事件类别
	public enum EventCategory{
		OTHER(1, "其它"),
		ATTACK(2, "攻击"),
		DEFENSE(3, "防守");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		EventCategory(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	

	// 攻击阶段
	public enum AttackPhase{
		OTHER(1, "其它"),
		RECONNAISSANCE(2, "目标侦察"),
		DELIVERY(3, "载荷投递"),
		EXPLOITATION(4, "渗透利用"),
		COMMAND(5, "命令控制"),
		ACTIONS(6, "任务执行");
			
		@Getter
		private Integer code;

		@Getter
		private String desc;

		AttackPhase(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}}
	

	// 防御阶段
	public enum DefensePhase{
		OTHER(1, "其它"),
		COUNTER_INVESTIGATION(2, "反侦察"),
		REVERSE_DELIVERY(3, "反投递"),
		REVERSE_OSMOSIS(4, "反渗透"),
		ANTI_CONTROL(5, "反控制"),
		COUNTER_EXECUTION(6, "反执行");
		
		@Getter
		private Integer code;

		@Getter
		private String desc;

		DefensePhase(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}}

	

	// 攻击行为
	public enum AttackAction{

		OTHER(1, "其它"),
		NET_SCAN(2, "网络扫描"),
		PASSWORD_BRUTE(3, "口令爆破"),
		BOUNCE_SHELL(4, "反弹shell"),
		SPRINGBOARD(5, "建立跳板、端口转发"),
		HOST_FALL(6, "攻陷主机拿到root权限"),
		DELIVERY(7, "载荷投递"),
		TARGET_SCAN(8, "目标扫描");
		
		@Getter
		private Integer code;

		@Getter
		private String desc;

		AttackAction(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}}
	

	// 防御行为
	public enum DefenseAction{

		OTHER(1, "其它"),
		ATTACK_SOURCE_DETECTED(2, "检测到攻击源"),
		TRY_DEFEND(3, "尝试防守"),
		REMOVE_TROJAN_HORSES(4, "清除木马与进程"),
		SUSPICIOUS_FILES_FOUND(5, "发现可疑文件"),
		CONFIGURE_FIREWALL_POLICY(6, "配置防火墙策略生效"),
		HOST_HARDENING(7, "完成系统加固动作");
		@Getter
		private Integer code;

		@Getter
		private String desc;

		DefenseAction(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public static <T extends DefenseAction> T getEnumByCode(Integer code, Class<T> enumClass) {
			return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getCode().equals(code))
						 .findFirst().orElse(null);
		}
	}


	// 行为类别
	public enum ActionType{
		OTHER(1, "其它"),
		BOUNDARY_BREAKTHROUGH(2, "边界突破"),
		COMMAND_CONTROL(3, "命令与控制"),
		PRIVILEGE_ESCALATION(4, "权限提升"),
		DEFENSIVE_CONFRONTATION(5, "防御对抗"),
		PERMISSION_MAINTENANCE(6, "权限维持"),
		RECONNAISSANCE(7, "情报侦察"),
		CREDENTIAL_EXTRACTION(8, "凭据提取");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		ActionType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}}

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

		public static <T extends EventType> T getEnumByCode(Integer code, Class<T> enumClass) {
			return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getCode().equals(code))
						 .findFirst().orElse(null);
		}

	}

	public enum PermissionGroup implements BaseI18n {

		ADMINISTRATOR(1,"管理员", "Administrator"),
		DIRECTOR(2, "导演", "Director"),
		REFEREE(3, "裁判", "Referee"),
		RED(4, "红方", "Red"),
		BLUE(5, "蓝方", "Blue")		;

		@Getter
		private final Integer code;

		@Getter
		private final String chinese;

		@Getter
		private final String english;

		PermissionGroup(Integer code, String chinese, String english) {
			this.code = code;
			this.chinese = chinese;
			this.english = english;
		}


	}




}
