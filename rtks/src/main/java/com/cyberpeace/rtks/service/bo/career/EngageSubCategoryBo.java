package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EngageSubCategoryBo<T extends FrameItemBase> implements Serializable {

	/**
	 * 技术名称
	 */
	@ApiModelProperty("技术名称")
	private String subCategoryName;

	/**
	 * 子技术
	 */
	@ApiModelProperty("子技术")
	private List<T> engages;

	public EngageSubCategoryBo() {
		engages = Lists.newArrayList();
	}
}
