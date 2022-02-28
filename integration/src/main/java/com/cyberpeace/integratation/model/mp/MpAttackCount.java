package com.cyberpeace.integratation.model.mp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel("攻击统计")
@Data
public class MpAttackCount implements Serializable {

	@ApiModelProperty("攻击百分比列表")
	private List<MpAttackWay> attackWayList;

	@ApiModelProperty("攻击次数")
	private Integer totalAttackNumber;

	@ApiModelProperty("流量大小(B)")
	private Double totalTrafficSize;

}
