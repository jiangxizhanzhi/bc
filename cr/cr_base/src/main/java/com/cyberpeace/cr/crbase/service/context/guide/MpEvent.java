package com.cyberpeace.cr.crbase.service.context.guide;

import com.cyberpeace.constant.BaseI18n;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Data
public class MpEvent implements Serializable {

	@ApiModelProperty("动作类型：attack:攻击,defense:防御")
	private String actionType;

	@ApiModelProperty("攻防类型名称")
	private List<String> attCkNames;

	@ApiModelProperty("攻防类型")
	private List<String> attCks;

	@ApiModelProperty("事件名称")
	private String eventName;

	@ApiModelProperty("场景id")
	private String id;

	@ApiModelProperty("源节点id")
	private String sourceNodeId;

	@ApiModelProperty("源节点ip")
	private String sourceNodeIp;

	@ApiModelProperty("源节点名称")
	private String sourceNodeName;

	@ApiModelProperty("源节点端口")
	private String sourceNodePort;

	@ApiModelProperty("目的点id")
	private String targetNodeId;

	@ApiModelProperty("目的点ip")
	private String targetNodeIp;

	@ApiModelProperty("目的点名称")
	private String targetNodeName;

	@ApiModelProperty("目的点端口")
	private String targetNodePort;

	@ApiModelProperty("触发类型:host:主机采集,flow:流量采集")
	private String triggerType;

	@ApiModelProperty("时间")
	private String time;

	public enum TriggerType implements BaseI18n {

		HOST("host", "主机采集", "Host Collection"),
		FLOW("flow", "流量采集", "Flow Collection");

		@Getter
		private final String code;

		@Getter
		private final String chinese;

		@Getter
		private final String english;

		TriggerType(String code, String chinese, String english) {
			this.code = code;
			this.chinese = chinese;
			this.english = english;
		}
		@Override
		public String getChinese() {
			return this.chinese;
		}

		@Override
		public String getEnglish() {
			return this.english;
		}

		public static String getMsgByCode(String code) {
			return Arrays.stream(MpEvent.TriggerType.values())
						 .filter(item -> item.getCode().equals(code))
						 .findFirst()
						 .orElseThrow(() -> new IllegalArgumentException("The code of EventType isn't available"))
						 .getMsg();
		}
	}

	public enum AttackType implements BaseI18n {

		ATTACK("attack", "攻击", "attack"),
		DEFENSE("defense", "防御", "defense");

		@Getter
		private final String code;

		@Getter
		private final String chinese;

		@Getter
		private final String english;

		AttackType(String code, String chinese, String english) {
			this.code = code;
			this.chinese = chinese;
			this.english = english;
		}
		@Override
		public String getChinese() {
			return this.chinese;
		}

		@Override
		public String getEnglish() {
			return this.english;
		}

		public static String getMsgByCode(String code) {
			return Arrays.stream(MpEvent.AttackType.values())
						 .filter(item -> item.getCode().equals(code))
						 .findFirst()
						 .orElseThrow(() -> new IllegalArgumentException("The code of EventType isn't available"))
						 .getMsg();
		}
	}

}
