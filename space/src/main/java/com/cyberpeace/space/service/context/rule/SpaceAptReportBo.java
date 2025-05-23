package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceAptReport;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpaceAptReportBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "报告名称")
	private String name;

	@ApiModelProperty(value = "APT组织")
	private String aptOrg;

	@ApiModelProperty(value = "发现时间")
	private String publicTime;

	@ApiModelProperty(value = "发布厂商")
	private String publicCompany;

	@ApiModelProperty(value = "报告类型， 1：APT组织分析， 2：典型攻击行动分析")
	private Integer type;

	@ApiModelProperty(value = "报告pdf路径")
	private String reportPath;
	@ApiModelProperty(value = "是否内置")
	private Boolean builtin;
	@Override
	public SpaceAptReport bo2Entity() {
        SpaceAptReport entity = BeanUtils.copy(this, SpaceAptReport.class);
        return entity;
	}
}
