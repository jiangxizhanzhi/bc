package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceLoadedFileThreatRuleBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
@TableName("space_loaded_file_threat_rule")
public class SpaceLoadedFileThreatRule extends SpaceBaseTable {

	@ApiModelProperty(value = "规则内容")
	private String content;

	@ApiModelProperty(value = "规则描述")
	private String description;

	@ApiModelProperty(value = "工具")
	private String tool;


	public SpaceLoadedFileThreatRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceLoadedFileThreatRuleBo bo = BeanUtils.copy(this, SpaceLoadedFileThreatRuleBo.class);
		bo.setTool(JsonUtil.toJsonObjectArray(this.tool, String.class));
		return bo;
	}
}
