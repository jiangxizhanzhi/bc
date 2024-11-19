package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Engage子类别Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EngageSubCategoryBo<T extends FrameItemBase> implements Serializable {

	/**
	 * 技术名称
	 *
	 * @see String
	 */
	@ApiModelProperty("技术名称")
	private String subCategoryName;

	/**
	 * 子技术
	 *
	 * @see List<T>
	 */
	@ApiModelProperty("子技术")
	private List<T> engages;

	public EngageSubCategoryBo() {
		engages = Lists.newArrayList();
	}
}
