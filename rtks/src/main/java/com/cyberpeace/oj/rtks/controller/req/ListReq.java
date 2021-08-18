package com.cyberpeace.oj.rtks.controller.req;

import com.cyberpeace.oj.common.model.PageReqBase2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@ApiModel
public class ListReq<Role> extends PageReqBase2<Role> {
    @ApiModelProperty(value = "模糊查询关键词")
    @Getter
    @Setter
    private String keyword;
}
