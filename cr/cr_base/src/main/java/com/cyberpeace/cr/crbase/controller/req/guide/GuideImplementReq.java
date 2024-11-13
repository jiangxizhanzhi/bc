package com.cyberpeace.cr.crbase.controller.req.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 导调实现请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class GuideImplementReq implements Serializable {

    /**
     * 资源id
     */
    @ApiModelProperty(value = "资源id", required = true)
    @NotBlank
    private String resourceId;

    /**
     * is 实例
     */
    @ApiModelProperty("是否为实例场景")
    private Boolean isInstance;

}
