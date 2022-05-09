package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EngageCategoryBo<T extends FrameItemBase> implements Serializable {

	@ApiModelProperty("")
	private String categoryName;

	@ApiModelProperty("")
	private List<EngageSubCategoryBo<T>> subCategories;
}
