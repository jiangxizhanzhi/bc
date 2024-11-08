package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

import java.util.Date;

/**
 * 注意评估
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@TableName("oj_note_evaluate")
public class NoteEvaluate extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 得分
	 */
	private Double score;

	/**
	 * 通过
	 */
	private Boolean passed;

	/**
	 * 评估评论
	 */
	private String evaluateComment;

	/**
	 * 计算器id
	 */
	private String evaluatorId;

	/**
	 * 注意id
	 */
	private String noteId;

	/**
	 * 评估时间
	 */
	private Date evaluateTime;

}
