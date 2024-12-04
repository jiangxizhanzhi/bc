package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.common.model.PatternBase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类别列表查询
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PatternBase
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryListQuery extends PatternBase {

	/**
	 * 执业类型
	 *
	 * @see Integer
	 */
	private Integer practiceType;

}
