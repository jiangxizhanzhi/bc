
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceAptThreatDetectRule;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpaceAptThreatDetectRuleBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "情报类型 1:IP,2:DNS,3:URL,4:邮箱账号,5:文件HASH,6:证书HASH")
	private String type;

	@ApiModelProperty(value = "情报对象")
	private String object;

	@ApiModelProperty(value = "信誉等级")
	private Integer level;

	@ApiModelProperty(value = "工具")
	private String tool;

	public SpaceAptThreatDetectRule bo2Entity() {
		SpaceAptThreatDetectRule entity = BeanUtils.copy(this, SpaceAptThreatDetectRule.class);
		return entity;
	}

}
    