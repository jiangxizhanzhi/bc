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
 * @date 2024/11/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryListQuery extends PatternBase {

	/**
	 * 练习类型
	 */
	private Integer practiceType;

}
