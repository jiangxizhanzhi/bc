package com.cyberpeace.gateway.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class ExternalPracticeSetBo implements Serializable {

    @ApiModelProperty("习题集编号")
    private String resourceId;

    @ApiModelProperty("习题集名称")
    private String name;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("更新时间")
    private String updateTime;

}
