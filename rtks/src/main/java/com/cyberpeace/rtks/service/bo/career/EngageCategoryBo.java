package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EngageCategoryBo<T extends FrameItemBase> implements Serializable {

	/**
	 * 阶段名称
	 */
	@ApiModelProperty("阶段名称")
	private String categoryName;

	/**
	 * 技术
	 */
	@ApiModelProperty("技术")
	private List<EngageSubCategoryBo<T>> subCategories;

	public EngageCategoryBo() {
		subCategories = Lists.newArrayList();
	}
}
