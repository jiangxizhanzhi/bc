package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.service.context.frame.SpaceActionBo;
import com.cyberpeace.space.service.context.frame.SpaceActionOfflineTool;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 空间作
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceFrameBaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_action")
public class SpaceAction extends SpaceFrameBaseTable {

	private Integer type;

	private String description;

	private String idNumber;

	private String parentNumber;

	private String platform;

	private String environment;

	private String action;

	@TableField("`analyze`")
	private String analyze;

	private String tool;

	private String offlineTool;

	private String strategy;

	public SpaceActionBo entity2Bo(Map<String, List<IdAndName>> relatedMap) {
		SpaceActionBo bo = BeanUtils.copy(this, SpaceActionBo.class);
		bo.setMethod(relatedMap.get(bo.getResourceId()));
		bo.setPlatform(JsonUtil.toJsonObjectArray(this.platform, String.class));
		bo.setOfflineTool(
				JsonUtil.toJsonObjectArray(this.offlineTool, SpaceActionOfflineTool.class));
		return bo;
	}
}