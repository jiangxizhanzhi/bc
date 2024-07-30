package com.cyberpeace.scene.service.bo.res;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneListRsp {

	@ApiModelProperty(value = "")
	private String resourceId;

	@ApiModelProperty(value = "场景名称")
	private String sceneName;

	@ApiModelProperty(value = "场景类型：0=课程，1=实操，2=运维")
	private Integer instanceType;

	@ApiModelProperty(value = "创建用户")
	private String user;

	@ApiModelProperty(value = "创建用户id")
	private String userId;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "运行时长")
	private Long length;

}
