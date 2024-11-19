package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * 课时试卷评估
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("oj_lesson_paper_evaluate")
public class LessonPaperEvaluate extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 课时id
	 *
	 * @see String
	 */
	private String lessonId;

	/**
	 * 得分
	 *
	 * @see Double
	 */
	private Double score;

	/**
	 * 总数得分
	 *
	 * @see Double
	 */
	private Double totalScore;

	/**
	 * 记录
	 *
	 * @see String
	 */
	private String record;

	/**
	 * 提交用户id
	 *
	 * @see String
	 */
	private String submitUserId;

	/**
	 * 水平
	 *
	 * @see Integer
	 */
	private Integer level;

	/**
	 * 准确性
	 *
	 * @see String
	 */
	private String accuracy;

	/**
	 * 校正者
	 *
	 * @see String
	 */
	private String corrector;

}
