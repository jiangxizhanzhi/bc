
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceAptFeature;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpaceAptFeatureBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;
    

	@ApiModelProperty(value = "别名")
	private String alias;

	@ApiModelProperty(value = "攻击者类型")
	private Integer attackerType;

	@ApiModelProperty(value = "技术能力")
	private Integer skill;

	@ApiModelProperty(value = "疑似来源")
	private String source;

	@ApiModelProperty(value = "最早活动时间")
	private String firstTime;

	@ApiModelProperty(value = "最近活动时间")
	private String latestTime;

	@ApiModelProperty(value = "影响国家和地区")
	private String effected;

	@ApiModelProperty(value = "影响行业")
	private String industry;

	@ApiModelProperty(value = "攻击方式")
	private String method;

	@ApiModelProperty(value = "攻击频率")
	private Integer frequency;

	@ApiModelProperty(value = "常用语言")
	private String language;

	@ApiModelProperty(value = "组织概述")
	private String organization;

	@ApiModelProperty(value = "技战法")
	private String tactic;

	@ApiModelProperty(value = "历史报告")
	private List<AptReportHistory> historyReport;

	@ApiModelProperty(value = "logo")
	private String logo;
	@ApiModelProperty(value = "内置")
	private Boolean builtin;
	public SpaceAptFeature bo2Entity() {
		SpaceAptFeature entity = BeanUtils.copy(this, SpaceAptFeature.class);
		entity.setHistoryReport(JsonUtil.arrayToString(this.historyReport));
		return entity;
	}

}
    