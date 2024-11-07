package com.cyberpeace.cr.crbase.service.context.evaluation;

import java.io.Serializable;
import java.util.Comparator;

import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估列表响应
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class EvaluationListRsp extends EvaluationListBo implements Serializable {

	/**
	 * 提交用户名称
	 */
	@ApiModelProperty(value = "提交人名称")
	private String submitUserName;

	/**
	 * 提交用户头像
	 */
	@ApiModelProperty(value = "提交人名称")
	private String submitUserAvatar;

	// @ApiModelProperty(value = "评估人名称")
	// private String evaluatorName;

	// @ApiModelProperty(value = "评估人id")
	// private String evaluator;

	// @ApiModelProperty(value = "任务id")
	// private String missionId;

	private boolean notEvaluated() {
		return this.getEvaluateStatus().equals(CrTaskSubmit.EvaluateStatus.WAIT.getCode());
	}

	public static class EvaluationListComparator implements Comparator<EvaluationListRsp> {

		@Override
		public int compare(EvaluationListRsp o1, EvaluationListRsp o2) {
			// 待评估的排前面
			if (o1.notEvaluated() && !o2.notEvaluated()) {
				return -1;
			} else if (!o1.notEvaluated() && o2.notEvaluated()) {
				return 1;
			} else {
				// 再按提交时间倒序
				return (int) (o2.getSubmitTime().getTime() / 1000
						- o1.getSubmitTime().getTime() / 1000);
			}
		}
	}

}
