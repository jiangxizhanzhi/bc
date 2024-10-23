
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceIocThreat;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SpaceIocThreatBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;
    
	@ApiModelProperty(value = "类型")
	private Integer type;
	@ApiModelProperty(value = "IOC列表")
	private String ioc;

	public SpaceIocThreat bo2Entity() {
		SpaceIocThreat entity = BeanUtils.copy(this, SpaceIocThreat.class);
		return entity;
	}

}
    