package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

/**
 * 态势导调方案表
 * @author YaoyaoMei
 */
@Data
@TableName("situation_guide_scheme")
public class SituationGuideScheme extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String sceneId;

	private String name;

	/**
	 *  1:任务导调 2:手动导调
	 */
	private Integer type;

	private Integer eventType;

	private Integer category;

	private String source;

	private String target;

	private Integer result;

	private Integer missionId;

	private Integer condition;

	public enum Type implements GenericEnum{

		ATTACK(1, "任务导调"), DEFENSE(2, "手动导调");

		@Getter
		private Integer code;
		@Getter
		private String desc;

		Type(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	public enum EventType implements GenericEnum{

		ATTACK(1, "攻击"), DEFENSE(2, "防御"), BLOCKING(3, "阻断"), BELONGING(4, "归属");

		@Getter
		private Integer code;
		@Getter
		private String desc;

		EventType(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	public enum resultType implements GenericEnum {

		SUCCESS(1, "成功"), FAIL(2, "失败");

		@Getter
		private Integer code;
		@Getter
		private String desc;

		resultType(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	public enum conditionType implements GenericEnum {

		MISSION_SUBMIT(1, "任务提交"), MISSION_SUCCESS(2, "任务判断成功");

		@Getter
		private Integer code;
		@Getter
		private String desc;

		conditionType(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
