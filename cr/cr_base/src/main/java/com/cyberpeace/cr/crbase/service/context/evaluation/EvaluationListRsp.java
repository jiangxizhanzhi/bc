package com.cyberpeace.cr.crbase.service.context.evaluation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 评估列表响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class EvaluationListRsp extends EvaluationListBo implements Serializable {

	/**
	 * 提交用户名称
	 */
	@ApiModelProperty(value = "提交人名称")
	private String submitUserName;

	/**
	 * 提交用户头像
	 */
	@ApiModelProperty(value = "提交人名称")
	private String submitUserAvatar;
}
