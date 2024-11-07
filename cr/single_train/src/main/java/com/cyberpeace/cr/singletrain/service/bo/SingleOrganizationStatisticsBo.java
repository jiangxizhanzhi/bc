package com.cyberpeace.cr.singletrain.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 单个组织统计 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel("单兵训练组织排名统计信息")
public class SingleOrganizationStatisticsBo implements Serializable {

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private String orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty("组织名")
    private String orgName;

    /**
     * 平均分
     */
    @ApiModelProperty("平均得分")
    private Double averageScore;

}
