package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * rmimport请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("资管数据导入基础请求类")
public class RMImportReq implements Serializable {

    /**
     * 路径
     *
     * @see String
     */
    @ApiModelProperty("资源地址绝对路径")
    @NotBlank
    private String path;

}
