package com.cyberpeace.gateway.controller.req;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class OperationPageReq extends PageReqBase {

    @ApiModelProperty("所属分类查询")
    private String category;

    @ApiModelProperty("所属习题集查询")
    private String practiceSet;

    @ApiModelProperty("难度查询(0-初级, 1-中级, 2-高级)")
    private Integer difficulty;

    @ApiModelProperty("知识点，用逗号隔开多个知识点")
    private List<String> knowledge;

    @ApiModelProperty("用户编号")
    @NotBlank(message = ErrorCode.USER_INFO_MISSING)
    private String userId;
}
