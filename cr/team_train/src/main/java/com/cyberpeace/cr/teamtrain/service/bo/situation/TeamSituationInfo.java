package com.cyberpeace.cr.teamtrain.service.bo.situation;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.model.situation.SituationRouteBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队态势信息
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class TeamSituationInfo implements Serializable {

	/**
	 * 任务下发进度
	 */
	@ApiModelProperty(value = "任务下发进度")
	private SituationTaskProgress progress;

	/**
	 * 任务状态
	 */
	@ApiModelProperty(value = "任务状态")
	private TeamSituationTaskStatistics statistics;

	/**
	 * 攻击路径
	 */
	@ApiModelProperty(value = "攻击路径")
	private List<SituationRouteBo> route;
}
