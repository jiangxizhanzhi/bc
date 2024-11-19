package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Engage类别Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EngageCategoryBo<T extends FrameItemBase> implements Serializable {

	/**
	 * 阶段名称
	 *
	 * @see String
	 */
	@ApiModelProperty("阶段名称")
	private String categoryName;

	/**
	 * 技术
	 *
	 * @see List<EngageSubCategoryBo<T>>
	 */
	@ApiModelProperty("技术")
	private List<EngageSubCategoryBo<T>> subCategories;

	public EngageCategoryBo() {
		subCategories = Lists.newArrayList();
	}
}
