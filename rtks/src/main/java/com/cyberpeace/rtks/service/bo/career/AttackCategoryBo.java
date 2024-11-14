package com.cyberpeace.rtks.service.bo.career;

import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AttackCategoryBo<T extends FrameItemBase> {

	/**
	 * 类型名称
	 */
	@ApiModelProperty("类型名称")
	private String categoryName;

	/**
	 * ATT&CK 列表
	 */
	@ApiModelProperty("ATT&CK 列表")
	private List<T> attacks;

	public AttackCategoryBo() {
		attacks = Lists.newArrayList();
	}
}