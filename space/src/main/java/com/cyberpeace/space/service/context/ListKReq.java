package com.cyberpeace.space.service.context;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("课时列表查询")
public class ListKReq extends PageReqBase {

    @ApiModelProperty(value = "岗位")
    private Integer job;

    @ApiModelProperty(value = "难度")
    private Integer handicap;

}
