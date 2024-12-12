package com.cyberpeace.space.dao.entity;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceThreatImageRuleBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("space_threat_image_rule")
public class SpaceThreatImageRule extends SpaceBaseTable {

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


	public SpaceThreatImageRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceThreatImageRuleBo bo = BeanUtils.copy(this, SpaceThreatImageRuleBo.class);
		return bo;
	}
}
