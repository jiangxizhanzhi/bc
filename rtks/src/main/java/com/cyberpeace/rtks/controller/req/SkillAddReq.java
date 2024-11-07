package com.cyberpeace.rtks.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import com.cyberpeace.rtks.service.bo.skill.IdWeight;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 技能新增请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel
public class SkillAddReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "技能编号")
	private String resourceId;

	/**
	 * 内容
	 */
	@ApiModelProperty(value = "技能内容")
	@NotBlank
	private String content;

	/**
	 * 任务
	 */
	@ApiModelProperty("关联任务")
	private List<IdWeight> missions = Lists.newArrayList();

	public void validate() {
		if (CollectionUtils.isEmpty(missions)) {
			return;
		}
		// validate weight 1~9
		missions.forEach((idWeight -> {
			CrCommonValidator.validateRange(idWeight.getWeight(),
					CrCommonConstants.MIN_MISSION_WEIGHT, CrCommonConstants.MAX_MISSION_WEIGHT,
					RtksBusinessErrorCode.INVALID_WEIGHT);
		}));
	}

}
