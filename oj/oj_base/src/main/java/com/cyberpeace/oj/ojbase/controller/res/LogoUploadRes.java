package com.cyberpeace.oj.ojbase.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 图标上传结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
@AllArgsConstructor
public class LogoUploadRes implements Serializable {

    /**
     * 网址
     *
     * @see String
     */
    @ApiModelProperty("可访问路径")
    private String url;

}
