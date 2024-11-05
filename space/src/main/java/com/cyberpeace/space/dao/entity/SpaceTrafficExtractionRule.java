package com.cyberpeace.space.dao.entity;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceTrafficExtractionRuleBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("space_traffic_extraction_rule")
public class SpaceTrafficExtractionRule extends SpaceBaseTable {

	@ApiModelProperty(value = "顺序")
	@TableField("`order`")
	private String order;

	@ApiModelProperty(value = "文件名")
	private String filename;

	@ApiModelProperty(value = "路径")
	private String route;



	public SpaceTrafficExtractionRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceTrafficExtractionRuleBo bo = BeanUtils.copy(this, SpaceTrafficExtractionRuleBo.class);
		return bo;
	}
}
