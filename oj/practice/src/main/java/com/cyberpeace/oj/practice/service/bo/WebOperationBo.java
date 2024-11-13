package com.cyberpeace.oj.practice.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Web实操博
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class WebOperationBo extends OperationBo {

	/**
	 * 解决详情
	 */
	private PracticeNoSolvedBo resolvedDetail;

	/**
	 * is 集合
	 */
	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	/**
	 * 场景数据
	 */
	@ApiModelProperty("场景数据")
	private JSONObject sceneData;

	/**
	 * 实践设置名称
	 */
	@ApiModelProperty("习题集名称")
	private String practiceSetName;

}
