package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.List;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务编排阶段
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class MissionSchemeStage {

	/**
	 * 阶段
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "阶段名", required = true)
	private String stage;

	/**
	 * 模板
	 *
	 * @see List<MissionSchemeMission>
	 */
	@ApiModelProperty(value = "任务列表", required = true)
	private List<MissionSchemeMission> templates;

	public void validate() {
		// 校验stage长度
		CrCommonValidator.validateLength(stage, CrCommonConstants.MIN_STAGE_NAME_LEN,
				CrCommonConstants.MAX_STAGE_NAME_LEN, CrBusinessErrorCode.INVALID_STAGE_NAME);
		List<String> titles = Lists.newArrayList();
		for (MissionSchemeMission template : templates) {
			titles.add(template.getTitle());
			// 校验任务名称长度
			template.validate();
		}
		// 校验阶段内任务重名
		CrCommonValidator.checkStringListDuplicate(titles,
				CrBusinessErrorCode.DUPLICATED_MISSION_NAME);
	}
}

/**
 * 使命 计划 使命
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
class MissionSchemeMission {

	/**
	 * 标题
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "任务标题", required = true)
	private String title;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "任务描述", required = true)
	private String description;

	public void validate() {
		CrCommonValidator.validateNameLen(title, CrBusinessErrorCode.INVALID_MISSION_NAME);
		CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_MISSION_DESC_LEN);
	}
}
