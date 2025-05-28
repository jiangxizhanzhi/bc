package com.cyberpeace.space.service.context.strategy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * 空间策略框架Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
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
