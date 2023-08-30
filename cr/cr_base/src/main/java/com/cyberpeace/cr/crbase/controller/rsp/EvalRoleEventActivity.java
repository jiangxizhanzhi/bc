package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("评估实时消息数据")
public class EvalRoleEventActivity implements Serializable {

    private String resourceId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("红蓝队")
    private Integer roleGroup;

    private String createTime;

    @ApiModelProperty("事件数量")
    private Integer count;
}
