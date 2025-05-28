package com.cyberpeace.space.dao.entity;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceIocThreatBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Space IoC 威胁
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_ioc_threat")
public class SpaceIocThreat extends SpaceBaseTable {
	@ApiModelProperty(value = "类型")
	private Integer type;
	@ApiModelProperty(value = "IOC列表")
	private String ioc;


	public SpaceIocThreatBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceIocThreatBo bo = BeanUtils.copy(this, SpaceIocThreatBo.class);
		return bo;
	}
}
