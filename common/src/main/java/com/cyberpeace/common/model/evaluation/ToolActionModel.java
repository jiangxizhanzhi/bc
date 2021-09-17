package com.cyberpeace.common.model.evaluation;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.collect.Lists;

import lombok.Data;

/**
 * 动作模型
 */
@Data
public class ToolActionModel implements Serializable {

	/**
	 * 操作分类--动作
	 */
	public static int ACTION_TYPE_ACT = 1;

	/**
	 * 操作分类--采集
	 */
	public static int ACTION_TYPE_COLLECT = 2;

	public static int SUB_ACTION_START = 1;

	public static int SUB_ACTION_PAUSE = 2;

	public static int SUB_ACTION_CONTINUE = 3;

	public static int SUB_ACTION_STOP = 4;

	public static int SUB_ACTION_PROGRESS_QUERY = 5;

	public static int SUB_ACTION_LOG_QUERY = 6;

	/**
	 * 工作id
	 */
	private String toolId;

	/**
	 * 动作id
	 */
	private String actionId;

	/**
	 * 操作类型
	 */
	private String name;

	/**
	 * 操作类型
	 */
	private Integer type;

	/**
	 * 父动作id。（只有子动作才有此属性）
	 */
	private String parentActionId;

	/**
	 * 脚本内容
	 */
	private String code;

	/**
	 * 子动作在动作中的编号，从1开始
	 */
	private Integer operateType;

	/**
	 *
	 */
	private List<ToolActionParamModel> parameters;

	/**
	 * 子动作
	 */
	private List<ToolActionModel> subActions;

	/**
	 * 获取开始子动作的动作id
	 * @return
	 */
	public String getStartActionId() {
		if (CollectionUtils.isEmpty(subActions)) {
			return null;
		}

		for (ToolActionModel action : subActions) {
			if (action.getOperateType().intValue() == SUB_ACTION_START) {
				return action.getActionId();
			}
		}

		return null;
	}

	/**
	 * true表示当前是停止的action
	 * @return
	 */
	public boolean stopAction(Integer subSequenceId) {
		if (null == subSequenceId) {
			return false;
		}

		return subSequenceId.intValue() == SUB_ACTION_STOP;
	}

	/**
	 * 获取子动作id
	 * @param subSequenceId
	 * @return
	 */
	public String getSubActionId(Integer subSequenceId) {
		// 没有子动作的默认为采集操作，返回当前动作id即可
		if (null == subSequenceId) {
			return this.getActionId();
		}

		for (ToolActionModel action : subActions) {
			if (action.getOperateType().intValue() == subSequenceId) {
				return action.getActionId();
			}
		}

		return null;
	}

	/**
	 * 获取当前动作对应的工具接口输入动作id
	 * @return
	 */
	public List<String> actionRspAsReq(Integer subSequenceId) {
		List<String> rsp = Lists.newArrayList();

		ToolActionModel currentAction = this;

		if (null != subSequenceId) {
			if (CollectionUtils.isEmpty(this.subActions)) {
				return rsp;
			}

			for (ToolActionModel action : subActions) {
				if (action.getOperateType().intValue() == subSequenceId) {
					currentAction = action;
					break;
				}
			}

			if (null == currentAction) {
				return rsp;
			}
		}

		List<ToolActionParamModel> params = currentAction.getParameters();

		// 逐个判断当前参数是否符合要求
		for (ToolActionParamModel paramModel : params) {
			if (paramModel.getInputType().intValue() == ToolActionParamModel.INPUT_TYPE_TOOL) {
				rsp.add(paramModel.getActionId());
			}
		}

		return rsp;
	}
}
