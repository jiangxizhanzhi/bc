package com.cyberpeace.space.dao.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceWarningDictionaryBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("space_warning_dictionary")
public class SpaceWarningDictionary extends SpaceBaseTable {
	@ApiModelProperty(value = "规则列表")
	private String rule;



	public SpaceWarningDictionaryBo entity2Bo() {
		SpaceWarningDictionaryBo bo = BeanUtils.copy(this, SpaceWarningDictionaryBo.class);
		return bo;
	}
}
