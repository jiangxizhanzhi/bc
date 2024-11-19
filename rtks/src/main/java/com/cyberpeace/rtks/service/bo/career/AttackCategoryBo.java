package com.cyberpeace.rtks.service.bo.career;

import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 攻击类别Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
public class AttackCategoryBo<T extends FrameItemBase> {

	/**
	 * 类型名称
	 *
	 * @see String
	 */
	@ApiModelProperty("类型名称")
	private String categoryName;

	/**
	 * ATT&CK 列表
	 *
	 * @see List<T>
	 */
	@ApiModelProperty("ATT&CK 列表")
	private List<T> attacks;

	public AttackCategoryBo() {
		attacks = Lists.newArrayList();
	}
}