package com.cyberpeace.cr.tool.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Data
@ApiModel
public class CategoryUpdateReq implements Serializable {
    @ApiModelProperty("resourceId")
    @NotBlank
    private String resourceId;

    @ApiModelProperty("中文名称")
    private String cnName;

    @ApiModelProperty("英文名称")
    private String enName;

}
