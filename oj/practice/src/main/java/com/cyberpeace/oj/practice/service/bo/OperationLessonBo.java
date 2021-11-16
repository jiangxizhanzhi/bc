package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class OperationLessonBo extends TheoryLessonBo {

	@ApiModelProperty("文件路径")
	private String filePath;

	@ApiModelProperty("文件名称")
	private String fileName;

	@ApiModelProperty("拓扑编号")
	private String topoId;

	@ApiModelProperty("场景配置信息")
	private String sceneConfigData;
}
