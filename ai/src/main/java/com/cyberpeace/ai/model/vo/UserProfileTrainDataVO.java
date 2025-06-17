package com.cyberpeace.ai.model.vo;

import java.util.List;

import com.cyberpeace.ai.model.vo.train.TrainListDataVO;
import com.cyberpeace.ai.model.vo.train.TrainStatisticsDataVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户画像-综合训练数据")
public class UserProfileTrainDataVO {

	@ApiModelProperty("训练任务总体情况")
	private TrainStatisticsDataVO overallStats;

	@ApiModelProperty("任务完成情况")
	private List<TrainListDataVO> taskCompletion;
}