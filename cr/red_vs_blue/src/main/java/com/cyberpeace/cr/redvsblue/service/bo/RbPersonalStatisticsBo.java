package com.cyberpeace.cr.redvsblue.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝对抗个人统计学Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel("红蓝对抗个人排名统计信息")
public class RbPersonalStatisticsBo implements Serializable {

	/**
	 * 用户resource_id
	 */
	@ApiModelProperty("用户resource_id")
    private String userId;

	/**
	 * 用户名
	 */
	@ApiModelProperty("用户名")
    private String username;

	/**
	 * 用户姓名
	 */
	@ApiModelProperty("用户姓名")
    private String name;

	/**
	 * 累计得分
	 */
	@ApiModelProperty("累计得分")
    private Integer totalScore;

}
