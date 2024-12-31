
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceAptThreatDetectRule;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceAptThreatDetectRuleBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 类型
	 * 1:IP,2:DNS,3:URL,4:邮箱账号,5:文件HASH,6:证书HASH
	 * @see String
	 */
	@ApiModelProperty(value = "情报类型")
	private String type;

	@ApiModelProperty(value = "APT代号")
	private String aptCode;

	@ApiModelProperty(value = "内容详情")
	private String content;

	@ApiModelProperty(value = "内容简介")
	private String description;

	@ApiModelProperty(value = "工具")
	private String tool;

	public SpaceAptThreatDetectRule bo2Entity() {
		SpaceAptThreatDetectRule entity = BeanUtils.copy(this, SpaceAptThreatDetectRule.class);
		return entity;
	}

}
    