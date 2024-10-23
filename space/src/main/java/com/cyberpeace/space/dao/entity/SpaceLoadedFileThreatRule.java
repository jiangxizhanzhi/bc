package com.cyberpeace.space.dao.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceLoadedFileThreatRuleBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("space_loaded_file_threat_rule")
public class SpaceLoadedFileThreatRule extends SpaceBaseTable {

	@ApiModelProperty(value = "规则内容")
	private String content;

	@ApiModelProperty(value = "规则描述")
	private String description;


	public SpaceLoadedFileThreatRuleBo entity2Bo() {
		SpaceLoadedFileThreatRuleBo bo = BeanUtils.copy(this, SpaceLoadedFileThreatRuleBo.class);
		return bo;
	}
}
