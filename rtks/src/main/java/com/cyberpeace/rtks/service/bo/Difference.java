package com.cyberpeace.rtks.service.bo;

import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.rtks.service.bo.skill.IdWeight;

import lombok.Data;

/**
 * 差异
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class Difference<T> {

	/**
	 * 编辑过权重的
	 */
	private List<T> modified = Lists.newArrayList();

	/**
	 * 新增的关联关系
	 */
	private List<IdWeight> added = Lists.newArrayList();

	/**
	 * 删除的关联关系
	 */
	private List<String> deleted = Lists.newArrayList();
}
