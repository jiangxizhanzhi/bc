package com.cyberpeace.oj.practice.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Web实操Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class WebOperationBo extends OperationBo {

	/**
	 * 解决详情
	 */
	private PracticeNoSolvedBo resolvedDetail;

	/**
	 * 是否收藏
	 */
	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	/**
	 * 场景数据
	 */
	@ApiModelProperty("场景数据")
	private JSONObject sceneData;

	/**
	 * 习题集名称
	 */
	@ApiModelProperty("习题集名称")
	private String practiceSetName;

}
