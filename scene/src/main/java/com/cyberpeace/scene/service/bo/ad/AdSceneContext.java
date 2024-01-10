package com.cyberpeace.scene.service.bo.ad;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdSceneContext {

	private String accessUserId;

	@ApiModelProperty(value = "销毁时间,单位：小时", name = "destroy_time")
	private Integer destroyTime;

	private String raceId;

	private String questionId;

	@ApiModelProperty(value = "实例类型,0=课程，1=实操，2=运维", name = "instance_type")
	private Integer instanceType;

	@ApiModelProperty(value = "日志对象名称, 题目类型-习题集-习题名", name = "target_name")
	private String targetName;
}
