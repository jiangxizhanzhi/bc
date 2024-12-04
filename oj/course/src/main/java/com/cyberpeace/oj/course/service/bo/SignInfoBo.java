package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 标牌信息 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class SignInfoBo implements Serializable {

	/**
	 * 完成计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成人数")
	private Integer finishCount = 0;

	/**
	 * 是符号
	 *
	 * @see Integer
	 */
	@ApiModelProperty("签到人数据")
	private Integer isSign = 0;

	/**
	 * 未签名
	 *
	 * @see Integer
	 */
	@ApiModelProperty("未签到人数")
	private Integer notSign = 0;

	/**
	 * 组织信息
	 *
	 * @see String
	 */
	@ApiModelProperty("组织信息")
	private String organizationInfo;

}
