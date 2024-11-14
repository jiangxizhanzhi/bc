package com.cyberpeace.oj.practice.service.bo;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.oj.practice.entity.PracticeOperation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 实操Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class OperationBo extends PracticeOperation {

	/**
	 * 文件名称
	 */
	@ApiModelProperty("文件名称")
	private String fileName;

	/**
	 * 文件地址
	 */
	@ApiModelProperty("文件地址")
	private String fileUrl;

	/**
	 * 场景配置信息
	 */
	@ApiModelProperty("场景配置信息")
	private JSONObject sceneConfigData;

	/**
	 * 后台实操习题附件 主要针对随机flag
	 */
	@ApiModelProperty("后台实操习题附件 主要针对随机flag")
	private List<PracticeOperationFileBo> files;

}
