package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceWarningDictionaryBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 空间警告词典
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_warning_dictionary")
public class SpaceWarningDictionary extends SpaceBaseTable {

	@ApiModelProperty(value = "工具类型")
	private String toolType;

	@ApiModelProperty(value = "工具告警等级")
	private String toolLevel;

	@ApiModelProperty(value = "告警等级,低1,中2,高3")
	private Integer level;

	@ApiModelProperty(value = "等级描述")
	private String description;

	public SpaceWarningDictionaryBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceWarningDictionaryBo bo = BeanUtils.copy(this, SpaceWarningDictionaryBo.class);
		return bo;
	}
}
