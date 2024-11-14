package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组织统计Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel("组织学习统计信息")
public class OrgStatsBo extends PublicStatsBo {

	/**
	 * 用户resource_id
	 */
	@ApiModelProperty("用户resource_id")
    private String resourceId;

	/**
	 * 组织名
	 */
	@ApiModelProperty("组织名")
    private String organizationData;

	/**
	 * 组织人数
	 */
	@ApiModelProperty("组织人数")
    private Integer orgUserCount;

}
