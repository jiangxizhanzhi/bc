package com.cyberpeace.cr.redvsblue.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("红蓝对抗组织排名统计信息")
public class RbOrganizationStatisticsBo implements Serializable {

    @ApiModelProperty("组织id")
    private String orgId;

    @ApiModelProperty("组织名")
    private String orgName;

    @ApiModelProperty("平均得分")
    private Double averageScore;

}
