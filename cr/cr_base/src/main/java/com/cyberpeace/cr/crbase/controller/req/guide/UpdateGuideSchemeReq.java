package com.cyberpeace.cr.crbase.controller.req.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 更新导调方案请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class UpdateGuideSchemeReq extends AddGuideSchemeReq {

    /**
     * 资源id
     */
    @ApiModelProperty(value = "资源id", required = true)
    @NotBlank
    private String resourceId;

}
