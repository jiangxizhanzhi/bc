package com.cyberpeace.cr.redvsblue.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝对抗组织统计学Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel("红蓝对抗组织排名统计信息")
public class RbOrganizationStatisticsBo implements Serializable {

	/**
	 * 组织id
	 */
	@ApiModelProperty("组织id")
    private String orgId;

	/**
	 * 组织名
	 */
	@ApiModelProperty("组织名")
    private String orgName;

	/**
	 * 平均得分
	 */
	@ApiModelProperty("平均得分")
    private Double averageScore;

}
