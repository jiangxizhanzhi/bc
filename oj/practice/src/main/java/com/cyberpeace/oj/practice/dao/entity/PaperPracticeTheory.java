package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * 试卷练习理论
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@TableName("oj_paper_practice_theory")
public class PaperPracticeTheory extends BaseTable {

	/**
	 * 资源编号
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 快照 对应习题的快照
	 */
	private String snapshot;

	/**
	 * 对应原习题编号
	 */
	private String originId;

	/**
	 * 试卷编号
	 */
	private String paperId;

	/**
	 * 提交的答案
	 */
	private String submitAnswer;

	/**
	 * 解题状态
	 */
	private Integer solved;
}
