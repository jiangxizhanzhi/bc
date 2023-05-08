package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练得分
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class TrainingScore {

	@ApiModelProperty(value = "客观得分")
	private Integer getScoreObjective = 0;

	@ApiModelProperty(value = "主观得分")
	private Integer getScoreSubjective = 0;

	@ApiModelProperty(value = "客观总分")
	private Integer sumScoreObjective = 0;

	@ApiModelProperty(value = "主观总分")
	private Integer sumScoreSubjective = 0;

}
