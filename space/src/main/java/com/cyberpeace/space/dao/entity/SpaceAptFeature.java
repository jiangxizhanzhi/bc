package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.space.model.SpaceBaseTable;
import lombok.Data;

@Data
@TableName("space_apt_feature")
public class SpaceAptFeature extends SpaceBaseTable {

	private String alias;

	private Integer attackerType;

	private Integer skill;

	private String source;

	private String firstTime;

	private String latestTime;

	private String effected;

	private String industry;

	private String method;

	private Integer frequency;

	private String language;

	private String organization;

	private String tactic;

	private String historyReport;
}
