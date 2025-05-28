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

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 父母
	 *
	 * @see String
	 */
	private String parent;

	/**
	 * 内置
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否内置")
    private Boolean builtin;

	/**
	 * 移动
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否可删除")
	private Boolean removable;

	/**
	 * 子级
	 *
	 * @see List<SpaceStrategyFrameBo>
	 */
	private List<SpaceStrategyFrameBo> children = Lists.newArrayList();

}
