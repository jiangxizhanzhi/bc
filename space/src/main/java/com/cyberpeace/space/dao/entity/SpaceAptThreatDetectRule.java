package com.cyberpeace.space.dao.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceAptThreatDetectRuleBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("space_apt_threat_detect_rule")
public class SpaceAptThreatDetectRule extends SpaceBaseTable {

	@ApiModelProperty(value = "情报类型")
	private String type;

	@ApiModelProperty(value = "情报对象")
	private String object;

	@ApiModelProperty(value = "信誉等级")
	private Integer level;



	public SpaceAptThreatDetectRuleBo entity2Bo() {
		SpaceAptThreatDetectRuleBo bo = BeanUtils.copy(this, SpaceAptThreatDetectRuleBo.class);
		return bo;
	}
}
