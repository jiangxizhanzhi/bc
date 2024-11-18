package com.cyberpeace.cr.crbase.controller.req;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务修改请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MissionModifyReq implements Serializable {

	/**
	 * 标题
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "标题", required = true)
	private String title;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "任务id", required = true)
	private String resourceId;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "分值", required = true)
	private Integer score;

	/**
	 * 技能点
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "技能点", required = true)
	private String skillPoints;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "描述", required = true)
	private String description;


	public void validate() {
		// 验证名称长度
		CrCommonValidator.validateNameLen(title, CrBusinessErrorCode.INVALID_MISSION_NAME);

		// validate skill points
		List<Integer> points = JSON.parseArray(skillPoints, Integer.class);
		for (Integer skillPoint : points) {
			CrCommonValidator.validateRange(skillPoint, CrCommonConstants.SKILL_POINT_MIN_VALUE,
					CrCommonConstants.SKILL_POINT_MAX_VALUE,
					CrBusinessErrorCode.INVALID_SKILL_POINT_VALUE);
		}
		// validate score
		CrCommonValidator.validateScore(score, CrCommonConstants.SCORE_MAX_VALUE,
				CrBusinessErrorCode.SCORE_OUT_OF_RANGE);
	}

}