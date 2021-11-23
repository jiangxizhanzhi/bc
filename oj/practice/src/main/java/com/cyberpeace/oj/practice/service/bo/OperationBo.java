package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class OperationBo extends PracticeOperation {

	@ApiModelProperty("文件名称")
	private String fileName;

	@ApiModelProperty("文件地址")
	private String fileUrl;

	@ApiModelProperty("场景配置信息")
	private String ojSceneConfig;

}
