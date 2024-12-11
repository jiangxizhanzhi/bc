package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceAptThreatDetectRuleBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
@TableName("space_apt_threat_detect_rule")
public class SpaceAptThreatDetectRule extends SpaceBaseTable {

	@ApiModelProperty(value = "情报类型,1:IP,2:DNS,3:URL,4:邮箱账号,5:文件HASH,6:证书HASH")
	private String type;

	@ApiModelProperty(value = "情报对象")
	private String object;

	@ApiModelProperty(value = "信誉等级")
	private Integer level;

	@ApiModelProperty(value = "工具")
	private String tool;

	public SpaceAptThreatDetectRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceAptThreatDetectRuleBo bo = BeanUtils.copy(this, SpaceAptThreatDetectRuleBo.class);
		return bo;
	}
}
