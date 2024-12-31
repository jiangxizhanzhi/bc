package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceLoadedFileThreatRuleBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@TableName("space_loaded_file_threat_rule")
public class SpaceLoadedFileThreatRule extends SpaceBaseTable {

	@ApiModelProperty(value = "检测类型")
	private String type;

	@ApiModelProperty(value = "内容简介")
	private String description;

	@ApiModelProperty(value = "内容详情")
	private String content;

	@ApiModelProperty(value = "应用工具")
	private String tool;

	@ApiModelProperty(value = "执行方式，自动2，手动1")
	private Integer executeType;


	public SpaceLoadedFileThreatRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceLoadedFileThreatRuleBo bo = BeanUtils.copy(this, SpaceLoadedFileThreatRuleBo.class);
		return bo;
	}
}
