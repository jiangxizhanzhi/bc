
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceSourceAnalyzeRule;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间源分析统治Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceSourceAnalyzeRuleBo extends SpaceBoBase {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称")
	private String name;


	/**
	 * 统治
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "规则列表")
	private String rule;

	/**
	 * BO2 实体
	 *
	 * @return 空间源分析规则
	 * @throws
	 * @see SpaceSourceAnalyzeRule
	 */
	public SpaceSourceAnalyzeRule bo2Entity() {
		SpaceSourceAnalyzeRule entity = BeanUtils.copy(this, SpaceSourceAnalyzeRule.class);
		return entity;
	}

}
    