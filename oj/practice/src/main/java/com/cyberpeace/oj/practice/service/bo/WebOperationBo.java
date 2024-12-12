package com.cyberpeace.oj.practice.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Web实操博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see OperationBo
 * @since 1.0.0
 */
@Getter
@Setter
public class WebOperationBo extends OperationBo {

	/**
	 * 解决详情
	 *
	 * @see PracticeNoSolvedBo
	 */
	private PracticeNoSolvedBo resolvedDetail;

	/**
	 * is 集合
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	/**
	 * 场景数据
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty("场景数据")
	private JSONObject sceneData;

	/**
	 * 实践设置名称
	 *
	 * @see String
	 */
	@ApiModelProperty("习题集名称")
	private String practiceSetName;

}
