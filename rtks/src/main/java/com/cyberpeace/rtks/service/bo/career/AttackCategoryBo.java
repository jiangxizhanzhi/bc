package com.cyberpeace.rtks.service.bo.career;

import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 攻击类别 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class AttackCategoryBo<T extends FrameItemBase> {

	/**
	 * 类别名称
	 */
	@ApiModelProperty("类型名称")
	private String categoryName;

	/**
	 * 攻击
	 */
	@ApiModelProperty("ATT&CK 列表")
	private List<T> attacks;

	public AttackCategoryBo() {
		attacks = Lists.newArrayList();
	}
}