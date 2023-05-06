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
 * 任务计划模板修改请求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class MissionSchemeTemplateModifyReq extends MissionSchemeTemplateReq implements Serializable {

	@ApiModelProperty(value = "必须字段，其他字段不更新则非必要", required = true)
	private String resourceId;

	@Override
	public void validate() {
		// 校验名称和描述长度
		if (this.getTitle() != null) {
			CrCommonValidator.validateNameLen(this.getTitle(),
					CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		}
		if (this.getDescription() != null) {
			CrCommonValidator.validateDescLen(this.getDescription(),
					CrBusinessErrorCode.ILLEGAL_DESC_LEN);
		}
		if (this.getCombatConfig() != null) {
			List<String> stageNames = Lists.newArrayList();
			for (MissionSchemeStage schemeStage : this.getCombatConfig()) {
				schemeStage.validate();
				stageNames.add(schemeStage.getStage());
			}
			// 校验阶段重名
			CrCommonValidator.checkStringListDuplicate(stageNames,
					CrBusinessErrorCode.DUPLICATED_STAGE_NAME);
		}
	}

}
