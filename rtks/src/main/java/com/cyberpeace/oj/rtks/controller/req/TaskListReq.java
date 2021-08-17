package com.cyberpeace.oj.rtks.controller.req;

import com.cyberpeace.oj.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@ApiModel
public class TaskListReq extends PageReqBase {
    @ApiModelProperty(value = "检索参数")
    @Getter
    @Setter
    private String pattern;
}
