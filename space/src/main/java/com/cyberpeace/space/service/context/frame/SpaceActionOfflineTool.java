package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceActionOfflineTool {
    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "简述")
    private String introduction;
}
