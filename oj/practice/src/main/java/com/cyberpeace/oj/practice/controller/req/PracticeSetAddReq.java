package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
public class PracticeSetAddReq implements Serializable {

	@ApiModelProperty(value = "名称", required = true)
	@NotBlank(groups = {PracticeSetAddReq.class})
	private String name;

	@ApiModelProperty(value = "封面地址")
	private String logo;

	@ApiModelProperty(value = "类型 0=理论基础 2=实践操作", allowableValues = "0,2", required = true)
	@NotNull(groups = {PracticeSetAddReq.class})
	private Integer practiceType;

	@ApiModelProperty(value = "公开", required = true)
	@NotNull(groups = {PracticeSetAddReq.class})
	private Boolean isPublic;

}
