package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.AptReportHistory;
import com.cyberpeace.space.service.context.rule.SpaceAptFeatureBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@TableName("space_apt_feature")
public class SpaceAptFeature extends SpaceBaseTable {

	@ApiModelProperty(value = "别名")
	@TableField("`alias`")
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
	private String historyReport;


	public SpaceAptFeatureBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceAptFeatureBo bo = BeanUtils.copy(this, SpaceAptFeatureBo.class);
		bo.setHistoryReport(JsonUtil.toJsonObjectArray(this.historyReport, AptReportHistory.class));
		return bo;
	}
}
