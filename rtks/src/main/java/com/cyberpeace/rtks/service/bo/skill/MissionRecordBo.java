package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务记录Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see MissionTemplateSimple
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MissionRecordBo extends MissionTemplateSimple implements Serializable {

	/**
	 * 关联技能id
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("关联技能id")
	private List<String> skillIds = Lists.newArrayList();

	/**
	 * 最后评分时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("最后评分时间")
	Date time;
}
