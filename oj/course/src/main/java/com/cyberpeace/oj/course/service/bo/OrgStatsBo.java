package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 组织统计 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PublicStatsBo
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel("组织学习统计信息")
public class OrgStatsBo extends PublicStatsBo {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("用户resource_id")
    private String resourceId;

    /**
     * 组织数据
     *
     * @see String
     */
    @ApiModelProperty("组织名")
    private String organizationData;

    /**
     * 组织用户计数
     *
     * @see Integer
     */
    @ApiModelProperty("组织人数")
    private Integer orgUserCount;

}
