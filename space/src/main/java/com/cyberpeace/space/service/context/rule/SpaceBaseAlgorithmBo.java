
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceBaseAlgorithm;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间基础算法Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceBaseAlgorithmBo extends SpaceBoBase {

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
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "简述")
	private String description;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "类型")
	private Integer type;

	/**
	 * 代号类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "代码语言类型")
	private Integer codeType;

	/**
	 * 代号
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "代码")
	private String code;

	/**
	 * 参数
	 *
	 * @see List<SpaceAlgorithmParameter>
	 */
	@ApiModelProperty(value = "参数")
	private List<SpaceAlgorithmParameter> parameter;

	/**
	 * BO2 实体
	 *
	 * @return SpaceBase算法
	 * @throws
	 * @see SpaceBaseAlgorithm
	 */
	public SpaceBaseAlgorithm bo2Entity() {
		SpaceBaseAlgorithm entity = BeanUtils.copy(this, SpaceBaseAlgorithm.class);
		entity.setParameter(JsonUtil.arrayToString(this.parameter));
		return entity;
	}

}
    