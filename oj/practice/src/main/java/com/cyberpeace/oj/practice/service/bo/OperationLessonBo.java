package com.cyberpeace.oj.practice.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课后实操练习数据
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class OperationLessonBo extends TheoryLessonBo {

	/**
	 * 文件路径
	 */
	@ApiModelProperty("文件路径")
	private String filePath;

	/**
	 * 文件名称
	 */
	@ApiModelProperty("文件名称")
	private String fileName;

	/**
	 * 拓扑id
	 */
	@ApiModelProperty("拓扑编号")
	private String topoId;

	/**
	 * 场景共享模式
	 */
	@ApiModelProperty("场景共享类型")
	private Integer sceneShareMode;

	/**
	 * 场景配置数据
	 */
	@ApiModelProperty("拓扑配置信息")
	private JSONObject sceneConfigData;

	/**
	 * 场景数据
	 */
	@ApiModelProperty("场景信息")
	private JSONObject sceneData;
}
