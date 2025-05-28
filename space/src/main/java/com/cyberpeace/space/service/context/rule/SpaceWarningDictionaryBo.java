
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceWarningDictionary;
import com.cyberpeace.space.model.SpaceBoBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 空间警告字典Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceWarningDictionaryBo extends SpaceBoBase {

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
	 * 工具类型
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "工具类型")
	private String toolType;

	/**
	 * 工具层级
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "工具告警等级")
	private String toolLevel;

	/**
	 * 层级
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "告警等级,低1,中2,高3")
	private Integer level;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "等级描述")
	private String description;

	/**
	 * 更新时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("更新时间")
	private Date updateTime;


	/**
	 * BO2 实体
	 *
	 * @return 空间警告词典
	 * @throws
	 * @see SpaceWarningDictionary
	 */
	public SpaceWarningDictionary bo2Entity() {
		SpaceWarningDictionary entity = BeanUtils.copy(this, SpaceWarningDictionary.class);
		return entity;
	}

}
    