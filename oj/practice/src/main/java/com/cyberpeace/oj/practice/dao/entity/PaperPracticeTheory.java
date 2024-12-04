package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * 纸实践理论
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("oj_paper_practice_theory")
public class PaperPracticeTheory extends BaseTable {

	/**
	 * 资源编号
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 快照 对应习题的快照
	 *
	 * @see String
	 */
	private String snapshot;

	/**
	 * 对应原习题编号
	 *
	 * @see String
	 */
	private String originId;

	/**
	 * 试卷编号
	 *
	 * @see String
	 */
	private String paperId;

	/**
	 * 提交的答案
	 *
	 * @see String
	 */
	private String submitAnswer;

	/**
	 * 解题状态
	 *
	 * @see Integer
	 */
	private Integer solved;
}
