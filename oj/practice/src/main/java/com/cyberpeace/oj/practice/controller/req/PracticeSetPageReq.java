package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PracticeSetPageReq extends PageReqBase {

    @ApiModelProperty("习题集类型 0=理论基础 2=实践操作")
    private Integer practiceType;

}
