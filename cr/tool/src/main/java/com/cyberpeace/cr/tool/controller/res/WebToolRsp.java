package com.cyberpeace.cr.tool.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("前台工具库信息")
@Data
public class WebToolRsp implements Serializable {

    @ApiModelProperty("多个分类i18n名称逗号分割")
    private String categoryI18nName;

    @ApiModelProperty("封面链接")
    private String coverUrl;

    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    private String resourceId;

    @ApiModelProperty("发布时间")
    private String publishTime;

}
