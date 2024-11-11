package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 标牌信息 bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class SignInfoBo implements Serializable {

	/**
	 * 完成计数
	 */
	@ApiModelProperty("完成人数")
	private Integer finishCount = 0;

	/**
	 * 是符号
	 */
	@ApiModelProperty("签到人数据")
	private Integer isSign = 0;

	/**
	 * 未签名
	 */
	@ApiModelProperty("未签到人数")
	private Integer notSign = 0;

	/**
	 * 组织信息
	 */
	@ApiModelProperty("组织信息")
	private String organizationInfo;

}
