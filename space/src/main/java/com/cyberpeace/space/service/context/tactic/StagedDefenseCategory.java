package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分阶段防御类别
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
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
