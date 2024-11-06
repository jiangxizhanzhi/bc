package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class StagedDefenseCategory {

	/**
	 * 阶段
	 */
	@ApiModelProperty(value = "1:事前阶段，2:事中阶段，3:事后阶段")
	private Integer stage;

    /**
     * 类别
     */
    private List<TacticDefenseCategoryBo> categories;
}
