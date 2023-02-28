package com.cyberpeace.situation.model;

import com.cyberpeace.common.baseinterface.GenericEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Data
public class Instruction implements Serializable {

	@ApiModelProperty(value = "指令（0：准备、1：开始、2：结束、3：暂停、4：继续、5：作战时间速率调整、6：作战时间调整）", allowableValues = "0, 1, 2, 3, 4, 6")
	private Integer code;

	@ApiModelProperty("id")
	@NotEmpty
	private String resourceId;

	@ApiModelProperty(value = "天文时间")
	private Date astronomicalTime;

	@ApiModelProperty(value = "作战时间")
	private Date combatTime;

	@ApiModelProperty(value = "作战时间速率")
	private Integer rate;

	public enum InstructionType implements GenericEnum {

		PREPARE(0, "准备"),
		START(1, "开始"),
		FINISH(2, "结束"),
		PAUSE(3, "暂停"),
		CONTINUE(4, "继续"),
		RATE(5, "作战时间速率调整"),
		ADJUST(6, "作战时间调整");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		InstructionType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
