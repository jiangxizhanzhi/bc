package com.cyberpeace.cr.tool.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * 类别更新请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class CategoryUpdateReq extends CategoryAddReq {
    /**
     * 资源id
     */
    @ApiModelProperty("resourceId")
    @NotBlank
    private String resourceId;

}
