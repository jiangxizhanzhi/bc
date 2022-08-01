package com.cyberpeace.cr.teamtrain.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("团队训练组织排名统计信息")
public class TeamOrganizationStatisticsBo implements Serializable {

    @ApiModelProperty("组织id")
    private String orgId;

    @ApiModelProperty("组织名")
    private String orgName;

    @ApiModelProperty("平均得分")
    private Double averageScore;

}
