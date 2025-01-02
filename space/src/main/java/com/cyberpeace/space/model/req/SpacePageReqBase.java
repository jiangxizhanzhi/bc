package com.cyberpeace.space.model.req;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.space.model.SpaceBaseTable;

import io.swagger.annotations.ApiModel;

@ApiModel
public class SpacePageReqBase extends PageReqBase {

	public LambdaQueryWrapper req2QueryWrapper() {
		LambdaQueryWrapper<SpaceBaseTable> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByDesc(SpaceBaseTable::getUpdateTime);
		if (StringUtils.isNotBlank(this.getPattern())) {
			wrapper.like(SpaceBaseTable::getName, this.getPattern());
		}
		wrapper.setEntityClass(SpaceBaseTable.class);
		return wrapper;
	}
}
