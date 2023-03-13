package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.situation.NodeAttackStatisticsContext;
import com.cyberpeace.integratation.model.situation.node.NodeThreatenedDistributeContext;
import com.cyberpeace.integratation.model.situation.user.UserActivityContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LayerDataAppRes implements Serializable {

	@ApiModelProperty("攻击IP-TOP5")
	private List<NodeAttackStatisticsContext> nodeAttackStatistics;

	@ApiModelProperty("设备受威胁分布")
	private List<NodeThreatenedDistributeContext> threatenedDistribute;

	@ApiModelProperty("用户活跃度-TOP10")
	private List<UserActivityContext> userActivity;

}
