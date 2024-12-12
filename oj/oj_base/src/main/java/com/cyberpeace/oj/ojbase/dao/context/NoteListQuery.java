package com.cyberpeace.oj.ojbase.dao.context;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 注释列表查询
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PatternBase
 * @since 1.0.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteListQuery extends PatternBase {

	/**
	 * 用户id列表
	 *
	 * @see List<String>
	 */
	private List<String> userIds;

	/**
	 * 注释类型
	 *
	 * @see Integer
	 */
	private Integer noteType;

	/**
	 * 业务类型
	 *
	 * @see Integer
	 */
	private Integer businessType;

	/**
	 * 业务id
	 *
	 * @see String
	 */
	private String businessId;

}
