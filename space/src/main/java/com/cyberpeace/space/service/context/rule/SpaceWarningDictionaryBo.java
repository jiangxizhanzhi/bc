
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceWarningDictionary;
import com.cyberpeace.space.model.SpaceBoBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SpaceWarningDictionaryBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "工具类型")
	private String toolType;

	@ApiModelProperty(value = "工具告警等级")
	private String toolLevel;

	@ApiModelProperty(value = "告警等级,低1,中2,高3")
	private Integer level;

	@ApiModelProperty(value = "等级描述")
	private String description;

	@ApiModelProperty("更新时间")
	private Date updateTime;


	public SpaceWarningDictionary bo2Entity() {
		SpaceWarningDictionary entity = BeanUtils.copy(this, SpaceWarningDictionary.class);
		return entity;
	}

}
    