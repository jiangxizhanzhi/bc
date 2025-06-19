package com.cyberpeace.ai.model.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户画像-岗位评估数据")
public class UserProfileEvaluationDataVO {

	@ApiModelProperty("岗位层级")
	private String level;

	@ApiModelProperty("头像")
	private String avatar;

	@ApiModelProperty("岗位")
	private String roleName;

	@ApiModelProperty("知识点清单")
	private List<PointDataVO> knowledgePoints;

	@ApiModelProperty("技能点清单")
	private List<PointDataVO> skillPoints;
}