package com.cyberpeace.space.service.context.strategy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

@Data
public class SpaceStrategyFrameBo {

	private String resourceId;

	private String name;

    private String parent;

	@ApiModelProperty(value = "是否内置")
    private Boolean builtin;

	@ApiModelProperty(value = "是否可删除")
	private Boolean removable;

	private List<SpaceStrategyFrameBo> children = Lists.newArrayList();

}
