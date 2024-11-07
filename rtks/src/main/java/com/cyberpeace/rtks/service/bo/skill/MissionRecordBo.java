package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务记录 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class MissionRecordBo extends MissionTemplateSimple implements Serializable {

	/**
	 * 技能id列表
	 */
	@ApiModelProperty("关联技能id")
	List<String> skillIds = Lists.newArrayList();

	/**
	 * 时间
	 */
	@ApiModelProperty("最后评分时间")
	Date time;
}
