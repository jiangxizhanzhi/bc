package com.cyberpeace.rtks.service.bo;

import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.rtks.service.bo.skill.IdWeight;

import lombok.Data;

/**
 * 差异
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
public class Difference<T> {

	/**
	 * 编辑过权重的
	 *
	 * @see List<T>
	 */
	private List<T> modified = Lists.newArrayList();

	/**
	 * 新增的关联关系
	 *
	 * @see List<IdWeight>
	 */
	private List<IdWeight> added = Lists.newArrayList();

	/**
	 * 删除的关联关系
	 *
	 * @see List<String>
	 */
	private List<String> deleted = Lists.newArrayList();
}
