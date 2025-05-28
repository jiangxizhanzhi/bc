package com.cyberpeace.space.dao.entity;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceFingerPrintBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 隔间指纹
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_finger_print")
public class SpaceFingerPrint extends SpaceBaseTable {

	@ApiModelProperty(value = "类型")
	private Integer type;

	@ApiModelProperty(value = "指纹列表")
	private String content;

	public SpaceFingerPrintBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceFingerPrintBo bo = BeanUtils.copy(this, SpaceFingerPrintBo.class);
		return bo;
	}
}
