package com.cyberpeace.space.service.context.tactic;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.space.dao.entity.SpaceTacticAttack;
import com.cyberpeace.space.model.req.SpacePageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class SpaceTacticPageReq extends SpacePageReqBase {

	@ApiModelProperty(value = "战法分类")
	@Getter
	@Setter
	private String category_id;

	@ApiModelProperty(value = "体系id")
	@Getter
	@Setter
	private String system_id;

	@Override
	public LambdaQueryWrapper req2QueryWrapper() {
		LambdaQueryWrapper<SpaceTacticAttack> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByDesc(SpaceTacticAttack::getUpdateTime);
		if (StringUtils.isNotBlank(this.getPattern())) {
			wrapper.like(SpaceTacticAttack::getName, this.getPattern());
		}
		if (StringUtils.isNotBlank(this.getCategory_id())) {
			wrapper.eq(SpaceTacticAttack::getCategoryId, this.getCategory_id());
		}
		if (StringUtils.isNotBlank(this.getSystem_id())) {
			wrapper.eq(SpaceTacticAttack::getSystemId, this.getSystem_id());
		}
		wrapper.setEntityClass(SpaceTacticAttack.class);
		return wrapper;
	}
}
