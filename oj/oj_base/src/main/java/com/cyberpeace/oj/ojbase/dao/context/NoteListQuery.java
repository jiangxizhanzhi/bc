package com.cyberpeace.oj.ojbase.dao.context;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注释列表查询
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteListQuery extends PatternBase {

	/**
	 * 用户id列表
	 */
	private List<String> userIds;

	/**
	 * 注释类型
	 */
	private Integer noteType;

	/**
	 * 业务类型
	 */
	private Integer businessType;

	/**
	 * 业务id
	 */
	private String businessId;

}
