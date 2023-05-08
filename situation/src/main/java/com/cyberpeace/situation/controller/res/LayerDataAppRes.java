package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.situation.NodeAttackStatisticsContext;
import com.cyberpeace.integratation.model.situation.node.NodeThreatenedDistributeContext;
import com.cyberpeace.integratation.model.situation.user.UserActivityContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 跨层-应用层数据返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class LayerDataAppRes implements Serializable {

	/**
	 * 攻击IP-TOP5
	 */
	@ApiModelProperty("攻击IP-TOP5")
	private List<NodeAttackStatisticsContext> nodeAttackStatistics;

	/**
	 * 设备受威胁分布
	 */
	@ApiModelProperty("设备受威胁分布")
	private List<NodeThreatenedDistributeContext> threatenedDistribute;

	/**
	 * 用户活跃度-TOP10
	 */
	@ApiModelProperty("用户活跃度-TOP10")
	private List<UserActivityContext> userActivity;

}
