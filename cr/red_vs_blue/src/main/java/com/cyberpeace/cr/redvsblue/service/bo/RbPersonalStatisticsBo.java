package com.cyberpeace.cr.redvsblue.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("红蓝对抗个人排名统计信息")
public class RbPersonalStatisticsBo implements Serializable {

    @ApiModelProperty("用户resource_id")
    private String userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户姓名")
    private String name;

    @ApiModelProperty("累计得分")
    private Integer totalScore;

}
