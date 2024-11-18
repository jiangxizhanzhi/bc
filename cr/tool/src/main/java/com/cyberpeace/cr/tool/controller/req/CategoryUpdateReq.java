package com.cyberpeace.cr.tool.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * 类别更新请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see CategoryAddReq
 * @since 1.0.0
 */
@Data
@ApiModel
public class CategoryUpdateReq extends CategoryAddReq {
    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("resourceId")
    @NotBlank
    private String resourceId;

}
