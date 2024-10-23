
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceWarningDictionary;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SpaceWarningDictionaryBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;
    
	@ApiModelProperty(value = "规则列表")
	private String rule;


	public SpaceWarningDictionary bo2Entity() {
		SpaceWarningDictionary entity = BeanUtils.copy(this, SpaceWarningDictionary.class);
		return entity;
	}

}
    