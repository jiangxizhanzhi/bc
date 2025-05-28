
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceAptThreatDetectRule;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间容易威胁检测统治Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceAptThreatDetectRuleBo extends SpaceBoBase {

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
	 * 1:IP,2:DNS,3:URL,4:邮箱账号,5:文件HASH,6:证书HASH
	 * @see String
	 */
	@ApiModelProperty(value = "情报类型")
	private String type;

	/**
	 * 容易代号
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "APT代号")
	private String aptCode;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "内容详情")
	private String content;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "内容简介")
	private String description;

	/**
	 * 工具
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "工具")
	private String tool;

	/**
	 * BO2 实体
	 *
	 * @return SpaceAPT威胁检测规则
	 * @throws
	 * @see SpaceAptThreatDetectRule
	 */
	public SpaceAptThreatDetectRule bo2Entity() {
		SpaceAptThreatDetectRule entity = BeanUtils.copy(this, SpaceAptThreatDetectRule.class);
		return entity;
	}

}
    