package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组织统计Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see PublicStatsBo
 * @since 1.0.0
 */
@Data
@ApiModel("组织学习统计信息")
public class OrgStatsBo extends PublicStatsBo {

	/**
	 * 用户resource_id
	 *
	 * @see String
	 */
	@ApiModelProperty("用户resource_id")
    private String resourceId;

	/**
	 * 组织名
	 *
	 * @see String
	 */
	@ApiModelProperty("组织名")
    private String organizationData;

	/**
	 * 组织人数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("组织人数")
    private Integer orgUserCount;

}
