package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.List;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionSchemeStage {

	@ApiModelProperty(required = true)
	private String stage;

	@ApiModelProperty(required = true)
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

@Data
class MissionSchemeMission {

	@ApiModelProperty(required = true)
	private String title;

	@ApiModelProperty(required = true)
	private String description;

	public void validate() {
		CrCommonValidator.validateNameLen(title, CrBusinessErrorCode.INVALID_MISSION_NAME);
		CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
	}
}
