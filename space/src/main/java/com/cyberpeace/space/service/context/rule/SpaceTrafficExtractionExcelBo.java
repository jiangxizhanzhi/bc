package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceTrafficExtractionExcelBo {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "顺序")
    private String order;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "工具")
    private String tool;


}
