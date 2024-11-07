package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 参与子类别 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class EngageSubCategoryBo<T extends FrameItemBase> implements Serializable {

	/**
	 * 子类别名称
	 */
	@ApiModelProperty("技术名称")
	private String subCategoryName;

	/**
	 * 从事
	 */
	@ApiModelProperty("子技术")
	private List<T> engages;

	public EngageSubCategoryBo() {
		engages = Lists.newArrayList();
	}
}
