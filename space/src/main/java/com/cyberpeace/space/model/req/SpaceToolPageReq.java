package com.cyberpeace.space.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class SpaceToolPageReq extends SpacePageReqBase {

    @Getter
    @Setter
    @ApiModelProperty(value = "工具搜索")
	private String tool;

    @Getter
    @Setter
    @ApiModelProperty(value = "阶段")
    private Integer stage;

}
