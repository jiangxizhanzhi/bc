package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 计划列表任务返回值 在获取任务列表接口使用
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class SchemeListMissionRsp implements Serializable {

	@ApiModelProperty("任务id")
	private String resourceId;

	@ApiModelProperty("任务名称")
	private String title;

	@ApiModelProperty("选项")
	private String option;

	@ApiModelProperty("任务类别")
	private String category;

	@ApiModelProperty("任务描述")
	private String description;

	@ApiModelProperty("任务状态")
	private Integer state;

}
