package com.cyberpeace.cr.tool.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class WebToolListReq extends PageReqBase {

    @ApiModelProperty("分类")
    private String category;

    @ApiModelProperty("平台")
    private String platforms;

    @ApiModelProperty("授权")
    private String cracked;

    private String type;
}
