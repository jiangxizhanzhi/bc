package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 导出动态方案请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class ExportDynamicSchemeReq implements Serializable {

    /**
     * 动效方案id
     *
     * @see String
     */
    @ApiModelProperty(value = "动效方案id",required = true)
    @NotBlank
    private String resourceId;
}
