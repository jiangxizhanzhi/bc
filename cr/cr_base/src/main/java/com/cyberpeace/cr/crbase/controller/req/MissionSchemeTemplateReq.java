package com.cyberpeace.cr.crbase.controller.req;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.service.context.mission.MissionSchemeStage;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务计划模板请求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class MissionSchemeTemplateReq implements Serializable {

	@ApiModelProperty(value = "标题", required = true)
	private String title;

	@ApiModelProperty(value = "描述", required = true)
	private String description;

	@ApiModelProperty(value = "模板类型", required = true)
	private Integer template;

	@ApiModelProperty(value = "模板颜色", required = true)
	private Integer color;

	@ApiModelProperty(value = "任务编排", required = true)
	private List<MissionSchemeStage> combatConfig;

	public void validate() {
		// 校验名称和描述长度
		CrCommonValidator.validateNameLen(title, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);

		List<String> stageNames = Lists.newArrayList();
		for (MissionSchemeStage schemeStage : combatConfig) {
			schemeStage.validate();
			stageNames.add(schemeStage.getStage());
		}
		// 校验阶段重名
		CrCommonValidator.checkStringListDuplicate(stageNames,
				CrBusinessErrorCode.DUPLICATED_STAGE_NAME);
	}
}
