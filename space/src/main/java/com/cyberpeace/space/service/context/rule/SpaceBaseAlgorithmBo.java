
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

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;
    

	@ApiModelProperty(value = "简述")
	private String description;

	@ApiModelProperty(value = "类型")
	private Integer type;

	@ApiModelProperty(value = "代码语言类型")
	private Integer codeType;

	@ApiModelProperty(value = "代码")
	private String code;

	@ApiModelProperty(value = "参数")
	private List<SpaceAlgorithmParameter> parameter;

	public SpaceBaseAlgorithm bo2Entity() {
		SpaceBaseAlgorithm entity = BeanUtils.copy(this, SpaceBaseAlgorithm.class);
		entity.setParameter(JsonUtil.arrayToString(this.parameter));
		return entity;
	}

}
    