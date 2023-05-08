package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务返回值
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class MissionRsp implements Serializable {

	@ApiModelProperty("任务id")
	private String resourceId;

	@ApiModelProperty("任务名称")
	private String title;

	@ApiModelProperty("选项")
	private String option;

	@ApiModelProperty("任务类型")
	private Integer type;

	@ApiModelProperty("任务类别id")
	private String category;

	@ApiModelProperty("任务描述")
	private String description;

	@ApiModelProperty("任务题解")
	private String writeUp;

	@ApiModelProperty("是否展示任务题解")
	private Boolean showWriteup;

	@ApiModelProperty("用户答案")
	private String userAnswer;
}
