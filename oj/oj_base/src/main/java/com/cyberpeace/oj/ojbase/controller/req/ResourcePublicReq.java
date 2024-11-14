package com.cyberpeace.oj.ojbase.controller.req;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 资源公共请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class ResourcePublicReq {

    /**
     * 更新资源ID
     */
    @ApiModelProperty("更新资源ID")
    @NotEmpty
    private List<String> resourceIds;

    /**
     * 是否公开
     */
    @ApiModelProperty("是否公开")
    @NotNull
    private Boolean isPublic;
}
