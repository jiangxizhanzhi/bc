package com.cyberpeace.cr.tool.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

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

    @ApiModelProperty("ID")
    private String resourceId;

    @ApiModelProperty("平台名称")
    private List<String> platforms;

}
