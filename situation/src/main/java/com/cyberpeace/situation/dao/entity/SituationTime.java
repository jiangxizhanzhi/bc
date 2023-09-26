package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@TableName("situation_time")
public class SituationTime extends BaseTable {

	@TableId(value = "resource_id")
	private String resourceId;

	@ApiModelProperty("topoId")
	private String topoId;

	@ApiModelProperty("天文时间")
	private Date astronomicalTime;

	@ApiModelProperty("作战时间")
	private Date combatTime;

	@ApiModelProperty("运行速率")
	private Integer rate;

	@ApiModelProperty("状态：（0：未开始、1：运行中、2：暂停中、3：结束）")
	private Integer status;

	@ApiModelProperty("开始时间")
	private Date startTime;

	@ApiModelProperty("运行时间(秒)")
	private Integer runTime;

	public SituationTime() {
		this.astronomicalTime = new Date();
		this.combatTime = new Date();
		this.rate = 1;
		this.status = Status.UNOPENED.getCode();
		this.runTime = 0;
	}

	public enum Status implements GenericEnum {
		UNOPENED(0, "未开始"),
		RUNNING(1, "运行中"),
		PAUSE(2, "暂停中"),
		FINISH(3, "结束");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		Status(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
