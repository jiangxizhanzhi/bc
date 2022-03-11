package com.cyberpeace.cr.report_score.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@ApiModel("配置模型数据")
@Data
public class ConfigAddReq implements Serializable{


    @ApiModelProperty("系统名称")
    private String name;

    @ApiModelProperty("拓扑模板名称")
    private String topoName;

    @ApiModelProperty("多个场景实例名称")
    private String scenesName;

    @ApiModelProperty("多个用户IP配置关系")
    private List<UserDataConfig> usersData;
}
