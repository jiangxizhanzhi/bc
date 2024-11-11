package com.cyberpeace.oj.practice.service.bo;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 实操博
 *
 * @author SN
 * @date 2024/11/11
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
	 * 文件 URL
	 */
	@ApiModelProperty("文件地址")
	private String fileUrl;

	/**
	 * 场景配置数据
	 */
	@ApiModelProperty("场景配置信息")
	private JSONObject sceneConfigData;

	/**
	 * 文件
	 */
	@ApiModelProperty("后台实操习题附件 主要针对随机flag")
	private List<PracticeOperationFileBo> files;

}
