package com.cyberpeace.oj.course.controller.res;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TrainPlanRsp implements Serializable {

	@ApiModelProperty("训练计划编号")
	private String resourceId;

	@ApiModelProperty("训练计划名称")
	private String name;

	@ApiModelProperty("开始时间")
	private Date startTime;

	@ApiModelProperty("结束时间")
	private Date endTime;

	@ApiModelProperty("更新时间")
	private Date updateTime;

	@ApiModelProperty("数量")
	private Integer count;

	@ApiModelProperty("创建人id")
	private String createUserId;

	@ApiModelProperty("创建人名称")
	private String createUser;

	@ApiModelProperty("状态，1:未开始，2:进行中，3：已结束")
	private Integer status;

	public void setStatus(Date now) {
		if (now.before(this.startTime)) {
			this.status = 1;
		} else if (now.after(this.endTime)) {
			this.status = 3;
		} else {
			this.status = 2;
		}
	}
}
