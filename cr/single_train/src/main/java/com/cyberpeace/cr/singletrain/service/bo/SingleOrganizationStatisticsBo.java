package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("单兵训练组织排名统计信息")
public class SingleOrganizationStatisticsBo implements Serializable {

    @ApiModelProperty("组织id")
    private String orgId;

    @ApiModelProperty("组织名")
    private String orgName;

    @ApiModelProperty("平均得分")
    private Double averageScore;

}
