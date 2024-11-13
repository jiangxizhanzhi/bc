package com.cyberpeace.cr.crbase.controller.rsp;

import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.crbase.controller.rsp.constants.MissionCategoryRsp;
import com.cyberpeace.cr.crbase.controller.rsp.guide.AttackCodeNameRsp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 回放选项响应
 *
 * @author SN
 * @date 2024/11/12
 */
@Data
public class PlaybackOptionRsp implements Serializable {

	/**
	 * 开始时间
	 */
	@ApiModelProperty("开始时间")
	private String startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty("结束时间")
	private String endTime;

	/**
	 * 攻击事件名称
	 */
	@ApiModelProperty("攻击事件名称列表")
	private List<String> attackEventNames;

	/**
	 * 防御事件名称
	 */
	@ApiModelProperty("防御事件名称列表")
	private List<String> defenseEventNames;

	/**
	 * 阻断事件名称列表
	 */
	@ApiModelProperty("阻断事件名称列表")
	private List<String> blockingEventNames;

	/**
	 * 所属事件名称
	 */
	@ApiModelProperty("归属事件名称列表")
	private List<String> belongingEventNames;

	/**
	 * 攻击类型
	 */
	@ApiModelProperty("攻击类型")
	private List<AttackCodeNameRsp> attackType;

	/**
	 * 防御型
	 */
	@ApiModelProperty("防御类型")
	private List<CodeNameRsp> defenseType;

	/**
	 * 结果
	 */
	@ApiModelProperty("结果")
	private List<CodeNameRsp> result;

	/**
	 * 岗位类型
	 */
	@ApiModelProperty("归属角色")
	private List<CodeNameRsp> roleType;

	/**
	 * 节点列表
	 */
	@ApiModelProperty("节点列表")
	private List<MissionCategoryRsp> nodeList;

}
