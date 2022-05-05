package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("组织学习统计信息")
public class OrgStatsBo extends PublicStatsBo {

    @ApiModelProperty("用户resource_id")
    private String resourceId;

    @ApiModelProperty("组织名")
    private String orgName;

    @ApiModelProperty("组织人数")
    private Integer orgUserCount;

}
