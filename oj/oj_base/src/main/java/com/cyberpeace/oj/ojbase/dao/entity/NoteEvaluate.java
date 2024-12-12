package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 注意评估
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("oj_note_evaluate")
public class NoteEvaluate extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 得分
	 *
	 * @see Double
	 */
	private Double score;

	/**
	 * 通过
	 *
	 * @see Boolean
	 */
	private Boolean passed;

	/**
	 * 评估评论
	 *
	 * @see String
	 */
	private String evaluateComment;

	/**
	 * 计算器id
	 *
	 * @see String
	 */
	private String evaluatorId;

	/**
	 * 注意id
	 *
	 * @see String
	 */
	private String noteId;

	/**
	 * 评估时间
	 *
	 * @see Date
	 */
	private Date evaluateTime;

}
