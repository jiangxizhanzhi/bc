package com.cyberpeace.cr.redvsblue.controller.rsp;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RbCombatRecordsGroupDataRsp implements Serializable {

	@ApiModelProperty(value = "攻击阶段")
	private JSONObject attackDefenseStage;

	@ApiModelProperty(value = "失陷主机")
	private JSONObject lostHost;
}
