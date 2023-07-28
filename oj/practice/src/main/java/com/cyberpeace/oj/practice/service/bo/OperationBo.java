package com.cyberpeace.oj.practice.service.bo;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.oj.practice.entity.PracticeOperation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class OperationBo extends PracticeOperation {

	@ApiModelProperty("文件名称")
	private String fileName;

	@ApiModelProperty("文件地址")
	private String fileUrl;

	@ApiModelProperty("场景配置信息")
	private JSONObject sceneConfigData;

	@ApiModelProperty("后台实操习题附件 主要针对随机flag")
	private List<PracticeOperationFileBo> files;

}
