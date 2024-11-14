package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionRecordBo extends MissionTemplateSimple implements Serializable {

	/**
	 * 关联技能id
	 */
	@ApiModelProperty("关联技能id")
	private List<String> skillIds = Lists.newArrayList();

	/**
	 * 最后评分时间
	 */
	@ApiModelProperty("最后评分时间")
	Date time;
}
