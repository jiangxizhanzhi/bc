package com.cyberpeace.cr.crbase.controller.rsp;

import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.crbase.controller.rsp.constants.MissionCategoryRsp;
import com.cyberpeace.cr.crbase.controller.rsp.guide.AttackCodeNameRsp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PlaybackOptionRsp implements Serializable {

	@ApiModelProperty("开始时间")
	private String startTime;

	@ApiModelProperty("结束时间")
	private String endTime;

	@ApiModelProperty("攻击事件名称列表")
	private List<String> attackEventNames;

	@ApiModelProperty("防御事件名称列表")
	private List<String> defenseEventNames;

	@ApiModelProperty("阻断事件名称列表")
	private List<String> blockingEventNames;

	@ApiModelProperty("归属事件名称列表")
	private List<String> belongingEventNames;

	@ApiModelProperty("攻击类型")
	private List<AttackCodeNameRsp> attackType;

	@ApiModelProperty("防御类型")
	private List<CodeNameRsp> defenseType;

	@ApiModelProperty("结果")
	private List<CodeNameRsp> result;

	@ApiModelProperty("归属角色")
	private List<CodeNameRsp> roleType;

	@ApiModelProperty("节点列表")
	private List<MissionCategoryRsp> nodeList;

	@ApiModelProperty("区域列表")
	private List<MissionCategoryRsp> areaList;

}
