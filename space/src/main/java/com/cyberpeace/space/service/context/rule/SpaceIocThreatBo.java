
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceIocThreat;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间国际 奥委会威胁Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceIocThreatBo extends SpaceBoBase {

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
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "类型")
	private Integer type;
	/**
	 * 国际 奥委会
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "IOC列表")
	private String ioc;

	/**
	 * BO2 实体
	 *
	 * @return SpaceIoC威胁
	 * @throws
	 * @see SpaceIocThreat
	 */
	public SpaceIocThreat bo2Entity() {
		SpaceIocThreat entity = BeanUtils.copy(this, SpaceIocThreat.class);
		return entity;
	}

}
    