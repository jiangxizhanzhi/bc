package com.cyberpeace.rtks.service.bo.career;

import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AttackCategoryBo<T extends FrameItemBase> {

	@ApiModelProperty("类型名称")
	private String categoryName;

	@ApiModelProperty("ATT&CK 列表")
	private List<T> attacks;

	@ApiModelProperty("类型编号")
	private String code;

	public AttackCategoryBo() {
		attacks = Lists.newArrayList();
	}
}