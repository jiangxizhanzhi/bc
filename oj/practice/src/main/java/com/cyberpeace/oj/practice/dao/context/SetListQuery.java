package com.cyberpeace.oj.practice.dao.context;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设置列表查询
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PatternBase
 * @since 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SetListQuery extends PatternBase {

	/**
	 * 设置id列表
	 *
	 * @see List<String>
	 */
	private List<String> setIds;

	/**
	 * 执业类型
	 *
	 * @see Integer
	 */
	private Integer practiceType;

}
