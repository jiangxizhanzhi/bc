package com.cyberpeace.cr.tool.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@ApiModel("前台工具分类")
@Data
@Builder
public class WebToolCategoryRsp implements Serializable {

    private String resourceId;

    @ApiModelProperty("工具分类名称")
    private String categoryI18nName;
}
