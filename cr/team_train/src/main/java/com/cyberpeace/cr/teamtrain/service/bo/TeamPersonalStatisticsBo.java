package com.cyberpeace.cr.teamtrain.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("团队训练个人排名统计信息")
public class TeamPersonalStatisticsBo implements Serializable {

    @ApiModelProperty("用户resource_id")
    private String userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户昵称")
    private String name;

    @ApiModelProperty("累计得分")
    private Integer totalScore;

}
