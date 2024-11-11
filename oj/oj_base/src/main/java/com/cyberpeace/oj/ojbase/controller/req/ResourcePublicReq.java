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
 * @date 2024/11/11
 */
@Data
public class ResourcePublicReq {

    /**
     * 资源id列表
     */
    @ApiModelProperty("更新资源ID")
    @NotEmpty
    private List<String> resourceIds;

    /**
     * 是公共
     */
    @ApiModelProperty("是否公开")
    @NotNull
    private Boolean isPublic;
}
