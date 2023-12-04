package com.cyberpeace.rtks.controller.req;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import com.cyberpeace.rtks.service.bo.skill.IdWeight;
import com.cyberpeace.rtks.util.RtksCommonValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class SkillAddReq implements Serializable {

	@ApiModelProperty(value = "技能编号")
	private String resourceId;

	@ApiModelProperty(value = "技能内容")
	@NotBlank
	private String content;

	@ApiModelProperty("关联任务")
	private List<IdWeight> missions = Lists.newArrayList();

	public void validate() {
		if (CollectionUtils.isEmpty(missions)) {
			return;
		}
		// validate weight 1~9
		missions.forEach((idWeight -> {
			RtksCommonValidator.validateRange(idWeight.getWeight(),
					Integer.parseInt(ConfigUtil.getContextProperty("rtks.mission.weight.min")),
					Integer.parseInt(ConfigUtil.getContextProperty("rtks.mission.weight.max")),
					RtksBusinessErrorCode.INVALID_WEIGHT);
		}));
	}

}
