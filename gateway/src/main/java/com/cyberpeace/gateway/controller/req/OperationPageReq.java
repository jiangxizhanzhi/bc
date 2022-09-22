package com.cyberpeace.gateway.controller.req;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OperationPageReq extends PageReqBase {

    @ApiModelProperty("所属分类查询")
    private String category;

    @ApiModelProperty("所属习题集查询")
    private String practiceSet;

    @ApiModelProperty("难度查询(0-初级, 1-中级, 2-高级)")
    private Integer difficulty;

    @ApiModelProperty("用户编号")
    @NotBlank(message = ErrorCode.USER_INFO_MISSING)
    private String userId;

    @ApiModelProperty(name = "knowledge", value = "知识点")
    private String knowledge;

}
