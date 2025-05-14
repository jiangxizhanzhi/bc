package com.cyberpeace.space.controller.req;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceAptReport;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class SpaceAptReportPageReq extends SpacePageReqBase {

	@Getter
	@Setter
	@ApiModelProperty(value = "APT组织")
	private String apt_org;
	@Getter
	@Setter
	@ApiModelProperty(value = "报告类型")
	private Integer report_type;
	@Override
	public LambdaQueryWrapper<SpaceAptReport> req2QueryWrapper() {
		LambdaQueryWrapper<SpaceAptReport> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByDesc(SpaceAptReport::getUpdateTime);
		if (StringUtils.isNotBlank(this.getPattern())) {
			wrapper.like(SpaceAptReport::getName, this.getPattern());
		}
		if (StringUtils.isNotBlank(this.apt_org)) {
			wrapper.like(SpaceAptReport::getAptOrg, this.getApt_org());
		}
		if (this.report_type != null) {
			wrapper.eq(SpaceAptReport::getType, this.getReport_type());
		}
		wrapper.setEntityClass(SpaceAptReport.class);
		return wrapper;
	}
}
