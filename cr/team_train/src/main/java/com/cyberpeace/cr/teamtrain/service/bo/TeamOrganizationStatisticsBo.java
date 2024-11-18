package com.cyberpeace.cr.teamtrain.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 团队组织统计 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("团队训练组织排名统计信息")
public class TeamOrganizationStatisticsBo implements Serializable {

    /**
     * 组织id
     *
     * @see String
     */
    @ApiModelProperty("组织id")
    private String orgId;

    /**
     * 组织名称
     *
     * @see String
     */
    @ApiModelProperty("组织名")
    private String orgName;

    /**
     * 平均分
     *
     * @see Double
     */
    @ApiModelProperty("平均得分")
    private Double averageScore;

}
