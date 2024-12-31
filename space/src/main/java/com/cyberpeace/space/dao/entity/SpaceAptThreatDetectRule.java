package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceAptThreatDetectRuleBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@TableName("space_apt_threat_detect_rule")
public class SpaceAptThreatDetectRule extends SpaceBaseTable {

	/**
	 * 类型
	 * 1:IP,2:DNS,3:URL,4:邮箱账号,5:文件HASH,6:证书HASH
	 * @see String
	 */
	@ApiModelProperty(value = "检测类型")
	private String type;

	@ApiModelProperty(value = "应用工具")
	private String tool;

	@ApiModelProperty(value = "APT代号")
	private String aptCode;

	@ApiModelProperty(value = "内容详情")
	private String content;

	@ApiModelProperty(value = "内容简介")
	private String description;

	public SpaceAptThreatDetectRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceAptThreatDetectRuleBo bo = BeanUtils.copy(this, SpaceAptThreatDetectRuleBo.class);
		return bo;
	}
}
