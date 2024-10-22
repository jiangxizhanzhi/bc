package com.cyberpeace.space.service.context.strategy;

import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

@Data
public class SpaceStrategyFrameBo {

	private String resourceId;

	private String name;

    private String parent;

    private Boolean builtin;

	private List<SpaceStrategyFrameBo> children = Lists.newArrayList();

}
