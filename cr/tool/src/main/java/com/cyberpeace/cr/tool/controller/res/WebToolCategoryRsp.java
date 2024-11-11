package com.cyberpeace.cr.tool.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Web工具类别响应
 *
 * @author SN
 * @date 2024/11/07
 */
@ApiModel("前台工具分类")
@Data
@Builder
public class WebToolCategoryRsp implements Serializable {

    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 类别i18n名称
     */
    @ApiModelProperty("工具分类名称")
    private String categoryI18nName;
}
