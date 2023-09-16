package com.cyberpeace.cr.teamtrain.service.bo.situation;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.model.situation.SituationRouteBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamSituationInfo implements Serializable {

	@ApiModelProperty(value = "任务下发进度")
	private SituationTaskProgress progress;

	@ApiModelProperty(value = "任务状态")
	private TeamSituationTaskStatistics statistics;

	@ApiModelProperty(value = "攻击路径")
	private List<SituationRouteBo> route;
}
