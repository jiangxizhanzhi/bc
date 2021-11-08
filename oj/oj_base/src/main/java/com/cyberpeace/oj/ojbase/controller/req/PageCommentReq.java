package com.cyberpeace.oj.ojbase.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PageCommentReq extends PageReqBase {

    @ApiModelProperty(value = "资源编号")
    private String businessId;

    @ApiModelProperty(value = "资源类型 1=课程 2=课时 3=实操题")
    private Integer businessType;

}
