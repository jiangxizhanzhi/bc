
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceThreatImageRule;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SpaceThreatImageRuleBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;
    

	@ApiModelProperty(value = "威胁类型")
	private Integer type;

	@ApiModelProperty(value = "威胁等级")
	private Integer level;

	@ApiModelProperty(value = "受害资产类型")
	private Integer assetType;

	@ApiModelProperty(value = "威胁情报")
	private String information;

	@ApiModelProperty(value = "结果")
	private Integer result;

	@ApiModelProperty(value = "标签")
	private String tag;

	public SpaceThreatImageRule bo2Entity() {
		SpaceThreatImageRule entity = BeanUtils.copy(this, SpaceThreatImageRule.class);
		return entity;
	}

}
    