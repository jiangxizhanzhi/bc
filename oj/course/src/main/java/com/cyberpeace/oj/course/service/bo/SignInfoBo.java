package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 标志信息Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class SignInfoBo implements Serializable {

	/**
	 * 完成人数
	 */
	@ApiModelProperty("完成人数")
	private Integer finishCount = 0;

	/**
	 * 签到人数据
	 */
	@ApiModelProperty("签到人数据")
	private Integer isSign = 0;

	/**
	 * 未签到人数
	 */
	@ApiModelProperty("未签到人数")
	private Integer notSign = 0;

	/**
	 * 组织信息
	 */
	@ApiModelProperty("组织信息")
	private String organizationInfo;

}
