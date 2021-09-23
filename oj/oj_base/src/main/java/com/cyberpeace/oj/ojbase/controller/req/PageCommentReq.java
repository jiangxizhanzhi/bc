package com.cyberpeace.oj.ojbase.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PageCommentReq extends PageReqBase {

    @ApiModelProperty("资源编号")
    @NotBlank
    private String resource;

}
